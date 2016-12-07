package com.iu.amazelocal.models;


import javax.validation.constraints.NotNull;

public class Payment {

 @NotNull
 private long PaymentId;

 @NotNull
 private long UserId;

 private String CardNumber;
 
 private String Name;

 private String CVV;
 
 private int ExpiryMonth;
 
 private int ExpiryYear;
 
 public long getPaymentId() {
  return PaymentId;
 }

 public void setPaymentId(long paymentId) {
	 PaymentId = paymentId;
 }

 public long getUserId() {
  return UserId;
 }

 public void setUserId(long userId) {
	 UserId = userId;
 }

 public String getCardNumber() {
  return CardNumber;
 }

 public void setCardNumber(String cardNumber) {
	 CardNumber = cardNumber;
 }
 
 public String getName() {
  return Name;
 }

 public void setName(String name) {
	 Name = name;
 }

 public void setCVV(String cvv) {
  CVV = cvv;
 }
 
 public String getCVV() {
  return CVV;
 }
 
 public void setExpiryMonth(int expiryMonth) {
  ExpiryMonth = expiryMonth;
 }
	 
 public int getExpiryMonth() {
  return ExpiryMonth;
 }
 
 public void setExpiryYear(int expiryYear) {
  ExpiryYear = expiryYear;
 }
		 
 public int getExpiryYear() {
  return ExpiryYear;
 }
 
 public Payment() {

 }

 public Payment(long id) {
  this.PaymentId = id;
 }
 
 public Payment(long paymentId, long userId) {
	  this.PaymentId = paymentId;
	  this.UserId = userId;
	 }
 
 public Payment(long userId, String cardNumber, String name, String cvv, int expiryMonth, int expiryYear) {
	    this.UserId = userId;
	    this.CardNumber = cardNumber;
	    this.Name = name;
	    this.CVV = cvv;
	    this.ExpiryMonth = expiryMonth;
	    this.ExpiryYear = expiryYear;
  }

}