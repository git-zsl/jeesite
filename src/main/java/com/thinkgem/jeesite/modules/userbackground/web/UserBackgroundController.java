/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userbackground.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
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
import com.thinkgem.jeesite.modules.userbackground.entity.UserBackground;
import com.thinkgem.jeesite.modules.userbackground.service.UserBackgroundService;

/**
 * 用户背景管理Controller
 *
 * @author zsl
 * @version 2019-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/userbackground/userBackground")
public class UserBackgroundController extends BaseController {

    @Autowired
    private UserBackgroundService userBackgroundService;

    @ModelAttribute
    public UserBackground get(@RequestParam(required = false) String id) {
        UserBackground entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userBackgroundService.get(id);
        }
        if (entity == null) {
            entity = new UserBackground();
        }
        return entity;
    }

    @RequiresPermissions("userbackground:userBackground:view")
    @RequestMapping(value = {"list", ""})
    public String list(UserBackground userBackground, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserBackground> page = userBackgroundService.findPage(new Page<UserBackground>(request, response), userBackground);
        model.addAttribute("page", page);
        return "modules/userbackground/userBackgroundList";
    }

    @RequiresPermissions("userbackground:userBackground:view")
    @RequestMapping(value = "form")
    public String form(UserBackground userBackground, Model model) {
        model.addAttribute("userBackground", userBackground);
        return "modules/userbackground/userBackgroundForm";
    }

    @RequiresPermissions("userbackground:userBackground:edit")
    @RequestMapping(value = "save")
    public String save(UserBackground userBackground, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, userBackground)) {
            return form(userBackground, model);
        }
        userBackgroundService.save(userBackground);
        addMessage(redirectAttributes, "保存背景成功");
        return "redirect:" + Global.getAdminPath() + "/userbackground/userBackground/?repage";
    }

    @RequiresPermissions("userbackground:userBackground:edit")
    @RequestMapping(value = "delete")
    public String delete(UserBackground userBackground, RedirectAttributes redirectAttributes) {
        userBackgroundService.delete(userBackground);
        addMessage(redirectAttributes, "删除背景成功");
        return "redirect:" + Global.getAdminPath() + "/userbackground/userBackground/?repage";
    }


    /**
     * 查询对应用户背景接口
     *
     * @param userBackground
     * @return
     */
    @RequestMapping("filter/findBackBround")
    @ResponseBody
    public ReturnEntity findBackBround(UserBackground userBackground) {
        try {
            //userBackgroundService.findBackBroundByUserId();
            return ReturnEntity.success(null, "查询背景成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnEntity.fail("系统出错");
        }
    }

    /**
     * 保存背景接口
     * @param userBackground
     * @return
     */
    @RequestMapping("filter/saveBackBround")
    @ResponseBody
    public ReturnEntity saveBackBround(UserBackground userBackground) {
        try {
                //保存逻辑未写
            return ReturnEntity.success("保存背景成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnEntity.fail("保存背景失败");
        }
    }
}