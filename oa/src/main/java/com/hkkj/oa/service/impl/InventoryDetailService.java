package com.hkkj.oa.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.InventoryDetailMapper;
import com.hkkj.oa.dto.InventoryDetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.InventoryDetail;
import com.hkkj.oa.service.IInventoryDetailService;


@Service
@Transactional
public class InventoryDetailService implements IInventoryDetailService{
	
	@Autowired
	private InventoryDetailMapper inventoryDetailMapper;

	@Override
	public ResultDto<PageBean<InventoryDetail>> getInventoryDetailPage(InventoryDetailParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<InventoryDetail> list = inventoryDetailMapper.getInventoryDetailPage(param);
		return ResultUtil.success(new PageBean<InventoryDetail>(list));
	}
	
	@Override
	public InventoryDetail isExistGoods(InventoryDetailParamDto param) {
		return inventoryDetailMapper.isExistGoods(param);
	}

	@Override
	public int saveInventoryDetail(InventoryDetail record) {
		return inventoryDetailMapper.insertSelective(record);
	}

	@Override
	public int updateInventoryDetail(InventoryDetail record) {
		return inventoryDetailMapper.updateInventoryDetail(record);
	}

	

}
