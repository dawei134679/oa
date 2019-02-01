package com.hkkj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.dto.CwarehouseParamDto;
import com.hkkj.oa.entity.CwarehouseInfo;

public interface CwarehouseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CwarehouseInfo record);

    int insertSelective(CwarehouseInfo record);

    CwarehouseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CwarehouseInfo record);

    int updateByPrimaryKey(CwarehouseInfo record);
    
    List<CwarehouseInfo> getCwarehouseConfigList(CwarehouseParamDto param);
    
    int delCwarehouseInfoByIds(@Param("record") CwarehouseInfo record, @Param("ids") String[] ids);

	int updateCwarehouseCapacity(@Param("id") Integer id, @Param("capacity") String capacity);

	int isCwarehouseManager(Integer uid);
	
	List<CwarehouseInfo> getCwarehouseConfigListByUid(Integer uid);
}