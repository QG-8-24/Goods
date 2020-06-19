package com.com.example.goods.entity;


public class User {
    private String phone;
    private String name;
    private Integer age;
    private String gender;
    private String remark;
    private Integer departmentid;
    private Integer roleid;

    public User() {
    }

    public User(String phone, String name, Integer age, String gender, String remark, Integer departmentid, Integer roleid) {
        this.phone = phone;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.remark = remark;
        this.departmentid = departmentid;
        this.roleid = roleid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }


    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", remark='" + remark + '\'' +
                ", departmentid=" + departmentid +
                ", roleid=" + roleid +
                '}';
    }
}