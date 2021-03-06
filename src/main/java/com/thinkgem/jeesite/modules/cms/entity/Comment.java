/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 评论Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Comment extends DataEntity<Comment> {

	private static final long serialVersionUID = 1L;
	private Category category;// 分类编号
	private String contentId;	// 归属分类内容的编号（Article.id、Photo.id、Download.id）
	private String parentContentId;    // 父级评论id
	private String title;	// 归属分类内容的标题（Article.title、Photo.title、Download.title）
	private String content; // 评论内容
	private String name; 	// 评论姓名
	private String ip; 		// 评论IP
	private Date createDate;// 评论时间
	private User auditUser; // 审核人
	private Date auditDate;	// 审核时间
	private String delFlag;	// 删除标记删除标记（0：正常；1：删除；2：审核）
	private List<Comment> childComments = Lists.newArrayList();  //子评论集合
	private String isTop;  // 是否置顶
	private String isRecommend; //是否推荐
	private String commentNum;   //评论数
	private String likeNum;   //点赞数
	private String likeUserIds;   //点赞数
	private String userPhoto; //评论人头像路径
	private String categoryIdFlag;   //是否文章标志
	private String isValidate;   //是否需要验证码
	private User user;
    private List<Comment> childrens = Lists.newArrayList();
    private String listFlag;    // 点赞标志
	public Comment() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
		this.isTop = Global.NO;
		this.isRecommend = Global.NO;
		this.commentNum = Global.NO;
		this.likeNum = Global.NO;
	}
	
	public Comment(String id){
		this();
		this.id = id;
	}
	
	public Comment(Category category){
		this();
		this.category = category;
	}
	


	@NotNull
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getParentContentId() {
		return parentContentId;
	}

	public void setParentContentId(String parentContentId) {
		this.parentContentId = parentContentId;
	}

	@NotNull
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@Length(min=1, max=255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=1, max=255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@NotNull
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Length(min=1, max=1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public List<Comment> getChildComments() {
		return childComments;
	}

	public void setChildComments(List<Comment> childComments) {
		this.childComments = childComments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}

	public String getCategoryIdFlag() {
		return categoryIdFlag;
	}

	public void setCategoryIdFlag(String categoryIdFlag) {
		this.categoryIdFlag = categoryIdFlag;
	}

	public List<Comment> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Comment> childrens) {
		this.childrens = childrens;
	}

	public String getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}

	public String getLikeUserIds() {
		return likeUserIds;
	}

	public void setLikeUserIds(String likeUserIds) {
		this.likeUserIds = likeUserIds;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getListFlag() {
		return listFlag;
	}

	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}
}