package com.thinkgem.jeesite.common.filter;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.crn.entity.UserCategoryNum;
import com.thinkgem.jeesite.modules.crn.service.UserCategoryNumService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SaveFilter implements Filter {
    private FilterConfig filterConfig;
    private UserCategoryNumService userCategoryNumService;
    private static final String ADMIN_ID="1";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
        if(cxt != null && cxt.getBean("userCategoryNumService") != null && userCategoryNumService == null)
            userCategoryNumService = (UserCategoryNumService) cxt.getBean("userCategoryNumService");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        String path = request.getRequestURI();
        String userId = request.getParameter("userId");
        System.out.println("自定义过滤器;"+"userId:"+userId+"path:"+path);
        Map parameterMap = request.getParameterMap();
        String[] categoryId = (String[])parameterMap.get("category.id");
        UserCategoryNum u = new UserCategoryNum();
        User user = UserUtils.get(userId);
        Category c = new Category(categoryId[0]);
        u.setUser(user);
        u.setCategory(c);
        String[] deptIds = Global.getConfig("deptId").split(",");
        for(int i = 0 ; i < deptIds.length ; i++){
            if(deptIds[i].equals(user.getOffice().getId()) || user.isAdmin()){
                filterChain.doFilter(request, response);
                return;
            }
        }
        List<UserCategoryNum> list = userCategoryNumService.findList(u);
        if(list.isEmpty()){
            req.getRequestDispatcher("/a/cms/article/noSave").forward(request,response);
            return;
        }
        UserCategoryNum userCategoryNum = list.get(0);
        Integer num = userCategoryNum.getCurrentNum();
        userCategoryNum.setCurrentNum(++num);
        if(userCategoryNum.getCurrentNum()>userCategoryNum.getCreateNum()){
            req.getRequestDispatcher("/a/cms/article/noSave").forward(request,response);
            return;
        }else{
            userCategoryNumService.save(userCategoryNum);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
