//var baseUrl = 'http://192.168.20.32:8080/oa';
var baseUrl = 'http://m.huikun99.com/oa';

Date.prototype.Format = function (fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"H+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
var dou = {};
/**
 * 检查登录
 */
dou.checkLogin = function(){
	var token = dou.store("token");
    if(!token){
    	$.modal({
			title: '登录',
			text: '<input type="text" class="weui-input weui-prompt-input" id="weui-prompt-username" value="" placeholder="输入用户名"><input type="password" class="weui-input weui-prompt-input" id="weui-prompt-password" value="" placeholder="输入密码">',
			autoClose: false,
			buttons: [
    		    {
    		    	text: "确定", onClick: function(){ 
						//$.toast('登录成功!', 'success');
    		    		var userCode = $("#weui-prompt-username").val();
    		    		var password = $("#weui-prompt-password").val();
    		    		var cookieCode = getQueryString("code");
    		    		if(cookieCode){
    		    			dou.store("code",cookieCode);
    		    		}else{
    		    			cookieCode = dou.store("code");
    		    		}
    		    		var code = cookieCode;
    		    		if(!code){
    		    			$.toast('请用微信打开!', 'cancel');
    		    			return;
    		    		}
    		    		if (!userCode) {
    		    			$.toast('请输入用户名!', 'cancel');
    						return;
    					}
    					if ((!password) || password.length < 6) {
    						$.toast('请输入6位以上密码!', 'cancel');
    						return;
    					}
    		    		dou.ajax({
    		    			url:"/front/wechat/boundOpenId",
    		    			data:{
    		    				"code":code,
    		    				"userCode":userCode,
    		    				"password":md5(password)
    		    			},
    		    			success:function(d){
    		    				if(d.code == '200'){
    		    					dou.store("token",d.data.token);
    		    					$.closeModal();
    		    					window.location.reload();
    		    				}else{
    		    					//$.toast(d.msg, 'cancel');
    		    					$.toptip(d.msg, 'error');
    		    				}
    		    			}
    		    		});
    		    	}
    		    }
    		]
		});
    }
}
/**
 * 将日期格式化成指定格式的字符串
 * 
 * @param date
 *            要格式化的日期，不传时默认当前时间，也可以是一个时间戳
 * @param fmt
 *            目标字符串格式，支持的字符有：y,M,d,q,w,H,h,m,S，默认：yyyy-MM-dd HH:mm:ss
 * @returns 返回格式化后的日期字符串
 */
dou.formatDate = function(date, fmt) {
	if(!date||date==0){
		return "";
	}
	date = date == undefined ? new Date() : date;
	date = typeof date == 'number' ? new Date(date) : date;
	fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
	var obj = {
		'y' : date.getFullYear(), // 年份，注意必须用getFullYear
		'M' : date.getMonth() + 1, // 月份，注意是从0-11
		'd' : date.getDate(), // 日期
		'q' : Math.floor((date.getMonth() + 3) / 3), // 季度
		'w' : date.getDay(), // 星期，注意是0-6
		'H' : date.getHours(), // 24小时制
		'h' : date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
		'm' : date.getMinutes(), // 分钟
		's' : date.getSeconds(), // 秒
		'S' : date.getMilliseconds()
	// 毫秒
	};
	var week = [ '天', '一', '二', '三', '四', '五', '六' ];
	for ( var i in obj) {
		fmt = fmt.replace(new RegExp(i + '+', 'g'), function(m) {
			var val = obj[i] + '';
			if (i == 'w')
				return (m.length > 2 ? '星期' : '周') + week[val];
			for (var j = 0, len = val.length; j < m.length - len; j++)
				val = '0' + val;
			return m.length == 1 ? val : val.substring(val.length - m.length);
		});
	}
	return fmt;
}

dou.ajax = function(opt) {
	var that = this;
	var defaults = {
		url : "",
		data : {},
		header:{},
		hideLoading : false,
		dataType : "html",
		async : true,
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		complete : function() {
		},
		success : function() {
		},
		error : function() {
		}
	};
	for ( var key in opt) {
		defaults[key] = opt[key];
	}
	if (defaults.hideLoading == false) {
		$.showLoading();
	}
	if (defaults.url.indexOf("?") == -1) {
		defaults.url = defaults.url += "?";
	} else {
		defaults.url = defaults.url += "&";
	}
	
	defaults.header.token = dou.store("token");
	
	$.ajax({
		url : baseUrl + defaults.url + "t=" + new Date().getTime(),
		data : defaults.data,
		dataType : defaults.dataType,
		async : defaults.async,
		type : defaults.type,
		contentType : defaults.contentType,
		beforeSend: function (xhr) {
			for(var key in defaults.header){
				xhr.setRequestHeader(key, defaults.header[key]);  
			}
		},
		complete : function(XMLHttpRequest, textStatus) {
			if (defaults.hideLoading == false) {
				$.hideLoading();
			}
			var sessionstatus = XMLHttpRequest.getResponseHeader('sessionStatus');
			if (sessionstatus == 'timeout') {
				//top.location.href = that.ctx + "/admin/login.do";
			}else if (sessionstatus == 'roleErr') {
				//top.location.href = that.ctx + "/admin/login.do";
				//layer.msg('您没有权限访问！',{icon:0});
			}else{
				defaults.complete.apply(this, arguments);
			}
		},
		success : function(d) {
			var rd = eval('(' + d + ')');
			if(rd.code == '403'){
				if($("#weui-prompt-username").length == 0){
					$.toptip(rd.msg, 'error');
					dou.store("token","");
					dou.checkLogin();
				}
			}
			defaults.success.call(this, rd);
		},
		error : function() {
			if (defaults.hideLoading == false) {
				//layer.closeAll('loading');
			}
			defaults.error.apply(this, arguments);
		}
	});
}

dou.store = function(key,value){
	var result = "";
	if(key != undefined && value != undefined){
		localStorage.setItem(key,value);
	}else if(key != undefined){
		result = localStorage.getItem(key);
	}
	return result;
}
dou.store.remove = function(key){
	localStorage.removeItem(key);
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
