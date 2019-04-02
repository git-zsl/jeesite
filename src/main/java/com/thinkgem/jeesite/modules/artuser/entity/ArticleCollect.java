/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.artuser.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 文章与用户收藏关系Entity
 * @author zsl
 * @version 2019-01-19
 */
public class ArticleCollect extends DataEntity<ArticleCollect> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户id
	private String articleId;		// 收藏的文章id
	private String updateTime ;		// 更新标志
	private List<Article> articles = Lists.newArrayList();     //收藏文章集合
	
	public ArticleCollect() {
		super();
	}

	public ArticleCollect(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=255, message="收藏的文章id长度必须介于 0 和 255 之间")
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}