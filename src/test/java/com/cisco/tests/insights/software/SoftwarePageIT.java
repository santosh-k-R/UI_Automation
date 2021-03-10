package com.cisco.tests.insights.software;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.software.Software360Page;
import com.cisco.pages.insights.software.SoftwarePage;
import com.cisco.pages.insights.software.SoftwareSystemPage;
import com.cisco.testdata.StaticData.ButtonName;
import java.text.*;
import java.util.Calendar;
import java.util.TimeZone;
import org.testng.Assert;

import static com.cisco.testdata.Data.*;
import static com.cisco.testdata.StaticData.DNAC_DropdownValue.ALL_CATEGORIES;
import static com.cisco.testdata.StaticData.DNAC_DropdownValue.DNA_CENTER_APPLIANCES;

public class SoftwarePageIT extends DriverBase {
	int softwareGroupCount;
	private String defaultTab = HOME_DATA.get("Default Tab");
	private String successTrack = HOME_DATA.get("SUCCESS_TRACK");
	private String useCase = HOME_DATA.get("Usecase");
	private String Search = SEARCH_DATA.get("SearchAll");
	private String DNACIP = SEARCH_DATA.get("Dnac IP");
	private String PartialDNACIP = SEARCH_DATA.get("Partial Dnac IP");
	private String PartialSearch = SEARCH_DATA.get("Partial Search");
	private String Source = SEARCH_DATA.get("source");
	public static final String RECOMMENDATIONS_FILTER_TYPE = "SUGGESTIONS";
	public static final String RISK_LEVEL_FILTER_TYPE = "RISK LEVEL";

	int tableColumnCountDNACIP;
	int tableColumnCountDNACHostName;
	String currentRecommendationInterval="Every 1 Month";
	String adminRecommendationInterval;
	String[] month;
	int tableDNACIPRowCount;
	int tableDNACHostNameRowCount;

