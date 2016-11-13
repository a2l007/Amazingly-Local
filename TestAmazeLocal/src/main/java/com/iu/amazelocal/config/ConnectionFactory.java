package com.iu.amazelocal.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnObject(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amlocal", "root", "luta");
			return con;
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}
		catch(SQLException sqe){
			sqe.printStackTrace();
			return null;
		}
	}
	public void closeConnections(){
		
	}
}