/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.classification.entity.BookClassification;
import com.thinkgem.jeesite.modules.classification.dao.BookClassificationDao;

/**
 * 书籍分类Service
 * @author zsl
 * @version 2018-11-03
 */
@Service
@Transactional(readOnly = true)
public class BookClassificationService extends CrudService<BookClassificationDao, BookClassification> {

	public BookClassification get(String id) {
		return super.get(id);
	}
	
	public List<BookClassification> findList(BookClassification bookClassification) {
		return super.findList(bookClassification);
	}
	
	public Page<BookClassification> findPage(Page<BookClassification> page, BookClassification bookClassification) {
		return super.findPage(page, bookClassification);
	}
	
	@Transactional(readOnly = false)
	public void save(BookClassification bookClassification) {
		super.save(bookClassification);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookClassification bookClassification) {
		super.delete(bookClassification);
	}
	
}