package com.cisco.tests.controlPoint.assets;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.controlPoint.assets.AssetGroupPage;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;

public class AssetGroupPageIT extends DriverBase {
    private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");

    public void loginAndNavigateToAdminPage(AssetGroupPage assetGroupPage){
        assetGroupPage.login()
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack);
        assetGroupPage.switchCXCloudAccount("AMERICAN NATIONAL INSURANCE COMPANY INC");
        assetGroupPage.clickAdminSettingsIcon();
    }

    @BeforeClass
    public void skipPopupIfPresent(){
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        loginAndNavigateToAdminPage(assetGroupPage);
        assetGroupPage.skipInfoPopups();
    }

   @Test(description = "Validate Asset Group Tab's Create Asset Group Link")
    public void checkAssetsGroupTabCreateAssetGroupLink() {
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed(),"Assert Group tab not displayed");
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkCreateAssertGroupLink(),"Create Asset Group link not present ");
        }
        softAssert.assertAll("checkAssetsGroupTabCreateAssetGroupLink");
    }

    @Test(description = "Validate Asset Group Tab's Asset Group's Natural Sorting order")
    public void checkNaturalSortAssetGroup(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.isNaturallySorted(),"Assert Groups are not correctly sorted");
        }
        softAssert.assertAll("checkNaturalSortAssetGroup");
    }

    @Test(description = "Validate Asset Group Tab's Asset Group's Reverse Sorting order")
    public void checkReverseSortAssetGroup(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.isReverselySorted(),"Assert Groups are not correctly sorted in reverse order");
        }
        softAssert.assertAll("checkReverseSortAssetGroup");

    }

   @Test(description = "Validate Asset Group Tab's Close button")
    public void checkCloseButton(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed(),"Assert Group tab not displayed");
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkIfCloseButtonPresent(), "Close button not present on Asset Groups Tab");
        }
        softAssert.assertAll("checkCloseButton");
    }

    @Test(description = "Verify if pagination is displayed on AssetGroup tab")
    public void checkIsPaginationPresent(){

        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed(),"Assert Group tab not displayed");
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkIfpaginationIsDisplayed(),"Pagination  not present on Asset Groups Tab");
        }
        softAssert.assertAll("checkIsPaginationPresent");
    }

    @Test(description = "Verify if search bar have close(X) button after entering value")
    public void checkCloseButtonInSearchBarWithValue(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed(),"Assert Group tab not displayed");
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkSearchBarWithCloseButton(),"Search bar not not have X button after entering vlaue");
        }
        softAssert.assertAll("checkCloseButtonInSearchBarWithValue");

    }

    @Test(description = "Validate Asset Group Tab's Search bar")
    public void checkAssetsGroupTabSearchBar() {
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertEquals(assetGroupPage.getPlaceHolderInSearchbar(),"Search asset groups","Search bar not matching");
        }
        softAssert.assertAll("checkAssetsGroupTabSearchBar");
    }

    @Test(description = "Validate Asset Group Tab's Title")
    public void checkAssetsGroupTabTitle() {
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertEquals(assetGroupPage.getAssetGroupPageHeading().trim(), "Asset Groups", "Asset Groups page title not matching, found "+assetGroupPage.getAssetGroupPageHeading().trim());
        }
        softAssert.assertAll("checkAssetsGroupTabTitle");
    }

    @Test(description = "Verify if pagination is working")
    public void checkIfPaginationWorking(){

        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed(),"Assert Group tab not displayed");
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertEquals(assetGroupPage.checkIfPaginationWorking(),"multiple page"," No sufficient data to validate pagination, only one page of data present");
        }
        softAssert.assertAll("checkIfPaginationWorking");
    }

    @Test(description="Verify if asset group search is working")
    public void checkAssetGroupSearch(){

        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkIfSearchWorking() , "Search in assert Group page is not working ");
        }
        softAssert.assertAll("checkAssetGroupSearch");
    }

    @Test(description = "Verify if popup is displayed after clinking  Create Asset Group ")
    public void checkIfPopupDisplayed(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkCreateAssertGroupModalDisplayed() , "Create Asset Group link not clickable and popup not displayed ");
        }
        assetGroupPage.closeCreateAssetGroupPopup();
        softAssert.assertAll("checkAssetGroupSearch");
    }

    @Test(description = "Verify if asset group is successfully created")
    public void checkCreationOFNewAssetGroup(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkCreateAssertGroupModalDisplayed() , "Create Asset Group link not clickable and popup not displayed ");
        }
        softAssert.assertTrue(assetGroupPage.createAssertGroup(),"AssertGroup creations is failing");
        softAssert.assertAll("checkCreationOFNewAssetGroup");
    }


    @Test(description = "Verify if deletion of asset group is working")//,dependsOnMethods = {"checkCreationOFNewAssetGroup"})
    public void checkDeletingOFAssetGroup(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
        }
        softAssert.assertTrue(assetGroupPage.deleteAssertGroup(),"Deleting of AssertGroup  is failing");
        softAssert.assertAll("checkDeletingOFAssetGroup");
    }

    @Test(description = "Verify if Title On Create AssetGroup link is present")
    public void checkTitleOnCreateAssetGroupPopup(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkCreateAssertGroupModalDisplayed() , "Title On Create Asset Group is not present");
        }
        softAssert.assertTrue(assetGroupPage.checkTitleOnCreateAssetGroupPopup(),"Assert Group title not present");

        softAssert.assertAll("checkTitleOnCreateAssetGroupPopup");
    }

    @Test(description = "Verify if Title On Create AssetGroup link is present")
    public void checkGroupNameFieldPresentOnCreateAssetGroupPopup(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkCreateAssertGroupModalDisplayed() , "Title On Create Asset Group is not present");
        }
        softAssert.assertTrue(assetGroupPage.checkGroupNameFieldOnCreateAssetGroupPopup(),"Group name field is not present");
        softAssert.assertAll("checkGroupNameFieldPresentOnCreateAssetGroupPopup");
    }

    @Test(description = "Verify if Title On Create AssetGroup link is present")
    public void checkAssertAttributeFieldOnCreateAssetGroupPopup(){
        SoftAssert softAssert = new SoftAssert();
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        softAssert.assertTrue(assetGroupPage.checkIfAssetGroupsTabDisplayed());
        if(assetGroupPage.checkIfAssetGroupsTabDisplayed()) {
            assetGroupPage.clickAssetGroupsTab();
            softAssert.assertTrue(assetGroupPage.checkCreateAssertGroupModalDisplayed() , "Title On Create Asset Group is not present");
        }
        softAssert.assertTrue(assetGroupPage.checkAssetAttributesFieldOnCreateAssetGroupPopup(),"Assert attribute field not present");
        softAssert.assertAll("checkAssertAttributeFieldOnCreateAssetGroupPopup");
    }

    @AfterClass
    public void cleanUp(){
        AssetGroupPage assetGroupPage = new AssetGroupPage();
        assetGroupPage.clickCloseButton();
    }
}