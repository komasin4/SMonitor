package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Result;
import com.example.demo.model.ResultModel;
import com.google.gson.Gson;

@Controller
public class demoController {
	
	private static final Logger logger = LoggerFactory.getLogger(demoController.class);
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public @ResponseBody String test()	{
		System.out.println("debug test 1");
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
		System.out.println("debug test 2");
		return "test!";
	}
	
	@RequestMapping(value="/request/{item_no}", method=RequestMethod.GET)
	public @ResponseBody Object requestTest(@PathVariable String item_no)	{
		
		if(item_no == null || item_no.isEmpty())
			item_no = "000660";
		
		//String url = "http://polling.finance.naver.com/api/realtime.nhn?query=SERVICE_ITEM:000660";
		String url = "http://polling.finance.naver.com/api/realtime.nhn?query=SERVICE_ITEM:" + item_no;
		String result = new String();
		StringBuffer sbResult = new StringBuffer();
		ResultModel resultModel = new ResultModel();
		Gson gson = new Gson();
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		/*
		//request.addHeader("User-Agent", USER_AGENT);
		*/
		
		//request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		//request.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8");
		//request.setHeader("content-type", "text/plain;charset=UTF-8");
		
		try {
			HttpResponse response = client.execute(request);

			System.out.println("Response Code : "
			                + response.getStatusLine().getStatusCode());
	
			/*
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
	
			String line = "";
			while ((line = rd.readLine()) != null) {
				sbResult.append(line);
			}

			result = sbResult.toString();
			*/
			
			
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
}
