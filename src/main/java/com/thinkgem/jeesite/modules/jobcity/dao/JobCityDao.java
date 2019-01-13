/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobcity.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.jobcity.entity.JobCity;

import java.util.List;

/**
 * 招聘城市DAO接口
 * @author zsl
 * @version 2019-01-13
 */
@MyBatisDao
public interface JobCityDao extends CrudDao<JobCity> {
    public List<JobCity> findCode(JobCity jobCity);
	
}