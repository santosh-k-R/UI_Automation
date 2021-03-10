package com.cisco.tests.lifecycle;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.lifecycle.AskTheExpertsPage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.PitStopsName;
import com.cisco.testdata.lifecycle.ATXPoJo;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

import static com.cisco.testdata.lifecycle.Data.*;
import static com.cisco.utils.LifeCycleUtils.getATXResponse;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.*;

public class AskTheExpertsPageIT extends DriverBase {

    private static final ThreadLocal<AskTheExpertsPage> askTheExpertsPage = ThreadLocal.withInitial(AskTheExpertsPage::new);
    public List<ATXPoJo> ATX_DATA_LIST_CISCO;
    public List<ATXPoJo> ATX_DATA_LIST_PARTNER;
    public ATXPoJo CISCO_ATX_DATA;
    public ATXPoJo PARTNER_ATX_DATA;
    private ATXPoJo.ItemsItem CISCO_ATX_ITEM;
    private ATXPoJo.SessionsItem CISCO_SESSION_ITEM;
    private ATXPoJo.ItemsItem PARTNER_ATX_ITEM;
    private ATXPoJo.SessionsItem PARTNER_SESSION_ITEM;

    @BeforeTest(alwaysRun = true)
    public void dataSetup() {
        try {
            ATX_DATA_LIST_CISCO = getATXDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, DEFAULT_USER_ROLE);
            ATX_DATA_LIST_PARTNER = getATXDataFromAllUseCase(CUSTOMER_ID_GRAPHIC, SOLUTION, DEFAULT_USER_ROLE);
            CISCO_ATX_DATA = getATXData(ATX_DATA_LIST_CISCO, itemsItem -> null == itemsItem.getProviderInfo() && itemsItem.getSessions().size() > 0 && !itemsItem.getStatus().equalsIgnoreCase("completed"));
            PARTNER_ATX_DATA = getATXData(ATX_DATA_LIST_PARTNER, itemsItem -> null != itemsItem.getProviderInfo() && itemsItem.getSessions().size() > 0);

            CISCO_ATX_ITEM = CISCO_ATX_DATA.getItems().get(0);
            CISCO_SESSION_ITEM = CISCO_ATX_DATA.getItems().get(0).getSessions().get(0);
            PARTNER_ATX_ITEM = PARTNER_ATX_DATA.getItems().get(0);
            PARTNER_SESSION_ITEM = PARTNER_ATX_DATA.getItems().get(0).getSessions().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize the data for ask the Experts..");
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToLifecyclePage() {
        AskTheExpertsPage askTheExpertPage = new AskTheExpertsPage();
        askTheExpertPage.login()
                .switchCXCloudAccount(CX_CLOUD_ACCOUNT_L2)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, SUCCESS_TRACK)
                .selectContextSelector(CxHomePage.USE_CASE, CISCO_ATX_ITEM.getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE);
    }

    @Test(description = "Verify Completed ATX items are showing green checkmark icon")
    public void verifyCompletedCheckmark() {
        ATXPoJo.ItemsItem completedAtx = getATXData(ATX_DATA_LIST_CISCO, itemsItem -> null == itemsItem.getProviderInfo() && itemsItem.getStatus().equalsIgnoreCase("completed")).getItems().get(0);
        askTheExpertsPage.get()
                .selectContextSelector(CxHomePage.USE_CASE, completedAtx.getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE)
                .changePitStop(PitStopsName.valueOf(completedAtx.getPitstop()));

        assertTrue(askTheExpertsPage.get().isGreenCheckMarkPresent(completedAtx.getTitle()), "Green check mark icon is not showing for the completed ATX tile");
        assertEquals(askTheExpertsPage.get().getCheckMarkIconColor(completedAtx.getTitle()),"#6abf4b");

    }

    @Test(description = "Verify that ATX View All table view is toggleable back to card view")
    public void verifyATXViewALLToggleableBack() {
        askTheExpertsPage.get()
                .clickATXViewAll()
                .clickATXtableView();

        assertTrue(askTheExpertsPage.get().isATXTableDisplayed(), "ATX viewAll list is not in table format");

        askTheExpertsPage.get().clickATXCardView();
        assertTrue(askTheExpertsPage.get().isATXCardDisplayed(), "ATX viewAll list is not in card format");
    }

