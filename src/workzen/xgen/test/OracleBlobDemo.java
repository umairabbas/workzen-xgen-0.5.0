/*
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
package workzen.xgen.test;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import workzen.xgen.util.SqlUtil;

/**
 * Store and fetch a binary file from an Oracle BLOB datatype.
 * The insert process requires three steps, but the fetch process 
 * remains the same.
 * 
 * <p>Reference: Peter Routtier-Wone
 * <a href="http://wamoz.com/">Wombat &amp; Me Pty Ltd</a>
 * 
 * <p>CREATE TABLE BLOB_TABLE (BLOB_ID INTEGER, BLOB_DATA BLOB);
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class OracleBlobDemo {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String username = "scott";
	private String password = "tiger";
	private String input = "d:/erd.jpg";
	private String output = "d:/erd2.jpg";
	
	private boolean inMemory = false;
	
	private Logger logger = Logger.getLogger(OracleBlobDemo.class);

	public static void main(String args[]) throws Exception {
		BasicConfigurator.configure();
		OracleBlobDemo demo = new OracleBlobDemo();
		demo.init();

		Connection conn = demo.getConnection();
		demo.insert(conn,1);
		demo.fetch(conn,1);
		demo.delete(conn,1);
		conn.close();
	}

	/**
	 * load assorted db drivers and let java work out 
	 * which one to use from the connection string
	 */
	private void init() {
		logger.debug("init()");
		Properties sysProps = System.getProperties();
		StringBuffer drivers =
			new StringBuffer("interbase.interclient.driver:oracle.jdbc.driver.OracleDriver");
		String oldDrivers = sysProps.getProperty("jdbc.drivers");
		if (oldDrivers != null)
			drivers.append(":" + oldDrivers);
		sysProps.put("jdbc.drivers", drivers.toString());
		System.setProperties(sysProps);
	}
	
	private Connection getConnection() throws SQLException{
		logger.debug("getConnection()");
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Insert data into an Oracle BLOB. Cut and paste as needed.
	 * This routine calls update(conn,id);
	 * 
	 * @param id
	 * @param inMemory
	 * @throws IOException
	 * @throws SQLException
	 */
	private void insert(Connection conn, int id) throws SQLException {
		logger.debug("insert()");
		
		//let the driver convert the string to an int
		String pkey = "" + id;

		String sqlNewRow =
			"INSERT INTO BLOB_TABLE (BLOB_ID,BLOB_DATA) VALUES (?,EMPTY_BLOB())";
				
		conn.setAutoCommit(false);
		
		//make new row
		PreparedStatement ps = conn.prepareStatement(sqlNewRow);
		ps.setString(1, pkey); 
		ps.executeUpdate();
		
		update(conn, id);
	}
	
	/**
	 * Update data in an Oracle BLOB. Cut and paste as needed.
	 * 
	 * @param id
	 * @param inMemory
	 * @throws IOException
	 * @throws SQLException
	 */
	private void update(Connection conn, int id) throws SQLException {
		logger.debug("update()");
		
		//let the driver convert the string to an int
		String pkey = "" + id;
		
		String sqlLockRow =
			"SELECT BLOB_DATA FROM BLOB_TABLE WHERE BLOB_ID = ? FOR UPDATE";
		String sqlSetBlob =
			"UPDATE BLOB_TABLE SET BLOB_DATA = ? WHERE BLOB_ID = ?";

		conn.setAutoCommit(false);

		//lock new row
		PreparedStatement ps = conn.prepareStatement(sqlLockRow);
		ps.setString(1, pkey); 
		ResultSet rs = ps.executeQuery();
		rs.next();

		//JDBC3 java.sql.Blob adds the method setBytes(int,byte[])
		oracle.sql.BLOB oracleBlob = (oracle.sql.BLOB) rs.getBlob(1);

		if (inMemory) {
			// use a byte[] to access data (from file or object)
			byte[] bytes = SqlUtil.readBytes(new File(input));
			SqlUtil.loadBLOB(bytes, oracleBlob);
		} else {
			// read bytes directly into the blob
			File inputFile = new File(input);
			SqlUtil.loadBLOB(inputFile, oracleBlob);
		}

		//update blob
		ps = conn.prepareStatement(sqlSetBlob);
		ps.setString(2, pkey); 
		ps.setBlob(1, oracleBlob);
		conn.commit();
		int rows = ps.executeUpdate();

		logger.debug("Rows affected: " + rows);
	}


	/**
	 * Fetch the data and write to disk.
	 * @param id
	 * @param inMemory
	 * @throws IOException
	 * @throws SQLException
	 */
	public void fetch(Connection conn, int id)
		throws IOException, SQLException {
		logger.debug("fetch()");
		
		String sql = "SELECT BLOB_DATA FROM BLOB_TABLE WHERE BLOB_ID = " + id;
	
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(sql);
		rs.next();

		Blob blob = rs.getBlob(1);

		File file = new File(output);

		if (inMemory) {
			byte[] bytes = SqlUtil.readBytes(blob);
			SqlUtil.writeBytes(bytes, file);
		} else {
			SqlUtil.writeBytes(blob, file);
		}
	}

	/**
	 * @param id
	 * @throws SQLException
	 */
	public void delete(Connection conn, int id) throws SQLException {
		logger.debug("delete()");
		String sql = "DELETE FROM BLOB_TABLE WHERE BLOB_ID = " + id;
		
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(sql);
	}

	/**
	 * Put the bytes into the blob.
	 * @param bytes
	 * @param oracleBlob
	 * @throws SQLException
	 *
	private void loadBLOB(byte[] bytes, oracle.sql.BLOB oracleBlob) throws SQLException{
		oracleBlob.putBytes(1, bytes);
	}*/

	/**
	 * Read bytes, source => destination
	 * @param inputFile
	 * @param blob
	 * @throws IOException
	 *
	private void loadBLOB(File inputFile, oracle.sql.BLOB blob)
		throws IOException, SQLException {
	
		OutputStream out = blob.getBinaryOutputStream();
		InputStream in = new FileInputStream(inputFile);
		byte[] buffer = new byte[10 * 1024];
		int num = 0;
		while ((num = in.read(buffer)) != -1) {
			out.write(buffer, 0, num);
		}
		in.close();
		out.close();
	}*/

	/**
	 * read source, return bytes
	 * @param file
	 * @return
	 * @throws SQLException
	 *
	private byte[] readBytes(File file) throws SQLException, IOException {
		int size = (int) file.length();
		byte[] data = new byte[size];
		FileInputStream fis = new FileInputStream(file);
		int chunk, i = 0;
		// METHOD 1 - read all at once
		// int n = fis.read( data );
		// METHOD 2 - byte at a time
		while ((chunk = fis.read()) != -1) {
			data[i++] = (byte) chunk;
		}
		fis.close();
	
		return data;
	}*/

	/**
	 * read source, return bytes
	 * @param blob
	 * @return
	 * @throws SQLException
	 *
	private byte[] readBytes(Blob blob) throws SQLException, IOException {
		byte[] data = new byte[(int) blob.length()];
		//METHOD 1 - get bytes using offset
		//this.blobType = blob.getBytes( (long)0, (int)blob.length() );    
		//METHOD 2 - read bytes using stream
		InputStream stream = blob.getBinaryStream();
		int n = stream.read(data);
		return data;
	}*/

	/**
	 * write bytes, source => dest
	 * @param bytes
	 * @param file
	 *
	private void writeBytes(byte[] bytes, File file) throws IOException {
		int length = bytes.length;
		FileOutputStream out = null;
		out = new FileOutputStream(file);
		out.write(bytes, 0, length);
		out.close();
	}*/

	/**
	 * write bytes, source => dest
	 * @param blob
	 * @param file
	 * @throws IOException
	 * @throws SQLException
	 *
	private void writeBytes(Blob blob, File file)
		throws IOException, SQLException {
		FileOutputStream out = new FileOutputStream(file);
		InputStream in = blob.getBinaryStream();
		byte[] buffer = new byte[10 * 1024];
		int num = 0;
	
		while ((num = in.read(buffer)) != -1) {
			out.write(buffer, 0, num);
		}
		in.close();
		out.close();
	}*/
}
