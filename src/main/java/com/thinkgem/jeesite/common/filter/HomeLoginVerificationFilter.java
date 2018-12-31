package com.thinkgem.jeesite.common.filter;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.crn.entity.UserCategoryNum;
import com.thinkgem.jeesite.modules.crn.service.UserCategoryNumService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LoginUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeLoginVerificationFilter implements Filter {
    private FilterConfig filterConfig;
  /*  private UserCategoryNumService userCategoryNumService;
    private static final String ADMIN_ID="1";*/

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      /*  ServletContext sc = filterConfig.getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
        if(cxt != null && cxt.getBean("userCategoryNumService") != null && userCategoryNumService == null)
            userCategoryNumService = (UserCategoryNumService) cxt.getBean("userCategoryNumService");*/
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String userId = request.getParameter("userId");
        String token = request.getParameter("token");
        User user = UserUtils.get(userId);
            if(!StringUtils.isBlank(token) && Objects.nonNull(user)){
                //验证token
                String plainPassword = MessageFormat.format("{}{}",user.getLoginName(),Global.ZSL);
                if(LoginUtils.validatePassword(plainPassword, token)){
                    filterChain.doFilter(request, response);
                    return;
                }
                req.getRequestDispatcher("/h/loginFail").forward(request,response);
                return;
            }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
