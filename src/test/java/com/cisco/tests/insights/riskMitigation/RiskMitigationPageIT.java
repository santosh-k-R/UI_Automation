package com.cisco.tests.insights.riskMitigation;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.riskMitigation.RiskMitigationPage;

import com.cisco.pages.sp2k.PreventiveMaintenancePage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.cisco.testdata.Data.RISK_MITIGATION_HOME_DATA;
import static com.cisco.testdata.Data.RISK_MITIGATION_TRACK_DATA;
import static org.testng.Assert.*;
import static com.cisco.testdata.StaticData.DNAC_DropdownValue.*;

public class RiskMitigationPageIT extends DriverBase {

	String filterValue;
	int rowsCount;
	int pagiNationCount;
	String systemNameFromFP;
	List<String> systemsColumnSortData;
	String riskFromFP;
	String actualMessage;
	boolean dataCheckInGrid;
	private String useCase = RISK_MITIGATION_HOME_DATA.get("USE_CASE");
	private String defaultTabName = RISK_MITIGATION_TRACK_DATA.get("DEFAULT_TAB_NAME");
	public String noResultsFound = "No Results Found";

	@BeforeClass(description = "Verify Login to SP2K")
	public void verifyLoginToSP2K() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.login();
		//assertEquals("M & W Data Entry", riskMitigationPage.homePageTitle());
	}

	@Test(description = "Verify PreventiveMaintenance Page Link", priority = 1)
	public void clickPreventiveMaintenancePageLink() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.PreventiveMaintenanceLink(), "Preventive Maintenance Page Link is Available");
	}
	@Test(description = "verify PreventiveMaintenance Logout",priority = 2)
	public void clickLogout(){
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.logout();
	}
	/*@BeforeClass(description = "Verify Login to Cx-Portal")
	public void verifyLoginToCxPortal() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.login();
		//riskMitigationPage.resetContextSelector();
	}

	@Test(description = "Verify MyPortfolio Tab-Tm5585393c", priority = 2)
	public void verifyMyPortfolio() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.myPortfolioTab(), "My Portfolio Tab is Not Displayed");
	}

	@Test(description = "Verify Insights Tab Tab-Tm5585395c,Tm5585460c,Tm5585396c,Tm5585397c,Tm5585398c,Tm5585399c", priority = 3)
	public void verifyInsightsTab() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus");
		boolean iconEnabled = riskMitigationPage.useCaseAddIconOption();
		riskMitigationPage.clickOnInsights();
		assertTrue(iconEnabled, "Usecase Select Icon is Not Present or Enabled");
	}

	@Test(description = "Verify the Default Software Tab selected after Click On INSIGHTS Tab-Tm5585654c", priority = 4)
	public void verifyDefaultSoftwareTab() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.isSoftwareTabSelected(defaultTabName));
	}

	@Test(description = "Verify & Navigate to Crash Risk Tab-Tm5585453c,Tm5585656c,Tm5585458c", priority = 5)
	public void verifyCrashRiskTab() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is Not Enabled");
		assertTrue(riskMitigationPage.isAssetsWithCrashRiskDisplayed(), "Assets With Crash Risk Tab is Not Displayed");
		assertTrue(riskMitigationPage.isCrashedAssetsDisplayed(), "Assets With Crash Risk Tab is Not Displayed");
	}

	@Test(description = "Verify the Default Selected Systems and Score Card Count-Tm5585660c,Tm5585461c", priority = 6)
	public void verifyAssetsWithCrashRiskScoreCardCount() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int assetsScoreCardCount = riskMitigationPage.systemsScoreCardCount();
		int insightsCount = Integer.parseInt(riskMitigationPage.crashRiskInsightCount());
		assertNotNull(assetsScoreCardCount, "Assets with Crash Risk Score Card count is Null");
		assertEquals(insightsCount, assetsScoreCardCount, "Insights count and Crash Risk Count is not matched");
	}

	@Test(description = "verify Donut Chart with all Risk filters-Tm5585452c,Tm5585454c,Tm5585664c", priority = 7)
	public void verifyDonutChartFilter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int count = riskMitigationPage.donutChartFilterCount();
		assertEquals(count, 2, "High and Medium Filters are not Present in Assets with Crash Risk Donut Chart");

	}

	@Test(description = "Verify the High Risk Donuts Filter-Tm5585490c,Tm5585511c,Tm5585568c,Tm5585585c", priority = 8)
	public void verifyTheHighRiskFilter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.riskFilter(RiskMitigationPage.RISK_HIGH);
			assertTrue(riskMitigationPage.checkFilterApplied(), "High Filter is Not Applied");
			assertTrue(riskMitigationPage.filteredRiskFilter(), "Filtered is Not Applied");
			assertEquals(
					"Showing 1-" + riskMitigationPage.riskDonutFilterDataCount(RiskMitigationPage.RISK_HIGH) + " of "
							+ riskMitigationPage.riskDonutFilterDataCount(RiskMitigationPage.RISK_HIGH)
							+ " assets with High Risk",
					riskMitigationPage.assetsPageIterationLabel(), "High Filter Label is Not Correct");
		} else {
			System.out.println("No Assets with Crash Risk to Report");
		}

	}

	@Test(description = "Verify the Medium Risk Donuts Filter-Tm5585516c,Tm5585554c,Tm5585560c,Tm5585568c", priority = 9)
	public void verifyTheMediumRiskFilter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.riskFilter(RiskMitigationPage.RISK_MEDIUM);
			assertTrue(riskMitigationPage.checkFilterApplied(), "Medium Filter is Not Applied");
			assertTrue(riskMitigationPage.filteredRiskFilter(), "Filtered is Not Applied");
			assertEquals(
					"Showing 1-" + riskMitigationPage.riskDonutFilterDataCount(RiskMitigationPage.RISK_MEDIUM) + " of "
							+ riskMitigationPage.riskDonutFilterDataCount(RiskMitigationPage.RISK_MEDIUM) + " assets",
					riskMitigationPage.assetsPageIterationLabel(), "Medium Filter Label is Not Correct");
		} else {
			System.out.println("No Assets with Crash Risk to Report");
		}

	}

	@Test(description = "Verify the Clear All Link-Tm5585462c,Tm5585472c,Tm5585527c", priority = 10)
	public void verifyClearAllLink() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		boolean booleanValue = riskMitigationPage.clearAllFilter();
		assertFalse(booleanValue, "Filter is not removed");
	}

	@Test(description = "HCR Search Field Display-Tm5585470c,Tm5585525c", priority = 11)
	public void verifySearchDisplay() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.searchIcon(), "Search Icon Is Not Available");

	}

	@Test(description = "Verify the Search Filter When No Filters Applied on Systems Grid-Tm5585469c,Tm5585687c", priority = 12)
	public void searchCriteriaALL() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.searchDNACField(ALL_CATEGORIES, "os");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(description = "Verify the search field for full string on Systems Grid-Tm5585532c,Tm5585577c", priority = 13)
	public void searchCriteriaALLWithFullString() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.searchDNACField(ALL_CATEGORIES, "IOS-XE");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(description = "Verify the Search Filter When No Filters Applied on Systems Grid-Tm5585692c,Tm5585693c", priority = 14)
	public void searchCriteriaDNAC() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.searchDNACField(DNA_CENTER_APPLIANCES, "10.127.102.216");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(description = "Verify the Page Nation Count", priority = 15)
	public void pagiNation() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		pagiNationCount = riskMitigationPage.pagiNationCount();
		System.out.println("Pagination Count is------------------->" + pagiNationCount);
	}

	@Test(description = "Verify the Systems Columns Names-Tm5585456c,Tm5585317c", priority = 16)
	public void verifySystemColumns() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		List<String> systemsActualColumnNames = riskMitigationPage.getAllTableTitles();
		List<String> systemsExpectedColumnNames = new ArrayList<String>();
		systemsExpectedColumnNames.add("Asset Name");
		systemsExpectedColumnNames.add("Product ID");
		systemsExpectedColumnNames.add("Software Type");
		systemsExpectedColumnNames.add("Software Release");
		systemsExpectedColumnNames.add("Risk");
		assertEquals(systemsActualColumnNames, systemsExpectedColumnNames);
	}

	@Test(description = "Verify the Asset Name Columns Sorting-Tm5585478c", priority = 17)
	public void verifyAssetNameColumnsSorting() {
		systemsColumnSortData = new ArrayList<>();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int rowCount = riskMitigationPage.getTableRowCount();
		riskMitigationPage.sortTable("Asset Name");
		for (int row = 1; row <= rowCount; row++) {
			String list = riskMitigationPage.getTableCellValue(row, 1);
			systemsColumnSortData.add(list);
		}
	}

	@Test(description = "Verify the Product ID Columns Sorting-Tm5585533c", priority = 18)
	public void verifyProductIDColumnSorting() {
		List<String> productIDColumnSortData = new ArrayList<>();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int rowCount = riskMitigationPage.getTableRowCount();
		riskMitigationPage.sortTable("Product ID");
		for (int row = 1; row <= rowCount; row++) {
			String list = riskMitigationPage.getTableCellValue(row, 2);
			productIDColumnSortData.add(list);
		}
	}

	@Test(description = "Verify the Software Type Columns Sorting-Tm5585534c", priority = 19)
	public void verifySoftwareTypeColumnSorting() {
		List<String> softwareTypeColumnSortData = new ArrayList<>();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int rowCount = riskMitigationPage.getTableRowCount();
		riskMitigationPage.sortTable("Software Type");
		for (int row = 1; row <= rowCount; row++) {
			String list = riskMitigationPage.getTableCellValue(row, 3);
			softwareTypeColumnSortData.add(list);
		}
	}

	@Test(description = "Verify the Software Release Columns Sorting-Tm5585478c", priority = 20)
	public void verifySoftwareReleaseColumnSorting() {
		List<String> softwareReleaseColumnSortData = new ArrayList<>();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int rowCount = riskMitigationPage.getTableRowCount();
		riskMitigationPage.sortTable("Software Release");
		for (int row = 1; row <= rowCount; row++) {
			String list = riskMitigationPage.getTableCellValue(row, 4);
			softwareReleaseColumnSortData.add(list);
		}
	}

	@Test(description = "Verify the Risk Columns Sorting-Tm5585503c", priority = 21)
	public void verifyRiskColumnSorting() {
		List<String> riskColumnSortData = new ArrayList<>();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int rowCount = riskMitigationPage.getTableRowCount();
		riskMitigationPage.riskColumnSort("Risk");
		for (int row = 1; row <= rowCount; row++) {
			String list = riskMitigationPage.getTableCellValue(row, 5);
			riskColumnSortData.add(list);
		}
	}

	@Test(description = "HCR_Mouse Hover over Risk for High device in Finger Print Page-Tm5585486c", priority = 22)
	public void verifyFPHighRiskTooltip() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertEquals(riskMitigationPage.FPRiskRankTooltip(),
				"High or Medium indicates the level of risk,calculated by Cisco's machine learning (ML), that a device is prone to crash during adverse network conditions. Review Optimal Software Versions, Risk Mitigation Checks, and run available scans and analysis to find device hardening options.");
	}

	@Test(description = "Verify the Fingerprint Details when High Filter is Applied-Tm5585464c,Tm5585466c,Tm5585557c,Tm5585515c,Tm5585482c", priority = 23)
	public void drillDownHighFingerprint() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.riskFilter(RiskMitigationPage.RISK_HIGH);
		riskMitigationPage.isDataPresentInFPGrid(RiskMitigationPage.RISK_HIGH);
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
		assertEquals(RiskMitigationPage.SYSTEMNAME, RiskMitigationPage.SYSTEMNAMEFROMFP);
	}

	@Test(description = "HCR_FP-Remove fingerprint intelligence and ML-Tm5585483c,Tm5585467c", priority = 24)
	public void verifyHighSimilarDevicesLabels() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertEquals(riskMitigationPage.fingerPrint360LabelNames(RiskMitigationPage.RISK_HIGH),
				riskMitigationPage.fingerPrintLabels(RiskMitigationPage.RISK_HIGH));
		assertEquals(riskMitigationPage.fingerprintSimilarAssets, "SIMILAR ASSETS",
				"Finger Print Similar Assets is Not Matched");
	}

	@Test(description = "Verify the similarity Device Details when High Filter is Applied-Tm5585463c,Tm5585483c,Tm5585485c,Tm5585489c,Tm5585484c,Tm5585492c,Tm5585480c,Tm5585500c", priority = 25)
	public void verifyHighSimilarityDevices() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.getSimilarDevicesForHigh(RiskMitigationPage.RISK_HIGH), "No Similar Devices");
		assertEquals(riskMitigationPage.softwareHighAssetName, riskMitigationPage.fpHighAssetName);
	}

	@Test(description = "HCR_Similar Systems Medium Compare toggle screen criteria", priority = 26)
	public void verifySimilarityCompareButtonsForHigh() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		List<String> actualButtonNames = new ArrayList<>();
		actualButtonNames.add("SOFTWARE");
		actualButtonNames.add("FEATURES");
		List<String> expectedButtonNames = riskMitigationPage.compareTabNamesHigh(RiskMitigationPage.RISK_HIGH);
		assertEquals(actualButtonNames, expectedButtonNames, "Compare Buttons Not Matching");
		assertEquals(riskMitigationPage.fingerPrintSimilarAsset, "Medium", "Medium Similar Asset is Not Matching");
	}

	@Test(description = "Fingerprint Details page Navigation to Asset 360 view page-Tm5585465c,Tm5585495c,Tm5585513c", priority = 27)
	public void navigateToCrashRiskAsset360PageForHigh() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.riskFilter(RiskMitigationPage.RISK_HIGH);
		assertEquals(riskMitigationPage.crashRiskAssetName, riskMitigationPage.assets360DeviceName,
				"Assets Name is Not Matching");
	}

	@Test(description = "Tool tip on Risk showing the definition of Medium risk. -Tm5585561c", priority = 28)
	public void verifyFPMediumRiskTooltip() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.riskFilter(RiskMitigationPage.RISK_MEDIUM);
		assertEquals(riskMitigationPage.FPRiskRankTooltip(),
				"High or Medium indicates the level of risk,calculated by Cisco's machine learning (ML), that a device is prone to crash during adverse network conditions. Review Optimal Software Versions, Risk Mitigation Checks, and run available scans and analysis to find device hardening options.");
	}

	@Test(description = "Verify the Fingerprint Details when Medium Filter is Applied-Tm5585481c,Tm5585550c,Tm5585518c,Tm5585536c", priority = 29)
	public void drillDownMediumFingerprint() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.isDataPresentInFPGrid(RiskMitigationPage.RISK_MEDIUM);
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
		assertEquals(RiskMitigationPage.SYSTEMNAME, RiskMitigationPage.SYSTEMNAMEFROMFP);
	}

	@Test(description = "Verify Fingerprint Labels for Medium-Tm5585521c", priority = 30)
	public void verifyMediumSimilarDevicesLabels() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertEquals(riskMitigationPage.fingerPrint360LabelNames(RiskMitigationPage.RISK_MEDIUM),
				riskMitigationPage.fingerPrintLabels(RiskMitigationPage.RISK_MEDIUM));
		assertEquals(riskMitigationPage.fingerprintSimilarAssets, "SIMILAR ASSETS",
				"Finger Print Similar Assets is Not Matched");
	}

	@Test(description = "Verify the similarity Device Details when Medium Filter is Applied-Tm5585517c,Tm5585522c,Tm5585544c,Tm5585497c,Tm5585547c,Tm5585545c,Tm5585564c", priority = 31)
	public void verifyMediumSimilarityDevices() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.getSimilarDevicesForMedium(RiskMitigationPage.RISK_MEDIUM), "No Similar Devices");
		assertEquals(riskMitigationPage.softwareMediumAssetName, riskMitigationPage.fpMediumAssetName,
				"Similar Devices Assets name is not Matching");
	}

	@Test(description = "HCR_Similar Systems Medium Compare toggle screen criteria-Tm5585542c,Tm5585537c,Tm5585535c", priority = 32)
	public void verifySimilarityCompareButtonsForMedium() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		List<String> actualButtonNames = new ArrayList<>();
		actualButtonNames.add("SOFTWARE");
		actualButtonNames.add("FEATURES");
		List<String> expectedButtonNames = riskMitigationPage.compareTabNamesMedium(RiskMitigationPage.RISK_MEDIUM);
		assertEquals(actualButtonNames, expectedButtonNames, "Compare Buttons Not Matching");
		assertEquals(riskMitigationPage.fingerPrintSimilarAsset, "Low", "Medium Similar Asset is Not Matching");
	}

	@Test(description = "Fingerprint Details page Navigation to Asset 360 view page-Tm5585520c,Tm5585562c", priority = 33)
	public void navigateToCrashRiskAsset360PageForMedium() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertEquals(riskMitigationPage.openAssetsWithCrash360View(RiskMitigationPage.RISK_MEDIUM), "Connected");
		assertEquals(riskMitigationPage.crashRiskAssetName, riskMitigationPage.assets360DeviceName,
				"Assets Name is Not Matching");
	}

	@Test(description = "Select and close  Medium risk Filter-Tm5585553c", priority = 34)
	public void verifyCloseMediumRisk() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.checkFilterApplied(), "Filter is Not Applied");
		riskMitigationPage.closeRiskFilter();
		assertFalse(riskMitigationPage.checkFilterApplied(), "Filter is Applied");
	}

	@Test(description = "Verify and click on the Crashed System Dashboard Count-Tm5585440c,Tm5585566c,Tm5585567c,Tm5585569c,Tm5585586c", priority = 35)
	public void clickOnCrashedAssetsTab() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.crashedSystemCount();
		String defaultFilterValue = riskMitigationPage.crashedSystemsDefaultFilterValue();
		assertTrue(defaultFilterValue.equalsIgnoreCase("24h"), "Default filter value is: 24h");
		assertTrue(riskMitigationPage.filteredRiskFilter(), "Filtered is Not Applied");
	}

	@Test(description = "Crashed Systems grid Search field-Tm5585676c,Tm5585594c", priority = 36)
	public void verifyCrashedAssetsSearchDisplay() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.searchIcon(), "Search Icon Is Not Available");

	}

	@Test(description = "Verify Time Range filter value is 90d-Tm5585627c", priority = 37)
	public void clickOn90Filter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("90d");
		actualMessage = riskMitigationPage.pageIterationMessage();
		filterValue = riskMitigationPage.getFilterValue("90d");
		assertTrue(filterValue.equalsIgnoreCase("90d"), "Time Range filter value is: ");
	}

	@Test(description = "Verify page iteration for Crashed Systems for 90d-", priority = 38)
	public void verifyPageIterationMessage90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int count = riskMitigationPage.getToolTipValue("90d");
	}

	@Test(description = "Verify Crashed Systems grid column Names-Tm5585581c", priority = 39)
	public void verifyCrashedAssetsGridColumnNames() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		List<String> actualColumnNames = riskMitigationPage.getAllTableTitles();
		List<String> expectedColumnNames = new ArrayList<String>();
		expectedColumnNames.add("Asset Name");
		expectedColumnNames.add("Product ID");
		expectedColumnNames.add("Software Type");
		expectedColumnNames.add("Software Release");
		expectedColumnNames.add("First Crash");
		expectedColumnNames.add("Last Crash");
		assertEquals(actualColumnNames, expectedColumnNames);
	}

	@Test(description = "Verify the Crashed  Assets Asset Name Column Sorting-Tm5585571c,Tm5585596c,Tm5585617c", priority = 40)
	public void verifyCrashedAssetNameColumnsSorting() {
		List<String> AssetNameColumnSortData = new ArrayList<>();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int rowCount = riskMitigationPage.getTableRowCount();
		riskMitigationPage.sortTable("Asset Name");
		for (int row = 1; row <= rowCount; row++) {
			String list = riskMitigationPage.getTableCellValue(row, 1);
			AssetNameColumnSortData.add(list);
		}
	}

	@Test(description = "Verify the Crashed  First Crash Name Column Sorting-Tm5585572c,Tm5585597c", priority = 41)
	public void verifyCrashedFirstCrashColumnsSorting() {
		List<String> AssetNameColumnSortData = new ArrayList<>();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int rowCount = riskMitigationPage.getTableRowCount();
		riskMitigationPage.sortTable("First Crash");
		for (int row = 1; row <= rowCount; row++) {
			String list = riskMitigationPage.getTableCellValue(row, 1);
			AssetNameColumnSortData.add(list);
		}
	}

	@Test(description = "Verify no of devices to crash count by clearing only search field in 90d time period-Tm5585602c,Tm5585622c,Tm5585677c,Tm5585680c", priority = 42)
	public void crashedSystemsAllSearchField90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.searchDNACField(ALL_CATEGORIES, "C9300-24T");
		riskMitigationPage.closeSearchIcon();
		assertTrue(filterValue.equalsIgnoreCase("90d"), "Time Range filter value is: ");
	}

	@Test(description = "Crashed Assets search field with different columns-Tm5585574c,Tm5585605c,Tm5585619c", priority = 43)
	public void crashedSystemsAllSearchOtherColumns() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.searchDNACField(ALL_CATEGORIES, "16.9.3");
		riskMitigationPage.closeSearchIcon();
		assertTrue(filterValue.equalsIgnoreCase("90d"), "Time Range filter value is: ");
	}

	@Test(description = "Verify Crashed Assets  search field for Case insensitive-Tm5585576c,Tm5585686c,Tm5585621c,Tm5585623c", priority = 44)
	public void crashedSystemsAllSearchCaseInSensitive() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.searchDNACField(ALL_CATEGORIES, "16.9");
		riskMitigationPage.closeSearchIcon();
		assertTrue(filterValue.equalsIgnoreCase("90d"), "Time Range filter value is: ");
	}

	@Test(description = "Verify no of devices to crash count by Dnac search field in 90d time period-Tm5585599c,Tm5585675c,Tm5585684c", priority = 45)
	public void crashedSystemsDnacSearchField90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.searchDNACField(DNA_CENTER_APPLIANCES, "10.127.102.216");
		riskMitigationPage.closeSearchIcon();
		assertTrue(filterValue.equalsIgnoreCase("90d"), "Time Range filter value is: ");
	}

	@Test(description = "Verify Crash History System name in 90d-Tm5585592c,Tm5585633c,Tm5585589c", priority = 46)
	public void verifyCrashHistoryPageSystemName90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("90d");
		riskMitigationPage.openCrashHistoryPage(1, 1);
		String expectedSystemName = riskMitigationPage.getTableCellValue(1, 1);
		riskMitigationPage.toggleFullScreen();
		String actualSystemName = riskMitigationPage.getCrashHistorySystemName();
		assertTrue(actualSystemName.equalsIgnoreCase(expectedSystemName),
				"System Name is not matching from Crash History page to Grid: ");
	}

	@Test(description = "Verify Crash History page labels in 90d-Tm5585631c", priority = 47)
	public void verifyCrashHistoryPageLabels90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		List<String> actualLabelNames = riskMitigationPage.getAllLabelNamesCrashHistoryPage();
		assertEquals(actualLabelNames, riskMitigationPage.crashHistoryLabels());
	}

	@Test(description = "Close Crash History Tab and verify it landed to Crashed Systems grid in 90d-Tm5585609c", priority = 48)
	public void closeCrashHistoryTab90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.close360View();
	}

	@Test(description = "RMC_ crash history page  Asset 360-Tm5585447c,Tm5585593c,Tm5585613c,Tm5585630c,Tm5585634c", priority = 49)
	public void navigateToCrashedAsset360PageFor90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.clearAllFilter();
		riskMitigationPage.timeRangeFilter("90d");
		riskMitigationPage.openCrashHistoryPage(1, 4);
		assertEquals(riskMitigationPage.openCrashedAssets360View(), "Connected", "Device Not Connected");
	}

	@Test(description = "Verify Time Range filter value is 30d-Tm5585608c", priority = 50)
	public void clickOn30dFilter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("30d");
		filterValue = riskMitigationPage.getFilterValue("30d");
		assertTrue(filterValue.equalsIgnoreCase("30d"), "Time Range filter value is: ");
	}

	@Test(description = "Verify page iteration for Crashed Systems for 30d -Tm5585606c", priority = 51)
	public void verifyPageIterationMessage30d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("30d");
		int thirtyDayCount = riskMitigationPage.getToolTipValue("30d");
		String actualmessage = riskMitigationPage.pageIterationMessage();
		String expectedMessage = riskMitigationPage.isPageIterationAvailable(thirtyDayCount);
		assertTrue(actualmessage.equalsIgnoreCase(expectedMessage), "Verify page iteration must be displayed as: ");
	}

	@Test(description = "Verify Crash History System name in 30d", priority = 52)
	public void verifyCrashHistoryPageSystemName30d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			String expectedSystemName = riskMitigationPage.getTableCellValue(1, 1);
			riskMitigationPage.openCrashHistoryPage(1, 1);
			riskMitigationPage.toggleFullScreen();
			String actualSystemName = riskMitigationPage.getCrashHistorySystemName();
			riskMitigationPage.close360View();
			assertTrue(actualSystemName.equalsIgnoreCase(expectedSystemName),
					"System Name is not matching from Crash History page to Grid: ");
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Verify Crash History page labels in 30d-Tm5585610c,Tm5585590c", priority = 53)
	public void verifyCrashHistoryPageLabels30d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.openCrashHistoryPage(1, 1);
			List<String> actualLabelNames = riskMitigationPage.getAllLabelNamesCrashHistoryPage();
			assertEquals(actualLabelNames, riskMitigationPage.crashHistoryLabels());
			riskMitigationPage.close360View();
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Navigation to Asset 360 view page 30d-Tm5585612c", priority = 54)
	public void navigateToCrashedAsset360PageFor30d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.clearAllFilter();
			riskMitigationPage.timeRangeFilter("30d");
			riskMitigationPage.openCrashHistoryPage(1, 4);
			assertEquals(riskMitigationPage.openCrashedAssets360View(), "Connected", "Device Not Connected");
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Verify Time Range filter value is 7d - Tm5585565c", priority = 55)
	public void clickOn7dFilter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("7d");
		filterValue = riskMitigationPage.getFilterValue("7d");
		assertTrue(filterValue.equalsIgnoreCase("7d"), "Time Range filter value is: ");
	}

	@Test(description = "Crashed Assets  fields with different columns with sorting-7d-Tm5585430c", priority = 56)
	public void verifyCrashedAssetsDifferentColumnSorting7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		List<String> AssetNameColumnSortData = new ArrayList<>();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			int rowCount = riskMitigationPage.getTableRowCount();
			riskMitigationPage.sortTable("First Crash");
			for (int row = 1; row <= rowCount; row++) {
				String list = riskMitigationPage.getTableCellValue(row, 1);
				AssetNameColumnSortData.add(list);
			}
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Verify page iteration for Crashed Systems for 7d-Tm5585439c", priority = 57)
	public void verifyPageIterationMessage7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("7d");
		int sevenDaycount = riskMitigationPage.getToolTipValue("7d");
		String actualmessage = riskMitigationPage.pageIterationMessage();
		String expectedMessage = riskMitigationPage.isPageIterationAvailable(sevenDaycount);
		assertTrue(actualmessage.equalsIgnoreCase(expectedMessage), "Verify page iteration must be displayed as: ");
	}

	@Test(description = "Verify Crash History System name in 7d-Tm5585443c,Tm5585446c,Tm5585448c", priority = 58)
	public void verifyCrashHistoryPageSystemName7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.openCrashHistoryPage(1, 1);
			String expectedSystemName = riskMitigationPage.getTableCellValue(1, 1);
			riskMitigationPage.toggleFullScreen();
			String actualSystemName = riskMitigationPage.getCrashHistorySystemName();
			assertTrue(actualSystemName.equalsIgnoreCase(expectedSystemName),
					"System Name is not matching from Crash History page to Grid: ");
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Verify Crash History page labels in 7d-Tm5585444c", priority = 59)
	public void verifyCrashHistoryPageLabels7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			List<String> actualLabelNames = riskMitigationPage.getAllLabelNamesCrashHistoryPage();
			assertEquals(actualLabelNames, riskMitigationPage.crashHistoryLabels());
			riskMitigationPage.close360View();
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Verify Clear Search Field 7d Time Range-Tm5585432c,Tm5585431c", priority = 60)
	public void verifyClearSearchField7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.searchDNACField(ALL_CATEGORIES, "16.9");
			riskMitigationPage.closeSearchIcon();
			assertTrue(filterValue.equalsIgnoreCase("7d"), "Time Range filter value is: ");
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Verify Time Range filter value is 24h-Tm5585587c", priority = 61)
	public void verifyFilterValue1d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("24h");
		filterValue = riskMitigationPage.getFilterValue("24h");
		assertTrue(filterValue.equalsIgnoreCase("24h"), "Time Range filter value is: ");
		assertTrue(riskMitigationPage.filteredRiskFilter(), "Filtered is Not Applied");
	}

	@Test(description = "Verify page iteration for Crashed Systems for 24h-Tm5585629c", priority = 62)
	public void verifyPageIterationMessage1d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		int oneDaycount = riskMitigationPage.getToolTipValue("24h");
		String expectedMessage = riskMitigationPage.isPageIterationAvailable(oneDaycount);
		String actualmessage = riskMitigationPage.pageIterationMessage();
		assertTrue(actualmessage.equalsIgnoreCase(expectedMessage), "Verify page iteration must be displayed as: ");

	}

	@Test(description = "Verify Crashed Assets  Clear All Filter Link-Tm5585573c,Tm5585598c,Tm5585618c", priority = 63)
	public void clickOnClearAllFilter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.clearAllFilter();
		String defaultFilterValue = riskMitigationPage.crashedSystemsDefaultFilterValue();
		assertTrue(defaultFilterValue.equalsIgnoreCase("24h"), "Default filter value is: 24h");
	}

	@Test(description = "Change Use Case selection and select Apply option-Tm5585402c", priority = 64)
	public void selectUseCase() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Scalable Access Policy");
		boolean insights = riskMitigationPage.verifyInsightsButton();
		assertTrue(insights, "Insights Tab is not visible");
	}

	@Test(description = "Verify and Check for Insights section in CX portal when Success Track is Closed-Tm5585394c,Tm5585406c,Tm5585401c,Tm5585403c", priority = 65)
	public void checkInsightsWhenCloseSuccessTrack() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.insightsTabWhenClosedSuccessTrackButton();
		boolean insights = riskMitigationPage.verifyInsightsButton();
		assertFalse(insights, "Insights Tab is visible");
	}*/

}
