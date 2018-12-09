/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classrelation.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.classrelation.entity.ClassificationRelation;

/**
 * 分类关系DAO接口
 * @author zsl
 * @version 2018-12-08
 */
@MyBatisDao
public interface ClassificationRelationDao extends CrudDao<ClassificationRelation> {
	
}