/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.area.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.area.entity.SysChina;
import com.thinkgem.jeesite.modules.area.dao.SysChinaDao;

/**
 * 省市区对象Service
 * @author zsl
 * @version 2019-05-13
 */
@Service
@Transactional(readOnly = true)
public class SysChinaService extends CrudService<SysChinaDao, SysChina> {

	public SysChina get(String id) {
		return super.get(id);
	}
	
	public List<SysChina> findList(SysChina sysChina) {
		return super.findList(sysChina);
	}
	
	public Page<SysChina> findPage(Page<SysChina> page, SysChina sysChina) {
		return super.findPage(page, sysChina);
	}
	
	@Transactional(readOnly = false)
	public void save(SysChina sysChina) {
		super.save(sysChina);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysChina sysChina) {
		super.delete(sysChina);
	}
	
}