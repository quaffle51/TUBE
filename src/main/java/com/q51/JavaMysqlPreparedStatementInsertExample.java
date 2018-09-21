package com.q51;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

/**
 * A Java MySQL PreparedStatement INSERT example. Demonstrates the use of a SQL
 * INSERT statement against a MySQL database, called from a Java program, using
 * a Java PreparedStatement.
 * 
 * Created by Alvin Alexander, http://alvinalexander.com
 */
public class JavaMysqlPreparedStatementInsertExample {

	public static void main(String[] args) {
		try {
			// create a mysql database connection
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/ULA?autoReconnect=true&useSSL=false";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "q51", "q+64vethU8%hfg");

			// create a sql date object so we can use it in our INSERT statement
			Calendar calendar = Calendar.getInstance();
			java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

			// the mysql insert statement
//			String query = " insert into users (first_name, last_name, date_created, is_admin, num_points) values (?, ?, ?, ?, ?)";
			String insertSql = " insert into tiles (location, symbol) values (?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(insertSql);
			

			
			int column = 1;
			preparedStmt.setString(column++, "0000_0000_0001");
			preparedStmt.setString(column++, "0");
			preparedStmt.execute();
			
			column = 1;
			preparedStmt.setString(column++, "0000_0000_0002");
			preparedStmt.setString(column++, "0");
			preparedStmt.execute();
			
			column = 1;
			preparedStmt.setString(column++, "0000_0000_0003");
			preparedStmt.setString(column++, "0");
			preparedStmt.execute();
			
			column = 1;
			preparedStmt.setString(column++, "0000_0000_0004");
			preparedStmt.setString(column++, "0");
			preparedStmt.execute();


			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
}
