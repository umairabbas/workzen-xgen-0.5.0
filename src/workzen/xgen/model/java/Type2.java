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
 * OF THIS SOFTWARE, ASSUMES _NO_ bundlePONSIBILITY FOR ANY
 * CONSEQUENCE bundleULTING FROM THE USE, MODIFICATION, OR
 * REDISTRIBUTION OF THIS SOFTWARE.
 */
package workzen.xgen.model.java;

import java.util.HashMap;

/**
 * This class is used to map the "type" of a field to an actual java type.
 * It returns string values for the: Java classname, classname abbreviation, 
 * primitive type name, and the class primitive function that returns the primitive value.
 * @author <a href="mailto:matlackb@users.sourceforge.net">Brad Matlack</a> 
 */
public class Type2 {

	private String name; // Byte
	private String qualifiedName; // java.lang.Byte
	private String primitiveName; // byte
	private String primitiveFunction; // byteValue()

	public Type2(
		String name,
		String primitiveName,
		String qualifiedName,
		String primitiveFunction) {
		this.name = name;
		this.qualifiedName = qualifiedName;
		this.primitiveName = primitiveName;
		this.primitiveFunction = primitiveFunction;
	}

	public void setName(String val) {
		this.name = val;
	}
	public void setClassName(String val) {
		this.qualifiedName = val;
	}
	public void setPrimitiveName(String val) {
		this.primitiveName = val;
	}
	public void setPrimitiveFunction(String val) {
		this.primitiveFunction = val;
	}
	public String getName(){
		return name;
	}
	public String getClassName() {
		return name;
	}
	public String getQualifiedName() {
		return qualifiedName;
	}
	public String getPrimitiveName() {
		return primitiveName;
	}
	public String getPrimitiveFunction() {
		return primitiveFunction;
	}

	/**
	 * @return
	 *
	public boolean isPrimitive() {
		if (name.equals("byte")
		    || name.equals("boolean")
			|| name.equals("short")
			|| name.equals("int")
			|| name.equals("long")
			|| name.equals("float")
			|| name.equals("double")) {
			return true;
		}
		return false;
	}*/

	public String getDebugInfo(){
		HashMap map = new HashMap();
		map.put("name",name);
		map.put("qualifiedName",qualifiedName);
		map.put("primitiveName",primitiveName);
		map.put("primitiveFunction",primitiveFunction);
		return map.toString();
	}

	/** 
	 * to String with specified line terminator
	 */
	public String toString() {
		return getDebugInfo();
	}

}
