package com.hkkj.oa.dao;

import java.util.List;

import com.hkkj.oa.dto.IoDetailParamDto;
import com.hkkj.oa.entity.IoDetailInfo;

public interface IoDetailInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(IoDetailInfo record);

	int insertSelective(IoDetailInfo record);

	IoDetailInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(IoDetailInfo record);

	int updateByPrimaryKey(IoDetailInfo record);
	
	List<IoDetailInfo> getIoDetailList(IoDetailParamDto param);
}