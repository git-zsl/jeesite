/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.custom.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自定义栏目配置Entity
 * @author zsl
 * @version 2018-12-12
 */
public class CustomCategory extends DataEntity<CustomCategory> {
	
	private static final long serialVersionUID = 1L;
	private String categoryName;		// 自定义栏目名称
	private String categoryId;		// 自定义栏目编号
	private String customMark;		// 栏目标志
	
	public CustomCategory() {
		super();
	}

	public CustomCategory(String id){
		super(id);
	}

	@Length(min=0, max=255, message="自定义栏目名称长度必须介于 0 和 255 之间")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Length(min=0, max=255, message="自定义栏目编号长度必须介于 0 和 255 之间")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Length(min=0, max=255, message="栏目标志长度必须介于 0 和 255 之间")
	public String getCustomMark() {
		return customMark;
	}

	public void setCustomMark(String customMark) {
		this.customMark = customMark;
	}
	
}