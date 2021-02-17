package com.nkq.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.util.StringUtils;
import com.nkq.dao.Basedao;
import com.nkq.pojo.User;

public class UserDaoImp implements UserDao {

    @Override
    public User getLoginUser(Connection con, String userCode) {
        PreparedStatement pre = null;
        ResultSet result = null;
        User user = null;
        // TODO Auto-generated method stub
        if (con != null) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = { userCode };
            try {
                result = Basedao.excute(con, sql, params, pre, result);
                if (result.next()) {
                    user = new User();
                    user.setId(result.getInt("id"));
                    user.setUserCode(result.getString("userCode"));
                    user.setUserName(result.getString("userName"));
                    user.setUserPassword(result.getString("userPassword"));
                    user.setGender(result.getInt("gender"));
                    user.setBirthday(result.getDate("birthday"));
                    user.setPhone(result.getString("phone"));
                    user.setAddress(result.getString("address"));
                    user.setUserRole(result.getInt("userRole"));
                    user.setCreatedBy(result.getInt("createdBy"));
                    user.setCreationDate(result.getTimestamp("creationDate"));
                    user.setModifyBy(result.getInt("modifyBy"));
                    user.setModifyDate(result.getTimestamp("modifyDate"));
                }
                Basedao.closeResource(null, pre, result);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.print("UserDaoimp====异常");
                e.printStackTrace();
            }
        }
        System.out.print("UserDaoimp====");
        System.out.println(user != null);
        return user;
    }

    @Override
    public int UpdatePwd(Connection con, int id, String password) throws SQLException {
        PreparedStatement pre = null;
        int excute = 0;
        if (con != null) {
            String sql = "update smbms_user set userPassword= ? where id= ?";
            Object[] params = { password, id };
            excute = Basedao.excute(con, sql, params, pre);
            Basedao.closeResource(null, pre, null);
        }
        return excute;
    }

    @Override
    public int getUserCount(Connection con, String userName, int userRole) {
        PreparedStatement pre = null;
        ResultSet rs = null;
        int count = 0;
        if (con != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            try {
                rs = Basedao.excute(con, sql.toString(), params, pre, rs);
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Basedao.closeResource(null, pre, rs);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)
            throws Exception {
        PreparedStatement pre = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            // 分页
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = Basedao.excute(connection, sql.toString(), params, pre, rs);
            // 将结果集添加到User类中，然后添加到列表
            while (rs.next()) {
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            Basedao.closeResource(null, pre, rs);
        }
        // 返回当前页面数据
        return userList;
    }
}
