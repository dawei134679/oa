package com.hkkj.oa.dao;

import com.hkkj.oa.entity.Purchase;

public interface PurchaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

	Purchase selectByApplyCode(String applyCode);
}