/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.articleclassify.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 文章分类Entity
 * @author zsl
 * @version 2019-02-05
 */
public class CmsArticleClassify extends DataEntity<CmsArticleClassify> {
	
	private static final long serialVersionUID = 1L;
	private String articleClassify;		// 文章分类
	private String sort;
	
	public CmsArticleClassify() {
		super();
	}

	public CmsArticleClassify(String id){
		super(id);
	}

	@Length(min=0, max=255, message="文章分类长度必须介于 0 和 255 之间")
	public String getArticleClassify() {
		return articleClassify;
	}

	public void setArticleClassify(String articleClassify) {
		this.articleClassify = articleClassify;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}