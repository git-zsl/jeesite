/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.is_article.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.is_article.entity.CmsIsArticle;
import com.thinkgem.jeesite.modules.is_article.dao.CmsIsArticleDao;

/**
 * 属于文章categoryService
 * @author zsl
 * @version 2019-02-03
 */
@Service
@Transactional(readOnly = true)
public class CmsIsArticleService extends CrudService<CmsIsArticleDao, CmsIsArticle> {

	public CmsIsArticle get(String id) {
		return super.get(id);
	}
	
	public List<CmsIsArticle> findList(CmsIsArticle cmsIsArticle) {
		return super.findList(cmsIsArticle);
	}
	public List<String> findCategoryIds(CmsIsArticle cmsIsArticle) {
		return dao.findCategoryIds(cmsIsArticle);
	}

	public Page<CmsIsArticle> findPage(Page<CmsIsArticle> page, CmsIsArticle cmsIsArticle) {
		return super.findPage(page, cmsIsArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsIsArticle cmsIsArticle) {
		super.save(cmsIsArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsIsArticle cmsIsArticle) {
		super.delete(cmsIsArticle);
	}
	
}