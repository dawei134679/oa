<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>物品管理-修改</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <%@ include file="../common/common.jsp" %>
</head>
<body>
<form class="layui-form" action="">
  <input type="text" id="id" name="id" style="display:none" value="${data.id }">    
  <div class="layui-form-item">
    <label class="layui-form-label">物品编号</label>
    <div class="layui-input-block" style="width: 300px;">
      <input type="text" id="code" name="code" required lay-verify="required" placeholder="请输入物品编号" autocomplete="off" class="layui-input" value="${data.code }">    
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">物品名称</label>
    <div class="layui-input-block" style="width: 300px;">
      <input type="text" id="name" name="name" required lay-verify="required" placeholder="请输入物品名称" autocomplete="off" class="layui-input" value="${data.name }">    
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">物品单位</label>
    <div class="layui-input-block" style="width: 300px;">
      <input type="text" id="unit" name="unit" required lay-verify="required" placeholder="请输入物品单位" autocomplete="off" class="layui-input" value="${data.unit }">    
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">备注</label>
    <div class="layui-input-block" style="width: 300px;">
      <textarea id="note" name="note" required lay-verify="required" placeholder="${data.note }" class="layui-textarea" ></textarea>
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
$(function(){
	$("#subBtn").click(function(){
		 var id = $("#id").val();
		 var code=$("#code").val();
		 if(code.length==0){
			 layer.alert('物品编码不能为空');
			 return;
		 }
		 var name=$("#name").val();
		 if(name.length==0){
			 layer.alert('物品名称不能为空');
			 return;
		 }
		 var unit=$("#unit").val();
		 if(unit.length==0){
			 layer.alert('物品单位不能为空');
			 return;
		 }
		 var note=$("#note").val();
		 $.ax({
			url:'${ctx}/admin/wgoods/updateWgoodsConfig',
			data:{'id':id,'code':code,'name':name,'unit':unit,'note':note},
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