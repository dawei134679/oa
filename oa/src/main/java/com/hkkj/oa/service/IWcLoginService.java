package com.hkkj.oa.service;

import java.util.Map;

import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.WechatUser;

public interface IWcLoginService {
	
	public ResultDto<Map<String, Object>> findUserByOpenId(String openid);
	
	int insertSelective(WechatUser record);

	public int updateSelective(WechatUser wechatUser);

	public WechatUser getWechatUserByUserCode(String userCode);
	
	public WechatUser getWechatUserByOpenId(String openid);

	public int delWechatUserByUserCode(String userCode);

	public int delWechatUserByOpenid(String openid);

	public WechatUser getUserByToken(String token);
}
