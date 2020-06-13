package com.crowd.domain;

public class PriceItem {
    private Integer id;
    private Integer projectId;
    private double money;
    private Integer delivery;
    private String introduce;

    public PriceItem() {
    }

    public PriceItem(Integer id, Integer projectId, double money, Integer delivery, String introduce) {
        this.id = id;
        this.projectId = projectId;
        this.money = money;
        this.delivery = delivery;
        this.introduce = introduce;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "PriceItem{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", money=" + money +
                ", delivery=" + delivery +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
