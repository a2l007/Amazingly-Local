package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.SaleApprovalGrid;
import com.iu.amazelocal.models.UserRegisterDao;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.VendorRevenueDao;
import com.iu.amazelocal.models.Vendors;
import com.iu.amazelocal.utils.AppConstants;

public class UserCrud {
	public void insertCustomer(Users u,String pass,String securityQue,String securityAns) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String insertTableSQL = "INSERT INTO AL_USERS (UserId,FirstName,LastName,EmailAddress,PhoneNumber,MailingAddress)" 
			+ " VALUES " + "(?,?,?,?,?,?)";
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
			UserCrud.createLogin(userId, u.getEmailAddress(), pass, securityQue,securityAns,con,AppConstants.CUSTOMERTYPE);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void insertVendor(Vendors v,String pass,String securityQue,String securityAns) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String insertTableSQL = "INSERT INTO AL_VENDORS " + "VALUES " + "(?,?,?,?,?,?,?)";
			Long vendorId=AppConstants.USERIDSEQ;
			PreparedStatement stmt = con.prepareStatement(insertTableSQL);
			Long newUserId=UserCrud.fetchLatestId("AL_VENDORS", "VendorId");
			if(vendorId!=0L){
				vendorId=newUserId;
			}
			stmt.setLong(1, vendorId);
			stmt.setString(2, v.getVendorName());
			stmt.setString(3, v.getFarmAddress());
			stmt.setString(4, v.getMailingAddress());
			stmt.setFloat(5, 0);
			stmt.setString(6, v.getEmailAddress());
			stmt.setLong(7, v.getPhoneNumber());
			stmt.executeUpdate();
			
			UserCrud.createLogin(vendorId, v.getEmailAddress(), pass, securityQue,securityAns,con,AppConstants.VENDORTYPE);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void createLogin(Long UserId, String uName, String password,String securityQ,String securityAns,Connection con, String userType){
		PasswordAuth pass=new PasswordAuth();
		String hashedPass=pass.signup(password);
		String insertLogineSQL = "INSERT INTO AL_LOGIN " + "VALUES " + "(?,?,?,?,?,?,?)";
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
			stmt.setString(7, userType);
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
	public String getUserTypeFromEmail(String email){
		Connection con = ConnectionFactory.getConnObject();
		String selectPasswordSQL = " SELECT UserType FROM AL_LOGIN WHERE UserName= ?";
		
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
	
	public long getUserIdFromEmail(String email){
		Connection con = ConnectionFactory.getConnObject();
		String selectPasswordSQL = " SELECT UserId FROM AL_LOGIN WHERE UserName= ?";
		
		try{
			PreparedStatement stmt = con.prepareStatement(selectPasswordSQL);
			stmt.setString(1, email);
			ResultSet res=stmt.executeQuery();
			System.out.println("email is"+email);
			if(res.next()){
				long que=res.getLong(1);
				System.out.println("question is"+que);
				return que;
			}
			else
				return 0;

		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		}
	public ArrayList<Users> fetchUserList(){
		Connection con = ConnectionFactory.getConnObject();
		String selectUserSQL = " select UserId,FirstName,LastName,EmailAddress,PhoneNumber,MailingAddress from AL_USERS";
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectUserSQL);
			ArrayList<Users> userList=new ArrayList<Users>();
			while (res.next()){
				long userId=res.getLong("UserId");
				String firstName=res.getString("FirstName");
				String lastName=res.getString("LastName");
				String email=res.getString("EmailAddress");
				int phone=res.getInt("PhoneNumber");
				String address=res.getString("MailingAddress");
				Users usr=new Users(firstName, lastName, email, phone, address);
				usr.setUserId(userId);
				userList.add(usr);
			}
			return userList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<UserRegisterDao> fetchUserRegReport(String type){
		Connection con = ConnectionFactory.getConnObject();
		String selectUserSql=null;
		if(type.equals("Month")){
			selectUserSql = " select CONCAT(MONTHNAME(creation_date), ' ',YEAR(creation_date)) AS RegMonth,count(UserId) FROM AL_USERS "
				+ "WHERE MONTH(creation_date)<>0 group by RegMonth";
		}
		else{
			selectUserSql="select YEAR(creation_date) AS RegYear,count(UserId) from AL_USERS "
					+ "where YEAR(creation_date)<>0 group by RegYear";
		}
		ArrayList<UserRegisterDao> registerList=new ArrayList<UserRegisterDao>(10);
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectUserSql);
			while(res.next()){
				String userId=res.getString(1);
				int count=res.getInt(2);
				UserRegisterDao vrd=new UserRegisterDao(userId,count);
				registerList.add(vrd);
			}
				return registerList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
}
	public int fetchUserCount(boolean presentMonth){
		Connection con = ConnectionFactory.getConnObject();
		int userCount=0;
		ResultSet res=null;
		try{
		if(presentMonth){
			 Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		        int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
		        int currentYear = localCalendar.get(Calendar.YEAR);
				String selectUserSql = " select count(UserId) FROM AL_USERS "
						+ "WHERE MONTH(creation_date)=? AND YEAR(creation_date)=? ";
				PreparedStatement stmt = con.prepareStatement(selectUserSql);
				stmt.setInt(1, currentMonth);
				stmt.setInt(2, currentYear);
				res=stmt.executeQuery();      
		}
		else{
			String selectUserSql = " select count(UserId) FROM AL_USERS";
			Statement stmt = con.createStatement();
			res=stmt.executeQuery(selectUserSql);
		}
			if(res.next()){
				userCount=res.getInt(1);
			}
				return userCount;
		}
		catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
		
}
	public Users getUserDetails(long userId){
        Connection con = ConnectionFactory.getConnObject();
        String selectPasswordSQL = " SELECT * FROM AL_USERS WHERE UserId= ?";

        Users buyer = new Users();
        try{
            PreparedStatement stmt = con.prepareStatement(selectPasswordSQL);
            stmt.setLong(1, userId);
            ResultSet res=stmt.executeQuery();
            
            if(res.next()){
                buyer.setFirstName(res.getString(2));
                buyer.setLastName(res.getString(3));
                buyer.setEmailAddress(res.getString(4));
                buyer.setPhoneNUmber(Long.parseLong(res.getString(5)));
                buyer.setMailingAddress(res.getString(6));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return buyer;
    }
}