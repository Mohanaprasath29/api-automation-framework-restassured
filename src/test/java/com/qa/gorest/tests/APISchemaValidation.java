package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.pojo.UserPojo;
import com.qa.gorest.utils.HTTPCodeStatus;
import com.qa.gorest.utils.StringUtils;

public class APISchemaValidation extends BaseTest{
	
	@BeforeMethod
	public void getUserSetUp() {	
		rc=new RestClient(prop,baseURI);
		//RestClient.isAuthorizationAdded=false;
	}

	@Test
	public void validate_for_schema() {
		UserPojo up= new UserPojo(StringUtils.generateName(),StringUtils.generateEmail(),"male","active");
		rc.postRequest(GOREST_ENDPOINT,up,"json",true,true)
//		res.prettyPrint();
		.then().log().all()	
		.assertThat()
		.statusCode(HTTPCodeStatus.CREATED_201.getCode())
		.body(matchesJsonSchemaInClasspath("CreateUserSchema.json"));// important method for validation of schema 
		
	}
	
}
