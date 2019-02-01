<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title>流程管理-新增</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<%@ include file="../common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/ztree/css/metroStyle/metroStyle.css" media="screen" />
<script type="text/javascript" src="${ctx }/static/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx }/js/md5.js"></script>
<style type="text/css">
.close {
    position: relative;
    right: 0px;
    top: 0px;
    width: 15px;
    height: 20px;
    cursor: pointer;
    text-align: center;
}
.close:hover {
    background: red;
}

</style>
</head>
<body>
	<form class="layui-form" action="">
		<fieldset style="margin:auto 20px auto; 20px;">
		    <legend>基本信息</legend>
		    <div class="layui-form-item">
				<label class="layui-form-label">名称</label>
				<div class="layui-input-block" style="width: 300px;">
					<input type="text" id="code" name="code" required lay-verify="required" placeholder="请输入流程编码" autocomplete="off" class="layui-input" value="000001" disabled="disabled">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">名称</label>
				<div class="layui-input-block" style="width: 300px;">
					<input type="text" id="name" name="name" required lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">描述</label>
				<div class="layui-input-block" style="width: 300px;">
				 <textarea id="remarks" name="remarks" required lay-verify="required" placeholder="请输入描述" class="layui-textarea"></textarea>
				</div>
			</div>
		</fieldset>
		<fieldset style="margin:auto 20px auto 20px;">
		    <legend>流程审批信息</legend>
		    <div id="workflowDiv"></div>
		</fieldset>
		</form>
		<div class="layui-form-item" style="margin-top:20px;">
			<div class="layui-input-block">
				<button class="layui-btn" id="addApprovalNode">增加审批节点</button>
				<button class="layui-btn" id="submit">保存</button>
				<button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
			</div>
		</div>
		<div id="orgContent" class="orgContent" style="position: absolute; display: none; z-index: 9999; background: #d2d2d2; border: 1px solid #d2d2d2;">
		 <ul id="orgTree" class="ztree" style="margin-top: 0; width: 288px;"></ul>
	   </div>
