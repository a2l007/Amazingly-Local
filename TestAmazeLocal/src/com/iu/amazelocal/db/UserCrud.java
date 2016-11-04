package com.iu.amazelocal.db;

import java.sql.*;
import java.util.UUID;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.utils.AppConstants;

public class UserCrud {
	public void insertUser(Users u,String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String insertTableSQL = "INSERT INTO AL_USERS " + "VALUES " + "(?,?,?,?,?,?)";
			Long userId=AppConstants.USERIDSEQ;
			PreparedStatement stmt = con.prepareStatement(insertTableSQL);
			stmt.setLong(1, userId);
			stmt.setString(2, u.getFirstName());
			stmt.setString(3, u.getLastName());
			stmt.setString(4, u.getEmailAddress());
			stmt.setLong(5, u.getPhoneNUmber());
			stmt.setString(6, u.getMailingAddress());
			stmt.executeUpdate();
			UserCrud.createLogin(userId, u.getEmailAddress(), pass, con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void createLogin(Long UserId, String uName, String password,Connection con){
		PasswordAuth pass=new PasswordAuth();
		String hashedPass=pass.signup(password);
		String insertLogineSQL = "INSERT INTO AL_LOGIN " + "VALUES " + "(?,?,?,?)";
		try{
			PreparedStatement stmt = con.prepareStatement(insertLogineSQL);
			stmt.setInt(1, AppConstants.LOGINIDSEQ);
			stmt.setLong(2,UserId);
			stmt.setString(3, uName);
			stmt.setString(4, hashedPass);
			stmt.executeUpdate();
			
			
			
		}
		catch(SQLException e){
			
		}
	}
	public String fetchPasswordfromEmail(String emailAddress){
		Connection con = ConnectionFactory.getConnObject();
		String selectPasswordSQL = " SELECT Password FROM AL_LOGIN WHERE UserName= ?";
		
		try{
			PreparedStatement stmt = con.prepareStatement(selectPasswordSQL);
			stmt.setString(1, emailAddress);
			ResultSet res=stmt.executeQuery();
			System.out.println("EMail is"+emailAddress);
			if(res.next()){
				System.out.println("Jhere");
				String pass=res.getString(1);
				return pass;
			}
			else
				return null;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
}