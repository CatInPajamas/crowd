package com.crowd.domain;

public class Project {
    private Integer id;
    private String name;
    private String remark;
    private Double money;
    private Integer status;
    private Integer day;
    private String deploydate;
    private Double supportmoney;
    private Integer supporter;
    private String head_picture_path;
    private String body_picture_path;

    public Project() {
    }

    public Project(String name, String remark, Double money, Integer status, Integer day, String deploydate, Double supportmoney, Integer supporter, String head_picture_path, String body_picture_path) {
        this.name = name;
        this.remark = remark;
        this.money = money;
        this.status = status;
        this.day = day;
        this.deploydate = deploydate;
        this.supportmoney = supportmoney;
        this.supporter = supporter;
        this.head_picture_path = head_picture_path;
        this.body_picture_path = body_picture_path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDeploydate() {
        return deploydate;
    }

    public void setDeploydate(String deploydate) {
        this.deploydate = deploydate;
    }

    public Double getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Double supportmoney) {
        this.supportmoney = supportmoney;
    }

    public Integer getSupporter() {
        return supporter;
    }

    public void setSupporter(Integer supporter) {
        this.supporter = supporter;
    }

    public String getHead_picture_path() {
        return head_picture_path;
    }

    public void setHead_picture_path(String head_picture_path) {
        this.head_picture_path = head_picture_path;
    }

    public String getBody_picture_path() {
        return body_picture_path;
    }

    public void setBody_picture_path(String body_picture_path) {
        this.body_picture_path = body_picture_path;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", money=" + money +
                ", status=" + status +
                ", day=" + day +
                ", deploydata='" + deploydate + '\'' +
                ", supportmoney=" + supportmoney +
                ", supporter=" + supporter +
                ", head_picture_path='" + head_picture_path + '\'' +
                ", body_picture_path='" + body_picture_path + '\'' +
                '}';
    }
}
