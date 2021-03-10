package com.cisco.tests.lifecycle;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.lifecycle.AcceleratorsPage;
import com.cisco.pages.lifecycle.LifecyclePage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.PitStopsName;
import com.cisco.testdata.lifecycle.ACCPoJo;
import com.cisco.testdata.lifecycle.ACCPoJo.ItemsItem;
import com.cisco.utils.LifeCycleUtils;

import io.qameta.allure.Issue;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.lang.NullPointerException;

import static com.cisco.testdata.Data.ACCELERATOR_DATA;
import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;
import static com.cisco.testdata.lifecycle.Data.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AcceleratorsPageIT extends DriverBase {

    private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");
    private String networkDeviceOnboardingUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_1");
    private String campusNetworkAssuranceUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_2");
    private String campusNetworkSegmentationUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_5");
    private String campusSoftwareImageManagementUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_4");
    private String cxCloudAccount = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
    private String partnername = ACCELERATOR_DATA.get("PARTNER_NAME");
    private String cloudAccountWithPartnerContent = LIFECYCLE_COMMON_DATA.get("PARTNER_CLOUD_ACCOUNT");
    private String cloudAccountWith3Request = LIFECYCLE_COMMON_DATA.get("CAV_ACCOUNT_WITH_3REQUEST");
    private static ThreadLocal<LifecyclePage> page = ThreadLocal.withInitial(() -> new LifecyclePage());
    //    private String cxCloudAccount1 = LIFECYCLE_COMMON_DATA.get("MULTIPLEBUID_Cloud_Account");
    private ACCPoJo.ItemsItem CISCO_ACC_ITEM1;
    private ACCPoJo.ItemsItem CISCO_ACC_ITEM2;
    private ACCPoJo.ItemsItem CISCO_REQUESTED_ACC_ITEM;
    private ACCPoJo.ItemsItem CISCO_COMPLETED_ACC_ITEM;
    private ACCPoJo.ItemsItem CISCO_FEEDBACK_ACC_ITEM;
    public List<ACCPoJo> ACC_DATA_LIST_CISCO;
    public List<ACCPoJo> ACC_DATA_LIST_PARTNER;
    public List<ACCPoJo> ACC_DATA_LIST_NISA;
    public ACCPoJo CISCO_ACC_DATA;
    public ACCPoJo CISCO_ACC_DATA_NISA;
    private ACCPoJo.ItemsItem CISCO_ACC_ITEM_NISA;
    public ACCPoJo PARTNER_ACC_DATA;
    public ACCPoJo CISCO_REQUESTED_ACC_DATA;
    public ACCPoJo CISCO_COMPLETED_ACC_DATA;
    public ACCPoJo CISCO_FEEDBACK_ACC_DATA;

    @BeforeTest(alwaysRun = true)
    public void accDataSetUp() {
        try {
            ACC_DATA_LIST_CISCO = getACCDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, DEFAULT_USER_ROLE);
            ACC_DATA_LIST_PARTNER = getACCDataFromAllUseCase(CUSTOMER_ID_GRAPHIC, SOLUTION, DEFAULT_USER_ROLE);
            CISCO_ACC_DATA = getACCData(ACC_DATA_LIST_CISCO, itemsItem -> null == itemsItem.getProviderInfo());
            PARTNER_ACC_DATA = getACCData(ACC_DATA_LIST_PARTNER, itemsItem -> null != itemsItem.getProviderInfo());
            CISCO_ACC_ITEM1 = CISCO_ACC_DATA.getItems().get(0);
            CISCO_ACC_ITEM2 = CISCO_ACC_DATA.getItems().get(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize ACC test data");
        }
    }


    @Test(description = "PBC-32: T121422: View all link opens a modal to show all applicable ACCs")
    public void verifyACCViewAllLinkFunctionality() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
//        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
//        acceleratorsPage.postACC(CISCO_ACC_ITEM);
        acceleratorsPage.login()
                .switchCXCloudAccount(CX_CLOUD_ACCOUNT_L2)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, SUCCESS_TRACK)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        acceleratorsPage.clickACCViewAllLink();
        assertEquals(acceleratorsPage.getACCModalWindowTitle(), "Accelerators");
    }

    @Test(description = "PBC-159 T122081- Verify Recommended ACCs have a link to schedule session in View All")
    public void verifyRequest1_1button() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        List<String> request1_1ButtonTexts = acceleratorsPage.clickACCViewAllLink().getAllRequest1_1ButtonText();
        request1_1ButtonTexts.forEach(buttonText -> assertEquals(buttonText, "Request a 1-on-1"));

    }

    @Test(groups = {"sanity"}, description = "Verify the content of Accelerator card UI in the Home Page")
    public void verifyACCCardUI() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        softAssert.assertEquals(acceleratorsPage.getACCTitle(), "ACCELERATORS");
        //softAssert.assertEquals(acceleratorsPage.getACCSubtitle(), "1-on-1 coaching to put you in the fast lane");
        softAssert.assertTrue(acceleratorsPage.getSourceAttributeOfACCImage().contains(".png"));
        softAssert.assertTrue(acceleratorsPage.getACCDescription().length() > 0);
        softAssert.assertAll();
    }

    @Test(description = "Verify bookmark Functionality of the Accelerator")
    public void verifyBookmarkFunctionality() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        acceleratorsPage.clickACCViewAllLink()
                .clickBookMarkRibbon();
        assertTrue(acceleratorsPage.getACCRibbonClass().contains("ribbon__blue"));
    }

    @Test(description = "PBC-159: T122082: Cards display the ACC title and description")
    public void verifyACCCardViewDisplayTitleAndDescription() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        acceleratorsPage.clickACCViewAllLink();
        int totalNoOfACCTileDisplayed = acceleratorsPage.getTotalNumberOfTopicsInTitle();
        softAssert.assertEquals(acceleratorsPage.getTotalNoOfCardsDisplayed(), totalNoOfACCTileDisplayed, "Card view is not displaying all the ACC Tiles");
        softAssert.assertEquals(acceleratorsPage.getTotalNoOfCardTitleDisplayed(), totalNoOfACCTileDisplayed, "Card view is not displaying title for ACC tiles");
        //softAssert.assertEquals(acceleratorsPage.getTotalNoOfCardsDescriptionDisplayed(), totalNoOfACCTileDisplayed, "Card view is not displaying descriptions for all ACC tiles");
        softAssert.assertAll();
    }

    @Test(description = "T125437: ACC View All modal should be filterable by status/bookmarked")
    public void verifyACCFilterWithStatusAndBookmarked() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);
         
         if(!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage()>0))
         	throw new SkipException("No ACC available under Lifecycle to test this scenario");

         acceleratorsPage.clickACCViewAllLink();
         HashMap<String, Integer> countBeforeApplyingFilter = acceleratorsPage.getTotalNoOfTilesWithStatus();
         acceleratorsPage.getTotalFilteredACCInViewAllPage("Status", "Requested");
         softAssert.assertEquals(acceleratorsPage.getTotalNoOfCardsDisplayed(), countBeforeApplyingFilter.get("Requested").intValue(), "Status filter with Requested is not working properly");
         
         /*acceleratorsPage.getTotalFilteredACCInViewAllPage("Status", "Scheduled");
         softAssert.assertEquals(acceleratorsPage.getTotalNoOfCardsDisplayed(), countBeforeApplyingFilter.get("Scheduled").intValue(), "Status filter with Scheduled is not working properly");*/
         
         acceleratorsPage.getTotalFilteredACCInViewAllPage("Status", "In Progress");
         softAssert.assertEquals(acceleratorsPage.getTotalNoOfCardsDisplayed(), countBeforeApplyingFilter.get("In Progress").intValue(), "Status filter with In Progress is not working properly");
         
         acceleratorsPage.getTotalFilteredACCInViewAllPage("Status", "Completed");
         softAssert.assertEquals(acceleratorsPage.getTotalNoOfCardsDisplayed(), countBeforeApplyingFilter.get("Completed").intValue(), "Status filter with Completed is not working properly");
         
         softAssert.assertAll();
    }

    @Test(description = "T133971: ACC View All table view should have status messages")
    public void verifyACCStatusInViewAllTableView() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        acceleratorsPage.clickACCViewAllLink().clickACCTableViewButton();
        assertEquals(acceleratorsPage.getStatusColumnNameViewAllTableView(), "Status", "Status column has not been displayed");
    }

    @Test(description = "T134544: ACC View All table view should be able to bookmark/unbookmark items")
    public void verifyBookmarkOnTableView() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        //Verifying Bookmark in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();
        softAssert.assertTrue(acceleratorsPage.verifyBookmarkOnCardView(), "Bookmark is not working properly in ACC Card View");;

        //Verifying Bookmark in Table View
        acceleratorsPage.clickACCTableViewButton();
        softAssert.assertTrue(acceleratorsPage.verifyBookmarkOnTableView(), "Bookmark is not working properly in ACC Table View");
        softAssert.assertAll();
    }

    @Test(description = "T125441: ACC View All modal should be filterable by status (requested)")
    public void veirfyACCFilterWithStatusRequested() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        acceleratorsPage.clickACCViewAllLink();
        HashMap<String, Integer> countBeforeApplyingFilter = acceleratorsPage.getTotalNoOfTilesWithStatus();
        acceleratorsPage.getTotalFilteredACCInViewAllPage("Status", "Requested");
        assertEquals(acceleratorsPage.getTotalNoOfCardsDisplayed(), countBeforeApplyingFilter.get("Requested").intValue(), "Status filter with Requested is not working properly");
    }

    @Test(description = "TC226814: Test user able to see 360 view when clicking on a subject from Home Page for Cisco ACC")
    public void verifyCiscoACC360ViewFromHomePage() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        if (!(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0))
            throw new SkipException("No Cisco ACC available to test this scenario");

        String titleFromHomePage = acceleratorsPage.getACCFirstTitleFromHomePage();
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened");
        softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), titleFromHomePage, "Title in 360 View is not matching with the title in Home Page");
        softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in ACC 360 View");
        softAssert.assertTrue(acceleratorsPage.checkEngagementsIn360ViewDisplayed(), "Engagement details are not available in 360 View");
        softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in 360 view");
        softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in 360 view");
        softAssert.assertAll();
    }

    @Test(description = "TC226816: Test user able to see 360 view when clicking on a subject from View All Page for Cisco ACC")
    public void verifyCiscoACC360ViewFromViewAllPage() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        if (acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") < 0)
            throw new SkipException("No Cisco ACC available to test this scenario");

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
        softAssert.assertAll();
    }

    @Test(description = "TC230090: Verify that the Acc tiles shows title,status, Thumbsup symbol with postive feed back perecntage and tick mark symbol with total no of completion")
    public void verifyAccTilesInViewAllPage() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        
        try {
        	CISCO_FEEDBACK_ACC_DATA = getACCData(ACC_DATA_LIST_CISCO, itemsItem -> itemsItem.getAssetCompletionCount() > 0 && itemsItem.getFeedbackEngagement() > 0);
        	CISCO_FEEDBACK_ACC_ITEM = CISCO_FEEDBACK_ACC_DATA.getItems().get(0);
        	System.out.println("Use Case: "+ CISCO_FEEDBACK_ACC_DATA.getItems().get(0).getUsecase());
        	System.out.println("Use Case: "+ CISCO_FEEDBACK_ACC_DATA.getItems().get(0).getPitstop());
        	acceleratorsPage.login()
        			.switchCXCloudAccount(cxCloudAccount)
        			.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
        			.selectContextSelector(CxHomePage.USE_CASE, CISCO_FEEDBACK_ACC_ITEM.getUsecase())
        			.selectCarousel(CarouselName.LIFECYCLE);
        	
        	String pitStop = CISCO_FEEDBACK_ACC_ITEM.getPitstop();
        	if(!pitStop.equalsIgnoreCase(acceleratorsPage.getCurrentPitStopName()))
        		acceleratorsPage.changePitStop(PitStopsName.valueOf(pitStop));
        	
        	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();
        	
        	softAssert.assertTrue(acceleratorsPage.checkStatusInCardViewDisplayed(), "Status is not being displayed or No data available to test this scenario in Card View");
            softAssert.assertTrue(acceleratorsPage.checkACCTitleInCardViewDisplayed(), "Title has not been displayed in Card View");
            softAssert.assertTrue(acceleratorsPage.checkACCPartnerNameInCardViewDisplayed() == 0, "Partner Name is being displayed in Card View");
            softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInCardView(AcceleratorsPage.ACCFeedbackRatingClassName), "Tick Mark symbol is not being displayed or No data is available to test this scenario in Card View");
            softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInCardView(AcceleratorsPage.ACCFeedbackRatingClassName), "Total No Of Completion count is not being displayed or No data is available to test this scenario in Card View");
            softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInCardView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive thumbs Up Symbol is not being displayed or No data is available to test this scenario in Card View");
            softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInCardView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive Percentage count is not being displayed or No data is available to test this scenario in Card View");

            //Checking in Table View
            acceleratorsPage.clickACCTableViewButton();
            softAssert.assertTrue(acceleratorsPage.checkStatusInTableViewDisplayed(), "Status is not being displayed or No data available to test this scenario in Table View");
            softAssert.assertTrue(acceleratorsPage.checkACCTitleInTableViewDisplayed(), "Title has not been displayed in Table View");
            softAssert.assertTrue(acceleratorsPage.checkACCPartnerNameInTableViewDisplayed().equalsIgnoreCase("Cisco"), "ACC Delivery Type is not being displayed properly");
            softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInTableView(AcceleratorsPage.ACCFeedbackRatingClassName), "Tick Mark symbol is not being displayed or No data is available to test this scenarion in Table View");
            softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInTableView(AcceleratorsPage.ACCFeedbackRatingClassName), "Total No Of Completion count is not being displayed or No data is available to test this scenario in Table View");
            softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInTableView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive thumbs Up Symbol is not being displayed or No data is available to test this scenario in Table View");
            softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInTableView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive Percentage count is not being displayed or No data is available to test this scenario in Table View");
            softAssert.assertAll();
        	
        } catch (NullPointerException e) {
			throw new SkipException("No ACC with Feedback and Rating available to test this scenario");
        }
    }

    @Test(description = "TC226815: Test user able to see 360 view when clicking on a subject from Home Page for Partner ACC", groups = {"singleThread"})
    public void verifyPartnerACC360ViewFromHomePage() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWithPartnerContent)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");
        
        if (!acceleratorsPage.checkPartnerContentDisplayed("Content Provider"))
            throw new SkipException("No Partner Data available under Lifecycle to test this scenario");

        //Checking for Partner Data availability
        if (acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnername) <= 0)
            throw new SkipException("No Partner ACC available to test this scenario");

        acceleratorsPage.clickOnFirstTitleFromHomePage();
        assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened");
        softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), acceleratorsPage.getACCFirstTitleFromHomePage(), "Title in 360 View is not matching with the title in Home Page");
        softAssert.assertEquals(acceleratorsPage.getPartnerNameFrom360View(), acceleratorsPage.getPartnerNameForFirstACCInHomePage(), "Partner Name in 360 view is not matching with the name present in the tile");
        softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in ACC 360 View");
        softAssert.assertTrue(acceleratorsPage.checkRequestA1On1ButtonDisplayed(), "Request A 1-On-1 button is not availabe in 360 view");
        softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in 360 view");
        softAssert.assertAll();
    }

    @Test(description = "TC226817: Test user able to see 360 view when clicking on a subject from View All Page for Partner ACC", groups = {"singleThread"})
    public void verifyPartnerACC360ViewFromViewAllPage() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWithPartnerContent)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        //Checking in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();
        acceleratorsPage.getTotalFilteredACCInViewAllPage("Partner", partnername);
        if (!(acceleratorsPage.getTotalNoOfCardsDisplayed() > 0))
            throw new SkipException("No Partner ACC available to test this scenario");

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
        softAssert.assertAll();
    }

    @Test(description = "TC230345: Verify that the partner Acc tiles shows title,status, Thumsup symol with postive feed back perecntage and tick mark symbol with total no of completion", groups = {"singleThread"})
    public void verifyPartnerAccTilesInViewAllPage() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWithPartnerContent)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!acceleratorsPage.checkPartnerContentDisplayed("Content Provider"))
            throw new SkipException("No Partner Data available under Lifecycle to test this scenario");
        
        //Checking for Partner Data availability
        if (acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", partnername) <= 0)
            throw new SkipException("No Partner ACC available to test this scenario");

        //Checking in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();
        acceleratorsPage.getTotalFilteredACCInViewAllPage("Partner", partnername);
        if (acceleratorsPage.getTotalNoOfCardsDisplayed() <= 0)
            throw new SkipException("No Partner ACC available to test this scenario");

        softAssert.assertTrue(acceleratorsPage.checkStatusInCardViewDisplayed(), "Status is not being displayed or No data available to test this scenario in Card View");
        softAssert.assertTrue(acceleratorsPage.checkACCTitleInCardViewDisplayed(), "Title has not been displayed in Card View");
        softAssert.assertTrue(acceleratorsPage.checkACCPartnerNameInCardViewDisplayed() > 0, "Partner Name has not been displayed in Card View");

        if (!(acceleratorsPage.getTotalNoOfACCWithPositiveFeedback(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName) > 0))
            throw new SkipException("No Partner ACC with Positive Feedback Available to test this scneario");

        if (!(acceleratorsPage.getTotalNoOfACCWithRatings(AcceleratorsPage.ACCFeedbackRatingClassName) > 0))
            throw new SkipException("No Partner ACC with Ratings available to test this scenario");

        softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInCardView(AcceleratorsPage.ACCFeedbackRatingClassName), "Tick Mark symbol is not being displayed or No data is available to test this scenario in Card View");
        softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInCardView(AcceleratorsPage.ACCFeedbackRatingClassName), "Total No Of Completion count is not being displayed or No data is available to test this scenario in Card View");
        softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInCardView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive thumbs Up Symbol is not being displayed or No data is available to test this scenario in Card View");
        softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInCardView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive Percentage count is not being displayed or No data is available to test this scenario in Card View");

        //Checking in Table View
        acceleratorsPage.clickACCTableViewButton();
        softAssert.assertTrue(acceleratorsPage.checkStatusInTableViewDisplayed(), "Status is not being displayed or No data available to test this scenario in Table View");
        softAssert.assertTrue(acceleratorsPage.checkACCTitleInTableViewDisplayed(), "Title has not been displayed in Table View");
        softAssert.assertFalse(acceleratorsPage.checkACCPartnerNameInTableViewDisplayed().equalsIgnoreCase("Cisco"), "Partner Name is not displaying properly");
        softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInTableView(AcceleratorsPage.ACCFeedbackRatingClassName), "Tick Mark symbol is not being displayed or No data is available to test this scenarion in Table View");
        softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInTableView(AcceleratorsPage.ACCFeedbackRatingClassName), "Total No Of Completion count is not being displayed or No data is available to test this scenario in Table View");
        softAssert.assertTrue(acceleratorsPage.checkCountAndPositiveSymbolDisplayedInTableView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive thumbs Up Symbol is not being displayed or No data is available to test this scenario in Table View");
        softAssert.assertTrue(acceleratorsPage.checkNoOfCompletionAndFeedbackCountDisplayedInTableView(AcceleratorsPage.ACCFeedbackPositiveThumbsUpClassName), "Positive Percentage count is not being displayed or No data is available to test this scenario in Table View");
        softAssert.assertAll();
    }

    @Test(description = "TC227545: Test ACC UI when 3 request available per success track in Home page and in View All page", groups = {"singleThread"})
    public void verifyACCUIWhen3RequestLeft() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        ACC_DATA_LIST_NISA = getACCDataFromAllUseCase(CUSTOMER_ID_NISA, SOLUTION, DEFAULT_USER_ROLE);
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_NISA);
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWith3Request)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        //Check for Data Availability
        if (acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") <= 0)
            throw new SkipException("No Cisco ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        if (acceleratorsPage.checkAvailableAccRequest() != 3)
            throw new SkipException("ACC with 3 request left is not available to test this scenario");
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(3), "UI is not looking corrent when 3 request Left for Customer in Home Page");
        acceleratorsPage.close360View();

        //Checking in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(3), "UI is not looking corrent when 3 request Left for Customer in View All Card View");
        acceleratorsPage.close360View();

        //Checking in Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(3), "UI is not looking corrent when 3 request Left for Customer in View All Table View");
        softAssert.assertAll();
    }

    @Test(description = "TC227546: Test ACC UI when 2 request available per success track in Home page and in View All page", groups = {"singleThread"})
    public void verifyACCUIWhen2RequestLeft() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        ACC_DATA_LIST_NISA = getACCDataFromAllUseCase(CUSTOMER_ID_NISA, SOLUTION, DEFAULT_USER_ROLE);
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_NISA);
        CISCO_ACC_DATA_NISA = getACCData(ACC_DATA_LIST_NISA, itemsItem -> null == itemsItem.getProviderInfo());
        acceleratorsPage.postACC(CISCO_ACC_DATA_NISA.getItems().get(0));
        
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWith3Request)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, CISCO_ACC_DATA_NISA.getItems().get(0).getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE);

        //Check for Data Availability
        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        if (!(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0))
            throw new SkipException("No Cisco ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        if (acceleratorsPage.checkAvailableAccRequest() != 2)
            throw new SkipException("ACC with 2 request left is not available to test this scenario");
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(2), "UI is not looking corrent when 3 request Left for Customer in Home Page");
        acceleratorsPage.close360View();

        //Checking in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(2), "UI is not looking corrent when 3 request Left for Customer in View All Card View");
        acceleratorsPage.close360View();

        //Checking in Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(2), "UI is not looking corrent when 3 request Left for Customer in View All Table View");
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_NISA);
        softAssert.assertAll();
    }

    @Test(description = "TC227547: Test ACC UI when 1 request available per success track in Home page and in View All page", groups = {"singleThread"})
    public void verifyACCUIWhen1RequestLeft() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        ACC_DATA_LIST_NISA = getACCDataFromAllUseCase(CUSTOMER_ID_NISA, SOLUTION, DEFAULT_USER_ROLE);
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_NISA);
        CISCO_ACC_DATA_NISA = getACCData(ACC_DATA_LIST_NISA, itemsItem -> null == itemsItem.getProviderInfo());
        acceleratorsPage.postACC(CISCO_ACC_DATA_NISA.getItems().get(0));
        acceleratorsPage.postACC(CISCO_ACC_DATA_NISA.getItems().get(1));
		
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWith3Request)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, CISCO_ACC_DATA_NISA.getItems().get(0).getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE);

        //Check for Data Availability
        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        if (!(acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") > 0))
            throw new SkipException("No Cisco ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        if (acceleratorsPage.checkAvailableAccRequest() != 1)
            throw new SkipException("ACC with 1 request left is not available to test this scenario");
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(1), "UI is not looking corrent when 1 request Left for Customer in Home Page");
        acceleratorsPage.close360View();

        //Checking in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(1), "UI is not looking corrent when 1 request Left for Customer in View All Card View");
        acceleratorsPage.close360View();

        //Checking in Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhenRequestAvailable(1), "UI is not looking corrent when 1 request Left for Customer in View All Table View");
        
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_NISA);
        softAssert.assertAll();
    }

    @Test(description = "TC226818: Test user able to close 360 view from home page and from view all page")
    public void verifyUserAbleToCloseACC360View() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        softAssert.assertTrue(acceleratorsPage.checkACC360ViewDisplayed(), "ACC 360 View is not displayed");
        acceleratorsPage.close360View();
        softAssert.assertFalse(acceleratorsPage.checkACC360ViewDisplayed(), "ACC 360 view is still open even after clicking on Close Button from Home Page");

        //Checking from Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertTrue(acceleratorsPage.checkACC360ViewDisplayed(), "ACC 360 View is not displayed");
        acceleratorsPage.close360View();
        softAssert.assertFalse(acceleratorsPage.checkACC360ViewDisplayed(), "ACC 360 view is still open even after clicking on Close Button from Card View");

        //Checking from Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertTrue(acceleratorsPage.checkACC360ViewDisplayed(), "ACC 360 View is not displayed");
        acceleratorsPage.close360View();
        softAssert.assertFalse(acceleratorsPage.checkACC360ViewDisplayed(), "ACC 360 view is still open even after clicking on Close Button from Card View");

        softAssert.assertAll();

    }

    @Test(description = "TC227549: Test there is no restriction for Partner ACC per success track in Home page and in View All page", groups = {"singleThread"})
    public void verifyPartnerACCDoesNotHaveRestrictions() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWithPartnerContent)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        if (!acceleratorsPage.checkPartnerContentDisplayed("Content Provider"))
            throw new SkipException("No Partner Data available to test this scenario");

        if (acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "PRESIDIO NETWORKED SOLUTIONS") <= 0)
            throw new SkipException("No Partner ACC available to test this scenario");

        //Checking from Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        softAssert.assertFalse(acceleratorsPage.checkACCEngagementsDisplayedIn360View(), "ACC Engagements are displayed for Partner ACC which is not expected");
        acceleratorsPage.close360View();

        //Checking from Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertFalse(acceleratorsPage.checkACCEngagementsDisplayedIn360View(), "ACC Engagements are displayed for Partner ACC which is not expected");
        acceleratorsPage.close360View();

        //Checking from Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertFalse(acceleratorsPage.checkACCEngagementsDisplayedIn360View(), "ACC Engagements are displayed for Partner ACC which is not expected");
        acceleratorsPage.close360View();

        softAssert.assertAll();
    }

    @Test(description = "T133347: Request 1-on-1 form should show a success message on submission success", groups = {"singleThread"})
    public void verifySuccessmsgOnSubmissionOfRequest() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        acceleratorsPage.clickACCViewAllLink();
        softAssert.assertEquals(acceleratorsPage.verifySuccessMsgOnSubmission(), "Your request has been successfully submitted", "Success Message displayed is not the correct one");

        //Deleting ACC Request
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        softAssert.assertAll();
    }

    @Test(description = "TC226823: Test user able to submit Request A 1-On-1 from 360 view", groups = {"singleThread"})
    public void verifyACCRequestFrom360() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);
        
        //Deleting ACC Request
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        acceleratorsPage.clickACCViewAllLink();
        acceleratorsPage.clickOnAccFirstTitleFromCard().clickRequest1On1Button();
        acceleratorsPage.FillACCRequestForm();
        acceleratorsPage.getACCRequestSubmissionmsg();
        softAssert.assertEquals(acceleratorsPage.getACCStatusFromCardView(1), "Requested", "Not able to Request ACC from 360 View");

        //Deleting ACC Request
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        softAssert.assertAll();
    }

    @Test(description = "TC226830: Test user able to differentiate the topic which is opened in the 360 view from Home page and from View All table and card view")
    public void verifyTileColorWhenOpens360() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        softAssert.assertEquals(acceleratorsPage.getTileColorWhen360Open("Home"), "#ecf4fc", "Back Groud color is not as per the mock up in Home Page when opens in 360 View");
        acceleratorsPage.close360View();
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertEquals(acceleratorsPage.getTileColorWhen360Open("Card"), "#ecf4fc", "Back Groud color is not as per the mock up in Card View when opens in 360 View");
        acceleratorsPage.close360View();
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertEquals(acceleratorsPage.getTileColorWhen360Open("Table"), "#ecf4fc", "Back Groud color is not as per the mock up in Table View when opens in 360 View");
        acceleratorsPage.close360View();
        softAssert.assertAll();
    }

    @Test(description = "TC226819:Test user able to maximize 360 view from home page and from View All page")
    public void verifyMaximizeButtonIn360() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        acceleratorsPage.clickOnFirstTitleFromHomePage();
        softAssert.assertTrue(acceleratorsPage.verifymaximize360().contains("fullscreen"), "Maximize didn't work properly in Home page");
        acceleratorsPage.close360View();
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertTrue(acceleratorsPage.verifymaximize360().contains("fullscreen"), "Maximize didn't work properly in view All card");
        acceleratorsPage.close360View();
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertTrue(acceleratorsPage.verifymaximize360().contains("fullscreen"), "Maximize didn't work properly in view All Table");
        acceleratorsPage.close360View();
        softAssert.assertAll();
    }

    @Test(description = "TC225358: Test Request 1-On-1 Button for the completed Cisco ACC program is enabled when sessions are available", groups = {"singleThread"})
    public void verifyRequest1On1ForCompletedStatusTile() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

        if (!acceleratorsPage.checkACCAvailabilityWithCompletedStatus())
            throw new SkipException("No Cisco ACC with Completed Status available to test this scenario");

        acceleratorsPage.getTotalFilteredACCInViewAllPage("Partner", "Cisco");
        softAssert.assertTrue(acceleratorsPage.verifyRequest1On1IsEnabledForCompletedTile("Cisco"), "Request 1-On-1 Button for the completed Cisco ACC program is not enabled when sessions are available");

        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        softAssert.assertAll();
    }

    @Test(description = "TC225127: verify that the user is able to request the same session multiple times.", groups = {"singleThread"})
    public void verifyACCRequestForCompletedStatusTile() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        acceleratorsPage.clickACCViewAllLink();
        softAssert.assertEquals(acceleratorsPage.ACCRequestFromCompletedStatusTile(), "Your request has been successfully submitted", "Not able to request Completed ACC again");

        //Deleting latest ACC Request
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        softAssert.assertAll();
    }

    @Test(description = "TC230094: Verify that LC tile notifications shows Thums up or down symbol when an ACC is completed", groups = {"singleThread"})
    public void verifyLCTileNotifications() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWithPartnerContent)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);
        
        //Checking for Data availability
        if(acceleratorsPage.getTotalNoOfNotificationInBellicon()<= 0)
        	throw new SkipException("No Notification available to test this scenario");
        
        acceleratorsPage.clickHomepageNotificationIcon();

        String[] arr=acceleratorsPage.verifyTitleAndcompletionDateInNotification();
        softAssert.assertNotNull(arr[2],"Thumbs Up Button is not available in LC tile notification");
        softAssert.assertNotNull(arr[3],"Thumbs Down Button is not available in LC tile notification");
        softAssert.assertAll();
    }

    @Test(description = "TC230346: Verify that selection of feedback in partner acc remains same after cliking on that.", groups = {"singleThread"})
    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvw04618")
    public void verifySelectionOfFeedbackStaysForPartner() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cloudAccountWithPartnerContent)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");
        
        if (!acceleratorsPage.checkPartnerContentDisplayed("Content Provider"))
            throw new SkipException("No Partner Data available under Lifecycle to test this scenario");

        if (acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "PRESIDIO NETWORKED SOLUTIONS") <= 0)
            throw new SkipException("No Partner ACC available to test this scenario");
        
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();
        if(acceleratorsPage.getStatusInCardViewDisplayed().equalsIgnoreCase("Completed")) {
        	acceleratorsPage.clickOnACCWithCompletedStatus();
        	String title = acceleratorsPage.getAccTitleFrom360View();
        	String feedbackBeforeSubmit = acceleratorsPage.getSelectedFeedbackOption();
        	if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Up"))
        		acceleratorsPage.provideThumbsDownFeedback();
    		else if(feedbackBeforeSubmit.equalsIgnoreCase("Thumbs Down") || feedbackBeforeSubmit.equalsIgnoreCase("No Feedback Selected"))
    			acceleratorsPage.provideThumbsUpFeedback();
        	acceleratorsPage.clickAccWithTitleInCardView(title);
    		String feedbackAfterSubmit = acceleratorsPage.getSelectedFeedbackOption();
    		System.out.println("After Submit: " + feedbackAfterSubmit);
    		assertTrue(!(feedbackBeforeSubmit.equalsIgnoreCase(feedbackAfterSubmit)), "Not able to submit feedback properly");
        } else {
        	throw new SkipException("No ACC with Completed Status available to test this scenarion");
        }
    }

    @Test(description = "TC227548: Test ACC UI when there are no request available per success track in Home page and in View All page", groups = {"singleThread"})
    public void verifyACCUIWhen0RequestLeft() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        acceleratorsPage.postACC(CISCO_ACC_ITEM1);
        acceleratorsPage.postACC(CISCO_ACC_ITEM2);
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, CISCO_ACC_ITEM1.getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        //Check for Data Availability
        if (acceleratorsPage.getTotalFilteredACCInHomePage("Content Provider", "Cisco") <= 0)
            throw new SkipException("No Partner ACC available to test this scenario");
        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhen0RequestAvailable(), "Request 1-On-1 button is enabled when 0 request Left for Customer in Home Page");
        acceleratorsPage.close360View();

        //Checking in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhen0RequestAvailable(), "Request 1-On-1 button is enabled when 0 request Left for Customer in View All Card View");
        acceleratorsPage.close360View();

        //Checking in Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        softAssert.assertTrue(acceleratorsPage.checkACCUIWhen0RequestAvailable(), "Request 1-On-1 button is enabled when 0 request Left for Customer in View All Table View");
        
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        softAssert.assertAll();
    }

    @Test(description = "T125111: Request 1-on-1 form can be opened from Lifecycle page", groups = {"singleThread"})
    public void verifyRequest1On1ButtonOpensFromHome() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        softAssert.assertTrue(acceleratorsPage.verifyRequestformOpenInHome(), "Not able to Request  from Home Page");

        //Deleting ACC Request
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        softAssert.assertAll();
    }

    @Test(description = "TC226820:Test user able to bookmark/unbookmark from 360 view from home page and from View All page")
    public void verifyBookmarkFrom360() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();
        acceleratorsPage.verifyBookmarkIn360(1);
        acceleratorsPage.close360View();
        //Verifying Bookmark in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();
        acceleratorsPage.verifyBookmarkIn360(acceleratorsPage.getACCPositionWithNoOrCompletesStatusInCardView());
        acceleratorsPage.close360View();
        //Verifying Bookmark in Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();
        acceleratorsPage.verifyBookmarkIn360(acceleratorsPage.getACCPositionWithNoOrCompletesStatusInCardView());
        assertTrue(acceleratorsPage.checkBookmarkRibbonDisplayed(), "Bookmark ribbbon is not present on the ACC tiles");

    }

    @Test(description = "TC230347: Verify that user is seeing a disclaimer when he clicks on 'view datasheet' or 'course document' for the first time")
    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvw01320")
    public void verifyDisclaimerOfDatsheetACCTile() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();

        //Checking for viewData sheet link availability
        if (acceleratorsPage.checkViewDatasheetLinkDisplayed() != true)
            throw new SkipException("No viewDatasheet link available on  this ACC");

        acceleratorsPage.clickViewDatasheetLinkDisplayed().checkDisclaimerOfViewDatasheetDisplayed();
        assertTrue(acceleratorsPage.verifyAfterViewDatasheetClickGoestoDatasheetpage(), "After accepting disclaimer the datasheet is not getting opened");

    }

    @Test(description = "TC230348: Verify that User need to accept the disclaimer to get the access to datasheet /docs")
    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvw01320")
    public void verifyAcceptanceDisclaimerOfDatasheetACCTile() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();

        //Checking for viewData sheet link availability
        if (acceleratorsPage.checkViewDatasheetLinkDisplayed() != true)
            throw new SkipException("No viewDatasheet link available on  this ACC");

        acceleratorsPage.clickViewDatasheetLinkDisplayed().checkDisclaimerOfViewDatasheetDisplayed();

        assertTrue(acceleratorsPage.verifyAfterViewDatasheetClickGoestoDatasheetpage(), "After accepting disclaimer the datasheet is not getting opened");

    }

    @Test(description = "TC226822: Test user able to View Datasheet from 360 view from Home page and from View All page")
    @Issue("https://cdetsng.cisco.com/webui/#view=CSCvw01320")
    public void verifyViewDatasheetInHomeAndViewAll() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        //Checking in Home Page
        acceleratorsPage.clickOnFirstTitleFromHomePage();

        //Checking for viewData sheet link availability
        if (acceleratorsPage.checkViewDatasheetLinkDisplayed() != true)
            throw new SkipException("No viewDatasheet link available on  this ACC");

        acceleratorsPage.clickViewDatasheetLinkDisplayed().checkDisclaimerOfViewDatasheetDisplayed();

        assertTrue(acceleratorsPage.verifyAfterViewDatasheetClickGoestoDatasheetpage(), "After accepting disclaimer the datasheet is not getting opened");
        acceleratorsPage.close360View();
        //Checking in Card View
        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton().clickOnAccFirstTitleFromCardView();

        //Checking for viewData sheet link availability
        if (acceleratorsPage.checkViewDatasheetLinkDisplayed() != true)
            throw new SkipException("No viewDatasheet link available on  this ACC");
