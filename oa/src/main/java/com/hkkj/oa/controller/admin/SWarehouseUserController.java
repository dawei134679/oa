package com.hkkj.oa.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.SWhUserParamDto;
import com.hkkj.oa.entity.SWarehouseUser;
import com.hkkj.oa.entity.WgoodsInfo;
import com.hkkj.oa.service.ISWarehouseUserService;

@Controller
@RequestMapping("/admin/SWhUser")
public class SWarehouseUserController {
	
	private static final Logger log = LogManager.getLogger(SWarehouseUserController.class);
	
	@Autowired
	private ISWarehouseUserService sWarehouseUserService;
	
	@SystemLog(module="仓库管理员模块",remark="进入仓库管理员管理")
	@RequestMapping(value="/toSWhUserManagePage")
	public String toSWhUserManagePage(){
		return "chuser/chuser_manage";
	}
	
	@SystemLog(module="仓库管理员模块",remark="获取仓库管理员列表")
	@RequestMapping(value="/getSWhUserPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<PageBean<Map<String, Object>>> getSWhUserPage(SWhUserParamDto param, HttpServletRequest request){
		return sWarehouseUserService.getSWhUserPage(param);
	}
	
	@SystemLog(module="仓库管理员模块",remark="进入仓库管理员新增页面")
	@RequestMapping(value="/toSWhUserAddPage")
	public String tochuserInfoAddPage(){
		return "chuser/chuser_add";
	}
	
	@SystemLog(module="仓库管理员模块",remark="新增仓库管理员")
	@RequestMapping(value="/saveSWhUser",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> saveSWhUser(SWarehouseUser record, HttpServletRequest request){
		return sWarehouseUserService.saveSWhUser(record);
	}
	
	@SystemLog(module="仓库管理员模块",remark="进入仓库管理员修改页面")
	@RequestMapping(value="/toSWhUserEditPage")
	public String toSWhUserEditPage(HttpServletRequest request){
		int id = Integer.valueOf(request.getParameter("id"));
		SWarehouseUser chuserInfo = sWarehouseUserService.selectByPrimaryKey(id);
		request.setAttribute("data", chuserInfo);
		return "chuser/chuser_update";
	}
	
	@SystemLog(module="仓库管理员模块",remark="修改仓库管理员")
	@RequestMapping(value="/updateSWhUser",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> updateSWhUser(SWarehouseUser record, HttpServletRequest request){
		return  sWarehouseUserService.updateSWhUser(record);
	}
	
	@SystemLog(module="仓库管理员模块",remark="删除仓库管理员")
	@RequestMapping(value="/delSWhUserByIds",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> delSWhUserByIds(WgoodsInfo record, HttpServletRequest request){
		String ids = request.getParameter("ids");
		System.out.println("要删除的id       "+ids);
		return sWarehouseUserService.delSWhUserByIds(ids);
	}
}
