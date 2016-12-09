package com.scenarios;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.driverPackage.ElementAction;
import com.driverPackage.driverSetUp;

public class businessComponentsHDFC extends driverSetUp{
	ElementAction action;
	public businessComponentsHDFC(){
		action = new ElementAction();
	}

	/* Method to verify the Home Screen Header */

	public void verifyHomeScreen(){
		if(driver.findElement(By.xpath(getObject("home_ScreenHeader"))).isDisplayed()){	
			System.out.println("Home screen Validated");
		}

	}

	/* Method to verify the My Menu Screen after the Login */

	public void verifyMyMenuScreen(){
		if(driver.findElement(By.xpath("//*[@content-desc='My Menu']")).isDisplayed()){
			System.out.println("My Menu screen Validated");
		}
	}

	/* Method to login the App using Customer ID */

	public void login(String TCID) throws InterruptedException{
		driver.findElement(By.xpath(getObject("home_customerID"))).click();		
		String usenamr= Fetchdata("Username", TCID);
		String password= Fetchdata("Password", TCID);
		System.out.println(usenamr + password);
		driver.findElement(By.xpath(getObject("login_UserNameField"))).sendKeys(usenamr);
		driver.findElement(By.xpath(getObject("login_ContinueBtn"))).click();
		System.out.println("Login Clicked");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//action.waitForElementPresence(getObject("login_PasswordField"));
		driver.findElement(By.xpath(getObject("login_PasswordField"))).sendKeys(password);
		driver.findElement(By.xpath(getObject("login_ConfirmCheckBox"))).click();
		driver.findElement(By.xpath(getObject("login_LoginBtn"))).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	}

	/* Common method to tap the Back Button */

	public void tapBackButton() throws InterruptedException{
		driver.findElement(By.xpath(getObject("backButton"))).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	/*
	 * Method to tap the Account Summary Option */

	public void tapAccountSummary() throws InterruptedException{
		driver.findElement(By.xpath(getObject("accountSummaryLabel"))).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	/* Method to verify the Account Balance in the Account Summary Dashboard Screen*/

	public void verifyAccountBalance(String Account,String Balance, String TCID) throws InterruptedException{
		String accnum = Fetchdata(Account, TCID);
		System.out.println("Account Number : " + accnum);		
		String accbal = Fetchdata(Balance, TCID);
		System.out.println("Account Balance : " + accbal);
		if(driver.findElement(By.xpath("//*[@content-desc='" + accnum + "']")).isDisplayed()){
			try{
				driver.findElement(By.xpath("//*[@content-desc='" + accbal +"']"));
				System.out.println("Account Balance Validated, Verified Balance : " + accbal);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/* Method to tap the Fund Transfer Option */

	public void tapFundTransfer(){
		driver.findElement(By.xpath(getObject("menu_FundTransfer"))).click();
		System.out.println("Fund Transfer Clicked");
	}

	/* Method to tap the View Lost Of Beneficiary Button */

	public void tapViewListOfBeneficiaries(){		
		driver.swipe(540, 1536, 540, 384, 3000);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("transfer_viewLstofBeneficiary")).click();
	}

	/* Method to select the Beneficiary Type*/

	public void selectBeneficiaryType(String BType, String TCID) throws InterruptedException{
		String BeneficiaryType = Fetchdata(BType, TCID);
		driver.findElement(By.xpath("list_BeneficiaryType")).click();
		driver.findElement(By.xpath("//*[@text='" + BeneficiaryType +"']")).click();
		driver.findElement(By.xpath("viewBeneficiaryBtn")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	/* Method to Validate the Benificiary Acount Name and Number */

	public void verifyBeneficiaryDetails(String AccountNumber, String AccountName,String TCID ){		

		String[] accountNumber=Fetchdata(AccountNumber, TCID).split(",");
		String[] accountName = Fetchdata(AccountName, TCID).split("'");		

		for (int i=0;i<=accountNumber.length;i++){
			String xPathAccountName ="//android.view.View[@content-desc='" +accountNumber[i]+"' and @index='5']/following-sibling::[@index='7']";

			String valueofAccountname=driver.findElement(By.xpath(xPathAccountName)).getText();
			for(int j=0; j<=accountName.length;j++){
				if (i==j){
					if(accountName[j].equals(valueofAccountname)){
						System.out.println("Benificiary Account Number and Name is dispalyed Properly");
					}else{
						System.out.println("Benificiary Account Number and Name displayed wrongly");
					}
				}

			}

		}

	}
}
