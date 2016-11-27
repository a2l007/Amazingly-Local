package com.iu.amazelocal.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Inventory {
	
	  @NotNull
	  private long InventoryId;
	  
	  @NotNull
	  private long ProductTypeId;
	  
	  @NotNull
	  private long ProductSubId;
	  
	  private String ProductName;
	  
	  private String Description;
	  
	  private int Quantity;
	  
	  private float Price;
	  
	  private String Unit;
	  
	  private float Calories;
	  
	  private float Sale; 
	  
	  private String SaleApproved;
	  
	  private float ProductRating;
	 

}