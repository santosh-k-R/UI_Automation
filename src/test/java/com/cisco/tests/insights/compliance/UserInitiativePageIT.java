package com.cisco.tests.insights.compliance;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.compliance.ComplianceAdminPage;
import com.cisco.pages.insights.compliance.ComplianceLandingPage;
import com.cisco.testdata.RccConstants;
import com.cisco.testdata.StaticData.CarouselName;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;

public class UserInitiativePageIT extends DriverBase {

    @BeforeMethod(description = "user iniative")
    public void Login()
    {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        landingPage.login();
        landingPage.switchCXCloudAccount(RccConstants.SMARTACCOUNT);
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
    }


    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether user able to initate on-demand from the asset with violation page when both policy group is enabled",priority=1)
    public void complianceOnDemandBothEnabled() {
        ComplianceAdminPage adminPage = new ComplianceAdminPage();
        ComplianceLandingPage landingPage = new ComplianceLandingPage();

        SoftAssert softAssert = new SoftAssert();
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        landingPage.clickSysWithVioTab(RccConstants.SYSTEMS_VIOLATIONS_SUB_VIEW);
        landingPage.resetTable();
        landingPage.clickRunComplianceCheck(1);

        softAssert.assertEquals(landingPage.getRunComplianceHeaderName(),RccConstants.USER_INITAITIVE_TITLE_MODAL,"The title of the modal is wrong");
        softAssert.assertEquals(landingPage.getRunComplianceSelectedSystemName(),landingPage.getFirstAssetName(),"The title of the modal is wrong");
        softAssert.assertTrue(landingPage.getRunComplianceSelectedPGName().equals("HIPAA,PCI") || landingPage.getRunComplianceSelectedPGName().equals("PCI,HIPAA"),"The title of the modal is wrong");
        landingPage.clickCloseTheRunComplianceCheck();
        softAssert.assertAll();
    }


    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether user able to initate on-demand from the asset with violation page when PCI policy group is enabled",priority=2)
    public void complianceOnDemandPCIEnabled() {
        ComplianceAdminPage adminPage = new ComplianceAdminPage();
        ComplianceLandingPage landingPage = new ComplianceLandingPage();

        SoftAssert softAssert = new SoftAssert();


        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
        adminPage.clickComplianceTab();

        adminPage.selectPolicyGroup("HIPAA");
        adminPage.enableComplianceScan(false);
        adminPage.savePolicyProfile("HIPAA");

        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        landingPage.clickSysWithVioTab(RccConstants.SYSTEMS_VIOLATIONS_SUB_VIEW);

        landingPage.clickRunComplianceCheck(1);
      //  softAssert.assertEquals(landingPage.getRunComplianceHeaderName(),RccConstants.USER_INITAITIVE_TITLE_MODAL,"The title of the modal is wrong");
        softAssert.assertEquals(landingPage.getRunComplianceSelectedSystemName(),landingPage.getFirstAssetName(),"The title of the modal is wrong");
        softAssert.assertEquals(landingPage.getRunComplianceSelectedPGName(),"PCI","The title of the modal is wrong");

        landingPage.clickCloseButtonTheRunComplianceCheck();
        softAssert.assertAll();
    }


    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether user able to initate on-demand from the asset with violation page when both policy group disabled",priority=3)
    public void complianceOnDemandBothDisabled() {
        ComplianceAdminPage adminPage = new ComplianceAdminPage();
        ComplianceLandingPage landingPage = new ComplianceLandingPage();

        SoftAssert softAssert = new SoftAssert();


        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
        adminPage.clickComplianceTab();

        adminPage.selectPolicyGroup("PCI");
        adminPage.enableComplianceScan(false);
        adminPage.savePolicyProfile("PCI");

        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);

        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        softAssert.assertEquals(landingPage.getBannerTitle(),RccConstants.OPTOUT_BANNER_TITLE,"Title of the optout as expected");
        softAssert.assertEquals(landingPage.getBannerMessage(),RccConstants.ENABLE_BANNER_BODY,"Message of the optout as expected");

        adminPage.clickBannerSettingButton();
        softAssert.assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify whether user able to initate on-demand from the asset with violation page when HIPAA policy group is enabled",priority=5)
    public void complianceOnDemandHIPAAEnabled(){

        ComplianceAdminPage adminPage = new ComplianceAdminPage();
        ComplianceLandingPage landingPage = new ComplianceLandingPage();

        SoftAssert softAssert = new SoftAssert();

        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
        adminPage.clickComplianceTab();

        adminPage.selectPolicyGroup("HIPAA");
        adminPage.enableComplianceScan(true);
        adminPage.savePolicyProfile("HIPAA");

        softAssert.assertTrue(adminPage.closeAdminPage().isLifeCyleTabDisplayed(),"After closing it didnt land to the lifecycle");
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);
        landingPage.clickSysWithVioTab(RccConstants.SYSTEMS_VIOLATIONS_SUB_VIEW);

        landingPage.clickRunComplianceCheck(1);
        softAssert.assertEquals(landingPage.getRunComplianceSelectedSystemName(),landingPage.getFirstAssetName(),"The title of the modal is wrong");
        softAssert.assertEquals(landingPage.getRunComplianceSelectedPGName(),"HIPAA","The title of the modal is wrong");

        landingPage.clickCloseTheRunComplianceCheck();
        softAssert.assertAll();

    }

    @AfterClass
    public void enableBothPolicies(){
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        ComplianceAdminPage adminPage = new ComplianceAdminPage();
        String[] policies = {"HIPAA", "PCI"};

        adminPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, RccConstants.TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        adminPage.clickGlobalSettingBtn();
        adminPage.clickInsightsSetting();
//        adminPage.clickComplianceTab();
        landingPage.clickCompianceTab(RccConstants.COMPLIANCE_TAB);

        if(!adminPage.isOptedIn())
            adminPage.clickOptInOption();

        Arrays.stream(policies).forEach(e -> {
            adminPage.selectPolicyGroup(e);
            if(!adminPage.isPolicyEnabled()){
                adminPage.enableComplianceScan(true);
                adminPage.savePolicyProfile(e);
            }
        });
    }

}