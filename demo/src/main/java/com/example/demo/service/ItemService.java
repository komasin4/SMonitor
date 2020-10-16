package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.example.demo.model.Data;
import com.example.demo.model.Group;
import com.example.demo.model.Item;
import com.example.demo.model.ItemList;
import com.example.demo.model.ResultModel;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.ItemRepository;

import com.example.demo.service.ItemService;

@Service
public class ItemService {

	
	@Autowired ItemRepository itemRepository;
	@Autowired GroupRepository groupRepository;
	
	public List<Group> getGroups()	{
		Iterable<Group> groups = groupRepository.findAll();
		
		List<Group> groupList = new ArrayList<Group>();
		groups.iterator().forEachRemaining(groupList::add);
		
		return groupList;
	}
	
	public List<Item> getItems(String type)	{
		
		Iterable<Item> items = itemRepository.getItems(type);
		
		List<Item> itemList = new ArrayList<Item>();
		items.iterator().forEachRemaining(itemList::add);
		
		return itemList;
	}
	
	public List<Item> getAllItems()	{
		return itemRepository.getAllItems();
	}
	
	public List<HashMap<String, String>> getOrder()	{
		return itemRepository.getOrder();
	}

	private ResultModel getCurrentPrice(String query)	{

		String url = "http://polling.finance.naver.com/api/realtime.nhn?query=" + query;
		String result = new String();
		ResultModel resultModel = new ResultModel();
		Gson gson = new Gson();
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		try {
			HttpResponse response = client.execute(request);

			if(response.getStatusLine().getStatusCode() != 200)
				System.out.println("Response Code : "
			                + response.getStatusLine().getStatusCode());
	
			HttpEntity resEntity = response.getEntity();
			
			if (resEntity != null) 
				result = EntityUtils.toString(resEntity,"utf-8");
			
			if(result != null && !result.isEmpty())	{
				resultModel = gson.fromJson(result, ResultModel.class);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultModel;
	}	
	
	public void checkItemList()	{
		if(ItemList.getItemList() == null || ItemList.getItemList().size() < 1)
			reloadItemList();
	}
	
	public void reloadItemList()	{
		ItemList.setItemList(getAllItems());
	}
	
	public void checkPricemap()	{
		if(ItemList.getPriceMap() == null || ItemList.getPriceMap().size() < 1)
			reloadPricemap();
	}
	
	public void reloadPricemap()	{
		
		checkItemList();
		
		List<String> indexList = new ArrayList<String>();
		List<String> itemList = new ArrayList<String>();
		
		int idx = 0;
		
		List<Item> currentList = ItemList.getItemList();

		for(Item item:currentList)	{
			if(item.getType() != null && item.getType().equals("INDEX"))
				indexList.add(item.getItem_no());
			else
				itemList.add(item.getItem_no());
		}
		
		String itemListString = String.join(",", indexList);
		
		String indexQuery = "SERVICE_INDEX:" + String.join(",", indexList);
		String itemQuery = "SERVICE_ITEM:" + String.join(",", itemList);

		ResultModel currentIndex = getCurrentPrice(indexQuery);
		ResultModel currentItem = getCurrentPrice(itemQuery);

		
		HashMap<String,Data> priceMap = new HashMap<String,Data>();
		
		if(currentIndex.getResult().getAreas().size() > 0)
			for(Data data:currentIndex.getResult().getAreas().get(0).getDatas())	{
				priceMap.put(data.getCd(), data);
			}

		if(currentItem.getResult().getAreas().size() > 0)
			for(Data data:currentItem.getResult().getAreas().get(0).getDatas())	{
				priceMap.put(data.getCd(), data);
			}

		//ItemList.itemCurrentList = new ItemCurrent[priceMap.size()];
		HashMap<String,Data> newPriceMap = new HashMap<String,Data>();
		
		for(Item item:currentList)	{
			//item.setData(priceMap.get(item.getItem_no()));
			newPriceMap.put(item.getItem_no(),  priceMap.get(item.getItem_no()));
		}

		ItemList.setPriceMap(newPriceMap);

	}
}
