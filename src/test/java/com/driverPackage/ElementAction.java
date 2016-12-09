package com.driverPackage;

import org.openqa.selenium.Dimension;

import io.appium.java_client.android.AndroidDriver;

public class ElementAction extends driverSetUp {
	
	public void swipeHorizontal() throws InterruptedException{				
			
			 Dimension size = driver.manage().window().getSize();
			  System.out.println(size);			
			  int startx = (int) (size.width * 0.70);			
			  int endx = (int) (size.width * 0.30);			  
			  int starty = size.height / 2;
			  System.out.println("startx = " + startx + " ,endx = " + endx + " , starty = " + starty);			
			  driver.swipe(startx, starty, endx, starty, 3000);
			  Thread.sleep(2000);		
			  driver.swipe(endx, starty, startx, starty, 3000);
			  Thread.sleep(2000);
			 }
	}


