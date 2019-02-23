/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.cms.entity.Category;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 广告信息Entity
 * @author zsl
 * @version 2019-02-23
 */
public class AdInfomation extends TreeEntity<AdInfomation> {
	
	private static final long serialVersionUID = 1L;
	private AdInfomation parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 广告位名称
	private Integer sort;		// 排序
	private Category category;		// 广告对应的栏目id
	private String articleId;         // 文章 id
	private Date releaseTime;		// 发布时间
	private Date soldOutTime;		// 下架时间
	private String promulgator;		// 发布者
	
	public AdInfomation() {
		super();
	}

	public AdInfomation(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public AdInfomation getParent() {
		return parent;
	}

	public void setParent(AdInfomation parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=255, message="所有父级编号长度必须介于 1 和 255 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=255, message="广告位名称长度必须介于 1 和 255 之间")
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发布时间不能为空")
	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="下架时间不能为空")
	public Date getSoldOutTime() {
		return soldOutTime;
	}

	public void setSoldOutTime(Date soldOutTime) {
		this.soldOutTime = soldOutTime;
	}
	
	@Length(min=1, max=255, message="发布者长度必须介于 1 和 255 之间")
	public String getPromulgator() {
		return promulgator;
	}

	public void setPromulgator(String promulgator) {
		this.promulgator = promulgator;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}