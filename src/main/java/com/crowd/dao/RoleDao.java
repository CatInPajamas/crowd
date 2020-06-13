package com.crowd.dao;

import com.crowd.domain.Admin;
import com.crowd.domain.Permission;
import com.crowd.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleDao {
    @Select("select * from t_role where id in (select role_id from t_admin_role where admin_id=#{adminId})")
    List<Role> getAssignedRoleById(Integer adminId);

    @Select("select * from t_role where id not in (select role_id from t_admin_role where admin_id=#{adminId})")
    List<Role> getUnAssignedRoleById(Integer adminId);

    @Delete("delete from t_role where id=#{id}")
    void deleteRole(Integer id);

    @Select("select * from t_permission")
    List<Permission> getAllPermission();

    @Insert("insert into t_role_permission(roleid,permissionid) values (#{roleid},#{permissionid})")
    void asignPermission(Integer roleid,Integer permissionid);

    /**
     * 根据关键字模糊搜索
     * @param keyword
     * @return
     */
    @Select("select * from t_role where name like '%${keyword}%'")
    List<Role> findByKeyword(String keyword);

    @Insert("INSERT ignore INTO t_role (name) VALUES (#{rolename})")
    void addRole(String rolename);
}
