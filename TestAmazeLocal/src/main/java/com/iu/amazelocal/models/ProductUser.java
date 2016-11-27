package com.iu.amazelocal.models;

public class ProductUser {
public String productName;
public int UserId;
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public int getUserId() {
	return UserId;
}
public void setUserId(int userId) {
	UserId = userId;
}
public ProductUser(String pName,int uId){
	this.productName=pName;
	this.UserId=uId;
}
}
