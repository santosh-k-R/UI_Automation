package com.cisco.tests.insights.compliance;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.compliance.ComplianceLandingPage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.DNAC_DropdownValue;
import com.cisco.testdata.StaticData.FilterValue;
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
import static com.cisco.testdata.insights.compliance.ViolationWithSystemsPageData.*;
import static org.testng.Assert.assertEquals;


public class ViolationWithSystemPageIT extends DriverBase {

    String params="solution~"+ TRACK+";customerId~"+ CUSTOMERID+";cxLevel~"+ CX_LEVEL+";saId~"+ SAID+";";
    private final String USERNAME = System.getProperty("username");
    private final String PASSWORD = System.getProperty("password");
    
    private static Header auth;
    ComplianceLandingPage landingPage;

    @BeforeClass(description = "Generating Headers for API Call")
    public void generateHeaders(){
        auth = TokenGenerator.getToken(USERNAME, PASSWORD);
        System.out.println("Headers : " + auth);
    }


//    @BeforeMethod(description = "Systems with Violation :Make sure we are loged in , select track and usecase And select the view")
    public void login() {
        landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        //landingPage.loginWithDetails(RccConstants.TRACKNAME,RccConstants.USERNAME,RccConstants.PASSWORD);
        landingPage.login();
        landingPage.switchCXCloudAccount(SMARTACCOUNT);
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(COMPLIANCE_TAB);
        landingPage.clickSysWithVioTab(SYSTEMS_VIOLATIONS_SUB_VIEW);
        landingPage.resetTable();
    }

    public void login(ComplianceLandingPage landingPage) {
        landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        //landingPage.loginWithDetails(RccConstants.TRACKNAME,RccConstants.USERNAME,RccConstants.PASSWORD);
        landingPage.login();
        landingPage.switchCXCloudAccount(SMARTACCOUNT);
        landingPage.resetContextSelector();
        landingPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, TRACK_UI);
        landingPage.selectCarousel(CarouselName.INSIGHTS);
        landingPage.clickCompianceTab(COMPLIANCE_TAB);
        landingPage.clickSysWithVioTab(SYSTEMS_VIOLATIONS_SUB_VIEW);
        landingPage.resetTable();
    }

    /*@Test(description = "Systems with Violation :Verify the count of compliance issues in insights Carousel tab ", priority = 1)
    public void verifyComplianceIssuesCarouselDetails() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        String apiComplianceIssuesCount;
        apiComplianceIssuesCount = (String) RccRestUtils.getViolationCount(auth,params,totalViolationCount,RccConstants.BASE_URI+RccConstants.VIOLATION_LANDING_FILTER_API);
        assertEquals(landingPage.getCountOnCarousel(RccConstants.COMPLIANCE_COUNT_CAROUSEL), apiComplianceIssuesCount, "Compliance issues count do not match on the PR tab");
    }*/

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Systems with Violation : Verify the overview count in compliance tab ,defaults sand other widgets", priority = 2)
    public void verifyOverViewCount() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiViolationsCount;
        String apiSystemsWithViolationsCount;

        apiSystemsWithViolationsCount = (String) RccRestUtils.getViolationCount(auth,params,totalViolatedDevices, BASE_URI+ ASSET_LANDING_API);

        System.out.println("apiSystemsWithViolationsCount : " + apiSystemsWithViolationsCount);
        login(landingPage);
        //Assertions
        softAssert.assertTrue(landingPage.checkActiveTab(COMPLIANCE_TAB), "Compliance tab isnt active");
        softAssert.assertTrue(!(landingPage.checkActiveSubTab(VIOLATIONS_SUB_VIEW)), "Violations sub tab is active");
        softAssert.assertTrue(landingPage.checkActiveSubTab(SYSTEMS_VIOLATIONS_SUB_VIEW), "Sytems with Violation isnt active");
        softAssert.assertTrue(!(landingPage.checkFilterApplied()), "Filters are applied");
        softAssert.assertTrue(landingPage.checkDefualtSearchWithAll(), "Search tab isnt with default All catogory");
        softAssert.assertEquals(landingPage.getPlaceholderText(), SEARCH_PLACEHOLDER,"Search placeholder is differnt");
        softAssert.assertEquals((landingPage.getCountSubTabViolations(SYSTEMS_VIOLATIONS_SUB_VIEW)).replace(",",""), apiSystemsWithViolationsCount, "Impacted Assets count do not match on the sub tab");
    }


