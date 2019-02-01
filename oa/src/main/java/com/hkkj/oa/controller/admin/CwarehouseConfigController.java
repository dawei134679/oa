package com.hkkj.oa.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.Constants;
import com.hkkj.oa.dto.CwarehouseParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.entity.CwarehouseInfo;
import com.hkkj.oa.service.ICwarehouseConfigService;


@Controller
@RequestMapping("/admin/cwarehouse")
public class CwarehouseConfigController {
	
		@Autowired
		private ICwarehouseConfigService cwarehouseConfigService;
		
		@SystemLog(module="仓库模块",remark="进入仓库管理")
		@RequestMapping(value="/tocwarehouseManagePage")
		public String toUserManage(){
			return "cwarehouse/cwarehouse_manage";
		}
		
		@SystemLog(module="仓库模块",remark="获取仓库列表")
		@RequestMapping(value="/getCwarehouseConfigPage",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<PageBean<CwarehouseInfo>> getcwarehouseConfigPage(CwarehouseParamDto param, HttpServletRequest request){
			return cwarehouseConfigService.getCwarehouseConfigPage(param);
		}
		
		@SystemLog(module="仓库模块",remark="获取全部仓库列表")
		@RequestMapping(value="/getAllCwarehouselist",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<List<CwarehouseInfo>>  getAllCwarehouselist(CwarehouseParamDto param, HttpServletRequest request){
			return cwarehouseConfigService.getAllCwarehouseConfigPage(param);
		}
		
		@SystemLog(module="仓库模块",remark="进入仓库新增页面")
		@RequestMapping(value="/toCwarehouseInfoAddPage")
		public String tocwarehouseInfoAddPage(){
			return "cwarehouse/cwarehouse_add";
		}
		
		@SystemLog(module="仓库模块",remark="新增仓库")
		@RequestMapping(value="/saveCwarehouseConfig",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<String> savecwarehouseConfig(CwarehouseInfo record, HttpServletRequest request){
			UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			long createTime = new Date().getTime();
			record.setCreateUserId(userInfoDto.getId());
			record.setCreateTime(createTime);
			return cwarehouseConfigService.saveCwarehouseConfig(record);
		}
		
		@SystemLog(module="仓库模块",remark="进入仓库修改页面")
		@RequestMapping(value="/toCwarehouseInfoEditPage")
		public String tocwarehouseInfoUpdatePage(HttpServletRequest request){
			int id = Integer.valueOf(request.getParameter("id"));
			CwarehouseInfo cwarehouseInfo = cwarehouseConfigService.selectByPrimaryKey(id);
			request.setAttribute("data", cwarehouseInfo);
			return "cwarehouse/cwarehouse_update";
		}
		
		@SystemLog(module="仓库模块",remark="修改仓库")
		@RequestMapping(value="/updateCwarehouseConfig",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<String> updatecwarehouseConfig(CwarehouseInfo record, HttpServletRequest request){
			UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			long updateTime = new Date().getTime();
			record.setUpdateUserId(userInfoDto.getId());
			record.setUpdateTime(updateTime);
			return cwarehouseConfigService.updateCwarehouseConfig(record);
		}
		
		@SystemLog(module="仓库模块",remark="删除仓库")
		@RequestMapping(value="/delCwarehouseInfoByIds",method= {RequestMethod.GET,RequestMethod.POST})
		@ResponseBody
		public ResultDto<String> delcwarehouseInfoByIds(CwarehouseInfo record, HttpServletRequest request){
			String ids = request.getParameter("ids");
			UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			long updateTime = new Date().getTime();
			record.setUpdateUserId(userInfoDto.getId());
			record.setUpdateTime(updateTime);
			return cwarehouseConfigService.delCwarehouseInfoByIds(record,ids);
		}
}
