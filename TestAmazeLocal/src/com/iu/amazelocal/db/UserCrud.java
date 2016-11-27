package com.iu.amazelocal.db;

import java.sql.*;
import java.util.UUID;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.utils.AppConstants;

public class UserCrud {
	public void insertUser(Users u,String pass,String securityQue,String securityAns) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String insertTableSQL = "INSERT INTO AL_USERS " + "VALUES " + "(?,?,?,?,?,?)";
			Long userId=AppConstants.USERIDSEQ;
			PreparedStatement stmt = con.prepareStatement(insertTableSQL);
			Long newUserId=UserCrud.fetchLatestId("AL_USERS", "UserId");
			if(userId!=0L){
				userId=newUserId;
			}
			stmt.setLong(1, userId);
			stmt.setString(2, u.getFirstName());
			stmt.setString(3, u.getLastName());
			stmt.setString(4, u.getEmailAddress());
			stmt.setLong(5, u.getPhoneNUmber());
			stmt.setString(6, u.getMailingAddress());
			stmt.executeUpdate();
			UserCrud.createLogin(userId, u.getEmailAddress(), pass, securityQue,securityAns,con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void createLogin(Long UserId, String uName, String password,String securityQ,String securityAns,Connection con){
		PasswordAuth pass=new PasswordAuth();
		String hashedPass=pass.signup(password);
		String insertLogineSQL = "INSERT INTO AL_LOGIN " + "VALUES " + "(?,?,?,?,?,?)";
		try{
			Long loginId=AppConstants.LOGINIDSEQ;
			Long newLoginId=UserCrud.fetchLatestId("AL_LOGIN", "LoginId");
			if(newLoginId!=0L){
				loginId=newLoginId;
			}
			PreparedStatement stmt = con.prepareStatement(insertLogineSQL);
			stmt.setLong(1, loginId);
			stmt.setLong(2,UserId);
			stmt.setString(3, uName);
			stmt.setString(4, hashedPass);
			stmt.setString(5,securityQ);
			stmt.setString(6, securityAns);
			stmt.executeUpdate();
			
			
			
		}
		catch(SQLException e){
			
		}
	}
	public static void changePassword(String emailId, String password){
		PasswordAuth pass=new PasswordAuth();
		String hashedPass=pass.signup(password);
		Connection con = ConnectionFactory.getConnObject();
		String updatePassSQL = "UPDATE AL_LOGIN SET Password = ? WHERE UserName = ?";
		try{
			PreparedStatement stmt = con.prepareStatement(updatePassSQL);
			stmt.setString(1, hashedPass);
			stmt.setString(2,emailId);
			stmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
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
	public String fetchQuestionFromEmail(String email){
		Connection con = ConnectionFactory.getConnObject();
		String selectPasswordSQL = " SELECT SecurityQuestion FROM AL_LOGIN WHERE UserName= ?";
		
		try{
			PreparedStatement stmt = con.prepareStatement(selectPasswordSQL);
			stmt.setString(1, email);
			ResultSet res=stmt.executeQuery();
			System.out.println("email is"+email);
			if(res.next()){
				String que=res.getString(1);
				System.out.println("question is"+que);
				return que;
			}
			else
				return null;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		}
	public String fetchAnswerFromEmail(String email){
		Connection con = ConnectionFactory.getConnObject();
		String selectPasswordSQL = " SELECT SecurityAnswer FROM AL_LOGIN WHERE UserName= ?";
		
		try{
			PreparedStatement stmt = con.prepareStatement(selectPasswordSQL);
			stmt.setString(1, email);
			ResultSet res=stmt.executeQuery();
			System.out.println("email is"+email);
			if(res.next()){
				String que=res.getString(1);
				System.out.println("question is"+que);
				return que;
			}
			else
				return null;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		}
	public static long fetchLatestId(String tableName,String IdName){
		Connection con = ConnectionFactory.getConnObject();
		String maxIdSQL = " select IFNULL(MAX("+IdName+"),0) from "+tableName;
		try{
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(maxIdSQL);
			rs.next();
				long latestId=rs.getLong(1);
				latestId+=1;
				return latestId;
		}
		catch(Exception e){
			e.printStackTrace();
			return 0L;
		}
		}
	
}