//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, description = "Systems with Violation :Verify the info message in violation landing view and table order", priority = 3)
    public void verifyTableOrderAndInfo() throws InterruptedException {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        List<String> AssetListTableHeaders;

        login(landingPage);
        AssetListTableHeaders= landingPage.getAllTableTitles();
        AssetListTableHeaders.remove(AssetListTableHeaders.size()-1);

        //workaroud as empty tr is present
        System.out.println("Asset "+AssetListTableHeaders);
        softAssert.assertTrue(AssetListTableHeaders.equals(ASSET_SUMMARY_TABLE), "Table order not maintained");
      //  softAssert.assertEquals(landingPage.getToolTipData(RccConstants.VIOLATIONS), RccConstants.VIOLATIONS_TOOL_TIP, "Violation Tool tip is different than expected");
      //  softAssert.assertEquals(landingPage.getToolTipData(RccConstants.SOURCE), RccConstants.SOURCE_TOOL_TIP, "Source Tool tip is different than expected");
      //  softAssert.assertEquals(landingPage.getToolTipData(RccConstants.HIGHEST_SEVERITY), RccConstants.HIGHEST_SEVERITY_TOOL_TIP_ASSET, "Highest severity Tool tip is different than expected");
        softAssert.assertAll();
    }

//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, description = "Systems with Violation :Verify Default sort on high severity working in violation landing ", priority = 4)
    public void verifyDefaultSortAssetSummaryListTable() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        List<String> apiColumnValues= new ArrayList<String>();
        List<String> uiColumnValues= new ArrayList<String>();

        login(landingPage);
        System.out.println("Headers : " + auth);
        apiColumnValues = RccRestUtils.getValuesOfColumn(auth,params, highestSeverity, BASE_URI+ ASSET_LANDING_API);
        System.out.println("List"+apiColumnValues.size());
        uiColumnValues= landingPage.verifyColmmnValue(6);

        //Aseert
        softAssert.assertEquals(uiColumnValues.size(),apiColumnValues.size());
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(HIGHEST_SEVERITY),"default asc Sort icon not active ");
        softAssert.assertTrue(uiColumnValues.equals(apiColumnValues), "Default sort on high severity isnt working as expected ");

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider="ViolationSummaryFilter",description = "Systems with Violation :Verify the filter functionality / pagination / filter tag.", priority = 5)
    public void verifyFilterViolationSummary(String filterValue,FilterValue filterBy,int columnValue,String paramName) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount;
        Map<String, Integer> s3Map;

        //Get API values
        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+paramName+"~"+filterValue+";",pageCount, BASE_URI+ ASSET_LANDING_API);
        System.out.println("apiTotalPagesCount : " + apiTotalPagesCount);

        login(landingPage);
        landingPage.filterByVisualFilters(filterBy);
        s3Map = landingPage.verifyValueOnViolationView(columnValue, ASSET_LANDING_COL_INDEX_VIO);

        //Assertions
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible for "+ filterValue);
        softAssert.assertEquals((s3Map.size())-1, 1, "Violations other than "+filterValue+" are present after filtering");
        softAssert.assertTrue(s3Map.containsKey(filterValue), "Violations other than "+filterValue+" are present after filtering");
        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter for "+ filterValue);

        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount)/10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for "+filterValue+"  regulatory type");
        landingPage.clearSpecificFilter(filterValue);
        softAssert.assertAll();
    }

