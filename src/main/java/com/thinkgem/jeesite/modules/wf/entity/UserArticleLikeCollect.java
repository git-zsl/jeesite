/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wf.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户文章点赞收藏用户管理Entity
 * @author zsl
 * @version 2019-04-24
 */
public class UserArticleLikeCollect extends DataEntity<UserArticleLikeCollect> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户id
	private String articleId;		// 文章id
	private String good;		// 点赞
	private String collect;		// 收藏
	
	public UserArticleLikeCollect() {
		super();
	}

	public UserArticleLikeCollect(String id){
		super(id);
	}

	public UserArticleLikeCollect(User user,String articleId){
		this.user = user;
		this.articleId = articleId;
	}

	@NotNull(message="用户id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=64, message="文章id长度必须介于 1 和 64 之间")
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	@Length(min=0, max=1, message="点赞长度必须介于 0 和 1 之间")
	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}


	@Length(min=0, max=1, message="收藏长度必须介于 0 和 1 之间")
	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}
	
}