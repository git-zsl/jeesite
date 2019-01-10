/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.SysOfficeInformation;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.LoginUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统管理，安全相关实体的管理类,包括用户
 *
 * @author zsl
 */
@Service
@Transactional(readOnly = true)
public class HomeLoginService extends BaseService {

    private static final String COMPANY = "企业用户";
    private static final String PERSONAL  = "个人用户";

    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;

    @Autowired
    private SysOfficeInformationService sysOfficeInformationService;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void signIn(boolean isCompany,Map<String,String> map) {
        List<Office> listByName = null;
        User user = new User();
        user.setLoginName(map.get("loginName"));
        user.setName(map.get("name"));
        String password = LoginUtils.entryptPassword(map.get("password"));
        LogUtils.getLogInfo(HomeLoginService.class).info("plainPassword:"+map.get("password"));
        LogUtils.getLogInfo(HomeLoginService.class).info("password为:"+password);
        user.setPassword(password);
        Office office= officeService.get(map.get("officeId"));
        Office company= officeService.get(map.get("companyId"));
        user.setOffice(office);
        user.setCompany(company);
        User user1 = UserUtils.get("1");
        user.setCreateBy(user1);
        user.setUpdateBy(user1);
        if(isCompany){
            user.setIsCompany("true");
        }else{
            user.setIsCompany("false");
        }
        if(isCompany){
            User user2 = sysOfficeInformationService.setUserInformation(map, user);
            SysOfficeInformation sysOfficeInformation = sysOfficeInformationService.setSysOfficeInformation(map, new SysOfficeInformation(),user2);
            systemService.createUser(user2);
            sysOfficeInformation.setUser(user2);
            sysOfficeInformationService.save(sysOfficeInformation);
        }else{
            systemService.createUser(user);
        }
    }

    /**
     * 个人用户封装数据
     */
    public Map<String, String> setpersonalParmas(Map<String, String> map){

        return map;
    }

    /**
     * 企业用户封装数据
     */
    public Map<String, String> setCompanyParmas(Map<String, String> map){

        return map;
    }
}
