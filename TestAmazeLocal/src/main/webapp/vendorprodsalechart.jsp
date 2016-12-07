<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="com.iu.amazelocal.db.InventoryCrud"%>
<%@page import="com.iu.amazelocal.models.ProductSaleDao" %>
<%@page import="com.iu.amazelocal.models.VendorRevenueDao"%>


<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<div id='chartContainer' style="width: 80%; height:400px;"></div>

<%
	Gson gsonObj = new Gson();
	Map<Object,Object> map = null;
	Map<Object,Object> yearMap = null;

	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
	List<Map<Object,Object>> yearList = new ArrayList<Map<Object,Object>>();

	InventoryCrud vc=new InventoryCrud();
	ArrayList<ProductSaleDao> revenueList=vc.fetchProductUnitSold(2);

	int count=0;
	for(ProductSaleDao vd:revenueList){
		++count;
		map = new HashMap<Object,Object>(); 
		map.put("x", count); 
		map.put("y", vd.getUnitSold());
		map.put("label",vd.getProductName());
		list.add(map);
	}
	String dataPoints = gsonObj.toJson(list);
	
	%>

	<script type="text/javascript">
	$(function () {
		var chart = new CanvasJS.Chart("chartContainer", {
			theme: "theme2",
			animationEnabled: true,
			title: {
				text: "Distribution of vendor sales"
			},
			subtitles: [
				{ text: "Productwise distribution" }
			],
			data: [{
				type: "pie", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(dataPoints);%>
			}]
		});
		chart.render();
		
	});
	</script>