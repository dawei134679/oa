package com.hkkj.oa.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.dto.WtypeParamDto;
import com.hkkj.oa.entity.WtypeInfo;

public interface WtypeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WtypeInfo record);

    int insertSelective(WtypeInfo record);

    WtypeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WtypeInfo record);

    int updateByPrimaryKey(WtypeInfo record);
    
    List<WtypeInfo> getWtypeConfigList(WtypeParamDto param);
    
    int delWtypeInfoByIds(@Param("record") WtypeInfo record, @Param("ids") String[] ids);
    
}