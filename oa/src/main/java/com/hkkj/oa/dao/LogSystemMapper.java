package com.hkkj.oa.dao;

import com.hkkj.oa.entity.LogSystem;

public interface LogSystemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogSystem record);

    int insertSelective(LogSystem record);

    LogSystem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogSystem record);

    int updateByPrimaryKeyWithBLOBs(LogSystem record);

    int updateByPrimaryKey(LogSystem record);
}