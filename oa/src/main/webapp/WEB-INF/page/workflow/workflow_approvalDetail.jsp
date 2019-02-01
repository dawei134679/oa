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
					<input type="text" id="name" name="name" required lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input" disabled="disabled">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">描述</label>
				<div class="layui-input-block" style="width: 300px;">
				 <textarea id="remarks" name="remarks" required lay-verify="required" placeholder="请输入描述" class="layui-textarea" disabled="disabled"></textarea>
				</div>
			</div>
		</fieldset>
		<fieldset style="margin:auto 20px auto 20px;">
		    <legend>流程审批信息</legend>
		    <div id="workflowDiv"></div>
		</fieldset>
		</form>
</body>
</html>
<script>
	;!function() {
		form = layui.form;
		form.render();
		$("#cancelBtn").click(function() {
			parent.layer.closeAll();
		});
	}();
	$(function(){
		$.ax({
			url : '${ctx}/admin/workflow/getWorkflowDetailByWorkflowId?workflowId=${workflowId}',
			data : {},
			success : function(res) {
				if (res.code != 200) {
					layer.msg('获取数据失败', {
						time : 1000
					});
					return;
				}
				$("#code").val(res.data.workflowCode);
				$("#name").val(res.data.workflowName);
				$("#remarks").val(res.data.workflowRemarks);
				
				var tempStr = [];
				tempStr.push('<div>')
				tempStr.push('<table style="width:90%;text-align:left;" cellpadding="20">');
				$.each(res.data.approvalDetailList,function(index,element){
					tempStr.push('<tr style="height:30px;">');
					tempStr.push('<td style="text-align:left;width:200px;">');
					tempStr.push(element.approvalUserName);
					tempStr.push('('+element.approvalUserCode+')');
					tempStr.push('</td>');
					tempStr.push('<td style="text-align:left;">');
					tempStr.push(element.approvalOrgName);
					tempStr.push('</td>');
					tempStr.push('</tr>');
				});
				$('#workflowDiv').append(tempStr.join(''));
				tempStr.push('</table>');
				tempStr.push('</div>')
			}
		});
	});
</script>