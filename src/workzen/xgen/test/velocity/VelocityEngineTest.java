/*
 * VelocityEngineTest.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft/gpl.html
 */

package workzen.xgen.test.velocity;

import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import workzen.xgen.loader.ILoader;
import workzen.xgen.loader.JavaModelXmlLoader;
import workzen.xgen.model.java.Package;

/**
 * Just a test to see how VelocityEngine works. This works.
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class VelocityEngineTest {
	
	public static void main(String[] args) throws Exception {
		String xmlFilepath = "D:/Project/workzen.xgen/etc/input/types.xml";
		String templatePath =
			"/Project/workzen.xgen/src/workzen/xgen/templates/pojo";
		String outputDir = "D:/Project/workzen.xgen/work/test";
		//String templateName = "control.vm";
		String templateName = "pojo.vm";

		Properties jp = new Properties();
		jp.setProperty("file.resource.loader.path", templatePath);
		jp.setProperty(JavaModelXmlLoader.XML_FILEPATH, xmlFilepath);
		jp.setProperty("outputDirectory", outputDir);
		
		
		BasicConfigurator.configure();
		
		
		Package pkg = null;
		try {
			ILoader loader = new JavaModelXmlLoader();
			loader.configure(jp);
			pkg = (Package) loader.loadModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(pkg.getName());

		VelocityEngine ve = new VelocityEngine();
		ve.init(jp);
		Template t = ve.getTemplate(templateName);
		VelocityContext context = new VelocityContext();

		context.put("model", pkg);
		context.put("outputDirectory", outputDir);
		context.put("template",templateName); 
		context.put("now", new Date().toString());

		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		System.out.println(writer.toString());
	}
}
