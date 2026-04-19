package com.qa.gorest.utils;

public class StringUtils {
	
	//Generate Random Name 
	public static String generateName() {
		String name="User"+Math.random();
		return name;
	}
	
	//Generate Random Email 
		public static  String generateEmail() {
			String email="User"+Math.random()+"@gmail.com";
			return email;
		}

}
