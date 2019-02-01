package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.dto.UserParamDto;
import com.hkkj.oa.dto.UserTreeNodeDto;
import com.hkkj.oa.entity.User;

public interface IUserService {
	
	 /**
	  * @param userName 用户名
	  * @param password 密码
	  * @return 用户基本信息
	  */
	public ResultDto<UserInfoDto> getUserByUserCodeAndPwd(String userCode,String password);

	public UserInfoDto getUserByUserCode(String userCode);

	public ResultDto<PageBean<UserInfoDto>> getUserInfoPage(UserParamDto param);

	public ResultDto<Object> modifyUserById(User param);

	public ResultDto<Object> saveUser(User param);
	
	public User selectByPrimaryKey(Integer id);
	
	public ResultDto<String> delUserByIds(User record, String ids);
	
	public ResultDto<List<UserTreeNodeDto>> getUserInfoPageByOrgId(UserParamDto param);

	public ResultDto<List<UserTreeNodeDto>> getALlUserInfoList();
	
	public User getUserById(Integer id);
}