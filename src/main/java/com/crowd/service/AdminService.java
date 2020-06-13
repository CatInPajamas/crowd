package com.crowd.service;

import com.crowd.domain.Admin;
import com.crowd.domain.Project;
import com.crowd.domain.ProjectType;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    /**
     * 根据账号密码查询管理员
     * @param loginacct
     * @param adminpswd
     * @return
     */
    Admin getAdminByLoginAcct(String loginacct,String adminpswd);

    /**
     * 根据关键字模糊搜索
     * @param keyword
     * @return
     */
    List<Admin> findByKeyword(String keyword);

    /**
     * 根据id查找admin
     * @param adminId
     * @return
     */
    Admin getAdminById(Integer adminId);

    /**
     * 保存admin
     * @param loginacct
     * @param userPswd
     * @param adminname
     * @param email
     */
    void saveAdmin(String loginacct, String userPswd, String adminname, String email);

    /**
     * 删除admin
     * @param adminId
     */
    void remove(Integer adminId);


    /**
     * 更新admin
     * @param admin
     */
    void update(Admin admin);

    /**
     * 以分页形式获取admin
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize);

    List<ProjectType> getAllType();

    void addType(String typename);

    void deleteType(Integer id);

    void updateProjectType(String typename,Integer id);

    List<Project> getAllProjects();

    void deleteProject(Integer id);

    void addProject(String name,String remark,Double money,int day,int status,String path1,String path2);

    List<Project> getProjectByKeyword(String keyword);

    Project getProjectByid(Integer id);
}
