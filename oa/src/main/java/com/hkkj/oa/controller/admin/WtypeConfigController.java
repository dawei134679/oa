package com.hkkj.oa.controller.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.Constants;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.dto.UserParamDto;
import com.hkkj.oa.dto.WtypeParamDto;
import com.hkkj.oa.entity.WtypeInfo;
import com.hkkj.oa.service.IWtypeConfigService;

@Controller
@RequestMapping("/admin/wtype")
public class WtypeConfigController {
	
	@Autowired
	private IWtypeConfigService wtypeConfigService;
	
	@SystemLog(module="物种模块",remark="进入物种管理")
	@RequestMapping(value="/toWtypeManagePage")
	public String toUserManage(){
		return "wtype/wtype_manage";
	}
	
	@SystemLog(module="物种模块",remark="获取物种列表")
	@RequestMapping(value="/getWtypeConfigPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<PageBean<WtypeInfo>> getWtypeConfigPage(WtypeParamDto param, HttpServletRequest request){
		return wtypeConfigService.getWtypeConfigPage(param);
	}
	
	@SystemLog(module="物种模块",remark="进入物种新增页面")
	@RequestMapping(value="/toWtypeInfoAddPage")
	public String toWtypeInfoAddPage(){
		return "wtype/wtype_add";
	}
	
	@SystemLog(module="物种模块",remark="新增物种")
	@RequestMapping(value="/saveWtypeConfig",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> saveWtypeConfig(WtypeInfo record, HttpServletRequest request){
		UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
		long createTime = new Date().getTime();
		record.setCreateUserId((long)userInfoDto.getId());
		record.setCreateTime(createTime);
		return wtypeConfigService.saveWtypeConfig(record);
	}
	
	@SystemLog(module="物种模块",remark="进入物修改页面")
	@RequestMapping(value="/toWtypeInfoEditPage")
	public String toWtypeInfoUpdatePage(HttpServletRequest request){
		int id = Integer.valueOf(request.getParameter("id"));
		WtypeInfo wtypeInfo = wtypeConfigService.selectByPrimaryKey(id);
		request.setAttribute("data", wtypeInfo);
		return "wtype/wtype_update";
	}
	
	@SystemLog(module="物种模块",remark="修改物种")
	@RequestMapping(value="/updateWtypeConfig",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> updateWtypeConfig(WtypeInfo record, HttpServletRequest request){
		UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
		long updateTime = new Date().getTime();
		record.setUpdateUserId((long)userInfoDto.getId());
		record.setUpdateTime(updateTime);
		return wtypeConfigService.updateWtypeConfig(record);
	}
	
	@SystemLog(module="物种模块",remark="删除物种")
	@RequestMapping(value="/delWtypeInfoByIds",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> delWtypeInfoByIds(WtypeInfo record, HttpServletRequest request){
		String ids = request.getParameter("ids");
		UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
		long updateTime = new Date().getTime();
		record.setUpdateUserId((long)userInfoDto.getId());
		record.setUpdateTime(updateTime);
		return wtypeConfigService.delWtypeInfoByIds(record,ids);
	}
	
}
