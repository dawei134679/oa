package com.hkkj.oa.dao;

import java.util.List;

import com.hkkj.oa.dto.WorkflowApprovalDetailDto;
import com.hkkj.oa.dto.WorkflowParamDto;
import com.hkkj.oa.entity.Workflow;

public interface WorkflowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Workflow record);

    int insertSelective(Workflow record);

    Workflow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Workflow record);

    int updateByPrimaryKey(Workflow record);
    
    List<Workflow> getWorkflowList(WorkflowParamDto param);
    
    Workflow getWorkflowByCode(String code);
    
    int delWorkflowByIds(WorkflowParamDto param);

	List<WorkflowApprovalDetailDto> getWorkflowDetailByWorkflowId(Long workflowId);
}