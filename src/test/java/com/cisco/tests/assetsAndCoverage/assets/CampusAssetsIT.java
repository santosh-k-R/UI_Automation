package com.cisco.tests.assetsAndCoverage.assets;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.assetsAndCoverage.assets.AllAssetsPage;
import com.cisco.testdata.AssetsDataProvider;
import com.cisco.testdata.AssetsExcelDataReader;
import com.cisco.testdata.AssetsUIPojo;
import com.cisco.utils.Commons;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static com.cisco.testdata.Data.*;

public class CampusAssetsIT extends DriverBase {
    private String cxCloudAccount = ASSETS_AND_DIAGNOSTICS_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
    private String cxCloudPartyId = ASSETS_AND_DIAGNOSTICS_COMMON_DATA.get("CX_CLOUD_ACCOUNT_ID");
    private AssetsUIPojo systemData = AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ASSETS_SYSTEM_CAMPUS");

    @Test(description = "Campus Assets login Test", priority = 1)
    public void verifyCampusAssetsLogin() {
        AllAssetsPage assetsPage = new AllAssetsPage();
       // assetsPage.login("assets").switchCXCloudAccount(cxCloudAccount, cxCloudPartyId);
        assetsPage.login("assets").switchCXCloudAccount(cxCloudAccount);
        assetsPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus");
        Assert.assertEquals(assetsPage.getAssetsTitle(), "ASSETS & COVERAGE", "Incorrect Carousel naming convention");
    }

    @Test(description = "Verify presence of Assets and Contract tabs", priority = 2)
    public void navigateToAssets() {
        SoftAssert softAssert = new SoftAssert();
        AllAssetsPage assetsPage = new AllAssetsPage();
        softAssert.assertEquals(assetsPage.clickOnAssets(), "Assets", "Assets tab is not selected");
        softAssert.assertEquals(assetsPage.verifyContractsTab(), "Contracts", "Contracts tab is not present");
        softAssert.assertAll();
    }

    @Test(description = "Verify System count matches expected count", priority = 3, groups = "pre-condition checks")
    public void verifySystemCount() {
        AllAssetsPage assetsPage = new AllAssetsPage();
        Assert.assertEquals(assetsPage.getAssetCount(), Integer.parseInt(systemData.getSystemCount()), "No asset data on this account");
    }

    @Test(description = "Validate visual filters titles", priority = 4, groups = "visual filter title checks", dataProvider = "visualFiltersAll", dataProviderClass = AssetsDataProvider.class)
    public void verifyVisualFiltersTitle(String visualFilterTitle) {
        AllAssetsPage assetsPage = new AllAssetsPage();
        Assert.assertEquals(assetsPage.getVisualFilterTitle(visualFilterTitle), visualFilterTitle, "Mismatch in visual filter title");
    }

    @Test(description = "Validate label in visual filters", priority = 5, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
    public void verifyLabelInVisualFilters__CONNECTIVITY(String filterLabel) {
        AssetsUIPojo visualFilterObj = AssetsExcelDataReader
                .visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA)
                .get(filterLabel);
        AllAssetsPage assetsPage = new AllAssetsPage();
        SoftAssert softAssert = new SoftAssert();
        String actualFilterLabel=assetsPage.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
        softAssert.assertEquals(actualFilterLabel, filterLabel, "Filter title not Matched/Selected");
        assetsPage.clearAllFilter();
        softAssert.assertAll();
    }

    @Test(description = "Validate label in visual filters", dependsOnMethods = {"verifyLabelInVisualFilters__CONNECTIVITY"}, priority = 6, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
    public void verifyVisualFilterCount__CONNECTIVITY(String filterLabel) {
        AssetsUIPojo visualFilterObj = AssetsExcelDataReader
                .visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA)
                .get(filterLabel);
        AllAssetsPage assetsPage = new AllAssetsPage();
        SoftAssert softAssert = new SoftAssert();
        assetsPage.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
        softAssert.assertEquals(assetsPage.getAssetCount(), Integer.parseInt(visualFilterObj.getCampusFilterCount()), "Mismatch found in connected data");
        assetsPage.clearAllFilter();
        softAssert.assertAll();
    }

    @Test(description = "Validate label in visual filters", priority = 7, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
    public void verifyLabelInVisualFilters__COVERAGE(String filterLabel) {
        AssetsUIPojo visualFilterObj = AssetsExcelDataReader
                .visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA)
                .get(filterLabel);
        AllAssetsPage assetsPage = new AllAssetsPage();
        SoftAssert softAssert = new SoftAssert();
        String actualFilterLabel=assetsPage.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
        softAssert.assertEquals(actualFilterLabel, filterLabel, "Filter title not Matched/Selected");
        assetsPage.clearAllFilter();
        softAssert.assertAll();
    }

    @Test(description = "Validate label in visual filters", dependsOnMethods = {"verifyLabelInVisualFilters__COVERAGE"}, priority = 6, groups = "visual filter label checks", dataProvider = "visualFiltersValues", dataProviderClass = AssetsDataProvider.class)
    public void verifyVisualFilterCount__COVERAGE(String filterLabel) {
        AssetsUIPojo visualFilterObj = AssetsExcelDataReader
                .visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA)
                .get(filterLabel);
        AllAssetsPage assetsPage = new AllAssetsPage();
        SoftAssert softAssert = new SoftAssert();
        assetsPage.selectVisualFilter(Commons.parseStringtoList(visualFilterObj.getFilterValues()), filterLabel);
        softAssert.assertEquals(assetsPage.getAssetCount(), Integer.parseInt(visualFilterObj.getCampusFilterCount()), "Mismatch found in connected data");
        assetsPage.clearAllFilter();
        softAssert.assertAll();
    }

    @Test(description = "Validate column selector", priority=4, groups="pre-condition checks", enabled=false)
    public void validateColumnsSelected() {
        SoftAssert softAssert = new SoftAssert();
        AllAssetsPage assetsPage = new AllAssetsPage();
        assetsPage.deSelectColumns(assetsPage.setColumns(Commons.parseStringtoList(systemData.getColumnsToDeSelect())));
        Map<String, String> mp = assetsPage.setColumns(Commons.parseStringtoList(systemData.getColumnsToSelect()));
        assetsPage.selectColumns(mp);
        // softAssert.assertTrue(false, "");
    }

}

