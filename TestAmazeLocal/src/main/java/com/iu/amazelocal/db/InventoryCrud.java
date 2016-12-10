package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.models.InventoryGrid;
import com.iu.amazelocal.models.InventoryMini;
import com.iu.amazelocal.models.ProductApprovalGrid;
import com.iu.amazelocal.models.ProductSaleDao;
import com.iu.amazelocal.models.ProductSubTypes;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.models.ProductTypeDao;
import com.iu.amazelocal.models.SaleApprovalGrid;
import com.iu.amazelocal.models.VendorRevenueDao;
import com.iu.amazelocal.utils.AppConstants;


public class InventoryCrud {
	public void insertProduct(Inventory i) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			String insertProdSQL = "INSERT INTO AL_INVENTORY (InventoryId, VendorId, ProductSubId,"
			+" ProductName, Description, Quantity, Price, Unit, Calories, Sale,ImageName)" 
			+"VALUES " + "(?,?,?,?,?,?,?,?,?,?,?)";
			Long inventoryId=AppConstants.INVENTORYSEQ;
			Long newinventoryId=UserCrud.fetchLatestId("AL_INVENTORY", "InventoryId");
			if(newinventoryId!=0L){
				inventoryId=newinventoryId;
			}
			PreparedStatement stmt = con.prepareStatement(insertProdSQL);
			stmt.setLong(1, inventoryId);
			stmt.setLong(2, 2);
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
	
	
	public ArrayList<InventoryGrid> fetchInventories(long vendorId){
		Connection con = ConnectionFactory.getConnObject();
		String selectProductsSQL = "SELECT INV.InventoryId, PTYPE.ProductTypeId, PTYPE.TypeName," 
		+" INV.ProductSubId, PSUBTYPE.ProdSubTypeName,INV.ProductName, INV.Description, INV.Quantity,"
		+" INV.Price, INV.Unit, INV.Calories, INV.Sale, INV.ProductRating, SaleApproved "
		+" FROM AL_INVENTORY INV LEFT JOIN AL_PRODUCTSUBTYPE PSUBTYPE ON INV.ProductSubId = PSUBTYPE.ProductSubId "
		+" LEFT JOIN AL_PRODUCT_TYPE PTYPE ON PTYPE.ProductTypeId = PSUBTYPE.ProductTypeId WHERE VendorId = ? ";
		
		ArrayList<InventoryGrid> inventories = new ArrayList<InventoryGrid>();
		try{
			PreparedStatement stmt = con.prepareStatement(selectProductsSQL);
			stmt.setLong(1, vendorId);
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
		String selectProductsSQL = "SELECT INV.InventoryId, INV.ImageName, PTYPE.ProductTypeId, PTYPE.TypeName," 
		+" INV.ProductSubId, PSUBTYPE.ProdSubTypeName,INV.ProductName, INV.Description, INV.Quantity,"
		+" INV.Price, INV.Unit, INV.Calories, INV.Sale, INV.ProductRating, SaleApproved "
		+" FROM AL_INVENTORY INV LEFT JOIN AL_PRODUCTSUBTYPE PSUBTYPE ON INV.ProductSubId = PSUBTYPE.ProductSubId "
		+" LEFT JOIN AL_PRODUCT_TYPE PTYPE ON PTYPE.ProductTypeId = PSUBTYPE.ProductTypeId WHERE INV.InventoryId = "
		+ inventoryId;
		System.out.print("fetch inv"+ inventoryId);
		Inventory prod = new Inventory();
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectProductsSQL);
			
			if(res.next()){
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
				prod.setImageName(res.getString("ImageName"));
				if(res.getString("Sale") != null){
					prod.setSale(Float.parseFloat(res.getString("Sale")));
				}
				System.out.print("res.getString:" + res.getString("Price"));
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
			System.out.println("Price of inv: "+ prod.getPrice());
		return prod;

		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<InventoryMini> fetchProductWithoutType(String searchStr){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL = " SELECT i.inventoryid,i.productname,i.imagename, i.productrating,v.vendorname FROM "
				+ "  AL_PRODUCTSUBTYPE st, AL_INVENTORY i, AL_VENDORS v WHERE "
				+ " st.productsubid = i.productsubid "
				+ "AND v.vendorid=i.vendorid "
				+ "AND st.prodsubtypename LIKE ? "
				+ "UNION SELECT i.inventoryid, i.productname, i.imagename, i.productrating, v.vendorname "
				+ "FROM   AL_VENDORS v, AL_INVENTORY i WHERE  v.vendorid = i.vendorid "
				+ "AND v.vendorname LIKE ? UNION "
				+ "SELECT i.inventoryid, i.productname, i.imagename, i.productrating, v.vendorname "
				+ "FROM  AL_INVENTORY i,AL_VENDORS v WHERE  i.productname LIKE ? "
				+ "AND v.vendorid=i.vendorid";  

		PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
		stmt.setString(1, "%"+searchStr+"%");
		stmt.setString(2, "%"+searchStr+"%");
		stmt.setString(3, "%"+searchStr+"%");
		String productTypeSql="select st.ProdSubTypeName,pt.TypeName from "
				+ "AL_INVENTORY i, AL_PRODUCTSUBTYPE st, AL_PRODUCT_TYPE pt " 
				+ "WHERE i.productsubid=st.productsubid AND st.producttypeid=pt.producttypeid "
				+ "AND i.InventoryId=?";
		ResultSet res=stmt.executeQuery();
		ArrayList<InventoryMini> inventoryList=new ArrayList<InventoryMini>();
		while (res.next()){
			long invId=res.getLong("InventoryId");
			String prName=res.getString("ProductName");
			String imgName=res.getString("ImageName");
			float prodRating=res.getFloat("ProductRating");
			String vendorName=res.getString("VendorName");
			InventoryMini im=new InventoryMini(invId,prName,prodRating,imgName,vendorName);
			stmt=con.prepareStatement(productTypeSql);
			stmt.setLong(1, invId);
			ResultSet typeResults=stmt.executeQuery();
			typeResults.next();
			im.setProductSubType(typeResults.getString(1));
			im.setProductType(typeResults.getString(2));
			inventoryList.add(im);
		}
		return inventoryList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<InventoryMini> fetchProductsByType(String type,String searchStr){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL ="";
		if(searchStr.equals("")){
			selectTypeSQL = " SELECT i.inventoryid,i.productname,i.imagename, i.productrating,v.vendorname FROM "
					+ "  AL_PRODUCT_TYPE t, AL_PRODUCTSUBTYPE st, AL_INVENTORY i, AL_VENDORS v WHERE "
					+ " t.producttypeid = st.producttypeid AND st.productsubid = i.productsubid "
					+ "AND i.vendorid=v.vendorid "
					+ "AND t.typename = ? ";
		}
		else{
		selectTypeSQL = " SELECT i.inventoryid,i.productname,i.imagename, i.productrating,v.vendorname FROM "
				+ "  AL_PRODUCT_TYPE t, AL_PRODUCTSUBTYPE st, AL_INVENTORY i, AL_VENDORS v WHERE "
				+ " t.producttypeid = st.producttypeid AND st.productsubid = i.productsubid "
				+ "AND i.vendorid=v.vendorid "
				+ "AND t.typename = ? AND st.prodsubtypename LIKE ? "
				+ "UNION SELECT i.inventoryid, i.productname, i.imagename, i.productrating,v.vendorName "
				+ "FROM   AL_VENDORS v, AL_INVENTORY i WHERE  v.vendorid = i.vendorid "
				+ "AND v.vendorname LIKE ? UNION "
				+ "SELECT i.inventoryid, i.productname, i.imagename, i.productrating, v.vendorname "
				+ "FROM   AL_INVENTORY i, AL_VENDORS v WHERE  i.productname LIKE ? "
				+ "AND i.vendorid=v.vendorid"; 
		}
		PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
	
		stmt.setString(1, type);
		if(!(searchStr.equals(""))){
			stmt.setString(2, "%"+searchStr+"%");
			stmt.setString(3, "%"+searchStr+"%");
			stmt.setString(4, "%"+searchStr+"%");
		}
		ResultSet res=stmt.executeQuery();
		ArrayList<InventoryMini> inventoryList=new ArrayList<InventoryMini>();
		String productTypeSql="select st.ProdSubTypeName,pt.TypeName from "
				+ "AL_INVENTORY i, AL_PRODUCTSUBTYPE st, AL_PRODUCT_TYPE pt " 
				+ "WHERE i.productsubid=st.productsubid AND st.producttypeid=pt.producttypeid "
				+ "AND i.InventoryId=?";
		while (res.next()){
			long invId=res.getLong("InventoryId");
			String prName=res.getString("ProductName");
			String imgName=res.getString("ImageName");
			float prodRating=res.getFloat("ProductRating");
			String vendorName=res.getString("VendorName");
			InventoryMini im=new InventoryMini(invId,prName,prodRating,imgName,vendorName);
			stmt=con.prepareStatement(productTypeSql);
			stmt.setLong(1, invId);
			ResultSet typeResults=stmt.executeQuery();
			typeResults.next();
			im.setProductSubType(typeResults.getString(1));
			im.setProductType(typeResults.getString(2));
			inventoryList.add(im);
		}
		return inventoryList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<InventoryMini> listProductsByType(String type){
		try{
			Connection con = ConnectionFactory.getConnObject();
			ResultSet res=null;
		// here sonoo is database name, root is username and password
		if(type.equals("All")){
			String selectTypeSQL = "  SELECT i.inventoryid,i.productname,i.imagename, i.productrating,v.vendorname FROM"
					+ "   AL_PRODUCTSUBTYPE st, AL_INVENTORY i, AL_VENDORS v WHERE st.productsubid = i.productsubid"
					+ " AND v.vendorid=i.vendorid";
			Statement stmt = con.createStatement();
			res=stmt.executeQuery(selectTypeSQL);

		}
		else{
			String selectTypeSQL = "  SELECT i.inventoryid,i.productname,i.imagename, i.productrating,v.vendorname FROM"
					+ "   AL_PRODUCTSUBTYPE st, AL_INVENTORY i, AL_VENDORS v WHERE st.productsubid = i.productsubid"
					+ " AND v.vendorid=i.vendorid" 
					+ " AND  st.prodsubtypename = ? "; 
			PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
			stmt.setString(1, type);
			res=stmt.executeQuery();

		}
		
	
		System.out.println("Type is"+type);
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
		System.out.println("Inventory size is"+inventoryList.size());
		return inventoryList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<SaleApprovalGrid> fetchApprovalList(){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL = "select i.InventoryId,v.VendorName,s.ProdSubTypeName,i.ProductName, i.Price,i.Sale "
				+ "from AL_INVENTORY i,AL_VENDORS v, AL_PRODUCTSUBTYPE s "
				+ "WHERE i.vendorid=v.vendorid AND i.ProductSubId=s.ProductSubId  "
				+ "AND i.SaleApproved IS null";

		Statement stmt = con.createStatement();
	
		ResultSet res=stmt.executeQuery(selectTypeSQL);
		ArrayList<SaleApprovalGrid> inventoryList=new ArrayList<SaleApprovalGrid>();
		while (res.next()){
			int invId=res.getInt("InventoryId");
			String vendName=res.getString("VendorName");
			String prodSubTypeName=res.getString("ProdSubTypeName");
			String prodName=res.getString("ProductName");
			float price=res.getFloat("Price");
			float sale=res.getFloat("Sale");
			SaleApprovalGrid sg=new SaleApprovalGrid(invId,vendName,prodSubTypeName,prodName,price,sale);
			inventoryList.add(sg);
		}
		System.out.println("Inventory size is"+inventoryList.size());
		return inventoryList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	public void approveSales(int[] inventory){
		try{
			Connection con = ConnectionFactory.getConnObject();
			int invLength=inventory.length;
			StringBuffer params=new StringBuffer("UPDATE AL_INVENTORY set SaleApproved='Y' WHERE InventoryId IN ( ");
			for(int i=0;i<invLength-1;i++){
				params.append("?,");
			}
			params.append("?");
			String selectTypeSQL = params.append(" )").toString();

			PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
			for(int i=0;i<invLength;i++){
				stmt.setInt(i+1, inventory[i]);
			}
			stmt.executeUpdate();
			
			System.out.println("Updated");
			}
			catch(SQLException e){
				System.out.println(e);
			}
	}
	public ArrayList<ProductTypeDao> fetchProductTypeQuantity(){
		Connection con = ConnectionFactory.getConnObject();
		String selectProductSql = "select pt.TypeName,count(st.ProductSubId) "
				+ " from AL_INVENTORY i, AL_PRODUCTSUBTYPE st, AL_PRODUCT_TYPE pt "
				+ "WHERE i.ProductSubId=st.ProductSubId AND st.ProductTypeId=pt.ProductTypeId GROUP BY TypeName";

		ArrayList<ProductTypeDao> productTypeList=new ArrayList<ProductTypeDao>(10);
		try{
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery(selectProductSql);
			while(res.next()){
				String productType=res.getString(1);
				long quantity=res.getLong(2);
				ProductTypeDao vrd=new ProductTypeDao(productType,quantity);
				productTypeList.add(vrd);
			}
				return productTypeList;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<ProductSaleDao> fetchProductUnitSold(int vendorId){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL = "select st.ProdSubTypeName, SUM(UnitSold) from AL_INVENTORY i,AL_PRODUCTSUBTYPE st "
				+ "where i.VendorId=? AND i.ProductSubId=st.ProductSubId group by ProdSubTypeName";

		PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
		stmt.setInt(1,vendorId);
		ResultSet res=stmt.executeQuery();
		ArrayList<ProductSaleDao> soldList=new ArrayList<ProductSaleDao>();
		while (res.next()){
			String prodSubTypeName=res.getString("ProdSubTypeName");
			int sumUnits=res.getInt(2);
			
			ProductSaleDao sg=new ProductSaleDao(prodSubTypeName,sumUnits);
			soldList.add(sg);
		}
		System.out.println("Inventory size is"+soldList.size());
		return soldList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	public boolean updateProduct(Inventory i) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = ConnectionFactory.getConnObject();
            String insertProdSQL = "UPDATE AL_INVENTORY SET ProductSubId = ?,"
            +" ProductName = ?, Description = ?, Quantity = ?, Price = ?, Unit = ?, Calories = ?, " 
            +" Sale = ?, ImageName = ? WHERE InventoryId = ? AND VendorId = ?";
            
            System.out.println("Inv Id in db.java "+ i.getInventoryId());
            PreparedStatement stmt = con.prepareStatement(insertProdSQL);
            stmt.setLong(1, i.getProductSubId());
            stmt.setString(2, i.getProductName());
            stmt.setString(3, i.getDescription());
            stmt.setInt(4, i.getQuantity());
            stmt.setFloat(5, i.getPrice());
            stmt.setString(6, i.getUnit());
            stmt.setFloat(7, i.getCalories());
            stmt.setFloat(8, i.getSale());
            stmt.setString(9, i.getImageName());
            stmt.setLong(10, i.getInventoryId());
            stmt.setLong(11, i.getVendorId());
            stmt.executeUpdate();
            con.close();
            System.out.println("Update successful");
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
	
	public ArrayList<ProductApprovalGrid> fetchProductApprovalList(long vendorId){
		try{
		Connection con = ConnectionFactory.getConnObject();
		// here sonoo is database name, root is username and password
		String selectTypeSQL = "select sc.cartId,u.FirstName,u.LastName,u.EmailAddress,u.MailingAddress, oh.OrderDate,i.ProductName,sc.Quantity "
				+ "from AL_ORDER_HISTORY oh,AL_SHOP_CART sc,AL_INVENTORY i,AL_USERS u where oh.OrderId=sc.OrderId "
				+ "AND sc.InventoryId=i.InventoryId AND oh.UserId=u.UserId AND oh.OrderStatus='New' AND i.VendorId= 2 ";

		PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
		//stmt.setLong(1,vendorId);
		ResultSet res=stmt.executeQuery(selectTypeSQL);
		ArrayList<ProductApprovalGrid> inventoryList=new ArrayList<ProductApprovalGrid>();
		while (res.next()){
			int cartId=res.getInt("cartId");
			String firstName=res.getString("FirstName");
			String lastName=res.getString("LastName");
			String email=res.getString("EmailAddress");
			String mailingAddress=res.getString("MailingAddress");
			String orderDate=res.getString("OrderDate");
			String productName=res.getString("ProductName");
			int qty=res.getInt("Quantity");
			ProductApprovalGrid sg=new ProductApprovalGrid(cartId,firstName,lastName,email,mailingAddress,orderDate,productName,qty);
			inventoryList.add(sg);
		}
		System.out.println("Inventory size is"+inventoryList.size());
		return inventoryList;
		}
		catch(SQLException e){
			System.out.println(e);
			return null;
		}
	}
	public void processProducts(int[] inventory){
		try{
			Connection con = ConnectionFactory.getConnObject();
			int invLength=inventory.length;
			StringBuffer params=new StringBuffer("update AL_ORDER_HISTORY set OrderStatus='Processed' "
					+ "where orderId in (select orderId from AL_SHOP_CART where cartId IN (");
 
			for(int i=0;i<invLength-1;i++){
				params.append("?,");
			}
			params.append("?");
			String selectTypeSQL = params.append(" ))").toString();

			PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
			for(int i=0;i<invLength;i++){
				stmt.setInt(i+1, inventory[i]);
			}
			stmt.executeUpdate();
			
			System.out.println("Updated");
			}
			catch(SQLException e){
				System.out.println(e);
			}
	}
}