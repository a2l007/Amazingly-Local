package com.iu.amazelocal.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductSubTypes {
	
	  @NotNull
	  private int ProductSubId;
	  
	  @NotNull
	  private int ProductTypeId;
	  
	  private String ProdSubTypeName;

	  public void setSubTypeId(int subTypeId) {
		  ProductSubId = subTypeId;
	  }

		public int getSubTypeId() {
			return ProductSubId;
	  }
		
	  public void setTypeId(int typeId) {
		  ProductTypeId = typeId;
	  }

		public int getTypeId() {
			return ProductTypeId;
	  }
	
	  public void setSubTypeName(String subTypeName) {
		  ProdSubTypeName = subTypeName;
	  }

	  public String getSubTypeName() {
			return ProdSubTypeName;
	  }	
		
	  public ProductSubTypes() { }

	  public ProductSubTypes(int id) { 
	    this.ProductTypeId = id;
	  }

	  public ProductSubTypes(int subTypeId, int typeId, String name) {
		this.ProductSubId = subTypeId;   
	    this.ProductTypeId = typeId;
	    this.ProdSubTypeName = name;
	  }

}