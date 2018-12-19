/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.posts.web;

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
import com.thinkgem.jeesite.modules.posts.entity.CmsPosts;
import com.thinkgem.jeesite.modules.posts.service.CmsPostsService;

import java.util.List;

/**
 * 分类Controller
 * @author zsl
 * @version 2018-12-19
 */
@Controller
@RequestMapping(value = "${adminPath}/posts/cmsPosts")
public class CmsPostsController extends BaseController {

	@Autowired
	private CmsPostsService cmsPostsService;
	
	@ModelAttribute
	public CmsPosts get(@RequestParam(required=false) String id) {
		CmsPosts entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsPostsService.get(id);
		}
		if (entity == null){
			entity = new CmsPosts();
		}
		return entity;
	}
	
	@RequiresPermissions("posts:cmsPosts:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsPosts cmsPosts, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CmsPosts> postsList = cmsPostsService.findPosts(cmsPosts);
		Page<CmsPosts> page = cmsPostsService.findPage(new Page<CmsPosts>(request, response), cmsPosts); 
		model.addAttribute("postsList", postsList);
		model.addAttribute("page", page);
		return "modules/posts/cmsPostsList";
	}

	@RequiresPermissions("posts:cmsPosts:view")
	@RequestMapping(value = "form")
	public String form(CmsPosts cmsPosts, Model model) {
		model.addAttribute("cmsPosts", cmsPosts);
		return "modules/posts/cmsPostsForm";
	}

	@RequiresPermissions("posts:cmsPosts:edit")
	@RequestMapping(value = "save")
	public String save(CmsPosts cmsPosts, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsPosts)){
			return form(cmsPosts, model);
		}
		cmsPostsService.save(cmsPosts);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/posts/cmsPosts/?repage";
	}
	
	@RequiresPermissions("posts:cmsPosts:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsPosts cmsPosts, RedirectAttributes redirectAttributes) {
		cmsPostsService.delete(cmsPosts);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/posts/cmsPosts/?repage";
	}

}