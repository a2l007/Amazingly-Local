package com.iu.amazelocal.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="AL_USERS")
public class Users {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long UserId;
	  	  
	  @NotNull
	  private String FirstName;

	  private String LastName;
	  
	  @NotNull
	  @Size(min = 8, max = 80)
	  private String EmailAddress;
	  
	  @NotNull
	  private long PhoneNUmber;

	  @NotNull
	  @Size(min = 3, max = 80)
	  private String MailingAddress;

	  public Users() { }

	  public Users(long id) { 
	    this.UserId = id;
	  }

	  public Users(String FirstName, String LastName, String EmailAddress, long PhoneNumber, String MailingAddress) {
	    this.FirstName = FirstName;
	    this.LastName = LastName;
	    this.EmailAddress=EmailAddress;
	    this.PhoneNUmber=PhoneNumber;
	    this.MailingAddress=MailingAddress;
	  }

}
