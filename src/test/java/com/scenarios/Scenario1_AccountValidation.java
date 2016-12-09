package com.scenarios;
import org.testng.annotations.Test;

public class Scenario1_AccountValidation  extends businessComponentsHDFC {


	/* Test Case to validate the Account Balance in Account Summary Dashboard */

	@Test
	public void AccountSummaryValidation() throws InterruptedException{
		verifyHomeScreen();
		login("TC01");		
		tapAccountSummary();		
		tapBackButton();		
		verifyAccountBalance("SavingsAccNum","SavingsBalance","TC01");
	}
}	




