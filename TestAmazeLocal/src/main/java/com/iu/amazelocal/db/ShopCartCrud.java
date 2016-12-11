package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Inventory;
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
				//cart.setVendorId(Long.parseLong(res.getString("VendorId")));
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
			String countItemsQuery = "(SELECT SC.CartId, TotalPrice, Quantity, OrderTotal, OrderQuantity FROM AL_SHOP_CART SC"
					+" INNER JOIN AL_ORDER_HISTORY OH ON SC.OrderId = OH.OrderId WHERE OH.OrderId = ? AND SC.CartId = ? AND OH.UserId = ?);";
		
			PreparedStatement stmt = con.prepareStatement(countItemsQuery);
			stmt.setLong(1, cartItem.getOrderId());
			stmt.setLong(2, cartItem.getCartId());
			stmt.setLong(3, cartItem.getUserId());
			ResultSet res = stmt.executeQuery();
			
			if(res.next()){
				cartItem.setInvTotalPrice(Float.parseFloat(res.getString("TotalPrice")));
				cartItem.setInvQuantity(Integer.parseInt(res.getString("Quantity")));
				cartItem.setOrderSubTotal(Float.parseFloat(res.getString("OrderTotal")));
				cartItem.setOrderQuantity(Integer.parseInt(res.getString("OrderQuantity")));
				
				String deleteItemsQuery = "DELETE FROM  AL_SHOP_CART WHERE CartId = ?; ";
				PreparedStatement delItemStmt = con.prepareStatement(deleteItemsQuery);
				delItemStmt.setLong(1, cartItem.getCartId());
				delItemStmt.executeUpdate();

				float orderSubTotal = cartItem.getOrderSubTotal() - cartItem.getInvTotalPrice();
				cartItem.setOrderQuantity(cartItem.getOrderQuantity() - cartItem.getInvQuantity());
				cartItem.setOrderSubTotal(orderSubTotal);
				updateOrderHistory(cartItem);
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
			System.out.println("fetchOrderDetails");
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
			System.out.println("in shop cart curd: " + c.getOrderId() + " Quantity: "+c.getQuantity() +"Total price: "+c.getTotalPrice());
			stmt.setInt(1, c.getQuantity());
			stmt.setFloat(2, c.getTotalPrice());
			stmt.setLong(3,c.getCartId());
			stmt.setLong(4,c.getOrderId());
			stmt.executeUpdate();
		
			//Calculate the total price and units
			String selectCartQuery = "SELECT INV.InventoryId,  SC.TotalPrice, Sale, SaleApproved, "
					+ " SC.Quantity FROM AL_INVENTORY INV INNER JOIN AL_SHOP_CART SC ON INV.INVENTORYID = SC.INVENTORYID "
					+ " WHERE ActiveCart = 'Y' AND CartId = ? AND OrderId = ?";
			
			PreparedStatement selStmt = con.prepareStatement(selectCartQuery);
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
			System.out.println("\nin shop cart curd for updating theOH:: " + c.getOrderId() +"O Total: "+c.getOrderSubTotal());
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
			String updateUnitQuery = "SELECT DISTINCT OH.OrderId, UserId, OrderTotal, date(OrderDate) AS OrderDate, OrderStatus "
					               +" FROM AL_ORDER_HISTORY OH INNER JOIN AL_SHOP_CART SC ON OH.OrderId = SC.OrderId "
					               + " WHERE UserId = ? AND SC.ActiveCart = 'N'";
			
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

public void insertOrder(ShopCart c) {
    try {
    	Class.forName("com.mysql.jdbc.Driver");
        Connection con = ConnectionFactory.getConnObject();
    	String selectActiveRecQuery = "SELECT OH.OrderId, OH.OrderTotal, OH.OrderQuantity FROM AL_ORDER_HISTORY OH "
    								  +" INNER JOIN AL_SHOP_CART SC ON SC.OrderId = OH.OrderId "
    								  +" WHERE UserId = ? AND ActiveCart = 'Y'";
    	
    	PreparedStatement checkStmt = con.prepareStatement(selectActiveRecQuery);
    	checkStmt.setLong(1, c.getUserId());
    	ResultSet res = checkStmt.executeQuery();
    	float unitPrice = 0;
		float sale =0;
		
		ShopCart cart = new ShopCart();
    	if (res.next()) {
	    	 c.setOrderId(Long.parseLong(res.getString("OrderId")));
	    	 c.setOrderSubTotal(Float.parseFloat(res.getString("OrderTotal")));
	    	 c.setOrderQuantity(Integer.parseInt(res.getString("OrderQuantity")));
	    	 System.out.println("\nInsert order db: Order id:+c.getOrderId()");
	    	 String selectItemQuery = " SELECT SC.CartId, SC.Quantity, INV.Price, SC.TotalPrice, IFNULL(INV.Sale, 0) AS Sale, "
	    			     			 +" INV.SaleApproved FROM AL_SHOP_CART SC INNER JOIN AL_INVENTORY INV ON" 
	    			                 +" SC.InventoryId = INV.InventoryId WHERE OrderId = ? AND INV.InventoryId = ? AND ActiveCart = 'Y'";
	    	 
	     	 PreparedStatement itemStmt = con.prepareStatement(selectItemQuery);
	     	 itemStmt.setLong(1, c.getOrderId());
	     	 itemStmt.setLong(2, c.getInventoryId());
	     	 
	     	 ResultSet itemResult = itemStmt.executeQuery();
	     	 if (itemResult.next()) {
	     		c.setCartId(Long.parseLong(itemResult.getString("CartId")));
	     		c.setInvQuantity(Integer.parseInt(itemResult.getString("Quantity")));
				c.setInvTotalPrice(Float.parseFloat(itemResult.getString("TotalPrice")));
				
				unitPrice = Float.parseFloat(itemResult.getString("Price"));
				sale = Float.parseFloat(itemResult.getString("Sale"));
				
				c.setUnitPrice(unitPrice-((sale/100)*unitPrice));
				
				System.out.println("Item already exists: " + c.getCartId());
				cart item = new cart();
				item.setOrderId(c.getOrderId());
				item.setCartId(c.getCartId());
				item.setQuantity(c.getInvQuantity() + 1);
				item.setTotalPrice(c.getInvTotalPrice() + c.getUnitPrice());
				
				cart = updateCartQuantity(item);
				c.setInvTotalPrice(cart.getInvTotalPrice());
				c.setInvQuantity(cart.getInvQuantity());
	     	 }else{
	     		//Fetch the inventory details
	 			InventoryCrud inv = new InventoryCrud();
	 			Inventory item = new Inventory();
	 			int inventoryId = (int)c.getInventoryId();
	 			item = inv.fetchInventoryDetails(inventoryId);
	 			unitPrice = item.getPrice();
	 			sale = item.getSale();
				 if(item.getSaleApproved() == "Approved"){
					 c.setUnitPrice(unitPrice-((sale/100)*unitPrice));
	                }
	                else{
	                 c.setUnitPrice(unitPrice);
	                }
	     		insertItemInShopCart(c);
	     	 }

				float orderSubtotal = c.getOrderSubTotal() + c.getUnitPrice();
				c.setOrderQuantity(c.getOrderQuantity() + 1);
		        c.setOrderSubTotal(orderSubtotal);
				updateOrderHistory(c);
     	}else{
	     		System.out.println("Inside insertOrder 4th condition");
	     		InventoryCrud inv = new InventoryCrud();
	 			Inventory item = new Inventory();
	 			int inventoryId = (int)c.getInventoryId();
	 			item = inv.fetchInventoryDetails(inventoryId);
	 			unitPrice = item.getPrice();
	 			sale = item.getSale();
				 if(item.getSaleApproved() == "Approved"){
					 c.setUnitPrice(unitPrice-((sale/100)*unitPrice));
	                }
	                else{
	                 c.setUnitPrice(unitPrice);
	                }
	     		cart = insertNewOrder(c);
	     		insertItemInShopCart(cart);
     	}
    } catch (Exception e) {
        System.out.println(e);
    }
}
	
	public int insertItemInShopCart(ShopCart cart) {
		try {

			System.out.println("Inside insertItemInShopCart");
			
			Class.forName("com.mysql.jdbc.Driver");
	        Connection con = ConnectionFactory.getConnObject();
			String insertItemQuery = "INSERT INTO AL_SHOP_CART (CartId, OrderId, InventoryId, Quantity, ActiveCart, TotalPrice)" 
		     	      + "VALUES " + "(?,?,?,?,?,?)";
		     		Long cartId = AppConstants.CARTSEQID;
		            PreparedStatement stmt = con.prepareStatement(insertItemQuery);
		            Long newCartId = UserCrud.fetchLatestId("AL_SHOP_CART", "CartId");
		            if(newCartId != 0L){
		            	cartId = newCartId + 1;
		            }
		            stmt.setLong(1, cartId);
		            stmt.setLong(2, cart.getOrderId());
		            stmt.setLong(3, cart.getInventoryId());
		            stmt.setInt(4, 1);
		            stmt.setString(5, "Y");
		            stmt.setFloat(6, cart.getUnitPrice());
		            stmt.executeUpdate();
		            con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
        	return 0;
	}
	
	public ShopCart insertNewOrder(ShopCart cart) {
		try {
			System.out.println("Inside insertNewOrder");
			Class.forName("com.mysql.jdbc.Driver");
	        Connection con = ConnectionFactory.getConnObject();
			String insertItemQuery = "INSERT INTO AL_ORDER_HISTORY (OrderId, UserId, OrderQuantity, OrderTotal)" 
		     	      + "VALUES " + "(?,?,?,?)";
		     		Long orderId = AppConstants.ORDERSEQID;
		            PreparedStatement stmt = con.prepareStatement(insertItemQuery);
		            Long newOrderId = UserCrud.fetchLatestId("AL_ORDER_HISTORY", "OrderId");
		            
		            if(newOrderId != 0L){
		            	orderId = newOrderId + 1;
		            }
		            System.out.println("Inside insertNewOrder orderId:"+orderId);
	            	cart.setOrderId(orderId);
		            stmt.setLong(1, cart.getOrderId());
		            stmt.setLong(2, cart.getUserId());
		            stmt.setLong(3, 1);
		            stmt.setFloat(4, cart.getUnitPrice());
		            stmt.executeUpdate();
		            con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
        	return cart;
	}
	
	public ArrayList<ShopCart> fetchOrderCart(ShopCart c ) {
        Connection con = ConnectionFactory.getConnObject();
        try {
            System.out.println("fetchCartItems insiseddvcdhjhdsj !!!");
            String selectCartQuery = "SELECT CartId, SC.OrderId, SC.InventoryId, SC.Quantity, ActiveCart, TotalPrice, INV.SaleApproved, "
                    + "INV.Price AS UnitPrice,ProductName, ImageName, IFNULL(INV.Sale, 0) AS Sale, CartUpdateDate, UserId, OH.VendorId, OrderStatus, "
                    + "OrderTotal, OrderDate, INV.Quantity AS InvQuantity, IFNULL(INV.UnitSold, 0) AS UnitSold FROM AL_SHOP_CART SC INNER JOIN AL_ORDER_HISTORY OH ON SC.OrderId = OH.OrderId "
                    + "INNER JOIN AL_INVENTORY INV ON SC.InventoryId = INV.InventoryId WHERE UserId= ? AND SC.OrderId = ?";
            
            PreparedStatement stmt = con.prepareStatement(selectCartQuery);
            stmt.setLong(1,c.getUserId() );
            stmt.setLong(2,c.getOrderId() );
            ResultSet res=stmt.executeQuery();
            
            ArrayList<ShopCart> cartItems = new ArrayList<ShopCart>();
            while(res.next()){
                System.out.println("Result set found inside fetch cartitems");
                ShopCart cart = new ShopCart();
                cart.setCartId(Long.parseLong(res.getString("CartId")));
                cart.setOrderId(Long.parseLong(res.getString("OrderId")));
                cart.setInventoryId(Long.parseLong(res.getString("InventoryId")));
                cart.setUserId(Long.parseLong(res.getString("UserId")));
                cart.setInvQuantity(Integer.parseInt(res.getString("Quantity")));
                cart.setInvTotalPrice(Float.parseFloat(res.getString("TotalPrice")));
                cart.setUnitPrice(Float.parseFloat(res.getString("UnitPrice")));
                cart.setOrderSubTotal(Float.parseFloat(res.getString("OrderTotal")));
                cart.setOrderDate(res.getString("OrderDate"));
                cart.setProductName(res.getString("ProductName"));
                cart.setOrderStatus(res.getString("OrderStatus"));
                cart.setImageName(res.getString("ImageName"));
                cart.setDeliveryAddress(res.getString("DeliveryAddress"));
                
                boolean IsActive = false;
                
                if(res.getString("ActiveCart") != null){
                    IsActive = res.getString("ActiveCart").equals("Y")? true : false;
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
}