/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.artuser.service;

import java.util.List;
import java.util.Objects;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.persistence.ReturnStatus;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.artuser.entity.ArticleCollect;
import com.thinkgem.jeesite.modules.artuser.dao.ArticleCollectDao;

/**
 * 文章与用户收藏关系Service
 * @author zsl
 * @version 2019-01-19
 */
@Service
@Transactional(readOnly = true)
public class ArticleCollectService extends CrudService<ArticleCollectDao, ArticleCollect> {
	@Autowired
	private ArticleService articleService;

	public ArticleCollect get(String id) {
		return super.get(id);
	}
	
	public List<ArticleCollect> findList(ArticleCollect articleCollect) {
		return super.findList(articleCollect);
	}

	public List<ArticleCollect> findCollectUsers(ArticleCollect articleCollect) {
		return dao.findAllList(articleCollect);
	}

	public Page<ArticleCollect> findPage(Page<ArticleCollect> page, ArticleCollect articleCollect) {
		return super.findPage(page, articleCollect);
	}
	public Page<ArticleCollect> findHomeCollectPage(Page<ArticleCollect> page, ArticleCollect articleCollect) {
		return super.findHomeCollectPage(page, articleCollect);
	}
	public List<ArticleCollect> findHomeCollects(ArticleCollect articleCollect) {
		return dao.findHomeCollects(articleCollect);
	}

	@Transactional(readOnly = false)
	public void save(ArticleCollect articleCollect) {
		super.save(articleCollect);
	}
	
	@Transactional(readOnly = false)
	public void delete(ArticleCollect articleCollect) {
		super.delete(articleCollect);
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public ReturnEntity updateCollectNum(String userId , String articleId) throws Exception {
		User user = UserUtils.get(userId);
		Article article = articleService.get(articleId);
		if(Objects.isNull(user)){
			throw new RuntimeException("当前用户不存在，请登录后收藏");
		}
		if(Objects.isNull(article)){
			throw new RuntimeException("当前文章不存在，或者已经删除");
		}
		List<ArticleCollect> sameDatas = dao.findSameDatas(userId, articleId);
		if(!sameDatas.isEmpty()){
			dao.delete(sameDatas.get(0));
			articleService.deleteCollectNum(article);
			return new ReturnEntity(ReturnStatus.UNAUTHORIZED,"取消收藏");
		}
		articleService.updateCollectNum(article);
		ArticleCollect articleCollect = new ArticleCollect();
		articleCollect.setUser(user);
		articleCollect.setArticleId(articleId);
		save(articleCollect);
		return new ReturnEntity(ReturnStatus.OPTSUCCESS,"收藏成功");
	}
}