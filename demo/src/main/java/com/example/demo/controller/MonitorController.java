package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Data;
import com.example.demo.model.Item;
import com.example.demo.model.ItemExt;
import com.example.demo.model.ItemList;
import com.example.demo.model.ResultModel;
import com.example.demo.service.ItemService;
import com.example.demo.service.MonitorService;

@Controller
public class MonitorController {
	private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);
	@Autowired ItemService itemService;
	@Autowired MonitorService monitorService;
	
	@GetMapping("/monitor")
	public ModelAndView monitor()	{

		ModelAndView mav = new ModelAndView();
		
		itemService.checkItemList();
		itemService.checkPricemap();
		
		List<Item> itemList = ItemList.getItemList();
		HashMap<String, Data> priceMap = ItemList.getPriceMap();

		List<ItemExt> itemExtList = new ArrayList<ItemExt>();
		
		for(Item item:itemList)	{
			ItemExt itemExt = new ItemExt(item);
			itemExt.setData(priceMap.get(item.getItem_no()));
			itemExtList.add(itemExt);
		}
		
		mav.addObject("displayList", monitorService.getDisplay(itemExtList));
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"); 
		
		mav.addObject("getTime", dt.format(ItemList.getGetTime()));

		mav.setViewName("monitor");

		return mav;
		
	}
	
	@GetMapping("/freemarkertest")
	public ModelAndView test()	{
		
		ModelAndView mav = new ModelAndView();

		mav.addObject("message", "Hello, Freemarker");
		mav.setViewName("welcome");
		
		return mav;
		
	}
}
