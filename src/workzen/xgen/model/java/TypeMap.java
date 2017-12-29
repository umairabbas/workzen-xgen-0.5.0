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

import java.math.BigDecimal;
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
 * Type maintains a map of TypeMaps, keyed on the XBean type.
 * @author <a href="mailto:matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class TypeMap {

  private static Hashtable typeMap = null;
  
  /*
   * TypeMap contains String parameters for source code generation:
   * <pre> 
   * className, primitiveName, qualifiedName,  primitiveFunction
   * </pre>
   * The qualifiedName is currently not used in the templates.
   * TypeMap keys are lower case, but the schema definitions
   * are case insensitive.
   *
  public static void init(){
    if( typeMap != null )
      return;
    typeMap = new Hashtable();
    typeMap.put("boolean",   new Type("Boolean","boolean","java.lang.Boolean","booleanValue()") );
    typeMap.put("byte",      new Type("Byte","byte","java.lang.Byte","byteValue()") );
    typeMap.put("short",     new Type("Short","short","java.lang.Short","shortValue()") );
    typeMap.put("integer",   new Type("Integer","int","java.lang.Integer","intValue()") );
    typeMap.put("long",      new Type("Long","long","java.lang.Long","longValue()") );
    typeMap.put("double",    new Type("Double","double","java.lang.Double","doubleValue()") );
    typeMap.put("float",     new Type("Float","float","java.lang.Float","floatValue()") );
    typeMap.put("decimal",   new Type("BigDecimal","double","java.math.BigDecimal","doubleValue()") );
    typeMap.put("string",    new Type("String","string","java.lang.String","") ); // primitive is intentional!!
    typeMap.put("date",      new Type("Date","long","java.sql.Date","") );
    typeMap.put("time",      new Type("Time","long","java.sql.Time","") );
    typeMap.put("timestamp", new Type("Timestamp","long","java.sql.Timestamp","") );
    typeMap.put("blob",     new Type("Blob","byte[]","java.sql.Blob","") );
    typeMap.put("clob",     new Type("Clob","char[]","java.sql.Clob","") );
	//typeMap.put("Object",   new TypeMap("Object","","","") );
  }*/
  
  public static void init(){
    if( typeMap != null )
      return;
    typeMap = new Hashtable();
    typeMap.put("boolean",   new workzen.xgen.type.Boolean() );
    typeMap.put("byte",      new workzen.xgen.type.Byte() );
    typeMap.put("short",     new workzen.xgen.type.Short() );
    typeMap.put("integer",   new workzen.xgen.type.Integer() );
    typeMap.put("long",      new workzen.xgen.type.Long() );
    typeMap.put("double",    new workzen.xgen.type.Double() );
    typeMap.put("float",     new workzen.xgen.type.Float() );
    typeMap.put("decimal",   new Decimal() );
    typeMap.put("string",    new Str() );
    typeMap.put("str",   	 new Str() );
    typeMap.put("date",      new Date() );
    typeMap.put("time",      new Time() );
    typeMap.put("timestamp", new Timestamp() );
    typeMap.put("blob",      new Blob() );
    typeMap.put("clob",      new Clob() );
	//typeMap.put("Object",   new TypeMap("Object","","","") );
  }  

  /** 
   * Get the type using a String key. (key if forced to lowercase)
   */
  public static XGenType getType(String key) throws XGenException{
    init();
    if( key == null )
      throw new XGenException("key is null",null);
    XGenType tm = (XGenType)typeMap.get( key.toLowerCase() ); 
    if( tm == null )
      throw new XGenException("TypeMap not found for: " + key,null);
    return tm;
  }

}  

 
