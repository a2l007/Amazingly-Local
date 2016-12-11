package com.iu.amazelocal.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iu.amazelocal.db.InventoryCrud;
import com.iu.amazelocal.db.PasswordAuth;
import com.iu.amazelocal.db.PaymentCrud;
import com.iu.amazelocal.db.RatingCrud;
import com.iu.amazelocal.db.RecipeCrud;
import com.iu.amazelocal.db.RecipeDetailCrud;
import com.iu.amazelocal.db.ShopCartCrud;
import com.iu.amazelocal.db.UserCrud;
import com.iu.amazelocal.db.VendorCrud;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.models.InventoryGrid;
import com.iu.amazelocal.models.InventoryMini;
import com.iu.amazelocal.models.JqGridData;
import com.iu.amazelocal.models.Login;
import com.iu.amazelocal.models.LoginDao;
import com.iu.amazelocal.models.Payment;
import com.iu.amazelocal.models.ProductApprovalGrid;
import com.iu.amazelocal.models.ProductApprovalGridData;
import com.iu.amazelocal.models.ProductSubTypes;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.models.Rating;
import com.iu.amazelocal.models.Recipe;
import com.iu.amazelocal.models.SaleApprovalGrid;
import com.iu.amazelocal.models.SaleGridData;
import com.iu.amazelocal.models.ShopCart;
import com.iu.amazelocal.models.UserGridData;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.UsersDao;
import com.iu.amazelocal.models.VendorGridData;
import com.iu.amazelocal.models.Vendors;
import com.iu.amazelocal.models.cart;
import com.iu.amazelocal.utils.AppConstants;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Controller
public class TestController {
	
	@Autowired
	private HttpSession httpSession;
	private static final String sessionExists="sessionExists";
	private static final String userName="userName";
	private static final String userEmail="userEmail";
	private static final String userType="userType";
	private static final String userId="userId";
	private static final String SessionOrderId="SessionOrderId";

	@RequestMapping(method = RequestMethod.POST, value="/save")
	  @Transactional
	  public String create(String fname, String lname, String email, 
			  Long phoneno, String address,String password,String securityq, String securitya,String usertype) {
    	Users user = new Users(fname, lname,email,phoneno,address);

	    try {
	    	
	    	System.out.println("User type is"+ usertype);
	    	UserCrud usersDao=new UserCrud();
	    	usersDao.insertCustomer(user,password,securityq,securitya);

	    }
	    catch(Exception ex) {
	    	System.out.println("Error"+ex.getMessage());
	    	ex.printStackTrace();
	      return ex.getMessage();
	    }
	    return "login";
	  }
	
	@RequestMapping(method = RequestMethod.POST, value="/savevendor")
	  @Transactional
	  public String createVendor(String vendorname,String email, 
			  Long phoneno, String mailingaddress,String password,String securityq, String securitya,String usertype,
			  String farmaddress) {
  	Vendors vendor = new Vendors(vendorname, email,phoneno,mailingaddress,farmaddress);

	    try {
	    	
	    	System.out.println("User type is"+ usertype);
	    	UserCrud usersDao=new UserCrud();
	    	usersDao.insertVendor(vendor,password,securityq,securitya);

	    }
	    catch(Exception ex) {
	    	System.out.println("Error"+ex.getMessage());
	    	ex.printStackTrace();
	      return ex.getMessage();
	    }
	    return "login";
	  }
	
	@RequestMapping(method = RequestMethod.POST, value="/login")
	  
	  @Transactional
	  public String checkUser(String emailAddress,String password) {
  	
	    PasswordAuth auth=new PasswordAuth();
	    UserCrud user = new UserCrud();
	    if(auth.login(emailAddress, password)){
	    	System.out.println("Login successful");
	    	httpSession.setAttribute(sessionExists, true);
	    	httpSession.setAttribute(userName,emailAddress);
	    	httpSession.setAttribute(userType, user.getUserTypeFromEmail(emailAddress));
	    	httpSession.setAttribute(userId, user.getUserIdFromEmail(emailAddress));

		    return "indexsession";
	    }
	    else
	    	return "loginfail";
	  }
	
