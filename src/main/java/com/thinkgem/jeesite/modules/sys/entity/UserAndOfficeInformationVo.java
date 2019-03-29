/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.RoleListType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 用户Entity
 * @author zsl
 * @version
 */
public class UserAndOfficeInformationVo {

	private String userId;   //用户id
	private String officeInfomationId;  // 企业信息id
	private String email;	// 邮箱
	private String mobile;	// 手机
	private String photo;	// 头像
	private String subscription;   //订阅
	private String douBan;    //豆瓣链接
	private String zhiHu;     // 短乎链接
	private String weiBo;     //微薄链接
	private String weiXinCode;   //微信二维码
	private String attentionNum;  //关注数
	private String attention2Num;  //被关注数
	private String provence;  // 省
	private String city;    // 市
	private String district;      // 区
	private String officeLink;     // 公司主页
	private String name;		// 机构名称
	private String teamSize;       //团队人数
	private String officeImage;    // 创作环境

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeInfomationId() {
		return officeInfomationId;
	}

	public void setOfficeInfomationId(String officeInfomationId) {
		this.officeInfomationId = officeInfomationId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getDouBan() {
		return douBan;
	}

	public void setDouBan(String douBan) {
		this.douBan = douBan;
	}

	public String getZhiHu() {
		return zhiHu;
	}

	public void setZhiHu(String zhiHu) {
		this.zhiHu = zhiHu;
	}

	public String getWeiBo() {
		return weiBo;
	}

	public void setWeiBo(String weiBo) {
		this.weiBo = weiBo;
	}

	public String getWeiXinCode() {
		return weiXinCode;
	}

	public void setWeiXinCode(String weiXinCode) {
		this.weiXinCode = weiXinCode;
	}

	public String getAttentionNum() {
		return attentionNum;
	}

	public void setAttentionNum(String attentionNum) {
		this.attentionNum = attentionNum;
	}

	public String getAttention2Num() {
		return attention2Num;
	}

	public void setAttention2Num(String attention2Num) {
		this.attention2Num = attention2Num;
	}

	public String getProvence() {
		return provence;
	}

	public void setProvence(String provence) {
		this.provence = provence;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getOfficeLink() {
		return officeLink;
	}

	public void setOfficeLink(String officeLink) {
		this.officeLink = officeLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(String teamSize) {
		this.teamSize = teamSize;
	}

	public String getOfficeImage() {
		return officeImage;
	}

	public void setOfficeImage(String officeImage) {
		this.officeImage = officeImage;
	}
}