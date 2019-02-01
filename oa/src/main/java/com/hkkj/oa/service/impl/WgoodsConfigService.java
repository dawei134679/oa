package com.hkkj.oa.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.WgoodsInfoMapper;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WgoodsParamDto;
import com.hkkj.oa.entity.WgoodsInfo;
import com.hkkj.oa.service.IWgoodsConfigService;

@Service
@Transactional
public class WgoodsConfigService implements IWgoodsConfigService{
	
	@Autowired
	private WgoodsInfoMapper wgoodsInfoMapper;

	@Override
	public ResultDto<String> saveWgoodsConfig(WgoodsInfo record) {
		int result = wgoodsInfoMapper.insertSelective(record);
		if (result != 1) {
			return ResultUtil.fail("添加失败");
		}else {
			return ResultUtil.success("添加成功");
		}
	}

	@Override
	public ResultDto<PageBean<WgoodsInfo>> getWgoodsConfigPage(WgoodsParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<WgoodsInfo> list = wgoodsInfoMapper.getWgoodsConfigList(param);
		return ResultUtil.success(new PageBean<WgoodsInfo>(list));
	}

	@Override
	public WgoodsInfo selectByPrimaryKey(Integer id) {
		return wgoodsInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultDto<String> updateWgoodsConfig(WgoodsInfo record) {
		int result = wgoodsInfoMapper.updateByPrimaryKeySelective(record);
		if (result != 1) {
			return ResultUtil.fail("修改失败");
		}else {
			return ResultUtil.success("修改成功");
		}
	}

	@Override
	public ResultDto<String> delWgoodsInfoByIds(WgoodsInfo record, String ids) {
		int result = wgoodsInfoMapper.delWgoodsInfoByIds(record,ids.split(","));
		if (result == 0) {
			return ResultUtil.fail("删除失败");
		}else {
			return ResultUtil.success("删除成功");
		}
	}

	@Override
	public ResultDto<List<WgoodsInfo>> getAllWgoodsConfigPage(WgoodsParamDto param) {
		List<WgoodsInfo> list = wgoodsInfoMapper.getWgoodsConfigList(param);
		return ResultUtil.success(list);
	}

}