	@RequestMapping(method = RequestMethod.POST, value="/forgotpassword")
	  
	  @Transactional
	  @ResponseBody

	  public String retrieveSecurityQuestion(String email) {
	
	    PasswordAuth auth=new PasswordAuth();
	    String question=auth.fetchSecurityQuestion(email);
	    return question;
	  }
	
	@RequestMapping(method = RequestMethod.POST, value="/verifyanswer")
	  @Transactional
	  @ResponseBody

	  public String verifySecurityAnswer(String email, String securityanswer) {
	
	    PasswordAuth auth=new PasswordAuth();
	    System.out.println("Email is"+securityanswer);
	    if(auth.validateAnswer(email,securityanswer)){
	    	httpSession.setAttribute(userEmail,email);
	    	return "Y";
	    }
	    else
	    	return "N";
	  }
	@RequestMapping(method = RequestMethod.POST, value="/resetPassword")
	  @Transactional
	  @ResponseBody
	  public String resetPassword(String password) {
		String test=(String)httpSession.getAttribute(userEmail);
		System.out.println("Session is"+test);
	    return "Ptre";
	  }
	
	@RequestMapping(method = RequestMethod.POST, value="/testsession")
	  @Transactional
	  @ResponseBody

	  public String testSession() {
		System.out.println("Session is"+httpSession.getAttribute(userName));
	    return "Ptre";
	  }
	
	@RequestMapping(method = RequestMethod.POST, value="/changepassword")
	  @Transactional
	  @ResponseBody

	  public String changePassword(String exisPass,String newPass) {
		 PasswordAuth auth=new PasswordAuth();
		 System.out.println("Existing password is"+ exisPass);
		 System.out.println("New password is"+ newPass);

		 if(auth.login((String)httpSession.getAttribute(userName), exisPass)){
			 UserCrud.changePassword((String)httpSession.getAttribute(userName), newPass);
			    return "Y";
		 }
		 else{
			 return "N";
		 }
	  }
	   
     @RequestMapping(method = RequestMethod.POST, value = "/addrecipe")
    public String addNewRecipe(String RecipeName, String ingredients, String instructions, 
    		@RequestParam("RecipeImage") MultipartFile file, String description) throws IOException {
    	 System.out.println("Vendor ID is"+ (String)httpSession.getAttribute(userName));
    	    
    	    String fileName = new StringBuffer("images/").append(file.getOriginalFilename()).toString();
    	    File convFile = new File(new StringBuffer(AppConstants.IMAGELOCATION).append(fileName).toString());
        	System.out.println("Image location"+convFile.getAbsolutePath());
       	    convFile.createNewFile(); 
        	File tempFile = new File(new StringBuffer(AppConstants.TEMPLOCATION).append(fileName).toString());
       	    System.out.println(convFile.getAbsolutePath());
       	    FileOutputStream fos = new FileOutputStream(convFile); 
       	    FileOutputStream tos = new FileOutputStream(tempFile); 
       	    fos.write(file.getBytes());
       	    tos.write(file.getBytes());
       	    fos.close();
       	    tos.close();
       	 Recipe recip=new Recipe(RecipeName,ingredients,instructions,fileName,description);
       	 RecipeCrud rC=new RecipeCrud();
       	 rC.insertRecipe(recip);
    	 return "recipe";
     }
     @RequestMapping(method = RequestMethod.GET, value = "/logout")
     public String doLogout(){
    	 httpSession.invalidate();
    	 return "logout";
     }	
     
