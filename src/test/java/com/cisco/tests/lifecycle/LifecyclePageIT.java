package com.cisco.tests.lifecycle;

import static com.cisco.testdata.Data.ACCELERATOR_DATA;
import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;
import static com.cisco.testdata.lifecycle.Data.CUSTOMER_ID_GRAPHIC;
import static com.cisco.testdata.lifecycle.Data.CUSTOMER_ID_ZHEHUI;
import static com.cisco.testdata.lifecycle.Data.DEFAULT_USER_ROLE;
import static com.cisco.testdata.lifecycle.Data.SOLUTION;
import static com.cisco.testdata.lifecycle.Data.getATXData;
import static com.cisco.testdata.lifecycle.Data.getATXDataFromAllUseCase;
import static com.cisco.testdata.lifecycle.Data.STANDARD_USER_ROLE;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;

import com.cisco.pages.lifecycle.LifecyclePage;
import com.cisco.testdata.StaticData.CarouselName;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.cisco.pages.lifecycle.AcceleratorsPage;
import com.cisco.pages.lifecycle.AskTheExpertsPage;
import com.cisco.pages.lifecycle.CiscoCommunityPage;
import com.cisco.pages.lifecycle.LearningPage;
import com.cisco.pages.lifecycle.RaceTrackPage;
import com.cisco.pages.lifecycle.SuccessTipsPage;
import com.cisco.testdata.StaticData.ButtonName;
import com.cisco.testdata.StaticData.PitStopsName;
import com.cisco.testdata.lifecycle.ATXPoJo;

import io.qameta.allure.Issue;
import io.qameta.allure.Step;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;

public class LifecyclePageIT extends DriverBase {
	private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");
    private String networkDeviceOnboardingUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_1");
    private String campusNetworkAssuranceUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_2");
    private String ScalableAccessPolicyUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_3");
    private String CampusSoftwareImageManagementUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_4");
    private String CampusNetworkSegmentationUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_5");
    private String cxCloudAccountL2 = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
    private String cxCloudAccountL1 = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L1");
    private static ThreadLocal<LifecyclePage> page = ThreadLocal.withInitial(() -> new LifecyclePage());
	private String multipleBuidCloudAccount = LIFECYCLE_COMMON_DATA.get("MULTIPLE_BUID_CLOUD_ACCOUNT");
	private String cloudAccountWithPartnerContent = LIFECYCLE_COMMON_DATA.get("PARTNER_CLOUD_ACCOUNT");
	private String partnerName = ACCELERATOR_DATA.get("PARTNER_NAME");
	private ATXPoJo COMPLETED_CISCO_ATX_ITEM;
	List<ATXPoJo> ATX_DATA_LIST_CISCO;


	    @Test(description = "TC230557-Verify that on the port folio tile the usecase count at pitstop level will be the from the successtrack of BUId which selected")
	    public void verifyPortfolioTileUsecasecount()  {
	    	 SoftAssert softAssert = new SoftAssert();
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(cxCloudAccountL2);
	    	lifecyclePage.refreshportfolio();
	    	 HashMap<String, Integer> countOfUsecasesAtThePitstop = lifecyclePage.getTotalNoOfUsecasePitstop();
	    	 softAssert.assertEquals(lifecyclePage.getRacetrackcount("Onboard"),Integer.toString(countOfUsecasesAtThePitstop.get("Onboard").intValue()), "usecases at Use pitstop is not matching");
	    	 softAssert.assertEquals(lifecyclePage.getRacetrackcount("Implement"),Integer.toString(countOfUsecasesAtThePitstop.get("Implement").intValue()), "usecases at Use pitstop is not matching");
	    	 softAssert.assertEquals(lifecyclePage.getRacetrackcount("Use"),Integer.toString(countOfUsecasesAtThePitstop.get("Use").intValue()), "usecases at Use pitstop is not matching");
	    	 softAssert.assertEquals(lifecyclePage.getRacetrackcount("Engage"),Integer.toString(countOfUsecasesAtThePitstop.get("Engage").intValue()), "usecases at Use pitstop is not matching");
	    	 softAssert.assertEquals(lifecyclePage.getRacetrackcount("Adopt"),Integer.toString(countOfUsecasesAtThePitstop.get("Adopt").intValue()), "usecases at Use pitstop is not matching");
	    	 softAssert.assertEquals(lifecyclePage.getRacetrackcount("Optimize"),Integer.toString(countOfUsecasesAtThePitstop.get("Optimize").intValue()), "usecases at Use pitstop is not matching");
	    	 softAssert.assertAll();

	    }
	    
	    @Test(description = "TC230551-verify that percentage for each pitstop completion is representative of the adoption at that level")
	    public void verifyPortfolioUsecasePercentage()  {
	    	 SoftAssert softAssert = new SoftAssert();
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(cxCloudAccountL2);
	    	lifecyclePage.refreshportfolio();
	    	HashMap<String, String> countOfUsecasepercentageAtportfolio = lifecyclePage.getUsecasePercentageAtPortfolioPage();
	    	softAssert.assertEquals(countOfUsecasepercentageAtportfolio, lifecyclePage.getSuccesstrackNames(), "usecase percentage showing at the portfolio doesnt match with the adoption percentage in homepage");
	    	softAssert.assertAll();
	    }
	    
	    @Test(description = "TC230552-Verify that the  by default in port folio any one buid should be in selected position according to the alpahabetical order and view should be based on that Buid", groups = {"singleThread"})
	    public void verifydefaultviewPortfolio()  {
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(multipleBuidCloudAccount);
	    	lifecyclePage.refreshportfolio();
	 	   	assertTrue(lifecyclePage.verifyDefaultView(),"portfolio default view is random");

	    }
	    
	    @Test(description = "TC230555-Verify that when a customer having multiple BUID logins to customer portal ,a fliter to select the BUID is shown in the Portfolio page", groups = {"singleThread"})
	    public void verifyFilterPortfolio()  {
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(multipleBuidCloudAccount);
	    	lifecyclePage.refreshportfolio();
	    	assertTrue(lifecyclePage.verifyFilterPortfolio(),"portfoliofilter is not working");

	    }
	    
	    @Test(description = "TC230550-verify that when we click on that 5 usecase link it should go to usecasepage and the data should be match with the portfolio page")
	    public void verify5UsecaseLink()  {
	    	SoftAssert softAssert = new SoftAssert();
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(cxCloudAccountL2);
	    	lifecyclePage.refreshportfolio();
	    	HashMap<String, String> countOfUsecasepercentageAtportfolio = lifecyclePage.getUsecasePercentageAtPortfolioPage();
	    	softAssert.assertEquals(countOfUsecasepercentageAtportfolio, lifecyclePage.getUsecasePercentageAtAllUsecasePage(), "usecase percentage showing at the portfolio doesnt match with the adoption percentage in Allusecase page");
	    	softAssert.assertAll();
	    }

      @Step("Verify CX Level 1 Access")
	   public void verifyCXLevel1Access(LifecyclePage lifeCyclePage) {
	 	SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(lifeCyclePage.checkPublicCommunityDisplayed(), "Public Community is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkSuccessTrackCommunityDisplayed(), "Success Track community is not displayed");
    	//softAssert.assertTrue(lifeCyclePage.checkAskQandADisplayed(), "Ask Q&A session is displayed which is not expected for L1 user");
    	softAssert.assertTrue(lifeCyclePage.checkProductDocumentationDisplayed(), "Product Documentation is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkATXPanelDisplayed(), "Ask The Expert Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkACCPanelDisplayed(), "Accelerators Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkELearningPanelDisplayed(), "E-Learning Panel is not displayed");
    	softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certifiation Prep Panel is displayed which is not expected for L1 user");
    	softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practice Labs Panel is displayed which is not expected for L1 user");
    	softAssert.assertTrue(lifeCyclePage.checkPitstopChecklistDisplayed(), "Pitstop Checklist Items are not displayed");
    	softAssert.assertFalse(lifeCyclePage.getTotalNoOfCiscoACC()>0, "Cisco ACC is being displayed which is not expected for L1 user");
    	softAssert.assertAll();
	}

    @Step("Verify CX Level 2 Access")
	public void verifyCXLevel2Access(LifecyclePage lifeCyclePage) {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(lifeCyclePage.checkPublicCommunityDisplayed(), "Public Community is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkSuccessTrackCommunityDisplayed(), "Success Track community is not displayed");
    	//softAssert.assertTrue(lifeCyclePage.checkAskQandADisplayed(), "Ask Q&A session is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkProductDocumentationDisplayed(), "Product Documentation is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkATXPanelDisplayed(), "Ask The Expert Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkACCPanelDisplayed(), "Accelerators Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkELearningPanelDisplayed(), "E-Learning Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkCertificationPrepDisplayed(), "Certifiation Prep Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practice Labs Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkPitstopChecklistDisplayed(), "Pitstop Checklist Items are not displayed");
    	softAssert.assertTrue(lifeCyclePage.getTotalNoOfCiscoACC()>0, "Cisco ACC is not being displayed or No data is available for Cisco ACC");
    	softAssert.assertAll();
	}
    
    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvv57449")
    @Test(description="TC230342: Test user with 'Engineer-Assets L1' role does not have access to any of the Lifeycle features")
    public void verifyEngineerAssetsL1Role() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("assetUserL1","lifecycle")
			.switchCXCloudAccount(cxCloudAccountL1);
    	assertFalse("Lifecycle Tab is visible for Engineer-Assets User", lifeCyclePage.checkLifecycleTabAvailability());
    }

    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvv57449")
    @Test(description="TC230342: Test user with 'Engineer-Assets L2' role does not have access to any of the Lifeycle features")
    public void verifyEngineerAssetsL2Role() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("assetUserL2","lifecycle")
			.switchCXCloudAccount(cxCloudAccountL2);
    	assertFalse("Lifecycle Tab is visible for Engineer-Assets User", lifeCyclePage.checkLifecycleTabAvailability());
    }

    @Test(description = "T152570: Verify the entitlement at various track points of a user having CX level 2 access")
    public void verifyCxLevel2Entitlement() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	this.verifyCXLevel2Access(lifeCyclePage);
    }

