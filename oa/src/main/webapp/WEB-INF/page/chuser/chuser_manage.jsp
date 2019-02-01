<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>仓库管理员</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <%@ include file="../common/common.jsp" %>
</head>
<body>
	<div>
		<table class="layui-hide" id="tableList" lay-filter="tableList"></table>
	</div>
</body>
</html>
<script type="text/html" id="mytoolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add">新增</button>
    <button class="layui-btn layui-btn-sm" lay-event="update">修改</button>
    <button class="layui-btn layui-btn-sm" lay-event="del">删除</button>
	<button class="layui-btn layui-btn-sm" lay-event="reload">刷新</button>
  </div>
</script>
<script type="text/javascript">
  var table = null;
  $(function(){
  table = layui.table;
  table.render({
	     elem: '#tableList'
   	    ,toolbar: '#mytoolbar'
   	    ,defaultToolbar:['filter', 'print']
        ,initSort: {field:'code', type:'asc'}
	    ,even: true
   	    ,height: 'full-20'
	    ,url:'${ctx}/admin/SWhUser/getSWhUserPage'
   	    ,request: {
   		     pageName: 'page' //页码的参数名称，默认：page
   		    ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
   		  }
   	    ,response: {
    	   statusCode: 200
   	    }
	    ,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
   	      return {
   	        "code": res.code, //解析接口状态
   	        "msg": res.msg, //解析提示文本
   	        "count": res.data.total, //解析数据长度
   	        "data": res.data.list //解析数据列表
   	      };
   	    }
	    ,cols: [[
	       {type: 'checkbox', fixed: 'left'}
	      ,{field: 'whName', title: '仓库名称', width: 250,align:'center',sort: true}
	      ,{field: 'userName', title: '仓库管理员员', width: 250,align:'center',sort: true}
	      ,{field: 'phoneNumber', title: '管理员联系方式', width: 250,align:'center',sort: true}
	    ]]
	    ,page:true
	    ,limit:10
	  });
  
  table.on('toolbar(tableList)', function(obj){
	  switch(obj.event){
	  		case 'add':
	  			layer.open({
	  			  type: 2,
	  			  title: '仓库管理员-新增',
	  			  shadeClose: false,
	  			  shade: 0.8,
	  			  area: ['500', '400'],
	  			  content: '${ctx}/admin/SWhUser/toSWhUserAddPage'
	  			}); 
 		   	break;
	  		case 'update':
	  			var checkStatus = table.checkStatus('tableList');
	  			if(checkStatus==null||checkStatus.data.length==0){
	  				layer.msg('请选择数据',{time:1000});
	  				return;
	  			}
	  			if(checkStatus.data.length>1){
	  				layer.msg('请选择一条数据',{time:1000});
	  				return;
	  			}
	  			var id=checkStatus.data[0].id;
	  			layer.open({
	  				  type: 2,
	  				  title: '仓库管理员-修改',
	  				  shadeClose: false,
	  				  shade: 0.8,
	  				  area: ['500', '400'],
	  				  content: '${ctx}/admin/SWhUser/toSWhUserEditPage?id='+id
	  				}); 
	 		break;
	  		case 'del':
	  			var checkStatus = table.checkStatus('tableList');
	  			if(checkStatus==null||checkStatus.data.length==0){
	  				layer.msg('请选择数据',{time:1000});
	  				return;
	  			}
	  			var ids=[];
	  		    $.each(checkStatus.data,function(index,node){
	  		    	ids.push(node.id);
	  		    });
	  			layer.confirm('确定删除该数据吗？', {
	  				  btn: ['确定','取消']
	  				}, function(){
	  					 $.ax({
	  						url:'${ctx}/admin/SWhUser/delSWhUserByIds',
	  						data:{'ids':ids.join(',')},
	  						success:function(res){
	  								if(res.code!=200){
	  									layer.msg('删除失败',{time:1000});
	  									return;
	  								}
	  								layer.msg('删除成功',{time:1000});
	  								table.reload('tableList');
	  							}
	  						});
	  				}, function(){});
	 		break;
	  		case 'reload':
	  			table.reload('tableList');
  			break;
	  };
  });
});
</script>