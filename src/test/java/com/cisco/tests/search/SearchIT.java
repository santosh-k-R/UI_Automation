package com.cisco.tests.search;

import com.cisco.base.DriverBase;
import com.cisco.pages.assetsAndCoverage.assets.Assets360Page;
import com.cisco.pages.assetsAndCoverage.assets.AssetsPage;
import com.cisco.pages.cases.Case360Page;
import com.cisco.pages.cases.CasesPage;
import com.cisco.pages.cases.OpenCasePage;
import com.cisco.pages.search.SearchPage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.CustomerActivity;
import com.cisco.utils.Commons;
import com.cisco.utils.RestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.cisco.pages.search.SearchPage.*;
import static com.cisco.testdata.Data.CASES_COMMON_DATA;
import static com.cisco.testdata.Data.CASES_TESTCASE_DATA;
import static com.cisco.testdata.StaticData.ButtonName.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Feature("Global Search")
public class SearchIT extends DriverBase {
    private final String trackName = CASES_COMMON_DATA.get("TRACK");
    private final String smartAccount = CASES_COMMON_DATA.get("SMART_ACCOUNT");
    private final String totalCountExtractString = CASES_COMMON_DATA.get("TOTAL_RECORD_COUNT");
    private final String searchHostName = CASES_TESTCASE_DATA.get("SEARCH_HOSTNAME");
    private final String openCaseSerialNumber = CASES_TESTCASE_DATA.get("SERIAL_NUMBER");
    private final String searchSerialNumber = CASES_TESTCASE_DATA.get("SEARCH_SN");
    private final String searchIpAddress = CASES_TESTCASE_DATA.get("SEARCH_IP_ADDRESS");
    private final String searchProduct = CASES_TESTCASE_DATA.get("SEARCH_PRODUCT");
    private final String searchSoftwareType = CASES_TESTCASE_DATA.get("SEARCH_SOFTWARE_TYPE");
    private final String searchCurrentRelease = CASES_TESTCASE_DATA.get("SEARCH_CURRENT_RELEASE");
    private final String searchProductId = CASES_TESTCASE_DATA.get("SEARCH_PRODUCT_ID");
    private final String caseTitle = CASES_COMMON_DATA.get("CASE_TITLE");
    private final String caseDescription = CASES_COMMON_DATA.get("DESCRIPTION");
    private final String problemCode = CASES_COMMON_DATA.get("Problem_Code");
    private final String uncoveredAsset = CASES_TESTCASE_DATA.get("UNCOVERED_ASSET");
    private final String searchContract = CASES_TESTCASE_DATA.get("CONTRACT_NUMBER");

    private String caseNumber;

    @BeforeMethod(description = "Login to portal")
    public void login() {
        SearchPage searchPage = new SearchPage();

        searchPage.login(trackName);
        searchPage.switchCXCloudAccount(smartAccount);
        searchPage.resetContextSelector();

    }

