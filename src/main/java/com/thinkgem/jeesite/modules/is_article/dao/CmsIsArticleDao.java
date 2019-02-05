/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.is_article.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.is_article.entity.CmsIsArticle;

import java.util.List;

/**
 * 属于文章categoryDAO接口
 * @author zsl
 * @version 2019-02-03
 */
@MyBatisDao
public interface CmsIsArticleDao extends CrudDao<CmsIsArticle> {

    public List<String> findCategoryIds(CmsIsArticle cmsIsArticle);
}