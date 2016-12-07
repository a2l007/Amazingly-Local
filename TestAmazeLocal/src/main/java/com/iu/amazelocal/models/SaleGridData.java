package com.iu.amazelocal.models;

import com.iu.amazelocal.models.InventoryGrid;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
 
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SaleGridData {
	 
	 /** Total number of pages */
	 private int total;
	 /** The current page number */
	 private int page;
	 /** Total number of records */
	 private int records;
	 /** The actual data */
	 private ArrayList<SaleApprovalGrid> rows;
	 
	 public SaleGridData(int total, int page, int records, ArrayList<SaleApprovalGrid> rows) {
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
	 
	 public ArrayList<SaleApprovalGrid> getRows() {
	  return rows;
	 }
}