    @Test(description = "Verify that ATX tooltip appears when mousehover on ATX title")
    public void verifyTooltip() {
        askTheExpertsPage.get().mouseHoverATXTile(CISCO_ATX_ITEM.getTitle());

        assertEquals(askTheExpertsPage.get().getTooltip(CISCO_ATX_ITEM.getTitle()),
                CISCO_ATX_ITEM.getTitle() + " " + CISCO_ATX_ITEM.getDescription(), "Tooltip is not present in ATx title");
    }

    @Test(description = "Verify ATX tiles have images")
    public void verifyATXImage() {
        assertTrue(askTheExpertsPage.get().getSourceAttributeOfATXImage(CISCO_ATX_ITEM.getTitle()).contains(".png"), "Atx  doesn't have image");
    }

    @Test(description = "Verify that the Atx tiles shows title,status, Thumsup symol with postive feed back perecntage and tick mark symbol with total no of session completed in the main page")
    public void verifyATXTile() {
        assertEquals(askTheExpertsPage.get().getATXTitleFromHomePage(CISCO_ATX_ITEM.getTitle()), CISCO_ATX_ITEM.getTitle());
        int expectedFeedbackPercentage = (int) Math.round(CISCO_ATX_ITEM.getFeedbackEngagement());
        assertEquals(askTheExpertsPage.get().getATXFeedbackPercentage(CISCO_ATX_ITEM.getTitle()), expectedFeedbackPercentage + "%");
        assertEquals(askTheExpertsPage.get().getATXFeedbackCount(CISCO_ATX_ITEM.getTitle()), String.valueOf(CISCO_ATX_ITEM.getCumulativeFeedbackCount()));
    }

    @Test(description = "Verify Test user able to close 360 view from home page and view all page")
    public void verify360CloseButton() {

        askTheExpertsPage.get().clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle());
        askTheExpertsPage.get().close360View();
        assertFalse(askTheExpertsPage.get().isATX360Visible());

        //From Card View
        askTheExpertsPage.get()
                .clickATXViewAll()
                .clickATXCardView()
                .clickATXTitleOnCardView(CISCO_ATX_ITEM.getTitle());

        askTheExpertsPage.get().close360View();
        assertFalse(askTheExpertsPage.get().isATX360Visible());

        //From Table View
        askTheExpertsPage.get()
                .clickATXtableView()
                .clickATXTitleOnTableView(CISCO_ATX_ITEM.getTitle());

        askTheExpertsPage.get().close360View();
        assertFalse(askTheExpertsPage.get().isATX360Visible());
    }

    @Test(description = "Verify Test user able to maximize 360 view from home page and View All page")
    public void verify360maximizeButton() {

        askTheExpertsPage.get().clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle());
        askTheExpertsPage.get().fullscreen360View();
        assertTrue(askTheExpertsPage.get().getATXDetailPanelClass().contains("fullscreen"));

        //From Card View
        askTheExpertsPage.get()
                .clickATXViewAll()
                .clickATXCardView()
                .clickATXTitleOnCardView(CISCO_ATX_ITEM.getTitle());

        askTheExpertsPage.get().fullscreen360View();
        assertTrue(askTheExpertsPage.get().getATXDetailPanelClass().contains("fullscreen"));
        askTheExpertsPage.get().close360View();

        //From Table View
        askTheExpertsPage.get()
                .clickATXtableView()
                .clickATXTitleOnTableView(CISCO_ATX_ITEM.getTitle());

        askTheExpertsPage.get().fullscreen360View();
        assertTrue(askTheExpertsPage.get().getATXDetailPanelClass().contains("fullscreen"));
    }

    @Test(description = "Verify Test user able to see 3 item on lifecycle tile")
    public void verifyHomePageATXCount() {
        List<ATXPoJo> atxPoJos = ATX_DATA_LIST_CISCO.stream().filter(item -> item.getItems().size() > 2)
                .collect(Collectors.toList());
        ATXPoJo.ItemsItem atxData = getATXData(atxPoJos, itemsItem -> true).getItems().get(0);

        askTheExpertsPage.get()
                .selectContextSelector(CxHomePage.USE_CASE, atxData.getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE);

        assertEquals(askTheExpertsPage.get().getATXHomePagecount(), 3, "Atx tile count in homepage is not 3");
    }


    @Test(description = "Test user able to watch-on-demand from 360 view from Home page and from View All page")
    public void verifyWatchOnDemandButton() throws InterruptedException {
        AskTheExpertsPage askTheExpertPage = new AskTheExpertsPage();
        askTheExpertPage.login()
                .switchCXCloudAccount(CX_CLOUD_ACCOUNT_L2)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, SUCCESS_TRACK)
                .selectContextSelector(CxHomePage.USE_CASE, CISCO_ATX_DATA.getUsecase())
                .selectCarousel(CarouselName.LIFECYCLE);

        askTheExpertPage.clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle())
                .clickWatchOnDemand();

        assertTrue(askTheExpertPage.watchOnDemand("Home"), "ATX 360 view is not closing from the vieAllCard view");
