/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.move.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.move.entity.Createmenu;
import com.thinkgem.jeesite.modules.move.dao.CreatemenuDao;

/**
 * 移动Service
 * @author zsl
 * @version 2018-11-10
 */
@Service
@Transactional(readOnly = true)
public class CreatemenuService extends CrudService<CreatemenuDao, Createmenu> {

	public Createmenu get(String id) {
		return super.get(id);
	}
	
	public List<Createmenu> findList(Createmenu createmenu) {
		return super.findList(createmenu);
	}
	
	public Page<Createmenu> findPage(Page<Createmenu> page, Createmenu createmenu) {
		return super.findPage(page, createmenu);
	}
	
	@Transactional(readOnly = false)
	public void save(Createmenu createmenu) {
		super.save(createmenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(Createmenu createmenu) {
		super.delete(createmenu);
	}
	
}