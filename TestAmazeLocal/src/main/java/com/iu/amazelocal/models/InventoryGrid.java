package com.iu.amazelocal.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InventoryGrid {

 @NotNull
 private long InventoryId;

 @NotNull
 private long ProductTypeId;
 
 private String ProductType;
 
 @NotNull
 private long ProductSubId;

 private String ProductSubType;
 
 private String ProductName;

 private String Description;

 private int Quantity;

 private float Price;

 private String Unit;

 private float Calories;

 private float Sale;

 private String SaleApproved;

 private float ProductRating;

 public long getInventoryId() {
  return InventoryId;
 }

 public void setInventoryId(long inventoryId) {
  InventoryId = inventoryId;
 }

 public long getProductTypeId() {
  return ProductTypeId;
 }

 public void setProductTypeId(long productTypeId) {
  ProductTypeId = productTypeId;
 }
 
 public String getProductType() {
  return ProductType;
 }

 public void setProductType(String productType) {
  ProductType = productType;
	 }

 public long getProductSubId() {
  return ProductSubId;
 }

 public void setProductSubId(long productSubId) {
  ProductSubId = productSubId;
 }
 
 public String getProductSubType() {
  return ProductSubType;
 }

public void setProductSubType(String productSubType) {
	ProductSubType = productSubType;
 }

 public String getProductName() {
  return ProductName;
 }

 public void setProductName(String productName) {
  ProductName = productName;
 }

 public String getDescription() {
  return Description;
 }

 public void setDescription(String description) {
  Description = description;
 }

 public int getQuantity() {
  return Quantity;
 }

 public void setQuantity(int quantity) {
  Quantity = quantity;
 }
 
 public float getPrice() {
  return Price;
 }

 public void setPrice(float price) {
  Price = price;
 }
 
 public String getUnit() {
  return Unit;
 }

 public void setUnit(String unit) {
  Unit = unit;
 }
 
 public float getCalories() {
  return Calories;
 }

 public void setCalories(float calories) {
  Calories = calories;
 }
 
 public float getSale() {
  return Sale;
 }

 public void setSale(float sale) {
  Sale = sale;
 }

 public float getProductRating() {
  return ProductRating;
 }

public void setProductRating(float productRating) {
  ProductRating = productRating;
 }

public String getSaleApproved() {
 return SaleApproved;
 }

public void setSaleApproved(String saleApproved) {
 SaleApproved = saleApproved;
}

 public InventoryGrid() {

 }

 public InventoryGrid(long id) {
  this.InventoryId = id;
 }

 public InventoryGrid(String prodName, String desc, long prodSubCat, float price, int quantity,
		  String unit, float cal, float salepercent) {
	    this.ProductName = prodName;
	    this.Description = desc;
	    this.ProductSubId=prodSubCat;
	    this.Price=price;
	    this.Quantity=quantity;
	    this.Unit = unit;
	    this.Calories=cal;
	    this.Sale=salepercent;
	  }

}