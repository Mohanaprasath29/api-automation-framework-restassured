package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameworkException;

public class ConfigurationManager {
	
private Properties prop;
private FileInputStream fp;

	public Properties iniProperties() {
		 prop=new Properties();
		 
		 
		 //mvn clean install -Denv="Stage"
		 
		 String environmentName=System.getProperty("env");
		 System.out.println("Environment name is :"+environmentName);
		
		try {
			 if(environmentName==null) {
				 System.out.println("Environment name is not passed. so, it is running in the default environment ");
				 fp= new FileInputStream("src\\test\\resources\\config\\config.properties");
			 }
			 else {
				 switch (environmentName.toLowerCase().trim()) {
				case "uat":
					System.out.println("Running in the "+environmentName+" Environment");
					fp= new FileInputStream("src\\test\\resources\\config\\config_uat.properties");
					break;
					
				case "stage":
					System.out.println("Running in the "+environmentName+" Environment");
					fp= new FileInputStream("src\\test\\resources\\config\\config_stage.properties");
					break;
					
				case "prod":
					System.out.println("Running in the "+environmentName+" Environment");
					fp= new FileInputStream("src\\test\\resources\\config\\config_prod.properties");
					break;

				default:
					System.out.println("Incorrect  "+environmentName+" Environment is passed. Please check the Environment");
					throw new APIFrameworkException("Incorrect Environment Name");
				}
			 }
			
			try {
				prop.load(fp);
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
}