//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider="ViolationSummarySearch",description = "Systems with Violation Verify the search functionality", priority = 6)
    public void verifySearchViolationSummary(String searchApiPath, DNAC_DropdownValue dnacCat,boolean caseSen) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String apiViolationsCount,apiTotalPagesCount,apiSearchValue;

        login(landingPage);
        //Get API values
        apiSearchValue =  (String) RccRestUtils.getAPIFristValue(auth,params,searchApiPath, BASE_URI+ ASSET_LANDING_API);
        if(caseSen)
            apiSearchValue = apiSearchValue.toLowerCase();

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"search~"+apiSearchValue+";",pageCount, BASE_URI+ ASSET_LANDING_API);
        //Execution in UI
        landingPage.searchDNACField(dnacCat,apiSearchValue);

        //Assertions
        softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After search page didnt rest");
        softAssert.assertTrue(landingPage.verifySearch(apiSearchValue), "Other than search Value is present " +apiSearchValue);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per search");
       // softAssert.assertEquals(landingPage.verifyCountOnViolationView(RccConstants.ASSET_LANDING_COL_INDEX_VIO), apiViolationsCount, "Sum of Violation count same as total violation count");
//        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount)/10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " +apiSearchValue);
        //Cleanup
        landingPage.clearSearch();
        softAssert.assertEquals(landingPage.getPlaceholderText(), SEARCH_PLACEHOLDER,"Search placeholder is differnt");

        //landingPage.RefreshAndWait();
        softAssert.assertAll();

    }

//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider = "ViolationSummarySearchFilterCombo",description = "Systems with Violation :Verify  functionality for the combinations of  filter and search with category", priority = 8)
    public void verifyCombinationOfVisualFiltersAndSearch(String searchApiPath, DNAC_DropdownValue dnacCat,FilterValue filterOneValue,String FilterOneLabel)    {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();

        login(landingPage);
        String apiViolationsCount,apiTotalPagesCount,apiSearchValue;
        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //Get API values
        apiSearchValue =  (String) RccRestUtils.getAPIFristValue(auth,params+"severity~"+FilterOneLabel+";",searchApiPath, BASE_URI+ ASSET_LANDING_API);
        apiTotalPagesCount =(String) RccRestUtils.getViolationCount(auth,params+"severity~"+FilterOneLabel+";search~"+apiSearchValue+";",pageCount, BASE_URI+ ASSET_LANDING_API);

        //Execution in UI
        landingPage.filterByVisualFilters(filterOneValue);
        landingPage.searchDNACField(dnacCat,apiSearchValue);

        //Assertions
        softAssert.assertTrue(landingPage.verifySearch(apiSearchValue), "Other than search Value is present " +apiSearchValue);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter");
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
       // softAssert.assertEquals(landingPage.verifyCountOnViolationView(RccConstants.ASSET_LANDING_COL_INDEX_VIO), apiViolationsCount, "Sum of Violation count same as total violation count");

        //Cleanup
        landingPage.clearAllFilter();

        //reverse should also work
//        landingPage.searchDNACField(dnacCat,apiSearchValue);
//        landingPage.filterByVisualFilters(filterOneValue);
//        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
//        softAssert.assertTrue(landingPage.verifySearch(apiSearchValue), "Other than search Value is present " +apiSearchValue);
//        landingPage.clearAllFilter();

        softAssert.assertAll();
    }

