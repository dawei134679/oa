package com.hkkj.oa.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hkkj.oa.controller.admin.UserController;
import com.hkkj.oa.dto.UserInfoDto;

public class UserUtil {

	private static final Logger log = LogManager.getLogger(UserController.class);

	public static UserInfoDto getSessionUser(HttpServletRequest request) {
		try {
			return (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public static void setSessionUser(HttpServletRequest request, UserInfoDto userDto) {
		try {
			request.getSession().setAttribute(Constants.SESSION_CURRENT_USER, userDto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
