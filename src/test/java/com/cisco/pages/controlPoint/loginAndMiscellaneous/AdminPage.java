package com.cisco.pages.controlPoint.loginAndMiscellaneous;

import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

public class AdminPage extends AppUtils {


    @Step("click on Admin Settings icon on header")
    public void clickOnAdminIcon() {
        getWebElement("//a[@data-auto-id='Admin']").click();
    }

    @Step("Check if Admin Setting icon is displayed otr not")
    public boolean checkAdminSettingsTabDisplayed(){
        return isElementPresent("//a[@data-auto-id='Admin']");
    }

    @Step("Check if all tabs are present")
    public List<String> getAllTabsOnAdminPage(){
        Set <String> tabsDisplayed = new HashSet<>();
        getWebElements("//div[contains(@class,'sidebar-Icon')]").stream().forEach(eachClass->tabsDisplayed.add(eachClass.getAttribute("class").replace(" sidebar-Icon","").trim()));

        String[] classes={"icon-data-source","icon-asset-group","icon-identity-access","icon-partners","icon-system","icon-insight"};
        Set<String> expectedTabs= new HashSet(Arrays.asList(classes));
        List<String> missingTab=new ArrayList();
        for (String eachTab:expectedTabs) {
            try{
            getWebElement("//div[contains(@class,'"+eachTab+"')]");
            }
            catch(NoSuchElementException e){
                missingTab.add(eachTab.replace("icon-",""));
            }
        }
        return missingTab;
    }

    @Step("click on Admin Settings icon on header")
    public String getAdminSettingsHeader() {
        return getWebElement("//h2[contains(text(),'Data')]").getText();
    }

    @Step("Cisco DNA Center Link")
    public String getCiscoDNALink() {
        return getWebElement("//a[contains(text(),'How do I add a Cisco DNA Center?')]").getText();
    }

    @Step("Cisco DNA Center Link")
    public String getStatusTitle() {
        return getWebElement("//h5[contains(text(),'STATUS')]").getText();
    }

    @Step("CX Cloud Agent Text")
    public String getCXCloudAgentTilte() {
        return getWebElement("//div[contains(text(),'CX Cloud Agent')]").getText();
    }

    @Step("CX Cloud Agent Version")
    public String getCXCloudAgentVersion() {
        return getWebElement("//span[text()='v1.1.0']").getText();
    }

    @Step("CX Cloud Agent Version")
    public String getReInstallLink() {
        return getWebElement("//a[contains(text(),'Re-install')]").getText();
    }

    @Step("CX Cloud View Update")
    public String getViewUpdateLink() {
        waitTillElementIsVisible("//span[contains(text(),'View Update')]");
        return getWebElement("//span[contains(text(),'View Update')]").getText();
    }

    @Step("CX Cloud DNAC IP Address")
    public String getDNACIPAddress() {
        return getWebElement("//div[@class='dnac-name']").getText();
    }

    @Step("CX Cloud DNA Center")
    public String getCiscoDNACenter() {
        return getWebElement("//span[contains(text(),'Cisco DNA Center')]").getText();
    }

    @Step("CX Cloud Agent Running Icon")
    public boolean checkAgentRunningIcon() {
        return isElementPresent("//span[.='Running']/preceding-sibling::span[contains(@class,'text-success')]");
    }

    @Step("CX Cloud DNAC Reachanble Icon")
    public boolean checkDNACReachableIcon() {
        return isElementPresent("//span[.='Reachable']/preceding-sibling::span[contains(@class,'text-success')]");
    }

    @Step("Cisco DNA Center Link")
    public void clickCiscoDNALink() {
        waitTillElementIsVisible("//a[contains(text(),'How do I add a Cisco DNA Center?')]");
         getWebElement("//a[contains(text(),'How do I add a Cisco DNA Center?')]").click();
    }

    @Step("Cisco Asset Group Tab")
    public void clickAssetGroupsTab() {
        waitTillElementIsVisible("//div[@class='icon-asset-group sidebar-Icon']");
        getWebElement("//div[@class='icon-asset-group sidebar-Icon']").click();
    }


    @Step("Cisco DNA Center pop up X icon click ")
    public void clickCiscoDNAXIcon() {
        getWebElement("//div[@class='icon-close icon-xsmall pull-right']").click();
    }

    @Step("Cisco DNA Center pop up X icon click ")
    public void clickReInstallXIcon() {
        getWebElement("//span[@class='icon-close']").click();
    }

