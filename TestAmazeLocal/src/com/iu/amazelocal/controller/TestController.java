package com.iu.amazelocal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iu.amazelocal.models.Login;
import com.iu.amazelocal.models.LoginDao;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.UsersDao;

@Controller
public class TestController {
	@RequestMapping(method = RequestMethod.POST, value="/save")
	  @ResponseBody
	  public String create(String FirstName, String LastName, String EmailAddress, long PhoneNumber, String MailingAddress) {
	    try {
	    	Users user = new Users(FirstName, LastName,EmailAddress,PhoneNumber,MailingAddress );
	    	_usersDao.save(user);
	    }
	    catch(Exception ex) {
	      return ex.getMessage();
	    }
	    return "User succesfully saved!";
	  }
	@Autowired
	  private UsersDao _usersDao;
	  

}
