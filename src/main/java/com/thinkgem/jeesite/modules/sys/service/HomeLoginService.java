/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LoginUtils;
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

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void signIn(boolean isCompany,Map<String,String> map) {
        List<Office> listByName = null;
        User user = new User();
        user.setLoginName(map.get("loginName"));
        user.setName(map.get("name"));
        user.setPassword(LoginUtils.entryptPassword(map.get("password")));
        Office office= officeService.get(map.get("officeId"));
        Office company= officeService.get(map.get("companyId"));
        user.setOffice(office);
        user.setCompany(company);
        systemService.createUser(user);
    }

}
