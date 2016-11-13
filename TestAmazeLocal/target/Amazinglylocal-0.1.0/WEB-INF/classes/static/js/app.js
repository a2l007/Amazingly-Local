$(document).ready(function () {
  $('#forgotpasswordform').submit(function(event) {
	  event.preventDefault();
	  var formData = $("#forgotpasswordform").serialize();
       
    $.ajax({
        url: "forgotpassword",
        data: formData,
        type: "POST",
         
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(resp) {
      	  echo("Yes");

             var respContent="<b>Successful</b>"
            $("#forgotpassword")div.html(respContent);         
        }
    });
      
  });
    
});