//    Not Working
//    @Test(description = "Systems with Violation :Verify on filter pagination  rest in violation landing ", priority = 9)
    public void verifyFilterPaginationReset() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String PageCountBeforeFilter ;

        login(landingPage);
        PageCountBeforeFilter = landingPage.getTotalCountOfPages();
        //UI operations
        landingPage.goToLastPage();
        landingPage.filterByVisualFilters(RG_CRITICAL_FILTER);
        //Aseert
        softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After filter page didnt rest");
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filters are applied");

        PageCountBeforeFilter = landingPage.getTotalCountOfPages();
        landingPage.clearSpecificFilter(SEVERITY_CRITICAL_FILTER_VALUE);
        if(!PageCountBeforeFilter.equals("1")) {
            landingPage.navigateToPage(Integer.valueOf(PageCountBeforeFilter)-1);
            landingPage.filterByVisualFilters(RG_CRITICAL_FILTER);
            softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After filter page didnt rest");
            softAssert.assertTrue(landingPage.checkFilterApplied(), "Filters are applied");
        }
        //Cleanup
        landingPage.clearSpecificFilter(SEVERITY_CRITICAL_FILTER_VALUE);
        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

//Not Working
//    @Test(timeOut = 1200000, description = "Systems with Violation :Verify on sort pagination is rest in violation landing ", priority = 10)
    public void verifySortPaginationNotReset() {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
//        SoftAssert softAssert = new SoftAssert();
        String pageCountBeforeSort;
        pageCountBeforeSort = landingPage.getTotalCountOfPages();
        System.out.println("pageCountBeforeSort : " + pageCountBeforeSort);
        //UI operations
        if(!pageCountBeforeSort.equals("1"))
            landingPage.navigateToPage(Integer.parseInt(pageCountBeforeSort));

        landingPage.sortTable(SYS_NAME);
        //Assert
        assertEquals((landingPage.getCurrentPage()), "1", "After sort page number didnt reset to 1 from last page");

        if(!pageCountBeforeSort.equals("1")) {
            landingPage.navigateToPage(Integer.parseInt(pageCountBeforeSort)-1);
            landingPage.sortTable(SYS_NAME);
            assertEquals((landingPage.getCurrentPage()), "1", "After sort page number didnt change from the n-1 page");
        }
//        softAssert.assertAll();
    }

    //Not Working
//    @Test(timeOut = 1200000, dataProviderClass = ViolationWithSystemsPageData.class, dataProvider = "ViolationSummarySearchPaginationReset",description = "Systems with Violation :Verify on search pagination  rest in violation landing ", priority = 11)
    public void verifySearchPaginationReset(String searchApiPath, DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        String PageCountBeforeFilter,apiSearchValue;

        login(landingPage);
        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        apiSearchValue =  (String) RccRestUtils.getAPIFristValue(auth,params,searchApiPath, BASE_URI+ ASSET_LANDING_API);

        PageCountBeforeFilter = landingPage.getTotalCountOfPages();

        //UI operations
        landingPage.goToLastPage();
        landingPage.searchDNACField(dnacCat,apiSearchValue);

        //Aseert
        softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After search page didnt rest");

        if(PageCountBeforeFilter.equals("1")) {
            landingPage.navigateToPage(Integer.valueOf(PageCountBeforeFilter)-1);
            landingPage.searchDNACField(dnacCat,apiSearchValue);
            softAssert.assertTrue((landingPage.getCurrentPage()).equals("1"), "After search page didnt rest");
        }
        //Pagination reset
        landingPage.clearSearch();
        softAssert.assertAll();
    }
    
//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider ="ViolationSummarySearchNoResult",description = "Systems with Violation :Verify the search functionality is for no results found ", priority = 12)
    public void verifySearchNoResult(DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();
        String apiViolationsCount,apiTotalPagesCount;
        String apiSearchValue= SEARCH_INVALID_STRING;

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //Execution in UI
        landingPage.searchDNACField(dnacCat,apiSearchValue);

        //Assertions
        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), "0", "Violation count in showing message as per search");
        softAssert.assertTrue(landingPage.checkNoResultFound(),"No resullts found banner isnt displayed");
        softAssert.assertTrue(!landingPage.checkTableVisible());
        //Cleanup
        landingPage.clearSearch();
        softAssert.assertTrue(landingPage.checkTableVisible());
        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }
    
