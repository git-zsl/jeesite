/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.move.web;

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
import com.thinkgem.jeesite.modules.move.entity.Createmenu;
import com.thinkgem.jeesite.modules.move.service.CreatemenuService;

/**
 * 移动Controller
 * @author zsl
 * @version 2018-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/move/createmenu")
public class CreatemenuController extends BaseController {

	@Autowired
	private CreatemenuService createmenuService;
	
	@ModelAttribute
	public Createmenu get(@RequestParam(required=false) String id) {
		Createmenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = createmenuService.get(id);
		}
		if (entity == null){
			entity = new Createmenu();
		}
		return entity;
	}
	
	@RequiresPermissions("move:createmenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(Createmenu createmenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Createmenu> page = createmenuService.findPage(new Page<Createmenu>(request, response), createmenu); 
		model.addAttribute("page", page);
		return "modules/move/createmenuList";
	}

	@RequiresPermissions("move:createmenu:view")
	@RequestMapping(value = "form")
	public String form(Createmenu createmenu, Model model) {
		model.addAttribute("createmenu", createmenu);
		return "modules/move/createmenuForm";
	}

	@RequiresPermissions("move:createmenu:edit")
	@RequestMapping(value = "save")
	public String save(Createmenu createmenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, createmenu)){
			return form(createmenu, model);
		}
		createmenuService.save(createmenu);
		addMessage(redirectAttributes, "保存移动成功");
		return "redirect:"+Global.getAdminPath()+"/move/createmenu/?repage";
	}
	
	@RequiresPermissions("move:createmenu:edit")
	@RequestMapping(value = "delete")
	public String delete(Createmenu createmenu, RedirectAttributes redirectAttributes) {
		createmenuService.delete(createmenu);
		addMessage(redirectAttributes, "删除移动成功");
		return "redirect:"+Global.getAdminPath()+"/move/createmenu/?repage";
	}

}