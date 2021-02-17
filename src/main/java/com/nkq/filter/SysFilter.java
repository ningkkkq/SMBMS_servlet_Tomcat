package com.nkq.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nkq.pojo.User;
import com.nkq.util.Constants;

public class SysFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User)req.getSession().getAttribute(Constants.USER_SESSION);
        if (user==null) {
            resp.sendRedirect("/smbms/error.jsp");
        }else {
            chain.doFilter(request, response);
        }

    }
}
