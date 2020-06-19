package com.com.example.goods.entity.vo;


import com.com.example.goods.utils.ListTransform;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author TongTianFu
 * @date 2020/5/25
 */
public class Outstorage {
    private Integer id;
    private String code;
    private String company;
    private String linkman;
    private String department;
    private String phone;
    private Date outtime;
    private Integer type;
    private Integer companyid;
    private Integer departmentid;
    private String linkmanid;
    private String goodsids;
    private String amount;
    private Integer status;
    private List<Integer> goodsidsList;
    private List<Integer> amountList;
    private List<HashMap<String, Object>> goodsList;

    public Outstorage() {
    }

    public Outstorage(Integer id, String code, String company, String linkman, String department, String phone, Date outtime, Integer type, Integer companyid, Integer departmentid, String linkmanid, String goodsids, String amount, Integer status, List<Integer> goodsidsList, List<Integer> amountList, List<HashMap<String, Object>> goodsList) {
        this.id = id;
        this.code = code;
        this.company = company;
        this.linkman = linkman;
        this.department = department;
        this.phone = phone;
        this.outtime = outtime;
        this.type = type;
        this.companyid = companyid;
        this.departmentid = departmentid;
        this.linkmanid = linkmanid;
        this.goodsids = goodsids;
        this.amount = amount;
        this.status = status;
        this.goodsidsList = goodsidsList;
        this.amountList = amountList;
        this.goodsList = goodsList;
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

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
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

    public Date getOuttime() {
        return outtime;
    }

    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public List<Integer> getGoodsidsList() {
        return goodsidsList;
    }

    public void setGoodsidsList(List<Integer> goodsidsList) {
        this.goodsidsList = goodsidsList;
    }

    public List<Integer> getAmountList() {
        return amountList;
    }

    public void setAmountList(List<Integer> amountList) {
        this.amountList = amountList;
    }

    public List<HashMap<String, Object>> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<HashMap<String, Object>> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public String toString() {
        return "Outstorage{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", company='" + company + '\'' +
                ", linkman='" + linkman + '\'' +
                ", department='" + department + '\'' +
                ", phone='" + phone + '\'' +
                ", outtime=" + outtime +
                ", type=" + type +
                ", companyid=" + companyid +
                ", departmentid=" + departmentid +
                ", linkmanid='" + linkmanid + '\'' +
                ", goodsids='" + goodsids + '\'' +
                ", amount='" + amount + '\'' +
                ", status=" + status +
                ", goodsidsList=" + goodsidsList +
                ", amountList=" + amountList +
                ", goodsList=" + goodsList +
                '}';
    }
}