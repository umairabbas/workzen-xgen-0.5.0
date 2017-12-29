/*
 * LobTypeBean.java 
 * Generated using xit and texen from template.vm
 * Mon May 13 12:52:48 MDT 2002
 */

package workzen.xgen.test.persistence;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.BatchUpdateException;
import java.math.BigDecimal;
import java.util.*;
import java.io.*;


/**
 * Demonstration for blob and clob types.  Blobs and Clobs are not implemented by all vendors.
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class LobTypeBean {

  /** class attributes */
  transient protected boolean recordExists = false;
  transient protected boolean handleConcurrency = true;
  transient protected boolean handleAutoIncrement = true;

  /** bean attributes */
  protected Long pKey = new Long ("0");
  protected byte[] blobType = new byte[1];
  protected char[] clobType = new char[1];
  protected Integer tcn = new Integer ("0");

  /** setters and getters for pKey */
  public void setPKey ( String value ) { this.pKey = new Long (value); }
  public void setPKey ( Long value ) { this.pKey = value; }
  public void setPKey ( long value ){ this.pKey = new Long (value); }
  public Long getPKey ( ){ return pKey; }
  
  /** setters and getters for blobType */
  public void setBlobType ( String value ) { this.blobType = value.getBytes(); }
  public void setBlobType ( byte[] value ){ this.blobType = value; }
  public void setBlobType ( java.sql.Blob blob ) throws SQLException {
    try{
      //METHOD 1 - get bytes using offset
      //this.blobType = blob.getBytes( (long)0, (int)blob.length() );
    
      //METHOD 2 - read bytes using stream
      this.blobType = new byte[ (int)blob.length() ];
      InputStream stream = blob.getBinaryStream(); 
      int n = stream.read(blobType);
    }catch(IOException e){
      throw new SQLException( e.getMessage() );
    }
  }
  /** set blob from file */
  public void setBlobType ( File file ) throws SQLException {
    try{
      FileInputStream fis = new FileInputStream(file);
      int size = (int)file.length();
      blobType = new byte[size];
      int data, i = 0;
      // METHOD 1 - read all at once
      // int n = fis.read( blobType );
      // METHOD 2 - byte at a time
      while( (data = fis.read() ) != -1 ){
        blobType[i++] = (byte)data;
      }
      fis.close();
    }catch(IOException e){
      throw new SQLException( e.getMessage() );
    }	
  }
  /** get blobType */
  public byte[] getBlobType (){ return blobType; }
  
  /** setters and getters for clobType */
  public void setClobType ( String value ) { this.clobType = value.toCharArray(); }
  public void setClobType ( char[] value ){ this.clobType = value; }
  public void setClobType ( java.sql.Clob clob ) throws SQLException {
    try{
      //METHOD 1 - get bytes using offset
      //JDBC 2.0 does not implement this method
    
      //METHOD 2 - read bytes using stream
      this.clobType = new char[ (int)clob.length() ];
      Reader reader = clob.getCharacterStream(); 
      int n = reader.read(clobType);
    }catch(IOException e){
      throw new SQLException( e.getMessage() );
    }
  }
  /** set clob from file */
  public void setClobType ( File file ) throws SQLException {
    try{
      FileReader reader = new FileReader(file);
      int size = (int)file.length();
      clobType = new char[size];
      int data, i = 0;
      // METHOD 1 - read all at once
      // int n = reader.read( clobType );
      // METHOD 2 - char at a time
      while( (data = reader.read() ) != -1 ){
        clobType[i++] = (char)data;
      }
      reader.close();
    }catch(IOException e){
      throw new SQLException( e.getMessage() );
    }
  }
  /** get clobType */
  public char[] getClobType (){ return clobType; }
  
  /** setters and getters for tcn */
  public void setTcn ( String value ) { this.tcn = new Integer (value); }
  public void setTcn ( Integer value ) { this.tcn = value; }
  public void setTcn ( int value ){ this.tcn = new Integer (value); }
  public Integer getTcn ( ){ return tcn; }
  
  /** 
   * Load this object from a result set 
   * If null fields are present in the table, catch the NullPointerException
   */
  public void load(ResultSet rs) throws SQLException {
    try{ setPKey ( rs.getLong("pkey") ); } catch(NullPointerException e){}
    try{ setBlobType ( rs.getBlob("blob_type") ); } catch(NullPointerException e){}
    try{ setClobType ( rs.getClob("clob_type") ); } catch(NullPointerException e){}
    try{ setTcn ( rs.getInt("tcn") ); } catch(NullPointerException e){}
    recordExists = true;
  }
  
  /** 
   * Load this object from a Map hash. 
   */
  public void load(java.util.Map map){
    java.util.Iterator it = map.keySet().iterator();
      
    while( it.hasNext() ){
      String key = (String)it.next();
      String value = (String)map.get(key);
      if(false){
        // just a placeholder for template construction
      }
      else if( key.equals("pKey") ) {
        setPKey( value ); 
      }
      else if( key.equals("blobType") ) {
        setBlobType( value ); 
      }
      else if( key.equals("clobType") ) {
        setClobType( value ); 
      }
      else if( key.equals("tcn") ) {
        setTcn( value ); 
      }
    }
  }

  /** 
   * Load this object from a request parameter Map, 
   * using only the first elements of the String[] values.
   */
  public void loadFromParameterMap(java.util.Map map){
    java.util.Iterator it = map.keySet().iterator();

    while( it.hasNext() ){
      String key = (String)it.next();
      String[] values = (String[])map.get(key);
      if(false){
        // just a placeholder for template construction
      }
      else if( key.equals("pKey") ) {
      setPKey( values[0] ); 
      }
      else if( key.equals("blobType") ) {
      setBlobType( values[0] ); 
      }
      else if( key.equals("clobType") ) {
      setClobType( values[0] ); 
      }
      else if( key.equals("tcn") ) {
      setTcn( values[0] ); 
      }
    }
  }

  /** 
   * Load a range of records from a connection, according to the where clause.
   * Returns a map of records accessible by a column value. (ie: foreign key)
   */
  public static Map loadByKey(Connection conn, String where, String colname) 
    throws SQLException{
  
    HashMap map = new HashMap();
    Statement s = conn.createStatement();
    String sql = "SELECT pkey,blob_type,clob_type,tcn FROM crud_lob_types " + where;
    ResultSet rs = s.executeQuery( sql );
    while(rs.next()){
      LobTypeBean bean = new LobTypeBean ();
      bean.load(rs);
      int col = rs.findColumn(colname);
      String key = rs.getString(col);
      map.put(key, bean);
    }
    rs.close();
    s.close();
    return map;
  }

  /** 
   * Load a range of records from a connection, according to the where clause 
   */
  public static Vector load(Connection conn, String where) throws SQLException{
    Vector beans = new Vector();
    Statement s = conn.createStatement();
    String sql = "SELECT pkey,blob_type,clob_type,tcn FROM crud_lob_types " + where;
    ResultSet rs = s.executeQuery( sql );
    while(rs.next()){
      LobTypeBean bean = new LobTypeBean ();
      bean.load(rs);
      beans.addElement(bean);
    }
    rs.close();
    s.close();
    return beans;
  }

  /**
   * Load a range of records using a scrollable resultset and absolute cursor
   */
  public static Vector load(Connection conn, String where, int startRow, int rowCount)
  throws SQLException {
    Vector beans = new Vector();
   
    Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                       ResultSet.CONCUR_UPDATABLE);
    
    String sql = "SELECT pkey,blob_type,clob_type,tcn FROM crud_lob_types ";
    if(where.length() > 0)
      sql = sql + where;
    ResultSet rs = s.executeQuery( sql );
    
    rs.absolute(startRow);
    
    int count = 0;
    while( rs.next() && (count < rowCount) ){
      count++;
      LobTypeBean bean = new LobTypeBean ();
      bean.load(rs);
      beans.addElement(bean);
    }
    rs.close();
    s.close();
    return beans;
  }

  /** 
   * Persist the record. Performs a sql update or insert.
   * The primary key will be "set" upon insert.
   * If autocommit is on, turn it off to make a transactional
   * check of the tcn field.
   */
  public void persist(Connection conn) throws SQLException{
    if(recordExists){
      if(handleConcurrency){
        boolean autoCommit = conn.getAutoCommit();
        if( autoCommit )
          conn.setAutoCommit(false);
        checkConcurrency(conn);
      
        //tcn = new Integer(tcn.intValue() + new Integer(1).intValue());
        tcn = new Integer (tcn.intValue() + new Integer (1).intValue() );
      
        update(conn);
        if( autoCommit ){
          conn.commit();
          conn.setAutoCommit(true);
        }
      }else{
        update(conn);
      }
    }else{
      setPrimaryKey(conn);
      insert(conn);
    }
  }
  
  /**
   * Query the database to make sure transaction control numbers match
   */
  private void checkConcurrency(Connection conn) throws SQLException{
    String sql = "SELECT tcn FROM crud_lob_types WHERE pKey = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt ( 1, getPKey ().intValue() );
  
    ResultSet rs = pstmt.executeQuery();
    rs.next();
    Integer tcnum = new Integer(rs.getInt(1));
    rs.close();
    pstmt.close();
    
    if( tcnum.intValue() !=  tcn.intValue() )
      throw new SQLException("Transaction control numbers do not match:" + tcnum + ":" + tcn );
  }

  /** 
    * Set the primary key field using the MAX value of the column 
    * If the primary key is a string, check for existence and return
    * If the primary key is already set (in a new bean its 0) return
    */
  private void setPrimaryKey(Connection conn) throws SQLException{
    if( getPKey().longValue() != 0 ) return;
    String sql = "SELECT MAX( pKey )+1 FROM crud_lob_types";
    Statement s = conn.createStatement();
    ResultSet rs = s.executeQuery( sql );
    rs.next();
    setPKey( rs.getLong(1) );
    rs.close();
    s.close();
   }

  /** 
    * Insert a new record 
    */
  public void insert(Connection conn) throws SQLException {
    PreparedStatement pstmt = null;
    pstmt = conn.prepareStatement("INSERT INTO crud_lob_types ( pkey,blob_type,clob_type,tcn ) VALUES ( ?,?,?,? )");
    pstmt.setLong ( 1, getPKey().longValue() );
    pstmt.setBinaryStream( 2, new ByteArrayInputStream( blobType ), blobType.length );
    pstmt.setCharacterStream( 3, new CharArrayReader( clobType ), clobType.length );
    pstmt.setInt ( 4, getTcn().intValue() );
    pstmt.executeUpdate();
    pstmt.close();
    recordExists = true;
  }

  /** 
    * Update an existing record 
    * (including the primary key)
    */
  public void update(Connection conn) throws SQLException{
    PreparedStatement pstmt = null;
    pstmt = conn.prepareStatement("UPDATE crud_lob_types SET " +
             "pkey = ?," +
             "blob_type = ?," +
             "clob_type = ?," +
             "tcn = ? " +	
             "WHERE pKey = ?" );
    pstmt.setLong ( 1, getPKey ().longValue() );
    pstmt.setBinaryStream( 2, new ByteArrayInputStream( blobType ), blobType.length );
    pstmt.setCharacterStream( 3, new CharArrayReader( clobType ), clobType.length );
    pstmt.setInt ( 4, getTcn ().intValue() );
    pstmt.setLong( 5, getPKey ().longValue() );
    pstmt.executeUpdate();
    pstmt.close();
  }

  /** 
    * Delete an existing record 
    */
  public static void delete(Connection conn, String where) throws SQLException{
    Statement stmt = conn.createStatement();
    String sql = "DELETE FROM crud_lob_types " + where;
    stmt.execute(sql);
    stmt.close();
  }

  /** 
    * Delete an existing record using a prepared statement
    */
  public void delete(Connection conn) throws SQLException{
    PreparedStatement pstmt = null;
    pstmt = conn.prepareStatement("DELETE FROM crud_lob_types WHERE pKey = ?");
    pstmt.setLong( 1, getPKey ().longValue() );
    pstmt.executeUpdate();
    pstmt.close();
  }
 
  /** 
    * to String 
    */
  public String toString(){
    return toString(",");
  }

  /** 
    * to String with specified line terminator
    */
  public String toString(String nl){
    StringBuffer buf = new StringBuffer();
    buf.append( getPKey () );
    buf.append(nl);
    buf.append( new String( getBlobType() ) );
    buf.append(nl);
    buf.append( new String( getClobType() ) );
    buf.append(nl);
    buf.append( getTcn () );
    buf.append(nl);
    return buf.toString();
  }
 
}




