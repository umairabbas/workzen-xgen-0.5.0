package workzen.xgen.test.loader;

import java.sql.SQLException;
import java.sql.*;
import java.sql.DriverManager;
import java.io.*;

/**
 * http://www.devx.com/dbzone/Article/27992#codeitemarea
 */
public class MetaDataStudy {
	public static void main(String args[]) {
		try {
			// load the DB2 Driver
			Class.forName("com.ibm.db2.jcc.DB2Driver");

			// establish a connection to DB2
			Connection connection = DriverManager.getConnection(
					"jdbc:db2://localhost:50000/EMPDB", "db2admin", "db2admin");

			// gather DatabaseMetaData from connection
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			// gather database product and version info from DatabaseMetaData
			String databaseProductName = databaseMetaData
					.getDatabaseProductName();
			String databaseProductVersion = databaseMetaData
					.getDatabaseProductVersion();
			System.out.println("------------------------------");
			System.out.println("Database Product Name: " + databaseProductName);
			System.out.println("Database Product Version: "
					+ databaseProductVersion);

			System.out.println("------------------------------");
			// gather max number of chars for the names of tables and columns
			int maximumColumnNameChars = databaseMetaData
					.getMaxColumnNameLength();
			System.out.println("Max number of chars for a column name: "
					+ maximumColumnNameChars);

			int maximumTableNameChars = databaseMetaData
					.getMaxTableNameLength();
			System.out.println("Max number of chars for a table name: "
					+ maximumTableNameChars);

			System.out.println("------------------------------");
			// report the available database types for the database being used
			ResultSet databaseTypes = databaseMetaData.getTypeInfo();
			System.out.println("Supported Database Types:");
			while (databaseTypes.next()) {
				String typeName = databaseTypes.getString("TYPE_NAME");
				System.out.println(typeName);
			}

			System.out.println("------------------------------");
			System.out.println("Support for batch updates: "
					+ databaseMetaData.supportsBatchUpdates());

			System.out.println("------------------------------");
			// report the primary key(s) for the EMPLOYEE table
			ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null,
					"EMPLOYEE");
			while (primaryKeys.next()) {
				String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
				System.out.println("Primary Key Column: " + primaryKeyColumn);
			}

			// query EMPLOYEE table and report information about column
			// structure

			System.out.println("------------------------------");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select * from EMPLOYEE");
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int numColumns = resultSetMetaData.getColumnCount();
			for (int i = 1; i < (numColumns + 1); i++) {
				String columnName = resultSetMetaData.getColumnName(i);
				String columnTypeName = resultSetMetaData.getColumnTypeName(i);
				int columnPrecision = resultSetMetaData.getPrecision(i);
				int columnScale = resultSetMetaData.getScale(i);
				System.out.println(columnName + ": " + columnTypeName + " - "
						+ " Precision: " + columnPrecision + ", " + " Scale: "
						+ columnScale);

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
