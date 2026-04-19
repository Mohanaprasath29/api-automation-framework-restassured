package com.qa.gorest.utils;

import io.restassured.response.Response;

import java.util.List;

import com.jayway.jsonpath.JsonPath;


public class JsonPathUtils {
	
	public String response;
	
	private String getJsonResponseAsString(Response res) {
		String response= res.getBody().asString();
		return response;
	}

	
	public <T> T read(Response res ,String path) {
		String response =getJsonResponseAsString(res);
		return JsonPath.read(response,path);
	}
	
	public <T> List<T>  readList(Response res ,String path) {
		String response =getJsonResponseAsString(res);
		return JsonPath.read(response,path);
	}
}
