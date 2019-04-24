/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wf.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wf.entity.UserArticleLikeCollect;
import com.thinkgem.jeesite.modules.wf.dao.UserArticleLikeCollectDao;

/**
 * 用户文章点赞收藏用户管理Service
 * @author zsl
 * @version 2019-04-24
 */
@Service
@Transactional(readOnly = true)
public class UserArticleLikeCollectService extends CrudService<UserArticleLikeCollectDao, UserArticleLikeCollect> {

	public UserArticleLikeCollect get(String id) {
		return super.get(id);
	}

	public UserArticleLikeCollect findByUserIdAndArticleId(UserArticleLikeCollect userArticleLikeCollect) {
		return dao.findByUserIdAndArticleId(userArticleLikeCollect);
	}

	public List<UserArticleLikeCollect> findList(UserArticleLikeCollect userArticleLikeCollect) {
		return super.findList(userArticleLikeCollect);
	}
	
	public Page<UserArticleLikeCollect> findPage(Page<UserArticleLikeCollect> page, UserArticleLikeCollect userArticleLikeCollect) {
		return super.findPage(page, userArticleLikeCollect);
	}
	
	@Transactional(readOnly = false)
	public void save(UserArticleLikeCollect userArticleLikeCollect) {
		super.save(userArticleLikeCollect);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserArticleLikeCollect userArticleLikeCollect) {
		super.delete(userArticleLikeCollect);
	}
	
}