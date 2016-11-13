  
 $(document).ready(function () {

             jQuery("#inventoryTable").jqGrid({
                // url: "",
                 //datatype: "json",
                 //jsonReader: {repeatitems: false, id: "ref"},
                 colNames: ['Inventory Id', 'Name', 'Description', 'Price', 'Quantity', 'Unit', 'Category Id', 'Type','Sub Category Id','Sub Type','Calories', 'Sale %', 'Sale Approved', 'Rating'],
		         colModel: [
		         {name: 'InventoryId', index: 'InventoryId', key: true, hidden:true},
		         {name: 'Name', index: 'Name', width: 50 },
		         {name: 'Description', index: 'Description', width: 80},
		         {name: 'Price', index: 'Price', width: 30, sorttype: "float"},
		         {name: 'Quantity', index: 'Quantity', width: 30, sorttype: "int"},
		         {name: 'Unit', index: 'Unit', width: 10},   
		         {name: 'CategoryId', index: 'CategoryId', hidden:true},  
		         {name: 'Category', index: 'Category', width: 30},
		         {name: 'SubCategoryId',index: 'SubCategoryId',hidden:true},
		         {name: 'SubCategory', index: 'SubCategory', width: 40},
		         {name: 'Calories', index: 'Calories', width: 35},
		         {name: 'Sale', index: 'Sale', width: 20},
		         {name: 'SaleStatus', index: 'SaleStatus', width: 20},
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
             
    var inventory = ["InventoryId", "Name", "Description", "Price", "Quantity", "Unit", "CategoryId", "Category","SubCategoryId","SubCategory", "Calories","Sale", "SaleStatus","AvgRating"];
    var mydata = [];
     var data = [["1", "Honey Crisp Apple", "Red and fresh", "$1.99", "39", "each","1","Fruits","2","Apples","100","0","NA","4.2"], [12, "Bacon", "Delicately smoked and loaded with flavor", "$10.99", "120", "packet","1","Meat","2","Pork","1245","0","NA","4.5"]];
    
      for (var i = 0; i < data.length; i++) {
        mydata[i] = {};
        for (var j = 0; j < data[i].length; j++) {
            mydata[i][inventory[j]] = data[i][j];
        }
    }
    
    for (var i = 0; i <= mydata.length; i++) {
        $("#inventoryTable").jqGrid('addRowData', i + 1, mydata[i]);
    }
        
 });