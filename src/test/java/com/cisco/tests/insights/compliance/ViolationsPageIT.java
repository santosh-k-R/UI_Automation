package com.cisco.tests.insights.compliance;


import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.compliance.ComplianceLandingPage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.DNAC_DropdownValue;
import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.testdata.insights.compliance.ViolationsPageData;
import com.cisco.testdata.insights.compliance.RccRestUtils;
import com.cisco.utils.auth.TokenGenerator;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.Header;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cisco.testdata.RccConstants.*;
import static com.cisco.testdata.insights.compliance.ViolationsPageData.*;


public class ViolationsPageIT extends DriverBase {

    String params = "solution~" + TRACK + ";customerId~" + CUSTOMERID + ";cxLevel~" + CX_LEVEL + ";saId~" + SAID + ";";
    private final String USERNAME = System.getProperty("username");
    private final String PASSWORD = System.getProperty("password");
    private static Header auth;
    ComplianceLandingPage landingPage;

    @BeforeClass(description = "Generating Headers for API Call")
    public void generateHeaders() {
        System.out.println(USERNAME + " : " + PASSWORD);
        auth = TokenGenerator.getToken(USERNAME, PASSWORD);
        System.out.println("auth : " + auth);
    }

//    @BeforeMethod(description = "Systems with Violation :Make sure we are loged in , select track and usecase And select the view")
    public void login() {
        landingPage = new ComplianceLandingPage();
        landingPage.login()
                .switchCXCloudAccount(SMARTACCOUNT)
                .resetContextSelector()
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, TRACK_UI)
                .selectCarousel(CarouselName.INSIGHTS);

        landingPage.clickCompianceTab(COMPLIANCE_TAB)
                .resetTable();
    }

    public void login(ComplianceLandingPage landingPage) {
        landingPage = new ComplianceLandingPage();
        landingPage.login()
                .switchCXCloudAccount(SMARTACCOUNT)
                .resetContextSelector()
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, TRACK_UI)
                .selectCarousel(CarouselName.INSIGHTS);

        landingPage.clickCompianceTab(COMPLIANCE_TAB)
                .resetTable();
    }

    @Test(description = "Verify the count of compliance issues in insights Carousel tab", priority = 1)
    public void verifyComplianceIssuesCarouselDetails() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiComplianceIssuesCount;
        apiComplianceIssuesCount = (String) RccRestUtils.getViolationCount(auth, params, violationCount, BASE_URI + VIOLATION_LANDING_API);

        login(landingPage);
        System.out.println("Violation count" + apiComplianceIssuesCount);
        softAssert.assertEquals(landingPage.getCountOnCarousel(COMPLIANCE_COUNT_CAROUSEL), apiComplianceIssuesCount, "Compliance issues count do not match on the PR tab");
        System.out.println("************************Done****************************");
        softAssert.assertAll();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify all the defaults in compliance tab", priority = 2)
    public void verifyOverViewCount() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        String apiViolationsCount;
        String apiSystemsWithViolationsCount;
        SoftAssert softAssert = new SoftAssert();
        List<String> violationsListTableHeaders;
        //Get API values
        apiViolationsCount = (String) RccRestUtils.getViolationCount(auth, params, violationCount, BASE_URI + VIOLATION_LANDING_API);
        apiSystemsWithViolationsCount = (String) RccRestUtils.getViolationCount(auth, params, impactedAssetCount, BASE_URI + VIOLATION_LANDING_FILTER_API);

        login(landingPage);
        //Assertions
        softAssert.assertTrue(landingPage.checkActiveTab(COMPLIANCE_TAB), "Compliance tab isnt active");
        softAssert.assertTrue(landingPage.checkActiveSubTab(VIOLATIONS_SUB_VIEW), "Violations sub tab isnt active");
        softAssert.assertTrue(!(landingPage.checkActiveSubTab(SYSTEMS_VIOLATIONS_SUB_VIEW)), "Sytems with Violation is active");
        softAssert.assertTrue(!(landingPage.checkFilterApplied()), "Filters are applied");
        softAssert.assertTrue(landingPage.checkDefualtSearchWithAll(), "Search tab isnt with default All catogory");
        softAssert.assertEquals(landingPage.getPlaceholderText(), SEARCH_PLACEHOLDER, "Search placeholder is differnt");
        softAssert.assertEquals((landingPage.getCountSubTabViolations(VIOLATIONS_SUB_VIEW)).replace(",", ""), apiViolationsCount, "Violations count do not match on the sub tab");
        softAssert.assertEquals((landingPage.getCountSubTabViolations(SYSTEMS_VIOLATIONS_SUB_VIEW)).replace(",", ""), apiSystemsWithViolationsCount, "Impacted Assets count do not match on the sub tab");
        // softAssert.assertEquals(landinpRccConstants.SOURCE), RccConstants.SOURCE_TOOL_TIP, "Source Tool tip is different than expected");

        violationsListTableHeaders = landingPage.getAllTableTitles();
        violationsListTableHeaders.remove(violationsListTableHeaders.size() - 1);

        System.out.println("Violation Table :  " + violationsListTableHeaders);
        softAssert.assertTrue(violationsListTableHeaders.equals(VIOLATION_SUMMARY_TABLE), "Table order not maintained");

        softAssert.assertAll();
    }

