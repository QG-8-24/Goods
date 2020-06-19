package com.com.example.goods.entity.vo;


import com.com.example.goods.utils.ListTransform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author TongTianFu
 * @date 2020/5/25
 */
public class Instorage {
    private Integer id;
    private String code;
    private String company;
    private String department;
    private String phone;
    private Date intime;
    private Integer type;
    private String linkman;
    private List<Integer> goodsidsList;
    private List<Integer> amountList;
    private List<HashMap<String, Object>> goodsList;

    public Instorage(com.com.example.goods.entity.Instorage instorage) {
        this.id = instorage.getId();
        this.code = instorage.getCode();
        this.company = instorage.getCompany();
        this.department = instorage.getDepartment();
        this.phone = instorage.getPhone();
        this.intime = instorage.getIntime();
        this.type = instorage.getType();
        this.linkman = instorage.getLinkman();
        //把逗号分隔的转化为数组
        this.goodsidsList = ListTransform.stringToList(instorage.getGoodsids());
        this.amountList = ListTransform.stringToList(instorage.getAmount());
    }

	public Instorage() {
	}

	public Instorage(Integer id, String code, String company, String department, String phone, Date intime, Integer type, String linkman, List<Integer> goodsidsList, List<Integer> amountList, List<HashMap<String, Object>> goodsList) {
		this.id = id;
		this.code = code;
		this.company = company;
		this.department = department;
		this.phone = phone;
		this.intime = intime;
		this.type = type;
		this.linkman = linkman;
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
		return "Instorage{" +
				"id=" + id +
				", code='" + code + '\'' +
				", company='" + company + '\'' +
				", department='" + department + '\'' +
				", phone='" + phone + '\'' +
				", intime=" + intime +
				", type=" + type +
				", linkman='" + linkman + '\'' +
				", goodsidsList=" + goodsidsList +
				", amountList=" + amountList +
				", goodsList=" + goodsList +
				'}';
	}
}