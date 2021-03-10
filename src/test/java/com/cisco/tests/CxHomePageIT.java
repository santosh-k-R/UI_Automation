package com.cisco.tests;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.testdata.StaticData.ChartType;
import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.testdata.StaticData.UseCaseName;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;

import static com.cisco.testdata.StaticData.CarouselName.ADVISORIES;
import static com.cisco.testdata.StaticData.CarouselName.CASES;
import static org.testng.Assert.assertEquals;

public class CxHomePageIT extends DriverBase {


    @Test(description = "Verify the Home Page of the CX Portal")
    public void verifyTitleOfTheHomePage() {
        CxHomePage homePage = new CxHomePage();
        homePage.login();
        String actualTitle = homePage.getTitle();
        Allure.step("The actual title is " + actualTitle);
        assertEquals(actualTitle, "CX Customer Portal Internal Stage");
    }


    @Test(description = "Verify submitting a Feedback")
    public void verifySubmitFeedback() {
        CxHomePage homePage = new CxHomePage();
        homePage.login();
        String feedbackTitle = homePage.clickFeedBackButton()
                .getFeedbackModalTitle();

        assertEquals(feedbackTitle, "Feedback");

        String feedbackMsg = homePage.clickLikeButton()
                .typeComment("This is a test Feedback")
                .clickSubmit()
                .getFeedbackMessage();

        assertEquals(feedbackMsg, "Thank you for your feedback.");

        homePage.clickCloseButton();
    }

    @Test(description = "Verfiy the Cisco Community Link")
    public void verifyCiscoCommunityLink() {
        CxHomePage homePage = new CxHomePage();
        homePage.login();
        String title = homePage.clickCiscoCommunityLink().switchTab().getTitle();
        homePage.switchToMainTab();
        System.out.println("title" + title);

    }

    @Test(description = "Verify the Learning LInk")
    public void verifyLearningLink() {
        CxHomePage homePage = new CxHomePage();
        homePage.login();
        String title = homePage.clickLearningLink().switchTab().getTitle();
        homePage.switchToMainTab();
        System.out.println("title" + title);
    }

    @Test(description = "Verify Selection of Smart accounts")
    public void verifySelectionSmartAccounts() {
        CxHomePage homePage = new CxHomePage();
        homePage.login();
        homePage.selectContextSelector(CxHomePage.SMART_ACCOUNT, CxHomePage.STATE_OF_MARYLAND);
        homePage.selectContextSelector(CxHomePage.SUCCESS_TRACK, CxHomePage.IBN);
        homePage.selectContextSelector(CxHomePage.USE_CASE, UseCaseName.CAMPUS_NETWORK_SEGEMENTATION.getUseCaseName());
        homePage.selectCarousel(ADVISORIES);
    }

    @Test(description = "VerifyNavigation to All tabs")
    public void navigateToAllTabs() {
        CxHomePage homePage = new CxHomePage();
        FilterValue filterValue2 = new FilterValue("UPDATED", ChartType.COLUMN, "3");
        FilterValue filterValue1 = new FilterValue("SEVERITY", ChartType.PIE, "S3");

        homePage.login();
        homePage.selectCarousel(CASES);
        homePage.filterByVisualFilters(filterValue1, filterValue2);
        homePage.clearSpecificFilter("Updated");
        homePage.clearAllFilter();

    }
}
