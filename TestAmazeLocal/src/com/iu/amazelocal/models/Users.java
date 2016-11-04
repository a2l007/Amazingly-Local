package com.iu.amazelocal.models;

import javax.persistence.Column;
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
	  	  
	@Column(nullable = false)
	  private String FirstName;

	@Column(nullable = false)
	  private String LastName;
	  
		@Column(nullable = false)
	  private String EmailAddress;
	  
		@Column(nullable = false)
	  private long PhoneNUmber;

	  public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}

	public long getPhoneNUmber() {
		return PhoneNUmber;
	}

	public void setPhoneNUmber(long phoneNUmber) {
		PhoneNUmber = phoneNUmber;
	}

	public String getMailingAddress() {
		return MailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		MailingAddress = mailingAddress;
	}

	@Column(nullable = false)
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
