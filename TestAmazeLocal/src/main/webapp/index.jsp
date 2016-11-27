<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="com.iu.amazelocal.db.VendorCrud"%>
<%@page import="com.iu.amazelocal.models.ProductSaleDao" %>
<%@page import="com.iu.amazelocal.models.VendorRevenueDao"%>


<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<div id='chartContainer'></div>
<div id='chartContainer1'></div>

<%
	Gson gsonObj = new Gson();
	Map<Object,Object> map = null;
	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
	VendorCrud vc=new VendorCrud();
	ArrayList<VendorRevenueDao> revenueList=vc.fetchRevenueReport();
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
	
	List<Map<Object,Object>> prodList = new ArrayList<Map<Object,Object>>();
	ArrayList<ProductSaleDao> saleList=vc.fetchProductSaleReport();
	count=0;
	for(ProductSaleDao vd:saleList){
		++count;
		map = new HashMap<Object,Object>(); 
		map.put("x", count); 
		map.put("y", vd.getUnitSold());
		map.put("label",vd.getProductName());
		prodList.add(map);

	}
	String prodDPoints = gsonObj.toJson(prodList);
	%>

	<script type="text/javascript">
	$(function () {
		var chart = new CanvasJS.Chart("chartContainer", {
			theme: "theme2",
			animationEnabled: true,
			title: {
				text: "Simple Column Chart in Spring Web MVC"
			},
			subtitles: [
				{ text: "Try Resizing the Browser" }
			],
			data: [{
				type: "column", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(dataPoints);%>
			}]
		});
		chart.render();
		
		var prodchart = new CanvasJS.Chart("chartContainer1", {
			theme: "theme2",
			animationEnabled: true,
			title: {
				text: "Simple Column Chart in Spring Web MVC"
			},
			subtitles: [
				{ text: "Try Resizing the Browser" }
			],
			data: [{
				type: "column", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(prodDPoints);%>
			}]
		});
		prodchart.render();
	});
	</script>