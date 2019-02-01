<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>用户管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <%@ include file="../common/common.jsp" %>
</head>
<body>
 <div class="layui-fluid" style="padding: 0px;">
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto" style="height: auto;padding: 0px;" >
        <div class="layui-form-item">
          <div class="layui-inline">
            <label class="layui-form-label">员工编号</label>
            <div class="layui-input-block">
              <input type="text" name="userCode" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-block">
              <input type="text" name="userName" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
             <button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-user-back-search">
              <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
            </button>
          </div>
        </div>
      </div>
		<div class="layui-card-body" style="padding: 0px;">
			<table class="layui-hide" id="tableList" lay-filter="tableList"></table>
		</div>
	  </div>
  </div>
</body>
</html>
<script type="text/html" id="mytoolbar" >
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add">新增</button>
    <button class="layui-btn layui-btn-sm" lay-event="update">修改</button>
    <button class="layui-btn layui-btn-sm" lay-event="del">删除</button>
	<button class="layui-btn layui-btn-sm" lay-event="reload">刷新</button>
  </div>
</script>
<script type="text/javascript">
var table =  null,form = null;
var tableIns = null;
$(function(){
	  table = layui.table;form = layui.form;
	  tableIns = table.render({
		     elem: '#tableList'
         	,toolbar: '#mytoolbar'
         	,method:'post'
  	   	    ,defaultToolbar:['filter', 'print']
  	        ,initSort: {field:'userCode', type:'asc'}
		    ,even: true
	   	    ,height: $(window).height()-$(".layui-card-header").outerHeight(true)
		    ,url:'${ctx}/admin/user/getUserInfoPage'
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
		      ,{field: 'id', title: 'ID', width: 100,hide:true}
		      ,{field: 'userCode', title: '员工编号', width: 120,align:'center',sort:true}
		      ,{field: 'userName', title: '姓名', width: 150,align:'center',sort:true}
		      ,{field: 'orgCode', title: '所属部门编码', width: 150,align:'center',hide:true,sort:true}
		      ,{field: 'orgName', title: '所属部门名称', width: 250,align:'center',sort:true}
		      ,{field: 'userType', title: '用户类型', width: 120,align:'center',sort:true,templet: function(d){
		    	  if(d.userType == 1){
		    		  return "系统管理员";
		    	  }else{
		    		  return "员工";
		    	  }
		      }}
		      ,{field: 'sex', title: '性别', width: 100,align:'center',sort:true,templet: function(d){
		    	  if(d.sex == 1){
		    		  return "男";
		    	  }else{
		    		  return "女";
		    	  }
		      }}
		      ,{field: 'phoneNumber', title: '手机号', width: 150,align:'center',sort:true}
		      ,{field: 'contactNumber', title: '联系电话', width: 150,align:'center',sort:true}
		      ,{field: 'post', title: '岗位', width: 200,align:'center',sort:true}
		    ]]
		    ,page:true
		    ,limit:10
		  });
	  
	  table.on('toolbar(tableList)', function(obj){
		  switch(obj.event){
		  		case 'add':
		  			layer.open({
		  			  type: 2,
		  			  title: '用户管理-新增',
		  			  shadeClose: false,
		  			  shade: 0.8,
		  			  area: ['600', $(window).height()-40],
		  			  content: '${ctx}/admin/user/toUserAddPage'
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
		  				  title: '用户管理-修改',
		  				  shadeClose: false,
		  				  shade: 0.8,
		  				  area: ['600', $(window).height()-40],
		  				  content: '${ctx}/admin/user/toUserEditPage?id='+id
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
		  						url:'${ctx}/admin/user/delUserByIds',
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
	  //监听搜索
      form.on('submit(LAY-user-back-search)', function(data){
	      var field = data.field;
	      table.reload('tableList', {
	        where: field
	      });
    });
   $(window).resize(function(){
	  tableIns.reload({
		  height: $(window).height()-$(".layui-card-header").outerHeight(true)
	  });
   });
});
</script>

