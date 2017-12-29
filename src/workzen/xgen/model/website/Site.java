/*
 * Copyright (c) Jan 20, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.model.website;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.tree.TreeModel;

/**
 * The site stores pages, and each page has a menuitem.
 * The site maps the pages by name. A TreeModel is also loaded
 * for convience.
 * <br>
 * The top level xml definition is a &lt;website&gt;
 * Subelements include &lt;menu&gt; and &lt;menuitem&gt; elements,
 * however, these elements are all modeled as menuitems.
 * <br>
 * Each menuitem can contain other menuitems, forming a tree. 
 * 
 * @author Brad.Matlack
 */
public class Site {

	private String sourceBasePath = "undefined_sourcebasepath";
	private String destBasePath = "undefined_destbasepath";
	private String webroot = "undefined_webroot";
	//private String indexFile = "index.html";
	private MenuTreeModel treeModel = new MenuTreeModel();
	private MenuItem rootMenuItem;

	/*
	 * We use a map to prevent duplication of pages.
	 * Velocity will continue to merge indefinately.
	 */
	//private TreeMap pages = new TreeMap();
	private ArrayList pages = new ArrayList();

	/**
	 * SiteException
	 * @author brad.matlack
	 */
	public class SiteException extends Exception {
		public SiteException(String msg, Throwable t) {
			super(msg, t);
		}
	}

	/*
	public void setModel(TreeModel model) {
		this.model = model;
	}
	
	public TreeModel getModelxxxx() {
		return model;
	}*/

	/*
	public String getRootHref(){
		return webroot + "/" + indexFile;
	}*/

	/*
	public void getChildMenuItems(MenuItem parent) {
	
	}*/

	public TreeModel getTreeModel() {
		return treeModel;
	}

	public void setRootMenu(MenuItem rootItem) {
		this.rootMenuItem = rootItem;
		treeModel.setRoot(rootItem);
	}

	public MenuItem getRootMenu() {
		//return (MenuItem)model.getRoot();
		return rootMenuItem;
	}

	public Collection getPages() {
		//return pages.values();
		return pages;
	}
	
	public ArrayList getOrderedPages(){
		return pages;
	}

	public void addPage(Page page) {
		this.addPage(page.getMenuItem().getPath(), page);
	}

	private void addPage(String key, Page page) {
		if (key == null) {
			key = ""; // keep the treeMap from barfing
		}
		page.setSite(this);
		//pages.put(key,page);
		pages.add(page);
	}

	/**
	 * This method creates a source file from the menuitem href,
	 * instantiates a new Page, reads the sourcefile if it exists,
	 * and adds the page to the site.
	 *
	public void addMenuItemxxx(MenuItem item) {
		//System.out.println("addMenuItem() " + item.getLabel());
		System.out.println("addMenuItem() " + item);
		//File file = getSourceFile(item);
		if (item.hasHref()) {
			Page page = new Page(this,item);
			item.setPage(page);
			addPage(item.getHref(), page);
		}
	}*/

	/**
	 * 
	 */
	public File getDestFile(Page page) {
		return getDestFile(page.getMenuItem());
	}

	public File getDestFile(MenuItem item) {
		String url = getDestPath(item);

		System.out.println("site.getDestFile():" + url);

		File file = new File(url);
		return file;
	}

	/**
	 * Path to the destination file:
	 * destBasePath + pagePath
	 * 
	 * @param item
	 * @return
	 */
	private String getDestPath(MenuItem item) {
		return destBasePath + "/" + item.getPath();
	}

	public File getSourceFile(MenuItem item) {
		// Some items only have labels
		/*
		if (menuItem.hasHref() == false) {
			System.out.println("NULL HREF");
			return null;
		}*/
		String url = getSourcePath(item);
		System.out.println("site.getSourceFile():" + url);
		File file = new File(url);
		return file;
	}

