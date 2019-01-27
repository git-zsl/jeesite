/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.artuser.entity.ArticleCollect;
import com.thinkgem.jeesite.modules.artuser.service.ArticleCollectService;
import com.thinkgem.jeesite.modules.classifying.entity.CmsClassifying;
import com.thinkgem.jeesite.modules.classifying.service.CmsClassifyingService;
import com.thinkgem.jeesite.modules.cms.entity.*;
import com.thinkgem.jeesite.modules.cms.service.*;
import com.thinkgem.jeesite.modules.cms.utils.TxtReadUtil;
import com.thinkgem.jeesite.modules.crn.entity.UserCategoryNum;
import com.thinkgem.jeesite.modules.custom.entity.CustomCategory;
import com.thinkgem.jeesite.modules.custom.service.CustomCategoryService;
import com.thinkgem.jeesite.modules.jobcity.entity.JobCity;
import com.thinkgem.jeesite.modules.jobcity.service.JobCityService;
import com.thinkgem.jeesite.modules.posts.entity.CmsPosts;
import com.thinkgem.jeesite.modules.posts.service.CmsPostsService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import com.thinkgem.jeesite.modules.cms.utils.TplUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.web.util.WebUtils;

/**
 * 文章Controller
 *
 * @author ThinkGem
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileTplService fileTplService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private CustomCategoryService customCategoryService;
    @Autowired
    private CmsPostsService cmsPostsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private JobCityService jobCityService;
    @Autowired
    private CmsClassifyingService cmsClassifyingService;
    @Autowired
    private ArticleCollectService articleCollectService;

    @ModelAttribute
    public Article get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return articleService.get(id);
        } else {
            return new Article();
        }
    }

    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = {"list", ""})
    public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            article.setCreateBy(user);
        }
        List<String> titles = articleService.findTitle(article);
        model.addAttribute("titles", titles);
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
        return "modules/cms/articleList";
    }

    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "form")
    public String form(Article article, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "all", required = false) String all) {
        try {
            model.addAttribute("postsList", cmsPostsService.findPosts(new CmsPosts()));
            model.addAttribute("cityList", jobCityService.findList(new JobCity()));
            model.addAttribute("cmsClassifying", cmsClassifyingService.findList(new CmsClassifying()));
            // 如果当前传参有子节点，则选择取消传参选择
            if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId())) {
                List<Category> list = categoryService.findByParentId(article.getCategory().getId(), Site.getCurrentSiteId());
                if (list.size() > 0) {
                    /*article.setCategory(null);*/
                    article.setArticleData(articleDataService.get(article.getId()));
                    model.addAttribute("userId", UserUtils.getUser().getId());
                    model.addAttribute("all", all);
                    model.addAttribute("contentViewList", getTplContent());
                    model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
                    model.addAttribute("article", article);
                    CmsUtils.addViewConfigAttribute(model, article.getCategory());
                    if (!StringUtils.isBlank(article.getCategory().getId())) {
                        String formContent = TxtReadUtil.getFormContent(article.getCategory().getId(), categoryService);
                        return formContent != null ? formContent : "modules/cms/articleForm";
                    }
                    return "modules/cms/articleForm";


                } else {
                    article.setCategory(categoryService.get(article.getCategory().getId()));
                }
            }
            article.setArticleData(articleDataService.get(article.getId()));
            model.addAttribute("userId", UserUtils.getUser().getId());
            model.addAttribute("all", all);
            model.addAttribute("contentViewList", getTplContent());
            model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
            model.addAttribute("article", article);
            CmsUtils.addViewConfigAttribute(model, article.getCategory());
            if (!StringUtils.isBlank(article.getCategory().getId())) {
                String formContent = TxtReadUtil.getFormContent(article.getCategory().getId(), categoryService);
                return formContent != null ? formContent : "modules/cms/articleForm";
            }
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info(e.getMessage());
            e.printStackTrace();
        }
        return "modules/cms/articleForm";
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "save")
    public String save(Article article, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "all", required = false) String all) {
        String path = "";
        if (!StringUtils.isBlank(all)) {
            path = "allList";
        }
        if (StringUtils.isBlank(article.getIsTop())) {
            article.setIsTop(Global.NO);
        }
        if (StringUtils.isBlank(article.getIsRecommend())) {
            article.setIsRecommend(Global.NO);
        }
        if (!beanValidator(model, article)) {
            return form(article, model, redirectAttributes, all);
        }
        articleService.save(article);
        addMessage(redirectAttributes, "保存文章'" + StringUtils.abbr(article.getTitle(), 50) + "'成功");
        String categoryId = article.getCategory() != null ? article.getCategory().getId() : null;
        return "redirect:" + adminPath + "/cms/article/" + path + "?repage&delFlag=2&category.id=" + (categoryId != null ? categoryId : "");
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "noSave")
    public String noSave(UserCategoryNum userCategoryNum, Model model, RedirectAttributes redirectAttributes) {
        String categoryId = userCategoryNum.getCategory().getId();
        addMessage(redirectAttributes, "当前用户未授权创建更多栏目");
        return "redirect:" + Global.getAdminPath() + "/cms/article/?repage&category.id=" + (categoryId != null ? categoryId : "");
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "delete")
    public String delete(Article article, String categoryId, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes, @RequestParam(value = "all", required = false) String all) {
        String path = "";
        if (!StringUtils.isBlank(all)) {
            path = "allList";
        }
        // 如果没有审核权限，则不允许删除或发布。
        if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
            addMessage(redirectAttributes, "你没有删除或发布权限");
        }
        articleService.delete(article, isRe);
        addMessage(redirectAttributes, (isRe ? "删除" : "发布") + "文章成功");
        return "redirect:" + adminPath + "/cms/article/" + path + "?repage&category.id=" + (categoryId != null ? categoryId : "");
    }

    /**
     * 文章选择列表
     */
    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "selectList")
    public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(article, request, response, model);
        return "modules/cms/articleSelectList";
    }

    /**
     * 通过编号获取文章标题
     */
    @RequiresPermissions("cms:article:view")
    @ResponseBody
    @RequestMapping(value = "findByIds")
    public String findByIds(String ids) {
        List<Object[]> list = articleService.findByIds(ids);
        return JsonMapper.nonDefaultMapper().toJson(list);
    }

    private List<String> getTplContent() {
        List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
        tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
        return tplList;
    }

    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "allList")
    public String allList(Article article, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "init", required = false) String init) {
        if (!StringUtils.isBlank(init)) {
            article.setDelFlag("2");
        }
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        List<String> titles = articleService.findTitle(article);
        model.addAttribute("titles", titles);
        model.addAttribute("page", page);
        return "modules/cms/articleAllList";
    }

    /**
     * 主页广告接口
     */

    @RequestMapping(value = "adsList", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> adsList(Article article, Category category, HttpServletRequest request) {
        List<Article> articles = null;
        try {
            CustomCategory gg = customCategoryService.findByMark(Global.GG);
            if (Objects.isNull(gg)) {
                LogUtils.saveLog(request, gg, new Exception(), "没有找到对应的自定义栏目，请配置");
                return ReturnEntity.success(null, "请先设置自定义栏目标志‘GG’");
            }
            List<String> byParentIdNoSite = categoryService.findByParentIdNoSite(gg.getCategoryId());
            articles = articleService.findByCategoryIdIn(byParentIdNoSite);
        } catch (Exception e) {
            LogUtils.saveLog(request, articles, e, "没有找到对应的自定义栏目，请配置");
            return ReturnEntity.fail("系统内部错误成功，请联系管理员");
        }
        return ReturnEntity.success(articles, "查询广告成功");
    }

    /**
     * 主页阅读场景接口
     */

    @RequestMapping(value = "readScene", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> readScene(Article article, Category category, HttpServletRequest request) {
        List<Article> articles = null;
        try {
            CustomCategory gg = customCategoryService.findByMark(Global.YDCJ);
            if (Objects.isNull(gg)) {
                LogUtils.saveLog(request, gg, new Exception(), "没有找到对应的自定义栏目，请配置");
                return ReturnEntity.success(null, "请先设置自定义栏目标志‘YDCJ’");
            }
            List<String> categoryIds = new ArrayList<>();
            categoryIds.add(gg.getCategoryId());
            articles = articleService.findByCategoryIdIn(categoryIds);
        } catch (Exception e) {
            LogUtils.saveLog(request, articles, e, "没有找到对应的自定义栏目，请配置");
            return ReturnEntity.fail("系统内部错误，请联系管理员");
        }
        return ReturnEntity.success(articles, "查询广告成功");
    }

    /**
     * 获取全局文章内容接口
     * param category.id （不必须）
     */

    @RequestMapping(value = "getAllArticle", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> getAllArticle(@ModelAttribute Article article, @RequestParam(value = "categoryId", required = false) String categoryId,@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!StringUtils.isBlank(userId)) {
                User user = UserUtils.get(userId);
                if(Objects.isNull(user)){
                    return ReturnEntity.fail("当前用户不存在，无法获取用户的信息列表");
                }
                article.setCreateBy(user);
            }
            if (!StringUtils.isBlank(categoryId)) {
                Category category = new Category(categoryId);
                category.setParentIds(categoryId);
                article.setCategory(category);
            }
            if (!StringUtils.isBlank(article.getId())) {
                ArticleData articleData = articleDataService.get(article.getId());
                Article article1 = articleService.findList(article).get(0);
                article1.setArticleData(articleData);
                return ReturnEntity.success(article1, "获取列表成功");
            }
            if (Global.YES.equals(article.getHits() + "")) {
                List<Article> list = articleService.findList(article);
                return ReturnEntity.success(list, "获取列表成功");
            } else {
                Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
                return ReturnEntity.success(page, "获取数据成功");
            }
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info("程序内部出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序内部出错");
        }
    }


    /**
     * 获取热门作者接口
     * param c
     */

    @RequestMapping(value = "getHostAuthor", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> getHostAuthor(@ModelAttribute Article article, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Article> hostAuthors = articleService.findHostAuthors(article);
            return ReturnEntity.success(hostAuthors, "获取热门作者列表成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info("程序内部出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序内部出错");
        }
    }

    /**
     * 热门关键词
     * keywords
     */
    @RequestMapping(value = "getHostKeywords", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> getHostKeywords(@ModelAttribute Article article, @RequestParam(value = "categoryId", required = false) String categoryId, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!StringUtils.isBlank(categoryId)) {
                Category category = new Category(categoryId);
                category.setParentIds(categoryId);
                article.setCategory(category);
            }
            List<Article> hostKeywords = articleService.findHostKeywords(article);
            return ReturnEntity.success(hostKeywords, "获取热门keywords列表成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info("程序内部出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序内部出错");
        }
    }

    /**
     * 文章详情
     * content
     */
    @RequestMapping(value = "getArticleContent", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> getArticleContent(@ModelAttribute Article article) {
        try {
            ArticleData articleData = articleDataService.get(article.getId());
            return ReturnEntity.success(articleData, "获取文章详情成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info("程序内部出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序内部出错");
        }
    }

    /**
     * 评论详情
     * comment
     */
    @RequestMapping(value = "getArticleComment", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> getArticleComment(@ModelAttribute Article article, HttpServletRequest request, HttpServletResponse response) {
        try {
            Comment comment = new Comment();
            comment.setContentId(article.getId());
            Page<Comment> page = commentService.findPage(new Page<Comment>(request, response), comment);
            return ReturnEntity.success(page, "获取评论列表成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info("程序内部出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序内部出错");
        }
    }

    /**
     * 获取招聘岗位接口
     * param
     */

    @RequestMapping(value = "getHostPost", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> getHostPost(@ModelAttribute Article article, @RequestParam(value = "categoryId", required = false) String categoryId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Category category = new Category(categoryId);
            article.setCategory(category);
            List<Article> hostPost = articleService.findHostPosts(article);
            return ReturnEntity.success(hostPost, "获取热门作者列表成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info("程序内部出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序内部出错");
        }
    }


    /**
     * 获取招聘岗位搜索接口
     * param category.id （不必须）
     */

    @RequestMapping(value = "getAllJobArticle", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<List<Article>> getAllJobArticle(@ModelAttribute Article article, @RequestParam(value = "categoryId", required = false) String categoryId, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!StringUtils.isBlank(categoryId)) {
                Category category = new Category(categoryId);
                category.setParentIds(categoryId);
                article.setCategory(category);
            }
            if (Global.YES.equals(article.getPay())) {
                article.setPay("");
            }
            if (Global.YES.equals(article.getArea())) {
                article.setArea("");
            }
            if (Global.YES.equals(article.getEducation())) {
                article.setEducation("");
            }
            if (Global.YES.equals(article.getExperience())) {
                article.setExperience("");
            }
            Page<Article> page = articleService.findJobList(new Page<Article>(request, response), article);
            return ReturnEntity.success(page, "获取数据成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(ArticleController.class).info("程序内部出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序内部出错");
        }
    }


    /**
     * 请教/文章保存接口
     */
    @RequestMapping(value = "consultationSave",method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity consultationSave(@ModelAttribute Article article,@ModelAttribute ArticleData articleData,HttpServletRequest request,@RequestParam("userId") String userId,@RequestParam(value = "categoryId") String categoryId) {
        if(!StringUtils.isBlank(categoryId)){
            Category category = new Category(categoryId);
            article.setCategory(category);
        }
        MultipartFile image = null;
        User user = null;
        File filePath = null;
        try{
            //转码
            String titleDecode = URLDecoder.decode(article.getTitle(),"UTF-8");
            if(!StringUtils.isBlank(article.getDescription())){
                String descriptiondecode = URLDecoder.decode(article.getDescription(),"UTF-8");
                article.setDescription(descriptiondecode);
            }
            if(!StringUtils.isBlank(article.getKeywords())){
                String keywordsdecode = URLDecoder.decode(article.getKeywords(),"UTF-8");
                article.setKeywords(keywordsdecode);
            }
            if(!StringUtils.isBlank(article.getBrand())){
                String branddecode = URLDecoder.decode(article.getBrand(),"UTF-8");
                article.setBrand(branddecode);
            }
            String contentdecode = URLDecoder.decode(articleData.getContent(),"UTF-8");
            article.setTitle(titleDecode);
            articleData.setContent(contentdecode);
            Category category = categoryService.get(article.getCategory().getId());
            if(Objects.isNull(category)){
                LogUtils.getLogInfo(ArticleController.class).info("获取栏目id为空或者栏目不存在，传入categoryId值为：" + article.getCategory().getId());
                return ReturnEntity.fail("获取栏目id为空或者栏目不存在，传入categoryId值为：" + article.getCategory().getId());
            }
            if(!StringUtils.isBlank(userId)){
            user = UserUtils.get(userId);
        }
        if(Objects.isNull(user)){
            LogUtils.getLogInfo(ArticleController.class).info("获取用户值为空，传入userId值为：" + userId);
            return ReturnEntity.fail("当前用户过期，或者不存在。传入userId值为：" + userId);
        }
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            image = multipartRequest.getFile("homeImage");
            String originalFilename = image.getOriginalFilename();
            String configPath = Global.getConfig("userfiles.basedir").substring(0, 1) + Global.getConfig("userfiles.basedir").substring(1);
            filePath = new File(configPath + "/" + category.getName() + "/" + user.getLoginName() + "/" + originalFilename);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            image.transferTo(filePath);
            //路径问题，应与原来保持一致，不然主页上传的图片，后台看不到
            String path = filePath.getPath();    // 目前为完整路径，改成相对路径
            //获取图片并保存。。。。。。
            article.setImage(path);
        }
            article.setIsTop(Global.NO);
            article.setIsRecommend(Global.NO);
            articleData.setAllowComment(Global.YES);
            article.setArticleData(articleData);
            articleService.save(article);
            //保存内容(如果需要设置相关请教，则在ArticleData处添加)
        }catch (Exception e){
            LogUtils.getLogInfo(ArticleController.class).info("保存出错",e);
            e.printStackTrace();
            return ReturnEntity.fail("保存出错");
        }
        return ReturnEntity.success("保存成功");
    }


    /**
     * 点赞接口
     */
    @RequestMapping(value="like")
    @ResponseBody
    public ReturnEntity updateLikeNum(@ModelAttribute Article article){
        try{
            articleService.updateLikeNum(article);
        }catch (Exception e){
            LogUtils.getLogInfo(ArticleController.class).info("点赞出错",e);
            e.printStackTrace();
            return ReturnEntity.fail("点赞出错");
        }
        return ReturnEntity.success("点赞成功");
    }
   /**
     * 查询对应用户最新动态接口
     */
    @RequestMapping("latestAction")
    @ResponseBody
    public ReturnEntity latestAction(@RequestParam("userId") String userId){
        List<Article> articleLists = Lists.newArrayList();
        List<Article> list = null;
        List<Article> articlecollects = null;
        List<Article> comments = null;
        List<Article> articleList = null;
        try{
            User user = UserUtils.get(userId);
            if(!StringUtils.isBlank(userId) && !Objects.isNull(user)){
                //文章类创建人
                Article article = new Article();
                article.setCreateBy(user);
                list = articleService.findList(article);
                //收藏类创建人
                ArticleCollect articleCollect = new ArticleCollect();
                articleCollect.setUser(user);
                articlecollects = articleCollect.getArticles();
                List<ArticleCollect> homeCollects = articleCollectService.findHomeCollects(articleCollect);
                if(!homeCollects.isEmpty()){
                    for (ArticleCollect ac : homeCollects) {
                        Article article1 = articleService.get(ac.getArticleId());
                        articlecollects.add(article1);
                    }
                }
                //评论类创建人   select * from cms_comment cc left join cms_article_data ca  on cc.content_id = ca.id
                Comment comment = new Comment();
                comment.setCreateBy(user);
                List<String> articleIds = commentService.findArticleIds(comment);
                comments = Lists.newArrayList();
                for (String s : articleIds) {
                    Article article1 = articleService.get(s);
                    comments.add(article1);
                }
            }
            articleLists.addAll(list);
            articleLists.addAll(articlecollects);
            articleLists.addAll(comments);
            //时间排序
            articleList = articleService.listSort(articleLists);
        }catch (Exception e){
            LogUtils.getLogInfo(ArticleController.class).info("查询关键词出错",e);
            e.printStackTrace();
            return ReturnEntity.fail("查询关键词出错");
        }
        return ReturnEntity.success(articleList,"查询成功");
    }
    /**
     * 请教分类接口：
     */
    @RequestMapping("findClassifying")
    @ResponseBody
    public ReturnEntity findClassifying(){
        List<CmsClassifying> list = null;
        try{
            list = cmsClassifyingService.findList(new CmsClassifying());
        }catch (Exception e){
            LogUtils.getLogInfo(ArticleController.class).info("查询请教分类出错",e);
            e.printStackTrace();
            return ReturnEntity.fail("查询关键词出错");
        }
        return ReturnEntity.success(list,"查询请教分类成功");
    }
}
