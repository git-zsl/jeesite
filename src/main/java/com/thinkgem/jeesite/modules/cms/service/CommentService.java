/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cms.entity.Category;
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
    public void delete(Comment entity, Boolean isRe) {
        super.delete(entity);
    }

    @Transactional(readOnly = false)
    public int findChildNum(Comment comment, int i) {
        return ChildNum(comment,i);
    }


    public int ChildNum(Comment comment, int i) {
        i += comment.getChildComments().size();
        for (Comment c : comment.getChildComments()) {
            if(!c.getChildComments().isEmpty()){
                i = ChildNum(c,i);
            }
        }
        return i;
    }
    @Transactional(readOnly = false)
    public void findParentCommentAndSet(Comment comment){
        updateCommentNum(comment);
    }

    @Transactional(readOnly = false)
    public void updateCommentNum(Comment comment){
        comment.setCommentNum(Integer.parseInt(comment.getCommentNum())+1+"");
        super.save(comment);
        List<Comment> parents = dao.findParent(comment);
        if(!parents.isEmpty()){
            findParentCommentAndSet(parents.get(0));
        }
    }

}
