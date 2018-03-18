package com.example.demo.model;

public class ItemExt {

	private Data data;
	private Item item;
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public ItemExt(Item item) {
		this.item = item;
	}

	
}
