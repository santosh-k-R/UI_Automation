package com.cisco.tests.cases;

import com.cisco.base.DriverBase;
import com.cisco.pages.assetsAndCoverage.assets.Assets360Page;
import com.cisco.pages.assetsAndCoverage.assets.AssetsPage;
import com.cisco.pages.cases.Case360Page;
import com.cisco.pages.cases.CasesPage;
import com.cisco.pages.cases.OpenCasePage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.CustomerActivity;
import com.cisco.utils.Commons;
import com.cisco.utils.RestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.cisco.pages.cases.Case360Page.*;
import static com.cisco.testdata.Data.CASES_COMMON_DATA;
import static com.cisco.testdata.Data.CASES_TESTCASE_DATA;
import static com.cisco.testdata.StaticData.ButtonName.*;
import static com.cisco.testdata.StaticData.CustomerActivity.CONFIGURATION;
import static org.testng.Assert.*;

@Feature("Case 360 page")
public class Case360PageIT extends DriverBase {

    private static String caseNumber;
    private final String trackName = CASES_COMMON_DATA.get("TRACK");
    private final String smartAccount = CASES_COMMON_DATA.get("SMART_ACCOUNT");
    private final String serialNumber = CASES_TESTCASE_DATA.get("SERIAL_NUMBER");
    private final String caseTitle = CASES_COMMON_DATA.get("CASE_TITLE") + " " + Commons.generateRandomNumber();
    private final String caseDescription = CASES_COMMON_DATA.get("DESCRIPTION");
    private final String problemCode = CASES_COMMON_DATA.get("Problem_Code");

    @BeforeClass(description = "Login to portal and create a Case")
    public void loginAndCreateCase() {
        Case360Page case360Page = new Case360Page();
        OpenCasePage openCasePage = new OpenCasePage();
        AssetsPage assetsPage = new AssetsPage();

        case360Page.login(trackName);
        case360Page.switchCXCloudAccount(smartAccount);
        case360Page.resetContextSelector();

        assetsPage.searchAssetViaSerialNumber(serialNumber + Keys.ENTER);
        openCasePage.openRowOption(serialNumber, AssetsPage.OPEN_SUPPORT_CASE_OPTION);
        caseNumber = openCasePage.addDefaultCaseDetailsSubmit(caseTitle, caseDescription, CONFIGURATION, problemCode);
        openCasePage.clickButton(DONE);
    }

