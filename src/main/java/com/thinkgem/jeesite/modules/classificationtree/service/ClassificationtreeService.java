/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classificationtree.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.classificationtree.entity.Classificationtree;
import com.thinkgem.jeesite.modules.classificationtree.dao.ClassificationtreeDao;

/**
 * 分类Service
 * @author zsl
 * @version 2018-12-08
 */
@Service
@Transactional(readOnly = true)
public class ClassificationtreeService extends TreeService<ClassificationtreeDao, Classificationtree> {

	public Classificationtree get(String id) {
		return super.get(id);
	}
	
	public List<Classificationtree> findList(Classificationtree classificationtree) {
		if (StringUtils.isNotBlank(classificationtree.getParentIds())){
			classificationtree.setParentIds(","+classificationtree.getParentIds()+",");
		}
		return super.findList(classificationtree);
	}
	
	@Transactional(readOnly = false)
	public void save(Classificationtree classificationtree) {
		super.save(classificationtree);
	}
	
	@Transactional(readOnly = false)
	public void delete(Classificationtree classificationtree) {
		super.delete(classificationtree);
	}
	
}