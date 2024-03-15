package com.fmt;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
import org.apache.logging.log4j.*;

/*
 * This is a class for connector to connect to database using JDBC principles.
 */
public class ConnectionFactory {
	private static Logger log = LogManager.getLogger(ConnectionFactory.class);

	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String USERNAME = "ssubedi";
	public static final String PASSWORD = "1FWWZNeP";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;
 
	public static Connection Connection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ConnectionFactory.URL, ConnectionFactory.USERNAME,
					ConnectionFactory.PASSWORD);
		} catch (SQLException e) {
			log.error("Error Connecting to the database");;
		}
		return conn;
	}
 
	// Closes the connection.
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}