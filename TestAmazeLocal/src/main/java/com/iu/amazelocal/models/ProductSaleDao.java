package com.iu.amazelocal.models;

public class ProductSaleDao {
	public String productName;
	public int UnitSold;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getUnitSold() {
		return UnitSold;
	}
	public void setUnitSold(int unitSold) {
		UnitSold = unitSold;
	}
	public ProductSaleDao(String prodName,int soldCount){
		this.productName=prodName;
		this.UnitSold=soldCount;
	}
}