    @AfterClass
    public void closeBrowserInstance() {
        closeDriverInstance();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify Asset details are proper when searched by Hostname", priority = 1)
    public void verifyHostnameSearch() {
        SearchPage searchPage = new SearchPage();
        Assets360Page assets360Page = new Assets360Page();
        SoftAssert softAssert = new SoftAssert();

        searchPage.performSearch(searchHostName);
        softAssert.assertTrue(searchPage.getSearchHeader().equalsIgnoreCase("Host Name " + searchHostName), "Hostname not searched properly");
        softAssert.assertEquals(searchPage.getFieldValue(PRODUCT), searchProduct, "Product not matched");
        softAssert.assertEquals(searchPage.getFieldValue(SOFTWARE_TYPE), searchSoftwareType, "Software Type not matched");
        softAssert.assertEquals(searchPage.getFieldValue(CURRENT_RELEASE), searchCurrentRelease, "Current Release not matched");
        softAssert.assertEquals(searchPage.getFieldValue(PRODUCT_ID), searchProductId, "Product ID not matched");
        softAssert.assertEquals(searchPage.getFieldValue(SERIAL_NUMBER), searchSerialNumber, "Serial Number not matched");
        softAssert.assertEquals(searchPage.getFieldValue(IP_ADDRESS), searchIpAddress, "IP Address not matched");
        softAssert.assertEquals(searchPage.getFieldValue(HOST_NAME), searchHostName, "HostName not matched");

        searchPage.clickButton(VIEW_DEVICE_DETAILS);
        softAssert.assertEquals(assets360Page.getAssetHeader(), searchHostName, "Asset 360 view not opened");
        searchPage.close360View();
        softAssert.assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify Asset details are proper when searched by Serial Number", priority = 2)
    public void verifySerialNumberSearch() {
        SearchPage searchPage = new SearchPage();
        Assets360Page assets360Page = new Assets360Page();
        SoftAssert softAssert = new SoftAssert();

        searchPage.performSearch(searchSerialNumber);
        softAssert.assertTrue(searchPage.getSearchHeader().equalsIgnoreCase("Serial Number " + searchSerialNumber), "Serial Number not searched properly");
        softAssert.assertEquals(searchPage.getFieldValue(PRODUCT), searchProduct, "Product not matched");
        softAssert.assertEquals(searchPage.getFieldValue(SOFTWARE_TYPE), searchSoftwareType, "Software Type not matched");
        softAssert.assertEquals(searchPage.getFieldValue(CURRENT_RELEASE), searchCurrentRelease, "Current Release not matched");
        softAssert.assertEquals(searchPage.getFieldValue(PRODUCT_ID), searchProductId, "Product ID not matched");
        softAssert.assertEquals(searchPage.getFieldValue(SERIAL_NUMBER), searchSerialNumber, "Serial Number not matched");
        softAssert.assertEquals(searchPage.getFieldValue(IP_ADDRESS), searchIpAddress, "IP Address not matched");
        softAssert.assertEquals(searchPage.getFieldValue(HOST_NAME), searchHostName, "HostName not matched");

        searchPage.clickButton(VIEW_DEVICE_DETAILS);
        softAssert.assertEquals(assets360Page.getAssetHeader(), searchHostName, "Asset 360 view not opened");
        searchPage.close360View();
        softAssert.assertAll();

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify Asset details are proper when searched by IP Address", priority = 3)
    public void verifyIpAddressSearch() {
        SearchPage searchPage = new SearchPage();
        Assets360Page assets360Page = new Assets360Page();
        SoftAssert softAssert = new SoftAssert();

        searchPage.performSearch(searchIpAddress);
        softAssert.assertTrue(searchPage.getSearchHeader().equalsIgnoreCase("IP Address " + searchIpAddress), "Serial Number not searched properly");
        softAssert.assertEquals(searchPage.getFieldValue(PRODUCT), searchProduct, "Product not matched");
        softAssert.assertEquals(searchPage.getFieldValue(SOFTWARE_TYPE), searchSoftwareType, "Software Type not matched");
        softAssert.assertEquals(searchPage.getFieldValue(CURRENT_RELEASE), searchCurrentRelease, "Current Release not matched");
        softAssert.assertEquals(searchPage.getFieldValue(PRODUCT_ID), searchProductId, "Product ID not matched");
        softAssert.assertEquals(searchPage.getFieldValue(SERIAL_NUMBER), searchSerialNumber, "Serial Number not matched");
        softAssert.assertEquals(searchPage.getFieldValue(IP_ADDRESS), searchIpAddress, "IP Address not matched");
        softAssert.assertEquals(searchPage.getFieldValue(HOST_NAME), searchHostName, "HostName not matched");

        searchPage.clickButton(VIEW_DEVICE_DETAILS);
        softAssert.assertEquals(assets360Page.getAssetHeader(), searchHostName, "Asset 360 view not opened");
        searchPage.close360View();
        softAssert.assertAll();
    }

    @Test(description = "Verify the Open Case button should not be present for an Uncovered Asset", priority = 4)
    public void verifyNoOpenCaseButton() {
        SearchPage searchPage = new SearchPage();
        SoftAssert softAssert = new SoftAssert();

        searchPage.performSearch(uncoveredAsset);
        softAssert.assertTrue(searchPage.getSearchHeader().equalsIgnoreCase("Serial Number " + uncoveredAsset), "Serial Number not searched properly");
        softAssert.assertFalse(searchPage.validateOpenCaseButtonAvailable(), "Open Case button is available for an Uncovered asset");

        searchPage.closeSearchResult();
        softAssert.assertAll();
    }

    @Test(description = "Verify the Case counts and Vulnerabilities data for an Asset", priority = 5)
    public void verifyAssetCaseAndVulnerabilitiesData() {
        SearchPage searchPage = new SearchPage();
        SoftAssert softAssert = new SoftAssert();

        String apiOpenCaseCount, rmaCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~T;serialNumbers~" + openCaseSerialNumber, totalCountExtractString);
        rmaCaseCount = (String) RestUtils.getCaseListData("allCases~T;hasRMAs~T;serialNumbers~" + openCaseSerialNumber, totalCountExtractString);

        searchPage.performSearch(openCaseSerialNumber);
        softAssert.assertTrue(searchPage.getSearchHeader().equalsIgnoreCase("Serial Number " + openCaseSerialNumber), "Serial Number not searched properly");
        softAssert.assertEquals(searchPage.getFieldValue(OPEN_CASES), apiOpenCaseCount, "Count of open Cases not matched");
        softAssert.assertEquals(searchPage.getFieldValue(OPEN_RMAS), rmaCaseCount, "Count of open Cases with RMAs not matched");
        softAssert.assertEquals(searchPage.getFieldValue(RELATED_FIELD_NOTICES), "1", "Count of Related Field notices not matched");
        softAssert.assertEquals(searchPage.getFieldValue(RELATED_SECURITY_ADVISORIES), "0", "Count of Related Security Advisories not matched");
        softAssert.assertEquals(searchPage.getFieldValue(RELATED_BUGS), "0", "Count of Related Bugs not matched");

        searchPage.closeSearchResult();
        softAssert.assertAll();
    }

    /**
     * Contract data not available in QA environment. Till its available, commenting this case.
     */
    /*@Test(description = "Verify the contract search details are properly displayed", priority = 6)
    public void verifyContractSearchDetails() {
        SearchPage searchPage = new SearchPage();
        Contract360Page contract360Page = new Contract360Page();
        SoftAssert softAssert = new SoftAssert();

        searchPage.performSearch(searchContract);
        System.out.println(searchContract + " is the contract number entered");
        softAssert.assertTrue(searchPage.getStatusValue().equalsIgnoreCase("active"), "Status not matching");
        softAssert.assertTrue(searchPage.getContractHeader().equalsIgnoreCase("Contract " + searchContract), "Contract Number not matching");
        softAssert.assertTrue(searchPage.getFieldValue(EXPIRATION_DATE).contains("02-Sep-2021"), "Expiration date not matching");
        softAssert.assertEquals(searchPage.getFieldValue(START_DATE), "03-Sep-2020", "Created Date is empty");
        softAssert.assertEquals(searchPage.getFieldValue(ASSETS_COVERED), "69", "Created Date is empty");

        searchPage.clickButton(CONTRACT_DETAILS);
        softAssert.assertTrue(contract360Page.getContractNumber().contains(searchContract), "Contract 360 Page not opened");

        contract360Page.close360View();
        softAssert.assertAll();
    }*/

    @Test(description = "Verify Open case functionality from Global Search of Asset", priority = 7)
    public void openCaseFromGlobalSearch() {
        SearchPage searchPage = new SearchPage();
        OpenCasePage openCasePage = new OpenCasePage();

        String caseTitleText = caseTitle + " " + Commons.generateRandomNumber();

        searchPage.performSearch(openCaseSerialNumber);

        assertTrue(searchPage.isButtonEnabled(OPEN_A_CASE), "Open Case button not present");
        searchPage.clickButton(OPEN_A_CASE);
        openCasePage.inputDefaultCaseDetails(caseTitleText, caseDescription, CustomerActivity.OPERATE, problemCode);
        searchPage.clickCaseCreateSubmit();
        caseNumber = openCasePage.getCaseNumber();
        openCasePage.clickButton(VIEW_CASE);
        assertTrue(new Case360Page().verifyCaseHeader(caseNumber), "Case number displayed on 360 view is incorrect");

        openCasePage.close360View();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dependsOnMethods = {"openCaseFromGlobalSearch"}, description = "Verify Case details in Global Search", priority = 7)
    public void verifySearchCaseDetails() {
        SearchPage searchPage = new SearchPage();
        Case360Page case360Page = new Case360Page();
        CasesPage casesPage = new CasesPage();
        SoftAssert softAssert = new SoftAssert();
        String allCaseCount;

        //Get API values
        allCaseCount = (String) RestUtils.getCaseListData("allCases~T", totalCountExtractString);

        Response response = RestUtils.getCaseData(caseNumber);
        JsonPath responseBody = response.getBody().jsonPath();

        searchPage.performSearch(caseNumber);
        softAssert.assertEquals(searchPage.getStatusValue(), responseBody.get("responseDetails.caseDetail.status"), "Status not matching");
        softAssert.assertTrue(searchPage.getSearchHeader().equalsIgnoreCase("Case " + caseNumber), "Case Number not matching");
        softAssert.assertEquals(searchPage.getCaseTitle(), responseBody.get("responseDetails.caseDetail.summary"), "Case Title not matching");
        softAssert.assertEquals(searchPage.getFieldValue(SEVERITY), responseBody.get("responseDetails.caseDetail.priority"), "Severity not matching");
        softAssert.assertNotNull(searchPage.getFieldValue(CREATED), "Created Date is empty");
        softAssert.assertEquals(searchPage.getFieldValue(CONTRACT), responseBody.get("responseDetails.caseDetail.contractId"), "Contract number not matching");
        softAssert.assertEquals(searchPage.getFieldValue(CASE_OWNER), responseBody.get("responseDetails.caseDetail.contactEmail"), "Case Owner not matching");
        softAssert.assertEquals(searchPage.getFieldValue(ASSET), responseBody.get("responseDetails.caseDetail.deviceName"), "Asset Name not matching");
        softAssert.assertNotNull(searchPage.getFieldValue(TAC_ENGINEER), "TAC Engineer not null");
        softAssert.assertNotNull(searchPage.getFieldValue(TRACKING_NUMBER), "Tracking Number not null");
        softAssert.assertNotNull(searchPage.getFieldValue(RELATED_RMAS), "Related RMAs not null");

        searchPage.clickButton(VIEW_CASE_DETAILS);
        softAssert.assertTrue(case360Page.verifyCaseHeader(caseNumber), "Case 360 page not open");
        case360Page.close360View();

        searchPage.performSearch(caseNumber);
        searchPage.clickButton(VIEW_ALL_OPEN_CASES);
        assertTrue(searchPage.verifyButtonSelected(ALL_OPEN_CASES), "All Open Cases is not selected");
        assertEquals(casesPage.getOpenCasesNumber(), allCaseCount, "Open Cases count not matching for All Open Cases");

        softAssert.assertAll("Please check individual failures to understand the error");

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(dependsOnMethods = {"openCaseFromGlobalSearch"}, description = "Open case from Asset Hyperlink present in Case Search results", priority = 9)
    public void openCaseFromAssetHyperlink() {
        SearchPage searchPage = new SearchPage();
        OpenCasePage openCasePage = new OpenCasePage();

        String caseTitleText = caseTitle + " " + Commons.generateRandomNumber();

        searchPage.selectCarousel(CarouselName.ASSETS_COVERAGE);
        searchPage.performSearch(caseNumber);
        searchPage.clickAssetHyperlink();
        openCasePage.selectTab(AssetsPage.HARDWARE_TAB);
        openCasePage.waitTillButtonVisible(OPEN_A_CASE);
        openCasePage.clickButton(OPEN_A_CASE);
        caseNumber = openCasePage.addDefaultCaseDetailsSubmit(caseTitleText, caseDescription, CustomerActivity.OPERATE, problemCode);
        openCasePage.clickCaseNumberLink();
        assertTrue(new Case360Page().verifyCaseHeader(caseNumber), "Case number displayed on 360 view is incorrect");

        openCasePage.close360View();
        searchPage.selectCarousel(CarouselName.ASSETS_COVERAGE);

    }

}