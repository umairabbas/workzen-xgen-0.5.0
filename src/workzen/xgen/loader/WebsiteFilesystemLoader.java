/*
 * Copyright (c) May 5, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.loader;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.tree.TreeModel;

import workzen.common.filesystem.FilesystemTreeModel;
import workzen.common.filesystem.filter.IFileFilter;
import workzen.common.util.BeanUtil;
import workzen.common.util.ResourceLoader;
import workzen.xgen.XGenException;
import workzen.xgen.model.website.MenuItem;
import workzen.xgen.model.website.Page;
import workzen.xgen.model.website.Site;
import workzen.xgen.util.PropertyUtil;

/**
 * This class walks the filesystem, and builds a Site using the
 * existing filesystem structure.
 * 
 * @author brad.matlack
 */
public class WebsiteFilesystemLoader implements ILoader {

	public static final String WEB_ROOT = "webRoot";
	public static final String SRC_BASEPATH = "srcBasePath";
	public static final String CSV_INCLUDE_LIST = "csvIncludeList";
	public static final String CSV_EXCLUDE_LIST = "csvExcludeList";
	public static final String FILE_FILTER_CLASS = "fileFilterClass";
	public static String OUTPUT_PATH = "outputPath"; // match of VelocityProcessor property

	private String webroot = "/undefined_webroot";
	private String srcBasePath = "/undefined_source_basepath";
	private String csvIncludeList = "*.html, *.htm";
	private String csvExcludeList = null;
	private String fileFilterClass = "workzen.common.filesystem.filter.IncludeFileExcludeDir";

	private Site site = new Site();
	private IFileFilter filter;
	private String[] includePatterns;
	private String[] excludePatterns;
	private Logger logger =
		Logger.getLogger(WebsiteFilesystemLoader.class.getName());

	public WebsiteFilesystemLoader() {
	}

	public void configure(Map map) throws XGenException {
		this.webroot = PropertyUtil.getRequired(map, WEB_ROOT);
		this.srcBasePath = PropertyUtil.getRequired(map, SRC_BASEPATH);
		this.csvIncludeList =
			PropertyUtil.getOptional(map, CSV_INCLUDE_LIST, csvIncludeList);
		this.csvExcludeList =
			PropertyUtil.getOptional(map, CSV_EXCLUDE_LIST, null);
		this.fileFilterClass =
			PropertyUtil.getOptional(map, FILE_FILTER_CLASS, fileFilterClass);

		this.includePatterns = csvIncludeList.split(",");
		this.excludePatterns = csvExcludeList.split(",");

		try {
			this.filter = (IFileFilter) BeanUtil.getInstance(fileFilterClass);
			filter.setPatterns(includePatterns, excludePatterns);
		} catch (Throwable t) {
			throw new XGenException("Error instantiating FileFilter", t);
		}
		
		site.setDestBasePath(PropertyUtil.getRequired(map, OUTPUT_PATH));
		
	}

	/*
	public WebsiteFilesystemLoader(String webroot) {
		this.webroot = webroot;
	}*/

	/** 
	 * Load the filesystem into a TreeModel
	 *
	public void loadTreeModel(TreeModel model, Object parent) {
		int count = model.getChildCount(parent);
		for (int i = 0; i < count; i++) {
			File child = (File) model.getChild(parent, i);
			//logger.debug(child);
			loadTreeModel(model, child);
		}
	}*/

	/**
	 * This uses a TreeModel to load the Site model.
	 * Return the Site as a generic object for use by the processor
	 */
	public Object loadModel() throws XGenException {
		//logger.debug("load()");
		File rootDir = ResourceLoader.loadFile(srcBasePath);
		if (rootDir.exists() == false) {
			throw new XGenException(
				"Directory does not exist: " + srcBasePath,
				null);
		}

		if (filter == null) {
			throw new XGenException("FileFilter is not initialized", null);
		}

		//this.basepath = url.getPath();
		//this.basepath = url.getFile();
		// set the basepath for href substitution

		FilesystemTreeModel treeModel =
			new FilesystemTreeModel(rootDir, filter);

		//ArrayList list = new ArrayList();
		//loadFileSystemTree(list, treeModel, rootDir);

		site.setSourceBasePath(srcBasePath);
		site.setWebRoot(webroot);

		MenuItem rootMenu = new MenuItem(rootDir.getName(), "index.html");
		///////////////////////////////////////
		//rootMenu.setPath("");
		site.setRootMenu(rootMenu);
		site.addPage(new Page(rootMenu));

		int level = 0;
		try {
			loadChildren(rootMenu, treeModel, rootDir, level);
		} catch (Throwable t) {
			throw new XGenException("Error loading menus", t);
		}
		return site;
	}

	/** 
	 * 
	 */
	public void loadChildren(
		MenuItem parentMenu,
		TreeModel treeModel,
		Object parentFile,
		int level) {

		level++;
		int count = treeModel.getChildCount(parentFile);
		for (int index = 0; index < count; index++) {
			File childFile = (File) treeModel.getChild(parentFile, index);
			//logger.debug("node:" + child);
			MenuItem childMenu = getMenuItem(childFile, level);
			parentMenu.add(childMenu);
			loadChildren(childMenu, treeModel, childFile, level);
		}
	}

	private MenuItem getMenuItem(File file, int level) {
		String label = file.getName();
		label = removeExtension(label);
		//System.out.println("getMenuItem() " + label);
		String path = buildRelativePath(file);
		MenuItem item = new MenuItem(label, path);
		item.setLevel(level);
		//site.addMenuItem(item);
		site.addPage(new Page(item));
		return item;
	}

	private String removeExtension(String filename) {
		int index = filename.lastIndexOf(".");
		if (index <= 0) {
			return filename;
		}
		return filename.substring(0, index);
	}

	/**
	 * chop off sourcebasepath from file path
	 * 
	 * @param file
	 * @return
	 */
	private String buildRelativePath(File file) {
		logger.fine("==============");

		String filePath = file.getAbsolutePath();
		log("FILEPATH=>" + filePath);

		int length = srcBasePath.length();
		String path = filePath.substring(length + 1);
		log("PATH=>" + path);

		return path;
	}

	private void log(String msg) {
		System.out.println(msg);
	}

	/**
	 * Build a qualified href using the site's webroot.
	 * Return an empty string if hrefPath is null.
	 * 
	 * @param path
	 * @return
	 *
	private String buildHref(File file) {
		logger.fine("==============");
		
		 // String.replaceAll(aString) uses regex on the aString-parameter, and regex uses \ (backslash) as a escape-code.
		 // So to tell regex you want a \ (backslash), you need to feed it \\. To do this in java you have to write "\\\\".
		 
		String filePath = file.getAbsolutePath();
		logger.fine("FILEPATH=>" + filePath);
	
		String hrefPath = filePath.replaceAll("\\\\", "/");
		logger.fine("HREFPATH=>" + hrefPath);
	
		
		 // escape any semicolon filesystem chars like d:
		 
		String urlPath = hrefPath.replaceAll(":", "\\:");
	
		
		 //strip off any leading slashes /D:/tmp
		 //fix me 
		 
		if (basepath.startsWith("/")) {
			basepath = basepath.substring(1);
		}
	
		logger.fine("REPLACE:" + basepath + "=>" + webroot);
	
		
		 // replace the basepath with the webroot
		 
		String href = urlPath.replaceFirst(basepath, webroot);
		logger.fine("HREF=>" + href);
	
		if (href.startsWith("/") == false) {
			//href = "/" + href;
		}
	
		return href;
	}*/

}
