<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix= "c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<HTML>
<head>
  <meta charset="utf-8">
  <title>OA管理用户</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/dist/css/AdminLTE.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/font-awesome/css/font-awesome.min.css" />
	<script src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
	<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/ax.js"></script>
	<script type="text/javascript" src="${ctx }/js/md5.js"></script>
</head>
<body class="hold-transition login-page">
<div class="login-box" id="rrapp">
  <div class="login-box-body">
   <form action="">
      <p class="login-box-msg"><b>OA 用户登录</b></p>
       <div class="alert alert-danger alert-dismissible" style="display:none">
        <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle" id="errorMessage"></i></h4>
      </div>
      <div class="form-group has-feedback">
          <input type="text" class="form-control" id="username" placeholder="账号">
          <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
          <input type="password" class="form-control" id="userpwd" placeholder="密码">
          <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>

      <div class="row">
        <div class="col-xs-12">
          <button type="button" class="btn btn-primary btn-block btn-flat" id="btn-login" >登录</button>
        </div>
      </div>
      </form>
  </div>
</div>
</body>
</HTML>
<script type="text/javascript">
$(document).ready(function(){
	//回车按钮
	$("#userpwd").keyup(function(event) {
		if (event.keyCode == 13) {
			//触发btn-login绑定的submit事件
			$("#btn-login").trigger("click");
		}
	});
	//点击登录按钮
	$('#btn-login').click(doLogin)
})
function doLogin(){
	var userName = $('#username').val();
	var userPwd = md5($('#userpwd').val());
	if(userName==''){
		$('#errorMessage').parent().parent().css('display','block');
		$('#errorMessage').text('用户名不能为空！');
		return false;
	}
	if(userPwd==''){
		$('#errorMessage').parent().parent().css('display','block');
		$('#errorMessage').text('密码不能为空！');
		return false;
	}
	var params = {'userCode':userName,'password':userPwd};
	$.post("${ctx}/admin/main/doLogin",params,function(result){
		var jo = JSON.parse(result);
		if(jo.code!=200){
			$('#errorMessage').parent().parent().css('display','block');
			$('#errorMessage').text(jo.msg);
			return false;
		}
		location.href='${ctx}/admin/main/main';
	});
}
</script>

