package com.iu.amazelocal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.models.InventoryMini;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.utils.AppConstants;

public class ProductTypeCrud {
	public HashMap<String,ArrayList<String>> fetchProductTypeMap() {
		HashMap<String,ArrayList<String>> typeMap=new HashMap<String,ArrayList<String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String selectTypeSQL = "SELECT pt.TypeName, st.ProdSubTypeName FROM AL_PRODUCT_TYPE pt,"
					+ " AL_PRODUCTSUBTYPE st WHERE pt.ProductTypeId=st.ProductTypeId";
			Statement stmt = con.createStatement();
			ResultSet typeResults=stmt.executeQuery(selectTypeSQL);
			while(typeResults.next()){
				String prodType=typeResults.getString(1);
				String prodSubType=typeResults.getString(2);
				ArrayList<String> subTypeList=new ArrayList<String>();
				if(typeMap.containsKey(prodType)){
					subTypeList=typeMap.get(prodType);
					subTypeList.add(prodSubType);
				}
				else{
					subTypeList.add(prodSubType);
					typeMap.put(prodType, subTypeList);
				}
			}
			return typeMap;
		} 
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public ArrayList<InventoryMini> fetchProductFromSubType(String subType){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL = "SELECT i.InventoryId, i.ProductName, i.ImageName, i.ProductRating,v.vendorname "
				+ "from AL_INVENTORY i, AL_PRODUCTSUBTYPE p, AL_VENDORS v WHERE i.ProductSubId=p.ProductSubId "
				+ "AND i.vendorid=v.vendorid "
				+ "AND p.ProdSubTypeName=?";
		PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
		stmt.setString(1, subType);
		ResultSet res=stmt.executeQuery();
		ArrayList<InventoryMini> inventoryList=new ArrayList<InventoryMini>();
		while (res.next()){
			long invId=res.getLong("InventoryId");
			String prName=res.getString("ProductName");
			String imgName=res.getString("ImageName");
			float prodRating=res.getFloat("ProductRating");
			String vendorName=res.getString("VendorName");
			InventoryMini im=new InventoryMini(invId,prName,prodRating,imgName,vendorName);
			inventoryList.add(im);
		}
		return inventoryList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
}
