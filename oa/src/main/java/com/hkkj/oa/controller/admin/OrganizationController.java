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

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.DateUtil;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.OrganizationTreeNodeDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.Organization;
import com.hkkj.oa.service.IOrganizationService;

@Controller
@RequestMapping("/admin/organization")
public class OrganizationController {
	
	@Autowired
	private IOrganizationService organizationService;

	private static final Logger log = LogManager.getLogger(OrganizationController.class);

	@SystemLog(module = "组织管理", remark = "进入组织管理页面")
	@RequestMapping(value = "/toOrganizationManagePage")
	public String toOrganizationManagePage() {
		return "organization/organization_manage";
	}

	@SystemLog(module = "组织管理", remark = "获取组织列表信息")
	@RequestMapping(value = "/getOrganizationList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<List<OrganizationTreeNodeDto>> getOrganizationTreeList() {
		try {
			return organizationService.getOrganizationTreeList();
		} catch (Exception e) {
			log.error("获取组织列表信息失败", e);
			return ResultUtil.error("获取组织列表信息失败");
		}
	}
	
	@SystemLog(module="组织管理",remark="保存组织信息")
	@RequestMapping(value="/saveOrganization",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<Organization> saveProjectInfo(Organization param, HttpServletRequest request){
		try {
			param.setCreateTime(DateUtil.nowTime());
			param.setCreateUserId(UserUtil.getSessionUser(request).getId());
			return organizationService.saveProjectInfo(param);
		} catch (Exception e) {
			log.error("保存组织信息失败",e);
			return ResultUtil.error("保存组织信息失败");
		}
	}
	
	@SystemLog(module="组织管理",remark="更新组织信息")
	@RequestMapping(value="/updateOrganizationById",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<Object> updateOrganizationById(Organization param, HttpServletRequest request){
		try {
			param.setModifyTime(DateUtil.nowTime());
			param.setModifyUserId(UserUtil.getSessionUser(request).getId());
			return organizationService.updateOrganizationById(param);
		} catch (Exception e) {
			log.error("更新组织信息失败",e);
			return ResultUtil.error("更新组织信息失败");
		}
	}
	
	@SystemLog(module="组织管理",remark="根据ID删除组织")
	@RequestMapping(value="/delOrganizationById",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<Object> delOrganizationById(Organization param, HttpServletRequest request){
		try {
			param.setModifyTime(DateUtil.nowTime());
			param.setModifyUserId(UserUtil.getSessionUser(request).getId());
			return organizationService.delProjectInfoById(param);
		} catch (Exception e) {
			log.error("根据ID删除组织失败",e);
			return ResultUtil.error("根据ID删除组织失败");
		}
	}
}
