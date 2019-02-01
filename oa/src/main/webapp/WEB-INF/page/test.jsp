<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
<script src="${ctx}/js/common.js"></script>
<title>Insert title here</title>
</head>
<script type="text/javascript">
$(function(){
	var url = "${ctx}/front/iodetail/saveIoDetail";
	var param = {
			'type':1,
			'transportType':1,
			'tansportName':'火车1号',
			'tansportNo':'1012#',
			'transportCode':'110#',
			'contractCode':'123456',
			'billCode':'321654',
			'provider':'李大为',
			'responsibleMan':'李小为',
			'operator':'为',
			'operateTime':12345678911,
			'dTransportNo':'1234565789',
			'wtypeName':'粮食',
			'subDetails':[
					{
						'goodsCode':'123456',
						'warehouseName':'1号库',
						'goodsAmount':10,
						'transportCost':100.0,
						'dTransportCost':20.0,
						'note':'test1'
					},{
						'goodsCode':'123456',
						'warehouseName':'2号库',
						'goodsAmount':10,
						'transportCost':100.0,
						'dTransportCost':20.0,
						'note':'test2'
					}
				]	
	}
	$.post(url,param,function(data){
		alert("sucess");
	});
})
</script>
<body>
	<p>
		测试订单插入
	</p>
	
</body>
</html>