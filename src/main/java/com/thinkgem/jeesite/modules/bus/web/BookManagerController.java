/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.classification.entity.BookClassification;
import com.thinkgem.jeesite.modules.classification.service.BookClassificationService;
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
import com.thinkgem.jeesite.modules.bus.entity.BookManager;
import com.thinkgem.jeesite.modules.bus.service.BookManagerService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 书籍管理Controller
 * @author zsl
 * @version 2018-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/bus/bookManager")
public class BookManagerController extends BaseController {

	@Autowired
	private BookManagerService bookManagerService;
	@Autowired
	private BookClassificationService bookClassificationService;
	
	@ModelAttribute
	public BookManager get(@RequestParam(required=false) String id) {
		BookManager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookManagerService.get(id);
		}
		if (entity == null){
			entity = new BookManager();
		}
		return entity;
	}
	
	@RequiresPermissions("bus:bookManager:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookManager bookManager, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookManager> page = bookManagerService.findPage(new Page<BookManager>(request, response), bookManager); 
		model.addAttribute("page", page);
		List<BookClassification> list = bookClassificationService.findList(new BookClassification());
		Set<String> classifications = new HashSet<String>();
		for ( BookClassification b: list) {
			classifications.add(b.getClassification());
		}
		model.addAttribute("classifications", classifications);
		return "modules/bus/bookManagerList";
	}

	@RequiresPermissions("bus:bookManager:view")
	@RequestMapping(value = "form")
	public String form(BookManager bookManager, Model model) {

		List<BookClassification> list = bookClassificationService.findList(new BookClassification());
		Set<String> classifications = new HashSet<String>();
		for ( BookClassification b: list) {
			classifications.add(b.getClassification());
		}
		model.addAttribute("classifications", classifications);
		model.addAttribute("bookManager", bookManager);
		return "modules/bus/bookManagerForm";
	}

	@RequiresPermissions("bus:bookManager:edit")
	@RequestMapping(value = "save")
	public String save(BookManager bookManager, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookManager)){
			return form(bookManager, model);
		}
		bookManagerService.save(bookManager);
		addMessage(redirectAttributes, "保存书籍成功");
		return "redirect:"+Global.getAdminPath()+"/bus/bookManager/?repage";
	}
	
	@RequiresPermissions("bus:bookManager:edit")
	@RequestMapping(value = "delete")
	public String delete(BookManager bookManager, RedirectAttributes redirectAttributes) {
		bookManagerService.delete(bookManager);
		addMessage(redirectAttributes, "删除书籍成功");
		return "redirect:"+Global.getAdminPath()+"/bus/bookManager/?repage";
	}

	@RequestMapping(value = "text")
	@ResponseBody
	public String text(@RequestParam("loginName") String loginName,@RequestParam("password") String password) {
		System.out.println(loginName);
		System.out.println(password);
		return "OK";
	}

}