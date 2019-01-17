/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classifying.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.classifying.entity.CmsClassifying;

/**
 * 全局分类DAO接口
 * @author zsl
 * @version 2019-01-15
 */
@MyBatisDao
public interface CmsClassifyingDao extends CrudDao<CmsClassifying> {
	
}