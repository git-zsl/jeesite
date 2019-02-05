/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.is_article.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 属于文章categoryEntity
 * @author zsl
 * @version 2019-02-03
 */
public class CmsIsArticle extends DataEntity<CmsIsArticle> {
	
	private static final long serialVersionUID = 1L;
	private String categoryid;		// 栏目id
	
	public CmsIsArticle() {
		super();
	}

	public CmsIsArticle(String id){
		super(id);
	}

	@Length(min=0, max=64, message="栏目id长度必须介于 0 和 64 之间")
	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
}