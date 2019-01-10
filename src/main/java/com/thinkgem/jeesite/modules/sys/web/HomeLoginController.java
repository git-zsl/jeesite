/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Email;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.HomeLoginService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysOfficeInformationService;
import com.thinkgem.jeesite.modules.sys.utils.EmailUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.LoginUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.*;

/**
 * 登录Controller
 *
 * @author zsl
 */
@Controller
public class HomeLoginController extends BaseController {

    @Autowired
    private HomeLoginService homeLoginService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheManager cacheManager;

    private static final String TRUE = "true";
    private Class clazz = HomeLoginController.class;
    /**
     * token验证失败
     */
    @RequestMapping(value = "/loginFail")
    @ResponseBody
    public ReturnEntity<String> login(HttpServletRequest request, HttpServletResponse response, Model model) {
        LogUtils.getLogInfo(clazz).info("token无效，请重新登录");
        return ReturnEntity.fail("token无效，请登录");
    }

    /**
     * 验证邮箱后用户注册
     */
    @RequestMapping(value = "/homeLogin", method = RequestMethod.GET)
    public String loginFail(@RequestParam Map<String, String> map, boolean isCompany, HttpServletRequest request, User user) {
        String loginName = map.get("loginName");
        try{
            Cache<String, String> cache = cacheManager.getCache(loginName);
            Set<String> keys = cache.keys();
            if(StringUtils.isBlank(loginName) || keys.size() == 0){
                LogUtils.getLogInfo(clazz).info("邮箱验证失败,loginName为空");
                return "redirect:http://www.baidu.com";
            }
            for (Iterator<String> it = keys.iterator(); it.hasNext();){
                String key = it.next();
                map.put(key,cache.get(key));
            }
            //开始注册
            homeLoginService.signIn(isCompany, map);
            //清除缓存map
            CacheUtils.removeAll(loginName);
        }catch (Exception e){
            e.printStackTrace();
            //清除缓存map
            CacheUtils.removeAll(loginName);
            LogUtils.getLogInfo(clazz).info("邮注册失败",e);
            return "redirect:http://www.baidu.com";
        }
        return "redirect:http://139.159.200.137:8081/";
    }
        /**
         * 邮箱认证
         */
        @RequestMapping(value = "/email", method = RequestMethod.POST)
        @ResponseBody
        public ReturnEntity<String> sentValidateEmail(HttpServletRequest request,@RequestParam MultipartFile image,@RequestParam MultipartFile file){
            try{
                Map<String,String[]> parameterMap = request.getParameterMap();
                Map<String,String> map = new HashMap<>();
                for (Map.Entry<String,String[]> entry : parameterMap.entrySet()) {
                    map.put(entry.getKey(), URLDecoder.decode(entry.getValue()[0],"UTF-8"));
                }
                String loginName = map.get("loginName");
                String originalFilename = image.getOriginalFilename();
                String originalFilename1 = file.getOriginalFilename();
                String configPath = Global.getConfig("userfiles.basedir").substring(0,1) + Global.getConfig("userfiles.basedir").substring(1);
                File file1 = new File(configPath + "/" + loginName +"/" + originalFilename);
                File file2 = new File(configPath + "/" + loginName +"/" + originalFilename1);
                if(!file1.getParentFile().exists()){
                    file1.getParentFile().mkdirs();
                }
                image.transferTo(file1);
                file.transferTo(file2);
                map.put("image",file1.getPath());
                map.put("file",file2.getPath());
                //个人用户字段
                String email = map.get("email");
                email = URLDecoder.decode(email);
                String isCompany = map.get("isCompany");
                //用户名验证
                if(StringUtils.isBlank(loginName)){
                    LogUtils.getLogInfo(clazz).info("用户名不能为空");
                    return ReturnEntity.fail("用户名不能为空");
                }
                User byLoginName = UserUtils.getByLoginName(loginName);
                if(!Objects.isNull(byLoginName)){
                    return ReturnEntity.fail("当前用户名已被占用");
                }
                //企业用户字段

                //清除缓存map
                    CacheUtils.removeAll(loginName);
                if(TRUE.equals(isCompany)){
                    map.put("isCompany","true");
                    String companyId = UserUtils.getCompanyId(Global.OFFICE_TYPE_2);
                    map.put("companyId",companyId);
                    //需要增加部门（部门字段未添加）

                }else{
                    String officeId = UserUtils.getOfficeId(Global.OFFICE_TYPE_1);
                    String companyId = UserUtils.getCompanyId(Global.OFFICE_TYPE_1);
                    map.put("officeId",officeId);
                    map.put("companyId",companyId);
                    map.put("isCompany","false");
                    map.put("name",loginName);
                }
                if (StringUtils.isBlank(map.get("loginName")) && StringUtils.isBlank(email)) {
                    return ReturnEntity.fail("用户名或者邮箱不能为空");
                }
                    String content = "<a href=http://"+ Global.getConfig("serverAddress")+"/homeLogin?loginName="+loginName+"&isCompany="+isCompany+">请点击完成此处激活帐号完成注册</a><br/>";
                    EmailUtils.sendHtmlMail(new Email(email,"注册验证",content));
                    CacheUtils.putMapAll(loginName,map);
            }catch (Exception e){
                LogUtils.getLogInfo(clazz).info("邮箱认证出错",e);
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
    public ReturnEntity<String> ValidateLoginName(@RequestParam Map<String,String> map){
        try{
            String loginName = map.get("loginName");
            if(StringUtils.isBlank(loginName)){
                LogUtils.getLogInfo(clazz).info("用户名不能为空");
               return ReturnEntity.fail("用户名不能为空");
            }
            User byLoginName = UserUtils.getByLoginName(loginName);
            if(Objects.isNull(byLoginName)){
                return ReturnEntity.success("当前用户名可以使用");
            }
        }catch (Exception e){
            LogUtils.getLogInfo(clazz).info("查询出错",e);
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
    public ReturnEntity<String> loginSuccess(@RequestParam Map<String,String> map){
        try{
            String token = "";
            String loginName = map.get("loginName");
            String password = map.get("password");
            if(StringUtils.isBlank(loginName)){
                return ReturnEntity.fail("用户名不能为空");
            }
            if(StringUtils.isBlank(password)){
                return ReturnEntity.fail("密码不能为空");
            }
            User byLoginName = UserUtils.getByLoginName(loginName);
            if(Objects.isNull(byLoginName)){
                return ReturnEntity.fail("用户名错误，请重新输入");
            }
            //验证密码
            if(LoginUtils.validatePassword(password,byLoginName.getPassword())){
                //生成token
               /* String format = MessageFormat.format("{}{}", loginName, Global.ZSL);*/
                String format = loginName + Global.ZSL;
                token = LoginUtils.entryptPassword(format);
                byLoginName.setToken(token);
                return ReturnEntity.success(byLoginName,"登录成功");
            }
        }catch (Exception e){
            LogUtils.getLogInfo(clazz).info("程序出错",e);
            return ReturnEntity.fail("程序出错");
        }
        return ReturnEntity.success("登录成功");
    }

    /**
     * 省市区接口
     */
    @RequestMapping("/getArea")
    @ResponseBody
    public ReturnEntity<Area> getAreaData(){
        List<Area> areas = Lists.newArrayList();
        try{
            List<Area> list = areaService.findTopArea();
            for (Area area : list) {
                if(Global.YES.equals(area.getParent().getId())){
                    areas.add(area);
                    areaService.findChildArea(area);
                }
            }
        }catch (Exception e){
            LogUtils.getLogInfo(clazz).info("获取省市区失败",e);
            e.printStackTrace();
            return ReturnEntity.fail("获取省市区失败");
        }
        return ReturnEntity.success(areas,"获取省市区成功");
    }

    /**
     * 上传测试
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public ReturnEntity upload(@RequestParam MultipartFile file)throws Exception{
        String originalFilename = file.getOriginalFilename();
        File file1 = new File("F://11/"+originalFilename);
        file.transferTo(file1);
        return ReturnEntity.success("上传成功");
    }
}