//
        assertTrue(acceleratorsPage.verifyAfterViewDatasheetClickGoestoDatasheetpage(), "After accepting disclaimer the datasheet is not getting opened");
        acceleratorsPage.close360View();
        //Checking in Table View
        acceleratorsPage.clickACCTableViewButton().clickOnAccFirstTitleFromTableView();

        assertTrue(acceleratorsPage.verifyAfterViewDatasheetClickGoestoDatasheetpage(), "After accepting disclaimer the datasheet is not getting opened");

    }

    @Test(description = "T125124: Form ACC title should match the title that was opened", groups = {"singleThread"} )
    public void verifyRequestFormTitleMatchedToACCTitle() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();

        String titleFromCardView = acceleratorsPage.getAccFirstTitleFromCardView().toUpperCase();
        acceleratorsPage.clickOnAccFirstTitleFromCardView();

        //Checking whether request left for the tile
        if (acceleratorsPage.checkRequestA1On1Buttonenbled() != true)
            throw new SkipException("Request left is 0 for this ACC. Hence testing this scenario is not possible");

        acceleratorsPage.clickRequest1On1Button();

        softAssert.assertEquals(acceleratorsPage.getAccNameFromRequestFormInViewAllCardView(), titleFromCardView, "Acc title is not matching with the request form title");

        //Deleting ACC Request
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        softAssert.assertAll();
    }


  @Test(description = "TC225053: Verify that the completion notification contains ACC session name,completion date and Thumsup/down to give the feedback", groups = {"singleThread"})
  public void verifyAccCompletionNotification() {
       AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
       SoftAssert softAssert = new SoftAssert();
       acceleratorsPage.login()
       		.switchCXCloudAccount(cloudAccountWithPartnerContent)
               .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
               .selectContextSelector(CxHomePage.USE_CASE, campusNetworkSegmentationUseCase)
               .selectCarousel(CarouselName.LIFECYCLE);

     //Checking for Data availability
       if(acceleratorsPage.getTotalNoOfNotificationInBellicon()<= 0)
       	throw new SkipException("No ACC available to test this scenario");

       acceleratorsPage.clickHomepageNotificationIcon();

       String[]arr=acceleratorsPage.verifyTitleAndcompletionDateInNotification();
       softAssert.assertNotNull(arr[0],"count is not reduced even afetr giving feed back from LC tile notification");
       softAssert.assertNotNull(arr[1],"feedback given from LC tile notification is not displaying in viewAll card");
       softAssert.assertAll();
  }

    @Test(description = "T122079: Each card has an ribbon for book mark")
    public void verifyRibbonOnACCTile() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusSoftwareImageManagementUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        //Checking for Data availability
        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        assertTrue(acceleratorsPage.checkBookmarkRibbonDisplayed(), "Bookmark ribbbon is not present on the ACC tiles");

    }

    @Test(description = "TC225125: verify that the status 'completed' is shown in the completed ACC session")
    public void verifyCompletedStatusOnACC() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        
        CISCO_COMPLETED_ACC_DATA = getACCData(ACC_DATA_LIST_CISCO, itemsItem -> itemsItem.getStatus().equalsIgnoreCase("Completed"));
        
        try {
        	CISCO_COMPLETED_ACC_ITEM = CISCO_COMPLETED_ACC_DATA.getItems().get(0);
        	
        	acceleratorsPage.login()
            		.switchCXCloudAccount(cxCloudAccount)
            		.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
            		.selectContextSelector(CxHomePage.USE_CASE, CISCO_COMPLETED_ACC_ITEM.getUsecase())
            		.selectCarousel(CarouselName.LIFECYCLE);
        	
        	String pitstop = CISCO_COMPLETED_ACC_ITEM.getPitstop();
        	if(!pitstop.equalsIgnoreCase(acceleratorsPage.getCurrentPitStopName()))
        		acceleratorsPage.changePitStop(PitStopsName.valueOf(pitstop));
        	
        	acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();
        	assertEquals(acceleratorsPage.getStatusInCardViewDisplayed(), "Completed", "Completed status is not showing on the completed acc tiles");
        	
        } catch (NullPointerException e) {
			throw new SkipException("No Completed ACC is available to test this scenario");
		}
    }

    @Test(description = "TC226829: Test 360 view for the ACC which is having Requested status", groups = {"singleThread"})
    public void verifyRequestedACC360ViewFromHomePage() {
        SoftAssert softAssert = new SoftAssert();
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        
       try {
        	CISCO_REQUESTED_ACC_DATA = getACCData(ACC_DATA_LIST_CISCO, itemsItem -> null != itemsItem.getProviderInfo() && itemsItem.getStatus().equalsIgnoreCase("Requested"));
        	CISCO_REQUESTED_ACC_ITEM = CISCO_REQUESTED_ACC_DATA.getItems().get(0);
        } catch (NullPointerException e) {
        	System.out.println("Inside Catch");
			acceleratorsPage.postACC(CISCO_ACC_ITEM1);
			CISCO_REQUESTED_ACC_DATA = LifeCycleUtils.getACCResponse(CISCO_ACC_ITEM1.getCustomerId(), CISCO_ACC_ITEM1.getSolution(), CISCO_ACC_ITEM1.getUsecase(), CISCO_ACC_ITEM1.getPitstop(), DEFAULT_USER_ROLE);
		}
       acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, CISCO_REQUESTED_ACC_DATA.getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE);

       String title = acceleratorsPage.getACCFirstTitleFromHomePage();
       acceleratorsPage.clickOnFirstTitleFromHomePage();
       softAssert.assertTrue(acceleratorsPage.isElementPresent(AcceleratorsPage.ACC360ViewDataAutoId), "ACC 360 View is not opened");
       softAssert.assertEquals(acceleratorsPage.getAccTitleFrom360View(), title, "Title in 360 View is not matching with the title in Home Page");
       softAssert.assertTrue(acceleratorsPage.checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in ACC 360 View");
       softAssert.assertEquals(acceleratorsPage.getStatusfrom360(), "Requested", "Request status availabe in 360 view");
       softAssert.assertTrue(acceleratorsPage.checkOverviewIn360ViewDisplayed(), "Overview Tab is not available in 360 view");
	   
       acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
       softAssert.assertAll();
    }

    @Test(description = "TC226825: Test the message received in 360 view after submitting Request A 1-On-1 session", groups = {"singleThread"})
    public void verifySubmissionSuccessmsgOfACC() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available to test this scenario");

        acceleratorsPage.clickACCViewAllLink();
        softAssert.assertEquals(acceleratorsPage.verifySuccessMsgOnSubmission(), "Your request has been successfully submitted", "Success Message displayed is not the correct one");

        //Deleting Requested ACC
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);

        softAssert.assertAll();
    }

    @Test(description = "TC224999-verify Test user able to see 3 item on lifecycle tile")
    public void verifyHomepageAcccount() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        if (!(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() > 0))
            throw new SkipException("No ATX available under Lifecycle to test this scenario");

        assertTrue(acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 3, "Atx tile count in homepage is not 3");
    }

    @Test(description = "TC226828: Test user able to Submit Request A 1-On-1 session for the completed session from 360 View", groups = {"singleThread"})
    public void verifyCompletedACCrequest() {
        AcceleratorsPage acceleratorsPage = new AcceleratorsPage();
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
        SoftAssert softAssert = new SoftAssert();
        acceleratorsPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);

        acceleratorsPage.changePitStop(PitStopsName.Onboard);

        if (acceleratorsPage.getTotalNoOfACCUnderLifecyclePage() <= 0)
            throw new SkipException("No ACC available under Lifecycle to test this scenario");

        acceleratorsPage.clickACCViewAllLink().clickACCCardViewButton();
        String title = acceleratorsPage.clickOnACCWithCompletedStatus().getAccTitleFrom360View();
        acceleratorsPage.clickRequest1On1Button().FillACCRequestForm();
        acceleratorsPage.getACCRequestSubmissionmsg();
        softAssert.assertEquals(acceleratorsPage.getStatusWithTitleInCardView(title), "Requested", "Not able to Request ACC with Completed status");

        //Deleting ACC Request
        acceleratorsPage.deleteACCRequest(ACC_DATA_LIST_CISCO);
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
