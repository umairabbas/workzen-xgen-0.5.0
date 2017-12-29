/*
 * WebsiteModelTest.java
 * Brad D Matlack 3-2003
 * License: http://www.gnu.org/gpl
 */
package workzen.xgen.test.website;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import workzen.xgen.loader.WebsiteXmlLoader;
import workzen.xgen.model.website.MenuItem;
import workzen.xgen.model.website.Page;
import workzen.xgen.model.website.Site;

/**
 * Display the model as a "collapsed tree" to System.out
 * 
 * @author <a href="mailto://matlackb@workzen.us">Brad Matlack</a>
 */
public class CollapsedTest {

	private String xmlFile = "project/workzen.xgen/etc/input/website.xml";
	//private String sourcePath = "/project/workzen.xgen/etc";
	private String webRoot = "testWebRoot";
	private Site site;

	/** */
	public CollapsedTest() {
	}

	/** 
	 * Main
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		CollapsedTest test = new CollapsedTest();
		test.run();
	}

	public void run() {
		//site = loadModel();
		site = buildModel();
		//site.setSourceBasePath(sourcePath);

		displayMenus(site);
	}

	private Properties getProperties() {
		Properties jp = new Properties();
		jp.setProperty(WebsiteXmlLoader.XML_FILEPATH, xmlFile);
		jp.setProperty(WebsiteXmlLoader.WEB_ROOT, webRoot);
		return jp;
	}

	/** 
	 * Load meta-data using the loader instance.
	 */
	private Site loadModel() {
		WebsiteXmlLoader loader = new WebsiteXmlLoader();

		Object obj = null;
		try {
			loader.configure(getProperties());
			obj = loader.loadModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(obj.getClass());
		
		Site site = (Site) obj;
		return site;
	}

	private Site buildModel(){
		Site site = new Site();
		//site.setSourceBasePath(sourcePath);
		//site.setWebroot("/mywebapp");
		
		MenuItem item1 = new MenuItem("one","one/index.html");
		MenuItem item11 = new MenuItem("one-one","one/one.html");
		MenuItem item2 = new MenuItem("two","two/index.html");
		MenuItem item21 = new MenuItem("two-one","two/two.html");

		item1.add(item11);
		item2.add(item21);

		site.addPage(new Page(item1));
		site.addPage(new Page(item11));
		site.addPage(new Page(item2));
		site.addPage(new Page(item21));

		return site;
	}

	private void displayMenus(Site site) {

		Collection pages = site.getPages();
		System.out.println("pages: " + pages.size());
		Iterator it = pages.iterator();
		while (it.hasNext()) {
			Page page = (Page) it.next();
			//System.out.println(page.getMenuItem());
			System.out.println("==>" + page.getMenuItem().getLabel());
			displayMenu(page);
		}
	}

	private void displayMenu(Page page) {
		Collection parents = page.getParentMenus();
		Iterator it = parents.iterator();
		while (it.hasNext()) {
			MenuItem parent = (MenuItem) it.next();
			displayMenuItem(parent, page);
		}
		Collection children;
		if (page.isLeaf()) {
			children = page.getParentMenu().getChildren();
		} else {
			displayMenuItem(page.getMenuItem(), page);
			children = page.getMenuItem().getChildren();
		}
		it = children.iterator();
		while (it.hasNext()) {
			MenuItem menu = (MenuItem) it.next();
			displayMenuItem(menu, page);
		}

	}

	private void displayMenuItem(MenuItem item, Page page) {
		if (site.isSelected(page, item)) {
			display(item, true);
		} else {
			display(item, false);
		}
	}

	private void display(MenuItem item, boolean selected) {
		String indent = getIndent(item.getLevel());
		if (selected) {
			System.out.println(indent + "[" + item.getLabel() + "]");
		} else {
			System.out.println(indent + item.getLabel());
		}
		//System.out.println(item);
		//System.out.println(item.getPage().getBody());
	}

	private String getIndent(int level) {
		String indent = "";
		switch (level) {
			case (1) :
				return "-";
			case (2) :
				return "--";
			case (3) :
				return "---";
			case (4) :
				return "----";
			default :
				return "";
		}
	}
}
