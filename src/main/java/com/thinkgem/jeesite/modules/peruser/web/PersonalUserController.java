/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.peruser.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
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

import java.util.List;

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
	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;

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
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Office office = new Office();
		office.setName("个人用户");
		List<Office> listByName = officeService.findListByName(office);
		user.setOffice(listByName.get(0));
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		if(user.getDelFlag().equals("1")){
			page.setList(systemService.findBlacklist(user));
		}
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
	public String delete(User user, RedirectAttributes redirectAttributes) {
		personalUserService.deleteOrrecover(user);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/peruser/personalUser/?repage";
	}



}