package com.project;

import com.project.beans.LoginBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        LoginBean session = (LoginBean) req.getSession().getAttribute("loginBeanBean");
        String url = req.getRequestURI();

        /*
        A. IF REQUEST IS FOR TABLE, ADD OR LOGOUT AND THERE'S NO SESSION, REDIRECT THE REQUEST TO INDEX.XHTML
        B. IF REQUEST IS FOR REGISTER OR INDEX AND THERE'S A SESSION, REDIRECT THE REQUEST TO TABLE.XHTML
        C. IF REQUEST IS FOR LOGOUT AND THERE'S A SESSION, REMOVE THE SESSION, THEN REDIRECT TO INDEX.XHTML
        * */
        if(session == null || !session.isLogged) {
            if(url.indexOf("table.xhtml") >= 0 || url.indexOf("logout.xhtml") >= 0 || url.indexOf("add.xhtml") >= 0) {
                resp.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            if(url.indexOf("register-xhtml") >= 0 || url.indexOf("index.xhtml") >= 0) {
                resp.sendRedirect(req.getServletContext().getContextPath() + "/table.xhtml");
            } else if(url.indexOf("logout.xhtml") >= 0) {
                req.getSession().removeAttribute("loginBeanBean");
                resp.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
