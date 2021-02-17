package com.nkq.dao.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nkq.dao.Basedao;
import com.nkq.pojo.Role;

public class RoleDaoimp implements RoleDao {
    // 获取角色列表
    @Override
    public List<Role> getRoleList(Connection con) throws SQLException {
        PreparedStatement pre = null;
        ResultSet rs = null;
        ArrayList<Role> rslist = new ArrayList<Role>();
        if (con != null) {
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = Basedao.excute(con, sql, params, pre, rs);
            while (rs.next()) {
                Role _role = new Role();
                _role.setId(rs.getInt("id"));
                _role.setRoleCode(rs.getString("roleCode"));
                _role.setRoleName(rs.getString("roleName"));
                rslist.add(_role);
            }
            Basedao.closeResource(null, pre, rs);
        }
        return rslist;
    }

}
