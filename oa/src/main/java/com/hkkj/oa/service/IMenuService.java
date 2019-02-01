package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.MenuTreeNodeDto;
import com.hkkj.oa.dto.ResultDto;

public interface IMenuService {
	public ResultDto<List<MenuTreeNodeDto>> getMenuByUserId(Integer userId);
}
