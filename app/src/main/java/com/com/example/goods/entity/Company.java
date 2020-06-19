package com.com.example.goods.entity;


import java.io.Serializable;

public class Company implements Serializable {
    private Integer id;
    private String name;
    private String shortname;
    private String principal;
    private String phone;
    private String address;
    private String code;
    private String remark;

    public Company(Integer id, String name, String shortname, String principal, String phone, String address, String code, String remark) {
        this.id = id;
        this.name = name;
        this.shortname = shortname;
        this.principal = principal;
        this.phone = phone;
        this.address = address;
        this.code = code;
        this.remark = remark;
    }

    public Company() {
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

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@Override
	public String toString() {
		return "Company{" +
				"id=" + id +
				", name='" + name + '\'' +
				", shortname='" + shortname + '\'' +
				", principal='" + principal + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", code='" + code + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}