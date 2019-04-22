/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.book.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.book.entity.BookManager;

import java.util.List;

/**
 * 书籍管理DAO接口
 * @author zsl
 * @version 2018-12-09
 */
@MyBatisDao
public interface BookManagerDao extends CrudDao<BookManager> {

    public List<BookManager> findByBookType(BookManager bookManager);
}