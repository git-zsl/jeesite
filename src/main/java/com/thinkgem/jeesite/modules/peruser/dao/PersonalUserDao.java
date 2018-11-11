/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.peruser.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.peruser.entity.PersonalUser;

/**
 * 个人用户DAO接口
 * @author zsl
 * @version 2018-11-04
 */
@MyBatisDao
public interface PersonalUserDao extends CrudDao<PersonalUser> {
	
}