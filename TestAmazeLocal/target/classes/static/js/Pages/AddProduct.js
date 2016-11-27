var ProductTypes;
var ProductSubTypes;

$(document).ready(function () {
         $.ajax({  
            url: "fetchTypes",  
            type: "GET",    
            success: function (data) {  
            ProductTypes = $.parseJSON(data[0])
            ProductSubTypes = $.parseJSON(data[1]);
            $('#category')
    		.find('option')
    		.remove()
    		.end()
    		.append('<option value="0">--Select a product category--</option>')
    		.val('0');
    		
            $('#subCat')
    		.find('option')
    		.remove()
    		.end()
    		.append('<option value="0">--Select a product sub category--</option>')
    		.val('0');
    		
            $.each(ProductTypes, function (key, value) {
                $("#category").append($('<option></option>').val(value.typeId).html(value.typeName));
            });
            },  
            error: function () {  
               alert("Error while processing your request");  
            }  
         });
});

$("#category").change(function() {
  var SelectedCategory = $('#category').val();
  
  $('#subCat')
    .find('option')
    .remove()
    .end()
    .append('<option value="0">--Select a product sub category--</option>')
    .val('0');

  $.each(ProductSubTypes, function (key, value) {
  if(value.typeId == SelectedCategory){
                $("#subCat").append($('<option></option>').val(value.subTypeId).html(value.subTypeName));
      }
   }); 
   
   $('#subCat')
    .find('option')
    .end()
    .append('<option value="-1">Add a new sub Category</option>');
     $(".subCat").removeClass("subCatNameShow");
     $(".subCat").addClass("subCatNameHide");
});

$("#subCat").change(function() {
  var SelectedSubCategory = $('#subCat').val();
  
  if(SelectedSubCategory == -1){
  $(".subCat").removeClass("subCatNameHide");
  $(".subCat").addClass("subCatNameShow");
  }
  else{
  $(".subCat").removeClass("subCatNameShow");
  $(".subCat").addClass("subCatNameHide");
  }
});