package com.cisco.tests.insights.compliance;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.compliance.ComplianceAdminPage;
import com.cisco.pages.insights.compliance.ComplianceLandingPage;
import com.cisco.testdata.RccConstants;
import com.cisco.testdata.StaticData.CarouselName;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;


public class AdminPageIT extends DriverBase {


    String[] listOfPG= {"HIPAA", "PCI"};

    @BeforeClass(description = "Admin page")
    public void Login(){
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();

        //landingPage.loginWithDetails(RccConstants.TRACKNAME,RccConstants.USERNAME,RccConstants.PASSWORD);
        landingPage.login();
        landingPage.switchCXCloudAccount(RccConstants.SMARTACCOUNT);
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
    }


    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether information is displayed and defaults values if the customer navigates to compliance settings page while the customer is opted in",priority=1)
    public void complianceAdminGlobalSetting()  {
        ComplianceAdminPage adminPage = new ComplianceAdminPage();
        SoftAssert softAssert = new SoftAssert();


        adminPage.clickGlobalSettingBtn();
        Assert.assertFalse(adminPage.getInsightsAttribute().contains("selected"));
        Assert.assertEquals(adminPage.getInsightsSettingName(), RccConstants.MENUE_TITLE.toUpperCase(),"the menue title isnt insights");
        adminPage.clickInsightsSetting();
        Assert.assertTrue(adminPage.getInsightsAttribute().contains("selected"));
        adminPage.clickComplianceTab();
        softAssert.assertEquals(adminPage.getComplianceHeaderName(), RccConstants.TITLE,"The menue title isnt compliance");
        softAssert.assertEquals(adminPage.getComplianceTitleMessage(), RccConstants.INFO_MESSAGE,"The description isnt matching");
        softAssert.assertEquals(adminPage.getPolicyProfileHeaderName(), RccConstants.POLICY_TITLE.toUpperCase(),"The label isnt Policy profile");
        softAssert.assertEquals(adminPage.getPolicyGroupHeaderName(), RccConstants.POLICY_GROUP_TITLE.toUpperCase(),"The label isnt policy group");
        softAssert.assertEquals(adminPage.getAssetSelectionHeaderName(), RccConstants.POLICY_ASEET_SELECTION_TITLE.toUpperCase(),"The label isnt asset seelection");
        softAssert.assertEquals(adminPage.getOptLabel(), "On","The compliance isnt turned on");
        softAssert.assertTrue(adminPage.getPolicyGroupStatus(),"Policy group list is present");
        softAssert.assertFalse(adminPage.getRunComplianceAttribute().contains("disabled"),"Run compliance check is disabled");


        ArrayList<String> actPGLists = adminPage.getPolicyGroupList();
        actPGLists.remove(0);
        Assert.assertEquals(actPGLists.size(), listOfPG.length);
        for(String pg: listOfPG) {
            softAssert.assertTrue(actPGLists.contains(pg),"contains a policy group which isnt expected "+ pg);
        }

        String[] listOfSelection= {"All Inventory", "Asset Tags"};
        ArrayList<String> asseetList = adminPage.getAssetSelectionList();
        Assert.assertEquals(actPGLists.size(), listOfPG.length);
        for(String al: asseetList) {
            softAssert.assertTrue(asseetList.contains(al),"contains a policy group which isnt expected "+ al);
        }
        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");

        softAssert.assertAll();



    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether admin can opt out for compliance check by clicking the OptIn switch",priority=2)
    public void complianceAdminAbleToOptOut() {
        ComplianceAdminPage adminPage = new ComplianceAdminPage();
        ComplianceLandingPage landingPage = new ComplianceLandingPage();

        SoftAssert softAssert = new SoftAssert();

        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);

        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
        adminPage.clickComplianceTab();
        adminPage.clickOptOption();
        softAssert.assertEquals(adminPage.getModalTitle(),RccConstants.OPTIN_MODAL_TITLE,"Title of the optin modal not as expected");
        softAssert.assertEquals(adminPage.getModalBody(),RccConstants.OPTIN_MODAL_BOADY,"Modal body doesnt match");
        adminPage.clickAminModalClose();
        softAssert.assertEquals(adminPage.getOptLabel(), "On","The compliance isnt turned on");
        adminPage.clickOptOption();
        adminPage.clickAminModalNo();
        softAssert.assertEquals(adminPage.getOptLabel(), "On","The compliance isnt turned on");
        adminPage.clickOptOption();
        adminPage.clickAminModalYes();

        softAssert.assertEquals(adminPage.getOptLabel(), "Off","The compliance is turned on");


        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        softAssert.assertEquals(landingPage.getBannerTitle(),RccConstants.OPTOUT_BANNER_TITLE,"Title of the optout as expected");
        softAssert.assertEquals(landingPage.getBannerMessage(),RccConstants.OPTOUT_BANNER_BODY,"Message of the optout as expected");
        adminPage.clickBannerSettingButton();
        Assert.assertTrue(adminPage.getInsightsAttribute().contains("selected"));

        softAssert.assertEquals(adminPage.getOptLabel(), "Off","The compliance is turned on");
//clickOptInOption
        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");


        softAssert.assertAll();

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether admin can able to navigate to compliance admin setting page by clicking on the Compliance Setting button in the banner message and optin after opting out",priority=3)
    public void complianceAdminAbleToOptIn() {
        String[] policies = {"HIPAA", "PCI"};
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        ComplianceAdminPage adminPage = new ComplianceAdminPage();

        SoftAssert softAssert = new SoftAssert();
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);

        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
        adminPage.clickComplianceTabWhileOptOut();
        adminPage.clickOptInOption();
        Arrays.stream(policies).forEach(e -> {
            adminPage.selectPolicyGroup(e);
            adminPage.enableComplianceScan(false);
            adminPage.savePolicyProfile(e);
        });
        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        softAssert.assertEquals(landingPage.getBannerTitle(),RccConstants.OPTOUT_BANNER_TITLE,"Title of the optout as expected");
        softAssert.assertTrue(landingPage.getBannerMessage().contains(RccConstants.ENABLE_BANNER_BODY),"Message of the optout as expected");

        softAssert.assertAll();


    }


    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether user can able to save one of the option from the POLICY GROUP dropdown with All Inventory option without scan enabled.",priority=4)
    public void complianceAdminPolicyEnableBanner(){

        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        ComplianceAdminPage adminPage = new ComplianceAdminPage();

        SoftAssert softAssert = new SoftAssert();
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);

        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
        adminPage.clickComplianceTab();


