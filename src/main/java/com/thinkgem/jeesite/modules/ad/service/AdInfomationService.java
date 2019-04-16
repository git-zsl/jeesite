/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private CategoryService categoryservice;

	public AdInfomation get(String id) {
		return super.get(id);
	}
	
	public List<AdInfomation> findList(AdInfomation adInfomation) {
		if (StringUtils.isNotBlank(adInfomation.getParentIds())){
			adInfomation.setParentIds(","+adInfomation.getParentIds()+",");
		}
		return super.findList(adInfomation);
	}

	public List<AdInfomation> findByArticleId(AdInfomation adInfomation) {
		return dao.findByArticleId(adInfomation);
	}

	public List<AdInfomation> getSortList(List<AdInfomation> lists) {
		Collections.sort(lists, new Comparator<AdInfomation>(){
			public int compare(AdInfomation p1, AdInfomation p2) {
				if (p1.getReleaseTime().getTime() > p2.getReleaseTime().getTime()) {
					return 1;
				}
				if (p1.getReleaseTime().getTime() == p2.getReleaseTime().getTime()) {
					return 0;
				}
				return -1;
			}
		});
		return lists;
	}

	/**
	 * 区分是历史，还是正在上架中广告
	 * @param lists
	 * @return
	 */
	public List<AdInfomation> getRuntimeOrHistoryADList(List<AdInfomation> lists,String flag) {
		Date date = new Date();
		long time = date.getTime();
		List<AdInfomation> historyADList = Lists.newArrayList();
		List<AdInfomation> runtimeADList = Lists.newArrayList();
		for (AdInfomation a : lists) {
			if(a.getReleaseTime().getTime() <= time && a.getSoldOutTime().getTime() >= time){
				runtimeADList.add(a);
			}
				historyADList.add(a);
		}
		if(Global.TRUE.equals(flag)){
			return runtimeADList;
		}
		return historyADList;
	}

	public List<AdInfomation> findAllList(AdInfomation adInfomation) {
		if (StringUtils.isNotBlank(adInfomation.getParentIds())){
			adInfomation.setParentIds(","+adInfomation.getParentIds()+",");
		}
		return dao.findAllList(adInfomation);
	}
	public List<AdInfomation> findConfigList(AdInfomation adInfomation) {
		if (StringUtils.isNotBlank(adInfomation.getParentIds())){
			adInfomation.setParentIds(","+adInfomation.getParentIds()+",");
		}
		return super.findConfigList(adInfomation);
	}

	@Transactional(readOnly = false)
	public void save(AdInfomation adInfomation) {
		super.save(adInfomation);
	}

	public List<AdInfomation> findByCategoryAndWinId(AdInfomation adInfomation) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		adInfomation.setNowDate(simpleDateFormat.format(date));
		List<AdInfomation> byCategoryAndWinId = dao.findByCategoryAndWinId(adInfomation);
		for (AdInfomation ad :byCategoryAndWinId) {
			Category category = categoryservice.get(ad.getCategory().getId());
			ad.setCategory(category);
		}
		return byCategoryAndWinId;
	}

	@Transactional(readOnly = false)
	public void delete(AdInfomation adInfomation) {
		super.delete(adInfomation);
	}

	public AdInfomation setAdInfomationData(Article article){
		AdInfomation adInfomation = new AdInfomation();
		adInfomation.setArticleId(article.getId());
		adInfomation.setDescription(article.getDescription());
		adInfomation.setName(article.getTitle());
		adInfomation.setLink(article.getLink());
		adInfomation.setImage(article.getImage());
		return adInfomation;
	}

	public AdInfomation setOldAdInfomationData(AdInfomation adInfomation,Article article){
		adInfomation.setDescription(article.getDescription());
		adInfomation.setArticleId(article.getId());
		adInfomation.setName(article.getTitle());
		adInfomation.setLink(article.getLink());
		adInfomation.setImage(article.getImage());
		return adInfomation;
	}
}