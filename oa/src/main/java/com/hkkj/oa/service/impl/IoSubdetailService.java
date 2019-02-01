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
import com.hkkj.oa.dao.IoSubdetailInfoMapper;
import com.hkkj.oa.dto.IoSubdetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.IoSubdetailInfo;
import com.hkkj.oa.entity.SubDetail;
import com.hkkj.oa.service.IIoSubdetailService;

@Service
@Transactional
public class IoSubdetailService implements IIoSubdetailService {

	@Autowired
	private IoSubdetailInfoMapper ioSubdetailInfoMapper;

	@Override
	public int insertSelective(IoSubdetailInfo record) {
		return ioSubdetailInfoMapper.insertSelective(record);
	}

	@Override
	public ResultDto<PageBean<SubDetail>> getIoSubDetailPage(IoSubdetailParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<SubDetail> list = ioSubdetailInfoMapper.getIoSubDetailPage(param);
		return ResultUtil.success(new PageBean<SubDetail>(list));
	}
}
