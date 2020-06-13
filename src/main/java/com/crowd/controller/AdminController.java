package com.crowd.controller;

import com.crowd.constant.CrowdConstant;
import com.crowd.dao.AdminDao;
import com.crowd.dao.RoleDao;
import com.crowd.domain.*;
import com.crowd.exception.LoginAcctAlreadyInUseException;
import com.crowd.exception.LoginFailedException;
import com.crowd.service.AdminService;
import com.crowd.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private RoleDao roleDao;


    @GetMapping("/admin/main")
    public String toMainPage(){
        return "main";
    }

    @GetMapping("/admin/login")
    public String toLoginPage(Model model){
        return "login";
    }

    @PostMapping("/admin/login")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("adminPswd") String userPswd,
                          HttpSession session, Map<String,Object> map) {
        // 调用Service 方法执行登录检查
        // 这个方法如果能够返回admin 对象说明登录成功，如果账号、密码不正确则会抛出异常
        try{
            Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
            // 将登录成功返回的admin 对象存入Session 域
            session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
            return "redirect:/admin/main";
        }catch(LoginFailedException e){
            map.put("msg",e.getMessage());
            return "login";
        }
    }

    @RequestMapping("/admin/logout")
    public String doLogout(HttpSession session) {
        // 强制Session失效
        session.invalidate();
        return "redirect:/admin/login";
    }

    @RequestMapping("admin/admin_list")
    public String getAdminList(@RequestParam(value="keyword", defaultValue="") String keyword,
                               @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
                               @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
                               ModelMap modelMap){

        // 查询得到分页数据
        PageInfo<Admin> pageInfo = adminService.getAdminPage(keyword, pageNum, pageSize);
        // 将分页数据存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "adminlist";
    }

    @GetMapping("/admin/admin_add")
    public String toAddPage(Model model){
        return "adminadd";
    }
    @PostMapping("/admin/admin_add")
    public String save(@RequestParam("loginacct") String loginacct,
                       @RequestParam("adminpswd") String userPswd,
                       @RequestParam("adminname") String adminname,
                       @RequestParam("email") String email,HttpSession session){
        try{
            adminService.saveAdmin(loginacct,userPswd,adminname,email);
            return "redirect:/admin/admin_list?pageNum="+10000;
        }catch (LoginAcctAlreadyInUseException e){
            session.setAttribute("erromsg",e.getMessage());
            return "adminadd";
        }

    }

    @GetMapping("/admin/admin_edit")
    public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
        // 1.根据id（主键）查询待更新的Admin 对象
        Admin admin = adminService.getAdminById(adminId);
        // 2.将Admin 对象存入模型
        modelMap.addAttribute("admin", admin);
        return "adminedit";
    }

    @RequestMapping("/admin/admin_delete")
    public String remove(@RequestParam("adminId") Integer adminId,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("keyWord") String keyword){
        adminService.remove(adminId);
        return "redirect:/admin/admin_list?pageNum="+pageNum+"&keyword="+keyword;
    }

    @GetMapping("/admin/assign_role")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
            ModelMap modelMap) {
        // 1.查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRoleById(adminId);
        // 2.查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRoleById(adminId);
        // 3.存入模型（本质上其实是：request.setAttribute("attrName",attrValue);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assignRole";
    }

    @PostMapping("/admin/assign_role")
    public String assignRole(@RequestParam("adminId") Integer adminId,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyWord") String keyword,
                             // 设置required=false 表示这个请求参数不是必须的
                             @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList
    ){
        // 我们允许用户在页面上取消所有已分配角色再提交表单，所以可以不提供roleIdList 请求参数
        roleService.saveAdminRole(adminId, roleIdList);
        return "redirect:/admin/admin_list?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/role_list")
    public String getRoleList(@RequestParam(value="keyword", defaultValue="") String keyword,
                               @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
                               @RequestParam(value="pageSize", defaultValue="10") Integer pageSize,
                               ModelMap modelMap){

        // 查询得到分页数据
        PageInfo<Role> pageInfo =roleService.getRolePage(keyword, pageNum, pageSize);
        // 将分页数据存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "rolelist";
    }

    @GetMapping("/admin/role_add")
    public String toAddRolePage(Model model){
        return "roleadd";
    }
    @PostMapping("/admin/role_add")
    public String save(@RequestParam("rolename") String rolename, HttpSession session){
        roleService.addRole(rolename);
        return "redirect:/admin/role_list";
    }

    @RequestMapping("/admin/role_delete")
    public String deleteRole(@RequestParam("roleid") Integer roleid){
        roleService.deleteRole(roleid);
        return "redirect:/admin/role_list";
    }

    @GetMapping("/admin/assign_permission")
    public String toAssignPermission(@RequestParam("roleid") Integer roleid,
                                     Model model){
        List<Permission> permissions=roleDao.getAllPermission();
        model.addAttribute("permissions",permissions);
        model.addAttribute("roleid",roleid);
        return "assignPermission";
    }

    @PostMapping("/admin/assign_permission")
    public String assignPermission(@RequestParam("roleid") Integer roleid,
                                   @RequestParam("permissionid") List<Integer> permissionid){
        for (int i = 0; i <permissionid.size() ; i++) {
            roleDao.asignPermission(roleid,permissionid.get(i));
        }
        return "redirect:/admin/role_list";
    }



    @GetMapping("/admin/project_type")
    public String projectType(Model model){
        List<ProjectType> typeList=adminDao.getAllType();
        model.addAttribute("typeList",typeList);
        return "project_type";
    }

    @RequestMapping("/admin/project_type_delete")
    public String deleteType(@RequestParam("typeid") Integer id){
        adminService.deleteType(id);
        return "redirect:/admin/project_type";
    }

    @RequestMapping("/admin/project_type_add")
    public String deleteType(@RequestParam("typename") String typename){
        adminService.addType(typename);
        return "redirect:/admin/project_type";
    }

    @RequestMapping("/admin/project_type_edit")
    public String updateType(@RequestParam("typename") String typename,
                             @RequestParam("typeid") Integer id){
        adminService.updateProjectType(typename,id);
        return "redirect:/admin/project_type";
    }

    @GetMapping("/admin/projects")
    public String getAllProjects(@RequestParam(value = "keyword",defaultValue ="") String keyword,
                                 Model model){
        List<Project> projectList=adminService.getProjectByKeyword(keyword);
        model.addAttribute("projectList",projectList);
        return "projects";
    }

    @RequestMapping("/admin/projects_delete")
    public String deleteProject(@RequestParam("projectid") Integer projectid){
        adminService.deleteProject(projectid);
        return "redirect:/admin/projects";
    }

    @RequestMapping("/admin/projects_add")
    public String addProject(@RequestParam("name") String name,
                             @RequestParam("remark") String remark,
                             @RequestParam("money") Double money,
                             @RequestParam("day") Integer day,
                             @RequestParam("path1") String path1,
                             @RequestParam("path2") String path2,
                             Model model){
        adminService.addProject(name,remark,money,day,1,path1,path2);
        return "redirect:/admin/projects";
    }

    @GetMapping("/admin/project")
    public String toProject(@RequestParam("projectid") Integer projectid,
                            Model model){
        String type=adminDao.getProjectType(projectid);
        Project project=adminService.getProjectByid(projectid);
        List<PriceItem> priceItems=adminDao.getPriceItemById(projectid);
        List<ProjectType> projectTypes=adminService.getAllType();
        model.addAttribute("projectTypes",projectTypes);
        model.addAttribute("priceItems",priceItems);
        model.addAttribute("type",type);
        model.addAttribute("project",project);
        return "project";
    }

    @RequestMapping("/admin/project_additem")
    public String addProjectItem(@RequestParam("projectid") Integer projectid,
                                 @RequestParam("money") double money,
                                 @RequestParam("delivery") Integer delivery,
                                 @RequestParam("introduce") String introduce,
                                 Model model){
        adminDao.addProjectItem(projectid,money,delivery,introduce);
        return "redirect:/admin/project?projectid="+projectid;
    }

    @RequestMapping("/admin/project_deleteitem")
    public String deleteProjectItem(@RequestParam("itemid") Integer itemid,
                                 @RequestParam("projectid") Integer projectid,
                                 Model model){
        adminDao.deleteProjectItem(itemid);
        return "redirect:/admin/project?projectid="+projectid.toString();
    }

    @RequestMapping("/admin/project_updatename")
    public String updateName(@RequestParam("name") String name,
                             @RequestParam("projectid") Integer projectid,
                             Model model){
        adminDao.updateProjectName(name,projectid);
        return "redirect:/admin/project?projectid="+projectid.toString();
    }

    @RequestMapping("/admin/project_updatemoney")
    public String updateMoney(@RequestParam("money") Double money,
                             @RequestParam("projectid") Integer projectid,
                             Model model){
        adminDao.updateProjectMoney(money,projectid);
        return "redirect:/admin/project?projectid="+projectid.toString();
    }

    @RequestMapping("/admin/project_updateday")
    public String updateDay(@RequestParam("day") Integer day,
                              @RequestParam("projectid") Integer projectid,
                              Model model){
        adminDao.updateProjectDay(day,projectid);
        return "redirect:/admin/project?projectid="+projectid.toString();
    }

    @RequestMapping("/admin/project_updatestatus")
    public String updateStatus(@RequestParam("status") Integer status,
                            @RequestParam("projectid") Integer projectid,
                            Model model){
        adminDao.updateProjectStatus(status,projectid);
        return "redirect:/admin/project?projectid="+projectid.toString();
    }

    @RequestMapping("/admin/project_updatetype")
    public String updateType(@RequestParam("typeid") Integer typeid,
                               @RequestParam("projectid") Integer projectid,
                               Model model){
        adminDao.deleteProjectType(projectid);
        adminDao.addProjectType(projectid,typeid);
        return "redirect:/admin/project?projectid="+projectid.toString();
    }

}

