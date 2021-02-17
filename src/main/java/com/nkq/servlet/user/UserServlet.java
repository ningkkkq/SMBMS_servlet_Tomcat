package com.nkq.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.nkq.pojo.Role;
import com.nkq.pojo.User;
import com.nkq.service.role.RoleServiceImpl;
import com.nkq.service.user.UserServiceImpl;
import com.nkq.util.Constants;
// import com.nkq.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")) {
            this.updatePwd(req, resp);
        }else if(method.equals("pwdmodify")){
            this.checkOldPwd(req, resp);
        }else if (method.equals("query")){
            this.getQueryUserList(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        Object id = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpwd = req.getParameter("newpassword");
        boolean res = false;
        if (id!=null && !StringUtils.isNullOrEmpty(newpwd)) {
            UserServiceImpl userService = new UserServiceImpl();
            res = userService.UpdateServicePwd(((User)id).getId(), newpwd);
            if (res) {
                // 密码修改成功后显示消息
                req.setAttribute("message", "update password success!");
                // 删除session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                // 密码修改失败后显示消息
                req.setAttribute("message", "unable to update password");
            }
        }else {
            req.setAttribute("message", "some problems happen");
        }
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    protected void checkOldPwd(HttpServletRequest req, HttpServletResponse resp){
        Object id = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpwd = req.getParameter("oldpassword");
        HashMap<String, String> resultMap = new HashMap<>();
        if(id==null){
            resultMap.put("result", "sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpwd)){
            resultMap.put("result", "error");
        }else{
            String curPwd = ((User)id).getUserPassword();
            if (curPwd.equals(oldpwd)) {
                resultMap.put("result", "true");
            }else{
                resultMap.put("result", "false");
            }
        }
        resp.setContentType("application/json");
        try {
            PrintWriter writer = resp.getWriter();
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void getQueryUserList(HttpServletRequest req, HttpServletResponse resp){
        String queryname = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;
        // 获取角色列表
        RoleServiceImpl roleServiceImpl = new RoleServiceImpl();
        List<Role> roleList = roleServiceImpl.getRoleList();
        // 设置查询参数
        int pageSize = 5;
        int currentPageNo = 1;
        if (queryname==null) {
            queryname = "";
        }
        if (temp!=null && !temp.equals("")) {
            queryUserRole = Integer.parseInt(temp);
        }
        if (pageIndex!=null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        // 获取用户总数
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        int todalCount = userServiceImpl.getUserCount(queryname, queryUserRole);

        // 通过工具类获取分页数量
        // PageSupport pageSupport = new PageSupport();
        // pageSupport.setPageSize(pageSize);
        // pageSupport.setCurrentPageNo(currentPageNo);
        // pageSupport.setTotalCount(todalCount);
        // int totalPageCount = pageSupport.getTotalPageCount();
        int totalPageCount = ((int)(todalCount/pageSize))+1;

        // 如果超出最后一页或第一页，设置为最后一页和第一页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }

        // 查询对应的用户列表
        List<User> userList = userServiceImpl.getUserList(queryname, queryUserRole, currentPageNo, pageSize);

        // 将数据返回前端
        // 角色列表
        req.setAttribute("roleList", roleList);
        // 用户列表
        req.setAttribute("userList", userList);
        // 总页数
        req.setAttribute("totalPageCount", totalPageCount);
        // 用户总数
        req.setAttribute("totalCount", todalCount);
        // 当前页数
        req.setAttribute("currentPageNo", currentPageNo);
        // 查询的名称
        req.setAttribute("queryUserName", queryname);
        // 查询的角色名
        req.setAttribute("queryUserRole", queryUserRole);

        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