	@Test(description = "Tm5555948c: Verify Login to Cx-Portal", priority = 1)
	public void verifyInsights() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.login();
		//	softwarePage.clickOnMyPortfolio();
		softwarePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);
		//	softwarePage.selectContextSelector(CxHomePage.USE_CASE, useCase);
		softwarePage.clickOnInsights();
		//	softwarePage.switchCXCloudAccount("PRINCETON LTD");
		//	softwarePage.selectCarousel(CarouselName.INSIGHTS);
	}

	@Test(description = "Tm5576387c: Verify the Default Software Tab selected after Click On INSIGHTS Tab", priority = 2)
	public void verifyDefaultSoftwareTab() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.selectTab(defaultTab);
		assertTrue(softwarePage.isSoftwareTabSelected(defaultTab));
	}

	@Test(description = "Tm5555934c:Verify the Default Selected Software Group and Count ", priority = 3)
	public void verifySoftwareGroupCount() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwareGroupCount = softwarePage.softwareGroupCount();
	}

	@Test(description = "Tm5555924c,Tm5555938c:Verify Color code for Risk Level", priority = 4)
	public void colorCode() {
		SoftwarePage SoftwarePage = new SoftwarePage();
		SoftwarePage.colorCode();
	}

	@Test(description = "Tm5555977c:Pagination Check", priority=5)
	public void verifyPagination()
	{
		SoftwarePage pageCheck = new SoftwarePage();
		pageCheck.paginationCheck();
	}

	@Test(description = "Tm5555974c,Tm5576388c,Tm5556005c: To Verify Software Releases are unique", priority = 6)
	public void softwareRelease()
	{
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.uniqueSWRelease();
	}

	@Test(description = "Tm5555899c:To verify current release tab is enabled", priority= 7)
	public void verifyCurrentReleasesInfo()
	{
		SoftwarePage releases = new SoftwarePage();
		releases.currentReleasesInfo();
	}

	@Test(description = "Tm5555973c,Tm5555959,Tm5555936cc: Verify the Automated Recommendation Donuts Filter in S/W Group", priority = 8)
	public void verifySuggestedFilter() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.filterCount(RECOMMENDATIONS_FILTER_TYPE,9);
	}

	@Test(description = "Tm5555952c,Tm5555936c,Tm5555956c,Tm5555954c: Verify the Risk Level Donuts Filter in S/W Group", priority = 9)
	public void verifyRiskLevelFilter()  {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.filterCount(RISK_LEVEL_FILTER_TYPE,6);
	}

	@Test(description = "Tm5555990c,Tm5576391c,Tm5555984c: Verify the software Group Sorting", priority = 13)
	public void softwareGroupSorting()  {

		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.sorting(SOFTWARE_GROUP_COLUMN_DATA);
	}

	@Test(description = "Tm5555929c,Tm5576389c,Tm5555979c: Validate that search in ALL category fetches results when searched with any visible content in the page.", priority = 14)
	public void searchCriteriaALL() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(ALL_CATEGORIES,Search);
		int tableRowCount = softwarePage.getTableRowCount();
		softwarePage.gridValidation(4,Search , tableRowCount);
	}

	@Test(description = "Tm5555931c,Tm5556014c: Validate that search in DNAC category fetches results when searched with ipaddress", priority = 15)
	public void searchCriteriaDNACIPAddress() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(DNA_CENTER_APPLIANCES,DNACIP);
		tableDNACIPRowCount = softwarePage.getTableRowCount();
		softwarePage.gridValidation(2, DNACIP, tableDNACIPRowCount);
	}

	@Test(description = "Tm5555963c,Tm5556026c: Verify the search in DNAC based on partial hostname gives the dnac related data", priority = 16)
	public void partialSearchCriteriaDNACIPAddress() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(DNA_CENTER_APPLIANCES,PartialDNACIP);
		tableDNACIPRowCount = softwarePage.getTableRowCount();
		softwarePage.gridValidation(2, DNACIP, tableDNACIPRowCount);
	}

	@Test(description = "Tm5555930c,Tm5556014c: Validate that search in DNAC category fetches results when searched with hostname.", priority = 17)
	public void searchCriteriaDNACHostName() {
		SoftwarePage softwarePage = new SoftwarePage();	    	
		softwarePage.searchDNACField(DNA_CENTER_APPLIANCES,Source); 
		tableDNACHostNameRowCount = softwarePage.getTableRowCount();
		if(tableDNACHostNameRowCount>0)
		{
			Assert.assertEquals(tableDNACHostNameRowCount,tableDNACIPRowCount,"There is count missmatch for IP Address search and Host Name Search");
		}else
		{
			System.out.println("DNAC Hostname data is not available from upstream");
		}
	}
	@Test(description = "Tm5556013c,Tm5576389c,Tm5555963c: Validate that when searched with partial text , appropriate search results are fetched.", priority = 18)
	public void partialSearchCriteriaALL() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(ALL_CATEGORIES,PartialSearch);
		int tableRowCount = softwarePage.getTableRowCount();
		softwarePage.gridValidation(4, Search, tableRowCount);     
	}  

	@Test(description = "Tm5556013c,Tm5555961c: Validate that when search text doesn’t have any matching value an error message is thrown.", priority = 19)
	public void invalidSearchCriteriaALL() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(ALL_CATEGORIES,"No Software Groups");
		int tableRowCount = softwarePage.getTableRowCount();
		softwarePage.gridValidation(4, "No Software Groups", tableRowCount);
	} 

	@Test(description = "Tm5556023c,Tm5556024c:Verify the search in DNAC based on invalid hostname gives the error message", priority = 20)
	public void invalidSearchCriteriaDNACIP() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(DNA_CENTER_APPLIANCES,"No Software Groups");
		int tableRowCount = softwarePage.getTableRowCount();
		softwarePage.gridValidation(4, "No Software Groups", tableRowCount);
	} 

	@Test(description = "Tm5487739c:Verify the search in DNAC based on invalid ipaddress gives the error message", priority = 21)
	public void invalidSearchCriteriaDNACHost() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(DNA_CENTER_APPLIANCES,"12346.234");
		int tableRowCount = softwarePage.getTableRowCount();
		softwarePage.gridValidation(4, "No Software Groups", tableRowCount);
		softwarePage.clearSearch();
	} 

	@Test(description = "Tm5555894c,Tm5555964c: Verify the software Group 360 window Validation", priority = 22)
	public void softwareGroup360WindowValidation()  {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		softwarePage.recommendationFilter("Yes");
		String profileName = softwarePage.getTableCellValue(1, 1);
		String productFamily = softwarePage.getTableCellValue(1, 3);
		String release = softwarePage.getTableCellValue(1, 5);
		String recommendation = softwarePage.getTableCellValue(1, 9);
		softwarePage.open360View(profileName);
		String softwareGroupName = software360Page.softwareGroupName();
		String sGProductFamily = software360Page.softwareGroupProductFamily();
		if (recommendation.equalsIgnoreCase("Yes")) {
			String sGRelease = software360Page.softwareGroupRelease();
			currentRecommendationInterval=	softwarePage.recommendationInterval();

			assertEquals(release, sGRelease);
		} else {
			String noRecommendations = software360Page.getNoRecommendations();
			assertEquals(noRecommendations, "No Recommendations");
		}
		assertEquals(profileName, softwareGroupName);
		assertEquals(productFamily, sGProductFamily);
		softwarePage.close360View();
		softwarePage.clearSpecificFilter("Yes");
	}

	@Test(description = "Verify the Default Selected Software Group and Count ", priority = 23)
	public void verifyCurrentReleaseLink() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.recommendationFilter("Yes");
		softwarePage.currentReleaseLink();
		int tableRowCount = softwarePage.getTableRowCountFor360Window();
		softwarePage.currentReleaseTab(tableRowCount);
		softwarePage.close360View();
		softwarePage.clearSpecificFilter("Yes");
	}

	@Test(description = "Tm5555884c,Tm5555862c,Tm5576397c:Validate that appropriate message is displayed as \"You have already deployed the recommended software\" when user is already running the best recommended ", priority = 24)	
	public void noRecommMessages() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.noRecommMessages();
	}

	@Test(description = "Tm5556006c: Verify the Default Software Tab selected after Click On Admin Settings Tab", priority = 27)
	public void adminSettings() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.adminSettings();
		Assert.assertEquals(softwarePage.tabname , "Data Sources" , "Title doesn't match ");
		softwarePage.adminSettingsInsights();
		assertTrue(softwarePage.isSoftwareTabSelected(defaultTab));
	}

	@Test(description = "Tm5555922c,Tm5555987c: Verify the Automated Recommendations Interval On Admin Settings Tab", priority = 28)
	public void automatedRecommInterval() {
		SoftwarePage softwarePage = new SoftwarePage();
		adminRecommendationInterval=softwarePage.automatedRecommIntervalDate();
		assertEquals(adminRecommendationInterval, currentRecommendationInterval);
	}

	@Test(description = "Tm5555998c,Tm5556018c,Tm5555999c: Verify that when we update the interval duration and click on save the next recommendation date should update based on interval and todays date.", priority = 29)
	public void nextRecommDateValidation() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.automatedRecommEditButton();
		softwarePage.clickButton(ButtonName.SAVE);
		String nextRecommDate=softwarePage.nextRecommIntervalDate();
		DateFormat date = DateFormat.getDateInstance();
		month = adminRecommendationInterval.split(" ", -2); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		calendar.add(Calendar.MONTH, Integer.parseInt(month[1]));
		String currentDate = date.format(calendar.getTime());
		assertEquals(currentDate, nextRecommDate);
	}

	@Test(description="Tm5593916c: To close admin settings", priority=30)
	public void adminSetting()	
	{
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.adminSettingClose();
	}

	@Test(description = "Tm5555873c: Verify export to csv is enabled", priority = 31)
	public void verifyCSVexport() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.getExportClick();
		assertTrue(softwarePage.getExportCSV());
	}

	@Test(description = "Tm5555873c: Verify export to csv is disabled", priority = 32)
	public void verifyCSVexportDisabledOption() {
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("Test123Test");
		software360Page.getExportClick();
		assertFalse(software360Page.getExportCSV());
		software360Page.searchClear();
	}

	@Test(description = "Tm5555882c: To verify beta tag is displayed", priority = 33)
	public void verifyBetaTag()
	{
		SoftwarePage softwarePage = new SoftwarePage();
		assertTrue(softwarePage.betaToolTip(defaultTab));
	}
}
