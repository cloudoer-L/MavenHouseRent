<%@page import="cloudoer.hr.entity.Users"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>青鸟租房 - 用户管理</title>
	<link type="text/css" rel="stylesheet" href="css/style.css" />
	<script type="text/javascript" src="scripts/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="scripts/function.js"></script>
	<script type="text/javascript">
		function toUrl(url){

			window.location.href = url;

			return;
		}
		function update(id){
		
			window.location.href = 'manage/updateUI?houseId='+id;

			return;
		}
		function show(id){
		
			window.location.href = 'manage/showUI?houseId='+id;

			return;
		}
	</script>
	<style type="text/css">
		#index_seek_table {
			width:100%;
			text-align: right ;
		}
		#index_seek_table th {
			text-align: right ;
			fout-size:50px;
		}
		#index_seek_table td {
			text-align: left ;
		}
		#index_seek_table td select {
			width: 80px;
		}
	</style>
</head>
<body>
<div id="header" class="wrap">
	<div id="logo"><img src="images/logo.gif" /></div>
	<div class="search">
	<!--
		<form method="get">
			<input class="text" type="text" name="keywords" />
			<label class="ui-green"><input type="button" name="search" value="搜索房屋" /></label>
		</form>
	-->	
		<label id="index_add_button" class="ui-green"><input onclick="toUrl('manage/addUI.action');" type="button" name="search" value="发布房屋信息" /></label>	
		<label class="ui-green"><input id="index_exit" type="button" name="search" value="退       出"/></label>
	</div>
</div>
<div id="index_seek_div" class="wrap">
	<table id="index_seek_table" >
		<tr>
			<th>区：</th>
			<td>
				<select id="add_district" class="text" name="district_id" onchange="house_manage_js.street_load();"></select>
			</td>
			<th>街：</th>
			<td>
				<select id="add_street" class="text" name="streetId"></select>
			</td>
			<th>房型：</th>
			<td>
				<select id="add_type" class="text" name="typeId"></select>
			</td>
			<th><label class="ui-green"><input id="seek_button" type="button" value="搜   索"/></label></th>
			<th><label class="ui-green"><input id="clear_bytton" type="button" value="清   除"/></label></th>
		</tr>
	</table>
</div>
<div class="main wrap">
	<table id="house_message" class="house-list"></table>
	<div class="pager">
		<ul>
			<li><a id="home_page" href="javascript:;">首页</a></li>
			<li><a id="previous_page" href="javascript:;">上一页</a></li>
			<li><a id="next_page" href="javascript:;">下一页</a></li>
			<li><a id="last_page" href="javascript:;">末页</a></li>
		</ul>
		<span class="total">
			第<select id="house_pageNo"><option value="1">1</option></select>页
			共<label id="house_pageCount"></label>页
		</span>
	</div>
</div>
<div id="footer" class="wrap">
	<dl>
    	<dt>青鸟租房 &copy; 2010 北大青鸟 京ICP证1000001号</dt>
        <dd>关于我们 · 联系方式 · 意见反馈 · 帮助中心</dd>
    </dl>
