package com.example.demo.service;

import java.io.IOException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.ResultModel;
import com.google.gson.Gson;

@Service
public class demoService {
	private static final Logger logger = LoggerFactory.getLogger(demoService.class);
	
	@Autowired LineService lineService;
	
	private String access_token;
	private String refresh_token;
	
	private Boolean expire_access_token = true;
	private Boolean expire_refresh_token = true;
	private float divid_unit = 100;
	
	private float alertBase_KOSPI = 0;
	private float alertBase_KOSDAQ = 0;
	
	private boolean alert_kospi = false;
	private boolean alert_kosdaq = false;
	
	public void setAlert(String type, boolean bAlert)	{
		
		if("KOSDAQ".equals(type))
			alert_kosdaq = bAlert;
		else
			alert_kospi = bAlert;
	}

	public void setAlertBase(String type, float index)	{
		
		if("KOSDAQ".equals(type))	{
			alertBase_KOSDAQ = index;
		} else	{
			alertBase_KOSPI = index;
		}
		
		setAlert(type, true);
	}
	
	public String getAlertBase(String type)	{
		if("KOSDAQ".equals(type))
			return String.valueOf(alertBase_KOSDAQ);
		else
			return String.valueOf(alertBase_KOSPI);
	}
	
	@Scheduled(cron="0/15 * 9-15 * * 1-5 ")
	public void batch()	{
		logger.debug("------------------------------");
		
		//String queryItem = "SERVICE_ITEM:" + item_no;
		
		String query = "SERVICE_INDEX:KOSPI";
		ResultModel kospiResult = (ResultModel) requestDetail(query);
		Float kospi_index = kospiResult.getResult().getAreas().get(0).getDatas().get(0).getNv() / divid_unit;
		printResult(kospiResult, true);

		if(kospi_index <= alertBase_KOSPI && alert_kospi == true && alertBase_KOSPI > 0)	{
			lineService.sendLineMessage("under alert KOSPI:" + kospi_index + ":" + alertBase_KOSPI);
			alert_kospi=false;
		} else if(kospi_index > alertBase_KOSPI && alert_kospi == false && alertBase_KOSPI > 0) {
			lineService.sendLineMessage("revoke alert KOSPI:" + kospi_index + ":" + alertBase_KOSPI);
			alert_kospi=true;
		}

		query = "SERVICE_INDEX:KOSDAQ";
		ResultModel kosdaqResult = (ResultModel) requestDetail(query);
		Float kosdaq_index = kosdaqResult.getResult().getAreas().get(0).getDatas().get(0).getNv() / divid_unit;
		printResult(kosdaqResult, true);
		
		if(kosdaq_index <= alertBase_KOSDAQ && alert_kosdaq == true && alertBase_KOSDAQ > 0)	{
			lineService.sendLineMessage("under alert KOSDAQ:" + kosdaq_index + ":" + alertBase_KOSDAQ);
			alert_kosdaq=false;
		} else if(kosdaq_index > alertBase_KOSDAQ && alert_kosdaq == false && alertBase_KOSDAQ > 0) {
			lineService.sendLineMessage("revoke alert KOSDAQ:" + kosdaq_index + ":" + alertBase_KOSDAQ);
			alert_kosdaq=true;
		}
		
		/*
		String alert_message = "";
		
		if(alert_kospi && alert_kosdaq)	{
			alert_message = "under alert KOSPI:" + kospi_index + ":" + alertBase_KOSPI + "\n" +
							"under alert KOSDAQ:" + kosdaq_index + ":" + alertBase_KOSDAQ;
			lineService.sendLineMessage(alert_message);
		} else if(alert_kospi) {
			alert_message = "under alert KOSPI:" + kospi_index + ":" + alertBase_KOSPI;
			lineService.sendLineMessage(alert_message);
		} else if(alert_kosdaq)	{
			alert_message = "under alert KOSDAQ:" + kosdaq_index + ":" + alertBase_KOSDAQ;
			lineService.sendLineMessage(alert_message);
		}
		*/
		

		/*
		String query = "SERVICE_INDEX:KOSPI";
		printResult((ResultModel) requestDetail(query), true);

		query = "SERVICE_INDEX:KOSDAQ";
		ResultModel kosdaqResult = (ResultModel) requestDetail(query);
		Float index = kosdaqResult.getResult().getAreas().get(0).getDatas().get(0).getNv() / divid_unit;
		
		if(index <= alertBase && newAlert == true)	{
			lineService.sendLineMessage("under alert KOSDAQ:" + index + ":" + alertBase);
			newAlert=false;
		} else if((index > alertBase && newAlert == false)) {
			lineService.sendLineMessage("revoke alert alert KOSDAQ:" + index + ":" + alertBase);
			newAlert=true;
		}
		
		printResult(kosdaqResult, true);

		query = "SERVICE_ITEM:122630";
		printResult((ResultModel) requestDetail(query), false);

		query = "SERVICE_ITEM:233740";
		printResult((ResultModel) requestDetail(query), false);
		*/
		
		logger.info("alertBase:" + alertBase_KOSPI + ":" + alert_kospi + ":" + alertBase_KOSDAQ + ":" + alert_kosdaq);
}
	
	public void printResult(ResultModel resultModel, Boolean bDivid)	{
		//Gson gson = new Gson();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		long time = resultModel.getResult().getTime();
		String cd = resultModel.getResult().getAreas().get(0).getDatas().get(0).getCd();
		String nm = resultModel.getResult().getAreas().get(0).getDatas().get(0).getNm();
		Float index = resultModel.getResult().getAreas().get(0).getDatas().get(0).getNv();
		Float cr = resultModel.getResult().getAreas().get(0).getDatas().get(0).getCr();
		Date now = new Date(time);
				
		//logger.info(sf.format(now) + ":" + (nm==null?"INDEX":nm) + "(" + cd + "):" + (bDivid?index/divid_unit:index) + ":" + cr + "%");
		logger.info((nm==null?"INDEX":nm) + "(" + cd + "):" + (bDivid?index/divid_unit:index) + ":" + cr + "%");
		
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
		//StringBuffer sbResult = new StringBuffer();
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
