/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.service;

import java.util.List;

import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ad.entity.AdInfomation;
import com.thinkgem.jeesite.modules.ad.dao.AdInfomationDao;

/**
 * 广告信息Service
 * @author zsl
 * @version 2019-02-23
 */
@Service
@Transactional(readOnly = true)
public class AdInfomationService extends TreeService<AdInfomationDao, AdInfomation> {

	public AdInfomation get(String id) {
		return super.get(id);
	}
	
	public List<AdInfomation> findList(AdInfomation adInfomation) {
		if (StringUtils.isNotBlank(adInfomation.getParentIds())){
			adInfomation.setParentIds(","+adInfomation.getParentIds()+",");
		}
		return super.findList(adInfomation);
	}
	
	@Transactional(readOnly = false)
	public void save(AdInfomation adInfomation) {
		super.save(adInfomation);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdInfomation adInfomation) {
		super.delete(adInfomation);
	}

	public AdInfomation setAdInfomationData(Article article){
		AdInfomation adInfomation = new AdInfomation();
		adInfomation.setArticleId(article.getId());
		adInfomation.setName(article.getTitle());
		return adInfomation;
	}
}