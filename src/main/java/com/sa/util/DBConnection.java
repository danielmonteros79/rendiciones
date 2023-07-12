package com.sa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;


public class DBConnection extends TestCase{
	private final static String userName = "admin";
	private final static String password = "";
	private final static String dataBase = "SUMINISTROS";
	private static final Log log = LogFactory
	.getLog(DBConnection.class);
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.sybase.jdbc2.jdbc.SybDriver");
	    Connection conn = null;
//	    Properties connectionProps = new Properties();
//	    connectionProps.put("user", this.userName);
//	    connectionProps.put("password", this.password);

        conn = DriverManager.getConnection("jdbc:sybase:Tds:192.168.4.190:5000 (sa)/" +dataBase, DBConnection.userName, DBConnection.password);
	   log.info("Connected to database");
	    return conn;
	}
	
	  public static void printSQLException(SQLException ex) {
		  	
	      if (ex instanceof SQLException) {
	          ex.printStackTrace(System.err);
	         log.error("SQLState: " + ((SQLException)ex).getSQLState());
	         log.error("Error Code: " + ((SQLException)ex).getErrorCode());
	         log.error("Message: " + ex.getMessage());
	          Throwable t = ex.getCause();
	          while(t != null) {
	              log.error("Cause: " + t);
	              t = t.getCause();
	          }
	      }
	  }
}
