/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.artuser.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.persistence.ReturnStatus;
import com.thinkgem.jeesite.common.utils.MyPageUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.artuser.entity.ArticleCollect;
import com.thinkgem.jeesite.modules.artuser.service.ArticleCollectService;
import com.thinkgem.jeesite.modules.attention.entity.UserAttentionUserids;
import com.thinkgem.jeesite.modules.attention.service.UserAttentionUseridsService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 文章与用户收藏关系Controller
 *
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
    @Autowired
    private UserAttentionUseridsService userAttentionUseridsService;
    private Class<ArticleCollectController> clazz = ArticleCollectController.class;

    @ModelAttribute
    public ArticleCollect get(@RequestParam(required = false) String id) {
        ArticleCollect entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = articleCollectService.get(id);
        }
        if (entity == null) {
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
        if (!beanValidator(model, articleCollect)) {
            return form(articleCollect, model);
        }
        articleCollectService.save(articleCollect);
        addMessage(redirectAttributes, "保存信息成功");
        return "redirect:" + Global.getAdminPath() + "/artuser/articleCollect/?repage";
    }

    @RequiresPermissions("artuser:articleCollect:edit")
    @RequestMapping(value = "delete")
    public String delete(ArticleCollect articleCollect, RedirectAttributes redirectAttributes) {
        articleCollectService.delete(articleCollect);
        addMessage(redirectAttributes, "删除信息成功");
        return "redirect:" + Global.getAdminPath() + "/artuser/articleCollect/?repage";
    }

    /**
     * 收藏文章接口
     */
    @RequestMapping(value = "filter/collectArticle")
    @ResponseBody
    public ReturnEntity collectArticle(@RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "articleId") String articleId) {
        try {
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(articleId)) {
                LogUtils.getLogInfo(clazz).info("参数缺失：userId = " + userId + " articleId = " + articleId);
                return new ReturnEntity(ReturnStatus.LOGOUT, "请先登录");
            }
            return articleCollectService.updateCollectNum(userId, articleId);
        } catch (RuntimeException ru) {
            LogUtils.getLogInfo(clazz).info(ru.getMessage());
            return ReturnEntity.fail(ru.getMessage());
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("收藏出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("收藏出错");
        }
    }

    /**
     * 查询用户收藏列表接口
     */
    @RequestMapping("filter/collectList")
    @ResponseBody
    public ReturnEntity collectList(@ModelAttribute ArticleCollect articleCollect, @RequestParam("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isBlank(userId) && Objects.isNull(UserUtils.get(userId))) {
                LogUtils.getLogInfo(clazz).info("用户不存在，请登录后再操作：user = null");
                new ReturnEntity<>(ReturnStatus.LOGOUT, "用户不存在，请登录后再操作");
            }
            List<Article> articlecollects = getArticleList(userId, articleCollect, request, response);
            Page<Article> page1 = new Page<Article>(request, response);
            page1.setList(articlecollects);
            page1.setCount(articlecollects.size());
            return ReturnEntity.success(page1, "查询收藏列表成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("查询收藏列表出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("查询收藏列表出错");
        }

    }

    private List<Article> getArticleList(String userId, ArticleCollect articleCollect, HttpServletRequest request, HttpServletResponse response) {
        List<Article> articlecollects = null;
        User user = UserUtils.get(userId);
        articleCollect.setUser(user);
        articlecollects = articleCollect.getArticles();
        Page<ArticleCollect> page = articleCollectService.findHomeCollectPage(new Page<>(request, response), articleCollect);
        if (!page.getList().isEmpty()) {
            List<ArticleCollect> list = page.getList();
            for (ArticleCollect ac : list) {
                Article article = articleService.get(ac.getArticleId());
                articlecollects.add(article);
            }
        }
        return articlecollects;
    }

    /**
     * 合作企业接口
     */
    @RequestMapping("filter/findCooperativeEnterprise")
    @ResponseBody
    public ReturnEntity findCooperativeEnterprise(@ModelAttribute ArticleCollect articleCollect, HttpServletRequest request, HttpServletResponse response, @RequestParam("userId") String userId) {
        try {
            if (StringUtils.isBlank(userId) && Objects.isNull(UserUtils.get(userId))) {
                LogUtils.getLogInfo(clazz).info("用户不存在，请登录后再操作：user = null");
                return new ReturnEntity<>(ReturnStatus.LOGOUT, "用户不存在，请登录后再操作");
            }
            List<Article> articlecollects = getArticleList(userId, articleCollect, request, response);
            List<User> userList = Lists.newArrayList();
            if (!articlecollects.isEmpty()) {
                articlecollects.stream().forEach(item -> {
                    if ("true".equals(item.getUser().getIsCompany())) {
                        userList.add(item.getUser());
                        if (userList.size() >= 10) {
                            return;
                        }
                    }
                });
            }
            return ReturnEntity.success(userList, "查询合作企业列表成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("系统出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("系统出错");
        }
    }


    /**
     * 查看当前文章收藏人列表（包含人数）
     */
    @RequestMapping("filter/collectUsers")
    @ResponseBody
    public ReturnEntity findCollectUsers(@ModelAttribute ArticleCollect articleCollect) {
        List<ArticleCollect> collectUsers = null;
        try {
            collectUsers = articleCollectService.findCollectUsers(articleCollect);
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("查询收藏人员列表出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("查询收藏人员列表出错");
        }
        return ReturnEntity.success(collectUsers, "查询收藏用户列表成功");
    }

    /**
     * 查看关注人更新文章列表
     */
    @RequestMapping("filter/attentionUserUpdateArticles")
    @ResponseBody
    public ReturnEntity attentionUserUpdateArticles(@RequestParam("userId") String userId, HttpServletResponse response, HttpServletRequest request) {
        Page<Article> page = new Page<Article>(request, response);
        List<Article> list = Lists.newArrayList();
        try {
            List<UserAttentionUserids> byUserId = userAttentionUseridsService.findByUserId(userId);
            if (!byUserId.isEmpty()) {
                for (UserAttentionUserids u : byUserId) {
                    String[] split = u.getAttentionUserIds().split(",");
                    for (int i = 1; i < split.length; i++) {
                        List<Article> articleList = articleService.findbyUserIdOrderByUpdateDate(split[i]);
                        list.addAll(articleList);
                    }
                }
            }
            List<Article> pageList = MyPageUtil.getPageList(list, request, response);
            page.setList(pageList);
            return ReturnEntity.success(page);
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("查询出错", e);
            return ReturnEntity.fail("查询出错");
        }
    }
}