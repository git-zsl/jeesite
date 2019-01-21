/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attention.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.attention.entity.UserAttentionUserids;
import com.thinkgem.jeesite.modules.attention.dao.UserAttentionUseridsDao;

/**
 * 关注人关系Service
 * @author zsl
 * @version 2019-01-20
 */
@Service
@Transactional(readOnly = true)
public class UserAttentionUseridsService extends CrudService<UserAttentionUseridsDao, UserAttentionUserids> {

	@Autowired
	private UserDao userDao;

	public UserAttentionUserids get(String id) {
		return super.get(id);
	}
	
	public List<UserAttentionUserids> findList(UserAttentionUserids userAttentionUserids) {
		return super.findList(userAttentionUserids);
	}

	public List<UserAttentionUserids> findByIds(String attentionUserIds) {
		return dao.findByIds(attentionUserIds);
	}

	public Page<UserAttentionUserids> findPage(Page<UserAttentionUserids> page, UserAttentionUserids userAttentionUserids) {
		return super.findPage(page, userAttentionUserids);
	}
	
	@Transactional(readOnly = false)
	public void save(UserAttentionUserids userAttentionUserids) {
		super.save(userAttentionUserids);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserAttentionUserids userAttentionUserids) {
		super.delete(userAttentionUserids);
	}

	@Transactional(readOnly = false)
	public void updateUserData(User user){
		user.preUpdate();
		userDao.update(user);
	}
}