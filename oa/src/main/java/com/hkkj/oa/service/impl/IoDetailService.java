package com.hkkj.oa.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.IoDetailInfoMapper;
import com.hkkj.oa.dto.InventoryDetailParamDto;
import com.hkkj.oa.dto.IoDetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.InventoryDetail;
import com.hkkj.oa.entity.IoDetailInfo;
import com.hkkj.oa.entity.IoSubdetailInfo;
import com.hkkj.oa.service.IInventoryDetailService;
import com.hkkj.oa.service.IIoDetailService;
import com.hkkj.oa.service.IIoSubdetailService;

@Service
@Transactional
public class IoDetailService implements IIoDetailService {

	@Autowired
	private IoDetailInfoMapper ioDetailInfoMapper;

	@Autowired
	private IInventoryDetailService iInventoryDetailService;

	@Autowired
	private IIoSubdetailService iIoSubdetailService;

	@Override
	public ResultDto<PageBean<IoDetailInfo>> getIoDetailPage(IoDetailParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if (!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy() + " " + param.getSort());
		}
		List<IoDetailInfo> list = ioDetailInfoMapper.getIoDetailList(param);
		return ResultUtil.success(new PageBean<IoDetailInfo>(list));
	}

	@Override
	public ResultDto<String> saveAllDetail(IoDetailInfo record, List<IoSubdetailInfo> subDetails) {
		// 生成明细订单号
		String detailOrder = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(100);
		// 库存对象
		InventoryDetail inventoryDetail = new InventoryDetail();
		// 查找库存参数
		InventoryDetailParamDto inventoryDetailParamDto = new InventoryDetailParamDto();
		int resultCode = 0;
		// 获取进出库类型 1:进库/2:出库
		Integer type = record.getType();

		for (IoSubdetailInfo ioSubdetailInfo : subDetails) {
			// 查该仓库的物品是否存在
			inventoryDetailParamDto.setWarehouseName(ioSubdetailInfo.getWarehouseName());
			inventoryDetailParamDto.setGoodsCode(ioSubdetailInfo.getGoodsCode());

			InventoryDetail resultGoods = iInventoryDetailService.isExistGoods(inventoryDetailParamDto);
			// 库存对象赋值
			inventoryDetail.setGoodsCode(ioSubdetailInfo.getGoodsCode());
			inventoryDetail.setGoodsAmount(ioSubdetailInfo.getGoodsAmount());
			inventoryDetail.setWarehouseName(ioSubdetailInfo.getWarehouseName());
			// 物品存在
			if (null != resultGoods) {
				// 出库
				if (type == 2) {
					int zamount = ioSubdetailInfo.getGoodsAmount();
					int aamount = resultGoods.getGoodsAmount();
					if (zamount > aamount) {
						// 库存不足
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
						return ResultUtil.fail(ioSubdetailInfo.getGoodsCode() + "库存不足,明细提交失败");
					} else {
						// 库存充足
						inventoryDetail.setGoodsAmount(-ioSubdetailInfo.getGoodsAmount());
						// 修改库存
						resultCode = iInventoryDetailService.updateInventoryDetail(inventoryDetail);
						if (resultCode != 1) {
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
							return ResultUtil.fail("明细提交失败");
						}
					}
				} else {
					// 进库直接修改
					resultCode = iInventoryDetailService.updateInventoryDetail(inventoryDetail);
					if (resultCode != 1) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
						return ResultUtil.fail("明细提交失败");
					}
				}
			} else {
				// 没有库存出库直接失败
				if (type == 2) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
					return ResultUtil.fail("没有库存,出库失败");
				}
				// 对应库物品不存在直接保存
				resultCode = iInventoryDetailService.saveInventoryDetail(inventoryDetail);
				if (resultCode != 1) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
					return ResultUtil.fail("明细提交失败");
				}
			}
			// 给子明细添加订单号
			ioSubdetailInfo.setDetailOrder(detailOrder);
			// 存子表
			resultCode = iIoSubdetailService.insertSelective(ioSubdetailInfo);
			if (resultCode != 1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
				return ResultUtil.fail("明细提交失败");
			}
		}
		// 存进出库明细主表
		record.setDetailOrder(detailOrder);
		resultCode = ioDetailInfoMapper.insertSelective(record);
		if (resultCode != 1) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动开启事务回滚
			return ResultUtil.fail("添加失败");
		} else {
			return ResultUtil.success("添加成功");
		}
	}
}
