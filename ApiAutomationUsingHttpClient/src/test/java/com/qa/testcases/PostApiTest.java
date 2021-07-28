package com.qa.testcases;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;


public class PostApiTest extends TestBase {

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
		
		// https://reqres.in/api/users
		url = endPointUrl + apiUrl;

	}


	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException{
		
		restClient = new RestClient();
		
		// HashMap for headers
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");

		// jackson API: used to convert Java object to JSON object (Marshalling) & convert JSON object back to Java object (Un-Marshalling).
		ObjectMapper mapper = new ObjectMapper();
		
		// passing value to Users class constructor
		Users users = new Users("morpheus", "leader");

		// converting Java object to JSON file using jackson API:
		String jsonFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\users.json";
		mapper.writeValue(new File(jsonFilePath), users);

		// converting Java object to JSON String jackson API:
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println("Value of Users class object converted as JSON String is : " + usersJsonString);

		// get the response from HTTP POST method call
		closebaleHttpResponse = restClient.post(url, usersJsonString, headerMap);

		// validate the response of POST method from API:
		// 1. status code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code of Response is : " + statusCode + " (" + closebaleHttpResponse.getStatusLine().getReasonPhrase() + ")");
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);

		// 2. JSON String:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		// convert the JSON Response String to JSON Object using JSON Library
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is : "+ responseJson);

		// converting JSON Response String to Java object using jackson API:
		Users usersResponseObject = mapper.readValue(responseString, Users.class);

		// compare the value passed to users class constructor to that of users object received value from JSON Response
		Assert.assertTrue(users.getName().equals(usersResponseObject.getName()));
		Assert.assertTrue(users.getJob().equals(usersResponseObject.getJob()));

		System.out.println("The ID received from Response is : " + usersResponseObject.getId());
		System.out.println("The Created At value received from Response is : " + usersResponseObject.getCreatedAt());

	}
	
	
	@AfterMethod
	public void tearDown() throws IOException {
		
		// Closes this stream and releases any system resources associated with it.
		closebaleHttpResponse.close();
		RestClient.closebaleHttpResponse.close();
		
		// Resets internal state of the request making it reusable.
		RestClient.httpPost.reset();
		
	}

}
