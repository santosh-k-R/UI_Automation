package com.cisco.tests.controlPoint.loginAndMiscellaneous;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.controlPoint.loginAndMiscellaneous.AdminPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.cisco.testdata.Data.CONTROL_POINT_DATA;
import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;


public class AdminPageIT extends DriverBase {
    private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");

    @BeforeClass
    public void skipInfoPopup(){
        AdminPage adminPage = new AdminPage();
        adminPage.login()
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);

        adminPage.switchCXCloudAccount("AMERICAN NATIONAL INSURANCE COMPANY INC");
        adminPage.clickOnAdminIcon();
        adminPage.skipInfoPopups();
    }

    @Test(description = "Verify if admin settings icon is displayed for user with customer role ",priority = 1)
    public void adminSettingsIconTest(){
        AdminPage adminPage = new AdminPage();
       Assert.assertTrue(adminPage.checkAdminSettingsTabDisplayed());

   }

    @Test(description = "Verify if admin role user have access to all tabs in admin settings page ")
    public void adminSettingPageIconsTest(){
        SoftAssert softAssert = new SoftAssert();
        AdminPage adminPage = new AdminPage();
        List<String> missingTabInfo=adminPage.getAllTabsOnAdminPage();
        softAssert.assertTrue(missingTabInfo.size()==0,"Not all tabs are displayed for admin user,"+(missingTabInfo.size()+" tab(s) missing"));
        if(missingTabInfo.size()>0){
            missingTabInfo.stream().forEach(e->
                    softAssert.assertTrue(false,e+" tab not displayed fro admin user"));
        }
        softAssert.assertAll("Validated all tabs in admin page");
    }

   @Test(description = "Verify if admin settings header is displayed")
    public void dataSourcesHeaderTitleTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getAdminSettingsHeader(), "Data Sources");
    }

    @Test(description = "Verify if How do I add a Cisco DNA Center? is displayed")
    public void ciscoDNACenterLinkTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getCiscoDNALink(), "How do I add a Cisco DNA Center?");
    }

    @Test(description = "Verify if STATUS title is displayed")
    public void statusTitleTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getStatusTitle(), "STATUS");
    }

    @Test(description = "Verify if CX Cloud Agent is displayed")
    public void cxCloudAgentTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getCXCloudAgentTilte(), "CX Cloud Agent");
    }

    @Test(description = "Verify if version is displayed")
    public void agentVersionTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getCXCloudAgentVersion(), "v1.1.0");
    }

    @Test(description = "Verify if Re-install link is displayed")
    public void reInstallLinkTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getReInstallLink(), "Re-install");
    }

    @Test(description = "Verify if View Update link is displayed")
    public void viewUpdateLinkTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getViewUpdateLink(), "View Update");
    }

    @Test(description = "Verify if dnac IP Address is displayed")
    public void dnacIPAddressTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getDNACIPAddress(), "192.168.139.149/dnac1.cisco.com");
    }

    @Test(description = "Verify if Cisco DNA Center text is displayed")
    public void ciscoDNACeterTextTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getCiscoDNACenter(), "Cisco DNA Center");
    }

    @Test(description = "Verify if agent Running green icon is displayed")
    public void agentRunningIconTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertTrue(adminPage.checkAgentRunningIcon(), "Agent is in NOT RUNNING state");
    }

    @Test(description = "Verify if DNAC Reachable geen icon is displayed")
    public void dnacReachableIconTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertTrue(adminPage.checkDNACReachableIcon(), "Agent is in NOT RUNNING state");
    }

    @Test(description = "Verify if How do I add a Cisco DNA Center? is displayed")
    public void dataSourcesLinkTest(){
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getCiscoDNALink(), "How do I add a Cisco DNA Center?");
    }

    @Test(description = "Verify right text for if How do I add a Cisco DNA Center? poup over")
    public void validateCiscoDNACenterLinkTest(){
        AdminPage adminPage = new AdminPage();
        adminPage.clickCiscoDNALink();
        Assert.assertTrue(adminPage.isDNACenterPopupPresent());
        Assert.assertEquals(adminPage.getHeaderCiscoDNALink(), "How do I add a Cisco DNA Center?");
        Assert.assertEquals(adminPage.getCiscoDNATextOne(), CONTROL_POINT_DATA.get("CiscoDNACenterOne"));
        Assert.assertEquals(adminPage.getCiscoDNATextTwo(), CONTROL_POINT_DATA.get("CiscoDNACenterTwo"));
        adminPage.clickCiscoDNAXIcon();
    }

    @Test(description = "Verify Close button X, works at How do I add a Cisco DNA Center? poup over")
    public void validateXIconAtPopOverTest(){
        AdminPage adminPage = new AdminPage();
        adminPage.clickCiscoDNALink();
        Assert.assertTrue(adminPage.isDNACenterPopupPresent());
        adminPage.clickCiscoDNAXIcon();
        Assert.assertTrue(!adminPage.isDNACenterPopupPresent());
    }


    @Test(description = "Verify right text for Re-Install poup over")
    public void validateReinstallPopupTest(){
        AdminPage adminPage = new AdminPage();
        adminPage.clickReInstallLink();
        Assert.assertTrue(adminPage.isAlertIconPresntAtReinstallPopup());
        Assert.assertTrue(adminPage.isHeaderPresntAtReinstallPopup());
        Assert.assertEquals(adminPage.getReInstallTextAtPopOver(), CONTROL_POINT_DATA.get("ReInstallText"));
        adminPage.clickReInstallXIcon();
    }

    @Test(description = "Verify Buttons Present at Re-Install poup over")
    public void validateButtonsPresentAtReinstallPopupTest(){
        AdminPage adminPage = new AdminPage();
        adminPage.clickReInstallLink();
        Assert.assertTrue(adminPage.isXIconPresntAtReinstallPopup());
        Assert.assertTrue(adminPage.isContinueButtonPresntAtReinstallPopup());
        Assert.assertTrue(adminPage.isCancelButtonPresntAtReinstallPopup());
        adminPage.clickReInstallXIcon();
    }

   @Test(description = "Verify Cancel Button is working for Re-Install poup over")
    public void validateCancelButtonAtReinstallPopupTest(){
        AdminPage adminPage = new AdminPage();
        adminPage.clickReInstallLink();
        Assert.assertTrue(adminPage.isXIconPresntAtReinstallPopup());
        adminPage.clickCancelButtonAtReinstallPopup();
        Assert.assertEquals(adminPage.getAdminSettingsHeader(), "Data Sources");
    }


    @Test(description = "Verify Patch Upgrade Popup is working")
    public void validatePatchUpgradePopupTest() {
        AdminPage adminPage = new AdminPage();
        adminPage.clickViewUpdateDownArrow();
        Assert.assertTrue(adminPage.isPatchTextPresent());
        adminPage.clickPatchText();
        Assert.assertTrue(adminPage.isPatchVersionHeaderPresent());
        adminPage.clickPatchPopupCloseIcon();
    }

    @Test(description = "Verify Patch Upgrade Popup has alert icon and right text")
    public void validateAlertIconAndTextAtpPatchUpgradePopupTest() {
        AdminPage adminPage = new AdminPage();
        adminPage.clickViewUpdateDownArrow();
        Assert.assertTrue(adminPage.isPatchTextPresent());
        adminPage.clickPatchText();
        Assert.assertTrue(adminPage.isAlertInconPresent());
        Assert.assertEquals(adminPage.getImpactYourNetworkTitleText(), "Impact to Your Network:");
        Assert.assertEquals(adminPage.getImpactToYourNetworkValue(), CONTROL_POINT_DATA.get("ImpacttoYourNetworkText"));
        adminPage.clickPatchPopupCloseIcon();
    }

    @Test(description = "Verify Patch Upgrade Popup has Upgrade Now and Schedule Upgrade Radio Buttons")
    public void validateUpgradeNowAndScheduleUpgradeRadioButtonsTest() {
        AdminPage adminPage = new AdminPage();
        adminPage.clickViewUpdateDownArrow();
        Assert.assertTrue(adminPage.isPatchTextPresent());
        adminPage.clickPatchText();
        Assert.assertTrue(adminPage.isUpgradeNowRadioButtonPresent());
        Assert.assertTrue(adminPage.isScheduleUpgradeRadioButtonPresent());
        adminPage.clickPatchPopupCloseIcon();
    }

    @Test(description = "Verify Patch Upgrade Popup has UPGRADE NOW Button")
    public void validateUpgradeNowButtonAtPatchUpgradePopupTest() {
        AdminPage adminPage = new AdminPage();
        adminPage.clickViewUpdateDownArrow();
        Assert.assertTrue(adminPage.isPatchTextPresent());
        adminPage.clickPatchText();
        Assert.assertTrue(adminPage.isUpgradeNowButtonPresent());
        adminPage.clickPatchPopupCloseIcon();
    }

    @Test(description = "Verify Patch Upgrade Popup has SCHEDULE UPGRADE Button")
    public void validateScheduleUpgradeButtonAtPatchUpgradePopupTest() {
        AdminPage adminPage = new AdminPage();
        adminPage.clickViewUpdateDownArrow();
        Assert.assertTrue(adminPage.isPatchTextPresent());
        adminPage.clickPatchText();
        adminPage.clickScheduleUpgradeRadioButtonPresent();
        Assert.assertTrue(adminPage.isScheduleUpgradeButtonPresent());
        adminPage.clickPatchPopupCloseIcon();
    }

    @Test(description = "Verify Schedule Upgrade Date Attributes")
    public void validateScheduleUpgradeDateAttributesTest() {
        AdminPage adminPage = new AdminPage();
        adminPage.clickViewUpdateDownArrow();
        Assert.assertTrue(adminPage.isPatchTextPresent());
        adminPage.clickPatchText();
        adminPage.clickScheduleUpgradeRadioButtonPresent();
        adminPage.clickDownArrowAtScheduleUpgrade();
        Assert.assertTrue(adminPage.isSceduleUpgradeDayLablePresent());
        Assert.assertTrue(adminPage.isSceduleUpgradeDateSelectComboPresent());
        Assert.assertTrue(adminPage.isSceduleUpgradeDateTimeLablePresent());
        Assert.assertTrue(adminPage.isSceduleUpgradeTimeComboPresent());
        Assert.assertTrue(adminPage.isSceduleUpgradeTimeZonePresent());
        adminPage.clickPatchPopupCloseIcon();
    }


    @Test(description = "Verify tool tip icon at All-Assets over")
    public void validateToolTipAtAllAssetsTest(){
        AdminPage adminPage = new AdminPage();
        adminPage.clickAssetGroupsTab();
        Assert.assertTrue(adminPage.isDNACenterPopupPresent());
        adminPage.clickCiscoDNAXIcon();
        Assert.assertTrue(!adminPage.isDNACenterPopupPresent());
    }

    @Test(description = "Verify Support Cases title & Text present")
    public void validateSupportCasesTitleAndTextTest() {
        AdminPage adminPage = new AdminPage();
        Assert.assertEquals(adminPage.getSupportCasesHeader(), "Support Cases");
        Assert.assertEquals(adminPage.getSupportCasesText(), "Send device data to TAC for Rapid Problem Resolution cases");
    }

    @Test(description = "Verify Support Cases toggle present & is functional")
    public void validateSupportCasesToggleTest() {
        AdminPage adminPage = new AdminPage();
        Assert.assertTrue(adminPage.isSupportCasesTogglePresent());
        Assert.assertEquals(adminPage.getSupportCasesYesText(), "Yes");
    }

    @AfterClass
    public void cleanUp(){
        AdminPage adminPage = new AdminPage();
        adminPage.clickCloseButton();
    }
}