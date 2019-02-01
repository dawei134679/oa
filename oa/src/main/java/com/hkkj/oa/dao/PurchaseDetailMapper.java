package com.hkkj.oa.dao;

import java.util.List;

import com.hkkj.oa.entity.PurchaseDetail;

public interface PurchaseDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseDetail record);

    int insertSelective(PurchaseDetail record);

    PurchaseDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseDetail record);

    int updateByPrimaryKey(PurchaseDetail record);

	List<PurchaseDetail> listDetailByPurchaseId(Long purchaseId);
}