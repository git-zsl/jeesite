/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.business.entity.BussinessUser;

/**
 * 企业用户DAO接口
 * @author zsl
 * @version 2018-11-04
 */
@MyBatisDao
public interface BussinessUserDao extends CrudDao<BussinessUser> {
	
}