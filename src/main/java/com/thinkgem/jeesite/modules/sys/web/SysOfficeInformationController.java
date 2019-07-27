/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysOfficeInformation;
import com.thinkgem.jeesite.modules.sys.service.SysOfficeInformationService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 机构详细信息Controller
 *
 * @author zsl
 * @version 2019-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOfficeInformation")
public class SysOfficeInformationController extends BaseController {

    @Autowired
    private SysOfficeInformationService sysOfficeInformationService;

    @ModelAttribute
    public SysOfficeInformation get(@RequestParam(required = false) String id) {
        SysOfficeInformation entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = sysOfficeInformationService.get(id);
        }
        if (entity == null) {
            entity = new SysOfficeInformation();
        }
        return entity;
    }

    @RequiresPermissions("sys:sysOfficeInformation:view")
    @RequestMapping(value = {"list", ""})
    public String list(SysOfficeInformation sysOfficeInformation, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysOfficeInformation> page = sysOfficeInformationService.findPage(new Page<SysOfficeInformation>(request, response), sysOfficeInformation);
        model.addAttribute("page", page);
        return "modules/sys/sysOfficeInformationList";
    }

    @RequiresPermissions("sys:sysOfficeInformation:view")
    @RequestMapping(value = "form")
    public String form(SysOfficeInformation sysOfficeInformation, String userId, Model model) {
        if (StringUtils.isNotBlank(userId)) {
            SysOfficeInformation byUserId = sysOfficeInformationService.findByUserId(userId);
            model.addAttribute("sysOfficeInformation", byUserId);
            return "modules/sys/sysOfficeInformationForm";
        }
        model.addAttribute("sysOfficeInformation", sysOfficeInformation);
        return "modules/sys/sysOfficeInformationForm";
    }

    @RequiresPermissions("sys:sysOfficeInformation:edit")
    @RequestMapping(value = "save")
    public String save(SysOfficeInformation sysOfficeInformation, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, sysOfficeInformation)) {
            return form(sysOfficeInformation, "", model);
        }
        if (Objects.nonNull(sysOfficeInformation.getUser()) && StringUtils.isNotBlank(sysOfficeInformation.getUser().getId())) {
            SysOfficeInformation byUserId = sysOfficeInformationService.findByUserId(sysOfficeInformation.getUser().getId());
            if (Objects.nonNull(byUserId)) {
                //参数转封装
                sysOfficeInformationService.changObject(byUserId, sysOfficeInformation);
            }
        }
        sysOfficeInformationService.save(sysOfficeInformation);
        addMessage(redirectAttributes, "保存信息成功");
        return "redirect:" + Global.getAdminPath() + "/sys/sysOfficeInformation/?repage";
    }

    @RequiresPermissions("sys:sysOfficeInformation:edit")
    @RequestMapping(value = "delete")
    public String delete(SysOfficeInformation sysOfficeInformation, RedirectAttributes redirectAttributes) {
        sysOfficeInformationService.delete(sysOfficeInformation);
        addMessage(redirectAttributes, "删除信息成功");
        return "redirect:" + Global.getAdminPath() + "/sys/sysOfficeInformation/?repage";
    }

    /**
     * 机构用户查询机构详情
     */
    @RequestMapping("filter/findOfficeInfomation")
    @ResponseBody
    public ReturnEntity findOfficeInfomation(@RequestParam("userId") String userId) {
        try {
            SysOfficeInformation sysOfficeInformation = sysOfficeInformationService.findByUserId(userId);
            return ReturnEntity.success(sysOfficeInformation, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getLogInfo(SysOfficeInformationController.class).info("程序出错", e);
            return ReturnEntity.fail("程序出错");
        }
    }

    /**
     * 获取机构创作环境图片接口
     */
    @RequestMapping("filter/findOfficeImage")
    @ResponseBody
    public ReturnEntity findOfficeImage(@RequestParam("userId") String userId) {
        try {
            List<String> officeImageList = sysOfficeInformationService.findOfficeImage(userId);
            return ReturnEntity.success(officeImageList, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getLogInfo(SysOfficeInformationController.class).info("程序出错", e);
            return ReturnEntity.fail("程序出错");
        }
    }
}