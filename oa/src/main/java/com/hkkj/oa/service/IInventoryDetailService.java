package com.hkkj.oa.service;

import com.hkkj.oa.dto.InventoryDetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.InventoryDetail;

public interface IInventoryDetailService {
	
	ResultDto<PageBean<InventoryDetail>> getInventoryDetailPage(InventoryDetailParamDto param);
	
	public InventoryDetail isExistGoods(InventoryDetailParamDto param);
	
	public int saveInventoryDetail(InventoryDetail record);
	
	public int updateInventoryDetail(InventoryDetail record);
	
}
