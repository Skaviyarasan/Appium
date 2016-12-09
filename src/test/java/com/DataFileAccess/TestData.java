package com.DataFileAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestData {

	static Properties pConfig=new Properties();
	static Properties pOR=new Properties();
	
		
	public static void loadPropertyFiles()
	{
		try
		{
			//pData.load(new FileInputstream(Data.properties));
			File fs = new File("Configuration.properties");
			System.out.println("Config Path = " + fs.getAbsolutePath());
			pConfig.load(new FileInputStream("D:\\Appium\\Workspace\\appium\\src\\test\\java\\com\\PropertyFile\\Configuration.properties"));
			pOR.load(new FileInputStream("D:\\Appium\\Workspace\\appium\\src\\test\\java\\com\\PropertyFile\\ObjectRepo.properties"));
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static  String getConfigProperty(String property)
	{
		return pConfig.getProperty(property);
	}
	
	public static String getObject(String property)
	
	{
		return pOR.getProperty(property);
	}
}
