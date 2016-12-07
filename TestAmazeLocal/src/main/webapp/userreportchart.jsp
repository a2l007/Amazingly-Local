<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="com.iu.amazelocal.db.UserCrud"%>
<%@page import="com.iu.amazelocal.models.ProductSaleDao" %>
<%@page import="com.iu.amazelocal.models.UserRegisterDao"%>


<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<div id='chartContainer' style="width: 80%; height:400px;"></div>
<div id='dupchartContainer' style="width: 80%; height:400px;"></div>

<%
	Gson gsonObj = new Gson();
	Map<Object,Object> map = null;
	Map<Object,Object> yearMap = null;

	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
	List<Map<Object,Object>> yearList = new ArrayList<Map<Object,Object>>();

	UserCrud vc=new UserCrud();
	ArrayList<UserRegisterDao> revenueList=vc.fetchUserRegReport("Month");
	ArrayList<UserRegisterDao> yearUserList=vc.fetchUserRegReport("Year");

	int count=0;
	for(UserRegisterDao vd:revenueList){
		++count;
		map = new HashMap<Object,Object>(); 
		map.put("x", count); 
		map.put("y", vd.getBuyerCount());
		map.put("label",vd.getRegPeriod());
		list.add(map);

	}
	String dataPoints = gsonObj.toJson(list);
	
	int yearcount=0;
	for(UserRegisterDao vd:yearUserList){
		++yearcount;
		yearMap = new HashMap<Object,Object>(); 
		yearMap.put("x", count); 
		yearMap.put("y", vd.getBuyerCount());
		yearMap.put("label",vd.getRegPeriod());
		yearList.add(yearMap);

	}
	String yeardataPoints = gsonObj.toJson(yearList);
	%>

	<script type="text/javascript">
	$(function () {
		var chart = new CanvasJS.Chart("chartContainer", {
			theme: "theme2",
			animationEnabled: true,
			title: {
				text: "Distribution of buyer registration"
			},
			subtitles: [
				{ text: "Monthwise distribution" }
			],
			data: [{
				type: "column", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(dataPoints);%>
			}]
		});
		chart.render();
		
		var dupChart = new CanvasJS.Chart("dupchartContainer", {
			theme: "theme2",
			animationEnabled: true,
			title: {
				text: "Distribution of buyer registration"
			},
			subtitles: [
				{ text: "Yearwise distribution" }
			],
			data: [{
				type: "column", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(yeardataPoints);%>
			}]
		});
		dupChart.render();
	});
	</script>