package com.cisco.tests.insights.software;

import static org.testng.Assert.assertEquals;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.software.Software360Page;
import com.cisco.pages.insights.software.SoftwarePage;
import com.cisco.testdata.StaticData.ButtonName;

import io.qameta.allure.Step;

import static com.cisco.testdata.Data.*;


public class Software360PageIT extends DriverBase {
	String riskTrend;
	String bugs;
	String securityAdvisories;
	String fieldNotices;
	String features;
	int tableRowCount;
	public static String currentReleaseBugCount;
	public static String currentReleaseBugCountBugsWindow;
	public static String currentReleaseSACount;
	public static String currentReleaseSACountSAWindow;
	public static String currentReleaseFNCount;
	public static String currentReleaseFNCountFNWindow;
	public static String bugData=ERROR_DATA.get("BUGS");
	private String defaultTab = HOME_DATA.get("Default Tab");
	private String successTrack = HOME_DATA.get("SUCCESS_TRACK");
	private String useCase = HOME_DATA.get("Usecase");
	public static  String systems;
	public static String	recommendationsRelease;
	public String featuresActive;
	public String featuresAffected;
	public String featureTab;
	public String recommFeaturesTab;
	public String featuresFixed;
	public static final String STATE = "State";
	public static final String SEVERITY = "Severity";
	public static final String IMPACT = "Impact";
	public int paginationCount;

