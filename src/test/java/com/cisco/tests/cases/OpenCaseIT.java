package com.cisco.tests.cases;

import com.cisco.base.DriverBase;
import com.cisco.pages.assetsAndCoverage.assets.AssetsPage;
import com.cisco.pages.cases.Case360Page;
import com.cisco.pages.cases.CasesPage;
import com.cisco.pages.cases.OpenCasePage;
import com.cisco.testdata.StaticData.CustomerActivity;
import com.cisco.utils.Commons;
import com.cisco.utils.RestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cisco.pages.cases.CasesPage.OPEN_CASES;
import static com.cisco.pages.cases.OpenCasePage.*;
import static com.cisco.testdata.Data.CASES_COMMON_DATA;
import static com.cisco.testdata.Data.CASES_TESTCASE_DATA;
import static com.cisco.testdata.StaticData.ButtonName.*;
import static com.cisco.testdata.StaticData.CarouselName.CASES;
import static io.qameta.allure.SeverityLevel.*;
import static org.testng.Assert.*;

@Feature("Open Case")
public class OpenCaseIT extends DriverBase {

    private final String trackName = CASES_COMMON_DATA.get("TRACK");
    private final String smartAccount = CASES_COMMON_DATA.get("SMART_ACCOUNT");
    private final String serialNumber = CASES_TESTCASE_DATA.get("SERIAL_NUMBER");
    private final String product = CASES_TESTCASE_DATA.get("PRODUCT");
    private final String name = CASES_TESTCASE_DATA.get("NAME");
    private final String caseTitle = CASES_COMMON_DATA.get("CASE_TITLE");
    private final String caseDescription = CASES_COMMON_DATA.get("DESCRIPTION");
    private final String problemCode = CASES_COMMON_DATA.get("Problem_Code");
    private final String totalCountExtractString = CASES_COMMON_DATA.get("TOTAL_RECORD_COUNT");
    private final String defaultSeverity = "Severity 3";

    @BeforeClass(description = "Login to portal and Select SUCCESS TRACK and USE CASE")
    public void login() {
        OpenCasePage openCasePage = new OpenCasePage();

        openCasePage.login(trackName);
        openCasePage.switchCXCloudAccount(smartAccount);
        openCasePage.resetContextSelector();
    }

    @AfterClass
    public void closeBrowserInstance() {
        closeDriverInstance();
    }

    @Severity(BLOCKER)
    @Test(description = "Open a case from Hardware via 3 dots option")
    public void openCaseFromHardwareVia3DotsOption() {
        OpenCasePage openCasePage = new OpenCasePage();
        AssetsPage assetsPage = new AssetsPage();
        CasesPage casesPage = new CasesPage();
        Case360Page case360Page = new Case360Page();
        String caseTitleText = caseTitle + " " + Commons.generateRandomNumber();
        int beforeCount, afterCount;
        String caseNumber, apiOpenCaseCount;

        openCasePage.login(trackName);
        openCasePage.resetContextSelector();
        beforeCount = Integer.parseInt(casesPage.getCountOnPR(OPEN_CASES));
        assetsPage.searchAssetViaSerialNumber(serialNumber);
        openCasePage.openRowOption(serialNumber, AssetsPage.OPEN_SUPPORT_CASE_OPTION);
        caseNumber = openCasePage.addDefaultCaseDetailsSubmit(caseTitleText, caseDescription, CustomerActivity.CONFIGURATION, problemCode);
        openCasePage.verifyCaseConfirmationScreen(serialNumber, product, name, caseTitleText, caseDescription, defaultSeverity, CustomerActivity.CONFIGURATION, problemCode);
        openCasePage.clickButton(DONE);

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("caseNumbers~" + caseNumber, totalCountExtractString);

        casesPage.selectCarousel(CASES);
        afterCount = Integer.parseInt(casesPage.getCountOnPR(OPEN_CASES));
        assertTrue(afterCount > beforeCount, "Count on Problem resolution Carousel has not increased");
        casesPage.searchCase(caseNumber);
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for respective search filtering");
        casesPage.open360View(caseNumber);
        assertTrue(case360Page.verifyCaseHeader(caseNumber), "Case number displayed on 360 view is incorrect");
        casesPage.close360View();

        casesPage.clearSearchCase();
    }