    @AfterClass
    public void closeBrowserInstance() {
        closeDriverInstance();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify all details displayed on the Case 360 view", priority = 1)
    public void verifyAllDetailsInCase360View() {
        Case360Page case360Page = new Case360Page();
        CasesPage casesPage = new CasesPage();
        SoftAssert softAssert = new SoftAssert();

        Response response = RestUtils.getCaseData(caseNumber);
        JsonPath responseBody = response.getBody().jsonPath();

        case360Page.login(trackName);
        case360Page.resetContextSelector();
        case360Page.selectCarousel(CarouselName.CASES);

        casesPage.searchCase(caseNumber);
        case360Page.open360View(caseNumber);

        softAssert.assertTrue(case360Page.verifyCaseHeader(caseNumber), "Case number displayed on 360 view is incorrect");
        case360Page.verifySeverityDetails("S3");
        softAssert.assertTrue(case360Page.verifyExpectedValues(STATUS_LABEL, responseBody.get("responseDetails.caseDetail.status")),
                "Status value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(CREATED_LABEL, responseBody.get("responseDetails.caseDetail.createdDate")),
                "Created value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(UPDATED_LABEL, responseBody.get("responseDetails.caseDetail.lastUpdateDate")),
                "Updated value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(PROBLEM_TYPE_LABEL, responseBody.getString("responseDetails.caseDetail.customerActivity")),
                "Customer Activity value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(PROBLEM_TYPE_LABEL, responseBody.getString("responseDetails.caseDetail.problemCodeName")),
                "Problem Code value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(ASSET_LABEL, responseBody.getString("responseDetails.caseDetail.deviceName")),
                "System name value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(SOFTWARE_RELEASE_LABEL, responseBody.getString("responseDetails.caseDetail.softwareVersion")),
                "Software Release value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(CONTRACT_NUMBER_LABEL, responseBody.getString("responseDetails.caseDetail.contractId")),
                "Contract Number value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(TRACKING_NUMBER_LABEL, responseBody.getString("responseDetails.caseDetail.trackingNumber")),
                "Tracking Number value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(CASE_OWNER_LABEL, responseBody.getString("responseDetails.caseDetail.contactEmail")),
                "Case Owner value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(TAC_ENGINEER_LABEL, responseBody.getString("responseDetails.caseDetail.ownerEmail")),
                "TAC Engineer value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(TITLE_LABEL, responseBody.getString("responseDetails.caseDetail.summary")),
                "Case Title value does not match with api");
        softAssert.assertTrue(case360Page.verifyExpectedValues(DESCRIPTION_LABEL, responseBody.getString("responseDetails.caseDetail.description")),
                "Case Description value does not match with api");
        softAssert.assertAll();
        case360Page.close360View();

    }

    @Test(description = "Verify the Attach File window capability", priority = 2)
    public void verifyAttachFileWindow() {
        Case360Page case360Page = new Case360Page();
        CasesPage casesPage = new CasesPage();

        case360Page.login(trackName);
        case360Page.resetContextSelector();

        casesPage.searchCase(caseNumber);
        case360Page.open360View(caseNumber);
        case360Page.clickButton(ATTACH_FILE);
        assertTrue(case360Page.verifyAttachFileWindowOpen(), "Attach file popup is not open after click Attach File button");
        case360Page.clickAttachFileButton(CANCEL);
        assertFalse(case360Page.verifyAttachFileWindowOpen(), "Attach file popup is open even after clicking Cancel button");
        case360Page.selectTab(FILES_TAB);
        assertTrue(case360Page.verifyNoFilesTextInFilesTab(), "Some files are attached to the case/ No files text is not proper");
        assertEquals(case360Page.getCountOfFiles_Notes(FILES_TAB), case360Page.getActualCountOfFiles(), "Count of files on tab does not match with the count of actual files");
        case360Page.close360View();
    }

    /**
     * Cannot run this code from Jenkins/ Selenium box as the file to be attached cannot be sent to SeleniumBox env.
     *//*
    @Test(enabled = false, description = "Verify the Attach file functionality in Case 360 view", priority = 3)
    public void verifyAttachFileFunctionality() {
        Case360Page case360Page = new Case360Page();
        CasesPage casesPage = new CasesPage();

        case360Page.login(trackName);
        casesPage.searchCase(caseNumber);
        case360Page.open360View(caseNumber);
        case360Page.clickButton(ATTACH_FILE);
        assertTrue(case360Page.verifyAttachFileWindowOpen(), "Attach file popup is not open after click Attach File button");
        case360Page.attachFile();
        case360Page.enterDescription("Test description to attach file");
        case360Page.clickAttachFileButton(ATTACH);
        case360Page.waitTillButtonVisible(DONE);
        case360Page.clickAttachFileButton(DONE);
        assertTrue(case360Page.verifyCaseHeader(caseNumber), "Case number displayed on 360 view is incorrect");
    }*/
    @Test(description = "Verify the Add notes functionality in Case 360 view", priority = 4)
    public void verifyAddNotesFunctionality() {
        Case360Page case360Page = new Case360Page();
        CasesPage casesPage = new CasesPage();
        int beforeCount, afterCount;

        case360Page.login(trackName);
        case360Page.resetContextSelector();

        casesPage.searchCase(caseNumber);
        case360Page.open360View(caseNumber);
        case360Page.selectTab(Case360Page.NOTES_TAB);
        beforeCount = case360Page.getCountOfFiles_Notes(Case360Page.NOTES_TAB);
        assertEquals(beforeCount, case360Page.getActualCountOfNotes(), "Count of notes on tab does not match with the count of actual notes");
        case360Page.clickButton(ADD_NOTE);
        case360Page.clickButton(CANCEL);
        case360Page.clickButton(ADD_NOTE);
        assertFalse(case360Page.isButtonEnabled(ADD), "Add button is enabled");
        case360Page.enterTextArea(Case360Page.TITLE, caseTitle);
        assertFalse(case360Page.isButtonEnabled(ADD), "Add button is enabled by entering just Title");
        case360Page.enterTextArea(Case360Page.DESCRIPTION, caseDescription);
        assertTrue(case360Page.isButtonEnabled(ADD), "Add button is disabled even after entering Title and description");
        case360Page.clickButton(ADD);
        assertTrue(case360Page.verifyCaseNotesText(caseTitle));
        assertTrue(case360Page.verifyCaseNotesText(caseDescription));
        afterCount = case360Page.getCountOfFiles_Notes(Case360Page.NOTES_TAB);
        assertEquals(afterCount, case360Page.getActualCountOfNotes(), "Count of notes on tab does not match with the count of actual notes");
        case360Page.close360View();
    }

    @Test(description = "Open a new Case from the Asset hyperlink present in Case 360 view", priority = 5)
    public void openCaseViaAssetHyperlinkOfCase360() {
        Case360Page case360Page = new Case360Page();
        CasesPage casesPage = new CasesPage();
        OpenCasePage openCasePage = new OpenCasePage();

        String caseTitleText = caseTitle + Commons.generateRandomNumber();

        case360Page.login(trackName);
        case360Page.resetContextSelector();
        casesPage.searchCase(caseNumber);
        case360Page.open360View(caseNumber);
        case360Page.clickAssetHyperlink();
        openCasePage.selectTab(AssetsPage.HARDWARE_TAB);
        new Assets360Page().clickOpenCaseButton();
        caseNumber = openCasePage.addDefaultCaseDetailsSubmit(caseTitleText, caseDescription, CustomerActivity.OPERATE, problemCode);
        openCasePage.clickCaseNumberLink();
        assertTrue(new Case360Page().verifyCaseHeader(caseNumber), "Case number displayed on 360 view is incorrect");

    }

}
