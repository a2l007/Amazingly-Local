package com.iu.amazelocal.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iu.amazelocal.db.InventoryCrud;
import com.iu.amazelocal.db.PasswordAuth;
import com.iu.amazelocal.db.RecipeCrud;
import com.iu.amazelocal.db.UserCrud;
import com.iu.amazelocal.models.Login;
import com.iu.amazelocal.models.LoginDao;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.models.Recipe;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.UsersDao;
import com.iu.amazelocal.models.Vendors;


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
	    System.out.println("initial email address is"+ emailAddress);
	    if(auth.login(emailAddress, password)){
	    	System.out.println("Login successful");
	    	httpSession.setAttribute(sessionExists, true);
	    	httpSession.setAttribute(userName,emailAddress);
	    	httpSession.setAttribute(userType, user.getUserTypeFromEmail(emailAddress));
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
	@RequestMapping(method = RequestMethod.GET, value="/fetchTypes")
    @ResponseBody
    public String getProductTypes() {
       ArrayList<ProductType> type = new ArrayList<ProductType>();

      try {
          System.out.println("fetch types");
          InventoryCrud inventory=new InventoryCrud();
          type = inventory.fetchProductTypes();
      }
      catch(Exception ex) {
          System.out.println("Error"+ex.getMessage());
          ex.printStackTrace();
        return ex.getMessage();
      }
      return "Yes";
    }
    //public String getAllInventories(boolean _search, int rows, int page, String sidx, String sord)
     @RequestMapping(method = RequestMethod.GET, value = "/getInventories")
     @ResponseBody        
    public String getAllInventories(
     @RequestParam("_search") Boolean _search,
     @RequestParam("nd") String nd, 
     @RequestParam("rows") int rows, 
     @RequestParam("page") int page, 
     @RequestParam("sidx") String sidx, 
     @RequestParam("sord") String sord) {
       try{
          return "Hee"; 
       }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
       }
     }
     @RequestMapping(method = RequestMethod.GET, value = "/saveProduct")
     @ResponseBody        
    public String saveNewProduct() {
     //public String getAllInventories(@RequestBody Grid prmNames, HttpServletRequest request){
       try{
           System.out.println("Hi hey how are you?");
          return "Hee"; 
       }
       catch(Exception ex){
           System.out.println("Error"+ex.getMessage());
           ex.printStackTrace();
           return ex.getMessage();   
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
     public String search(String searchStr) throws IOException {
     	/*
     	 * Need to search for ProductName in AL_PRODUCTS, VendorName in AL_VENDORS, TypeName in AL_PRODUCT_TYPE
     	 * Everything is finally linked to the AL_INVENTORY table 
     	 * AL_VENDORS: VendorName:VendorId > AL_INVENTORY
     	 * AL_PRODUCTS: ProductName: ProductId > AL_INVENTORY
     	 * AL_PRODUCT_TYPE:TypeName:ProductTypeId > AL_PRODUCTS : ProductId > AL_INVENTORY
     	 */
    	 return "Results";
}
     
     @RequestMapping("/hello")
     public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
         model.addAttribute("name", name);
         return "hello";
     }
}