//        askTheExpertPage.clickATXViewAll();
//        askTheExpertPage.openciscoATX360FromViewAllCard();
//        assertTrue(askTheExpertPage.watchOnDemand("Card"),"ATX 360 view is not closing from the vieAllCard view");
//        askTheExpertPage.getATXtableClass();
//        askTheExpertPage.openciscoATX360FromViewAllTable();
//        assertTrue(askTheExpertPage.watchOnDemand("Table"),"ATX 360 view is not closing from the vieAllTable view");
//        assertTrue(askTheExpertPage.watchOnDemandFromViewAll("Card"),"ATX 360 view is not closing from the vieAllCard view");
//        assertTrue(askTheExpertPage.watchOnDemandFromViewAll("Table"),"ATX 360 view is not closing from the vieAllTable view");
    }

    @Test(description = "Verify Test user able to see 360 view when clicking on a subject from Home Page for Cisco ATX")
    public void verifyCiscoATX360ViewFromHomePage() {
        SoftAssert softAssert = new SoftAssert();

        askTheExpertsPage.get().clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle());
        assertTrue(askTheExpertsPage.get().isElementPresent(AskTheExpertsPage.ATX360ViewDataAutoId), "ATX 360 View is not opened");
        softAssert.assertEquals(askTheExpertsPage.get().getAtxTitleFrom360View(), CISCO_ATX_ITEM.getTitle(), "Title in 360 View is not matching with the title in Home Page");
        softAssert.assertTrue(askTheExpertsPage.get().checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in ACC 360 View");
        softAssert.assertTrue(askTheExpertsPage.get().checkLaunchCourseIn360ViewDisplayed(), "watch on Demand is not available in 360 View");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxDescriptionIn360ViewDisplayed(), "Atx Description is not avilable in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxHeaderIn360ViewDisplayed(), "Atx header is not available in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkSessionTableDisplayed(), "Atx sessions are not available in 360 view");
        softAssert.assertAll();
    }

    @Test(description = "Verify Test user able to see 360 view when clicking on a subject from Home Page for Partner ATX")
    public void verifyPartnerATX360ViewFromHomePage() {
        askTheExpertsPage.get().checkPartnerDataAvailability(PARTNER_ATX_ITEM);
        SoftAssert softAssert = new SoftAssert();

        askTheExpertsPage.get().clickATXTitleOnHomePage(PARTNER_ATX_ITEM.getTitle());

        assertTrue(askTheExpertsPage.get().isElementPresent(AskTheExpertsPage.ATX360ViewDataAutoId), "ATX 360 View is not opened");
        softAssert.assertEquals(askTheExpertsPage.get().getAtxTitleFrom360View(), PARTNER_ATX_ITEM.getTitle(), "Title in 360 View is not matching with the title in Home Page");
        softAssert.assertTrue(askTheExpertsPage.get().checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in ACC 360 View");
        softAssert.assertTrue(askTheExpertsPage.get().checkLaunchCourseIn360ViewDisplayed(), "watch on Demand is not available in 360 View");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxDescriptionIn360ViewDisplayed(), "Atx Description is not avilable in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxHeaderIn360ViewDisplayed(), "Atx header is not available in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkSessionTableDisplayed(), "Atx sessions are not available in 360 view");
        softAssert.assertAll();
    }

    @Test(description = "Verify Test user able to see 360 view when clicking on a subject from viewAll Table for Cisco ATX")
    public void verifyCiscoATX360ViewFromViewAllPage() {
        SoftAssert softAssert = new SoftAssert();

        //Checking in Card View
        askTheExpertsPage.get().clickATXViewAll()
                .clickATXCardView()
                .clickATXTitleOnCardView(CISCO_ATX_ITEM.getTitle());

        assertTrue(askTheExpertsPage.get().isElementPresent(AskTheExpertsPage.ATX360ViewDataAutoId), "ATX 360 View is not opened in Card View");
        softAssert.assertEquals(askTheExpertsPage.get().getAtxTitleFrom360View(), CISCO_ATX_ITEM.getTitle(), "Title in 360 View is not matching with the title in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkLaunchCourseIn360ViewDisplayed(), "watch on Demand is not available in 360 View");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxDescriptionIn360ViewDisplayed(), "Atx Description is not avilable in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxHeaderIn360ViewDisplayed(), "Atx header is not available in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkSessionTableDisplayed(), "Atx sessions are not available in 360 view");

        //Checking from Table View
        askTheExpertsPage.get().close360View();
        askTheExpertsPage.get().clickATXtableView();
        askTheExpertsPage.get().clickATXTitleOnTableView(CISCO_ATX_ITEM.getTitle());

        assertTrue(askTheExpertsPage.get().isElementPresent(AskTheExpertsPage.ATX360ViewDataAutoId), "ATX 360 View is not opened in Table View");
        softAssert.assertEquals(askTheExpertsPage.get().getAtxTitleFrom360View(), CISCO_ATX_ITEM.getTitle(), "Title in 360 View is not matching with the title in Table View");
        softAssert.assertTrue(askTheExpertsPage.get().checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkLaunchCourseIn360ViewDisplayed(), "watch on Demand is not available in Table View 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxDescriptionIn360ViewDisplayed(), "Atx Description is not availabe in Table View 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxHeaderIn360ViewDisplayed(), "Atx header is not available in Table View 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkSessionTableDisplayed(), "Atx sessions are not available in 360 view");
        softAssert.assertAll();
    }

    @Test(description = "Verify Test user able to view 360 view when clicking on a subject from View All Page for Partner ATX")
    public void verifyPartnerATX360ViewFromViewAllPage() {
        askTheExpertsPage.get().checkPartnerDataAvailability(PARTNER_ATX_ITEM);
        SoftAssert softAssert = new SoftAssert();

        //Checking in Card View
        askTheExpertsPage.get().clickATXViewAll()
                .clickATXCardView()
                .clickATXTitleOnCardView(PARTNER_ATX_ITEM.getTitle());

        assertTrue(askTheExpertsPage.get().isElementPresent(AskTheExpertsPage.ATX360ViewDataAutoId), "ATX 360 View is not opened in Card View");
        softAssert.assertEquals(askTheExpertsPage.get().getAtxTitleFrom360View(), PARTNER_ATX_ITEM.getTitle(), "Title in 360 View is not matching with the title in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Card View ACC 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkLaunchCourseIn360ViewDisplayed(), "watch on Demand is not available in 360 View");
        softAssert.assertTrue(askTheExpertsPage.get().checkPartnerIn360ViewDisplayed(), "Partner name is not avilable in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxDescriptionIn360ViewDisplayed(), "Atx Description is not avilable in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxHeaderIn360ViewDisplayed(), "Atx header is not available in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkSessionTableDisplayed(), "Atx sessions are not available in 360 view");


        //Checking from Table View
        askTheExpertsPage.get().close360View();
        askTheExpertsPage.get().clickATXtableView()
                .clickATXTitleOnTableView(PARTNER_ATX_ITEM.getTitle());

        assertTrue(askTheExpertsPage.get().isElementPresent(AskTheExpertsPage.ATX360ViewDataAutoId), "ATX 360 View is not opened in Table View");
        softAssert.assertEquals(askTheExpertsPage.get().getAtxTitleFrom360View(), PARTNER_ATX_ITEM.getTitle(), "Title in 360 View is not matching with the title in Table View");
        softAssert.assertTrue(askTheExpertsPage.get().checkBookmarkIn360ViewDisplayed(), "Bookmark button is not present in Table View ACC 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkLaunchCourseIn360ViewDisplayed(), "watch on Demand is not available in Table View 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkPartnerIn360ViewDisplayed(), "Partner name is not avilable in 360 view");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxDescriptionIn360ViewDisplayed(), "Atx Description is not availabe in Table View 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkAtxHeaderIn360ViewDisplayed(), "Atx header is not available in Table View 360");
        softAssert.assertTrue(askTheExpertsPage.get().checkSessionTableDisplayed(), "Atx sessions are not available in 360 view");
        softAssert.assertAll();
    }


    @Test(description = "Test user able to differentiate the topic which is opened in the 360 view from Home page and from View All Table and Card view")
    public void verifyTileColour() {
        askTheExpertsPage.get().clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle());
        assertEquals(askTheExpertsPage.get().getTileColorWhenOpen360(), "#ecf4fc");

    }

    @Test(description = "Verify that ATx image,title,status,Thumsup/down symbol with postive feed back perecntage and tick mark symbol with total no of times the session completed dispalyed  in atx tile in ATX view all")
    public void verifyAtxTilesInViewAllPage() {
        SoftAssert softAssert = new SoftAssert();

        //Checking in Card View
        askTheExpertsPage.get().clickATXViewAll().clickATXCardView();

        softAssert.assertTrue(askTheExpertsPage.get().checkStatusInCardViewDisplayed(CISCO_ATX_ITEM.getTitle()), "Status is not being displayed or No data available to test this scenario in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkATXTitleInCardViewDisplayed(), "Title has not been displayed in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkATXPartnerNameInCardViewDisplayed() == 0, "Partner Name is being displayed in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkCountAndPositiveSymbolDisplayedInCardView(AskTheExpertsPage.ATXFeedbackRatingClassName), "Tick Mark symbol is not being displayed or No data is available to test this scenario in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkNoOfCompletionAndFeedbackCountDisplayedInCardView(AskTheExpertsPage.ATXFeedbackRatingClassName), "Total No Of Completion count is not being displayed or No data is available to test this scenario in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkCountAndPositiveSymbolDisplayedInCardView(AskTheExpertsPage.ATXFeedbackPositiveThumbsUpClassName), "Positive thumbs Up Symbol is not being displayed or No data is available to test this scenario in Card View");
        softAssert.assertTrue(askTheExpertsPage.get().checkNoOfCompletionAndFeedbackCountDisplayedInCardView(AskTheExpertsPage.ATXFeedbackPositiveThumbsUpClassName), "Positive Percentage count is not being displayed or No data is available to test this scenario in Card View");

        //Checking in Table View
        askTheExpertsPage.get().clickATXtableView();
        softAssert.assertTrue(askTheExpertsPage.get().checkStatusInTableViewDisplayed(CISCO_ATX_ITEM.getTitle()), "Status is not being displayed or No data available to test this scenario in Table View");
        softAssert.assertTrue(askTheExpertsPage.get().checkATXTitleInTableViewDisplayed(), "Title has not been displayed in Table View");
        softAssert.assertTrue(askTheExpertsPage.get().checkATXPartnerNameInTableViewDisplayed(), "ATX Delivery Type is not being displayed properly");
        softAssert.assertTrue(askTheExpertsPage.get().checkCountAndPositiveSymbolDisplayedInTableView(AskTheExpertsPage.ATXFeedbackRatingClassName), "Tick Mark symbol is not being displayed or No data is available to test this scenarion in Table View");
        softAssert.assertTrue(askTheExpertsPage.get().checkNoOfCompletionAndFeedbackCountDisplayedInTableView(AskTheExpertsPage.ATXFeedbackRatingClassName), "Total No Of Completion count is not being displayed or No data is available to test this scenario in Table View");
        softAssert.assertTrue(askTheExpertsPage.get().checkCountAndPositiveSymbolDisplayedInTableView(AskTheExpertsPage.ATXFeedbackPositiveThumbsUpClassName), "Positive thumbs Up Symbol is not being displayed or No data is available to test this scenario in Table View");
        softAssert.assertTrue(askTheExpertsPage.get().checkNoOfCompletionAndFeedbackCountDisplayedInTableView(AskTheExpertsPage.ATXFeedbackPositiveThumbsUpClassName), "Positive Percentage count is not being displayed or No data is available to test this scenario in Table View");
        softAssert.assertAll();
    }

    //*********************************************Runs in Single Thread*************************************************************************************

    @Test(description = "Test user able to cancel session from 360 view from Home page", groups = {"singleThread"})
    public void verifyCancellationFromHomePage() {
        askTheExpertsPage.get().postATXSessionIfNotRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickCancelButton()
                .waitForATXTilesToLoad();

        assertTrue(askTheExpertsPage.get().waitForCalendarIconToDisappear(CISCO_ATX_ITEM.getTitle()));
        assertTrue(askTheExpertsPage.get().waitForStatusToDisappear(CISCO_ATX_ITEM.getTitle()));

    }

    @Test(description = "Test user able to cancel session from 360 view from View All Card view", groups = {"singleThread"})
    public void verifyCancellationFromViewAllCardView() {
        askTheExpertsPage.get().postATXSessionIfNotRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().clickATXViewAll()
                .clickATXCardView()
                .clickATXTitleOnCardView(CISCO_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickCancelButton()
                .waitForATXTilesToLoad();

        assertTrue(askTheExpertsPage.get().waitForCalendarIconToDisappear(CISCO_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");
        assertTrue(askTheExpertsPage.get().waitForStatusToDisappear(CISCO_ATX_ITEM.getTitle()));

    }

    @Test(description = "Test user able to cancel session from 360 View All Table View", groups = {"singleThread"})
    public void verifyCancellationFromHomeViewAllTableView() {
        askTheExpertsPage.get().postATXSessionIfNotRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().clickATXViewAll()
                .clickATXtableView()
                .clickATXTitleOnTableView(CISCO_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickCancelButton()
                .waitForATXTilesToLoad();

        assertTrue(askTheExpertsPage.get().waitForCalendarIconToDisappear(CISCO_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");
        assertTrue(askTheExpertsPage.get().waitForStatusToDisappear(CISCO_ATX_ITEM.getTitle()));

    }

    @Test(description = "Test user able to register session from 360 view from Home page", groups = {"singleThread"})
    public void verifyRegistrationFromHomePage() {
        askTheExpertsPage.get().cancelATXSessionIfRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickRegisterButton();

        assertEquals(askTheExpertsPage.get().getPresenterName(CISCO_ATX_ITEM.getTitle()), CISCO_SESSION_ITEM.getPresenterName(), "Presenter Name does not match");
        assertEquals(askTheExpertsPage.get().getScheduledTime(CISCO_ATX_ITEM.getTitle()),
                askTheExpertsPage.get().getFormattedDate("MMM d, u HH:mm a z", String.valueOf(CISCO_SESSION_ITEM.getSessionStartDate())), "Scheduled Time doesn not match");
        assertTrue(askTheExpertsPage.get().isCalendarIconPresent(CISCO_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");
    }

    @Test(description = "Test user able to register session from 360 view from View All Card view", groups = {"singleThread"})
    public void verifyRegistrationFromViewAllCardView() {
        askTheExpertsPage.get().cancelATXSessionIfRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().clickATXViewAll().clickATXCardView();
        askTheExpertsPage.get().clickATXTitleOnCardView(CISCO_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickRegisterButton();

        assertEquals(askTheExpertsPage.get().getPresenterName(CISCO_ATX_ITEM.getTitle()), CISCO_SESSION_ITEM.getPresenterName(), "Presenter Name does not match");
        assertEquals(askTheExpertsPage.get().getScheduledTime(CISCO_ATX_ITEM.getTitle()),
                askTheExpertsPage.get().getFormattedDate("MMM d, u HH:mm a z", String.valueOf(CISCO_SESSION_ITEM.getSessionStartDate())), "Scheduled Time doesn not match");
        assertTrue(askTheExpertsPage.get().isCalendarIconPresent(CISCO_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");

    }

    @Test(description = "Test user able to register session from 360 View All Table View", groups = {"singleThread"})
    public void verifyRegistrationFromViewAllTableView() {
        askTheExpertsPage.get().cancelATXSessionIfRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().clickATXViewAll()
                .clickATXtableView()
                .clickATXTitleOnTableView(CISCO_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickRegisterButton();

        assertEquals(askTheExpertsPage.get().getPresenterName(CISCO_ATX_ITEM.getTitle()), CISCO_SESSION_ITEM.getPresenterName(), "Presenter Name does not match");
        assertEquals(askTheExpertsPage.get().getScheduledTime(CISCO_ATX_ITEM.getTitle()),
                askTheExpertsPage.get().getFormattedDate("MMM d, u HH:mm a z", String.valueOf(CISCO_SESSION_ITEM.getSessionStartDate())), "Scheduled Time doesn not match");
        assertTrue(askTheExpertsPage.get().isCalendarIconPresent(CISCO_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");
    }

    @Test(description = "T135781-Verify that the ATX View Sessions cancel button is enabling when selecting the scheduled session", groups = {"singleThread"})
    public void verifyATXSEnabledCancelButton() {
        askTheExpertsPage.get().postATXSessionIfNotRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle())
                .clickATXSession();

        assertTrue(askTheExpertsPage.get().isATXCancelButtonEnabled(), "ATX cancel button is not enabled");
    }

    @Test(description = "User should be able to schedule a Partner ATX from the main lifecycle page", groups = {"singleThread"})
    public void verifyPartnerATXSchedulingFromHome() {
        askTheExpertsPage.get().checkPartnerDataAvailability(PARTNER_ATX_ITEM);
        askTheExpertsPage.get().cancelATXSessionIfRegistered(PARTNER_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        //TODO: Apply partner filter once the partner data is available
        askTheExpertsPage.get().clickATXTitleOnHomePage(PARTNER_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickRegisterButton();

        assertEquals(askTheExpertsPage.get().getPresenterName(PARTNER_ATX_ITEM.getTitle()), PARTNER_SESSION_ITEM.getPresenterName(), "Presenter Name does not match");
        assertEquals(askTheExpertsPage.get().getScheduledTime(PARTNER_ATX_ITEM.getTitle()),
                askTheExpertsPage.get().getFormattedDate("MMM d, u HH:mm a z", String.valueOf(PARTNER_SESSION_ITEM.getSessionStartDate())), "Scheduled Time doesn not match");
        assertTrue(askTheExpertsPage.get().isCalendarIconPresent(PARTNER_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");

    }

    @Test(description = "User should be able to schedule a Partner ATX from the View All Card View", groups = {"singleThread"})
    public void verifyPartnerATXSchedulingFromViewAllCardView() {
        askTheExpertsPage.get().checkPartnerDataAvailability(PARTNER_ATX_ITEM);
        askTheExpertsPage.get().cancelATXSessionIfRegistered(PARTNER_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        //TODO: Apply partner filter once the partner data is available
        askTheExpertsPage.get()
                .clickATXViewAll()
                .clickATXTitleOnCardView(PARTNER_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickRegisterButton();

        assertEquals(askTheExpertsPage.get().getPresenterName(PARTNER_ATX_ITEM.getTitle()), PARTNER_SESSION_ITEM.getPresenterName(), "Presenter Name does not match");
        assertEquals(askTheExpertsPage.get().getScheduledTime(PARTNER_ATX_ITEM.getTitle()),
                askTheExpertsPage.get().getFormattedDate("MMM d, u HH:mm a z", String.valueOf(PARTNER_SESSION_ITEM.getSessionStartDate())), "Scheduled Time doesn not match");
        assertTrue(askTheExpertsPage.get().isCalendarIconPresent(PARTNER_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");

    }

    @Test(description = "User should be able to schedule a Partner ATX from the View All Table View", groups = {"singleThread"})
    public void verifyPartnerATXSchedulingFromViewAllTableView() {
        askTheExpertsPage.get().checkPartnerDataAvailability(PARTNER_ATX_ITEM);
        askTheExpertsPage.get().cancelATXSessionIfRegistered(PARTNER_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();

        //TODO: Apply partner filter once the partner data is available
        askTheExpertsPage.get()
                .clickATXViewAll()
                .clickATXtableView()
                .clickATXTitleOnTableView(PARTNER_ATX_ITEM.getTitle())
                .clickATXSession()
                .clickRegisterButton();

        assertEquals(askTheExpertsPage.get().getPresenterName(PARTNER_ATX_ITEM.getTitle()), PARTNER_SESSION_ITEM.getPresenterName(), "Presenter Name does not match");
        assertEquals(askTheExpertsPage.get().getScheduledTime(PARTNER_ATX_ITEM.getTitle()),
                askTheExpertsPage.get().getFormattedDate("MMM d, u HH:mm a z", String.valueOf(PARTNER_SESSION_ITEM.getSessionStartDate())), "Scheduled Time doesn not match");
        assertTrue(askTheExpertsPage.get().isCalendarIconPresent(PARTNER_ATX_ITEM.getTitle()), "Calendar icon not displaying after atx registration");

    }

    @Test(description = "Verify ATX bookmark functionality", groups = {"singleThread"})
    public void verifyBookmark() {
        askTheExpertsPage.get().clearBookMark(CISCO_ATX_ITEM);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get().scrollToBottom();
        askTheExpertsPage.get().clickBookmark(CISCO_ATX_ITEM.getTitle());
        assertTrue(askTheExpertsPage.get().getBookMarkColor(CISCO_ATX_ITEM.getTitle()).contains("ribbon__blue"));
    }

    @Test(description = "T134402-verify ATX View All table view able to bookmark/un-bookmark items", groups = {"singleThread"})
    public void verifyVieALLBookmark() {
        askTheExpertsPage.get().clearBookMark(CISCO_ATX_ITEM);
        askTheExpertsPage.get().refreshLifecycleTiles();

        askTheExpertsPage.get()
                .clickATXViewAll()
                .clickATXtableView();

        askTheExpertsPage.get().clickBookmarkOnTableView(CISCO_ATX_ITEM.getTitle());
        assertEquals(askTheExpertsPage.get().getBookMarkColorFromTableView(CISCO_ATX_ITEM.getTitle()), "#00bceb");
    }

    @Test(description = "Test user able to bookmark from 360 view from Home page and from View All page", groups = {"singleThread"})
    public void verify360BookmarkButton() {
        askTheExpertsPage.get().clearBookMark(CISCO_ATX_ITEM);
        askTheExpertsPage.get().refreshLifecycleTiles();
        askTheExpertsPage.get().changePitStop(PitStopsName.valueOf(CISCO_ATX_ITEM.getPitstop()));

        askTheExpertsPage.get()
                .clickATXTitleOnHomePage(CISCO_ATX_ITEM.getTitle())
                .click360BookmarkButton();

        assertEquals(askTheExpertsPage.get().getBookMarkColorFrom360View(), "#00bceb");

        askTheExpertsPage.get().close360View();
        askTheExpertsPage.get()
                .clickATXViewAll()
                .clickATXCardView()
                .clickATXTitleOnCardView(CISCO_ATX_ITEM.getTitle());

        assertEquals(askTheExpertsPage.get().getBookMarkColorFrom360View(), "#00bceb");

        askTheExpertsPage.get().close360View();
        askTheExpertsPage.get()
                .clickATXtableView()
                .clickATXTitleOnTableView(CISCO_ATX_ITEM.getTitle());

        assertEquals(askTheExpertsPage.get().getBookMarkColorFrom360View(), "#00bceb");

    }

    @Test(description = "T134398- ATX View All card view should be filterable by status", groups = {"singleThread"})
    public void verifyATXFilterWithStatus() {
        ATXPoJo scheduledATXs = getATXResponse(CISCO_ATX_DATA, DEFAULT_USER_ROLE, "&status=scheduled");
        ATXPoJo inProgressATXs = getATXResponse(CISCO_ATX_DATA, DEFAULT_USER_ROLE, "&status=in-progress");
        ATXPoJo completedATXs = getATXResponse(CISCO_ATX_DATA, DEFAULT_USER_ROLE, "&status=completed");
        askTheExpertsPage.get().postATXSessionIfNotRegistered(CISCO_ATX_DATA);
        askTheExpertsPage.get().refreshLifecycleTiles();


        askTheExpertsPage.get().clickATXViewAll().clickATXCardView();

        askTheExpertsPage.get().applyATXFilterInViewAllPage("Status", "Scheduled")
                                .waitTillNumberOfCardsIs(scheduledATXs.getItems().size());
        assertEquals(askTheExpertsPage.get().getATXCardTitlesInCardView(), scheduledATXs.getItems().stream().map(ATXPoJo.ItemsItem::getTitle).collect(Collectors.toList()));

    }


    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        try {
            askTheExpertsPage.get().reload();
//            page.get().closeAllPopUps();
//			page.get().closeAllDropDowns();
        } catch (Exception e) {
            System.out.println("Page not reloaded properly" + e);
//			page.get().reload();
        }
    }
}
