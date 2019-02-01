package com.hkkj.oa.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hkkj.oa.common.annotation.SystemLog;
import com.hkkj.oa.common.utils.HMap;
import com.hkkj.oa.common.utils.JsonUtil;
import com.hkkj.oa.common.utils.LogUtils;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.common.utils.UserUtil;
import com.hkkj.oa.dto.PurchaseDetailParamDto;
import com.hkkj.oa.dto.PurchaseParamDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.service.IPurchaseService;

@Controller
@RequestMapping("/front/purchase")
public class PurchaseController {

	@Autowired
	private IPurchaseService purchaseService;

	@SystemLog(module = "采购模块", remark = "发起采购流程")
	@RequestMapping(value = "/initiationPurchaseProcess", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<Object> initiationPurchaseProcess(String purchaseStr,String subDetails, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(purchaseStr)) {
				return ResultUtil.fail("参数不能为空");
			}
			PurchaseParamDto param = JsonUtil.toBean(purchaseStr, PurchaseParamDto.class);
			List<PurchaseDetailParamDto> purchaseDetailList = JsonUtil.toListBean(subDetails, PurchaseDetailParamDto.class);
			param.setPurchaseDetailList(purchaseDetailList);
			UserInfoDto userInfoDto = UserUtil.getSessionUser(request);
			param.setCreateUserId(userInfoDto.getId());
			return purchaseService.initiationPurchaseProcess(param);
		} catch (Exception e) {
			LogUtils.error(getClass(), "发起采购流程异常", e);
			return ResultUtil.error("发起采购流程失败");
		}
	}
	
	@SystemLog(module = "采购模块", remark = "获取采购流程详细信息")
	@RequestMapping(value = "/getPurchaseProcessInfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResultDto<HMap> getPurchaseProcessInfo(HttpServletRequest request,String applyCode) {
		try {
			return purchaseService.getPurchaseProcessInfo(applyCode);
		} catch (Exception e) {
			LogUtils.error(getClass(), "获取采购流程异常", e);
			return ResultUtil.error("获取采购流程失败");
		}
	}
}
