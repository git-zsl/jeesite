/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.crn.entity.UserCategoryNum;
import com.thinkgem.jeesite.modules.crn.service.UserCategoryNumService;

import java.util.List;

/**
 * 栏目授权Controller
 * @author zsl
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/crn/userCategoryNum")
public class UserCategoryNumController extends BaseController {

	@Autowired
	private UserCategoryNumService userCategoryNumService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ArticleService articleService;
	public static final String  TOP_PARENT_ID= "1";

	@ModelAttribute
	public UserCategoryNum get(@RequestParam(required=false) String id) {
		UserCategoryNum entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userCategoryNumService.get(id);
		}
		if (entity == null){
			entity = new UserCategoryNum();
		}
		return entity;
	}
	
	@RequiresPermissions("crn:userCategoryNum:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserCategoryNum userCategoryNum, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCategoryNum> page = userCategoryNumService.findPage(new Page<UserCategoryNum>(request, response), userCategoryNum);
		model.addAttribute("page", page);
		return "modules/crn/userCategoryNumList";
	}

	@RequiresPermissions("crn:userCategoryNum:view")
	@RequestMapping(value = "form")
	public String form(UserCategoryNum userCategoryNum, Model model) {
		List<Category> categorys = categoryService.findCategorysByParentId(TOP_PARENT_ID);
		model.addAttribute("categorys", categorys);
		model.addAttribute("userCategoryNum", userCategoryNum);
		return "modules/crn/userCategoryNumForm";
	}

	@RequiresPermissions("crn:userCategoryNum:edit")
	@RequestMapping(value = "save")
	public String save(UserCategoryNum userCategoryNum, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userCategoryNum)){
			return form(userCategoryNum, model);
		}
		userCategoryNumService.save(userCategoryNum);
		addMessage(redirectAttributes, "保存栏目数量成功");
		return "redirect:"+Global.getAdminPath()+"/crn/userCategoryNum/?repage";
	}
	
	@RequiresPermissions("crn:userCategoryNum:edit")
	@RequestMapping(value = "delete")
	public String delete(UserCategoryNum userCategoryNum, RedirectAttributes redirectAttributes) {
		userCategoryNumService.delete(userCategoryNum);
		addMessage(redirectAttributes, "删除栏目数量成功");
		return "redirect:"+Global.getAdminPath()+"/crn/userCategoryNum/?repage";
	}

	@RequestMapping(value = "ownNum")
	@ResponseBody
	public Integer findOwnNum(String id,String createBy) {
		Integer ownNum = articleService.findOwnNum(id,createBy);
		return ownNum;
	}
}