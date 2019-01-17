/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classifying.web;

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
import com.thinkgem.jeesite.modules.classifying.entity.CmsClassifying;
import com.thinkgem.jeesite.modules.classifying.service.CmsClassifyingService;

/**
 * 全局分类Controller
 * @author zsl
 * @version 2019-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/classifying/cmsClassifying")
public class CmsClassifyingController extends BaseController {

	@Autowired
	private CmsClassifyingService cmsClassifyingService;
	
	@ModelAttribute
	public CmsClassifying get(@RequestParam(required=false) String id) {
		CmsClassifying entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsClassifyingService.get(id);
		}
		if (entity == null){
			entity = new CmsClassifying();
		}
		return entity;
	}
	
	@RequiresPermissions("classifying:cmsClassifying:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsClassifying cmsClassifying, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsClassifying> page = cmsClassifyingService.findPage(new Page<CmsClassifying>(request, response), cmsClassifying); 
		model.addAttribute("page", page);
		return "modules/classifying/cmsClassifyingList";
	}

	@RequiresPermissions("classifying:cmsClassifying:view")
	@RequestMapping(value = "form")
	public String form(CmsClassifying cmsClassifying, Model model) {
		model.addAttribute("cmsClassifying", cmsClassifying);
		return "modules/classifying/cmsClassifyingForm";
	}

	@RequiresPermissions("classifying:cmsClassifying:edit")
	@RequestMapping(value = "save")
	public String save(CmsClassifying cmsClassifying, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsClassifying)){
			return form(cmsClassifying, model);
		}
		cmsClassifyingService.save(cmsClassifying);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/classifying/cmsClassifying/?repage";
	}
	
	@RequiresPermissions("classifying:cmsClassifying:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsClassifying cmsClassifying, RedirectAttributes redirectAttributes) {
		cmsClassifyingService.delete(cmsClassifying);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/classifying/cmsClassifying/?repage";
	}

}