package com.hkkj.oa.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.CwarehouseInfoMapper;
import com.hkkj.oa.dto.CwarehouseParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.CwarehouseInfo;
import com.hkkj.oa.service.ICwarehouseConfigService;

@Service
@Transactional
public class CwarehouseConfigService implements ICwarehouseConfigService {

	@Autowired
	private CwarehouseInfoMapper cwarehouseInfoMapper;
	
	@Override
	public ResultDto<String> saveCwarehouseConfig(CwarehouseInfo record) {
		int result = cwarehouseInfoMapper.insertSelective(record);
		if (result != 1) {
			return ResultUtil.fail("添加失败");
		}else {
			return ResultUtil.success("添加成功");
		}
	}

	@Override
	public ResultDto<PageBean<CwarehouseInfo>> getCwarehouseConfigPage(CwarehouseParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<CwarehouseInfo> list = cwarehouseInfoMapper.getCwarehouseConfigList(param);
		return ResultUtil.success(new PageBean<CwarehouseInfo>(list));
	}

	@Override
	public CwarehouseInfo selectByPrimaryKey(Integer id) {
		return cwarehouseInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultDto<String> updateCwarehouseConfig(CwarehouseInfo record) {
		int result = cwarehouseInfoMapper.updateByPrimaryKeySelective(record);
		if (result != 1) {
			return ResultUtil.fail("修改失败");
		}else {
			return ResultUtil.success("修改成功");
		}
	}

	@Override
	public ResultDto<String> delCwarehouseInfoByIds(CwarehouseInfo record, String ids) {
		int result = cwarehouseInfoMapper.delCwarehouseInfoByIds(record,ids.split(","));
		if (result == 0) {
			return ResultUtil.fail("删除失败");
		}else {
			return ResultUtil.success("删除成功");
		}
	}

	@Override
	public ResultDto<List<CwarehouseInfo>> getAllCwarehouseConfigPage(CwarehouseParamDto param) {
		List<CwarehouseInfo> list = cwarehouseInfoMapper.getCwarehouseConfigList(param);
		return ResultUtil.success(list);
	}

	@Override
	public ResultDto<List<CwarehouseInfo>> getCwarehouseConfigListByUid(Integer uid) {
		List<CwarehouseInfo> cwarehouseInfoList = cwarehouseInfoMapper.getCwarehouseConfigListByUid(uid);
		return ResultUtil.success(cwarehouseInfoList);
	}

	@Override
	public ResultDto<String> updateCwarehouseCapacity(Integer id, String capacity) {
		int result = cwarehouseInfoMapper.updateCwarehouseCapacity(id,capacity);
		if (result != 1) {
			return ResultUtil.fail("修改库存失败");
		}else {
			return ResultUtil.success("修改库存成功");
		}
	}

	@Override
	public ResultDto<String> isCwarehouseManager(Integer uid) {
		int result = cwarehouseInfoMapper.isCwarehouseManager(uid);
		if (result == 0) {
			return ResultUtil.fail("不是仓库管理员");
		}else {
			return ResultUtil.success("是仓库管理员");
		}
	}

}
