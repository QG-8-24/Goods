package com.com.example.goods.entity;


public class Goods {
    private Integer id;
    private String code;
    private Integer typeid;
    private String name;
    private String unit;
    private String specifications;
    private String producer;
    private String remark;
    private String image;

    public Goods() {
    }

    public Goods(Integer id, String code, Integer typeid, String name, String unit, String specifications, String producer, String remark, String image) {
        this.id = id;
        this.code = code;
        this.typeid = typeid;
        this.name = name;
        this.unit = unit;
        this.specifications = specifications;
        this.producer = producer;
        this.remark = remark;
        this.image = image;
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

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}