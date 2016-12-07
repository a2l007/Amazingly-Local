package com.iu.amazelocal.models;


import java.util.Date;

import javax.validation.constraints.NotNull;

public class ShopCart {

 @NotNull
 private long OrderId;

 @NotNull
 private long CartId;

 @NotNull
 private long InventoryId;

 @NotNull
 private long VendorId;

 @NotNull
 private long UserId;

 private String ProductName;

 private int OrderQuantity;
 
 private int QuantityAvailable;

 private int InvQuantity;

 private float OrderSubTotal;

 private float UnitPrice;

 private float Discount;

 private float InvTotalPrice;

 private String ImageName;

 private String OrderDate;
 
 private boolean IsActive;

 private String DeliveryAddress;
 
 private String OrderStatus;
 
 private long PaymentId;
 
 public long getOrderId() {
  return OrderId;
 }

 public void setOrderId(long orderId) {
  OrderId = orderId;
 }
 
 public long getCartId() {
  return CartId;
 }

 public void setCartId(long cartId) {
  CartId = cartId;
 }
 
 public long getInventoryId() {
  return InventoryId;
 }

 public void setInventoryId(long inventoryId) {
  InventoryId = inventoryId;
 }

 public long getVendorId() {
  return VendorId;
 }

 public void setVendorId(long vendorId) {
  VendorId = vendorId;
 }

 public long getUserId() {
  return UserId;
 }

 public void setUserId(long userId) {
  UserId = userId;
 }

 public int getOrderQuantity() {
  return OrderQuantity;
 }

 public void setOrderQuantity(int orderQuantity) {
  OrderQuantity = orderQuantity;
 }
 
 public int getQuantityAvailable() {
 return QuantityAvailable;
}

public void setQuantityAvailable(int quantityAvailable) {
   QuantityAvailable = quantityAvailable;
}

 public int getInvQuantity() {
  return InvQuantity;
 }

 public void setInvQuantity(int invQuantity) {
  InvQuantity = invQuantity;
 }
 public float getOrderSubTotal() {
  return OrderSubTotal;
 }

 public void setOrderSubTotal(float orderSubTotal) {
  OrderSubTotal = orderSubTotal;
 }

 public float getUnitPrice() {
  return UnitPrice;
 }

 public void setUnitPrice(float unitPrice) {
  UnitPrice = unitPrice;
 }

 public String getProductName() {
  return ProductName;
 }

 public void setProductName(String productName) {
  ProductName = productName;
 }

 public float getInvTotalPrice() {
  return InvTotalPrice;
 }

 public void setInvTotalPrice(float invTotalPrice) {
  InvTotalPrice = invTotalPrice;
 }

 public float getDiscount() {
  return Discount;
 }

 public void setDiscount(float discount) {
  Discount = discount;
 }

 public String getImageName() {
  return ImageName;
 }

 public void setImageName(String imageName) {
  ImageName = imageName;
 }

 public String getOrderDate() {
  return OrderDate;
 }

 public void setOrderDate(String orderDate) {
  OrderDate = orderDate;
 }

 public boolean getIsActive() {
  return IsActive;
 }

public void setIsActive(boolean isActive) {
	IsActive = isActive;
}

public String getDeliveryAddress() {
    return DeliveryAddress;
}

public void setDeliveryAddress(String deliveryAddress) {
	DeliveryAddress = deliveryAddress;
}

public long getPaymentId() {
    return PaymentId;
}

public void setPaymentId(long paymentId) {
	PaymentId = paymentId;
}

public String getOrderStatus() {
    return OrderStatus;
}

public void setOrderStatus(String orderStatus) {
	OrderStatus = orderStatus;
}

public ShopCart() {
}

public ShopCart(long id) {
  this.CartId = id;
}

public ShopCart(long cartId, long orderId, long userId) {
	  this.CartId = cartId;
	  this.OrderId = orderId;
	  this.UserId = userId;
}
 
 public ShopCart(long orderId, long cartId, long inventoryId, long vendorId, long userId, String prodName,
  int orderQuantity, int invQuantity, float orderSubTotal, float unitPrice, float invTotal,
  float discount, String imageName, String orderDate) {
  this.ProductName = prodName;
  this.ImageName = imageName;
 }

}