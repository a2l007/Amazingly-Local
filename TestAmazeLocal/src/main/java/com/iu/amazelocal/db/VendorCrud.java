package com.iu.amazelocal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.ProductSaleDao;
import com.iu.amazelocal.models.ProductUser;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.VendorRevenueDao;
import com.iu.amazelocal.models.Vendors;

public class VendorCrud {
	
	public ArrayList<VendorRevenueDao> fetchRevenueReport(){
		Connection con = ConnectionFactory.getConnObject();
		String selectVendorSql = " select CONCAT(MONTHNAME(PayMonth), ' ',YEAR(PayMonth)) as Period ,sum(Profit) from AL_VENDOR_REVENUE "
				+ "WHERE VendorId=2 group by Period;";
		ArrayList<VendorRevenueDao> revenueList=new ArrayList<VendorRevenueDao>(10);
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectVendorSql);
			while(res.next()){
				String payPeriod=res.getString(1);
				float revenue=res.getFloat(2);
				VendorRevenueDao vrd=new VendorRevenueDao(revenue);
				vrd.setPayPeriod(payPeriod);
				revenueList.add(vrd);
			}
				return revenueList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
}
	public ArrayList<VendorRevenueDao> fetchAllVendorRevenueReport(boolean presentMonth){
		Connection con = ConnectionFactory.getConnObject();
		ResultSet res=null;
		ArrayList<VendorRevenueDao> revenueList=new ArrayList<VendorRevenueDao>(10);

		try{
		if(presentMonth){
			 Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		        int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
		        int currentYear = localCalendar.get(Calendar.YEAR);
				String selectUserSql = " select v.VendorName,sum(Profit) from AL_VENDOR_REVENUE vr,AL_VENDORS v "
						+ "WHERE v.vendorId=vr.vendorId AND MONTH(vr.PayMonth)= ? "
						+ "AND YEAR(vr.PayMonth)= ? group by vr.VendorId";
				PreparedStatement stmt = con.prepareStatement(selectUserSql);
				stmt.setInt(1, currentMonth);
				stmt.setInt(2, currentYear);
				res=stmt.executeQuery();      
		}
		else{
			String selectUserSql = "select v.VendorName,sum(Profit) "
					+ "from AL_VENDOR_REVENUE vr,AL_VENDORS v WHERE v.vendorId=vr.vendorId group by vr.VendorId";
			Statement stmt = con.createStatement();
			res=stmt.executeQuery(selectUserSql);
		}
		
			while(res.next()){
				String vendorName=res.getString(1);
				float revenue=res.getFloat(2);
				VendorRevenueDao vrd=new VendorRevenueDao(revenue);
				vrd.setVendorName(vendorName);
				revenueList.add(vrd);
			}
				return revenueList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
}
	public ArrayList<VendorRevenueDao> fetchRevenueReportById(String type){
		Connection con = ConnectionFactory.getConnObject();
		ResultSet res=null;
		ArrayList<VendorRevenueDao> revenueList=new ArrayList<VendorRevenueDao>(10);

		try{
		if(type.equals("Month")){
			 Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		        int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
		        int currentYear = localCalendar.get(Calendar.YEAR);
				String selectUserSql = "select concat(MONTHNAME(PayMonth),' ',YEAR(PayMonth)), SUM(Profit) "
						+ "from AL_VENDOR_REVENUE where vendorid=? group by MONTH(PayMonth);";
				PreparedStatement stmt = con.prepareStatement(selectUserSql);
				stmt.setInt(1, 3);
				res=stmt.executeQuery();      
		}
		else{
			String selectUserSql = "select YEAR(PayMonth), SUM(Profit) "
					+ "from AL_VENDOR_REVENUE where vendorid=? group by YEAR(PayMonth)";
			PreparedStatement stmt = con.prepareStatement(selectUserSql);
			stmt.setInt(1, 3);
			res=stmt.executeQuery();      
		}
		
			while(res.next()){
				String period=res.getString(1);
				float revenue=res.getFloat(2);
				VendorRevenueDao vrd=new VendorRevenueDao(revenue);
				vrd.setPayPeriod(period);
				revenueList.add(vrd);
			}
				return revenueList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
}
	public ArrayList<ProductSaleDao> fetchProductSaleReport(){
		Connection con = ConnectionFactory.getConnObject();
		String selectVendorSql = "SELECT ProductName,UnitSold FROM AL_INVENTORY WHERE VendorId=2";
		ArrayList<ProductSaleDao> saleList=new ArrayList<ProductSaleDao>(10);
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectVendorSql);
			while(res.next()){
				String productName=res.getString(1);
				int saleCount=res.getInt(2);
				ProductSaleDao psd=new ProductSaleDao(productName,saleCount);
				saleList.add(psd);
			}
				return saleList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
}
	
	public ArrayList<ProductUser> fetchUserProductReport(){
		Connection con = ConnectionFactory.getConnObject();
		String selectVendorSql = " select ai.ProductName,oh.UserId from AL_ORDER_HISTORY oh, AL_SHOP_CART sc, AL_INVENTORY ai "
				+ "WHERE oh.CartId=sc.CartId AND sc.InventoryId=ai.InventoryId AND oh.VendorId=2 order by ai.ProductName";
		ArrayList<ProductUser> usageList=new ArrayList<ProductUser>(10);
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectVendorSql);
			while(res.next()){
				String productName=res.getString(1);
				int userId=res.getInt(2);
				ProductUser vrd=new ProductUser(productName,userId);
				usageList.add(vrd);
			}
				return usageList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
}
	public ArrayList<Vendors> fetchVendorList(){
		Connection con = ConnectionFactory.getConnObject();
		String selectVendorSQL = " select VendorId, VendorName, FarmAddress,MailingAddress,VendorRating,EmailAddress,PhoneNumber,Revenue "
				+ "from AL_VENDORS";
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectVendorSQL);
			ArrayList<Vendors> vendorList=new ArrayList<Vendors>();
			while (res.next()){
				long vendorId=res.getLong("VendorId");
				String vendorName=res.getString("VendorName");
				String farmAddress=res.getString("FarmAddress");
				String mailAddress=res.getString("MailingAddress");
				float rating=res.getFloat("VendorRating");
				String emailAddress=res.getString("EmailAddress");
				long phone=res.getLong("PhoneNumber");
				float revenue=res.getFloat("Revenue");
				Vendors vendor=new Vendors(vendorName, emailAddress, phone, mailAddress, farmAddress);
				vendor.setVendorId(vendorId);
				vendor.setRevenue(revenue);
				vendorList.add(vendor);
			}
			return vendorList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	}
//	public ArrayList<Integer> fetchVendorIds(String vendorName){
//		Connection con = ConnectionFactory.getConnObject();
//		String selectVendorSql = " SELECT VendorId FROM AL_VENDORS WHERE VendorName= ?";
//		
//		try{
//			PreparedStatement stmt = con.prepareStatement(selectVendorSql);
//			stmt.setString(1, vendorName);
//			ResultSet res=stmt.executeQuery();
//			
//
//		}
//		catch(SQLException e){
//			e.printStackTrace();
//			return null;
//		}
//		}

