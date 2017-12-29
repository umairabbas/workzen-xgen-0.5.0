/*
 * Copyright (c) Apr 18, 2004 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.model.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class TextFile {

	private static final String NL = System.getProperty("line.separator");
	
	private File file;
	private HashMap replacementMap = new HashMap();
	private Logger logger = Logger.getLogger(TextFile.class);

	public TextFile(File file) {
		this.file = file;
	}

	public void addReplacement(String oldStr, String newStr) {
		replacementMap.put(oldStr, newStr);
	}

	public String parse() throws IOException {
		
		StringBuffer buf = new StringBuffer();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String currentLine;
		while ((currentLine = bufferedReader.readLine()) != null) {
			String newLine = findAndReplace(currentLine);
			buf.append(newLine);
			buf.append(NL);
		}
		bufferedReader.close();
		String text = buf.toString();
		return text;
	}

	private String findAndReplace(String line) {
		Iterator it = replacementMap.keySet().iterator();
		while (it.hasNext()) {
			String oldStr = (String) it.next();
			String newStr = (String) replacementMap.get(oldStr);
			logger.debug("replacing: " + oldStr + " " + newStr);
			line = line.replaceAll(oldStr, newStr);
		}
		logger.debug(line);
		return line;
	}
	
	public File getFile(){
		return file;
	}

}
