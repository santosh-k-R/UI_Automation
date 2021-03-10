package com.cisco.tests.cases;

import com.cisco.base.DriverBase;
import com.cisco.pages.cases.Case360Page;
import com.cisco.pages.cases.CasesPage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.CasesTableHeader;
import com.cisco.testdata.StaticData.ChartType;
import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.utils.Commons;
import com.cisco.utils.RestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cisco.pages.cases.CasesPage.*;
import static com.cisco.testdata.Data.CASES_COMMON_DATA;
import static com.cisco.testdata.StaticData.ButtonName.ALL_OPEN_CASES;
import static com.cisco.testdata.StaticData.ButtonName.MY_OPEN_CASES;
import static com.cisco.testdata.StaticData.CasesTableHeader.*;
import static com.cisco.testdata.StaticData.ChartType.COLUMN;
import static io.qameta.allure.SeverityLevel.*;
import static org.testng.Assert.*;

@Feature("Cases Page")
public class CasesPageIT extends DriverBase {

    private final String trackName = CASES_COMMON_DATA.get("TRACK");
    private final String totalCountExtractString = CASES_COMMON_DATA.get("TOTAL_RECORD_COUNT");
    private final String pageCountExtractString = CASES_COMMON_DATA.get("PAGE_NUMBER");
    private final String smartAccount = CASES_COMMON_DATA.get("SMART_ACCOUNT");
    private final FilterValue filterSeverity3 = new FilterValue(SEVERITY_FILTER_TITLE, ChartType.PIE, S3_VALUE);
    private final FilterValue filterSeverity4 = new FilterValue(SEVERITY_FILTER_TITLE, ChartType.PIE, S4_VALUE);
    private final FilterValue filterUpdatedMore1Week = new FilterValue(CasesPage.UPDATED_FILTER_TITLE, COLUMN, ONE_WEEK_VALUE);
    private final FilterValue filterOpenMore1Week = new FilterValue(TOTAL_TIME_OPEN_FILTER_TITLE, COLUMN, ONE_WEEK_VALUE);
    private final FilterValue filterWithoutRMAs = new FilterValue(RMAS_FILTER_TITLE, ChartType.BAR, WITHOUT_RMAS_VALUE);
    private final FilterValue filterCiscoPendingStatus = new FilterValue(STATUS_FILTER_TITLE, ChartType.PIE, CISCO_PENDING_STATUS);
    private final FilterValue filterProductTypeOthers = new FilterValue(PRODUCT_TYPE_FILTER_TITLE, ChartType.PIE, OTHERS_PROD_TYPE);
    private final FilterValue filterProductTypeDataCenter = new FilterValue(PRODUCT_TYPE_FILTER_TITLE, ChartType.PIE, DATA_CENTER_PROD_TYPE);
    private final FilterValue filterProductTypeSwitches = new FilterValue(PRODUCT_TYPE_FILTER_TITLE, ChartType.PIE, SWITCHES_PROD_TYPE);
    private final FilterValue filterProductTypeRouters = new FilterValue(PRODUCT_TYPE_FILTER_TITLE, ChartType.PIE, ROUTERS_PROD_TYPE);
    private final FilterValue filterProductTypeWireless = new FilterValue(PRODUCT_TYPE_FILTER_TITLE, ChartType.PIE, WIRELESS_PROD_TYPE);
    private final FilterValue filterProductTypeIndustria = new FilterValue(PRODUCT_TYPE_FILTER_TITLE, ChartType.PIE, INDUSTRIAL_PROD_TYPE);

    @BeforeClass(description = "Login to portal and Select SUCCESS TRACK and USE CASE")
    public void login() {
        CasesPage casesPage = new CasesPage();

        casesPage.login(trackName);
        casesPage.switchCXCloudAccount(smartAccount);
        casesPage.resetContextSelector();
        casesPage.selectCarousel(CarouselName.CASES);
        casesPage.clickButton(MY_OPEN_CASES);
        casesPage.refreshCasesPage();
    }

    @BeforeMethod
    public void loginReset() {
        CasesPage casesPage = new CasesPage();

        casesPage.login(trackName);
        casesPage.selectCarousel(CarouselName.CASES);
        casesPage.refreshCasesPage();
    }

    @AfterMethod
    public void closeBrowserInstance() {
        DriverBase.closeDriverInstance();
    }

