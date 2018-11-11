/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业用户Entity
 * @author zsl
 * @version 2018-11-04
 */
public class BussinessUser extends DataEntity<BussinessUser> {
	
	private static final long serialVersionUID = 1L;
	private String bussinessLoginName;		// 企业用户名
	private String bussinessLoginPassword;		// 企业用户密码
	private String company;		// 公司名称
	private String phone;		// 联系电话
	private String email;		// 邮箱
	private String userName;		// 姓名
	private String checked;		// 是否通过审核
	private String integral;		// 积分
	private Date endTime;		// 积分截至时间
	
	public BussinessUser() {
		super();
	}

	public BussinessUser(String id){
		super(id);
	}

	@Length(min=0, max=255, message="企业用户名长度必须介于 0 和 255 之间")
	public String getBussinessLoginName() {
		return bussinessLoginName;
	}

	public void setBussinessLoginName(String bussinessLoginName) {
		this.bussinessLoginName = bussinessLoginName;
	}
	
	@Length(min=0, max=255, message="企业用户密码长度必须介于 0 和 255 之间")
	public String getBussinessLoginPassword() {
		return bussinessLoginPassword;
	}

	public void setBussinessLoginPassword(String bussinessLoginPassword) {
		this.bussinessLoginPassword = bussinessLoginPassword;
	}
	
	@Length(min=0, max=255, message="公司名称长度必须介于 0 和 255 之间")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="姓名长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=1, message="是否通过审核长度必须介于 0 和 1 之间")
	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	@Length(min=0, max=255, message="积分长度必须介于 0 和 255 之间")
	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}