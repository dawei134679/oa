package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.ApprovalDto;
import com.hkkj.oa.dto.ApprovalRecordDto;
import com.hkkj.oa.dto.HaveDoneTaskDto;
import com.hkkj.oa.dto.HaveDoneTaskParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ProcessedTasksDto;
import com.hkkj.oa.dto.ProcessedTasksParamDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.WorkflowApproval;

public interface IWorkflowApprovalService {

	public ResultDto<Object> saveWorkFlowApproval(WorkflowApproval workflowApproval);
	 
	public ResultDto<Object> approval(ApprovalDto param);
	
	public ResultDto<PageBean<ProcessedTasksDto>> getProcessedTasksByUserId(ProcessedTasksParamDto param);
	
	public ResultDto<PageBean<HaveDoneTaskDto>> getHaveDoneTaskByUserId(HaveDoneTaskParamDto param);
	
	public ResultDto<List<ApprovalRecordDto>> getWorkflowApprovalRecord(String applyCode);
}
