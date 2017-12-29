/*
 * XGenLoader.java
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
package workzen.xgen.loader;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import workzen.xgen.XGenException;
import workzen.xgen.model.website.MenuItem;
import workzen.xgen.model.website.Page;
import workzen.xgen.model.website.Site;
import workzen.xgen.util.PropertyUtil;

/**
 * This loader reads an xml sitemap schema and loads the 
 * model.website.Site object 
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class WebsiteXmlLoader extends XmlLoader implements ILoader {

	public static final String WEB_ROOT = "webRoot";
	public static final String SRC_BASE_PATH = "srcBasePath";
	public static String OUTPUT_PATH = "outputPath"; // match of VelocityProcessor property

	private Site site = new Site();
	private String webroot = "undefined_webroot";
	private String srcBasePath = "undefined_srcBasePath";

	private Logger logger = Logger.getLogger(WebsiteXmlLoader.class);

	public void configure(Map map) throws XGenException {
		super.configure(map);
		this.webroot = PropertyUtil.getRequired(map, WEB_ROOT);
		this.srcBasePath = PropertyUtil.getRequired(map, SRC_BASE_PATH);
		site.setDestBasePath(PropertyUtil.getRequired(map, OUTPUT_PATH));
	}

	/**
	 * @param url
	 * @param validate
	 * @return
	 * @throws IOException
	 *
	private Document loadDocument(URL url, boolean validate)
		throws IOException, XGenException {
		SAXBuilder builder = new SAXBuilder(validate);
		Document doc = null;
		try {
			doc = builder.build(url);
		} catch (JDOMException e) {
			throw new XGenException("error loading document", e);
		}
		return doc;
	}*/

	/**
	 * Use an xml Document to load the Site model.
	 * Return the Site as a generic object for use by the processor
	 *
	 */
	public Object loadModel() throws XGenException {
		logger.debug("loadModel()");

		Document doc = super.loadDocument();
		Element rootEl = doc.getRootElement();
		//the site element may be embedded within an html page
		Element siteEl = rootEl.getChild("website");
		// if not defined, the root is the sitemapEl
		if (siteEl == null) {
			siteEl = rootEl;
		}

		/*
		 * The source basePath is defined in the ant task, so this is optional.
		 * This is a bit complex...
		 */
		//site.setSourceBasePath(siteEl.getAttributeValue("sourceBasePath"));
		//this.webroot = siteEl.getAttributeValue("webroot");
		site.setSourceBasePath(srcBasePath);
		site.setWebRoot(webroot);
		String siteHref = siteEl.getAttributeValue("href");
		MenuItem root = new MenuItem("site",siteHref);
		//root.setHref(site.getRootHref());
		//MenuTreeModel model = new MenuTreeModel(root);
		site.setRootMenu(root);
		//site.addMenuItem(root);
		site.addPage(new Page(root));
		int level = 0;
		try {
			loadMenus(root, siteEl, level);
		} catch (Throwable t) {
			throw new XGenException("Error loading menus", t);
		}
		return site;

	}

	private void loadMenus(MenuItem parent, Element node, int level)
		throws IOException {
		List menus = node.getChildren("menu");
		if (menus == null) {
			return;
		}
		level++;
		Iterator it = menus.iterator();
		while (it.hasNext()) {
			Element menuEl = (Element) it.next();
			MenuItem item = getMenuItem(menuEl, level);
			parent.add(item);
			loadChildren(item, menuEl, level);
			loadMenus(item, menuEl, level);
		}
	}

	private void loadChildren(MenuItem parent, Element node, int level)
		throws IOException {
		List menuItems = node.getChildren("a");
		if (menuItems == null) {
			return;
		}
		level++;
		Iterator it = menuItems.iterator();
		while (it.hasNext()) {
			Element menuEl = (Element) it.next();
			MenuItem item = getMenuItem(menuEl, level);
			parent.add(item);
		}
	}

	private MenuItem getMenuItem(Element menuEl, int level)
		throws IOException {
		String label = menuEl.getText();
		label = label.trim();
		//System.out.println("getMenuItem() " + label);
		String path = menuEl.getAttributeValue("href");
		
		//String href = buildHref(hrefPath);
		System.out.println("LABEL: [" + label + "] PATH: " + path);
		MenuItem item = new MenuItem(label, path);
		item.setLevel(level);
		//site.addMenuItem(item);
		site.addPage(new Page(item));
		return item;
	}

	/**
	 * Build a qualified href using the site's webroot.
	 * Return an empty string if hrefPath is null.
	 * 
	 * @param path
	 * @return
	 *
	private String buildHref(String hrefPath) {
		if (hrefPath == null) {
			return null;
		}

		if (hrefPath.startsWith("/") == false) {
			hrefPath = "/" + hrefPath;
		}
		return webroot + hrefPath;
	}*/

	/*
	private void debug(String str) {
		System.out.println("debug()" + str);
	}*/

}
