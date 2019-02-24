/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.supcan.treelist.cols.Col;
import com.thinkgem.jeesite.common.supcan.treelist.cols.Group;
import com.thinkgem.jeesite.modules.book.entity.BookManager;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.fop.util.LogUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.cms.service.FileTplService;
import com.thinkgem.jeesite.modules.cms.service.SiteService;
import com.thinkgem.jeesite.modules.cms.utils.TplUtils;

/**
 * 栏目Controller
 * @author ThinkGem
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
    @Autowired
   	private FileTplService fileTplService;
    @Autowired
   	private SiteService siteService;
	
	@ModelAttribute("category")
	public Category get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return categoryService.get(id);
		}else{
			return new Category();
		}
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model,@RequestParam(value ="isShowHome",required = false) String isShowHome) {
		List<Category> list = Lists.newArrayList();
		List<Category> sourcelist = categoryService.findByUser(true, null,isShowHome,"");
		Category.sortList(list, sourcelist, Global.NO);
        model.addAttribute("list", list);
		return "modules/cms/categoryList";
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model,@RequestParam(value = "ad",required = false) String ad) {
		if (category.getParent()==null||category.getParent().getId()==null){
			category.setParent(new Category(Global.YES));
		}
		Category parent = categoryService.get(category.getParent().getId());
		if(Objects.isNull(parent)){
			parent = new Category(Global.NO);
			parent.setOffice(UserUtils.getUser().getOffice());
		}
		category.setParent(parent);
		if (category.getOffice()==null||category.getOffice().getId()==null){
			category.setOffice(parent.getOffice());
		}
        model.addAttribute("listViewList",getTplContent(Category.DEFAULT_TEMPLATE));
        model.addAttribute("category_DEFAULT_TEMPLATE",Category.DEFAULT_TEMPLATE);
        model.addAttribute("contentViewList",getTplContent(Article.DEFAULT_TEMPLATE));
        model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
		model.addAttribute("office", category.getOffice());
		model.addAttribute("category", category);
		if(!StringUtils.isBlank(ad)){
			return "modules/cms/categoryADForm";
		}
		return "modules/cms/categoryForm";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "save")
	public String save(Category category, Model model, RedirectAttributes redirectAttributes,@RequestParam(value = "ad",required = false) String ad) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/category/";
		}
		if (!beanValidator(model, category)){
			return form(category, model,ad);
		}
		categoryService.save(category);
		addMessage(redirectAttributes, "保存栏目'" + category.getName() + "'成功");
		return "redirect:" + adminPath + "/cms/category/";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "delete")
	public String delete(Category category, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/category/";
		}
		if (Category.isRoot(category.getId())){
			addMessage(redirectAttributes, "删除栏目失败, 不允许删除顶级栏目或编号为空");
		}else{
			categoryService.delete(category);
			addMessage(redirectAttributes, "删除栏目成功");
		}
		return "redirect:" + adminPath + "/cms/category/";
	}

	/**
	 * 批量修改栏目排序
	 */
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Category[] entitys = new Category[len];
    	for (int i = 0; i < len; i++) {
    		entitys[i] = categoryService.get(ids[i]);
    		entitys[i].setSort(sorts[i]);
    		categoryService.save(entitys[i]);
    	}
    	addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:" + adminPath + "/cms/category/";
	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String module, @RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHome,@RequestParam(required=false) String ad, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Category> list = categoryService.findByUser(true, module,isShowHome,ad);
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				map.put("module", e.getModule());
				mapList.add(map);
			}
		}
		return mapList;
	}

    private List<String> getTplContent(String prefix) {
   		List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
   		tplList = TplUtils.tplTrim(tplList, prefix, "");
   		return tplList;
   	}

	/**
	 * 主页导航栏接口/(获取子栏目列表)
	 */
	@RequestMapping(value = "navigationBar",method = RequestMethod.POST)
	@ResponseBody
	public ReturnEntity<List<Category>> navigationBar(Category category,@RequestParam(value = "allchildcategory",required = false) String allchildcategory) {
        List<Category> parentList = new ArrayList<>();
	    try{
            category.setIsShowHome(Global.YES);
            List<Category> categorys = categoryService.findNavigationBar(category);
			if(!StringUtils.isBlank(category.getId()) && StringUtils.isBlank(allchildcategory)){
				return ReturnEntity.success(categorys,"查询对应栏目成功");
			}
			if(category.getSubscriber() != 0 && StringUtils.isBlank(allchildcategory)){
				return ReturnEntity.success(categorys,"查询热门栏目成功");
			}
            if(categorys.isEmpty()){
                throw new RuntimeException("查询categorys为空");
            }
			if(!StringUtils.isBlank(allchildcategory) && !StringUtils.isBlank(category.getId())){
				return ReturnEntity.success(categoryService.findChildNavigationBar(category),"查询当前栏目及以下子栏目成功") ;
			}
            for (Category c : categorys) {
                for (Category cc: categorys) {
                    if(!Global.NO.equals(cc.getParent().getId()) && c.getId().equals(cc.getParent().getId())){
                        c.getChildList().add(cc);
                    }
                }
                if(Global.NO.equals(c.getParent().getId())){
                    parentList.add(c);
                }
            }
            //排序
			Collections.sort(parentList, new Comparator<Category>() {
				@Override
				public int compare(Category o1, Category o2) {
					if (o1.getSort() > o2.getSort()){
						return 1;
					}else if (o1.getSort() < o2.getSort()){
						return -1;
					}
					return 0;
				}
			});
        }catch(Exception e){
            LogUtils.getLogInfo(CategoryController.class).info("系统出错",e);
            e.printStackTrace();
            return ReturnEntity.fail("系统出错，请联系管理员");
        }
		return ReturnEntity.success(parentList,"查询栏目成功");
	}

	/**
	 * 上传文章分类接口
	 */
	@RequestMapping("updateArticleClassify")
	@ResponseBody
	public ReturnEntity findUpdateArticleClassify(){
		List<Category> updateArticleClassify = null;
		try{
			updateArticleClassify = categoryService.findUpdateArticleClassify();
		}catch (Exception e){
			LogUtils.getLogInfo(CategoryController.class).info("查询上传文章分类出错",e);
			e.printStackTrace();
			return ReturnEntity.fail("查询上传文章分类出错");
		}
		return ReturnEntity.success(updateArticleClassify,"查询上传文章分类成功");
	}
}
