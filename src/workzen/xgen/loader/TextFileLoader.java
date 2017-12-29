/*
 * Copyright (c) Apr 18, 2004 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.loader;

import java.io.File;
import java.util.Map;

import workzen.common.util.ResourceLoader;
import workzen.xgen.XGenException;
import workzen.xgen.model.file.TextFile;
import workzen.xgen.util.PropertyUtil;

/**
 * This loader reads a textfile and loads a model.TextFile object.
 * <b>Properties</b>
 * <br>
 * <pre>
 * txt.filepath
 * </pre>
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class TextFileLoader implements ILoader {

	public static final String TXT_FILEPATH = "txtFilepath";

	private String txtFilepath = "undefined";

	/**
	 * @see workzen.xgen.loader.ILoader#configure(java.util.Map)
	 */
	public void configure(Map map) throws XGenException {
		this.txtFilepath = PropertyUtil.getRequired(map,TXT_FILEPATH);
	}

	/**
	 * @return File model
	 * @see workzen.xgen.loader.ILoader#loadModel()
	 */
	public Object loadModel() throws XGenException {
		File file = ResourceLoader.loadFile(txtFilepath);
		if (file == null || file.exists() == false) {
			throw new XGenException("File does not exist " + txtFilepath, null);
		}
		TextFile textFile = new TextFile(file);
		return textFile;
	}

}
