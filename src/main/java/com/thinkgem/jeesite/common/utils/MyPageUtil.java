package com.thinkgem.jeesite.common.utils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分页工具
 */
public class MyPageUtil {

    /**
     * 模拟分页
     * @param t
     * @param request
     * @param response
     * @param <T>
     * @return
     */
    public static <T> List<T> getPageList(List<T> t, HttpServletRequest request, HttpServletResponse response){
        Page<T> page = new Page<T>(request, response);
        List<T> lists = Lists.newArrayList();
        for (int i = 0; i < t.size(); i++) {
            if( i >= ((page.getPageNo()-1)*page.getPageSize()) && i <= (page.getPageNo()*page.getPageSize()-1)){
                lists.add(t.get(i));
            }
        }
        return lists;
    }
}
