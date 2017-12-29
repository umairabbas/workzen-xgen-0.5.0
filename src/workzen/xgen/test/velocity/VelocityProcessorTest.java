/*
 * VelocityEngineTest.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft/gpl.html
 */

package workzen.xgen.test.velocity;

import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import workzen.xgen.loader.ILoader;
import workzen.xgen.loader.JavaModelXmlLoader;
import workzen.xgen.model.java.Package;
import workzen.xgen.engine.IProcessor;
import workzen.xgen.engine.VelocityProcessor;

/**
 * Just a test to see how VelocityProcessor works.
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class VelocityProcessorTest {
	
	public static void main(String[] args) throws Exception {
		String basePath = "D:/Project/workzen.xgen/src/workzen/xgen";
		String xmlFilepath = "D:/Project/workzen.xgen/etc/input/types.xml";
		String templatePath = "templates/pojo";
		
		//String template = "bean.vm";
		//String template = "pojo.vm";
		String template = "control.vm";
		
		String outputPath = "D:/Project/workzen.xgen/work/vptest";
		String outputFile = "velocity.out";
		String resourceLoader = "file";
		//String resourceLoader = "class";
		
		Properties jp = new Properties();
		
		jp.setProperty(JavaModelXmlLoader.XML_FILEPATH, xmlFilepath);
		jp.setProperty("resourceLoader",resourceLoader);
		jp.setProperty("templateBasePath", basePath);
		jp.setProperty("templatePath",templatePath);
		jp.setProperty("template",template);
		jp.setProperty("outputFile",outputFile);
		jp.setProperty("outputPath", outputPath);

		BasicConfigurator.configure();

		Package pkg = null;
		try {
			ILoader loader = new JavaModelXmlLoader();
			loader.configure(jp);
			pkg = (Package) loader.loadModel();
			IProcessor vp = new VelocityProcessor();
			vp.configure(jp);
			vp.process(pkg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
