/*
 * Copyright (c) May 24, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.texen.util.FileUtil;
import org.apache.velocity.texen.util.PropertiesUtil;
import org.apache.velocity.util.StringUtils;

import workzen.xgen.XGenException;
import workzen.xgen.util.PropertyUtil;

/**
 * <h3>Velocity Processor</h3>
 * This class is based on the TexenTask that ships with Velocity.
 * Properties loaded from the Ant task are used to initialize Velocity. 
 * The "generator" class has been merged with this class.
 * <p>
 * Example properties:
 <pre>
&lt;xgen
 loader="workzen.xgen.loader.JavaModelLoader"
 processor="workzen.xgen.processor.velocity.VelocityProcessor"
 xmlFilepath="${basedir}/etc/input/blog.xml"
 resourceLoader="file"
 basePath="${basedir}/src/workzen/xgen"
 template="control_manager.vm"
 templatePath="templates/persistence/manager"
 outputPath="${basedir}/work/blog"
 outputFile="manager.out"
/&gt;</pre>
<table border="1" >
	<tr>
		<th>properties</th>
		<th>description</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<tr>
		<td>loader</td>
		<td>ILoader classname</td>
		<td>required</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>processor</td>
		<td>IProcessor classname</td>
		<td>required</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>xmlFilePath</td>
		<td>XmlLoader property</td>
		<td>required</td>
		<td>full path to xml input file</td>
	</tr>
	<tr>
		<td>resourceLoader</td>
		<td>Velocity resource loader property</td>
		<td>optional</td>
		<td>file or class, default=&quot;file,class&quot;</td>
	</tr>
	<tr>
		<td>templateBasePath</td>
		<td>Velocity fileResourceLoader property</td>
		<td>optional</td>
		<td>FileResourceLoader basepath to templates</td>
	</tr>
	<tr>
		<td>templatePath</td>
		<td>Velocity template path, relative from basePath</td>
		<td>required</td>
		<td>example: templates/pojo</td>
	</tr>
	<tr>
		<td>template</td>
		<td>Velocity template name</td>
		<td>required</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>outputPath</td>
		<td>Velocity output directory path</td>
		<td>required</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>outputFile</td>
		<td>Velocity output filename</td>
		<td>required</td>
		<td>&nbsp;</td>
	</tr>
</table>
<pre>

 * @author brad.matlack
 */
public class VelocityProcessor implements IProcessor {

	public static String TEMPLATE_BASEPATH = "templateBasePath";
	public static String TEMPLATE_PATH = "templatePath";
	public static String TEMPLATE = "template";
	public static String OUTPUT_PATH = "outputPath";
	public static String OUTPUT_FILE = "outputFile";
	public static String RESOURCE_LOADER = "resourceLoader";

	private Object model;
	private Properties vp = new Properties();
	private VelocityContext context = new VelocityContext();
	private Logger logger = Logger.getLogger(VelocityProcessor.class);

	/**
	 * Configure the processor using a property Map.
	 * This also sets several Velocity core properties like:
	 * 
	 * <li>Velocity.FILE_RESOURCE_LOADER_PATH=templateBasePath</li>
	 * <li>Velocity.RESOURCE_LOADER="file,class"</li>
	 * 
	 * @see workzen.xgen.processor.IProcessor#configure(java.util.Map)
	 */
	public void configure(Map map) throws XGenException {

		vp.putAll(map);

		String templateBasePath =
			PropertyUtil.getOptional(vp, "templateBasePath", "");
		vp.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templateBasePath);

		String resourceLoader =
			PropertyUtil.getOptional(vp, "resourceLoader", "file,class");
		vp.setProperty(Velocity.RESOURCE_LOADER, resourceLoader);

