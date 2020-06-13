package com.crowd.service.impl;

import com.crowd.dao.AdminDao;
import com.crowd.dao.RoleDao;
import com.crowd.domain.Role;
import com.crowd.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminDao adminDao;

    @Override
    public List<Role> getAssignedRoleById(Integer adminId) {
        return roleDao.getAssignedRoleById(adminId);
    }

    @Override
    public List<Role> getUnAssignedRoleById(Integer adminId) {
        return roleDao.getUnAssignedRoleById(adminId);
    }

    @Override
    public void addRole(String rolename) {
        roleDao.addRole(rolename);
    }

    @Override
    public void deleteRole(Integer id) {
        roleDao.deleteRole(id);
    }

    @Override
    public void saveAdminRole(Integer adminId, List<Integer> roleIdList) {
        // 1.根据adminId 删除旧的关联关系数据
        adminDao.deleteOldRoles(adminId);
        // 2.根据roleIdList 和adminId 保存新的关联关系
        if(roleIdList != null && roleIdList.size() > 0) {
            adminDao.addNewRoles(adminId, roleIdList);
        }
    }

    @Override
    public PageInfo<Role> getRolePage(String keyword, Integer pageNum, Integer pageSize) {
        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 2.查询Admin 数据
        List<Role> roleList =roleDao.findByKeyword(keyword);
        // 3.为了方便页面使用将roleList 封装为PageInfo
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

}
