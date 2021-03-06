/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.book.entity.BookManager;
import com.thinkgem.jeesite.modules.book.dao.BookManagerDao;

/**
 * 书籍管理Service
 * @author zsl
 * @version 2018-12-09
 */
@Service
@Transactional(readOnly = true)
public class BookManagerService extends CrudService<BookManagerDao, BookManager> {

	public BookManager get(String id) {
		return super.get(id);
	}
	
	public List<BookManager> findList(BookManager bookManager) {
		return super.findList(bookManager);
	}

	public List<BookManager> findByBookType(BookManager bookManager) {
		return dao.findByBookType(bookManager);
	}

	public Page<BookManager> findPage(Page<BookManager> page, BookManager bookManager) {
		return super.findPage(page, bookManager);
	}
	
	@Transactional(readOnly = false)
	public void save(BookManager bookManager) {
		super.save(bookManager);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookManager bookManager) {
		super.delete(bookManager);
	}
	
}