package com.hkkj.oa.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.DateUtil;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.WorkflowApplyMapper;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WorkflowApplyInfoDto;
import com.hkkj.oa.dto.WorkflowApplyInfoParamDto;
import com.hkkj.oa.dto.WorkflowApplyParamDto;
import com.hkkj.oa.entity.Workflow;
import com.hkkj.oa.entity.WorkflowApply;
import com.hkkj.oa.entity.WorkflowApproval;
import com.hkkj.oa.service.IWorkflowApplyService;
import com.hkkj.oa.service.IWorkflowApprovalService;
import com.hkkj.oa.service.IWorkflowDetailService;
import com.hkkj.oa.service.IWorkflowService;

@Service
@Transactional
public class WorkflowApplyService implements IWorkflowApplyService{

	@Autowired
	private WorkflowApplyMapper workflowApplyMapper;
	
	@Autowired
	private IWorkflowService workflowService;
	
	@Autowired
	private IWorkflowDetailService workflowDetailService;
	
	@Autowired
	private IWorkflowApprovalService workflowApprovalService;
	
	@Override
	public ResultDto<Object> submitApply(WorkflowApplyParamDto param) {
		if(param.getApplyUserId()==null||param.getApplyUserId()==0) {
			return ResultUtil.fail("申请人不能为空");
		}
		if(StringUtils.isBlank(param.getApplyCode())) {
			return ResultUtil.fail("申请单不能为空");
		}
		if(StringUtils.isBlank(param.getWorkflowCode())) {
			return ResultUtil.fail("流程不能为空");
		}
		Workflow workflow = workflowService.getWorflowByCode(param.getWorkflowCode());
		if(workflow==null) {
			return ResultUtil.fail("流程不存在");
		}
		WorkflowApply workflowApply =  new WorkflowApply();
		workflowApply.setCode(param.getApplyCode());
		workflowApply.setStatus(0);
		workflowApply.setApplyUserId(param.getApplyUserId());
		workflowApply.setApplyTime(DateUtil.nowTime());
		workflowApply.setWorkflowId(workflow.getId());
		workflowApply.setExtra(param.getExtra());
		
		int i = workflowApplyMapper.insertSelective(workflowApply);
		if(i==0) {
			ResultUtil.fail();
		}
		ResultDto<List<Integer>> approvalUserIdResDto = workflowDetailService.getWorkflowDetailApprovalUserIdRootByWorkflowId(workflow.getId());
		if(!ResultUtil.SUCCESS_CODE.equals(approvalUserIdResDto.getCode())) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtil.fail(approvalUserIdResDto.getMsg());
		}
		List<Integer> list = approvalUserIdResDto.getData();
		for (Integer approvalId : list) {
			WorkflowApproval workflowApproval = new WorkflowApproval();
			workflowApproval.setWorkflowId(workflowApply.getWorkflowId());
			workflowApproval.setWorkflowApplyId(workflowApply.getId());
			workflowApproval.setApprovalUserId(approvalId);
			ResultDto<Object> resDto  = workflowApprovalService.saveWorkFlowApproval(workflowApproval);
			if(!ResultUtil.SUCCESS_CODE.equals(resDto.getCode())) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return ResultUtil.fail();
			}
		}
		return ResultUtil.success();
	}

	@Override
	public ResultDto<PageBean<WorkflowApplyInfoDto>> getApplyInfoByUserId(WorkflowApplyInfoParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<WorkflowApplyInfoDto> list = workflowApplyMapper.getApplyInfoByUserId(param);
		return ResultUtil.success(new PageBean<WorkflowApplyInfoDto>(list));
	}

	@Override
	public WorkflowApply getyApplyCode(String applyCode) {
		return workflowApplyMapper.getyApplyCode(applyCode);
	}

	@Override
	public ResultDto<Object> updateApply(WorkflowApply param) {
		int i = workflowApplyMapper.updateByPrimaryKeySelective(param);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public int getyApplyCountByWorkflowId(Long workflowId) {
		return workflowApplyMapper.getyApplyCountByWorkflowId(workflowId);
	}
}
