/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 书籍管理Entity
 * @author zsl
 * @version 2018-11-03
 */
public class BookManager extends DataEntity<BookManager> {
	
	private static final long serialVersionUID = 1L;
	private String bookName;		// 书籍名称
	private String author;		// 作者
	private String cover;		// 封面
	private String firstClass;		// 一级分类
	private String secondClass;		// 二级分类
	private String price;		// 价格
	private String byLink;		// 购买链接
	private String isRecommend;		// 推荐
	private Date publishDate;		// 发布时间
	private String beginPrice;		// 开始 价格
	private String endPrice;		// 结束 价格
	
	public BookManager() {
		super();
	}

	public BookManager(String id){
		super(id);
	}

	@Length(min=0, max=255, message="书籍名称长度必须介于 0 和 255 之间")
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Length(min=0, max=255, message="作者长度必须介于 0 和 255 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Length(min=0, max=255, message="封面长度必须介于 0 和 255 之间")
	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
	@Length(min=0, max=255, message="一级分类长度必须介于 0 和 255 之间")
	public String getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(String firstClass) {
		this.firstClass = firstClass;
	}
	
	@Length(min=0, max=255, message="二级分类长度必须介于 0 和 255 之间")
	public String getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=255, message="购买链接长度必须介于 0 和 255 之间")
	public String getByLink() {
		return byLink;
	}

	public void setByLink(String byLink) {
		this.byLink = byLink;
	}
	
	@Length(min=0, max=1, message="推荐长度必须介于 0 和 1 之间")
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	public String getBeginPrice() {
		return beginPrice;
	}

	public void setBeginPrice(String beginPrice) {
		this.beginPrice = beginPrice;
	}
	
	public String getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}
		
}