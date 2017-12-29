/*
 * Package.java
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

import java.util.*;

/**
 * The Package class stores metadata about contained Java classes, 
 *
 * @author <a href="mailto:matlackb@users.sourceforge.net">Brad Matlack</a>
 * @version $Id: Bean.java $
 */
public class Package {

	private String name = "undefined";

	private Vector beans = new Vector();
	private HashMap beanMap = new HashMap();

	/** add a bean to the package */
	public void add(JavaClass bean) {
		beans.addElement(bean);
		beanMap.put(bean.getName(), bean);
	}
	
	public JavaClass getClassByName(String name){
		return (JavaClass)beanMap.get(name);
	}

	/** 
	 * set package name 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/** 
	 * get package name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Legacy accessor
	 * @return
	 */
	public Vector getBeans() {
		return beans;
	}
	
	/**
	 * @return
	 */
	public List getJavaClasses(){
		return beans;
	}

	/** 
	 * to String 
	 */
	public String toString() {
		return toString(",");
	}

	/** 
	 * to String with specified line terminator
	 */
	public String toString(String nl) {
		StringBuffer buf = new StringBuffer();
		buf.append(getName());
		buf.append(nl);
		Enumeration e = beans.elements();
		while (e.hasMoreElements()) {
			JavaClass bean = (JavaClass) e.nextElement();
			buf.append(bean.toString());
			buf.append(nl);
		}
		return buf.toString();
	}

}
