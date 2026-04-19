package com.qa.gorest.tests;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.utils.HTTPCodeStatus;

import io.restassured.response.Response;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.hamcrest.Matchers.*;
import com.qa.gorest.utils.XMLPathValidator;

public class GetTestUser extends BaseTest {
	
	@BeforeMethod
	public void getUserSetUp() {
		rc=new RestClient(prop,baseURI);
		//RestClient.isAuthorizationAdded=false;
	}
	
	/*
	 * Fetch all user with response log
	 */
	@Test(description = "In This is case we will all the user ",priority=4)
	public void getAllUserTest() {
		
		rc.getRequest(GOREST_ENDPOINT,true,true)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPCodeStatus.OK_200.getCode());
	}
	
	/*
	 * Fetch all user without response log
	 */
	@Test(priority=2)
	public void getAllUserTest1() {
	
		rc.getRequest(GOREST_ENDPOINT,false,true)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPCodeStatus.OK_200.getCode())
		.statusLine(HTTPCodeStatus.OK_200.getMessage())
		.body("id",is(not(empty())));
	}
	
	/*
	 * Fetch single user 8423652
	 */
	@Test(priority=3)
	public void getSingleUserTest() {
		
		rc.getRequest(GOREST_ENDPOINT,true,true)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPCodeStatus.OK_200.getCode())
		.statusLine(HTTPCodeStatus.OK_200.getMessage());
	}
	
	/*
	 * Fetch user with query parameter
	 */
	@Test(priority=5, enabled = false)
	public void getUserTestWithQuery() {
		Map<String, String> queryMap=new HashMap<String , String>();
		queryMap.put("gender","male");
		queryMap.put("status","inactive");
		
		rc.getRequest(GOREST_ENDPOINT+8435809,null,queryMap, true,true)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPCodeStatus.OK_200.getCode())
		.statusLine(HTTPCodeStatus.OK_200.getMessage());
	}
	
	/*
	 * Fetch user in xml format 
	 */
	@Test(priority=1)
	public void getUserTestXml() {
		
		Response res=rc.getRequest(GOREST_XML_ENDPOINT,true,true);
		res.prettyPrint();
		int code = res.statusCode();
		Assert.assertEquals(code,HTTPCodeStatus.OK_200.getCode());
		
		
		 XMLPathValidator xml= new XMLPathValidator(); 
         List<String> nameList=xml.readListXml(res,"objects.object.name");
		 System.out.println("Name List "+nameList);
		 
		//Assert.assertTrue(nameList.contains("Miss Sweta Nair"));
	}
	

}
