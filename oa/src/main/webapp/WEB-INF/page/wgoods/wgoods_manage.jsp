<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>物品管理</title>
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
            <label class="layui-form-label">物品编号</label>
            <div class="layui-input-block">
              <input type="text" name="goodsCode" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">物品名称</label>
            <div class="layui-input-block">
              <input type="text" name="goodsName" placeholder="请输入" autocomplete="off" class="layui-input">
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
<script type="text/html" id="mytoolbar">
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
  table = layui.table;
  form = layui.form;
  tableIns = table.render({
	     elem: '#tableList'
   	    ,toolbar: '#mytoolbar'
   	    ,method:'post'
   	    ,defaultToolbar:['filter', 'print']
        ,initSort: {field:'code', type:'asc'}
	    ,even: true
   	    ,height: $(window).height()-$(".layui-card-header").outerHeight(true)
	    ,url:'${ctx}/admin/wgoods/getWgoodsConfigPage'
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
	      ,{field: 'code', title: '物品编码', width: 150,align:'center',sort: true}
	      ,{field: 'name', title: '物品名称', width: 250,align:'center',sort: true}
	      ,{field: 'unit', title: '物品单位', width: 250,align:'center',sort: true}
	      ,{field: 'note', title: '备注', width: 300}
	      ,{field: 'status', title: '状态', width: 100,align:'center',hide:true,templet: function(d){
	    	  if(d.status == 1){
	    		  return "有效";
	    	  }else{
	    		  return "<font color='red'>无效</font>";
	    	  }
	      }}
	      ,{field: 'createTime',title: '录入时间',width: 200,align:'center',sort: true,templet: function(d){
	    	  return d.createTime ? new Date(d.createTime).Format("yyyy-MM-dd HH:mm:ss") : "-";
		  }}
		  ,{field: 'updateTime',title: '最后修改时间',width: 200,align:'center',sort: true,templet:function(d){
			  return d.updateTime ? new Date(d.updateTime).Format("yyyy-MM-dd HH:mm:ss") : "-";
		  }}
	    ]]
	    ,page:true
	    ,limit:10
	  });
  
  table.on('toolbar(tableList)', function(obj){
	  switch(obj.event){
	  		case 'add':
	  			layer.open({
	  			  type: 2,
	  			  title: '物品管理-新增',
	  			  shadeClose: false,
	  			  shade: 0.8,
	  			  area: ['500', '400'],
	  			  content: '${ctx}/admin/wgoods/toWgoodsInfoAddPage'
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
	  				  title: '物品管理-修改',
	  				  shadeClose: false,
	  				  shade: 0.8,
	  				  area: ['500', '400'],
	  				  content: '${ctx}/admin/wgoods/toWgoodsInfoEditPage?id='+id
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
	  						url:'${ctx}/admin/wgoods/delWgoodsInfoByIds',
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