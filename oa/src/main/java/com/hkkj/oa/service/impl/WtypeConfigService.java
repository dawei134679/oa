package com.hkkj.oa.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.WtypeInfoMapper;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WtypeParamDto;
import com.hkkj.oa.entity.WtypeInfo;
import com.hkkj.oa.service.IWtypeConfigService;

@Service
@Transactional
public class WtypeConfigService implements IWtypeConfigService{

	@Autowired
	private WtypeInfoMapper wtypeInfoMapper;
	
	@Override
	public ResultDto<String> saveWtypeConfig(WtypeInfo record) {
		int result = wtypeInfoMapper.insertSelective(record);
		if (result != 1) {
			return ResultUtil.fail("添加失败");
		}else {
			return ResultUtil.success("添加成功");
		}
	}

	@Override
	public ResultDto<PageBean<WtypeInfo>> getWtypeConfigPage(WtypeParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<WtypeInfo> list = wtypeInfoMapper.getWtypeConfigList(param);
		return ResultUtil.success(new PageBean<WtypeInfo>(list));
		
	}

	@Override
	public WtypeInfo selectByPrimaryKey(Integer id) {
		return wtypeInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultDto<String> updateWtypeConfig(WtypeInfo record) {
		int result = wtypeInfoMapper.updateByPrimaryKeySelective(record);
		if (result != 1) {
			return ResultUtil.fail("修改失败");
		}else {
			return ResultUtil.success("修改成功");
		}
	}

	@Override
	public ResultDto<String> delWtypeInfoByIds(WtypeInfo record, String ids) {
		int result = wtypeInfoMapper.delWtypeInfoByIds(record,ids.split(","));
		if (result == 0) {
			return ResultUtil.fail("删除失败");
		}else {
			return ResultUtil.success("删除成功");
		}
	}

	@Override
	public ResultDto<List<WtypeInfo>> getAllWtypeConfigPage(WtypeParamDto param) {
		List<WtypeInfo> list = wtypeInfoMapper.getWtypeConfigList(param);
		return ResultUtil.success(list);
	}

}
