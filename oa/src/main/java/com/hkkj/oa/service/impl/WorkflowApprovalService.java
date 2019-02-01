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
import com.hkkj.oa.dao.WorkflowApprovalMapper;
import com.hkkj.oa.dto.ApprovalDto;
import com.hkkj.oa.dto.ApprovalRecordDto;
import com.hkkj.oa.dto.HaveDoneTaskDto;
import com.hkkj.oa.dto.HaveDoneTaskParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ProcessedTasksDto;
import com.hkkj.oa.dto.ProcessedTasksParamDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.WorkflowApply;
import com.hkkj.oa.entity.WorkflowApproval;
import com.hkkj.oa.service.IWorkflowApplyService;
import com.hkkj.oa.service.IWorkflowApprovalService;
import com.hkkj.oa.service.IWorkflowDetailService;

@Service
@Transactional
public class WorkflowApprovalService implements IWorkflowApprovalService {

	@Autowired
	
	private WorkflowApprovalMapper workflowApprovalMapper;
	@Autowired
	private IWorkflowDetailService workflowDetailService;
	
	@Autowired
	private IWorkflowApplyService workflowApplyService;
	
	@Override
	public ResultDto<Object> saveWorkFlowApproval(WorkflowApproval workflowApproval) {
		int i =  workflowApprovalMapper.insertSelective(workflowApproval);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public ResultDto<Object> approval(ApprovalDto param) {
		if(StringUtils.isBlank(param.getApplyCode())||param.getStatus()==null||param.getStatus()==0) {
			return ResultUtil.fail("参数不能为空");
		}
		if(StringUtils.isBlank(param.getRemarks())) {
			return ResultUtil.fail("审批意见不能为空");
		}
		if(param.getApprovalUserId()==null||param.getApprovalUserId()==0) {
			return ResultUtil.fail("审批人不能为空");
		}
		WorkflowApply workflowApply = workflowApplyService.getyApplyCode(param.getApplyCode());
		if(workflowApply==null) {
			return ResultUtil.fail("申请单不存在");
		}
		WorkflowApproval workflowApproval = workflowApprovalMapper.getWorkflowApproval(workflowApply.getId(),param.getApprovalUserId());
		if(workflowApproval==null) {
			return ResultUtil.fail();
		}
		workflowApproval.setApprovalTime(DateUtil.nowTime());
		workflowApproval.setRemarks(param.getRemarks());
		workflowApproval.setStatus(param.getStatus());
		int a = workflowApprovalMapper.updateByPrimaryKeySelective(workflowApproval);
		if(a==0) {
			return ResultUtil.fail();
		}
		ResultDto<List<Integer>> approvalUserIdResDto = workflowDetailService.getLowerLevelApprovalUserId(param.getApplyCode(),param.getApprovalUserId());
		if(!ResultUtil.SUCCESS_CODE.equals(approvalUserIdResDto.getCode())) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtil.fail(approvalUserIdResDto.getMsg());
		}
		List<Integer> list = approvalUserIdResDto.getData();
		if(list!=null&&list.size()>=0) {
			//同意 流到下级节点
			if(param.getStatus()==1) {
				for (Integer approvalUserId : list) {
					WorkflowApproval record = new WorkflowApproval();
					record.setApprovalUserId(approvalUserId);
					record.setStatus(0);
					record.setWorkflowApplyId(workflowApply.getId());
					record.setWorkflowId(workflowApply.getWorkflowId());
					int i = workflowApprovalMapper.insertSelective(record);
					if(i==0) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return ResultUtil.fail();
					}
				}
				return ResultUtil.success();
			}
			//拒绝
			if(param.getStatus()==2) {
				workflowApply.setStatus(2);
				ResultDto<Object> resDto = workflowApplyService.updateApply(workflowApply);
				if(!resDto.getCode().equals(ResultUtil.SUCCESS_CODE)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ResultUtil.fail();
				}
				return ResultUtil.success();
			}
		}
		//结束流程
		workflowApply.setStatus(param.getStatus());
		ResultDto<Object> resDto = workflowApplyService.updateApply(workflowApply);
		if(!resDto.getCode().equals(ResultUtil.SUCCESS_CODE)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public ResultDto<PageBean<ProcessedTasksDto>> getProcessedTasksByUserId(ProcessedTasksParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<ProcessedTasksDto> list = workflowApprovalMapper.getProcessedTasksByUserId(param);
		return ResultUtil.success(new PageBean<ProcessedTasksDto>(list));
	}

	@Override
	public ResultDto<PageBean<HaveDoneTaskDto>> getHaveDoneTaskByUserId(HaveDoneTaskParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<HaveDoneTaskDto> list = workflowApprovalMapper.getHaveDoneTaskByUserId(param);
		return ResultUtil.success(new PageBean<HaveDoneTaskDto>(list));
	}

	@Override
	public ResultDto<List<ApprovalRecordDto>> getWorkflowApprovalRecord(String applyCode) {
		List<ApprovalRecordDto> list = workflowApprovalMapper.getWorkflowApprovalRecord(applyCode);
		return ResultUtil.success(list);
	}
}
