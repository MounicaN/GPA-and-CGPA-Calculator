package com.college.student.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static Connection getDBConnection()
	{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn  = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","mouniora");
			return conn;
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Driver Not Found");

		}
		catch(SQLException e)
		{
			System.out.println("Could not connect to database");
			e.printStackTrace();

		}
		return null;

	}
}

