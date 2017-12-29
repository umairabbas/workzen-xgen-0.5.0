/*
 * VelocityTest.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft
 */

package workzen.xgen.test.velocity.legacy;

import workzen.xgen.loader.ILoader;
import workzen.xgen.loader.JavaModelXmlLoader;
import workzen.xgen.model.java.Package;

import java.util.*;

import java.net.URL;

import java.io.*;
import java.io.StringWriter;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.texen.Generator;

import org.apache.log4j.*;

/**
 * Just a bunch of tests to see how Velocity works.
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class VelocityTest {
	private Package pkg = null;
	//private String controlTemplate = "d:/project/xit/templates/pojo/control.vm";
	private String controlTemplate = "control.vm";
	private Logger logger = Logger.getLogger(VelocityTest.class);

	/** */
	public static void main(String args[]) throws Exception {

		String usage = "usage: LoadTest [xmlfile]";
		String xmlFilepath = "D:/project/xit/work/pojo/input/typebean.xml";

		if (args.length > 0) {
			if (args[0].equals("help")) {
				System.out.println(usage);
				System.exit(1);
			} else {
				xmlFilepath = args[0];
			}
		}
		BasicConfigurator.configure();
		VelocityTest test = new VelocityTest(xmlFilepath);
		test.run1();
		System.out.println("==================");
		//test.run3();

	}

	/** 
	 * load the schema and the package
	 */
	public VelocityTest(String filepath) {
		try {
			Properties jp = new Properties();
			jp.setProperty(JavaModelXmlLoader.XML_FILEPATH, filepath);
			ILoader loader = new JavaModelXmlLoader();
			loader.configure(jp);
			pkg = (Package) loader.loadModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * push and pull from the context
	 */
	public void run1() {
		try {
			//System.out.println(pkg.toString("\n"));   

			Velocity.init();

			/* lets make a Context and put data into it */
			VelocityContext context = new VelocityContext();

			context.put("name", "Velocity");
			context.put("project", "Jakarta");

			/* lets render a template */
			StringWriter w = new StringWriter();

			//deprecated
			//Velocity.mergeTemplate(controlTemplate, context, w );
			//System.out.println(" controlTemplate : " + w );

			/* lets make our own string to render */
			String s = "We are using $project $name to render this.";
			Velocity.evaluate(context, w, "xxxmystring", s);
			System.out.println(" string : " + w);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * try to merge the template
	 *
	public void run2(){
	try{
	
	Velocity.init();  
	VelocityContext context = new VelocityContext();
	
	Vector beans = pkg.getBeans();
	System.out.println(pkg.getName());
	context.put("packageName", pkg.getName());
	context.put("beans", beans); 
	context.put("su", StringUtils.class);
	
	StringWriter w = new StringWriter();
	
	// deprecated!!!
	Velocity.mergeTemplate(controlTemplate, context, w );
	System.out.println(" template : " + w );
	
	}catch(Exception e){
	e.printStackTrace();
	}
	}*/

	/**
	 *
	 */
	public void run3() {
		//String controlTemplate = "d:/project/xit/templates/pojo/control.vm";
		String controlTemplate = "control.vm";
		String outputFile = "/d:/project/xit/work/velocityTest.txt";
		String outputDirectory = "/d:/project/xit/work";

		Properties p = new Properties();
		p.setProperty(
			"file.resource.loader.path",
			"/d:/project/xit/templates/pojo");
		//p.setProperty("file.resource.loader.path", "/");

		Generator generator = Generator.getInstance();
		generator.setOutputPath(outputDirectory);

		VelocityContext context = new VelocityContext();

		Vector beans = pkg.getBeans();
		System.out.println(pkg.getName());
		//context.put("packageName", pkg.getName());
		//context.put("beans", beans); 
		context.put("model", pkg);
		context.put("generator", generator);

		VelocityEngine ve = new VelocityEngine();
		//Template template = null;
		StringWriter sw = new StringWriter();

		try {
			//ve.init();
			ve.init(p);
			//template = ve.getTemplate(controlTemplate);	
			//template.merge( context, sw );
			//ve.mergeTemplate(controlTemplate, context, sw);
			//StringBuffer buf = sw.getBuffer();
			//System.out.println("output: " + buf );

			BufferedWriter writer =
				new BufferedWriter(new OutputStreamWriter(System.out));
			//template.merge(context, writer);

			//Writer writer = new FileWriter(outputFile);
			ve.mergeTemplate(controlTemplate, context, writer);
			//ve.evaluate(context, writer, "logtag", sw);

			System.out.println("output: " + sw);
			writer.flush();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 *
	 */
	public void run4() {
		String templatePath = "/d:/project/xit/templates/pojo";
		String controlTemplate = "/d:/project/xit/templates/pojo/control.vm";
		//String controlTemplate = "control.vm";
		String outputDirectory = "/d:/project/xit/work";
		String outputFile = "test-velocity.output";
		boolean useClasspath = true;

		Properties p = new Properties();
		p.setProperty(
			"file.resource.loader.path",
			"/d:/project/xit/templates/pojo");

		VelocityEngine ve = new VelocityEngine();

		try {

			if (templatePath != null) {
				logger.debug("Using templatePath: " + templatePath);
				p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);
			}

			if (useClasspath) {
				logger.debug("Using classpath");
				p.setProperty(Velocity.RESOURCE_LOADER, "classpath");

				p.setProperty(
					"classpath." + Velocity.RESOURCE_LOADER + ".class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

				p.setProperty(
					"classpath." + Velocity.RESOURCE_LOADER + ".cache",
					"false");

				p.setProperty(
					"classpath."
						+ Velocity.RESOURCE_LOADER
						+ ".modificationCheckInterval",
					"2");
			}

			ve.init(p);

			// Create the text generator.
			Generator generator = Generator.getInstance();
			generator.setOutputPath(outputDirectory);
			//generator.setInputEncoding(inputEncoding);
			//generator.setOutputEncoding(outputEncoding);

			if (templatePath != null) {
				generator.setTemplatePath(templatePath);
			}

			// Make sure the output directory exists, if it doesn't then create it.
			File file = new File(outputDirectory);
			if (!file.exists()) {
				file.mkdirs();
			}

			String path = outputDirectory + File.separator + outputFile;
			logger.info("Generating to file " + path);

			Writer writer = generator.getWriter(path, "8859-1");

			VelocityContext context = new VelocityContext();
			context.put("model", pkg);

			writer.write(generator.parse(controlTemplate, context));
			writer.flush();
			writer.close();
			generator.shutdown();
		}
		//catch( BuildException e){ logger.error("BuildException: " + e.getMessage());}
		catch (MethodInvocationException e) {
			logger.error(
				"Exception thrown by '"
					+ e.getReferenceName()
					+ "."
					+ e.getMethodName()
					+ "'"
					+ e.getWrappedThrowable());
		} catch (ParseErrorException e) {
			logger.error("Velocity syntax error " + e.getMessage());
		} catch (ResourceNotFoundException e) {
			logger.error("Resource not found " + e.getMessage());
		} catch (Exception e) {
			logger.error("Generation failed " + e.getMessage());
		}
	}

}
