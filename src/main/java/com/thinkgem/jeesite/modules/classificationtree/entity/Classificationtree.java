/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classificationtree.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 分类Entity
 * @author zsl
 * @version 2018-12-08
 */
public class Classificationtree extends TreeEntity<Classificationtree> {
	
	private static final long serialVersionUID = 1L;
	private Classificationtree parent;		// 父id
	private String parentIds;		// 父ids
	private String name;		// 分类名称
	private Integer sort;		// 排序
	
	public Classificationtree() {
		super();
	}

	public Classificationtree(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父id不能为空")
	public Classificationtree getParent() {
		return parent;
	}

	public void setParent(Classificationtree parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="父ids长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=255, message="分类名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}