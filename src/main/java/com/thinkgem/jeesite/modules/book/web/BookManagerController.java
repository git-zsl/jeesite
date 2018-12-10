/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.book.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.classificationtree.entity.Classificationtree;
import com.thinkgem.jeesite.modules.classificationtree.service.ClassificationtreeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.book.entity.BookManager;
import com.thinkgem.jeesite.modules.book.service.BookManagerService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 书籍管理Controller
 * @author zsl
 * @version 2018-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/book/bookManager")
public class BookManagerController extends BaseController {

	@Autowired
	private BookManagerService bookManagerService;
	
	@Autowired
	private ClassificationtreeService classificationtreeService;
	private static final String PARENT_FINST_ID = "0";
	private static final String SOFT_TYPE_YES = "0";
	private static final String SOFT_TYPE_NO = "1";

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
	
	@RequiresPermissions("book:bookManager:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookManager bookManager, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookManager> page = bookManagerService.findPage(new Page<BookManager>(request, response), bookManager);
		model.addAttribute("page", page);
		return "modules/book/bookManagerList";
	}

	@RequiresPermissions("book:bookManager:view")
	@RequestMapping(value = "form")
	public String form(BookManager bookManager, Model model) {
		//一级分组
		Classificationtree tree1 = new Classificationtree("0");
		Classificationtree tree2 = new Classificationtree();
		tree2.setParent(tree1);
		List<Classificationtree> firstClassification = classificationtreeService.findList(tree2);
		model.addAttribute("firstClassification", firstClassification);
		//二级分组
		tree1.setId("-1");
		List<Classificationtree> secondClassification = classificationtreeService.findList(tree2);
		model.addAttribute("secondClassification", secondClassification);
		model.addAttribute("bookManager", bookManager);
		return "modules/book/bookManagerForm";
	}

	@RequiresPermissions("book:bookManager:edit")
	@RequestMapping(value = "save")
	public String save(BookManager bookManager, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookManager)){
			return form(bookManager, model);
		}
		if(StringUtils.isBlank(bookManager.getFirstClassId().getId()) || StringUtils.isBlank(bookManager.getSecondClassId().getId())){
			addMessage(redirectAttributes, "请选择分类");
			return "redirect:"+Global.getAdminPath()+"/book/bookManager/form";
		}
		bookManagerService.save(bookManager);
		addMessage(redirectAttributes, "保存书籍成功");
		return "redirect:"+Global.getAdminPath()+"/book/bookManager/?repage";
	}
	
	@RequiresPermissions("book:bookManager:edit")
	@RequestMapping(value = "delete")
	public String delete(BookManager bookManager, RedirectAttributes redirectAttributes) {
		bookManagerService.delete(bookManager);
		addMessage(redirectAttributes, "删除书籍成功");
		return "redirect:"+Global.getAdminPath()+"/book/bookManager/?repage";
	}
	/**
	 * 主页图书排序规则接口
	 */

	@RequestMapping(value = "bookSoft",method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity<List<BookManager>> bookSoft(@RequestParam Map<String,String> map,BookManager bookManager,HttpServletResponse response,HttpServletRequest request) {
		if(!StringUtils.isBlank(map.get("softType"))){
			bookManager.setSoftType(SOFT_TYPE_YES.equals(map.get("softType"))?"a.hits":"a.create_date");
		}
		if(!StringUtils.isBlank(map.get("bookName"))){
			bookManager.setBookName(map.get("bookName"));
		}
		if(!StringUtils.isBlank(map.get("parentId")) && !StringUtils.isBlank(map.get("id"))){
			Classificationtree Classification = new Classificationtree(map.get("id"));
			if(PARENT_FINST_ID.equals(map.get("parentId"))){
				bookManager.setFirstClassId(Classification);
			}else{
				bookManager.setSecondClassId(Classification);
			}
		}
		Page<BookManager> page = bookManagerService.findPage(new Page<BookManager>(request, response), bookManager);
		return ReturnEntity.success(page,"查询书籍分类成功");
	}

}