//    Not Working
//    @Test(timeOut = 1200000, description = "Verify Default sort on high severity working in violation landing ", priority = 4)
    public void verifyDefaultSortOfViolationSummaryListTable() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        List<String> apiColumnValues = new ArrayList<>();
        List<String> uiColumnValues = new ArrayList<>();
        apiColumnValues = RccRestUtils.getValuesOfColumn(auth, params, highestSeverity, BASE_URI + VIOLATION_LANDING_API);

        login(landingPage);
        System.out.println("List" + apiColumnValues.size());
        uiColumnValues = landingPage.verifyColmmnValue(1);

        //Assert
        softAssert.assertEquals(uiColumnValues.size(), apiColumnValues.size());
        softAssert.assertTrue(uiColumnValues.equals(apiColumnValues), "Default sort on high severity isnt working as expected ");

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

//    Not Working
//    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummaryFilter", description = "Verify the filter functionality / pagination / filter tag.", priority = 5)
    public void verifyFilterViolationSummary(String filterValue, FilterValue filterBy, int columnValue, String paramName) {

        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiViolationsCount, apiTotalPagesCount;
        Map<String, Integer> s3Map;

        //Get API values
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + paramName + "~" + filterValue + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);

        login(landingPage);
        //Execution in UI
        landingPage.filterByVisualFilters(filterBy);
        s3Map = landingPage.verifyValueOnViolationView(columnValue, 5);
        //Assertions
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible for " + filterValue);
        softAssert.assertEquals((s3Map.size()) - 1, 1, "Violations other than " + filterValue + " are present after filtering");
        softAssert.assertTrue(s3Map.containsKey(filterValue), "Violations other than " + filterValue + " are present after filtering");
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter for " + filterValue);
        // softAssert.assertEquals(String.valueOf(s3Map.get("Sum")), apiViolationsCount, "Sum of Violation count same as total violation count for "+ filterValue);
        // softAssert.assertEquals(landingPage.verifyCountOnViolationView(), apiViolationsCount, "Sum of Violation count same as total violation count for "+ filterValue);
        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount) / 10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for " + filterValue + "  regulatory type");

        //Cleanup
        landingPage.clearSpecificFilter(filterValue);

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

