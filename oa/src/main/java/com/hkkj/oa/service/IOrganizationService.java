package com.hkkj.oa.service;

import java.util.List;

import com.hkkj.oa.dto.OrganizationTreeNodeDto;
import com.hkkj.oa.dto.ResultDto;
import com.hkkj.oa.entity.Organization;

public interface IOrganizationService {

	public ResultDto<List<OrganizationTreeNodeDto>> getOrganizationTreeList();

	public ResultDto<Organization> saveProjectInfo(Organization param);
	
	public ResultDto<Object> delProjectInfoById(Organization param);

	public ResultDto<Object> updateOrganizationById(Organization param);

	public Organization getOrganizationById(Integer id);
}
