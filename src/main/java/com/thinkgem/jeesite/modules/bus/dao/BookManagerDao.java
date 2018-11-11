/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bus.entity.BookManager;

/**
 * 书籍管理DAO接口
 * @author zsl
 * @version 2018-11-03
 */
@MyBatisDao
public interface BookManagerDao extends CrudDao<BookManager> {
	
}