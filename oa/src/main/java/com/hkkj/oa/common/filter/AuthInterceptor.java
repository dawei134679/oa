package com.hkkj.oa.common.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hkkj.oa.common.annotation.NoAuth;
import com.hkkj.oa.common.utils.AESCipher;
import com.hkkj.oa.common.utils.JsonUtil;
import com.hkkj.oa.common.utils.LogUtils;
import com.hkkj.oa.common.utils.ResponseUtils;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.entity.WechatUser;
import com.hkkj.oa.service.IUserService;
import com.hkkj.oa.service.IWcLoginService;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	private String authErrorCode = "403";
	
	@Autowired
	private IUserService userService;

	@Autowired
	private IWcLoginService iWcLoginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			LogUtils.error(getClass(), "TEST：没茅台");
			LogUtils.info(getClass(), "-------------------------");
			LogUtils.info(getClass(), "header：");
			Enumeration headerEum = request.getHeaderNames();
			while (headerEum.hasMoreElements()) {
				String key = (String) headerEum.nextElement();
				LogUtils.info(getClass(), key + ":" + request.getHeader(key));
			}
			LogUtils.info(getClass(), "========================");
			// LogUtils.info(getClass(),"params：");
			// Enumeration enu = request.getParameterNames();
			// while (enu.hasMoreElements()) {
			// String key = (String) enu.nextElement();
			// LogUtils.info(getClass(),key + ":" + request.getParameter(key));
			// }
			// LogUtils.info(getClass(),"-------------------------");

			String contextPath = request.getContextPath();
			String reqUrl = request.getRequestURI();
			String baseUrl = StringUtils.removeStart(reqUrl, contextPath);
			LogUtils.error(getClass(), "baseUrl:" + baseUrl);
			if (needIgnore(baseUrl)) {
				return true;
			}
			LogUtils.info(getClass(), "-------------------------1");
			if (handler instanceof HandlerMethod) {
				LogUtils.info(getClass(), "-------------------------2");
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				NoAuth interceptor = handlerMethod.getMethodAnnotation(NoAuth.class);
				if (interceptor != null) {
					return true;
				}
			}

			if (StringUtils.startsWith(baseUrl, "/admin")) {
				UserInfoDto user = UserUtil.getSessionUser(request);
				if (user == null) {
					ResponseUtils.renderJs(response,
							"top.location.href = '" + request.getContextPath() + "/admin/main/login';");
					return false;
				}
			} else if (StringUtils.startsWith(baseUrl, "/front")) {
				String token = request.getHeader("token");
				if (StringUtils.isBlank(token)) {
					LogUtils.info(getClass(), "权限验证失败:token为空");
					String result = JsonUtil.toJson(ResultUtil.fail(authErrorCode, "权限验证失败"));
					ResponseUtils.renderHtml(response, result);
					return false;
				}
				String userCode = AESCipher.aesDecryptString(token.replaceAll(" ", "+")).split("_")[0];
				WechatUser wechatUser= iWcLoginService.getUserByToken(token);
				if (wechatUser == null) {
					LogUtils.info(getClass(), "权限验证失败:token失效");
					String result = JsonUtil.toJson(ResultUtil.fail(authErrorCode, "token失效"));
					ResponseUtils.renderHtml(response, result);
					return false;
				}
				UserInfoDto userDto = userService.getUserByUserCode(userCode);
				if(null == userDto) {
					LogUtils.info(getClass(), "权限验证失败:账户失效");
					String result = JsonUtil.toJson(ResultUtil.fail(authErrorCode, "账户失效"));
					ResponseUtils.renderHtml(response, result);
					return false;
				}
				UserUtil.setSessionUser(request,userDto);
			} else {
				String result = JsonUtil.toJson(ResultUtil.fail(authErrorCode, "请求路径不对"));
				ResponseUtils.renderHtml(response, result);
				return false;
			}
			return true;
		} catch (Exception e) {
			LogUtils.error(getClass(), "-------------------------12");
			e.printStackTrace();
			LogUtils.error(getClass(), "系统异常：", e);
			String result = JsonUtil.toJson(ResultUtil.fail(authErrorCode, "权限验证失败!"));
			ResponseUtils.renderHtml(response, result);
			return false;
		}
	}

	private String getRealIp(HttpServletRequest request) {
		String ipFromNginx = getHeader(request, "X-Real-IP");
		return StringUtils.isEmpty(ipFromNginx) ? request.getRemoteAddr() : ipFromNginx;
	}

	private static String getHeader(HttpServletRequest request, String headName) {
		String value = request.getHeader(headName);
		return !StringUtils.isEmpty(value) && !"unknown".equalsIgnoreCase(value) ? value : "";
	}

	private boolean needIgnore(String baseUrl) {
		if (baseUrl.endsWith(".html") || baseUrl.endsWith(".htm") || baseUrl.endsWith(".js")
				|| baseUrl.endsWith(".json") || baseUrl.endsWith(".css") || baseUrl.endsWith(".jpg")
				|| baseUrl.endsWith(".png") || baseUrl.endsWith(".jpeg") || baseUrl.endsWith(".gif")
				|| baseUrl.endsWith(".font")) {
			return true;
		}
		return false;
	}

}