     @RequestMapping(method = RequestMethod.POST, value = "/search")
     public String search(String searchStr,String criteria, HttpServletRequest request) throws IOException {
     	/*
     	 * Need to search for ProductName in AL_PRODUCTS, VendorName in AL_VENDORS, TypeName in AL_PRODUCT_TYPE
     	 * Everything is finally linked to the AL_INVENTORY table 
     	 * AL_VENDORS: VendorName:VendorId > AL_INVENTORY
     	 * AL_PRODUCTS: ProductName: ProductId > AL_INVENTORY
     	 * AL_PRODUCT_TYPE:TypeName:ProductTypeId > AL_PRODUCTS : ProductId > AL_INVENTORY
     	 */
    	 InventoryCrud inv=new InventoryCrud();
    	 ArrayList<InventoryMini> searchResults=new ArrayList<InventoryMini>();
    	 //find Inventory from AL_VENDORS;
    	 if(criteria.equals("All")){
    		 searchResults=inv.fetchProductWithoutType(searchStr);
    	 }
    	 else {
    		 System.out.println("Here2");
    		 searchResults=inv.fetchProductsByType(criteria,searchStr);

    	 }
    	 request.setAttribute("searchresults", searchResults);
    	// System.out.println(searchResults.get(0).getProductName());
    	// return "searchresults";
    	 return "searchrst";
}
     
