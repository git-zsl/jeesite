/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Email;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.HomeLoginService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.EmailUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.LoginUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 登录Controller
 *
 * @author zsl
 */
@Controller
public class HomeLoginController extends BaseController {

    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private HomeLoginService homeLoginService;
    @Autowired
    private CacheManager cacheManager;
    private static final String TRUE = "true";

    /**
     * 管理登录
     */
    @RequestMapping(value = "/h/homeLogin", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println(homePath);
        System.out.println(Global.getConfig("homePath"));
        System.out.println("${adminPath}");
        System.out.println("${homePath}");
        Principal principal = UserUtils.getPrincipal();

        if (logger.isDebugEnabled()) {
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            CookieUtils.setCookie(response, "LOGINED", "false");
        }

        // 如果已经登录，则跳转到管理首页
        if (principal != null && !principal.isMobileLogin()) {
            return "redirect:" + adminPath;
        }
        return "modules/sys/sysLogin";
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "/h/homeLogin", method = RequestMethod.GET)
    @ResponseBody
    public ReturnEntity<String> loginFail(@RequestParam Map<String, String> map, boolean isCompany, HttpServletRequest request, User user) {
        /*String email = map.get("email");
        if (StringUtils.isBlank(map.get("loginName")) && StringUtils.isBlank(email)) {
            return "用户名不能为空,或者邮箱不能为空";
        }
        // 如果已经登录，则跳转到管理首页
        if (!StringUtils.isBlank(map.get("token"))) {
            //验证token
            LoginUtils.validatePassword((String) request.getSession().getAttribute(map.get("loginName")), map.get("token"));
            return "已经登录";
        }*/
        try{
            String loginName = map.get("loginName");
            Cache<String, String> cache = cacheManager.getCache(loginName);
            Set<String> keys = cache.keys();
            if(StringUtils.isBlank(loginName) || keys.size() == 0){
                LogUtils.getLogInfo(HomeLoginController.class).info("邮箱验证失败,loginName为空");
                return ReturnEntity.fail("邮箱验证失败,loginName为空");
            }
            for (Iterator<String> it = keys.iterator(); it.hasNext();){
                String key = it.next();
                map.put(key,cache.get(key));
            }
            //开始注册
            homeLoginService.signIn(isCompany, map);
            //清除缓存map
            CacheUtils.removeAll(loginName);
            //生成token
            //*********后续补充**********
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.getLogInfo(HomeLoginController.class).info("邮注册失败",e);
            return ReturnEntity.fail("注册失败");
        }
        return ReturnEntity.success("注册成功");
    }
        /**
         * 邮箱认证
         */
        @RequestMapping(value = "/h/email", method = RequestMethod.POST)
        @ResponseBody
        public ReturnEntity<String> sentValidateEmail(@RequestParam Map<String, String> map){
            try{
                String email = map.get("email");
                String loginName = map.get("loginName");
                String isCompany = map.get("isCompany");
                if(TRUE.equals(isCompany)){
                    map.put("isCompany","true");
                }else{
                    map.put("isCompany","false");
                }
                if (StringUtils.isBlank(map.get("loginName")) && StringUtils.isBlank(email)) {
                    return ReturnEntity.fail("用户名或者邮箱不能为空");
                }
                User byLoginName = UserUtils.getByLoginName(loginName);
                if(Objects.isNull(byLoginName)){
                    String content = "<a href=http://localhost:8080/zsl/h/homeLogin?loginName="+loginName+"&isCompany="+isCompany+">请点击完成此处激活帐号完成注册</a><br/>";
                    EmailUtils.sendHtmlMail(new Email(email,"注册验证",content));
                    CacheUtils.putMapAll(loginName,map);
                    return ReturnEntity.success("邮件发送成功");
                }
            }catch (Exception e){
                LogUtils.getLogInfo(HomeLoginController.class).info("邮箱认证出错",e);
                e.printStackTrace();
                return ReturnEntity.fail("系统出错，请联系管理员");
            }
            return ReturnEntity.fail("当前用户已存在");
        }

    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${homePath}")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        Principal principal = UserUtils.getPrincipal();

        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(principal.getLoginName(), false, true);

        if (logger.isDebugEnabled()) {
            logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            String logined = CookieUtils.getCookie(request, "LOGINED");
            if (StringUtils.isBlank(logined) || "false".equals(logined)) {
                CookieUtils.setCookie(response, "LOGINED", "true");
            } else if (StringUtils.equals(logined, "true")) {
                UserUtils.getSubject().logout();
                return "redirect:" + adminPath + "/login";
            }
        }

        // 如果是手机登录，则返回JSON字符串
        if (principal.isMobileLogin()) {
            if (request.getParameter("login") != null) {
                return renderString(response, principal);
            }
            if (request.getParameter("index") != null) {
                return "modules/sys/sysIndex";
            }
            return "redirect:" + adminPath + "/login";
        }

        return "modules/sys/sysIndex";
    }


    /**
     * 是否是验证码登录
     *
     * @param useruame 用户名
     * @param isFail   计数加1
     * @param clean    计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(useruame);
        }
        return loginFailNum >= 3;
    }

}
