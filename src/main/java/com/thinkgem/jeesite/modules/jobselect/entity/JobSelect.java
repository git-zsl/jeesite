/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobselect.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

import javax.validation.constraints.NotNull;

/**
 * 招聘搜索条件Entity
 * @author zsl
 * @version 2019-01-14
 */
public class JobSelect extends TreeEntity<JobSelect> {
	
	private static final long serialVersionUID = 1L;
	private JobSelect parent;		// 父id
	private String parentIds;		// 父ids
	private String area;		// 热门城市
	private String pay;		// 薪资
	private String education;		// 学历
	private String experience;		// 经验
	private Integer sort;		// 排序
	
	public JobSelect() {
		super();
	}

	public JobSelect(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父id不能为空")
	public JobSelect getParent() {
		return parent;
	}

	public void setParent(JobSelect parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=255, message="父ids长度必须介于 0 和 255 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=255, message="热门城市长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="薪资长度必须介于 0 和 255 之间")
	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}
	
	@Length(min=0, max=255, message="学历长度必须介于 0 和 255 之间")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@Length(min=0, max=255, message="经验长度必须介于 0 和 255 之间")
	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
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