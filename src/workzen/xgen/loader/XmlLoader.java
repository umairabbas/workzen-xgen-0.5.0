/*
 * DocLoader.java
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

import java.net.URL;
import java.util.Map;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import workzen.xgen.XGenException;
import workzen.xgen.model.xml.XmlDoc;
import workzen.xgen.util.PropertyUtil;

/**
 * Load the xml schema into a XmlDoc model.
 * This is a common baseclass for loaders that need JDom Document access.
 * <br>
 * <b>Properties</b>
 * <br>
 * <pre>
 * xml.filepath
 * </pre>
 * 
 * @author <a href="mailto:matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class XmlLoader implements ILoader {

	public static final String XML_FILEPATH = "xmlFilePath";
	public static final String XML_VALIDATE = "xmlValidate";

	private String xmlFilepath = "undefined_xmlFilepath";
	private boolean validate = false;

	public void configure(Map map) throws XGenException {
		this.xmlFilepath = PropertyUtil.getRequired(map, XML_FILEPATH);
		this.validate = PropertyUtil.getOptionalBoolean(map, XML_VALIDATE, validate);
	}

	/**
	 * Load the JDom Document into an XmlDoc model.
	 * @return XmlDoc model.
	 */
	public Object loadModel() throws XGenException {
		Document doc = null;
		try {
			doc = loadDocument();
		} catch (Throwable t) {
			throw new XGenException("Error loading Document model", t);
		}
		XmlDoc xmlDoc = new XmlDoc(doc);
		return xmlDoc;
	}

	public Document loadDocument() throws XGenException {
		URL url = createURL(xmlFilepath);
		return loadJDomDocument(url, validate);
	}

	/**
	 * @param url
	 * @param validate
	 * @return
	 * @throws IOException
	 */
	public static Document loadJDomDocument(URL url, boolean validate)
		throws XGenException {
		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		try {
			doc = builder.build(url);
		} catch (Throwable t) {
			throw new XGenException("Error loading document", t);
		}
		return doc;
	}

	/**
	 * Create and return a file based URL
	 * @param filepath
	 * @throws XGenException
	 */
	public static URL createURL(String filepath) throws XGenException {
		URL url = null;
		try {
			url = new java.net.URL("file:///" + filepath);
		} catch (Throwable t) {
			throw new XGenException("Error creating URL", t);
		}
		return url;
	}
}