    @Test(description = "TC230295: Test user with 'Super Admin' role has full L2 access of the lifecycle")
    public void verifySuperAdminCxLevel2Entitlement() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2);
    	assertTrue(lifeCyclePage.checkAdminButtonDisplayed(), "Admin button is not being displayed");
    	assertTrue(lifeCyclePage.verifyAdminAccessForTechnicalAdmin(), "Identity & Access Option is not displayed for Super Admin");
    	
    	lifeCyclePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel2Access(lifeCyclePage);
    }
    
    @Test(description = "TC230319: Test user with 'Read Only Users' role has access View only access to L1 level features")
    public void verifyReadOnlyUsersL1Access() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("readOnlyUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	softAssert.assertFalse(lifeCyclePage.checkAdminButtonDisplayed(), "Admin Button is being displayed which is not expected for Read Only Users");
    	softAssert.assertTrue(lifeCyclePage.checkPublicCommunityDisplayed(), "Public Community Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkProductDocumentationDisplayed(), "Product Documentation Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkPitstopChecklistDisplayed(), "Pitstop Checklist Items are not displayed");
    	softAssert.assertFalse(lifeCyclePage.checkSuccessTrackCommunityDisplayed(), "Success Track Community is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkAskQandADisplayed(), "Ask Q&A is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkATXPanelDisplayed(), "ATX Panel is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkACCPanelDisplayed(), "ACC Panel is being displayed which is not expected for Read Only Users");
    	softAssert.assertFalse(lifeCyclePage.checkELearningPanelDisplayed(), "E-Learning Panel is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certification Prep is being displayed which is not expected for Read Onlu users");
    	softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs panel is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.verifyPitstopActionsManagable(), "User able to check Manual Check List with Read Only users which is not expected");
    	softAssert.assertAll();
    }

    @Test(description = "TC230319: Test user with 'Read Only Users' role has access View only access to L2 level features")
    public void verifyReadOnlyUsersL2Access() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("readOnlyUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	softAssert.assertFalse(lifeCyclePage.checkAdminButtonDisplayed(), "Admin Button is being displayed which is not expected for Read Only Users");
    	softAssert.assertTrue(lifeCyclePage.checkPublicCommunityDisplayed(), "Public Community Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkProductDocumentationDisplayed(), "Product Documentation Panel is not displayed");
    	softAssert.assertTrue(lifeCyclePage.checkPitstopChecklistDisplayed(), "Pitstop Checklist Items are not displayed");
    	softAssert.assertFalse(lifeCyclePage.checkSuccessTrackCommunityDisplayed(), "Success Track Community is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkAskQandADisplayed(), "Ask Q&A is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkATXPanelDisplayed(), "ATX Panel is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkACCPanelDisplayed(), "ACC Panel is being displayed which is not expected for Read Only Users");
    	softAssert.assertFalse(lifeCyclePage.checkELearningPanelDisplayed(), "E-Learning Panel is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certification Prep is being displayed which is not expected for Read Onlu users");
    	softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs panel is being displayed which is not expected for Read Only users");
    	softAssert.assertFalse(lifeCyclePage.verifyPitstopActionsManagable(), "User able to check Manual Check List with Read Only users which is not expected");
    	softAssert.assertAll();
    }

    @Test(description = "T152469: Verify the entitlement for a user having CX level 1 access")
    public void verifyCXLevel1Entitlement() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	this.verifyCXLevel1Access(lifeCyclePage);
    }

    @Test(description = "TC230295: Test user with 'Super Admin' role has full L1 and L2 access of the lifecycle")
    public void verifySuperAdminCXLevel1Entitlement() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1);
    	assertTrue(lifeCyclePage.checkAdminButtonDisplayed(), "Admin button is not displayed");
    	assertTrue(lifeCyclePage.verifyAdminAccessForTechnicalAdmin(), "Identity & Access option is not present for Super Admin User");
    	lifeCyclePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel1Access(lifeCyclePage);
    }

    @Test(description = "T152564: Verify the entitlement at various track points of a user having CX level 1 access")
    public void verifyCXLevel1EntitlementForVariousTrackPoints() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	String currentHighlightedPitStop;
    	lifeCyclePage.login("superAdminL1","lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	String currentPitStopName = lifeCyclePage.getCurrentPitStopName();
    	currentHighlightedPitStop = currentPitStopName;
    	do {
    		System.out.println("Checking L1 Access for Pitstop: "+currentHighlightedPitStop);
    		this.verifyCXLevel1Access(lifeCyclePage);
    		lifeCyclePage.moveToNextPitstop();
    		currentHighlightedPitStop = lifeCyclePage.getHighlightedPitstopName();
    		System.out.println("Current Highlighted Pitstop Name: "+currentHighlightedPitStop);
    	} while (!currentHighlightedPitStop.equalsIgnoreCase(currentPitStopName));
    }

    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvv67320")
    @Test(description = "T152550: Verify the entitlement for a user not having any contract")
    public void verifyEntitlementForUserWithoutContract() {
    	SoftAssert softAssert = new SoftAssert();
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.loginWithUnauthorizedError("NoAccess");
    	
    	System.out.println("Logged in successfully");
    	softAssert.assertEquals(lifeCyclePage.getUnauthorizedUserHeadline(), "You don’t have access to this", "Generic Error Title is not displayed");
    	softAssert.assertEquals(lifeCyclePage.getUnathorizedErrorText(), "If you think this is an error, please contact your CX Cloud admin. They can help ensure you have the right permissions.", "Generic Error Sub Text is not displayed");
    	softAssert.assertEquals(lifeCyclePage.getLogoutUserRedirectWebsite(), "https://www-stage.cisco.com/autho/logout.html", "Redirect Website Details are not correct");
    	softAssert.assertAll();
    	System.out.println("Test Completed");
    }

    @Test(description = "TC230311: Test user with 'Engineer' role has full L2 access of the lifecycle")
    public void verifyEngineerRoleAccessForL2User() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();

    	//Checking for L2 User
    	lifeCyclePage.login("standardUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	assertFalse("Admin button is displayed which is not expected for User with 'Engineer' Role", lifeCyclePage.checkAdminButtonDisplayed());
    	this.verifyCXLevel2Access(lifeCyclePage);

    }

    @Test(description = "TC230311: Test user with 'Engineer' role has full L1 access of the lifecycle")
    public void verifyEngineerRoleAccessForL1User() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("standardUserL1", "lifecycle")
    			.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	assertFalse("Admin button is displayed which is not expected for User with 'Engineer' Role", lifeCyclePage.checkAdminButtonDisplayed());
    	this.verifyCXLevel1Access(lifeCyclePage);
    }

    @Test(description = "TC230554: verify that when the user navigates to landing page by clicking on the row ,the context selector header shows the path accordingly")
    public void verifyContextSelectorHeaderWhenClickOnRow() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login().switchCXCloudAccount(cxCloudAccountL2);
    	
    	lifeCyclePage.refreshportfolio();
    	String successTrackName = lifeCyclePage.getSuccessTrackUnderMyPortfolio();
    	String useCaseName = lifeCyclePage.getFirstUseCaseNameUnderMyPortfolioPage();
    	lifeCyclePage.clickOnFirstUseCaseUnderMyPortFolioPage();
    	softAssert.assertTrue(lifeCyclePage.getSuccessTrackNameinLifeCycleTile().equalsIgnoreCase(successTrackName), "Success Track Name is not displayed properly in Context Selector");
    	softAssert.assertEquals(lifeCyclePage.getUseCaseNameinLifecycleTile(), useCaseName, "Use Case Name is not displayed properly in Context Selector");
    	softAssert.assertAll();
    }

    @Test(description = "TC230303: Test user with 'Technical Admin' role has full L2 access of the lifecycle")
    public void verifyTechnicalAdminL2User() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("adminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2);
    	assertTrue(lifeCyclePage.checkAdminButtonDisplayed(), "Admin button is not displayed");
    	assertFalse("Identity and Access Option is present for Technical Admin which is not expected", lifeCyclePage.verifyAdminAccessForTechnicalAdmin());
    	
    	lifeCyclePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	this.verifyCXLevel2Access(lifeCyclePage);
    }

    @Test(description = "TC230303: Test user with 'Technical Admin' role has full L1 access of the lifecycle")
    public void verifyTechnicalAdminL1User() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("adminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1);
    	assertTrue(lifeCyclePage.checkAdminButtonDisplayed(), "Admin button is not displayed");
    	assertFalse("Identity and Access Option is present for Technical Admin which is not expected", lifeCyclePage.verifyAdminAccessForTechnicalAdmin());
    	
    	lifeCyclePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel1Access(lifeCyclePage);
    }

    @Test(description = "TC230553: Verify that when the user clicks on the raw ,it navigates to landing page with correseponding pitstop and checklist item shown as a title and teh page gets rearranged based on the checklist item")
    public void verifyLifecyclePageWhenClickOnRowFromMyPortfolioPage() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login().switchCXCloudAccount(cxCloudAccountL2);
    	
    	lifeCyclePage.refreshportfolio();
    	String successTrackName = lifeCyclePage.getSuccessTrackUnderMyPortfolio();
    	String useCaseName = lifeCyclePage.getFirstUseCaseNameUnderMyPortfolioPage();
    	lifeCyclePage.mouseHoverOnFirstNextChecklistItem();
    	String pitStopName = lifeCyclePage.getNextCheckListItemPitStopForFirstUseCase();
    	String nextCheckListItem = lifeCyclePage.getNextCheckListItemForFirstUseCase();
    	lifeCyclePage.clickOnFirstUseCaseUnderMyPortFolioPage();
    	lifeCyclePage.selectCarousel(CarouselName.LIFECYCLE);
    	softAssert.assertTrue(lifeCyclePage.getSuccessTrackNameinLifeCycleTile().equalsIgnoreCase(successTrackName), "Success Track Name is not displayed properly in Context Selector");
    	softAssert.assertEquals(lifeCyclePage.getUseCaseNameinLifecycleTile(), useCaseName, "Use Case Name is not displayed properly in Context Selector");
    	softAssert.assertEquals(lifeCyclePage.getCurrentPitStopName(), pitStopName, "Portfolio Page is not moved to corrct pit stop");
    	softAssert.assertEquals(lifeCyclePage.getSelectedChecklistInLifecyclePage(), nextCheckListItem, "Portfolio page is not moved to proper next checklist item");
    	softAssert.assertEquals(lifeCyclePage.getActiveCheckListItemFromLifeCyclePage(), nextCheckListItem, "Active Check List Item is not matching with the next checklist item");
    	softAssert.assertTrue(lifeCyclePage.checkActiveCheckListSubItemDisplayed(), "Sub Text is not displayed for the Active Check List");
    	softAssert.assertAll();
    }
    
    @Test(description = "TC230318: Test user with 'Engineer L2' role has access to view and Launch E-Learning, Certification Prep and Remote Practices Labs courses")
    public void verifyEngineerL2RoleAccessToLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	LearningPage learningPage = new LearningPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("standardUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
        String LastLaunchedDate = formatter.format(date);
        System.out.println("Today : " + LastLaunchedDate);

        if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");

        if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");

        if(!(learningPage.getAllRemotePracLabsTopicUnderLifeCycle()>0))
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");

    	//Checking from Home Page for e-Learning
    	learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
    	softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for e-Learning
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View e-Learning
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		//Checking from Home Page for Certification Prep
		learningPage.clickFirstTitleFromHomePage(LearningPage.CertPrepTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for Certification Prep
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.CertPrepCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View for Certification Prep
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		//Checking from Home Page for Remote Practices Labs
		learningPage.clickFirstTitleFromHomePage(LearningPage.RemotePracLabsTitleInHomePageDataAutoId);
		learningPage.waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching from Home Page");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getLastLaunchesDetailsFromHomePage(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Home Page");

		//Verifying Last Launched Details in Card View for Remote Practices Labs
		learningPage.clickRemotePracLabsViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewDataAutoID).waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Card View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromCardView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Card View");

		//Verifying Last Launched Details in Table View for Remote Practices Labs
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Table View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromTableView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Table View");

		softAssert.assertAll();
    }
    
    @Test(description = "TC230318: Test user with 'Engineer L1' role has access to view and Launch E-Learning, Certification Prep and Remote Practices Labs courses")
    public void verifyEngineerL1RoleAccessToLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	LearningPage learningPage = new LearningPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("standardUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

        if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");

    	//Checking from Home Page for e-Learning
    	learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
    	softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for e-Learning
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View e-Learning
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certification Prep Panel is displayed for Level 1 User");
		softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs Panel is displayed for Level 1 User");

		softAssert.assertAll();
    }

    @Test(description = "TC230317: Test user with 'Engineer L2' role has access to view Success Tips")
    public void verifyEngineerL2RoleAccessToProductDocumentation() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("standardUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");
    	
    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }
    
    @Test(description = "TC230317: Test user with 'Engineer L2' role has access to view Success Tips")
    public void verifyEngineerL1RoleAccessToProductDocumentation() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("standardUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");

    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }

    @Test(description = "TC230324: Test user with 'Read Only Users L1' role does not have access to attend Success Track community Q&A session")
    public void verifyReadOnlyUserL1CannotAccessQandASession() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("readOnlyUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	assertFalse("Success Track Q&A session is enabled for Read Only users which is not expected", lifeCyclePage.checkAskQandADisplayed());
    }

    @Test(description = "TC230324: Test user with 'Read Only Users L2' role does not have access to attend Success Track community Q&A session")
    public void verifyReadOnlyUserL2CannotAccessQandASession() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("readOnlyUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	assertFalse("Success Track Q&A session is enabled for Read Only users which is not expected", lifeCyclePage.checkAskQandADisplayed());
    }
    
    @Test(description = "TC230328: Test user with 'Read Only Users L1' role does not have access to view E-Learning, Certification Prep and Remote Practice Labs")
    public void verifyReadOnlyUserL1CannotAccessLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("readOnlyUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	softAssert.assertFalse(lifeCyclePage.checkELearningPanelDisplayed(), "E-Learning Panel is enabled for Read Only users which is not expected");
    	softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certification Prep Panel is enabled for Read Only users which is not expected");
    	softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs Panel is enabled for Read Only users which is not expected");
    	softAssert.assertAll();
    }

    @Test(description = "TC230328: Test user with 'Read Only Users L2' role does not have access to view E-Learning, Certification Prep and Remote Practice Labs")
    public void verifyReadOnlyUserL2CannotAccessLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("readOnlyUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	softAssert.assertFalse(lifeCyclePage.checkELearningPanelDisplayed(), "E-Learning Panel is enabled for Read Only users which is not expected");
    	softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certification Prep Panel is enabled for Read Only users which is not expected");
    	softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs Panel is enabled for Read Only users which is not expected");
    	softAssert.assertAll();
    }
    
    @Test(description = "TC230321: Test user with 'Read Only Users L1' role has access to view Success Tips")
    public void verifyReadOnlyUsersL1HasAccessToProductDoc() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("readOnlyUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");

    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }

    @Test(description = "TC230321: Test user with 'Read Only Users L2' role has access to view Success Tips")
    public void verifyReadOnlyUsersL2HasAccessToProductDoc() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("readOnlyUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");
    	
    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }
    
    @Test(description = "TC230302: Test user with 'Super Admin L2' role has access to view and Launch E-Learning, Certification Prep and Remote Practices Labs courses")
    public void verifySuperAdminL2HasAccessToLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	LearningPage learningPage = new LearningPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
        String LastLaunchedDate = formatter.format(date);
        System.out.println("Today : " + LastLaunchedDate);

        if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");

        if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");

        if(!(learningPage.getAllRemotePracLabsTopicUnderLifeCycle()>0))
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");

    	//Checking from Home Page for e-Learning
    	learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
    	softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for e-Learning
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View e-Learning
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		//Checking from Home Page for Certification Prep
		learningPage.clickFirstTitleFromHomePage(LearningPage.CertPrepTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for Certification Prep
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.CertPrepCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View for Certification Prep
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		//Checking from Home Page for Remote Practices Labs
		learningPage.clickFirstTitleFromHomePage(LearningPage.RemotePracLabsTitleInHomePageDataAutoId);
		learningPage.waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching from Home Page");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getLastLaunchesDetailsFromHomePage(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Home Page");

		//Verifying Last Launched Details in Card View for Remote Practices Labs
		learningPage.clickRemotePracLabsViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewDataAutoID).waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Card View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromCardView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Card View");

		//Verifying Last Launched Details in Table View for Remote Practices Labs
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Table View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromTableView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Table View");

		softAssert.assertAll();
    }
    
    @Test(description = "TC230302: Test user with 'Super Admin L1' role has access to view and Launch E-Learning, Certification Prep and Remote Practices Labs courses")
    public void verifySuperAdminL1HasAccessToLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	LearningPage learningPage = new LearningPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

        if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");

    	//Checking from Home Page for e-Learning
    	learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
    	softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for e-Learning
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View e-Learning
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certification Prep Panel is displayed for Level 1 User");
		softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs Panel is displayed for Level 1 User");

		softAssert.assertAll();
    }

    @Test(description = "TC230301: Test user with 'Super Admin L2' role has access to view Success Tips")
    public void verifySuperAdminL2HasAccessToProductDoc() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");
    	
    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }
    
    @Test(description = "TC230301: Test user with 'Super Admin L1' role has access to view Success Tips")
    public void verifySuperAdminL1HasAccessToProductDoc() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");

    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }

    @Test(description = "TC230309: Test user with 'Technical Admin L2' role has access to view Success Tips")
    public void verifyTechnicalAdminL2HasAccessToProductDoc() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("adminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");

    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }

    @Test(description = "TC230309: Test user with 'Technical Admin L1' role has access to view Success Tips")
    public void verifyTechnicalAdminL1HasAccessToProductDoc() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SuccessTipsPage successTipsPage = new SuccessTipsPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("adminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	if(!(successTipsPage.getAllProdDocTopicUnderLifeCycle()>0))
    		throw new SkipException("No Product Documentation Topics are available to test this scenario");
    	
    	//Checking for access to Product Documentation from Home Page
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInHomePage(), 2, "User not able to Launch Product Documentation courses from Home Page");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Card View
    	successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInCardView(), 2, "User not able to Launch Product Documentation courses from View All Card View");
    	successTipsPage.switchTab().switchToMainTab();

    	//Checking for access to Product Documentation from View All Table View
    	successTipsPage.clickSuccessTipsTableModalButton();
    	softAssert.assertEquals(successTipsPage.getNoOfTabsWhileClickingOnFirstTitleInTableView(), 2, "User not able to Launch Product Documentation courses from View All Table View");
    	successTipsPage.switchTab().switchToMainTab();

    	softAssert.assertAll();
    }

    @Test(description = "TC230298: Test user with 'Super Admin L2 User' role has full access to check off Checklist/step within a pitstop")
    public void verifySuperAdminL2CanManagePitstop() {
    	RaceTrackPage raceTrackPage = new RaceTrackPage();
    	raceTrackPage.login("superAdminL2", "lifecycle")
    			.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	assertTrue(raceTrackPage.verifyPitstopActionsWithManualCheckAllowedTrue(), "User is not able to check the checkbox with manualCheckAllowed True");
    }

    @Test(description = "TC230298: Test user with 'Super Admin L1 User' role has full access to check off Checklist/step within a pitstop")
    public void verifySuperAdminL1CanManagePitstop() {
    	RaceTrackPage raceTrackPage = new RaceTrackPage();
    	raceTrackPage.login("superAdminL1", "lifecycle")
    			.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	assertTrue(raceTrackPage.verifyPitstopActionsWithManualCheckAllowedTrue(), "User is not able to check the checkbox with manualCheckAllowed True");
    }

    @Test(description = "TC230310: Test user with 'Technical Admin L2 User' role has access to view and Launch E-Learning, Certification Prep and Remote Practices Labs courses")
    public void verifyTechnicalAdminL2HasAccessToLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	LearningPage learningPage = new LearningPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("adminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
        String LastLaunchedDate = formatter.format(date);
        System.out.println("Today : " + LastLaunchedDate);

        if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");

        if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");

        if(!(learningPage.getAllRemotePracLabsTopicUnderLifeCycle()>0))
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");


    	//Checking from Home Page for e-Learning
    	learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
    	softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for e-Learning
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View e-Learning
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		//Checking from Home Page for Certification Prep
		learningPage.clickFirstTitleFromHomePage(LearningPage.CertPrepTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for Certification Prep
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.CertPrepCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View for Certification Prep
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		//Checking from Home Page for Remote Practices Labs
		learningPage.clickFirstTitleFromHomePage(LearningPage.RemotePracLabsTitleInHomePageDataAutoId);
		learningPage.waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching from Home Page");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getLastLaunchesDetailsFromHomePage(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Home Page");

		//Verifying Last Launched Details in Card View for Remote Practices Labs
		learningPage.clickRemotePracLabsViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewDataAutoID).waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Card View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromCardView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Card View");

		//Verifying Last Launched Details in Table View for Remote Practices Labs
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Table View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromTableView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Table View");

		softAssert.assertAll();
    }

    @Test(description = "TC230310: Test user with 'Technical Admin L1 User' role has access to view and Launch E-Learning, Certification Prep and Remote Practices Labs courses")
    public void verifyTechnicalAdminL1HasAccessToLearningContent() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	LearningPage learningPage = new LearningPage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("adminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

        if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");

    	//Checking from Home Page for e-Learning
    	learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId).clickButton(ButtonName.LAUNCH_COURSE);
    	softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Home Page");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Card View for e-Learning
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton().clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Card View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();

		//Checking from View All Table View e-Learning
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView().clickButton(ButtonName.LAUNCH_COURSE);
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from View All Table View");
		learningPage.switchTab().switchToMainTab();
		learningPage.close360View();
		learningPage.closeViewAllScreen();

		softAssert.assertFalse(lifeCyclePage.checkCertificationPrepDisplayed(), "Certification Prep Panel is displayed for Level 1 User");
		softAssert.assertFalse(lifeCyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs Panel is displayed for Level 1 User");

		softAssert.assertAll();
    }

    @Test(description = "TC230306: Test user with 'Technical Admin L1 user' role has full access to check off 'Checklist/step' within a pitstop")
    public void verifyTechnicalAdminL1UserCanManagePitstop() {
    	RaceTrackPage raceTrackPage = new RaceTrackPage();
    	raceTrackPage.login("adminL1", "lifecycle")
    			.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	assertTrue(raceTrackPage.verifyPitstopActionsWithManualCheckAllowedTrue(), "User is not able to check the checkbox with manualCheckAllowed True");
    }

    @Test(description = "TC230306: Test user with 'Technical Admin L2 user' role has full access to check off 'Checklist/step' within a pitstop")
    public void verifyTechnicalAdminL2UserCanManagePitstop() {
    	RaceTrackPage raceTrackPage = new RaceTrackPage();
    	raceTrackPage.login("adminL2", "lifecycle")
    			.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	assertTrue(raceTrackPage.verifyPitstopActionsWithManualCheckAllowedTrue(), "User is not able to check the checkbox with manualCheckAllowed True");
    }
	    @Test(description = "TC230546-Verify that the My network view changes according to how many successtrack the user is assigned to")
	    public void verifyPorfolioview()  {
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(cxCloudAccountL2);
	    	lifecyclePage.refreshportfolio();
	    	assertTrue(lifecyclePage.verifyportfoliosuccesstracks(),"portfoliofilter is not showing all the successtracks the user is assigned with.");
	    }
	    
	    @Test(description = "TC230549,Tm5559177c-verify that ADOPTION JOURNEY is written as a title in the portfolio page and when we click on verall progress question mark a pop up opens with text")
	    public void verifyPorfolioTitle()  {
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(cxCloudAccountL2);
	    	lifecyclePage.refreshportfolio();
	    	assertTrue(lifecyclePage.verifyportflioTitle(),"portfolio title not showing and overall progress question mark is content is not the expected data");
	    }
	    
	    @Test(description = "TC230545,Tm5559182c-Verify that when the user logins with multiple buid ,and when the filter is selected n the portfolio page,filter name is shown as a subtitle", groups = {"singleThread"})
	    public void verifyPorfolioFilterTitle() throws InterruptedException  {
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
	        		.switchCXCloudAccount(multipleBuidCloudAccount);
	    	lifecyclePage.refreshportfolio();
	    	assertTrue(lifecyclePage.verifyFilterTitleportflio(),"portfolio subtitle is not same as the filtername");
	    }
	    
	    @Test(description = "TC230556-Verify that when a customer having multiple BUID logins to customer portal ,a fliter to select the BUID is shown in the lifecycle page", groups = {"singleThread"})
	    public void verifyFilterHomePage()  {
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
    		.switchCXCloudAccount(multipleBuidCloudAccount)
            .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
            .selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
            .selectCarousel(CarouselName.LIFECYCLE);
	    	assertFalse(lifecyclePage.verifyFilterHome(),"portfoliofilter is not working");
	  	    }
	    
	    @Test(description = "TC230548,Tm5559184c-Verify that when the user mousehover on the rider a pop up with next checklist item and pitstop name are showing.")
	    public void verifyTooltipPortfolio()  {
	    	LifecyclePage lifecyclePage = new LifecyclePage();
	    	lifecyclePage.login()
    		.switchCXCloudAccount(cxCloudAccountL2);
	        lifecyclePage.refreshportfolio();
	        assertTrue(lifecyclePage.getTooltipPortfolioRaw(),"tooltipa are not showing when mousehovering/shown the checklist item is not the correct one");
	  	    }


