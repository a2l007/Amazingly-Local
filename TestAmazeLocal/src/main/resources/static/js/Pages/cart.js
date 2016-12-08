$(document).ready(function () {
        
});

function updateCart(){
	var items = [];
	 $('.cart_item').each(function () {
			var cartItem = {};
		    var curr_row = $(this);
		    cartItem.CartId = curr_row.find('input[name*="cartId"]').val();
		    cartItem.Quantity = curr_row.find('input[name*="itemQuantity"]').val();
	        cartItem.OrderId = curr_row.find('input[name*="orderId"]').val();
	        cartItem.TotalPrice = curr_row.find('input[name*="totalPrice"]').val();
	        items.push(cartItem);
    });
	 
	 //Save the updated cart details
	 $.ajax({  
         url: "updateCart",  
         type: "POST",    
         data: JSON.stringify(items),
         contentType: "application/json; charset=utf-8",
         success: function (data) { 
        	 alert("Hello successWW");

        	 window.location.href = "OrderReview.jsp";
         },  
         error: function () {  
            alert("Error while processing your request");  
         }  
      });
}

function DeleteCartItem(cartId, orderId){
	
	//Delete saved payment card details
	 $.ajax({  
        url:"deleteItem?cartId="+cartId+"&orderId="+orderId,
        type: "GET",  
        contentType: "application/json; charset=utf-8",
        success: function (data) { 
        	
         alert("Delete cart item successful! : " + data + " this was the data");

         //hide the modal
     	 $('#myModal_'+cartId).modal('hide');
     	 
         if(data == "success"){
         
         //Hide the cart td
         $("#trCart_"+cartId).hide();
     	 //Set the success message in the success modal
    	 $("#divSuccessMsg").html("Success! Item has been deleted successfully from your cart.")
    	 
    	 //Show the success modal
       	 $('#divSuccessModal').modal('show');
        } else{
        	alert("Error occured!");
         }
        },  
        error: function () {  
        	 //hide the modal
        	 $('#myModal_'+cartId).modal('hide');
          	 alert("Error occured!");
        }  
     });
}
