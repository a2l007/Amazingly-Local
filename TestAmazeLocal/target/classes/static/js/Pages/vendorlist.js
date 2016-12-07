 $(document).ready(function () {
             jQuery("#vendorTable").jqGrid({
                 url: "getvendorlist",
                 datatype: "json",
                 //jsonReader: {repeatitems: false, id: "ref"},
                 colNames: ['Vendor Id', 'Vendor Name','Farm Address', 'Mailing Address', 'Vendor Rating'
                            ,'Email Address', 'Phone Number','Revenue'],
		         colModel: [
		         {name: 'vendorId', index: 'vendorId', key: true, hidden: true},
		         {name: 'vendorName', index: 'vendorName', width: 20, formatter:'showlink', //predefined formatter to display hyperlink
                     formatoptions:{baseLinkUrl:'vendorstats.jsp'}},
		         {name: 'farmAddress', index: 'farmAddress', width: 80},
		         {name: 'mailingAddress', index: 'mailingAddress', width: 80},
		         {name: 'vendorRating', index: 'vendorRating', width: 20, sorttype: "float"},
		         {name: 'emailAddress', index: 'emailAddress', width: 20},
		         {name: 'phoneNumber', index: 'phoneNumber', width: 20},
		         {name: 'revenue', index: 'revenue', width: 20, sorttype: "float"},

               ],
               	 rowNum: 20,  
                 rowList:[20,60,100],
                 height:460,
                 pager: "#pagingDiv",
                 viewrecords: true,
                 caption: "List of registered buyers",
                 sortname: 'userId', //the column according to which data is to be sorted(optional)  
     			 viewrecords: true, //if true, displays the total number of records, etc. as: "View X to Y out of Z” (optional)  
                 sortorder: "asc", //sort order(optional)  
      multiselect: true,
      loadonce: false,  
      autowidth: true,  
      shrinkToFit: true,
      sortable: true  
             });
             
$("#vendorTable").jqGrid('hideCol', 'cb');
 });
