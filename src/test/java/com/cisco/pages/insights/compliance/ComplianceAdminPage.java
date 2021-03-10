package com.cisco.pages.insights.compliance;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.cisco.testdata.StaticData.ButtonName;
import com.cisco.utils.AppUtils;

public class ComplianceAdminPage extends AppUtils{

    Logger logger = Logger.getLogger(ComplianceAdminPage.class);


    private String AdminPageHeader = ".header-bar__heading h2";
    private String settingButton = "//a[@data-auto-id='Admin']";
    private String insightsLink = "//*[@data-auto-id='settings.sidebar']/ul/li[6]";
    private String complianceAdminTab = "//a[@data-auto-id='ComplianceTabNav']";
    private String complianceAssetSelectionHeader="//div[@id='group_systems']";
    private String complianceAdminHeader = "//div[text()='REGULATORY COMPLIANCE']";
    private String complianceAdminMessgae = "//div[text()='REGULATORY COMPLIANCE']/../div[2]";
    private String compliancePolicyHeader = "//div[text()='policy Profile']";
    private String compliancePolicyGroupHeader="//label[text()='Policy Group']";
    private String lifecyleCurosel = "//span[text()='LIFECYCLE']";
    private String toggleOptOutIN=".switch__input";
    private String toggleLabel="//span[@class='switch__label'][1]";

    private String modalTitleOpt="//cui-modal//div[@class='modal__header']";
    private String modalBodyOpt="//div[@class='modal__body']";
    private String modalCloseSign="cuiModalService.hide";
    private String modalNoButton="saveChanges";
    private String modalYesButton="discardChanges";
    private String runComplianceCheck="run-compliance-id";
    private String complianceSetting="//button[text()='COMPLIANCE SETTINGS']";
    private String complianceEnable=".switch:nth-child(2) > .switch__input";
    private String savePolicyDetails="savePolicyDetails";


