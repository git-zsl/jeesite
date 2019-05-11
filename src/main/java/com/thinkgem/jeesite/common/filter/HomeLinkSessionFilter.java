package com.thinkgem.jeesite.common.filter;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HomeLinkSessionFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    @ResponseBody
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String userId = request.getParameter("myUserId");
        if(StringUtils.isNotBlank(userId)){
            if(userId.equals("tourists")){
                filterChain.doFilter(request, response);
                return;
            }
            String loginFlag = (String)CacheUtils.get("homeLoginSession_" + userId);
            if (StringUtils.isNotBlank(loginFlag)) {
                filterChain.doFilter(request, response);
                return;
            }else{
                res.setCharacterEncoding("utf-8");
                res.setContentType("application/json; charset=utf-8");
                PrintWriter writer = res.getWriter();
                Map<String, String> map = new HashMap<>();
                map.put("status", "3");
                map.put("result","请先登录");
                writer.write(map.toString());
                return;
            }
        }
        filterChain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
