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
import com.hkkj.oa.dto.WgoodsParamDto;
import com.hkkj.oa.entity.WgoodsInfo;
import com.hkkj.oa.service.IWgoodsConfigService;

@Controller
@RequestMapping("/admin/wgoods")
public class WgoodsConfigController {
	
		@Autowired
		private IWgoodsConfigService wgoodsConfigService;
		
		@SystemLog(module="物品模块",remark="进入物品管理")
		@RequestMapping(value="/toWgoodsManagePage")
		public String toUserManage(){
			return "wgoods/wgoods_manage";
		}
		
		@SystemLog(module="物品模块",remark="获取物品列表")
		@RequestMapping(value="/getWgoodsConfigPage",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<PageBean<WgoodsInfo>> getwgoodsConfigPage(WgoodsParamDto param, HttpServletRequest request){
			return wgoodsConfigService.getWgoodsConfigPage(param);
		}
		
		@SystemLog(module="物品模块",remark="进入物品新增页面")
		@RequestMapping(value="/toWgoodsInfoAddPage")
		public String towgoodsInfoAddPage(){
			return "wgoods/wgoods_add";
		}
		
		@SystemLog(module="物品模块",remark="新增物品")
		@RequestMapping(value="/saveWgoodsConfig",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<String> savewgoodsConfig(WgoodsInfo record, HttpServletRequest request){
			UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			long createTime = new Date().getTime();
			record.setCreateUserId(userInfoDto.getId());
			record.setCreateTime(createTime);
			return wgoodsConfigService.saveWgoodsConfig(record);
		}
		
		@SystemLog(module="物品模块",remark="进入物品修改页面")
		@RequestMapping(value="/toWgoodsInfoEditPage")
		public String towgoodsInfoUpdatePage(HttpServletRequest request){
			int id = Integer.valueOf(request.getParameter("id"));
			WgoodsInfo wgoodsInfo = wgoodsConfigService.selectByPrimaryKey(id);
			request.setAttribute("data", wgoodsInfo);
			return "wgoods/wgoods_update";
		}
		
		@SystemLog(module="物品模块",remark="修改物品")
		@RequestMapping(value="/updateWgoodsConfig",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<String> updatewgoodsConfig(WgoodsInfo record, HttpServletRequest request){
			UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			long updateTime = new Date().getTime();
			record.setUpdateUserId(userInfoDto.getId());
			record.setUpdateTime(updateTime);
			return wgoodsConfigService.updateWgoodsConfig(record);
		}
		
		@SystemLog(module="物品模块",remark="删除物品")
		@RequestMapping(value="/delWgoodsInfoByIds",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<String> delwgoodsInfoByIds(WgoodsInfo record, HttpServletRequest request){
			String ids = request.getParameter("ids");
			UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			long updateTime = new Date().getTime();
			record.setUpdateUserId(userInfoDto.getId());
			record.setUpdateTime(updateTime);
			return wgoodsConfigService.delWgoodsInfoByIds(record,ids);
		}
	
		
}
