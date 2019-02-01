package com.hkkj.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.WorkflowDetailMapper;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.Organization;
import com.hkkj.oa.entity.User;
import com.hkkj.oa.entity.WorkflowDetail;
import com.hkkj.oa.service.IOrganizationService;
import com.hkkj.oa.service.IUserService;
import com.hkkj.oa.service.IWorkflowDetailService;

@Service
@Transactional
public class WorkflowDetailService implements IWorkflowDetailService {

	@Autowired
	private WorkflowDetailMapper workflowDetailMapper;
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public ResultDto<List<Integer>> getWorkflowDetailApprovalUserIdRootByWorkflowId(Long workflowId) {
		List<Integer> list = new ArrayList<Integer>();
		List<WorkflowDetail>  workflowDetailList= workflowDetailMapper.getWorkflowDetailRootByWorkflowId(workflowId);
		for (WorkflowDetail workflowDetail : workflowDetailList) {
			Integer approvalId = workflowDetail.getApprovalId();
			//部门负责人
			if(workflowDetail.getType()==2) {
				Organization organization = organizationService.getOrganizationById(approvalId);
				if(organization==null) {
					return ResultUtil.fail("下级审批人不存在");
				}
				approvalId = organization.getLeaderId();
			}
			if(approvalId==null||approvalId==0) {
				return ResultUtil.fail("下级审批人不存在");
			}
			User user = userService.getUserById(approvalId);
			if(user==null) {
				return ResultUtil.fail("下级审批人不存在");
			}
			list.add(approvalId);
		}
		return ResultUtil.success(list);
	}

	@Override
	public ResultDto<List<Integer>> getLowerLevelApprovalUserId(String applyCode, Integer approvalUserId) {
		List<Integer> list = new ArrayList<Integer>();
		List<WorkflowDetail>  workflowDetailList= workflowDetailMapper.getLowerLevelApprovalInfo(applyCode,approvalUserId);
		for (WorkflowDetail workflowDetail : workflowDetailList) {
			Integer approvalId = workflowDetail.getApprovalId();
			//部门负责人
			if(workflowDetail.getType()==2) {
				Organization organization = organizationService.getOrganizationById(approvalId);
				if(organization==null) {
					return ResultUtil.fail("下级审批人不存在");
				}
				approvalId = organization.getLeaderId();
			}
			if(approvalId==null||approvalId==0) {
				return ResultUtil.fail("下级审批人不存在");
			}
			User user = userService.getUserById(approvalId);
			if(user==null) {
				return ResultUtil.fail("下级审批人不存在");
			}
			list.add(approvalId);
		}
		return ResultUtil.success(list);
	}
}
