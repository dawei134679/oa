package com.hkkj.oa.service;

import java.util.List;
import java.util.Map;

import com.hkkj.oa.dto.CwarehouseParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.CwarehouseInfo;

public interface ICwarehouseConfigService {
	
	public ResultDto<String> saveCwarehouseConfig(CwarehouseInfo record);
	
	public ResultDto<PageBean<CwarehouseInfo>> getCwarehouseConfigPage(CwarehouseParamDto param);
	
	public CwarehouseInfo selectByPrimaryKey(Integer id);
	
	public ResultDto<String> updateCwarehouseConfig(CwarehouseInfo record);
	
	public ResultDto<String> delCwarehouseInfoByIds(CwarehouseInfo record, String ids);
	
	public ResultDto<List<CwarehouseInfo>> getAllCwarehouseConfigPage(CwarehouseParamDto param);
	
	public ResultDto<List<CwarehouseInfo>> getCwarehouseConfigListByUid(Integer uid);
	
	public ResultDto<String> updateCwarehouseCapacity(Integer id,String capacity);
	
	public ResultDto<String> isCwarehouseManager(Integer uid);
	
	
}
