/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobcity.entity;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 招聘城市Entity
 * @author zsl
 * @version 2019-01-13
 */
public class JobCity extends DataEntity<JobCity> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 拼音简称
	private String city;		// 城市名称
	private List<JobCity> childList = Lists.newArrayList();  //代号内集合
	
	public JobCity() {
		super();
	}

	public JobCity(String id){
		super(id);
	}

	@Length(min=0, max=255, message="拼音简称长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="城市名称长度必须介于 0 和 255 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<JobCity> getChildList() {
		return childList;
	}

	public void setChildList(List<JobCity> childList) {
		this.childList = childList;
	}
}