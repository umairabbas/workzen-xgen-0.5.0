/*
 * Copyright (c) Jan 20, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.model.website;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import workzen.common.util.FileUtil;

/**
 * Each page contains a reference to a menuitem.
 * A Page provides access to an existing file in the filesystem.
 * The path is created using the site's sourceBasePath, and the  
 * menuitem's href.
 * 
 * @author Brad.Matlack
 */
public class Page {

	private Site site;
	//private File file;
	private MenuItem menuItem;
	//private String body = "undefined"; 

	public Page(MenuItem item) {
		//this.file = file;
		//this.site = site;
		this.menuItem = item;
		//menuItem.setPage(this);
		//this.body = readFile();
		//System.out.println("body: " + body);
	}
	
	public String toString(){
		return menuItem.getLabel();
	}
	
	public void setSite(Site site){
		this.site = site;
	}

	public String getBody() {
		File file = site.getSourceFile(menuItem);
		/*
		if (file != null && file.exists()) {
			return FileUtil.readFile(file);
		}*/
		String body;
		try {
			body = FileUtil.readFile(file);
		} catch (Exception e) {
			return (e.getMessage());
		}
		return body;
	}

	/*
	public File getSourceFile() {
		// Some items only have labels
		
		String url = getSourcePath();
		System.out.println("page.getSourceFile():" + url);
		File file = new File(url);
		return file;
	}


	private String getSourcePath() {
		String pagePath = menuItem.getPath();
		
		String sourcePath = site.getSourceBasePath();
		//System.out.println("SOURCE: " + source);
		String sourceUrl = sourcePath + "/" + pagePath;
		return sourceUrl;
	}*/

	public boolean isLeaf() {
		return menuItem.isLeaf();
	}

	public boolean isRoot() {
		return menuItem.isRoot();
	}

	public boolean isSelected(MenuItem item) {
		return item == menuItem ? true : false;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	/*
	public Collection getMenuItems(){
		return menuItem.getMenuItems();
	}*/

	public Collection getParentMenus() {
		return menuItem.getParents();
	}

	public Collection getChildMenus() {
		return menuItem.getChildren();
	}

	public MenuItem getParentMenu() {
		return menuItem.getParent();
	}

	/*
	public Collection getPeerMenus(){
		return menuItem.getPeers();
	}*/

	/**
	 * FIX ME, don't return webroot
	 *
	public String getRelativePath() {
		String href = menuItem.getHref();
		if( href == null || href.equals("")){
			//return menuItem.getLabel();
		}
		return href;
	}*/
	
	public String getRelativePath(){
		return menuItem.getPath();
	}

}
