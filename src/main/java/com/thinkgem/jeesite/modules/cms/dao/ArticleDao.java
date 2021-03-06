/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import org.apache.ibatis.annotations.Param;

/**
 * 文章DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {
	
	public List<Article> findByIdIn(String[] ids);

	public List<Article> findArticles();

	public List<Article> searchArticle(Article article);

	public List<Article> findByCategoryIdIn(List<String> list);

	public List<Article> findbyUserIdOrderByUpdateDate(@Param("createById") String createById);

	public List<Article> findByCategoryIdInAndPageNum(@Param("list") List<String> list,@Param("pageNum") String pageNum);

	public List<String> findTitle(Article article);

	public List<Article> findHotCompany(Article article);

	public List<Article> findHostAuthors(Article article);

	public List<Article> findHostPosts(Article article);

	public void updateLikeNum(Article article);

	public void updateIsRecommend(Article article);

	public void updateIsTop(Article article);

	public void updateCollectNum(Article article);

	public void updataArticleCommentNum(Article article);

	public void updateArticleHits(Article article);

	public List<Article> findHostKeywords(Article article);

	public HashMap<String,Object> findByYearCount();


	public Integer findOwnNum(@Param("categoryId") String categoryId,@Param("createBy") String createBy);
	//		return find("from Article where id in (:p1)", new Parameter(new Object[]{ids}));
//	{

//	}

	public int updateHitsAddOne(String id);
//	{
//		return update("update Article set hits=hits+1 where id = :p1", new Parameter(id));
//	}
	
	public int updateExpiredWeight(Article article);
	
	public List<Category> findStats(Category category);
//	{
//		return update("update Article set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}

	public void newDelete(@Param("delFlag") String delFlag,@Param("id") String id,@Param("promulgatorId") String promulgatorId);
}
