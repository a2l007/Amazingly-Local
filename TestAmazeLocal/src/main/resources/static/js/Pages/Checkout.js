$(document).ready(function () {
});

$('input:radio[id="newAddress"]').change(
	    function(){
	        if ($(this).is(':checked')) {
	        	$("#name").prop('disabled', false);
	        	$("#mailaddress").prop('disabled', false);
	        	$("#zipcode").prop('disabled', false);
	        	$("#phone").prop('disabled', false);
	        }
});

$('input:radio[id="defaultAddress"]').change(
	    function(){
	   	if ($(this).is(':checked')) {
	   		    $("#name").prop('disabled', true);
	        	$("#mailaddress").prop('disabled', true);
	        	$("#zipcode").prop('disabled', true);
	        	$("#phone").prop('disabled', true);
	        }
});

function SaveAddress(){
	var address;
	var isValid = true;
	var alertMsg ="";
	
	if ($('input:radio[id="newAddress"]').is(':checked')) {
		var name = $("#name").val();
		var mailingAddr = $("#mailaddress").val();
		var zipCode = $("#zipcode").val();
		var phoneNo = $("#phone").val();

		//Check if address is a valid one
		if(name == ""){
			alertMsg += "Please enter your name!<br>";
			isValid = false;
		}
		if(mailingAddr == ""){
			alertMsg += "Please enter your mailing address!<br>";
			isValid = false;
		}
		if(zipCode == ""){
			alertMsg += "Please enter your zipcode!<br>";
			isValid = false;
		}
		if(phoneNo == ""){
			alertMsg += "Please enter your phone number!<br>";
			isValid = false;
		}
		address = name + "<br>," + mailingAddr + "<br>," + zipCode + "<br>," + phoneNo;
	}else{
		address = $("#divDefaultAddr").text().trim();
	}
	
	//Save address only if data is valid
	if(isValid){
	//	alert("isValid"+isValid);
	$.ajax({  
         url: "saveAddress",  
         type: "POST",    
         data: address,
         contentType: "application/json; charset=utf-8",
         success: function (data) { 
        //	 alert("Hello successWW");
         },  
         error: function () {  
           // alert("Error while processing your request");  
         }  
      });
	}else{
		//Set the warning message in the success modal
   	 	$("#divAlertMsg").html("Alert! " + alertMsg);
   	 
		$("#divAlertModal").modal("show");
	}
	return isValid;
}