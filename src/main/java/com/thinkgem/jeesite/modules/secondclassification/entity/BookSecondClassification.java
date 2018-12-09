/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.secondclassification.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 二级分类Entity
 * @author zsl
 * @version 2018-12-08
 */
public class BookSecondClassification extends DataEntity<BookSecondClassification> {
	
	private static final long serialVersionUID = 1L;
	private String secondClassification;		// 二级分类
	
	public BookSecondClassification() {
		super();
	}

	public BookSecondClassification(String id){
		super(id);
	}

	@Length(min=0, max=255, message="二级分类长度必须介于 0 和 255 之间")
	public String getSecondClassification() {
		return secondClassification;
	}

	public void setSecondClassification(String secondClassification) {
		this.secondClassification = secondClassification;
	}
	
}