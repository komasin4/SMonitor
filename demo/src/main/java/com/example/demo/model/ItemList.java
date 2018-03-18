package com.example.demo.model;

import java.util.HashMap;
import java.util.List;

public class ItemList {
	//public static List<Item> indexes;
	//public static List<Item> items;
	//public static List<HashMap<String,String>> order;
	
	//public static String indexQuery;
	//public static String itemQuery;
	
	//public static ItemCurrent[] itemCurrentList;
	private static List<Item> itemList;
	private static HashMap<String,Data> priceMap;
	
	public static List<Item> getItemList() {
		return itemList;
	}
	public static synchronized void setItemList(List<Item> itemList) {
		ItemList.itemList = itemList;
	}
	public static HashMap<String, Data> getPriceMap() {
		return priceMap;
	}
	public static synchronized void setPriceMap(HashMap<String, Data> priceMap) {
		ItemList.priceMap = priceMap;
	} 
}
