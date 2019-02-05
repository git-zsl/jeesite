/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.articleclassify.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.articleclassify.entity.CmsArticleClassify;

/**
 * 文章分类DAO接口
 * @author zsl
 * @version 2019-02-05
 */
@MyBatisDao
public interface CmsArticleClassifyDao extends CrudDao<CmsArticleClassify> {
	
}