package org.sang.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @ClassName MyInterceptor
 * @Description TODO
 * @Author haocanwen
 * @Date 2020/4/30 10:51
 * @Version 1.0
 */
public class MyInterceptor implements HandlerInterceptor {
    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("preHandle被调用");
        Map map = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        System.out.println(map.get("username"));
        System.out.println(httpServletRequest.getParameter("username"));
        if (map.get("username").equals("sang")) {
            return true;    //如果false，停止流程，api被拦截
        } else {
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("please login again!");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle被调用");
    }
}