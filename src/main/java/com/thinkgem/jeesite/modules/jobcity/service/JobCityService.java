/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobcity.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.jobcity.entity.JobCity;
import com.thinkgem.jeesite.modules.jobcity.dao.JobCityDao;

/**
 * 招聘城市Service
 * @author zsl
 * @version 2019-01-13
 */
@Service
@Transactional(readOnly = true)
public class JobCityService extends CrudService<JobCityDao, JobCity> {

	public JobCity get(String id) {
		return super.get(id);
	}
	
	public List<JobCity> findList(JobCity jobCity) {
		return super.findList(jobCity);
	}
	
	public Page<JobCity> findPage(Page<JobCity> page, JobCity jobCity) {
		return super.findPage(page, jobCity);
	}
	
	@Transactional(readOnly = false)
	public void save(JobCity jobCity) {
		super.save(jobCity);
	}
	
	@Transactional(readOnly = false)
	public void delete(JobCity jobCity) {
		super.delete(jobCity);
	}

	public List<JobCity> findCode(JobCity jobCity){
		return dao.findCode(jobCity);
	}
	
}