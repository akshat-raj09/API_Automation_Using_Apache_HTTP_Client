package com.qa.base;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class TestBase {

	public static final int RESPONSE_STATUS_CODE_200 = 200;		// OK
	public static final int RESPONSE_STATUS_CODE_201 = 201;		// Created
	public static final int RESPONSE_STATUS_CODE_202 = 202;		// Accepted
	
	public static final int RESPONSE_STATUS_CODE_301 = 301;		// Moved Permanently
	
	
	public static final int RESPONSE_STATUS_CODE_400 = 400;		// Bad Request
	public static final int RESPONSE_STATUS_CODE_401 = 401;		// Unauthorized
	public static final int RESPONSE_STATUS_CODE_403 = 403;		// Forbidden
	public static final int RESPONSE_STATUS_CODE_404 = 404;		// Not Found
	
	public static final int RESPONSE_STATUS_CODE_500 = 500;		// Internal Server Error
	public static final int RESPONSE_STATUS_CODE_502 = 502;		// Bad Gateway
	public static final int RESPONSE_STATUS_CODE_503 = 503;		// Service Unavailable
	

	public static Properties prop;

	public TestBase(){
		
		try {
			prop = new Properties();
			String configFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\config\\config.properties";
			FileInputStream in = new FileInputStream(configFilePath);
			prop.load(in);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
