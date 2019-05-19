/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userbackground.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户背景管理Entity
 * @author zsl
 * @version 2019-05-19
 */
public class UserBackground extends DataEntity<UserBackground> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户id
	private String backgroundPath;		// 背景图地址
	
	public UserBackground() {
		super();
	}

	public UserBackground(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=2000, message="背景图地址长度必须介于 0 和 2000 之间")
	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}
	
}