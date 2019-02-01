package com.hkkj.oa.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.NoAuth;
import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.Constants;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.MenuTreeNodeDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.service.IMenuService;
import com.hkkj.oa.service.IUserService;

@Controller
@RequestMapping("/admin/main")
public class MainController {
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IMenuService menuService;
	
	@NoAuth
	@SystemLog(module="用户模块",remark="进入登录页面")
	@RequestMapping(value="/login",method= {RequestMethod.GET,RequestMethod.POST})
	public String toLoginPage(){
		return "login";
	}
	
	@NoAuth
	@SystemLog(module="用户模块",remark="用户登录")
	@RequestMapping(value="/doLogin",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<UserInfoDto> doLogin(HttpServletRequest request){
		try {
			String userCode = request.getParameter("userCode");
			String password = request.getParameter("password");
			ResultDto<UserInfoDto> resultDto =  userService.getUserByUserCodeAndPwd(userCode, password);
			if(resultDto.getData()!=null) {
				request.getSession().setAttribute(Constants.SESSION_CURRENT_USER, resultDto.getData());
			}
			return resultDto;
		} catch (Exception e) {
			log.error("登录异常",e);
			return ResultUtil.error("登录失败");
		}
	}
	
	@SystemLog(module="用户模块",remark="退出登录")
	@RequestMapping(value="/doLogout",method= {RequestMethod.GET,RequestMethod.POST})
	public String doLogout(HttpServletRequest request){
		request.getSession().invalidate();
		return "login";
	}
	
	@SystemLog(module="用户模块",remark="进入主页面")
	@RequestMapping(value="/main")
	public String toMainPage(){
		return "main";
	}
	
	@SystemLog(module="用户模块",remark="获取菜单")
	@RequestMapping(value="/getMenuByUser",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<List<MenuTreeNodeDto>> getMenuByUser(HttpServletRequest request){
		try {
			return menuService.getMenuByUserId(UserUtil.getSessionUser(request).getId());
		} catch (Exception e) {
			log.error("获取菜单异常",e);
			return ResultUtil.error("获取菜单失败");
		}
	}
}
