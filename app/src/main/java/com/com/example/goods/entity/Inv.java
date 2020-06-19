package com.com.example.goods.entity;

public class Inv {
    private String unit;
    private Integer amount;
    private String code;
    private Integer goodsId;
    private String name;
    private String typeName;
    private String producer;
    private String remark;
    private String specifications;
    private Integer inventoryId;
    private Integer typeId;

    public Inv() {
    }

    public Inv(String unit, Integer amount, String code, Integer goodsId, String name, String typeName, String producer, String remark, String specifications, Integer inventoryId, Integer typeId) {
        this.unit = unit;
        this.amount = amount;
        this.code = code;
        this.goodsId = goodsId;
        this.name = name;
        this.typeName = typeName;
        this.producer = producer;
        this.remark = remark;
        this.specifications = specifications;
        this.inventoryId = inventoryId;
        this.typeId = typeId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Inv{" +
                "unit='" + unit + '\'' +
                ", amount=" + amount +
                ", code='" + code + '\'' +
                ", goodsId=" + goodsId +
                ", name='" + name + '\'' +
                ", typeName='" + typeName + '\'' +
                ", producer='" + producer + '\'' +
                ", remark='" + remark + '\'' +
                ", specifications='" + specifications + '\'' +
                ", inventoryId=" + inventoryId +
                ", typeId=" + typeId +
                '}';
    }
}