    @Severity(BLOCKER)
    @Test(description = "Verify the count of open cases and With RMAs on the PR Carousel tab", priority = 1)
    public void verifyProblemResolutionCarouselDetails() {
        CasesPage casesPage = new CasesPage();

        String apiOpenCaseCount, apiOpenRMACount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~T", totalCountExtractString);
        apiOpenRMACount = (String) RestUtils.getCaseListData("allCases~T;hasRMAs~T", totalCountExtractString);

        assertEquals(casesPage.getCountOnPR(OPEN_CASES), apiOpenCaseCount, "Open cases count do not match on the PR tab");
        assertEquals(casesPage.getCountOnPR(WITH_RMAS_LABEL), apiOpenRMACount, "Open RMAs count do not match on the PR tab");

    }

    @Severity(CRITICAL)
    @Test(description = "Verify the filter functionality / Open cases number / pagination / filter tag of SEVERITY filter for S3.", priority = 1)
    public void verifySeverityFilterS4() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount, apiTotalPagesCount;
        Map<String, Integer> s4Map;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("priorities~4", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("priorities~4", pageCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterSeverity4);
        s4Map = casesPage.verifyValueOnCaseView(1);

        //Assertions
        assertEquals(s4Map.size(), 1, "Cases other than S4 are present after filtering");
        assertTrue(s4Map.containsKey(S4_VALUE), "Cases other than S4 are present after filtering");
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for S4 severity");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for S4 severity");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearSpecificFilter(S4_VALUE);
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for S3.", priority = 2)
    public void verifyTotalNoOfOpenCasesOfSeverityFilterS3() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("priorities~3", totalCountExtractString);

        casesPage.filterByVisualFilters(filterSeverity3);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases for S3 is not correct.");

