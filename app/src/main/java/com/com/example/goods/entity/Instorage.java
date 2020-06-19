package com.com.example.goods.entity;


import java.util.Date;

public class Instorage {
    private Integer id;
    private String code;
    private String company;
    private String department;
    private String phone;
    private Date intime;
    private Integer type;
    private String linkman;
    private String goodsids;
    private String amount;

    public Instorage() {
    }

    public Instorage(Integer id, String code, String company, String department, String phone, Date intime, Integer type, String linkman, String goodsids, String amount) {
        this.id = id;
        this.code = code;
        this.company = company;
        this.department = department;
        this.phone = phone;
        this.intime = intime;
        this.type = type;
        this.linkman = linkman;
        this.goodsids = goodsids;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getGoodsids() {
        return goodsids;
    }

    public void setGoodsids(String goodsids) {
        this.goodsids = goodsids;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Instorage{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", phone='" + phone + '\'' +
                ", intime=" + intime +
                ", type=" + type +
                ", linkman='" + linkman + '\'' +
                ", goodsids='" + goodsids + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}