/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classrelation.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 分类关系Entity
 * @author zsl
 * @version 2018-12-08
 */
public class ClassificationRelation extends DataEntity<ClassificationRelation> {
	
	private static final long serialVersionUID = 1L;
	private String firstClassificationId;		// first_classification_id
	private String secondClassificationId;		// second_classification_id
	
	public ClassificationRelation() {
		super();
	}

	public ClassificationRelation(String id){
		super(id);
	}

	@Length(min=0, max=255, message="first_classification_id长度必须介于 0 和 255 之间")
	public String getFirstClassificationId() {
		return firstClassificationId;
	}

	public void setFirstClassificationId(String firstClassificationId) {
		this.firstClassificationId = firstClassificationId;
	}
	
	@Length(min=0, max=255, message="second_classification_id长度必须介于 0 和 255 之间")
	public String getSecondClassificationId() {
		return secondClassificationId;
	}

	public void setSecondClassificationId(String secondClassificationId) {
		this.secondClassificationId = secondClassificationId;
	}
	
}