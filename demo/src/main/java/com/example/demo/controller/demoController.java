package com.example.demo.controller;

/*
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.configuration.ConstantsConfiguration;
import com.example.demo.service.LineService;
import com.example.demo.service.Util;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.apache.http.NameValuePair;


@Controller
public class demoController {
	
	private static final Logger logger = LoggerFactory.getLogger(demoController.class);
	@Autowired com.example.demo.service.demoService demoService;
	@Autowired LineService lineService;
	
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
	
	@GetMapping(value="/line/send")
	public @ResponseBody String sendMessageLine(@RequestParam String message)	{
		return lineService.sendLineMessage(message);
	}
	
	@GetMapping(value="/setalertbase")
	public @ResponseBody String setAlertBase(@RequestParam float alertBase, @RequestParam String type)	{
		demoService.setAlertBase(type, alertBase);
		return "OK";
	}

	
	@GetMapping(value="callback")
	public String callback(@RequestParam String code)	{
		logger.info("code:" + code);
		
		if(code == null || code.isEmpty()) {
			return "error";
		}
		
		try	{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			//Access Token Get
			params.add(new BasicNameValuePair("grant_type", "authorization_code"));
			params.add(new BasicNameValuePair("client_id", ConstantsConfiguration.CLIENT_ID));
			params.add(new BasicNameValuePair("client_secret", ConstantsConfiguration.CLIENT_SECRET));
			params.add(new BasicNameValuePair("code", code));
			String responseBody = Util.postURL(ConstantsConfiguration.OAUTH_URL, params, null);
			
			JsonObject tokenObject = new Gson().fromJson(responseBody, JsonObject.class);
	    	String access_token = tokenObject.get("access_token").getAsString();
	    	logger.info("access_token:" + access_token);
	    	
	    	JsonObject template_object = new JsonObject();
	    	JsonObject link_object = new JsonObject();
	    	
	    	template_object.addProperty("object_type", "text");
	    	template_object.addProperty("text", "테스트입니다.");
	    	
	    	link_object.addProperty("web_url", "http://komasin4.ipdisk.co.kr");
	    	link_object.addProperty("mobile_web_url", "http://komasin4.ipdisk.co.kr");
	    	
	    	template_object.add("link", new Gson().fromJson(link_object.toString(), JsonElement.class));
	    	template_object.addProperty("button_title", "바로 확인");
	    	
	    	params.clear();
	    	Header[] headers = {new BasicHeader("Authorization","Bearer " + access_token)};
	    	params.add(new BasicNameValuePair("template_object", template_object.toString()));
	    	responseBody = Util.postURL(ConstantsConfiguration.MESSAGE_SEND_URL, params, headers);
	    	
	    	logger.info("response:" + responseBody);
	    	//access_token이 없으면 오류
//	    	if(access_token == null || access_token.equals(""))	{
//	    		
//	    	}
	    	
	    	/*
	    	
			//GET ME
	    	params.clear();
	    	Header[] headers = {new BasicHeader("Authorization","Bearer " + access_token)};
			//responseBody = getURL("https://kapi.kakao.com/v1/user/me", params, headers);
	    	responseBody = postURL("https://kapi.kakao.com/v1/user/me", params, headers);
			JsonObject jsonBody = new Gson().fromJson(responseBody, JsonObject.class);
			
	    	//계정 정보 Assign
	    	//String email = jsonBody.get("kaccount_email").getAsString();
	    	String id = jsonBody.get("id").getAsString();
	    	JsonObject properties = jsonBody.getAsJsonObject("properties");
	    	
	    	String profile_image = properties.get("profile_image").isJsonNull()?"":properties.get("profile_image").getAsString();
	    	String nickname = properties.get("nickname").isJsonNull()?"":properties.get("nickname").getAsString();
	    	String thumbnail_image = properties.get("thumbnail_image").isJsonNull()?"":properties.get("thumbnail_image").getAsString();

	    	//logger.info("kaccount_email:" + email);
	    	logger.info("id:" + id);
	    	logger.info("profile_image:" + profile_image);
	    	logger.info("nickname:" + nickname);
	    	logger.info("thumbnail_image:" + thumbnail_image);
	    	*/

		} catch (Exception e)	{
			e.printStackTrace();
			return ("error");
		}
		
		return "jsptest";
	}
	
	
	/*
	@PostMapping(value="/parse")
	public @ResponseBody Object parsingTest(@RequestParam String url, @RequestParam String tag)	{
		
		
		try {
			Document doc = Jsoup.connect(url).get();
			logger.info(doc.title());

			//Elements newsHeadlines = doc.select("#mp-itn b a");
			//for (Element headline : newsHeadlines) {
			//	logger.info("%s\n\t%s", 
			//    headline.attr("title"), headline.absUrl("href"));
			//}
			
			Elements elements = doc.getElementsMatchingText(tag);
			
			logger.info(doc.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String result = new String();
//		StringBuffer sbResult = new StringBuffer();
//		
//		HttpClient client = HttpClientBuilder.create().build();
//		HttpGet request = new HttpGet(url);
//		
//		try {
//			HttpResponse response = client.execute(request);
//
//			System.out.println("Response Code : "
//			                + response.getStatusLine().getStatusCode());
//			
//			HttpEntity resEntity = response.getEntity();
//			
//			if (resEntity != null) 
//				result = EntityUtils.toString(resEntity,"utf-8");
//			
//			System.out.println(result);
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return "parsing test";
	}
	*/
	
	@RequestMapping(value="/request/item/{item_no}", method=RequestMethod.GET)	
		public @ResponseBody Object requestItem(@PathVariable String item_no)	{

		if(item_no == null || item_no.isEmpty())
			item_no = "000660";
		
		String query = "SERVICE_ITEM:" + item_no;
		
		return demoService.requestDetail(query);
		
	}

	@RequestMapping(value="/request/index/{index}", method=RequestMethod.GET)	
	public @ResponseBody Object requestIndexem(@PathVariable String index)	{

	if(index == null || index.isEmpty())
		index = "KOSPI";
	
	String query = "SERVICE_INDEX:" + index.toUpperCase();
	
	return demoService.requestDetail(query);
	
	}
}
