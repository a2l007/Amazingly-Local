<!DOCTYPE html>
<%@page import="com.iu.amazelocal.db.TestCrud"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.iu.amazelocal.models.InventoryMini"%>

<html lang="en-us">
 
<head>
<meta charset="utf-8">
<title>Filtering Example</title>
<style>
body {
    padding: 25px;
}
h1 {
    font-family: "Franklin Gothic Medium", sans-serif;
    color: #666;
    font-weight: normal;
    font-size: 56px;
    margin: 15px;
}
ul {
    font-family: Arial, Helvetica, sans-serif;
    font-size: 13pt;
    line-height: 30px;
    padding: 0px;
}
.filterSection li {
    list-style: none;
    margin: 0px;
    padding: 5px;
    display: inline;
     
}
.filterSection {
    margin: 0px;
    padding: 0px;
    background-color: #EEEEEE;
}
#itemsToFilter li {
    list-style: none;
    background-position: 0 3px;
    background-repeat: no-repeat;
    margin: 15px;
    padding-left: 40px;
    font-size: 15pt;
    color: #666;
}
#itemsToFilter li[data-type=food] {
    background-image: url("//www.kirupa.com/mini_icons/entypo/small_leaf.png");
}
  
#itemsToFilter li[data-type=place] {
    background-image: url("//www.kirupa.com/mini_icons/entypo/small_plane.png");
}
 
#itemsToFilter li[data-type=musician] {
    background-image: url("//www.kirupa.com/mini_icons/entypo/small_megaphone.png");
}
.showItem {
    display: list-item;
}
.hideItem {
    display: none;
}
 
</style>
</head>
 
<body>
 
<h1>Filtering Example</h1>
			<div id="masterdiv">
				<%  TestCrud tc=new TestCrud();
					ArrayList<InventoryMini> list = tc.searchProduct("Vendor1");
					for(InventoryMini item : list){
					%> <div data-type="<%=item.getProductType() %>">
						Value is <%=item.getProductType() %>
						</div>
					<% } %>
					</div>
					<div id="checkfilter">
<ul class="filterSection">
    <li>
        <strong>Show:</strong>
        <input checked="true" type="checkbox" value="VEGETABLES"/>
        <label>Food</label>
    </li>
    <li>
        <input checked="true" type="checkbox" value="FRUITS"/>
        <label>Place</label>
    </li>
    <li>
        <input checked="true" type="checkbox" value="MEAT"/>
        <label>Musician</label>
    </li>
</ul>
 </div>
<ul id="itemsToFilter">
    <li data-type="food">Pasta</li>
    <li data-type="place">Michigan</li>
    <li data-type="food">Celery</li>
    <li data-type="place">Westeros</li>
    <li data-type="musician">Bon Jovi</li>
    <li data-type="musician">Taylor Swift</li>
    <li data-type="place">Portland</li>
    <li data-type="food">Waffles</li>
    <li data-type="musician">Bono</li>
    <li data-type="food">Fried Chicken</li>
    <li data-type="place">New Zealand</li>
    <li data-type="musician">Bruce Springsteen</li>
    <li data-type="food">Bananas</li>
    <li data-type="musician">Beach Boys</li>
    <li data-type="place">Idaho</li>
    <li data-type="musician">Justin Bieber</li>
</ul>
 
<script>
//get all of our list items
//var itemsToFilter = document.querySelectorAll("#itemsToFilter li");
var div = document.getElementById('masterdiv').children;
//var typeDivs = div.getElementsByTagName('div'); 
//setup click event handlers on our checkboxes
var checkBoxes = document.getElementById("checkfilter").getElementsByTagName("li");
var filteredList=[];
alert(checkBoxes.length);
for (var i = 0; i < checkBoxes.length; i++) {
    checkBoxes[i].addEventListener("click", filterItems, false);
    checkBoxes[i].checked = false;
}
  
// the event handler!
function filterItems(e) {
    var clickedItem = e.target;
      
    if (clickedItem.checked == true) {
        showItems(clickedItem.value, "hideItem", "showItem");
    } else if (clickedItem.checked == false) {
        hideItems(clickedItem.value, "showItem", "hideItem");
    } else {
        // deal with the indeterminate state if needed
    }
}
  
// add or remove classes to show or hide our content
function showItems(itemType, classToRemove, classToAdd) {
    for (var i = 0; i < div.length; i++) {
        var currentItem = div[i];
          
        if (currentItem.getAttribute("data-type") == itemType) {
        	filteredList.push(currentItem);
           // removeClass(currentItem, classToRemove);
          //  addClass(currentItem, classToAdd);
          currentItem.style.display='block';
        }
        else{
        	currentItem.style.display='none';
        }
    }
}
function hideItems(itemType, classToRemove, classToAdd) {
    for (var i = 0; i < div.length; i++) {
        var currentItem = div[i];
        var indx=filteredList.indexOf(currentItem);
        if(indx > -1){
        	if (currentItem.getAttribute("data-type") == itemType) {
           // removeClass(currentItem, classToRemove);
          //  addClass(currentItem, classToAdd);
         	  currentItem.style.display='none';
         	  filteredList.splice(indx,1);
        	}
        	else{
                currentItem.style.display='block';
        	}
        }
        if(filteredList.length==0){
        	for (var i = 0; i < div.length; i++) {
                var cItem = div[i];
            	cItem.style.display='none';
       		 }
        }
      }
}
//
// Helper functions for adding and removing class values
//
function addClass(element, classToAdd) {
    var currentClassValue = element.className;
        
    if (currentClassValue.indexOf(classToAdd) == -1) {
        if ((currentClassValue == null) || (currentClassValue === "")) {
            element.className = classToAdd;
        } else {
            element.className += " " + classToAdd;
        }
    }
}
        
function removeClass(element, classToRemove) {
    var currentClassValue = element.className;
  
    if (currentClassValue == classToRemove) {
        element.className = "";
        return;
    }
  
    var classValues = currentClassValue.split(" ");
    var filteredList = [];
  
    for (var i = 0 ; i < classValues.length; i++) {
        if (classToRemove != classValues[i]) {
            filteredList.push(classValues[i]);
        }
    }
  
    element.className = filteredList.join(" ");
}
 
</script>
 
</body>
 
</html>