package com.iu.amazelocal.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iu.amazelocal.db.InventoryCrud;
import com.iu.amazelocal.db.PasswordAuth;
import com.iu.amazelocal.db.UserCrud;
import com.iu.amazelocal.models.Login;
import com.iu.amazelocal.models.LoginDao;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.UsersDao;

@Controller
public class TestController {
	
	@Autowired
	private HttpSession httpSession;
	private static final String sessionExists="sessionExists";
	private static final String userName="userName";
	private static final String userEmail="userEmail";

	@RequestMapping(method = RequestMethod.POST, value="/save")
	  @ResponseBody
	  @Transactional
	  public String create(String fname, String lname, String email, 
			  Long phoneno, String address,String password,String securityq, String securitya) {
    	Users user = new Users(fname, lname,email,phoneno,address);

	    try {
	    	System.out.println("First name is"+ fname);
	    	UserCrud usersDao=new UserCrud();
	    	usersDao.insertUser(user,password,securityq,securitya);

	    }
	    catch(Exception ex) {
	    	System.out.println("Error"+ex.getMessage());
	    	ex.printStackTrace();
	      return ex.getMessage();
	    }
	    return "User succesfully saved!";
	  }
	
	@RequestMapping(method = RequestMethod.POST, value="/login")
	  
	  @Transactional
	  public String checkUser(String emailAddress,String password) {
  	
	    PasswordAuth auth=new PasswordAuth();
	    System.out.println("initial email address is"+ emailAddress);
	    if(auth.login(emailAddress, password)){
	    	System.out.println("Login successful");
	    	httpSession.setAttribute(sessionExists, true);
	    	httpSession.setAttribute(userName,emailAddress);
	    }
	    else
	    	System.out.println("Incorrect password");
	    return "indexsession";
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
	    System.out.println("Emial is"+securityanswer);
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
		 System.out.println("Exsitnig password is"+ exisPass);
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
      return "User succesfully saved!";
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
  //  public String getAllInventories(@RequestBody Grid prmNames){
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
}
