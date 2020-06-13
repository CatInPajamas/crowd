package com.crowd.service.impl;

import com.crowd.constant.CrowdConstant;
import com.crowd.dao.AdminDao;
import com.crowd.domain.Admin;
import com.crowd.domain.Project;
import com.crowd.domain.ProjectType;
import com.crowd.exception.LoginAcctAlreadyInUseException;
import com.crowd.exception.LoginFailedException;
import com.crowd.service.AdminService;
import com.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin getAdminByLoginAcct(String loginacct, String adminpswd) {
        List<Admin> admins=adminDao.getAdminByLoginAcct(loginacct,adminpswd);
        // 2.判断Admin 对象是否为null
        if(admins == null || admins.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if(admins.size() > 1) {
            throw new
                    RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = admins.get(0);
        // 3.如果Admin 对象为null 则抛出异常
        if(admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.如果Admin 对象不为null 则将数据库密码从Admin 对象中取出
        // 5.将表单提交的明文密码进行加密
        // 6.对密码进行比较
        if(!Objects.equals(CrowdUtil.md5(adminpswd), CrowdUtil.md5(admin.getAdminpswd()))) {
        // 7.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 8.如果一致则返回Admin 对象
        return admin;
    }

    @Override
    public List<Admin> findByKeyword(String keyword) {
        return adminDao.findByKeyword(keyword);
    }

    @Override
    public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize) {
        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 2.查询Admin 数据
        List<Admin> adminList =adminDao.findByKeyword(keyword);
        // ※辅助代码：打印adminList 的全类名
        Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
        logger.debug("adminList 的全类名是："+adminList.getClass().getName());
        // 3.为了方便页面使用将adminList 封装为PageInfo
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return pageInfo;
    }

    @Override
    public List<ProjectType> getAllType() {
        return adminDao.getAllType();
    }

    @Override
    public void addType(String typename) {
        adminDao.addType(typename);
    }

    @Override
    public void deleteType(Integer id) {
        adminDao.deleteType(id);
    }

    @Override
    public void updateProjectType(String typename,Integer id) {
        adminDao.updateProjectType(typename,id);
    }

    @Override
    public List<Project> getAllProjects() {
        return adminDao.getAllProjects();
    }

    @Override
    public void deleteProject(Integer id) {
        adminDao.deleteProject(id);
    }

    @Override
    public void addProject(String name,String remark,Double money,int day,int status,String path1,String path2) {
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String s=df.format(date);
        Project project=new Project(name,remark,money,1,day,s,0.0,0,path1,path2);
        adminDao.addProject(project);
    }

    @Override
    public List<Project> getProjectByKeyword(String keyword) {
        return adminDao.getProjectByKeyword(keyword);
    }

    @Override
    public Project getProjectByid(Integer id) {
        return adminDao.getProjectByid(id);
    }

    @Override
    public void saveAdmin(String loginacct, String userPswd, String adminname, String email) {
        // 生成当前系统时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        Admin admin=new Admin(loginacct,CrowdUtil.md5(userPswd),adminname,email,createTime);
        // 执行保存，如果账户被占用会抛出异常
        try {
            adminDao.saveAdmin(admin);
        } catch (Exception e) {
            // 检测当前捕获的异常对象，如果是DuplicateKeyException 类型说明是账号重复导致的
            if(e instanceof DuplicateKeyException) {
            // 抛出自定义的LoginAcctAlreadyInUseException 异常
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }


    @Override
    public void remove(Integer adminId) {
        adminDao.remove(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminDao.getAdminById(adminId);
    }

    @Override
    public void update(Admin admin) {
        adminDao.update(admin);
    }


}