     @RequestMapping("/hello")
     public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
         model.addAttribute("name", name);
         return "hello";
     }
     @RequestMapping(method = RequestMethod.GET, value = "/category")
     public String fetchProductInfo(@RequestParam("subtype") String subtype) throws IOException {
    	 InventoryCrud inv=new InventoryCrud();
    	 ArrayList<InventoryMini> searchResults=new ArrayList<InventoryMini>();
    	 //find Inventory from AL_VENDORS;
    	 searchResults=inv.listProductsByType(subtype);
    	 System.out.println("Size is "+searchResults.size());
    	 httpSession.setAttribute("displayresults", searchResults);
    	// System.out.println(searchResults.get(0).getProductName());
    	 return "categoryresults";
      }
     
     @RequestMapping(method = RequestMethod.GET, value = "/getInventories")
     @ResponseBody        
     public String getAllInventories(
    	     @RequestParam("_search") Boolean _search,
    	     @RequestParam("nd") String nd, 
    	     @RequestParam("rows") int rows, 
    	     @RequestParam("page") int page, 
    	     @RequestParam("sidx") String sidx, 
    	     @RequestParam("sord") String sord) {
		 ArrayList<InventoryGrid> inventories = new ArrayList<InventoryGrid>();
		 String products = null;
	    try {
	    	 InventoryCrud inventory=new InventoryCrud();
	    	 long vendorId=(long)httpSession.getAttribute(userId);
	    	 inventories = inventory.fetchInventories(vendorId);
	    	 int totalPages = (int) Math.floor((inventories.size()/rows)+1);
	    	 JqGridData gridData = new JqGridData(totalPages, page,inventories.size(),inventories);
	    	 ModelMap model = new ModelMap();
	    	 model.put("products", inventories);
	    	 ObjectMapper mapper = new ObjectMapper();
	    	 products = mapper.writeValueAsString(gridData);
	    	 return products; 
	    }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
       }
    }
	 
 @RequestMapping(method = RequestMethod.GET, value="/fetchTypes")
	  @ResponseBody
	  public String[] getProductTypes() {
		 ArrayList<ProductType> type = new ArrayList<ProductType>();
		 ArrayList<ProductSubTypes> subType = new ArrayList<ProductSubTypes>();
		 String productTypes = null;
		 String productSubTypes = null;
	    try {
	    	 InventoryCrud inventory=new InventoryCrud();
	    	 type = inventory.fetchProductTypes();
	    	 subType = inventory.fetchProductSubTypes();
	    	 ObjectMapper mapper = new ObjectMapper();
	    	 productTypes = mapper.writeValueAsString(type);
	    	 productSubTypes = mapper.writeValueAsString(subType);
	    	 System.out.println(productSubTypes);
	    }
	    catch(Exception ex) {
	    	System.out.println("Error"+ex.getMessage());
	    	ex.printStackTrace();
	      return new String [] { "Error Message", ex.getMessage()};
	    }
	    return new String [] { productTypes, productSubTypes};
	} 
 	 
	 @RequestMapping(method = RequestMethod.POST, value = "/saveProduct")
     public String saveNewProduct(String prodname, String desc, String prodcat, String prodsubcat, 
    		 String subcatname, String price, String quantity, String unit, String cal, String salepercent,
    		 @RequestParam("uploadfile") MultipartFile[] file) throws IOException {
	   try{
		   System.out.println("Here in saveproduct");
		   String fileName = null;
		   StringBuffer fileNames = new StringBuffer("");
	    	String msg = "";
	    	String filePath = new File(".").getCanonicalPath();
	    	System.out.println(filePath+" filePathe");
			if (file != null && file.length >0) {
	    		for(int i =0 ;i< file.length; i++){
	    			
		            	
	    			fileName = new StringBuffer("images/").append(file[i].getOriginalFilename()).toString();
	    			System.out.println("Filename is"+fileName);
	    			if(i>0){
	                	fileNames.append(",");
	                }
	    			fileNames.append(fileName); 
		                
		            	File convFile = new File(new StringBuffer(AppConstants.IMAGELOCATION).append(fileName).toString());
		            	System.out.println("Image location"+convFile.getAbsolutePath());
		           	    convFile.createNewFile(); 
		            	File tempFile = new File(new StringBuffer(AppConstants.TEMPLOCATION).append(fileName).toString());
		           	    System.out.println(convFile.getAbsolutePath());
		           	    FileOutputStream fos = new FileOutputStream(convFile); 
		           	    FileOutputStream tos = new FileOutputStream(tempFile); 
		           	    fos.write(file[i].getBytes());
		           	    tos.write(file[i].getBytes());
		           	    fos.close();
		           	    tos.close();
		                System.out.println("You have successfully uploaded " + fileName +"<br/>");
	    		}
			}
			
   	    long subCatId = Long.parseLong(prodsubcat);
   	    InventoryCrud inventory = new InventoryCrud();
   	 
   	    //New product sub category is to be added.
   	    if(Long.parseLong(prodsubcat) == -1){
   	    	ProductSubTypes newSubType = new ProductSubTypes(Integer.parseInt(prodcat), Integer.parseInt(prodsubcat), subcatname);
   	    	subCatId = inventory.insertProductSubType(newSubType);
   	    }
   	  
	   Inventory product = new Inventory(prodname, desc, subCatId, Float.parseFloat(price), 
			   Integer.parseInt(quantity), unit, Float.parseFloat(cal), Float.parseFloat(salepercent),fileNames.toString()); 
	  
  	   inventory.insertProduct(product);
  	   	   return "Inventory";
	   }
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();   
	   }
	 }
	 
	 //Method for editing the product details
	 @RequestMapping(method = RequestMethod.GET, value = "/edit")     
	    public void getInventoryDetails(
	            @RequestParam(value = "id", required=false) int invId,
	            HttpServletRequest request, HttpServletResponse response ) {
	        System.out.println("The id is: "+ invId);
	              Inventory invDetails = new Inventory();
	        try {
	             InventoryCrud inventory=new InventoryCrud();
	             invDetails = inventory.fetchInventoryDetails(invId);
	             request.setAttribute("inv", invDetails ); 
	             RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edititem.jsp");
	             requestDispatcher.forward(request, response);
	        }
	      catch(Exception ex){
	          System.out.println("Error" + ex.getMessage());
	          ex.printStackTrace();
	      }
	        return;
	   }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/viewrecipe")
     public String viewrecipe(
             //@RequestParam(value = "RecipeId", required=false) Long RecipeId,
             HttpServletRequest request) {
             long RecipeId = Long.parseLong(request.getParameter("RecipeId"));
             Recipe rec = new Recipe();
             RecipeDetailCrud recipedetail=new RecipeDetailCrud();
             rec = recipedetail.displayRecipe(RecipeId);
             httpSession.setAttribute("Rec", rec);
        return "viewrecipe";
}
	 @RequestMapping(method = RequestMethod.GET, value = "/cart")
     public String AddToCart(@RequestParam(value = "inventoryId", required=false) int invId, HttpServletRequest request) {
		 try {
		 System.out.println("Inside add to cart controller");
             ShopCart cart = new ShopCart();
             cart.setInventoryId(invId);
             cart.setUserId((Long)httpSession.getAttribute(userId));
             ShopCartCrud order =new ShopCartCrud();
             System.out.println("User ID: "+cart.getUserId() + " InventoryId : "+cart.getInventoryId());
             order.insertOrder(cart);
             }
	       catch(Exception ex){
	           System.out.println("Error"+ex.getMessage());
	           ex.printStackTrace();
	           return ex.getMessage();   
	       }
		 
        return "cart";
}

