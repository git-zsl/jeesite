/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cms.dao.CommentDao;
import com.thinkgem.jeesite.modules.cms.entity.Comment;

import java.util.List;
import java.util.Objects;

/**
 * 评论Service
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentDao, Comment> {

    @Autowired
    private ArticleService articleService;

    public Page<Comment> findPage(Page<Comment> page, Comment comment) {
//		DetachedCriteria dc = commentDao.createDetachedCriteria();
//		if (StringUtils.isNotBlank(comment.getContentId())){
//			dc.add(Restrictions.eq("contentId", comment.getContentId()));
//		}
//		if (StringUtils.isNotEmpty(comment.getTitle())){
//			dc.add(Restrictions.like("title", "%"+comment.getTitle()+"%"));
//		}
//		dc.add(Restrictions.eq(Comment.FIELD_DEL_FLAG, comment.getDelFlag()));
//		dc.addOrder(Order.desc("id"));
//		return commentDao.find(page, dc);
        comment.getSqlMap().put("dsf", dataScopeFilter(comment.getCurrentUser(), "o", "u"));

        return super.findPage(page, comment);
    }

    public Page<Comment> findconsultationPage(Page<Comment> page, Comment comment) {
        return super.findconsultationPage(page, comment);
    }

    public List<Comment> findconsultationList(Comment comment) {
        return dao.findconsultationList(comment);
    }

    public List<String> findArticleIds(Comment comment) {
        return dao.findArticleIds(comment);
    }

    @Transactional(readOnly = false)
    public String updateCommentLikeNum(Comment comment, String userId) {
        Comment comment1 = dao.get(comment.getId());
        String userIds = comment1.getLikeUserIds();
        StringBuffer sb = new StringBuffer();
        String message = "";
        if (StringUtils.isBlank(userIds)) {
            comment1.setLikeUserIds(",");
        }
        if (!comment1.getLikeUserIds().contains(userId)) {
            sb.append(comment1.getLikeUserIds()).append(userId).append(",");
            comment1.setLikeUserIds(sb.toString());
            comment1.setLikeNum(Integer.parseInt(comment1.getLikeNum()) + 1 + "");
            message = "点赞成功";
        } else {
            String s = comment1.getLikeUserIds().replaceAll( userId + ",", "");
            comment1.setLikeNum(Integer.parseInt(comment1.getLikeNum()) - 1 + "");
            comment1.setLikeUserIds(s);
            message = "取消点赞成功";
        }
        dao.updateCommentLikeNum(comment1);
        return message;
    }

    @Transactional(readOnly = false)
    public void delete(Comment entity, Boolean isRe) {
        super.delete(entity);
    }

    @Transactional(readOnly = false)
    public int findChildNum(Comment comment, int i) {
        return ChildNum(comment, i);
    }


    public int ChildNum(Comment comment, int i) {
        i += comment.getChildComments().size();
        for (Comment c : comment.getChildComments()) {
            if (!c.getChildComments().isEmpty()) {
                i = ChildNum(c, i);
            }
        }
        return i;
    }

    @Transactional(readOnly = false)
    public void findParentCommentAndSet(Comment comment) {
        updateCommentNum(comment);
    }

    @Transactional(readOnly = false)
    public void updateCommentNum(Comment comment) {
        comment.setCommentNum(Integer.parseInt(comment.getCommentNum()) + 1 + "");
        super.save(comment);
        List<Comment> parents = dao.findParent(comment);
        if (!parents.isEmpty()) {
            findParentCommentAndSet(parents.get(0));
        } else {
            Article article = articleService.get(comment.getContentId());
            article.setCommentNum(article.getCommentNum() + 1);
            articleService.updataArticleCommentNum(article);
        }
    }


    public List<Comment> findCommentByArticle(Article article,String userId) {
        //查询，并组装list返回
        List<Comment> list = dao.findCommentByArticle(article.getId());
        if(StringUtils.isNotBlank(userId)){
            list = verdictAndSetStatus(list,userId);
        }
        List<Comment> comments = Lists.newArrayList();
        //封装子集合
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
        return comments;
    }

    public int findCommentNumByArticle(Article article) {
        List<Comment> list = dao.findCommentByArticle(article.getId());
        return list.size();
    }

    public List<Comment> verdictAndSetStatus(List<Comment> list , String userId){
        if(!list.isEmpty()){
            for (Comment c : list) {
                if(c.getLikeUserIds().contains(userId)){
                    c.setListFlag(Global.YES);
                }
            }
        }
        return list;
    }
}
