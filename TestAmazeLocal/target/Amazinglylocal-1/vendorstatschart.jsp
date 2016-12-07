<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="com.iu.amazelocal.db.VendorCrud"%>
<%@page import="com.iu.amazelocal.models.ProductSaleDao" %>
<%@page import="com.iu.amazelocal.models.VendorRevenueDao"%>


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

	VendorCrud vc=new VendorCrud();
	ArrayList<VendorRevenueDao> revenueList=vc.fetchRevenueReportById("Month");
	ArrayList<VendorRevenueDao> yearUserList=vc.fetchRevenueReportById("Year");

	int count=0;
	for(VendorRevenueDao vd:revenueList){
		++count;
		map = new HashMap<Object,Object>(); 
		map.put("x", count); 
		map.put("y", vd.getProfit());
		map.put("label",vd.getPayPeriod());
		list.add(map);
	}
	String dataPoints = gsonObj.toJson(list);
	
	int yearcount=0;
	for(VendorRevenueDao vd:yearUserList){
		++yearcount;
		yearMap = new HashMap<Object,Object>(); 
		yearMap.put("x", yearcount); 
		yearMap.put("y", vd.getProfit());
		yearMap.put("label",vd.getPayPeriod());
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
				text: "Distribution of vendor revenue"
			},
			subtitles: [
				{ text: "Monthly distribution" }
			],
			data: [{
				type: "column", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(dataPoints);%>
			}]
		});
		chart.render();
		
		var dupchart = new CanvasJS.Chart("dupchartContainer", {
			theme: "theme2",
			animationEnabled: true,
			title: {
				text: "Distribution of vendor revenue"
			},
			subtitles: [
				{ text: "Yearly distribution" }
			],
			data: [{
				type: "column", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(yeardataPoints);%>
			}]
		});
		dupchart.render();
	});
	</script>