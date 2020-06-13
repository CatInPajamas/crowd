package com.crowd.service.impl;

import com.crowd.CrowdApplication;
import com.crowd.domain.Admin;
import com.crowd.exception.LoginAcctAlreadyInUseException;
import com.crowd.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrowdApplication.class)
@EnableAutoConfiguration
@EnableWebMvc
public class AdminServiceImplTest {
    @Autowired
    private AdminService as;


    @Test
    public void test1(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        AdminService as = (AdminService) ac.getBean("adminService");
        Admin admin = as.getAdminByLoginAcct("admin", "123456");
        System.out.println(admin.getLoginacct());
    }

    @Test
    public void test2(){
        List<Admin> adminList=as.findByKeyword("admin");
        for (int i = 0; i < adminList.size(); i++) {
            Admin admin =  adminList.get(i);
            System.out.println(admin);
        }
     }

    @Test
    public void test3(){
        Admin admin=as.getAdminById(1);
        System.out.println(admin.getAdminname());
    }

    @Test
    public void test4(){
        try{
            as.saveAdmin("adminadmin","654321","hxg","11111@cug.com");
        }catch (Exception e){
            System.out.println("111:");
            System.out.println(e.getMessage());
        }
    }
}