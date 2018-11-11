/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.move.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 移动Entity
 * @author zsl
 * @version 2018-11-10
 */
public class Createmenu extends DataEntity<Createmenu> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	
	public Createmenu() {
		super();
	}

	public Createmenu(String id){
		super(id);
	}

	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}