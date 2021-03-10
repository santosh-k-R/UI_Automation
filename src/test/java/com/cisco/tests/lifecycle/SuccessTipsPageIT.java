package com.cisco.tests.lifecycle;

import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.List;

import com.cisco.pages.lifecycle.LifecyclePage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.PitStopsName;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.lifecycle.SuccessTipsPage;
import com.cisco.testdata.StaticData.ButtonName;

public class SuccessTipsPageIT extends DriverBase {
	
	private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");
    private String networkDeviceOnboardingUsecase = LIFECYCLE_COMMON_DATA.get("USE_CASE_1");
    private String campusNetworkAssuranceUsecase = LIFECYCLE_COMMON_DATA.get("USE_CASE_2");
    private String ScalableAccessPolicyUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_3");
    private String cxCloudAccount = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
	private static ThreadLocal<LifecyclePage> page = ThreadLocal.withInitial(() -> new LifecyclePage());

	@Test(groups = {"sanity"}, description = "PBC-142: T122954: UI Layout")
	public void verifySuccessTipsUILayout() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.clickSuccessTipsViewAllLink();
		assertEquals(successTipsPage.getWindowTitleInViewAllModal(), "Product Documentation","Wrong window title is being displayed");
		successTipsPage.clickSuccessTipsCardModalButton();
		successTipsPage.getAllTopicUnderSuccessTips();
		successTipsPage.getTotalNumberOfTopicsDisplayed();
	}
	
	@Test(description = "PBC-188: T122944: Web Product Guides open in a new tab")
	public void verifySuccessTipsWebOpenInNewTab() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking in Card View
		successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
		
		//Check data availability
		if(successTipsPage.getTotalNoOfTopicsDisplayed(SuccessTipsPage.ProductDocWebIconDataAutoIdInCardView) == 0)
			throw new SkipException("No Product Documentation topics with Video type.(No Data to test this scenario)");
				
		assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.ProductDocWebIconDataAutoIdInCardView), 2, "Product Documentation Web Topics is not displaying in new tab from Card View");
		successTipsPage.switchTab();
		successTipsPage.switchToMainTab();
		
		//Checking in Table View
		successTipsPage.clickSuccessTipsTableModalButton();
		assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.SuccessBytesWebIconDataAutoIdInTableView), 2, "Product Documentation Web Topics is not displaying in new tab from Table View");
		successTipsPage.switchTab();
		successTipsPage.switchToMainTab();
	}
	
	@Test(description = "T122943: PBC-188: PDF Product Guides open in a new tab")
	public void verifySuccessTipsPDFOpenInNewTab() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, ScalableAccessPolicyUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking in Card View
		successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
		
		//Check data availability
		if(successTipsPage.getTotalNoOfTopicsDisplayed(SuccessTipsPage.ProductDocPDFIconDataAutoIdInCardView) == 0)
			throw new SkipException("No Product Documentation topics with Video type.(No Data to test this scenario)");
				
		assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.ProductDocPDFIconDataAutoIdInCardView), 2, "Product Documentation PDF Topics is not displaying in new tab from Card View");
		successTipsPage.switchTab();
		successTipsPage.switchToMainTab();
		
		//Checking in Table View
		successTipsPage.clickSuccessTipsTableModalButton();
		assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.SuccessBytesPDFIconDataAutoIdInTableView), 2, "Product Documentation PDF Topics is not displaying in new tab from Table View");
		successTipsPage.switchTab();
		successTipsPage.switchToMainTab();
	}
	
	@Test(description = "PBC-569: T136061: Success Bytes and Product Guides cards should have View button")
	public void verifyViewButtonOnSuccessGuidesCards() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.clickSuccessTipsViewAllLink();
		List<String> viewButtonTexts = successTipsPage.getAllViewButtonText();
		viewButtonTexts.forEach(buttonText -> assertEquals(buttonText, ButtonName.VIEW.getButtonName().toUpperCase(), "Button Text is wrong"));
	}
	
	@Test(description = "PBC-188: T122946: Video Product Guides open in a new tab")
	public void verifySuccessTipsVideoOpenInNewTab() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Checking in Card View
		successTipsPage.changePitStop(PitStopsName.Onboard);
		successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
		
		//Check data availability
		if(successTipsPage.getTotalNoOfTopicsDisplayed(SuccessTipsPage.ProductDocVideoIconDataAutoIdInCardView) == 0)
			throw new SkipException("No Product Documentation topics with Video type.(No Data to test this scenario)");
				
		assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.ProductDocVideoIconDataAutoIdInCardView), 2, "Product Documentation Video topics are not opening in new tab from Card View");
		successTipsPage.switchTab().switchToMainTab();
		
		//Checking in Table View
		successTipsPage.clickSuccessTipsTableModalButton();
		assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.SuccessBytesVideoIconDataAutoIdInTableView), 2, "Product Documentation Video topics are not opening in new tab from Table View");
		successTipsPage.switchTab().switchToMainTab();
	}
	
	@Test(description = "PBC-201: T122948: Product Guides section shows at most 3 links")
	public void verifySuccessBytesShows3Link() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		assertEquals(successTipsPage.getAllSuccessTipsTopicUnderLifecycle(), 3, "Product Documentation is not displaying 3 links in the hom page");
	}
	
	@Test(description = "PBC-569: T136062: Success Bytes and Product Guides cards type icons should no longer cross-launch")
	public void verifySuccessTipsCardView() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.clickSuccessTipsViewAllLink();
		assertEquals(successTipsPage.getWindowTitleInViewAllModal(), "Product Documentation", "Wrong window title is being displayed");
		assertEquals(successTipsPage.getTotalNumberOfTabsOpened(), 1, "Card type icon is displaying in new tab");
	}
	
	/*@Test(description = "PBC-459: T133955: Clicking an item's title or content type should cross-launch")
	public void verifyProductGuidesCrossLaunch() {
		SoftAssert softAssert = new SoftAssert();
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchSmartAccount(cxCloudAccount)
				.selectMainDropDown(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectMainDropDown(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.clickProdDocAndVideosButton();
		
		//Verifying Clicking on Item's Title Opening in a new tab
		softAssert.assertEquals(successTipsPage.getTopicsByClickingOnTile(SuccessTipsPage.ProductGuideWebIconDataAutoId), 2, "Product Guides Web topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		softAssert.assertEquals(successTipsPage.getTopicsByClickingOnTile(SuccessTipsPage.ProductGuideVideoIconDataAutoId), 2, "Product Guides Video topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		softAssert.assertEquals(successTipsPage.getTopicsByClickingOnTile(SuccessTipsPage.ProductGuidePDFIconDataAutoId), 2, "Product Guides PDF topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		softAssert.assertEquals(successTipsPage.getTopicsByClickingOnTile(SuccessTipsPage.ProductGuideDataSheetIconDataAutoId), 2, "Product Guides Data Sheet topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		
		//Verifying Clicking on View Button Opening in a new tab
		softAssert.assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.ProductGuideWebIconDataAutoId), 2, "Product Guides Web topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		softAssert.assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.ProductGuideVideoIconDataAutoId), 2, "Product Guides Video topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		softAssert.assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.ProductGuidePDFIconDataAutoId), 2, "Product Guides PDF topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		softAssert.assertEquals(successTipsPage.getTopicsinNewTab(SuccessTipsPage.ProductGuideDataSheetIconDataAutoId), 2, "Product Guides Data Sheet topic is not displaying in new tab");
		successTipsPage.switchTab().switchToMainTab();
		
		softAssert.assertAll();
	}
	
	@Test(description = "PBC-459: T133945: Modal should have table and card view")
	public void verifyAllProductDocAndVideosCardAndTableView() {
		SoftAssert softAssert = new SoftAssert();
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchSmartAccount(cxCloudAccount)
				.selectMainDropDown(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectMainDropDown(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.clickProdDocAndVideosButton();
		int totalNumberOfTopicsDisplayed = successTipsPage.getTotalNumberOfTopicsInTitle();
		softAssert.assertEquals(successTipsPage.getProductDocAndVideosTableViewButton().getTableRowCount()-1, totalNumberOfTopicsDisplayed, "Total number of topics displayed is in correct");
		softAssert.assertEquals(successTipsPage.getProductDocAndVideosCardViewButton().getTotalNoOfTopicsinCardView(), totalNumberOfTopicsDisplayed, "Total number of topics displayed is in correct");
		softAssert.assertAll();
	}
	
	@Test(description = "PBC-459: T133948: Modal content should be filterable by archetype")
	public void verifyAllProductDocAndVideosFilter() {
		//SoftAssert softAssert = new SoftAssert();
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchSmartAccount(cxCloudAccount)
				.selectMainDropDown(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectMainDropDown(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.clickProdDocAndVideosButton().verifyFilterInAllProdDocAndVideosCardView();
		successTipsPage.getProductDocAndVideosTableViewButton();
		successTipsPage.verifyFilterInAllProdDocAndVideosTableView();
	}
	
	@Test(description = "PBC-459: T133951: Modal table view should have expected columns")
	public void verifyAllProductDocAndVideosTableView() {
		SoftAssert softAssert = new SoftAssert();
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchSmartAccount(cxCloudAccount)
				.selectMainDropDown(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectMainDropDown(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.clickProdDocAndVideosButton();
		List<String> allColumnsName = successTipsPage.getProductDocAndVideosTableViewButton().getAllTableColumnsName();
		softAssert.assertEquals(allColumnsName.get(0), "Bookmark", "Bookmark column name is not displayed");
		softAssert.assertEquals(allColumnsName.get(1), "Name", "Name column name is not displayed");
		softAssert.assertEquals(allColumnsName.get(2), "Category", "Category column name is not displayed");
		softAssert.assertEquals(allColumnsName.get(3), "Format", "Format column name is not dislayed");
		softAssert.assertAll();
	}*/
	
	@Test(groups = {"sanity"}, description = "PBC-500: T135698: Hovering over success bytes links shows a summary flyover")
	public void verifyHoeringOverSuccessBytesShowsSummary() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, networkDeviceOnboardingUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		successTipsPage.getHoverOverOperationOnSuccessBytesLink();
	}

	@Test(description = "T136059: Success Bytes and Product Guides cards should have description")
	public void verifySuccessBytesAndProductGuidsCardsHaveDescription() {
		SoftAssert softAssert = new SoftAssert();
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);

		//Verifying Card Description in Product Documentation
		successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
		softAssert.assertEquals(successTipsPage.getTotalNumberOfTopicsInTitle(), successTipsPage.getTotalNoOfSuccessTipsCardDescription(), "Some of the Product Documentation topics are not having description");

		/*Verifying Card Description in Product Documentation and Videos
		successTipsPage.clickProdDocAndVideosButton().clickProdDocAndVideosCardModalButton();
		softAssert.assertEquals(successTipsPage.getTotalNumberOfTopicsInTitle(), successTipsPage.getTotalNoOfProdDocAndVideosCardDescription(), "Some of the Product Documentation and Videos are not having description");
		softAssert.assertAll();*/
	}

	@Test(description = "T136060: Success Bytes and Product Guides cards titles should be hyperlinks")
	public void verifySuccessTipsCardsHasHyperlinks() {
		SoftAssert softAssert = new SoftAssert();
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);

		//Verifying Title hyperlinks in Product Documentation view all card modal
		successTipsPage.clickSuccessTipsViewAllLink().clickSuccessTipsCardModalButton();
		softAssert.assertTrue(successTipsPage.getHyperlinkInSuccessTipsTitle().length() > 0, "Product Documentation title is not having hyperlinks");

		/*Verifying title hyperlinks in Product Documentation and Videos view all card modal
		successTipsPage.clickProdDocAndVideosButton().clickProdDocAndVideosCardModalButton();
		softAssert.assertTrue(successTipsPage.getHyperlinkInSuccessTipsTitle().length() > 0, "Success Tips title is not having hyperlinks");
		softAssert.assertAll();*/
	}
	
	@Test(description = "T135702: Product Documentation Flyover correctly displays title and summary")
	public void verifyProdDocHoverOverDisplaysTitleAndSummary() {
		SuccessTipsPage successTipsPage = new SuccessTipsPage();
		successTipsPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, campusNetworkAssuranceUsecase)
				.selectCarousel(CarouselName.LIFECYCLE);
		
		//Data Availability check
		if(successTipsPage.getAllSuccessTipsTopicUnderLifecycle() <= 0)
			throw new SkipException("No Product Documentation Topics available to test this scenario");
		
		assertTrue(successTipsPage.checkHoverOverFromHomePage(),"Hover Over Operation is not displaying title or Summary for Product Documentation from Home Page");
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {
		try {
			page.get().reload();
//            page.get().closeAllPopUps();
//			page.get().closeAllDropDowns();
		} catch (Exception e) {
			System.out.println("Page not reloaded properly" + e);
//			page.get().reload();
		}
	}
}

