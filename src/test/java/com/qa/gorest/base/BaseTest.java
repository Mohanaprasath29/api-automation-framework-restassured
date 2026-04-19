package com.qa.gorest.base;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;
import io.qameta.allure.restassured.AllureRestAssured;

import io.restassured.RestAssured;

public class BaseTest {
	
	//Keep the all End points details
	public static final String GOREST_ENDPOINT="/public/v2/users/";
	public static final String HTTPBIN_ENDPOINT="/get";
	public static final String AMADEUS_TOKEN_ENDPOINT="/v1/security/oauth2/token";
	public static final String AMADEUS_FLIGHT_ENDPOINT="/v2/reference-data/urls/checkin-links";
	public static final String FAKESTORE_PRODUCT_ENDPOINT="/products";
	public static final String GOREST_XML_ENDPOINT="/public/v2/users.xml";
	
	
	
	public  ConfigurationManager cm;
	public Properties prop;
	public  RestClient rc;
	public String baseURI;
	
	@Parameters({"baseURI"})
	@BeforeTest
	public void setUp(String baseUri) {
	
		RestAssured.filters(new AllureRestAssured());
		
		 cm= new ConfigurationManager();
		
		 prop =cm.iniProperties();
			this.baseURI=baseUri;
		
		//String baseUri=prop.getProperty("baseURI");
		
		 //rc=new RestClient(prop,baseUri);
		
	}

}
