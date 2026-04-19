package com.qa.gorest.utils;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XMLPathValidator {
public String response;
	
	private String getJsonResponseAsString(Response res) {
		String response= res.getBody().asString();
		return response;
	}

	
	public <T> T readXml(Response res ,String path) {
		String response =getJsonResponseAsString(res);
		XmlPath xmlPath= new XmlPath(response);
		return xmlPath.get(path);
	}
	
	public <T> List<T>  readListXml(Response res ,String path) {
		String response =getJsonResponseAsString(res);
		XmlPath xmlPath= new XmlPath(response);
		return xmlPath.getList(path);
	}
}
