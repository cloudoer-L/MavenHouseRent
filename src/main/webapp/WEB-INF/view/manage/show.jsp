<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>青鸟租房 -发布房屋信息</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="scripts/jquery-1.12.4.js"></script>
<script type="text/javascript" src="scripts/function.js"></script>
</head>
<body>
	<div id="header" class="wrap">
		<div id="logo">
			<img src="images/logo.gif" />
		</div>

	</div>
	<div id="regLogin" class="wrap">
		<div class="dialog">
			<dl class="clearfix">
				<dt>新房屋信息发布</dt>
				<dd class="past">填写房屋信息</dd>

			</dl>
			<div class="box">
				<form id="add_form" method="post" onsubmit="house_add_js.house_submit();">
					<input type="hidden" id="house_id" name="house.id"/>
					<div class="infos">
						<table class="field">
							<tr><td colspan="2" style="text-align: center;"><img src="images/thumb_house.gif"/></td></tr>
							<tr>
								<td class="field">标 题：</td>
								<td><input id="add_title" type="text" class="text" name="house.title" readonly/></td>
							</tr>
							<tr>
								<td class="field">户 型：</td>
								<td><select id="add_type" class="text" name="typeId" readonly></select></td>
							</tr>
							<tr>
								<td class="field">面 积：</td>
								<td><input id="add_floorage" type="text" class="text" name="house.floorage" disabled/></td>
							</tr>
							<tr>
								<td class="field">价 格：</td>
								<td><input id="add_price" type="text" class="text" name="house.price" readonly/></td>
							</tr>
							<!-- 
						<tr>
							<td class="field">房产证日期：</td>
							<td><input type="text" class="text" name="houseDate" /></td>
						</tr>
						-->
							<tr>
								<td class="field">位 置：</td>
								<td>区：<select id="add_district" class="text" name="district_id" disabled onchange="house_add_js.street_load();"></select> 
									街：<select id="add_street" class="text" name="streetId" disabled></select>
								</td>
							</tr>
							<!-- 
						<tr>
							<td class="field">坐  标：</td>
							<td><input type="text" class="text" name="point" />
							</td>
						</tr>
						-->
							<!--  <tr>
							<td class="field">Y 坐  标：</td>
							<td><input type="text" class="text" name="point.y" /></td>
						</tr>-->
							<tr>
								<td class="field">联系方式：</td>
								<td><input id="add_contact" type="text" class="text" name="house.contact" readonly/></td>
							</tr>
							<tr>
								<td class="field">详细信息：</td>
								<td><textarea id="add_description" name="house.description" readonly></textarea></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="footer" class="wrap">
		<dl>
			<dt>青鸟租房 &copy; 2010 北大青鸟 京ICP证1000001号</dt>
			<dd>关于我们 · 联系方式 · 意见反馈 · 帮助中心</dd>
		</dl>
	</div>
	<script type="text/javascript">

	var house_add_js = {
		init: function(){
			house_add_js.district_load();
			house_add_js.type_load();
		},district_load: function(){
			$.ajax({
				url:'other/getDistricts.action',
				type:'post',
				success : function(data) {
					var result = JSON.parse(data);
					$('#add_district').html('');
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
					$('#add_type').html('');
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
						$('#add_street').html('');
						for (i = 0; i < result.length; i++){
							$('#add_street').append('<option value="'+result[i].id+'">'+result[i].name+'</option>');
						}
						
					}
				});
		},house_message_load: function(){
			var _houseId = <%=request.getAttribute("houseId")%>;
			if (_houseId != null){
				$('#house_id').val(_houseId);
				$.ajax({
					url: 'manage/getHouseById',
					data: {houseId:_houseId},
					type: 'post',
					success: function(data){
						var result = JSON.parse(data);
						$('#add_title').val(result[0].title);
						$('#add_floorage').val(result[0].floorage);
						$('#add_price').val(result[0].price);
						$('#add_contact').val(result[0].contact);
						$('#add_description').val(result[0].description);
						if (result[0].type != null){
							$('#add_type').val(result[0].type.id);
						}
						if (result[0].street != null){
							if (result[0].street.district != null){
								$('#add_district').val(result[0].street.district.id);
								house_add_js.street_load();
								$('#add_street').val(result[0].street.id);
							}
						}
					}
				});
			}
		}
	}
	
	var house_add = (function(){
		$(function(){
			house_add_js.init();
			house_add_js.house_message_load();
		});
	})();

</script>
</body>
</html>

