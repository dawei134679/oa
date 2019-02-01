package com.hkkj.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkkj.oa.common.utils.ResultUtil;
import com.hkkj.oa.dao.OrganizationMapper;
import com.hkkj.oa.dto.OrganizationTreeNodeDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.Organization;
import com.hkkj.oa.service.IOrganizationService;

@Service
@Transactional
public class OrganizationService implements IOrganizationService {

	@Autowired
	private OrganizationMapper organizationDao;
	
	@Override
	public ResultDto<List<OrganizationTreeNodeDto>> getOrganizationTreeList() {
		List<Organization> list = organizationDao.getOrganizationList();
		List<OrganizationTreeNodeDto> orgList  = new ArrayList<OrganizationTreeNodeDto>();
		for (Organization organization : list) {
			if(organization.getParentId()==0) {
				OrganizationTreeNodeDto organizationTreeNodeDto = new OrganizationTreeNodeDto();
				organizationTreeNodeDto.setId(organization.getId());
				organizationTreeNodeDto.setOrgCode(organization.getOrgCode());
				organizationTreeNodeDto.setOrgName(organization.getOrgName());
				organizationTreeNodeDto.setOrgType(organization.getOrgType());
				organizationTreeNodeDto.setSort(organization.getSort());
				organizationTreeNodeDto.setRemarks(organization.getRemarks());
				organizationTreeNodeDto.setParentId(organization.getParentId());
				organizationTreeNodeDto.setParentCode(organization.getParentCode());
				organizationTreeNodeDto.setLeaderId(organization.getLeaderId());
				orgList.add(organizationTreeNodeDto);
			}
		}
	    for (OrganizationTreeNodeDto bean : orgList) {
	    	bean.setChildNode(getChildNode(bean.getId(),list));
	    }
		return ResultUtil.success(orgList);
	}
	
	private List<OrganizationTreeNodeDto> getChildNode(int pid ,List<Organization> list){
	    List<OrganizationTreeNodeDto> childList = new ArrayList<OrganizationTreeNodeDto>();
	    for (Organization organization : list) {
	    	 if (organization.getParentId()==pid) {
    		   OrganizationTreeNodeDto organizationTreeNodeDto = new OrganizationTreeNodeDto();
				organizationTreeNodeDto.setId(organization.getId());
				organizationTreeNodeDto.setOrgCode(organization.getOrgCode());
				organizationTreeNodeDto.setOrgName(organization.getOrgName());
				organizationTreeNodeDto.setOrgType(organization.getOrgType());
				organizationTreeNodeDto.setSort(organization.getSort());
				organizationTreeNodeDto.setRemarks(organization.getRemarks());
				organizationTreeNodeDto.setParentId(organization.getParentId());
				organizationTreeNodeDto.setParentCode(organization.getParentCode());
				organizationTreeNodeDto.setLeaderId(organization.getLeaderId());
                childList.add(organizationTreeNodeDto);
	         }
	    }
	    for (OrganizationTreeNodeDto bean : childList) {
	    	bean.setChildNode(getChildNode(bean.getId(), list));
	    }
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}
	
	@Override
	public ResultDto<Organization> saveProjectInfo(Organization param) {
		param.setStatus(1);
		int i = organizationDao.insertSelective(param);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success(param);
	}

	@Override
	public ResultDto<Object> delProjectInfoById(Organization param) {
		param.setStatus(0);
		int i = organizationDao.updateByPrimaryKeySelective(param);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public ResultDto<Object> updateOrganizationById(Organization param) {
		int i = organizationDao.updateByPrimaryKeySelective(param);
		if(i==0) {
			return ResultUtil.fail();
		}
		return ResultUtil.success();
	}

	@Override
	public Organization getOrganizationById(Integer id) {
		return organizationDao.selectByPrimaryKey(id);
	}
}