//    Not Working
//    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySearch", description = "Verify the search functionality", priority = 6)
    public void verifySearchViolationSummary(String searchApiPath, DNAC_DropdownValue dnacCat, boolean caseSen) {

        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiViolationsCount, apiTotalPagesCount, apiSearchValue;

        login(landingPage);
        //Get API values
        apiSearchValue = (String) RccRestUtils.getAPIFristValue(auth, params, searchApiPath, BASE_URI + VIOLATION_LANDING_API);
        if (caseSen)
            apiSearchValue = apiSearchValue.toLowerCase();

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params = params.concat("category~dnac;");

        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "search~" + apiSearchValue + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //Execution in UI
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Assertions
        softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After search page didnt rest");
        softAssert.assertTrue(landingPage.verifySearch(apiSearchValue), "Other than search Value is present " + apiSearchValue);
//        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per search");
        // softAssert.assertEquals(landingPage.verifyCountOnViolationView(RccConstants.VIOLATION_LANDING_COL_INDEX_VIO), apiViolationsCount, "Sum of Violation count same as total violation count");
//        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount) / 10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " + apiSearchValue);

        //Cleanup
        landingPage.clearSearch();
        softAssert.assertEquals(landingPage.getPlaceholderText(), SEARCH_PLACEHOLDER, "Search placeholder is differnt");

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }


    @Test(description = "Verify the visual filter functionality for the combinations of  filter .", priority = 8)
    public void verifyCombinationOfVisualFilters() {

        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();

        String apiViolationsCount, apiTotalPagesCount;

        //Get API values
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "severity~" + SEVERITY_CRITICAL_FILTER_VALUE + ";policyType~" + RG_PCI_FILTER_VALUE + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);
        //apiViolationsCount =(String) RccRestUtils.getViolationCount(auth,params+"severity~"+ RccConstants.SEVERITY_MED_FILTER_VALUE+";policyType~"+ RccConstants.RG_PCI_FILTER_VALUE+";",violationCount, RccConstants.BASE_URI+RccConstants.VIOLATION_LANDING_API);

        //Execution in UI

        login(landingPage);
        landingPage.filterByVisualFilters(RG_PCI_FILTER, RG_CRITICAL_FILTER);

        //Assertions
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter");
        //softAssert.assertEquals(landingPage.verifyCountOnViolationView(RccConstants.VIOLATION_LANDING_COL_INDEX_VIO), apiViolationsCount, "Sum of Violation count same as total violation count");

        //Cleanup ..should clear both search and filter
        landingPage.clearAllFilter();
        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySearchFilterCombo", description = "Verify  functionality for the combinations of  filter and search with category", priority = 9)
    public void verifyCombinationOfVisualFiltersAndSearch(String searchApiPath, DNAC_DropdownValue dnacCat, FilterValue filterOneValue, FilterValue filterTwoValue, String FilterOneLabel, String FilterSecLabel) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();

        String apiViolationsCount, apiTotalPagesCount, apiSearchValue;

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //Get API values
        apiSearchValue = (String) RccRestUtils.getAPIFristValue(auth, params + "severity~" + FilterOneLabel + ";policyType~" + FilterSecLabel + ";", searchApiPath, BASE_URI + VIOLATION_LANDING_API);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "severity~" + FilterOneLabel + ";policyType~" + FilterSecLabel + ";search~" + apiSearchValue + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);
        //  apiViolationsCount =(String) RccRestUtils.getViolationCount(auth,params+"severity~"+FilterOneLabel+";policyType~"+FilterSecLabel+";search~"+apiSearchValue+";",violationCount, RccConstants.BASE_URI+RccConstants.VIOLATION_LANDING_API);
        System.out.println("apiSearchValue : " + apiSearchValue);
        System.out.println("apiTotalPageCount : " + apiTotalPagesCount);

        //Execution in UI
        login(landingPage);
        landingPage.filterByVisualFilters(filterOneValue, filterTwoValue);
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Assertions
        softAssert.assertTrue(landingPage.verifySearch(apiSearchValue), "Other than search Value is present " + apiSearchValue);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter");
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        //softAssert.assertEquals(landingPage.verifyCountOnViolationView(RccConstants.VIOLATION_LANDING_COL_INDEX_VIO), apiViolationsCount, "Sum of Violation count same as total violation count");

        //Cleanup
        //landingPage.clearSearch();
        landingPage.clearAllFilter();

        //reverse should also work
