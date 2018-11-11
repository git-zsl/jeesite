/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.peruser.web;

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
import com.thinkgem.jeesite.modules.peruser.entity.PersonalUser;
import com.thinkgem.jeesite.modules.peruser.service.PersonalUserService;

/**
 * 个人用户Controller
 * @author zsl
 * @version 2018-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/peruser/personalUser")
public class PersonalUserController extends BaseController {

	@Autowired
	private PersonalUserService personalUserService;
	
	@ModelAttribute
	public PersonalUser get(@RequestParam(required=false) String id) {
		PersonalUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personalUserService.get(id);
		}
		if (entity == null){
			entity = new PersonalUser();
		}
		return entity;
	}
	
	@RequiresPermissions("peruser:personalUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonalUser personalUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonalUser> page = personalUserService.findPage(new Page<PersonalUser>(request, response), personalUser); 
		model.addAttribute("page", page);
		return "modules/peruser/personalUserList";
	}

	@RequiresPermissions("peruser:personalUser:view")
	@RequestMapping(value = "form")
	public String form(PersonalUser personalUser, Model model) {
		model.addAttribute("personalUser", personalUser);
		return "modules/peruser/personalUserForm";
	}

	@RequiresPermissions("peruser:personalUser:edit")
	@RequestMapping(value = "save")
	public String save(PersonalUser personalUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personalUser)){
			return form(personalUser, model);
		}
		personalUserService.save(personalUser);
		addMessage(redirectAttributes, "保存个人用户成功");
		return "redirect:"+Global.getAdminPath()+"/peruser/personalUser/?repage";
	}
	
	@RequiresPermissions("peruser:personalUser:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonalUser personalUser, RedirectAttributes redirectAttributes) {
		personalUserService.delete(personalUser);
		addMessage(redirectAttributes, "删除个人用户成功");
		return "redirect:"+Global.getAdminPath()+"/peruser/personalUser/?repage";
	}

}