	@Test(description = "Verify Login to Cx-Portal", priority = 1)
	public void verifyInsights() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.login();
		//	softwarePage.clickOnMyPortfolio();
		softwarePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);
		softwarePage.clickOnInsights();
	}

	@Test(description = "Tm5576387c: Verify the Default Software Tab selected after Click On INSIGHTS Tab", priority = 2)
	public void verifyDefaultSoftwareTab() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.selectTab(defaultTab);
		assertTrue(softwarePage.isSoftwareTabSelected(defaultTab));
	}


	@Test(description = "Tm5555889c,Tm5555897c: Verify the software Group 360 window Validation", priority = 3)
	public void softwareGroup360WindowValidation() {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		softwarePage.recommendationFilter("Yes");
		String profileName = softwarePage.getTableCellValue(1, 1);
		systems=softwarePage.getTableCellValue(1, 8);
		softwarePage.open360View(profileName);
		String softwareGroupName = software360Page.softwareGroupName();
		String releaseDate = softwarePage.getTableCellValue(1, 1);
		String statusAction = softwarePage.getTableCellValue(2, 1);
		String riskScore = softwarePage.getTableCellValue(3, 1);
		riskTrend = softwarePage.getTableCellValue(4, 1);
		bugs = softwarePage.getTableCellValue(5, 1);

		assertEquals(profileName, softwareGroupName);
		assertEquals(releaseDate, "Release & Date");
		assertEquals(statusAction, "Action");
		assertEquals(riskScore, "Risk Score");
		assertEquals(riskTrend, "Risk Trend");
		assertEquals(bugs, "Bugs");
	}

	@Test(description = "Tm5555939c: Validate that the risk score of recommended releases are always lesser than the current release risk score.", priority = 4)
	public void riskScore() {
		Software360Page software360Page = new Software360Page();
		int currentRiskScore=	Integer.parseInt(software360Page.riskScore("2"));
		int recommOneRiskScore	= Integer.parseInt(software360Page.riskScore("3"));
		int	recommTwoRiskScore= Integer.parseInt(software360Page.riskScore("4"));
		int	recommThreeRiskScore = Integer.parseInt(software360Page.riskScore("5"));


		if (recommOneRiskScore < currentRiskScore)
		{
			if(recommOneRiskScore < recommTwoRiskScore )
			{
				if(recommTwoRiskScore < recommThreeRiskScore) {
					System.out.println("recommended releases are  lesser than the current release risk score");
				}else
				{
					System.out.println("Recommendation3 risk score is grater then  Recommendation2 risk score");
				}

			}else
			{
				System.out.println("Recommendation2 risk score is grater then  Recommendation1 risk score");
			}

		}else
		{
			System.out.println("recommended releases are  grater than the current release risk score");
		}

	}

	@Test(description = "Tm5555968c,Tm5555945c,Tm5555947c: To validate user selection of first recommendation workflow", priority = 5)
	public void verifyAcceptButton() {
		Software360Page software360Page = new Software360Page();
		SoftwarePage softwarePage = new SoftwarePage();
		String accept = softwarePage.getTableCellValue(2, 3);
		if (accept.equalsIgnoreCase("SELECT")) {

			software360Page.selectFunction();
			assertEquals(software360Page.getselectedReccomendationRlease(),software360Page.getReccomendationRlease());
			software360Page.clickButton(ButtonName. DONE);
			software360Page.dateFunction();


		} else {
			software360Page.cancelThreeDot();
			software360Page.cancelButton();
			software360Page.selectFunction();
			assertEquals(software360Page.getselectedReccomendationRlease(),software360Page.getReccomendationRlease());
			software360Page.clickButton(ButtonName. DONE);
			software360Page.dateFunction();
		}
	}

	@Test(description = "Tm5555994c,Tm5555943c,Tm5555969c: Validate that when user accepts a second recommendation, the first accepted recommendation automatically cancels.", priority = 6)
	public void verifyRecommAcceptButton() {
		Software360Page software360Page = new Software360Page();
		SoftwarePage softwarePage = new SoftwarePage();
		String select = softwarePage.getTableCellValue(2, 4);
		String recommTwo = softwarePage.getTableCellValue(1, 4);

		if (recommTwo.contains("OPTION 2"))
		{
			if (select.equalsIgnoreCase("SELECT")) {
				recommendationsRelease=software360Page.getReccomendationRlease2();
				software360Page.selectFunction();
				assertEquals(softwarePage.getTableCellValue(2, 3), "SELECT");
				software360Page.clickButton(ButtonName. DONE);	
			}else 
			{
				recommendationsRelease=software360Page.getReccomendationRlease2();
				software360Page.selectFunction();
				software360Page.clickButton(ButtonName. DONE);
			}

		}else
		{
			System.out.println("only one Recommendation available for this customer");
		}
	}

	@Test(description = "Tm5555844c: Verify the current release bugs count bugs from bugs window", priority = 7)
	public void currentReleaseBugCountFromBugs(){
		Software360Page software360Page = new Software360Page();
		currentReleaseBugCount = software360Page.getCurrentlyExposedToBugs();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			software360Page.getBugs();
			currentReleaseBugCountBugsWindow = software360Page.getCurrentlyExposedToBugsFromBugstab();
			assertEquals(currentReleaseBugCountBugsWindow, currentReleaseBugCount);
		}else
		{
			software360Page.getBugs();
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}

	@Test(description = "Tm5555877c: Verify the current release bugs count bugs from bugs window", priority = 8)
	public void bugCount(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			paginationCount=software360Page.paginationCheck1();
			System.out.println("paginationCount--------->"+paginationCount);
		}else
		{
			software360Page.getBugs();
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}
	@Test(description = "Tm5555877c: Verify the current release bugs count bugs from bugs window", priority = 9)
	public void exposedBugCount(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			int exposedCount=software360Page.currentReleaseExposedCount();
			System.out.println("paginationCount--------->"+paginationCount);
			System.out.println("exposedCount--------->"+exposedCount);
			Assert.assertEquals(paginationCount , exposedCount , "Unique Software Releases Count doesn't Match");
		}else
		{
			software360Page.getBugs();
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}

	@Test(description = "Tm5555857c: Verify the State Filters in Bugs Screen", priority = 10)
	public void verifyBugsStateFilter() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0)
		{
			software360Page.filterCount(STATE,4);
		}else
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
	}

	@Test(description = "Tm5555856c,Tm5555953c,Tm5555957c,Tm5555955c: Verify the Severity Filter in Bugs screen", priority = 11)
	public void verifyBugsSeverityFilter()  {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			software360Page.filterCount(SEVERITY,1);
		}else
		{
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}

	@Test(description = "Tm5555873c: Verify export to csv is enabled in Option1 tab", priority = 12)
	public void verifyBugsCSVexport() {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			softwarePage.getExportClick();
			assertTrue(softwarePage.getExportCSV());
		}else
		{
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}

	@Test(description = "Tm5555895c: Verify Cdet id,Severity and Title from bug screen to cdet summary", priority = 13)
	public void verifyBugCdetSummary()
	{
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			String severityBugScreen=software360Page.getTableCellValueFlyout(1,1);
			String cdetIdBugScreen=software360Page.getTableCellValueFlyout(1,2);
			String titleBugScreen=software360Page.getTableCellValueFlyout(1,3);
			software360Page.bugsCdetSummary();

			assertEquals(severityBugScreen, software360Page.severity, "Severity value doesn't match from bug screen to cdet summnary");
			assertEquals("Bugs "+cdetIdBugScreen, software360Page.cdet, "Cdet value doesn't match from bug screen to cdet summnary");
			assertEquals(titleBugScreen,software360Page.title , "Title value doesn't match from bug screen to cdet summnary");
		}else
		{
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}

	@Test(description = "Tm5576392c,Tm5576396c,Tm5555845c: Verify Cdet's Affected System count from bug screen to Cdet's Affected summary", priority = 14)
	public void verifyBugAffectedSystem()
	{
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			software360Page.hideContextualDetailsView();
			software360Page.bugsAffectSystemsCount();
			assertEquals(software360Page.affectedSystemBugScreen, software360Page.affectedSystemCount ,"Affected System Count doesn't match");
		}else
		{
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}}

	@Test(description = "Tm5576396c: Verify Cdet's features count from bug screen to Cdet's Affected summary", priority = 15)
	public void verifyBugsFeaturesCount()
	{
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			software360Page.hideContextualDetailsView();
			software360Page.bugsFeaturesCount();

			assertEquals(software360Page.featuresBugScreen, software360Page.featuresASCount ,"Features count doesn't match from bug screen to affected Systems");
			assertEquals(software360Page.featuresASCount, software360Page.count-1 ,"Features count doesn't match from bug screen to affected Systems");	
		}else
		{
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}}

	@Test(description = "Tm5555848c: To verify clicking on each bug has 2 Tabs i.e Summary and Affected Systems", priority = 16)
	public void verifyBugidDetailsTabs()
	{
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			software360Page.hideContextualDetailsView();
			software360Page.bugsTabs();

			assertEquals(software360Page.summaryTab, "SUMMARY","Summary Tab is not present");
			Assert.assertTrue(software360Page.affectedSystemTab.contains("AFFECTED SYSTEMS"));	
		}else
		{
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}

	@Test(description = "Tm5576396c,Tm5576396c: Verify the System Tab Sorting from 360 window", priority = 17)
	public void currentReleaseTabSorting() {
		Software360Page software360Page = new Software360Page();
		SoftwarePage softwarePage = new SoftwarePage();
		if(Integer.parseInt(currentReleaseBugCount)>0){
			software360Page.hideContextualDetailsView();
			softwarePage.sorting(BUG_DATA);
		}else
		{
			Assert.assertEquals(software360Page.noBugsInformationAvailable(),"No Bugs Information Available");
		}
	}

	@Test(description = "Tm5593918c: Verify the Bugs Recommendation1 is displayed", priority = 18)
	public void bugsRecommendationTab() {
		Software360Page software360Page = new Software360Page();
		Assert.assertEquals(software360Page.Recommendation1(),"OPTION 1", "Bugs recommendation1 doesn't exist");
	}

	@Test(description = "Tm5555865c,Tm5555868c,Tm5555958c: Verify the State Filters in Bugs Recommendation Screen", priority = 19)
	public void verifyBugsRecommStateFilter() {
		Software360Page software360Page = new Software360Page();
		software360Page.clearAllFilter();
		software360Page.filterCount(STATE,4);
	}

	@Test(description = "Tm5555953c,Tm5555957c,Tm5555955c: Verify the Severity Filter in Bugs Recommendation screen", priority = 20)
	public void verifyBugsRecommSeverityFilter()  {
		Software360Page software360Page = new Software360Page();
		software360Page.filterCount(SEVERITY,1);
	}

	@Test(description = "Tm5555873c: Verify export to csv is enabled in Option1 tab", priority = 21)
	public void verifyBugsCSVexportRecomm() {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		softwarePage.getExportClick();
		assertTrue(softwarePage.getExportCSV());
	}

	@Test(description = "Tm5576396c,Tm5576396c: Verify the System Tab Sorting from 360 window", priority = 22)
	public void recommTabSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		softwarePage.sorting(BUG_DATA);
	}

	@Test(description = "Tm5555885c:Verify the Recommended Tab", priority = 25)
	public void verifyRecommendedTab() {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		software360Page.backButton();
		software360Page.selectTab("SUGGESTIONS");
		securityAdvisories = softwarePage.getTableCellValue(6, 1);
		assertEquals(securityAdvisories, "Security Advisories");
	}

	@Test(description = "Tm5555900c: Verify the Security Advisories from 360 window", priority = 26)
	public void verifySecurityAdvisories()  {
		Software360Page software360Page = new Software360Page();
		currentReleaseSACount = software360Page.getCurrentlyExposedToSA();
		Assert.assertEquals(software360Page.getSecurityAdvisories(),"Security Advisories", "Security Advisories Tab is not displayed");
	}

	@Test(description = "Verify the current release SA count from 360 screen to SA window", priority = 27)
	public void currentReleaseSACountFromSA(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			currentReleaseSACountSAWindow = software360Page.getCurrentlyExposedToBugsFromBugstab();
			assertEquals(currentReleaseSACountSAWindow, currentReleaseSACount);
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}
	}

	@Test(description = "Tm5555879c: Verify the current release bugs count bugs from bugs window", priority = 28)
	public void SACount(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			paginationCount=software360Page.paginationCheck1();
			System.out.println("paginationCount--------->"+paginationCount);
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}
	}

	@Test(description = "Tm5555879c: Verify the current release bugs count bugs from bugs window", priority = 29)
	public void exposedSACount(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			int exposedCount=software360Page.currentReleaseExposedCount();
			System.out.println("paginationCount--------->"+paginationCount);
			System.out.println("exposedCount--------->"+exposedCount);
			Assert.assertEquals(paginationCount , exposedCount , "Unique Software Releases Count doesn't Match");
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}
	}
	@Test(description = "Tm5555864c:Verify the Security Advisories contains title and description information", priority = 30)
	public void titledespSA() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			String titleDetailsGrid=software360Page.getTableCellValueFlyout(1,2);
			software360Page.getTitleColnameSA();
			assertEquals("TITLE", software360Page.titleSA, "Title headers doesn't match from SA grid to SA details view");
			assertEquals(software360Page.getDescriptionColnameSA(), "DESCRIPTION", "Title headers doesn't match from SA grid to SA details view");
			assertEquals(titleDetailsGrid, software360Page.getTitleDetailsSA(), "Title details doesn't match from SA grid to SA details view");
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}}
	@Test(description = "Tm5555860c: Verify the state Filters in Security Advisory Screen", priority = 31)
	public void verifySAStateFilter() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			software360Page.filterCount(STATE,5);
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}
	}

	@Test(description = "Tm5555859c: Verify the impact Filter in Security Advisory screen", priority = 32)
	public void verifySASeverityFilter()  {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			software360Page.filterCount(IMPACT,1);
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}
	}

	@Test(description = "Tm5555873c: Verify export to csv is enabled", priority = 33)
	public void verifySACSVexport() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			software360Page.getExportClick();
			assertTrue(software360Page.getExportCSV());
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}
	}

	@Test(description = "Tm5555873c: Verify export to csv is disabled", priority = 34)
	public void verifySACSVexportDisabledOption() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			software360Page.searchOSVTrackItems("Test123Test");
			software360Page.getExportClick();
			assertFalse(software360Page.getExportCSV());
			software360Page.searchClear();
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}
	}

	@Test(description = "Tm5593919c:Verify the Security Advisories Sorting from 360 window", priority = 34)
	public void currentReleaseSATabSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseSACount)>0){
			softwarePage.sorting(SECURITY_ADVISORIES_SORTING_DATA);
		}else
		{
			Assert.assertEquals(software360Page.noSecurityAdvisoriesFound(),"No Security Advisories Found");
		}		
	}

	@Test(description = "Tm5593920c:Verify the Security Advisories Recommendation1 is displayed", priority = 35)
	public void verifySARecommendationTab() {
		Software360Page software360Page = new Software360Page();
		Assert.assertEquals(software360Page.Recommendation1(),"OPTION 1", "Security Advisories recommendation1 doesn't exist");
	}

	@Test(description = "Tm5555860c,Tm5555860c,Tm5555870c,Tm5555860c,Tm5555872c: Verify the State Filters in Security Advisory Recommendation Screen", priority = 36)
	public void verifySARecommStateFilter() {
		Software360Page software360Page = new Software360Page();
		software360Page.clearAllFilter();
		software360Page.filterCount(STATE,5);
	}

	@Test(description = "Tm5593921c,Tm5593921c,Tm5593921c: Verify the impact Filters in Security Advisory Recommendation Screen", priority = 37)
	public void verifySARecommImpactFilter()  {
		Software360Page software360Page = new Software360Page();
		software360Page.filterCount(IMPACT,1);
	}

	@Test(description = "Tm5555873c: Verify export to csv is enabled", priority = 38)
	public void verifySACSVexportRecomm() {
		Software360Page software360Page = new Software360Page();
		software360Page.getExportClick();
		assertTrue(software360Page.getExportCSV());
	}

	@Test(description = "Tm5555873c: Verify export to csv is disabled", priority = 39)
	public void verifySACSVexportDisabledOptionRecomm() {
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("Test123Test");
		software360Page.getExportClick();
		assertFalse(software360Page.getExportCSV());
		software360Page.searchClear();
	}

	@Test(description = "Tm5555917c: Verify the Security Advisories Tab Soting from 360 window", priority = 40)
	public void recommSAReleaseTabSorting() {

		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		softwarePage.sorting(SECURITY_ADVISORIES_SORTING_DATA);
	}

	@Test(description = "Verify the Security Advisories Search Filter", priority = 41)
	public void saSearchCriteria() {
		String val;
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("HighMedLow");
		val=software360Page.noSecurityAdvisoriesFound();
		assertEquals(val, "No Security Advisories Found");
		software360Page.searchClear();
	}

	@Test(description = "Tm5555887c:Verify the Recommended Tab", priority = 42)
	public void verifyRecommendedTab1() {

		Software360Page software360Page = new Software360Page();
		software360Page.backButton();
		Assert.assertEquals(software360Page.recommendedTab(),"OPTION 1","Recommendation 1 tab is not displayed");
	}

	@Test(description = "Tm5555898c: Verify the Field Notices from 360 window", priority = 43)
	public void verifyFieldNotices()  {
		Software360Page software360Page = new Software360Page();
		currentReleaseFNCount = software360Page.getCurrentlyExposedToFN();
		Assert.assertEquals(software360Page.getFieldNotices(),"Field Notices", "Field Notices Tab is not displayed");
	}

	@Test(description = "Verify the current release FN count from 360 screen to FN window", priority = 44)
	public void currentReleaseFNCountFromFN360(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			currentReleaseFNCountFNWindow = software360Page.getCurrentlyExposedToBugsFromBugstab();
			assertEquals(currentReleaseFNCountFNWindow, currentReleaseFNCount);
		}else
		{
			Assert.assertEquals(software360Page.noFNsFound(),"No FNs Found");
		}
	}

	@Test(description = "Tm5555878c: Verify the current release bugs count bugs from bugs window", priority = 45)
	public void FNCount(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			paginationCount=software360Page.paginationCheck1();
			System.out.println("paginationCount--------->"+paginationCount);
		}else
		{
			Assert.assertEquals(software360Page.noFNsFound(),"No FNs Found");
		}}

	@Test(description = "Tm5555878c: Verify the current release bugs count bugs from bugs window", priority = 46)
	public void exposedFNCount(){
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			int exposedCount=software360Page.currentReleaseExposedCount();
			System.out.println("paginationCount--------->"+paginationCount);
			System.out.println("exposedCount--------->"+exposedCount);
			Assert.assertEquals(paginationCount , exposedCount , "Unique Software Releases Count doesn't Match");
		}else
		{
			Assert.assertEquals(software360Page.noFNsFound(),"No FNs Found");
		}
	}

	@Test(description = "Verify the Field Notices contains title and problem resolution information", priority = 47)
	public void titledespFN() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			String titleDetailsGrid=software360Page.getTableCellValueFlyout(1,2);
			software360Page.getTitleColnameSA();
			assertEquals("TITLE", software360Page.titleSA, "Title headers doesn't match from SA grid to SA details view");
			assertEquals(software360Page.problemResolutionFN(), "PROBLEM DESCRIPTION", "Title headers doesn't match from SA grid to SA details view");
			assertEquals(titleDetailsGrid, software360Page.getTitleDetailsSA(), "Title details doesn't match from SA grid to SA details view");
		}else
		{
			Assert.assertEquals(software360Page.noFNsFound(),"No FNs Found");
		}	}

	@Test(description = "Tm5555866c: Verify the State Filters in Field Notices Screen", priority = 48)
	public void verifyFNStateFilter() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			software360Page.filterCount(STATE,5);
		}else
		{
			Assert.assertEquals(software360Page.noFNsFound(),"No FNs Found");
		}	}

	@Test(description = "Tm5555873c: Verify export to csv is enabled", priority = 49)
	public void verifyFNCSVexport() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			software360Page.getExportClick();
			assertTrue(software360Page.getExportCSV());
		}
	}

	@Test(description = "Tm5555873c: Verify export to csv is disabled", priority = 50)
	public void verifyFNCSVexportDisabledOption() {
		Software360Page software360Page = new Software360Page();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			software360Page.searchOSVTrackItems("Test123Test");
			software360Page.getExportClick();
			assertFalse(software360Page.getExportCSV());
			software360Page.searchClear();
		}
	}

	@Test(description = "Tm5555916c: Verify the Field Notices Sorting from 360 window", priority = 51)
	public void currentReleaseFNTabSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		if(Integer.parseInt(currentReleaseFNCount)>0){
			softwarePage.sorting(FIELD_NOTICES_SORTING_DATA);
		}
	}

	@Test(description = "Tm5593923c: Verify the Field Notices Recommendation1 is displayed", priority = 52)
	public void verifyFNRecommendationTab() {
		Software360Page software360Page = new Software360Page();
		Assert.assertEquals(software360Page.Recommendation1(),"OPTION 1", "Field Notices recommendation1 doesn't exist");
	}

	@Test(description = "Tm5555980c: Verify the Field Notices Search Filter", priority = 53)
	public void recommFNSearchCriteria() {
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("High");
		software360Page.searchClear();
	}

	@Test(description = "Tm5555858c,Tm5555867c,Tm5555936c,Tm5555869c,Tm5555871c: Verify the State Filters in Field Notices recommendation Screen", priority = 54)
	public void verifyFnRecommStateFilter() {
		Software360Page software360Page = new Software360Page();
		software360Page.clearAllFilter();
		software360Page.filterCount(STATE,5);
	}


	@Test(description = "Tm5555873c: Verify export to csv is enabled", priority = 55)
	public void verifyFNCSVexportRecomm() {
		Software360Page software360Page = new Software360Page();
		software360Page.getExportClick();
		assertTrue(software360Page.getExportCSV());
	}

	@Test(description = "Tm5555873c: Verify export to csv is disabled", priority = 56)
	public void verifyFNCSVexportDisabledOptionRecomm() {
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("Test123Test");
		software360Page.getExportClick();
		assertFalse(software360Page.getExportCSV());
		software360Page.searchClear();
	}

	@Test(description = "Tm5555916c: Verify the Field Notices Tab Sorting from 360 window", priority = 57)
	public void recommFNReleaseTabSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		softwarePage.sorting(FIELD_NOTICES_SORTING_DATA);
	}

	@Test(description = "Tm5555980c: Verify the Field Notices Search Filter", priority = 58)
	public void fnSearchCriteria() {
		String val;
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("HML");
		val=software360Page.noFNsFound();
		assertEquals(val,"No FNs Found");
		software360Page.searchClear();
	}

	@Test(description = "Tm5555886c: Verify the Back Button From Field Notices Tab", priority = 59)
	public void verifyFNBackButton() {
		Software360Page software360Page = new Software360Page();
		SoftwarePage softwarePage = new SoftwarePage();
		software360Page.backButton();
		fieldNotices = softwarePage.getTableCellValue(7, 1);
		assertEquals(fieldNotices, "Field Notices");
	}

	@Test(description = "Verify the release Summary  tab from 360 window", priority = 60)
	public void releaseSummaryTab() {
		Software360Page software360Page = new Software360Page();
		software360Page.selectTab("RELEASE SUMMARY");
	}

	@Test(description = "Tm5555914c,Tm5555946c: Verify the System Tab from 360 window", priority = 61)
	public void systemTab()  {
		Software360Page software360Page = new Software360Page();
		software360Page.selectTab("SYSTEMS");
		String AcceptedRelease = software360Page.getTableCellValue(1, 5);

		if (AcceptedRelease!=null)
		{
			String currentRelease = software360Page.getTableCellValue(1, 4);

			String deploymentStatus = software360Page.getTableCellValue(1, 6);

			if (currentRelease.equalsIgnoreCase(AcceptedRelease))
			{
				assertEquals(deploymentStatus, "Production");
			}else
			{
				assertEquals(deploymentStatus, "Upgrade");
			}
		}
	}

	@Test(description = "Tm5555883c,Tm5555935c: Validate that systems count indicates the number of systems in that profile.", priority = 62)
	public void verifySystemsCount()  {
		int systemCount;
		Software360Page software360Page = new Software360Page();	
		systemCount = software360Page.systemTabRowCount();
		assertEquals(systemCount, Integer.parseInt(systems));
	}

	@Test(description = "Tm5593925c:Verify the System Tab Soting from 360 window", priority = 63)
	public void systemTabSorting() 
	{
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.sorting(SYSTEM_SORTING_DATA);
	}

	@Test(description = "Tm5555883c: Verify the Accepted Release validation in System Tab  from 360 window", priority = 64)
	public void systemTabAcceptedReleaseValidation() {
		Software360Page software360Page = new Software360Page();
		String systemAR = software360Page.getTableCellValue(1, 5);
		assertEquals(recommendationsRelease, systemAR);
	}

	@Test(description = "Tm5593925c:Verify the Current Releases Tab from 360 window", priority = 65)
	public void currentReleases()  {
		Software360Page software360Page = new Software360Page();
		software360Page.selectTab("CURRENT RELEASES");
	}

	@Test(description = "Tm5593925c:Verify the current releases Tab Soting from 360 window", priority = 66)
	public void currentReleasesSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.sorting(CURRENT_RELEASE_SORTING_DATA);
	}

	@Test(description = "Tm5556009c: Verify the Accepted Release validation in current releases Tab  from 360 window", priority = 67)
	public void acceptedReleaseValidation() {
		SoftwarePage softwarePage = new SoftwarePage();
		String currentReleasesAR = softwarePage.getTableCellValue(1, 4);
		assertEquals(recommendationsRelease, currentReleasesAR);
	}

	@Test(description = "Tm5555940c: Validate that the risk score value in risk score row and trend chart always match", priority = 68)
	public void riskScoreTrendValidation() {
		int currentRiskScore,riskScoretrendChart;
		Software360Page software360Page = new Software360Page();
		software360Page.selectTab("SUGGESTIONS");
		currentRiskScore=	Integer.parseInt(software360Page.riskScore("2"));
		software360Page.riskScoretrendChart();
		riskScoretrendChart=Integer.parseInt(software360Page.riskScoreTrendWindow());
		assertEquals(currentRiskScore, riskScoretrendChart);
	}

	@Test(description = "Tm5555886c: Verify the Back Button From Field Notices Tab", priority = 69)
	public void verifyRiskScoreBackButton() {
		Software360Page software360Page = new Software360Page();
		SoftwarePage softwarePage = new SoftwarePage();
		software360Page.backButton();
		features = softwarePage.getTableCellValue(8, 1);
		assertEquals(features, "Features");
	}

	@Test(description = "Tm5555893c: Verify Affected and Active Features count from Features 360 window to Features detail Screen", priority = 70)
	public void featuresCountValidation()
	{
		Software360Page software360Page = new Software360Page();
		software360Page.featuresCount360Window();
		featureTab=software360Page.getFeatures();
		featuresActive=software360Page.featuresCount("Active");
		featuresAffected=software360Page.featuresCount("Affected");
		assertEquals(software360Page.featuresAffected360W,featuresAffected,"Features - Affected count doesn't match");
		assertEquals(software360Page.featuresActive360W,featuresActive,"Features - Active count doesn't match");
		assertEquals(featureTab,"Features","Features tab not displayed");
		software360Page.backButton();
	}

	@Test(description = "Tm5555918c:To validate the Affected records count in Features Screen", priority = 71)
	public void featuresAffectedRecordsCount()
	{
		int affectedFeaturesRowCount;
		Software360Page software360Page = new Software360Page();
		software360Page.getFeatures();
		affectedFeaturesRowCount =software360Page.featuresRowCount();
		assertEquals(Integer.parseInt(featuresAffected),affectedFeaturesRowCount,"Features - Affected count doesn't match");		
	}

	@Test(description = "Tm5555893c,Tm5555918c:To validate affected feature name and systems count in Features Screen", priority = 72)
	public void featuresAffectedData()
	{
		Software360Page software360Page = new Software360Page();
		software360Page.featuresNameSystems();
		assertEquals(software360Page.featureName,software360Page.titleName(),"Affected feature name doesn't match");
		assertEquals(Integer.parseInt(software360Page.systems),software360Page.getFlyoutTableRowCount(),"Affected feature name and system count doesn't match");
	}

	@Test(description = "Tm5593926c: Verify the Affected Featurs Tab Soting from 360 window", priority = 73)
	public void affectedFeaturedetailSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.sorting(ACTIVE_FEATURES_SORTING_DATA);
	}

	@Test(description="Tm5555921c:To validate the affected features grid column names", priority = 74)
	public void verifyaffectedFeatureHeadings()
	{
		Software360Page software360Page = new Software360Page();

		List<String> actualLabelNames = software360Page.systemTableTitle();
		List<String> expectedLabelNames = new ArrayList<String>();
		expectedLabelNames.add("System Name");
		expectedLabelNames.add("IP Address");
		expectedLabelNames.add("Product ID");
		expectedLabelNames.add("Software Release");
		assertEquals(actualLabelNames, expectedLabelNames);
		software360Page.backButton();
	}

	@Test(description = "Tm5555893c,Tm5555918c:To validate Active feature name and systems count in Features Screen", priority = 75)
	public void featuresActiveData()
	{
		Software360Page software360Page = new Software360Page();
		software360Page.featuresclick("Active");
		software360Page.featuresNameSystems();
		assertEquals(software360Page.featureName,software360Page.titleName(),"Active feature name doesn't match");
		assertEquals(Integer.parseInt(software360Page.systems),software360Page.getFlyoutTableRowCount(),"Active feature name and system count doesn't match");
	}

	@Test(description = "Tm5593926c: Verify the Active Features Tab Soting from 360 window", priority = 76)
	public void activeFeaturedetailSorting() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.sorting(ACTIVE_FEATURES_SORTING_DATA);
	}

	@Test(description="Tm5555921c:To validate the Active features tab grid column names", priority = 77)
	public void verifyactiveFeatureHeadings()
	{
		Software360Page software360Page = new Software360Page();

		List<String> actualLabelNames = software360Page.systemTableTitle();
		List<String> expectedLabelNames = new ArrayList<String>();
		expectedLabelNames.add("System Name");
		expectedLabelNames.add("IP Address");
		expectedLabelNames.add("Product ID");
		expectedLabelNames.add("Software Release");
		assertEquals(actualLabelNames, expectedLabelNames);
		software360Page.backButton();
	}

	@Test(description="Search function in Features Screen", priority= 78)
	public void searchFeature()
	{
		String featuresVal;
		Software360Page software360Page = new Software360Page();
		software360Page.searchOSVTrackItems("HighMedLow");
		featuresVal=software360Page.noFeaturesFound();
		assertEquals(featuresVal, "No Features are Available");
		software360Page.searchClear();
		software360Page.backButton();
	}

	@Test(description = "Tm5555893c: Verify Affected and Fixed Features count from Features 360 window to Features detail Screen", priority = 79)
	public void recommFeaturesCountValidation()
	{
		Software360Page software360Page = new Software360Page();
		software360Page.recommFeaturesCount360Window();
		software360Page.getFeatures();
		recommFeaturesTab=software360Page.getRecommFeatures();
		featuresAffected=software360Page.featuresCount("Affected");
		featuresFixed=software360Page.featuresCount("Fixed");

		assertEquals(recommFeaturesTab,"OPTION 1","Recommendation Features tab not displayed");
		assertEquals(software360Page.recommFeaturesAffected360W,featuresAffected,"Features - Affected count doesn't match");
		assertEquals(software360Page.recommFeaturesActive360W,featuresFixed,"Features - Fixed count doesn't match");
	}

	@Test(description="To verify the feature name is disabled on click in recommendation Feature Screen", priority= 80)
	public void recommFeatureIsDisabledCheck()
	{
		Software360Page software360Page = new Software360Page();
		boolean val =software360Page.recommFeatureIsDisabled();
		assertTrue(val, "Passed");
	}

}