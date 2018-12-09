/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.notice.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 图书公告Entity
 * @author zsl
 * @version 2018-12-09
 */
public class BookNotice extends DataEntity<BookNotice> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 公告内容
	private String href;		// 跳转链接
	
	public BookNotice() {
		super();
	}

	public BookNotice(String id){
		super(id);
	}

	@Length(min=0, max=255, message="公告内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=2000, message="跳转链接长度必须介于 0 和 2000 之间")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
}