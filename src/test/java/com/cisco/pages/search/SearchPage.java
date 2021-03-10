package com.cisco.pages.search;

import com.cisco.pages.cases.CaseUtils;
import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends CaseUtils {

    public static final String SEVERITY = "Severity";
    public static final String CREATED = "Created";
    public static final String CONTRACT = "Contract";
    public static final String CASE_OWNER = "Case Owner";
    public static final String ASSET = "Asset";
    public static final String TAC_ENGINEER = "TAC Engineer";
    public static final String TRACKING_NUMBER = "Tracking Number";
    public static final String RELATED_RMAS = "Related RMAs";
    public static final String PRODUCT = "Product";
    public static final String ASSET_SUPPORT_COVERAGE = "Asset Support Coverage";
    public static final String EXPIRATION_DATE = "Expiration Date";
    public static final String SOFTWARE_TYPE = "Software Type (OS)";
    public static final String CURRENT_RELEASE = "Current Release";
    public static final String OPEN_CASES = "Open Cases";
    public static final String OPEN_RMAS = "Open RMAs";
    public static final String RELATED_FIELD_NOTICES = "Related Field Notices";
    public static final String RELATED_SECURITY_ADVISORIES = "Related Security Advisories";
    public static final String RELATED_BUGS = "Related Bugs";
    public static final String PRODUCT_ID = "Product ID";
    public static final String SERIAL_NUMBER = "Serial Number";
    public static final String IP_ADDRESS = "IP Address";
    public static final String HOST_NAME = "Host Name";
    public static final String START_DATE = "Start Date";
    public static final String ASSETS_COVERED = "Assets Covered";


    @Step("Input the Search text {0} in the Global Search bar")
    public WebElement inputSearchText(String searchText) {
        WebElement searchBar = getWebElement("//input[@data-auto-id='searchBarInput']");
        searchBar.clear();
        searchBar.sendKeys(searchText);
        return searchBar;
    }

    @Step("Perform the Search for the text {0}")
    public void performSearch(String searchText) {
        WebElement searchBar = inputSearchText(searchText);
        searchBar.sendKeys(Keys.ENTER);
    }

    @Step("Get the Case status present in the Search results")
    public String getStatusValue() {
        return getWebElement("//span[text()='Status:']/following-sibling::span").getText();
    }

    @Step("Get the Search result Header value")
    public String getSearchHeader() {
        return getWebElement("//h3").getText();
    }

    @Step("Get the Case title from the Case Search results")
    public String getCaseTitle() {
        return getWebElement("//h3/following-sibling::div").getText();
    }

    @Step("Get the value for the label {0}")
    public String getFieldValue(String labelName) {
        return getWebElement("//*[text()='" + labelName + "']//following::span[1]").getText();
    }

    @Step("Get the Contract Search result header")
    public String getContractHeader() {
        return getWebElement("//h4").getText();
    }

    @Step("Close the Search result window")
    public void closeSearchResult() {
        getWebElement("//button[@data-auto-id='searchClose']").click();
    }

    @Step("Click the Case create Submit button when Opening a case from Global search")
    public void clickCaseCreateSubmit() {
        getWebElement("//app-case-open//*[contains(@class,'btn')][text()='Submit']").click();
    }

    @Step("Check whether the Open Case Button is available in Asset search Results based on the Coverage of the asset")
    public Boolean validateOpenCaseButtonAvailable() {
        return isElementPresent("//div[@id='specialContainer']//*[contains(@class,'btn')][text()='Open a Case']");
    }

}
