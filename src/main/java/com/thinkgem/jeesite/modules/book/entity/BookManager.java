/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.book.entity;

import com.thinkgem.jeesite.modules.classificationtree.entity.Classificationtree;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 书籍管理Entity
 * @author zsl
 * @version 2018-12-09
 */
public class BookManager extends DataEntity<BookManager> {
	
	private static final long serialVersionUID = 1L;
	private String bookName;		// 图书名称
	private String author;		// 作者名称
	private String authorIntroduce;		// 作者信息
	private String bookImagUrl;		// 图书图片存储路径
	private Classificationtree firstClassId;		// 图书类目
	private Classificationtree secondClassId;		// 图书分组
	private String price;		// 价格
	private String buyLink;		// 购买链接
	private String isRecommend;		// 是否推荐
	private Date publishDate;		// 发布日期
	private String editorRecommend;		// 主编荐语
	private String publishingHouse;		// 出版社
	private String language;		// 语言
	private String specification;		// 规格
	private String placeOfPublication;		// 出版地
	private String particulars;		// 图文描述
	private Integer praiseNum;		// 点赞数
	private Integer collectNum;		//收藏数
	private Integer recommendNum;   // 推荐数
	private String bookType;		// 图书类型
	private Integer hits;		    // 点击数
	private String softType;		    // 排序类型

	
	public BookManager() {
		this.praiseNum = 0;		// 点赞数
		this.collectNum = 0;		//收藏数
		this.hits = 0;	   // 推荐数
		this.recommendNum = 0;			    // 点击数
		this.price = "0.0";			    // 价格
	}

	public BookManager(String id){
		super(id);
		this.praiseNum = 0;		// 点赞数
		this.collectNum = 0;		//收藏数
		this.hits = 0;	   // 推荐数
		this.recommendNum = 0;			    // 点击数
		this.price = "0.0";			    // 价格
	}

	@Length(min=0, max=255, message="图书名称长度必须介于 0 和 255 之间")
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Length(min=0, max=255, message="作者名称长度必须介于 0 和 255 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Length(min=0, max=255, message="作者信息长度必须介于 0 和 255 之间")
	public String getAuthorIntroduce() {
		return authorIntroduce;
	}

	public void setAuthorIntroduce(String authorIntroduce) {
		this.authorIntroduce = authorIntroduce;
	}
	
	@Length(min=0, max=2000, message="图书图片存储路径长度必须介于 0 和 2000 之间")
	public String getBookImagUrl() {
		return bookImagUrl;
	}

	public void setBookImagUrl(String bookImagUrl) {
		this.bookImagUrl = bookImagUrl;
	}

	public Classificationtree getFirstClassId() {
		return firstClassId;
	}

	public void setFirstClassId(Classificationtree firstClassId) {
		this.firstClassId = firstClassId;
	}

	public Classificationtree getSecondClassId() {
		return secondClassId;
	}

	public void setSecondClassId(Classificationtree secondClassId) {
		this.secondClassId = secondClassId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=255, message="购买链接长度必须介于 0 和 255 之间")
	public String getBuyLink() {
		return buyLink;
	}

	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}
	
	@Length(min=0, max=1, message="是否推荐长度必须介于 0 和 1 之间")
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
	
	@Length(min=0, max=255, message="主编荐语长度必须介于 0 和 255 之间")
	public String getEditorRecommend() {
		return editorRecommend;
	}

	public void setEditorRecommend(String editorRecommend) {
		this.editorRecommend = editorRecommend;
	}
	
	@Length(min=0, max=255, message="出版社长度必须介于 0 和 255 之间")
	public String getPublishingHouse() {
		return publishingHouse;
	}

	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}
	
	@Length(min=0, max=255, message="语言长度必须介于 0 和 255 之间")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Length(min=0, max=255, message="规格长度必须介于 0 和 255 之间")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@Length(min=0, max=255, message="出版地长度必须介于 0 和 255 之间")
	public String getPlaceOfPublication() {
		return placeOfPublication;
	}

	public void setPlaceOfPublication(String placeOfPublication) {
		this.placeOfPublication = placeOfPublication;
	}
	
	@Length(min=0, max=2000, message="图文描述长度必须介于 0 和 2000 之间")
	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	
	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	
	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}
	
	public Integer getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(Integer recommendNum) {
		this.recommendNum = recommendNum;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public String getSoftType() {
		return softType;
	}

	public void setSoftType(String softType) {
		this.softType = softType;
	}
}