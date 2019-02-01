package com.hkkj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hkkj.oa.dto.UserInfoDto;
import com.hkkj.oa.dto.UserParamDto;
import com.hkkj.oa.dto.UserTreeNodeDto;
import com.hkkj.oa.entity.User;

public interface UserMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User getUserByUserCode(String userCode);
    
    UserInfoDto getUserInfoByUserCode(String userCode);
    
    List<UserInfoDto> getUserInfoList(UserParamDto param);
    
    int delUserInfoByIds(@Param("record") User record, @Param("ids") String[] ids);
    
    List<UserTreeNodeDto> getUserInfoPageByOrgId(UserParamDto param);

	List<UserTreeNodeDto> getALlUserInfoList();

	User getUserById(Integer id);
}