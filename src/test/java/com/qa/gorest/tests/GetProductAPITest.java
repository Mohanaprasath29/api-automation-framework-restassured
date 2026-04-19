package com.qa.gorest.tests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.utils.HTTPCodeStatus;
import com.qa.gorest.utils.JsonPathUtils;

import java.util.List;

import org.testng.Assert;

import io.restassured.response.Response;
public class GetProductAPITest extends BaseTest{

	
	@BeforeMethod
	public void getProductSetUp() {
		rc=new RestClient(prop,baseURI);
		RestClient.isAuthorizationAdded=false;
	}
	
	
	@Test
	public void productInfoTest() {
		Response res=rc.getRequest(FAKESTORE_PRODUCT_ENDPOINT,true,false);
		int statusCode=res.statusCode();
		Assert.assertEquals(statusCode,HTTPCodeStatus.OK_200.getCode());
		
		JsonPathUtils js= new JsonPathUtils();
            List<Integer> intList= js.readList(res,"$..id");
		
		for(Integer p:intList) {
			System.out.println(p);
		}
	}
	
}
