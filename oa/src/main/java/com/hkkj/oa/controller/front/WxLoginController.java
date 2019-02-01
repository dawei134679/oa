package com.hkkj.oa.controller.front;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hkkj.oa.common.annotation.NoAuth;
import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.AESCipher;
import com.hkkj.oa.common.utils.Constants;
import com.hkkj.oa.common.utils.DateUtil;
import com.hkkj.oa.common.utils.LogUtils;
import com.hkkj.oa.common.utils.RandomUtil;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.wechat.WeixinUtil;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.entity.WechatUser;
import com.hkkj.oa.service.IUserService;
import com.hkkj.oa.service.IWcLoginService;

@Controller
@RequestMapping("/front/wechat")
public class WxLoginController {

	private static final Logger log = LogManager.getLogger(WxLoginController.class);

	@Autowired
	private IWcLoginService iWcLoginService;

	@Autowired
	private IUserService iUserService;


	/**
	 * 公众号入口（进销存、OA办公）
	 * @param request
	 * @param response
	 * @param page
	 */
	@NoAuth
	@SystemLog(module = "微信登录模块", remark = "验证权限")
	@RequestMapping(value = "/auth/{page}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void auth(HttpServletRequest request, HttpServletResponse response, @PathVariable String page) {
		try {
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constants.WECHAT_APPID
					+ "&redirect_uri=" + URLDecoder.decode(Constants.WECHAT_LOGIN_URL + page, "utf-8")
					+ "&response_type=code&scope=snsapi_base&state=state#wechat_redirect");
		} catch (Exception e) {
			log.error("登录异常", e);
		}
	}
	
	@NoAuth
	@SystemLog(module = "微信登录模块", remark = "登录")
	@RequestMapping(value = "/login/{page}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void weChatLogin(HttpServletRequest request, HttpServletResponse response, @PathVariable String page) {
		try {
			String code = request.getParameter("code");
			JSONObject jo = WeixinUtil.getAccess_Token(Constants.WECHAT_APPID, Constants.WECHAT_SECRET, code);
			LogUtils.debug(getClass(), "微信返回参数 : " + jo.toString());
			String openid = (String) jo.get("openid");
			ResultDto<Map<String, Object>> result = iWcLoginService.findUserByOpenId(openid);
			String token = result.getCode().equals("200") ? "&token=" + result.getData().get("token") : "";
			response.sendRedirect(String.format(Constants.WECHAT_INDEX_URL,page) + "?code=" + code + token);
		} catch (Exception e) {
			log.error("登录异常", e);
		}
	}

	@NoAuth
	@SystemLog(module = "微信登录模块", remark = "绑定用户OpenId")
	@RequestMapping(value = "/boundOpenId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<Map<String, Object>> boundOprnId(HttpServletRequest request) {
		try {
			String userCode = request.getParameter("userCode");
			String password = request.getParameter("password");
			ResultDto<UserInfoDto> userinfo = iUserService.getUserByUserCodeAndPwd(userCode, password);
			Map<String, Object> result = new HashMap<>();
			if ("200".equals(userinfo.getCode())) {
				String token = AESCipher.aesEncryptString(userCode+"_"+RandomUtil.getTimeRandom(3));
				String code = request.getParameter("code");
				JSONObject jo = WeixinUtil.getAccess_Token(Constants.WECHAT_APPID, Constants.WECHAT_SECRET, code);
				String openid = (String) jo.get("openid");
				
				iWcLoginService.delWechatUserByUserCode(userCode);
				iWcLoginService.delWechatUserByOpenid(openid);
				WechatUser wechatUser = new WechatUser();
				/** 如果要更新opendid则用下面这个 **/
				wechatUser.setUserCode(userCode);
				wechatUser.setOpenId(openid);
				wechatUser.setUserToken(token);
				// 新增或更改
				int num = 0;
				if (wechatUser.getId() == null || wechatUser.getId() == 0) {// 新增
					num = iWcLoginService.insertSelective(wechatUser);
				} else {// 修改
					num = iWcLoginService.updateSelective(wechatUser);
				}
				if (num != 1) {
					return ResultUtil.fail("登录失败!");
				}
				result.put("token", token);
				result.put("message", "绑定成功");
				return ResultUtil.success(result);
			} else {
				return ResultUtil.fail("账号或者密码不正确!");
			}

		} catch (Exception e) {
			log.error("绑定用户OpenId异常", e);
			return ResultUtil.error("绑定用户OpenId失败");
		}
	}

}
