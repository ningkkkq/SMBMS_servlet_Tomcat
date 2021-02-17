package com.nkq.dao.role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nkq.pojo.Role;

public interface RoleDao {
    // 获取角色列表
    public List<Role> getRoleList(Connection con) throws SQLException;
}
