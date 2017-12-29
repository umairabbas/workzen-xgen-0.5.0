/*
 * XGenLoader.java
 * Copyright (c) Brad D Matlack 2002 - 2003
 * License: http://www.gnu.org/gpl
 *
 * This program is free software.
 *
 * You may redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation.
 * Version 2 of the license should be included with this distribution in
 * the file LICENSE, as well as License.html. If the license is not
 * included with this distribution, you may find a copy at the FSF web
 * site at 'www.gnu.org' or 'www.fsf.org', or you may write to the
 * Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139 USA.
 *
 * THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
 * NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
 * OF THIS SOFTWARE, ASSUMES _NO_ bundlePONSIBILITY FOR ANY
 * CONSEQUENCE bundleULTING FROM THE USE, MODIFICATION, OR
 * REDISTRIBUTION OF THIS SOFTWARE.
 */
package workzen.xgen.loader;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import workzen.xgen.XGenException;
import workzen.xgen.model.java.Collection;
import workzen.xgen.model.java.Column;
import workzen.xgen.model.java.Field;
import workzen.xgen.model.java.JavaClass;
import workzen.xgen.model.java.Package;
import workzen.xgen.model.java.Reference;
import workzen.xgen.model.java.TypeMap;
import workzen.xgen.type.XGenType;

/**
 * This loader reads an xml schema and loads the java model Package class.
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class JavaModelXmlLoader extends XmlLoader  {

	private Logger logger = Logger.getLogger(JavaModelXmlLoader.class);

	/**
	 * @param url
	 * @param validate
	 * @return
	 * @throws IOException
	 *
	private Document loadDocument(URL url, boolean validate)
		throws IOException {
		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		try {
			doc = builder.build(url);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return doc;
	}*/

	/**
	 * Load the document into the Package model.
	 * Return the Package as a generic object for use by Velocity.
	 * 
	 */
	public Object loadModel() throws XGenException {
		logger.debug("loadModel()");
		Document doc = super.loadDocument();
		
		Package pkg = new Package();

		Element root = doc.getRootElement();

		// jdo has a top level element
		Element pkgEl = root.getChild("package");
		// if not defined, package element is the root
		if (pkgEl == null) {
			pkgEl = root;
		}

		pkg.setName(pkgEl.getAttributeValue("name"));
		logger.debug("loading package:" + pkg.getName());

		List children = pkgEl.getChildren();
		Iterator it = children.iterator();

		// iterate through each java class
		while (it.hasNext()) {
			Element beanEl = (Element) it.next();
			JavaClass bean = new JavaClass();
			bean.setName(beanEl.getAttributeValue("name"));
			logger.debug("loading bean:" + bean.getName());

			bean.setTableName(beanEl.getAttributeValue("tablename"));
			bean.setHandleConcurrency(
				beanEl.getAttributeValue("handleConcurrency"));
			bean.setHandleAutoIncrement(
				beanEl.getAttributeValue("handleAutoIncrement"));
			bean.setAuthor(beanEl.getAttributeValue("author"));
			bean.setDescription(beanEl.getAttributeValue("description"));
			bean.setImports(beanEl.getAttributeValue("import"));
			bean.setSuperclass(beanEl.getAttributeValue("superclass"));
			pkg.add(bean);

			// fields
			List fields = beanEl.getChildren("field");
			Iterator fit = fields.iterator();
			while (fit.hasNext()) {

				Field field = loadField((Element) fit.next());
				bean.addField(field);

				if (field.isPrimaryKey()) {
					bean.setPrimaryKey(field);
				}
				if (field.isTransactionKey()) {
					bean.setTransactionKey(field);
				}
			}
			// references
			List references = beanEl.getChildren("reference");
			Iterator rit = references.iterator();
			while (rit.hasNext()) {
				Reference ref = loadReference((Element) rit.next());
				bean.addReference(ref);
			}
			// collections
			List collections = beanEl.getChildren("collection");
			Iterator cit = collections.iterator();
			while (cit.hasNext()) {
				workzen.xgen.model.java.Collection col =
					loadCollection((Element) cit.next());
				logger.debug("collection: " + col);	
				bean.addCollection(col);
			}
			checkPrimaryKey(bean);
		}
		return pkg;
	}

	private void checkPrimaryKey(JavaClass javaClass) {
		logger.debug("checkPrimaryKey()");
		Field field = javaClass.getPrimaryKey();
		if (field == null) {
			logger.error(
				"NULL primary key for java class: " + javaClass.getName());
			return;
		}
		if (!field.isPrimaryKey()) {
			logger.error("Field is NOT a primary key: " + field.getName());
		}
	}

	private Field loadField(Element el) throws XGenException {

		Field field = new Field();
		field.setName(el.getAttributeValue("name"));
		field.setIsPrimitive(el.getAttributeValue("primitive"));

		logger.debug("loading field:" + field.getName());

		String typename = el.getAttributeValue("type");
		
		logger.debug("getting type: " + typename);
		if (typename != null) {
			XGenType type = TypeMap.getType(typename);
			field.setType(type);
		}

		Column col = loadColumn(el.getChild("column"));

		// override column elements (allows for legacy definitions)
		col.setName(el.getAttributeValue("col"));
		col.setIsPrimaryKey(el.getAttributeValue("primary-key"));
		col.setIsTransactionKey(el.getAttributeValue("transaction-key"));

		logger.debug("col: " + col);
		logger.debug("field: " + field);

		field.setColumn(col);

		return field;
	}

	private Column loadColumn(Element el) {
		Column col = new Column();
		if (el == null) {
			logger.warn("null column");
			return col;
		}
		col.setName(el.getAttributeValue("name"));
		col.setIsPrimaryKey(el.getAttributeValue("primary-key"));
		col.setIsForeignKey(el.getAttributeValue("foreign-key"));
		col.setIsTransactionKey(el.getAttributeValue("transaction-key"));
		col.setIsAutoincrement(el.getAttributeValue("autoincrement"));
		col.setIsNull(el.getAttributeValue("null"));
		col.setSize(el.getAttributeValue("size"));
		col.setPrecision(el.getAttributeValue("precision"));
		col.setScale(el.getAttributeValue("scale"));
		col.setForeignTable(el.getAttributeValue("foreign-table"));
		return col;
	}

	private Reference loadReference(Element el) {
		Reference ref = new Reference();
		ref.setName(el.getAttributeValue("name"));
		ref.setClassname(el.getAttributeValue("class"));
		ref.setKey(el.getAttributeValue("key"));
		return ref;
	}

	private Collection loadCollection(Element el) {
		Collection col = new workzen.xgen.model.java.Collection();
		col.setName(el.getAttributeValue("name"));
		col.setClassname(el.getAttributeValue("class"));
		col.setTypename(el.getAttributeValue("type"));
		col.setReverseKey(el.getAttributeValue("reverse-key"));
		return col;
	}
}
