package com.hkkj.oa.service;

import com.hkkj.oa.common.utils.HMap;
import com.hkkj.oa.dto.PurchaseParamDto;
import com.hkkj.oa.dto.ResultDto;

public interface IPurchaseService {

	/**
	 * 添加采购流程
	 * @param param
	 * @return
	 */
	ResultDto<Object> initiationPurchaseProcess(PurchaseParamDto param);

	/**
	 * 根据采购流程申请编号查询采购明细列表
	 * @param applyCode
	 * @return
	 */
	public ResultDto<HMap> getPurchaseProcessInfo(String applyCode);

}
