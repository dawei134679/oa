package com.hkkj.oa.service;

import com.hkkj.oa.dto.IoSubdetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.IoSubdetailInfo;
import com.hkkj.oa.entity.SubDetail;

public interface IIoSubdetailService {
	
	int insertSelective(IoSubdetailInfo record);
	
	public ResultDto<PageBean<SubDetail>> getIoSubDetailPage(IoSubdetailParamDto param);
}
