<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="utf-8">
  <title>组织管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <%@ include file="../common/common.jsp" %>
  <link rel="stylesheet" type="text/css"  href="${ctx }/static/ztree/css/metroStyle/metroStyle.css" media="screen" />
  <script type="text/javascript" src="${ctx }/static/ztree/jquery.ztree.all.min.js"></script>
</head>
<body>
	<div id="contentDiv" style="width: 100%;">
		<div id="leftDiv" style="float: left;width: 250px;min-height: 400px;border:1px solid #d2d2d2;overflow-y:auto; overflow-x:auto;background: #e2e2e2;padding: 5px;">
			<ul id="organizationTree" class="ztree"></ul>
		</div>
		<div id="rightDiv" style="float: left;margin-left:2px;min-width:500px;min-height: 400px;border:1px solid #d2d2d2;background: #eeeeee;padding: 5px;">
			<form class="layui-form" action="">
			 <div class="layui-form-item">
			    <label class="layui-form-label">部门ID</label>
			    <div class="layui-input-block" style="width: 300px;">
			      <input type="text" id="id" name="id" required lay-verify="required" placeholder="请输入部门ID" autocomplete="off" class="layui-input" disabled="disabled">    
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">部门编码</label>
			    <div class="layui-input-block" style="width: 300px;">
			      <input type="text" id="orgCode" name="orgCode" required lay-verify="required" placeholder="请输入部门编码" autocomplete="off" class="layui-input">    
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">部门名称</label>
			    <div class="layui-input-block" style="width: 300px;">
			      <input type="text" id="orgName" name="orgName" required lay-verify="required" placeholder="请输入部门名称" autocomplete="off" class="layui-input">    
			    </div>
			  </div>
			  
			  <div class="layui-form-item">
			    <label class="layui-form-label">部门负责人</label>
			    <div class="layui-input-block" style="width: 300px;">
			      <select id="orgLeader" name="orgLeader" lay-verify="required">
			       
			      </select>
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">上级部门</label>
			    <div class="layui-input-block" style="width: 300px;">
			      <input type="hidden" id="parentId" name="parentId">
			      <input type="hidden" id="parentCode" name="parentCode">
			      <input type="text" id="parentName" name="parentName" required lay-verify="required" placeholder="上级部门名称" autocomplete="off" class="layui-input" disabled="disabled">    
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">备注</label>
			    <div class="layui-input-block" style="width: 300px;">
			      <textarea id="remarks" name="remarks" required lay-verify="required" placeholder="请输入备注" class="layui-textarea"></textarea>
			    </div>
			  </div>
			</form>
			  <div class="layui-form-item">
			    <div class="layui-input-block" >
			       <button class="layui-btn" id="saveSubBtn">增加下级</button>
			       <button class="layui-btn" id="saveBtn">保存</button>
			       <button class="layui-btn" id="delBtn">删除</button>
			    </div>
			  </div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
function initSelect(){
	//layui select添加完需要渲染
	layui.use('form', function(){
        var form = layui.form; 
        form.render();
   }); 
}
var zTreeObj;
var setting = {
		data:{
			key:{
				name:'orgName',
				children:'childNode'
			}
		},
		callback: {
			onClick: zTreeOnClick
		}
   };
