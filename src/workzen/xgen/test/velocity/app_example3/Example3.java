package workzen.xgen.test.velocity.app_example3;

/*
 * Copyright 2000-2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;
import java.util.ArrayList;

/**
 * This class is a simple demonstration of how the Velocity Template Engine
 * can be used in a standalone application.
 *
 */

public class Example3 {

	public Example3(String templateFile) {
		try {
			
			String velocityProperties =
						"D:/Project/workzen.xgen/src/workzen/xgen/test/velocity/app_example1/velocity.properties";
			/*
			 * setup
			 */
			Velocity.init(velocityProperties);

			/*
			 *  Make a context object and populate with the data.  This 
			 *  is where the Velocity engine gets the data to resolve the
			 *  references (ex. $list) in the template
			 */

			VelocityContext context = new VelocityContext();
			context.put("model", getModel());

			/*
			 *  get the Template object.  This is the parsed version of your 
			 *  template input file.  Note that getTemplate() can throw
			 *   ResourceNotFoundException : if it doesn't find the template
			 *   ParseErrorException : if there is something wrong with the VTL
			 *   Exception : if something else goes wrong (this is generally
			 *        indicative of as serious problem...)
			 */

			Template template = null;

			try {
				template = Velocity.getTemplate(templateFile);
			} catch (ResourceNotFoundException rnfe) {
				System.out.println(
					"Example : error : cannot find template " + templateFile);
			} catch (ParseErrorException pee) {
				System.out.println(
					"Example : Syntax error in template "
						+ templateFile
						+ ":"
						+ pee);
			}

			/*
			 *  Now have the template engine process your template using the
			 *  data placed into the context.  Think of it as a  'merge' 
			 *  of the template and the data to produce the output stream.
			 */

			BufferedWriter writer =
				writer = new BufferedWriter(new OutputStreamWriter(System.out));

			if (template != null)
				template.merge(context, writer);

			/*
			 *  flush and cleanup
			 */

			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Object getModel(){
		TestModel model = new TestModel();
		model.setName("Linus Torvald");
		return model;
	}

	/**
	 * Check your velocity properties file.resource.loader.path
	 * @param args
	 */
	public static void main(String[] args) {
		//Example t = new Example(args[0]);
		//String template = "example.vm";
		//String template =	"D:/Project/workzen.xgen/src/workzen/xgen/test/velocity/app_example1/example.vm";
		String template =	"/Project/workzen.xgen/src/workzen/xgen/test/velocity/app_example3/example.vm";
		Example3 t = new Example3(template);
	}
}