//        landingPage.searchDNACField(dnacCat, apiSearchValue);
//        landingPage.filterByVisualFilters(filterOneValue, filterTwoValue);
//        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
//        softAssert.assertTrue(landingPage.verifySearch(apiSearchValue), "Other than search Value is present " + apiSearchValue);
//
//        //landingPage.RefreshAndWait();
//        softAssert.assertAll();
//        landingPage.clearAllFilter();
    }


    @Test(description = "Verify on filter pagination  rest in violation landing ", priority = 13)
    public void verifyFilterPaginationReset() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String PageCountBeforeFilter;

        //UI operations
        login(landingPage);
        PageCountBeforeFilter = landingPage.getTotalCountOfPages();
        landingPage.goToLastPage();
        landingPage.filterByVisualFilters(RG_HIPAA_FILTER);

        //Aseert
        softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After filter page didnt rest");
        //Cleanup
        landingPage.clearSpecificFilter(RG_HIPAA_FILTER_VALUE);
        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }


    @Test(description = "Verify on sort pagination is rest in violation landing ", priority = 14)
    public void verifySortPaginationNotReset() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        int pageCountBeforeSort;

        //UI operations
        login(landingPage);
        pageCountBeforeSort = Integer.parseInt(landingPage.getTotalCountOfPages());

        if(pageCountBeforeSort>1)
            landingPage.navigateToPage(pageCountBeforeSort);

        landingPage.sortTable(CATEGORY);

        //Assert
        //  softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(RccConstants.CATEGORY),"Asc sort sign isnt shown or table title isnt highlights");
        softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "sort page  didnt reset from last page instead it is " + landingPage.getCurrentPage());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySearchPaginationReset", description = "Verify on search pagination  rest in violation landing ", priority = 15)
    public void verifySearchPaginationReset(String searchApiPath, DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String PageCountBeforeFilter, apiSearchValue;

        apiSearchValue = (String) RccRestUtils.getAPIFristValue(auth, params, searchApiPath, BASE_URI + VIOLATION_LANDING_API);

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //UI operations
        login(landingPage);
        PageCountBeforeFilter = landingPage.getTotalCountOfPages();
        landingPage.navigateToPage(Integer.parseInt(PageCountBeforeFilter));
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Aseert
        softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After search page didnt rest");
        softAssert.assertAll();
    }

    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySearchNoResult", description = "Verify the search functionality is for no results found ", priority = 16)
    public void verifySearchNoResult(DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiViolationsCount, apiTotalPagesCount;
        String apiSearchValue = SEARCH_INVALID_STRING;

        //Execution in UI
        login(landingPage);
        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Assertions
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), "0", "Violation count in showing message as per search");
        softAssert.assertTrue(landingPage.checkNoResultFound(), "No resullts found banner isnt displayed");
        softAssert.assertTrue(!landingPage.checkTableVisible());
        //Cleanup
        landingPage.clearSearch();
        softAssert.assertTrue(landingPage.checkTableVisible());

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummaryFilterSearchNoResult", description = "Verify the  no result found for : visual filter functionality for the combinations of  filter and search with categoty", priority = 17)
    public void verifyCombinationOfVisualFiltersAndSearchNoResult(DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();

        String apiTotalPagesCount, apiSearchValue;
        //Get API values
        apiSearchValue = SEARCH_INVALID_STRING;

        //Execution in UI
        login(landingPage);
        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        landingPage.waitForInitialPageToLoad();
        landingPage.filterByVisualFilters(RG_PCI_FILTER, RG_MEDIUM_FILTER);
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Assertions
        softAssert.assertTrue(landingPage.checkNoResultFound(), "No resullts found banner isnt displayed");
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), "0", "Violation count in showing message as per filter");
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        landingPage.clearAllFilter();
        softAssert.assertAll();
    }

    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummaryFilterSearchSortNoResult", description = "Verify the  no result found for : visual filter functionality for the combinations of  filter, sort and search with categoty", priority = 18)
    public void verifyCombinationOfVisualFiltersSortAndSearchNoResult(DNAC_DropdownValue dnacCat, String sortLabel, String sortParam) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount, apiSearchValue;

        //Get API values
        apiSearchValue = SEARCH_INVALID_STRING;

        //Execution in UI
        login(landingPage);
        landingPage.sortTable(sortLabel);
        landingPage.filterByVisualFilters(RG_PCI_FILTER, RG_MEDIUM_FILTER);
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Assertions
        softAssert.assertTrue(landingPage.checkNoResultFound(), "No resullts found banner isnt displayed");
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), "0", "Violation count in showing message as per filter");
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        landingPage.clearAllFilter();
        softAssert.assertAll();

    }

    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySearchSortNoResult", description = "Verify the  no result found for :   combinations of  sort and search with categoty", priority = 19)
    public void verifyCombinationOfSortAndSearchNoResult(DNAC_DropdownValue dnacCat, String sortLabel, String sortParam) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount, apiSearchValue;
        apiSearchValue = SEARCH_INVALID_STRING;

        //Get API values
        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~asc;search~" + apiSearchValue + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //Execution in UI
        login(landingPage);
        landingPage.sortTable(sortLabel);
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Assertions
        softAssert.assertTrue(landingPage.checkNoResultFound(), "No results found banner isnt displayed");
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter");

        //landingPage.RefreshAndWait();
        landingPage.clearSearch();
        softAssert.assertAll();

    }

    //Sort isnt working
    //    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySort", description = "Verify sorting functionality in violation landing ", priority = 7)
    public void verifySortSummaryListTable(String sortApiPath, String sortParam, String sortLabel, int col) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount;
        List<String> apiColumnValues = new ArrayList<String>();
        List<String> uiColumnValues = new ArrayList<String>();

        //UI Setup

        landingPage.sortTable(sortLabel);

        apiColumnValues = RccRestUtils.getValuesOfColumn(auth, params + "sortName~" + sortParam + ";sortOrder~asc", sortApiPath, BASE_URI + VIOLATION_LANDING_API);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~asc", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //UI operations
        uiColumnValues = landingPage.verifyColmmnValue(col);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel), "Sort icon not active " + sortParam);
        softAssert.assertEquals(uiColumnValues.size(), apiColumnValues.size(), "Number of records not same after sorting " + sortParam);
        softAssert.assertTrue(uiColumnValues.equals(apiColumnValues), " asc sort isnt working as expected " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);

        //reser page number

        landingPage.sortTable(sortLabel);
        uiColumnValues = landingPage.verifyColmmnValue(col);
        apiColumnValues = RccRestUtils.getValuesOfColumn(auth, params + "sortName~" + sortParam + ";sortOrder~desc", sortApiPath, BASE_URI + VIOLATION_LANDING_API);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~desc", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel), "Sort icon not active " + sortParam);
        softAssert.assertEquals(uiColumnValues.size(), apiColumnValues.size(), "Number of records not same after sorting " + sortParam);
        softAssert.assertTrue(uiColumnValues.equals(apiColumnValues), "desc sort isnt working as expected " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);

        //landingPage.RefreshAndWait();
        landingPage.sortTable(sortLabel);
        softAssert.assertAll();


    }

    //    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySortFilterCombo", description = "Verify combination of filter and sorting functionality in violation landing ", priority = 10)
    public void verifyCombinationOfVisualFiltersAndSort(String sortApiPath, String sortParam, String sortLabel, int col, FilterValue filterOneValue, FilterValue filterTwoValue, String FilterOneLabel, String FilterSecLabel) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();

        String apiViolationsCount, apiTotalPagesCount, apiSearchValue;

        landingPage.filterByVisualFilters(filterOneValue, filterTwoValue);

        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "severity~" + FilterOneLabel + ";policyType~" + FilterSecLabel + ";sortName~" + sortParam + ";sortOrder~asc;", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //UI operations
        landingPage.sortTable(sortLabel);

        //Aseert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel), "Sort Asc icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);

        //reset page number

        landingPage.sortTable(sortLabel);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "severity~" + FilterOneLabel + ";policyType~" + FilterSecLabel + ";sortName~" + sortParam + ";sortOrder~desc;", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //Aseert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel), "Sort Desc icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);

        //reset page number
        landingPage.clearAllFilter();

