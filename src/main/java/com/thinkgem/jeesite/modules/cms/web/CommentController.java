/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Comment;
import com.thinkgem.jeesite.modules.cms.service.CommentService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 评论Controller
 * @author ThinkGem
 * @version
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/comment")
public class CommentController extends BaseController {

	@Autowired
	private CommentService commentService;
	
	@ModelAttribute
	public Comment get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return commentService.get(id);
		}else{
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
		if (beanValidator(redirectAttributes, comment)){
			if (comment.getAuditUser() == null){
				comment.setAuditUser(UserUtils.getUser());
				comment.setAuditDate(new Date());
			}
			comment.setDelFlag(Comment.DEL_FLAG_NORMAL);
			commentService.save(comment);
			addMessage(redirectAttributes, DictUtils.getDictLabel(comment.getDelFlag(), "cms_del_flag", "保存")
					+"评论'" + StringUtils.abbr(StringUtils.replaceHtml(comment.getContent()),50) + "'成功");
		}
		return "redirect:" + adminPath + "/cms/comment/?repage&delFlag=2";
	}
	
	@RequiresPermissions("cms:comment:edit")
	@RequestMapping(value = "delete")
	public String delete(Comment comment, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes) {
		commentService.delete(comment, isRe);
		addMessage(redirectAttributes, (isRe!=null&&isRe?"恢复审核":"删除")+"评论成功");
		return "redirect:" + adminPath + "/cms/comment/?repage&delFlag=2";
	}

	/**
	 * 主页获取请教接口
	 */
	@RequestMapping(value = "consultationList",method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity<Page<Comment>> findConsultationList(@ModelAttribute Comment comment, HttpServletRequest request, HttpServletResponse response) {
		Page<Comment> page =null;
		try{
			page = commentService.findconsultationPage(new Page<Comment>(request, response), comment);
			List<Comment> comments =new ArrayList<>();
			if(!page.getList().isEmpty()){
				for (Comment c : page.getList()) {
					c.setCommentNum("0");
					for (Comment cc : page.getList()) {
						if(c.getId().equals(cc.getParentContentId())){
							c.getChildComments().add(cc);
							c.setCommentNum(Integer.parseInt(c.getCommentNum())+1+"");
						}
					}
					if(StringUtils.isBlank(c.getParentContentId())){
						comments.add(c);
					}
				}
			}
			//统计评论数量
			for (Comment c : comments) {
				int i = 0;
				if(!c.getChildComments().isEmpty()){
					int childNum = commentService.findChildNum(c,i);
					c.setCommentNum(childNum + "");
				}
			}
			page.setList(comments);
		}catch (Exception e){
			LogUtils.getLogInfo(CommentController.class).info("程序出错",e);
			e.printStackTrace();
			ReturnEntity.fail("程序出错");
		}
		return ReturnEntity.success(page,"获取评论列表成功");
	}
}
