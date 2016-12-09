package com.scenarios;

import org.testng.annotations.Test;

public class Scenario2_BenificiaryValidation extends businessComponentsHDFC{


	/* Scenario to validate the List of Beneficiaries and their names*/
	@Test
	public void verifyBenificiaryList() throws InterruptedException{
		verifyHomeScreen();
		login("TC02");	
		tapFundTransfer();
		tapViewListOfBeneficiaries();
		selectBeneficiaryType("BeneficiaryType", "TC02");
		verifyBeneficiaryDetails("BeneficiaryAccounts", "BeneficiaryName", "TC02");	
	}


}
