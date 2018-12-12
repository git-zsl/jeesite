/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.custom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.custom.entity.CustomCategory;
import com.thinkgem.jeesite.modules.custom.dao.CustomCategoryDao;

/**
 * 自定义栏目配置Service
 * @author zsl
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class CustomCategoryService extends CrudService<CustomCategoryDao, CustomCategory> {

	public CustomCategory get(String id) {
		return super.get(id);
	}

	public CustomCategory findByMark(String key) {
		return dao.findByMark(key);
	}

	public List<CustomCategory> findList(CustomCategory customCategory) {
		return super.findList(customCategory);
	}
	
	public Page<CustomCategory> findPage(Page<CustomCategory> page, CustomCategory customCategory) {
		return super.findPage(page, customCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomCategory customCategory) {
		super.save(customCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomCategory customCategory) {
		super.delete(customCategory);
	}
	
}