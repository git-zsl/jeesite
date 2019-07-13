/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ad.entity.AdRequirement;
import com.thinkgem.jeesite.modules.ad.dao.AdRequirementDao;

/**
 * 广告需求Service
 * @author zsl
 * @version 2019-03-30
 */
@Service
@Transactional(readOnly = true)
public class AdRequirementService extends CrudService<AdRequirementDao, AdRequirement> {

	public AdRequirement get(String id) {
		return super.get(id);
	}
	
	public List<AdRequirement> findList(AdRequirement adRequirement) {
		return super.findList(adRequirement);
	}
	
	public Page<AdRequirement> findPage(Page<AdRequirement> page, AdRequirement adRequirement) {
		return super.findPage(page, adRequirement);
	}
	
	@Transactional(readOnly = false)
	public void save(AdRequirement adRequirement) {
		super.save(adRequirement);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdRequirement adRequirement) {
		super.delete(adRequirement);
	}

	@Transactional(readOnly = false)
	public void changeStatus(AdRequirement adRequirement) {
		dao.changeStatus(adRequirement);
	}

}