        for(String pg: listOfPG) {
            adminPage.selectPolicyGroup(pg);
            adminPage.enableComplianceScan(false);
            adminPage.savePolicyProfile(pg);
        }
        softAssert.assertTrue(adminPage.getRunComplianceAttribute().contains("disabled"),"Run compliance check is enabled after disabling both the policy group");

        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        softAssert.assertEquals(landingPage.getBannerTitle(),RccConstants.OPTOUT_BANNER_TITLE,"Title of the optout as expected");
        softAssert.assertEquals(landingPage.getBannerMessage(),RccConstants.ENABLE_BANNER_BODY,"Message of the optout as expected");

        adminPage.clickBannerSettingButton();
        softAssert.assertTrue(adminPage.getRunComplianceAttribute().contains("disabled"),"Run compliance check is enabled after disabling both the policy group");
        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");


    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether user can able to save one of the option from the POLICY GROUP dropdown with All Inventory option with scan enabled.",priority=5)
    public void complianceAdminAbleToSavePolicyProfile() throws Exception{


        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        ComplianceAdminPage adminPage = new ComplianceAdminPage();

        SoftAssert softAssert = new SoftAssert();
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        adminPage.clickBannerSettingButton();

        adminPage.selectPolicyGroup("HIPAA");
        adminPage.enableComplianceScan(true);
        adminPage.savePolicyProfile("HIPAA");
        softAssert.assertFalse(adminPage.getRunComplianceAttribute().contains("disabled"),"Run compliance check is disabled after enabling HIPAA");

        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        softAssert.assertEquals(landingPage.getBannerTitle(),RccConstants.PENDING_BANNER_TITLE,"Title of the optout as expected");
        softAssert.assertTrue(landingPage.getBannerMessage().contains(RccConstants.PENDING_BANNER_BODY),"Message of the optout as expected");

        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
        adminPage.clickComplianceTab();

        adminPage.selectPolicyGroup("PCI");
        adminPage.enableComplianceScan(true);
        adminPage.savePolicyProfile("PCI");

        softAssert.assertFalse(adminPage.getRunComplianceAttribute().contains("disabled"),"Run compliance check is disabled after enabling PCI");


        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        softAssert.assertEquals(landingPage.getBannerTitle(),RccConstants.PENDING_BANNER_TITLE,"Title of the optout as expected");
        softAssert.assertTrue(landingPage.getBannerMessage().contains(RccConstants.PENDING_BANNER_BODY),"Message of the optout as expected");

        softAssert.assertAll();


    }



}
