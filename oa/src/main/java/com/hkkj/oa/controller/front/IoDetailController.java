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

import com.alibaba.fastjson.JSONArray;
import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dto.IoDetailParamDto;
import com.hkkj.oa.dto.IoSubdetailParamDto;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.IoDetailInfo;
import com.hkkj.oa.entity.IoSubdetailInfo;
import com.hkkj.oa.entity.SubDetail;
import com.hkkj.oa.service.IIoDetailService;
import com.hkkj.oa.service.IIoSubdetailService;

@Controller
@RequestMapping("/front/iodetail")
public class IoDetailController {
	
	private static final Logger log = LogManager.getLogger(IoDetailController.class);

	@Autowired
	private IIoDetailService iIoDetailService;

	@Autowired
	private IIoSubdetailService iIoSubdetailService;

	@SystemLog(module = "进出库明细模块", remark = "新增进出库明细")
	@RequestMapping(value = "/saveIoDetail", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<String> saveIoDetail(IoDetailInfo record, HttpServletRequest request) {
		try {
			List<IoSubdetailInfo> subDetails = JSONArray.parseArray(record.getSubDetails(), IoSubdetailInfo.class);
			return iIoDetailService.saveAllDetail(record, subDetails);
		} catch (Exception e) {
			log.error("新增进出库明细异常",e);
			return ResultUtil.error("新增进出库明细失败");
		}
	}

	@SystemLog(module = "进出库明细模块", remark = "获取进出库明细主列表")
	@RequestMapping(value = "/getIoDetailPage", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<PageBean<IoDetailInfo>> getIoDetailPage(IoDetailParamDto param, HttpServletRequest request) {
		try {
			return iIoDetailService.getIoDetailPage(param);
		} catch (Exception e) {
			log.error("获取进出库明细主列表异常",e);
			return ResultUtil.error("新获取进出库明细主列表失败");
		}
	}
	
	@SystemLog(module = "进出库明细模块", remark = "获取进出库明细子列表")
	@RequestMapping(value = "/getIoSubDetailPage", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<PageBean<SubDetail>> getIoSubDetailPage(IoSubdetailParamDto param, HttpServletRequest request) {
		try {
			return iIoSubdetailService.getIoSubDetailPage(param);
		} catch (Exception e) {
			log.error("获取进出库明细主列表异常",e);
			return ResultUtil.error("获取进出库明细主列表失败");
		}
	}
}
