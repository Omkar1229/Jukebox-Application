package org.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {

	Connection con=null;
	
	public static Connection getConnection() throws SQLException
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/practice","root","root@123");
		return con;
		
	}
}
