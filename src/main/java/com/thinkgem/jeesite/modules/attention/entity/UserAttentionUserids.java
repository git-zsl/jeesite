/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attention.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 关注人关系Entity
 * @author zsl
 * @version 2019-01-20
 */
public class UserAttentionUserids extends DataEntity<UserAttentionUserids> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户id
	private String attentionUserIds;		// 关注人ids
	private List<User> userList = Lists.newArrayList();  //当前用户关注人列表
	private String fans;
	private String articleNum;
	
	public UserAttentionUserids() {
		super();
	}

	public UserAttentionUserids(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getAttentionUserIds() {
		return attentionUserIds;
	}

	public void setAttentionUserIds(String attentionUserIds) {
		this.attentionUserIds = attentionUserIds;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getFans() {
		return fans;
	}

	public void setFans(String fans) {
		this.fans = fans;
	}

	public String getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(String articleNum) {
		this.articleNum = articleNum;
	}
}