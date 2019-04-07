<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加页面</title>

<link rel="stylesheet" href="<%=basePath %>layui-v2.4.5/layui/css/layui.css">
<script src="<%=basePath %>layui-v2.4.5/layui/layui.js"></script>
<script src="<%=basePath %>jq/jquery.min.js"></script>
<script src="<%=basePath %>vue/vue.min.js"></script>

</head>
<style>
.layui-form-item {
	margin-bottom: 3px;
}
</style>
<body id="app">
	<div style="padding: 10px;" class="layui-form layui-form-pane">
		
		<div class="layui-form-item">
			<label class="layui-form-label">编号</label>
			<div class="layui-input-block">
				<input type="text"  v-model="bianhao" value="${client.bianhao}"  autocomplete="off"
					placeholder="请输入  编号" class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">姓名</label>
			<div class="layui-input-block">
				<input type="text"  v-model="name" value="${client.name}"  autocomplete="off"
					placeholder="请输入  姓名" class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">电话</label>
			<div class="layui-input-block">
				<input type="text"  v-model="phone" value="${client.phone}"  autocomplete="off"
					placeholder="请输入  电话" class="layui-input">
			</div>
		</div>
		
		
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text"   v-model="remark" value="${client.remark}" autocomplete="off"
					placeholder="请输入  备注" class="layui-input">
			</div>
		</div>
		
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="demo1"
					onclick="save()">添加</button>
			</div>
		</div>
		
	</div>
</body>

	<script type="text/javascript">
	
	var save_url = '<%=basePath%>/admin/insert';
	function save() {
		var index = layer.msg('提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});
		
		$.post(save_url, {
			bianhao : app.bianhao,name:app.name,
			phone : app.phone,
			remark : app.remark
		}, function(result) {
			// 如果返回为true
			if (result.success) {
				window.parent.closeDlg(result.msg);
			} else {
				layer.msg(result.msg);
			}
		}, 'json');
	}
	
	
	layui.use([ 'laydate', 'laypage', 'layer', 'table', 'carousel', 'upload',
			'element' ], function() {
		var laydate = layui.laydate //日期
		, laypage = layui.laypage //分页
		, layer = layui.layer //弹层
		, table = layui.table //表格
		, carousel = layui.carousel //轮播
		, upload = layui.upload //上传
		, element = layui.element; //元素操作
		
	});
	
	
	var app = new Vue({
		el : '#app',
		data : {
		}
	});
	</script>

</html>