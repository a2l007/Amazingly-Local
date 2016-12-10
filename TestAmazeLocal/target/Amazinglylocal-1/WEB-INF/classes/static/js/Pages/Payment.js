$(document).ready(function () {
//Select the first radio button
$('input[type=radio]:first').attr('checked',true);
});

function SaveCard(){
	
	var name = $("#name").val();
	var cardNumber = $("#CardNumber").val();
	var Cvv = $("#Cvv").val();
	var expYear = $( "#expYear option:selected" ).text();
	var expMonth = $( "#expMonth option:selected" ).text();
	var isValid = true;
	var alertMsg = "";
	
	if(name == ""){
		alertMsg += "Please enter name on the card.<br>";
		isValid = false;
	}
	if(cardNumber == ""){
		alertMsg += "Please enter your card number.<br>";
		isValid = false;
	}
	if(Cvv == ""){
		alertMsg += "Please enter CVV.<br>";
		isValid = false;
	}
	var cardDetails = cardNumber + "~" + name + "~" + Cvv + "~" + expMonth + "~" + expYear;
	
	
	if(isValid){
		
	 //Save the updated cart details
	 $.ajax({  
         url: "saveNewCard",  
         type: "POST",    
         data: cardDetails,
         contentType: "application/json; charset=utf-8",
         success: function (data) { 
        	 $('#Payment_Modal').modal('hide');
        	 location.reload();
        	 $('#divSuccessModal').modal('show');
         },  
         error: function () { 
        	$('#Payment_Modal').modal('hide');
            //alert("Error while processing your request");  
         }  
      });
	}else
		{
		 $("#divAlertMsg").html("Alert! " + alertMsg);
    	 
    	 //Show the error modal
       	 $('#divAlertModal').modal('show');
		}
}

function DeleteCard(paymentId){
	//alert("Payment id to be deleted new alert: "+paymentId);
	//Delete saved payment card details
	 $.ajax({  
        url: "deleteCard?paymentId="+paymentId,  
        type: "GET",  
        contentType: "application/json; charset=utf-8",
        success: function (data) { 
        	

         //hide the modal
     	 $('#deleteModal_'+paymentId).modal('hide');
     	 
         if(data == "success"){
     	 //Set the success message in the success modal
    	 $("#divSuccessMsg").html("Success! Payment card details has been deleted successfully.")
    	 
    	 //Hide the payment div deleted
    	 $("#divPay_"+paymentId).hide();
    	 
    	 //Select the first radio button
    	 $('input[type=radio]:first').attr('checked',true);
    	 
    	 //Show the success modal
       	 $('#divSuccessModal').modal('show');
        } else{
        	//alert("Error occured!");
         }
        },  
        error: function () {  
        	$('#deleteModal_'+paymentId).modal('hide');
          	//alert("Error occured!");
        }  
     });
}

function saveOrder(){
	
	var selectedPayId = $("input[type=radio]:checked").val();
	
  	$('#divOrderModal').modal('show');
  	 
	$.ajax({  
        url: "saveOrder?paymentId="+selectedPayId,  
        type: "GET",  
        contentType: "application/json; charset=utf-8",
        success: function (data) {
       	window.location.href = "CustomerOrders.jsp";
        },  
        error: function () { 
          //	alert("Error occured!");
        }  
     });
}