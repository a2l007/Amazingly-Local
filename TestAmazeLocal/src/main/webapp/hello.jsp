<%@page import="com.iu.amazelocal.ui.GetInfo"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>
   <p>
   <%for(int i=0;i<5;i++){
   %>
    Hello 
    <%
    GetInfo theClassInstance = new GetInfo();
    String msg=theClassInstance.getData();
%>
<%= msg %> 
<%} %></p>
</body>
</html>