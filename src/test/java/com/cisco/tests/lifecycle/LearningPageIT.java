package com.cisco.tests.lifecycle;

import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;
import static com.cisco.testdata.lifecycle.Data.CUSTOMER_ID_GRAPHIC;
import static com.cisco.testdata.lifecycle.Data.CUSTOMER_ID_ZHEHUI;
import static com.cisco.testdata.lifecycle.Data.DEFAULT_USER_ROLE;
import static com.cisco.testdata.lifecycle.Data.SOLUTION;
import static com.cisco.testdata.lifecycle.Data.getACCData;
import static com.cisco.testdata.lifecycle.Data.getACCDataFromAllUseCase;
import static com.cisco.testdata.Data.LEARNING_DATA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.cisco.pages.lifecycle.LifecyclePage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.lifecycle.ACCPoJo;
import com.cisco.testdata.lifecycle.ElearningPoJo;

import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.cisco.testdata.lifecycle.Data.*;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.lifecycle.LearningPage;
import com.cisco.testdata.StaticData.ButtonName;

public class LearningPageIT extends DriverBase {
	
	private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");
    private String networkDeviceOnboardingUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_1");
    private String campusNetworkAssuranceUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_2");
    private String CampusNetworkSegmentationUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_5");
    private String cxCloudAccountL2 = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
    private String cxCloudAccountL1 = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L1");
    private String eLearningData = LEARNING_DATA.get("E_LEARNING");
    private String certPrepData = LEARNING_DATA.get("CERTIFICATION_PREP");
	private static ThreadLocal<LifecyclePage> page = ThreadLocal.withInitial(() -> new LifecyclePage());
	private ACCPoJo.ItemsItem CISCO_ACC_ITEM1;
    private ACCPoJo.ItemsItem CISCO_ACC_ITEM2;
    private ACCPoJo.ItemsItem CISCO_REQUESTED_ACC_ITEM;
    public List<ElearningPoJo> LEARNING_DATA_LIST;
    public ElearningPoJo ELEARNING_FEEDBACK_DATA_LIST;
    public ElearningPoJo CERTIFICATION_PREP_DATA_LIST;
    private ElearningPoJo.ItemsItem ELEARNING_FEEDBACK_ITEM;
    public ACCPoJo PARTNER_ACC_DATA;
    public ACCPoJo CISCO_REQUESTED_ACC_DATA;

	@Step("Verify Table View matches mockup")
		public void verifyTableViewMatchesMockup(String locator) {
	    	LearningPage learningPage = new LearningPage();
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertTrue(learningPage.isElementPresent(learningPage.getTableDataXPath(2, "Bookmark")), "Bookmark is not displayed");
			softAssert.assertTrue(learningPage.isElementPresent(learningPage.getTableDataXPath(2, "Name")), "Title is not displayed");
			softAssert.assertTrue(learningPage.isElementPresent(learningPage.getTableDataXPath(2, "Status")), "Status is not displayed");
			softAssert.assertTrue(learningPage.isElementPresent(learningPage.getTableDataXPath(2, "Delivery Type")), "Delivery Type is not displayed");
			if(!locator.equalsIgnoreCase(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID)) {
				softAssert.assertTrue(learningPage.isElementPresent(learningPage.getTableDataXPath(2, "Ratings")), "Ratings column us not displayed");
			}
			softAssert.assertAll();
		}

    @Step("Verify Card View matches mockup")
	public void verifyCardViewMatchesMockup(String locator) {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		softAssert.assertTrue(learningPage.isElementPresent("//button[@data-auto-id='" + locator + "--0']"), "Bookmark is not displayed");
		softAssert.assertTrue(learningPage.isElementPresent("//button[@data-auto-id='" + locator + "--0']//following-sibling::div/img"), " Icon is not displayed");
		softAssert.assertTrue(learningPage.isElementPresent("//button[@data-auto-id='" + locator + "--0']//following-sibling::div[contains(@class,'title-line-clamp')]"), "Title is not displayed");
		if(!locator.equalsIgnoreCase(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID)) {
			softAssert.assertTrue(learningPage.isElementPresent("//button[@data-auto-id='" + locator + "--0']//following-sibling::div[@class='video-status']"), "Duration is not displayed");
			if(learningPage.isElementPresent("//button[@data-auto-id='" + locator + "--0']//following-sibling::div/div[@class='status']")) {
				softAssert.assertTrue(learningPage.isElementPresent("//button[@data-auto-id='" + locator + "--0']//following-sibling::div/div[@class='status']"), "Course Status is not displayed");
			}
		}
		softAssert.assertAll();
	}
	
	@Test(description = "PBC-440: T133781: Certificate section shows 3 courses with valid links to learning portal")
	public void verifyCertificationPrepShows3Links() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
			throw new SkipException("No Certification Prep Courses are available to test this scneario");
		
