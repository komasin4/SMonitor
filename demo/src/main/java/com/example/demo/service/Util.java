package com.example.demo.service;

import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	
	public static String postURL (String url, List<NameValuePair> params, Header[] headers)	{
		
		String responseBody = null;
		
	    try {
	    	HttpClient httpClient = HttpClientBuilder.create().build();
	    	//HttpGet req = new HttpGet(url);
	    	HttpPost req = new HttpPost(url);
	    	
	    	URI uri = null;
	    	
	    	if(params != null && params.size() > 0)
	    		uri = new URIBuilder(req.getURI()).addParameters(params).build();
	    	else
	    		uri = new URIBuilder(req.getURI()).build();
	    		
    		logger.info("uri:" + uri.toString());
    		req.setURI(uri);

	    	if(headers != null && headers.length > 0)
	    		req.setHeaders(headers);
	    	
	    	HttpResponse res = httpClient.execute(req);
	    	
	    	int responseCode = res.getStatusLine().getStatusCode();
	    	
	    	logger.info("responseCode:" + responseCode);
	    	
    		responseBody = EntityUtils.toString(res.getEntity());
    		logger.info("responseBody:" + responseBody);
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return responseBody;
	}
}
