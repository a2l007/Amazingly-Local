package com.iu.amazelocal.models;

public class ProductTypeDao {

	public String productType;
	public long quantity;
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public ProductTypeDao(String pType,long qty){
		this.productType=pType;
		this.quantity=qty;
	}
}
