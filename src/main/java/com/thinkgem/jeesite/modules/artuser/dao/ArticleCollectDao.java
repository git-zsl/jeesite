/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.artuser.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.artuser.entity.ArticleCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章与用户收藏关系DAO接口
 * @author zsl
 * @version 2019-01-19
 */
@MyBatisDao
public interface ArticleCollectDao extends CrudDao<ArticleCollect> {

	public List<ArticleCollect> findSameDatas(@Param("userId") String userId,@Param("articleId") String articleId);
}