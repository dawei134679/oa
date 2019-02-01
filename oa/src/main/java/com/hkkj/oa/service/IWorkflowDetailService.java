package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.ResultDto;

public interface IWorkflowDetailService {
	
	public ResultDto<List<Integer>> getWorkflowDetailApprovalUserIdRootByWorkflowId(Long workflowId);

	public ResultDto<List<Integer>> getLowerLevelApprovalUserId(String applyCode, Integer approvalUserId);
}
