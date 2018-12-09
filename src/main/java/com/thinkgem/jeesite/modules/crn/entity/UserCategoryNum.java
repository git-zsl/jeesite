/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crn.entity;

import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 栏目授权Entity
 * @author zsl
 * @version 2018-11-28
 */
public class UserCategoryNum extends DataEntity<UserCategoryNum> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户id
	private String categoryId;		// 栏目id
	private Integer createNum;		// 允许创建数量
	private Integer currentNum;		// 拥有数量
	private Category category;        // 栏目
	
	public UserCategoryNum() {
		super();
	}

	public UserCategoryNum(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=255, message="栏目id长度必须介于 0 和 255 之间")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public Integer getCreateNum() {
		return createNum;
	}

	public void setCreateNum(Integer createNum) {
		this.createNum = createNum;
	}
	
	public Integer getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}
	
}