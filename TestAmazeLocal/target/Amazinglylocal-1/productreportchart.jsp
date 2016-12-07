<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="com.iu.amazelocal.db.InventoryCrud"%>
<%@page import="com.iu.amazelocal.models.ProductTypeDao" %>
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
	ArrayList<ProductTypeDao> revenueList=vc.fetchProductTypeQuantity();

	int count=0;
	for(ProductTypeDao vd:revenueList){
		++count;
		map = new HashMap<Object,Object>(); 
		map.put("x", count); 
		map.put("y", vd.getQuantity());
		map.put("label",vd.getProductType());
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
				text: "Category-wise product distribution"
			},
			subtitles: [
				{ text: "Overall distribution" }
			],
			data: [{
				type: "pie", //change type to bar, line, area, pie, etc
				dataPoints: <%out.print(dataPoints);%>
			}]
		});
		chart.render();
		
	});
	</script>