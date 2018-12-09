/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.notice.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.book.entity.BookManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.notice.entity.BookNotice;
import com.thinkgem.jeesite.modules.notice.service.BookNoticeService;

import java.util.List;
import java.util.Map;

/**
 * 图书公告Controller
 * @author zsl
 * @version 2018-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/notice/bookNotice")
public class BookNoticeController extends BaseController {

	@Autowired
	private BookNoticeService bookNoticeService;
	
	@ModelAttribute
	public BookNotice get(@RequestParam(required=false) String id) {
		BookNotice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookNoticeService.get(id);
		}
		if (entity == null){
			entity = new BookNotice();
		}
		return entity;
	}
	
	@RequiresPermissions("notice:bookNotice:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookNotice bookNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookNotice> page = bookNoticeService.findPage(new Page<BookNotice>(request, response), bookNotice); 
		model.addAttribute("page", page);
		return "modules/notice/bookNoticeList";
	}

	@RequiresPermissions("notice:bookNotice:view")
	@RequestMapping(value = "form")
	public String form(BookNotice bookNotice, Model model) {
		model.addAttribute("bookNotice", bookNotice);
		return "modules/notice/bookNoticeForm";
	}

	@RequiresPermissions("notice:bookNotice:edit")
	@RequestMapping(value = "save")
	public String save(BookNotice bookNotice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookNotice)){
			return form(bookNotice, model);
		}
		bookNoticeService.save(bookNotice);
		addMessage(redirectAttributes, "保存公告成功");
		return "redirect:"+Global.getAdminPath()+"/notice/bookNotice/?repage";
	}
	
	@RequiresPermissions("notice:bookNotice:edit")
	@RequestMapping(value = "delete")
	public String delete(BookNotice bookNotice, RedirectAttributes redirectAttributes) {
		bookNoticeService.delete(bookNotice);
		addMessage(redirectAttributes, "删除公告成功");
		return "redirect:"+Global.getAdminPath()+"/notice/bookNotice/?repage";
	}

	/**
	 * 主页图书排序规则接口
	 */

	@RequestMapping(value = "notice",method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity<List<BookNotice>> notice(BookNotice bookNotice) {
		List<BookNotice> bookNotices = bookNoticeService.findList(bookNotice);
		return ReturnEntity.success(bookNotices,"查询公告成功");
	}

}