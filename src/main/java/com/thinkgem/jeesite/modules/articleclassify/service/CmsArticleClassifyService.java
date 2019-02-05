/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.articleclassify.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.articleclassify.entity.CmsArticleClassify;
import com.thinkgem.jeesite.modules.articleclassify.dao.CmsArticleClassifyDao;

/**
 * 文章分类Service
 * @author zsl
 * @version 2019-02-05
 */
@Service
@Transactional(readOnly = true)
public class CmsArticleClassifyService extends CrudService<CmsArticleClassifyDao, CmsArticleClassify> {

	public CmsArticleClassify get(String id) {
		return super.get(id);
	}
	
	public List<CmsArticleClassify> findList(CmsArticleClassify cmsArticleClassify) {
		return super.findList(cmsArticleClassify);
	}
	
	public Page<CmsArticleClassify> findPage(Page<CmsArticleClassify> page, CmsArticleClassify cmsArticleClassify) {
		return super.findPage(page, cmsArticleClassify);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsArticleClassify cmsArticleClassify) {
		super.save(cmsArticleClassify);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsArticleClassify cmsArticleClassify) {
		super.delete(cmsArticleClassify);
	}
	
}