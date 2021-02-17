package com.nkq.service.role;

import java.sql.Connection;
import java.util.List;

import com.nkq.dao.Basedao;
import com.nkq.dao.role.RoleDao;
import com.nkq.dao.role.RoleDaoimp;
import com.nkq.pojo.Role;

public class RoleServiceImpl implements RoleService {
    // 业务层调用dao层，引入Dao层
    private RoleDao Roledao;
    public RoleServiceImpl(){
        Roledao = new RoleDaoimp();
    }

    @Override
    public List<Role> getRoleList(){
        Connection con = null;
        List<Role> roleList = null;
        try {
            con = Basedao.getConnection();
            roleList = Roledao.getRoleList(con);
        } catch (Exception e) {
            //TODO: handle exception
        } finally {
            Basedao.closeResource(con, null, null);
        }
        return roleList;
    }

}
