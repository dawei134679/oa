<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>仓库管理-新增</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <%@ include file="../common/common.jsp" %>
</head>
<body>
<form class="layui-form" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">仓库名称</label>
    <div class="layui-input-block" style="width: 300px;">
      <select id="warehouseName" name="warehouseName" lay-verify="required" lay-search="">
      </select>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">仓库负责人</label>
    <div class="layui-input-block" style="width: 300px;">
      <select id="administratorName" name="administratorName" lay-verify="required" lay-search="">
      </select>
    </div>
  </div>
</form>
  <div class="layui-form-item">
    <div class="layui-input-block" >
      <button class="layui-btn" id="subBtn">保存</button>
      <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
    </div>
  </div>
 </body>
 </html>
<script>
function initSelect(){
	//layui select添加完需要渲染
	layui.use('form', function(){
        var form = layui.form; 
        form.render();
   }); 
}
$(function(){
	
	$("#warehouseName").empty();
	$("#administratorName").empty();
	// 获取全部仓库start
		$.ax({
		 url:"${ctx}/admin/cwarehouse/getAllCwarehouselist",
		 success:function(res){
			 if(res.code!=200){
				 layer.msg('查询仓库列表失败',{time:1000});
				 return;
			 }
				 $("#warehouseName").append("<option value='-1'>请选择仓库</option>");
				 $(res.data).each(function(index, element){
						 $("#warehouseName").append("<option value="+element.id+">"+element.name+"</option>");
				 });
			 initSelect();
		 }
	 });
	// 获取全部仓库end
	
	// 获取全部员工start 
 	$.ax({
		 url:"${ctx}/admin/user/getALlUserInfoList",
		 success:function(res){
			 if(res.code!=200){
				 layer.msg('查询员工列表失败',{time:1000});
				 return;
			 }
				 $("#administratorName").append("<option value='-1'>请选择负责人</option>");
				 $(res.data).each(function(index, element){
						 $("#administratorName").append("<option value="+element.uid+">"+element.userName+"</option>");
				 });
			 initSelect();
		 }
	 });
 	// 获取全部员工end
 	
	$("#subBtn").click(function(){
		 var warehouseId = $("#warehouseName").val();
		 if(warehouseId.length==0 || warehouseId == -1){
			 layer.alert('仓库不能为空');
			 return;
		 }
		 var uid = $("#administratorName").val();
		 if(uid.length==0 || uid == -1 ){
			 layer.alert('仓库管理员不能为空');
			 return;
		 }
		 $.ax({
			url:'${ctx}/admin/SWhUser/saveSWhUser',
			data:{'warehouseId':warehouseId,'uid':uid},
			success:function(res){
					if(res.code!=200){
						layer.msg('保存失败',{time:1000});
						return;
					}
					parent.layer.closeAll();
					parent.table.reload('tableList');
				}
		});
	});
	$("#cancelBtn").click(function(){
		parent.layer.closeAll();
	});
});
</script>