/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.secondclassification.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.secondclassification.entity.BookSecondClassification;

/**
 * 二级分类DAO接口
 * @author zsl
 * @version 2018-12-08
 */
@MyBatisDao
public interface BookSecondClassificationDao extends CrudDao<BookSecondClassification> {
	
}