/*
 * XGenTask.java
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
package workzen.xgen.ant.legacy;

import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import workzen.xgen.XGenException;
import workzen.xgen.ant.legacy.ILoader;


/**
 * This class exends VETexenTask and initializes the Velocity context.
 * The meta-data loader initializes the model, which gets placed 
 * into the context, for translation into code.
 * 
 * <pre>
 * loader = workzen.xgen.util.XGenLoader
 * inputFile = d:/project/test/input/blog.xml
 * inputDirectory = not_used_in_this_case
 * controlTemplate = templates/persistenceMgr/control.vm
 * resourceTemplate = not_used_in_this_case
 * templatePath = d:/project/test
 * outputDir = d:/project/test/output/blog
 * outputFile = "some_output_file"
 * </pre>
 * 
 * The <b>loader</b> is used to define the meta-data loader. 
 * You can write loaders for any type of structured data, 
 * and then transform that data using Velocity templates. 
 * <p>
 * Velocity is the only template engine supported at this time.
 * <p>
 * The <b>inputFile</b> defines the file used by the meta-data loader.
 * <p>
 * The <b>controlTemplate</b> defines the start template for the engine
 * using a "resource path"
 * <p>
 * The <b>resourceTemplate</b> defines an optional template for use by the
 * control template. Is is also specified using a "resource path" 
 * This allows the control template to process multiple files against a  
 * secondary template.
 * <p>
 * The <b>templatePath</b> is used by the resource loader to locate your templates.
 * The VETexenTask defines the recourceloader as "file,classpath". 
 * If the file resource is not found, the classpath is automatically  searched. 
 * This should really be called <i>templateBasePath</i> or <i>templatePathPrefix</i>. 
 * For example setting templatePath=./mytemplates, 
 * Velocity will try and load the ./mytemplates/${controlTemplate}.
 * <p>
 * The <b>outputDir</b> defines the base directory of your output. 
 * <p>
 * The <b>outputFile</b> is an optional field for templates that do not define
 * the outputFile. This is a Velocity default used by standalone templates.
 * <p>
 * Templates will use the above properties to setup the output generation.
 * Refer to template documentation to see exact property requirements.
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class XGenTask2 extends VETexenTask {

	public static final String DEFAULT_OUTPUT_FILE = "default.output";

	private String inputFile = "fooFile";
	private String inputDirectory = "fooDir";
	private String resourceTemplate = "foo.vm";
	
	private ILoader loader = null;
	
	private Logger logger = Logger.getLogger(XGenTask2.class);

	public XGenTask2() {
		super();
		// initialize log4j
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		logger.debug("XGenTask()");
		super.outputFile = DEFAULT_OUTPUT_FILE;
	}

	/**
	 * Set the input file for the xml loader.
	 * <i>Required</i>
	 * Example: inputFile = d:/project/input/jesso.xml
	 * 
	 * @param xmlfile  to load.		
	 */
	public void setInputFile(String val) {
		logger.debug("setInputFile() " + val);
		if (isNull(val))
			return;
		this.inputFile = val;
	}
	
	/**
	 * Set the input directory. 
	 * <i>Optional</i>
	 * This is only required for models requiring directory information.
	 * Example: inputDirectory="/project/workzen.xgen/etc/website"
	 * @param val
	 */
	public void setInputDirectory(String val){
		logger.debug("setInputDirectory() " + val);
		if (isNull(val))
			return;
		this.inputDirectory = val;
	}

	/**
	 * Initialize the loader.
	 * <i>Required</i>
	 * Example: loader=com.loader.MyLoader
	 * @param val
	 */
	public void setLoader(String val) throws XGenException {
		logger.debug("setLoader() " + val);
		if (isNull(val))
			return;
		this.loader = getLoaderInstance(val);
	}
	
	/**
	  * This parameter is passed to the superclass.
	  * This is where Velocity will look for templates
	  * using the file template loader.
	  */
	public void setTemplatePath(String val) throws Exception{
		logger.debug("setTemplatePath() " + val);
		super.setTemplatePath(val);
	}
	
	/**
	 * This sets the the resource template, an is no longer
	 * associated with the control template.
	 * <i>Optional</i>
	 * Example usage: "templates/website/navtop.vm"
	 */
	public void setResourceTemplate(String val){
		logger.debug("setResourceTemplate() " + val);
		if (isNull(val))
			return;
		this.resourceTemplate = val;
		//super.controlTemplate = resourceTemplate;
	}
	
	/**
	 * This parameter is passed to the superclass.
	 * This no longer associated with the resourceTemplate
	 * Example usage: "templates/pojo/control.vm".
	 * @see org.apache.velocity.texen.ant.TexenTask#setControlTemplate(java.lang.String)
	 */
	public void setControlTemplate(String val){
		logger.debug("setControlTemplate() " + val);
		//setResourceTemplate(val);
		super.setControlTemplate(val);
	}

	/**
	 * This parameter is passed to the superclass.
	 * <i>Optional</i> Required for standalone templates.
	 * @see org.apache.velocity.texen.ant.TexenTask#setOutputFile(java.lang.String)
	 */
	public void setOutputFile(String outputFile) {
		logger.debug("setOutputFile() " + outputFile);
		if (isNull(outputFile))
			return;
		super.setOutputFile(outputFile);
	}
	
	/**
	 * Override and initialize the Velocity context.
	 * @see org.apache.velocity.texen.ant.TexenTask#initControlContext()
	 */
	public Context initControlContext() throws Exception {
		super.initControlContext();
		logger.debug("initControlContext()");
		
		// put the model into the context
		VelocityContext context = new VelocityContext();
		Object model = loadModel(inputFile);
		context.put("model", model);
		context.put("inputDirectory",inputDirectory);
		context.put("resourcePath", getResourcePath(controlTemplate));
		context.put("resourceTemplate",resourceTemplate);
		context.put("templatePath",templatePath);
		return context;
	}

	/** 
	 * Load meta-data using the loader instance.
	 */
	private Object loadModel(String xmlFile) {
		logger.debug("loadModel() " + xmlFile);
		Object obj = null;
		try {
			URL url = new java.net.URL("file:///" + xmlFile);
			obj = loader.load(url, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	
	/**
	 * The resource path is used in control templates to 
	 * setup child template names.
	 * If the controlTemplate = templates/pojo/control.vm, 
	 * the resourcePath = templates/pojo/
	 * 
	 * @param res
	 * @return
	 */
	private String getResourcePath(String res){
		int index = res.lastIndexOf("/");
		String path = res;	
		if( index > 0 ){
			path = res.substring(0, index + 1);
		}
		logger.debug("getResourcePath() " + path);
		return path;
	}
	
	public String getTemplatePath(){
		logger.debug("getTemplatePath() " + templatePath);
		return super.getTemplatePath();	
	}

	/** */
	private boolean isNull(String str) {
		if (str == null || str.equals(""))
			return true;
		return false;
	}
	
	/** */
	private boolean notNull(String str){
		return ! isNull(str);	
	}

	/** 
	 * Instantiate the ILoader class.
	 * 
	 * @param classname
	 */
	private ILoader getLoaderInstance(String loaderClassname)
		throws XGenException {
		logger.debug("getLoaderInstance() " + loaderClassname);

		Class c = null;
		try {
			c = Class.forName(loaderClassname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new XGenException("Error instantiating loader",e);
		}

		Object obj = null;
		try {
			obj = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
			throw new XGenException("Error instantiating loader",e1);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			throw new XGenException("Error instantiating loader",e1);
		}
		return (ILoader) obj;
	}

	/**
	 * @return
	 */
	private String getResourceTemplate() {
		logger.debug("getResourceTemplate() " + resourceTemplate);
		return resourceTemplate;
	}

}
