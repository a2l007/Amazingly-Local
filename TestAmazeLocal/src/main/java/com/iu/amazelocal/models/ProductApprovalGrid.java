package com.iu.amazelocal.models;

import javax.validation.constraints.NotNull;

public class ProductApprovalGrid {
	@NotNull
	private int cartId;
	
	 private String firstname;

	 private String lastName;
	 
	 private String emailAddress;
	 
	 private String mailingAddress;

	 private String orderDate;
	 
	 private String productName;
	 
	 private int quantity;
	 
	 public ProductApprovalGrid(int cartId,String Firstname,String LastName, String EmailAddress,
			 String MailingAddress,String orderDate, String productName, int quantity){
		 this.cartId=cartId;
		 this.firstname=Firstname;
		 this.lastName=LastName;
		 this.emailAddress=EmailAddress;
		 this.mailingAddress=MailingAddress;
		 this.orderDate=orderDate;
		 this.productName=productName;
		 this.quantity=quantity;
	 }

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}