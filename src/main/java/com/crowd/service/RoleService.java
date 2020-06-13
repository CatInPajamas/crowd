package com.crowd.service;

import com.crowd.domain.Admin;
import com.crowd.domain.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    List<Role> getAssignedRoleById(Integer adminId);

    List<Role> getUnAssignedRoleById(Integer adminId);

    void addRole(String rolename);

    void deleteRole(Integer id);

    void saveAdminRole(Integer adminId, List<Integer> roleIdList);

    /**
     * 以分页形式获取admin
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Role> getRolePage(String keyword, Integer pageNum, Integer pageSize);
}
