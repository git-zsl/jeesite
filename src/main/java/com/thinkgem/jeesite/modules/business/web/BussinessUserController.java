/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.business.web;

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
import com.thinkgem.jeesite.modules.business.entity.BussinessUser;
import com.thinkgem.jeesite.modules.business.service.BussinessUserService;

/**
 * 企业用户Controller
 * @author zsl
 * @version 2018-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/business/bussinessUser")
public class BussinessUserController extends BaseController {

	@Autowired
	private BussinessUserService bussinessUserService;
	
	@ModelAttribute
	public BussinessUser get(@RequestParam(required=false) String id) {
		BussinessUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bussinessUserService.get(id);
		}
		if (entity == null){
			entity = new BussinessUser();
		}
		return entity;
	}
	
	@RequiresPermissions("business:bussinessUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(BussinessUser bussinessUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BussinessUser> page = bussinessUserService.findPage(new Page<BussinessUser>(request, response), bussinessUser); 
		model.addAttribute("page", page);
		return "modules/business/bussinessUserList";
	}

	@RequiresPermissions("business:bussinessUser:view")
	@RequestMapping(value = "form")
	public String form(BussinessUser bussinessUser, Model model) {
		model.addAttribute("bussinessUser", bussinessUser);
		return "modules/business/bussinessUserForm";
	}

	@RequiresPermissions("business:bussinessUser:edit")
	@RequestMapping(value = "save")
	public String save(BussinessUser bussinessUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bussinessUser)){
			return form(bussinessUser, model);
		}
		bussinessUserService.save(bussinessUser);
		addMessage(redirectAttributes, "保存企业用户成功");
		return "redirect:"+Global.getAdminPath()+"/business/bussinessUser/?repage";
	}
	
	@RequiresPermissions("business:bussinessUser:edit")
	@RequestMapping(value = "delete")
	public String delete(BussinessUser bussinessUser, RedirectAttributes redirectAttributes) {
		bussinessUserService.delete(bussinessUser);
		addMessage(redirectAttributes, "删除企业用户成功");
		return "redirect:"+Global.getAdminPath()+"/business/bussinessUser/?repage";
	}

}