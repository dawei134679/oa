<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>OA办公系统</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <%@ include file="common/common.jsp" %>
  <!-- 左侧菜单模块加载  去掉了报错 -->
  <script src="${ctx}/static/layui/layui.js"></script>
<style type="text/css"></style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">汇坤科技</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left"></ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
      	当前用户：&nbsp;<font color="red">${currentUser.userName }</font>&nbsp;&nbsp;&nbsp;&nbsp;
      </li>
      <li class="layui-nav-item"><a href="${ctx}/admin/main/doLogout">退出</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="menuTree"></ul>
    </div>
  </div>
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div>
    	<div class="layui-tab layui-tab-card" lay-filter="menuTabs" lay-allowclose="true" style="margin: 0px;">
		  <ul class="layui-tab-title">
		    <li class="layui-this">首页</li>
		  </ul>
		  <div class="layui-tab-content">
		    <div class="layui-tab-item layui-show">
		    	<div style="text-align: center;margin-top: 150px;" class="layui-bg-gray" ><h1>欢迎使用</h1></div>
		    </div>
		  </div>
		</div>            
    </div>
  </div>
  <div class="layui-footer" style="text-align: center;">
    <!-- 底部固定区域 -->
    © 版权所有 汇坤科技
  </div>
</div>
</body>
<script>
$(function(){
	$(window).resize(function() {
		$(".layui-tab-content").height($(".layui-body").height()-$(".layui-tab-title").height()-25);
		$(".layui-tab-content iframe").height($(".layui-tab-content").height());
	});
	$(".layui-tab-content").height($(".layui-body").height()-$(".layui-tab-title").height()-25);
	$.ax({
		url:"${ctx}/admin/main/getMenuByUser",
		data:{},
		success:function(res){
			if(res.code==200){
				var data = res.data;
				var menus = [];
				$.each(data,function(index,node){
					var temp=[];
					menus.push('<li class="layui-nav-item layui-nav-itemed">');
					menus.push('<a class="" href="javascript:void(0);">'+node.name+'</a>');
					if(node.childNode.length>0){
						menus.push('<dl class="layui-nav-child">');
						menus.push(getChildNode(node,temp));
						menus.push("</dl>");
					}else{
					}
					menus.push("</li>");
				});
				$(".layui-nav-tree").append(menus.join(""));
				layui.use('element', function(){
					  element = layui.element;
				});
			}
		}
	});
	
	function getChildNode(node,temp){
		$.each(node.childNode,function(index,n){
			var url = n.url;
			if(url==null||url==""){
				url ="javascript:void(0);";
			}
			temp.push('<dd><a href="javascript:void(0);" onclick="addTab(\''+n.name+'\',\'${ctx}'+n.url+'\')">'+n.name+'</a></dd>');
		});
	   return temp.join('');
	 }
});

var element = null;
function addTab(name,url){
	if($("#iframe_"+name).length==0){
		var contentHeight = $(".layui-tab-content").height();
		element.tabAdd('menuTabs', {
			   id:name
		      ,title: name
		      ,content: '<iframe id="iframe_'+name+'" name="iframeContent" frameborder="0" width="100%" height="'+contentHeight+'px" scrolling="auto" src="'+url+'"></iframe>'
		    });
	}
	element.tabChange('menuTabs', name);
}
</script>
</html>