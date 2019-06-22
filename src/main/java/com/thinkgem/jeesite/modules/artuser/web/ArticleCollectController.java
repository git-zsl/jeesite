/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.artuser.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.persistence.ReturnStatus;
import com.thinkgem.jeesite.modules.ad.entity.AdInfomation;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
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
import com.thinkgem.jeesite.modules.artuser.entity.ArticleCollect;
import com.thinkgem.jeesite.modules.artuser.service.ArticleCollectService;

import java.util.List;
import java.util.Objects;

/**
 * 文章与用户收藏关系Controller
 * @author zsl
 * @version 2019-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/artuser/articleCollect")
public class ArticleCollectController extends BaseController {

	@Autowired
	private ArticleCollectService articleCollectService;
	@Autowired
	private ArticleService articleService;
	private Class<ArticleCollectController> clazz = ArticleCollectController.class;

	@ModelAttribute
	public ArticleCollect get(@RequestParam(required=false) String id) {
		ArticleCollect entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = articleCollectService.get(id);
		}
		if (entity == null){
			entity = new ArticleCollect();
		}
		return entity;
	}
	
	@RequiresPermissions("artuser:articleCollect:view")
	@RequestMapping(value = {"list", ""})
	public String list(ArticleCollect articleCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ArticleCollect> page = articleCollectService.findPage(new Page<ArticleCollect>(request, response), articleCollect); 
		model.addAttribute("page", page);
		return "modules/artuser/articleCollectList";
	}

	@RequiresPermissions("artuser:articleCollect:view")
	@RequestMapping(value = "form")
	public String form(ArticleCollect articleCollect, Model model) {
		model.addAttribute("articleCollect", articleCollect);
		return "modules/artuser/articleCollectForm";
	}

	@RequiresPermissions("artuser:articleCollect:edit")
	@RequestMapping(value = "save")
	public String save(ArticleCollect articleCollect, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, articleCollect)){
			return form(articleCollect, model);
		}
		articleCollectService.save(articleCollect);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/artuser/articleCollect/?repage";
	}
	
	@RequiresPermissions("artuser:articleCollect:edit")
	@RequestMapping(value = "delete")
	public String delete(ArticleCollect articleCollect, RedirectAttributes redirectAttributes) {
		articleCollectService.delete(articleCollect);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/artuser/articleCollect/?repage";
	}

	/**
	 * 收藏文章接口
	 */
	@RequestMapping(value = "filter/collectArticle")
	@ResponseBody
	public ReturnEntity collectArticle(@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "articleId") String articleId){
		try{
			if(StringUtils.isBlank(userId) || StringUtils.isBlank(articleId)){
				LogUtils.getLogInfo(clazz).info("参数缺失：userId = "+ userId + " articleId = " + articleId);
				return new ReturnEntity(ReturnStatus.LOGOUT, "请先登录");
			}
			return articleCollectService.updateCollectNum(userId, articleId);
		}catch (RuntimeException ru){
			LogUtils.getLogInfo(clazz).info(ru.getMessage());
			return ReturnEntity.fail(ru.getMessage());
		}catch(Exception e){
			LogUtils.getLogInfo(clazz).info("收藏出错",e);
			e.printStackTrace();
			return ReturnEntity.fail("收藏出错");
		}
	}

	/**
	 * 查询用户收藏列表接口
	 */
	@RequestMapping("filter/collectList")
	@ResponseBody
	public ReturnEntity collectList(@ModelAttribute ArticleCollect articleCollect,@RequestParam("userId") String userId ,HttpServletRequest request, HttpServletResponse response) {
		List<Article> articlecollects = null;
		try{
			if(StringUtils.isBlank(userId) && Objects.isNull(UserUtils.get(userId))){
				LogUtils.getLogInfo(clazz).info("用户不存在，请登录后再操作：user = null");
				return ReturnEntity.fail("用户不存在，请登录后再操作");
			}
			User user = UserUtils.get(userId);
			articleCollect.setUser(user);
			articlecollects = articleCollect.getArticles();
			Page<ArticleCollect> page = articleCollectService.findHomeCollectPage(new Page<ArticleCollect>(request, response), articleCollect);
			Page<Article> page1 = new Page<Article>(request, response);
			if(!page.getList().isEmpty()){
				List<ArticleCollect> list = page.getList();
				for (ArticleCollect ac : list) {
					Article article = articleService.get(ac.getArticleId());
					articlecollects.add(article);
				}
				page1.setList(articlecollects);
				page1.setCount(articlecollects.size());
			}
			return ReturnEntity.success(page1,"查询收藏列表成功");
		}catch (Exception e){
			LogUtils.getLogInfo(clazz).info("查询收藏列表出错",e);
			e.printStackTrace();
			return ReturnEntity.fail("查询收藏列表出错");
		}

	}

	/**
	 * 查看当前文章收藏人列表（包含人数）
	 */
	@RequestMapping("filter/collectUsers")
	@ResponseBody
	public ReturnEntity findCollectUsers(@ModelAttribute ArticleCollect articleCollect){
		List<ArticleCollect> collectUsers = null;
		try{
			collectUsers = articleCollectService.findCollectUsers(articleCollect);
		}catch (Exception e){
			LogUtils.getLogInfo(clazz).info("查询收藏人员列表出错",e);
			e.printStackTrace();
			return ReturnEntity.fail("查询收藏人员列表出错");
		}

		return ReturnEntity.success(collectUsers,"查询收藏用户列表成功");
	}
}