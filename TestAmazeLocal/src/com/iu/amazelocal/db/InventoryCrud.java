package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.models.ProductSubTypes;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.utils.AppConstants;


public class InventoryCrud {
	public void insertProduct(Inventory i,String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String insertTableSQL = "INSERT INTO AL_USERS " + "VALUES " + "(?,?,?,?,?,?)";
			Long userId=AppConstants.USERIDSEQ;
			PreparedStatement stmt = con.prepareStatement(insertTableSQL);
			stmt.setLong(1, userId);
			/*stmt.setString(2, i.getFirstName());
			stmt.setString(3, u.getLastName());
			stmt.setString(4, u.getEmailAddress());
			stmt.setLong(5, u.getPhoneNUmber());
			stmt.setString(6, u.getMailingAddress());
			stmt.executeUpdate();
			UserCrud.createLogin(userId, u.getEmailAddress(), pass, con);
			con.close();*/
		} catch (Exception e) {
			System.out.println(e);
		}
	}	
	
	public ArrayList<ProductType> fetchProductTypes(){
		Connection con = ConnectionFactory.getConnObject();
		String selectProdTypeSQL = " SELECT * FROM AL_PRODUCT_TYPE";
		ArrayList<ProductType> prodTypes = new ArrayList<ProductType>();
		try{
			PreparedStatement stmt = con.prepareStatement(selectProdTypeSQL);
			ResultSet res=stmt.executeQuery();
			System.out.println("selectProdTypeSQL executed");
			while(res.next()){
				ProductType type = new ProductType();
				System.out.println("Jhere");
				type.setTypeId(Integer.parseInt(res.getString("ProductTypeId")));
				type.setTypeName(res.getString("TypeName"));
				prodTypes.add(type);
			}
		return prodTypes;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<ProductSubTypes> fetchProductSubTypes(int productType){
		Connection con = ConnectionFactory.getConnObject();
		String selectProdTypeSQL = " SELECT * FROM AL_PRODUCTSUBTYPE WHERE ProductTypeId = "+ productType;
		ArrayList<ProductSubTypes> prodSubTypes = new ArrayList<ProductSubTypes>();
		try{
			PreparedStatement stmt = con.prepareStatement(selectProdTypeSQL);
			ResultSet res=stmt.executeQuery();
			System.out.println("selectProdTypeSQL executed");
			while(res.next()){
				ProductSubTypes subType = new ProductSubTypes();
				System.out.println("Jhere");
				subType.setSubTypeId(Integer.parseInt(res.getString("ProductSubId")));
				subType.setTypeId(Integer.parseInt(res.getString("ProductTypeId")));
				subType.setSubTypeName(res.getString("ProdSubTypeName"));
				prodSubTypes.add(subType);
			}
		return prodSubTypes;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}