@RequestMapping(method = RequestMethod.GET, value = "/orderdetails")
    public void fetchOrderCartItems(@RequestParam(value = "orderId", required=false) int ordId,
            HttpServletRequest request, HttpServletResponse response) {
         try {
            ShopCart cart = new ShopCart();
            ArrayList<ShopCart> cartItems = new ArrayList<ShopCart>();
            cart.setOrderId(ordId);
            cart.setUserId((Long)httpSession.getAttribute(userId));
            ShopCartCrud order =new ShopCartCrud();
            cartItems = order.fetchOrderCart(cart);
            request.setAttribute("orderItems", cartItems ); 
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/orderdetails.jsp");
            requestDispatcher.forward(request, response);

            }
           catch(Exception ex){
               System.out.println("Error"+ex.getMessage());
               ex.printStackTrace();
           }
         
       return ;
}
	 @RequestMapping(method = RequestMethod.GET, value = "/getSaleApprovals")
     @ResponseBody        
     public String getAllSaleApprovals(
    	     @RequestParam("_search") Boolean _search,
    	     @RequestParam("nd") String nd, 
    	     @RequestParam("rows") int rows, 
    	     @RequestParam("page") int page, 
    	     @RequestParam("sidx") String sidx, 
    	     @RequestParam("sord") String sord) {
		 ArrayList<SaleApprovalGrid> inventories = new ArrayList<SaleApprovalGrid>();
		 String products = null;
	    try {
	    	 InventoryCrud inventory=new InventoryCrud();
	    	 inventories = inventory.fetchApprovalList();
	    	 int totalPages = (int) Math.floor((inventories.size()/rows)+1);
	    	 SaleGridData gridData = new SaleGridData(totalPages, page,inventories.size(),inventories);
	    	 ModelMap model = new ModelMap();
	    	 model.put("products", inventories);
	    	 ObjectMapper mapper = new ObjectMapper();
	    	 products = mapper.writeValueAsString(gridData);
	    	 return products; 
	    }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
       }
    }
	 @RequestMapping(method = RequestMethod.POST, value = "/approvesale")
     public String approveSale(@RequestParam("inventoryids[]") int[] inventoryids) throws IOException {
		 InventoryCrud ic=new InventoryCrud();
		 ic.approveSales(inventoryids);
     	System.out.println("Invrntory is"+inventoryids[0]);
     	return "saleapproval";
	 }
	 @RequestMapping(method = RequestMethod.GET, value = "/getuserlist")
     @ResponseBody        
     public String getUserList(
    	     @RequestParam("_search") Boolean _search,
    	     @RequestParam("nd") String nd, 
    	     @RequestParam("rows") int rows, 
    	     @RequestParam("page") int page, 
    	     @RequestParam("sidx") String sidx, 
    	     @RequestParam("sord") String sord) {
		 ArrayList<Users> userList = new ArrayList<Users>();
		 String products = null;
	    try {
	    	 UserCrud user=new UserCrud();
	    	 userList = user.fetchUserList();
	    	 int totalPages = (int) Math.floor((userList.size()/rows)+1);
	    	 UserGridData gridData = new UserGridData(totalPages, page,userList.size(),userList);
	    	 ModelMap model = new ModelMap();
	    	 model.put("products", userList);
	    	 ObjectMapper mapper = new ObjectMapper();
	    	 products = mapper.writeValueAsString(gridData);
	    	 return products; 
	    }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
       }
    }

	 @RequestMapping(method = RequestMethod.GET, value = "/getvendorlist")
     @ResponseBody        
     public String getVendorList(
    	     @RequestParam("_search") Boolean _search,
    	     @RequestParam("nd") String nd, 
    	     @RequestParam("rows") int rows, 
    	     @RequestParam("page") int page, 
    	     @RequestParam("sidx") String sidx, 
    	     @RequestParam("sord") String sord) {
		 ArrayList<Vendors> vendorList = new ArrayList<Vendors>();
		 String products = null;
	    try {
	    	 VendorCrud vendor=new VendorCrud();
	    	 vendorList = vendor.fetchVendorList();
	    	 int totalPages = (int) Math.floor((vendorList.size()/rows)+1);
	    	 VendorGridData gridData = new VendorGridData(totalPages, page,vendorList.size(),vendorList);
	    	 ModelMap model = new ModelMap();
	    	 model.put("products", vendorList);
	    	 ObjectMapper mapper = new ObjectMapper();
	    	 products = mapper.writeValueAsString(gridData);
	    	 return products; 
	    }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
       }
    }
	 
	 //Method for 
	 @RequestMapping(method = RequestMethod.POST, value = "/updateProduct")
	    public String updateProduct(String prodname, String invId, String desc, String prodcat, String prodsubcat, 
	            String subcatname, String price, String quantity, String imagecb, String unit, String cal, 
	            String salepercent, @RequestParam("uploadfile") MultipartFile file) throws IOException {
	       try{
	           String imageName = null;
	           
	           if(!file.isEmpty()){
	                   File convFile = new File(file.getOriginalFilename());
	                convFile.createNewFile(); 
	                FileOutputStream fos = new FileOutputStream(convFile); 
	                fos.write(file.getBytes());
	                imageName = convFile.getName(); 
	                fos.close();
	           }else{
	               imageName = imagecb;
	           }

	          long subCatId = Long.parseLong(prodsubcat);
	          InventoryCrud inventory = new InventoryCrud();
	       
	          //New product sub category is to be added.
	          if(Long.parseLong(prodsubcat) == -1){
	              ProductSubTypes newSubType = new ProductSubTypes(Integer.parseInt(prodcat), Integer.parseInt(prodsubcat), subcatname);
	              subCatId = inventory.insertProductSubType(newSubType);
	          }
	        
	      Inventory product = new Inventory(prodname, desc, subCatId, Float.parseFloat(price), 
	               Integer.parseInt(quantity), unit, Float.parseFloat(cal), Float.parseFloat(salepercent),imageName); 
	      product.setInventoryId(Long.parseLong(invId));
	      product.setVendorId((long)httpSession.getAttribute(userId));
	      
	       if(inventory.updateProduct(product)){
	               return "redirect:Inventory.html";
	       }
	       else{
	           return"Could not update product details!";
	       }
	     }
	       catch(Exception ex){
	           System.out.println("Error"+ex.getMessage());
	           ex.printStackTrace();
	           return ex.getMessage();   
	       }
	       
	     }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/deleteItem")
     public String deleteCartItem(HttpServletRequest request) throws IOException {
	   try{
		   long cartId = Long.parseLong(request.getParameter("cartId"));
		   long orderId = Long.parseLong(request.getParameter("orderId"));
		   
		   //get userid from session
		   long uId =(long)httpSession.getAttribute(userId);
		   ShopCartCrud cart = new ShopCartCrud();
		   ShopCart cartItem = new ShopCart(cartId, orderId, uId);
		   cart.deleteCartItem(cartItem);
		  }
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();
	   }
	   return "cart";
	 }
	 
	 @RequestMapping(value = "/updateCart", headers="Accept=*/*", consumes="application/json", method = RequestMethod.POST)
     public String updateCartDetails(@RequestBody String cartDetails) {
	   try{
		   ObjectMapper objectMapper = new ObjectMapper();
		   JsonFactory factory = new JsonFactory();
		   JsonParser  parser  = factory.createParser(cartDetails);
		  ArrayList<cart> cartItems = objectMapper.readValue(parser, new TypeReference<ArrayList<cart>>(){});
	
		   ShopCartCrud cart = new ShopCartCrud();
		   
		   float orderTotal = 0;
		   int orderQuantity = 0;
		   long orderId = 0;
		   
           for(int i = 0; i < cartItems.size(); i++){
   			System.out.println("in shop cart curd: " + cartItems.get(i).getCartId());
   			   ShopCart cartItem = new ShopCart(); 
   			   System.out.println("Total price inside the loop"+ cartItems.get(i).getTotalPrice());
   			   cartItem = cart.updateCartQuantity(cartItems.get(i));
   			   orderId = cartItems.get(i).getOrderId();
   			   orderTotal += cartItem.getInvTotalPrice();
   			   orderQuantity += cartItem.getInvQuantity();
           }
           
           ShopCart order = new ShopCart(); 
           order.setOrderId(orderId);
           order.setOrderQuantity(orderQuantity);
           order.setOrderSubTotal(orderTotal);
           cart.updateOrderHistory(order);
		   return "cart.jsp";
		  }
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();
	   }
	 }
	 
	 @RequestMapping(value = "/saveAddress", headers="Accept=*/*", consumes="application/json", method = RequestMethod.POST)
     public String saveAddress(@RequestBody String orderAddr) {
	   try{
		   ShopCartCrud cart = new ShopCartCrud(); 
		   ShopCart order = new ShopCart();
		   order.setDeliveryAddress(orderAddr);
		   //Get orderid from session
		   order.setOrderId((long)httpSession.getAttribute(SessionOrderId));
        	   cart.saveOrderAddr(order);
		   return "Payment.jsp";
		  }
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();
	   }
	 }
	 
	 @RequestMapping(value = "/saveNewCard", headers="Accept=*/*", consumes="application/json", method = RequestMethod.POST)
     public String saveNewCard(@RequestBody String cardDetails) {
	   try{
		   System.out.println("Hello! saveAddress inside the controller");
		   
		   System.out.println("This is the address in the saveAddress method controller "+ cardDetails);
		   
		   PaymentCrud card = new PaymentCrud(); 
		   
		   String[] paymentCardDetails = cardDetails.split("~");
		   
		   long uId = (long)httpSession.getAttribute(userId);
		   Payment paymentCard = new Payment(uId, paymentCardDetails[0], paymentCardDetails[1], 
				   paymentCardDetails[2], Integer.parseInt(paymentCardDetails[3]),
				   Integer.parseInt(paymentCardDetails[4]));
		   
		   //card.setDeliveryAddress(cardDetails);
		   card.insertCardPayment(paymentCard);
		   return "Payment.jsp";
		  }
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();
	   }
	 }
	 
	 @RequestMapping(value = "/deleteCard", method = RequestMethod.GET)
	 @ResponseBody
     public String deleteCard(HttpServletRequest request) throws IOException {
	   try{
		   long paymentId = Long.parseLong(request.getParameter("paymentId"));
		   
		   //get userid from session
		   long uId = (long)httpSession.getAttribute(userId);
		   
		   PaymentCrud card = new PaymentCrud();
		   Payment paymentCard = new Payment(paymentId, uId);
		   
		   if(card.deletePaymentCard(paymentCard)){
			 return "success";  
		   }
		   return "error";
		  }
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();
	   }
	 }
	 
	 
	 @RequestMapping(value = "/saveOrder", method = RequestMethod.GET)
     public String saveOrder(HttpServletRequest request) throws IOException {
	   try{
		   long paymentId = Long.parseLong(request.getParameter("paymentId")); 
		   System.out.println("This is the saveOrder method controller: "+ paymentId);
		   
		   ShopCartCrud cart = new ShopCartCrud(); 
		   ShopCart order = new ShopCart();
		   order.setPaymentId(paymentId);
		   
		   //Get orderid from session
		   order.setOrderId((long)httpSession.getAttribute(SessionOrderId));
		   //Update SHop cart and order history table
		   ArrayList<ShopCart> cartItems = cart.saveOrder(order);
		   
		   //Update inventory and vendor revenue table
		   for(int i = 0; i < cartItems.size(); i++){
			  if(!cart.saveRevenue(cartItems.get(i))){
				  System.out.println("Save revenue: error");
	   			  return "error";
	   		   }
	       }
		}
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();
	   }

      return "CustomerOrders";
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/getVendorSaleApprovals")
     @ResponseBody        
     public String getAllVendorSaleApprovals(
    	     @RequestParam("_search") Boolean _search,
    	     @RequestParam("nd") String nd, 
    	     @RequestParam("rows") int rows, 
    	     @RequestParam("page") int page, 
    	     @RequestParam("sidx") String sidx, 
    	     @RequestParam("sord") String sord) {
		 ArrayList<SaleApprovalGrid> inventories = new ArrayList<SaleApprovalGrid>();
		 String products = null;
	    try {
	    	 InventoryCrud inventory=new InventoryCrud();
	    	 inventories = inventory.fetchApprovalList();
	    	 int totalPages = (int) Math.floor((inventories.size()/rows)+1);
	    	 SaleGridData gridData = new SaleGridData(totalPages, page,inventories.size(),inventories);
	    	 ModelMap model = new ModelMap();
	    	 model.put("products", inventories);
	    	 ObjectMapper mapper = new ObjectMapper();
	    	 products = mapper.writeValueAsString(gridData);
	    	 return products; 
	    }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
       }
    }
	 @RequestMapping(method = RequestMethod.GET, value = "/rating")
     public String directrating(
             HttpServletRequest request) {
             long inventoryId = Long.parseLong(request.getParameter("inventoryId"));    
             System.out.println("inventoryiD"+inventoryId);
             httpSession.setAttribute("id", inventoryId);
        return "rating";
     }
	 
	@RequestMapping(method = RequestMethod.POST, value = "/rate")
	    public String addrating(long inventoryId, long rating, String comment) throws IOException {
		int UserId=(Integer)httpSession.getAttribute(userId);
		//System.out.println("inventory is id"+UserId);
	       	 Rating rat=new Rating(inventoryId,UserId,rating,comment);
	       	 RatingCrud rC=new RatingCrud();
	       	 rC.insertRating(rat);
	    	 return "rating";
	     }
	 
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/viewproduct")
     public String viewproduct(HttpServletRequest request) {
             int inventoryId = Integer.parseInt(request.getParameter("inventoryId"));
             System.out.println("Inventoryid"+inventoryId);
             Inventory item = new Inventory();
             InventoryCrud prod=new InventoryCrud();
             item = prod.fetchInventoryDetails(inventoryId);
             System.out.println("Item"+item);
             httpSession.setAttribute("inventoryId", item);
             ArrayList<Rating> ratlist=new ArrayList<Rating>();
             Rating rat=new Rating();
             RatingCrud ratings=new RatingCrud();
             ratlist=ratings.displayRating(inventoryId);
             System.out.println(rat.getRating());
             httpSession.setAttribute("ratings",ratlist);
        return "viewproduct";
	 }
	 @RequestMapping(method = RequestMethod.GET, value = "/getProductApprovals")
     @ResponseBody        
     public String getProductApprovals(
    	     @RequestParam("_search") Boolean _search,
    	     @RequestParam("nd") String nd, 
    	     @RequestParam("rows") int rows, 
    	     @RequestParam("page") int page, 
    	     @RequestParam("sidx") String sidx, 
    	     @RequestParam("sord") String sord) {
		 ArrayList<ProductApprovalGrid> inventories = new ArrayList<ProductApprovalGrid>();
		 String products = null;
		 long vendorId=(long)httpSession.getAttribute(userId);
	    try {
	    	 InventoryCrud inventory=new InventoryCrud();
	    	 inventories = inventory.fetchProductApprovalList(vendorId);
	    	 System.out.println("Inventory size is"+inventories.size());
	    	 int totalPages = (int) Math.floor((inventories.size()/rows)+1);
	    	 ProductApprovalGridData gridData = new ProductApprovalGridData(totalPages, page,inventories.size(),inventories);
	    	 ModelMap model = new ModelMap();
	    	 model.put("products", inventories);
	    	 ObjectMapper mapper = new ObjectMapper();
	    	 products = mapper.writeValueAsString(gridData);
	    	 System.out.println("Products are "+products);
	    	 return products; 
	    }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
       }
    }
	 @RequestMapping(method = RequestMethod.POST, value = "/processpurchase")
     public String processPurchase(@RequestParam("saleIds[]") int[] saleids) throws IOException {
		 InventoryCrud ic=new InventoryCrud();
		 ic.processProducts(saleids);
     	System.out.println("Invrntory is"+saleids[0]);
     	return "vendorprodapproval";
	 }
}