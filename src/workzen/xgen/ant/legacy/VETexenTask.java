/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Velocity", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package workzen.xgen.ant.legacy;

import java.io.File;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.texen.ant.TexenTask;
import org.apache.velocity.util.StringUtils;

import workzen.xgen.ant.legacy.*;

/**
 * An ant task for generating output by using VelocityEngine 
 * instead of the Velocity helper class.
 * 
 * This task defines the default resourse loader to be "classpath".
 * If a templatePath is defined, then the resourceLoader = "file, classloader". 
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 * @version $Id: TexenTask.java,v 1.37 2001/12/06 07:46:47 jvanzyl Exp $
 */
public class VETexenTask extends TexenTask {

	/**
	 * This message fragment (telling users to consult the log or
	 * invoke ant with the -debug flag) is appended to rethrown
	 * exception messages.
	 */
	public static final String ERR_MSG_FRAGMENT = 
		". For more information consult the velocity log, or invoke ant " +
		"with the -debug flag.";

	//properties for the VelocityEngine
	private Properties velocityProperties = new Properties();

	public void setVelocityProperties(Properties p) {
		this.velocityProperties = p;
	}

	/**
	 * Execute the input script with Velocity
	 *
	 * @throws BuildException  
	 * BuildExceptions are thrown when required attributes are missing.
	 * Exceptions thrown by Velocity are rethrown as BuildExceptions.
	 */
	public void execute() throws BuildException {

		// Make sure the template path is set.
		/*
		if (templatePath == null && useClasspath == false) {
			throw new BuildException(
				"The template path needs to be defined if you are not using "
					+ "the classpath for locating templates!");
		}*/

		// Make sure the control template is set.
		if (controlTemplate == null) {
			throw new BuildException("The control template needs to be defined!");
		}

		// Make sure the output directory is set.
		if (outputDirectory == null) {
			throw new BuildException("The output directory needs to be defined!");
		}

		// Make sure there is an output file.
		if (outputFile == null) {
			throw new BuildException("The output file needs to be defined!");
		}

		try {
			
			// default resource loader
			String resourceLoader = "classpath";
			
			// setup the file resource loader
			if (templatePath != null) {
				log("Using templatePath: " + templatePath, project.MSG_VERBOSE);
								
				resourceLoader = "file, classpath";
				//resourceLoader = "file";
				
				velocityProperties.setProperty(
					Velocity.FILE_RESOURCE_LOADER_PATH,
					templatePath);	
			}else{
				log("templatePath is NULL");
			}

			// setup the classpath resource loader
			if (true) {
				log("resourceLoader: " + resourceLoader);
				
				velocityProperties.setProperty(
					Velocity.RESOURCE_LOADER,
					resourceLoader);

				velocityProperties.setProperty(
					"classpath." + Velocity.RESOURCE_LOADER + ".class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

				velocityProperties.setProperty(
					"classpath." + Velocity.RESOURCE_LOADER + ".cache",
					"false");

				velocityProperties.setProperty(
					"classpath."
						+ Velocity.RESOURCE_LOADER
						+ ".modificationCheckInterval",
					"2");
					
			}

			VelocityEngine engine = new VelocityEngine();
			engine.init(velocityProperties);

			// Create the text generator.
			VEGenerator generator = VEGenerator.getInstance();
			generator.setVelocityEngine(engine);  //BDM
			generator.setOutputPath(outputDirectory);
			generator.setInputEncoding(inputEncoding);
			generator.setOutputEncoding(outputEncoding);

			if (templatePath != null) {
				generator.setTemplatePath(templatePath);
			}

			// Make sure the output directory exists, if it doesn't
			// then create it.
			File file = new File(outputDirectory);
			if (!file.exists()) {
				file.mkdirs();
			}

			String path = outputDirectory + File.separator + outputFile;
			log("Generating to file " + path, project.MSG_INFO);
			Writer writer = generator.getWriter(path, outputEncoding);

			// The generator and the output path should
			// be placed in the init context here and
			// not in the generator class itself.
			Context c = initControlContext();

			// Everything in the generator class should be
			// pulled out and placed in here. What the generator
			// class does can probably be added to the Velocity
			// class and the generator class can probably
			// be removed all together.
			populateInitialContext(c);

			// Feed all the options into the initial
			// control context so they are available
			// in the control/worker templates.
			if (contextProperties != null) {
				Iterator i = contextProperties.getKeys();

				while (i.hasNext()) {
					String property = (String) i.next();
					String value = contextProperties.getString(property);

					// Now lets quickly check to see if what
					// we have is numeric and try to put it
					// into the context as an Integer.
					try {
						c.put(property, new Integer(value));
					} catch (NumberFormatException nfe) {
						// Now we will try to place the value into
						// the context as a boolean value if it
						// maps to a valid boolean value.
						String booleanString =
							contextProperties.testBoolean(value);

						if (booleanString != null) {
							c.put(property, new Boolean(booleanString));
						} else {
							// We are going to do something special
							// for properties that have a "file.contents"
							// suffix: for these properties will pull
							// in the contents of the file and make
							// them available in the context. So for
							// a line like the following in a properties file:
							//
							// license.file.contents = license.txt
							//
							// We will pull in the contents of license.txt
							// and make it available in the context as
							// $license. This should make texen a little
							// more flexible.
							if (property.endsWith("file.contents")) {
								// We need to turn the license file from relative to
								// absolute, and let Ant help :)
								value =
									StringUtils.fileContentsToString(
										project
											.resolveFile(value)
											.getCanonicalPath());

								property =
									property.substring(
										0,
										property.indexOf("file.contents") - 1);
							}

							c.put(property, value);
						}
					}
				}
			}

			writer.write(generator.parse(controlTemplate, c));
			writer.flush();
			writer.close();
			generator.shutdown();
			cleanup();
		} catch (BuildException e) {
			throw e;
		} catch (MethodInvocationException e) {
			throw new BuildException(
				"Exception thrown by '"
					+ e.getReferenceName()
					+ "."
					+ e.getMethodName()
					+ "'"
					+ ERR_MSG_FRAGMENT,
				e.getWrappedThrowable());
		} catch (ParseErrorException e) {
			throw new BuildException(
				"Velocity syntax error" + ERR_MSG_FRAGMENT,
				e);
		} catch (ResourceNotFoundException e) {
			throw new BuildException(
				"Resource not found" + ERR_MSG_FRAGMENT,
				e);
		} catch (Exception e) {
			throw new BuildException("Generation failed" + ERR_MSG_FRAGMENT, e);
		}
	}

}
