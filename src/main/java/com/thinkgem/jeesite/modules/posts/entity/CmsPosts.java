/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.posts.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 分类Entity
 * @author zsl
 * @version 2018-12-19
 */
public class CmsPosts extends DataEntity<CmsPosts> {
	
	private static final long serialVersionUID = 1L;
	private String posts;		// 岗位
	private String sort;       // 排序
	
	public CmsPosts() {
		super();
	}

	public CmsPosts(String id){
		super(id);
	}

	@Length(min=0, max=255, message="岗位长度必须介于 0 和 255 之间")
	public String getPosts() {
		return posts;
	}

	public void setPosts(String posts) {
		this.posts = posts;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}