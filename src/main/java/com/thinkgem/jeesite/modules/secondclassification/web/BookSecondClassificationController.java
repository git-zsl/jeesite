/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.secondclassification.web;

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
import com.thinkgem.jeesite.modules.secondclassification.entity.BookSecondClassification;
import com.thinkgem.jeesite.modules.secondclassification.service.BookSecondClassificationService;

/**
 * 二级分类Controller
 * @author zsl
 * @version 2018-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/secondclassification/bookSecondClassification")
public class BookSecondClassificationController extends BaseController {

	@Autowired
	private BookSecondClassificationService bookSecondClassificationService;
	
	@ModelAttribute
	public BookSecondClassification get(@RequestParam(required=false) String id) {
		BookSecondClassification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookSecondClassificationService.get(id);
		}
		if (entity == null){
			entity = new BookSecondClassification();
		}
		return entity;
	}
	
	@RequiresPermissions("secondclassification:bookSecondClassification:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookSecondClassification bookSecondClassification, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookSecondClassification> page = bookSecondClassificationService.findPage(new Page<BookSecondClassification>(request, response), bookSecondClassification); 
		model.addAttribute("page", page);
		return "modules/secondclassification/bookSecondClassificationList";
	}

	@RequiresPermissions("secondclassification:bookSecondClassification:view")
	@RequestMapping(value = "form")
	public String form(BookSecondClassification bookSecondClassification, Model model) {
		model.addAttribute("bookSecondClassification", bookSecondClassification);
		return "modules/secondclassification/bookSecondClassificationForm";
	}

	@RequiresPermissions("secondclassification:bookSecondClassification:edit")
	@RequestMapping(value = "save")
	public String save(BookSecondClassification bookSecondClassification, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookSecondClassification)){
			return form(bookSecondClassification, model);
		}
		bookSecondClassificationService.save(bookSecondClassification);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/secondclassification/bookSecondClassification/?repage";
	}
	
	@RequiresPermissions("secondclassification:bookSecondClassification:edit")
	@RequestMapping(value = "delete")
	public String delete(BookSecondClassification bookSecondClassification, RedirectAttributes redirectAttributes) {
		bookSecondClassificationService.delete(bookSecondClassification);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/secondclassification/bookSecondClassification/?repage";
	}

}