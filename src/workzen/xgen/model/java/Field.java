/* 
 * Field.java
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

import java.util.HashMap;

import workzen.common.util.StrBuf;
import workzen.common.util.StringUtil;
import workzen.xgen.*;
import workzen.xgen.type.XGenType;

/**
 * The Field class stores metadata about a java attribute, and its corresponding sql table column.
 *
 * @author <a href="mailto:matlackb@users.sourceforge.net">Brad Matlack</a>
 * @version $Id: Field.java $
 */
public class Field {
	private String name;
	private JavaClass javaClass; 
	private XGenType type;
	private Column column;
	private boolean isPrimitive = false;
	
	/* default constructor */
	public Field() {
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setType(XGenType type) throws XGenException {
		this.type = type;
	}

	public boolean isPrimaryKey() {
		return column.isPrimaryKey();
	}

	public boolean isTransactionKey() {
		return column.isTransactionKey();
	}

	public String getName() {
		return name;
	}

	public String getCapName() {
		return StringUtil.capitalize(name);
	}

	public XGenType getType() {
		return type;
	}
	
	public String getTypeName(){
		if( isPrimitive ){
			return type.getPrimitiveName();
		}
		return type.getName();
	}

	public void setIsPrimitive(String str){
		if( str != null ){
			if( str.equals("true")){
				this.isPrimitive = true;
			}
		}
	}
	
	public void setIsPrimitive(boolean bool){
		this.isPrimitive = bool;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}
	
	public String getPrimitiveType(){
		return type.getPrimitiveName();
	}
	
	public String getPrimitiveFunction(){
		return type.getPrimitiveFunction();
	}

	public String getAttributeSetter() {
		String attr = StringUtil.capitalize(name);
		return "set" + attr;
	}

	public String getAttributeGetter() {
		String attr = StringUtil.capitalize(name);
		return "get" + attr;
	}

	/**
	 * @return "setInt"
	 */
	public String getResultSetter() {
		String primitive = type.getPrimitiveName();
		primitive = StringUtil.capitalize(primitive);
		return "set" + primitive;
	}

	/**
	 * @return "setInt"
	 */
	public String getResultGetter() {
		String primitive = type.getPrimitiveName();
		primitive = StringUtil.capitalize(primitive);
		return "get" + primitive;
	}

	/** 
	 * to String 
	 */
	public String toString() {
		return getDebugInfo();
	}

	/** 
	 * to String with specified line terminator
	 */
	public String getDebugInfo() {
		HashMap map = new HashMap();
		map.put("name",name);
		map.put("isPrimitive", "" + isPrimitive);
		map.put("type", type.getName());
		return map.toString();
	}

	/**
	 * @return
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * @return
	 */
	public JavaClass getJavaClass() {
		return javaClass;
	}

	/**
	 * @param column
	 */
	public void setColumn(Column column) {
		this.column = column;
	}

	/**
	 * @param class1
	 */
	public void setJavaClass(JavaClass class1) {
		javaClass = class1;
	}

}
