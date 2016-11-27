<%@page import="com.iu.amazelocal.models.InventoryMini"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
Search results
</head>
<body>
<% 
ArrayList<InventoryMini> list = (ArrayList<InventoryMini>) request.getAttribute("searchresults");
for(InventoryMini item : list) 
{
   %><p><%=item.getProductName() %></p>
   <p><%=item.getInventoryId() %></p>
   <p><%=item.getImageName() %></p>
<% } %>

</body>
</html>