package com.hkkj.oa.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hkkj.oa.common.utils.DateUtil;
import com.hkkj.oa.common.utils.HMap;
import com.hkkj.oa.common.utils.RandomUtil;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.PurchaseDetailMapper;
import com.hkkj.oa.dao.PurchaseMapper;
import com.hkkj.oa.dto.ApprovalRecordDto;
import com.hkkj.oa.dto.PurchaseDetailParamDto;
import com.hkkj.oa.dto.PurchaseParamDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WorkflowApplyParamDto;
import com.hkkj.oa.entity.Purchase;
import com.hkkj.oa.entity.PurchaseDetail;
import com.hkkj.oa.service.IPurchaseService;
import com.hkkj.oa.service.IWorkflowApplyService;
import com.hkkj.oa.service.IWorkflowApprovalService;

@Service
@Transactional
public class PurchaseService implements IPurchaseService {
	
	@Autowired
	private IWorkflowApplyService workflowApplyService;
	
	@Autowired
	private PurchaseMapper purchaseMapper;

	@Autowired
	private PurchaseDetailMapper purchaseDetailMapper;
	
	@Autowired
	private IWorkflowApprovalService workflowApprovalService;

	@Override
	public ResultDto<Object> initiationPurchaseProcess(PurchaseParamDto param) {
		if (StringUtils.isBlank(param.getTitle())) {
			return ResultUtil.fail("请输入标题");
		}
		if (StringUtils.isBlank(param.getRemark())) {
			return ResultUtil.fail("请输入备注");
		}
		Purchase record = new Purchase();
		record.setApplyCode(RandomUtil.getTimeRandom(3));
		record.setTitle(param.getTitle());
		record.setRemark(param.getRemark());
		record.setCreateTime(DateUtil.nowTime());
		record.setCreateUserId(Long.valueOf(param.getCreateUserId()));
		int i = purchaseMapper.insertSelective(record);
		if (i != 1) {
			return ResultUtil.fail("提交失败");
		}
		WorkflowApplyParamDto workflowApplyParamDto = new  WorkflowApplyParamDto();
		workflowApplyParamDto.setApplyCode(record.getApplyCode());
		workflowApplyParamDto.setApplyUserId(param.getCreateUserId());
		workflowApplyParamDto.setExtra(record.getTitle());
		workflowApplyParamDto.setWorkflowCode("000001");
		ResultDto<Object> resDto = workflowApplyService.submitApply(workflowApplyParamDto);
		if(!resDto.getCode().equals(ResultUtil.SUCCESS_CODE)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return resDto;//ResultUtil.fail("提交失败");
		}
		List<PurchaseDetailParamDto> detailList = param.getPurchaseDetailList();
		if (null != detailList && detailList.size() > 0) {
			for (PurchaseDetailParamDto detail : detailList) {
				PurchaseDetail p = new PurchaseDetail();
				p.setName(detail.getName());
				p.setNum(detail.getNum());
				p.setPrice(detail.getPrice());
				p.setPurchaseId(record.getId());
				p.setSpecifications(detail.getSpecifications());
				p.setUnit(detail.getUnit());
				p.setDescription(detail.getDescription());
				int k = purchaseDetailMapper.insertSelective(p);
				if (k != 1) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ResultUtil.fail("保存明细失败");
				}
			}

		}
		return ResultUtil.success();
	}

	@Override
	public ResultDto<HMap> getPurchaseProcessInfo(String applyCode) {
		Purchase purchase = purchaseMapper.selectByApplyCode(applyCode);
		if(null == purchase) {
			return ResultUtil.fail("查询采购流程失败");
		}
		List<PurchaseDetail> details = purchaseDetailMapper.listDetailByPurchaseId(purchase.getId());
		HMap hmap = HMap.by("purchase", purchase);
		hmap.set("detailList", details);
		ResultDto<List<ApprovalRecordDto>> logs = workflowApprovalService.getWorkflowApprovalRecord(applyCode);
		hmap.set("logs", logs.getData());
		return ResultUtil.success(hmap);
	}

}
