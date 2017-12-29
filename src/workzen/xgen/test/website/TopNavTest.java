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
 * Display the model as a "top-nav" to System.out
 * 
 * @author <a href="mailto://matlackb@workzen.us">Brad Matlack</a>
 */
public class TopNavTest {

	private String xmlFile = "project/workzen.xgen/etc/input/website.xml";
	//private String sourcePath = "/project/workzen.xgen/etc";
	private String webRoot = "testRoot";
	private Site site;

	/** */
	public TopNavTest() {
	}

	/** 
	 * Main
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		TopNavTest test = new TopNavTest();
		test.run();
	}

	public void run() {
		site = loadModel();
		//site = buildModel();
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
			StringBuffer buf = new StringBuffer();
			Page page = (Page) it.next();
			String label = page.getMenuItem().getLabel(); 
			buf.append(label + "=>");
			//System.out.println("displayMenu: " + label);
			displayMenu(buf, page);
			System.out.println(buf.toString());
			
		}
	}

	private void displayMenu(StringBuffer buf, Page page) {
		Collection menus = page.getParentMenus();
		Iterator it = menus.iterator();
		while (it.hasNext()) {
			MenuItem menu = (MenuItem) it.next();
			addMenuItems(buf, menu, page);
		}
		addMenuItems(buf, page.getMenuItem(), page);
	}

	private void addMenuItems(StringBuffer buf, MenuItem menu, Page page) {
		//System.out.println("item: " + menu);
		if( menu == null ){
			return;	
		}
		Collection items = menu.getMenuItems();
		Iterator it = items.iterator();
		while (it.hasNext()) {
			MenuItem item = (MenuItem) it.next();
			if( site.isSelected(page,item)){
				buf.append("[");
				buf.append(item.getLabel());
				buf.append("]");
			}else{
				buf.append(item.getLabel());
				buf.append("-");
			}
		}
		buf.append("\n   ");
	}
}
