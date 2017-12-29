/*
 * JavaClass.java
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
package workzen.xgen.model.java;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * This class stores metadata about a java class, including references
 * and collections, and information about the corresponding sql table.
 * It has a map of all the fields in the class, 
 * and knows which attributes are the primary key or transaction key.
 *
 * @author <a href="mailto:matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class JavaClass {

	// java attributes
	private String name = "undefined";
	private Vector fields = new Vector();
	private Vector references = new Vector();
	private Vector collections = new Vector();
	private HashMap fieldMap = new HashMap();
	private String imports = "";
	private String superclass = "";
			
	// javadoc attribites
	private String author;
	private String description;
	
	// sql attributes
	private String tablename;
	private boolean handleAutoIncrement = true;
	private boolean handleConcurrency = false;
	private Field primaryKey;
	private Field transactionKey;

	/** classname */
	public void setName(String name) {
		this.name = name;
	}
	public void setTableName(String name) {
		this.tablename = name;
	}
	public void setPrimaryKey(Field field) {
		this.primaryKey = field;
	}
	public void setTransactionKey(Field field) {
		this.transactionKey = field;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setDescription(String desc) {
		this.description = desc;
	}

	public String getName() {
		return name;
	}
	public String getTableName() {
		return tablename;
	}
	public Field getPrimaryKey() {
		return primaryKey;
	}
	public Field getTransactionKey() {
		return transactionKey;
	}
	public String getAuthor() {
		return author;
	}
	public String getDescription() {
		return description;
	}

	public Vector getFields() {
		return fields;
	}

	public void setHandleConcurrency(boolean val) {
		this.handleConcurrency = val;
	}
	public void setHandleAutoIncrement(boolean val) {
		this.handleAutoIncrement = val;
	}

	public boolean handleConcurrency() {
		return handleConcurrency;
	}
	public boolean handleAutoIncrement() {
		return handleAutoIncrement;
	}

	public void setHandleConcurrency(String value) {
		if( value != null ){
			handleConcurrency = (value.equals("true")) ? true : false;
		}
	}

	public void setHandleAutoIncrement(String value) {
		if( value != null ){
			handleAutoIncrement = (value.equals("true")) ? true : false;
		}
	}
	
	public void setImports(String val){
		if( val != null ){
			this.imports = val;
		}
	}
	
	public void setSuperclass(String val){
		if( val != null ){
			this.superclass = val;
		}
	}

	public void addField(Field field) {
		fields.addElement(field);
		fieldMap.put(field.getName(),field);
	}
	
	public void addReference(Reference ref){
		references.addElement(ref);
	}
	
	public void addCollection(workzen.xgen.model.java.Collection col){
		collections.addElement(col);
	}
	
	public Collection getReferences(){
		return references;
	}
	
	public Collection getCollections(){
		return collections;
	}
	
	public boolean hasCollections(){
		return ! collections.isEmpty();
	}
	
	public boolean hasReferences(){
		return ! references.isEmpty();
	}
	
	public boolean hasImports(){
		return ! imports.equals("");
	}
	
	public boolean hasSuperclass(){
		return ! superclass.equals("");
	}
	
	public Field getFieldByName(String name){
		return (Field)fieldMap.get(name);
	}
	
	public String getImports(){
		return imports;
	}
	
	public String getSuperclass(){
		return superclass;
	}

	/**
	 * Construct a CSV list of the field names
	 */
	public String getSqlColumnsAsCSV() {
		StringBuffer buf = new StringBuffer();
		Iterator it = fields.iterator();
		while (it.hasNext()) {
			Field field = (Field) it.next();
			buf.append(field.getColumn().getName());
			if (it.hasNext())
				buf.append(",");
		}
		return buf.toString();
	}

	/**
	 * Construct a quoted CSV list of the field names
	 */
	public String getQuotedSqlColumns() {
		StringBuffer buf = new StringBuffer();
		Iterator it = fields.iterator();
		while (it.hasNext()) {
			Field field = (Field) it.next();
			buf.append("\"");
			buf.append(field.getColumn().getName());
			buf.append("\"");
			if (it.hasNext())
				buf.append(",");
		}
		return buf.toString();
	}

	/**
	 * Construct a CSV list of field placeholders
	 * @return
	 */
	public String getSqlPlaceHoldersAsCSV() {
		StringBuffer buf = new StringBuffer();
		Iterator it = fields.iterator();
		while (it.hasNext()) {
			Field field = (Field) it.next();
			buf.append("?");
			if (it.hasNext())
				buf.append(",");
		}
		return buf.toString();
	}

	/** 
	 * to String 
	 */
	public String toString() {
		return getDebugInfo();
	}

	/** 
	 * 
	 */
	public String getDebugInfo() {
		HashMap map = new HashMap();
		map.put("name",name);
		map.put("tablename",tablename);
		map.put("handleConcurrency","" + handleConcurrency);
		map.put("handleAutoIncrement","" + handleAutoIncrement);
		map.put("author",author);
		map.put("description",description);
		map.put("import", imports);
		map.put("superclass", superclass);
		map.put("hasReferences","" + hasReferences());
		map.put("hasCollections","" + hasCollections());
		
		if (primaryKey != null) {
			map.put("primaryKey", getPrimaryKey().getName());
		}
		if (transactionKey != null) {
			map.put("transactionKey", getTransactionKey().getName());
		}
		return map.toString();
	}
}
