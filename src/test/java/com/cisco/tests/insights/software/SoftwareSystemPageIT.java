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
import com.cisco.testdata.StaticData.CarouselName;

import static com.cisco.testdata.Data.*;
import static com.cisco.testdata.StaticData.DNAC_DropdownValue.ALL_CATEGORIES;

public class SoftwareSystemPageIT extends DriverBase{

	private String defaultTab = HOME_DATA.get("Default Tab");
	private String successTrack = HOME_DATA.get("SUCCESS_TRACK");
	private String useCase = HOME_DATA.get("Usecase");
	private String Search = SEARCH_DATA.get("ReleaseSearch");
	private String softwareGroupSystemCount;
	private String softwareReleaseSystemCount;

	@Test(description = "Tm5555950c,Tm5555949c: Verify Login to Cx-Portal", priority = 1)
	public void verifyInsights() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.login();
		//softwarePage.clickOnMyPortfolio();
		softwarePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);
		softwarePage.selectContextSelector(CxHomePage.USE_CASE, useCase);
		softwarePage.clickOnInsights();	

		//	softwarePage.selectCarousel(CarouselName.INSIGHTS);
	}

	@Test(description = "Tm5576387c: Verify the Default Software Tab selected after Click On INSIGHTS Tab", priority = 2)
	public void verifyDefaultSoftwareTab() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.selectTab(defaultTab);
		assertTrue(softwarePage.isSoftwareTabSelected(defaultTab));
	}

	@Test(description = "Tm5555880c:Validate that search in ALL category fetches results when searched with any visible content in the page.", priority = 3)
	public void searchCriteriaALL1() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.searchDNACField(ALL_CATEGORIES,Search);
		softwareGroupSystemCount=softwarePage.getTableCellValue(1, 8);
	}

	@Test(description = "Tm5576387c: Verify Software releases Tab page", priority = 4)
	public void softwareReleasePage() {
		SoftwareSystemPage SoftwareSystemPage = new SoftwareSystemPage();
		SoftwareSystemPage.SoftwareReleasesPageReleasePage();
	}

	@Test(description = "Tm5555933c,Tm5555941c,Tm5576388c,Tm5555904c: verify Software Release are unique", priority = 5)
	public void swReleaseUniqueCount() {
		SoftwareSystemPage SoftwareSystemPage = new SoftwareSystemPage();
		SoftwareSystemPage.uniqueSWRelease();
	}

	@Test(description = "Tm5555888c,Tm5576390c: Verify the Software Releases Sorting", priority = 6)
	public void softwareReleaseSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.sorting(SOFTWARE_RELEASE_COLUMN_DATA);
	}

	@Test(description = "Tm5555962c,Tm5555880c: Verify the Search Filter When No Filters Applied on Systems Grid", priority = 7)
	public void searchCriteria()  {
		SoftwareSystemPage SoftwareSystemPage = new SoftwareSystemPage();
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems(Search);
		softwareReleaseSystemCount=SoftwareSystemPage.getTableCellValue(1, 4);
		assertEquals( softwareGroupSystemCount, softwareReleaseSystemCount);
		software360Page.searchClear();
	}

	@Test(description = "Tm5555977c,Tm5555881c: Verify the Page Nation Count", priority = 8)
	public void pagiNation() {
		SoftwareSystemPage SoftwareSystemPage = new SoftwareSystemPage();
		SoftwareSystemPage.navigateToPage(1);	
	}

	@Test(description = "Tm5555873c: Verify export to csv is enabled", priority = 9)
	public void verifyCSVexport() {
		SoftwareSystemPage SoftwareSystemPage = new SoftwareSystemPage();
		SoftwareSystemPage.	getExportClick();
		assertTrue(SoftwareSystemPage.getExportCSV());
	}

	@Test(description = "Tm5555873c: Verify export to csv is disabled", priority = 10)
	public void verifyCSVexportDisabledOption() {
		SoftwareSystemPage SoftwareSystemPage = new SoftwareSystemPage();
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("Test123Test");
		SoftwareSystemPage.	getExportClick();
		assertFalse(SoftwareSystemPage.getExportCSV());
	}
}

