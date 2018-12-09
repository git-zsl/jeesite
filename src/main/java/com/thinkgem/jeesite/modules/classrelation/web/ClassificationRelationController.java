/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classrelation.web;

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
import com.thinkgem.jeesite.modules.classrelation.entity.ClassificationRelation;
import com.thinkgem.jeesite.modules.classrelation.service.ClassificationRelationService;

/**
 * 分类关系Controller
 * @author zsl
 * @version 2018-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/classrelation/classificationRelation")
public class ClassificationRelationController extends BaseController {

	@Autowired
	private ClassificationRelationService classificationRelationService;
	
	@ModelAttribute
	public ClassificationRelation get(@RequestParam(required=false) String id) {
		ClassificationRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = classificationRelationService.get(id);
		}
		if (entity == null){
			entity = new ClassificationRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("classrelation:classificationRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClassificationRelation classificationRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClassificationRelation> page = classificationRelationService.findPage(new Page<ClassificationRelation>(request, response), classificationRelation); 
		model.addAttribute("page", page);
		return "modules/classrelation/classificationRelationList";
	}

	@RequiresPermissions("classrelation:classificationRelation:view")
	@RequestMapping(value = "form")
	public String form(ClassificationRelation classificationRelation, Model model) {
		model.addAttribute("classificationRelation", classificationRelation);
		return "modules/classrelation/classificationRelationForm";
	}

	@RequiresPermissions("classrelation:classificationRelation:edit")
	@RequestMapping(value = "save")
	public String save(ClassificationRelation classificationRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, classificationRelation)){
			return form(classificationRelation, model);
		}
		classificationRelationService.save(classificationRelation);
		addMessage(redirectAttributes, "保存分类关系成功");
		return "redirect:"+Global.getAdminPath()+"/classrelation/classificationRelation/?repage";
	}
	
	@RequiresPermissions("classrelation:classificationRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(ClassificationRelation classificationRelation, RedirectAttributes redirectAttributes) {
		classificationRelationService.delete(classificationRelation);
		addMessage(redirectAttributes, "删除分类关系成功");
		return "redirect:"+Global.getAdminPath()+"/classrelation/classificationRelation/?repage";
	}

}