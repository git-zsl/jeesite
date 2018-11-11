/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.business.entity.BussinessUser;
import com.thinkgem.jeesite.modules.business.dao.BussinessUserDao;

/**
 * 企业用户Service
 * @author zsl
 * @version 2018-11-04
 */
@Service
@Transactional(readOnly = true)
public class BussinessUserService extends CrudService<BussinessUserDao, BussinessUser> {

	public BussinessUser get(String id) {
		return super.get(id);
	}
	
	public List<BussinessUser> findList(BussinessUser bussinessUser) {
		return super.findList(bussinessUser);
	}
	
	public Page<BussinessUser> findPage(Page<BussinessUser> page, BussinessUser bussinessUser) {
		return super.findPage(page, bussinessUser);
	}
	
	@Transactional(readOnly = false)
	public void save(BussinessUser bussinessUser) {
		super.save(bussinessUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(BussinessUser bussinessUser) {
		super.delete(bussinessUser);
	}
	
}