package com.hkkj.oa.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.SWarehouseUserMapper;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.SWhUserParamDto;
import com.hkkj.oa.dto.WarehouseDto;
import com.hkkj.oa.entity.SWarehouseUser;
import com.hkkj.oa.service.ISWarehouseUserService;

@Service
public class SWarehouseUserServiceImpl implements ISWarehouseUserService{
	
	@Autowired
	private SWarehouseUserMapper sWarehouseUserMapper;

	@Override
	public ResultDto<PageBean<Map<String, Object>>> getSWhUserPage(SWhUserParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<Map<String, Object>> list = sWarehouseUserMapper.getSWhUserPage(param);
		return ResultUtil.success(new PageBean<Map<String, Object>>(list));
	}

	@Override
	public ResultDto<String> saveSWhUser(SWarehouseUser record) {
		int result = sWarehouseUserMapper.insertSelective(record);
		if (result != 1) {
			return ResultUtil.fail("添加失败");
		}else {
			return ResultUtil.success("添加成功");
		}
	}

	@Override
	public SWarehouseUser selectByPrimaryKey(int id) {
		return sWarehouseUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultDto<String> updateSWhUser(SWarehouseUser record) {
		int result = sWarehouseUserMapper.updateByPrimaryKeySelective(record);
		if (result != 1) {
			return ResultUtil.fail("修改失败");
		}else {
			return ResultUtil.success("修改成功");
		}
	}

	@Override
	public ResultDto<String> delSWhUserByIds(String ids) {
		int result = sWarehouseUserMapper.delSWhUserByIds(ids.split(","));
		if (result == 0) {
			return ResultUtil.fail("删除失败");
		}else {
			return ResultUtil.success("删除成功");
		}
	}

	@Override
	public ResultDto<String> isCwarehouseManager(Integer uid) {
		int result = sWarehouseUserMapper.isCwarehouseManager(uid);
		if (result == 0) {
			return ResultUtil.fail("不是仓库管理员");
		}else {
			return ResultUtil.success("是仓库管理员");
		}
	}

	@Override
	public ResultDto<List<WarehouseDto>> getCwarehouseConfigListByUid(Integer uid) {
		List<WarehouseDto> list = sWarehouseUserMapper.getCwarehouseConfigListByUid(uid);
		return ResultUtil.success(list);
	}

}