    @Step("Cisco DNA Center Pop over")
    public boolean isDNACenterPopupPresent() {
        return isElementPresent("//div[@class='cxui-popover']");
    }

    @Step("Reinstall Pop over")
    public boolean isReinstallPopupPresent() {
        return isElementPresent("//div[@class='modal modal--large']//div[@class='modal__content']");
    }

    @Step("Reinstall Alert Icon Pop over")
    public boolean isAlertIconPresntAtReinstallPopup() {
        return isElementPresent("//div[@class='alert__icon icon-warning-outline']");
    }

    @Step("Reinstall Header at Pop over")
    public boolean isHeaderPresntAtReinstallPopup() {
        return isElementPresent("//h4[contains(text(),'Re-install')]");
    }

    @Step("Reinstall Continue Button at Pop over")
    public boolean isContinueButtonPresntAtReinstallPopup() {
        return isElementPresent("//button[@class='btn btn--primary']");
    }

    @Step("Reinstall Cancel Button at Pop over")
    public boolean isCancelButtonPresntAtReinstallPopup() {
        return isElementPresent("//button[@class='btn']");
    }

    @Step("Reinstall Cancel Button at Pop over")
    public void clickCancelButtonAtReinstallPopup() {
        getWebElement("//button[@class='btn']").click();
    }

    @Step("Reinstall Continue Button at Pop over")
    public void clickContinueButtonAtReinstallPopup() {
        getWebElement("//button[@class='btn btn--primary']").click();
    }

    @Step("Reinstall X icon at Pop over")
    public boolean isXIconPresntAtReinstallPopup() {
        return isElementPresent("//span[@class='icon-close']");
    }

    @Step("Install CX Cloud Agent on a VM header")
    public boolean isInstallCXCloudAgentHeaderPresent() {
        waitTillElementIsVisible("//div[@class='modal__header release_note_header']");
        return isElementPresent("//div[@class='modal__header release_note_header']");
    }

    @Step("Install CX Cloud Agent on a VM header")
    public boolean isPatchVersionHeaderPresent() {
       return isElementPresent("//div[@class='modal__header release_note_header']");
    }

    @Step("Patch Upgrade Alert Icon")
    public boolean isAlertInconPresent() {
        return isElementPresent("//span[@class='icon-exclamation-triangle']");
    }

    @Step("Patch Upgrade - Upgrade Now Radio Button Present")
    public boolean isUpgradeNowRadioButtonPresent() {
        return isElementPresent("//label[@class='radio updateCollectorRadio']//span[@class='radio__input']");
    }

    @Step("Patch Upgrade - Schedule Upgrade Radio Button Present")
    public boolean isScheduleUpgradeRadioButtonPresent() {
        return isElementPresent("    //label[@class='radio half-margin-top schedule-radio-display']//span[@class='radio__input']");
    }

    @Step("Patch Upgrade - UPGRADE NOW Button Present")
    public boolean isUpgradeNowButtonPresent() {
        return isElementPresent("//button[text()='UPGRADE NOW']");
    }

    @Step("Patch Upgrade - SCHEDULE UPGRADE Button Present")
    public boolean isScheduleUpgradeButtonPresent() {
        return isElementPresent("//button[text()='SCHEDULE UPGRADE']");
    }

    @Step("Patch Schedule Upgrade - Day Lable Present")
    public boolean isSceduleUpgradeDayLablePresent() {
        return isElementPresent("//span[contains(text(),'Day:')]");
    }

    @Step("Patch Schedule Upgrade - Date Select combo Present")
    public boolean isSceduleUpgradeDateSelectComboPresent() {
        return isElementPresent("//div[@class='dd__input']");
    }

    @Step("Patch Schedule Upgrade - Date Time lable Present")
    public boolean isSceduleUpgradeDateTimeLablePresent() {
        return isElementPresent("//span[contains(text(),'Time:')]");
    }

    @Step("Patch Schedule Upgrade - Time Combo Present")
    public boolean isSceduleUpgradeTimeComboPresent() {
        return isElementPresent("//select[@id='hourmin-select']");
    }

    @Step("Patch Schedule Upgrade - Time Zone Present")
    public boolean isSceduleUpgradeTimeZonePresent() {
        return isElementPresent("//select[@id='hourmin-select']");
    }

    @Step("Patch Schedule Upgrade - Time Zone Present")
    public boolean isSupportCasesTogglePresent() {
        waitTillElementIsVisible("//span[@class='switch__input']");
        return isElementPresent("//span[@class='switch__input']");
    }

