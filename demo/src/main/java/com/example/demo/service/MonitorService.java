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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.example.demo.model.Data;
import com.example.demo.model.Display;
import com.example.demo.model.ItemExt;
import com.example.demo.model.ItemList;
import com.example.demo.model.ResultModel;

@Service
public class MonitorService {

	private static final Logger logger = LoggerFactory.getLogger(MonitorService.class);
	
	@Autowired ItemService itemService;
	
	/*
	public ResultModel getCurrentPrice(String query)	{
		
	
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
	*/
	
	//@Scheduled(cron="0/15 * 9-15 * * 1-5 ")
	@Scheduled(cron="0 0/10 9-15 * * 1-5 ")
	public void stockMonitor()	{
		logger.info("stockMonitor start");
		itemService.reloadPricemap();;
	}
	
	public List<Display> getDisplay(List<ItemExt> itemExtList)	{
		ArrayList<Display> displayList = new ArrayList<Display>();
		
		for(ItemExt itemExt:itemExtList)	{
			Display display = new Display();
			
			display.setCode(itemExt.getItem().getItem_no());
			
			if(itemExt.getData().getNm()==null)
				display.setName(itemExt.getData().getCd());
			else 
				display.setName(itemExt.getData().getNm());

			if(itemExt.getItem().getType().equals("INDEX"))	{
				display.setCurrent_price(itemExt.getData().getNv()==null?0:itemExt.getData().getNv()/100);
				display.setBase_price((float)(itemExt.getData().getSv()==null?0:itemExt.getData().getSv()/100));
				display.setRatio(String.valueOf(itemExt.getData().getCr()==null?0:itemExt.getData().getCr()));
			}
			else	{
				display.setCurrent_price(itemExt.getData().getNv()==null?0:itemExt.getData().getNv());
				display.setBase_price((float)(itemExt.getData().getSv()==null?0:itemExt.getData().getSv()));
				display.setRatio(String.valueOf(itemExt.getData().getCr()==null?0:itemExt.getData().getCr()));
				
				if(display.getCurrent_price() < display.getBase_price())
					display.setRatio("-" + display.getRatio());
			}
			
			int high = itemExt.getItem().getHigh();
			int low = itemExt.getItem().getLow();
			
			display.setLevel1(high);
			display.setLevel2(high - (int)((high-low) * 0.25));
			display.setLevel3(high - (int)((high-low) * 0.5));
			display.setLevel4(high - (int)((high-low) * 0.75));
			display.setLevel5(low);
			
			if(display.getLevel5() >= display.getCurrent_price())	{
				display.setOncolor(5);
				display.setColor("purple");
			} else if (display.getLevel4() >= display.getCurrent_price()) {
				display.setOncolor(4);
				display.setColor("blueViolet");
			} else if (display.getLevel3() >= display.getCurrent_price()) {
				display.setOncolor(3);
				display.setColor("magenta");
			} else if (display.getLevel2() >= display.getCurrent_price()) {
				display.setOncolor(2);
				display.setColor("orchid");
			} else if (display.getLevel1() >= display.getCurrent_price()) {
				display.setOncolor(1);
				display.setColor("lavender");
			}
			
			display.setGroup_name(itemExt.getItem().getGroup_name());
			
			displayList.add(display);
		}
			
		return displayList;
	}
}
