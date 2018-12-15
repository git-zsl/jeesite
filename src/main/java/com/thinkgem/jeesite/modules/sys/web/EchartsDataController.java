/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.sys.utils.MapSortByKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2015-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/echarts/echartsData")
public class EchartsDataController extends BaseController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "initEchartsHome")
	public String demo(Article article, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response) {
		//查询一年的文章数量
		article.setDelFlag("2");
		article.setCategory(new Category());
		List<Integer> muns = new ArrayList<>();
		HashMap<String, Object> byYearCount = articleService.findByYearCount();
		if(!Objects.isNull(byYearCount)){
			MapSortByKeyUtils.sortMapByKey(byYearCount,muns);
		}else{
			Integer[] integers = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			muns = Arrays.asList(integers);
		}
		List<Article> list = articleService.findArticles();
		if(list.isEmpty()){
			list.add(new Article(null,"暂时没有文章"));
		}
		model.addAttribute("articleNum",articleService.findList(article).size());
		model.addAttribute("list",list);
		model.addAttribute("data",muns);
		return "/modules/test/echarts";
	}
}