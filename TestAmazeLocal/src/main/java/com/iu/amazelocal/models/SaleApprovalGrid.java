package com.iu.amazelocal.models;

import javax.validation.constraints.NotNull;

public class SaleApprovalGrid {
	@NotNull
	 private long InventoryId;

	 private String vendorName;
	 
	 private String ProdSubTypeName;
	 
	 private String ProductName;

	 private float Price;
	 
	 private float Sale;
	 
	 public SaleApprovalGrid(int inventoryId,String vendorName, String prodSubTypeName, String productName,float price, float sale){
		 this.InventoryId=inventoryId;
		 this.vendorName=vendorName;
		 this.ProdSubTypeName=prodSubTypeName;
		 this.ProductName=productName;
		 this.Price=price;
		 this.Sale=sale;
	 }
	public long getInventoryId() {
		return InventoryId;
	}

	public void setInventoryId(long inventoryId) {
		InventoryId = inventoryId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getProdSubTypeName() {
		return ProdSubTypeName;
	}

	public void setProdSubTypeName(String prodSubTypeName) {
		ProdSubTypeName = prodSubTypeName;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public float getPrice() {
		return Price;
	}

	public void setPrice(float price) {
		Price = price;
	}

	public float getSale() {
		return Sale;
	}

	public void setSale(float sale) {
		Sale = sale;
	}
}
