package com.hkkj.oa.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.Constants;
import com.hkkj.oa.common.utils.DateUtil;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.dto.UserParamDto;
import com.hkkj.oa.dto.UserTreeNodeDto;
import com.hkkj.oa.entity.User;
import com.hkkj.oa.service.IUserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@SystemLog(module="用户模块",remark="进入用户管理")
	@RequestMapping(value="/toUserManagePage")
	public String toUserManage(){
		return "user/user_manage";
	}
	
	@SystemLog(module="用户模块",remark="获取用户列表")
	@RequestMapping(value="/getUserInfoPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<PageBean<UserInfoDto>> getUserInfoPage(UserParamDto param, HttpServletRequest request){
		try {
			return userService.getUserInfoPage(param);
		} catch (Exception e) {
			log.error("获取用户列表",e);
			return ResultUtil.error("用户列表失败");
		}
	}
	
	@SystemLog(module="用户模块",remark="进入用户新增页面")
	@RequestMapping(value="/toUserAddPage")
	public String toUserAddPage(){
		return "user/user_add";
	}
	
	
	@SystemLog(module="用户模块",remark="保存用户信息")
	@RequestMapping(value="/saveUser",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<Object> saveUser(User param, HttpServletRequest request){
		try {
			param.setCreateTime(DateUtil.nowTime());
			param.setCreateUserId(UserUtil.getSessionUser(request).getId());
			return userService.saveUser(param);
		} catch (Exception e) {
			log.error("保存用户信息失败",e);
			return ResultUtil.error("保存用户信息失败");
		}
	}
	
	@SystemLog(module="用户模块",remark="进入用户编辑页面")
	@RequestMapping(value="/toUserEditPage")
	public String toUserEditPage(HttpServletRequest request){
		int id = Integer.valueOf(request.getParameter("id"));
		User userInfo = userService.selectByPrimaryKey(id);
		request.setAttribute("data", userInfo);
		return "user/user_edit";
	}
	
	@SystemLog(module="用户模块",remark="修改用户信息")
	@RequestMapping(value="/modifyUserById",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<Object> modifyUserById(User param, HttpServletRequest request){
		try {
			param.setModifyTime(DateUtil.nowTime());
			param.setModifyUserId(UserUtil.getSessionUser(request).getId());
			return userService.modifyUserById(param);
		} catch (Exception e) {
			log.error("修改用户信息失败",e);
			return ResultUtil.error("修改用户信息失败");
		}
	}
	
	@SystemLog(module="用户模块",remark="删除用户信息")
	@RequestMapping(value="/delUserByIds",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> delUserByIds(User record, HttpServletRequest request){
		String ids = request.getParameter("ids");
		UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
		long updateTime = new Date().getTime();
		record.setModifyUserId(userInfoDto.getId());
		record.setModifyTime(updateTime);
		return userService.delUserByIds(record,ids);
	}
	
	@SystemLog(module="用户模块",remark="获取部门用户列表")
	@RequestMapping(value="/getUserInfoPageByOrgId",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<List<UserTreeNodeDto>> getUserInfoPageByOrgId(UserParamDto param, HttpServletRequest request){
		try {
			return userService.getUserInfoPageByOrgId(param);
		} catch (Exception e) {
			log.error("获取部门用户列表",e);
			return ResultUtil.error("获取部门用户列表失败");
		}
	}
	
	@SystemLog(module="用户模块",remark="获取用户列表")
	@RequestMapping(value="/getALlUserInfoList",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<List<UserTreeNodeDto>> getALlUserInfoList(HttpServletRequest request){
		try {
			return userService.getALlUserInfoList();
		} catch (Exception e) {
			log.error("获取用户列表",e);
			return ResultUtil.error("获取用户列表失败");
		}
	}
}