	/**
	 * Return sourceBasePath  + pagePath
	 * @return
	 */
	private String getSourcePath(MenuItem menuItem) {
		String pagePath = menuItem.getPath();
		/*
		if (href.startsWith("/") == false) {
			href = "/" + href;
		}
		*/
		String sourcePath = getSourceBasePath();
		//System.out.println("SOURCE: " + source);
		String sourceUrl = sourcePath + "/" + pagePath;
		return sourceUrl;
	}

	public boolean sourceFileExists(Page page) {
		File srcFile = getSourceFile(page.getMenuItem());
		//System.out.println("sourceFileExists() " + srcFile);
		return srcFile.exists();
	}

	/**
	 * The sourcefile must exist, and must not be a directory.
	 * 
	 * @param page
	 * @return
	 */
	public boolean isValidPage(Page page) {
		File srcFile = getSourceFile(page.getMenuItem());
		if (srcFile.exists() && srcFile.isDirectory() == false) {
			return true;
		}
		return false;
	}

	/**
	 * Compare the page, and the page's parents to the item 
	 * 
	 * @param page
	 * @param item
	 * @return
	 */
	public boolean isSelected(Page page, MenuItem item) {
		if (page.getMenuItem() == item) {
			return true;
		}
		Collection parentMenus = page.getParentMenus();
		Iterator it = parentMenus.iterator();
		while (it.hasNext()) {
			MenuItem parent = (MenuItem) it.next();
			if (parent == item) {
				return true;
			}
		}
		return false;
	}
	
	public String getAbsoluteHref(MenuItem item){
		return getAbsoluteHref("a", item);
	}

	/**
	 * @return webroot + item.path();
	 */
	public String getAbsoluteHref(String tag, MenuItem item) {
		if (item.hasPath() == false) {
			return item.getLabel();
		}
		String href = webroot + "/" + item.getPath();
		String html = "<" + tag + " href=\"" + href + "\">" + item.getLabel() + "</" + tag + ">";
		//System.out.println("HREF " + href);
		return html;
	}

	/**
	 * Getting the relative path is much more complex.
	 * Just stripping off the parent path will not work.
	 * Navigation on a level with subdirectories is different
	 * that navigation on a level with files. This method
	 * must be able to navigate backwards (../path).
	 * 
	 * @param item
	 * @return
	 */
	public String getRelativeHrefFIXME(MenuItem item) {
		MenuItem parent = item.getParent();
		int parentLength = 0;
		if (parent != null && parent.hasPath()) {
			String parentPath = parent.getPath();
			parentLength = parentPath.length();
		}
		String href = "";
		if (item.hasPath()) {
			href = item.getPath();
		}
		// strip the parent path from the child path
		if (href.length() > parentLength) {
			href = href.substring(parentLength);
		}
		if (href.startsWith("/") || href.startsWith("\\")) {
			href = href.substring(1);
		}
		String html = "<a href=\"" + href + "\">" + item.getLabel() + "</a>";
		System.out.println("HREF " + href);
		return html;
	}
	

	/**
	 * @param menuItem
	 */
	public String getHref(MenuItem menuItem) {
		return getAbsoluteHref(menuItem);
		//return getRelativeHref(menuItem);
	}

	public String getHRef(MenuItem menuItem) {
		return getHref(menuItem);
	}

	/**
	 * @return
	 */
	public String getSourceBasePath() {
		return sourceBasePath;
	}

	/**
	 * @param string
	 */
	public void setSourceBasePath(String string) {
		sourceBasePath = string;
	}

	/**
	 * @param list
	 *
	public void setPages(ArrayList list) {
		pages = list;
	}*/

	/**
	 * @return
	 */
	public String getDestBasePath() {
		return destBasePath;
	}

	/**
	 * @param string
	 */
	public void setDestBasePath(String string) {
		destBasePath = string;
	}

	/**
	 * @return
	 */
	public String getWebRoot() {
		return webroot;
	}

	/**
	 * @param string
	 */
	public void setWebRoot(String string) {
		webroot = string;
	}

	/**
	 * @return
	 *
	public String getIndexFile() {
		return indexFile;
	}
	
	
	public void setIndexFile(String string) {
		indexFile = string;
	}*/

}
