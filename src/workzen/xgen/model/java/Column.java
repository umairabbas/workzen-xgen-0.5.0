/*
 * Column.java
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

import org.apache.log4j.Logger;

/**
 * This class models a sql column, for use with code generation.
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class Column {

	private String name = "undefined";
	private boolean isNull = false;
	private boolean isPrimaryKey = false;
	private boolean isForeignKey = false;
	private boolean isTransactionKey = false;
	private boolean isAutoincrement = false;
	private String foreignTable = "undefined";
	private String size = "0";
	private String precision = "0";
	private String scale = "0";

	private Logger logger = Logger.getLogger(Column.class);

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	public String getName() {
		return name;
	}

	public void setIsNull(String value) {
		if (value != null) {
			this.isNull = value.equals("true") ? true : false;
		}
	}
	public void setIsPrimaryKey(String value) {
		if (value != null) {
			this.isPrimaryKey = value.equals("true") ? true : false;
		}
	}
	public void setIsForeignKey(String value) {
		if (value != null) {
			this.isForeignKey = value.equals("true") ? true : false;
		}
	}
	public void setIsAutoincrement(String value) {
		if (value != null) {
			this.isAutoincrement = value.equals("true") ? true : false;
		}
	}
	public void setIsTransactionKey(String value) {
		if (value != null) {
			this.isTransactionKey = value.equals("true") ? true : false;
		}
	}

	/**
	 * @return
	 */
	public boolean isAutoincrement() {
		return isAutoincrement;
	}

	/**
	 * @return
	 */
	public boolean isForeignKey() {
		return isForeignKey;
	}

	/**
	 * @return
	 */
	public boolean isNull() {
		return isNull;
	}

	/**
	 * @return
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public boolean isVarchar() {
		return !size.equals("0");
	}

	/**
	 * @return
	 */
	public String getPrecision() {
		return precision;
	}

	/**
	 * @return
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * @return
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param b
	 */
	public void setIsAutoincrement(boolean b) {
		isAutoincrement = b;
	}

	/**
	 * @param b
	 */
	public void setIsForeignKey(boolean b) {
		isForeignKey = b;
	}

	/**
	 * @param b
	 */
	public void setIsNull(boolean b) {
		isNull = b;
	}

	/**
	 * @param b
	 */
	public void setIsPrimaryKey(boolean b) {
		isPrimaryKey = b;
	}

	/**
	 * @param string
	 */
	public void setPrecision(String string) {
		if (string != null) {
			precision = string;
		}
	}

	/**
	 * @param string
	 */
	public void setScale(String string) {
		if (string != null) {
			scale = string;
		}
	}

	/**
	 * @param string
	 */
	public void setSize(String string) {
		if (string != null) {
			size = string;
		}
	}

	/**
	 * @return
	 */
	public String getForeignTable() {
		return foreignTable;
	}

	/**
	 * @param string
	 */
	public void setForeignTable(String string) {
		foreignTable = string;
	}

	/**
	 * @return
	 */
	public boolean isTransactionKey() {
		return isTransactionKey;
	}

	/**
	 * @param b
	 */
	public void setIsTransactionKey(boolean b) {
		isTransactionKey = b;
	}

	public String getDebugInfo() {
		HashMap map = new HashMap();
		map.put("name", name);
		map.put("isNull", "" + isNull);
		map.put("isPrimaryKey", "" + isPrimaryKey);
		map.put("isForeignKey", "" + isForeignKey);
		map.put("isTransactionKey", "" + isTransactionKey);
		map.put("isAutoincrement", "" + isAutoincrement);
		map.put("size", size);
		map.put("precision", precision);
		map.put("scale", scale);
		return map.toString();
	}

	public String toString() {
		return getDebugInfo();
	}

}
