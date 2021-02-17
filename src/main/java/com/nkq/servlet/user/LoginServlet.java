package com.nkq.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nkq.pojo.User;
import com.nkq.service.user.UserServiceImpl;
import com.nkq.util.Constants;

public class LoginServlet extends HttpServlet {
    // 控制层，调用业务层代码，实现登录业务逻辑
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        UserServiceImpl loguserin = new UserServiceImpl();
        User user = loguserin.Login(userCode, userPassword);
        if (user!=null) {
            // 设置session
            req.getSession().setAttribute(Constants.USER_SESSION, user);
            // 跳转登陆页面
            resp.sendRedirect("jsp/frame.jsp");
        }else {
            // 无法登陆
            req.setAttribute("error", "wrong password!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
