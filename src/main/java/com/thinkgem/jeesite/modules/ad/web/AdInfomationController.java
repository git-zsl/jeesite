/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ad.entity.AdInfomation;
import com.thinkgem.jeesite.modules.ad.service.AdInfomationService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.entity.Comment;
import com.thinkgem.jeesite.modules.cms.service.ArticleDataService;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.Cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 广告信息Controller
 * @author zsl
 * @version 2019-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/ad/adInfomation")
public class AdInfomationController extends BaseController {

	@Autowired
	private AdInfomationService adInfomationService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;

	@ModelAttribute
	public AdInfomation get(@RequestParam(required=false) String id) {
		AdInfomation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adInfomationService.get(id);
		}
		if (entity == null){
			entity = new AdInfomation();
		}
		return entity;
	}
	
	@RequiresPermissions("ad:adInfomation:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdInfomation adInfomation, HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required = false) String show) {
		List<AdInfomation> sourcelist = null;
		List<AdInfomation> list = Lists.newArrayList();
		if(StringUtils.isNotBlank(show)){
			sourcelist = adInfomationService.findList(adInfomation);
			AdInfomation.sortList(list, sourcelist, Global.NO);
			model.addAttribute("list", list);
			model.addAttribute("categoryId", adInfomation.getCategory().getId());
			return "modules/ad/adInfomationNewList";
		}
		sourcelist = adInfomationService.findConfigList(adInfomation);
		/*AdInfomation.sortList(list, sourcelist, Global.NO);*/
		model.addAttribute("list", sourcelist);
		return "modules/ad/adInfomationList";
	}

	@RequiresPermissions("ad:adInfomation:view")
	@RequestMapping(value = "form")
	public String form(AdInfomation adInfomation, Model model) {
		if (adInfomation.getCategory()!=null && StringUtils.isNotBlank(adInfomation.getCategory().getId())){
			Category category = categoryService.get(adInfomation.getCategory().getId());
			adInfomation.setCategory(category);
		}
		if (adInfomation.getParent()!=null && StringUtils.isNotBlank(adInfomation.getParent().getId())){
			adInfomation.setParent(adInfomationService.get(adInfomation.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(adInfomation.getId())){
				AdInfomation adInfomationChild = new AdInfomation();
				adInfomationChild.setParent(new AdInfomation(adInfomation.getParent().getId()));
				List<AdInfomation> list = adInfomationService.findList(adInfomation); 
				if (list.size() > 0){
					adInfomation.setSort(list.get(list.size()-1).getSort());
					if (adInfomation.getSort() != null){
						adInfomation.setSort(adInfomation.getSort() + 30);
					}
				}
			}
		}
		if (adInfomation.getSort() == null){
			adInfomation.setSort(30);
		}
		model.addAttribute("adInfomation", adInfomation);
		return "modules/ad/adInfomationForm";
	}

	@RequiresPermissions("ad:adInfomation:edit")
	@RequestMapping(value = "save")
	public String save(AdInfomation adInfomation, Category category, Model model, RedirectAttributes redirectAttributes) {
		adInfomation.setPromulgator(UserUtils.getUser().getLoginName());
		Article article = articleService.get(adInfomation.getArticleId());
		ArticleData articleData = articleDataService.get(adInfomation.getArticleId());
		article.setArticleData(articleData);
		if(Objects.nonNull(article) && StringUtils.isNotBlank(article.getIsPutaway()) && !Global.YES.equals(article.getIsPutaway())){
			article.setIsPutaway(Global.YES);
			articleService.save(article);
		}
		if (!beanValidator(model, adInfomation)){
			return form(adInfomation, model);
		}
		adInfomationService.save(adInfomation);
		addMessage(redirectAttributes, "保存广告信息成功");
		return "redirect:"+Global.getAdminPath()+"/ad/adInfomation?category.id="+adInfomation.getCategory().getId()+"&show=1&repage";
	}

	/**
	 * 批量修改排序
	 */
	@RequiresPermissions("ad:adInfomation:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String categoryId,String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
		int len = ids.length;
		AdInfomation[] entitys = new AdInfomation[len];
		for (int i = 0; i < len; i++) {
			entitys[i] = adInfomationService.get(ids[i]);
			entitys[i].setSort(sorts[i]);
			adInfomationService.save(entitys[i]);
		}
		addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:" + adminPath + "/ad/adInfomation?show=1&category.id="+categoryId;
	}


	@RequiresPermissions("ad:adInfomation:edit")
	@RequestMapping(value = "delete")
	public String delete(AdInfomation adInfomation, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(adInfomation.getArticleId())){
			Article article = articleService.get(adInfomation.getArticleId());
			ArticleData articleData = articleDataService.get(adInfomation.getArticleId());
			article.setArticleData(articleData);
			article.setIsPutaway(Global.NO);
			articleService.save(article);
		}
		adInfomationService.delete(adInfomation);
		addMessage(redirectAttributes, "删除广告信息成功");
		return "redirect:"+Global.getAdminPath()+"/ad/adInfomation?category.id="+adInfomation.getCategory().getId()+"&show=1&repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AdInfomation> list = adInfomationService.findAllList(new AdInfomation());
		for (int i=0; i<list.size(); i++){
			AdInfomation e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}


	/**
	 * 获取所有广告窗信息接口
	 */
	@RequestMapping("filter/findAllAdInfomationList")
	@ResponseBody
	public ReturnEntity findAllAdInfomationList(AdInfomation adInfomation){
		List<AdInfomation> list = null;
		List<AdInfomation> newConfigList = Lists.newArrayList();
		try{
			list = adInfomationService.findConfigList(adInfomation);
			if(!list.isEmpty()){
				for (AdInfomation c : list) {
					for (AdInfomation cc : list) {
						if(c.getId().equals(cc.getParentId())){
							c.getChilderAdInfomations().add(cc);
						}
					}
					if(StringUtils.isBlank(c.getParentId()) || Global.NO.equals(c.getParentId())){
						newConfigList.add(c);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			LogUtils.getLogInfo(AdInfomationController.class).info("程序出错",e);
		}
		return ReturnEntity.success(newConfigList,"查询成功");
	}

	/**
	 * 广告窗对应广告查询接口
	 */
	@RequestMapping("filter/findAdWindowAdInfomationList")
	@ResponseBody
	public ReturnEntity findAdWindowAdInfomationList(@ModelAttribute AdInfomation adInfomation,@RequestParam("categoryId") String categoryId){
		Category category = new Category(categoryId);
		adInfomation.setCategory(category);
		List<AdInfomation> list = null;
		try{
			list = adInfomationService.findByCategoryAndWinId(adInfomation);
		}catch (Exception e){
			e.printStackTrace();
			LogUtils.getLogInfo(AdInfomationController.class).info("程序出错",e);
		}
		return ReturnEntity.success(list,"查询成功");
	}
}