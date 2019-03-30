/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.web.UserController;
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
import com.thinkgem.jeesite.modules.ad.entity.AdRequirement;
import com.thinkgem.jeesite.modules.ad.service.AdRequirementService;

/**
 * 广告需求Controller
 * @author zsl
 * @version 2019-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/ad/adRequirement")
public class AdRequirementController extends BaseController {

	@Autowired
	private AdRequirementService adRequirementService;
	
	@ModelAttribute
	public AdRequirement get(@RequestParam(required=false) String id) {
		AdRequirement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adRequirementService.get(id);
		}
		if (entity == null){
			entity = new AdRequirement();
		}
		return entity;
	}
	
	@RequiresPermissions("ad:adRequirement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdRequirement adRequirement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdRequirement> page = adRequirementService.findPage(new Page<AdRequirement>(request, response), adRequirement); 
		model.addAttribute("page", page);
		return "modules/ad/adRequirementList";
	}

	@RequiresPermissions("ad:adRequirement:view")
	@RequestMapping(value = "form")
	public String form(AdRequirement adRequirement, Model model) {
		model.addAttribute("adRequirement", adRequirement);
		return "modules/ad/adRequirementForm";
	}

	@RequiresPermissions("ad:adRequirement:edit")
	@RequestMapping(value = "save")
	public String save(AdRequirement adRequirement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, adRequirement)){
			return form(adRequirement, model);
		}
		adRequirementService.save(adRequirement);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/ad/adRequirement/?repage";
	}
	
	@RequiresPermissions("ad:adRequirement:edit")
	@RequestMapping(value = "delete")
	public String delete(AdRequirement adRequirement, RedirectAttributes redirectAttributes) {
		adRequirementService.delete(adRequirement);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/ad/adRequirement/?repage";
	}


	/**
	 * 广告需求发布
	 */
	@RequestMapping("filter/findADRequirement")
	@ResponseBody
	public ReturnEntity findADRequirement(AdRequirement adRequirement,String userId){
		try{
			User user = UserUtils.get(userId);
			adRequirement.setCreateBy(user);
			adRequirement.setUpdateBy(user);
			adRequirementService.save(adRequirement);
		}catch (Exception e){
			e.printStackTrace();
			LogUtils.getLogInfo(AdRequirementController.class).info("程序出错", e);
			return ReturnEntity.fail("程序出错");
		}
		return ReturnEntity.success("查询成功");
	}
}