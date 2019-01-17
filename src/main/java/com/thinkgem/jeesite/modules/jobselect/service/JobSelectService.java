/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobselect.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.jobselect.entity.JobSelect;
import com.thinkgem.jeesite.modules.jobselect.dao.JobSelectDao;

/**
 * 招聘搜索条件Service
 * @author zsl
 * @version 2019-01-14
 */
@Service
@Transactional(readOnly = true)
public class JobSelectService extends TreeService<JobSelectDao, JobSelect> {

	public JobSelect get(String id) {
		return super.get(id);
	}
	
	public List<JobSelect> findList(JobSelect jobSelect) {
		if (StringUtils.isNotBlank(jobSelect.getParentIds())){
			jobSelect.setParentIds(","+jobSelect.getParentIds()+",");
		}
		return super.findList(jobSelect);
	}
	
	@Transactional(readOnly = false)
	public void save(JobSelect jobSelect) {
		super.save(jobSelect);
	}
	
	@Transactional(readOnly = false)
	public void delete(JobSelect jobSelect) {
		super.delete(jobSelect);
	}
	
}