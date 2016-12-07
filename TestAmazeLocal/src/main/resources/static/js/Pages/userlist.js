 $(document).ready(function () {
             jQuery("#userTable").jqGrid({
                 url: "getuserlist",
                 datatype: "json",
                 //jsonReader: {repeatitems: false, id: "ref"},
                 colNames: ['User Id', 'First Name','Last Name', 'Email Address', 'Phone Number', 'Mailing Address'],
		         colModel: [
		         {name: 'userId', index: 'userId', key: true, hidden: true},
		         {name: 'firstName', index: 'firstName', width: 80, formatter:'showlink', //predefined formatter to display hyperlink
                     formatoptions:{baseLinkUrl:'ViewUser.html'}},
		         {name: 'lastName', index: 'lastName', width: 80},
		         {name: 'emailAddress', index: 'emailAddress', width: 80},
		         {name: 'phoneNumber', index: 'phoneNumber', width: 20},
		         {name: 'mailingAddress', index: 'mailingAddress', width: 100},
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
             
$("#userTable").jqGrid('hideCol', 'cb');
 });
