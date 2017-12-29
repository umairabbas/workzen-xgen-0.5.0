/*
 * LobTest.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft/gpl.html
 */

package workzen.xgen.test.persistence;
  
import java.sql.*;
import java.io.*;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Properties;
import java.util.GregorianCalendar;
import java.util.Calendar;

//import com.workzen.util.Timer;


/**
 * This is the original test for the LobBean. It provides more
 * output and is easier to debug that the unit test.
 * <br>
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 * @version $Id: DbMap.java $
 */
public class LobTest 
{
    
    String baseDir = "D:/Project/workzen.xgen/src/workzen/xgen/test/persistence/lobs";
    String dbUrl = "";
    String dbDriver = "";
    String dbUsername = "";
    String dbPassword = "";
    //String blobInputFile = baseDir + "/powered-by-logo.gif";
    //String blobOutputFile = baseDir + "/powered-by-logo-out.gif";
    String blobInputFile = baseDir + "/test.doc";
    String blobOutputFile = baseDir + "/test_out.doc";
    String clobInputFile = baseDir + "/index.html";
    String clobOutputFile = baseDir + "/index_out.html";
      
    Connection conn = null;

    /**
     * @param args optional property filename 
     * default = db.properties
     */
    public static void main (String args[]) throws Exception {
      
      String usage = "usage: Test [propertyfile] ";  
      String propertyFile = "db.properties";
      
      if(args.length > 0){
        if(args[0].equals("help")){
          System.out.println(usage);
          System.exit(1);
        }	else{
          propertyFile = args[0];
        }
      }
      LobTest test = new LobTest(propertyFile);
      test.run();
      
    }
    
    /** constructor */	
    public LobTest(String propertyFile){
      try{
        Properties properties = new Properties();
        properties.load( new FileInputStream( propertyFile )  );
        this.dbUrl = properties.getProperty("db.url");
        this.dbDriver = properties.getProperty("db.driver");
        this.dbUsername = properties.getProperty("db.username");
        this.dbPassword = properties.getProperty("db.password");
      }catch(IOException e){
        e.printStackTrace();
      }
    }
    
    /** run the test */
    public void run(){
      int key;
      try{
        conn = getConnection();
        
        conn.setAutoCommit(false);
        key = testPersist();

        //key = testPersistBlob();
        //key = testPersistClob();

        key = testPersistFileToBlob();
        testLoadBlobAndWriteToDisk(key);
        
        key = testPersistFileToClob();
        testLoadClobAndWriteToDisk(key);

        printAll("");
        conn.commit();
        conn.setAutoCommit(true);
        disconnect();
      }catch(Exception e){
        e.printStackTrace();
      }
    }
        
    /* persist with automatic key generation */
    public int testPersist(){
      System.out.println("testing automatic key generation");
      LobTypeBean test = new LobTypeBean();
      try{        
        test.persist(conn);			
      }catch(SQLException e){
        e.printStackTrace();
      }
      return test.getPKey().intValue();
    }
    
    /* persist a file */
    public int testPersistFileToBlob(){
      System.out.println("testing persisting a file to blob");
      LobTypeBean test = new LobTypeBean();
      try{        
        //java.io.FileInputStream fis = new java.io.FileInputStream(testInputFile);
        java.io.File file = new java.io.File(blobInputFile);
        test.setBlobType(file);
        test.persist(conn);			
      }catch(SQLException e){
        e.printStackTrace();
      }catch(Exception ex){
        ex.printStackTrace();
      }
      return test.getPKey().intValue();
    }
    
    /* persist a file */
    public int testPersistFileToClob(){
      System.out.println("testing persisting a file to clob");
      LobTypeBean test = new LobTypeBean();
      try{        
        java.io.File file = new java.io.File(clobInputFile);
        test.setClobType(file);
        //System.out.println(test);
        test.persist(conn);			
      }catch(SQLException e){
        e.printStackTrace();
      }catch(Exception ex){
        ex.printStackTrace();
      }
      return test.getPKey().intValue();
    }
    
    /* load the blob and write to disk */
    public void testLoadBlobAndWriteToDisk(int key){
      System.out.println("testing loading the blob and writing to disk");
      try{        
        Vector beans = LobTypeBean.load(conn, "where pkey = " + key);
        LobTypeBean bean = (LobTypeBean)beans.firstElement();
        java.io.FileOutputStream fos = new java.io.FileOutputStream(blobOutputFile);
        //System.out.println( new String(bean.getBlobType()) );
        fos.write( bean.getBlobType() );			
        fos.close();
      }catch(SQLException e){
        e.printStackTrace();
      }catch(java.io.IOException ex){
        ex.printStackTrace();
      }
    }
    
    /* load the clob and write to disk */
    public void testLoadClobAndWriteToDisk(int key){
      System.out.println("testing loading the clob and writing to disk");
      try{        
        Vector beans = LobTypeBean.load(conn, "where pkey = " + key);
        LobTypeBean bean = (LobTypeBean)beans.firstElement();
        java.io.FileWriter writer = new java.io.FileWriter(clobOutputFile);
        //System.out.println( new String(bean.getBlobType()) );
        writer.write( bean.getClobType() );			
        writer.close();
      }catch(SQLException e){
        e.printStackTrace();
      }catch(java.io.IOException ex){
        ex.printStackTrace();
      }
    }
    
  
    /* persist with automatic key generation */
    public int testPersistBlob(){
      System.out.println("testing blob persistence");
      LobTypeBean test = new LobTypeBean();
      try{
        test.setBlobType("&#65&#66 2 testing array persistence");
        test.persist(conn);			
      }catch(SQLException e){
        e.printStackTrace();
      }
      return test.getPKey().intValue();
    }
    
     /* persist with automatic key generation */
    public int testPersistClob(){
      System.out.println("testing clob persistence");
      LobTypeBean test = new LobTypeBean();
      try{
        test.setClobType("some chars for CLOBTYPE");
        //conn.setAutoCommit(false);
        test.persist(conn);		
        //conn.commit();
        //conn.setAutoCommit(true);
      }catch(SQLException e){
        e.printStackTrace();
      }
      return test.getPKey().intValue();
    }
    
    /** print all where */
    public void printAll(String where){
      System.out.println("printing: " + where);
      try{
        Vector beans = LobTypeBean.load(conn, where);
        if(beans.size() == 0)
          System.out.println("Records not found: " + where);
        else
          printVector(beans);
      }catch(Exception e){
        e.printStackTrace();
      }
    }

    /** print a vector */
    public void printVector(Vector vect){
      //System.out.println("printing " + vect.size() + " records...");
      Enumeration e = vect.elements();
      while( e.hasMoreElements() ){
        LobTypeBean test = (LobTypeBean)e.nextElement();
        System.out.println(test.toString());
      }
    }
    
    /** get a connection */
    private Connection getConnection() {
      Connection connection = null;
      try{
        Class.forName(dbDriver).newInstance();
        connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (IllegalAccessException ex){
        ex.printStackTrace();
      } catch (InstantiationException ex){
        ex.printStackTrace();
      } catch (ClassNotFoundException exc){
        exc.printStackTrace();
      }
      return connection;
    }
    
    /** disconnect */
    private void disconnect(){
      try{
        conn.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
      

}

