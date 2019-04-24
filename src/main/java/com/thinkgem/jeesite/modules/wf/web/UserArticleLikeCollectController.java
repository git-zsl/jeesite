/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wf.web;

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
import com.thinkgem.jeesite.modules.wf.entity.UserArticleLikeCollect;
import com.thinkgem.jeesite.modules.wf.service.UserArticleLikeCollectService;

/**
 * 用户文章点赞收藏用户管理Controller
 * @author zsl
 * @version 2019-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/wf/userArticleLikeCollect")
public class UserArticleLikeCollectController extends BaseController {

	@Autowired
	private UserArticleLikeCollectService userArticleLikeCollectService;
	
	@ModelAttribute
	public UserArticleLikeCollect get(@RequestParam(required=false) String id) {
		UserArticleLikeCollect entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userArticleLikeCollectService.get(id);
		}
		if (entity == null){
			entity = new UserArticleLikeCollect();
		}
		return entity;
	}
	
	@RequiresPermissions("wf:userArticleLikeCollect:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserArticleLikeCollect userArticleLikeCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserArticleLikeCollect> page = userArticleLikeCollectService.findPage(new Page<UserArticleLikeCollect>(request, response), userArticleLikeCollect); 
		model.addAttribute("page", page);
		return "modules/wf/userArticleLikeCollectList";
	}

	@RequiresPermissions("wf:userArticleLikeCollect:view")
	@RequestMapping(value = "form")
	public String form(UserArticleLikeCollect userArticleLikeCollect, Model model) {
		model.addAttribute("userArticleLikeCollect", userArticleLikeCollect);
		return "modules/wf/userArticleLikeCollectForm";
	}

	@RequiresPermissions("wf:userArticleLikeCollect:edit")
	@RequestMapping(value = "save")
	public String save(UserArticleLikeCollect userArticleLikeCollect, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userArticleLikeCollect)){
			return form(userArticleLikeCollect, model);
		}
		userArticleLikeCollectService.save(userArticleLikeCollect);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/wf/userArticleLikeCollect/?repage";
	}
	
	@RequiresPermissions("wf:userArticleLikeCollect:edit")
	@RequestMapping(value = "delete")
	public String delete(UserArticleLikeCollect userArticleLikeCollect, RedirectAttributes redirectAttributes) {
		userArticleLikeCollectService.delete(userArticleLikeCollect);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/wf/userArticleLikeCollect/?repage";
	}

}