    @Step("Cisco DNA Center pop over header")
    public String getHeaderCiscoDNALink() {
        return getWebElement("//a[contains(text(),'How do I add a Cisco DNA Center?')]").getText();
    }

    @Step("Patch Upgrage pop up - Impact Your Network text")
    public String getImpactYourNetworkTitleText() {
        return getWebElement("//strong[@class='half-margin-left']").getText();
    }

    @Step("Verify Support Cases header")
    public String getSupportCasesHeader() {
        waitTillElementIsVisible("//h6[contains(text(),'Support Cases')]");
        return getWebElement("//h6[contains(text(),'Support Cases')]").getText();
    }

    @Step("Verify Support Cases Text")
    public String getSupportCasesText() {
        return getWebElement("//span[contains(text(),'Send device data')]").getText();
    }

    @Step("Verify Support Cases Text")
    public String getSupportCasesYesText() {
        return getWebElement("//span[@class='switch__label']").getText();
    }

    @Step("Cisco DNA Center pop over text one")
    public String getCiscoDNATextOne() {
        return getWebElement("//div[contains(text(),'Cisco DNA Center appliances')]").getText();
    }

    @Step("Cisco DNA Center pop over text two")
    public String getCiscoDNATextTwo() {
        return getWebElement("//div[contains(text(),\"The Cisco DNA Center appliance's credentials will\")]").getText();
    }

    @Step("ReInstall text present at pop over")
    public String getReInstallTextAtPopOver() {
        return getWebElement("//div[contains(text(),'Reinstalling CX Cloud Agent requires')]").getText();
    }

    @Step("Impact to Your Network text present at Patch Upgrade Pop up")
    public String getImpactToYourNetwork() {
        return getWebElement("//div[contains(text(),'Reinstalling CX Cloud Agent requires')]").getText();
    }

    @Step("Impact to Your Network text present at Patch Upgrade Pop up")
    public String getImpactToYourNetworkValue() {
        return getWebElement("//span[contains(text(),'An upgrade can take')]").getText();
    }

    @Step("CX Cloud Agent Version")
    public void clickReInstallLink() {
        waitTillElementIsVisible("//a[contains(text(),'Re-install')]");
        getWebElement("//a[contains(text(),'Re-install')]").click();
    }

    @Step("Click on Gear Icon")
    public void clickGearIcon() {
        waitTillElementIsVisible("//span[contains(@class,'cx-header-links')]//a");
        getWebElement("//span[contains(@class,'cx-header-links')]//a").click();
    }

    @Step("Patch Upgrade - Click Schedule Upgrade Radio Button")
    public void clickScheduleUpgradeRadioButtonPresent() {
        getWebElement("//label[@class='radio half-margin-top schedule-radio-display']//span[@class='radio__input']").click();
    }

    @Step("Click on Gear Icon")
    public void clickPatchPopupCloseIcon()  {
        waitTillElementIsVisible("//span[@class='icon-close']");
        getWebElement("//span[@class='icon-close']").click();
    }

    @Step("Click on Gear Icon")
    public void clickViewUpdateDownArrow()  {
        waitTillElementIsVisible("//span[contains(text(),'View Update')]");
        getWebElement("//span[contains(text(),'View Update')]").click();
    }


    @Step("check if patch text is present")
    public boolean isPatchTextPresent()  {
        return isElementPresent("//div[contains(text(),':')]");
    }

    @Step("Click on Gear Icon")
    public void clickPatchText() {
        getWebDriverWait().until(ExpectedConditions.visibilityOf(getWebElement("//div[contains(text(),':')]")));
        getWebElement("//div[contains(text(),':')]").click();
    }

    @Step("Click on Down Arrow at Schedule Upgrade")
    public void clickDownArrowAtScheduleUpgrade()  {
        getWebElement("//span[@class='icon-chevron-down']").click();
    }

    @Step("CLose the admin screen")
    public AdminPage clickCloseButton(){
        getJavascriptExecutor().executeScript("let ele=document.querySelector(\"[data-auto-id='xbutton']\");ele.click();");
       return this;
    }

    @Step("Skip the info popups on admin page")
    public void skipInfoPopups(){
        while(isElementPresent("//div[contains(@class,'popup--')]")){
            getWebElement("//div[contains(@class,'popup--')]//button[position()=1]").click();
        }
    }
 }
