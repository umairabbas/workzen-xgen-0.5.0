/*
 * TypeMap.java
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
 * OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
 * CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
 * REDISTRIBUTION OF THIS SOFTWARE.
 */
package workzen.xgen.type;

import java.sql.Types;
import java.util.HashMap;

/**
 * This class is used to map the "type" of an xgen field to an actual java type.
 * It returns string values for the: Java classname, classname abbreviation, 
 * primitive type name, and the class primitive function that returns the primitive value.
 * 
 *  http://java.sun.com/j2se/1.3/docs/guide/jdbc/spec/jdbc-spec.frame8.html
 */
public abstract class XGenType {

	protected String name = "Undefined"; 
	protected String qualifiedName = "java.lang.Undefined"; 
	protected String primitiveName = "undefined"; 
	protected String primitiveFunction = "undefinedValue()"; 
	protected String sqlType = "" + Types.NULL;

	public String getName() {
		return name;
	}

	public String getPrimitiveFunction() {
		return primitiveFunction;
	}

	public String getPrimitiveName() {
		return primitiveName;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}
	
	public String getSqlType() {
		return sqlType;
	}

	public String getDebugInfo(){
		HashMap map = new HashMap();
		map.put("name",name);
		map.put("qualifiedName",qualifiedName);
		map.put("primitiveName",primitiveName);
		map.put("primitiveFunction",primitiveFunction);
		map.put("sqlType", sqlType);
		return map.toString();
	}

	/** 
	 * to String with specified line terminator
	 */
	public String toString() {
		return getDebugInfo();
	}

}
