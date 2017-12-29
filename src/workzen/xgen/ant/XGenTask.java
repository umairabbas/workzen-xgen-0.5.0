/*
 * Copyright (c) May 10, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.ant;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Task;

import workzen.xgen.XGenException;
import workzen.xgen.loader.ILoader;
import workzen.xgen.engine.IProcessor;
import workzen.xgen.util.PropertyUtil;

/**
 * @author brad.matlack
 */
public class XGenTask extends Task {
	
	public static final String LOG_LEVEL = "logLevel";

	private String loader;
	private String processor;
	private Logger logger = Logger.getLogger(XGenTask.class);

	public XGenTask() {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
	}

	public void maybeConfigure() throws BuildException {
		System.out.println("maybeConfigure()");

		RuntimeConfigurable rc = super.getRuntimeConfigurableWrapper();
		Map map = rc.getAttributeMap();
		//updateAttributeMap(map);

		String logLevel = PropertyUtil.getOptional(map, "logLevel", "info");
		System.out.println("logLevel: " + logLevel);
		configureLogger(logLevel);

		//System.out.println(map);
		try {
			this.loader = PropertyUtil.getRequired(map, "loader");
			this.processor = PropertyUtil.getRequired(map, "processor");
		} catch (XGenException e) {
			System.out.println("maybyConfigure() " + e.getMessage());
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	private void configureLogger(String logLevel) {
		Logger root = Logger.getRootLogger();
		if (logLevel.equalsIgnoreCase("debug")) {
			root.setLevel(Level.DEBUG);
		} else if (logLevel.equalsIgnoreCase("info")) {
			root.setLevel(Level.INFO);
		} else if (logLevel.equalsIgnoreCase("warn")) {
			root.setLevel(Level.WARN);
		} else if (logLevel.equalsIgnoreCase("error")) {
			root.setLevel(Level.ERROR);
		} else if (logLevel.equalsIgnoreCase("fatal")) {
			root.setLevel(Level.FATAL);
		} else if (logLevel.equalsIgnoreCase("off")) {
			root.setLevel(Level.OFF);
		}

	}

	private Map updateAttributeMap(Map map) {
		Project p = getProject();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) map.get(key);
			String newValue = p.replaceProperties(value);
			map.put(key, newValue); // update the map
			print(key, newValue);
		}
		return map;
	}

	public void execute() throws BuildException {
		System.out.println("execute()");
		RuntimeConfigurable rc = super.getRuntimeConfigurableWrapper();
		Map map = rc.getAttributeMap();
		updateAttributeMap(map);
		System.out.println(map);

		try {
			ILoader xgenLoader = (ILoader) getInstance(loader);
			xgenLoader.configure(map);
			Object model = xgenLoader.loadModel();
			IProcessor xgenProcessor = (IProcessor) getInstance(processor);
			xgenProcessor.configure(map);
			xgenProcessor.process(model);
		} catch (Throwable t) {
			System.out.println("execute() " + t.getMessage());
			t.printStackTrace();
			throw new BuildException("", t);
		}
		shutdown();
	}
	
	protected void shutdown(){
		logger.info("shutdown()");
		System.out.println(":");
	}

	/**
	 * @return
	 */
	private Object getInstance(String classname)
		throws
			ClassNotFoundException,
			InstantiationException,
			IllegalAccessException {
		super.log("getInstance() " + classname);
		Class c = Class.forName(classname);
		Object obj = c.newInstance();
		return obj;
	}

	private void print(String key, String value) {
		System.out.println(key + " = " + value);
	}

}
