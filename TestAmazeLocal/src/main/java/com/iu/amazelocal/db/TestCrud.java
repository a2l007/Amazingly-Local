package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.models.InventoryGrid;
import com.iu.amazelocal.models.InventoryMini;
import com.iu.amazelocal.models.ProductSubTypes;
import com.iu.amazelocal.models.ProductType;
import com.iu.amazelocal.utils.AppConstants;


public class TestCrud {
	public ArrayList<InventoryMini> searchProduct(String str) {
     InventoryCrud inv=new InventoryCrud();
   	 ArrayList<InventoryMini> searchResults=new ArrayList<InventoryMini>();
   	 //find Inventory from AL_VENDORS;
   	 	searchResults=inv.fetchProductWithoutType(str);
   	 	return searchResults;
   	 }
}