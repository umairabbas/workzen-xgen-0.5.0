/*
 * Copyright (c) May 16, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.engine;

import java.util.Map;

import workzen.xgen.XGenException;


/**
 * @author brad.matlack
 */
public interface IProcessor {
	
	public void configure(Map map) throws XGenException;
	
	public void process(Object model) throws XGenException;

}
