 $(document).ready(function () {
             jQuery("#saleTable").jqGrid({
                 url: "getProductApprovals",
                 datatype: "json",
                 //jsonReader: {repeatitems: false, id: "ref"},
                 colNames: ['cartId', 'First Name', 'Last Name','Email Address', 'Mailing Address', 'OrderDate', 'Product Name', 'Quantity'],
		         colModel: [
				 {name: 'cartId', index: 'cartId', width: 80, key: true, hidden: true},
		         {name: 'firstname', index: 'firstname', width: 80},
		         {name: 'lastName', index: 'lastName', width: 80},
		         {name: 'emailAddress', index: 'emailAddress', width: 80},
		         {name: 'mailingAddress', index: 'mailingAddress', width: 80},
		         {name: 'orderDate', index: 'orderDate', width: 20},
		         {name: 'productName', index: 'productName', width: 20},
		         {name: 'quantity', index: 'quantity', width: 30},
               ],
               	 rowNum: 20,  
                 rowList:[20,60,100],
                 height:460,
                 pager: "#pagingDiv",
                 viewrecords: true,
                 caption: "Pending Purchase Approvals",
                 sortname: 'cartId', //the column according to which data is to be sorted(optional)  
     			 viewrecords: true, //if true, displays the total number of records, etc. as: "View X to Y out of Z” (optional)  
                 sortorder: "asc", //sort order(optional)  
      multiselect: true,
      loadonce: false,  
      autowidth: true,  
      shrinkToFit: true,
      sortable: true  
             })
             .navGrid('#pagingDiv',{search:true});
             
//$("#saleTable").jqGrid('hideCol', 'cb');
 });
