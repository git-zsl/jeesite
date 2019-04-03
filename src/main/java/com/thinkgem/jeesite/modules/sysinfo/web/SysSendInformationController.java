/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sysinfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sysinfo.entity.SysSendInformation;
import com.thinkgem.jeesite.modules.sysinfo.service.SysSendInformationService;

/**
 * 系统信息Controller
 *
 * @author zsl
 * @version 2019-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sysinfo/sysSendInformation")
public class SysSendInformationController extends BaseController {

    @Autowired
    private SysSendInformationService sysSendInformationService;

    @ModelAttribute
    public SysSendInformation get(@RequestParam(required = false) String id) {
        SysSendInformation entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = sysSendInformationService.get(id);
        }
        if (entity == null) {
            entity = new SysSendInformation();
        }
        return entity;
    }

    @RequiresPermissions("sysinfo:sysSendInformation:view")
    @RequestMapping(value = {"list", ""})
    public String list(SysSendInformation sysSendInformation, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysSendInformation> page = sysSendInformationService.findPage(new Page<SysSendInformation>(request, response), sysSendInformation);
        model.addAttribute("page", page);
        return "modules/sysinfo/sysSendInformationList";
    }

    @RequiresPermissions("sysinfo:sysSendInformation:view")
    @RequestMapping(value = "form")
    public String form(SysSendInformation sysSendInformation, Model model) {
        model.addAttribute("sysSendInformation", sysSendInformation);
        return "modules/sysinfo/sysSendInformationForm";
    }

    @RequiresPermissions("sysinfo:sysSendInformation:edit")
    @RequestMapping(value = "save")
    public String save(SysSendInformation sysSendInformation, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, sysSendInformation)) {
            return form(sysSendInformation, model);
        }
        sysSendInformationService.save(sysSendInformation);
        addMessage(redirectAttributes, "保存系统信息成功");
        return "redirect:" + Global.getAdminPath() + "/sysinfo/sysSendInformation/?repage";
    }

    @RequiresPermissions("sysinfo:sysSendInformation:edit")
    @RequestMapping(value = "delete")
    public String delete(SysSendInformation sysSendInformation, RedirectAttributes redirectAttributes) {
        sysSendInformationService.delete(sysSendInformation);
        addMessage(redirectAttributes, "删除系统信息成功");
        return "redirect:" + Global.getAdminPath() + "/sysinfo/sysSendInformation/?repage";
    }

    /**
     * 查询系统信息数据
     */
    @RequestMapping("filter/findSysInformation")
    @ResponseBody
    public ReturnEntity findSysInformation(SysSendInformation sysSendInformation,String userId,String type,HttpServletRequest request, HttpServletResponse response){
        try{
            sysSendInformation.setUser(new User(userId));
            sysSendInformation.setType(type);
            Page<SysSendInformation> page = sysSendInformationService.findPage(new Page<SysSendInformation>(request, response), sysSendInformation);
            return ReturnEntity.success(page,"查询成功");
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.getLogInfo(SysSendInformationController.class).info("程序出错",e);
            return ReturnEntity.fail("查询成功");
        }
    }
}