package com.cisco.tests.lifecycle;

import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.SkipException;

import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.PitStopsName;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.lifecycle.CiscoCommunityPage;
import com.cisco.pages.lifecycle.LifecyclePage;

public class CiscoCommunityPageIT extends DriverBase {
	
	private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");
    private String NetworkDeviceOnBoardingUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_1");
    private String campusNetworkAssuranceUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_2");
    private String ScalableAccessPolicyUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_3");
    private String CampusSoftwareImageManagementUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_4");
    private String CampusNetworkSegmentationUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_5");
    private String cxCloudAccount = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
	private static ThreadLocal<LifecyclePage> page = ThreadLocal.withInitial(() -> new LifecyclePage());

	@Test(groups = {"sanity"}, description = "T135696 & T133716: Success Track(Private) community links are provided for all pitstops in Campus Software Image Management")
	public void verifyCuratedCommunityLinkForCampusSWImageManagement() {
		SoftAssert softAssert = new SoftAssert();
		String currentHighLightedPitStop;
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusSoftwareImageManagementUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		String currentPitStopName = ciscoCommunityPage.getCurrentPitStopName();
		currentHighLightedPitStop = currentPitStopName;
		for(int i = 0; i <= PitStopsName.values().length; i++)
		{
			softAssert.assertTrue(ciscoCommunityPage.clickSuccessTrackCommunityLink().getCurrentURLFromSecondTab().contains("t5/Campus%20Software%20Image%20Management/bd-p/ibn-swim"), "Success Track Community URL for Campus Software Image Management is not displaying properly for "+currentHighLightedPitStop);
			ciscoCommunityPage.moveToNextPitstop();
			currentHighLightedPitStop = ciscoCommunityPage.getHighlightedPitstopName();
			System.out.println(currentHighLightedPitStop);
		}
		
		softAssert.assertAll();
	}
	
	@Test(description = "T133715 & T135695: Success Track(Private) community links are provided for all pitstops in Network Device Onboarding")
	public void verifyCuratedCommunityLinkForNetworkDeviceOnboarding() {
		SoftAssert softAssert = new SoftAssert();
		String currentHighLightedPitStop;
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, NetworkDeviceOnBoardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		String currentPitStopName = ciscoCommunityPage.getCurrentPitStopName();
		currentHighLightedPitStop = currentPitStopName;
		
		for(int i = 0; i <= PitStopsName.values().length; i++)
		{
			softAssert.assertTrue(ciscoCommunityPage.clickSuccessTrackCommunityLink().getCurrentURLFromSecondTab().contains("t5/Network%20Device%20Onboarding/bd-p/ibn-onboarding"), "Success Track Community URL for Network Device Onboarding is not displaying properly for "+currentHighLightedPitStop);
			ciscoCommunityPage.moveToNextPitstop();
			currentHighLightedPitStop = ciscoCommunityPage.getHighlightedPitstopName();
			System.out.println(currentHighLightedPitStop);
		}
		
		softAssert.assertAll();
	}
	
	@Test(description = "T133714 & T135694: Success Track(Private) community links are provided for all pitstops in Scalable Access Policy")
	public void verifyCuratedCommunityLinkForScalableAccessPolicy() {
		SoftAssert softAssert = new SoftAssert();
		String currentHighLightedPitStop;
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, ScalableAccessPolicyUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		String currentPitStopName = ciscoCommunityPage.getCurrentPitStopName();
		currentHighLightedPitStop = currentPitStopName;
		for(int i = 0; i <= PitStopsName.values().length; i++)
		{
			softAssert.assertTrue(ciscoCommunityPage.clickSuccessTrackCommunityLink().getCurrentURLFromSecondTab().contains("t5/Scalable%20Access%20Policy/bd-p/ibn-policy"), "Success Track Community URL for Scalable Access Policy is not displaying properly for "+currentHighLightedPitStop);
			ciscoCommunityPage.moveToNextPitstop();
			currentHighLightedPitStop = ciscoCommunityPage.getHighlightedPitstopName();
			System.out.println(currentHighLightedPitStop);
		}
		
		softAssert.assertAll();
	}
	
	@Test(description = "T133712 & T135692: Success Track(Private) community links are provided for all pitstops in Campus Network Assurance")
	public void verifyCuratedCommunityLinkForCampusNetworkAssurance() {
		SoftAssert softAssert = new SoftAssert();
		String currentHighLightedPitStop;
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		String currentPitStopName = ciscoCommunityPage.getCurrentPitStopName();
		currentHighLightedPitStop = currentPitStopName;
		for(int i = 0; i <= PitStopsName.values().length; i++)
		{
			softAssert.assertTrue(ciscoCommunityPage.clickSuccessTrackCommunityLink().getCurrentURLFromSecondTab().contains("t5/Campus%20Network%20Assurance/bd-p/ibn-assurance"), "Success Track Community URL for Campus Network Assurance is not displaying properly for "+currentHighLightedPitStop);
			ciscoCommunityPage.moveToNextPitstop();
			currentHighLightedPitStop = ciscoCommunityPage.getHighlightedPitstopName();
			System.out.println(currentHighLightedPitStop);
		}
		
		softAssert.assertAll();
	}
	
