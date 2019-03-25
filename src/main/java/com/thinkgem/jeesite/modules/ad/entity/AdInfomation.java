/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

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
	private String period;       //周期
	private String link;        // 链接
	private String image;          // 图片
	private String nowDate;          // 当前时间
	private Category category;		// 广告对应的栏目id
	private String articleId;         // 文章 id
	private Date releaseTime;		// 发布时间
	private Date soldOutTime;		// 下架时间
	private String promulgator;		// 发布者
	private String isConfig;         // 配置标志
	private List<AdInfomation> childerAdInfomations = Lists.newArrayList();
	
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

	public String getIsConfig() {
		return isConfig;
	}

	public void setIsConfig(String isConfig) {
		this.isConfig = isConfig;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<AdInfomation> getChilderAdInfomations() {
		return childerAdInfomations;
	}

	public void setChilderAdInfomations(List<AdInfomation> childerAdInfomations) {
		this.childerAdInfomations = childerAdInfomations;
	}

	public static void sortList(List<AdInfomation> list, List<AdInfomation> sourcelist, String parentId){
		for (int i=0; i<sourcelist.size(); i++){
			AdInfomation e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					AdInfomation child = sourcelist.get(j);
					if (child.getParent()!=null && child.getParent().getId()!=null
							&& child.getParent().getId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
}