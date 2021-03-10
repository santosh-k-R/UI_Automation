package com.cisco.pages.cases;

import com.cisco.testdata.StaticData.CustomerActivity;
import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

import static com.cisco.testdata.StaticData.ButtonName.NEXT;
import static com.cisco.testdata.StaticData.ButtonName.SUBMIT;
import static org.testng.Assert.*;

public class OpenCasePage extends CaseUtils {

    public static final String SERIAL_NUMBER = "Serial Number";
    public static final String PRODUCT = "Product";
    public static final String NAME = "Name";
    public static final String CASE_TITLE = "Case Title";
    public static final String DESCRIPTION = "Description";
    public static final String DESCRIBE_THE_PROBLEM = "Describe the Problem";
    public static final String SEVERITY = "Severity";
    public static final String TECHNOLOGY = " Technology ";
    public static final String SUBTECH = "SubTech";
    public static final String PROBLEM_AREA = "Problem Area";
    public static final String REFRESH_SUGGESTIONS = "Refresh suggestions";
    public static final String SEE_ALL_OPTIONS = "See all options";
    public static final String DIAGNOSE_AND_FIX = "Diagnose and Fix";
    public static final String REQUEST_RMA = "Request RMA";
    public static final String ASK_A_QUESTION = "Ask a Question";
    public static final String ENTER_SERIAL_NUMBER = "Enter Serial Number";
    public static final String SELECT_A_PRODUCT = "Select a Product";

    /**
     * @return
     */
    @Step("Verify the Open Case window is visible")
    public Boolean verifyOpenCaseWindowOpen() {
        try {
            return getWebElement("//h2").getText().equalsIgnoreCase("Open a Case");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Verify the device details in Open Window page of label : {0} having value {1}")
    public void verifyDeviceDetails(String labelName, String value) {
        assertTrue(getWebElement("//div[text()='" + labelName + "']/following-sibling::div").getText().equalsIgnoreCase(value), labelName + " not matching");
    }

    @Step("Click the label:  {0}")
    public void clickLabel(String labelName) {
        getWebElement("//label[text()=' " + labelName + " ']").click();
    }

    @Step("Input the title in field " + CASE_TITLE + " : {0}")
    public void inputTitle(String title) {
        WebElement titleElement = getWebElement("//dt[text()='Case Title']//following::input[1]");
        titleElement.clear();
        titleElement.sendKeys(title);
    }

    @Step("Verify whether the field has error text displayed")
    public void verifyFieldError(String labelName) {
        String errorXpath = "//dt[text()='" + labelName + "']//following::span[contains(@class,'text-danger')][1]";
        String errorText = "";

        try {
            getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(errorXpath)));
            errorText = getWebElement(errorXpath).getText();
        } catch (NoSuchElementException | TimeoutException e) {
            fail("Could not find Error text");
        }
        if (labelName.equalsIgnoreCase(CASE_TITLE))
            assertTrue(errorText.equalsIgnoreCase("Title requires 5 or more characters."), "Error message not found");
        else if (labelName.equalsIgnoreCase(DESCRIBE_THE_PROBLEM))
            assertTrue(errorText.equalsIgnoreCase("Description requires 5 or more characters."));
        else
            throw new NoSuchElementException("Incorrect label name entered");
    }

    @Step("Input description in the field " + DESCRIBE_THE_PROBLEM + " : {0}")
    public void inputDescription(String description) {
        WebElement descriptionElement = getWebElement("//dt[text()='Describe the Problem']//following::textarea");
        descriptionElement.clear();
        descriptionElement.sendKeys(description);
    }

    @Step("Select the Severity as : " + SEVERITY + " {0}")
    public void selectSeverity(String severityNumber) {
        getWebElement("//dt[text()='" + SEVERITY + "']//following::span[text()='SEVERITY " + severityNumber + "']").click();
    }

    @Step("Select the dropdown with label name : {0} and value as : {1}")
    public void selectDropdown(String labelName, String dropdownValue) {
        //Security - Network Firewalls and Intrusion Prevention Systems
        //FWSM (Firewall Services Module)

        WebElement dropDownElement = getWebElement("//dt[text()='" + labelName + "']//following::input[1]");

        dropDownElement.click();
        dropDownElement.sendKeys(dropdownValue);
        getWebElement("//a[text()='" + dropdownValue + "']").click();
    }


