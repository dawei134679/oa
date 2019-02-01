package com.hkkj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.dto.WgoodsParamDto;
import com.hkkj.oa.entity.WgoodsInfo;

public interface WgoodsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WgoodsInfo record);

    int insertSelective(WgoodsInfo record);

    WgoodsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WgoodsInfo record);

    int updateByPrimaryKey(WgoodsInfo record);
    
    List<WgoodsInfo> getWgoodsConfigList(WgoodsParamDto param);
    
    int delWgoodsInfoByIds(@Param("record") WgoodsInfo record, @Param("ids") String[] ids);
}