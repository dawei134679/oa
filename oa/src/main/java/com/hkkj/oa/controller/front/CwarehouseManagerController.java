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
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.entity.CwarehouseInfo;
import com.hkkj.oa.service.ICwarehouseConfigService;

@Controller
@RequestMapping("/front/chManager")
public class CwarehouseManagerController {
private static final Logger log = LogManager.getLogger(CwarehouseManagerController.class);
	

	@Autowired
	private ICwarehouseConfigService cwarehouseConfigService;
	
	@SystemLog(module="仓库容量模块",remark="判断是否为仓库管理员")
	@RequestMapping(value="/isCwarehouseManager",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> isCwarehouseManager(HttpServletRequest request){
		try {
			UserInfoDto userInfoDto = UserUtil.getSessionUser(request);
			Integer uid = userInfoDto.getId();
			return cwarehouseConfigService.isCwarehouseManager(uid);
		} catch (Exception e) {
			log.error("判断是否为仓库管理员异常",e);
			return ResultUtil.error("判断是否为仓库管理员异常");
		}
	}
	
	@SystemLog(module="仓库容量模块",remark="获取仓库剩余容量列表")
	@RequestMapping(value="/getCwarehouseCapacityById",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<List<CwarehouseInfo>> getCwarehouseCapacityById(HttpServletRequest request){
		try {
			UserInfoDto userInfoDto = UserUtil.getSessionUser(request);
			Integer uid = userInfoDto.getId();
			return cwarehouseConfigService.getCwarehouseConfigListByUid(uid);
		} catch (Exception e) {
			log.error("获取仓库剩余容量异常",e);
			return ResultUtil.error("获取仓库剩余容量失败");
		}
	}
	
	@SystemLog(module="仓库容量模块",remark="修改仓库剩余容量")
	@RequestMapping(value="/updateCwarehouseCapacity",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResultDto<String> updateCwarehouseCapacity(Integer id,String capacity, HttpServletRequest request){
		try {
			return cwarehouseConfigService.updateCwarehouseCapacity(id,capacity);
		} catch (Exception e) {
			log.error("修改仓库剩余容量异常",e);
			return ResultUtil.error("修改仓库剩余容量失败");
		}
	}
}