//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider = "ViolationSummarySearchSortNoResult",description = "Systems with Violation :Verify the  no result found for :   combinations of  sort and search with categoty", priority = 13)
    public void verifyCombinationOfSortndSearchNoResult(DNAC_DropdownValue dnacCat,String sortLabel) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();
        String apiViolationsCount,apiTotalPagesCount,apiSearchValue;

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //Get API values
        apiSearchValue =  SEARCH_INVALID_STRING;
        //Execution in UI
        landingPage.sortTable(sortLabel);
        landingPage.searchDNACField(dnacCat,apiSearchValue);
        //Assertions
        softAssert.assertTrue(landingPage.checkNoResultFound(),"No results found banner isnt displayed");
        landingPage.clearSearch();
        //landingPage.RefreshAndWait();
        softAssert.assertAll();
        landingPage.sortTable(sortLabel);
    }
    
//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider = "ViolationSummaryFilterSearchNoResult",description = "Systems with Violation :Verify the  no result found for : visual filter functionality for the combinations of  filter and search with category", priority = 14)
    public void verifyCombinationOfVisualFiltersAndSearchNoResult(DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();

        String apiSearchValue =  SEARCH_INVALID_STRING;

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //Execution in UI
        landingPage.filterByVisualFilters(RG_CRITICAL_FILTER);
        landingPage.searchDNACField(dnacCat,apiSearchValue);
        //Assertions
        softAssert.assertTrue(landingPage.checkNoResultFound(),"No resullts found banner isnt displayed");
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), "0", "Violation count in showing message as per filter");
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        //Cleanup
        landingPage.clearAllFilter();

        softAssert.assertAll();
    }
    
//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider = "ViolationSummaryFilterSearchSortNoResult",description = "Systems with Violation :Verify the  no result found for : visual filter functionality for the combinations of  filter and search with categ", priority = 15)
    public void verifyCombinationOfVisualFiltersSearchAndSortNoResult(DNAC_DropdownValue dnacCat,String sortLabel) {

        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();

        String apiSearchValue;

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //Get API values
        apiSearchValue =  SEARCH_INVALID_STRING;

        //Execution in UI
        landingPage.sortTable(sortLabel);
        landingPage.filterByVisualFilters(RG_CRITICAL_FILTER);
        landingPage.searchDNACField(dnacCat,apiSearchValue);

        //Assertions
        softAssert.assertTrue(landingPage.checkNoResultFound(),"No resullts found banner isnt displayed");
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), "0", "Violation count in showing message as per filter");
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");

        //Cleanup
        landingPage.clearAllFilter();
        landingPage.sortTable(sortLabel);

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

    //    Need to Fix
//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider ="ViolationSummarySort", description = "Systems with Violation :Verify sorting functionality in violation landing ", priority = 7)
    public void verifySortSummaryListTable(String sortApiPath,String sortParam,String sortLabel,int col) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount;
        List<String> apiColumnValues= new ArrayList<String>();
        List<String> uiColumnValues= new ArrayList<String>();

        apiColumnValues = RccRestUtils.getValuesOfColumn(auth,params+"sortName~"+sortParam+";sortOrder~asc",sortApiPath, BASE_URI+ ASSET_LANDING_API );
        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~asc",pageCount, BASE_URI+ ASSET_LANDING_API);

        //UI operations
        if(!sortParam.equals("ruleSeverityId"))
            landingPage.sortTable(sortLabel);

        uiColumnValues= landingPage.verifyColmmnValue(col);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel),"Asc ort icon not active "+ sortParam);
        softAssert.assertEquals(uiColumnValues.size(),apiColumnValues.size(),"Number of records not same after sorting "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);

        //reset page number
        //landingPage.navigateToPage(1);
        landingPage.sortTable(sortLabel);
        uiColumnValues= landingPage.verifyColmmnValue(col);
        apiColumnValues = RccRestUtils.getValuesOfColumn(auth,params+"sortName~"+sortParam+";sortOrder~desc", sortApiPath, BASE_URI+ ASSET_LANDING_API);
        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~desc",pageCount, BASE_URI+ ASSET_LANDING_API);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel),"Desc Sort icon not active "+ sortParam);
        softAssert.assertEquals(uiColumnValues.size(),apiColumnValues.size(),"Number of records not same after sorting "+ sortParam);

        //softAssert.assertTrue(uiColumnValues.equals(apiColumnValues), "desc sort isnt working as expected "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);

        landingPage.sortTable(sortLabel);

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }


