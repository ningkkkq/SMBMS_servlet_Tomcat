package com.nkq.service.user;

import java.sql.Connection;
import java.util.List;

import com.nkq.dao.Basedao;
import com.nkq.dao.user.UserDao;
import com.nkq.dao.user.UserDaoImp;
import com.nkq.pojo.User;

public class UserServiceImpl implements UserService {
    // 业务层调用dao层，引入Dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImp();
    }

    @Override
    public User Login(String userCode, String password) {
        // TODO Auto-generated method stub
        Connection con = null;
        User user = null;
        try {
            con = Basedao.getConnection();
            // 业务层调用dao层数据库操作，完成登录验证
            user = userDao.getLoginUser(con, userCode);
            System.out.print("UserDaoimp====");
            System.out.println(user != null);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            Basedao.closeResource(con, null, null);
        }
        // 密码验证
        if (user != null) {
            if (!user.getUserPassword().equals(password)) {
                System.out.println("验证失败====");
                user = null;
            }
        }
        return user;
    }

    @Override
    public boolean UpdateServicePwd(int id, String password) {
        Connection con = null;
        boolean flag = false;
        try {
            con = Basedao.getConnection();
            if (userDao.UpdatePwd(con, id, password) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            Basedao.closeResource(con, null, null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String Username, int UserRole) {
        Connection con = null;
        int count = 0;
        try {
            con = Basedao.getConnection();
            count = userDao.getUserCount(con, Username, UserRole);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            Basedao.closeResource(con, null, null);
        }
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        // TODO Auto-generated method stub
        Connection con = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            con = Basedao.getConnection();
            userList = userDao.getUserList(con, queryUserName, queryUserRole, currentPageNo, pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            Basedao.closeResource(con, null, null);
        }
        return userList;
    }


}