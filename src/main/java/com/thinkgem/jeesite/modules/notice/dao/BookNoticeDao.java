/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.notice.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.notice.entity.BookNotice;

/**
 * 图书公告DAO接口
 * @author zsl
 * @version 2018-12-09
 */
@MyBatisDao
public interface BookNoticeDao extends CrudDao<BookNotice> {
	
}