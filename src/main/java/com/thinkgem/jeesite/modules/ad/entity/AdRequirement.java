/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 广告需求Entity
 * @author zsl
 * @version 2019-03-30
 */
public class AdRequirement extends DataEntity<AdRequirement> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 广告标题
	private String adType;		// 广告投放类型
	private String email;		// 工作邮箱
	private String wechatId;		// 微信id
	private String period;		// 投放周期
	private String content;		// 附件内容
	
	public AdRequirement() {
		super();
	}

	public AdRequirement(String id){
		super(id);
	}

	@Length(min=0, max=255, message="广告标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="广告投放类型长度必须介于 0 和 255 之间")
	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}
	
	@Length(min=0, max=255, message="工作邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="微信id长度必须介于 0 和 255 之间")
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	
	@Length(min=0, max=255, message="投放周期长度必须介于 0 和 255 之间")
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	@Length(min=0, max=2000, message="附件内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}