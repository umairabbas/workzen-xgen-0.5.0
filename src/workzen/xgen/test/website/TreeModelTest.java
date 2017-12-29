/*
 * WebsiteTreeModelTest.java
 * Brad D Matlack 3-2003
 * License: http://www.gnu.org/gpl
 */
package workzen.xgen.test.website;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import workzen.xgen.loader.WebsiteFilesystemLoader;
import workzen.xgen.model.website.MenuItem;
import workzen.xgen.model.website.Page;
import workzen.xgen.model.website.Site;

/**
 * This tests the WebsiteFileSystemLoader
 * 
 * @author <a href="mailto://matlackb@workzen.us">Brad Matlack</a>
 */
public class TreeModelTest {

	private Site site;
	private String srcBasePath = "D:/project/workzen.xgen/etc/website";
	//private String srcBasePath = "D:/tmp";
	/* no spaces allowed */
	private String includeList = "*.txt,*.xml,*.html,*.htm";
	private String excludeList = "css";
	private String webRoot = "/mysite";
	private String fileFilter = "workzen.common.filesystem.filter.IncludeFileExcludeDir";
	
	private Logger logger = Logger.getLogger(TreeModelTest.class);

	/** */
	public TreeModelTest() {
	}

	/** 
	 * Main
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		TreeModelTest test = new TreeModelTest();

		test.run();
	}

	public void run() {

		try {
			//site = buildModel();
			site = loadModel();

			displayJTree(site);
			displayMenus(site);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Properties getProperties() {
		Properties jp = new Properties();
		jp.setProperty(WebsiteFilesystemLoader.WEB_ROOT, webRoot);
		jp.setProperty(WebsiteFilesystemLoader.SRC_BASEPATH, srcBasePath);
		jp.setProperty(WebsiteFilesystemLoader.CSV_INCLUDE_LIST, includeList);
		jp.setProperty(WebsiteFilesystemLoader.CSV_EXCLUDE_LIST, excludeList);
		jp.setProperty(WebsiteFilesystemLoader.FILE_FILTER_CLASS, fileFilter);
		return jp;
	}

	/** 
	 * Load meta-data using the loader instance.
	 */
	private Site loadModel() {
		WebsiteFilesystemLoader loader = new WebsiteFilesystemLoader();

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

	private Site buildModel() {
		Site site = new Site();
		//site.setSourceBasePath(sourcePath);
		site.setWebRoot("/mywebapp");

		MenuItem item1 = new MenuItem("one", "one/index.html");
		MenuItem item11 = new MenuItem("one-one", "one/one.html");
		MenuItem item2 = new MenuItem("two", "two/index.html");
		MenuItem item21 = new MenuItem("two-one", "two/two.html");

		item1.add(item11);
		item2.add(item21);

		site.addPage(new Page(item1));
		site.addPage(new Page(item11));
		site.addPage(new Page(item2));
		site.addPage(new Page(item21));

		return site;
	}

	/** */
	public void displayJTree(Site site) {
		TreeModel model = site.getTreeModel();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		try {

			final JTree tree = new JTree(model) {
				public String convertValueToText(
					Object value,
					boolean selected,
					boolean expanded,
					boolean leaf,
					int row,
					boolean hasFocus) {

					MenuItem item = (MenuItem) value;
					return item.getLabel();
				}
			};

			tree.setRootVisible(true);
			tree.setShowsRootHandles(true);
			tree.putClientProperty("JTree.lineStyle", "Angled");

			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					//DefaultMutableTreeNode node = (DefaultMutableTreeNode)
					MenuItem node =
						(MenuItem) tree.getLastSelectedPathComponent();

					if (node == null)
						return;
					logger.info("node selected:" + node.getPath());
				}
			});

			// add the tree to the scrollpane
			JScrollPane scrollPane = new JScrollPane(tree);
			scrollPane.setPreferredSize(new Dimension(300, 300));

			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout());
			centerPanel.add(scrollPane, BorderLayout.CENTER);

			JFrame frame = getFrame(centerPanel, "website");
			frame.show();
		} catch (Exception e) {
			e.printStackTrace();
			//frame.setStatus(e.getMessage());
		}
	}

	/** */
	private JFrame getFrame(JPanel panel, String title) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame();
		frame.setTitle(title);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setLocation(
			dim.width / 2 - frame.getSize().width / 2,
			dim.height / 2 - frame.getSize().height / 2);
		//this.show();
		return frame;
	}

	/**
	 * Uncomment as needed.
	 *  
	 * @param model
	 * @param root
	 */
	public void debug(TreeModel model) {
		displayTree(model, model.getRoot());

		//Vector treeList = new Vector();
		//loadFileSystemTree(treeList, model, root);
		//print(treeList);
	}

	/** 
	 * Display the model manually.
	 */
	public void displayTree(TreeModel model, Object parent) {

		int count = model.getChildCount(parent);
		for (int i = 0; i < count; i++) {
			MenuItem child = (MenuItem) model.getChild(parent, i);
			//logger.debug(child);
			System.out.println(child);
			displayTree(model, child);
		}
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
