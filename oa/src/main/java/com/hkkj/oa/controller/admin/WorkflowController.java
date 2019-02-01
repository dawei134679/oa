package com.hkkj.oa.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.DateUtil;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WorkflowDto;
import com.hkkj.oa.dto.WorkflowInfoParamDto;
import com.hkkj.oa.dto.WorkflowParamDto;
import com.hkkj.oa.entity.Workflow;
import com.hkkj.oa.service.IWorkflowService;

@Controller
@RequestMapping("/admin/workflow")
public class WorkflowController {
	
	@Autowired
	private IWorkflowService workflowService;
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@SystemLog(module="流程管理",remark="流程管理")
	@RequestMapping(value="/toWorkflowManagePage")
	public String toWorkflowManagePage(){
		return "workflow/workflow_manage";
	}
	
	@SystemLog(module="流程管理",remark="获取流程列表")
	@RequestMapping(value="/getWorkflowPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<PageBean<Workflow>> getWorkflowPage(WorkflowParamDto param,HttpServletRequest request){
		try {
			return workflowService.getWorkflowPage(param);
		} catch (Exception e) {
			log.error("获取流程列表异常",e);
			return ResultUtil.error("获取流程列表失败");
		}
	}
	
	@SystemLog(module="流程管理",remark="新增流程页面")
	@RequestMapping(value="/toWorkflowAddPage")
	public String toAddWorkflowManage(){
		return "workflow/workflow_add";
	}
	
	@SystemLog(module="流程管理",remark="保存流程")
	@RequestMapping(value="/saveWorkflow",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<Object> saveWorkflow(@RequestBody WorkflowInfoParamDto param,HttpServletRequest request){
		try {
			param.setCreateTime(DateUtil.nowTime());
			param.setCreateUserId(UserUtil.getSessionUser(request).getId());
			return workflowService.saveWorkflow(param);
		} catch (Exception e) {
			log.error("保存流程异常",e);
			return ResultUtil.error("保存流程失败");
		}
	}
	
	
	@SystemLog(module="流程管理",remark="删除流程")
	@RequestMapping(value="/delWorkflowByIds",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<Integer> delWorkflowByIds(@RequestBody WorkflowParamDto param,HttpServletRequest request){
		try {
			param.setModifyTime(DateUtil.nowTime());
			param.setModifyUserId(UserUtil.getSessionUser(request).getId());
			return workflowService.delWorkflowByIds(param);
		} catch (Exception e) {
			log.error("删除流程异常",e);
			return ResultUtil.error("删除流程失败");
		}
	}
	
	@SystemLog(module="流程管理",remark="进入流程审批详情页面")
	@RequestMapping(value="/toWorkflowApprovalDetailPage")
	public String toWorkflowApprovalDetailPage(HttpServletRequest request){
		request.setAttribute("workflowId", request.getParameter("workflowId"));
		return "workflow/workflow_approvalDetail";
	}
	
	@SystemLog(module="流程管理",remark="获取流程详情")
	@RequestMapping(value="/getWorkflowDetailByWorkflowId",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<WorkflowDto> getWorkflowDetailByWorkflowId(Long workflowId,HttpServletRequest request){
		try {
			return workflowService.getWorkflowDetailByWorkflowId(workflowId);
		} catch (Exception e) {
			log.error("获取流程详情异常",e);
			return ResultUtil.error("获取流程详情失败");
		}
	}
}