</div>
<script type="text/javascript">
	
	var house_manage_js = {
		streetId: null,
		typeId: null,
		init: function(){
			house_manage_js.house_message_load(1);
			house_manage_js.house_page_load();
			house_manage_js.index_classify();
		},index_classify: function(){
			if ('0'==<%=((Users)request.getSession().getAttribute("user")).getIsadmin() %>){
				$('#index_seek_div').html('');
			}else {
				$('#index_add_button').html('');
				$('.update_btn').html('');
				$('.delete_btn').html('');
				house_manage_js.district_load();
				house_manage_js.type_load();
				//house_manage_js.street_load();
			}
		},house_message_load: function(_pageNo){
			$.ajax({
				url: 'manage/getHousePage.action',
				type: 'post',
				async: false,
				data: {pageNo:_pageNo,streetId:house_manage_js.streetId,typeId:house_manage_js.typeId},
				success : function(data) {
					var result = JSON.parse(data);
					$('#house_message').html('');
					if (result != null && result.length > 0){
						for (i = 0; i < result.length; i++){
							var houseStr = '<tr>'+
								'<td class="house-thumb"><span><a href="manage/showUI?houseId='+result[i].id+'"><img src="images/thumb_house.gif" /></a></span></td>'+
								'<td><dl>'+
									'<dt><a href="manage/showUI?houseId='+result[i].id+'">'+result[i].title+'</a></dt>'+
									'<dd>'+result[i].street.district.name+'区'+result[i].street.name+','+
									result[i].floorage+'平米<br/>联系方式：'+result[i].contact+'</dd>'+
								'</dl></td>'+
								'<td class="house-type"><label class="ui-green update_btn"><input type="button" onclick="update('+result[i].id+');" name="search" value="修    改" /></label></td>'+
								'<td class="house-price"><label class="ui-green delete_btn"><input type="button" name="search" value="删    除" onclick="house_manage_js.delete_house('+result[i].id+')"/></label></td>'+
							'</tr>';
							$('#house_message').append(houseStr);
							if ('0'==<%=((Users)request.getSession().getAttribute("user")).getIsadmin() %>){
								
							}else {
								$('.update_btn').html('');
								$('.delete_btn').html('');
							}
						}
					}else {
						var houseStr = '<tr>无租房信息</tr>';
						$('#house_message').append(houseStr);
					}
				}
			});
		},house_page_load: function(){
			$.ajax({
				url: 'manage/getHouseCount.action',
				type: 'post',
				data: {streetId:house_manage_js.streetId,typeId:house_manage_js.typeId},
				success : function(data) {
					$('#house_pageCount').html('');
					$('#house_pageCount').append(data);
					$('#house_pageNo').html('');
					for (var i = 1; i <= data; i++){
						$('#house_pageNo').append('<option value="'+i+'">'+i+'</option>');
					}
				}
			});
		},btn_load: function(){
			$('#home_page').click(function(){
				var pageNo = parseInt($('#house_pageNo option:selected').val());
				if (1 == pageNo){
					return;
				}else {
					house_manage_js.house_message_load(1);
					$('#house_pageNo option:selected').val(1);
					$('#house_pageNo option:selected').text(1);
				}
			});
			$('#previous_page').click(function(){
				var pageNo = parseInt($('#house_pageNo option:selected').val());
				if (1 == pageNo){
					return;
				}else {
					pageNo = pageNo-1;
					house_manage_js.house_message_load(pageNo);
					$('#house_pageNo option:selected').val(pageNo);
					$('#house_pageNo option:selected').text(pageNo);
				}
			});
			$('#next_page').click(function(){
				var pageCount = parseInt($('#house_pageCount').html().trim());
				var pageNo = parseInt($('#house_pageNo option:selected').val());
				if (pageCount == pageNo){
					return;
				}else {
					pageNo = pageNo+1;
					house_manage_js.house_message_load(pageNo);
					$('#house_pageNo option:selected').val(pageNo);
					$('#house_pageNo option:selected').text(pageNo);
				}
			});
			$('#last_page').click(function(){
				var pageCount = parseInt($('#house_pageCount').html().trim());
				var pageNo = parseInt($('#house_pageNo option:selected').val());
				if (pageCount == pageNo){
					return;
				}else {
					house_manage_js.house_message_load(pageCount);
					$('#house_pageNo option:selected').val(pageCount);
					$('#house_pageNo option:selected').text(pageCount);
				}
			});
			$('#house_pageNo').change(function(){
				var pageNo = parseInt($('#house_pageNo option:selected').val());
				house_manage_js.house_message_load(pageNo);
			});
			$('#index_exit').click(function(){
				if (confirm("确认退出？")){
					window.location.href = '../../../index.jsp';
				}
			});
			$('#add_type').change(function(){
				house_manage_js.typeId = parseInt($('#add_type option:selected').val());
			});
			$('#add_street').change(function(){
				house_manage_js.streetId = parseInt($('#add_street option:selected').val());
			});
			$('#add_district').change(function(){
				house_manage_js.streetId = null;
			});
			$('#seek_button').click(function(){
				house_manage_js.house_message_load(1);
				house_manage_js.house_page_load();
			});
			$('#clear_bytton').click(function(){
				house_manage_js.streetId=null;
				house_manage_js.typeId=null;
				house_manage_js.house_message_load(1);
				house_manage_js.house_page_load();
				house_manage_js.district_load();
				house_manage_js.type_load();
			});
		},delete_house: function(_houseId){
			if (confirm("确认删除？")){
				$.ajax({
					url: 'manage/deleteHouse',
					data: {houseId:_houseId},
					type: 'post',
					success : function(data) {
						alert(data);
					}
				});
				house_manage_js.house_page_load();
				house_manage_js.house_message_load(1);
			}
		},district_load: function(){
			$.ajax({
				url:'other/getDistricts.action',
				type:'post',
				success : function(data) {
					var result = JSON.parse(data);
					$('#add_district').html('<option value="0">请选择</option>');
					for (i = 0; i < result.length; i++){
						$('#add_district').append('<option value="'+result[i].id+'">'+result[i].name+'</option>');
					}
					
				}
			});
		},type_load: function(){
			$.ajax({
				url:'other/getTypes.action',
				type:'post',
				async: false,
				success : function(data) {
					var result = JSON.parse(data);
					$('#add_type').html('<option value="0">请选择</option>');
					for (i = 0; i < result.length; i++){
						$('#add_type').append('<option value="'+result[i].id+'">'+result[i].name+'</option>');
					}
					
				}
			});
		},street_load: function(){
				var _districtId = parseInt($('#add_district option:selected').val());
				$.ajax({
					url:'other/getSteets.action?districtId='+_districtId,
					type:'post',
					async: false,
					success : function(data) {
						var result = JSON.parse(data);
						$('#add_street').html('<option value="0">请选择</option>');
						for (i = 0; i < result.length; i++){
							$('#add_street').append('<option value="'+result[i].id+'">'+result[i].name+'</option>');
						}
						
					}
				});
		}
	}
	
	var house_manage = (function(){
	
		$(function(){
			house_manage_js.init();
			house_manage_js.btn_load();
		});
	})();
</script>
</body>
</html>


