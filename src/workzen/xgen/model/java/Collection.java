/*
 * Collection.java
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

import workzen.common.util.StringUtil;

/**
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class Collection {

	private String name = "undefined";
	private String classname = "undefined";
	private String typename = "undefined";
	private String reverseKey = "undefined";

	/**
	 * @return
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	public String getCapName() {
		return StringUtil.capitalize(name);
	}

	/**
	 * @return
	 */
	public String getTypename() {
		return typename;
	}

	/**
	 * @param string
	 */
	public void setClassname(String string) {
		classname = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setTypename(String string) {
		typename = string;
	}

	public String getDebugInfo() {
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("classname", classname);
		map.put("typename", typename);
		return map.toString();
	}

	public String toString() {
		return getDebugInfo();
	}

	/**
	 * @return
	 */
	public String getReverseKey() {
		return reverseKey;
	}

	/**
	 * @param string
	 */
	public void setReverseKey(String string) {
		reverseKey = string;
	}

	public String getAttributeSetter() {
		String attr = StringUtil.capitalize(name);
		return "set" + attr;
	}

	public String getAttributeGetter() {
		String attr = StringUtil.capitalize(name);
		return "get" + attr;
	}

}
