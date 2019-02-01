package com.hkkj.oa.service;

import com.hkkj.oa.dto.WorkflowApplyInfoParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WorkflowApplyInfoDto;
import com.hkkj.oa.dto.WorkflowApplyParamDto;
import com.hkkj.oa.entity.WorkflowApply;

public interface IWorkflowApplyService {
	
	public ResultDto<Object>  submitApply(WorkflowApplyParamDto param);

	public WorkflowApply  getyApplyCode(String applyCode);
	
	public ResultDto<Object>  updateApply(WorkflowApply param);

	public ResultDto<PageBean<WorkflowApplyInfoDto>> getApplyInfoByUserId(WorkflowApplyInfoParamDto param);

	public int getyApplyCountByWorkflowId(Long workflowId);
}