    @Severity(BLOCKER)
    @Test(description = "Open a case from Asset 360 Page")
    public void openCaseFromAsset360() {
        OpenCasePage openCasePage = new OpenCasePage();
        AssetsPage assetsPage = new AssetsPage();
        Case360Page case360Page = new Case360Page();
        String caseTitleText = caseTitle + " " + Commons.generateRandomNumber();
        String caseNumber;

        openCasePage.login(trackName);
        openCasePage.resetContextSelector();
        assetsPage.searchOpenAssetViaSerialNumber(serialNumber);
        assetsPage.selectTab(AssetsPage.HARDWARE_TAB);
        openCasePage.waitTillButtonVisible(OPEN_A_CASE);
        openCasePage.clickButton(OPEN_A_CASE);
        caseNumber = openCasePage.addDefaultCaseDetailsSubmit(caseTitleText, caseDescription, CustomerActivity.OPERATE, problemCode);
        openCasePage.verifyCaseConfirmationScreen(serialNumber, product, name, caseTitleText, caseDescription, defaultSeverity, CustomerActivity.OPERATE, problemCode);
        openCasePage.clickCaseNumberLink();
        assertTrue(case360Page.verifyCaseHeader(caseNumber), "Case number displayed on 360 view is incorrect");
    }

    @Severity(NORMAL)
    @Test(description = "Verify the Details in the Open Case window")
    public void verifyCaseOpenWindowDetails() {
        OpenCasePage openCasePage = new OpenCasePage();
        AssetsPage assetsPage = new AssetsPage();

        assetsPage.searchAssetViaSerialNumber(serialNumber);
        openCasePage.openRowOption(serialNumber, AssetsPage.OPEN_SUPPORT_CASE_OPTION);
        assertTrue(openCasePage.verifyOpenCaseWindowOpen(), "Open Case Window not visible");
        openCasePage.verifyDeviceDetails(SERIAL_NUMBER, serialNumber);
        openCasePage.verifyDeviceDetails(PRODUCT, product);
        openCasePage.verifyDeviceDetails(NAME, name);
        openCasePage.clickLabel(ASK_A_QUESTION);
        openCasePage.clickLabel(DIAGNOSE_AND_FIX);
        openCasePage.clickLabel(REQUEST_RMA);
        openCasePage.clickButton(NEXT);
    }

    @Severity(CRITICAL)
    @Test(dependsOnMethods = {"verifyCaseOpenWindowDetails"}, description = "Perform field validations for Case title and Description fields")
    public void verifyCaseOpenWindowAllFields() {
        OpenCasePage openCasePage = new OpenCasePage();

        assertFalse(openCasePage.isButtonEnabled(NEXT), "Next button is enabled");
        openCasePage.inputTitle("Char");
        openCasePage.verifyFieldError(CASE_TITLE);
        openCasePage.inputDescription("Desc");
        openCasePage.verifyFieldError(DESCRIBE_THE_PROBLEM);
        openCasePage.inputTitle(caseTitle);
        openCasePage.inputDescription(caseDescription);
        assertTrue(openCasePage.isButtonEnabled(NEXT), "Next button is not enabled");
        openCasePage.clickButton(NEXT);
        assertFalse(openCasePage.isButtonEnabled(SUBMIT), "Submit button is enabled");
        assertTrue(openCasePage.getSelectedSeverity().equalsIgnoreCase("SEVERITY 3"), "Did not auto select severity 3");
        openCasePage.selectProblemArea(CustomerActivity.INSTALLATION, problemCode);
        assertTrue(openCasePage.isButtonEnabled(SUBMIT), "Submit button is not enabled");

    }

    @Severity(MINOR)
    @Test(dependsOnMethods = {"verifyCaseOpenWindowAllFields"}, description = "Verify Cancel Case open functionality")
    public void verifyCancelCaseOperation() {
        OpenCasePage openCasePage = new OpenCasePage();

        openCasePage.clickButton(CANCEL);
        assertTrue(openCasePage.verifyCancelCaseOpenPopupText(), "Cancel Case popup window not visible");
        openCasePage.clickButton(CONTINUE_OPENING_CASE);
        assertTrue(openCasePage.verifyOpenCaseWindowOpen(), "Open Case Window not visible");
        openCasePage.clickButton(CANCEL);
        assertTrue(openCasePage.verifyCancelCaseOpenPopupText(), "Cancel Case popup window not visible");
        openCasePage.clickButton(CANCEL_OPENING_CASE);
        assertFalse(openCasePage.verifyOpenCaseWindowOpen(), "Open case window still visible");
    }

    /*@Test(description = "Perform Field validations on Open Case from Cold case workflow", priority = 6)
    public void verifyColdCaseWindow() {
        OpenCasePage openCasePage = new OpenCasePage();


        openCasePage.login(trackName);
        openCasePage.resetContextSelector();

        openCasePage.clickButton(OPEN_A_CASE);
        assertTrue(openCasePage.verifyOpenCaseWindowOpen(),"Open Case window not opened");
        openCasePage.clickLabel(REQUEST_RMA);
        openCasePage.clickLabel(ASK_A_QUESTION);
        openCasePage.clickLabel(DIAGNOSE_AND_FIX);
        openCasePage.clickLabel(SELECT_A_PRODUCT);


    }*/

}

