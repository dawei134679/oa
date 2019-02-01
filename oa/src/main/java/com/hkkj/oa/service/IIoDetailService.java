package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.IoDetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.IoDetailInfo;
import com.hkkj.oa.entity.IoSubdetailInfo;

public interface IIoDetailService {
	
	public ResultDto<PageBean<IoDetailInfo>> getIoDetailPage(IoDetailParamDto param);
	
	public ResultDto<String> saveAllDetail(IoDetailInfo record,List<IoSubdetailInfo> subDetails);
}
