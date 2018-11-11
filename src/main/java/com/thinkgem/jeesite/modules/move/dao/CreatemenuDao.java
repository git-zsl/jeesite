/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.move.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.move.entity.Createmenu;

/**
 * 移动DAO接口
 * @author zsl
 * @version 2018-11-10
 */
@MyBatisDao
public interface CreatemenuDao extends CrudDao<Createmenu> {
	
}