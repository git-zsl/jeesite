/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classification.web;

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
import com.thinkgem.jeesite.modules.classification.entity.BookClassification;
import com.thinkgem.jeesite.modules.classification.service.BookClassificationService;

/**
 * 书籍分类Controller
 * @author zsl
 * @version 2018-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/classification/bookClassification")
public class BookClassificationController extends BaseController {

	@Autowired
	private BookClassificationService bookClassificationService;
	
	@ModelAttribute
	public BookClassification get(@RequestParam(required=false) String id) {
		BookClassification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookClassificationService.get(id);
		}
		if (entity == null){
			entity = new BookClassification();
		}
		return entity;
	}
	
	@RequiresPermissions("classification:bookClassification:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookClassification bookClassification, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookClassification> page = bookClassificationService.findPage(new Page<BookClassification>(request, response), bookClassification); 
		model.addAttribute("page", page);
		return "modules/classification/bookClassificationList";
	}

	@RequiresPermissions("classification:bookClassification:view")
	@RequestMapping(value = "form")
	public String form(BookClassification bookClassification, Model model) {
		model.addAttribute("bookClassification", bookClassification);
		return "modules/classification/bookClassificationForm";
	}

	@RequiresPermissions("classification:bookClassification:edit")
	@RequestMapping(value = "save")
	public String save(BookClassification bookClassification, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookClassification)){
			return form(bookClassification, model);
		}
		bookClassificationService.save(bookClassification);
		addMessage(redirectAttributes, "保存书籍分类成功");
		return "redirect:"+Global.getAdminPath()+"/classification/bookClassification/?repage";
	}
	
	@RequiresPermissions("classification:bookClassification:edit")
	@RequestMapping(value = "delete")
	public String delete(BookClassification bookClassification, RedirectAttributes redirectAttributes) {
		bookClassificationService.delete(bookClassification);
		addMessage(redirectAttributes, "删除书籍分类成功");
		return "redirect:"+Global.getAdminPath()+"/classification/bookClassification/?repage";
	}

}