package com.driverPackage;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterMethod;
import org.testng.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



//import com.DataFileAccess.TestData;
import com.DataFileAccess.ExcelReader;
import com.DataFileAccess.TestData;

import io.appium.java_client.android.AndroidDriver;

public class driverSetUp extends TestData{
	
	String Udata;
	
	DesiredCapabilities cap = new DesiredCapabilities();
	public AndroidDriver driver = null;	
	
	public void loadDriver() throws MalformedURLException{	
      
       cap.setCapability("deviceName", getConfigProperty("DeviceName"));
       this.cap.setCapability("appPackage", getConfigProperty("AppPackage"));
    	this.cap.setCapability("appActivity", getConfigProperty("appActivity"));
    	cap.setCapability("platformVersion", getConfigProperty("PlatformVersion"));
		cap.setCapability("platformName", getConfigProperty("PlatformName"));   
        cap.setCapability("udid", getConfigProperty("DeviceUDID"));		
    	this.cap.setCapability("deviceType", getConfigProperty("DeviceType"));
    	
    	
       driver	=	new AndroidDriver(new URL(getConfigProperty("AppiumServerPort")), this.cap);       
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
	}
	
	public String Fetchdata(String Uname, String TCID){
		System.out.println("Entered into Fetch Method");
		ExcelReader readr=new ExcelReader();
		System.out.println("Excel Object Created");
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		int ColumnIndex = readr.readHeaderIndex(relativePath + "\\AppiumData.xlsx","Data", Uname);
		Udata=readr.readXLatIndex(relativePath + "\\AppiumData.xlsx","Data",TCID,ColumnIndex);
      
		/*int ColumnIndex = readr.readHeaderIndex("D:\\Appium\\Workspace\\appium\\AppiumData.xlsx","Data", Uname);
		Udata=readr.readXLatIndex("D:\\Appium\\Workspace\\appium\\AppiumData.xlsx","Data",TCID,ColumnIndex);*/
		return Udata;	
		
		
	}
	
	@BeforeClass
	public void  preconfig()
	{
	TestData.loadPropertyFiles();	

		}
	@BeforeMethod
	public  void  SetUp(){			
			try {
				loadDriver();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
	

	@AfterMethod
	public void terminate()
	{
		
	}
}
