package com.hkkj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.dto.IoSubdetailParamDto;
import com.hkkj.oa.entity.IoSubdetailInfo;
import com.hkkj.oa.entity.SubDetail;

public interface IoSubdetailInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IoSubdetailInfo record);

    int insertSelective(IoSubdetailInfo record);

    IoSubdetailInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IoSubdetailInfo record);

    int updateByPrimaryKey(IoSubdetailInfo record);
    
    int saveSubdetailList(@Param("list") List<IoSubdetailInfo> subDetails);
    
    List<SubDetail> getIoSubDetailPage(IoSubdetailParamDto param);
}