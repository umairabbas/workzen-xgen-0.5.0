/*
 * Copyright (c) Nov 18, 2005 - All rights reserved. 
 * This software protected by the license provided with the distribution.
 */
package workzen.xgen.loader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import workzen.common.db.ColumnMetaData;
import workzen.common.db.DbMetaData;
import workzen.common.db.TableMetaData;
import workzen.common.util.DbUtil;
import workzen.common.util.StringUtil;
import workzen.xgen.XGenException;
import workzen.xgen.model.java.Column;
import workzen.xgen.model.java.Field;
import workzen.xgen.model.java.JavaClass;
import workzen.xgen.model.java.Package;
import workzen.xgen.model.java.SqlTypeMap;
//import workzen.xgen.model.java.Type;
import workzen.xgen.type.XGenType;
import workzen.xgen.util.PropertyUtil;

/**
 * This loader connects to a database and loads JDBC metadata into the
 * SchemaModel <br>
 * <b>Properties</b> <br>
 * 
 * <pre>
 * dbDriver   = org.gjt.mm.mysql.Driver
 * dbUrl      = jdbc:mysql://localhost:3306/test
 * dbUsername = root
 * dbPassword = green
 * dbSchema = test
 * dbTables = employee,department,project
 * javaPackage = my.package
 * </pre>
 * 
 * <br>
 * 
 * @author brad.matlack
 */
public class JavaModelDbLoader implements ILoader {

	public static final String DB_DRIVER = "dbDriver";

	public static final String DB_URL = "dbUrl";

	public static final String DB_USERNAME = "dbUsername";

	public static final String DB_PASSWORD = "dbPassword";

	public static final String DB_SCHEMA = "dbSchema";

	public static final String DB_TABLES = "dbTables";

	public static final String JAVA_PACKAGE = "javaPackage";

	private Properties properties = new Properties();

	private String dbKey = "db";

	private String dbSchema = "undefined";

	private String javaPackage = "undefined";

	private String[] dbTables;

	private Logger logger = Logger.getLogger(JavaModelDbLoader.class);

	/**
	 * @see workzen.xgen.loader.ILoader#configure(java.util.Map)
	 */
	public void configure(Map map) throws XGenException {
		// System.out.println("MAP: " + map);
		this.properties.put("db.driver", PropertyUtil.getRequired(map,
				DB_DRIVER));
		this.properties.put("db.url", PropertyUtil.getRequired(map, DB_URL));
		this.properties.put("db.username", PropertyUtil.getRequired(map,
				DB_USERNAME));
		this.properties.put("db.password", PropertyUtil.getRequired(map,
				DB_PASSWORD));
		// this.properties.put("db.password", "");
		System.out.println("PROPERTIES: " + properties);

		this.dbSchema = PropertyUtil.getRequired(map, DB_SCHEMA);
		this.javaPackage = PropertyUtil.getRequired(map, JAVA_PACKAGE);
		String tables = PropertyUtil.getOptional(map, DB_TABLES, null);
		if (tables != null) {
			dbTables = tables.split(",");
		}
	}

