/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.EmailUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.web.util.WebUtils;

/**
 * 用户Controller
 *
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private OfficeService officeService;

    private static final String OFFICE_TYPE_1 = "企业用户";
    private static final String OFFICE_TYPE_2 = "企业用户";
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @ModelAttribute
    public User get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return systemService.getUser(id);
        } else {
            return new User();
        }
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"index"})
    public String index(User user, Model model) {
        return "modules/sys/userIndex";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        return "modules/sys/userList";
    }

    @ResponseBody
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"listData"})
    public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        return page;
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "form")
    public String form(User user, Model model) {
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", systemService.findAllRole());
        return "modules/sys/userForm";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "save")
    public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(new Office(request.getParameter("company.id")));
        user.setOffice(new Office(request.getParameter("office.id")));
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidator(model, user)) {
            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        List<String> roleIdList = user.getRoleIdList();
        for (Role r : systemService.findAllRole()) {
            if (roleIdList.contains(r.getId())) {
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);
        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        if (UserUtils.getUser().getId().equals(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
        } else if (User.isAdmin(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
        } else {
            systemService.deleteUser(user);
            addMessage(redirectAttributes, "删除用户成功");
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 导出用户数据
     *
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
            new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 导入用户数据
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<User> list = ei.getDataList(User.class);
            for (User user : list) {
                try {
                    if ("true".equals(checkLoginName("", user.getLoginName()))) {
                        user.setPassword(SystemService.entryptPassword("123456"));
                        BeanValidators.validateWithException(validator, user);
                        systemService.saveUser(user);
                        successNum++;
                    } else {
                        failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
                        failureNum++;
                    }
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 下载导入用户数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据导入模板.xlsx";
            List<User> list = Lists.newArrayList();
            list.add(UserUtils.getUser());
            new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 用户信息显示及保存
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "info")
    public String info(User user, HttpServletResponse response, Model model) {
        User currentUser = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getName())) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userInfo";
            }
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone(user.getPhone());
            currentUser.setMobile(user.getMobile());
            currentUser.setRemarks(user.getRemarks());
            currentUser.setPhoto(user.getPhoto());
            systemService.updateUserInfo(currentUser);
            model.addAttribute("message", "保存用户信息成功");
        }
        model.addAttribute("user", currentUser);
        //修改Global 没有私有构造函数，实现懒汉式单例模式.在第一次调用的时候实例化自己！
        model.addAttribute("Global", Global.getInstance());
        return "modules/sys/userInfo";
    }

    /**
     * 返回用户信息
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "infoData")
    public User infoData() {
        return UserUtils.getUser();
    }

    /**
     * 修改个人用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userModifyPwd";
            }
            if (SystemService.validatePassword(oldPassword, user.getPassword())) {
                systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
                model.addAttribute("message", "修改密码成功");
            } else {
                model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }
        model.addAttribute("user", user);
        return "modules/sys/userModifyPwd";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = systemService.findUserByOfficeId(officeId);
        for (int i = 0; i < list.size(); i++) {
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "u_" + e.getId());
            map.put("pId", officeId);
            map.put("name", StringUtils.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"personalList"})
    public String personalList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Office office = new Office();
        office.setName("个人用户");
        List<Office> listByName = officeService.findListByName(office);
        user.setOffice(listByName.get(0));
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        if (user.getDelFlag().equals("1")) {
            page.setList(systemService.findBlacklist(user));
        }
        model.addAttribute("page", page);
        return "modules/peruser/personalUserList";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "personalForm")
    public String personalForm(User user, Model model) {
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", systemService.findAllRole());
        return "modules/peruser/personalUserForm";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "personalSave")
    public String personalSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(new Office(request.getParameter("company.id")));
        user.setOffice(new Office(request.getParameter("office.id")));
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidator(model, user)) {
            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        List<String> roleIdList = user.getRoleIdList();
        for (Role r : systemService.findAllRole()) {
            if (roleIdList.contains(r.getId())) {
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);
        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
        return "redirect:" + adminPath + "/sys/user/personalList?repage";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"businessList"})
    public String companyList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Office office = new Office();
        office.setName("企业用户");
        List<Office> listByName = officeService.findCompanyListByName(office);
        if (!listByName.isEmpty()) {
            user.setOffice(listByName.get(0));
        }
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        if (!user.getDelFlag().equals("0")) {
            page.setList(systemService.findCompanyBlacklist(user));
        }
        model.addAttribute("page", page);
        return "modules/business/bussinessUserList";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "bussinessDelete")
    public String bussinessDelete(User user, RedirectAttributes redirectAttributes, @RequestParam(value = "isSendEmail", required = false) String isSendEmail) {
        systemService.deleteOrrecover(user);
        addMessage(redirectAttributes, "操作成功");
        //审核失败发送邮件
        if (!StringUtils.isBlank(isSendEmail)) {
            ExecutorService archiveService = Executors.newSingleThreadExecutor();
            archiveService.execute(() -> {
                try {
                    System.out.println("发邮件ing......");
                    EmailUtils.sendHtmlMail(new Email(user.getEmail(), "审核失败", "资料缺失，请重新提交资料审核"));
                } catch (Exception ex) {
                    LOG.info("线程发送邮件失败：{}", ex);
                }
            });
        }
        return "redirect:" + Global.getAdminPath() + "/sys/user/businessList/?repage";
    }

/*	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "bussinessDelete")
	public String bussinessDelete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/bussinessUserList?repage";
	}*/

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "bussinessSave")
    public String bussinessSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(new Office(request.getParameter("company.id")));
        user.setOffice(new Office(request.getParameter("office.id")));
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidator(model, user)) {
            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        List<String> roleIdList = user.getRoleIdList();
        for (Role r : systemService.findAllRole()) {
            if (roleIdList.contains(r.getId())) {
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);
        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
        return "redirect:" + adminPath + "/sys/user/businessList?repage";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "bussinessForm")
    public String bussinessForm(User user, Model model) {
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", systemService.findAllRole());
        return "modules/business/bussinessUserForm";
    }

    /**
     * 个人信息更新接口
     */
    @RequestMapping("/h/updateInformation")
    @ResponseBody
    public ReturnEntity updateInformation(@ModelAttribute User user,HttpServletRequest request){
        MultipartFile image = null;
        try{
            //解码
            user = systemService.decode(user);
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
                image = multipartRequest.getFile("image");
                String originalFilename = Encodes.urlDecode(image.getOriginalFilename());
                String configPath = Global.getConfig("userfiles.basedir").substring(0, 1) + Global.getConfig("userfiles.basedir").substring(1);
                File file1 = new File(configPath + "/" + Global.getConfig("homePhoto") +user.getLoginName() + "/" + originalFilename);
                if (!file1.getParentFile().exists()) {
                    file1.getParentFile().mkdirs();
                }
                String wappPath = request.getSession().getServletContext().getContextPath();
                image.transferTo(file1);
                String s = wappPath + "/" + file1.getPath().substring(configPath.length()+1);
                user.setPhoto(s);
                systemService.updateHomeUserInformation(user);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.getLogInfo(UserController.class).info("程序出错",e);
        }
        return ReturnEntity.success(UserUtils.get(user.getId()),"更新成功");
    }

    /**
     * 企业信息更新接口
     */
    @RequestMapping("filter/updateOfficeInformation")
    @ResponseBody
    public ReturnEntity updateOfficeInformation(MultipartFile headPhoto,@ModelAttribute User user, @ModelAttribute SysOfficeInformation sysOfficeInformation){
        try{

            systemService.updateHomeUserInformation(user);
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.getLogInfo(UserController.class).info("程序出错",e);
        }
        return ReturnEntity.success(UserUtils.get(user.getId()),"更新成功");
    }

}
