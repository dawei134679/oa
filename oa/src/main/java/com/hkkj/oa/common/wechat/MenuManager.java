package com.hkkj.oa.common.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class MenuManager {

	public static String getMenu() {
		// 创建一级目录
		JSONObject jobject = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			// 商家登录
			JSONObject businessLogin = new JSONObject();
			businessLogin.put("type", "view");
			businessLogin.put("name", "警员登录");
			//businessLogin.put("url", SysProp.getObj("menu_employee_login"));
			businessLogin.put("url","http://ddc.sc8838.com/ddc/wap/police/login.do");

			// 用户登录
			JSONObject userLogin = new JSONObject();
			userLogin.put("type", "view");
			userLogin.put("name", "用户登录");
			//userLogin.put("url", SysProp.getObj("menu_user_login"));
			userLogin.put("url","http://ddc.sc8838.com/ddc/wap/user/login.do");
			
			array.add(businessLogin);
			array.add(userLogin);
			
			jobject.put("button", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jobject.toString();
	}

}