<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix= "c" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公系统</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/dist/css/AdminLTE.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css"  href="${ctx}/static/layui/css/layui.css" media="screen" />
	<script src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
	<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/common.js"></script>
	<script src="${ctx}/static/layer/layer.js"></script>
	<script src="${ctx}/static/layui/layui.js"></script>
	<link rel="stylesheet" type="text/css"  href="${ctx}/css/style.css" media="screen" />
</head>
<body>
	<div style="min-width: 1024px;">
		<div id="topDiv" class="topDiv">
			<div class="topDivLeft">
				汇坤科技
			</div>
			<div class="topDivRight">
				<div id="quitDiv" style="float: right;margin-right: 20px;cursor: pointer;"><i class="fa fa-power-off" aria-hidden="true"></i>&nbsp;退出</div>
				<div style="float: right;margin-right: 20px;">欢迎：${sessionScope.currentUser.userName }</div>
			</div>
		</div>
		<div>
			 <div id="lfetDiv" style="background:#2f4050 none repeat scroll 0 0;float: left;width: 200px;">
			 		<ul id="accordion" class="accordion">
			 		</ul>
			 </div>
			 <div id="rightDiv" style="float: left;">
			 	<div id="tabs" style="height: 28px;line-height:28px;border-bottom: 2px solid #00CCFF;">
			 		<div style="float: left;pointer;background:#00CCFF;line-height:24px;padding: 2px 5px;cursor: pointer;">位置</div>
			 	</div>
			 	  <iframe id="iframeContent" name="iframeContent" frameborder="0" scrolling='auto'></iframe>
			 </div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$(window).resize(function() {
			$("#lfetDiv").height($(window).height()-$("#topDiv").height());
			$("#rightDiv iframe").height($(window).height()-$("#topDiv").height()-30);
			$("#rightDiv iframe").width($(window).width()-$("#lfetDiv").width());
		});
		$("#lfetDiv").height($(window).height()-$("#topDiv").height());
		$("#rightDiv iframe").height($(window).height()-$("#topDiv").height()-30);
		$("#rightDiv iframe").width($(window).width()-$("#lfetDiv").width());
		
		$("#quitDiv").click(function(){
			location.href='${pageContext.request.contextPath}/main/doLogout';
		});
		
		$.ax({
			url:"${pageContext.request.contextPath}/main/getMenuByUser",
			data:{},
			success:function(res){
				if(res.code==200){
					var data = res.data;
					var menus = [];
					$.each(data,function(index,node){
						var temp=[];
						menus.push("<li>");
						if(node.childNode.length>0){
							menus.push("<div class='link'><i class='"+node.icon+"'></i>"+node.name+"<i class='fa fa-chevron-down'></i></div>");
							menus.push("<ul class='submenu'>");
							menus.push(getChildNode(node,temp));
							menus.push("</ul>");
						}else{
							var url = node.url;
							if(url==null||url==""){
								url ="javascript:void(0);";
							}
							menus.push("<div class='menu'><i class='"+node.icon+"'></i><a href='"+url+"' target='iframeContent'>"+node.name+"</a></div>");
						}
						menus.push("</li>");
					});
					$("#accordion").append(menus.join(""));
					bindMenuClick();
				}
			}
		});
		
		function getChildNode(node,temp){
			$.each(node.childNode,function(index,n){
				temp.push("<li>");
				if(n.childNode.length>0){
					var temp1=[];
					temp.push("<div class='link'><i class='"+n.icon+"'></i>"+n.name+"<i class='fa fa-chevron-down'></i></div>");
					temp.push("<ul class='submenu'>");
					temp.push(getChildNode(n,temp1));
					temp.push("</ul>");
				}else{
					var url = n.url;
					if(url==null||url==""){
						url ="javascript:void(0);";
					}
					temp.push("<i class='"+n.icon+"'></i><a href='"+url+"' target='iframeContent'>"+n.name+"</a>");
				}
				temp.push("</li>");
			});
		   return temp.join('');
		 }
	});
	
	
	function bindMenuClick(){
		var Accordion = function(el, multiple) {
			this.el = el || {};
			this.multiple = multiple || false;
			var links = this.el.find('.link');
			links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
		}
		Accordion.prototype.dropdown = function(e) {
			var $el = e.data.el;
				$this = $(this),
				$next = $this.next();

			$next.slideToggle();
			$this.parent().toggleClass('open');

			if (!e.data.multiple) {
				$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
			};
		}	
		var accordion = new Accordion($('#accordion'), false);
	}
	
	
// 	function addTab(title,showCloseBtn,url){
// 		$("#tabs div").css("background","");
// 		var strs = [];
// 		strs.push('<div id='+title+'_div style="background:#00CCFF;float: left;pointer;line-height:24px;padding: 2px 5px;margin-left: 1px;cursor: pointer;">');
// 		strs.push('<span onclick="showTab(this)">');
// 		strs.push(title)
// 		strs.push('</span>');
// 		if(showCloseBtn==1){
// 			strs.push(' <i class="fa fa-times-circle" aria-hidden="true" style="margin-left: 1px;" onclick="closeTab(this)"></i>')
// 		}
// 		strs.push('</div>');
// 		$("#tabs").append(strs.join(''));
// 		var iframeHeight = $(window).height()-$("#topDiv").height();
// 		var iframeWidth = $(window).width()-$("#lfetDiv").width();
// 		var ifrm = "<iframe id="+title+"_iframe  frameborder='0' scrolling='auto' height="+iframeHeight+" width="+iframeWidth+"  src="+url+"></iframe>";
// 		$("#rightDiv iframe").hide();
// 		$("#rightDiv").append(ifrm);
// 	}
// 	function closeTab(dom){
// 		var div =$(dom).parent();
// 		var  iframeId = $(div).attr("id").split("_")[0]+"_iframe";
// 		div.remove();
// 		$("#rightDiv #"+iframeId).remove();
// 		$("#rightDiv #iframeContent").show();
// 	}
// 	function showTab(dom){
// 		$("#tabs div").css("background","");
// 		$(dom).parent().css("background","#00CCFF");
// 		$("#rightDiv iframe").hide();
// 		var  iframeId = $(dom).parent().attr("id").split("_")[0]+"_iframe";
// 		$("#rightDiv #"+iframeId).show();
// 	}
</script>
</html>