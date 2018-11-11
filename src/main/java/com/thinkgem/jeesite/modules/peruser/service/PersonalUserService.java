/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.peruser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.peruser.entity.PersonalUser;
import com.thinkgem.jeesite.modules.peruser.dao.PersonalUserDao;

/**
 * 个人用户Service
 * @author zsl
 * @version 2018-11-04
 */
@Service
@Transactional(readOnly = true)
public class PersonalUserService extends CrudService<PersonalUserDao, PersonalUser> {

	public PersonalUser get(String id) {
		return super.get(id);
	}
	
	public List<PersonalUser> findList(PersonalUser personalUser) {
		return super.findList(personalUser);
	}
	
	public Page<PersonalUser> findPage(Page<PersonalUser> page, PersonalUser personalUser) {
		return super.findPage(page, personalUser);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonalUser personalUser) {
		super.save(personalUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonalUser personalUser) {
		super.delete(personalUser);
	}
	
}