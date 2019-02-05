/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.is_article.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.is_article.entity.CmsIsArticle;
import com.thinkgem.jeesite.modules.is_article.service.CmsIsArticleService;

/**
 * 属于文章categoryController
 * @author zsl
 * @version 2019-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/is_article/cmsIsArticle")
public class CmsIsArticleController extends BaseController {

	@Autowired
	private CmsIsArticleService cmsIsArticleService;
	
	@ModelAttribute
	public CmsIsArticle get(@RequestParam(required=false) String id) {
		CmsIsArticle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsIsArticleService.get(id);
		}
		if (entity == null){
			entity = new CmsIsArticle();
		}
		return entity;
	}
	
	@RequiresPermissions("is_article:cmsIsArticle:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsIsArticle cmsIsArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsIsArticle> page = cmsIsArticleService.findPage(new Page<CmsIsArticle>(request, response), cmsIsArticle); 
		model.addAttribute("page", page);
		return "modules/is_article/cmsIsArticleList";
	}

	@RequiresPermissions("is_article:cmsIsArticle:view")
	@RequestMapping(value = "form")
	public String form(CmsIsArticle cmsIsArticle, Model model) {
		model.addAttribute("cmsIsArticle", cmsIsArticle);
		return "modules/is_article/cmsIsArticleForm";
	}

	@RequiresPermissions("is_article:cmsIsArticle:edit")
	@RequestMapping(value = "save")
	public String save(CmsIsArticle cmsIsArticle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsIsArticle)){
			return form(cmsIsArticle, model);
		}
		cmsIsArticleService.save(cmsIsArticle);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/is_article/cmsIsArticle/?repage";
	}
	
	@RequiresPermissions("is_article:cmsIsArticle:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsIsArticle cmsIsArticle, RedirectAttributes redirectAttributes) {
		cmsIsArticleService.delete(cmsIsArticle);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/is_article/cmsIsArticle/?repage";
	}

}