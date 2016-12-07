package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.ShopCart;
import com.iu.amazelocal.models.cart;
import com.iu.amazelocal.utils.AppConstants;


public class ShopCartCrud {
	public ArrayList<ShopCart> fetchCartItems(Long userId ) {
		Connection con = ConnectionFactory.getConnObject();
		try {
			System.out.println("fetchCartItems insiseddvcdhjhdsj !!!");
			String selectCartQuery = "SELECT CartId, SC.OrderId, SC.InventoryId, SC.Quantity, ActiveCart, TotalPrice, INV.SaleApproved, "
					+ "INV.Price AS UnitPrice,ProductName, ImageName, IFNULL(INV.Sale, 0) AS Sale, CartUpdateDate, UserId, OH.VendorId, "
					+ "OrderTotal, OrderDate, INV.Quantity AS InvQuantity, IFNULL(INV.UnitSold, 0) AS UnitSold FROM AL_SHOP_CART SC INNER JOIN AL_ORDER_HISTORY OH ON SC.OrderId = OH.OrderId "
					+ "INNER JOIN AL_INVENTORY INV ON SC.InventoryId = INV.InventoryId WHERE ActiveCart = 'Y' AND UserId = ?";
			
			PreparedStatement stmt = con.prepareStatement(selectCartQuery);
			stmt.setLong(1,userId );
			ResultSet res=stmt.executeQuery();
			
			ArrayList<ShopCart> cartItems = new ArrayList<ShopCart>();
			while(res.next()){
				System.out.println("Result set found inside fetch cartitems");
				ShopCart cart = new ShopCart();
				cart.setCartId(Long.parseLong(res.getString("CartId")));
				cart.setOrderId(Long.parseLong(res.getString("OrderId")));
				cart.setInventoryId(Long.parseLong(res.getString("InventoryId")));
				cart.setUserId(Long.parseLong(res.getString("UserId")));
				cart.setVendorId(Long.parseLong(res.getString("VendorId")));
				//System.out.println("Unit sold: "+Integer.parseInt(res.getString("UnitSold")));
				cart.setInvQuantity(Integer.parseInt(res.getString("Quantity")));
				cart.setQuantityAvailable(Integer.parseInt(res.getString("InvQuantity")) - Integer.parseInt(res.getString("UnitSold")));
				cart.setInvTotalPrice(Float.parseFloat(res.getString("TotalPrice")));
				cart.setUnitPrice(Float.parseFloat(res.getString("UnitPrice")));
				cart.setProductName(res.getString("ProductName"));
				cart.setImageName(res.getString("ImageName"));
				
				//cart.setOrderDate(SimpleDateFormat.parse(res.getString("OrderDate")));
				boolean IsActive = false;
				
				if(res.getString("ActiveCart") != null){
					IsActive	= res.getString("ActiveCart").equals("Y")? true : false;
				}

				cart.setIsActive(IsActive);
				
				if(res.getString("SaleApproved") != null){
					cart.setDiscount(res.getString("SaleApproved").equals("Y")?Float.parseFloat(res.getString("Sale")):0);
				}
				else{
					cart.setDiscount(Float.parseFloat(res.getString("Sale")));
				}
				
				cartItems.add(cart);
			}
			return cartItems;
		} 
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteCartItem(ShopCart cartItem) {
		Connection con = ConnectionFactory.getConnObject();
		try {
			System.out.println("Delete CartItems insiseddvcdhjhdsj !!!");
			String countItemsQuery = "(SELECT COUNT(*) AS TOTALROWS FROM AL_SHOP_CART SC"
					+" INNER JOIN AL_ORDER_HISTORY OH ON SC.OrderId = OH.OrderId WHERE OH.OrderId = ? AND SC.CartId = ? AND OH.UserId = ?);";
		
			PreparedStatement stmt = con.prepareStatement(countItemsQuery);
			stmt.setLong(1, cartItem.getOrderId());
			stmt.setLong(2, cartItem.getCartId());
			stmt.setLong(3, cartItem.getUserId());
			ResultSet res = stmt.executeQuery();
			int totalItems = 0;
			
			if(res.next()){
				totalItems = Integer.parseInt(res.getString("TOTALROWS"));
			}
			String deleteItemsQuery = "DELETE FROM  AL_SHOP_CART WHERE CartId = ?; ";
			if(totalItems > 1){
				deleteItemsQuery += " DELETE FROM  AL_ORDER_HISTORY WHERE OrderId =" + cartItem.getOrderId() +"; "; 
			}
			PreparedStatement delStmt = con.prepareStatement(deleteItemsQuery);
			delStmt.setLong(1, cartItem.getCartId());
			int rowsDeleted = delStmt.executeUpdate();
			if (rowsDeleted > 0) {
			    System.out.println("Rows deleted successfully!");
			    return true;
			}
			con.close();
		} 
		catch(SQLException e){
			e.printStackTrace();
		}

		return false;
	}
	
	
	public ShopCart fetchOrderDetails(Long userId) {
		try {
			Connection con = ConnectionFactory.getConnObject();
			System.out.println("fetchOrderDetails insiseddvcdhjhdsj !!!");
			String selectCartQuery = "SELECT DISTINCT OH.OrderId, OrderQuantity, OrderTotal, date(OrderDate) AS OrderDate, " 
					+ " OrderStatus, DeliveryAddress FROM AL_ORDER_HISTORY OH INNER JOIN AL_SHOP_CART SC ON OH.OrderId = SC.OrderId "
					+ " WHERE ActiveCart = 'Y' AND UserId = ?";
			
			PreparedStatement stmt = con.prepareStatement(selectCartQuery);
			stmt.setLong(1, userId);
			ResultSet res=stmt.executeQuery();
			
			ShopCart cart = new ShopCart();
			while(res.next()){
				cart.setOrderId(Long.parseLong(res.getString("OrderId")));
				cart.setOrderQuantity(Integer.parseInt(res.getString("OrderQuantity")));
				cart.setOrderSubTotal(Float.parseFloat(res.getString("OrderTotal")));
				cart.setOrderDate(res.getString("OrderDate"));
				cart.setOrderStatus(res.getString("OrderStatus"));
				cart.setDeliveryAddress(res.getString("DeliveryAddress"));
			}
			return cart;
		} 
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ShopCart updateCartQuantity(cart c){
		
		Connection con = ConnectionFactory.getConnObject();

		ShopCart cart = new ShopCart();
		
		try{
			String updateQuantityQuery = "UPDATE AL_SHOP_CART SET Quantity = ?, TotalPrice = ? WHERE CartId = ? AND OrderId = ?";
			PreparedStatement stmt = con.prepareStatement(updateQuantityQuery);
			System.out.println("in shop cart curd: " + c.getOrderId());
			stmt.setInt(1, c.getQuantity());
			stmt.setFloat(2, c.getTotalPrice());
			stmt.setLong(3,c.getCartId());
			stmt.setLong(4,c.getOrderId());
			stmt.executeUpdate();
		
			//Calculate the total price and units
			String selectCartQuery = "SELECT INV.InventoryId,  SC.Quantity*Price AS TotalPrice, Sale, SaleApproved, "
					+ " SC.Quantity FROM AL_INVENTORY INV INNER JOIN AL_SHOP_CART SC ON INV.INVENTORYID = SC.INVENTORYID "
					+ " WHERE ActiveCart = 'Y' AND CartId = ? AND OrderId = ?";
			
			PreparedStatement selStmt = con.prepareStatement(selectCartQuery);
			System.out.println("in shop cart curd: " + c.getOrderId());
			selStmt.setLong(1, c.getCartId());
			selStmt.setLong(2, c.getOrderId());
			ResultSet res = selStmt.executeQuery();
			
			while(res.next()){
				System.out.println("Calculate the total price and units");
				cart.setInventoryId(Long.parseLong(res.getString("InventoryId")));
				cart.setInvTotalPrice(Float.parseFloat(res.getString("TotalPrice")));
				cart.setInvQuantity(Integer.parseInt(res.getString("Quantity")));
				
				if(res.getString("SaleApproved") != null){
					cart.setDiscount(res.getString("SaleApproved").equals("Y")?Float.parseFloat(res.getString("Sale")):0);
				}
				else{
					cart.setDiscount(Float.parseFloat(res.getString("Sale")));
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return cart;
	}
	
	public void updateOrderHistory(ShopCart c){
		Connection con = ConnectionFactory.getConnObject();
		
		String updateAddrQuery = "UPDATE AL_ORDER_HISTORY SET OrderQuantity = ?, OrderTotal = ? WHERE OrderId = ?";
		try{
			PreparedStatement stmt = con.prepareStatement(updateAddrQuery);
			System.out.println("in shop cart curd for updating the address: " + c.getOrderId());
			stmt.setInt(1, c.getOrderQuantity());
			stmt.setFloat(2, c.getOrderSubTotal());
			stmt.setLong(3, c.getOrderId());
			stmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void saveOrderAddr(ShopCart c){
		Connection con = ConnectionFactory.getConnObject();
		
		String updateAddrQuery = "UPDATE AL_ORDER_HISTORY SET DeliveryAddress = ? WHERE OrderId = ?";
		try{
			PreparedStatement stmt = con.prepareStatement(updateAddrQuery);
			System.out.println("in shop cart curd for updating the address: " + c.getOrderId());
			stmt.setString(1, c.getDeliveryAddress());
			stmt.setLong(2,c.getOrderId());
			stmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<ShopCart> saveOrder(ShopCart c){
		Connection con = ConnectionFactory.getConnObject();
		ArrayList<ShopCart> cartItems = new ArrayList<ShopCart>();
		
		try{
			System.out.println("Update order history in crud");
			
			//Update Order history table
			String updateOrderQuery = "UPDATE AL_ORDER_HISTORY SET PaymentId = ?, OrderStatus = 'New' WHERE OrderId = ?";
			PreparedStatement stmt = con.prepareStatement(updateOrderQuery);
			System.out.println("in shop cart curd for updating the address: " + c.getOrderId());
			stmt.setLong(1, c.getPaymentId());
			//java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			stmt.setLong(2,c.getOrderId());
			stmt.executeUpdate();
			
			System.out.println("Update cart history in crud");
			//Update Shop cart table
			String updateCartQuery = "UPDATE AL_SHOP_CART SET ActiveCart = 'N' WHERE OrderId = ?";
			PreparedStatement stmtCart = con.prepareStatement(updateCartQuery);
			System.out.println("in shop cart curd for updating the address: " + c.getOrderId());
			stmtCart.setLong(1,c.getOrderId());
			stmtCart.executeUpdate();
			
			//Update inventory table unitsold
			String selectCartQuery = "SELECT SC.Quantity, SC.InventoryId, SC.TotalPrice, INV.VendorId "
					+ " FROM AL_SHOP_CART SC INNER JOIN AL_INVENTORY INV ON SC.InventoryId = INV.InventoryId "
					+" WHERE OrderId = ?";
			
			PreparedStatement selStmt = con.prepareStatement(selectCartQuery);
			System.out.println("in shop cart curd: " + c.getOrderId());
			selStmt.setLong(1, c.getOrderId());
			ResultSet res = selStmt.executeQuery();
			
			while(res.next()){
				ShopCart item = new ShopCart();
				System.out.println("Calculate the total price and units");
				item.setInventoryId(Long.parseLong(res.getString("InventoryId")));
				item.setVendorId(Long.parseLong(res.getString("VendorId")));
				item.setInvQuantity(Integer.parseInt(res.getString("Quantity")));
				item.setInvTotalPrice(Float.parseFloat(res.getString("TotalPrice")));
				cartItems.add(item);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return cartItems;
	}
	
	public boolean saveRevenue(ShopCart c){
		Connection con = ConnectionFactory.getConnObject();
		
		String updateUnitQuery = "UPDATE AL_INVENTORY SET UnitSold = UnitSold+? WHERE InventoryId = ?";
		try{
			//Updating the unit sold column in the inventory table
			PreparedStatement stmt = con.prepareStatement(updateUnitQuery);
					System.out.println("in shop cart curd for updating the the unit sold: " + c.getInventoryId());
					stmt.setInt(1, c.getInvQuantity());
					stmt.setLong(2,c.getInventoryId());
					stmt.executeUpdate();
			
		  //Saving the revenue earned by the vendor
			String insertProdSQL = "INSERT INTO AL_VENDOR_REVENUE (VendorRevenueId, VendorId, Profit) VALUES " + "(?,?,?)";
					Long VendorRevenueId = AppConstants.REVIDSEQ;
					Long newRevId = UserCrud.fetchLatestId("AL_VENDOR_REVENUE", "VendorRevenueId");
					
					if(VendorRevenueId!= 0L){
						VendorRevenueId = newRevId;
					}
					PreparedStatement insertStmt = con.prepareStatement(insertProdSQL);
					insertStmt.setLong(1, VendorRevenueId);
					insertStmt.setLong(2, c.getVendorId());
					insertStmt.setFloat(3, c.getInvTotalPrice());
					insertStmt.executeUpdate();
					con.close();
		  return true;		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		 return false;	
	}
	
public ArrayList<ShopCart> fetchOrderHistory(long userId){
	
		Connection con = ConnectionFactory.getConnObject();
		ArrayList<ShopCart> orders = new ArrayList<ShopCart>();
		
		try{
			//Get the order history for the logged in user
			String updateUnitQuery = "SELECT OrderId, UserId, OrderTotal, date(OrderDate) AS OrderDate, OrderStatus FROM AL_ORDER_HISTORY WHERE UserId = ?";
			
			PreparedStatement stmt = con.prepareStatement(updateUnitQuery);
					System.out.println("in shop cart curd for fetching order hisory: ");
					stmt.setLong(1, userId);
					ResultSet res=stmt.executeQuery();
					
					while(res.next()){
						ShopCart cart = new ShopCart();
						cart.setOrderId(Long.parseLong(res.getString("OrderId")));
						cart.setOrderDate((res.getString("OrderDate")));
						cart.setOrderSubTotal(Float.parseFloat(res.getString("OrderTotal")));
						cart.setOrderStatus(res.getString("OrderStatus"));
						orders.add(cart);
					}	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return orders;	
	}
}