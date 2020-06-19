package com.com.example.goods.entity;

import java.util.Date;


public class Outstorage {
    private Integer id;
    private String code;
    private Integer companyid;
    private Integer departmentid;
    private String linkmanid;
    private String phone;
    private Date intime;
    private Integer type;
    private String goodsids;
    private String amount;
    private Integer status;

    public Outstorage() {
    }

    public Outstorage(Integer id, String code, Integer companyid, Integer departmentid, String linkmanid, String phone, Date intime, Integer type, String goodsids, String amount, Integer status) {
        this.id = id;
        this.code = code;
        this.companyid = companyid;
        this.departmentid = departmentid;
        this.linkmanid = linkmanid;
        this.phone = phone;
        this.intime = intime;
        this.type = type;
        this.goodsids = goodsids;
        this.amount = amount;
        this.status = status;
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

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getLinkmanid() {
        return linkmanid;
    }

    public void setLinkmanid(String linkmanid) {
        this.linkmanid = linkmanid;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Outstorage{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", companyid=" + companyid +
                ", departmentid=" + departmentid +
                ", linkmanid='" + linkmanid + '\'' +
                ", phone='" + phone + '\'' +
                ", intime=" + intime +
                ", type=" + type +
                ", goodsids='" + goodsids + '\'' +
                ", amount='" + amount + '\'' +
                ", status=" + status +
                '}';
    }
}