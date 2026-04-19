package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;

public class AmadeusAPITest extends BaseTest {
	public String token;
	
    @Parameters({"grantType","clientId","clientSecret"})
	@BeforeMethod
	public void getAccessTokenSetUp(String grantType,String clientId, String clientSecret ) {
		rc=new RestClient(prop, baseURI);
	    token=rc.getAccessTokenUsingOAuth2(AMADEUS_TOKEN_ENDPOINT, grantType,clientId,clientSecret);
		
	}
    
    @Test
    public void getDetailsOfFight() {
    	RestClient rec=new RestClient(prop, baseURI);
    	
    	Map<String,String> queryMap=new HashMap<String,String>();
    	queryMap.put("airlineCode","IB");
    	
    	Map<String,String> headersMap=new HashMap<String,String>();
    	headersMap.put("Authorization","Bearer "+token);
    	
    	rec.getRequest(AMADEUS_FLIGHT_ENDPOINT, headersMap,queryMap,true,false)
    	.then().log().all()
    	.statusCode(200);
    	
    	
    }
}
