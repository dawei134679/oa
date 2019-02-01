package com.hkkj.oa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.dto.SWhUserParamDto;
import com.hkkj.oa.dto.WarehouseDto;
import com.hkkj.oa.entity.SWarehouseUser;

public interface SWarehouseUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SWarehouseUser record);

    int insertSelective(SWarehouseUser record);

    SWarehouseUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SWarehouseUser record);

    int updateByPrimaryKey(SWarehouseUser record);

	List<Map<String, Object>> getSWhUserPage(SWhUserParamDto param);

	int delSWhUserByIds(@Param("ids")String[] ids);

	int isCwarehouseManager(Integer uid);
	
	List<WarehouseDto> getCwarehouseConfigListByUid(Integer uid);
}