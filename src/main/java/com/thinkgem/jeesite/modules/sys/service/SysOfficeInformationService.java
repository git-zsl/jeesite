/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysOfficeInformation;
import com.thinkgem.jeesite.modules.sys.dao.SysOfficeInformationDao;

/**
 * 机构详细信息Service
 * @author zsl
 * @version 2019-01-06
 */
@Service
@Transactional(readOnly = true)
public class SysOfficeInformationService extends CrudService<SysOfficeInformationDao, SysOfficeInformation> {

	public SysOfficeInformation get(String id) {
		return super.get(id);
	}
	
	public List<SysOfficeInformation> findList(SysOfficeInformation sysOfficeInformation) {
		return super.findList(sysOfficeInformation);
	}
	
	public Page<SysOfficeInformation> findPage(Page<SysOfficeInformation> page, SysOfficeInformation sysOfficeInformation) {
		return super.findPage(page, sysOfficeInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOfficeInformation sysOfficeInformation) {
		super.save(sysOfficeInformation);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOfficeInformation sysOfficeInformation) {
		super.delete(sysOfficeInformation);
	}
	
}