function getRoot() {
    var treeObj = $.fn.zTree.getZTreeObj("#organizationTree");
    var node = treeObj.getNodesByFilter(function(node) {
	   return node.level == 0 },true);
}
function zTreeOnClick(event, treeId, treeNode) {
	$("#id").val(treeNode.id);
 	$("#orgCode").val(treeNode.orgCode);
 	$("#orgName").val(treeNode.orgName);
 	var parentNode = treeNode.getParentNode();
 	if(parentNode!=null){
 		$("#parentName").val(parentNode.orgName);
 	}
 	$("#remarks").val(treeNode.remarks);
 	// 获取部门员工start 
 	$("#orgLeader").empty();
 	$.ax({
		 url:"${ctx}/admin/user/getUserInfoPageByOrgId",
		 data:{'orgId':treeNode.id},
		 success:function(res){
			 if(res.code!=200){
				 layer.msg('查询部门员工失败',{time:1000});
				 return;
			 }
			 if(res.data.length>0){
				 $("#orgLeader").append("<option value='-1'>请选择</option>");
				 $(res.data).each(function(index, element){
					 if(element.uid == treeNode.leaderId){
						 $("#orgLeader").append("<option selected = 'selected' value="+element.uid+">"+element.userName+"</option>");
					 }else{
						 $("#orgLeader").append("<option value="+element.uid+">"+element.userName+"</option>");
					 }
				 });
			 }else{
				 $("#orgLeader").append("<option value='-1' selected = 'selected'>此部门没员工请添加</option>");
			 }
			 initSelect();
		 }
	 });
 	// 获取部门员工end
 	
 	
};
$(document).ready(function(){
	initSelect();
	$('#leftDiv').height($(parent.window).height()-200);
	$('#rightDiv').height($(parent.window).height()-200).width($(window).width()-285);
	$(window).resize(function(){
		$('#leftDiv').height($(parent.window).height()-200);
		$('#rightDiv').height($(parent.window).height()-200).width($(window).width()-285);
	});
	 $.ax({
			url:'${ctx}/admin/organization/getOrganizationList',
			data:{},
			success:function(res){
					if(res.code!=200){
						layer.msg('获取数据失败',{time:1000});
						return;
					}
				 zTreeObj = $.fn.zTree.init($("#organizationTree"), setting, res.data);
				 zTreeObj.expandAll(true);
                 var node = zTreeObj.getNodeByParam('id', 1);//获取id为1的点
                 zTreeObj.selectNode(node);//选择点
                 zTreeObj.setting.callback.onClick(null, zTreeObj.setting.treeId, node);//调用事件
				}
		});
	 $("#saveSubBtn").click(function(){
		 var nodes = zTreeObj.getSelectedNodes();
		 if(nodes==null||nodes.length==0){
			 return;
		 }
		 $("#id").val('');
		 $("#orgCode").val('');
		 $("#orgName").val('');
		 $("#remarks").val('');
		 $("#orgLeader").empty();
		 $("#orgLeader").append("<option value='-1' selected = 'selected'>此部门没员工请添加</option>");
		 initSelect();
		 $("#parentId").val(nodes[0].id);
		 $("#parentCode").val(nodes[0].orgCode);
		 $("#parentName").val(nodes[0].orgName);
	 });
	 
	 $("#saveBtn").click(function(){
		 var id = $("#id").val();
		 var orgCode = $("#orgCode").val();
		 var orgName = $("#orgName").val();
		 if(orgName.length==0){
			 layer.alert('部门名称不能为空');
			 return;
		 }
		 var remarks = $("#remarks").val();
		 if(remarks.length==0){
			 layer.alert('备注不能为空');
			 return;
		 }
		 var parentId = $("#parentId").val();
		 if(parentId==null){
			 parentId =0;
		 }
		 var parentCode = $("#parentCode").val();
		 if(parentCode==null){
			 parentCode = '';
		 }
		 var orgLeaderId = $("#orgLeader").val();
		 if(orgLeaderId==null){
			 orgLeaderId = -1;
		 }
		 if(id==''){
			 $.ax({
				 url:"${ctx}/admin/organization/saveOrganization",
				 data:{'orgCode':orgCode,'orgName':orgName,'remarks':remarks,'parentId':parentId,'parentCode':parentCode,'orgLeaderId':orgLeaderId},
				 success:function(res){
					 if(res.code!=200){
						 layer.msg('保存数据失败',{time:1000});
						 return;
					 }
					 layer.msg('保存成功',{time:1000});
					 var nodes = zTreeObj.getSelectedNodes();
					 var selectNode = null;
					 if(nodes!=null&&nodes.length>0){
						 selectNode = nodes[0];
					 }
					 var newNode = {'id':res.data.id,'orgCode':orgCode,'orgName':orgName,'remarks':remarks,'parentId':parentId,'parentCode':parentCode,'orgLeaderId':orgLeaderId};
					 newNode = zTreeObj.addNodes(selectNode, newNode);
					 setTimeout(function(){
						 zTreeObj.setting.callback.onClick(null, zTreeObj.setting.treeId, selectNode);//调用事件
					 },500);
				 }
			 });
		 }else{
			 $.ax({
				 url:"${ctx}/admin/organization/updateOrganizationById",
				 data:{'id':id,'orgCode':orgCode,'orgName':orgName,'remarks':remarks,'parentId':parentId,'parentCode':parentCode,'leaderId':orgLeaderId},
				 success:function(res){
					 if(res.code!=200){
						 layer.msg('保存数据失败',{time:1000});
						 return;
					 }
					 layer.msg('保存成功',{time:1000});
					 var nodes = zTreeObj.getSelectedNodes();
					 if(nodes==null||nodes.length==0){
						 return;
					 }
					 nodes[0].orgCode=orgCode;
					 nodes[0].orgName=orgName;
					 nodes[0].remarks=remarks;
					 nodes[0].parentCode=parentCode;
					 zTreeObj.updateNode(nodes[0]);
				 }
			 }); 
		 }
	 });
	 
	 $("#delBtn").click(function(){
		 var nodes = zTreeObj.getSelectedNodes();
		 if(nodes==null||nodes.length==0){
			 return;
		 }
		 var children = nodes[0].childNode;
		 if(children!=null&&children.length>0){
			 layer.msg('当前节点有子节点，不能删除',{time:1000});
			 return;
		 }
		 var id = $("#id").val();
		 $.ax({
			 url:"${ctx}/admin/organization/delOrganizationById",
			 data:{'id':id},
			 success:function(res){
				 if(res.code!=200){
					 layer.msg('删除数据失败',{time:1000});
					 return;
				 }
				 layer.msg('删除成功',{time:1000});
				 zTreeObj.removeNode(nodes[0]);
				 var node = zTreeObj.getNodeByParam('id', 1);//获取id为1的点
                 zTreeObj.selectNode(node);//选择点
                 zTreeObj.setting.callback.onClick(null, zTreeObj.setting.treeId, node);//调用事件
			 }
		 });
	 });
});
</script>