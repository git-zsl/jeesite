/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classification.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 书籍分类Entity
 * @author zsl
 * @version 2018-11-03
 */
public class BookClassification extends DataEntity<BookClassification> {
	
	private static final long serialVersionUID = 1L;
	private String classification;		// 分类
	
	public BookClassification() {
		super();
	}

	public BookClassification(String id){
		super(id);
	}

	@Length(min=0, max=255, message="分类长度必须介于 0 和 255 之间")
	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	
}