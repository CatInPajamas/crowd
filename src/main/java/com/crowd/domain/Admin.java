package com.crowd.domain;

public class Admin {
    private Integer id;

    private String loginacct;

    private String adminpswd;

    private String adminname;

    private String email;

    private String createtime;

    public Admin(String loginacct, String adminpswd, String adminname, String email, String createtime) {
        this.loginacct = loginacct;
        this.adminpswd = adminpswd;
        this.adminname = adminname;
        this.email = email;
        this.createtime = createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }

    public String getAdminpswd() {
        return adminpswd;
    }

    public void setAdminpswd(String adminpswd) {
        this.adminpswd = adminpswd;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", loginacct='" + loginacct + '\'' +
                ", adminpswd='" + adminpswd + '\'' +
                ", adminname='" + adminname + '\'' +
                ", email='" + email + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
