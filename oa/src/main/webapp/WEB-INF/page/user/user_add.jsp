<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title>用户管理-新增</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<%@ include file="../common/common.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx }/static/ztree/css/metroStyle/metroStyle.css"
	media="screen" />
<script type="text/javascript"
	src="${ctx }/static/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="${ctx }/js/md5.js"></script>
</head>
<body>
	<form class="layui-form" action="">
		<div class="layui-form-item">
			<label class="layui-form-label">员工编号</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="text" id="userCode" name="userCode" required
					lay-verify="required" placeholder="请输入员工编号" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密码</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="password" id="password" name="password" required
					lay-verify="required" placeholder="请输入密码" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">姓名</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="text" id="userName" name="userName" required
					lay-verify="required" placeholder="请输入姓名" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">性别</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="radio" name="sex" value="1" title="男"> <input
					type="radio" name="sex" value="2" title="女" checked>
			</div>
		</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户类型</label>
				<div class="layui-input-block" style="width: 300px;">
					<input type="radio" name="userType" value="1" title="系统管理员"> <input
						type="radio" name="userType" value="2" title="员工" checked>
				</div>
			</div>
		<div class="layui-form-item">
			<label class="layui-form-label">联系电话</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="text" id="contactNumber" name="contactNumber" required
					lay-verify="required" placeholder="请输入联系电话" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="text" id="phoneNumber" name="phoneNumber" required
					lay-verify="required" placeholder="请输入手机号" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">所属部门</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="hidden" id="orgId" name="orgId" /> 
				<input type="text"id="orgName" name="orgName" required lay-verify="required" placeholder="请输入所属部门" autocomplete="off" class="layui-input" onclick="showMenu();" readonly="readonly">
				<div id="orgContent" class="orgContent" style="position: absolute; display: none; z-index: 9999; background: #d2d2d2; border: 1px solid #d2d2d2;">
					<ul id="orgTree" class="ztree" style="margin-top: 0; width: 288px;"></ul>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">岗位信息</label>
			<div class="layui-input-block" style="width: 300px;">
				<input type="text" id="post" name="post" required
					lay-verify="required" placeholder="请输入岗位信息" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block" style="width: 300px;">
				<textarea id="remarks" name="remarks" required lay-verify="required"
					placeholder="请输入备注" class="layui-textarea"></textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="go">保存</button>
				<button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
			</div>
		</div>
	</form>
</body>
</html>
<script>
	//layui表单提交start
	;!function() {
		form = layui.form;
		form.render();
		//监听提交
		form.on('submit(go)', function(data) {
			data.field.password=md5(data.field.password);
			$.ax({
				url : '${ctx}/admin/user/saveUser',
				data : data.field,
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
			return false;
		});
	}();
	//layui表单提交end
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

	$(function() {
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

		$("#cancelBtn").click(function() {
			parent.layer.closeAll();
		});
	});

	function zTreeOnClick(event, treeId, treeNode) {
		$("#orgId").val(treeNode.id);
		$("#orgName").val(treeNode.orgName);
		hideMenu();
	};

	function showMenu() {
		var orgName = $("#orgName");
		var cityOffset = $("#orgName").offset();
		$("#orgContent").css({
			left : orgName.left + "px",
			top : orgName.top + orgName.outerHeight() + "px"
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
</script>