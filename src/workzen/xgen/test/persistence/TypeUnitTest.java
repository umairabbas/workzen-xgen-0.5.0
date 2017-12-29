/*
 * TypeUnitTest.java
 * Copyright (c) Brad D Matlack 3-2002
 * License http://www.gnu.org/copyleft/gpl.html
 */

package workzen.xgen.test.persistence;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Vector;

import workzen.xgen.util.Timer;

import junit.framework.*;

/*
 * This is the junit test for the TypeBean.
 * <br>
 * NOTE: the use of the Gregroian Calendar and Date.
 *  This is the "cross-platform" way to specify a date in JDK1.3
 *  where java.sql.Date is created using a "long" milliseconds constructor.
 *  java.sql.Date extends java.util.Date which encapsulated all three
 *  sql date types: date, time, and timestamp.
 * <br>
 *  When executed from ant, db.properties sits in the ant directory.
 * <br>
 * This uses a single connection that stays open for the duration of the test.
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class TypeUnitTest extends TestCase
{
    /** static properties are initialized in main */
    public static String propertyFile = "db.properties";
    public static String dbUrl = "";
    public static String dbDriver = "";
    public static String dbUsername = "";
    public static String dbPassword = "";
    public static Connection conn = null;
    
    protected static TypeBean bean;
    protected static Timer timer;
    
    
    /** initialize Test */
    public TypeUnitTest(String name) {
      super(name);
    }
    
    /**
   * @param args optional property filename 
   * default = test.properties
   */
    public static void main (String args[]) throws Exception {
      
      String usage = "usage: java com.workzen.xit.test.TypeUnitTest [propertyfile]";
  
      if( args.length == 0 ){
        System.out.println(usage);
        System.exit(1);
      }
      
      if(args[0].equals("help")){
        System.out.println(usage);
        System.exit(1);
      }else{
        propertyFile = args[0];
      }
      
      try{
        Properties properties = new Properties();    
        properties.load( new FileInputStream( propertyFile ) );
        dbUrl = properties.getProperty("db.url");
        dbDriver = properties.getProperty("db.driver");
        dbUsername = properties.getProperty("db.username");
        dbPassword = properties.getProperty("db.password");
      }catch(Exception e){
        e.printStackTrace();
      }     
      timer = new Timer();
      bean = new TypeBean();
      try{
        conn = getConnection();
        junit.textui.TestRunner.run(TypeUnitTest.class);
        disconnect();
      }catch(Exception e){
        e.printStackTrace();
  
      }
      
    }

    /** junit setup */	
    public void setUp(){
      try{}catch(Exception e){
        e.printStackTrace();
      }
    }

    /** test persistance with a manually set primary key */  
    public void testManualPersistance(){
      long num = 5;
      System.out.println("testing persistance where pkey = " + num);
      try{
        // manually set key 
        bean.setPKey(num);
        bean.setStringType("testing manual key set");
        bean.persist(conn);
      }catch(SQLException e){
        e.printStackTrace();
        
      }
    }

    /** test persistance using automatic key generation */
    public void testAutoIncrementalPersistence(){
      System.out.println("testing automatic key generation");
      GregorianCalendar cal = new GregorianCalendar();
      try{
        bean.setStringType("testing automatic key generation");
        bean.persist(conn);
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    
    /** test persistence from a Map object */
    public void testPersistFromMap(){
      System.out.println("testing persistance from map...");
      java.util.HashMap map = new java.util.HashMap();
      map.put("booleanType","0");
      map.put("charType","N");
      map.put("byteType","AC183E6A");
      map.put("shortType","12345");
      map.put("intType","1234567890");
      map.put("longType","1234567801234567890");
      map.put("floatType","12345.67");
      map.put("doubleType","1234567890.1234567890");
      map.put("stringType","MAP33 TEST");
      map.put("decimalType","12345.12345");
      map.put("dateType","2001-12-24");
      map.put("timeType","01:00:05");
      map.put("timestampType","2002-01-01 00:11:00");
      map.put("clobType","hello world");
      map.put("byteType","this is a byte array");
      try{
        bean.persist(conn);
      }catch(SQLException e){
        e.printStackTrace();
      }
    } 
 
    /** test persistence of date, time and timestamp setting */
    public void testDateTimePersistence(){
      System.out.println("testing date, time, timestamp");
      GregorianCalendar cal = new GregorianCalendar();
      try{
        cal.set(2001,12,12);
        bean.setDateType( cal.getTime() );

        cal.set(Calendar.HOUR, 22);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 01);
        bean.setTimeType( cal.getTime() );
        
        cal.set(2001,12,12,01,01,01);
        bean.setTimestampType( cal.getTime() );
        
        bean.persist(conn);
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    
    /** test load and update using a where clause */
    public void testLoadAndUpdateWhere(){
      String where = "where pkey = 5";	
      System.out.println("testing update " + where);
      try{
        Vector beans = TypeBean.load(conn, where ); 
        TypeBean bean = (TypeBean)beans.firstElement();
        bean.setStringType("THIS IS AN UPDATE");
        bean.persist(conn);
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    
    /** test load and update using a transaction */
    public void testLoadAndUpdateWithTransaction(){
      String where = "where pkey = 5";	
      System.out.println("testing transaction...");
      try{
        conn.setAutoCommit(false);
        Vector beans = TypeBean.load(conn, where); 
        TypeBean bean = (TypeBean)beans.firstElement();
        bean.setStringType("Testing Transactions");
        bean.persist(conn);
        conn.commit();
        conn.setAutoCommit(true);
      }catch(SQLException e){
        try{
          conn.rollback();
        }catch(SQLException ex){
          e.printStackTrace();
        }
      }		
    }
   
    /** test a rollback with a transaction */
    public void testRollbackTransaction(){
      String where = "where pkey = 5";	
      System.out.println("testing rollback...");
      testPrintRecord();
      try{
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        Vector beans  = TypeBean.load(conn, where); 
        TypeBean work = (TypeBean)beans.firstElement();
        work.setStringType("Testing Rollback");
        work.persist(conn);
        throw new SQLException("just a failed transaction");
        //conn.commit(); // unreachable
      }catch(SQLException e){
        try{
          System.out.println("rolling back transaction");
          conn.rollback();
        }catch(SQLException ex){
          ex.printStackTrace();
        }
      }finally{
        try{
          conn.setAutoCommit(true);
        }catch(SQLException sqle){
          sqle.printStackTrace();
        }
      }
      testPrintRecord();
      
    }
    
    /** test performance : read-write (load/persist) */
    public void testPerformance(){
      int num = 1000;
      String where = "where pkey = 5";	
      System.out.println("testing performance: load, update and persist: " + num + " records.");
      Timer timer = new Timer();
      for(int i=0; i < num; i++){
        if(i % 10 == 0)	System.out.print(".");
        try{
          Vector beans = TypeBean.load(conn, where); 
          TypeBean bean = (TypeBean)beans.firstElement();
          bean.setStringType("Performance Test");
          bean.persist(conn);
          bean = null; // garbage collect
        }catch(SQLException e){
          e.printStackTrace();
          break;
        }
      }
      timer.print("Run time in milliseconds");
    }    
  
    // print a record 
    public void testPrintRecord(){
      String where = "where pkey = 5";	
      System.out.println("printing: " + where);
      try{
        Vector beans = TypeBean.load(conn, where);
        if(beans.size() == 0){
          System.out.println("Records not found: " + where);
        }else{
          TypeBean bean = (TypeBean)beans.firstElement();
          System.out.println(bean.toString());
        }
      }catch(Exception e){
        e.printStackTrace();
      }
    }

    /** test delete using a where clause */ 
    public void testDeleteWhere(){
      String where = "where pkey = 5";	
      System.out.println("testing delete " + where);
      try{
        TypeBean.delete(conn, where);
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    
    // get a connection 
    public static Connection getConnection() {
      Connection connection = null;
      try{
        Class.forName(dbDriver).newInstance();
        connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
      } catch (SQLException e) {
        System.out.println("I can't connect using: " + dbUrl);
        e.printStackTrace();
      } catch (IllegalAccessException ex){
        ex.printStackTrace();
      } catch (InstantiationException ex){
        ex.printStackTrace();
      } catch (ClassNotFoundException exc){
        System.out.println("I can't find the class for: " + dbDriver);
        exc.printStackTrace();
      }
      return connection;
    }
    
    // disconnect 
    public static void disconnect(){
      try{
        conn.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    
}

