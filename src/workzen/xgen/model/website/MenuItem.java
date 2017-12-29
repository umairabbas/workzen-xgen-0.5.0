/*
 * Copyright (c) Jan 20, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.model.website;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Menuitems represent a node in a tree. Each item has a parent,
 * and potentially children.
 * <br>
 * Each item also has a level within the tree.
 * 
 * @author Brad.Matlack
 */
public class MenuItem {

	//private Menu menu;
	private MenuItem parent;
	private ArrayList childItems = new ArrayList();
	//private Page pagex;
	private int level = 0;
	private String label = "undefined";
	private String path; // unqualified: "subdir/page.html"

	/*
	public MenuItem() {
	}*/

	/*
	public MenuItem(String label) {
		setLabel(label);
	}*/

	public MenuItem(String label, String path) {
		setLabel(label);
		setPath(path);
	}

	private void setParent(MenuItem parentMenuItem) {
		this.parent = parentMenuItem;
	}

	public MenuItem getParent() {
		return parent;
	}
	
	public Collection getPeers(){
		if( this.isLeaf() ){
			return getParent().getChildren();
		}
		ArrayList list = new ArrayList();
		list.add(this);
		return list;
	}
	
	public Collection getChildren(){
		return getMenuItems();
	}

	public Collection getParents() {
		LinkedList list = new LinkedList();
		if( this.isRoot()){
			return list;
		}
		getParents(list, this);
		return list;
	}

	private void getParents(LinkedList list, MenuItem item) {
		MenuItem parent = item.getParent();
		//System.out.println("child:" + item.label + " parent:" + parent.label);
		list.addFirst(parent);
		if (parent == null || parent.isRoot()) {
			return;
		}
		getParents(list, parent);
	}

	/**
	 * Add a child menu item, and set the parent to this
	 */
	public void add(MenuItem item) {
		item.setParent(this);
		childItems.add(item);
	}

	public ArrayList getMenuItems() {
		return childItems;
	}

	public boolean isLeaf() {
		return childItems.isEmpty();
	}

	public boolean isRoot() {
		return parent == null ? true : false;
	}

	
	/*
	public String getHtml() {
		if (href == null) {
			return label;
		}
		return "<a href=\"" + href + "\">" + label + "</a>";
	}*/

	public String toString() {
		return getPath();
	}

	/*
	 * 
	 */
	public boolean hasPath() {
		return path == null ? false : true;
	}

	/**
	 * @return
	 *
	public Page getPage() {
		return page;
	}

	
	public void setPage(Page page) {
		this.page = page;
	}*/

	/**
	 * @return
	 *
	public String getHref() {
		return href;
	}*/

	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param string
	 *
	public void setHref(String string) {
		href = string;
	}*/

	/**
	 * @param string
	 */
	public void setLabel(String string) {
		label = string.trim();
	}
	
	public boolean equals(Object o){
		MenuItem mi = (MenuItem)o;
		String a = mi.getPath();
		String b = getPath();
		if( a ==  null ){
			return false;
		}
		//System.out.println("a=b:" + a + " " + b);
		if( a.equals(b) ){
			return true;
		}
		return false;
	}

	/**
	 * @return
	 *
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}*/

	/**
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param i
	 */
	public void setLevel(int i) {
		level = i;
	}

	/**
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param string
	 */
	public void setPath(String string) {
		path = string;
	}

}
