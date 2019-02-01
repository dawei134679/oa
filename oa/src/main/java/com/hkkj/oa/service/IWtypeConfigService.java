package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WtypeParamDto;
import com.hkkj.oa.entity.WtypeInfo;

public interface IWtypeConfigService {
	
	public ResultDto<String> saveWtypeConfig(WtypeInfo record);
	
	public ResultDto<PageBean<WtypeInfo>> getWtypeConfigPage(WtypeParamDto param);
	
	public WtypeInfo selectByPrimaryKey(Integer id);
	
	public ResultDto<String> updateWtypeConfig(WtypeInfo record);
	
	public ResultDto<String> delWtypeInfoByIds(WtypeInfo record, String ids);
	
	public ResultDto<List<WtypeInfo>> getAllWtypeConfigPage(WtypeParamDto param);
	
}
