/*
 * Copyright (c) May 16, 2006 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.util;

import java.util.Map;

import org.apache.log4j.Logger;

import workzen.xgen.XGenException;

/**
 * This class  is derived from the workzen.service.util.PropertyUtil class.
 * These methods use  Maps instead of ServiceProperties.
 * 
 * @author brad.matlack
 */
public class PropertyUtil {

	private static Logger logger = Logger.getLogger(PropertyUtil.class);

	/**
	 * Get the required property or throw an exception
	 * 
	 * @param map
	 * @param key
	 * @return
	 * @throws XGenException
	 */
	public static String getRequired(Map map, String key)
		throws XGenException {
		String property = (String) map.get(key);
		if (property == null || property.equals("")) {
			throw new XGenException(
				"Error getting required property: " + key,
				null);
		}
		logger.info("required property found: " + key + " = " + property);
		return property;
	}

	/**
	 * Get the optional property or return the default
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getOptional(
		Map map,
		String key,
		String defaultValue) {
		String property = (String) map.get(key);
		if (property == null || property.equals("")) {
			logger.info(
				"optional property default: " + key + " = " + defaultValue);
			return defaultValue;
		}
		logger.info("optional property found: " + key + " = " + property);
		return property;
	}

	/**
	 * Get the boolean value of the property or return the default
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getOptionalBoolean(
		Map map,
		String key,
		boolean defaultValue) {
		String property = (String) map.get(key);
		boolean value = defaultValue;
		if (property == null || property.equals("")) {
			logger.info(
				"optional property default: " + key + " = " + defaultValue);
			return defaultValue;
		}
		value = property.equalsIgnoreCase("true") ? true : false;
		logger.info("boolean property " + key + " = " + property);
		return value;
	}

}
