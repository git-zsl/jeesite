/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classrelation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.classrelation.entity.ClassificationRelation;
import com.thinkgem.jeesite.modules.classrelation.dao.ClassificationRelationDao;

/**
 * 分类关系Service
 * @author zsl
 * @version 2018-12-08
 */
@Service
@Transactional(readOnly = true)
public class ClassificationRelationService extends CrudService<ClassificationRelationDao, ClassificationRelation> {

	public ClassificationRelation get(String id) {
		return super.get(id);
	}
	
	public List<ClassificationRelation> findList(ClassificationRelation classificationRelation) {
		return super.findList(classificationRelation);
	}
	
	public Page<ClassificationRelation> findPage(Page<ClassificationRelation> page, ClassificationRelation classificationRelation) {
		return super.findPage(page, classificationRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(ClassificationRelation classificationRelation) {
		super.save(classificationRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClassificationRelation classificationRelation) {
		super.delete(classificationRelation);
	}
	
}