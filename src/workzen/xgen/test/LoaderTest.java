/*
 * LoadTest.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft
 */

package workzen.xgen.test;

import java.net.URL;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import workzen.xgen.loader.JavaModelXmlLoader;
import workzen.xgen.model.java.Package;

/**
 * This is a test of the xml loader. It loads the document and returns
 * the package which gets printed to standard out.
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class LoaderTest {
	Package pkg = null;
	

	/** */
	public static void main(String args[]) throws Exception {

		String usage = "usage: LoadTest [xmlfile]";
		String xmlFilepath =
			"D:/Project/workzen.xgen/etc/input/types.xml";
			
		Properties jp = new Properties();
		jp.setProperty(JavaModelXmlLoader.XML_FILEPATH, xmlFilepath);

		if (args.length > 0) {
			if (args[0].equals("help")) {
				System.out.println(usage);
				System.exit(1);
			} else {
				xmlFilepath = args[0];
			}
		}
		BasicConfigurator.configure();

		LoaderTest test = new LoaderTest(jp);
		test.run();

	}

	/** constructor */
	public LoaderTest(Properties jp) {
		try {
			JavaModelXmlLoader loader = new JavaModelXmlLoader();
			loader.configure(jp);
			pkg = (Package) loader.loadModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** run the test */
	public void run() {
		try {
			System.out.println(pkg.toString("\n"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
