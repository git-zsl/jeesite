/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Email;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.HomeLoginService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
        public ReturnEntity<String> sentValidateEmail(@RequestParam Map<String, String> map){
            try{
                String email = map.get("email");
                String loginName = map.get("loginName");
                String isCompany = map.get("isCompany");
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
                User byLoginName = UserUtils.getByLoginName(loginName);
                if(Objects.isNull(byLoginName)){
                    String content = "<a href=http://"+ Global.getConfig("serverAddress")+"/homeLogin?loginName="+loginName+"&isCompany="+isCompany+">请点击完成此处激活帐号完成注册</a><br/>";
                    EmailUtils.sendHtmlMail(new Email(email,"注册验证",content));
                    CacheUtils.putMapAll(loginName,map);
                    return ReturnEntity.success("邮件发送成功");
                }
            }catch (Exception e){
                LogUtils.getLogInfo(clazz).info("邮箱认证出错",e);
                e.printStackTrace();
                return ReturnEntity.fail("系统出错，请联系管理员");
            }
            return ReturnEntity.fail("当前用户已存在");
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
                token = LoginUtils.entryptPassword(MessageFormat.format("{}{}",loginName,Global.ZSL));
                byLoginName.setToken(token);
                return ReturnEntity.success(byLoginName,"登录成功");
            }
        }catch (Exception e){
            LogUtils.getLogInfo(clazz).info("程序出错",e);
            return ReturnEntity.fail("程序出错");
        }
        return ReturnEntity.success("登录成功");
    }
}
