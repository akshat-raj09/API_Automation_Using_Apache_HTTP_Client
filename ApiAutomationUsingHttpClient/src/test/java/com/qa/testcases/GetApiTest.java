package com.qa.testcases;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;


public class GetApiTest extends TestBase {

	static TestBase testBase;
	static String endPointUrl;
	static String apiUrl;
	static String url;
	static RestClient restClient;
	static CloseableHttpResponse closebaleHttpResponse;


	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		
		testBase = new TestBase();
		endPointUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		
		//URI: https://reqres.in/api/users
		url = endPointUrl + apiUrl;

	}



	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException{
		
		restClient = new RestClient();
		
		// pass the url without headers to get()
		closebaleHttpResponse = restClient.get(url);

		// 1. Status Code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code ---> "+ statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// 2. JSON String:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ---> "+ responseJson);

		// single value assertion:
		// Assert value of per_page:
		String perPageValue = TestUtil.getValueByJsonPath(responseJson, "/per_page");
		System.out.println("value of per page is --> "+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		// Assert value of total:
		String totalValue = TestUtil.getValueByJsonPath(responseJson, "/total");
		System.out.println("value of total is --> "+ totalValue);		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		// get the value from JSON ARRAY:
		String lastName = TestUtil.getValueByJsonPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJsonPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJsonPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJsonPath(responseJson, "/data[0]/first_name");
		
		// print the values fetched from JSON ARRAY
		System.out.println("value of lastname is --> "+ lastName);
		System.out.println("value of id is --> "+ id);
		System.out.println("value of avatar is --> "+ avatar);
		System.out.println("value of firstname is --> "+ firstName);


		// 3. All Headers
		Header[] headersArray =  closebaleHttpResponse.getAllHeaders();
		Map<String, String> allHeaders = new HashMap<String, String>();	
		
		// Insert the values of header[] to HashMap
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());
		}	
		
		System.out.println("Headers Array --> " + allHeaders);

	}



	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException{
		
		restClient = new RestClient();

		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		These Headers can also be used:-
//		headerMap.put("username", "test@amazon.com");
//		headerMap.put("password", "test213");
//		headerMap.put("Auth Token", "12345");

		// pass the url with headers to get()
		closebaleHttpResponse = restClient.get(url,headerMap);

		// 1. Status Code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->"+ statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// 2. JSON String:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ---> "+ responseJson);

		// single value assertion:
		// per_page:
		String perPageValue = TestUtil.getValueByJsonPath(responseJson, "/per_page");
		System.out.println("value of per page is --> "+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		// total:
		String totalValue = TestUtil.getValueByJsonPath(responseJson, "/total");
		System.out.println("value of total is --> "+ totalValue);		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		// get the value from JSON ARRAY:
		String lastName = TestUtil.getValueByJsonPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJsonPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJsonPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJsonPath(responseJson, "/data[0]/first_name");

		// print the values fetched from JSON ARRAY
		System.out.println("value of lastname is --> "+ lastName);
		System.out.println("value of id is --> "+ id);
		System.out.println("value of avatar is --> "+ avatar);
		System.out.println("value of firstname is --> "+ firstName);


		// 3. All Headers
		Header[] headersArray =  closebaleHttpResponse.getAllHeaders();
		Map<String, String> allHeaders = new HashMap<String, String>();	
		
		// Insert the values of header[] to HashMap
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());
		}	
		
		System.out.println("Headers Array --> " + allHeaders);

	}

}