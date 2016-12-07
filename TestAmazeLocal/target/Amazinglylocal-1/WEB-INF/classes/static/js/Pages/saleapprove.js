 $(document).ready(function () {
             jQuery("#saleTable").jqGrid({
                 url: "getSaleApprovals",
                 datatype: "json",
                 //jsonReader: {repeatitems: false, id: "ref"},
                 colNames: ['Inventory Id', 'Vendor Name','Product Type', 'Product Name', 'Price', 'Sale Percentage'],
		         colModel: [
		         {name: 'inventoryId', index: 'inventoryId', key: true, hidden: true},
		         {name: 'vendorName', index: 'vendorName', width: 80},
		         {name: 'prodSubTypeName', index: 'prodSubTypeName', width: 80},
		         {name: 'productName', index: 'productName', width: 80},
		         {name: 'price', index: 'price', width: 20, sorttype: "float"},
		         {name: 'sale', index: 'sale', width: 30},
               ],
               	 rowNum: 20,  
                 rowList:[20,60,100],
                 height:460,
                 pager: "#pagingDiv",
                 viewrecords: true,
                 caption: "Pending Sale Approvals",
                 sortname: 'InventoryId', //the column according to which data is to be sorted(optional)  
     			 viewrecords: true, //if true, displays the total number of records, etc. as: "View X to Y out of Z” (optional)  
                 sortorder: "asc", //sort order(optional)  
      multiselect: true,
      loadonce: false,  
      autowidth: true,  
      shrinkToFit: true,
      sortable: true  
             });
             
//$("#saleTable").jqGrid('hideCol', 'cb');
 });
