package com.hkkj.oa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.RandomUtil;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.WorkflowDetailMapper;
import com.hkkj.oa.dao.WorkflowMapper;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WorkflowApprovalDetailDto;
import com.hkkj.oa.dto.WorkflowDto;
import com.hkkj.oa.dto.WorkflowInfoDetailParamDto;
import com.hkkj.oa.dto.WorkflowInfoParamDto;
import com.hkkj.oa.dto.WorkflowParamDto;
import com.hkkj.oa.entity.Workflow;
import com.hkkj.oa.entity.WorkflowDetail;
import com.hkkj.oa.service.IWorkflowApplyService;
import com.hkkj.oa.service.IWorkflowService;

@Service
@Transactional
public class WorkflowService implements IWorkflowService {

	@Autowired
	private WorkflowMapper workflowMapper;
	
	@Autowired
	private WorkflowDetailMapper workflowDetailMapper;
	
	@Autowired
	private IWorkflowApplyService workflowApplyService;
	
	@Override
	public ResultDto<PageBean<Workflow>> getWorkflowPage(WorkflowParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<Workflow> list = workflowMapper.getWorkflowList(param);
		return ResultUtil.success(new PageBean<Workflow>(list));
	}

	@Override
	public ResultDto<Object> saveWorkflow(WorkflowInfoParamDto param) {
		Workflow w = getWorflowByCode(param.getCode());
		if(w!=null) {
			return ResultUtil.fail();
		}
		Workflow workflow =  new Workflow();
		workflow.setName(param.getName());
		workflow.setRemarks(param.getRemarks());
		workflow.setStatus(1);
		workflow.setCode(param.getCode());
		workflow.setCreateUserId(param.getCreateUserId());
		workflow.setCreateTime(param.getCreateTime());
		int i = workflowMapper.insertSelective(workflow);
		if(i==0) {
			return ResultUtil.fail();
		}
		
		List<WorkflowInfoDetailParamDto> list  = param.getDetails();
		List<String> keys =  new ArrayList<String>();
		Map<String,List<WorkflowInfoDetailParamDto>> data = new HashMap<String,List<WorkflowInfoDetailParamDto>>();
		for (int j = 0; j < list.size(); j++) {
			boolean flag = true;
			WorkflowInfoDetailParamDto workflowInfoDetailParamDto = list.get(j);
			for(Entry<String,List<WorkflowInfoDetailParamDto>> entry : data.entrySet()) {
				if(StringUtils.isNotBlank(entry.getKey())&&entry.getKey().equals(workflowInfoDetailParamDto.getKey())) {
					List<WorkflowInfoDetailParamDto> tempList = entry.getValue();
					tempList.add(workflowInfoDetailParamDto);
					data.put(entry.getKey(), tempList);
					flag = false;
					break;
				}
			}
			if(flag) {
				List<WorkflowInfoDetailParamDto> tempList = new ArrayList<WorkflowInfoDetailParamDto>();
				tempList.add(workflowInfoDetailParamDto);
				String key = StringUtils.isBlank(workflowInfoDetailParamDto.getKey())?RandomUtil.getUUID():workflowInfoDetailParamDto.getKey();
				data.put(key, tempList);
				keys.add(key);
			}
		}
		long pid = 0;
		String parentCode = "0";
		for (String key : keys) {
			
			String uuid = RandomUtil.getUUID();
			for (WorkflowInfoDetailParamDto workflowInfoDetailParamDto : data.get(key)) {
				WorkflowDetail workflowDetail =  new WorkflowDetail();
				workflowDetail.setType(workflowInfoDetailParamDto.getType());
				workflowDetail.setApprovalId(workflowInfoDetailParamDto.getApprovalId());
				workflowDetail.setConditions(workflowInfoDetailParamDto.getConditions());
				workflowDetail.setWorkflowId(workflow.getId());
				workflowDetail.setParentId(pid);
				workflowDetail.setParentCode(parentCode);
				workflowDetail.setCode(uuid);
				int a = workflowDetailMapper.insertSelective(workflowDetail);
				if(a==0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ResultUtil.fail();
				}
				pid = workflowDetail.getId();
			}
			parentCode = uuid;
		}
		return ResultUtil.success();
	}

	@Override
	public ResultDto<Integer> delWorkflowByIds(WorkflowParamDto param) {
		for (Long id : param.getIds()) {
			int count = workflowApplyService.getyApplyCountByWorkflowId(id);
			if(count>0) {
				return ResultUtil.fail("有正在进行的流程审批，删除失败");
			}
		}
		int i =  workflowMapper.delWorkflowByIds(param);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public Workflow getWorflowByCode(String code) {
		return workflowMapper.getWorkflowByCode(code);
	}

	@Override
	public ResultDto<WorkflowDto> getWorkflowDetailByWorkflowId(Long workflowId) {
		WorkflowDto workflowDto =  new WorkflowDto();
		
		Workflow workflow = workflowMapper.selectByPrimaryKey(workflowId);
		workflowDto.setWorkflowId(workflow.getId());
		workflowDto.setWorkflowCode(workflow.getCode());
		workflowDto.setWorkflowName(workflow.getName());
		workflowDto.setWorkflowRemarks(workflow.getRemarks());
		
		List<WorkflowApprovalDetailDto> list =  workflowMapper.getWorkflowDetailByWorkflowId(workflowId);
		workflowDto.setApprovalDetailList(list);
		
		return ResultUtil.success(workflowDto);
	}
}
