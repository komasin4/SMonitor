package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.ResultModel;
import com.google.gson.Gson;

@Service
public class demoService {
	private static final Logger logger = LoggerFactory.getLogger(demoService.class);
	
	@Scheduled(cron="0/15 * * * * *")
	public void batch()	{
		logger.debug("--------------------------------------------------");
		
		//String queryItem = "SERVICE_ITEM:" + item_no;
		
		
		
		String query = "SERVICE_INDEX:KOSPI";
		printResult((ResultModel) requestDetail(query), true);

		query = "SERVICE_INDEX:KOSDAQ";
		printResult((ResultModel) requestDetail(query), true);

		query = "SERVICE_ITEM:001000";
		printResult((ResultModel) requestDetail(query), false);
}
	
	public void printResult(ResultModel resultModel, Boolean bDivid)	{
		
		Gson gson = new Gson();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		float divid_unit = 100;
		
		if(!bDivid)
			divid_unit = 1;

		long time = resultModel.getResult().getTime();
		String cd = resultModel.getResult().getAreas().get(0).getDatas().get(0).getCd();
		String nm = resultModel.getResult().getAreas().get(0).getDatas().get(0).getNm();
		Float index = resultModel.getResult().getAreas().get(0).getDatas().get(0).getNv();
		Date now = new Date(time);
				
		logger.info(sf.format(now) + ":" + (nm==null?"INDEX":nm) + "(" + cd + "):" + index/divid_unit);
		
		//logger.info(gson.toJson(resultModel));
	}
	
	public Object requestDetail(@PathVariable String query)	{
		/*
		if(item_no == null || item_no.isEmpty())
			item_no = "000660";
		*/
		
		//String url = "http://polling.finance.naver.com/api/realtime.nhn?query=SERVICE_ITEM:000660";
		String url = "http://polling.finance.naver.com/api/realtime.nhn?query=" + query;
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

			if(response.getStatusLine().getStatusCode() != 200)
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
