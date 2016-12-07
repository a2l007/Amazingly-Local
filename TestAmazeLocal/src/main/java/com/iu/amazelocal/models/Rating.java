package com.iu.amazelocal.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Rating {

 @NotNull
 private long RatingId;

 @NotNull
 private long InventoryId;

 @NotNull
 private long UserId;
 
 private long Rating;
 
 
 private String Comments;
 

 public long getRatingId() {
  return RatingId;
 }

 public void setRatingId(long r) {
  RatingId = r;
 }

 public long getUserId() {
  return UserId;
 }

 public void setUserId(long u) {
  UserId = u;
 }

 public long getInventoryId() {
  return InventoryId;
 }

 public void setInventoryId(long i) {
	 InventoryId = i;
 }
 
 public long getRating() {
  return Rating;
 }

 public void setRating(long r) {
  Rating = r;
	 }


 
 public String getComment() {
  return Comments;
 }

public void setComment(String Comment) {
	Comments = Comment;
 }

 
 public Rating() {

 }

 public Rating(long Ratingid) {
  this.RatingId = Ratingid;
 }

 public Rating(long InventoryId, long UserId, long r,String Comment) {
	    this.InventoryId = InventoryId;
	    this.UserId = UserId;
	    this.Rating=r;
	    this.Comments=Comment;
	    
	  }

}