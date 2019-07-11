/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.persistence.ReturnStatus;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.area.entity.SysChina;
import com.thinkgem.jeesite.modules.area.service.SysChinaService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Email;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.HomeLoginService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.EmailUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.LoginUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.*;

/**
 * 登录Controller
 *
 * @author zsl
 */
@Controller
@RequestMapping("filter")
public class HomeLoginController extends BaseController {

    @Autowired
    private HomeLoginService homeLoginService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SysChinaService sysChinaService;

    private static final String TRUE = "true";
    private Class clazz = HomeLoginController.class;

    /**
     * token验证失败
     */
    @RequestMapping(value = "/loginFail")
    @ResponseBody
    public ReturnEntity login(HttpServletRequest request, HttpServletResponse response, Model model) {
        LogUtils.getLogInfo(clazz).info("token无效，请重新登录");
        return new ReturnEntity(ReturnStatus.UNAUTHORIZED, "token无效，请登录");
    }

    /**
     * 验证邮箱后用户注册
     */
    @RequestMapping(value = "/homeLogin", method = RequestMethod.GET)
    public String loginFail(@RequestParam Map<String, String> map, boolean isCompany, HttpServletRequest request, User user) {
        String loginName = map.get("loginName");
        try {
            Cache<String, String> cache = cacheManager.getCache(loginName);
            Set<String> keys = cache.keys();
            if (StringUtils.isBlank(loginName) || keys.size() == 0) {
                LogUtils.getLogInfo(clazz).info("邮箱验证失败,loginName为空");
                return "redirect:/failPage.jsp";
            }
            for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
                String key = it.next();
                map.put(key, cache.get(key));
            }
            //开始注册
            homeLoginService.signIn(isCompany, map);
            //清除缓存map
            CacheUtils.removeAll(loginName);
        } catch (Exception e) {
            e.printStackTrace();
            //清除缓存map
            CacheUtils.removeAll(loginName);
            LogUtils.getLogInfo(clazz).info("邮注册失败", e);
            return "redirect:/failPage.jsp";
        }
        return "redirect:http://www.useidea.com";
    }

    /**
     * 邮箱认证
     */
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<String> sentValidateEmail(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> map = new HashMap<>();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                map.put(entry.getKey(), URLDecoder.decode(entry.getValue()[0], "UTF-8"));
            }
            MultipartFile file = null;
            MultipartFile image = null;
            String originalFilename = null;
            String originalFilename1 = null;
            String loginName = map.get("loginName");
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
                file = multipartRequest.getFile("file");
                image = multipartRequest.getFile("image");
                String configPath = Global.getConfig("userfiles.basedir").substring(0, 1) + Global.getConfig("userfiles.basedir").substring(1);
                File file0 = new File(configPath + "/userfiles/" + loginName);
                if (!file0.exists()) {
                    file0.mkdirs();
                }
                if(Objects.nonNull(image)){
                    originalFilename = image.getOriginalFilename();
                    File file1 = new File(configPath + "/userfiles/" + loginName + "/" + originalFilename);
                    image.transferTo(file1);

                    map.put("image", getOnLineUrl(file1.getPath(),request));
                }
                if(Objects.nonNull(file)){
                    originalFilename1 = file.getOriginalFilename();
                    File file2 = new File(configPath + "/userfiles/" + loginName + "/" + originalFilename1);
                    file.transferTo(file2);
                    map.put("file", getOnLineUrl(file2.getPath(),request));
                }
            }
            //个人用户字段
            String email = map.get("email");
            email = URLDecoder.decode(email);
            String isCompany = map.get("isCompany");
            //用户名验证
            if (StringUtils.isBlank(loginName)) {
                LogUtils.getLogInfo(clazz).info("用户名不能为空");
                return ReturnEntity.fail("用户名不能为空");
            }
            User byLoginName = UserUtils.getByLoginName(loginName);
            if (!Objects.isNull(byLoginName)) {
                return ReturnEntity.fail("当前用户名已被占用");
            }
            if (!UserUtils.getByEmail(email)) {
                return ReturnEntity.fail("当前邮箱已被占用");
            }
            //企业用户字段
            //清除缓存map
            CacheUtils.removeAll(loginName);
            if (TRUE.equals(isCompany)) {
                map.put("isCompany", "true");
                String companyId = UserUtils.getCompanyId(Global.OFFICE_TYPE_2);
                map.put("companyId", companyId);
                //需要增加部门（部门字段未添加）

            } else {
                String officeId = UserUtils.getOfficeId(Global.OFFICE_TYPE_1);
                String companyId = UserUtils.getCompanyId(Global.OFFICE_TYPE_1);
                map.put("officeId", officeId);
                map.put("companyId", companyId);
                map.put("isCompany", "false");
            }
            if (StringUtils.isBlank(map.get("loginName")) && StringUtils.isBlank(email)) {
                return ReturnEntity.fail("用户名或者邮箱不能为空");
            }
            //String content = "<a href=http://" + Global.getConfig("serverAddress") + "/filter/homeLogin?loginName=" + loginName + "&isCompany=" + isCompany + ">请点击完成此处激活帐号完成注册</a><br/>";
            String url = "http://" + Global.getConfig("serverAddress") + "/filter/homeLogin?loginName=" + loginName + "&isCompany=" + isCompany;
            EmailUtils.sendHtmlMail(new Email(email, "响创意@USERIDEA", EmailUtils.setEmailPage(loginName,url)));
            CacheUtils.putMapAll(loginName, map);
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("邮箱认证出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("系统出错，请联系管理员");
        }
        return ReturnEntity.success("邮件发送成功");
    }

    /**
     * 用户名验证
     */
    @RequestMapping(value = "/ValidateLoginName", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity<String> ValidateLoginName(@RequestParam Map<String, String> map) {
        try {
            String loginName = map.get("loginName");
            if (StringUtils.isBlank(loginName)) {
                LogUtils.getLogInfo(clazz).info("用户名不能为空");
                return ReturnEntity.fail("用户名不能为空");
            }
            User byLoginName = UserUtils.getByLoginName(loginName);
            if (Objects.isNull(byLoginName)) {
                return ReturnEntity.success("当前用户名可以使用");
            }
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("查询出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("系统内部出错");
        }
        return ReturnEntity.fail("当前用户名已存在");
    }

    /**
     * 名称验证
     */
    @RequestMapping(value = "/ValidateName")
    @ResponseBody
    public ReturnEntity<String> ValidateName(@RequestParam Map<String, String> map) {
        try {
            String name = map.get("name");
            if(StringUtils.isBlank(name)){
                return ReturnEntity.fail("请输入名称");
            }
            User byName = UserUtils.getByName(name);
            if (Objects.isNull(byName)) {
                return ReturnEntity.success("当前用户名可以使用");
            }
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("查询出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("系统内部出错");
        }
        return ReturnEntity.fail("当前用户名已存在");
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
    @ResponseBody
    public ReturnEntity loginSuccess(@RequestParam Map<String, String> map,HttpServletRequest request) {
        User byLoginName = null;
        try {
            String token = "";
            String loginName = map.get("loginName");
            String password = map.get("password");
            String isCompany = map.get("isCompany");
            if (StringUtils.isBlank(loginName)) {
                return ReturnEntity.fail("用户名不能为空");
            }
            if (StringUtils.isBlank(password)) {
                return ReturnEntity.fail("密码不能为空");
            }
            byLoginName = UserUtils.getByLoginName(loginName);
            if (Objects.isNull(byLoginName)) {
                return new ReturnEntity(ReturnStatus.LOGINUSERNAME, "用户名错误");
            }
            if(StringUtils.isBlank(byLoginName.getIsCompany())){
                return ReturnEntity.fail( "此为后台用户，不允许登录主页");
            }
            if (!byLoginName.getIsCompany().equals(isCompany)) {
                return ReturnEntity.fail(Global.TRUE.equals(isCompany) ? "请选择个人登录入口登录" : "请选择企业登录入口登录");
            }
            if (byLoginName.getIsCompany().equals(Global.TRUE) && !byLoginName.getLoginFlag().equals(Global.YES)) {
                return ReturnEntity.fail( "当前企业用户还在审核，或者未通过审核");
            }
            //验证密码
            if (LoginUtils.validatePassword(password, byLoginName.getPassword())) {
                //生成token
               /* String format = MessageFormat.format("{}{}", loginName, Global.ZSL);*/
                String format = loginName + Global.ZSL;
                token = LoginUtils.entryptPassword(format);
                byLoginName.setToken(token);
                byLoginName.setIsCompany(isCompany);
                CacheUtils.put("homeLoginSession_"+byLoginName.getId(),byLoginName.getId());
                return ReturnEntity.success(byLoginName, "登录成功");
            }
            return new ReturnEntity(ReturnStatus.LOGINUPASSWORD, "密码错误");
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("程序出错", e);
            return ReturnEntity.fail("程序出错");
        }

    }

    /**
     * 省市区接口
     * 一次性封闭
     */
    @RequestMapping("/getArea1")
    @ResponseBody
    public ReturnEntity<Area> getAreaData1() {
        List<Area> areas = Lists.newArrayList();
        try {
            List<Area> list = areaService.findTopArea();
            for (Area area : list) {
                    areas.add(area);
                    areaService.findChildArea(area);
            }
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("获取省市区失败", e);
            e.printStackTrace();
            return ReturnEntity.fail("获取省市区失败");
        }
        return ReturnEntity.success(areas, "获取省市区成功");
    }

    /**
     * 省市区接口
     * 封装对应name的数据
     */
    @RequestMapping("/getArea")
    @ResponseBody
    public ReturnEntity<SysChina> getAreaData(SysChina sysChina) {
        try {
            List<SysChina> list = sysChinaService.findCurrentArea(sysChina);
            return ReturnEntity.success(list, "获取省市区成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("获取省市区失败", e);
            e.printStackTrace();
            return ReturnEntity.fail("获取省市区失败");
        }
    }

    /**
     * 上传测试
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public ReturnEntity upload(@RequestParam MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        File file1 = new File("F://11/" + originalFilename);
        file.transferTo(file1);
        return ReturnEntity.success("上传成功");
    }


    /**
     * 密码找回接口
     */
    @RequestMapping("/findPassword")
    @ResponseBody
    public ReturnEntity findPassword(@RequestParam("email") String email,HttpServletRequest request){
        try{
            boolean userEmail = systemService.findUserEmail(email);
            if(userEmail){
                String content = "<a href=http://www.baidu.com>点击完成密码找回</a><br/>";
                EmailUtils.sendHtmlMail(new Email(email, "注册验证", content));
                request.getSession().setAttribute("findPasswordUserId","此处用用户名查询出来用户id");
            }else{
                return ReturnEntity.success("邮箱不正确，请重新输入");
            }
        }catch (Exception e){
            LogUtils.getLogInfo(clazz).info("程序出错", e);
            e.printStackTrace();
            return ReturnEntity.fail("程序出错");
        }
        return ReturnEntity.success("邮件发送成功");
    }

   @RequestMapping("updatePassword")
    public ReturnEntity updatePassword(String newPassword,HttpServletRequest request){
        try{
            String userid = (String)request.getSession().getAttribute("findPasswordUserId");
            request.getSession().removeAttribute("findPasswordUserId");
            if(StringUtils.isBlank(userid)){
                return ReturnEntity.fail("邮箱验证失效，请重新申请验证");
            }
            User user = UserUtils.get(userid);
            systemService.updatePasswordById(userid,user.getLoginName(),newPassword);
            return ReturnEntity.success(true,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return ReturnEntity.success(false,"修改失败");
        }
   }

    /**
     * 同步省市区数据
     */
    @RequestMapping("copyArea")
    @ResponseBody
    public ReturnEntity copyArea(){
        try{
            List<Area> all = areaService.findList(new Area());
            for (Area a : all ) {
                a.setParentIds("");
                a.setType("");
                StringBuffer sb = new StringBuffer();
                for (Area b : all) {
                    if(a.getId().equals(b.getParentId())){
                        sb.append(a.getId()).append(",");
                    }
                }
                if(a.getName().contains("中国")){
                    a.setType("1");
                }else  if(a.getName().contains("省")){
                    a.setType("2");
                }else  if(a.getName().contains("市")){
                    a.setType("3");
                }else if(a.getName().contains("区")){
                    a.setType("4");
                }else{
                    a.setType("4");
                }
                a.setParentIds(sb.toString());
                areaService.save(a);
            }
            return ReturnEntity.success("同步成功");
        }catch (Exception e){
            e.printStackTrace();
            return ReturnEntity.fail("同步失败");
        }
    }

    /**
     * 获取密码
     * @param user
     * @return
     */
    @RequestMapping("filter/getMyPassword")
    @ResponseBody
    public ReturnEntity getMyPassword(User user){
        return ReturnEntity.success(SystemService.entryptPassword(user.getNewPassword()),"获取成功");
    }


    private String getOnLineUrl(String path,HttpServletRequest request){
        String configPath = Global.getConfig("userfiles.basedir").substring(0, 1) + Global.getConfig("userfiles.basedir").substring(1);
        String replace = path.replace("\\", "/");
        String s = replace.split(configPath)[1];
        String url =  request.getContextPath() +  s;
        return url;
    }
}
