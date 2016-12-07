package com.iu.amazelocal.models;

import com.iu.amazelocal.models.InventoryGrid;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
 
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class VendorGridData {
	 
	 /** Total number of pages */
	 private int total;
	 /** The current page number */
	 private int page;
	 /** Total number of records */
	 private int records;
	 /** The actual data */
	 private ArrayList<Vendors> rows;
	 
	 public VendorGridData(int total, int page, int records, ArrayList<Vendors> rows) {
	  this.total = total;
	  this.page = page;
	  this.records = records;
	  this.rows = rows;
	 }
	 
	 public int getTotal() {
	  return total;
	 }
	 
	 public int getPage() {
	  return page;
	 }
	 
	 public int getRecords() {
	  return records;
	 }
	 
	 public ArrayList<Vendors> getRows() {
	  return rows;
	 }
}