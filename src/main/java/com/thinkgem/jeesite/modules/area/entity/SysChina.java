/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.area.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 省市区对象Entity
 * @author zsl
 * @version 2019-05-13
 */
public class SysChina extends DataEntity<SysChina> {
	
	private static final long serialVersionUID = 1L;
	private SysChina parent;		// 父级编号
	private String name;		// 名称
	private String sort;		// 排序
	private String code;		// 区域编码
	private String type;		// 区域类型
	
	public SysChina() {
		super();
	}

	public SysChina(String id){
		super(id);
	}

	@JsonBackReference
	public SysChina getParent() {
		return parent;
	}

	public void setParent(SysChina parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=100, message="区域编码长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=1, message="区域类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}