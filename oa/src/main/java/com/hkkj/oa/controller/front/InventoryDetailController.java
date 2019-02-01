package com.hkkj.oa.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dto.InventoryDetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.InventoryDetail;
import com.hkkj.oa.service.IInventoryDetailService;

@Controller
@RequestMapping("/front/inventoryDetail")
public class InventoryDetailController {
	
	private static final Logger log = LogManager.getLogger(InventoryDetailController.class);
	
	@Autowired
	private IInventoryDetailService iInventoryDetailService;
	
	@SystemLog(module="库存明细模块",remark="获取库存明细列表")
	@RequestMapping(value="/getInventoryDetailPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<PageBean<InventoryDetail>> getInventoryDetailPage(InventoryDetailParamDto param, HttpServletRequest request){
		try {
			return iInventoryDetailService.getInventoryDetailPage(param);
		} catch (Exception e) {
			log.error("获取库存明细列表异常",e);
			return ResultUtil.error("获取库存明细列表失败");
		}
	}
} 
