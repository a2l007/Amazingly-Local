package com.iu.amazelocal.models;

import javax.validation.constraints.NotNull;

public class cart {
	@NotNull
	 public long OrderId;

	 @NotNull
	 public long CartId;

	 public int Quantity;
	 
	 public float TotalPrice;
	 
	 public long getOrderId() {
		  return OrderId;
		 }

		 public void setOrderId(long orderId) {
		  OrderId = orderId;
		 }
		 
		 public long getCartId() {
		  return CartId;
		 }

		 public void setCartId(long cartId) {
		  CartId = cartId;
		 }
		 
		 public int getQuantity() {
			  return Quantity;
			 }

			 public void setQuantity(int quantity) {
			  Quantity = quantity;
			 }
			 public float getTotalPrice() {
				  return TotalPrice;
				 }

				 public void setTotalPrice(float totalPrice) {
					 TotalPrice = totalPrice;
				 }	 
}