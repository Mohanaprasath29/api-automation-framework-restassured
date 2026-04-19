package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;

import com.qa.gorest.pojo.*;
import com.qa.gorest.utils.StringUtils;
import com.qa.gorest.utils.ExcelUtils;
import com.qa.gorest.utils.HTTPCodeStatus;

import static org.hamcrest.Matchers.equalTo;

public class PostTestUser extends BaseTest {
//RestClient postRes;
//RestClient getRes;
	
	
	@BeforeMethod
	public void getUserSetUp() {
		rc=new RestClient(prop,baseURI);
		RestClient.isAuthorizationAdded=false;
	}
	
	
	/*
	 * @DataProvider public Object[][] getUser() { Object obj[][]=
	 * {{"male","active"},{"female","active"}}; return obj; }
	 */
	
	@DataProvider
	public Object[][] getTestDataFromExcelSheet() {
		Object obj[][]=ExcelUtils.getTestDataFromSheet("Users");
		return obj;
	}
	
	@Test(dataProvider="getTestDataFromExcelSheet")
	public void createUser(String name,String gender, String status) {
		
		
		//Create 
		
		UserPojo up=new UserPojo(name,StringUtils.generateEmail(),gender,status);
	
		int id =rc.postRequest(GOREST_ENDPOINT,up,"json",true,true)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPCodeStatus.CREATED_201.getCode())
		.extract().path("id");
		System.out.println("Id is "+id);
		
		
		//Fetch 
			
		rc.getRequest(GOREST_ENDPOINT+id, true,true)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPCodeStatus.OK_200.getCode());
		//.body("name",equalTo(up.getName()));
		 
	}
	
}
