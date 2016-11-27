package com.iu.amazelocal.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductType {
	
	  @NotNull
	  private int ProductTypeId;
	  
	  private String ProductTypeName;

	  public void setTypeId(int typeId) {
		  ProductTypeId = typeId;
	  }

		public int getTypeId() {
			return ProductTypeId;
	  }
	
	  public void setTypeName(String typeName) {
		  ProductTypeName = typeName;
	  }

	  public String getTypeName() {
			return ProductTypeName;
	  }	
		
	  public ProductType() { }

	  public ProductType(int id) { 
	    this.ProductTypeId = id;
	  }

	  public ProductType(int id, String name) {
	    this.ProductTypeId = id;
	    this.ProductTypeName = name;
	  }

}