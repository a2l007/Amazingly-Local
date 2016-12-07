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
@Table(name="AL_VENDORS")
public class Vendors {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	  private long VendorId;
	  	  
	@Column(nullable = false)
	  private String VendorName;
 
		@Column(nullable = false)
	  private String EmailAddress;
	  
		@Column(nullable = false)
	  private long PhoneNumber;

		@Column(nullable = false)
		  private String FarmAddress;
		
		@Column(nullable = false)
		  private String MailingAddress;
		
		private float revenue;
	 
private float vendorRating;
	  public float getVendorRating() {
	return vendorRating;
}

public void setVendorRating(float vendorRating) {
	this.vendorRating = vendorRating;
}

	public Vendors() { }

	  public Vendors(long id) { 
	    this.VendorId = id;
	  }

	  public long getVendorId() {
		return VendorId;
	}

	public void setVendorId(long vendorId) {
		VendorId = vendorId;
	}

	public String getVendorName() {
		return VendorName;
	}

	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}

	public long getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getFarmAddress() {
		return FarmAddress;
	}

	public void setFarmAddress(String farmAddress) {
		FarmAddress = farmAddress;
	}

	public String getMailingAddress() {
		return MailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		MailingAddress = mailingAddress;
	}

	public Vendors(String VendorName, String EmailAddress, long PhoneNumber, String MailingAddress,String FarmAddress) {
	    this.VendorName = VendorName;
	    this.EmailAddress=EmailAddress;
	    this.PhoneNumber=PhoneNumber;
	    this.MailingAddress=MailingAddress;
	    this.FarmAddress=FarmAddress;

	  }

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

}
