/*
 * SqlUtil.java
 * Copyright (c) May 26, 2003 Brad D Matlack.  
 * License http://www.gnu.org/copyleft/gpl.html
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
package workzen.xgen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * Sql utility methods for reading and writing Blobs, Clobs, BLOBS, and CLOBS
 * Catch IOExceptions for convience, and throw SQLExceptions.
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class SqlUtil {

	/**
	 * Put the bytes into the BLOB.
	 * @param bytes
	 * @param oracleBlob
	 * @throws SQLException
	 */
	public static void loadBLOB(byte[] bytes, oracle.sql.BLOB oracleBlob)
		throws SQLException {
		oracleBlob.putBytes(1, bytes);
	}

	/**
	 * Read bytes, source => destination
	 * @param inputFile
	 * @param blob
	 * @throws IOException
	 */
	public static void loadBLOB(File inputFile, oracle.sql.BLOB blob)
		throws SQLException {

		OutputStream out = null;
		InputStream in = null;
		try {
			out = blob.getBinaryOutputStream();
			in = new FileInputStream(inputFile);
			byte[] buffer = new byte[10 * 1024];
			int num = 0;
			while ((num = in.read(buffer)) != -1) {
				out.write(buffer, 0, num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e1) {
			}
			try {
				out.close();
			} catch (IOException e2) {
			}
		}
	}

	/**
	 * Put chars int the CLOB
	 * @param chars
	 * @param oracle.sql.CLOB clob
	 * @throws SQLException
	 */
	public static void loadCLOB(char[] chars, oracle.sql.CLOB clob)
		throws SQLException {
		clob.putChars(1, chars);
	}

	/**
	 * Read chars, source => destination
	 * @param inputFile
	 * @param oracle.sql.CLOB clob
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void loadCLOB(File inputFile, oracle.sql.CLOB clob)
		throws SQLException {

		Writer writer = null;
		Reader reader = null;
		try {
			writer = clob.getCharacterOutputStream();
			reader = new FileReader(inputFile);
			char[] buffer = new char[10 * 1024];
			int num = 0;
			while ((num = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, num);
			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		try {
			reader.close();
		} catch (IOException e1) {
		}
		try {
			writer.close();
		} catch (IOException e2) {
		}
	}

	/**
	 * read source, return bytes
	 * @param file
	 * @return
	 * @throws SQLException
	 */
	public static byte[] readBytes(File file) throws SQLException {
		int size = (int) file.length();
		byte[] data = new byte[size];
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			int chunk, i = 0;
			// METHOD 1 - read all at once
			// int n = fis.read( data );
			// METHOD 2 - byte at a time
			while ((chunk = fis.read()) != -1) {
				data[i++] = (byte) chunk;
			}
			fis.close();
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}

		return data;
	}

	/**
	 * read source, return bytes
	 * @param blob
	 * @return
	 * @throws SQLException
	 */
	public static byte[] readBytes(Blob blob) throws SQLException {
		byte[] data = new byte[(int) blob.length()];
		//METHOD 1 - get bytes using offset
		//this.blobType = blob.getBytes( (long)0, (int)blob.length() );    
		//METHOD 2 - read bytes using stream
		InputStream stream = blob.getBinaryStream();
		try {
			int n = stream.read(data);
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}
		return data;
	}

	/**
	 * write bytes, source => dest
	 * @param bytes
	 * @param file
	 */
	public static void writeBytes(byte[] bytes, File file) throws IOException {
		int length = bytes.length;
		FileOutputStream out = null;
		out = new FileOutputStream(file);
		out.write(bytes, 0, length);
		out.close();
	}

	/**
	 * write bytes, source => dest
	 * @param blob
	 * @param file
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void writeBytes(Blob blob, File file) throws SQLException {
		FileOutputStream out = null;
		InputStream in = null;
		try {
			out = new FileOutputStream(file);
			in = blob.getBinaryStream();
			byte[] buffer = new byte[10 * 1024];
			int num = 0;

			while ((num = in.read(buffer)) != -1) {
				out.write(buffer, 0, num);
			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		try {
			in.close();
		} catch (IOException e1) {
		}
		try {
			out.close();
		} catch (IOException e2) {
		}
	}

	/**
	 * read source, return chars
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	public static char[] readChars(Clob clob) throws SQLException {
		char[] data = new char[(int) clob.length()];
		try {
			//METHOD 1 - get bytes using offset
			//JDBC 2.0 does not implement this method
			//METHOD 2 - read bytes using stream
			Reader reader = clob.getCharacterStream();
			int n = reader.read(data);
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}
		return data;
	}

	/**
	 * read source, return chars
	 * @param file
	 * @return
	 * @throws SQLException
	 */
	public static char[] readChars(File file) throws SQLException {
		int size = (int) file.length();
		char[] data = new char[size];
		try {
			FileReader reader = new FileReader(file);
			int chunk, i = 0;
			// METHOD 1 - read all at once
			// int n = reader.read( data );
			// METHOD 2 - char at a time
			while ((chunk = reader.read()) != -1) {
				data[i++] = (char) chunk;
			}
			reader.close();
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}
		return data;
	}

	/**
	 * write chars, source => dest
	 * @param bytes
	 * @param file
	 */
	public static void writeChars(char[] chars, File file) throws IOException {
		int length = chars.length;
		FileWriter writer = new FileWriter(file);
		writer.write(chars);
		writer.close();
	}

	/**
	 * write bytes, source => dest
	 * @param blob
	 * @param file
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void writeChars(Clob clob, File file) throws SQLException {
		FileWriter writer = null;
		Reader reader = null;
		try {
			writer = new FileWriter(file);
			reader = clob.getCharacterStream();
			char[] buffer = new char[10 * 1024];
			int num = 0;
			while ((num = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, num);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writer.close();
		} catch (IOException e1) {
		}
		try {
			reader.close();
		} catch (IOException e2) {
		}
	}

	/** 
	 * This method single quotes all String objects.
	 * Other objects are returned as object.toString();
	 * 
	 * @param obj
	 * @return
	 */
	public static String quote(Object obj) {
		String value = obj.toString();
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		}
		return value;
	}

}
