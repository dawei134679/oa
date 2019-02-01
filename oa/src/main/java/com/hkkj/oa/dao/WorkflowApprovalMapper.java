package com.hkkj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.dto.ApprovalRecordDto;
import com.hkkj.oa.dto.HaveDoneTaskDto;
import com.hkkj.oa.dto.HaveDoneTaskParamDto;
import com.hkkj.oa.dto.ProcessedTasksDto;
import com.hkkj.oa.dto.ProcessedTasksParamDto;
import com.hkkj.oa.entity.WorkflowApproval;

public interface WorkflowApprovalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WorkflowApproval record);

    int insertSelective(WorkflowApproval record);

    WorkflowApproval selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkflowApproval record);

    int updateByPrimaryKey(WorkflowApproval record);
    
    List<ProcessedTasksDto> getProcessedTasksByUserId(ProcessedTasksParamDto param);

	List<HaveDoneTaskDto> getHaveDoneTaskByUserId(HaveDoneTaskParamDto param);
	
	WorkflowApproval getWorkflowApproval(@Param("workflowApplyId") Long workflowApplyId,@Param("approvalUserId") Integer approvalUserId);

	List<ApprovalRecordDto> getWorkflowApprovalRecord(String applyCode);
}