		vp.setProperty(
			"class.resource.loader.class",
			"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		/*
		vp.setProperty(
			"classpath." + Velocity.RESOURCE_LOADER + ".class",
			"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		*/

		vp.setProperty(
			"classpath." + Velocity.RESOURCE_LOADER + ".cache",
			"false");

		vp.setProperty(
			"classpath."
				+ Velocity.RESOURCE_LOADER
				+ ".modificationCheckInterval",
			"2");

		try {
			Velocity.init(vp);
		} catch (Throwable t) {
			throw new XGenException("Error initializing Velocity", t);
		}

	}

	public Map getProperties() {
		return vp;
	}

	/**
	 * Add external objects to the context
	 * Note: this only get added to the original context.
	 * BDM: This needs to be tested with a control template.
	 * @param key
	 * @param value
	 */
	public void setContextProperty(String key, Object value) {
		context.put(key, value);
	}

	/**
	 * Parse the template. This uses the configuration properties of 
	 * <li>template</li>
	 * <li>templatePath</li>
	 * <li>outputPath</li>
	 * <li>outputFile</li>
	 * 
	 * For control templates, the first outputFile is a velocity "report".
	 * 
	 * @see workzen.xgen.processor.IProcessor#process(java.lang.Object)
	 */
	public void process(Object model) throws XGenException {
		this.model = model;
		String templatePath = PropertyUtil.getRequired(vp, "templatePath");
		String templateFile = PropertyUtil.getRequired(vp, "template");
		String outputPath = PropertyUtil.getRequired(vp, "outputPath");
		String outputFile = PropertyUtil.getRequired(vp, "outputFile");

		createFilePath(outputPath);

		String template = templatePath + "/" + templateFile;
		String output = outputPath + "/" + outputFile;
		parse(template, output);
		//parse(templateFile, outputPath);
	}

	/**
	 * Initialize the VelocityContext.
	 * This method adds the following to the context:
	 * <li>model:Object</li>
	 * <li>generator:VelocityProcessor</li>
	 * <li>strings:StringUtils</li>
	 * <li>files:FileUtil</li>
	 * <li>properties:PropertiesUtil</li>
	 * <li>now:Date</li>
	 * Plus all the original attributes from the configuration map.
	 * <li></li>
	 * 
	 * @return
	 */
	private void initializeContext() {

		context.put("model", model);
		context.put("generator", this);

		StringUtils su = new org.apache.velocity.util.StringUtils();
		FileUtil fu = new org.apache.velocity.texen.util.FileUtil();
		PropertiesUtil pu = new org.apache.velocity.texen.util.PropertiesUtil();
		Date now = new Date();

		context.put("strings", su);
		context.put("files", fu);
		context.put("properties", pu);
		context.put("now", now);

		Iterator it = vp.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			context.put(key, vp.get(key));
		}
	}

	/**
	 * Returns a writer, based on encoding and qualified filepath.
	 *
	 * @param path      path to the output file
	 * @param encoding  output encoding
	 */
	private Writer getWriter(String filepath, String encoding, boolean append)
		throws XGenException {
		//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		Writer writer;
		if (encoding == null
			|| encoding.length() == 0
			|| encoding.equals("8859-1")
			|| encoding.equals("8859_1")) {
			try {
				writer = new FileWriter(filepath, append);
			} catch (Throwable t) {
				System.out.println("Error " + t.getMessage());
				throw new XGenException("Error getting writer", t);
			}
		} else {
			try {
				writer =
					new BufferedWriter(
						new OutputStreamWriter(
							new FileOutputStream(filepath, append),
							encoding));
			} catch (Throwable t) {
				System.out.println("Error " + t.getMessage());
				throw new XGenException("Error getting writer", t);
			}
		}
		return writer;
	}

	/**
	 * Create the directories to the path
	 * 
	 * @param path
	 * @throws XGenException
	 */
	public void createFilePath(String path) throws XGenException {
		if (path == null || path.length() < 2) {
			throw new XGenException(
				"Error creating filepath for " + path,
				null);
		}
		File file = new File(path);
		file.mkdirs();
	}

	/**
	 * Use velocity to evaluate an input string, and merge with the context
     *
	 * @param input
	 * @return
	 * @throws XGenException
	 */
	public String evaluate(String input) throws XGenException {

		StringWriter writer = new StringWriter();
		String output = "undefined";
		try {
			Velocity.evaluate(context, writer, "[whats this for?]", input);
			output = writer.toString();
			writer.close();
		} catch (Throwable t) {
			throw new XGenException("Error evaluating string",t);
		}
		//System.out.println("OUTPUT: " + output);
		return output;
	}

	/**
	 * Legacy Velocity signature
	 * 
	 * @param templateFile
	 * @param outputFile
	 * @param x
	 * @param y
	 * @throws XGenException
	 */
	public void parse(
		String templateFile,
		String outputFile,
		String x,
		String y)
		throws XGenException {
		parse(templateFile, outputFile);
	}

	public void parse(String template, String output) throws XGenException {
		parse(template, output, false);
	}

	public void parse(String templateFile, String outputFile, boolean append)
		throws XGenException {
		logger.info("parse() " + templateFile + "=>" + outputFile);

		Writer writer = null;
		try {

			Template template = null;

			try {
				template = Velocity.getTemplate(templateFile);
			} catch (ResourceNotFoundException rnfe) {
				String msg = "Error : cannot find template " + templateFile;
				System.out.println(msg);
				throw new XGenException(msg, null);
			} catch (ParseErrorException pee) {
				String msg =
					"Error : Syntax error in template "
						+ templateFile
						+ ":"
						+ pee;
				System.out.println(msg);
				throw new XGenException(msg, null);
			}

			/*
			 * Initialize the context
			 */
			//VelocityContext context = initializeContext();
			initializeContext();

			String encoding = PropertyUtil.getOptional(vp, "encoding", "UTF-8");

			writer = getWriter(outputFile, encoding, append);

			if (template != null)
				template.merge(context, writer);

		} catch (Throwable t) {
			throw new XGenException("Error parsing template", t);
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (Exception e) {
				//System.out.println(e.toString());
			}
		}
	}
}