    @Step("Click Banner Setting Button")
    public ComplianceAdminPage clickBannerSettingButton()
    {
        getWebElement(complianceSetting).click();
        getWebDriverWait().until(loadingWheelDisappears);
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(toggleOptOutIN)));

        return this;

    }

    @Step("Click Global Setting Button")
    public ComplianceAdminPage clickGlobalSettingBtn() {
        getWebElement(settingButton).click();
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(insightsLink)));
        return this;
    }

    public String getInsightsAttribute() {
        String attr = getWebElement(insightsLink).getAttribute("class");
        logger.info("Class value is : " + attr);
        return attr;
    }

    public String getRunComplianceAttribute() {
        String attr = getWebElement(runComplianceCheck).getAttribute("class");
        logger.info("Class value is : " + attr);
        return attr;
    }

    @Step("Click Insights Setting Button")
    public ComplianceAdminPage clickInsightsSetting() {
        getWebElement(insightsLink).click();
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(complianceAdminTab)));

        return this;
    }

    @Step("Get Insights Setting Button Name")
    public String getInsightsSettingName() {
        return getWebElement(insightsLink+"//descendant::span[text()='Insights']").getText();
    }

    @Step("Click Compliance Tab")
    public ComplianceAdminPage clickComplianceTab() {
        getWebElement(complianceAdminTab).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Select']")));
        return this;
    }

    @Step("Click Compliance Tab while Opt-out")
    public ComplianceAdminPage clickComplianceTabWhileOptOut() {
        getWebElement(complianceAdminTab).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(toggleOptOutIN)));
        return this;
    }

    @Step("Get Modal Title")
    public String getModalTitle()
    {
        return getWebElement(modalTitleOpt).getText();
    }

    public String getModalBody()
    {
        return getWebElement(modalBodyOpt).getText();

    }

    @Step("Close Admin Modal")
    public ComplianceAdminPage clickAminModalClose() {
        getWebElement(modalCloseSign).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Select']")));
        return this;

    }

    @Step("Select No in Admin Modal Close popup")
    public ComplianceAdminPage clickAminModalNo() {
        getWebElement(modalNoButton).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Select']")));
        return this;

    }

    @Step("Select Yes in Admin Modal Close Pop up")
    public ComplianceAdminPage clickAminModalYes() {
        getWebElement(modalYesButton).click();
        getWebDriverWait().until(loadingWheelDisappears);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return this;

    }

    @Step("Click Opt-In Switch icon")
    public ComplianceAdminPage clickOptOption()
    {
        getWebElement(toggleOptOutIN).click();
        return this;

    }

    @Step("Click OptIn Option")
    public ComplianceAdminPage clickOptInOption()
    {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(toggleOptOutIN)));
        getWebElement(toggleOptOutIN).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Select']")));
        return this;
    }

    @Step("Get Opt-In Status")
    public String getOptLabel() {
        getWebElement(toggleLabel).isDisplayed();
        return getWebElement(toggleLabel).getText();
    }

    @Step("Get Compliance Header Name")
    public String getComplianceHeaderName() {
        return getWebElement(complianceAdminHeader).getText();
    }

    @Step("Get Compliance Title Message")
    public String getComplianceTitleMessage() {
        return getWebElement(complianceAdminMessgae).getText();
    }

    @Step("Get Policy Profile Header Name")
    public String getPolicyProfileHeaderName() {
        return getWebElement(compliancePolicyHeader).getText();
    }

    @Step("Click Policy Group Header Name")
    public String getPolicyGroupHeaderName() {
        return getWebElement(compliancePolicyGroupHeader).getText();
    }

    @Step("Get Asset Selection Header Name")
    public String getAssetSelectionHeaderName() {
        return getWebElement(complianceAssetSelectionHeader).getText();
    }

    @Step("Check whether Admin Page Header is Displayed")
    public boolean isAdminPageHeaderDisplayed() {
        return getWebElement(AdminPageHeader).isDisplayed();
    }

    @Step("Get Policy Group Status")
    public boolean getPolicyGroupStatus() {
        try {
            getWebElement("PolicySelected");
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    @Step("Get List of Policy Groups")
    public ArrayList<String> getPolicyGroupList() {
        return (ArrayList<String>) getDropDownValues("//select[@data-auto-id='PolicySelected']");
    }

    @Step("Select policy Group")
    public ComplianceAdminPage selectPolicyGroup(String value)
    {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Select']")));
        Select policyGroupList = new Select(getWebElement("//select[@data-auto-id='PolicySelected']"));
        policyGroupList.selectByVisibleText(value);
        getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner-container")));
        return this;

    }

    @Step("Enable Compliance Scan")
    public ComplianceAdminPage enableComplianceScan(boolean value) {
        if (value)
            getWebDriverWait().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//input[@data-auto-id='enable compliance check']/../span[text()='No']"),"No"));
        else
            getWebDriverWait().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//input[@data-auto-id='toBeScanned']/../span[text()='Yes']"),"Yes"));

        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(complianceEnable)));
        getWebElement(complianceEnable).click();
        return this;
    }

    @Step("Save Policy Profile")
    public ComplianceAdminPage savePolicyProfile(String label)
    {
        getWebElement(savePolicyDetails).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'"+label+" policy')]")));
        return this;
    }

    @Step("Get Asset Selection List")
    public ArrayList<String> getAssetSelectionList() {
        ArrayList<String> valuesSelection = new ArrayList<String>();
        for (WebElement eachElement : getWebElements("//div[@id='systems_id']//span[@class='radio__label']"))
            valuesSelection.add(eachElement.getText().trim());
        return valuesSelection;
    }

    @Step("Close Compliance Admin Page")
    public ComplianceAdminPage closeAdminPage() {
        getWebElement("xbutton").click();
        waitForInitialPageToLoad();
        return this;
    }

    @Step("Verify whether LifeCycle Tab displayed")
    public boolean isLifeCyleTabDisplayed() {
        return getWebElement(lifecyleCurosel).isDisplayed();

    }

    @Step("Verify whether policies are enabled")
    public boolean isPolicyEnabled(){
        getWebDriverWait().until(loadingWheelDisappears);
        return getWebElement("input[data-auto-id='toBeScanned'] ~ span[class='switch__label']").getText()
                    .equalsIgnoreCase("yes");
    }

    @Step("Verify whether customer is opted in")
    public boolean isOptedIn(){
        getWebDriverWait().until(loadingWheelDisappears);
        return getWebElement("div[data-auto-id='toggleOptlnStatus'] span[class='switch__label']").getText()
                .equalsIgnoreCase("on");
    }

}
