package com.iu.amazelocal.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iu.amazelocal.db.InventoryCrud;
import com.iu.amazelocal.db.PasswordAuth;
import com.iu.amazelocal.db.RecipeCrud;
import com.iu.amazelocal.db.RecipeDetailCrud;
import com.iu.amazelocal.db.UserCrud;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.models.InventoryGrid;
import com.iu.amazelocal.models.InventoryMini;
import com.iu.amazelocal.models.JqGridData;
import com.iu.amazelocal.models.Login;
import com.iu.amazelocal.models.LoginDao;
import com.iu.amazelocal.models.ProductSubTypes;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.models.Recipe;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.UsersDao;
import com.iu.amazelocal.models.Vendors;
import com.iu.amazelocal.utils.AppConstants;
import com.fasterxml.jackson.core.JsonGenerationException;
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
    	 File convFile = new File(file.getOriginalFilename());
    	    convFile.createNewFile(); 
    	    FileOutputStream fos = new FileOutputStream(convFile); 
    	    fos.write(file.getBytes());
    	    fos.close();
       	 Recipe recip=new Recipe(RecipeName,ingredients,instructions,convFile.getName(),description);
       	 RecipeCrud rC=new RecipeCrud();
       	 rC.insertRecipe(recip);
    	 return "RecipeSuccess";
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
	    	 int vendorId=(int)httpSession.getAttribute(userId);
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
		           	    convFile.createNewFile(); 
		           	    System.out.println(convFile.getAbsolutePath());
		           	    FileOutputStream fos = new FileOutputStream(convFile); 
		           	    fos.write(file[i].getBytes());
		           	    fos.close();
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
  	   	   return "redirect:Inventory.html";
	   }
	   catch(Exception ex){
		   System.out.println("Error"+ex.getMessage());
	       ex.printStackTrace();
	       return ex.getMessage();   
	   }
	 }
	 @RequestMapping(method = RequestMethod.GET, value = "/edit")     
	    public String getInventoryDetails(
	            @RequestParam(value = "id", required=false) int invId,
	            HttpServletRequest request ) {
	              Inventory invDetails = new Inventory();
	        try {
	             InventoryCrud inventory=new InventoryCrud();
	             invDetails = inventory.fetchInventoryDetails(invId);
	             request.setAttribute("inv", invDetails );
	             ObjectMapper mapper = new ObjectMapper();
	             System.out.println(mapper.writeValueAsString(request.getAttribute("inv")));
	            return "edititem";
	        }
	      catch(Exception ex){
	          System.out.println("Error" + ex.getMessage());
	          ex.printStackTrace();
	          return ex.getMessage();   
	      }
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
}