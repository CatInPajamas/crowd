package com.crowd.dao;

import com.crowd.domain.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminDao {
    /**
     * 根据账号密码查询管理员
     * @param loginacct
     * @param adminpswd
     * @return
     */
    @Select("select * from t_admin where loginacct=#{loginacct} and adminpswd=#{adminpswd}")
    List<Admin> getAdminByLoginAcct(String loginacct,String adminpswd);

    /**
     * 根据关键字模糊搜索
     * @param keyword
     * @return
     */
    @Select("select * from t_admin where loginacct like '%${keyword}%' or adminname like '%${keyword}%' or email like '%${keyword}%'")
    List<Admin> findByKeyword(String keyword);

    /**
     * 根据id查找admin
     * @param adminId
     * @return
     */
    @Select("select * from t_admin where id=#{adminId}")
    Admin getAdminById(Integer adminId);

    /**
     * 保存admin
     * @param admin
     */
    @Insert("INSERT INTO t_admin(loginacct, adminpswd, adminname, email, createtime) VALUES (#{loginacct}, #{adminpswd}, #{adminname}, #{email}, #{createtime})")
    void saveAdmin(Admin admin);

    /**
     * 删除admin
     * @param adminId
     */
    @Delete("DELETE FROM t_admin WHERE id =#{adminId}")
    void remove(Integer adminId);


    /**
     * 更新admin
     * @param admin
     */
    @Update("UPDATE t_admin SET loginacct=#{loginacct}, adminpswd=#{adminpswd}, adminname=#{adminname}, email=#{email} WHERE id =#{id}")
    void update(Admin admin);

    /**
     * 根据adminId删除关联的角色
     * @param adminId
     */
    @Delete("delete from t_admin_role where admin_id=#{adminId}")
    void deleteOldRoles(Integer adminId);

    /**
     * 添加新的角色
     * @param roleIdList
     */
    @Insert("<script>" +
            "insert into t_admin_role(admin_id,role_id) values" +
            "<foreach collection='roleIdList' item='roleId' separator=','>" +
            "(#{adminId},#{roleId})" +
            "</foreach>" +
            "</script>")
    void addNewRoles(Integer adminId,List<Integer> roleIdList);

    @Select("select * from t_type")
    List<ProjectType> getAllType();

    @Insert("INSERT INTO `t_type`(`name`) VALUES (#{typename})")
    void addType(String typename);

    @Delete("delete from t_type where id=#{id}")
    void deleteType(Integer id);

    @Update("update t_type set name=#{typename} where id=#{id}")
    void updateProjectType(String typename,Integer id);

    @Select("select * from t_project")
    List<Project> getAllProjects();

    @Delete("delete from t_project where id=#{id}")
    void deleteProject(Integer id);

    @Insert("insert into t_project(name,remark,money,day,status,supportmoney,supporter,head_picture_path,body_picture_path,deploydate) "+
            "values (#{name},#{remark},#{money},#{day},#{status},#{supportmoney},#{supporter},#{head_picture_path},#{body_picture_path},#{deploydate})")
    void addProject(Project project);

    @Select("select * from t_project where name like '%${keyword}%' ")
    List<Project> getProjectByKeyword(String keyword);

    @Select("select * from t_project where id=#{id}")
    Project getProjectByid(Integer id);

    @Select("SELECT a.name " +
            "FROM t_type a,t_project_type b " +
            "WHERE a.id=b.typeid AND b.projectid=#{id}")
    String getProjectType(Integer id);

    @Select("SELECT * "+
            "from t_project_item "+
            "WHERE projectid=#{id} "+
            "ORDER BY t_project_item.money desc")
    List<PriceItem> getPriceItemById(Integer id);

    @Insert("insert into t_project_item(projectid,money,delivery,introduce) values(#{projectid},#{money},#{delivery},#{introduce}) ")
    void addProjectItem(Integer projectid,double money,Integer delivery,String introduce);

    @Delete("delete from t_project_item where id=#{id}")
    void deleteProjectItem(Integer id);

    @Update("update t_project set name=#{name} where id=#{id}")
    void updateProjectName(String name,Integer id);

    @Update("update t_project set money=#{money} where id=#{id}")
    void updateProjectMoney(double money,Integer id);

    @Update("update t_project set status=#{status} where id=#{id}")
    void updateProjectStatus(Integer status,Integer id);


    @Update("update t_project set day=#{day} where id=#{id}")
    void updateProjectDay(Integer day,Integer id);

    @Delete("delete from t_project_type where projectid=#{id}")
    void deleteProjectType(Integer id);

    @Insert("insert into t_project_type(projectid,typeid) values (#{projectid},#{typeid})")
    void addProjectType(Integer projectid,Integer typeid);

}
