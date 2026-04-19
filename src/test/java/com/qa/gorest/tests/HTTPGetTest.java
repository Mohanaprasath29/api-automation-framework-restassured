package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.utils.HTTPCodeStatus;

public class HTTPGetTest extends BaseTest {

	@BeforeMethod
	public void getHttpSetUp() {
		rc=new RestClient(prop,baseURI);
	}
	
	@Test
	public void getUser() {
		
		rc.getRequest(HTTPBIN_ENDPOINT,true,false)
		.then().log().all()
		.assertThat()
		.statusCode(HTTPCodeStatus.OK_200.getCode());
	}
}
