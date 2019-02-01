package com.hkkj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.entity.WorkflowDetail;

public interface WorkflowDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WorkflowDetail record);

    int insertSelective(WorkflowDetail record);

    WorkflowDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkflowDetail record);

    int updateByPrimaryKey(WorkflowDetail record);

	List<WorkflowDetail> getWorkflowDetailRootByWorkflowId(Long workflowId);

	List<WorkflowDetail> getLowerLevelApprovalInfo(@Param("applyCode") String applyCode, @Param("approvalUserId") Integer approvalUserId);
}