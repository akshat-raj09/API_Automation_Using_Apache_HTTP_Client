package com.qa.client;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.qa.base.TestBase;


public class RestClient extends TestBase {
	
	public static HttpGet httpGet;
	public static HttpPost httpPost;
	public static CloseableHttpClient httpClient;
	public static CloseableHttpResponse closebaleHttpResponse;

	// 1. GET Method without Headers:
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException{
		
		httpClient = HttpClients.createDefault();
		httpGet = new HttpGet(url); // HTTP GET request
		closebaleHttpResponse =  httpClient.execute(httpGet); // Hit the GET URL without headers
		
		return closebaleHttpResponse;

	}

	
	// 2. GET Method with Headers:
	public CloseableHttpResponse get(String url, Map<String, String> headerMap) throws ClientProtocolException, IOException{
		
		httpClient = HttpClients.createDefault();
		httpGet = new HttpGet(url); // HTTP GET request

		// Add headers from the HashMap to httpget
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		
		closebaleHttpResponse =  httpClient.execute(httpGet); // Hit the GET URL with Headers
		
		return closebaleHttpResponse;

	}
	

	// 3. POST Method: with Headers & JSON Payload
	public CloseableHttpResponse post(String url, String entityString, Map<String, String> headerMap) throws ClientProtocolException, IOException{
		
		httpClient = HttpClients.createDefault();
		httpPost = new HttpPost(url); // HTTP POST request
		httpPost.setEntity(new StringEntity(entityString)); // for JSON payload

		// for headers:
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}

		closebaleHttpResponse = httpClient.execute(httpPost);
		
		return closebaleHttpResponse;

	}

}