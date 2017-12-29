/*
 * TypeMap.java
 * Copyright (c) Brad D Matlack 2002 - 2003
 * License: http://www.gnu.org/gpl
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

package workzen.xgen.model.java;

import java.util.Hashtable;

import workzen.xgen.*;
import workzen.xgen.type.Blob;
import workzen.xgen.type.Clob;
import workzen.xgen.type.Date;
import workzen.xgen.type.Decimal;
import workzen.xgen.type.Str;
import workzen.xgen.type.Time;
import workzen.xgen.type.Timestamp;
import workzen.xgen.type.XGenType;

/**
 * Type maintains a map of TypeMaps, keyed on the SQL types.
 * Used by JavaModelDbLoader to reverse-engineer database types.
 * <p>
 * TODO: all types for all databases. Strategy may be required.
 * </p>
 * @author <a href="mailto:matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class SqlTypeMap {

  private static Hashtable typeMap = null;
  
  /*
   * TypeMap contains String parameters for source code generation:
   * <pre> 
   * className, primitiveName, qualifiedName,  primitiveFunction
   * </pre>
   * Oracle Types: [INTERVALDS, INTERVALYM, TIMESTAMP WITH LOCAL TIME ZONE, TIMESTAMP WITH TIME ZONE, NUMBER, NUMBER, NUMBER, LONG RAW, RAW, LONG, CHAR, NUMBER, NUMBER, NUMBER, FLOAT, REAL, VARCHAR2, DATE, DATE, TIMESTAMP, STRUCT, ARRAY, BLOB, CLOB, REF]
   * JDBC Types: [CHAR, VARCHAR, LONGVARCHAR, NUMERIC, DECIMAL, BIT, TINYINT, SMALLINT, INTEGER, BIGINT, REAL, FLOAT, DOUBLE, BINARY, VARBINARY, LONGVARBINARY, DATE, TIME, TIMESTAMP]
   *
  public static void init(){
    if( typeMap != null )
      return;
    typeMap = new Hashtable();
    typeMap.put("char",    	 new Type("String","string","java.lang.String","") );
    typeMap.put("bpchar",  	 new Type("String","string","java.lang.String","") ); // postgres
    typeMap.put("varchar",   new Type("String","string","java.lang.String","") ); // primitive is intentional!!
    typeMap.put("varchar2",  new Type("String","string","java.lang.String","") ); // primitive is intentional!!
    typeMap.put("longvarchar",new Type("String","string","java.lang.String","") ); // primitive is intentional!!
    typeMap.put("text",		 new Type("String","string","java.lang.String","") ); // primitive is intentional!!
    typeMap.put("numeric",   new Type("BigDecimal","double","java.math.BigDecimal","doubleValue()") );
    typeMap.put("decimal",   new Type("BigDecimal","double","java.math.BigDecimal","doubleValue()") );
    typeMap.put("bit",   	 new Type("Boolean","boolean","java.lang.Boolean","booleanValue()") );
    typeMap.put("bool",   	 new Type("Boolean","boolean","java.lang.Boolean","booleanValue()") );
    typeMap.put("boolean", 	 new Type("Boolean","boolean","java.lang.Boolean","booleanValue()") );
    typeMap.put("int",	     new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("int2",   	 new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("int4",   	 new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("int8",   	 new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("tinyint",   new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("smallint",  new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("integer",   new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("oid", 		 new Type("Long","long","java.lang.Long","longValue()") ); // postgresql testme
    //typeMap.put("number",    new Type("Long","long","java.lang.Long","longValue()") );
    typeMap.put("number",   new Type("Integer","int","java.lang.Integer","intValue()") ); // use int for oracle compatability with postgresql
    typeMap.put("bigint",    new Type("Long","long","java.lang.Long","longValue()") );
    typeMap.put("real",      new Type("Float","float","java.lang.Float","floatValue()") );
    typeMap.put("float",     new Type("Double","double","java.lang.Double","doubleValue()") );
    typeMap.put("float8",    new Type("Double","double","java.lang.Double","doubleValue()") ); // postgresql
    typeMap.put("double",    new Type("Double","double","java.lang.Double","doubleValue()") );
    typeMap.put("binary",    new Type("Byte","byte","java.lang.Byte","byteValue()") );
    typeMap.put("varbinary", new Type("Byte","byte","java.lang.Byte","byteValue()") );
    typeMap.put("longbinary",new Type("Byte","byte","java.lang.Byte","byteValue()") );
    typeMap.put("date",      new Type("Date","date","java.sql.Date","") );
    typeMap.put("time",      new Type("Time","date","java.sql.Time","") );
    typeMap.put("timestamp", new Type("Timestamp","date","java.sql.Timestamp","") );
    typeMap.put("bytea",     new Type("Blob","byte[]","java.sql.Blob","") ); // postgresql
    typeMap.put("blob",      new Type("Blob","byte[]","java.sql.Blob","") );
    typeMap.put("clob",      new Type("Clob","char[]","java.sql.Clob","") );
	//typeMap.put("Object",   new TypeMap("Object","","","") );
    
  }*/
  
  public static void init(){
	    if( typeMap != null )
	      return;
	    typeMap = new Hashtable();
	    typeMap.put("char",    	 new Str() );
	    typeMap.put("bpchar",  	 new Str() ); // postgres
	    typeMap.put("varchar",   new Str() ); 
	    typeMap.put("varchar2",  new Str());
	    typeMap.put("longvarchar",new Str() );
	    typeMap.put("text",		 new Str() ); 
	    typeMap.put("numeric",   new workzen.xgen.type.Long() );
	    typeMap.put("decimal",   new Decimal() );
	    typeMap.put("bit",   	 new workzen.xgen.type.Boolean() );
	    typeMap.put("bool",   	 new workzen.xgen.type.Boolean() );
	    typeMap.put("boolean", 	 new workzen.xgen.type.Boolean() );
	    typeMap.put("int",	     new workzen.xgen.type.Integer() );
	    typeMap.put("int2",   	 new workzen.xgen.type.Integer() );
	    typeMap.put("int4",   	 new workzen.xgen.type.Integer() );
	    typeMap.put("int8",   	 new workzen.xgen.type.Integer() );
	    typeMap.put("tinyint",   new workzen.xgen.type.Integer() );
	    typeMap.put("smallint",  new workzen.xgen.type.Integer() );
	    typeMap.put("integer",   new workzen.xgen.type.Integer() );
	    typeMap.put("oid", 		 new workzen.xgen.type.Long() ); // postgresql testme
	    //typeMap.put("number",    new workzen.xgen.type.Long() );
	    typeMap.put("number",    new workzen.xgen.type.Integer() ); // use int for oracle compatability with postgresql
	    typeMap.put("bigint",    new workzen.xgen.type.Long() );
	    typeMap.put("real",      new workzen.xgen.type.Float() );
	    typeMap.put("float",     new workzen.xgen.type.Double() );
	    typeMap.put("float8",    new workzen.xgen.type.Double() ); // postgresql
	    typeMap.put("double",    new workzen.xgen.type.Double() );
	    typeMap.put("binary",    new workzen.xgen.type.Byte() );
	    typeMap.put("varbinary", new workzen.xgen.type.Byte() );
	    typeMap.put("longbinary",new workzen.xgen.type.Byte() );
	    typeMap.put("date",      new Date() );
	    typeMap.put("time",      new Time() );
	    typeMap.put("timestamp", new Timestamp() );
	    typeMap.put("bytea",     new Blob() ); // postgresql
	    typeMap.put("blob",      new Blob() );
	    typeMap.put("clob",      new Clob() );
	    
	  }

  /** 
   * Get the type using a String key. (key if forced to lowercase)
   */
  public static XGenType getXGenType(String key) throws XGenException{
	init();
    if( key == null )
      throw new XGenException("key is null",null);
    XGenType tm = (XGenType)typeMap.get( key.toLowerCase() ); 
    if( tm == null )
      throw new XGenException("TypeMap not found for: " + key,null);
    return tm;
  }

}  

 
