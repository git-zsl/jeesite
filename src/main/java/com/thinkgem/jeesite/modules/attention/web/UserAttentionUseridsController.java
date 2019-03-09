/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attention.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.cms.web.ArticleController;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
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
import com.thinkgem.jeesite.modules.attention.entity.UserAttentionUserids;
import com.thinkgem.jeesite.modules.attention.service.UserAttentionUseridsService;

import java.util.List;
import java.util.Objects;

/**
 * 关注人关系Controller
 * @author zsl
 * @version 2019-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/attention/userAttentionUserids")
public class UserAttentionUseridsController extends BaseController {

	@Autowired
	private UserAttentionUseridsService userAttentionUseridsService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public UserAttentionUserids get(@RequestParam(required=false) String id) {
		UserAttentionUserids entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userAttentionUseridsService.get(id);
		}
		if (entity == null){
			entity = new UserAttentionUserids();
		}
		return entity;
	}
	
	@RequiresPermissions("attention:userAttentionUserids:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAttentionUserids userAttentionUserids, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserAttentionUserids> page = userAttentionUseridsService.findPage(new Page<UserAttentionUserids>(request, response), userAttentionUserids); 
		model.addAttribute("page", page);
		return "modules/attention/userAttentionUseridsList";
	}

	@RequiresPermissions("attention:userAttentionUserids:view")
	@RequestMapping(value = "form")
	public String form(UserAttentionUserids userAttentionUserids, Model model) {
		model.addAttribute("userAttentionUserids", userAttentionUserids);
		return "modules/attention/userAttentionUseridsForm";
	}

	@RequiresPermissions("attention:userAttentionUserids:edit")
	@RequestMapping(value = "save")
	public String save(UserAttentionUserids userAttentionUserids, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userAttentionUserids)){
			return form(userAttentionUserids, model);
		}
		userAttentionUseridsService.save(userAttentionUserids);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/attention/userAttentionUserids/?repage";
	}
	
	@RequiresPermissions("attention:userAttentionUserids:edit")
	@RequestMapping(value = "delete")
	public String delete(UserAttentionUserids userAttentionUserids, RedirectAttributes redirectAttributes) {
		userAttentionUseridsService.delete(userAttentionUserids);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/attention/userAttentionUserids/?repage";
	}


	/**
	 * 用户关注人列表(我关注了什么人)
	 */
	@RequestMapping("filter/attentionList")
	@ResponseBody
	public ReturnEntity findAttentionList(@RequestParam("userId") String userId,UserAttentionUserids userAttentionUserids){
		UserAttentionUserids userAttentionUserids1 = null;
		try{
			if(StringUtils.isBlank(userId) || Objects.isNull(UserUtils.get(userId))){
				LogUtils.getLogInfo(UserAttentionUseridsController.class).info("用户id为空，用户未登录，请登录后操作");
				return ReturnEntity.fail("用户未登录，请登录后操作");
			}
			User user = UserUtils.get(userId);
			userAttentionUserids.setUser(user);
			List<UserAttentionUserids> list = userAttentionUseridsService.findList(userAttentionUserids);
			userAttentionUserids1 = list.get(0);
			List<User> userList = userAttentionUserids1.getUserList();
			String[] ids = userAttentionUserids1.getAttentionUserIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				User user1 = UserUtils.get(ids[i]);
				if(!Objects.isNull(user1)){
					userList.add(user1);
				}
			}
		}catch (Exception e){
			LogUtils.getLogInfo(UserAttentionUseridsController.class).info("查询出错",e);
			e.printStackTrace();
			return ReturnEntity.fail("查询出错");
		}
		return ReturnEntity.success(userAttentionUserids1,"查询关注人列表成功");
	}

	/**
	 * 关注按钮功能接口(未判断重复关注)
	 */
	@RequestMapping("filter/attention")
	@ResponseBody
	public ReturnEntity attentionSave(@RequestParam("userId") String userId,@RequestParam("attention2UserId") String attention2UserId,UserAttentionUserids userAttentionUserids){
		try{
			if(StringUtils.isBlank(userId) || Objects.isNull(UserUtils.get(userId))){
				LogUtils.getLogInfo(UserAttentionUseridsController.class).info("userId为空");
				return ReturnEntity.fail("关注失败");
			}
			if(StringUtils.isBlank(attention2UserId) || Objects.isNull(UserUtils.get(attention2UserId))){
				LogUtils.getLogInfo(UserAttentionUseridsController.class).info("attention2UserId为空");
				return ReturnEntity.fail("关注失败");
			}
			userAttentionUserids.setUser(UserUtils.get(userId));
			User user1 = UserUtils.get(userId); //我去关注
			User user2 = UserUtils.get(attention2UserId); //我被关注
			if(Objects.isNull(user2)){
				LogUtils.getLogInfo(UserAttentionUseridsController.class).info("关注人不存在");
				return ReturnEntity.fail("关注失败");
			}
			List<UserAttentionUserids> list = userAttentionUseridsService.findList(userAttentionUserids);
			StringBuffer sb = new StringBuffer();
			UserAttentionUserids userAttentionUserids1 = new UserAttentionUserids();
			if(!list.isEmpty()){
				userAttentionUserids1 = list.get(0);
				String attentionUserIds = userAttentionUserids1.getAttentionUserIds();
				if(attentionUserIds.contains(attention2UserId)){
					return ReturnEntity.fail("已经关注过此用户");
				}
				if(StringUtils.isBlank(attentionUserIds)){
					sb.append(",");
				}
				sb.append(attentionUserIds).append(attention2UserId).append(",");
				userAttentionUserids1.setAttentionUserIds(sb.toString());
			}else{
				userAttentionUserids1.setUser(user1);
				userAttentionUserids1.setAttentionUserIds("," +attention2UserId + ",");
			}
			userAttentionUseridsService.save(userAttentionUserids1);
			if(StringUtils.isBlank(user1.getAttentionNum())){
				user1.setAttentionNum(Global.NO);
			}
			if(StringUtils.isBlank(user2.getAttention2Num())){
				user2.setAttention2Num(Global.NO);
			}
			String s = Integer.parseInt(user1.getAttentionNum()) + 1 + "";
			String s1 = Integer.parseInt(user2.getAttention2Num()) + 1 + "";
			user1.setAttentionNum(s);
			user2.setAttention2Num(s1);
			userAttentionUseridsService.updateUserData(user1);
			userAttentionUseridsService.updateUserData(user2);
		}catch (Exception e){
			LogUtils.getLogInfo(UserAttentionUseridsController.class).info("关注失败",e);
			e.printStackTrace();
			return ReturnEntity.fail("关注失败");
		}
		return ReturnEntity.success("关注成功");
	}

	/**
	 * 用户关注人列表(什么人关注了我)
	 */
	@RequestMapping("filter/attention2List")
	@ResponseBody
	public ReturnEntity findAttention2MeList(@RequestParam("userId") String userId,UserAttentionUserids userAttentionUserids){
		UserAttentionUserids userAttentionUserids1 = null;
		List<User> userList = Lists.newArrayList();
		try{
			if(StringUtils.isBlank(userId) || Objects.isNull(UserUtils.get(userId))){
				LogUtils.getLogInfo(UserAttentionUseridsController.class).info("用户id为空，用户未登录，请登录后操作");
				return ReturnEntity.fail("用户未登录，请登录后操作");
			}
			List<UserAttentionUserids> list = userAttentionUseridsService.findByIds(userId);
			for (UserAttentionUserids uu : list) {
				userList.add(uu.getUser());
			}
		}catch (Exception e){
			LogUtils.getLogInfo(UserAttentionUseridsController.class).info("查询出错",e);
			e.printStackTrace();
			return ReturnEntity.fail("查询出错");
		}
		return ReturnEntity.success(userList,"查询粉丝列表成功");
	}


}