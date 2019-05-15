/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.area.web;

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
import com.thinkgem.jeesite.modules.area.entity.SysChina;
import com.thinkgem.jeesite.modules.area.service.SysChinaService;

/**
 * 省市区对象Controller
 * @author zsl
 * @version 2019-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/area/sysChina")
public class SysChinaController extends BaseController {

	@Autowired
	private SysChinaService sysChinaService;
	
	@ModelAttribute
	public SysChina get(@RequestParam(required=false) String id) {
		SysChina entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysChinaService.get(id);
		}
		if (entity == null){
			entity = new SysChina();
		}
		return entity;
	}
	
	@RequiresPermissions("area:sysChina:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysChina sysChina, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysChina> page = sysChinaService.findPage(new Page<SysChina>(request, response), sysChina); 
		model.addAttribute("page", page);
		return "modules/area/sysChinaList";
	}

	@RequiresPermissions("area:sysChina:view")
	@RequestMapping(value = "form")
	public String form(SysChina sysChina, Model model) {
		model.addAttribute("sysChina", sysChina);
		return "modules/area/sysChinaForm";
	}

	@RequiresPermissions("area:sysChina:edit")
	@RequestMapping(value = "save")
	public String save(SysChina sysChina, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysChina)){
			return form(sysChina, model);
		}
		sysChinaService.save(sysChina);
		addMessage(redirectAttributes, "保存数据成功");
		return "redirect:"+Global.getAdminPath()+"/area/sysChina/?repage";
	}
	
	@RequiresPermissions("area:sysChina:edit")
	@RequestMapping(value = "delete")
	public String delete(SysChina sysChina, RedirectAttributes redirectAttributes) {
		sysChinaService.delete(sysChina);
		addMessage(redirectAttributes, "删除数据成功");
		return "redirect:"+Global.getAdminPath()+"/area/sysChina/?repage";
	}

}