/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classifying.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.classifying.entity.CmsClassifying;
import com.thinkgem.jeesite.modules.classifying.dao.CmsClassifyingDao;

/**
 * 全局分类Service
 * @author zsl
 * @version 2019-01-15
 */
@Service
@Transactional(readOnly = true)
public class CmsClassifyingService extends CrudService<CmsClassifyingDao, CmsClassifying> {

	public CmsClassifying get(String id) {
		return super.get(id);
	}
	
	public List<CmsClassifying> findList(CmsClassifying cmsClassifying) {
		return super.findList(cmsClassifying);
	}
	
	public Page<CmsClassifying> findPage(Page<CmsClassifying> page, CmsClassifying cmsClassifying) {
		return super.findPage(page, cmsClassifying);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsClassifying cmsClassifying) {
		super.save(cmsClassifying);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsClassifying cmsClassifying) {
		super.delete(cmsClassifying);
	}
	
}