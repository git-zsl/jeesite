/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.notice.entity.BookNotice;
import com.thinkgem.jeesite.modules.notice.dao.BookNoticeDao;

/**
 * 图书公告Service
 * @author zsl
 * @version 2018-12-09
 */
@Service
@Transactional(readOnly = true)
public class BookNoticeService extends CrudService<BookNoticeDao, BookNotice> {

	public BookNotice get(String id) {
		return super.get(id);
	}
	
	public List<BookNotice> findList(BookNotice bookNotice) {
		return super.findList(bookNotice);
	}
	
	public Page<BookNotice> findPage(Page<BookNotice> page, BookNotice bookNotice) {
		return super.findPage(page, bookNotice);
	}
	
	@Transactional(readOnly = false)
	public void save(BookNotice bookNotice) {
		super.save(bookNotice);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookNotice bookNotice) {
		super.delete(bookNotice);
	}
	
}