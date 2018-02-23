package com.example.demo.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;

import com.example.demo.configuration.ConstantsConfiguration;

@Service
public class LineService {

	public String sendLineMessage(String message)	{
		String responseBody = null;
		
		try	{
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Header[] headers = {new BasicHeader("Authorization","Bearer " + ConstantsConfiguration.LINE_TOKEN)};
			
			responseBody = Util.postURL(String.format(ConstantsConfiguration.LINE_NOTIFY_URL, URLEncoder.encode(message, "UTF-8")), params, headers);
			
		} catch (Exception e)	{
			e.printStackTrace();
			return ("error");
		}
		
//		String url = String.format(ConstantsConfiguration.AUTH_URL, ConstantsConfiguration.CLIENT_ID, ConstantsConfiguration.REDIRECT_URL);
//		return "redirect:" + url;

//		logger.info("jsptest");
		return responseBody;
	}
	
}
