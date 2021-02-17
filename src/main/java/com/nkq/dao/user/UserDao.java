package com.nkq.dao.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nkq.pojo.User;

public interface UserDao {
    // 用户登陆
    public User getLoginUser(Connection con, String userCode);
    // 修改密码
    public int UpdatePwd(Connection con, int id, String password) throws SQLException;
    // 根据用户名或角色获取用户个数
    public int getUserCount(Connection con, String userName, int userRole);
    // 获取用户列表
    public List<User> getUserList(Connection con, String userName, int userRole, int currentPageNo, int pageSize)
            throws Exception;


}
