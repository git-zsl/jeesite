/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.area.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.area.entity.SysChina;

/**
 * 省市区对象DAO接口
 * @author zsl
 * @version 2019-05-13
 */
@MyBatisDao
public interface SysChinaDao extends CrudDao<SysChina> {
	
}