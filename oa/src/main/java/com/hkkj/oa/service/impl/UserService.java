package com.hkkj.oa.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.UserMapper;
import com.hkkj.oa.dto.PageBean;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.dto.UserParamDto;
import com.hkkj.oa.dto.UserTreeNodeDto;
import com.hkkj.oa.entity.User;
import com.hkkj.oa.service.IUserService;

@Service
@Transactional
public class UserService implements IUserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public ResultDto<UserInfoDto> getUserByUserCodeAndPwd(String userCode, String password) {
		if(StringUtils.isEmpty(userCode)) {
			return ResultUtil.fail("请输入用户名");
		}
		if(StringUtils.isEmpty(password)) {
			return ResultUtil.fail("请输入密码");
		}
		User user = userMapper.getUserByUserCode(userCode);
		if(user==null) {
			return ResultUtil.fail("用户不存在");
		}
		if(!password.equalsIgnoreCase(user.getPassword())) {
			return ResultUtil.fail("用户名或密码错误");
		}
		UserInfoDto userInfoDto = userMapper.getUserInfoByUserCode(userCode);
		return ResultUtil.success(userInfoDto);
	}

	@Override
	public UserInfoDto getUserByUserCode(String userCode) {
		return userMapper.getUserInfoByUserCode(userCode);
	}
	
	@Override
	public ResultDto<PageBean<UserInfoDto>> getUserInfoPage(UserParamDto param) {
		Page<Object> page = PageHelper.startPage(param.getPage(), param.getPageSize());
		if(!StringUtils.isBlank(param.getOrderBy())) {
			page.setOrderBy(param.getOrderBy()+ " " +param.getSort());
		}
		List<UserInfoDto> list = userMapper.getUserInfoList(param);
		return ResultUtil.success(new PageBean<UserInfoDto>(list));
	}

	@Override
	public ResultDto<Object> modifyUserById(User param) {
		int i = userMapper.updateByPrimaryKeySelective(param);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public ResultDto<Object> saveUser(User param) {
		int i = userMapper.insertSelective(param);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultDto<String> delUserByIds(User record, String ids) {
		int result = userMapper.delUserInfoByIds(record,ids.split(","));
		if (result == 0) {
			return ResultUtil.fail("删除失败");
		}else {
			return ResultUtil.success("删除成功");
		}
	}

	@Override
	public ResultDto<List<UserTreeNodeDto>> getUserInfoPageByOrgId(UserParamDto param) {
		List<UserTreeNodeDto> list = userMapper.getUserInfoPageByOrgId(param);
		return ResultUtil.success(list);
	}

	@Override
	public ResultDto<List<UserTreeNodeDto>> getALlUserInfoList() {
		List<UserTreeNodeDto> list = userMapper.getALlUserInfoList();
		return ResultUtil.success(list);
	}

	@Override
	public User getUserById(Integer id) {
		return userMapper.getUserById(id);
	}
}
