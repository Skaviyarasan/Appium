package com.scenarios;

import org.testng.annotations.Test;

public class Scenario2_BenificiaryValidation extends businessComponentsHDFC{
	
@Test
public void verifyBenificiaryList() throws InterruptedException{
	verifyHomeScreen();
	login("TC02");
	tapFundTransfer();
	tapViewListOfBeneficiaries();
	selectBeneficiaryType("BeneficiaryType", "TC02");
	
	
}


}
