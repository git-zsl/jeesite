/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userbackground.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.userbackground.entity.UserBackground;
import com.thinkgem.jeesite.modules.userbackground.dao.UserBackgroundDao;

/**
 * 用户背景管理Service
 * @author zsl
 * @version 2019-05-19
 */
@Service
@Transactional(readOnly = true)
public class UserBackgroundService extends CrudService<UserBackgroundDao, UserBackground> {

	public UserBackground get(String id) {
		return super.get(id);
	}
	
	public List<UserBackground> findList(UserBackground userBackground) {
		return super.findList(userBackground);
	}
	
	public Page<UserBackground> findPage(Page<UserBackground> page, UserBackground userBackground) {
		return super.findPage(page, userBackground);
	}
	
	@Transactional(readOnly = false)
	public void save(UserBackground userBackground) {
		super.save(userBackground);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserBackground userBackground) {
		super.delete(userBackground);
	}
	
}