package com.cisco.pages.assetsAndCoverage.contracts;

import com.cisco.pages.assetsAndCoverage.assets.AllAssetsPage;
import com.cisco.testdata.StaticData.CarouselName;

import io.qameta.allure.Step;

public class ContractsPage extends AllAssetsPage {

	@Step("Click on Contracts Tab")
	public String clickOnContracts() {
		selectCarousel(CarouselName.ASSETS_COVERAGE);
		System.out.println("enabled: " + getWebElement(CONTRACTS_TAB).isEnabled());
		getWebElement(CONTRACTS_TAB).click();
		System.out.println("contracts text: " + getWebElement(CONTRACTS_TAB).getText());
		return getWebElement(CONTRACTS_TAB).getText();
	}

}
