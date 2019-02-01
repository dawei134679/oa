package com.hkkj.oa.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.LogUtils;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.ApprovalDto;
import com.hkkj.oa.dto.HaveDoneTaskDto;
import com.hkkj.oa.dto.HaveDoneTaskParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ProcessedTasksDto;
import com.hkkj.oa.dto.ProcessedTasksParamDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.dto.WorkflowApplyInfoDto;
import com.hkkj.oa.dto.WorkflowApplyInfoParamDto;
import com.hkkj.oa.service.IWorkflowApplyService;
import com.hkkj.oa.service.IWorkflowApprovalService;

@Controller
@RequestMapping("/front/userWorkflowTask")
public class UserWorkflowTaskController {
	
	@Autowired
	private IWorkflowApplyService workflowApplyService;
	
	@Autowired
	private IWorkflowApprovalService workflowApprovalService;

	@SystemLog(module = "流程模块", remark = "获取用户发起的流程列表")
	@RequestMapping(value = "/getApplyByUserId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<PageBean<WorkflowApplyInfoDto>> getApplyInfoByUserId(WorkflowApplyInfoParamDto param, HttpServletRequest request) {
		try {
			UserInfoDto userInfoDto = UserUtil.getSessionUser(request);
			param.setUserId(userInfoDto.getId());
			return workflowApplyService.getApplyInfoByUserId(param);
		} catch (Exception e) {
			LogUtils.error(getClass(), "获取用户发起的流程列表异常", e);
			return ResultUtil.error("获取用户发起的流程列表失败");
		}
	}
	
	@SystemLog(module = "流程模块", remark = "获取用户代办的流程列表")
	@RequestMapping(value = "/getProcessedTasksByUserId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<PageBean<ProcessedTasksDto>> getProcessedTasksByUserId(ProcessedTasksParamDto param, HttpServletRequest request) {
		try {
			UserInfoDto userInfoDto = UserUtil.getSessionUser(request);
			param.setUserId(userInfoDto.getId());
			return workflowApprovalService.getProcessedTasksByUserId(param);
		} catch (Exception e) {
			LogUtils.error(getClass(), "获取用户代办的流程列表异常", e);
			return ResultUtil.error("获取用户代办的流程列表失败");
		}
	}
	
	@SystemLog(module = "流程模块", remark = "获取用户已办的流程列表")
	@RequestMapping(value = "/getHaveDoneTaskByUserId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<PageBean<HaveDoneTaskDto>> getHaveDoneTaskByUserId(HaveDoneTaskParamDto param, HttpServletRequest request) {
		try {
			UserInfoDto userInfoDto = UserUtil.getSessionUser(request);
			param.setUserId(userInfoDto.getId());
			return workflowApprovalService.getHaveDoneTaskByUserId(param);
		} catch (Exception e) {
			LogUtils.error(getClass(), "获取用户已办的流程列表异常", e);
			return ResultUtil.error("获取用户已办的流程列表失败");
		}
	}

	@SystemLog(module = "流程模块", remark = "审批")
	@RequestMapping(value = "/approval", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<Object> approval(ApprovalDto param, HttpServletRequest request) {
		try {
			UserInfoDto userInfoDto = UserUtil.getSessionUser(request);
			param.setApprovalUserId(userInfoDto.getId());
			return workflowApprovalService.approval(param);
		} catch (Exception e) {
			LogUtils.error(getClass(), "审批异常", e);
			return ResultUtil.error("审批失败");
		}
	}

}
