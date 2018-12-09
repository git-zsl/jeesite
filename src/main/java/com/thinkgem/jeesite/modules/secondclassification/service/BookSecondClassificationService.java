/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.secondclassification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.secondclassification.entity.BookSecondClassification;
import com.thinkgem.jeesite.modules.secondclassification.dao.BookSecondClassificationDao;

/**
 * 二级分类Service
 * @author zsl
 * @version 2018-12-08
 */
@Service
@Transactional(readOnly = true)
public class BookSecondClassificationService extends CrudService<BookSecondClassificationDao, BookSecondClassification> {

	public BookSecondClassification get(String id) {
		return super.get(id);
	}
	
	public List<BookSecondClassification> findList(BookSecondClassification bookSecondClassification) {
		return super.findList(bookSecondClassification);
	}
	
	public Page<BookSecondClassification> findPage(Page<BookSecondClassification> page, BookSecondClassification bookSecondClassification) {
		return super.findPage(page, bookSecondClassification);
	}
	
	@Transactional(readOnly = false)
	public void save(BookSecondClassification bookSecondClassification) {
		super.save(bookSecondClassification);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookSecondClassification bookSecondClassification) {
		super.delete(bookSecondClassification);
	}
	
}