//


    @Test(description = "T152552: Verify the entitlement for a user having CX level 1 access with different use cases")
    public void verifyL1EntitlementForDifferentUseCases() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);

    	//Verifying for Network Device Onboarding
    	System.out.println("Verifying for " + networkDeviceOnboardingUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel1Access(lifeCyclePage);

    	//Verifying for Campus Network Assurance
    	System.out.println("Verifying for " + campusNetworkAssuranceUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel1Access(lifeCyclePage);

    	//Verifying for Scalable Access Policy
    	System.out.println("Verifying for " + ScalableAccessPolicyUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, ScalableAccessPolicyUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel1Access(lifeCyclePage);

    	//Verifying for Campus Software Image Management
    	System.out.println("Verifying for " + CampusSoftwareImageManagementUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, CampusSoftwareImageManagementUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel1Access(lifeCyclePage);

    	//Verifying for Campus Network Segmentation
    	System.out.println("Verifying for " + CampusNetworkSegmentationUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel1Access(lifeCyclePage);
    }

    @Test(description = "T152554: Verify the entitlement for a user having Cx level 2 access with different use cases")
    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvv99768")
    public void verifyL2EntitlementForDifferentUseCases() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	lifeCyclePage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);

    	//Verifying for Network Device Onboarding
    	System.out.println("Verifying for " + networkDeviceOnboardingUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel2Access(lifeCyclePage);

    	//Verifying for Campus Network Assurance
    	System.out.println("Verifying for " + campusNetworkAssuranceUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel2Access(lifeCyclePage);

    	//Verifying for Scalable Access Policy
    	System.out.println("Verifying for " + ScalableAccessPolicyUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, ScalableAccessPolicyUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel2Access(lifeCyclePage);

    	//Verifying for Campus Software Image Management
    	System.out.println("Verifying for " + CampusSoftwareImageManagementUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, CampusSoftwareImageManagementUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel2Access(lifeCyclePage);

    	//Verifying for Campus Network Segmentation
    	System.out.println("Verifying for " + CampusNetworkSegmentationUseCase + " Use Case");
    	lifeCyclePage.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase).selectCarousel(CarouselName.LIFECYCLE);
    	this.verifyCXLevel2Access(lifeCyclePage);
    }

    @Test(description = "T152566: Verify the entitlement when checklists are checked for user having cx level 1 access")
    public void verifyChecklistPercentageForCXLevel1User() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	String currentHighLightedPitStop = "";
    	String currentPitStopName = lifeCyclePage.getCurrentPitStopName();
		currentHighLightedPitStop = currentPitStopName;
		for(int i = 0; i <= PitStopsName.values().length; i++)
		{
			if(currentHighLightedPitStop.equalsIgnoreCase(PitStopsName.Onboard.toString())) {
				lifeCyclePage.moveToNextPitstop();
				continue;
			}
			softAssert.assertTrue(lifeCyclePage.checkChecklistPercentageDisplayed(), "Checklist Progress Bar and Checklist Percentage is not displayed");
			lifeCyclePage.moveToNextPitstop();
			currentHighLightedPitStop = lifeCyclePage.getHighlightedPitstopName();
			System.out.println(currentHighLightedPitStop);
		}

		softAssert.assertAll();
    }

    @Test(description = "T152572: Verify the entitlement when checklists are checked for a user having CX level2 access")
    public void verifyChecklistPercentageForCXLevel2User() {
    	LifecyclePage lifeCyclePage = new LifecyclePage();
    	SoftAssert softAssert = new SoftAssert();
    	lifeCyclePage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	String currentHighLightedPitStop = "";
    	String currentPitStopName = lifeCyclePage.getCurrentPitStopName();
		currentHighLightedPitStop = currentPitStopName;
		for(int i = 0; i <= PitStopsName.values().length; i++)
		{
			if(currentHighLightedPitStop.equalsIgnoreCase(PitStopsName.Onboard.toString())) {
				lifeCyclePage.moveToNextPitstop();
				continue;
			}
			softAssert.assertTrue(lifeCyclePage.checkChecklistPercentageDisplayed(), "Checklist Progress Bar and Checklist Percentage is not displayed");
			lifeCyclePage.moveToNextPitstop();
			currentHighLightedPitStop = lifeCyclePage.getHighlightedPitstopName();
			System.out.println(currentHighLightedPitStop);
		}

		softAssert.assertAll();
    }

    @Test(description = "TC230297: Test user with 'Super Admin L2' role has full access to Accelerator")
    public void verifySuperAdminL2HasFullAccessForACC() {
    	AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
    	SoftAssert softAssert = new SoftAssert();

    	acceleratorsPage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
        	throw new SkipException("No ACC available under Lifecycle to test this scenario");

        if(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0) {
        	//Checking in Card View
        	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

        	String titleFromCardView = acceleratorsPage.getAccFirstTitleFromCardView();
        	acceleratorsPage.clickOnAccFirstTitleFromCardView();
        	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
        	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), titleFromCardView, "Title in 360 View is not matching with the title in Card View");
        	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
        	softAssert.assertTrue(acceleratorsPage.checkEngagementsIn360ViewDisplayed(), "Engagement details are not available in Card View 360");
        	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
        	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
        	acceleratorsPage.close360View();

        	//Checking from Table View
        	acceleratorsPage.clickACCTableViewButton();
        	String titleFromTableView = acceleratorsPage.getAccFirstTitleFromTableView();
        	acceleratorsPage.clickOnAccFirstTitleFromTableView();
        	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
        	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), titleFromTableView, "Title in 360 View is not matching with the title in Table View");
        	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
        	softAssert.assertTrue(acceleratorsPage.checkEngagementsIn360ViewDisplayed(), "Engagement details are not available in Table View 360");
        	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
        	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");
        	acceleratorsPage.close360View();
        	acceleratorsPage.clickACCCardViewButton();
        	if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
        		System.out.println("Completed ACC Available");
        		acceleratorsPage.clickOnACCWithCompletedStatus();
        		softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
        		softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
        	}
        	acceleratorsPage.closeViewAllScreen();
        } else {
        	System.out.println("Not able to verify Cisco ACC as there is no data available");
        }

    	if(acceleratorsPage.checkPartnerContentDisplayed("Content Provider")) {

    		acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnerName);
    		//Checking in Card View
    		acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

    		acceleratorsPage.clickOnAccFirstTitleFromCardView();
    		assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
    		softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromCardView(), "Title in 360 View is not matching with the title in Card View");
    		softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
    		softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllCardView(), "Partner Name in 360 view is not matching with the name in Card View");
    		softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
    		softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
    		acceleratorsPage.close360View();

    		//Checking from Table View
    		acceleratorsPage.clickACCTableViewButton();
    		acceleratorsPage.clickOnAccFirstTitleFromTableView();
    		assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
    		softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromTableView(), "Title in 360 View is not matching with the title in Table View");
    		softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
    		softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllTableView(), "Partner Name in 360 view is not matching with the name in Table view");
    		softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
    		softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");

    		acceleratorsPage.close360View();
    		acceleratorsPage.clickACCCardViewButton();
    		if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
    			acceleratorsPage.clickOnACCWithCompletedStatus();
    			softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
    			softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
    		}
    	} else {
    		System.out.println("Not able to verify Partner ACC as there is no data available");
    	}
    	softAssert.assertAll();
    }

    /*@Test(description = "TC230297: Test user with 'Super Admin L1' role has full access to Accelerator")
    public void verifySuperAdminL1HasFullAccessForACC() {
    	AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
    	SoftAssert softAssert = new SoftAssert();

    	acceleratorsPage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
        	throw new SkipException("No ACC available under Lifecycle to test this scenario");

        assertTrue(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0, "Cisco ACC should not be displayed for L1 User");


    	if(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnerName) <= 0)
    		throw new SkipException("No Partner ACC available to test this scenario");

    	//Checking in Card View
    	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

    	acceleratorsPage.clickOnAccFirstTitleFromCardView();
    	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
    	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromCardView(), "Title in 360 View is not matching with the title in Card View");
    	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
    	softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllCardView(), "Partner Name in 360 view is not matching with the name in Card View");
    	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
    	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
    	acceleratorsPage.close360View();

    	//Checking from Table View
    	acceleratorsPage.clickACCTableViewButton();
    	acceleratorsPage.clickOnAccFirstTitleFromTableView();
    	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
    	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromTableView(), "Title in 360 View is not matching with the title in Table View");
    	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
    	softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllTableView(), "Partner Name in 360 view is not matching with the name in Table view");
    	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
    	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");

    	acceleratorsPage.close360View();
    	acceleratorsPage.clickACCCardViewButton();
    	if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
    		acceleratorsPage.clickOnACCWithCompletedStatus();
    		softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
    		softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
    	}
    	softAssert.assertAll();
    }*/

    @Test(description = "TC230313: Tets user with 'Engineer L2' role has full access to Accelerators")
    public void verifyStandardUserL2HasFullAccessForACC() {
    	AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
    	SoftAssert softAssert = new SoftAssert();

    	acceleratorsPage.login("standardUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
        	throw new SkipException("No ACC available under Lifecycle to test this scenario");

        if(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0) {
        	//Checking in Card View
        	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

        	String titleFromCardView = acceleratorsPage.getAccFirstTitleFromCardView();
        	acceleratorsPage.clickOnAccFirstTitleFromCardView();
        	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
        	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), titleFromCardView, "Title in 360 View is not matching with the title in Card View");
        	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
        	softAssert.assertTrue(acceleratorsPage.checkEngagementsIn360ViewDisplayed(), "Engagement details are not available in Card View 360");
        	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
        	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
        	acceleratorsPage.close360View();

        	//Checking from Table View
        	acceleratorsPage.clickACCTableViewButton();
        	String titleFromTableView = acceleratorsPage.getAccFirstTitleFromTableView();
        	acceleratorsPage.clickOnAccFirstTitleFromTableView();
        	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
        	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), titleFromTableView, "Title in 360 View is not matching with the title in Table View");
        	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
        	softAssert.assertTrue(acceleratorsPage.checkEngagementsIn360ViewDisplayed(), "Engagement details are not available in Table View 360");
        	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
        	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");
        	acceleratorsPage.close360View();
        	acceleratorsPage.clickACCCardViewButton();
        	if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
        		System.out.println("Completed ACC Available");
        		acceleratorsPage.clickOnACCWithCompletedStatus();
        		softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
        		softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
        	}
        	acceleratorsPage.closeViewAllScreen();
        } else {
        	System.out.println("Not able to verify Cisco ACC as there is no data available");
        }

    	if(acceleratorsPage.checkPartnerContentDisplayed("Content Provider")) {

    		acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnerName);
    		//Checking in Card View
    		acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

    		acceleratorsPage.clickOnAccFirstTitleFromCardView();
    		assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
    		softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromCardView(), "Title in 360 View is not matching with the title in Card View");
    		softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
    		softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllCardView(), "Partner Name in 360 view is not matching with the name in Card View");
    		softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
    		softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
    		acceleratorsPage.close360View();

    		//Checking from Table View
    		acceleratorsPage.clickACCTableViewButton();
    		acceleratorsPage.clickOnAccFirstTitleFromTableView();
    		assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
    		softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromTableView(), "Title in 360 View is not matching with the title in Table View");
    		softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
    		softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllTableView(), "Partner Name in 360 view is not matching with the name in Table view");
    		softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
    		softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");

    		acceleratorsPage.close360View();
    		acceleratorsPage.clickACCCardViewButton();
    		if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
    			acceleratorsPage.clickOnACCWithCompletedStatus();
    			softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
    			softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
    		}
    	} else {
    		System.out.println("Not able to verify Partner ACC as there is no data available");
    	}
    	softAssert.assertAll();
    }

    /*@Test(description = "TC230313: Tets user with 'Engineer L1' role has full access to Accelerators")
    public void verifyStandardUserL1HasFullAccessForACC() {
    	AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
    	SoftAssert softAssert = new SoftAssert();

    	acceleratorsPage.login("standardUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
        	throw new SkipException("No ACC available under Lifecycle to test this scenario");

        assertTrue(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0, "Cisco ACC should not be displayed for L1 User");


    	if(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnerName) <= 0)
    		throw new SkipException("No Partner ACC available to test this scenario");

    	//Checking in Card View
    	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

    	acceleratorsPage.clickOnAccFirstTitleFromCardView();
    	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
    	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromCardView(), "Title in 360 View is not matching with the title in Card View");
    	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
    	softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllCardView(), "Partner Name in 360 view is not matching with the name in Card View");
    	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
    	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
    	acceleratorsPage.close360View();

    	//Checking from Table View
    	acceleratorsPage.clickACCTableViewButton();
    	acceleratorsPage.clickOnAccFirstTitleFromTableView();
    	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
    	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromTableView(), "Title in 360 View is not matching with the title in Table View");
    	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
    	softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllTableView(), "Partner Name in 360 view is not matching with the name in Table view");
    	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
    	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");

    	acceleratorsPage.close360View();
    	acceleratorsPage.clickACCCardViewButton();
    	if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
    		acceleratorsPage.clickOnACCWithCompletedStatus();
    		softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
    		softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
    	}
    	softAssert.assertAll();
    }*/

    @Test(description = "TC230305: Tets user with 'Technical Admin L2' role has full access to Accelerators")
    public void verifyTechnicalAdminL2HasFullAccessForACC() {
    	AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
    	SoftAssert softAssert = new SoftAssert();

    	acceleratorsPage.login("adminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
        	throw new SkipException("No ACC available under Lifecycle to test this scenario");

        if(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0) {
        	//Checking in Card View
        	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

        	String titleFromCardView = acceleratorsPage.getAccFirstTitleFromCardView();
        	acceleratorsPage.clickOnAccFirstTitleFromCardView();
        	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
        	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), titleFromCardView, "Title in 360 View is not matching with the title in Card View");
        	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
        	softAssert.assertTrue(acceleratorsPage.checkEngagementsIn360ViewDisplayed(), "Engagement details are not available in Card View 360");
        	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
        	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
        	acceleratorsPage.close360View();

        	//Checking from Table View
        	acceleratorsPage.clickACCTableViewButton();
        	String titleFromTableView = acceleratorsPage.getAccFirstTitleFromTableView();
        	acceleratorsPage.clickOnAccFirstTitleFromTableView();
        	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
        	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), titleFromTableView, "Title in 360 View is not matching with the title in Table View");
        	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
        	softAssert.assertTrue(acceleratorsPage.checkEngagementsIn360ViewDisplayed(), "Engagement details are not available in Table View 360");
        	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
        	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");
        	acceleratorsPage.close360View();
        	acceleratorsPage.clickACCCardViewButton();
        	if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
        		System.out.println("Completed ACC Available");
        		acceleratorsPage.clickOnACCWithCompletedStatus();
        		softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
        		softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
        	}
        	acceleratorsPage.closeViewAllScreen();
        } else {
        	System.out.println("Not able to verify Cisco ACC as there is no data available");
        }

    	if(acceleratorsPage.checkPartnerContentDisplayed("Content Provider")) {

    		acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnerName);
    		//Checking in Card View
    		acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

    		acceleratorsPage.clickOnAccFirstTitleFromCardView();
    		assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
    		softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromCardView(), "Title in 360 View is not matching with the title in Card View");
    		softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
    		softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllCardView(), "Partner Name in 360 view is not matching with the name in Card View");
    		softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
    		softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
    		acceleratorsPage.close360View();

    		//Checking from Table View
    		acceleratorsPage.clickACCTableViewButton();
    		acceleratorsPage.clickOnAccFirstTitleFromTableView();
    		assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
    		softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromTableView(), "Title in 360 View is not matching with the title in Table View");
    		softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
    		softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllTableView(), "Partner Name in 360 view is not matching with the name in Table view");
    		softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
    		softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");

    		acceleratorsPage.close360View();
    		acceleratorsPage.clickACCCardViewButton();
    		if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
    			acceleratorsPage.clickOnACCWithCompletedStatus();
    			softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
    			softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
    		}
    	} else {
    		System.out.println("Not able to verify Partner ACC as there is no data available");
    	}
    	softAssert.assertAll();
    }

    /*@Test(description = "TC230305: Tets user with 'Technical Admin L1' role has full access to Accelerators")
    public void verifyTechnicalAdminL1HasFullAccessForACC() {
    	AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
    	SoftAssert softAssert = new SoftAssert();

    	acceleratorsPage.login("adminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

    	if(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
        	throw new SkipException("No ACC available under Lifecycle to test this scenario");

        assertTrue(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0, "Cisco ACC should not be displayed for L1 User");

    	if(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnerName) <= 0)
    		throw new SkipException("No Partner ACC available to test this scenario");

    	//Checking in Card View
    	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

    	acceleratorsPage.clickOnAccFirstTitleFromCardView();
    	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Card View");
    	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromCardView(), "Title in 360 View is not matching with the title in Card View");
    	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
    	softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllCardView(), "Partner Name in 360 view is not matching with the name in Card View");
    	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Card View 360");
    	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Card View 360");
    	acceleratorsPage.close360View();

    	//Checking from Table View
    	acceleratorsPage.clickACCTableViewButton();
    	acceleratorsPage.clickOnAccFirstTitleFromTableView();
    	assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened in Table View");
    	softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getAccFirstTitleFromTableView(), "Title in 360 View is not matching with the title in Table View");
    	softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
    	softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInViewAllTableView(), "Partner Name in 360 view is not matching with the name in Table view");
    	softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in Table View 360");
    	softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in Table View 360");

    	acceleratorsPage.close360View();
    	acceleratorsPage.clickACCCardViewButton();
    	if(acceleratorsPage.checkACCAvailabilityWithCompletedStatus()) {
    		acceleratorsPage.clickOnACCWithCompletedStatus();
    		softAssert.assertTrue(acceleratorsPage.checkThumbsUpButtonDisplayed(), "Thumbs Up Button is not displayed");
    		softAssert.assertTrue(acceleratorsPage.checkThumbsDownButtonDisplayed(), "Thumbs Down Button is not displayed");
    	}
    	softAssert.assertAll();
    }*/

    @Test(description = "TC230300: Test user with 'Super Admin' role has access to join Success Track community Q&A session")
    public void verifySuperAdminAbleToJoinQandASession() {
    	CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();

    	ciscoCommunityPage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

		ciscoCommunityPage.loadSuccessTrackCommunityURLWithCred().switchToMainTab();
    	String topicNameFromLifeCyclePage = ciscoCommunityPage.getQandATopicNameFromLifecyclePage();
    	System.out.println("Lifecycle Page:" + topicNameFromLifeCyclePage);
    	ciscoCommunityPage.clickOnQandASession().clickOnAskQandAJoinDiscussionButton().switchTab();
    	String topicNameFromCommunityPage = ciscoCommunityPage.getQandATopicNameFromCommunityPage();
    	System.out.println("Community Page:" + topicNameFromCommunityPage);
    	assertTrue(!topicNameFromLifeCyclePage.equalsIgnoreCase(topicNameFromCommunityPage), "Session Name in Community Page is not matching with the name provided in Lifecycle Page");
    }
    
    @Test(description = "TC230312: Test user with 'Engineer L2' role has full access to Ask The Experts(ATX)")
    public void verifyStandardUserL2HasFullAccessToATX() {
    	AskTheExpertsPage askTheExpertsPage = new AskTheExpertsPage();
    	AskTheExpertsPageIT askTheExpertsPageIT = new AskTheExpertsPageIT();
    	ATX_DATA_LIST_CISCO = getATXDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, STANDARD_USER_ROLE);
    	askTheExpertsPageIT.CISCO_ATX_DATA = getATXData(ATX_DATA_LIST_CISCO, itemsItem -> null == itemsItem.getProviderInfo() && itemsItem.getSessions().size() > 0);
    	SoftAssert softAssert = new SoftAssert();
    	askTheExpertsPage.login("standardUserL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	String atxTitle = askTheExpertsPageIT.CISCO_ATX_DATA.getItems().get(0).getTitle();
    	askTheExpertsPage.clickATXViewAll().clickATXCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	
    	//Registering a Session
    	String sessionTime = askTheExpertsPage.scheduleFirstSession();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase("Scheduled on "+sessionTime), "Not able to Register ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Cancelling a session
    	askTheExpertsPage.cancelSheduledSessionInCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase(""), "Not able to Cancel the ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Providing Feedback for Completed ATX
    	try {
    		COMPLETED_CISCO_ATX_ITEM = getATXData(askTheExpertsPageIT.ATX_DATA_LIST_CISCO, itemsItem -> itemsItem.getStatus().equalsIgnoreCase("Completed"));
    		if(COMPLETED_CISCO_ATX_ITEM.getItems().size() > 0) {
        		String feedbackSelectedBefore = askTheExpertsPage.getSelectedFeedbackOption();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Up"))
            		askTheExpertsPage.provideThumbsDownFeedback();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Down") || feedbackSelectedBefore.equalsIgnoreCase("No Feedback Selected"))
            		askTheExpertsPage.provideThumbsUpFeedback();
            	askTheExpertsPage.clickOnFirstCompletedATX();
            	String feedbackSelectedAfter = askTheExpertsPage.getSelectedFeedbackOption();
            	softAssert.assertFalse(feedbackSelectedBefore.equalsIgnoreCase(feedbackSelectedAfter), "Not able to Submit feedback");
        	}
    	} catch (NullPointerException e) {
    		System.out.println("No Completed ATX Session available to test Feedback");
		}
    	softAssert.assertAll();
    }
    
    @Test(description = "TC230312: Test user with 'Engineer L1' role has full access to Ask The Experts(ATX)")
    public void verifyStandardUserL1HasFullAccessToATX() {
    	AskTheExpertsPage askTheExpertsPage = new AskTheExpertsPage();
    	AskTheExpertsPageIT askTheExpertsPageIT = new AskTheExpertsPageIT();
    	askTheExpertsPageIT.dataSetup();
    	COMPLETED_CISCO_ATX_ITEM = getATXData(askTheExpertsPageIT.ATX_DATA_LIST_CISCO, itemsItem -> null != itemsItem.getProviderInfo() && itemsItem.getStatus().equalsIgnoreCase("Completed"));
    	SoftAssert softAssert = new SoftAssert();
    	askTheExpertsPage.login("standardUserL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, askTheExpertsPageIT.CISCO_ATX_DATA.getUsecase())
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	String atxTitle = askTheExpertsPageIT.CISCO_ATX_DATA.getItems().get(0).getTitle();
    	askTheExpertsPage.changePitStop(PitStopsName.Implement);
    	askTheExpertsPage.clickATXViewAll().clickATXCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	
    	//Registering a Session
    	String sessionTime = askTheExpertsPage.scheduleFirstSession();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase("Scheduled on "+sessionTime), "Not able to Register ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Cancelling a session
    	askTheExpertsPage.cancelSheduledSessionInCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase(""), "Not able to Cancel the ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Providing Feedback for Completed ATX
    	try {
    		if(COMPLETED_CISCO_ATX_ITEM.getItems().size() > 0) {
        		String feedbackSelectedBefore = askTheExpertsPage.getSelectedFeedbackOption();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Up"))
            		askTheExpertsPage.provideThumbsDownFeedback();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Down") || feedbackSelectedBefore.equalsIgnoreCase("No Feedback Selected"))
            		askTheExpertsPage.provideThumbsUpFeedback();
            	askTheExpertsPage.clickOnFirstCompletedATX();
            	String feedbackSelectedAfter = askTheExpertsPage.getSelectedFeedbackOption();
            	softAssert.assertFalse(feedbackSelectedBefore.equalsIgnoreCase(feedbackSelectedAfter), "Not able to Submit feedback");
        	}
    	} catch (NullPointerException e) {
    		System.out.println("No Completed ATX Session available to test Feedback");
		}
    	softAssert.assertAll();
    }
    
    @Test(description = "TC230296: Test user with 'Super Admin L2' role has full access to Ask The Experts(ATX)")
    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvw02385")
    public void verifySuperAdminL2HasFullAccessToATX() {
    	AskTheExpertsPage askTheExpertsPage = new AskTheExpertsPage();
    	AskTheExpertsPageIT askTheExpertsPageIT = new AskTheExpertsPageIT();
    	askTheExpertsPageIT.dataSetup();
    	COMPLETED_CISCO_ATX_ITEM = getATXData(askTheExpertsPageIT.ATX_DATA_LIST_CISCO, itemsItem -> null != itemsItem.getProviderInfo() && itemsItem.getStatus().equalsIgnoreCase("Completed"));
    	SoftAssert softAssert = new SoftAssert();
    	askTheExpertsPage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, askTheExpertsPageIT.CISCO_ATX_DATA.getUsecase())
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	String atxTitle = askTheExpertsPageIT.CISCO_ATX_DATA.getItems().get(0).getTitle();
    	askTheExpertsPage.changePitStop(PitStopsName.Implement);
    	askTheExpertsPage.clickATXViewAll().clickATXCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	
    	//Registering a Session
    	String sessionTime = askTheExpertsPage.scheduleFirstSession();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase("Scheduled on "+sessionTime), "Not able to Register ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Cancelling a session
    	askTheExpertsPage.cancelSheduledSessionInCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase(""), "Not able to Cancel the ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Providing Feedback for Completed ATX
    	try {
    		if(COMPLETED_CISCO_ATX_ITEM.getItems().size() > 0) {
        		String feedbackSelectedBefore = askTheExpertsPage.getSelectedFeedbackOption();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Up"))
            		askTheExpertsPage.provideThumbsDownFeedback();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Down") || feedbackSelectedBefore.equalsIgnoreCase("No Feedback Selected"))
            		askTheExpertsPage.provideThumbsUpFeedback();
            	askTheExpertsPage.clickOnFirstCompletedATX();
            	String feedbackSelectedAfter = askTheExpertsPage.getSelectedFeedbackOption();
            	softAssert.assertFalse(feedbackSelectedBefore.equalsIgnoreCase(feedbackSelectedAfter), "Not able to Submit feedback");
        	}
    	} catch (NullPointerException e) {
    		System.out.println("No Completed ATX Session available to test Feedback");
		}
    	softAssert.assertAll();
    }
    
    @Test(description = "TC230296: Test user with 'Super Admin L1' role has full access to Ask The Experts(ATX)")
    public void verifySuperAdminL1HasFullAccessToATX() {
    	AskTheExpertsPage askTheExpertsPage = new AskTheExpertsPage();
    	AskTheExpertsPageIT askTheExpertsPageIT = new AskTheExpertsPageIT();
    	askTheExpertsPageIT.dataSetup();
    	COMPLETED_CISCO_ATX_ITEM = getATXData(askTheExpertsPageIT.ATX_DATA_LIST_CISCO, itemsItem -> null != itemsItem.getProviderInfo() && itemsItem.getStatus().equalsIgnoreCase("Completed"));
    	SoftAssert softAssert = new SoftAssert();
    	askTheExpertsPage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, askTheExpertsPageIT.CISCO_ATX_DATA.getUsecase())
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	String atxTitle = askTheExpertsPageIT.CISCO_ATX_DATA.getItems().get(0).getTitle();
    	askTheExpertsPage.changePitStop(PitStopsName.Implement);
    	askTheExpertsPage.clickATXViewAll().clickATXCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	
    	//Registering a Session
    	String sessionTime = askTheExpertsPage.scheduleFirstSession();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase("Scheduled on "+sessionTime), "Not able to Register ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Cancelling a session
    	askTheExpertsPage.cancelSheduledSessionInCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase(""), "Not able to Cancel the ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Providing Feedback for Completed ATX
    	try {
    		if(COMPLETED_CISCO_ATX_ITEM.getItems().size() > 0) {
        		String feedbackSelectedBefore = askTheExpertsPage.getSelectedFeedbackOption();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Up"))
            		askTheExpertsPage.provideThumbsDownFeedback();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Down") || feedbackSelectedBefore.equalsIgnoreCase("No Feedback Selected"))
            		askTheExpertsPage.provideThumbsUpFeedback();
            	askTheExpertsPage.clickOnFirstCompletedATX();
            	String feedbackSelectedAfter = askTheExpertsPage.getSelectedFeedbackOption();
            	softAssert.assertFalse(feedbackSelectedBefore.equalsIgnoreCase(feedbackSelectedAfter), "Not able to Submit feedback");
        	}
    	} catch (NullPointerException e) {
    		System.out.println("No Completed ATX Session available to test Feedback");
		}
    	softAssert.assertAll();
    }
    
    @Test(description = "TC230304: Test user with 'Technical Admin L2' role has full access to Ask The Experts(ATX)")
    public void verifyTechAdminL2HasFullAccessToATX() {
    	AskTheExpertsPage askTheExpertsPage = new AskTheExpertsPage();
    	AskTheExpertsPageIT askTheExpertsPageIT = new AskTheExpertsPageIT();
    	askTheExpertsPageIT.dataSetup();
    	COMPLETED_CISCO_ATX_ITEM = getATXData(askTheExpertsPageIT.ATX_DATA_LIST_CISCO, itemsItem -> null != itemsItem.getProviderInfo() && itemsItem.getStatus().equalsIgnoreCase("Completed"));
    	SoftAssert softAssert = new SoftAssert();
    	askTheExpertsPage.login("adminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, askTheExpertsPageIT.CISCO_ATX_DATA.getUsecase())
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	String atxTitle = askTheExpertsPageIT.CISCO_ATX_DATA.getItems().get(0).getTitle();
    	askTheExpertsPage.changePitStop(PitStopsName.valueOf(askTheExpertsPageIT.CISCO_ATX_DATA.getPitstop()));
    	askTheExpertsPage.clickATXViewAll().clickATXCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	
    	//Registering a Session
    	String sessionTime = askTheExpertsPage.scheduleFirstSession();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase("Scheduled on "+sessionTime), "Not able to Register ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Cancelling a session
    	askTheExpertsPage.cancelSheduledSessionInCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase(""), "Not able to Cancel the ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Providing Feedback for Completed ATX
    	try {
    		if(COMPLETED_CISCO_ATX_ITEM.getItems().size() > 0) {
        		String feedbackSelectedBefore = askTheExpertsPage.getSelectedFeedbackOption();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Up"))
            		askTheExpertsPage.provideThumbsDownFeedback();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Down") || feedbackSelectedBefore.equalsIgnoreCase("No Feedback Selected"))
            		askTheExpertsPage.provideThumbsUpFeedback();
            	askTheExpertsPage.clickOnFirstCompletedATX();
            	String feedbackSelectedAfter = askTheExpertsPage.getSelectedFeedbackOption();
            	softAssert.assertFalse(feedbackSelectedBefore.equalsIgnoreCase(feedbackSelectedAfter), "Not able to Submit feedback");
        	}
    	} catch (NullPointerException e) {
    		System.out.println("No Completed ATX Session available to test Feedback");
		}
    	softAssert.assertAll();
    }
    
    @Test(description = "TC230304: Test user with 'Technical Admin L1' role has full access to Ask The Experts(ATX)")
    public void verifyTechAdminL1HasFullAccessToATX() {
    	AskTheExpertsPage askTheExpertsPage = new AskTheExpertsPage();
    	AskTheExpertsPageIT askTheExpertsPageIT = new AskTheExpertsPageIT();
    	askTheExpertsPageIT.dataSetup();
    	COMPLETED_CISCO_ATX_ITEM = getATXData(askTheExpertsPageIT.ATX_DATA_LIST_CISCO, itemsItem -> null != itemsItem.getProviderInfo() && itemsItem.getStatus().equalsIgnoreCase("Completed"));
    	SoftAssert softAssert = new SoftAssert();
    	askTheExpertsPage.login("adminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, askTheExpertsPageIT.CISCO_ATX_DATA.getUsecase())
				.selectCarousel(CarouselName.LIFECYCLE);
    	
    	String atxTitle = askTheExpertsPageIT.CISCO_ATX_DATA.getItems().get(0).getTitle();
    	askTheExpertsPage.changePitStop(PitStopsName.Implement);
    	askTheExpertsPage.clickATXViewAll().clickATXCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	
    	//Registering a Session
    	String sessionTime = askTheExpertsPage.scheduleFirstSession();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase("Scheduled on "+sessionTime), "Not able to Register ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Cancelling a session
    	askTheExpertsPage.cancelSheduledSessionInCardView();
    	askTheExpertsPage.clickATXTitleOnCardView(atxTitle);
    	softAssert.assertTrue(askTheExpertsPage.getATXSessionStatus().equalsIgnoreCase(""), "Not able to Cancel the ATX Session");
    	askTheExpertsPage.close360View();
    	
    	//Providing Feedback for Completed ATX
    	try {
    		if(COMPLETED_CISCO_ATX_ITEM.getItems().size() > 0) {
        		String feedbackSelectedBefore = askTheExpertsPage.getSelectedFeedbackOption();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Up"))
            		askTheExpertsPage.provideThumbsDownFeedback();
            	if(feedbackSelectedBefore.equalsIgnoreCase("Thumbs Down") || feedbackSelectedBefore.equalsIgnoreCase("No Feedback Selected"))
            		askTheExpertsPage.provideThumbsUpFeedback();
            	askTheExpertsPage.clickOnFirstCompletedATX();
            	String feedbackSelectedAfter = askTheExpertsPage.getSelectedFeedbackOption();
            	softAssert.assertFalse(feedbackSelectedBefore.equalsIgnoreCase(feedbackSelectedAfter), "Not able to Submit feedback");
        	}
    	} catch (NullPointerException e) {
    		System.out.println("No Completed ATX Session available to test Feedback");
		}
    	softAssert.assertAll();
    }

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {
		try {
			page.get().reload();
//            page.get().closeAllPopUps();
//			page.get().closeAllDropDowns();
		} catch (Exception e) {
			System.out.println("Page not reloaded properly" + e);
//			page.get().reload();
		}
	}
}