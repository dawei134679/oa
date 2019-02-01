package com.hkkj.oa.controller.front;

import java.util.List;

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
import com.hkkj.oa.dto.CwarehouseParamDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.WgoodsParamDto;
import com.hkkj.oa.dto.WtypeParamDto;
import com.hkkj.oa.entity.CwarehouseInfo;
import com.hkkj.oa.entity.WgoodsInfo;
import com.hkkj.oa.entity.WtypeInfo;
import com.hkkj.oa.service.ICwarehouseConfigService;
import com.hkkj.oa.service.IWgoodsConfigService;
import com.hkkj.oa.service.IWtypeConfigService;

@Controller
@RequestMapping("/front/config")
public class IoConfigController {
	
	private static final Logger log = LogManager.getLogger(IoConfigController.class);

	
	@Autowired
	private IWtypeConfigService wtypeConfigService;
	
	@Autowired
	private IWgoodsConfigService wgoodsConfigService;
	
	@Autowired
	private ICwarehouseConfigService cwarehouseConfigService;
	
	@SystemLog(module="前端配置模块",remark="前端获取物种列表")
	@RequestMapping(value="/getAllWtypeConfigPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<List<WtypeInfo>> getWtypeAllConfigPage(WtypeParamDto param, HttpServletRequest request){
		try {
			return wtypeConfigService.getAllWtypeConfigPage(param);
		} catch (Exception e) {
			log.error("获取物种列表异常",e);
			return ResultUtil.error("获取物种列表失败");
		}
		
	}
	
	@SystemLog(module="前端配置模块",remark="前端获取物品列表")
	@RequestMapping(value="/getAllWgoodsConfigPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<List<WgoodsInfo>> getwgoodsConfigPage(WgoodsParamDto param, HttpServletRequest request){
		try {
			return wgoodsConfigService.getAllWgoodsConfigPage(param);
		} catch (Exception e) {
			log.error("获取物品列表异常",e);
			return ResultUtil.error("获取物品列表失败");
		}
	}
	
	@SystemLog(module="前端配置模块",remark="前端获取仓库列表")
	@RequestMapping(value="/getwarehouseConfigPage",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<List<CwarehouseInfo>> getwarehouseConfigPage(CwarehouseParamDto param, HttpServletRequest request){
		try {
			return cwarehouseConfigService.getAllCwarehouseConfigPage(param);
		} catch (Exception e) {
			log.error("获取仓库列表异常",e);
			return ResultUtil.error("获取仓库列表失败");
		}
	}
}
