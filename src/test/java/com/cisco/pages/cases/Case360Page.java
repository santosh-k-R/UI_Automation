package com.cisco.pages.cases;

import com.cisco.testdata.Data;
import com.cisco.testdata.StaticData.ButtonName;
import com.cisco.testdata.StaticData.SeverityIcon;
import com.cisco.testdata.StaticData.SeverityRequestType;
import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

public class Case360Page extends CaseUtils {

    public static final String SUMMARY_TAB = "SUMMARY";
    public static final String NOTES_TAB = "NOTES";
    public static final String FILES_TAB = "FILES";
    public static final String TITLE = "Title";
    public static final String DESCRIPTION = "Description";
    public static final String SEVERITY_LABEL = "SEVERITY";
    public static final String STATUS_LABEL = "STATUS";
    public static final String CREATED_LABEL = "CREATED";
    public static final String UPDATED_LABEL = "UPDATED";
    public static final String PROBLEM_TYPE_LABEL = "PROBLEM TYPE";
    public static final String ASSET_LABEL = "ASSET";
    public static final String SOFTWARE_RELEASE_LABEL = "SOFTWARE RELEASE";
    public static final String CONTRACT_NUMBER_LABEL = "CONTRACT NUMBER";
    public static final String TRACKING_NUMBER_LABEL = "TRACKING NUMBER";
    public static final String CASE_OWNER_LABEL = "CASE OWNER";
    public static final String TAC_ENGINEER_LABEL = "TAC ENGINEER";
    public static final String TITLE_LABEL = "TITLE";
    public static final String DESCRIPTION_LABEL = "DESCRIPTION";

    @Step("Verify whether the Case 360 view contains the Case Number {0}")
    public Boolean verifyCaseHeader(String caseNumber) {
        return getWebElement("//div[@class='text-xlarge']").getText().equalsIgnoreCase("Case " + caseNumber);
    }

    @Step("Input in the text area of {0} the text - {1}")
    public AppUtils enterTextArea(String labelName, String inputText) {
        WebElement element = getWebElement("//label[text()='" + labelName + "']/../textarea");
        element.clear();
        element.sendKeys(inputText);
        return this;
    }

    @Step("Verify the Case note text in the first block of notes. -- {0}")
    public Boolean verifyCaseNotesText(String verificationText) {
        String caseNotesText = getWebElement("//app-case-notes/div/div[1]//div[contains(@class,'timeline__item')]").getText();
        return caseNotesText.contains(verificationText);
    }

    @Step("Verify the Severity icon, code and Severity text for the case")
    public void verifySeverityDetails(String severityCode) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getWebElement("//details-panel//*[text()='SEVERITY']/..//span[@data-auto-id='CaseDetailsSeverityIcon']").getAttribute("class")
                .contains(SeverityIcon.valueOf(severityCode).getSeverityIcon()), "Icon does not match with the Severity value");
        softAssert.assertEquals(getWebElement("//details-panel//*[text()='SEVERITY']/..//span[@data-auto-id='CaseDetailsSeverity']").getText(), severityCode, "Severity code does not match");
        softAssert.assertEquals(getWebElement("//details-panel//*[text()='SEVERITY']/..//span[@data-auto-id='CaseDetailsRequestType']").getText(),
                SeverityRequestType.valueOf(severityCode).getSeverityRequestType(), "Severity Request Type does not match");
        softAssert.assertAll();
    }

    @Step("Verify if the actual value in UI under label {0} contains the expected value from api {1}")
    public Boolean verifyExpectedValues(String labelName, String expectedValue) {
        String actualValue = getWebElement("//details-panel//*[text()='" + labelName + "']/following::div[1]").getText();
        if (actualValue.contains("Unavailable") && expectedValue.isEmpty())
            return true;
        if (actualValue.contains("not yet assigned") && expectedValue.isEmpty())
            return true;
        if (labelName.equalsIgnoreCase(CREATED_LABEL) || labelName.equalsIgnoreCase(UPDATED_LABEL)) {
            return (!actualValue.equalsIgnoreCase("Never") && !actualValue.isEmpty());
        }

        return actualValue.contains(expectedValue);
    }

    @Step("Verify if the Attach file Window is open or not")
    public Boolean verifyAttachFileWindowOpen() {
        try {
            return getWebElement("//*[@data-auto-id='CSC-UploadFilesDialogTitle']").getText().contains("Attach Files");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Attach a file to the case")
    public void attachFile() {
        try {
            getWebElement("//div[@class='not-close-360']//cui-dropzone//input[@type='file']").sendKeys(Data.DIR + "Attach_file.png");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            fail("Unable to attach the file to the case");
        }
    }

    @Step("Enter the description for file attach: {0}")
    public void enterDescription(String description) {
        WebElement textAreaElement;
        try {
            textAreaElement = getWebElement("//textarea[@data-auto-id='CSC-FileDescription']");
            textAreaElement.clear();
            textAreaElement.sendKeys(description);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            fail("Unable to find Description text area");
        }
    }

    @Step("Click the Button on Attach File popup window: {0}")
    public void clickAttachFileButton(ButtonName buttonName) {
        try {
            getWebElement("(//div[contains(@class,'not-close-360')]//*[contains(@class,'btn')][text()='" + buttonName.getButtonName() + "'])[2]").click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            fail("Unable to click button " + buttonName.getButtonName());
        }
    }

    @Step("Get the count on the tab name of {0}")
    public int getCountOfFiles_Notes(String label) {
        String textOfTab;
        try {
            textOfTab = getWebElement("//div[@class='tab__heading']/span[contains(text(),'" + label + "')]").getText();
            textOfTab = textOfTab.split(" ")[1];
            textOfTab = textOfTab.replaceAll("[()]", "");
            return Integer.parseInt(textOfTab);
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return 0;
        }
    }

    @Step("Get the actual number of Notes present in the Case 360 view")
    public int getActualCountOfNotes() {
        try {
            return getWebElements("//app-case-notes/div/div//div[contains(@class,'timeline__item')]").size();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    @Step("Get the actual number of file added to the Case")
    public int getActualCountOfFiles() {
        try {
            return getWebElements("//table//td/span[@data-auto-id='CaseFileName']").size();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    @Step("Verify No files text is displayed if there are no files attached to a case")
    public Boolean verifyNoFilesTextInFilesTab() {
        String noFilesText;
        try {
            if (getCountOfFiles_Notes(FILES_TAB) == 0) {
                noFilesText = getWebElement("//app-case-files/div").getText();
                return noFilesText.contains("No Attached Files Found") && noFilesText.contains("All files related to this case will be listed here");
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
