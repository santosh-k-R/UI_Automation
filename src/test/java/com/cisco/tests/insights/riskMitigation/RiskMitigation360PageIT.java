package com.cisco.tests.insights.riskMitigation;

import static com.cisco.testdata.StaticData.DNAC_DropdownValue.ALL_CATEGORIES;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.riskMitigation.RiskMitigation360Page;
import com.cisco.pages.insights.riskMitigation.RiskMitigationPage;

/**
 * @author roodesai
 *
 */
public class RiskMitigation360PageIT extends DriverBase

{
	@BeforeClass(groups = { "Severity1" }, description = "Verify Login to Cx-Portal and Click on Use Case")
	public void verifyInsights() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.login();
		riskMitigationPage.resetContextSelector();
		riskMitigationPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus");
		riskMitigationPage.clickOnInsights();
	}

	@Test(groups = {
			"Severity1" }, description = "Verify the Default Software Tab selected after Click On INSIGHTS Tab", priority = 2)
	public void verifyDefaultSoftwareTab() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.isSoftwareTabSelected("Software"), "Software Tab is not present");
	}

	@Test(groups = { "Severity1" }, description = "Verify & Navigate to Risk Mitigation Tab", priority = 3)
	public void verifyCrashRiskTab() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Software Image Management");
	}

	@Test(groups = {
			"Severity2" }, description = "Verify & Navigate to Assets with Crash Risk ExportCSVrisk-Tm5585293c,Tm5585292c", priority = 4)
	public void exportCSVRisk() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is enabled");
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.isResetButtonEnabled("ResetButton");
	}

	@Test(groups = {
			"Severity2" }, description = "Click on  Assets with Crash Risk Table column selector icon-Tm5585321c", priority = 5)
	public void clickSelectColumn() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.selectColumns("Product Family");
		riskMitigationPage1.selectColumns("Serial Number");
		riskMitigationPage1.selectColumns("IP Address");
		riskMitigationPage1.isApplyButtonEnabled("ApplyButton");
	}

	@Test(groups = {
			"Severity2" }, description = "Verify the  Assets with Crash Risk ALL Columns Names-Tm5585322c,Tm5585327c", priority = 6)
	public void verifySystemAllColumns() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		List<String> systemsActualColumnNames = riskMitigationPage1.getAllTableTitles();
		List<String> systemsExpectedColumnNames = new ArrayList<String>();
		systemsExpectedColumnNames.add("Asset Name");
		systemsExpectedColumnNames.add("Product ID");
		systemsExpectedColumnNames.add("Software Type");
		systemsExpectedColumnNames.add("Software Release");
		systemsExpectedColumnNames.add("Risk");
		systemsExpectedColumnNames.add("Product Family");
		systemsExpectedColumnNames.add("Serial Number");
		systemsExpectedColumnNames.add("IP Address");
		assertEquals(systemsActualColumnNames, systemsExpectedColumnNames);
	}

	@Test(groups = {
			"Severity2" }, description = "Verify the  Assets with Crash Risk Mandatory and Default Columns Names-Tm5585323c", priority = 7)
	public void verifySystemColumn() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.isResetButtonEnabled("ResetButton");
		List<String> systemsActualColumnNames = riskMitigationPage1.getAllTableTitles();
		List<String> systemsExpectedColumnNames = new ArrayList<String>();
		systemsExpectedColumnNames.add("Asset Name");
		systemsExpectedColumnNames.add("Product ID");
		systemsExpectedColumnNames.add("Software Type");
		systemsExpectedColumnNames.add("Software Release");
		systemsExpectedColumnNames.add("Risk");
		assertEquals(systemsActualColumnNames, systemsExpectedColumnNames);
	}

	@Test(groups = {
			"Severity2" }, description = "Assests grid Search field for different columns mgmtSystemHostname or mgmtSystemAddr-Tm5585688c,Tm5585689c", priority = 8)
	public void mgmtSystemHostnameSearchAssets() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchALL("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchALL("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Medium risk grid DNAC Search field for different columns mgmtSystemHostname or mgmtSystemAddr-Tm5585695c", priority = 9)
	public void assetsDnacSearch() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchDNAC("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchDNAC("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Deselect on  Assets with Crash Risk Table column selector icon-Tm5585319c", priority = 10)
	public void verifyDeselectColumns() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.deselectColumn("Product ID");
		riskMitigationPage1.deselectColumn("Software Type");
		riskMitigationPage1.deselectColumn("Software Release");
		riskMitigationPage1.isApplyButtonEnabled("ApplyButton");
		List<String> systemsActualColumnNames = riskMitigationPage1.getAllTableTitles();
		List<String> systemsExpectedColumnNames = new ArrayList<String>();
		systemsExpectedColumnNames.add("Asset Name");
		systemsExpectedColumnNames.add("Risk");
		assertEquals(systemsActualColumnNames, systemsExpectedColumnNames);
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.isResetButtonEnabled("ResetButton");
	}

	@Test(groups = {
			"Severity2" }, description = "HCR Assets search field with different columns-Tm5585477c", priority = 11)
	public void searchHCRColumns() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage1.searchValueinGrid(1, 2), "search is not found");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Verify Export to CSV option from 360 detailed view-Tm5585312c", priority = 12)
	public void verifyExportCSV360View() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage1.open360View(riskMitigationPage1.getTableCellValue(1, 1));
		boolean exportCSV = riskMitigationPage1.isElementPresent(RiskMitigation360Page.EXPORT_CSV);
		assertFalse(exportCSV, "export csv tab is available");
		riskMitigationPage1.close360View();
	}

	@Test(groups = {
			"Severity2" }, description = "Verify & Navigate to High risk ExportCSVcrash-Tm5585300c", priority = 13)
	public void clickDonutChart() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage.riskFilter(RiskMitigationPage.RISK_HIGH);
		assertTrue(riskMitigationPage.checkFilterApplied(), "filter is applied");
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is not enabled");
	}

	@Test(groups = { "Severity2" }, description = "Select and close High risk Filter-Tm5585507c", priority = 14)
	public void verifyHighRisk() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage1.closeHighFilter();
	}

	@Test(groups = { "Severity2" }, description = "Verify the Medium Risk ExportCSVcrash-Tm5585301c", priority = 15)
	public void verifyMediumRiskFilter() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.riskFilter(RiskMitigationPage.RISK_MEDIUM);
		assertTrue(riskMitigationPage.checkFilterApplied(), "filter is applied");
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is not enabled");
	}

	@Test(groups = { "Severity2" }, description = "Selected risk Medium is displayed in grid-Tm5585555c", priority = 16)
	public void verifyMediumRisk() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isMediumRiskPresentInGrid("Medium"), "filter not is applied");
	}

	@Test(groups = { "Severity2" }, description = "Verify & Navigate to ExportCSVcrash-Tm5585291c", priority = 17)
	public void navigateCSVcrash() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.crashedSystemCount();
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is not enabled");
	}

	@Test(groups = {
			"Severity2" }, description = "Medium risk grid Search field for different columns mgmtSystemHostname or mgmtSystemAddr-Tm5585690c", priority = 18)
	public void mgmtSystemHostnameSearchMedium() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchALL("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchALL("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Medium risk grid DNAC Search field for different columns mgmtSystemHostname or mgmtSystemAddr-Tm5585694c", priority = 19)
	public void mediumRiskDnacSearch() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchDNAC("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchDNAC("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = { "Severity2" }, description = "Verify & Navigate to 90d ExportCSVcrash-Tm5585298c", priority = 20)
	public void exportCSVcrash90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("90d");
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is not enabled");
	}

	@Test(groups = {
			"Severity2" }, description = "Verify Crashed Assets search field for Special character-Tm5585579c,Tm5585624c", priority = 21)
	public void searchSpecialChar90d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage.searchDNACField(ALL_CATEGORIES, "%#&_*");
		assertTrue(riskMitigationPage1.isNoCrashesMessageDisplayed(), "Special char search failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = { "Severity2" }, description = "Verify the Crashed Assets Columns Names-Tm5585335c", priority = 22)
	public void selectCrashedSystemColumn() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.clickResetButton();
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.selectColumns("Product Family");
		riskMitigationPage1.selectColumns("Serial Number");
		riskMitigationPage1.selectColumns("IP Address");
		riskMitigationPage1.selectColumns("Crashes");
		riskMitigationPage1.clickApplyButton();
	}

	@Test(groups = { "Severity2" }, description = "Verify the Crashed Assets Columns Names-Tm5585335c", priority = 23)
	public void totalCrashedSystemColumn() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		List<String> systemsActualColumnNames = riskMitigationPage1.getAllTableTitles();
		List<String> systemsExpectedColumnNames = new ArrayList<String>();
		systemsExpectedColumnNames.add("Asset Name");
		systemsExpectedColumnNames.add("Product ID");
		systemsExpectedColumnNames.add("Software Type");
		systemsExpectedColumnNames.add("Software Release");
		systemsExpectedColumnNames.add("First Crash");
		systemsExpectedColumnNames.add("Last Crash");
		systemsExpectedColumnNames.add("Product Family");
		systemsExpectedColumnNames.add("Serial Number");
		systemsExpectedColumnNames.add("IP Address");
		systemsExpectedColumnNames.add("Crashes");
		assertEquals(systemsActualColumnNames, systemsExpectedColumnNames);
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems valid search field with different columns-Tm5585625c", priority = 24)
	public void searchCrashedSystems90d() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage1.searchValueinGrid(1, 2), "search is not found");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = { "Severity2" }, description = "Verify the Crashed Assets Columns Names-Tm5585332c", priority = 25)
	public void resetCrashedSystemColumn() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		riskMitigationPage1.clickTableColumnSelector();
		riskMitigationPage1.clickResetButton();
		List<String> systemsActualColumnNames = riskMitigationPage1.getAllTableTitles();
		List<String> systemsExpectedColumnNames = new ArrayList<String>();
		systemsExpectedColumnNames.add("Asset Name");
		systemsExpectedColumnNames.add("Product ID");
		systemsExpectedColumnNames.add("Software Type");
		systemsExpectedColumnNames.add("Software Release");
		systemsExpectedColumnNames.add("First Crash");
		systemsExpectedColumnNames.add("Last Crash");
		assertEquals(systemsActualColumnNames, systemsExpectedColumnNames);
	}

	@Test(groups = { "Severity2" }, description = "Verify & Navigate to 30d ExportCSVcrash-Tm5585297c", priority = 26)
	public void exportCSVcrash30d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("30d");
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is not enabled");
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems search field for valid partial string-Tm5585603c", priority = 27)
	public void partialSearch30d() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage1.isDataPresentForSearch("C930"), " Partial search failed");
		riskMitigationPage.closeSearchIcon();

	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems search field for Special character-Tm5585604c", priority = 28)
	public void specialCharSearch30d() {
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		assertTrue(riskMitigationPage1.isDataPresentForSearch("%#&_*"), "Special char search failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems grid Search with ALL Categories mgmtSystemHostname or mgmtSystemAddr in 30d timeperiod-Tm5585679c", priority = 29)
	public void mgmtSystemHostnameSearch30d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchALL("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchALL("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems grid Search with DNAC Categories mgmtSystemHostname or mgmtSystemAddr in 30d timeperiod-Tm5585683c", priority = 30)
	public void dnacSearch30d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchDNAC("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchDNAC("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = { "Severity2" }, description = "Verify & Navigate to 7d ExportCSVcrash-Tm5585296c", priority = 31)
	public void exportCSVcrash7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("7d");
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is not enabled");
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems grid Search with ALL Categories mgmtSystemHostname or mgmtSystemAddr in 7d timeperiod-Tm5585678c", priority = 32)
	public void mgmtSystemHostnameSearch7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchALL("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchALL("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage1.isNoCrashesMessageDisplayed();
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems grid Search with DNAC Categories mgmtSystemHostname or mgmtSystemAddr in 7d timeperiod-Tm5585682c", priority = 33)
	public void dnacSearch7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchDNAC("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchDNAC("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage1.isNoCrashesMessageDisplayed();
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity3" }, description = "Crashed Assets search field for * in 7d timeperiod-Tm5585433c", priority = 34)
	public void specialSearch7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isDataPresentForSearch("*"), "* char search failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity3" }, description = "Crashed Assets search field for Case insensitive in 7d timeperiod-Tm5585434c", priority = 35)
	public void caseSensitiveSearch7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isDataPresentForSearch("c9407R"), "Case insensitive  search failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity3" }, description = "Crashed Assets search field for full string in 7d timeperiod-Tm5585435c", priority = 36)
	public void fullStringSearch7d() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isDataPresentForSearch("C9800-CL-K9"), "full string search failed");
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity3" }, description = "Crashed Assets search field for partial string in 7d timeperiod-Tm5585436c", priority = 37)
	public void partialStringSearch7d() {
		partialSearch30d();	
	}

	@Test(groups = {
			"Severity3" }, description = "Crashed Assets search field for Special character in 7d timeperiod-Tm5585437c", priority = 38)
	public void specialCharSearch7d() {
		specialCharSearch30d();
	}

	@Test(groups = { "Severity2" }, description = "Verify & Navigate to 24h ExportCSVcrash-Tm5585295c", priority = 39)
	public void exportCSVcrash24h() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.timeRangeFilter("24h");
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.isExportCSVbuttonEnabled("ExportCSV"), "export csv tab is not enabled");
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems grid Search with ALL Categories mgmtSystemHostname or mgmtSystemAddr in 24hr timeperiod-Tm5585681c", priority = 40)
	public void mgmtSystemHostnameSearch24h() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchALL("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchALL("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage1.isNoCrashesMessageDisplayed();
		riskMitigationPage.closeSearchIcon();
	}

	@Test(groups = {
			"Severity2" }, description = "Crashed Systems grid Search with DNAC Categories mgmtSystemHostname or mgmtSystemAddr in 24h timeperiod-Tm5585685c", priority = 41)
	public void dnacSearch24h() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		RiskMitigation360Page riskMitigationPage1 = new RiskMitigation360Page();
		assertTrue(riskMitigationPage1.searchDNAC("10.127.102.216"), "DNAC search is failed");
		riskMitigationPage.closeSearchIcon();
		assertTrue(riskMitigationPage1.searchDNAC("Cisco DNA Center"), "DNAC search is failed");
		riskMitigationPage1.isNoCrashesMessageDisplayed();
		riskMitigationPage.closeSearchIcon();
	}

}
