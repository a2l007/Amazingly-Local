package com.iu.amazelocal.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="AL_LOGIN")
public class Login {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long LoginId;
	  
	  @NotNull
	  private long UserId;
	  
	  @NotNull
	  @Size(min = 3, max = 80)
	  private String UserName;
	  
	  @NotNull
	  @Size(min = 2, max = 80)
	  private String Password;

	  public Login() { }

	  public Login(long id) { 
	    this.LoginId = id;
	  }

	  public Login(String username, String pass) {
	    this.UserName = username;
	    this.Password = pass;
	  }

}
