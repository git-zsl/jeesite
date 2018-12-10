/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classificationtree.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.book.entity.BookManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.classificationtree.entity.Classificationtree;
import com.thinkgem.jeesite.modules.classificationtree.service.ClassificationtreeService;

/**
 * 分类Controller
 * @author zsl
 * @version 2018-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/classificationtree/classificationtree")
public class ClassificationtreeController extends BaseController {

	@Autowired
	private ClassificationtreeService classificationtreeService;
	
	@ModelAttribute
	public Classificationtree get(@RequestParam(required=false) String id) {
		Classificationtree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = classificationtreeService.get(id);
		}
		if (entity == null){
			entity = new Classificationtree();
		}
		return entity;
	}
	
	@RequiresPermissions("classificationtree:classificationtree:view")
	@RequestMapping(value = {"list", ""})
	public String list(Classificationtree classificationtree, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Classificationtree> list = classificationtreeService.findList(classificationtree); 
		model.addAttribute("list", list);
		return "modules/classificationtree/classificationtreeList";
	}

	@RequiresPermissions("classificationtree:classificationtree:view")
	@RequestMapping(value = "form")
	public String form(Classificationtree classificationtree, Model model) {
		if (classificationtree.getParent()!=null && StringUtils.isNotBlank(classificationtree.getParent().getId())){
			classificationtree.setParent(classificationtreeService.get(classificationtree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(classificationtree.getId())){
				Classificationtree classificationtreeChild = new Classificationtree();
				classificationtreeChild.setParent(new Classificationtree(classificationtree.getParent().getId()));
				List<Classificationtree> list = classificationtreeService.findList(classificationtree); 
				if (list.size() > 0){
					classificationtree.setSort(list.get(list.size()-1).getSort());
					if (classificationtree.getSort() != null){
						classificationtree.setSort(classificationtree.getSort() + 30);
					}
				}
			}
		}
		if (classificationtree.getSort() == null){
			classificationtree.setSort(30);
		}
		model.addAttribute("classificationtree", classificationtree);
		return "modules/classificationtree/classificationtreeForm";
	}

	@RequiresPermissions("classificationtree:classificationtree:edit")
	@RequestMapping(value = "save")
	public String save(Classificationtree classificationtree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, classificationtree)){
			return form(classificationtree, model);
		}
		classificationtreeService.save(classificationtree);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/classificationtree/classificationtree/?repage";
	}
	
	@RequiresPermissions("classificationtree:classificationtree:edit")
	@RequestMapping(value = "delete")
	public String delete(Classificationtree classificationtree, RedirectAttributes redirectAttributes) {
		classificationtreeService.delete(classificationtree);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/classificationtree/classificationtree/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Classificationtree> list = classificationtreeService.findList(new Classificationtree());
		for (int i=0; i<list.size(); i++){
			Classificationtree e = list.get(i);
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


	/**
	 * 主页分类接口
	 *
	 */

	@RequestMapping(value = "findClassification" ,method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity<List<Classificationtree>> findClassification() {
		Classificationtree classificationtree = new Classificationtree();
		System.out.println(",,");
		List<Classificationtree> Classificationtrees = classificationtreeService.findList(classificationtree);
		return ReturnEntity.success(Classificationtrees,"查询分组信息成功");
	}
}