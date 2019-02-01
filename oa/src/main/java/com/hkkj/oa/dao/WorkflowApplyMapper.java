package com.hkkj.oa.dao;

import java.util.List;

import com.hkkj.oa.dto.WorkflowApplyInfoParamDto;
import com.hkkj.oa.dto.WorkflowApplyInfoDto;
import com.hkkj.oa.entity.WorkflowApply;

public interface WorkflowApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WorkflowApply record);

    int insertSelective(WorkflowApply record);

    WorkflowApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkflowApply record);

    int updateByPrimaryKey(WorkflowApply record);

    WorkflowApply getyApplyCode(String applyCode);

	List<WorkflowApplyInfoDto> getApplyInfoByUserId(WorkflowApplyInfoParamDto param);

	int getyApplyCountByWorkflowId(Long workflowId);
}