    @Step("Select problem area from the Problem dropdown: {1}")
    public void selectProblemArea(CustomerActivity customerActivity, String problemArea) {
        String problemAreaXpath = "//dt[text()='Problem Area']//following::input[1]";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(problemAreaXpath)));
        getWebElement(problemAreaXpath).sendKeys(problemArea);
        getActions().moveToElement(getWebElement("//div[text()='" + customerActivity.getCustomerActivity() + "']/../following-sibling::div//a[text()='" + problemArea + "']"));
        getWebElement("//div[text()='" + customerActivity.getCustomerActivity() + "']/../following-sibling::div//a[text()='" + problemArea + "']").click();
    }

    @Step("Get Case number from the Case confirmation window")
    public String getCaseNumber() {
        String caseLinkXpath = "//a[@data-auto-id='CaseNumberLink']";
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(caseLinkXpath)));
        return getWebElement(caseLinkXpath).getText();
    }

    @Step("Click on the Case Number link ")
    public void clickCaseNumberLink() {
        String caseLinkXpath = "//a[@data-auto-id='CaseNumberLink']";
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(caseLinkXpath)));
        getWebElement(caseLinkXpath).click();
    }

    @Step("Click on Refresh Suggestions link")
    public void clickRefreshSuggestions() {
        getWebElement("//a[text()='" + REFRESH_SUGGESTIONS + "']").click();
    }

    @Step("Click on See All Options link")
    public void clickSeeAllOptions() {
        getWebElement("//a[text()='" + SEE_ALL_OPTIONS + "']").click();
    }

    @Step("Get the selected Severity on Open Case window")
    public String getSelectedSeverity() {
        return getWebElement("//div[contains(@class,'horizontal selected')]//div[contains(@class,'case')]").getText();
    }

    @Step("Verify whether Auto Suggestions have appeared or now")
    public void verifyAutoSuggestedTechnology() {
        assertTrue(getWebElement("//span[@class='subtitle']").getText().equalsIgnoreCase("(showing suggestions)"), "Not showing suggestions");
        assertTrue(getWebElement("//dt[text()=' Technology ']/..//a[@data-auto-id='PanelSelectOption0']/div[contains(@class,'selected')]").isEnabled(), "First option is auto selected");
        assertEquals(getWebElements("//dt[text()=' Technology ']/..//a/div").size(), 3, "3 auto suggestions not found");
    }

    @Step("Verify whether the Cancel Case Open Popup window is appearing in view")
    public Boolean verifyCancelCaseOpenPopupText() {
        try {
            return getWebElement("//app-close-confirm//h1").getText().equalsIgnoreCase("Cancel Opening Case?");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Add valid Case title, Case description, Customer Activity and problem area")
    public void inputDefaultCaseDetails(String caseTitle, String caseDescription, CustomerActivity customerActivity, String problemArea) {
        try {
            clickButton(NEXT);
            inputTitle(caseTitle);
            inputDescription(caseDescription);
            clickButton(NEXT);
            selectProblemArea(customerActivity, problemArea);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            fail("Case creation unsuccessful. Please check the screenshot for more details");
        }
    }

    @Step("Input default case details and click Submit")
    public String addDefaultCaseDetailsSubmit(String caseTitle, String caseDescription, CustomerActivity customerActivity, String problemArea) {
        try {
            inputDefaultCaseDetails(caseTitle, caseDescription, customerActivity, problemArea);
            clickButton(SUBMIT);
            return getCaseNumber();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            fail("Case creation unsuccessful. Please check the screenshot for more details");
        }
        return null;
    }

    @Step("Get the value for the label {0} present in the Case Confirmation screen")
    public String getCaseConfirmationText(String label) {
        return getWebElement("//dt[text()='" + label + "']/following::span[1]").getText();
    }

    @Step(" Validate all the values present in the Case confirmation screen are proper")
    public void verifyCaseConfirmationScreen(String serialNumber, String product, String name, String caseTitle, String caseDescription, String severity, CustomerActivity customerActivity, String problemCode) {
        SoftAssert softAssert = new SoftAssert();
        String problemArea;

        softAssert.assertTrue(getWebElement("//span[contains(text(),'Case Opened')]").isDisplayed(), "Case status is not displayed properly");
        softAssert.assertEquals(getWebElement("//div[text()='" + SERIAL_NUMBER + "']/following-sibling::div").getText(), serialNumber, "Serial number not matching");
        softAssert.assertEquals(getWebElement("//div[text()='" + PRODUCT + "']/following-sibling::div").getText(), product, "Host Name not matching");
        softAssert.assertEquals(getWebElement("//div[text()='" + NAME + "']/following-sibling::div").getText(), name, "Model not matching");
        softAssert.assertEquals(getCaseConfirmationText(CASE_TITLE), caseTitle, "Case Title not matching");
        softAssert.assertEquals(getCaseConfirmationText(DESCRIPTION), caseDescription, "Case Description not matching");
        softAssert.assertTrue(getWebElement("//dt[text()='" + SEVERITY + "']/following::dd[1]").getText().contains(severity), "Severity not matching");
        problemArea = getCaseConfirmationText(PROBLEM_AREA);
        softAssert.assertTrue(problemArea.contains(customerActivity.getCustomerActivity()), "Customer Activity not matching");
        softAssert.assertTrue(problemArea.contains(problemCode), "Problem Code not matching");

        softAssert.assertAll("One of the assert condition failed. Please check");
    }

}
