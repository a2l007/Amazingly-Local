package com.iu.amazelocal.models;

import com.iu.amazelocal.models.InventoryGrid;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
 
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ProductApprovalGridData {
	 
	 /** Total number of pages */
	 private int total;
	 /** The current page number */
	 private int page;
	 /** Total number of records */
	 private int records;
	 /** The actual data */
	 private ArrayList<ProductApprovalGrid> rows;
	 
	 public ProductApprovalGridData(int total, int page, int records, ArrayList<ProductApprovalGrid> rows) {
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
	 
	 public ArrayList<ProductApprovalGrid> getRows() {
	  return rows;
	 }
}