/*
 * TypeBean.java 
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

import workzen.common.util.DateUtil;


/**
 * Demonstration class of all types except blob and clob
 *@author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class TypeBean {

  /** class attributes */
  transient protected boolean recordExists = false;
  transient protected boolean handleConcurrency = true;
  transient protected boolean handleAutoIncrement = true;

  /** bean attributes */
  protected Long pKey = new Long ("0");
  protected Boolean booleanType = new Boolean ("0");
  protected Byte byteType = new Byte ("0");
  protected String charType = new String ("Y");
  protected Short shortType = new Short ("0");
  protected Integer intType = new Integer ("0");
  protected Long longType = new Long ("0");
  protected Float floatType = new Float ("0");
  protected Double doubleType = new Double ("0");
  protected String stringType = new String ("xxx");
  protected String textType = new String ("longer text");
  protected java.util.Date dateType = DateUtil.getDate("2000-12-31");
  protected java.util.Date timeType = DateUtil.getDate("01:01:01");
  protected java.util.Date timestampType = DateUtil.getDate("2001-01-01 00:00:01");
  protected Integer tcn = new Integer ("0");

  /** setters and getters for pKey */
  public void setPKey ( String value ) { this.pKey = new Long (value); }
  public void setPKey ( Long value ) { this.pKey = value; }
  public void setPKey ( long value ){ this.pKey = new Long (value); }
  public Long getPKey ( ){ return pKey; }
  
  /** setters and getters for booleanType */
  public void setBooleanType ( String value ) { this.booleanType = new Boolean (value); }
  public void setBooleanType ( Boolean value ) { this.booleanType = value; }
  public void setBooleanType ( boolean value ){ this.booleanType = new Boolean (value); }
  public Boolean getBooleanType ( ){ return booleanType; }
  
  /** setters and getters for byteType */
  public void setByteType ( String value ) { this.byteType = new Byte (value); }
  public void setByteType ( Byte value ) { this.byteType = value; }
  public void setByteType ( byte value ){ this.byteType = new Byte (value); }
  public Byte getByteType ( ){ return byteType; }
  
  /** setters and getters for charType */
  public void setCharType ( String value ) { this.charType = new String (value); }
  public String getCharType ( ){ return charType; }	
  
  /** setters and getters for shortType */
  public void setShortType ( String value ) { this.shortType = new Short (value); }
  public void setShortType ( Short value ) { this.shortType = value; }
  public void setShortType ( short value ){ this.shortType = new Short (value); }
  public Short getShortType ( ){ return shortType; }
  
  /** setters and getters for intType */
  public void setIntType ( String value ) { this.intType = new Integer (value); }
  public void setIntType ( Integer value ) { this.intType = value; }
  public void setIntType ( int value ){ this.intType = new Integer (value); }
  public Integer getIntType ( ){ return intType; }
  
  /** setters and getters for longType */
  public void setLongType ( String value ) { this.longType = new Long (value); }
  public void setLongType ( Long value ) { this.longType = value; }
  public void setLongType ( long value ){ this.longType = new Long (value); }
  public Long getLongType ( ){ return longType; }
  
  /** setters and getters for floatType */
  public void setFloatType ( String value ) { this.floatType = new Float (value); }
  public void setFloatType ( Float value ) { this.floatType = value; }
  public void setFloatType ( float value ){ this.floatType = new Float (value); }
  public Float getFloatType ( ){ return floatType; }
  
  /** setters and getters for doubleType */
  public void setDoubleType ( String value ) { this.doubleType = new Double (value); }
  public void setDoubleType ( Double value ) { this.doubleType = value; }
  public void setDoubleType ( double value ){ this.doubleType = new Double (value); }
  public Double getDoubleType ( ){ return doubleType; }
  
  /** setters and getters for stringType */
  public void setStringType ( String value ) { this.stringType = new String (value); }
  public String getStringType ( ){ return stringType; }	
  
  /** setters and getters for textType */
  public void setTextType ( String value ) { this.textType = new String (value); }
  public String getTextType ( ){ return textType; }	
  
  /** setters and getters for dateType */
  public void setDateType ( String value ){ this.dateType = DateUtil.getDate(value); }
  public void setDateType ( long value ){ this.dateType = new java.util.Date(value); }
  public void setDateType ( java.util.Date value ){ this.dateType = value; }
  public java.util.Date getDateType ( ){ return dateType; }
  
  /** setters and getters for timeType */
  public void setTimeType ( String value ){ this.timeType = DateUtil.getDate(value); }
  public void setTimeType ( long value ){ this.timeType = new java.util.Date(value); }
  public void setTimeType ( java.util.Date value ){ this.timeType = value; }
  public java.util.Date getTimeType ( ){ return timeType; }
  
  /** setters and getters for timestampType */
  public void setTimestampType ( String value ){ this.timestampType = DateUtil.getDate(value); }
  public void setTimestampType ( long value ){ this.timestampType = new java.util.Date(value); }
  public void setTimestampType ( java.util.Date value ){ this.timestampType = value; }
  public java.util.Date getTimestampType ( ){ return timestampType; }
  
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
    try{ setBooleanType ( rs.getBoolean("boolean_type") ); } catch(NullPointerException e){}
    try{ setByteType ( rs.getByte("byte_type") ); } catch(NullPointerException e){}
    try{ setCharType ( rs.getString("char_type") ); } catch(NullPointerException e){}
    try{ setShortType ( rs.getShort("short_type") ); } catch(NullPointerException e){}
    try{ setIntType ( rs.getInt("int_type") ); } catch(NullPointerException e){}
    try{ setLongType ( rs.getLong("long_type") ); } catch(NullPointerException e){}
    try{ setFloatType ( rs.getFloat("float_type") ); } catch(NullPointerException e){}
    try{ setDoubleType ( rs.getDouble("double_type") ); } catch(NullPointerException e){}
    try{ setStringType ( rs.getString("string_type") ); } catch(NullPointerException e){}
    try{ setTextType ( rs.getString("text_type") ); } catch(NullPointerException e){}
    try{ setDateType ( rs.getDate("date_type") ); } catch(NullPointerException e){}
    try{ setTimeType ( rs.getTime("time_type") ); } catch(NullPointerException e){}
    try{ setTimestampType ( rs.getTimestamp("timestamp_type") ); } catch(NullPointerException e){}
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
      else if( key.equals("booleanType") ) {
        setBooleanType( value ); 
      }
      else if( key.equals("byteType") ) {
        setByteType( value ); 
      }
      else if( key.equals("charType") ) {
        setCharType( value ); 
      }
      else if( key.equals("shortType") ) {
        setShortType( value ); 
      }
      else if( key.equals("intType") ) {
        setIntType( value ); 
      }
      else if( key.equals("longType") ) {
        setLongType( value ); 
      }
      else if( key.equals("floatType") ) {
        setFloatType( value ); 
      }
      else if( key.equals("doubleType") ) {
        setDoubleType( value ); 
      }
      else if( key.equals("stringType") ) {
        setStringType( value ); 
      }
      else if( key.equals("textType") ) {
        setTextType( value ); 
      }
      else if( key.equals("dateType") ) {
        setDateType( value ); 
      }
      else if( key.equals("timeType") ) {
        setTimeType( value ); 
      }
      else if( key.equals("timestampType") ) {
        setTimestampType( value ); 
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
      else if( key.equals("booleanType") ) {
      setBooleanType( values[0] ); 
      }
      else if( key.equals("byteType") ) {
      setByteType( values[0] ); 
      }
      else if( key.equals("charType") ) {
      setCharType( values[0] ); 
      }
      else if( key.equals("shortType") ) {
      setShortType( values[0] ); 
      }
      else if( key.equals("intType") ) {
      setIntType( values[0] ); 
      }
      else if( key.equals("longType") ) {
      setLongType( values[0] ); 
      }
      else if( key.equals("floatType") ) {
      setFloatType( values[0] ); 
      }
      else if( key.equals("doubleType") ) {
      setDoubleType( values[0] ); 
      }
      else if( key.equals("stringType") ) {
      setStringType( values[0] ); 
      }
      else if( key.equals("textType") ) {
      setTextType( values[0] ); 
      }
      else if( key.equals("dateType") ) {
      setDateType( values[0] ); 
      }
      else if( key.equals("timeType") ) {
      setTimeType( values[0] ); 
      }
      else if( key.equals("timestampType") ) {
      setTimestampType( values[0] ); 
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
    String sql = "SELECT pkey,boolean_type,byte_type,char_type,short_type,int_type,long_type,float_type,double_type,string_type,text_type,date_type,time_type,timestamp_type,tcn FROM crud_types " + where;
    ResultSet rs = s.executeQuery( sql );
    while(rs.next()){
      TypeBean bean = new TypeBean ();
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
    String sql = "SELECT pkey,boolean_type,byte_type,char_type,short_type,int_type,long_type,float_type,double_type,string_type,text_type,date_type,time_type,timestamp_type,tcn FROM crud_types " + where;
    ResultSet rs = s.executeQuery( sql );
    while(rs.next()){
      TypeBean bean = new TypeBean ();
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
    
    String sql = "SELECT pkey,boolean_type,byte_type,char_type,short_type,int_type,long_type,float_type,double_type,string_type,text_type,date_type,time_type,timestamp_type,tcn FROM crud_types ";
    if(where.length() > 0)
      sql = sql + where;
    ResultSet rs = s.executeQuery( sql );
    
    rs.absolute(startRow);
    
    int count = 0;
    while( rs.next() && (count < rowCount) ){
      count++;
      TypeBean bean = new TypeBean ();
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
    String sql = "SELECT tcn FROM crud_types WHERE pKey = ?";
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
    String sql = "SELECT MAX( pKey )+1 FROM crud_types";
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
    pstmt = conn.prepareStatement("INSERT INTO crud_types ( pkey,boolean_type,byte_type,char_type,short_type,int_type,long_type,float_type,double_type,string_type,text_type,date_type,time_type,timestamp_type,tcn ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
    pstmt.setLong ( 1, getPKey().longValue() );
    pstmt.setBoolean ( 2, getBooleanType().booleanValue() );
    pstmt.setByte ( 3, getByteType().byteValue() );
    pstmt.setString ( 4, getCharType() );
    pstmt.setShort ( 5, getShortType().shortValue() );
    pstmt.setInt ( 6, getIntType().intValue() );
    pstmt.setLong ( 7, getLongType().longValue() );
    pstmt.setFloat ( 8, getFloatType().floatValue() );
    pstmt.setDouble ( 9, getDoubleType().doubleValue() );
    pstmt.setString ( 10, getStringType() );
    pstmt.setString ( 11, getTextType() );
    pstmt.setDate ( 12, new java.sql.Date ( getDateType ().getTime() ) );
    pstmt.setTime ( 13, new java.sql.Time ( getTimeType ().getTime() ) );
    pstmt.setTimestamp ( 14, new java.sql.Timestamp ( getTimestampType ().getTime() ) );
    pstmt.setInt ( 15, getTcn().intValue() );
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
    pstmt = conn.prepareStatement("UPDATE crud_types SET " +
             "pkey = ?," +
             "boolean_type = ?," +
             "byte_type = ?," +
             "char_type = ?," +
             "short_type = ?," +
             "int_type = ?," +
             "long_type = ?," +
             "float_type = ?," +
             "double_type = ?," +
             "string_type = ?," +
             "text_type = ?," +
             "date_type = ?," +
             "time_type = ?," +
             "timestamp_type = ?," +
             "tcn = ? " +	
             "WHERE pKey = ?" );
    pstmt.setLong ( 1, getPKey ().longValue() );
    pstmt.setBoolean ( 2, getBooleanType ().booleanValue() );
    pstmt.setByte ( 3, getByteType ().byteValue() );
    pstmt.setString ( 4, getCharType () );
    pstmt.setShort ( 5, getShortType ().shortValue() );
    pstmt.setInt ( 6, getIntType ().intValue() );
    pstmt.setLong ( 7, getLongType ().longValue() );
    pstmt.setFloat ( 8, getFloatType ().floatValue() );
    pstmt.setDouble ( 9, getDoubleType ().doubleValue() );
    pstmt.setString ( 10, getStringType () );
    pstmt.setString ( 11, getTextType () );
    pstmt.setDate ( 12, new java.sql.Date ( getDateType ().getTime() ) );
    pstmt.setTime ( 13, new java.sql.Time ( getTimeType ().getTime() ) );
    pstmt.setTimestamp ( 14, new java.sql.Timestamp ( getTimestampType ().getTime() ) );
    pstmt.setInt ( 15, getTcn ().intValue() );
    pstmt.setLong( 16, getPKey ().longValue() );
    pstmt.executeUpdate();
    pstmt.close();
  }

  /** 
    * Delete an existing record 
    */
  public static void delete(Connection conn, String where) throws SQLException{
    Statement stmt = conn.createStatement();
    String sql = "DELETE FROM crud_types " + where;
    stmt.execute(sql);
    stmt.close();
  }

  /** 
    * Delete an existing record using a prepared statement
    */
  public void delete(Connection conn) throws SQLException{
    PreparedStatement pstmt = null;
    pstmt = conn.prepareStatement("DELETE FROM crud_types WHERE pKey = ?");
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
    buf.append( getBooleanType () );
    buf.append(nl);
    buf.append( getByteType () );
    buf.append(nl);
    buf.append( getCharType () );
    buf.append(nl);
    buf.append( getShortType () );
    buf.append(nl);
    buf.append( getIntType () );
    buf.append(nl);
    buf.append( getLongType () );
    buf.append(nl);
    buf.append( getFloatType () );
    buf.append(nl);
    buf.append( getDoubleType () );
    buf.append(nl);
    buf.append( getStringType () );
    buf.append(nl);
    buf.append( getTextType () );
    buf.append(nl);
    buf.append( getDateType () );
    buf.append(nl);
    buf.append( getTimeType () );
    buf.append(nl);
    buf.append( getTimestampType () );
    buf.append(nl);
    buf.append( getTcn () );
    buf.append(nl);
    return buf.toString();
  }
 
}




