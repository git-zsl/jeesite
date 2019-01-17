/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobcity.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.jobcity.entity.JobCity;
import com.thinkgem.jeesite.modules.jobcity.service.JobCityService;

import java.util.Iterator;
import java.util.List;

/**
 * 招聘城市Controller
 * @author zsl
 * @version 2019-01-13
 */
@Controller
@RequestMapping(value = "${adminPath}/jobcity/jobCity")
public class JobCityController extends BaseController {

	@Autowired
	private JobCityService jobCityService;
	
	@ModelAttribute
	public JobCity get(@RequestParam(required=false) String id) {
		JobCity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobCityService.get(id);
		}
		if (entity == null){
			entity = new JobCity();
		}
		return entity;
	}
	
	@RequiresPermissions("jobcity:jobCity:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobCity jobCity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JobCity> page = jobCityService.findPage(new Page<JobCity>(request, response), jobCity); 
		model.addAttribute("page", page);
		return "modules/jobcity/jobCityList";
	}

	@RequiresPermissions("jobcity:jobCity:view")
	@RequestMapping(value = "form")
	public String form(JobCity jobCity, Model model) {
		model.addAttribute("jobCity", jobCity);
		return "modules/jobcity/jobCityForm";
	}

	@RequiresPermissions("jobcity:jobCity:edit")
	@RequestMapping(value = "save")
	public String save(JobCity jobCity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobCity)){
			return form(jobCity, model);
		}
		jobCityService.save(jobCity);
		addMessage(redirectAttributes, "保存招聘城市成功");
		return "redirect:"+Global.getAdminPath()+"/jobcity/jobCity/?repage";
	}
	
	@RequiresPermissions("jobcity:jobCity:edit")
	@RequestMapping(value = "delete")
	public String delete(JobCity jobCity, RedirectAttributes redirectAttributes) {
		jobCityService.delete(jobCity);
		addMessage(redirectAttributes, "删除招聘城市成功");
		return "redirect:"+Global.getAdminPath()+"/jobcity/jobCity/?repage";
	}


	/**
	 * 招聘城市接口选择
	 */
	@RequestMapping(value = "findJobCity",method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity findJobCity(JobCity jobCity){
		List<JobCity> codes = null;
		try {
			List<JobCity> list = jobCityService.findList(jobCity);
			codes = jobCityService.findCode(jobCity);
			if(list.isEmpty()){
				return ReturnEntity.success(list,"城市列表为空，请添加");
			}
			for (JobCity c : codes) {
				for ( JobCity ct: list) {
					if(ct.getCode().equals(c.getCode())){
						c.getChildList().add(ct);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			LogUtils.getLogInfo(JobCityController.class).info("获取城市列表出错",e);
			return ReturnEntity.fail("获取城市列表出错");
		}
		return  ReturnEntity.success(codes,"获取城市列表成功");
	}

	/**
	 * 招聘城市列表接口
	 */
	@RequestMapping(value = "findAllJobCity",method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity findAllJobCity(JobCity jobCity){
		List<JobCity> list = null;
		try {
			list = jobCityService.findList(jobCity);
		}catch (Exception e){
			e.printStackTrace();
			LogUtils.getLogInfo(JobCityController.class).info("获取城市列表出错",e);
			return ReturnEntity.fail("获取城市列表出错");
		}
		return  ReturnEntity.success(list,"获取城市列表成功");
	}
}