	@Test(description = "T133713 & T135693: Success Track(Private) community links are provided for all pitstops in Campus Network Segmentation")
	public void verifyCuratedCommunityLinkForCampusNetworkSegmentation() {
		SoftAssert softAssert = new SoftAssert();
		String currentHighLightedPitStop;
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		String currentPitStopName = ciscoCommunityPage.getCurrentPitStopName();
		currentHighLightedPitStop = currentPitStopName;
		for(int i = 0; i <= PitStopsName.values().length; i++)
		{
			softAssert.assertTrue(ciscoCommunityPage.clickSuccessTrackCommunityLink().getCurrentURLFromSecondTab().contains("t5/Campus%20Network%20Segmentation/bd-p/ibn-segmentation"), "Success Track Community URL for Campus Network Segmentation is not displaying properly for "+currentHighLightedPitStop);
			ciscoCommunityPage.moveToNextPitstop();
			currentHighLightedPitStop = ciscoCommunityPage.getHighlightedPitstopName();
			System.out.println(currentHighLightedPitStop);
		}
		
		softAssert.assertAll();
	}
	
	@Test(description = "TC225342: Verify the Community threads are based on the user's usecase and pitstop")
	public void verifyCommunityThreadsBasedOnUseCase() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		SoftAssert softAssert = new SoftAssert();
		ciscoCommunityPage.login().switchCXCloudAccount(cxCloudAccount).selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);
		
		//Verifying for Network Device Onboarding Use Case
		ciscoCommunityPage.selectContextSelector(CxHomePage.USE_CASE, NetworkDeviceOnBoardingUseCase).selectCarousel(CarouselName.LIFECYCLE);
		softAssert.assertEquals(ciscoCommunityPage.getSuccessTrackCommunityText(), NetworkDeviceOnBoardingUseCase+" Community", "Community Threads are not proper");
		
		//Verifying for Campus Network Assurance Use Case
		ciscoCommunityPage.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase).selectCarousel(CarouselName.LIFECYCLE);
		softAssert.assertEquals(ciscoCommunityPage.getSuccessTrackCommunityText(), campusNetworkAssuranceUseCase+" Community", "Community Threads are not proper");
		
		//Verifying for Scalable Access Policy Use Case
		ciscoCommunityPage.selectContextSelector(CxHomePage.USE_CASE, ScalableAccessPolicyUseCase).selectCarousel(CarouselName.LIFECYCLE);
		softAssert.assertEquals(ciscoCommunityPage.getSuccessTrackCommunityText(), ScalableAccessPolicyUseCase+" Community", "Community Threads are not proper");
		
		//Verifying for Campus Software Image Management Use Case
		ciscoCommunityPage.selectContextSelector(CxHomePage.USE_CASE, CampusSoftwareImageManagementUseCase).selectCarousel(CarouselName.LIFECYCLE);
		softAssert.assertEquals(ciscoCommunityPage.getSuccessTrackCommunityText(), CampusSoftwareImageManagementUseCase+" Community", "Community Threads are not proper");
		
		//Verifying for Campus Network Segmentation Use Case
		ciscoCommunityPage.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase).selectCarousel(CarouselName.LIFECYCLE);
		softAssert.assertEquals(ciscoCommunityPage.getSuccessTrackCommunityText(), CampusNetworkSegmentationUseCase+" Community", "Community Threads are not proper");
	}
	
	@Test(description = "T132417: Community Links for use case: Campus Network Assurance")
	public void verifyPublicCommunityForCampusNetworkAssurance() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		assertTrue(ciscoCommunityPage.getPublicCommunityURL().contains("/t5/wireless-and-mobility/bd-p/5956-discussions-getting-started-wireles"), "Public URL is not correct for Campus Network Assurance");
	}
	
	@Test(description = "T132418: Community Links for use case: Campus Network Segmentation")
	public void verifyPublicCommunityForCampusNetworkSegmentation() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		ciscoCommunityPage.clickPublicCommunityLink();
		assertTrue(ciscoCommunityPage.getPublicCommunityURL().contains("t5/software-defined-access-sda/bd-p/discussions-sd-access"), "Public URL is not correct for Campus Network Segmentation");
	}

	@Test(description = "T132419: Community Links for use case: Scalable Access Policy")
	public void verifyPublicCommunityForScalableAccessPolicy() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, ScalableAccessPolicyUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		ciscoCommunityPage.clickPublicCommunityLink();
		assertTrue(ciscoCommunityPage.getPublicCommunityURL().contains("t5/software-defined-access-sda/bd-p/discussions-sd-access"), "Public URL is not correct for Scalable Access Policy");
	}
	
	@Test(description = "T132420: Community Links for use case: Network Device Onboarding")
	public void verifyPublicCommunityForNetworkDeviceOnboarding() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, NetworkDeviceOnBoardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		ciscoCommunityPage.clickPublicCommunityLink();
		assertTrue(ciscoCommunityPage.getPublicCommunityURL().contains("t5/digital-network-architecture/bd-p/discussions-dna"), "Public URL is not correct for Network Device Onboarding");
	}
	
	@Test(description = "T132421: Community Links for use case: Campus Software Image Management")
	public void verifyPublicCommunityForCampusSoftwareImageManagement() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusSoftwareImageManagementUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		ciscoCommunityPage.clickPublicCommunityLink();
		assertTrue(ciscoCommunityPage.getPublicCommunityURL().contains("t5/digital-network-architecture/bd-p/discussions-dna"), "Public URL is not correct for Campus Software Image Management");
	}
	
	@Test(description = "TC225339: Verify the Community section displays the top 1 notification from the public communities")
	public void verifyPublicCommunityTop1Notification() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		assertTrue(ciscoCommunityPage.getTop1PublicNotification(), "Top 1 notification from Public community is not displayed");
	}
	
	@Test(description = "T133710: Tile title is 'CISCO COMMUNITY'")
	public void verifyTileTitleForCiscoCommunity() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		assertEquals(ciscoCommunityPage.getCiscoCommunityTileTitle(), "CISCO COMMUNITY", "Tile title is not displayed properly");
	}
	
	@Test(description = "TC225340: Verify the Community section displays the top 1 notifications from the Success track communities")
	public void verifySuccessTrackCommunityTop1Notification() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		assertTrue(ciscoCommunityPage.getTop1SuccessTrackNotification(), "Top 1 notification from Success Track community is not displayed");
	}
	
	@Test(description = "TC226562: Verify that the 360 view contains the title(contains success track and usecase),session date,brief description,experts information and the link'ADD TO CALENDAR'")
	public void verifyASKQandAContainsDetailsIn360View() {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		SoftAssert softAssert = new SoftAssert();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, NetworkDeviceOnBoardingUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		if(!ciscoCommunityPage.checkAskQandADisplayed())
			throw new SkipException("No Ask The Expert Q&A is displayed to test this scenario");
		
		ciscoCommunityPage.clickOnAskQandATitle();
		softAssert.assertTrue(ciscoCommunityPage.checkAskQandA360ViewDisplayed(), "360 View for Ask The Expert Q&A is not displayed");
		softAssert.assertEquals(ciscoCommunityPage.getAskQandASessionTitleFromHomePage(), ciscoCommunityPage.getAskQandASessionTitleFrom360View(), "Title displaying in 360 view is not matching with the title displaying in Home Page");
		softAssert.assertEquals(ciscoCommunityPage.getAskQandAEventTimeFromHomePage(), ciscoCommunityPage.getAskQandAEventTimeFrom360View(), "Event Time displaying in the 360 view is not matching with Event time displaying in Home Page");
		softAssert.assertTrue(ciscoCommunityPage.checkAddToCalenderDisplayedIn360View(), "Add To Calender button is not being displayed in 360 View");
		softAssert.assertTrue(ciscoCommunityPage.verifyPresenterInformationDisplayedForAskQandA360View(), "Presenter Information is not displyed in 360 View");
		softAssert.assertAll();
	}
	
	@Test(description = "T133854: Communities links should be sorted by last post date (most recent first) in the community page.")
	public void verifyCommunityLinksDisplayMostRecentUpdate() throws ParseException {
		CiscoCommunityPage ciscoCommunityPage = new CiscoCommunityPage();
		SoftAssert softAssert = new SoftAssert();
		ciscoCommunityPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking for Success Track Community
		String topSuccessTrackCommunityTopic = ciscoCommunityPage.getTopSuccessTrackCommunityTopic();
		System.out.println("Topic from Lifecycle Page: "+topSuccessTrackCommunityTopic);
		String latestPostDate = ciscoCommunityPage.getLatestPostFromSuccessTrackCommunityURL(topSuccessTrackCommunityTopic);
		System.out.println("Latest Post Date: "+latestPostDate);
		Date date = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa").parse(latestPostDate);
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy hh:mm aaa");
		String convertedDate = formatter.format(date);
		System.out.println(convertedDate);
		System.out.println("Last Comment from Lifecycle Page: "+ciscoCommunityPage.getLastCommentfromSuccessTrackTopNotification());
		softAssert.assertTrue(ciscoCommunityPage.getLastCommentfromSuccessTrackTopNotification().contains(convertedDate), "Latest Comment is not got updated for Success Track Community Top 1 Notification");
		
		//Checking for Public Community
		String topPublicCommunityTopic = ciscoCommunityPage.getTopPublicCommunityTopic();
		latestPostDate = ciscoCommunityPage.getLatestPostFromPublicCommunityURL(topPublicCommunityTopic);
		System.out.println("Latest Post Date: "+latestPostDate);
		date = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa").parse(latestPostDate);
		convertedDate = formatter.format(date);
		System.out.println(convertedDate);
		System.out.println("Last Comment from Lifecycle Page: "+ciscoCommunityPage.getLastCommentfromPubicCommunityTopNotification());
		softAssert.assertTrue(ciscoCommunityPage.getLastCommentfromPubicCommunityTopNotification().contains(convertedDate), "Latest Comment is not got updated for Public Community Top 1 Notification");
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