		assertEquals(learningPage.getAllCertificationPrepTopicUnderLifeCycle(), 3, "Certification Prep has not displayed 3 links on the Home page");
	}
	
	@Test(description = "PBC-440: T133782: Learning section shows 3 courses with valid links to learning portal")
	public void verifyELearningShows3Links() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
			throw new SkipException("No E-Learning Courses are available to test this scneario");
		
		assertEquals(learningPage.getAllELearningTopicUnderLifeCycle(), 3, "E-Learning tile has not displayed 3 topics on the Home page");
	}
	
	@Test(groups = {"sanity"}, description = "PBC-441: T133972: Certification links should have a hover modal")
	public void verifyHoveringOverCertificationPrepShowsSummary() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
			throw new SkipException("No Certification Prep Courses are available to test this scneario");
		
		learningPage.getHoverOverOperationOnCertificationPrepLink();
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton().getHoverOverOptionOnCertPrepCardView();
	}
	
	@Test(description = "TC226140: Test user able to launch the Certification course by clicking on the Launch Course Button from Home page screen")
	public void verifyLaunchCourseCertPrepFromHomePage() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Checking Launch Course from Home Page 360 View
		if(learningPage.clickOnCourseWithTitle(certPrepData)) {
			System.out.println("Inside Data Availability");
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			learningPage.switchTab();
			softAssert.assertTrue(certPrepData.contains(learningPage.getTitleFromELearningPortal()), "E-Learning portal title is not matching with the title present in Certification Prep Home Page");
			learningPage.switchToMainTab();
			learningPage.close360View();
		} else {
			learningPage.clickFirstTitleFromHomePage(LearningPage.CertPrepTitleInHomePageDataAutoId);
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from Home Page");
			learningPage.switchTab().switchToMainTab();
			learningPage.close360View();
		}
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC226139: Test user able to launch the Certification course by clicking on the Launch Course Button from View all table view and card view")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvw08539")
	public void verifyLaunchCorseCertPrepFromCardAndTableView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllCertificationPrepTopicUnderLifeCycle() <= 0)
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Checking Launch Course from Card View
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		//Checking Launch Course from Home Page 360 View
		if(learningPage.clickOnCourseWithTitle(certPrepData)) {
			System.out.println("Inside Data Availability");
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			learningPage.switchTab();
			softAssert.assertTrue(certPrepData.contains(learningPage.getTitleFromELearningPortal()), "E-Learning portal title is not matching with the title present in Certification Prep Home Page");
			learningPage.switchToMainTab();
			learningPage.close360View();
		} else {
			learningPage.clickFirstTitleInCardViewXPath(LearningPage.CertPrepCardViewBookmarkDataAutoID);
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from Card View Modal");
			learningPage.switchTab().switchToMainTab();
			learningPage.close360View();
		}
				
		//Clicking Launch Course From Table View
		learningPage.clickLearningTableViewButton();
		if(learningPage.clickOnCourseWithTitle(certPrepData)) {
			System.out.println("Inside Data Availability");
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			learningPage.switchTab();
			softAssert.assertTrue(certPrepData.contains(learningPage.getTitleFromELearningPortal()), "E-Learning portal title is not matching with the title present in Certification Prep Home Page");
			learningPage.switchToMainTab();
			learningPage.close360View();
		} else {
			learningPage.clickFirstTitleInLearningTableView();
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in Certification Prep from Table View Modal");
			learningPage.switchTab().switchToMainTab();
			learningPage.close360View();
		}
		softAssert.assertAll();
	}
	
	@Test(description = "TC225998: Test user able to launch the course by clicking on the Launch Course Button from Home page screen")
	public void verifyLaunchCourseELearningFromHomePage() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Checking Launch Course from Home Page 360 View
		if(learningPage.clickOnCourseWithTitle(eLearningData)) {
			System.out.println("Inside Data Availability");
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			learningPage.switchTab();
			softAssert.assertTrue(eLearningData.contains(learningPage.getTitleFromELearningPortal()), "E-Learning portal title is not matching with the title present in Certification Prep Home Page");
			learningPage.switchToMainTab();
			learningPage.close360View();
		} else {
			learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId);
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working for E-Learning");
			learningPage.switchTab().switchToMainTab();
			learningPage.close360View();
		}
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC225999: Test user able to launch the course by clicking on the Launch Course Button from View all table view and card view")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvw08539")
	public void verifyLaunchCourseElearningFromCardAndTableView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Checking Launch Course from Card View
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		if(learningPage.clickOnCourseWithTitle(eLearningData)) {
			System.out.println("Inside Data Availability");
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			learningPage.switchTab();
			softAssert.assertTrue(eLearningData.contains(learningPage.getTitleFromELearningPortal()), "E-Learning portal title is not matching with the title present in Certification Prep Home Page");
			learningPage.switchToMainTab();
		} else {
			learningPage.clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewBookmarkDataAutoID);
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Table View Modal");
			learningPage.switchTab().switchToMainTab();
		}
		learningPage.close360View();
				
		//Checking Launch Course from Table View
		learningPage.clickLearningTableViewButton();
		if(learningPage.clickOnCourseWithTitle(eLearningData)) {
			System.out.println("Inside Data Availability");
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			learningPage.switchTab();
			softAssert.assertTrue(eLearningData.contains(learningPage.getTitleFromELearningPortal()), "E-Learning portal title is not matching with the title present in Certification Prep Home Page");
			learningPage.switchToMainTab();
		} else {
			learningPage.clickFirstTitleInLearningTableView();
			learningPage.clickButton(ButtonName.LAUNCH_COURSE);
			softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Launch Course Button is not working in E-Learning from Table View Modal");
			learningPage.switchTab().switchToMainTab();
		}
		learningPage.close360View();
				
		softAssert.assertAll();
		
	}
	
	@Test(description = "TC226016: Test user able to see In-Portal learning window when he click on a subject from Certification Prep for cx level 2 and 3 user")
	public void verify360ViewForCertPrepFromHomePage() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Verifying 360 view from Home Page
		learningPage.clickFirstTitleFromHomePage(LearningPage.CertPrepTitleInHomePageDataAutoId);
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 View is not opened for Certification Prep in Home Page");
		softAssert.assertEquals(learningPage.getFirstTitleFromHomePage(LearningPage.CertPrepTitleInHomePageDataAutoId), learningPage.getTitleFrom360View(), "Certification Prep Title is not matching");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewOverviewTabDataAutoId), "Overview tab is not displayed from Home Page");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewCourseOutlineTabDataAutoId), "Course Outline Tab is not displayed from Home Page");
		learningPage.close360View();
		
		softAssert.assertAll();
	}
	
	@Test(groups = {"sanity"}, description = "TC226017: Test user able to see In-Portal learning window when he click on a subject from Certification Prep View All model table and card view")
	public void verify360ViewForCertPrepFromCardAndTableView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Verifying 360 view from Card View
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		learningPage.clickFirstTitleInCardViewXPath(LearningPage.CertPrepCardViewBookmarkDataAutoID);
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 View is not opened for Certification Prep in Card View");
		softAssert.assertEquals(learningPage.getFirstTitleInCardViewXPath(LearningPage.CertPrepCardViewBookmarkDataAutoID), learningPage.getTitleFrom360View(), "Certification Prep Title is not matching in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDurationFrom360View()), "Certification Prep First Title Duration is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDeliveryTypeFrom360View()), "Certification Prep First Title Delivery Type is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewOverviewTabDataAutoId), "Overview tab is not displayed from Card View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewCourseOutlineTabDataAutoId), "Course Outline Tab is not displayed from Card View");
		learningPage.close360View();
				
		//Verifying 360 View from Table View
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView();
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 View is not opened for Certification Prep in Table View");
		softAssert.assertEquals(learningPage.getFirstTitleInLearningTableView(), learningPage.getTitleFrom360View(), "Certification Prep Title is not matching in Table View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDurationFrom360View()), "Certification Prep First Title Duration is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDeliveryTypeFrom360View()), "Certification Prep First Title Delivery Type is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewOverviewTabDataAutoId), "Overview tab is not displayed from Table View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewCourseOutlineTabDataAutoId), "Course Outline Tab is not displayed from Table View");
		learningPage.close360View();
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC225993: Test user able to see In-Portal learning window when he click on a subject from E-Learning session")
	public void verify360ViewForELearningFromHomePage() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Verifying 360 view from Home Page
		learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId);
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 View is not opened for E-Learning in Home Page");
		softAssert.assertEquals(learningPage.getFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId), learningPage.getTitleFrom360View(), "E-Learning Title is not matching");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewOverviewTabDataAutoId), "Overview tab is not displayed from Home Page");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewCourseOutlineTabDataAutoId), "Course Outline Tab is not displayed from Home Page");
		learningPage.close360View();
		
		softAssert.assertAll();
	}
	
	@Test(groups = {"sanity"}, description = "TC225996: Test user able to see In-Portal learning window when he click on a subject from E-Learning View All model table and card view")
	public void verify360ViewForElearningFromCardAndTableView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Verifying 360 view from Card View
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		learningPage.clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewBookmarkDataAutoID);
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 View is not opened for E-Learning in Card View");
		softAssert.assertEquals(learningPage.getFirstTitleInCardViewXPath(LearningPage.ELearningCardViewBookmarkDataAutoID), learningPage.getTitleFrom360View(), "E-Learning Title is not matching in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDurationFrom360View()), "E-Learning First Title Duration is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDeliveryTypeFrom360View()), "E-Learning First Title Delivery Type is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewOverviewTabDataAutoId), "Overview tab is not displayed from Card View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewCourseOutlineTabDataAutoId), "Course Outline Tab is not displayed from Card View");
		learningPage.close360View();
				
		//Verifying 360 view from Table View
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView();
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 View is not opened for Certification Prep in Table View");
		softAssert.assertEquals(learningPage.getFirstTitleInLearningTableView(), learningPage.getTitleFrom360View(), "Certification Prep Title is not matching in Table View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDurationFrom360View()), "E-Learning First Title Duration is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(learningPage.getDeliveryTypeFrom360View()), "E-Learning First Title Delivery Type is not present in Card View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewOverviewTabDataAutoId), "Overview tab is not displayed from Table View");
		softAssert.assertTrue(learningPage.isElementPresent(LearningPage.Learning360ViewCourseOutlineTabDataAutoId), "Course Outline Tab is not displayed from Table View");
		learningPage.close360View();
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC226042: Test user able to see Last launched details on the screen for remote practice labs list and view all table view")
	public void verifyLastLaunchedDetailsInRemotePracticesLabs() {
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
        String LastLaunchedDate = formatter.format(date);
        System.out.println("Today : " + LastLaunchedDate);
        
        SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllRemotePracLabsTopicUnderLifeCycle()>0))
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");
		
		//Verifying Last Launched Details on Home Page
		learningPage.clickFirstTitleFromHomePage(LearningPage.RemotePracLabsTitleInHomePageDataAutoId);
		learningPage.waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching from Home Page");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getLastLaunchesDetailsFromHomePage(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Home Page");
		
		//Verifying Last Launched Details in Card View
		learningPage.clickRemotePracLabsViewAllLink().clickLearningCardViewButton();
		learningPage.clickFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewDataAutoID);
		learningPage.waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Card View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromCardView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Card View");
		
		//Verifying Last Launched Details in Table View
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView();
		learningPage.waitUntilRemotePracticesLabsOpened();
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 2, "Remote Practices Labs Course is not launching in Table View");
		learningPage.switchTab().switchToMainTab();
		softAssert.assertEquals(learningPage.getFirstTitleLastLaunchedDetailsFromTableView(), "Last Launched "+LastLaunchedDate, "Last Launched Text is not displayed for Remote Practices Labs in Table View");
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC226001: E-Learning list and card view matches the provided mockup")
	public void verifyELearningListAndCardViewMatchesMockup() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Verifying Card View match with mockup
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		this.verifyCardViewMatchesMockup(LearningPage.ELearningCardViewBookmarkDataAutoID);
		
		//Verifying List View match with mockup
		learningPage.clickLearningTableViewButton();
		this.verifyTableViewMatchesMockup(LearningPage.ELearningCardViewBookmarkDataAutoID);
	}
	
	@Test(description = "TC226020: Certification Prep list and card view matches the provided mockup")
	public void verifyCertPrepListAndCardViewMatchesMockup() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Verifying Card View match with mockup
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		this.verifyCardViewMatchesMockup(LearningPage.CertPrepCardViewBookmarkDataAutoID);
		
		//Verifying List View match with mockup
		learningPage.clickLearningTableViewButton();
		this.verifyTableViewMatchesMockup(LearningPage.CertPrepCardViewBookmarkDataAutoID);
	}
	
	@Test(description = "TC226044: Remote Practices Labs list and card view matches the provided mockup")
	public void verifyRemotePracLabsListAndCardViewMatchesMockup() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllRemotePracLabsTopicUnderLifeCycle()>0))
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");
		
		//Verifying Card View match with mockup
		learningPage.clickRemotePracLabsViewAllLink().clickLearningCardViewButton();
		this.verifyCardViewMatchesMockup(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID);
		
		//Verifying List View match with mockup
		learningPage.clickLearningTableViewButton();
		this.verifyTableViewMatchesMockup(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID);
	}
	
	@Test(description = "TC226043: View all link opens a modal to show all applicable Remote Practices Labs section")
	public void verifyViewAllRemotePracticesLabs() {
		SoftAssert softAssert  = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllRemotePracLabsTopicUnderLifeCycle()>0))
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");
		
		learningPage.clickRemotePracLabsViewAllLink();
		softAssert.assertEquals(learningPage.getWindowTitleInViewAllModal(), "Remote Practice Labs", "Wrong window title is being displayed");
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 1, "View All for Remote Practices are not working properly");
		learningPage.clickLearningCardViewButton();
		softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.RemotePracLabsCardViewDataAutoID), "No of Topics displayed is not matching with the actual number of topics in the Card view");
		learningPage.clickLearningTableViewButton();
		softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), learningPage.getTableRowCount()-1, "No of Topics displayed is not matching with the actual number of topics in the Table view");
		softAssert.assertAll();
	}
	
	@Test(description = "TC226000: View all link opens a modal to show all applicable E-Learning Section")
	public void verifyViewAllELearning() {
		SoftAssert softAssert  = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink();
		softAssert.assertEquals(learningPage.getWindowTitleInViewAllModal(), "e-Learning", "Wrong window title is being displayed");
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 1, "View All for Remote Practices are not working properly");
		learningPage.clickLearningCardViewButton();
		softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.ELearningCardViewDataAutoID), "No of Topics displayed is not matching with the actual number of topics in the Card view");
		learningPage.clickLearningTableViewButton();
		softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), learningPage.getTableRowCount()-1, "No of Topics displayed is not matching with the actual number of topics in the Table view");
		softAssert.assertAll();
	}
	
	@Test(description = "TC226019: View all link opens a modal to show all applicable Certification Prep section")
	public void verifyViewAllCertificationPrep() {
		SoftAssert softAssert  = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		learningPage.clickCertPrepViewAllLink();
		softAssert.assertEquals(learningPage.getWindowTitleInViewAllModal(), "Certification Prep", "Wrong window title is being displayed");
		softAssert.assertEquals(learningPage.getTotalNumberOfTabsOpened(), 1, "View All for Remote Practices are not working properly");
		learningPage.clickLearningCardViewButton();
		softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.CertPrepCardViewDataAutoID), "No of Topics displayed is not matching with the actual number of topics in the Card view");
		learningPage.clickLearningTableViewButton();
		softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), learningPage.getTableRowCount()-1, "No of Topics displayed is not matching with the actual number of topics in the Table view");
		softAssert.assertAll();
	}
	
	@Test(description = "TC225994: Test user able to close the In-Portal learning window from home page screen and from view all table and card view")
	public void verifyCloseButtonOn360View() {
		SoftAssert softAssert  = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Verifying Close button in Home Page
		learningPage.clickFirstTitleFromHomePage(LearningPage.ELearningTitleInHomePageDataAutoId).close360View();
		softAssert.assertFalse(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 view is not closed by clicking on Close button from Home Page");
		
		//Verifying Close button in Card View
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		learningPage.clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).close360View();
		softAssert.assertFalse(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 view is not closed by clicking on Close button from Card View");
		
		//Verifying Close button in Table View
		learningPage.clickLearningTableViewButton().clickFirstTitleInLearningTableView();
		learningPage.close360View();
		softAssert.assertFalse(learningPage.isElementPresent(LearningPage.Learning360ViewDataAutoID), "360 view is not closed by clicking on Close button from Table View");
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC226053: Remote Practices Labs View All table view should be able to bookmark/unbookmark items")
	public void verifyBookMarkForRemotePracticesLabs() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllRemotePracLabsTopicUnderLifeCycle() <= 0 )
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");
		
		//Verifying Bookmark in Home Page
		softAssert.assertTrue(learningPage.verifyBookMarkonFirstTitleFromHomePage(LearningPage.RemotePracLabsBlockDataAutoId), "Not able to bookmark Remote Practice Labs from Home Page");
		
		//Verifying Bookmark in Card View
		learningPage.clickRemotePracLabsViewAllLink().clickLearningCardViewButton();
		softAssert.assertTrue(learningPage.verifyBookmarkOnCardView(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID), "Not able to bookmark Remote Practice Labs from Card View");
		
		//Verifying Bookmark in Table View
		learningPage.clickLearningTableViewButton();
		softAssert.assertTrue(learningPage.verifyBookmarkOnTableView(), "Not able to bookmark Remote Practice Labs from Table View");
		softAssert.assertAll();
	}
	
	@Test(description = "TC226032: Certification Prep View All table view should be able to bookmark/unbookmark items")
	public void verifyBookMarkForCertPrep() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllCertificationPrepTopicUnderLifeCycle() <= 0)
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Verifying Bookmark in Home Page
		softAssert.assertTrue(learningPage.verifyBookMarkonFirstTitleFromHomePage(LearningPage.CertPrepBlockDataAutoId), "Not able to bookmark Certification Prep from Home Page");
		
		//Verifying Bookmark in Card View
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		softAssert.assertTrue(learningPage.verifyBookmarkOnCardView(LearningPage.CertPrepCardViewBookmarkDataAutoID), "Not able to bookmark Certification Prep from Card View");
		
		//Verifying Bookmark in Table View
		learningPage.clickLearningTableViewButton();
		softAssert.assertTrue(learningPage.verifyBookmarkOnTableView(), "Not able to bookmark Certification Prep from Table View");
		softAssert.assertAll();
	}
	
	@Test(description = "TC226009: E-Learning View All table view should be able to bookmark/unbookmark items")
	public void verifyBookMarkForELearning() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllELearningTopicUnderLifeCycle() <= 0)
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Verifying Bookmark in Home Page
		softAssert.assertTrue(learningPage.verifyBookMarkonFirstTitleFromHomePage(LearningPage.ELearningBlockDataAutoId), "Not able to bookmark E-Learning from Home Page");
		
		//Verifying Bookmark in Card View
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		softAssert.assertTrue(learningPage.verifyBookmarkOnCardView(LearningPage.ELearningCardViewBookmarkDataAutoID), "Not able to bookmark E-Learning from Card View");
		
		//Verifying Bookmark in Table View
		learningPage.clickLearningTableViewButton();
		softAssert.assertTrue(learningPage.verifyBookmarkOnTableView(), "Not able to bookmark E-Learning from Table View");
		softAssert.assertAll();
	}
	
	@Test(description = "TC226005: E-Learning View All card and table view should show progress bar for In-Progress items")
	public void verifyELearningProgressBarInHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllELearningTopicUnderLifeCycle() <= 0)
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		assertTrue(learningPage.checkProgressBarDetailsForLearningInHomePage(LearningPage.ELearningProgressBarDataAutoId), "Progress Bar is not being displayed or No Data to test this scenario");
	}
	
	@Test(description = "TC226038: Completed Certification Prep topics should not be displayed under Certification Prep tile in Lifecycle page")
	public void verifyCertPrepCompletedStatusOnHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
	
		assertTrue(learningPage.getTotalNoOfCompletedCourses(LearningPage.CertPrepBlockDataAutoId)==0, "Completed Courses are displayed on the home page");
	}
	
	@Test(description = "TC226015: Completed E-Learning topics should not be displayed under E-Learning tile in Lifecycle page")
	public void verifyELearningCompletedStatusOnHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
	
		assertTrue(learningPage.getTotalNoOfCompletedCourses(LearningPage.ELearningBlockDataAutoId)==0, "Completed Courses are displayed on the home page");
	}
	
	@Test(description = "TC226051: Remote Practices Labs View All table view sorting should be sticky and reversible table/card view")
	public void verifyRemotePracLabsSortStickyAndReversible() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllRemotePracLabsTopicUnderLifeCycle()>0))
        	throw new SkipException("No Remote Practices Labs courses are available to test this scenario");
		
		learningPage.clickRemotePracLabsViewAllLink().clickLearningTableViewButton().sortByColumnNameInTableView("Bookmark");
		softAssert.assertEquals(learningPage.getFirstTitleInLearningTableView(), learningPage.clickLearningCardViewButton().getFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID), "Title is not matching with Table View when sorting by Bookmark");
		
		learningPage.clickLearningTableViewButton().sortByColumnNameInTableView("Name");
		softAssert.assertEquals(learningPage.getFirstTitleInLearningTableView(), learningPage.clickLearningCardViewButton().getFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID), "Title is not matching with Table View when sorting by Name");
		
		learningPage.clickLearningTableViewButton().sortByColumnNameInTableView("Delivery Type");
		softAssert.assertEquals(learningPage.getFirstTitleInLearningTableView(), learningPage.clickLearningCardViewButton().getFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID), "Title is not matching with Table View when sorting by Delivery Type");
		
		learningPage.clickLearningTableViewButton().sortByColumnNameInTableView("Status");
		softAssert.assertEquals(learningPage.getFirstTitleInLearningTableView(), learningPage.clickLearningCardViewButton().getFirstTitleInCardViewXPath(LearningPage.RemotePracLabsCardViewBookmarkDataAutoID), "Title is not matching with Table View when sorting by Status");
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC230148: Test user able to see ratings for Certification Prep in Card and List View")
	public void verifyCertPrepRatingsFromCardAndListView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Checking for the data availability
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName)>0))
			throw new SkipException("No Certification Prep courses with Ratings available to test this scenario");
		
		//Checking in Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertEquals(learningPage.getRatingsInCardView(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackRatingClassName), "No of Courses Completed in Card View is not matching with the value present in 360 View");
		learningPage.close360View();
		
		//Checking in Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertEquals(learningPage.getRatingsInTableView(LearningPage.LearningFeedbackRatingClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackRatingClassName), "No of Courses Completed in Table View is not matching with the value present in 360 View");
		softAssert.assertAll();
	}
	
	@Test(description = "TC230146: Test user able to see ratings for Certification Prep in Home Page")
	public void verifyCertPrepRatingsFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Checking for Data Availability
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName)>0))
			throw new SkipException("No Certification Prep courses with Ratings available to test this scenario");
		
		//Checking in Home Page
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName);
		assertEquals(learningPage.getRatingsInHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackRatingClassName), "No of Courses Completed in Home Page is not matching with the value present in 360 View");
	}
	
	@Test(description = "TC230147: Test user able to see ratings for E-Learning in Card and List View")
	public void verifyELearningRatingsFromCardAndListView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Checking for the data availability
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName)>0))
			throw new SkipException("No E-Learning courses with Ratings available to test this scenario");
		
		//Checking in Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertEquals(learningPage.getRatingsInCardView(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackRatingClassName), "No of Courses Completed in Card View is not matching with the value present in 360 View");
		learningPage.close360View();
				
		//Checking in Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertEquals(learningPage.getRatingsInTableView(LearningPage.LearningFeedbackRatingClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackRatingClassName), "No of Courses Completed in Table View is not matching with the value present in 360 View");
		softAssert.assertAll();
	}
	
	@Test(description = "TC230145: Test user able to see ratings for E-Learning in Home Page")
	public void verifyELearningRatingsFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName)>0))
			throw new SkipException("No E-Learning courses with Ratings available to test this scenario");
		
		//Checking in Home Page
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName);
		assertEquals(learningPage.getRatingsInHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackRatingClassName), "No of Courses Completed in Home Page is not matching with the value present in 360 View");
	}
	
	@Test(description = "TC227844: Test user able to see Completed sessions and % of Positive thumbs up from Card and table view for Certification Prep")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvv81832")
	public void verifyCertPrepPositiveThumbsUpFromCardAndListView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Checking for the data availability
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName)>0))
			throw new SkipException("No Certification Prep courses with Positive Thumbs Up available to test this scenario");
		
		//Checking in Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertEquals(learningPage.getRatingsInCardView(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "No of Courses Completed in Card View is not matching with the value present in 360 View");
		learningPage.close360View();
		
		//Checking in Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertEquals(learningPage.getRatingsInTableView(LearningPage.LearningFeedbackPositiveThumbsUpClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "No of Courses Completed in Table View is not matching with the value present in 360 View");
		softAssert.assertAll();
	}
	
	/*@Test(description = "TC227842: Test user able to see Completed sessions and % of Positive thumbs up for Certification Prep in Home page")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvv77577")
	public void verifyCertPrepPositiveThumbsUpFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName)>0))
			throw new SkipException("No Certification Prep courses with Positive Thumbs Up available to test this scenario");
		
		//Checking in Home Page
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		assertEquals(learningPage.getRatingsInHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "No of Courses Completed in Home Page is not matching with the value present in 360 View");
	}*/
	
	@Test(description = "TC227840: Test user able to see Completed sessions and % of Positive thumbs up from Card and table view for e-Learning")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvv81832")
	public void verifyELearningositiveThumbsUpFromCardAndListView() {
		SoftAssert softAssert = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for the data availability
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName)>0))
			throw new SkipException("No E-Learning courses with Positive Thumbs Up available to test this scenario");
		
		//Checking in Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertEquals(learningPage.getRatingsInCardView(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "No of Courses Completed in Card View is not matching with the value present in 360 View");
		learningPage.close360View();
				
		//Checking in Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertEquals(learningPage.getRatingsInTableView(LearningPage.LearningFeedbackPositiveThumbsUpClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "No of Courses Completed in Table View is not matching with the value present in 360 View");
		softAssert.assertAll();
	}
	
	/*@Test(description = "TC227838: Test user able to see Completed sessions and % of Positive thumbs up for e-Learning in Home page")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvv77577")
	public void verifyELearningPositiveThumbsUpFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName)>0))
			throw new SkipException("No E-Learning courses with Positive Thumbs Up available to test this scenario");
		
		//Checking in Home Page
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		assertEquals(learningPage.getRatingsInHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName), learningPage.getRatingsIn360View(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "No of Courses Completed in Home Page is not matching with the value present in 360 View");
	}*/
	
	@Test(description = "TC230152: Test user able to see ratings for Certification Prep in 360 view on Home Page")
	public void verifyRatingsForCertPrep360ViewFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
		    throw new SkipException("No Certification Prep courses are available to test this scenario");
				
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName)>0))
			throw new SkipException("No Certification Prep courses with Ratings available in Home Page to test this scenario");
		
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName);
		assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackRatingClassName), "Ratings is not displayed in 360 View from Home Page");
	}
	
	@Test(description = "TC230154: Test user able to see ratings for Certification Prep in 360 view on Table View and Card View")
	public void verifyRatingsForCertPrep360ViewFromViewAllPage() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
		    throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		
		if(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName) <= 0)
			throw new SkipException("No Certification Prep courses with Ratings available in View All Page to test this scenario");
		
		//Checking from Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackRatingClassName), "Ratings is not displayed in 360 View from View All Card View");
		learningPage.close360View();
		
		//Checking from Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackRatingClassName), "Ratings is not displayed in 360 View from View All Table View");
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC230151: Test user able to see ratings for E-Learning in 360 view on Home Page")
	public void verifyRatingsForELearning360ViewFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
		    throw new SkipException("No E-Learning courses are available to test this scenario");
				
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName)>0))
			throw new SkipException("No E-Learning courses with Ratings available in Home Page to test this scenario");
		
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackRatingClassName);
		assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackRatingClassName), "Ratings is not displayed in 360 View from Home Page");
	}
	
	@Test(description = "TC230153: Test user able to see ratings for E-Learning in 360 view on Table View and Card View")
	public void verifyRatingsForELearning360ViewFromViewAllPage() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
		    throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		
		if(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName) <= 0)
			throw new SkipException("No E-Learning courses with Ratings available in View All Page to test this scenario");
		
		//Checking from Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackRatingClassName), "Ratings is not displayed in 360 View from View All Card View");
		learningPage.close360View();
		
		//Checking from Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackRatingClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackRatingClassName), "Ratings is not displayed in 360 View from View All Table View");
		
		softAssert.assertAll();
	}
	
	/*@Test(description = "TC227843: Test user able to see Completed sessions and % of Positive thumbs up for Certification Prep in 360 view from Home page")
	  @Issue("https://cdetsng.cisco.com/webui/#view=CSCvv77577")
	public void verifyPositiveThumbsUpForCertPrep360ViewFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
		    throw new SkipException("No Certification Prep courses are available to test this scenario");
				
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName)>0))
			throw new SkipException("No Certification Prep courses with Ratings available in Home Page to test this scenario");
		
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.CertPrepBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "Positive Thumbs Up is not displayed in 360 View from Home Page");
	}
	
	@Test(description = "TC227839: Test user able to see Completed sessions and % of Positive thumbs up for e-Learning in 360 view from Home page")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvv77577")
	public void verifyPositiveThumbsUpForELearning360ViewFromHomePage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(learningPage.getAllELearningTopicUnderLifeCycle() < 0)
		    throw new SkipException("No E-Learning courses are available to test this scenario");
				
		if(!(learningPage.getTotalNoOfCoursesWithRatingsFromHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName)>0))
			throw new SkipException("No E-Learning courses with Ratings available in Home Page to test this scenario");
		
		learningPage.clickOnCourseWithRatingsInHomePage(LearningPage.ELearningBlockDataAutoId, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "Positive Thumbs Up is not displayed in 360 View from Home Page");
	}*/
	

	@Test(description = "TC230156: Test user able to provide Thumbs up or Thumbs Down when there is First to Rate experience is present")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvw08539")
	public void verifyUserAbleToProvideFeedbackForFirstTime() {
		LearningPage learningPage = new LearningPage();
		LEARNING_DATA_LIST = getElearningDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, DEFAULT_USER_ROLE);
		ELEARNING_FEEDBACK_DATA_LIST = getElearningData(LEARNING_DATA_LIST, itemsItem -> null != itemsItem.getFeedbackInfo() && itemsItem.getType().equalsIgnoreCase("E-Learning"));
		ELEARNING_FEEDBACK_ITEM = ELEARNING_FEEDBACK_DATA_LIST.getItems().get(0);
		learningPage.deleteELearningFeedback(ELEARNING_FEEDBACK_ITEM);
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, ELEARNING_FEEDBACK_ITEM.getUsecase())
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllELearningTopicUnderLifeCycle() <= 0)
		    throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		
		if(!learningPage.getnoOfCourseWithBeTheFirstToRateExperience())
			throw new SkipException("No E-Learning courses with 'Be The First To Rate' feedback option available to test this scenario");
		
		learningPage.close360View();
		learningPage.clickOnFirstCourseWithCompletedStatus();
		String feedbackBeforeSubmit = learningPage.getSelectedFeedbackOption();
		System.out.println("Before Submit:" + feedbackBeforeSubmit);
		if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Up"))
			learningPage.provideThumbsDownFeedback();
		else if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Down") || feedbackBeforeSubmit.equalsIgnoreCase("No Feedback Selected"))
			learningPage.provideThumbsUpFeedback();
		learningPage.clickOnFirstCourseWithCompletedStatus();
		String feedbackAfterSubmit = learningPage.getSelectedFeedbackOption();
		System.out.println("After Submit: " + feedbackAfterSubmit);
		assertTrue(!(feedbackBeforeSubmit.equalsIgnoreCase(feedbackAfterSubmit)), "Not able to submit feedback properly");
	}
	
	@Test(description = "TC227848: Test user able to provide feedback for e-Learning in 360 view")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvw08539")
	public void verifyUserAbleToProvideFeedback() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllELearningTopicUnderLifeCycle() <= 0)
		    throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		
		if(learningPage.getNoOfCourseWithCompletedStatus() <= 0)
			throw new SkipException("No E-Learning courses with Completed Status available to test this scenario");
		
		learningPage.clickOnFirstCourseWithCompletedStatus();
		String feedbackBeforeSubmit = learningPage.getSelectedFeedbackOption();
		System.out.println("Before Submit:" + feedbackBeforeSubmit);
		if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Up"))
			learningPage.provideThumbsDownFeedback();
		else if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Down") || feedbackBeforeSubmit.equalsIgnoreCase("No Feedback Selected"))
			learningPage.provideThumbsUpFeedback();
		learningPage.clickOnFirstCourseWithCompletedStatus();
		String feedbackAfterSubmit = learningPage.getSelectedFeedbackOption();
		System.out.println("After Submit: " + feedbackAfterSubmit);
		assertTrue(!(feedbackBeforeSubmit.equalsIgnoreCase(feedbackAfterSubmit)), "Not able to submit feedback properly");
	}
	
	/*@Test(description = "TC227849: Test user is getting confirmation message once feedback is submitted")
	public void verifyFeedbackSubmitMessage() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllELearningTopicUnderLifeCycle() <= 0)
		    throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		
		if(learningPage.getNoOfCourseWithCompletedStatus() <= 0)
			throw new SkipException("No E-Learning courses with Completed Status available to test this scenario");
		
		learningPage.clickOnFirstCourseWithCompletedStatus();
		String feedbackBeforeSubmit = learningPage.getSelectedFeedbackOption();
		System.out.println("Before Submit:" + feedbackBeforeSubmit);
		if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Up"))
			learningPage.provideThumbsDownFeedback();
		else if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Down") || feedbackBeforeSubmit.equalsIgnoreCase("No Feedback Selected"))
			learningPage.provideThumbsUpFeedback();
		assertTrue(learningPage.checkFeedbackSubmitMessageDisplayed(), "Feedback Submit Message is not getting displayed");
	}*/
	
	@Test(description = "TC226002: E-Learning View All card and table view should be able to Filter Status")
	public void verifyELearningViewAllFilterableByStatus() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllELearningTopicUnderLifeCycle() <= 0)
		    throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Checking in Card View
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		int totalNoOfCourseInCardViewBeforeFilter = learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.ELearningCardViewDataAutoID);
		HashMap<String, Integer> countBeforeApplyingFilter = learningPage.getTotalNoOfTilesWithStatus(LearningPage.ELearningCardViewDataAutoID, LearningPage.ELearningCardViewBookmarkDataAutoID);
		learningPage.getTotalFilteredELearningInViewAllPage("Status", "In Progress");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.ELearningCardViewDataAutoID), countBeforeApplyingFilter.get("In Progress").intValue(), "Status filter with Requested is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("In Progress").intValue(), "Status filter with Requested is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "Completed");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.ELearningCardViewDataAutoID), countBeforeApplyingFilter.get("Completed").intValue(), "Status filter with Scheduled is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("Completed").intValue(), "Status filter with Scheduled is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "Bookmarked");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.ELearningCardViewDataAutoID), countBeforeApplyingFilter.get("Bookmarked").intValue(), "Status filter with In Progress is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("Bookmarked").intValue(), "Status filter with In Progress is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "All");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.ELearningCardViewDataAutoID), totalNoOfCourseInCardViewBeforeFilter, "Status filter with In Progress is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), totalNoOfCourseInCardViewBeforeFilter, "Status filter with In Progress is not working properly");
        
        softAssert.assertAll();
	}
	
	@Test(description = "TC226021: Certification Prep View All card and table view should be able to filter by Status")
	public void verifyCertPrepViewAllFilterableByStatus() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllCertificationPrepTopicUnderLifeCycle() <= 0)
		    throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Checking in Card View
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		int totalNoOfCourseInCardViewBeforeFilter = learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.CertPrepCardViewDataAutoID);
		HashMap<String, Integer> countBeforeApplyingFilter = learningPage.getTotalNoOfTilesWithStatus(LearningPage.CertPrepCardViewDataAutoID, LearningPage.CertPrepCardViewBookmarkDataAutoID);
		learningPage.getTotalFilteredELearningInViewAllPage("Status", "In Progress");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.CertPrepCardViewDataAutoID), countBeforeApplyingFilter.get("In Progress").intValue(), "Status filter with Requested is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("In Progress").intValue(), "Status filter with Requested is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "Completed");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.CertPrepCardViewDataAutoID), countBeforeApplyingFilter.get("Completed").intValue(), "Status filter with Scheduled is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("Completed").intValue(), "Status filter with Scheduled is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "Bookmarked");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.CertPrepCardViewDataAutoID), countBeforeApplyingFilter.get("Bookmarked").intValue(), "Status filter with In Progress is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("Bookmarked").intValue(), "Status filter with In Progress is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "All");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.CertPrepCardViewDataAutoID), totalNoOfCourseInCardViewBeforeFilter, "Status filter with In Progress is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), totalNoOfCourseInCardViewBeforeFilter, "Status filter with In Progress is not working properly");
        
        softAssert.assertAll();
	}
	
	@Test(description = "TC226045: Remote Practices Labs View All card and table view should be filterable by Launched,Not Launched and Bookmark")
	public void verifyRemotePracLabsViewAllFilterableByStatus() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllRemotePracLabsTopicUnderLifeCycle() <= 0)
		    throw new SkipException("No Remote Practices Labs are available to test this scenario");
		
		//Checking in Card View
		learningPage.clickRemotePracLabsViewAllLink().clickLearningCardViewButton();
		int totalNoOfCourseInCardViewBeforeFilter = learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.RemotePracLabsCardViewDataAutoID);
		HashMap<String, Integer> countBeforeApplyingFilter = learningPage.getTotalNoOfTilesWithStatus(LearningPage.RemotePracLabsCardViewDataAutoID, LearningPage.RemotePracLabsCardViewBookmarkDataAutoID);
		learningPage.getTotalFilteredELearningInViewAllPage("Status", "Launched");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.RemotePracLabsCardViewDataAutoID), countBeforeApplyingFilter.get("Launched").intValue(), "Status filter with Requested is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("Launched").intValue(), "Status filter with Requested is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "Not Launched");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.RemotePracLabsCardViewDataAutoID), countBeforeApplyingFilter.get("Not Launched").intValue(), "Status filter with Scheduled is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("Not Launched").intValue(), "Status filter with Scheduled is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "Bookmarked");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.RemotePracLabsCardViewDataAutoID), countBeforeApplyingFilter.get("Bookmarked").intValue(), "Status filter with In Progress is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), countBeforeApplyingFilter.get("Bookmarked").intValue(), "Status filter with In Progress is not working properly");
        
        learningPage.getTotalFilteredELearningInViewAllPage("Status", "All");
        softAssert.assertEquals(learningPage.getTotalNoOfTitlesDisplayedInCardView(LearningPage.RemotePracLabsCardViewDataAutoID), totalNoOfCourseInCardViewBeforeFilter, "Status filter with In Progress is not working properly");
        softAssert.assertEquals(learningPage.getTotalNumberOfTopicsInTitle(), totalNoOfCourseInCardViewBeforeFilter, "Status filter with In Progress is not working properly");
        
        softAssert.assertAll();
	}
	
	@Test(description = "TC227845: Test user able to see Completed sessions and % Positive of thumbs up from 360 view in Card and Table view for Certification Prep")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvv81832")
	public void verifyPositiveThumbsUpforCertPrepCardAndTable360View() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllCertificationPrepTopicUnderLifeCycle()>0))
		    throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		
		if(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName) <= 0)
			throw new SkipException("No Certification Prep courses with Positive Thumbs Up available in View All Page to test this scenario");
		
		//Checking from Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.CertPrepCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "Positive Thumbs Up is not displayed in 360 View from View All Card View");
		learningPage.close360View();
				
		//Checking from Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "Positive Thumbs Up is not displayed in 360 View from View All Table View");
		learningPage.close360View();
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC227841: Test user able to see Completed sessions and % of Positive thumbs up from 360 view in Card and Table view for e-Learning")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvv81832")
	public void verifyPositiveThumbsUpforELearningCardAndTable360View() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Data Availability
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
		    throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		
		if(learningPage.getTotalNoOfCoursesWithRatingsFromViewAll(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName) <= 0)
			throw new SkipException("No Certification Prep courses with Positive Thumbs Up available in View All Page to test this scenario");
		
		//Checking from Card View
		learningPage.clickOnCourseWithRatingsInCardView(LearningPage.ELearningCardViewDataAutoID, LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "Positive Thumbs Up is not displayed in 360 View from View All Card View");
		learningPage.close360View();
				
		//Checking from Table View
		learningPage.clickLearningTableViewButton().clickOnCourseWithRatingsInTableView(LearningPage.LearningFeedbackPositiveThumbsUpClassName);
		softAssert.assertTrue(learningPage.checkRatingsandThumbsUpIn360ViewDisplayed(LearningPage.LearningFeedbackPositiveThumbsUpClassName), "Positive Thumbs Up is not displayed in 360 View from View All Table View");
		learningPage.close360View();
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC227847: Test CX Level 2 user is able to see Certification Prep and Remote Practice Labs tiles")
	public void verifyCXL2UserAblToSeeCertPrepAndRemotePracLabsTiles() {
		LifecyclePage lifecyclePage = new LifecyclePage();
		SoftAssert softAssert = new SoftAssert();
		lifecyclePage.login("superAdminL2", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		softAssert.assertTrue(lifecyclePage.checkCertificationPrepDisplayed(), "Certification Prep Panel is not displayed for CX Level 2 User");
		softAssert.assertTrue(lifecyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs Panel is not displayed for CX Level 2 User");
		softAssert.assertAll();
	}
	
	@Test(description = "TC227846: Test CX Level 1 user is able to only e-Learning tile")
	public void verifyCXL1UserAblToSeeOnlyELearningTile() {
		LifecyclePage lifecyclePage = new LifecyclePage();
		SoftAssert softAssert = new SoftAssert();
		lifecyclePage.login("superAdminL1", "lifecycle")
				.switchCXCloudAccount(cxCloudAccountL1)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		softAssert.assertTrue(lifecyclePage.checkELearningPanelDisplayed(), "E-Learning Panel is not displayed for the customer with CX Level 1 User");
		softAssert.assertFalse(lifecyclePage.checkCertificationPrepDisplayed(), "Certification Prep Panel is displayed for CX Level 1 User");
		softAssert.assertFalse(lifecyclePage.checkRemotePracLabsDisplayed(), "Remote Practices Labs Panel is displayed for CX Level 1 User");
		softAssert.assertAll();
	}

	@Test(description = "TC230155: Test user able to see 'First to Rate' when there is no feedback available")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvw08539")
	public void verifyFirstToRateWhenNoFeedbackAvailable() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		LEARNING_DATA_LIST = getElearningDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, DEFAULT_USER_ROLE);
		ELEARNING_FEEDBACK_DATA_LIST = getElearningData(LEARNING_DATA_LIST, itemsItem -> null != itemsItem.getFeedbackInfo() && itemsItem.getType().equalsIgnoreCase("E-Learning"));
		ELEARNING_FEEDBACK_ITEM = ELEARNING_FEEDBACK_DATA_LIST.getItems().get(0);
		learningPage.deleteELearningFeedback(ELEARNING_FEEDBACK_ITEM);
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, ELEARNING_FEEDBACK_ITEM.getUsecase())
				.selectCarousel(CarouselName.LIFECYCLE);
		
		learningPage.clickELearningViewAllLink();
		
		if(!learningPage.getnoOfCourseWithBeTheFirstToRateExperience())
			throw new SkipException("No E-Learning courses with 'Be The First To Rate' feedback option available to test this scenario");
		
		String title = learningPage.getTitleFrom360View();
		learningPage.close360View();
		softAssert.assertEquals(learningPage.getStatsWithSpecificTitleInCardView(title), "Completed", "Be The First To Rate is displayed for the course which is not in Completed Status");
		softAssert.assertFalse(learningPage.checkRatingsandFeedbackWithTitleDisplayed(title, LearningPage.LearningFeedbackPositiveThumbsUpClassName), "Be The First To Rate is displayed for the course which is already having Feedback Percentage");
		softAssert.assertAll();
	}
	
	@Test(description = "TC230157: Test user able to see ratings immediately when he provide feedback")
	@Issue("https://cdetsng.cisco.com/webui/#view=CSCvw08539")
	public void verifyFeedbackGettingUpdatedImmediately() {
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		int positiveFeedbackPercentageBefore = 0;
		
		if(learningPage.getAllELearningTopicUnderLifeCycle() <= 0)
		    throw new SkipException("No E-Learning courses are available to test this scenario");
		
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		
		if(learningPage.getNoOfCourseWithCompletedStatus() <= 0)
			throw new SkipException("No E-Learning courses with Completed Status available to test this scenario");
		
		learningPage.clickOnFirstCourseWithCompletedStatus();
		String title = learningPage.getTitleFrom360View();
		learningPage.close360View();
		if(learningPage.checkRatingsandFeedbackWithTitleDisplayed(title, LearningPage.LearningFeedbackPositiveThumbsUpClassName))
			positiveFeedbackPercentageBefore = Integer.parseInt(learningPage.getRatingsandFeedbackWithTitle(title, LearningPage.LearningFeedbackPositiveThumbsUpClassName).split("%")[0]);
		System.out.println("Before: " +positiveFeedbackPercentageBefore);
		learningPage.clickOnFirstCourseWithCompletedStatus();
		String feedbackBeforeSubmit = learningPage.getSelectedFeedbackOption();
		System.out.println("Before Submit:" + feedbackBeforeSubmit);
		if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Up"))
			learningPage.provideThumbsDownFeedback();
		else if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Down") || feedbackBeforeSubmit.equalsIgnoreCase("No Feedback Selected"))
			learningPage.provideThumbsUpFeedback();
		int positiveFeedbackPercentageAfter = Integer.parseInt(learningPage.getRatingsandFeedbackWithTitle(title, LearningPage.LearningFeedbackPositiveThumbsUpClassName).split("%")[0]);
		System.out.println("After: " +positiveFeedbackPercentageAfter);
		assertTrue(positiveFeedbackPercentageBefore != positiveFeedbackPercentageAfter, "Feedback Percentage is not getting updated for e-Learning courses");
	}
	
	@Test(description = "TC226067: Verify that the labels in cxportal under 'ViewAll' page are according to the consistent naming conventions")
	public void verifyLearningNamingConsistentOnViewAll() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//For E-Learning
		learningPage.clickELearningViewAllLink();
		softAssert.assertEquals(learningPage.getWindowTitleInViewAllModal(), "e-Learning", "e-Learning naming Consitency is not present in View All Title");
		softAssert.assertEquals(learningPage.clickFirstTitleInCardViewXPath(LearningPage.ELearningCardViewDataAutoID).getLearningTitleIn360View(), "E-LEARNING", "E-LEARNING naming consitency is not present in 360 View from View All Page");
		learningPage.close360View();
		learningPage.closeViewAllScreen();
		
		//For Certification Prep
		learningPage.clickCertPrepViewAllLink();
		softAssert.assertEquals(learningPage.getWindowTitleInViewAllModal(), "Certification Prep", "Certification Prep naming Consitency is not present in View All Title");
		softAssert.assertEquals(learningPage.clickFirstTitleInCardViewXPath(LearningPage.CertPrepCardViewDataAutoID).getLearningTitleIn360View(), "CERTIFICATION PREP", "CERTIFICATION PREP naming consitency is not present in 360 View from View All Page");
		learningPage.close360View();
		learningPage.closeViewAllScreen();
		
		//For Remote Practices Labs
		learningPage.clickRemotePracLabsViewAllLink();
		softAssert.assertEquals(learningPage.getWindowTitleInViewAllModal(), "Remote Practice Labs", "Remote Practice Labs naming Consistency is not present in View All Title");
		learningPage.closeViewAllScreen();
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC225309: Verify that the labels in cxportal home pages are according to the consistent naming conventions")
	public void verifyLearningNamingConsistentOnHomePage() {
		LearningPage learningPage = new LearningPage();
		SoftAssert softAssert = new SoftAssert();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Learning Tile
		softAssert.assertEquals(learningPage.getLearningTileName(), "LEARNING", "LEARNING tile naming consistency in not proper in Lifecycle Home Page");
	
		//For E-Learning
		softAssert.assertEquals(learningPage.getLearningBlockTileName(LearningPage.ELearningBlockDataAutoId), "E-LEARNING", "E-LEARNING tile naming consistency in not proper in Lifecycle Home Page");
		
		//For Certification Prep
		softAssert.assertEquals(learningPage.getLearningBlockTileName(LearningPage.CertPrepBlockDataAutoId), "CERTIFICATION PREP", "CERTIFICATION PREP tile naming consistency in not proper in Lifecycle Home Page");
		
		//For Remote Practice Labs
		softAssert.assertEquals(learningPage.getLearningBlockTileName(LearningPage.RemotePracLabsBlockDataAutoId), "REMOTE PRACTICE LABS", "REMOTE PRACTICE LABS tile naming consistency in not proper in Lifecycle Home Page");
		softAssert.assertAll();
	}
	
	@Test(description = "TC225997: Test user able to see progress bar details for the course on the screen and View all table view and card view for E-Learning session")
	public void verifyELearningrogressBarInViewAllPage() {
		SoftAssert softAssert  = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!(learningPage.getAllELearningTopicUnderLifeCycle()>0))
        	throw new SkipException("No E-Learning courses are available to test this scenario");
		
		//Verifying Progress Bar in Card View
		learningPage.clickELearningViewAllLink().clickLearningCardViewButton();
		softAssert.assertTrue(learningPage.checkProgressBarDetailsForLearningInViewAll(), "No Data is available to verify E-Learning Progress bar in Card View");
		
		//Verifying Progress Bar in Table View
		learningPage.clickLearningTableViewButton();
		softAssert.assertTrue(learningPage.checkProgressBarDetailsForLearningInViewAll(), "No Data is available to verify E-Learning Progress bar in Table View");
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC226018: Test user able to see progress bar details for the course on the screen and View all table view and card view for certification prep for cx level 2 and 3 user")
	public void verifyCertPreprogressBarInViewAllPage() {
		SoftAssert softAssert  = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllCertificationPrepTopicUnderLifeCycle() <= 0)
        	throw new SkipException("No Certification Prep courses are available to test this scenario");
		
		//Verifying Progress Bar in Card View
		learningPage.clickCertPrepViewAllLink().clickLearningCardViewButton();
		softAssert.assertTrue(learningPage.checkProgressBarDetailsForLearningInViewAll(), "No Data is available to verify Certification Prep Progress bar in Card View");
		
		//Verifying Progress Bar in Table View
		learningPage.clickLearningTableViewButton();
		softAssert.assertTrue(learningPage.checkProgressBarDetailsForLearningInViewAll(), "No Data is available to verify Certification Prep Progress bar in Table View");
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC226050: Remote Practices Labs View All table view should be toggle-able back to card view")
	public void verifyRemotePracLabsToggleBackToCardView() {
		SoftAssert softAssert  = new SoftAssert();
		LearningPage learningPage = new LearningPage();
		learningPage.login()
				.switchCXCloudAccount(cxCloudAccountL2)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(learningPage.getAllRemotePracLabsTopicUnderLifeCycle() <= 0)
			throw new SkipException("No Remote Practices Labs are available to test this scenario");
		
		learningPage.clickRemotePracLabsViewAllLink().clickLearningTableViewButton();
		softAssert.assertTrue(learningPage.getTableRowCount() > 1, "Labs are not displaying in Table View");
		
		learningPage.clickLearningCardViewButton();
		softAssert.assertTrue(learningPage.getTotalNoofCoursesDisplayedInCardView(LearningPage.RemotePracLabsCardViewDataAutoID) > 0, "Labs are not displaying in Card View or User not able to toggle back to Card View");
		
		learningPage.clickLearningTableViewButton();
		softAssert.assertTrue(learningPage.getTableRowCount() > 1, "Labs are not displaying in Table View or User not able to toggle back to Table View");
		
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