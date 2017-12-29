/*
 * DirectoryTreeModel.java
 * Copyright (c) Brad D Matlack 2006
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

package workzen.xgen.model.website;

import org.apache.log4j.*;

import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;

/**
 * MenuTreeModel models a navigation tree.
 * This TreeModel returns MenuItems instead of DefaultTreeNodes.
 * 
 * <p>Reference http://java.sun.com/products/jfc/tsc/articles/jtree.</p>
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class MenuTreeModel implements TreeModel {

	protected Vector modelListeners = new Vector();
	protected MenuItem root;
	
	protected Logger logger = Logger.getLogger(MenuTreeModel.class);

	public MenuTreeModel(){
		this.root = new MenuItem("root","index.html");
	}

	/** 
	 * @param File root
	 */
	public MenuTreeModel(MenuItem root) {
		this.root = root;
	}
	
	public void insertNode(MenuItem item){
		root.add(item);
	}
	
	public void setRoot(MenuItem item){
		this.root = item;
	}

	/** 
	 * TreeModel method
	 */
	public Object getRoot() {
		return root;
	}

	/** 
	 * TreeModel method.
	 * Get an "ordered" child.
	 */
	public Object getChild(Object parent, int index) {
		logger.debug("getChild at:" + index + " for parent:" + parent.toString());

		MenuItem child = null;

		try {
			ArrayList list = ((MenuItem) parent).getMenuItems(); 
			child = (MenuItem)list.get(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Error getting child:" + e.getMessage());
			e.printStackTrace();
		}
		return child;
	}

	/** 
	 * TreeModel method
	 */
	public int getChildCount(Object parent) {
		MenuItem item = (MenuItem)parent;
		int size = item.getMenuItems().size();
		logger.debug("getChildCount() " + size);
		return size;
	}

	/** 
	 * TreeModel method.
	 * Spin through all children, looking for a matching name. 
	 * Hmmmm. can we speed this up?
	 */
	public int getIndexOfChild(Object parent, Object child) {
		MenuItem menu = (MenuItem)parent;
		MenuItem item = (MenuItem)child;
		ArrayList list = menu.getMenuItems();
		int index = list.indexOf(item);
		logger.debug("getIndexOfChild() " + index);
		return index;
	}

	/** 
	 * TreeModel method
	 */
	public boolean isLeaf(Object node) {
		MenuItem item = (MenuItem)node;
		boolean isLeaf = item.isLeaf();
		logger.debug("isLeaf() [" + item.getLabel() + "] " + isLeaf);
		return isLeaf;
	}

	/** 
	 * TreeModel method
	 * Implement if tree is editable
	 */
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	public void addTreeModelListener(TreeModelListener listener) {
		if (listener != null && !modelListeners.contains(listener)) {
			modelListeners.addElement(listener);
		}
	}

	public void removeTreeModelListener(TreeModelListener listener) {
		if (listener != null) {
			modelListeners.removeElement(listener);
		}
	}
}
