package com.iu.amazelocal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.ProductSaleDao;
import com.iu.amazelocal.models.ProductUser;
import com.iu.amazelocal.models.VendorRevenueDao;

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
				String vendId=res.getString(1);
				float revenue=res.getFloat(2);
				VendorRevenueDao vrd=new VendorRevenueDao(vendId,revenue);
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

