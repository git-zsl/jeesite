/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.custom.web;

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
import com.thinkgem.jeesite.modules.custom.entity.CustomCategory;
import com.thinkgem.jeesite.modules.custom.service.CustomCategoryService;

/**
 * 自定义栏目配置Controller
 * @author zsl
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/custom/customCategory")
public class CustomCategoryController extends BaseController {

	@Autowired
	private CustomCategoryService customCategoryService;
	
	@ModelAttribute
	public CustomCategory get(@RequestParam(required=false) String id) {
		CustomCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customCategoryService.get(id);
		}
		if (entity == null){
			entity = new CustomCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("custom:customCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomCategory customCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomCategory> page = customCategoryService.findPage(new Page<CustomCategory>(request, response), customCategory); 
		model.addAttribute("page", page);
		return "modules/custom/customCategoryList";
	}

	@RequiresPermissions("custom:customCategory:view")
	@RequestMapping(value = "form")
	public String form(CustomCategory customCategory, Model model) {
		model.addAttribute("customCategory", customCategory);
		return "modules/custom/customCategoryForm";
	}

	@RequiresPermissions("custom:customCategory:edit")
	@RequestMapping(value = "save")
	public String save(CustomCategory customCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customCategory)){
			return form(customCategory, model);
		}
		customCategoryService.save(customCategory);
		addMessage(redirectAttributes, "保存自定义栏目配置成功");
		return "redirect:"+Global.getAdminPath()+"/custom/customCategory/?repage";
	}
	
	@RequiresPermissions("custom:customCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomCategory customCategory, RedirectAttributes redirectAttributes) {
		customCategoryService.delete(customCategory);
		addMessage(redirectAttributes, "删除自定义栏目配置成功");
		return "redirect:"+Global.getAdminPath()+"/custom/customCategory/?repage";
	}

}