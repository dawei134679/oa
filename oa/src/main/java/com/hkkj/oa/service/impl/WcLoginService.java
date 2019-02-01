package com.hkkj.oa.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkkj.oa.common.utils.AESCipher;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.WechatUserMapper;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.WechatUser;
import com.hkkj.oa.service.IWcLoginService;

@Service
@Transactional
public class WcLoginService implements IWcLoginService{
	
	@Autowired
	private WechatUserMapper wechatUserMapper;

	@Override
	public ResultDto<Map<String, Object>> findUserByOpenId(String openid) {
		WechatUser  wechatUser= wechatUserMapper.findUserByOpenId(openid);
		Map<String, Object> result = new HashMap<>();
		if (null != wechatUser) {
			String token = AESCipher.aesEncryptString(wechatUser.getUserCode());
			result.put("message", "登陆成功");
			result.put("token", token);
			return ResultUtil.success(result);
		}else {
//			result.put("message", "登陆失败");
			return ResultUtil.fail();
		}
	}
	

	@Override
	public int insertSelective(WechatUser record) {
		return wechatUserMapper.insertSelective(record);
	}

	@Override
	public int updateSelective(WechatUser wechatUser) {
		return wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
	}


	@Override
	public WechatUser getWechatUserByUserCode(String userCode) {
		return wechatUserMapper.getWechatUserByUserCode(userCode);
	}


	@Override
	public WechatUser getWechatUserByOpenId(String openid) {
		return wechatUserMapper.getWechatUserByOpenId(openid);
	}


	@Override
	public int delWechatUserByUserCode(String userCode) {
		return wechatUserMapper.delWechatUserByUserCode(userCode);
	}


	@Override
	public int delWechatUserByOpenid(String openid) {
		return wechatUserMapper.delWechatUserByOpenid(openid);
	}


	@Override
	public WechatUser getUserByToken(String token) {
		return wechatUserMapper.getUserByToken(token);
	}

}
