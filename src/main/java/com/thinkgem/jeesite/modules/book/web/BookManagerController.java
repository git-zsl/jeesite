/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.book.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.classificationtree.entity.Classificationtree;
import com.thinkgem.jeesite.modules.classificationtree.service.ClassificationtreeService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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

import java.lang.reflect.Array;
import java.util.*;

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
		List<Classificationtree> secondClassification = null;
		if(!Objects.isNull(bookManager.getFirstClassId())){
			 secondClassification = classificationtreeService.findByFirstClassificationId(bookManager.getFirstClassId());
		}
		model.addAttribute("secondClassification", secondClassification);
		model.addAttribute("bookManager", bookManager);
		return "modules/book/bookManagerForm";
	}

	@RequestMapping(value = "changeSecondClassification",method = RequestMethod.GET)
	@ResponseBody
	public List<Classificationtree> changeSecondClassification(BookManager bookManager, Model model){
		//二级分组
		List<Classificationtree> secondClassification = classificationtreeService.findByFirstClassificationId(bookManager.getFirstClassId());
		if(secondClassification.isEmpty()){
			return null;
		}

		return secondClassification;
	}

	@RequiresPermissions("book:bookManager:edit")
	@RequestMapping(value = "save")
	public String save(BookManager bookManager, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookManager)){
			return form(bookManager, model);
		}
		String s = StringEscapeUtils.unescapeXml(bookManager.getParticulars());
		bookManager.setParticulars(s);
		if(StringUtils.isBlank(bookManager.getFirstClassId().getId())){
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

	@RequestMapping(value = "filter/bookSoft",method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity<List<BookManager>> bookSoft(@RequestParam Map<String,String> map,BookManager bookManager,HttpServletResponse response,HttpServletRequest request) {
		Page page1 = new Page();
		if(!StringUtils.isBlank(map.get("pageNo"))){
			page1.setPageNo(Integer.parseInt(map.get("pageNo")));
		}else{
			page1.setPageNo(1);
		}
		page1.setPageSize(Integer.parseInt(Global.getConfig("home.pageSize")));
		if(!StringUtils.isBlank(map.get("softType"))){
			bookManager.setSoftType(SOFT_TYPE_YES.equals(map.get("softType"))?"a.hits":"a.create_date");
		}
		if(!StringUtils.isBlank(map.get("bookName"))){
			bookManager.setBookName(map.get("bookName"));
		}
		if(!StringUtils.isBlank(map.get("bookId"))){
			BookManager book = bookManagerService.get(map.get("bookId"));
			List<String> strings = Arrays.asList(book.getBookImagUrl().split("\\|"));
			List<String> strs = new ArrayList<>();
			for (String s : strings) {
				if(!StringUtils.isBlank(s)){
					strs.add(s);
				}
			}
			book.setImagUrl(strs);
			List<BookManager> books = new ArrayList<BookManager>();
			books.add(book);
			Integer hits = book.getHits();
			book.setHits(++hits);
			bookManagerService.save(book);
			return ReturnEntity.success(books,"查询对应书籍成功");
		}
		if(!StringUtils.isBlank(map.get("parentId")) && !StringUtils.isBlank(map.get("id"))){
			Classificationtree Classification = new Classificationtree(map.get("id"));
			if(PARENT_FINST_ID.equals(map.get("parentId"))){
				bookManager.setFirstClassId(Classification);
			}else{
				bookManager.setSecondClassId(Classification);
			}
			if(!StringUtils.isBlank(map.get("isRecommend"))){
				if(!StringUtils.isBlank(map.get("getPage"))){
					bookManager.setIsRecommend(map.get("isRecommend"));
					return ReturnEntity.success(bookManagerService.findPage(page1, bookManager),"查询书籍列表成功");
				}else{
					bookManager.setIsRecommend(map.get("isRecommend"));
					return ReturnEntity.success(bookManagerService.findList(bookManager),"查询同类好书列表成功");
				}
			}
		}
		if(!StringUtils.isBlank(map.get("isRecommend"))) {
			bookManager.setIsRecommend(map.get("isRecommend"));
		}
		Page<BookManager> page = bookManagerService.findPage(page1, bookManager);
		return ReturnEntity.success(page,"查询书籍列表成功");
	}

	/**
	 * 同类好书接口
	 */
	@RequestMapping("filter/sameBookType")
	@ResponseBody
	public ReturnEntity findSameBookType(BookManager bookManager){
		try{
			return ReturnEntity.success(bookManagerService.findByBookType(bookManager),"获取数据成功");
		}catch (Exception e){
			LogUtils.getLogInfo(BookManagerController.class).info("程序出错",e);
			return ReturnEntity.fail("程序出错");
		}
	}
}