        casesPage.clearSpecificFilter(S3_VALUE);
    }

    @Severity(NORMAL)
    @Test(description = "Verify the severity filter for S3 & S4 wrt total number of open cases.", priority = 3)
    public void verifyTotalNoOfOpenCasesOfSeverityFilterS3AndS4() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("priorities~3,4", totalCountExtractString);

        casesPage.filterByVisualFilters(filterSeverity3, filterSeverity4);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases for S3 and S4 is not correct.");

        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the pagination is showing correct numbers for the all open cases for <=24 hr of Updated.", priority = 4)
    public void verifyPaginationOfUpdatedForLessThanTwentyFourHours() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterUpdatedLess24Hours = new FilterValue(UPDATED_FILTER_TITLE, COLUMN, TWENTY_FOUR_HOUR_VALUE);
        String apiTotalPageCount;

        apiTotalPageCount = (String) RestUtils.getCaseListData("lastUpdateFrom~" + casesPage.date(1), pageCountExtractString);

        casesPage.filterByVisualFilters(filterUpdatedLess24Hours);

        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPageCount, "The pagination is not correct for <=24 hr of Updated.");

        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 day of Updated.", priority = 5)
    public void verifyTotalNoOfOpenCasesOfUpdatedForMoreThanOneDay() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterUpdatedMore1Day = new FilterValue(UPDATED_FILTER_TITLE, COLUMN, ONE_DAY_VALUE);
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("lastUpdateFrom~" + casesPage.date(7) + ";lastUpdateTo~" + casesPage.date(1), totalCountExtractString);

        casesPage.filterByVisualFilters(filterUpdatedMore1Day);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for > 1 day of Updated.");

        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 week of Updated.", priority = 6)
    public void verifyTotalNoOfOpenCasesOfUpdatedForMoreThanOneWeek() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("lastUpdateTo~" + casesPage.date(7), totalCountExtractString);

        casesPage.filterByVisualFilters(filterUpdatedMore1Week);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for > 1 week of Updated.");

        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the filter functionality / total number of open cases / pagination of Total Time Open filter for <=24 hr of Total Time Open.", priority = 7)
    public void verifyTotalTimeOpenFilter() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterOpenLess24Hours = new FilterValue(TOTAL_TIME_OPEN_FILTER_TITLE, COLUMN, TWENTY_FOUR_HOUR_VALUE);
        String apiOpenCaseCount, apiTotalPagesCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedFrom~" + casesPage.date(1), totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("dateCreatedFrom~" + casesPage.date(1), pageCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterOpenLess24Hours);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for <=24H Ago of Total Time Open.");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for <=24 hr of Total Time Open");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearSpecificFilter("<1 Day");
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 day of Total Time Open.", priority = 8)
    public void verifyPaginationOfTotalTimeOpenForMoreThanOneDay() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterOpenMore1Day = new FilterValue(TOTAL_TIME_OPEN_FILTER_TITLE, COLUMN, ONE_DAY_VALUE);
        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedFrom~" + casesPage.date(7) + ";dateCreatedTo~" + casesPage.date(1), totalCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterOpenMore1Day);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of cases is not correct for > 1 day of Total Time Open.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 week of Total Time Open.", priority = 9)
    public void verifyTotalNoOfOpenCasesOfTotalTimeOpenForGreaterThanOneWeek() {
        CasesPage casesPage = new CasesPage();

        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedTo~" + casesPage.date(7), totalCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterOpenMore1Week);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for > 1 week of Total Time Open.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    /*@Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 2 week of Total Time Open.", priority = 10)
    public void verifyTotalNoOfOpenCasesOfTotalTimeOpenForGreaterThanTwoWeek() {
        ProblemResolutionPage problemResolutionPage = new ProblemResolutionPage();
        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedTo~" + problemResolutionPage.date(14), totalCountExtractString);

        //Execution in UI
        problemResolutionPage.login(trackName);

        problemResolutionPage.filterByVisualFilters(filterOpenMore2Weeks);

        //Assertions
        assertEquals(problemResolutionPage.verifyShowingCaseNumbers(), apiOpenCaseCount, "The total number of open cases is correct for > 2 week of Total Time Open.");

        //Cleanup
        problemResolutionPage.clearAllFilter();
    }*/

    @Severity(CRITICAL)
    @Test(description = "Verify the filter functionality / total number of open cases / pagination for without RMAs of RMAs .", priority = 11)
    public void verifyRmasFilterByWithoutRmas() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount, apiTotalPagesCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("hasRMAs~F", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("hasRMAs~F", pageCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterWithoutRMAs);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for Without Rmas of RMAS.");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Without Rmas of RMAS.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for With RMAs of RMAS.", priority = 12)
    public void verifyRmasFilterByWithRmas() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterWithRMAs = new FilterValue(RMAS_FILTER_TITLE, ChartType.BAR, WITH_RMAS_LABEL);
        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("hasRMAs~T", totalCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterWithRMAs);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for Without Rmas of RMAS.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the visual filter functionality for the combinations of more than one filter from each filters .", priority = 13)
    public void verifyCombinationOfVisualFilters() {
        CasesPage casesPage = new CasesPage();

        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedTo~" + casesPage.date(7) + ";lastUpdateTo~" + casesPage.date(7) + ";hasRMAs~F;priorities~3,4", totalCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterSeverity3, filterSeverity4, filterUpdatedMore1Week, filterOpenMore1Week, filterWithoutRMAs);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for respective filtering");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the visual filter functionality for Cisco Pending Status", priority = 14)
    public void verifyCiscoPendingStatusFilter() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount, apiTotalPagesCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("openStatuses~Cisco Pending", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("openStatuses~Cisco Pending", pageCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterCiscoPendingStatus);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for New Status filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for New Status filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearAllFilter();

    }


    @Severity(NORMAL)
    @Test(description = "Verify the visual filter functionality for multiple Status options", priority = 15)
    public void verifyMultipleStatusFilter() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterCiscoPendingStatus = new FilterValue(STATUS_FILTER_TITLE, ChartType.PIE, CISCO_PENDING_STATUS);
        FilterValue filterCustomerPendingStatus = new FilterValue(STATUS_FILTER_TITLE, ChartType.PIE, CUSTOMER_PENDING_STATUS);
        String apiOpenCaseCount, apiTotalPagesCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("openStatuses~Cisco Pending", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("openStatuses~Cisco Pending", pageCountExtractString);

        casesPage.filterByVisualFilters(filterCiscoPendingStatus);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for Cisco Pending Status filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Cisco Pending Status filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        casesPage.clearAllFilter();

        apiOpenCaseCount = (String) RestUtils.getCaseListData("openStatuses~Customer Pending,Cisco Pending", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("openStatuses~Customer Pending,Cisco Pending", pageCountExtractString);

        casesPage.filterByVisualFilters(filterCustomerPendingStatus, filterCiscoPendingStatus);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for Customer Pending and Cisco Pending Status filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Customer Pending and Cisco Pending Status filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the visual filter functionality for Auto Created Cases", priority = 16)
    public void verifyAutoCreatedFilter() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterAutoCreated = new FilterValue(AUTO_CREATED_FILTER_TITLE, ChartType.BAR, AUTO_CREATED_VALUE);
        String apiOpenCaseCount, apiTotalPagesCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("caseOrigin~Auto-Case", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("caseOrigin~Auto-Case", pageCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterAutoCreated);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for Auto Created filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Auto Created filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the visual filter functionality for All option in AUTO-CREATED Cases", priority = 17)
    public void verifyAllAutoCreatedFilter() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterAll = new FilterValue(AUTO_CREATED_FILTER_TITLE, ChartType.BAR, ALL_VALUE);
        String apiOpenCaseCount, apiTotalPagesCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~F", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("allCases~F", pageCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterAll);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for Auto Created filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Auto Created filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearSpecificFilter(ALL_VALUE);
    }

    @Severity(NORMAL)
    @Test(description = "Verify the text No Cases Found after applying filters and no cases are returned in the table", priority = 18)
    public void verifyNoCasesFoundText() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterWithRMAs = new FilterValue(RMAS_FILTER_TITLE, ChartType.BAR, WITH_RMAS_LABEL);
        FilterValue filterOpenLess24Hours = new FilterValue(TOTAL_TIME_OPEN_FILTER_TITLE, COLUMN, TWENTY_FOUR_HOUR_VALUE);
        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedTo~" + casesPage.date(1) + ";lastUpdateTo~" + casesPage.date(7) + ";hasRMAs~T;priorities~3,4;openStatuses~Cisco Pending", totalCountExtractString);

        //Execution in UI
        casesPage.filterByVisualFilters(filterSeverity3, filterSeverity4, filterUpdatedMore1Week, filterOpenLess24Hours, filterWithRMAs, filterCiscoPendingStatus);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for respective filtering");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");
        assertTrue(casesPage.verifyNoCasesFoundText(), "There are few open cases present after applying filter");

        //Cleanup
        casesPage.clearAllFiltersNoResults();
    }

    @Severity(MINOR)
    @Test(description = "Verify all the headers are proper and in order in the Case List table", priority = 19)
    public void verifyTableHeaders() {
        CasesPage casesPage = new CasesPage();
        List<String> caseListTableHeaders;

        casesPage.resetColumnsFromColumnSelector();
        caseListTableHeaders = casesPage.getAllTableTitles();

        assertEquals(caseListTableHeaders, Arrays.stream(CasesTableHeader.values()).map(CasesTableHeader::getTableHeader).collect(Collectors.toList()), "Case list Table headers do not match");

    }

    @Severity(NORMAL)
    @Test(description = "Verify Sorting of all the columns in the case list table", priority = 20)
    public void verifySortingOfCaseListTable() {
        CasesPage casesPage = new CasesPage();
        SoftAssert softAssert = new SoftAssert();

        casesPage.resetColumnsFromColumnSelector();

        casesPage.verifyColumnOrdering(SEVERITY, 1, "Descending");
        casesPage.verifyColumnOrdering(SEVERITY, 1, "Ascending");

        casesPage.verifyColumnOrdering(CASE_NUMBER, 2, "Descending");
        casesPage.verifyColumnOrdering(CASE_NUMBER, 2, "Ascending");

        casesPage.verifyColumnOrdering(TITLE, 3, "Descending");
        casesPage.verifyColumnOrdering(TITLE, 3, "Ascending");

        casesPage.verifyColumnOrdering(STATUS, 4, "Descending");
        casesPage.verifyColumnOrdering(STATUS, 4, "Ascending");

        casesPage.verifyColumnOrdering(OWNER, 7, "Descending");
        casesPage.verifyColumnOrdering(OWNER, 7, "Ascending");

        softAssert.assertFalse(casesPage.sortTable(WITH_RMAS.getTableHeader()), "Sorting enabled for With RMAs column");
        softAssert.assertFalse(casesPage.sortTable(AUTO_CREATED.getTableHeader()), "Sorting enabled for Auto-Created column");

        softAssert.assertAll("One of the sort functionality has failed. Please check the asserts");
    }

    @Severity(NORMAL)
    @Test(description = "Verify Sorting of all the columns in the case list table in All Open Cases", priority = 20)
    public void verifySortingOfCaseListTableAllCases() {
        CasesPage casesPage = new CasesPage();
        SoftAssert softAssert = new SoftAssert();

        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.resetColumnsFromColumnSelector();

        casesPage.verifyColumnOrdering(SEVERITY, 1, "Descending");
        casesPage.verifyColumnOrdering(SEVERITY, 1, "Ascending");

        casesPage.verifyColumnOrdering(CASE_NUMBER, 2, "Descending");
        casesPage.verifyColumnOrdering(CASE_NUMBER, 2, "Ascending");

        casesPage.verifyColumnOrdering(TITLE, 3, "Descending");
        casesPage.verifyColumnOrdering(TITLE, 3, "Ascending");

        casesPage.verifyColumnOrdering(STATUS, 4, "Descending");
        casesPage.verifyColumnOrdering(STATUS, 4, "Ascending");

        casesPage.verifyColumnOrdering(OWNER, 7, "Descending");
        casesPage.verifyColumnOrdering(OWNER, 7, "Ascending");

        softAssert.assertFalse(casesPage.sortTable(WITH_RMAS.getTableHeader()), "Sorting enabled for With RMAs column in All Open cases");
        softAssert.assertFalse(casesPage.sortTable(AUTO_CREATED.getTableHeader()), "Sorting enabled for Auto-Created column in All Open Cases");

        casesPage.sortTable(UPDATED.getTableHeader());
        softAssert.assertAll("One of the sort functionality has failed. Please check the asserts");
    }

    @Severity(MINOR)
    @Test(description = "Verify the search functionality for invalid cases.", priority = 21)
    public void verifySearchFunctionalityForInvalidCaseNumber() {
        CasesPage casesPage = new CasesPage();

        //Execution in UI
        casesPage.searchCase(String.valueOf(Commons.generateRandomNumber()));

        //Assertions
        assertTrue(casesPage.verifyNoCasesFoundText(), "Found a valid case");

        //Cleanup
        casesPage.clearSearchCase();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the toggle filter functionality for My and All Open Cases", priority = 22)
    public void verifyMyAndAllOpenCasesToggle() {
        CasesPage casesPage = new CasesPage();
        String allCaseCount, myCaseCount;

        //Get API values
        allCaseCount = (String) RestUtils.getCaseListData("allCases~T", totalCountExtractString);
        myCaseCount = (String) RestUtils.getCaseListData("allCases~F", totalCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        assertTrue(casesPage.verifyButtonSelected(ALL_OPEN_CASES));
        assertEquals(casesPage.getOpenCasesNumber(), allCaseCount, "Open Cases count not matching for All Open Cases");

        casesPage.clickButton(MY_OPEN_CASES);
        assertTrue(casesPage.verifyButtonSelected(MY_OPEN_CASES));
        assertEquals(casesPage.getOpenCasesNumber(), myCaseCount, "Open Cases count not matching for My Open Cases");

    }

    @Severity(CRITICAL)
    @Test(description = "Verify the Product type filter functionality", priority = 23)
    public void verifyProductTypeFilterFunctionality() {
        CasesPage casesPage = new CasesPage();

        //Execution in UI
        casesPage.filterByVisualFilters(filterProductTypeOthers);
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");
        assertFalse(casesPage.getShowingCaseNumbersCount().isEmpty(), "Open Cases count not matching for others type product type filter.");

        casesPage.filterByVisualFilters(filterProductTypeIndustria, filterProductTypeSwitches);
        assertFalse(casesPage.getShowingCaseNumbersCount().isEmpty(), "Open Cases count not matching for others and switches type product type filter.");

        //Cleanup
        casesPage.clearAllFilter();

    }

    @Severity(CRITICAL)
    @Test(description = "Verify the filter functionality / pagination / filter tag of SEVERITY filter for S3 -All Open cases.", priority = 24)
    public void verifySeverityFilterS4AllOpenCases() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount, apiTotalPagesCount;
        Map<String, Integer> s4Map;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~T;priorities~4", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("allCases~T;priorities~4", pageCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.clickOpenCasesView();
        casesPage.filterByVisualFilters(filterSeverity4);
        s4Map = casesPage.verifyValueOnCaseView(1);

        //Assertions
        assertEquals(s4Map.size(), 1, "Cases other than S4 are present after filtering");
        assertTrue(s4Map.containsKey(S4_VALUE), "Cases other than S4 are present after filtering");
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for S4 severity");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for S4 severity");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearSpecificFilter(S4_VALUE);
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for S3 of All Open cases", priority = 25)
    public void verifyTotalNoOfOpenCasesOfSeverityFilterS3AllOpenCases() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~T;priorities~3", totalCountExtractString);

        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.clickOpenCasesView();
        casesPage.filterByVisualFilters(filterSeverity3);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases for S4 is not correct.");

        casesPage.clearSpecificFilter(S3_VALUE);
    }

    @Severity(NORMAL)
    @Test(description = "Verify the severity filter for S3 & S4 wrt total number of open cases.", priority = 26)
    public void verifyTotalNoOfOpenCasesOfSeverityFilterS3AndS4AllOpenCases() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~T;priorities~3,4", totalCountExtractString);

        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.clickOpenCasesView();
        casesPage.filterByVisualFilters(filterSeverity3, filterSeverity4);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases for S3 and S4 is not correct.");

        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the pagination is showing correct numbers for the all open cases for <=24 hr of Updated.", priority = 27)
    public void verifyPaginationOfUpdatedForLessThanTwentyFourHoursAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterUpdatedLess24Hours = new FilterValue(UPDATED_FILTER_TITLE, COLUMN, TWENTY_FOUR_HOUR_VALUE);
        String apiTotalPageCount;

        apiTotalPageCount = (String) RestUtils.getCaseListData("lastUpdateFrom~" + casesPage.date(1) + ";allCases~T", pageCountExtractString);

        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterUpdatedLess24Hours);

        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPageCount, "The pagination is not correct for <=24 hr of Updated.");

        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 day of Updated.", priority = 28)
    public void verifyTotalNoOfOpenCasesOfUpdatedForMoreThanOneDayAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterUpdatedMore1Day = new FilterValue(UPDATED_FILTER_TITLE, COLUMN, ONE_DAY_VALUE);
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("lastUpdateFrom~" + casesPage.date(7) + ";lastUpdateTo~" + casesPage.date(1) + ";allCases~T", totalCountExtractString);

        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterUpdatedMore1Day);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for > 1 day of Updated.");

        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 week of Updated.", priority = 29)
    public void verifyTotalNoOfOpenCasesOfUpdatedForMoreThanOneWeekAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("lastUpdateTo~" + casesPage.date(7) + ";allCases~T", totalCountExtractString);

        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterUpdatedMore1Week);

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for > 1 week of Updated.");

        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the filter functionality / total number of open cases / pagination of Total Time Open filter for <=24 hr of Total Time Open  for All Open cases.", priority = 30)
    public void verifyTotalTimeOpenFilterAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterOpenLess24Hours = new FilterValue(TOTAL_TIME_OPEN_FILTER_TITLE, COLUMN, TWENTY_FOUR_HOUR_VALUE);
        String apiOpenCaseCount, apiTotalPagesCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedFrom~" + casesPage.date(1) + ";allCases~T", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("dateCreatedFrom~" + casesPage.date(1) + ";allCases~T", pageCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterOpenLess24Hours);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for <=24H Ago of Total Time Open.");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for <=24 hr of Total Time Open");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        casesPage.clearSpecificFilter("<1 Day");
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 day of Total Time Open for All Open cases.", priority = 31)
    public void verifyPaginationOfTotalTimeOpenForMoreThanOneDayAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterOpenMore1Day = new FilterValue(TOTAL_TIME_OPEN_FILTER_TITLE, COLUMN, ONE_DAY_VALUE);
        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedFrom~" + casesPage.date(7) + ";dateCreatedTo~" + casesPage.date(1) + ";allCases~T", totalCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterOpenMore1Day);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of cases is not correct for > 1 day of Total Time Open for All Open cases.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for > 1 week of Total Time Open for All Open cases  for All Open cases.", priority = 32)
    public void verifyTotalNoOfOpenCasesOfTotalTimeOpenForGreaterThanOneWeekAllOpenCases() {
        CasesPage casesPage = new CasesPage();

        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedTo~" + casesPage.date(7) + ";allCases~T", totalCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterOpenMore1Week);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for > 1 week of Total Time Open for All Open cases.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the filter functionality / total number of open cases / pagination for without RMAs of RMAs for All Open cases.", priority = 33)
    public void verifyRmasFilterByWithoutRmasAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        String apiOpenCaseCount, apiTotalPagesCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~T;hasRMAs~F", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("allCases~T;hasRMAs~F", pageCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterWithoutRMAs);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for Without Rmas of RMAS for All Open cases.");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Without Rmas of RMAS for All Open cases.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(NORMAL)
    @Test(description = "Verify the total number of open cases for With RMAs of RMAS  for All Open cases.", priority = 34)
    public void verifyRmasFilterByWithRmasAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterWithRMAs = new FilterValue(RMAS_FILTER_TITLE, ChartType.BAR, WITH_RMAS_LABEL);
        String apiOpenCaseCount;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("allCases~T;hasRMAs~T", totalCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.filterByVisualFilters(filterWithRMAs);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "The total number of open cases is correct for Without Rmas of RMAS  for All Open cases.");

        //Cleanup
        casesPage.clearAllFilter();
    }

    @Severity(CRITICAL)
    @Test(description = "Verify the visual filter functionality for the combinations of more than one filter from each filter's for All Open cases .", priority = 35)
    public void verifyCombinationOfVisualFiltersAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        Case360Page case360Page = new Case360Page();
        String apiOpenCaseCount, caseNumber;

        //Get API values
        apiOpenCaseCount = (String) RestUtils.getCaseListData("dateCreatedTo~" + casesPage.date(7) + ";lastUpdateTo~" + casesPage.date(7) + ";hasRMAs~F;priorities~3,4;allCases~T", totalCountExtractString);

        //Execution in UI
        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.clickOpenCasesView();
        casesPage.filterByVisualFilters(filterSeverity3, filterSeverity4, filterUpdatedMore1Week, filterOpenMore1Week, filterWithoutRMAs);

        //Assertions
        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for respective filtering for All Open cases");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        caseNumber = casesPage.getTableCellValue(1, 2);
        casesPage.searchCase(caseNumber);
        casesPage.open360View(caseNumber);
        assertTrue(case360Page.verifyCaseHeader(caseNumber), "Case number not matched");

        //Cleanup
        case360Page.close360View();
        casesPage.clearAllFilter();

    }

    @Severity(NORMAL)
    @Test(description = "Verify the visual filter functionality for multiple Status options for all open cases", priority = 36)
    public void verifyMultipleStatusFilterAllOpenCases() {
        CasesPage casesPage = new CasesPage();
        FilterValue filterCiscoPendingStatus = new FilterValue(STATUS_FILTER_TITLE, ChartType.PIE, CISCO_PENDING_STATUS);
        FilterValue filterCustomerPendingStatus = new FilterValue(STATUS_FILTER_TITLE, ChartType.PIE, CUSTOMER_PENDING_STATUS);
        String apiOpenCaseCount, apiTotalPagesCount;

        apiOpenCaseCount = (String) RestUtils.getCaseListData("openStatuses~Cisco Pending;allCases~T", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("openStatuses~Cisco Pending;allCases~T", pageCountExtractString);

        casesPage.clickButton(ALL_OPEN_CASES);
        casesPage.clickOpenCasesView();
        casesPage.filterByVisualFilters(filterCiscoPendingStatus);

        //Assertions

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for Cisco Pending Status filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Cisco Pending Status filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        casesPage.clearAllFilter();

        apiOpenCaseCount = (String) RestUtils.getCaseListData("openStatuses~Customer Pending;allCases~T", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("openStatuses~Customer Pending;allCases~T", pageCountExtractString);

        casesPage.clickOpenCasesView();
        casesPage.filterByVisualFilters(filterCustomerPendingStatus);

        //Assertions

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for Customer Pending Status filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Customer Pending Status filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        casesPage.clearAllFilter();

        apiOpenCaseCount = (String) RestUtils.getCaseListData("openStatuses~Customer Pending,Cisco Pending;allCases~T", totalCountExtractString);
        apiTotalPagesCount = (String) RestUtils.getCaseListData("openStatuses~Customer Pending,Cisco Pending;allCases~T", pageCountExtractString);

        casesPage.clickOpenCasesView();
        casesPage.filterByVisualFilters(filterCustomerPendingStatus, filterCiscoPendingStatus);

        //Assertions

        assertEquals(casesPage.getShowingCaseNumbersCount(), apiOpenCaseCount, "Open Cases count not matching for Cisco Pending and Customer Pending Status filter");
        assertEquals(casesPage.getTotalCountOfPages(), apiTotalPagesCount, "Total page count not matching for Cisco Pending and Customer Pending Status filter.");
        assertTrue(casesPage.checkFilterApplied(), "Filter tag is not visible");

        casesPage.clearAllFilter();
    }

}