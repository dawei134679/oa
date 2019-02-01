<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>仓库管理-修改</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <%@ include file="../common/common.jsp" %>
</head>
<body>
<form class="layui-form" action="">
  <input type="text" id="id" name="id" style="display:none" value="${data.id }">    
  <div class="layui-form-item">
    <label class="layui-form-label">仓库名称</label>
    <div class="layui-input-block" style="width: 300px;">
      <input type="text" id="name" name="name" required lay-verify="required" placeholder="请输入仓库名称" autocomplete="off" class="layui-input" value="${data.name }">    
    </div>
  </div>
 <div class="layui-form-item">
    <label class="layui-form-label">仓库剩余容量</label>
    <div class="layui-input-block" style="width: 300px;">
      <input type="text" id="capacity" name="capacity" required lay-verify="required" placeholder="请输入仓库剩余容量" autocomplete="off" class="layui-input" value="${data.capacity }">    
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">仓库负责人</label>
    <div class="layui-input-block" style="width: 300px;">
      <select id="administratorName" name="administratorName" lay-verify="required" lay-search="">
      </select>
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
function initSelect(){
	//layui select添加完需要渲染
	layui.use('form', function(){
        var form = layui.form; 
        form.render();
   }); 
}
$(function(){
	$("#administratorName").empty();
 	$.ax({
		 url:"${ctx}/admin/user/getALlUserInfoList",
		 success:function(res){
			 if(res.code!=200){
				 layer.msg('查询员工失败',{time:1000});
				 return;
			 }
			 
			 $("#administratorName").append("<option value='-1'>请选择负责人</option>");
			 $(res.data).each(function(index, element){
				 if(element.uid == "${data.administratorUid }" ){
					 $("#administratorName").append("<option selected = 'selected' value="+element.uid+">"+element.userName+"</option>");
				}else {
					$("#administratorName").append("<option value="+element.uid+">"+element.userName+"</option>");
				}  
			 });
		 initSelect();
		 }
	 });
	$("#subBtn").click(function(){
		 var id = $("#id").val();
		 var name=$("#name").val();
		 if(name.length==0){
			 layer.alert('仓库名称不能为空');
			 return;
		 }
		 var capacity=$("#capacity").val();
		 if(capacity.length==0){
			 layer.alert('仓库剩余容量不能为空');
			 return;
		 }
		 var administratorUid=$("#administratorName").val();
		 if(administratorUid.length==0){
			 layer.alert('仓库管理员不能为空');
			 return;
		 }
		 var note=$("#note").val();
		 if(note.length == 0){
			 note = "${data.note }"
		 }
		 $.ax({
			url:'${ctx}/admin/cwarehouse/updateCwarehouseConfig',
			data:{'id':id,'name':name,'administratorUid':administratorUid,'note':note},
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