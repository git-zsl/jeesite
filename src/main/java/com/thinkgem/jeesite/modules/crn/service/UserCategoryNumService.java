/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crn.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.crn.entity.UserCategoryNum;
import com.thinkgem.jeesite.modules.crn.dao.UserCategoryNumDao;

/**
 * 栏目授权Service
 * @author zsl
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly = true)
public class UserCategoryNumService extends CrudService<UserCategoryNumDao, UserCategoryNum> {

	public UserCategoryNum get(String id) {
		return super.get(id);
	}

	public UserCategoryNum findByCategoryIdAndUserId(UserCategoryNum u) {
		return dao.findByCategoryIdAndUserId(u);
	}

	public List<UserCategoryNum> findList(UserCategoryNum userCategoryNum) {
		return super.findList(userCategoryNum);
	}
	
	public Page<UserCategoryNum> findNewPage(Page<UserCategoryNum> page, UserCategoryNum userCategoryNum) {
		return super.findNewPage(page, userCategoryNum);
	}
	
	@Transactional(readOnly = false)
	public void save(UserCategoryNum userCategoryNum) {
		super.save(userCategoryNum);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserCategoryNum userCategoryNum) {
		super.delete(userCategoryNum);
	}
	
}