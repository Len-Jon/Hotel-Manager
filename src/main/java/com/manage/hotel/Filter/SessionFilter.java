package com.manage.hotel.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 韦庆斌
 * @since 2020/4/20 22:07
 */
@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
public class SessionFilter implements Filter {
    String[] urls = {"/login", "/doLogin"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String URI = request.getRequestURI();
        if (urls[0].equals(URI) || urls[1].equals(URI))
            filterChain.doFilter(servletRequest, servletResponse);//无需过滤
        else {
            // session中包含指定字段,则是登录状态
            if (session != null && session.getAttribute("name") != null && session.getAttribute("id") != null)
                filterChain.doFilter(request, response);
            else
                response.sendRedirect(request.getContextPath() + "/login");//重定向
        }
    }

    @Override
    public void destroy() {

    }
}
