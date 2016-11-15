/**
 * 
 */
package com.iu.amazelocal.models;

/**
 * @author atul
 *
 */
public class InventoryMini {
	
      private long inventoryId;
	  private String productName;
	  private float productRating;
	  private String imageName;
	  
	public long getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(long inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public float getProductRating() {
		return productRating;
	}
	public void setProductRating(float productRating) {
		this.productRating = productRating;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
public InventoryMini(long inventoryId, String productName, float productRating, String imageName){
	this.inventoryId=inventoryId;
	this.productName=productName;
	this.productRating=productRating;
	this.imageName=imageName;
}
}
