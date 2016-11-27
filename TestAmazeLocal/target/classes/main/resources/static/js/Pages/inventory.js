/*$(document).ready(function () {  
   $("#inventoryTable").jqGrid({  
      ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },  
      prmNames: {  
         rows: "numRows",  
         page: "pageNumber",
         nd: null 
      },  
     colNames: ['Inventory Id', 'Name', 'Description', 'Price', 'Quantity', 'Unit', 'Category Id', 'Category','Sub Category Id','Sub Category','Calories Per Unit', 'Sale Percent', 'Sale Approved', 'Average Rating'],
		         colModel: [
		         {name: 'InventoryId', index: 'InventoryId', hidedlg:true, width: 10, key: true},
		         {name: 'Name', index: 'Name', width: 50 },
		         {name: 'Description', index: 'Description', width: 80},
		         {name: 'Price', index: 'Price', width: 30},
		         {name: 'Quantity', index: 'Quantity', width: 10},
		         {name: 'Unit', index: 'Unit', width: 30},   
		         {name: 'CategoryId', index: 'CategoryId',hidedlg:true, width: 10},  
		         {name: 'Category', index: 'Category', width: 50},
		         {name: 'SubCategoryId',index: 'SubCategoryId',hidedlg:true, width: 10},
		         {name: 'SubCategory', index: 'SubCategory', width: 50},
		         {name: 'Calories', index: 'Calories', width: 40},
		         {name: 'Sale', index: 'Sale', width: 40},
		         {name: 'SaleStatus', index: 'SaleStatus', width: 40},
		         {name: 'AvgRating', index:'AvgRating', width:30}
               ], //define column models  
      datatype: function (postdata) {  
         var dataUrl = "/getInventories";
         $.ajax({  
            url: dataUrl,  
            type: "GET",  
            contentType: "application/json; charset=utf-8",  
            dataType: "json",  
            data: JSON.stringify(postdata),  
            success: function (data, st) {  
               if (st == "success" && JSON.parse(data.d.indexOf("_Error_") != 0)) {  
                //  var grid = $("#divMyGrid")[0];  
                  //grid.addJSONData(JSON.parse(data.d));  
               }  
            },  
            error: function () {  
               alert("Error while processing your request");  
            }  
         });  
      },  
      pager: '#pagingDiv',  
      sortname: 'InventoryId', //the column according to which data is to be sorted(optional)  
      viewrecords: true, //if true, displays the total number of records, etc. as: "View X to Y out of Z” (optional)  
      sortorder: "asc", //sort order(optional)  
      caption: "Files", //Sets the caption for the grid. If this parameter is not set the Caption layer will be not visible  
      multiselect: true,  
      rowNum: 20,  
      loadonce: false,  
      autowidth: true,  
      shrinkToFit: true,  
      height: '100%',  
      rowList: [10, 20, 30, 50, 100],  
      sortable: true  
   }).navGrid("#pagingDiv", { search: true, edit: false, add: false, del: false }, {}, {}, {}, { multipleSearch: false });  
});*/   
 $(document).ready(function () {

             jQuery("#inventoryTable").jqGrid({
                 url: "/getInventories",
                 datatype: "json",
                 //jsonReader: {repeatitems: false, id: "ref"},
                 colNames: ['Inventory Id', 'Name', 'Description', 'Price', 'Quantity', 'Unit', 'Category Id', 'Category','Sub Category Id','Sub Category','Calories Per Unit', 'Sale Percent', 'Sale Approved', 'Average Rating'],
		         colModel: [
		         {name: 'InventoryId', index: 'InventoryId', key: true, hidden:true},
		         {name: 'Name', index: 'Name', width: 50 },
		         {name: 'Description', index: 'Description', width: 80},
		         {name: 'Price', index: 'Price', width: 30, sorttype: "float"},
		         {name: 'Quantity', index: 'Quantity', width: 10, sorttype: "int"},
		         {name: 'Unit', index: 'Unit', width: 30},   
		         {name: 'CategoryId', index: 'CategoryId', hidden:true},  
		         {name: 'Category', index: 'Category', width: 50},
		         {name: 'SubCategoryId',index: 'SubCategoryId',hidden:true},
		         {name: 'SubCategory', index: 'SubCategory', width: 50},
		         {name: 'Calories', index: 'Calories', width: 40},
		         {name: 'Sale', index: 'Sale', width: 40},
		         {name: 'SaleStatus', index: 'SaleStatus', width: 40},
		         {name: 'AvgRating', index:'AvgRating', width:30}
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
         });
