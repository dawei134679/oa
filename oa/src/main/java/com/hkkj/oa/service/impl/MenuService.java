package com.hkkj.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.MenuMapper;
import com.hkkj.oa.dto.MenuTreeNodeDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.Menu;
import com.hkkj.oa.service.IMenuService;

@Service
@Transactional
public class MenuService implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public  ResultDto<List<MenuTreeNodeDto>> getMenuByUserId(Integer userId) {
		if(userId==null||userId==0) {
			ResultUtil.success(new ArrayList<MenuTreeNodeDto>());
		}
		List<Menu> list = menuMapper.getMenuByUserId(userId);
		List<MenuTreeNodeDto> menuList  = new ArrayList<MenuTreeNodeDto>();
		for (Menu menu : list) {
			if(menu.getPid()==0) {
				MenuTreeNodeDto menuTreeNodeDto = new MenuTreeNodeDto();
				menuTreeNodeDto.setId(menu.getId());
				menuTreeNodeDto.setName(menu.getName());
				menuTreeNodeDto.setUrl(menu.getUrl());
				menuTreeNodeDto.setType(menu.getType());
				menuTreeNodeDto.setSort(menu.getSort());
				menuTreeNodeDto.setIcon(menu.getIcon());
				menuList.add(menuTreeNodeDto);
			}
		}
	    for (MenuTreeNodeDto bean : menuList) {
	    	bean.setChildNode(getChildNode(bean.getId(),list));
	    }
		return ResultUtil.success(menuList);
	}
	
	
	private List<MenuTreeNodeDto> getChildNode(int pid ,List<Menu> list){
	    List<MenuTreeNodeDto> childList = new ArrayList<MenuTreeNodeDto>();
	    for (Menu menu : list) {
	    	 if (menu.getPid()==pid) {
	    		MenuTreeNodeDto menuTreeNodeDto = new MenuTreeNodeDto();
				menuTreeNodeDto.setId(menu.getId());
				menuTreeNodeDto.setName(menu.getName());
				menuTreeNodeDto.setUrl(menu.getUrl());
				menuTreeNodeDto.setType(menu.getType());
				menuTreeNodeDto.setSort(menu.getSort());
				menuTreeNodeDto.setIcon(menu.getIcon());
                childList.add(menuTreeNodeDto);
	         }
	    }
	    for (MenuTreeNodeDto bean : childList) {
	    	bean.setChildNode(getChildNode(bean.getId(), list));
	    }
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}
}
