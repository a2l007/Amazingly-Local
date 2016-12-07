 $(document).ready(function () {
             jQuery("#inventoryTable").jqGrid({
                 url: "getInventories",
                 datatype: "json",
                 //jsonReader: {repeatitems: false, id: "ref"},
                 colNames: ['Inventory Id', 'Name', 'Description', 'Price', 'Quantity', 'Unit', 'Category Id', 'Category','Sub Category Id','Sub Category','Calories Per Unit', 'Sale Percent', 'Sale Approved', 'Average Rating'],
		         colModel: [
		         {name: 'inventoryId', index: 'inventoryId', key: true, hidden: true},
		         {name: 'productName', index: 'productName', width: 50, formatter:'showlink', //predefined formatter to display hyperlink
                         formatoptions:{baseLinkUrl:'edit'}},
		         {name: 'description', index: 'description', width: 80, hidden: true},
		         {name: 'price', index: 'price', width: 20, sorttype: "float"},
		         {name: 'quantity', index: 'quantity', width: 25, sorttype: "int"},
		         {name: 'unit', index: 'unit', width: 20},   
		         {name: 'productTypeId', index: 'productTypeId', hidden: true},  
		         {name: 'productType', index: 'TypeName', width: 30},
		         {name: 'productSubId',index: 'productSubId', hidden: true},
		         {name: 'productSubType', index: 'productSubType', width: 30},
		         {name: 'calories', index: 'calories', width: 35},
		         {name: 'sale', index: 'sale', width: 30},
		         {name: 'saleApproved', index: 'saleApproved', width: 30},
		         {name: 'productRating', index:'productRating', width: 30}
               ],
               	 rowNum: 20,  
                 rowList:[20,60,100],
                 height:460,
                 pager: "#pagingDiv",
                 viewrecords: true,
                 caption: "Products in stock",
                 sortname: 'InventoryId', //the column according to which data is to be sorted(optional)  
     			 viewrecords: true, //if true, displays the total number of records, etc. as: "View X to Y out of Z” (optional)  
                 sortorder: "asc", //sort order(optional)  
      multiselect: true,
      loadonce: false,  
      autowidth: true,  
      shrinkToFit: true,
      sortable: true  
             });
             
$("#inventoryTable").jqGrid('hideCol', 'cb');
 });