	/**
	 * 
	 */
	private List<TableMetaData> loadTableMetaData() throws XGenException {
		logger.debug("loadTableMetaData()");

		Connection conn = null;
		List<TableMetaData> tmd = null;
		try {
			conn = DbUtil.getConnection(properties, dbKey);
			DbMetaData dbmd = new DbMetaData(conn);
			if (dbTables == null) {
				List<String> tables = dbmd.getTablesBySchema(dbSchema, conn);
				logger.debug(tables);
				tmd = dbmd.getTableMetaData(tables, conn);
			} else {
				tmd = dbmd.getTableMetaData(dbTables, conn);
			}
		} catch (Throwable t) {
			throw new XGenException("error loading properties", t);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tmd;
	}

	/**
	 * Load the document into the Package model. Return the Package as a generic
	 * object for use by Velocity.
	 * 
	 */
	public Object loadModel() throws XGenException {
		logger.debug("loadModel()");

		List<TableMetaData> mdList = loadTableMetaData();
		if (mdList == null) {
			throw new XGenException("Error loading table metadata", null);
		}

		Package pkg = new Package();

		pkg.setName(javaPackage);
		logger.debug("loading package:" + pkg.getName());

		Iterator it = mdList.iterator();

		// iterate through each table
		while (it.hasNext()) {
			TableMetaData tmd = (TableMetaData) it.next();
			JavaClass bean = new JavaClass();
			bean.setName(convertTableNameToJavaname(tmd.getTablename()));
			logger.debug("loading bean:" + bean.getName());

			bean.setTableName(tmd.getTablename());
			bean.setHandleConcurrency(handleConcurrency(tmd)); // false
			bean.setHandleAutoIncrement(handleAutoIncrement(tmd)); // false
			
			// bean.setAuthor(beanEl.getAttributeValue("author"));
			// bean.setDescription(beanEl.getAttributeValue("description"));
			// bean.setImports(beanEl.getAttributeValue("import"));
			// bean.setSuperclass(beanEl.getAttributeValue("superclass"));
			
			pkg.add(bean);

			// fields
			List columns = tmd.getColumnMetaData();
			Iterator fit = columns.iterator();
			while (fit.hasNext()) {
				ColumnMetaData cmd = (ColumnMetaData) fit.next();
				Field field = loadField(cmd);
				bean.addField(field);

				if (field.isPrimaryKey()) {
					bean.setPrimaryKey(field);
				}
				if (field.isTransactionKey()) {
					bean.setTransactionKey(field);
				}
			}
			/*
			 * // references List references = beanEl.getChildren("reference");
			 * Iterator rit = references.iterator(); while (rit.hasNext()) {
			 * Reference ref = loadReference((Element) rit.next());
			 * bean.addReference(ref); } // collections List collections =
			 * beanEl.getChildren("collection"); Iterator cit =
			 * collections.iterator(); while (cit.hasNext()) {
			 * workzen.xgen.model.java.Collection col = loadCollection((Element)
			 * cit.next()); logger.debug("collection: " + col);
			 * bean.addCollection(col); }
			 */
			checkPrimaryKey(bean);
		}
		return pkg;
	}

	/**
	 * Convert "workgroup_member" to WorkgroupMember
	 * @param tablename
	 * @return
	 */
	public String convertTableNameToJavaname(String tablename) {
		// logger.debug("converting tablename: " + tablename);
		tablename = tablename.toLowerCase();

		String[] str = tablename.split("_");
		for (int i = 0; i < str.length; i++) {
			str[i] = StringUtil.capitalize(str[i]);
		}
		String javaClassName = StringUtil.join(str, "");
		// logger.debug("javaClassName " + javaClassName);
		return javaClassName;
	}

	/**
	 * Convert "member_id" to memberId
	 * @param tablename
	 * @return
	 */
	public String convertColumnNameToJavaname(String tablename) {
		// logger.debug("converting tablename: " + tablename);
		tablename = tablename.toLowerCase();

		String[] str = tablename.split("_");
		for (int i = 0; i < str.length; i++) {
			if (i > 0) {// skip first letter
				str[i] = StringUtil.capitalize(str[i]);
			}
		}
		String javaClassName = StringUtil.join(str, "");
		// logger.debug("javaClassName " + javaClassName);
		return javaClassName;
	}

	private boolean handleConcurrency(TableMetaData tmd) {
		return false;
	}

	private boolean handleAutoIncrement(TableMetaData tmd) {
		return true;
	}

	/**
	 * Make sure the class has a primary key
	 * 
	 * @param javaClass
	 */
	private void checkPrimaryKey(JavaClass javaClass) {
		logger.debug("checkPrimaryKey()");
		Field field = javaClass.getPrimaryKey();
		if (field == null) {
			logger.error("NULL primary key for java class: "
					+ javaClass.getName());
			return;
		}
		if (!field.isPrimaryKey()) {
			logger.error("Field is NOT a primary key: " + field.getName());
		}
	}

	private Field loadField(ColumnMetaData cmd) throws XGenException {

		Field field = new Field();
		String fieldName = convertColumnNameToJavaname(cmd.getName());
		field.setName(fieldName);
		// JDBC prepared statements take primitives 
		//field.setIsPrimitive(true);
		
		// Autoboxing primitives always returns 0, causing referential integrity
		// problems with nullable keys
		field.setIsPrimitive(false);

		logger.debug("loading field:" + field.getName());

		String typename = cmd.getType();
		if (typename != null) {
			XGenType type = SqlTypeMap.getXGenType(typename);
			field.setType(type);
		}

		Column col = new Column();

		col.setName(cmd.getName());
		col.setIsPrimaryKey(cmd.isPrimaryKey());
		col.setIsForeignKey(false);
		col.setIsTransactionKey(false);
		// col.setIsAutoincrement(false);
		col.setIsNull(cmd.isNullable());
		col.setSize(col.getSize());
		col.setPrecision(col.getPrecision());
		col.setScale(col.getScale());
		// col.setForeignTable();

		logger.debug(col);
		logger.debug(field);

		field.setColumn(col);

		return field;
	}

}
