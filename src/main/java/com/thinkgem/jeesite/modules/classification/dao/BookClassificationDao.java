/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classification.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.classification.entity.BookClassification;

/**
 * 书籍分类DAO接口
 * @author zsl
 * @version 2018-11-03
 */
@MyBatisDao
public interface BookClassificationDao extends CrudDao<BookClassification> {
	
}