//reverse action should also work
        landingPage.filterByVisualFilters(filterOneValue, filterTwoValue);
        landingPage.sortTable(sortLabel);

        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel), "Sort asc icon not active " + sortParam);

        //landingPage.RefreshAndWait();
        landingPage.clearAllFilter();

        softAssert.assertAll();

    }

    //    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySortSearchCombo", description = "Verify combo sorting search functionality in violation landing ", priority = 11)
    public void verifyComboSortSearch(String sortApiPath, String sortParam, String sortLabel, int col, String searchApiPath, DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount, apiSearchValue;

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        landingPage.sortTable(sortLabel);

        //Get API values
        apiSearchValue = (String) RccRestUtils.getAPIFristValue(auth, params + "sortName~" + sortParam + ";sortOrder~asc", searchApiPath, BASE_URI + VIOLATION_LANDING_API);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~asc;search~" + apiSearchValue + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //UI operations
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel), "asc Sort icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);
        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount) / 10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " + apiSearchValue);

        landingPage.sortTable(sortLabel);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~desc;search~" + apiSearchValue + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel), "desc Sort icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);
        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount) / 10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " + apiSearchValue);

        //reset
        landingPage.clearSearch();

        landingPage.searchDNACField(dnacCat, apiSearchValue);
        landingPage.sortTable(sortLabel);

        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel), "asc Sort icon not active " + sortParam);
        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount) / 10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " + apiSearchValue);

        //landingPage.RefreshAndWait();
        softAssert.assertAll();

    }

    //    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySortSearchFilterCombo", description = "Verify sorting filter search functionality in violation landing ", priority = 12)
    public void verifyComboSortSearchFilter(String sortApiPath, String sortParam, String sortLabel, int col, String searchApiPath, DNAC_DropdownValue dnacCat, FilterValue filterOneValue, FilterValue filterTwoValue, String FilterOneLabel, String FilterSecLabel) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount, apiSearchValue;

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        landingPage.filterByVisualFilters(filterOneValue, filterTwoValue);

        //Get API values
        apiSearchValue = (String) RccRestUtils.getAPIFristValue(auth, params + "sortName~" + sortParam + ";sortOrder~asc;severity~" + FilterOneLabel + ";policyType~" + FilterSecLabel + ";", searchApiPath, BASE_URI + VIOLATION_LANDING_API);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~asc;search~" + apiSearchValue + ";severity~" + FilterOneLabel + ";policyType~" + FilterSecLabel + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //UI operations
        landingPage.sortTable(sortLabel);
        landingPage.searchDNACField(dnacCat, apiSearchValue);

        //Aseert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel), "Asc Sort icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);
        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount) / 10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " + apiSearchValue);

        landingPage.sortTable(sortLabel);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~desc;search~" + apiSearchValue + ";severity~" + FilterOneLabel + ";policyType~" + FilterSecLabel + ";", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //Aseert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel), "Desc Sort icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);
        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount) / 10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " + apiSearchValue);

        //reset
        landingPage.clearAllFilter();

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

    //    @Test(dataProviderClass = ViolationsPageData.class, dataProvider = "ViolationSummarySortDesc", description = "Verify sorting functionality in violation landing - desc default column ", priority = 20)
    public void verifySortDescSummaryListTable(String sortApiPath, String sortParam, String sortLabel, int col) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount;
        List<String> apiColumnValues = new ArrayList<String>();

        //UI Setup

        landingPage.sortTable(sortLabel);

        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~desc", pageCount, BASE_URI + VIOLATION_LANDING_API);
        //UI operations

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel), "Desc Sort icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);

        //reser page number

        landingPage.sortTable(sortLabel);
        apiTotalPagesCount = (String) RccRestUtils.getViolationCount(auth, params + "sortName~" + sortParam + ";sortOrder~asc", pageCount, BASE_URI + VIOLATION_LANDING_API);

        //Aseert

        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel), "Asc Sort icon not active " + sortParam);
        softAssert.assertEquals(landingPage.verifyShowingViolationsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter " + sortParam);

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }
}