</body>
</html>
<script>
	;!function() {
		form = layui.form;
		form.render();
		$("#addApprovalNode").click(function(){
			var timestamp=new Date().getTime();
			var str = [];
			str.push("<div style='float: left;background:#e2e2e2;padding: 5px;width: 320px;height:240px;margin: 5px'>");
			str.push("<div class='close' onclick='remDiv(this)'>x</div>");
			str.push("<div class='layui-form-item'>");
			str.push("<label class='layui-form-label'>同级标识</label>");
			str.push("<div class='layui-input-block' style='width: 200px;'>");
			str.push("<input type='text' id='key_"+timestamp+"' name='key' required lay-verify='required' placeholder='同级节点标识' autocomplete='off' class='layui-input'>");
			str.push("</div>");
			str.push("</div>");
			str.push("<div class='layui-form-item'>");
			str.push("<label class='layui-form-label'>类型</label>");
			str.push("<div class='layui-input-block' style='width: 200px;'>");
			str.push("<input type='radio' name='type_"+timestamp+"' value='1' title='员工' checked lay-filter='type_"+timestamp+"'>");
			str.push("<input type='radio' name='type_"+timestamp+"' value='2' title='部门' lay-filter='type_"+timestamp+"'>");
			str.push("</div>");
			str.push("</div>");
			str.push("<div class='layui-form-item'>");
			str.push("<label class='layui-form-label'>审批</label>");
			str.push("<div class='layui-input-block' style='width: 200px;'>");
			str.push("<input id='approvalId_"+timestamp+"' type='hidden' name='approvalId' required lay-verify='required' placeholder='请输入员工ID或部门ID' autocomplete='off' class='layui-input'>");
			str.push("<input type='text' id='orgName_"+timestamp+"' name='orgName' required lay-verify='required' placeholder='请输选择部门' autocomplete='off' class='layui-input' onclick='showMenu("+timestamp+");' style='display:none;' readonly='readonly'>");
			str.push("<select id='user_"+timestamp+"' name='user' lay-verify='required' lay-search lay-filter='user_"+timestamp+"'>");
			str.push(userSelect.join(""));
			str.push("</select>");
			str.push("</div>");
			str.push("</div>");
		/* 	str.push("<div class='layui-form-item'>");
			str.push("<label class='layui-form-label'>条件</label>");
			str.push("<div class='layui-input-block' style='width: 200px;'>");
			str.push("<input type='text' id='conditions_"+timestamp+"'  name='conditions' required lay-verify='required' placeholder='请输入' autocomplete='off' class='layui-input'>");
			str.push("</div>");
			str.push("</div>"); */
			str.push("</div>");
			$("#workflowDiv").append(str.join(""));
			form.render();
			
			 form.on('radio(type_'+timestamp+')', function (data) {
				 if(data.value=='1'){
					 $("#approvalId_"+timestamp).val('');
					 $('#orgName_'+timestamp).hide();
					 $('#user_'+timestamp).next().show();
					 form.render();
				 }else{
					 $("#approvalId_"+timestamp).val('');
					 $('#orgName_'+timestamp).show();
					 $('#user_'+timestamp).next().hide();
				 }
			 });
			 form.on('select(user_'+timestamp+')', function (data) {
				 $("#approvalId_"+timestamp).val(data.value);
			 });
		});
		$("#submit").click(function(){
			var params = {};
			var code = $("#code").val();
			 if(code.length==0){
				 layer.alert('编码不能为空');
				 return;
			 }
			var name = $("#name").val();
			 if(name.length==0){
				 layer.alert('名称不能为空');
				 return;
			 }
			 var remarks = $("#remarks").val();
			 if(remarks.length==0){
				 layer.alert('备注不能为空');
				 return;
			 }
			 var typeDoc = $("#workflowDiv input[type='radio']").filter(':checked');;  
			 var approvalIdDoc = $("#workflowDiv input[name='approvalId']");
			 var conditionsDoc = $("#workflowDiv input[name='conditions']");
			 var keys = $("#workflowDiv input[name='key']");
			 var details = [];
			 for(var a=0;a<typeDoc.length;a++){
				 var detail = {};
				 detail.key = keys[a].value;
				 detail.type=typeDoc[a].value;
				 detail.approvalId = approvalIdDoc[a].value;
				 //detail.conditions = conditionsDoc[a].value;
				 details.push(detail);
			 }
			 params.code=code;
			 params.name=name;
			 params.remarks=remarks;
			 params.details = details;
			$.ax({
				url : '${ctx}/admin/workflow/saveWorkflow',
				contentType:'application/json',
				data:JSON.stringify(params),
				success : function(res) {
					if (res.code != 200) {
						layer.msg('保存失败', {
							time : 1000
						});
						return;
					}
					
					parent.layer.closeAll();
					parent.table.reload('tableList');
				}
			});
		});
		$("#cancelBtn").click(function() {
			parent.layer.closeAll();
		});
	}();
	
	$(function(){
		$.ax({
			url : '${ctx}/admin/organization/getOrganizationList',
			data : {},
			success : function(res) {
				if (res.code != 200) {
					layer.msg('获取数据失败', {
						time : 1000
					});
					return;
				}
				zTreeObj = $.fn.zTree.init($("#orgTree"), setting, res.data);
				zTreeObj.expandAll(true);
			}
		});
		
		$.ax({
			url : '${ctx}/admin/user/getALlUserInfoList',
			data : {},
			success : function(res) {
				if (res.code != 200) {
					layer.msg('获取数据失败', {
						time : 1000
					});
					return;
				}
				userSelect.push("<option value=''>请选择</option>");
				$.each(res.data,function(index,element){
					userSelect.push("<option value="+element.uid+">"+element.userName+"</option>");
				});
			}
		});
	});
	var zTreeObj;
	var setting = {
		data : {
			key : {
				name : 'orgName',
				children : 'childNode'
			}
		},
		callback : {
			onClick : zTreeOnClick
		}
	};
	var userSelect= [];
	var clickDoc = null;
	function zTreeOnClick(event, treeId, treeNode) {
		$(clickDoc).prev().val(treeNode.id);
		$(clickDoc).val(treeNode.orgName);
		hideMenu();
	};
	function showMenu(t) {
		clickDoc = $('#orgName_'+t);
		$("#orgContent").css({
			left : clickDoc.offset().left + "px",
			top : clickDoc.offset().top + clickDoc.outerHeight() + "px"
		}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}

	function hideMenu() {
		$("#orgContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}

	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "orgContent" || $(
				event.target).parents("#orgContent").length > 0)) {
			hideMenu();
		}
	}
	function typeRadioChange(timeLong){
		var type  =$("input[name=radio_"+timeLong+"]:checked").val();
		console.info(timeLong)
	}
	function remDiv(t){
		$(t).parent().remove();
	}
</script>