//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider ="ViolationSummarySortDesc", description = "Systems with Violation :Verify sorting functionality in violation landing ", priority = 19)
    public void verifySortDescSummaryListTable(String sortApiPath,String sortParam,String sortLabel,int col) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount;
        //UI operations

        if(!sortParam.equals("ruleSeverityId"))
            landingPage.sortTable(sortLabel);

        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~desc",pageCount, BASE_URI+ ASSET_LANDING_API);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel),"Desc Sort icon not active "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);
        //reset page number
        //landingPage.navigateToPage(1);
        landingPage.sortTable(sortLabel);
        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~asc",pageCount, BASE_URI+ ASSET_LANDING_API);
        //Assert
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel),"Asc ort icon not active "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);

        //landingPage.RefreshAndWait();
        softAssert.assertAll();
    }

//    @Test(dataProviderClass = ViolationWithSystemsPageData.class ,dataProvider ="ViolationSummarySortFilterCombo", description = "Systems with Violation :Verify combination of filter and sorting functionality in violation landing ", priority = 16)
    public void verifyCombinationOfVisualFiltersAndSort(String sortApiPath,String sortParam,String sortLabel,int col,FilterValue filterOneValue,String FilterOneLabel) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        SoftAssert softAssert = new SoftAssert();
        login(landingPage);

        String apiViolationsCount,apiTotalPagesCount;

        if(!sortParam.equals("ruleSeverityId"))
            landingPage.sortTable(sortLabel);

        apiTotalPagesCount =(String) RccRestUtils.getViolationCount(auth,params+"severity~"+FilterOneLabel+";sortName~"+sortParam+";sortOrder~asc;",pageCount, BASE_URI+ ASSET_LANDING_API);
        landingPage.filterByVisualFilters(filterOneValue);
        //UI operations
        //Aseert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel),"Sort asc icon not active "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);

        landingPage.sortTable(sortLabel);
        apiTotalPagesCount =(String) RccRestUtils.getViolationCount(auth,params+"severity~"+FilterOneLabel+";sortName~"+sortParam+";sortOrder~desc;",pageCount, BASE_URI+ ASSET_LANDING_API);

        //Assert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel),"Sort desc icon not active "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);

        landingPage.clearAllFilter();
        //reverse action should also work
        landingPage.sortTable(sortLabel);
        landingPage.filterByVisualFilters(filterOneValue);

        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel),"asc Sort icon not active "+ sortParam);
        
        landingPage.clearAllFilter();
        softAssert.assertAll();
    }
    
