/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userbackground.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.userbackground.entity.UserBackground;

/**
 * 用户背景管理DAO接口
 * @author zsl
 * @version 2019-05-19
 */
@MyBatisDao
public interface UserBackgroundDao extends CrudDao<UserBackground> {
	
}