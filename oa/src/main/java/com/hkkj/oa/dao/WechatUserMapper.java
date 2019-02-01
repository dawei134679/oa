package com.hkkj.oa.dao;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.entity.WechatUser;

public interface WechatUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    WechatUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);
    
    WechatUser findUserByOpenId(@Param("openId") String openId);

	WechatUser getWechatUserByUserCode(String userCode);
	
	WechatUser getWechatUserByOpenId(String openid);

	int delWechatUserByUserCode(String userCode);

	int delWechatUserByOpenid(String openid);

	WechatUser getUserByToken(String token);
}