<%@page import="com.iu.amazelocal.models.ProductUser"%>
<%@page import="com.iu.amazelocal.db.VendorCrud"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
Search results
</head>
<body>
<% 
VendorCrud vc=new VendorCrud();
ArrayList<ProductUser> list = vc.fetchUserProductReport();
%>
<table>
<%for (ProductUser p:list) {
	String productName=p.productName;
	int userId=p.getUserId();
	%><tr><td>
	<%=productName %></td>
	<td><%=userId %></td></tr>
	<%} %>
	
	</table>

</body>
</html>