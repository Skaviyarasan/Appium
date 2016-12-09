package com.scenarios;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.driverPackage.driverSetUp;

public class businessComponentsHDFC extends driverSetUp{

	public void verifyHomeScreen(){
		if(driver.findElement(By.xpath(getObject("home_ScreenHeader"))).isDisplayed()){	
			System.out.println("Home screen Validated");
		}

	}
	public void verifyMyMenuScreen(){
		if(driver.findElement(By.xpath("//*[@content-desc='My Menu']")).isDisplayed()){
			System.out.println("My Menu screen Validated");
		}
	}
	public void login(String TCID) throws InterruptedException{
		driver.findElement(By.xpath(getObject("home_customerID"))).click();		
		String usenamr= Fetchdata("Username", TCID);
		String password= Fetchdata("Password", TCID);
		System.out.println(usenamr + password);
		driver.findElement(By.xpath(getObject("login_UserNameField"))).sendKeys(usenamr);
		driver.findElement(By.xpath(getObject("login_ContinueBtn"))).click();		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath(getObject("login_PasswordField"))).sendKeys(password);
		driver.findElement(By.xpath(getObject("login_ConfirmCheckBox"))).click();
		driver.findElement(By.xpath(getObject("login_LoginBtn"))).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	}
	public void tapBackButton() throws InterruptedException{
		driver.findElement(By.xpath(getObject("backButton"))).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void tapAccountSummary() throws InterruptedException{
		driver.findElement(By.xpath(getObject("accountSummaryLabel"))).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

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
	public void tapFundTransfer(){
		driver.findElement(By.xpath(getObject("menu_FundTransfer"))).click();
		System.out.println("Fund Transfer Clicked");
	}
	
	public void tapViewListOfBeneficiaries(){		
		driver.swipe(540, 1536, 540, 384, 3000);
		driver.findElement(By.xpath("transfer_viewLstofBeneficiary")).click();
	}
	
	public void selectBeneficiaryType(String BType, String TCID) throws InterruptedException{
		String BeneficiaryType = Fetchdata(BType, TCID);
		driver.findElement(By.xpath("list_BeneficiaryType")).click();
		driver.findElement(By.xpath("//*[@text='" + BeneficiaryType +"']")).click();
		driver.findElement(By.xpath("viewBeneficiaryBtn")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
}