//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider ="ViolationSummarySortSearchCombo", description = "Systems with Violation :Verify combo sorting search functionality in violation landing ", priority = 17)
    public void verifyComboSortSearch(String sortApiPath,String sortParam,String sortLabel,int col,String searchApiPath, DNAC_DropdownValue dnacCat) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount,apiSearchValue;

        //UI operations
        if(!sortParam.equals("ruleSeverityId"))
            landingPage.sortTable(sortLabel);

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        //Get API values
        apiSearchValue =  (String) RccRestUtils.getAPIFristValue(auth,params,searchApiPath, BASE_URI+ ASSET_LANDING_API);
        landingPage.searchDNACField(dnacCat,apiSearchValue);

        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~asc;search~"+apiSearchValue+";",pageCount, BASE_URI+ ASSET_LANDING_API);
        System.out.println("Asc : apiTotalPagesCount ::: " + apiTotalPagesCount);

        //Aseert
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel),"asc Sort icon not active "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);
//        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount)/10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " +apiSearchValue);

        landingPage.sortTable(sortLabel);
        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~desc;search~"+apiSearchValue+";",pageCount, BASE_URI+ ASSET_LANDING_API);
        System.out.println("Desc : apiTotalPagesCount ::: " + apiTotalPagesCount);

        //Assert
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel)," desc Sort icon not active "+ sortParam);
//        softAssert.assertEquals(landingPage.verifyShowingAssetsNumbers(), apiTotalPagesCount, "Violation count in showing message as per filter "+ sortParam);
//        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount)/10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " +apiSearchValue);

        //reset
        landingPage.sortTable(sortLabel);
        landingPage.clearSearch();

        softAssert.assertAll();
    }
    
//    @Test(dataProviderClass = ViolationWithSystemsPageData.class, dataProvider ="ViolationSummarySortSearchFilterCombo", description = "Systems with Violation :Verify sorting filter search functionality in violation landing ", priority = 18)
    public void verifyComboSortSearchFilter(String sortApiPath,String sortParam,String sortLabel,int col,String searchApiPath, DNAC_DropdownValue dnacCat,FilterValue filterOneValue,String FilterOneLabel) {
        ComplianceLandingPage landingPage = new ComplianceLandingPage();
        login(landingPage);
        SoftAssert softAssert = new SoftAssert();
        String apiTotalPagesCount,apiSearchValue;

        landingPage.filterByVisualFilters(filterOneValue);

        if (dnacCat.equals(DNAC_DropdownValue.DNA_CENTER_APPLIANCES))
            params = params.concat("category~dnac;");

        if(!sortParam.equals("ruleSeverityId"))
            landingPage.sortTable(sortLabel);

        //Get API values
        apiSearchValue =  (String) RccRestUtils.getAPIFristValue(auth,params+"sortName~"+sortParam+";sortOrder~asc;severity~"+FilterOneLabel+";",searchApiPath, BASE_URI+ ASSET_LANDING_API);
        landingPage.searchDNACField(dnacCat,apiSearchValue);
        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~asc;search~"+apiSearchValue+";severity~"+FilterOneLabel+";",pageCount, BASE_URI+ ASSET_LANDING_API);
        System.out.println("Asc : apiTotalPagesCount ::: " + apiTotalPagesCount);

        //Aseert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible");
//        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount)/10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " +apiSearchValue);
        softAssert.assertTrue(landingPage.checkSortActiveWithAscSign(sortLabel),"asc Sort icon not active "+ sortParam);

        //reser page number
        // landingPage.navigateToPage(1);
        landingPage.sortTable(sortLabel);
        apiTotalPagesCount =(String)RccRestUtils.getViolationCount(auth,params+"sortName~"+sortParam+";sortOrder~desc;search~"+apiSearchValue+";severity~"+FilterOneLabel+";",pageCount, BASE_URI+ ASSET_LANDING_API);
        System.out.println("Desc : apiTotalPagesCount ::: " + apiTotalPagesCount);
        //Aseert
        softAssert.assertTrue(landingPage.checkFilterApplied(), "Filter tag is not visible after desc order");
//        softAssert.assertEquals(landingPage.getTotalCountOfPages(), String.valueOf(Integer.parseInt(apiTotalPagesCount)/10 + (Integer.parseInt(apiTotalPagesCount) % 10 == 0 ? 0 : 1)), "Total page count not matching for search Value " +apiSearchValue);
        softAssert.assertTrue(landingPage.checkSortActiveWithDecSign(sortLabel),"desc Sort icon not active "+ sortParam);

        landingPage.sortTable(sortLabel);
        landingPage.clearAllFilter();
        softAssert.assertAll();
    }

}