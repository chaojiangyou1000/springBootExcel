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
<title>客户管理</title>

<!--添加 layui  支持加载-->
<link rel="stylesheet"	href="/layui-v2.4.5/layui/css/layui.css">
<script	src="/layui-v2.4.5/layui/layui.js"></script>
<!--添加 layui  支持加载-->

<!--添加 jq  支持加载-->
<script	src="/jq/jquery_2_1.min.js"></script>
<!--添加 jq  支持加载-->

<!--添加 layui 自己的js  支持加载-->
<script	src="/manage/manage_base.js"></script>
<!--添加 layui 自己的js  支持加载-->

<!--添加异步 上传 的js  支持加载-->
<script	src="/AjaxFileUpload/ajaxfileupload.js"></script>
<!--添加异步 上传 的js  支持加载-->

</head>

<style>
body {
	padding-top: 3px;
}

.layui-table-cell {
	padding-left: 0px;
	padding-right: 0px;
	text-align: center;
}
</style>

<body>
	<div class="layui-table-toolbar" style="margin-bottom: 3px; ">
		<div class="layui-btn-group">
			<button onclick="add()"  class="layui-btn layui-btn-sm"><i class="layui-icon">&#xe654;</i>添加</button>
		
			<input type="file" id="file" name="file" class="layui-btn" onchange ="uploadFile(this)"  ></input>
	
			<a href="<%=basePath %>admin/downloadExcel"  class="layui-btn layui-btn-sm"><i class="layui-icon">&#xe640;</i>导出excel</a>
	    	<button class="layui-btn layui-btn-sm" onclick="reload_data()"><i class="layui-icon">&#xe669;</i>刷新</button>
 	 	</div>
	</div>

	<div class="layui-form layui-form-pane" style="margin-bottom: 3px; "  id="app"  >
		<div class="layui-form-item" style="margin-bottom: 1px;">
	    	<label class="layui-form-label" style="width: 97px;">模糊查询</label>
	    	<div class="layui-input-inline" style="width: 150px; margin-right: 0px;">
	           	<input id="q" type="text" autocomplete="off" placeholder="编号 姓名 模糊查询" class="layui-input">
	    	</div>
	    	<a class="layui-btn" onclick="reload_data()" style="margin-left: 20px;">查询</a>    
		</div>
	</div>

	<table class="layui-hide"   id="table" lay-filter="table"></table>
</body>

	<script type="text/javascript">
		//定义在外面。使用。
	var table;
		layui.use([ 'laydate', 'laypage', 'layer','form', 'table', 'carousel',
				'upload', 'element' ], function() {
		table = layui.table;
	});

	var global_ids;
	var global_ids_len;

	var w ;//窗口的宽
	var h ;//窗口的高

	//添加
	function add(){
		w = 800;
		h = 600;
		checkWindow();
		//iframe层
		layer.open({
	  	type: 2,
	  	title: '添加数据',
	  	shadeClose: false,
	  	shade: 0.8,
	  	area: [w+'px', h+'px'],
	  	content: '<%=basePath%>index/add' //iframe的url
		});
	}

	function edit(id){
		w = 800;
		h = 600;
		checkWindow();
		//iframe层
		layer.open({
	  	type: 2,
	  	title: '修改数据',
	  	shadeClose: false,
	  	shade: 0.8,
	  	area: [w+'px', h+'px'],
	  	content: '/houtai/client/edit?id='+id //iframe的url
		});
	}


	//子窗口调用 的  关闭窗口方法 
	function closeDlg(msg){
	 	layer.closeAll();
	 	layer.msg(msg);
	 	reload_data();
	}

	//相当前刷新  重新加载
	function reload_data(){
  		table.reload('table', {
		 	where: {
	     	},page: {curr: 1 //重新从第 1 页开始
			}
    	});
	}


	function uploadFile(file){
		var index = layer.msg('正在上传，请稍候',{icon: 16,time:false,shade:0.8});
    	$.ajaxFileUpload({  
        	url : '<%=basePath%>admin/upload_excel', //用于文件上传的服务器端请求地址  
        	secureuri : false, //一般设置为false
        	fileElementId : 'file', //文件上传空间的id属性  <input type="file" id="file" name="file" />  
        	type : 'post',  
        	dataType : 'text', //返回值类型 一般设置为 
        	//服务器成功响应处理函数       
        	success : function(result){
        		var result = $.parseJSON(result.replace(/<.*?>/ig,""));
        		alert(result.msg);
        		layer.closeAll();
        	},  
        	//服务器响应失败处理函数
        	error : function(result)  {  
        	}
    	});  
    	return false;
	}
	
	
	layui.use([ 'laydate', 'laypage', 'layer', 'table', 'carousel',
				'upload', 'element' ], function() {
			var laydate = layui.laydate //日期
			, laypage = layui.laypage //分页
			,layer = layui.layer //弹层
			, table = layui.table //表格
			, carousel = layui.carousel //轮播
			, upload = layui.upload //上传
			, element = layui.element; //元素操作
			  table.render({
			    elem: '#table'
			    ,url: '<%=basePath%>admin/pageList'
			    ,response: { //定义后端 json 格式，需要跟后端约定好接口返回数据格式
	     			statusCode: '0', //状态字段成功值
	     			countName: 'count', //总页数字段
	     			dataName: 'clientList' //数据字段
	  			}
			    ,height: 'full-210'
			    ,totalRow: true
				,cols: [[
			       {checkbox: true, fixed: true}
			       
			      ,{field:'bianhao', title: '会员编号', width:90,style:'font-size: 12px;' }
			      ,{field:'name', title: '姓名', width:100,style:'font-size: 12px;' }
			      ,{field:'phone', title: '电话', width:150, sort: true   }
			      ,{field:'remark', title: '备注', width:90,style:'font-size: 12px;' , sort: true }
			      ,{field:'createDateTime', title: '创建时间', width:150,style:'font-size: 12px;' , sort: true}
			      
			      ,{fixed:'right', width:60,title: '操作', align:'center', toolbar: '#table_bar'}
			      
			    ]]
			    ,id: 'table'
			     // 页码
			    ,page: true
			    ,limits:[500,1000,2000,3000],
			   // 
			   limit:10
			  });
			
			//监听工具条 table_bar
				table.on('tool(table)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
					var data = obj.data //获得当前行数据
					, layEvent = obj.event; //获得 lay-event 对应的值
					if (layEvent === 'edit') {
						edit(data.id);
					} 
					
				});
		});
	</script>

</html>