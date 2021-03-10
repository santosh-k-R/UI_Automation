package com.cisco.tests.assetsAndCoverage.assets;

import com.cisco.base.DriverBase;
import com.cisco.pages.assetsAndCoverage.contracts.ContractsPage;
import com.cisco.testdata.AssetsDataProvider;
import com.cisco.testdata.AssetsExcelDataReader;
import com.cisco.testdata.AssetsUIPojo;
import com.cisco.utils.Commons;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.cisco.testdata.Data.*;

public class ContractsIT extends DriverBase {
	private String cxCloudAccount = ASSETS_AND_DIAGNOSTICS_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
	private String cxCloudPartyId = ASSETS_AND_DIAGNOSTICS_COMMON_DATA.get("CX_CLOUD_ACCOUNT_ID");
	private AssetsUIPojo contractData = AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA)
			.get("ASSETS_CONTRACTS");

	@Test(description = "All Assets login Test")
	public void verifyCXLogin() {
		ContractsPage contractsPage = new ContractsPage();
		contractsPage.login("assets").switchCXCloudAccount(cxCloudAccount, cxCloudPartyId);
		// contractsPage.login("assets").switchCXCloudAccount(cxCloudAccount);
		Assert.assertEquals(contractsPage.getAssetsTitle(), "ASSETS & COVERAGE",
				"Incorrect Carousel naming convention");
	}

	@Test(description = "Click Contracts Tab", dependsOnMethods = { "verifyCXLogin" })
	public void navigateToAllAssetsContracts() {
		SoftAssert softAssert = new SoftAssert();
		ContractsPage contractsPage = new ContractsPage();
		softAssert.assertEquals(contractsPage.clickOnContracts(), "Contracts", "Contracts tab is not selected");
		softAssert.assertAll();
	}

	@Test(description = "Verify Contracts count matches expected count", dependsOnMethods = {
			"navigateToAllAssetsContracts" }, groups = "pre-condition checks")
	public void verifyContractCount() {
		ContractsPage contractsPage = new ContractsPage();
		Assert.assertEquals(contractsPage.getAssetCount(), Integer.parseInt(contractData.getSystemCount()),
				"No contract data on this account");
	}

	@Test(description = "Validate visual filters titles", dependsOnMethods = {
			"navigateToAllAssetsContracts" }, groups = "visual filter title checks", dataProvider = "visualFiltersAllContracts", dataProviderClass = AssetsDataProvider.class)
	public void verifyVisualFiltersTitle(String visualFilterTitle) {
		ContractsPage contractsPage = new ContractsPage();
		Assert.assertEquals(contractsPage.getVisualFilterTitle(visualFilterTitle), visualFilterTitle,
				"Mismatch in visual filter title");
	}

	@Test(description = "Validate label in visual filters", dependsOnMethods = {
			"navigateToAllAssetsContracts" }, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
	public void verifyLabelInVisualFilters__STATUS(String filterLabel) {
		AssetsUIPojo visualFilterObj = AssetsExcelDataReader
				.visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA).get(filterLabel);
		ContractsPage contractsPage = new ContractsPage();
		SoftAssert softAssert = new SoftAssert();
		String actualFilterLabel = contractsPage
				.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
		softAssert.assertEquals(actualFilterLabel, filterLabel, "Filter title not Matched/Selected");
		contractsPage.clearAllFilter();
		softAssert.assertAll();
	}

	@Test(description = "Validate count in visual filters", dependsOnMethods = {
			"verifyLabelInVisualFilters__STATUS" }, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
	public void verifyVisualFilterCount__STATUS(String filterLabel) {
		AssetsUIPojo visualFilterObj = AssetsExcelDataReader
				.visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA).get(filterLabel);
		ContractsPage contractsPage = new ContractsPage();
		SoftAssert softAssert = new SoftAssert();
		contractsPage.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
		softAssert.assertEquals(contractsPage.getAssetCount(),
				Integer.parseInt(visualFilterObj.getAllAssetFilterCount()), "Mismatch found in status data");
		contractsPage.clearAllFilter();
		softAssert.assertAll();
	}

	@Test(description = "Validate label in visual filters", dependsOnMethods = {
			"navigateToAllAssetsContracts" }, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
	public void verifyLabelInVisualFilters__TYPE(String filterLabel) {
		AssetsUIPojo visualFilterObj = AssetsExcelDataReader
				.visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA).get(filterLabel);
		ContractsPage contractsPage = new ContractsPage();
		SoftAssert softAssert = new SoftAssert();
		String actualFilterLabel = contractsPage
				.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
		softAssert.assertEquals(actualFilterLabel, filterLabel, "Filter title not Matched/Selected");
		contractsPage.clearAllFilter();
		softAssert.assertAll();
	}

	@Test(description = "Validate count in visual filters", dependsOnMethods = {
			"verifyLabelInVisualFilters__TYPE" }, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
	public void verifyVisualFilterCount__TYPE(String filterLabel) {
		AssetsUIPojo visualFilterObj = AssetsExcelDataReader
				.visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA).get(filterLabel);
		ContractsPage contractsPage = new ContractsPage();
		SoftAssert softAssert = new SoftAssert();
		contractsPage.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
		softAssert.assertEquals(contractsPage.getAssetCount(),
				Integer.parseInt(visualFilterObj.getAllAssetFilterCount()), "Mismatch found in type data");
		contractsPage.clearAllFilter();
		softAssert.assertAll();
	}

	@Test(description = "Validate label in visual filters", dependsOnMethods = {
			"navigateToAllAssetsContracts" }, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
	public void verifyLabelInVisualFilters__EXPIRY(String filterLabel) {
		AssetsUIPojo visualFilterObj = AssetsExcelDataReader
				.visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA).get(filterLabel);
		ContractsPage contractsPage = new ContractsPage();
		SoftAssert softAssert = new SoftAssert();
		String actualFilterLabel = contractsPage
				.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
		softAssert.assertEquals(actualFilterLabel, filterLabel, "Filter title not Matched/Selected");
		contractsPage.clearAllFilter();
		softAssert.assertAll();
	}

	@Test(description = "Validate count in visual filters", dependsOnMethods = {
			"verifyLabelInVisualFilters__EXPIRY" }, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
	public void verifyVisualFilterCount__EXPIRY(String filterLabel) {
		AssetsUIPojo visualFilterObj = AssetsExcelDataReader
				.visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA).get(filterLabel);
		ContractsPage contractsPage = new ContractsPage();
		SoftAssert softAssert = new SoftAssert();
		contractsPage.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
		softAssert.assertEquals(contractsPage.getAssetCount(),
				Integer.parseInt(visualFilterObj.getAllAssetFilterCount()), "Mismatch found in expiry data");
		contractsPage.clearAllFilter();
		softAssert.assertAll();
	}

}
