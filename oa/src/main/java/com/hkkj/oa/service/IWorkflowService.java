package com.hkkj.oa.service;

import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WorkflowDto;
import com.hkkj.oa.dto.WorkflowInfoParamDto;
import com.hkkj.oa.dto.WorkflowParamDto;
import com.hkkj.oa.entity.Workflow;

public interface IWorkflowService {

	public ResultDto<PageBean<Workflow>>  getWorkflowPage(WorkflowParamDto param);
	
	public ResultDto<Object>  saveWorkflow(WorkflowInfoParamDto param);
	
	public ResultDto<Integer>  delWorkflowByIds(WorkflowParamDto param);

	public Workflow getWorflowByCode(String code);

	public ResultDto<WorkflowDto> getWorkflowDetailByWorkflowId(Long workflowId);
}
