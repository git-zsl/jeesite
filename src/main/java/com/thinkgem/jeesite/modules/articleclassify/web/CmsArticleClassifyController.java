/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.articleclassify.web;

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
import com.thinkgem.jeesite.modules.articleclassify.entity.CmsArticleClassify;
import com.thinkgem.jeesite.modules.articleclassify.service.CmsArticleClassifyService;

/**
 * 文章分类Controller
 * @author zsl
 * @version 2019-02-05
 */
@Controller
@RequestMapping(value = "${adminPath}/articleclassify/cmsArticleClassify")
public class CmsArticleClassifyController extends BaseController {

	@Autowired
	private CmsArticleClassifyService cmsArticleClassifyService;
	
	@ModelAttribute
	public CmsArticleClassify get(@RequestParam(required=false) String id) {
		CmsArticleClassify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsArticleClassifyService.get(id);
		}
		if (entity == null){
			entity = new CmsArticleClassify();
		}
		return entity;
	}
	
	@RequiresPermissions("articleclassify:cmsArticleClassify:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsArticleClassify cmsArticleClassify, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsArticleClassify> page = cmsArticleClassifyService.findPage(new Page<CmsArticleClassify>(request, response), cmsArticleClassify); 
		model.addAttribute("page", page);
		return "modules/articleclassify/cmsArticleClassifyList";
	}

	@RequiresPermissions("articleclassify:cmsArticleClassify:view")
	@RequestMapping(value = "form")
	public String form(CmsArticleClassify cmsArticleClassify, Model model) {
		model.addAttribute("cmsArticleClassify", cmsArticleClassify);
		return "modules/articleclassify/cmsArticleClassifyForm";
	}

	@RequiresPermissions("articleclassify:cmsArticleClassify:edit")
	@RequestMapping(value = "save")
	public String save(CmsArticleClassify cmsArticleClassify, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsArticleClassify)){
			return form(cmsArticleClassify, model);
		}
		cmsArticleClassifyService.save(cmsArticleClassify);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/articleclassify/cmsArticleClassify/?repage";
	}
	
	@RequiresPermissions("articleclassify:cmsArticleClassify:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsArticleClassify cmsArticleClassify, RedirectAttributes redirectAttributes) {
		cmsArticleClassifyService.delete(cmsArticleClassify);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/articleclassify/cmsArticleClassify/?repage";
	}

}