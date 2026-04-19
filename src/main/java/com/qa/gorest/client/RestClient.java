package com.qa.gorest.client;

import java.util.Map;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import com.qa.gorest.frameworkexception.APIFrameworkException;
import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class RestClient {
	
//	private static final String BaseUri="https://gorest.co.in";
//	private static final String Token="2876fbfebee93cef11d19ccfb659d45183c045a2cd076c9df72d0441a2adbb27";
	
	private static  RequestSpecBuilder specBuilder;
	
	private static  Properties prop;
	 
	private static  String baseUri;
	
	
	public static  Boolean isAuthorizationAdded = false;
	
//	//initialize the object of RequestSpecBuilder class
//	static {
//		specBuilder=new RequestSpecBuilder();
//	}
	
	
	//Constructor
	public RestClient(Properties prop, String baseUri) {
		specBuilder=new RequestSpecBuilder();
		this.prop=prop;
		this.baseUri=baseUri;
		
	}
	
	//*********************common Reusable Method *************************************

	//Base URI
	public static void addBaseUri() {
		specBuilder.setBaseUri(baseUri);
	}
	
	//Authorization 
	public static void addAuthorization() {
		if(!isAuthorizationAdded) {
		specBuilder.addHeader("Authorization","Bearer "+prop.getProperty("tokenId"));
	     isAuthorizationAdded = true;
		}
	}
	
	//Getting ContentType
	public void setRequestContentType(String contentType) {
		System.out.println("Content Type is :"+contentType);
		switch (contentType.toLowerCase().trim()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;

		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
			
		case "html":
			specBuilder.setContentType(ContentType.HTML);
			break;
			
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;
			
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		default:
			throw new APIFrameworkException("**********invalid content type****************");
		}
	}
	
	
	
	//*******************Request Specification Methods ********************************
	/**
	 * Setting up the BaseUri and Authorization 
	 * @return
	 */
	public RequestSpecification createRequestSpec(Boolean includeAuth) {
	    addBaseUri();
	    if(includeAuth) {
		addAuthorization();
	    }
		return specBuilder.build();	
	}
	
	/**
	 * Setting up the BaseUri and Authorization  along with Header Map
	 * @return
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap,Boolean includeAuth) {
	    addBaseUri();
	    if(includeAuth) {
		addAuthorization();
	    }
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();	
	}
	
	
	/**
	 * Setting up the BaseUri and Authorization  along with Header Map , Query Map
	 * @return
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryMap,Boolean includeAuth) {
	    addBaseUri();
	    if(includeAuth) {
		addAuthorization();
	    }
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryMap!=null) {
			specBuilder.addHeaders(queryMap);
		}
		return specBuilder.build();	
	}
	
	
	/**
	 * Setting up the BaseUri and Authorization , requestBody and contenttype for POST call
	 * @return
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType,Boolean includeAuth) {
	    addBaseUri();
	    if(includeAuth) {
		addAuthorization();
	    }
		setRequestContentType(contentType);
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		
		return specBuilder.build();	
	}
	
	
	/**
	 * Setting up the BaseUri and Authorization , requestBody and contenttype with header map for POST call
	 * @return
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType,Map<String, String> headersMap,Boolean includeAuth ) {
	    addBaseUri();
	    if(includeAuth) {
			addAuthorization();
		    }
		setRequestContentType(contentType);
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		
		return specBuilder.build();	
	}
	
	/**
	 * Setting up the BaseUri and Authorization , requestBody and contenttype map for POST call
	 * Along with header and query parameter 
	 * @return
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType,Map<String, String> headersMap ,Map<String, String> queryMap,Boolean includeAuth) {
	    addBaseUri(); 
	    if(includeAuth) {
			addAuthorization();
		    }
		setRequestContentType(contentType);
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryMap!=null) {
			specBuilder.addHeaders(queryMap);
		}
		
		return specBuilder.build();	
	}
	
	
	//****************************HTTP Get Request*************************************
	
	
	/*
	 * use this method for GET call passing endpoint as serviceurl and Boolean value 
	 */
	public Response getRequest(String serviceUrl, Boolean log,Boolean includeAuth) {
		
		if (log) {
			Response res= RestAssured.given().log().all()
					.spec(createRequestSpec(includeAuth))
					.when()
					.get(serviceUrl);
					return res;
		}
		else {
			Response res= RestAssured.given()
					.spec(createRequestSpec(includeAuth))
					.when()
					.get(serviceUrl);
					return res;
		}
		
	}
	
	/*
	 * use this method for GET call passing endpoint as serviceurl and Boolean value 
	 * using the headersmap
	 */
	public Response getRequest(String serviceUrl,Map<String, String> headersMap, Boolean log,Boolean includeAuth) {
		
		if (log) {
			Response res= RestAssured.given().log().all()
					.spec(createRequestSpec(headersMap,includeAuth))
					.when()
					.get(serviceUrl);
					return res;
		}
		else {
			Response res= RestAssured.given()
					.spec(createRequestSpec(headersMap,includeAuth))
					.when()
					.get(serviceUrl);
					return res;
		}
		
	}
	
	/*
	 * use this method for GET call passing endpoint as serviceurl and Boolean value 
	 * using the headersmap and querymap 
	 */
	
public Response getRequest(String serviceUrl,Map<String, String> headersMap,Map<String, String> queryMap, Boolean log,Boolean includeAuth) {
		
		if (log) {
			Response res= RestAssured.given().log().all()
					.spec(createRequestSpec(headersMap,queryMap,includeAuth))
					.when()
					.get(serviceUrl);
					return res;
		}
		else {
			Response res= RestAssured.given()
					.spec(createRequestSpec(headersMap,queryMap,includeAuth))
					.when()
					.get(serviceUrl);
					return res;
		}
		
	}

//********************************HTTP Post Method**************************************************


/*
 * use this method for POST call passing endpoint as serviceurl and Boolean value 
 * passing the request body and content type 
 */
public Response postRequest(String serviceUrl,Object requestBody, String contentType, Boolean log,Boolean includeAuth) {
	
	if (log) {
		Response res= RestAssured.given().log().all()
				.spec(createRequestSpec(requestBody,contentType,includeAuth))
				.when()
				.post(serviceUrl);
				return res;
	}
	else {
		Response res= RestAssured.given()
				.spec(createRequestSpec(requestBody,contentType,includeAuth))
				.when()
				.post(serviceUrl);
				return res;
	}
}
	
	
	/*
	 * use this method for POST call passing endpoint as serviceurl and Boolean value 
	 * passing the request body and content type 
	 */
	public Response postRequest(String serviceUrl,Object requestBody, String contentType,Map<String, String> headersMap, Boolean log,Boolean includeAuth) {
		
		if (log) {
			Response res= RestAssured.given().log().all()
					.spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
					.when()
					.post(serviceUrl);
					return res;
		}
		else {
			Response res= RestAssured.given()
					.spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
					.when()
					.post(serviceUrl);
					return res;
		}
	}
	
	
	/*
	 * use this method for POST call passing endpoint as serviceurl and Boolean value 
	 * passing the request body and content type 
	 */
	public Response postRequest(String serviceUrl,Object requestBody, String contentType,Map<String, String> headersMap,Map<String, String> queryMap, Boolean log,Boolean includeAuth) {
		
		if (log) {
			Response res= RestAssured.given().log().all()
					.spec(createRequestSpec(requestBody,contentType,headersMap,queryMap,includeAuth))
					.when()
					.post(serviceUrl);
					return res;
		}
		else {
			Response res= RestAssured.given()
					.spec(createRequestSpec(requestBody,contentType,headersMap,queryMap,includeAuth))
					.when()
					.post(serviceUrl);
					return res;
		}
	}
	
	
	//****************************************HTTP Put Method *****************************************************
	
	/*
	 * use this method for PUT call passing endpoint as serviceurl and Boolean value 
	 * passing the request body and content type 
	 */
	public Response putRequest(String serviceUrl,Object requestBody, String contentType, Boolean log,Boolean includeAuth) {
		
		if (log) {
			Response res= RestAssured.given().log().all()
					.spec(createRequestSpec(requestBody,contentType,includeAuth))
					.when()
					.put(serviceUrl);
					return res;
		}
		else {
			Response res= RestAssured.given()
					.spec(createRequestSpec(requestBody,contentType,includeAuth))
					.when()
					.put(serviceUrl);
					return res;
		}
	}
		
		
		/*
		 * use this method for PUT call passing endpoint as serviceurl and Boolean value 
		 * passing the request body and content type 
		 */
		public Response putRequest(String serviceUrl,Object requestBody, String contentType,Map<String, String> headersMap, Boolean log,Boolean includeAuth) {
			
			if (log) {
				Response res= RestAssured.given().log().all()
						.spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
						.when()
						.put(serviceUrl);
						return res;
			}
			else {
				Response res= RestAssured.given()
						.spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
						.when()
						.put(serviceUrl);
						return res;
			}
		}
		
		
		/*
		 * use this method for PUT call passing endpoint as serviceurl and Boolean value 
		 * passing the request body and content type 
		 */
		public Response putRequest(String serviceUrl,Object requestBody, String contentType,Map<String, String> headersMap,Map<String, String> queryMap, Boolean log,Boolean includeAuth) {
			
			if (log) {
				Response res= RestAssured.given().log().all()
						.spec(createRequestSpec(requestBody,contentType,headersMap,queryMap,includeAuth))
						.when()
						.put(serviceUrl);
						return res;
			}
			else {
				Response res= RestAssured.given()
						.spec(createRequestSpec(requestBody,contentType,headersMap,queryMap,includeAuth))
						.when()
						.put(serviceUrl);
						return res;
			}
		}
		
		
		
		//*************************************HTTP Delete Method ********************************************************
		
		/*
		 * Use this method for the DELETE call 
		 */
		public Response deleteRequest(String serviceUrl,Boolean log,Boolean includeAuth) {
			if (log) {
				Response res= RestAssured.given().log().all()
						.spec(createRequestSpec(includeAuth))
						.when()
						.delete(serviceUrl);
						return res;
			}
			else {
				Response res= RestAssured.given()
						.spec(createRequestSpec(includeAuth))
						.when()
						.delete(serviceUrl);
						return res;
			}
			
		}
		
		
		//**********************************OAuth 2.0 ************************************************
		
		
		/*
		 * Parameter using the testng xml file 
		 */
		
		public String getAccessTokenUsingOAuth2(String serviceUrl , String grantType, String clientId, String clientSecret) {
			
			RestAssured.baseURI = "https://test.api.amadeus.com";

	        // Post call for fetching the token
	        String oAuthToken =
	                RestAssured
	                        .given()
	                        .log().all()
	                        .contentType(ContentType.URLENC)
	                        .formParam("grant_type", grantType)
	                        .formParam("client_id", clientId)
	                        .formParam("client_secret", clientSecret)
	                        .when()
	                        .post(serviceUrl)
	                        .then()
	                        .log().all()
	                        .assertThat()
	                        .statusCode(200)
	                        .extract()
	                        .path("access_token");

	        System.out.println("OAuth 2.0 Token is : " + oAuthToken);
	        return oAuthToken;
		}

}
