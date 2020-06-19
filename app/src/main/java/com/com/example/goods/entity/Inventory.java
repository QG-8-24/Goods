package com.com.example.goods.entity;


public class Inventory {
    private Integer id;
    private Integer goodsid;
    private Integer amount;

	public Inventory() {
	}

	public Inventory(Integer id, Integer goodsid, Integer amount) {
		this.id = id;
		this.goodsid = goodsid;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Inventory{" +
				"id=" + id +
				", goodsid=" + goodsid +
				", amount=" + amount +
				'}';
	}
}