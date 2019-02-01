package com.hkkj.oa.common.annotation;

import javax.servlet.http.HttpServletRequest;

public class AspectRequestUtil {
	
	/**
	 * 获取int(二进制存储)类型IP地址
	 * @param request
	 * @return
	 */
	public static int getIpAddrInt(HttpServletRequest request) {
		return ip2Int(getIpAddr(request));
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 * 
	 * @return ip
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(!checkIP(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	/**
	 * IP字符串转整数 IP是.分割的整数字符串,按照二进制转十进制的规律,按权相加求和,这里的权是256.
	 * 
	 * @param IP
	 * @return
	 */
	public static int ip2Int(String ip) {
		if (null == ip || "".equals(ip.trim()) || !checkIP(ip))
			return 0;
		String[] ipStrs = ip.split("\\.");// 分割ip
		int result = 0;
		for (int i = 0; i < 4; i++) {
			Integer ipSubInteger = Integer.parseInt(ipStrs[i]);
			if (ipSubInteger > 255) {// 正则验证不能为负数
				result = 0;
				break;
			}
			result += (ipSubInteger << (24 - i * 8));
		}
		return result;
	}

	/**
	 * 正则验证ip
	 * @param ip
	 * @return 是否是IP地址
	 */
	private static boolean checkIP(String ip) {
		return ip.matches("\\d{1,3}(\\.\\d{1,3}){3}");
	}

	/**
	 * 整数转IP
	 * 
	 * @param IP
	 * @return
	 */
	public String int2Ip(int ip) {
		StringBuilder builder = new StringBuilder(String.valueOf(ip >>> 24));
		builder.append(".");
		builder.append(String.valueOf((ip & 0X00FFFFFF) >>> 16));
		builder.append(".");
		builder.append(String.valueOf((ip & 0X0000FFFF) >>> 8));
		builder.append(".");
		builder.append(String.valueOf(ip & 0X000000FF));
		return builder.toString();
	}
}
