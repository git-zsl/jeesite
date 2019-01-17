/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.jobselect.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.jobselect.entity.JobSelect;
import com.thinkgem.jeesite.modules.jobselect.service.JobSelectService;

/**
 * 招聘搜索条件Controller
 * @author zsl
 * @version 2019-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/jobselect/jobSelect")
public class JobSelectController extends BaseController {

	@Autowired
	private JobSelectService jobSelectService;
	
	@ModelAttribute
	public JobSelect get(@RequestParam(required=false) String id) {
		JobSelect entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobSelectService.get(id);
		}
		if (entity == null){
			entity = new JobSelect();
		}
		return entity;
	}
	
	@RequiresPermissions("jobselect:jobSelect:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobSelect jobSelect, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<JobSelect> list = jobSelectService.findList(jobSelect); 
		model.addAttribute("list", list);
		return "modules/jobselect/jobSelectList";
	}

	@RequiresPermissions("jobselect:jobSelect:view")
	@RequestMapping(value = "form")
	public String form(JobSelect jobSelect, Model model) {
		if (jobSelect.getParent()!=null && StringUtils.isNotBlank(jobSelect.getParent().getId())){
			jobSelect.setParent(jobSelectService.get(jobSelect.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(jobSelect.getId())){
				JobSelect jobSelectChild = new JobSelect();
				jobSelectChild.setParent(new JobSelect(jobSelect.getParent().getId()));
				List<JobSelect> list = jobSelectService.findList(jobSelect); 
				if (list.size() > 0){
					jobSelect.setSort(list.get(list.size()-1).getSort());
					if (jobSelect.getSort() != null){
						jobSelect.setSort(jobSelect.getSort() + 30);
					}
				}
			}
		}
		if (jobSelect.getSort() == null){
			jobSelect.setSort(30);
		}
		model.addAttribute("jobSelect", jobSelect);
		return "modules/jobselect/jobSelectForm";
	}

	@RequiresPermissions("jobselect:jobSelect:edit")
	@RequestMapping(value = "save")
	public String save(JobSelect jobSelect, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobSelect)){
			return form(jobSelect, model);
		}
		jobSelectService.save(jobSelect);
		addMessage(redirectAttributes, "保存搜索条件成功");
		return "redirect:"+Global.getAdminPath()+"/jobselect/jobSelect/?repage";
	}
	
	@RequiresPermissions("jobselect:jobSelect:edit")
	@RequestMapping(value = "delete")
	public String delete(JobSelect jobSelect, RedirectAttributes redirectAttributes) {
		jobSelectService.delete(jobSelect);
		addMessage(redirectAttributes, "删除搜索条件成功");
		return "redirect:"+Global.getAdminPath()+"/jobselect/jobSelect/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<JobSelect> list = jobSelectService.findList(new JobSelect());
		for (int i=0; i<list.size(); i++){
			JobSelect e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}