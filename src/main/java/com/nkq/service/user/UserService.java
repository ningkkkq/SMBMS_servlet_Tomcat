package com.nkq.service.user;

import java.util.List;

import com.nkq.pojo.User;

public interface UserService {
    // 用户登录
    public User Login(String userCode, String password);
    // 更新密码
    public boolean UpdateServicePwd(int id, String password);
    // 查询用户个数
    public int getUserCount(String Username, int UserRole);
    // 根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
}
