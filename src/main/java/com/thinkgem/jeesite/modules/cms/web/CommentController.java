/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.utils.MyPageUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ad.entity.AdInfomation;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.entity.Comment;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CommentService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 评论Controller
 *
 * @author ThinkGem
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @ModelAttribute
    public Comment get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return commentService.get(id);
        } else {
            return new Comment();
        }
    }

    @RequiresPermissions("cms:comment:view")
    @RequestMapping(value = {"list", ""})
    public String list(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Comment> page = commentService.findPage(new Page<Comment>(request, response), comment);
        model.addAttribute("page", page);
        return "modules/cms/commentList";
    }

    @RequiresPermissions("cms:comment:edit")
    @RequestMapping(value = "save")
    public String save(Comment comment, RedirectAttributes redirectAttributes) {
        if (beanValidator(redirectAttributes, comment)) {
            if (comment.getAuditUser() == null) {
                comment.setAuditUser(UserUtils.getUser());
                comment.setAuditDate(new Date());
            }
            comment.setDelFlag(Comment.DEL_FLAG_NORMAL);
            commentService.save(comment);
            //修改文章的评论数
             Article article = articleService.get(comment.getContentId());
            article.setCommentNum(article.getCommentNum() + 1);
            articleService.updataArticleCommentNum(article);
            addMessage(redirectAttributes, DictUtils.getDictLabel(comment.getDelFlag(), "cms_del_flag", "保存")
                    + "评论'" + StringUtils.abbr(StringUtils.replaceHtml(comment.getContent()), 50) + "'成功");
        }
        return "redirect:" + adminPath + "/cms/comment/?repage&delFlag=2";
    }

    @RequiresPermissions("cms:comment:edit")
    @RequestMapping(value = "delete")
    public String delete(Comment comment, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes) {
        if (Objects.nonNull(isRe)) {
            comment.setDelFlag(Comment.DEL_FLAG_AUDIT);
        } else {
            comment.setDelFlag(Comment.DEL_FLAG_DELETE);
            Article article = articleService.get(comment.getContentId());
            article.setCommentNum(article.getCommentNum() - 1);
            articleService.updataArticleCommentNum(article);
        }
        commentService.delete(comment, isRe);
        addMessage(redirectAttributes, (isRe != null && isRe ? "恢复审核" : "删除") + "评论成功");
        return "redirect:" + adminPath + "/cms/comment/?repage&delFlag=2";
    }

    /**
     * 主页获取请教接口
     */
    @RequestMapping(value = "filter/consultationList", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<Page<Comment>> findConsultationList(String userId, String categoryId, @ModelAttribute Comment comment, HttpServletRequest request, HttpServletResponse response) {
		Page<Comment> page = new Page<Comment>(request, response);
        List<Comment> comments = new ArrayList<>();
        List<Comment> newComments = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(categoryId)) {
                comment.setCategory(new Category(categoryId));
            }
            List<Comment> list = commentService.findconsultationList(comment);
            if (!StringUtils.isBlank(userId)) {
                for (Comment comment1 : list) {
                    if (StringUtils.isBlank(comment1.getParentContentId())) {
                        if (comment1.getCreateBy().getId().equals(userId)) {
                            newComments.add(comment1);
                        }
                        continue;
                    }
                    newComments.add(comment1);
                }
                list = newComments;
            }
            if (!list.isEmpty()) {
                for (Comment c : list) {
                    for (Comment cc : list) {
                        if (c.getId().equals(cc.getParentContentId())) {
                            c.getChildComments().add(cc);
                        }
                    }
                    if (StringUtils.isBlank(c.getParentContentId())) {
                        comments.add(c);
                    }
                }
            }

            //模拟分页
            List<Comment> pageList = MyPageUtil.getPageList(comments, request, response);
            page.setList(pageList);

            //统计评论数量
		/*	for (Comment c : comments) {
				int i = 0;
				if(!c.getChildComments().isEmpty()){
					int childNum = commentService.findChildNum(c,i);
					c.setCommentNum(childNum + "");
				}
			}*/
            //模拟分页
		/*	for (int i = 0; i < comments.size(); i++) {
				if( i >= ((page.getPageNo()-1)*page.getPageSize()) && i <= (page.getPageNo()*page.getPageSize()-1)){
					lists.add(comments.get(i));
				}
			}
			page.setList(lists);*/
            //不分页
            /*	page.setList(comments);*/
		/*	if(Global.YES.equals(comment.getIsRecommend())){
				for (Comment c : comments) {
					if(Global.YES.equals(c.getIsRecommend())){
						lists.add(c);
					}
				}
				page.setList(lists);
				return ReturnEntity.success(lists,"获取推荐评论列表成功");
			}*/
        } catch (Exception e) {
            LogUtils.getLogInfo(CommentController.class).info("程序出错", e);
            e.printStackTrace();
            ReturnEntity.fail("程序出错");
        }
        return ReturnEntity.success(page, "获取评论列表成功");
    }

    /**
     * 主页请教保存接口
     */
    @RequestMapping(value = "filter/homeSsave")
    public ReturnEntity homeSsave(@ModelAttribute Comment comment,String userId) {
        if (comment.getAuditUser() == null) {
            comment.setAuditUser(UserUtils.get(userId));
            comment.setAuditDate(new Date());
        }
        comment.setDelFlag(Comment.DEL_FLAG_NORMAL);
        commentService.save(comment);
        return ReturnEntity.success("保存成功");
    }

    /**
     * 查询请教列表接口
     */
    @RequestMapping(value = "filter/consultationArticleList", method = RequestMethod.POST)
    @ResponseBody
    public synchronized  ReturnEntity<Page<Comment>> findConsultationArticleList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "categoryId") String categoryId, @ModelAttribute Comment comment,String userId) {
        Article article = new Article();
        article.setCategory(new Category(categoryId));
        if(!Global.YES.equals(comment.getIsRecommend())){
            article.setIsRecommend("");
        }else{
            article.setIsRecommend(comment.getIsRecommend());
        }
        if(StringUtils.isNotBlank(userId)){
            article.setCreateBy(new User(userId));
        }
        List<Article> articleList0Answers = Lists.newArrayList();
        Page<Article> articlePage = null;
        try {
            articlePage = articleService.findconsultationArticlePage(new Page<Article>(request, response), article);
            //设置评论数
            List<Article> list = articlePage.getList();
            if(!list.isEmpty()){
                for (Article a : list) {
                    int commentNumByArticle = commentService.findCommentNumByArticle(a);
                    a.setCommentNum(commentNumByArticle);
                    if(StringUtils.isNotBlank(comment.getParentContentId()) && commentNumByArticle == 0){
                        articleList0Answers.add(a);
                    }
                }
                article.setCommentNum(Integer.parseInt(comment.getCommentNum()));
            }
            if(StringUtils.isNotBlank(comment.getParentContentId())){
                articlePage.setList(articleList0Answers);
            }
            return ReturnEntity.success(articlePage, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getLogInfo(CommentController.class).info("程序出错", e);
            return ReturnEntity.fail( "查询失败");
        }
    }


    /**
     * 文章详情内的评论内容
     *
     * @param article（只需文章id）
     * @return
     */
    @RequestMapping("filter/findCommentByArticle")
    @ResponseBody
    public ReturnEntity findCommentByArticle(Article article,@RequestParam(value = "userId",required = false) String userId, HttpServletRequest request,HttpServletResponse response) {
        try {
            Page<Comment> page = new Page<Comment>(request, response);
            //模拟分页
            List<Comment> pageList = MyPageUtil.getPageList(commentService.findCommentByArticle(article,userId), request, response);
            page.setList(pageList);
            return ReturnEntity.success(page, "获取评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getLogInfo(CommentController.class).info("程序出错", e);
            return ReturnEntity.fail("程序出错");
        }
    }

    /**
     * 评论点赞修改接口
     * @param comment（只需评论的id）
     * @return
     */
    @RequestMapping("filter/updateCommentLikeNum")
    @ResponseBody
    public ReturnEntity updateCommentLikeNum(Comment comment,String userId) {
        try {
            String message = commentService.updateCommentLikeNum(comment, userId);
            return ReturnEntity.success(message);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getLogInfo(CommentController.class).info("程序出错", e);
            return ReturnEntity.fail("程序出错");
        }
    }



    /**
     * 个人中心新评论接口
     * @param
     * @return
     */
    @RequestMapping("filter/personalComment")
    @ResponseBody
    public ReturnEntity personalComment(String userId,HttpServletRequest request,HttpServletResponse response) {
        List<Comment> comments = Lists.newArrayList();
        Page<Comment> page = new Page<Comment>(request, response);
        try {
            Article article = new Article();
            article.setCreateBy(new User(userId));
            List<Article> list = articleService.findList(article);
            for (Article a : list) {
                List<Comment> commentByArticleId = commentService.findCommentByArticleId(a);
                comments.addAll(commentByArticleId);
            }
            //模拟分页
            List<Comment> pageList = MyPageUtil.getPageList(comments, request, response);
            page.setList(pageList);
            return ReturnEntity.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getLogInfo(CommentController.class).info("程序出错", e);
            return ReturnEntity.fail("程序出错");
        }
    }


}
