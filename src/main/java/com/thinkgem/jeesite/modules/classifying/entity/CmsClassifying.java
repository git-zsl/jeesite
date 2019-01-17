/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classifying.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 全局分类Entity
 * @author zsl
 * @version 2019-01-15
 */
public class CmsClassifying extends DataEntity<CmsClassifying> {
	
	private static final long serialVersionUID = 1L;
	private String classifying;		// 分类
	
	public CmsClassifying() {
		super();
	}

	public CmsClassifying(String id){
		super(id);
	}

	@Length(min=0, max=255, message="分类长度必须介于 0 和 255 之间")
	public String getClassifying() {
		return classifying;
	}

	public void setClassifying(String classifying) {
		this.classifying = classifying;
	}
	
}