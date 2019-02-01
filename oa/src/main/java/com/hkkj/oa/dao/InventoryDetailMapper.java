package com.hkkj.oa.dao;

import java.util.List;

import com.hkkj.oa.dto.InventoryDetailParamDto;
import com.hkkj.oa.entity.InventoryDetail;

public interface InventoryDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InventoryDetail record);

    int insertSelective(InventoryDetail record);
    
    InventoryDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InventoryDetail record);

    int updateByPrimaryKey(InventoryDetail record);
    
    InventoryDetail isExistGoods(InventoryDetailParamDto param);
    
    int updateInventoryDetail(InventoryDetail record);
    
    List<InventoryDetail> getInventoryDetailPage(InventoryDetailParamDto param);
}