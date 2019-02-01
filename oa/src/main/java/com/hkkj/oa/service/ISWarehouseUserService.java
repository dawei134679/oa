package com.hkkj.oa.service;

import java.util.List;
import java.util.Map;

import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.SWhUserParamDto;
import com.hkkj.oa.dto.WarehouseDto;
import com.hkkj.oa.entity.SWarehouseUser;

public interface ISWarehouseUserService {

	ResultDto<PageBean<Map<String, Object>>> getSWhUserPage(SWhUserParamDto param);

	ResultDto<String> saveSWhUser(SWarehouseUser record);

	SWarehouseUser selectByPrimaryKey(int id);

	ResultDto<String> updateSWhUser(SWarehouseUser record);

	ResultDto<String> delSWhUserByIds(String ids);
	
	ResultDto<String> isCwarehouseManager(Integer uid);
	
	ResultDto<List<WarehouseDto>>  getCwarehouseConfigListByUid(Integer uid);
}
