/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobselect.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.jobselect.entity.JobSelect;

/**
 * 招聘搜索条件DAO接口
 * @author zsl
 * @version 2019-01-14
 */
@MyBatisDao
public interface JobSelectDao extends TreeDao<JobSelect> {
	
}