/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.custom.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.custom.entity.CustomCategory;

/**
 * 自定义栏目配置DAO接口
 * @author zsl
 * @version 2018-12-12
 */
@MyBatisDao
public interface CustomCategoryDao extends CrudDao<CustomCategory> {
	public CustomCategory findByMark(String key);
}