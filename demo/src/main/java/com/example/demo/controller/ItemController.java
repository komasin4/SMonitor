package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Group;
import com.example.demo.model.Item;
import com.example.demo.model.ItemList;
import com.example.demo.service.ItemService;

@Controller
public class ItemController {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	@Autowired ItemService itemService;

	@GetMapping("/getitem")
	public @ResponseBody Object getItems()	{
		//return itemService.getItems();
		//itemService.checkItemCurrentList();
		itemService.checkItemList();
		return ItemList.getItemList();
	}

	@GetMapping("/reloaditem")
	public @ResponseBody Object reloadItems()	{
		itemService.reloadItemList();
		return ItemList.getItemList();
	}
	
	@GetMapping("/current")
	public @ResponseBody Object getCurrentPrice()	{
		itemService.reloadPricemap();
		return ItemList.getPriceMap();
	}
}
