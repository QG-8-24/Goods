package com.com.example.goods.entity;


import java.io.Serializable;

public class Department implements Serializable {
    private Integer id;
    private String name;
    private String code;
    private String remark;
    private Integer companyid;

    public Department() {
    }

    public Department(Integer id, String name, String code, String remark, Integer companyid) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.remark = remark;
        this.companyid = companyid;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", remark='" + remark + '\'' +
                ", companyid=" + companyid +
                '}';
    }
}