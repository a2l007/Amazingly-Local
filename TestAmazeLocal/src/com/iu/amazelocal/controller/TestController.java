package com.iu.amazelocal.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iu.amazelocal.db.PasswordAuth;
import com.iu.amazelocal.db.UserCrud;
import com.iu.amazelocal.models.Login;
import com.iu.amazelocal.models.LoginDao;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.UsersDao;

@Controller
public class TestController {
	
	@Autowired
	private HttpSession httpSession;
	private static final String sessionExists="sessionExists";
	private static final String userName="userName";

	@RequestMapping(method = RequestMethod.POST, value="/save")
	  @ResponseBody
	  @Transactional
	  public String create(String fname, String lname, String email, Long phoneno, String address,String password) {
    	Users user = new Users(fname, lname,email,phoneno,address );

	    try {
	    	System.out.println("First name is"+ fname);
	    	UserCrud usersDao=new UserCrud();
	    	usersDao.insertUser(user,password);

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
	    	httpSession.setAttribute(userName,"User1");
	    }
	    else
	    	System.out.println("Incorrect password");
	    return "indexsession";
	  }
	  

}
