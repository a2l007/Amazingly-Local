package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.models.InventoryGrid;
import com.iu.amazelocal.models.InventoryMini;
import com.iu.amazelocal.models.ProductSubTypes;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.utils.AppConstants;


public class InventoryCrud {
	public void insertProduct(Inventory i) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			String insertProdSQL = "INSERT INTO AL_INVENTORY (InventoryId, VendorId, ProductSubId,"
			+" ProductName, Description, Quantity, Price, Unit, Calories, Sale,ImageName)" 
			+"VALUES " + "(?,?,?,?,?,?,?,?,?,?,?)";
			Long inventoryId=AppConstants.USERIDSEQ+12;
			PreparedStatement stmt = con.prepareStatement(insertProdSQL);
			stmt.setLong(1, inventoryId);
			stmt.setLong(2, 10000);
			stmt.setLong(3, i.getProductSubId());
			stmt.setString(4, i.getProductName());
			stmt.setString(5, i.getDescription());
			stmt.setInt(6, i.getQuantity());
			stmt.setFloat(7, i.getPrice());
			stmt.setString(8, i.getUnit());
			stmt.setFloat(9, i.getCalories());
			stmt.setFloat(10, i.getSale());
			stmt.setString(11, i.getImageName());
			stmt.executeUpdate();
			con.close();
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
			while(res.next()){
				ProductType type = new ProductType();
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
	public int insertProductSubType(ProductSubTypes subType) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			String insertSQL = "INSERT INTO AL_PRODUCTSUBTYPE (ProductSubId, ProductTypeId, ProdSubTypeName)"
			+"VALUES " + "(?,?,?)";
			int ProductSubId = 10004;
			PreparedStatement stmt = con.prepareStatement(insertSQL);
			stmt.setInt(1, ProductSubId);
			stmt.setInt(2, subType.getTypeId());
			stmt.setString(3, subType.getSubTypeName());
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("A new sub type was inserted successfully!");
			    return ProductSubId;
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
        	return 0;
	}
	
	
	public ArrayList<ProductSubTypes> fetchProductSubTypes(){
		Connection con = ConnectionFactory.getConnObject();
		String selectProdTypeSQL = " SELECT * FROM AL_PRODUCTSUBTYPE";
		ArrayList<ProductSubTypes> prodSubTypes = new ArrayList<ProductSubTypes>();
		try{
			PreparedStatement stmt = con.prepareStatement(selectProdTypeSQL);
			ResultSet res=stmt.executeQuery();
			while(res.next()){
				ProductSubTypes subType = new ProductSubTypes();
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
	
	
	public ArrayList<InventoryGrid> fetchInventories(){
		Connection con = ConnectionFactory.getConnObject();
		String selectProductsSQL = "SELECT INV.InventoryId, PTYPE.ProductTypeId, PTYPE.TypeName," 
		+" INV.ProductSubId, PSUBTYPE.ProdSubTypeName,INV.ProductName, INV.Description, INV.Quantity,"
		+" INV.Price, INV.Unit, INV.Calories, INV.Sale, INV.ProductRating, SaleApproved "
		+" FROM AL_INVENTORY INV LEFT JOIN AL_PRODUCTSUBTYPE PSUBTYPE ON INV.ProductSubId = PSUBTYPE.ProductSubId "
		+" LEFT JOIN AL_PRODUCT_TYPE PTYPE ON PTYPE.ProductTypeId = PSUBTYPE.ProductTypeId WHERE VendorId = 2";
		
		ArrayList<InventoryGrid> inventories = new ArrayList<InventoryGrid>();
		try{
			PreparedStatement stmt = con.prepareStatement(selectProductsSQL);
			ResultSet res=stmt.executeQuery();
			while(res.next()){
				InventoryGrid prod = new InventoryGrid();
				prod.setInventoryId(Long.parseLong(res.getString("InventoryId")));
				prod.setProductTypeId(Long.parseLong(res.getString("ProductTypeId")));
				prod.setProductType(res.getString("TypeName"));
				prod.setProductSubId(Long.parseLong(res.getString("ProductSubId")));
				prod.setProductSubType(res.getString("ProdSubTypeName"));
				prod.setProductName(res.getString("ProductName"));
				prod.setDescription(res.getString("Description"));
				prod.setQuantity(Integer.parseInt(res.getString("Quantity")));
				prod.setPrice(Float.parseFloat(res.getString("Price")));
				prod.setUnit(res.getString("Unit"));
				prod.setCalories(Float.parseFloat(res.getString("Calories")));
				
				if(res.getString("Sale") != null){
					prod.setSale(Float.parseFloat(res.getString("Sale")));
				}
				
				if(res.getString("ProductRating") != null){
					prod.setProductRating(Float.parseFloat(res.getString("ProductRating")));
				}
				
				String saleStatus = null;
				System.out.println("ststus "+res.getString("SaleApproved"));
				if(res.getString("SaleApproved") != null){
					saleStatus	= res.getString("SaleApproved").equals("Y")?"Approved" : "Declined";
					System.out.println("saleStatus"+saleStatus);
				}
				else{
					saleStatus = "Not Available";
					System.out.println("saleStatus"+saleStatus);
				}
				
				System.out.println("saleStatus"+saleStatus);
				prod.setSaleApproved(saleStatus);
				
				inventories.add(prod);
			}
		return inventories;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Inventory fetchInventoryDetails(int inventoryId){
		Connection con = ConnectionFactory.getConnObject();
		String selectProductsSQL = "SELECT INV.InventoryId, PTYPE.ProductTypeId, PTYPE.TypeName," 
		+" INV.ProductSubId, PSUBTYPE.ProdSubTypeName,INV.ProductName, INV.Description, INV.Quantity,"
		+" INV.Price, INV.Unit, INV.Calories, INV.Sale, INV.ProductRating, SaleApproved "
		+" FROM AL_INVENTORY INV LEFT JOIN AL_PRODUCTSUBTYPE PSUBTYPE ON INV.ProductSubId = PSUBTYPE.ProductSubId "
		+" LEFT JOIN AL_PRODUCT_TYPE PTYPE ON PTYPE.ProductTypeId = PSUBTYPE.ProductTypeId WHERE VendorId = 2 "
		+ "AND INV.InventoryId = " + inventoryId;
		
		Inventory prod = new Inventory();
		try{
			PreparedStatement stmt = con.prepareStatement(selectProductsSQL);
			ResultSet res=stmt.executeQuery();
			
			while(res.next()){
				prod.setInventoryId(Long.parseLong(res.getString("InventoryId")));
				prod.setProductTypeId(Long.parseLong(res.getString("ProductTypeId")));
				prod.setProductType(res.getString("TypeName"));
				prod.setProductSubId(Long.parseLong(res.getString("ProductSubId")));
				prod.setProductSubType(res.getString("ProdSubTypeName"));
				prod.setProductName(res.getString("ProductName"));
				prod.setDescription(res.getString("Description"));
				prod.setQuantity(Integer.parseInt(res.getString("Quantity")));
				prod.setPrice(Float.parseFloat(res.getString("Price")));
				prod.setUnit(res.getString("Unit"));
				prod.setCalories(Float.parseFloat(res.getString("Calories")));
				
				if(res.getString("Sale") != null){
					prod.setSale(Float.parseFloat(res.getString("Sale")));
				}
				
				if(res.getString("ProductRating") != null){
					prod.setProductRating(Float.parseFloat(res.getString("ProductRating")));
				}
				
				String saleStatus = null;
				System.out.println("ststus "+res.getString("SaleApproved"));
				if(res.getString("SaleApproved") != null){
					saleStatus	= res.getString("SaleApproved").equals("Y")?"Approved" : "Declined";
					System.out.println("saleStatus"+saleStatus);
				}
				else{
					saleStatus = "Not Available";
					System.out.println("saleStatus"+saleStatus);
				}
				
				System.out.println("saleStatus"+saleStatus);
				prod.setSaleApproved(saleStatus);
			}
		return prod;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<InventoryMini> fetchProductFromVendor(String vendorName){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL = "SELECT i.InventoryId, i.ProductName, i.ImageName, i.ProductRating FROM AL_VENDORS v, AL_INVENTORY i "
				+ "WHERE v.VendorId=i.VendorId AND v.VendorName LIKE '%?%'";
		PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
		stmt.setString(1, vendorName);
		ResultSet res=stmt.executeQuery();
		ArrayList<InventoryMini> inventoryList=new ArrayList<InventoryMini>();
		while (res.next()){
			long invId=res.getLong("InventoryId");
			String prName=res.getString("ProductName");
			String imgName=res.getString("ImageName");
			float prodRating=res.getFloat("ProductRating");
			InventoryMini im=new InventoryMini(invId,prName,prodRating,imgName);
			inventoryList.add(im);
		}
		return inventoryList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<InventoryMini> fetchProductsByType(String type,String subType){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL = "SELECT i.InventoryId, i.ProductName, i.ImageName, i.ProductRating from AL_PRODUCT_TYPE t, AL_PRODUCTSUBTYPE st, AL_INVENTORY i "
				+ "WHERE t.ProductTypeId=st.ProductTypeId AND st.ProductSubId=i.ProductSubId "
				+ "AND t.TypeName=? AND st.ProdSubTypeName=?";
		PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
		stmt.setString(1, type);
		stmt.setString(2, subType);
		ResultSet res=stmt.executeQuery();
		ArrayList<InventoryMini> inventoryList=new ArrayList<InventoryMini>();
		while (res.next()){
			long invId=res.getLong("InventoryId");
			String prName=res.getString("ProductName");
			String imgName=res.getString("ImageName");
			float prodRating=res.getFloat("ProductRating");
			InventoryMini im=new InventoryMini(invId,prName,prodRating,imgName);
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