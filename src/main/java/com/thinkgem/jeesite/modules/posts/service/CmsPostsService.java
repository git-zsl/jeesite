/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.posts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.posts.entity.CmsPosts;
import com.thinkgem.jeesite.modules.posts.dao.CmsPostsDao;

/**
 * 分类Service
 * @author zsl
 * @version 2018-12-19
 */
@Service
@Transactional(readOnly = true)
public class CmsPostsService extends CrudService<CmsPostsDao, CmsPosts> {

	public CmsPosts get(String id) {
		return super.get(id);
	}
	
	public List<CmsPosts> findList(CmsPosts cmsPosts) {
		return super.findList(cmsPosts);
	}

	public List<CmsPosts> findPosts(CmsPosts cmsPosts) {
		return dao.findPosts(cmsPosts);
	}

	public Page<CmsPosts> findPage(Page<CmsPosts> page, CmsPosts cmsPosts) {
		return super.findPage(page, cmsPosts);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsPosts cmsPosts) {
		super.save(cmsPosts);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsPosts cmsPosts) {
		super.delete(cmsPosts);
	}
	
}