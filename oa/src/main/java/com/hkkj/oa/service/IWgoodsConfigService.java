package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WgoodsParamDto;
import com.hkkj.oa.entity.WgoodsInfo;

public interface IWgoodsConfigService {
	
	public ResultDto<String> saveWgoodsConfig(WgoodsInfo record);
	
	public ResultDto<PageBean<WgoodsInfo>> getWgoodsConfigPage(WgoodsParamDto param);
	
	public WgoodsInfo selectByPrimaryKey(Integer id);
	
	public ResultDto<String> updateWgoodsConfig(WgoodsInfo record);
	
	public ResultDto<String> delWgoodsInfoByIds(WgoodsInfo record, String ids);
	
	public ResultDto<List<WgoodsInfo>> getAllWgoodsConfigPage(WgoodsParamDto param);
	
}
