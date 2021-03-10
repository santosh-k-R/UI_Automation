package com.cisco.tests.lifecycle;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.lifecycle.LifecyclePage;
import com.cisco.pages.lifecycle.RaceTrackPage;
import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.lifecycle.RaceTrackPage;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.PitStopsName;
import org.testng.annotations.AfterMethod;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;
import static org.testng.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.cisco.testdata.Data.LIFECYCLE_COMMON_DATA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RaceTrackPageIT extends DriverBase {

    private String successTrack = LIFECYCLE_COMMON_DATA.get("SUCCESS_TRACK");
    private String NetworkDeviceOnBoardingUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_1");
    private String CampusNetworkAssuranceUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_2");
    private String CampusNetworkSegmentationUseCase = LIFECYCLE_COMMON_DATA.get("USE_CASE_5");
    private String cxCloudAccount = LIFECYCLE_COMMON_DATA.get("CX_CLOUD_ACCOUNT_L2");
    private static ThreadLocal<LifecyclePage> page = ThreadLocal.withInitial(() -> new LifecyclePage());

    @Test(description = "T125688: Pitstop actions should be displayed with checkboxes")
    public void verifyPitStopActionsHasCheckBoxes() {
        RaceTrackPage raceTrackPage = new RaceTrackPage();
        raceTrackPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, NetworkDeviceOnBoardingUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);
        assertTrue(raceTrackPage.verifyPitStopActionCheckBox(), "Some Pitstop actions are not having checkbox");
    }

    @Test(groups = {"sanity"}, description = "T125689: Selecting a pitstop action should filter the other Lifecycle content")
    public void verifyPitStopChangeFilterOtherContent() {
        RaceTrackPage raceTrackPage = new RaceTrackPage();
        SoftAssert softAssert = new SoftAssert();
        raceTrackPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);
        raceTrackPage.changePitStop(PitStopsName.Onboard);
        List<String> usePitstopContentDetails = raceTrackPage.getContentDetailsFromPitstop();
        raceTrackPage.changePitStop(PitStopsName.Implement);
		//softAssert.assertNotEquals(usePitstopContentDetails.get(0), raceTrackPage.getSuccessTrackCommunityLink(), "Success Track Community details has not been changed");
        softAssert.assertNotEquals(usePitstopContentDetails.get(0), raceTrackPage.getSuccessTipsFirstTitle(), "Success Tips content has not been changed");
        softAssert.assertNotEquals(usePitstopContentDetails.get(1), raceTrackPage.getAskTheExpertFirstTitle(), "Ask The Expert content has not been changed");
        softAssert.assertNotEquals(usePitstopContentDetails.get(2), raceTrackPage.getAcceleratorFirstTitle(), "Accelerator content has not been changed");
        softAssert.assertAll();
    }

    @Test(description = "T125692: Pitstop actions should not be checkable if manualCheckAllowed is false")
    public void verifyPitstopActionsForAutoUpdatedCheckList() {
        RaceTrackPage raceTrackPage = new RaceTrackPage();
        raceTrackPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, CampusNetworkAssuranceUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);
        raceTrackPage.changePitStop(PitStopsName.Use);
        assertTrue(raceTrackPage.verifyPitstopActionsWithManualCheckAllowedFalse(), "User able to check with Pitstop action with manualCheckAllowed False");
    }

    @Test(description = "T125691: Pitstop actions that are not complete should be checkable")
    public void verifyPitstopActionsCheckboxComplete() {
        RaceTrackPage raceTrackPage = new RaceTrackPage();
        raceTrackPage.login()
                .switchCXCloudAccount(cxCloudAccount)
                .selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
                .selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
                .selectCarousel(CarouselName.LIFECYCLE);
        raceTrackPage.changePitStop(PitStopsName.Use);
        assertTrue(raceTrackPage.verifyPitstopActionsWithManualCheckAllowedTrue(), "User is not able to check the checkbox with manualCheckAllowed True");

    }

	@Test(description = "T133912: Racetrack arrows should move preview between pitstops")
	public void verifyChangingPitsop() {
		RaceTrackPage raceTrackPage = new RaceTrackPage();
		raceTrackPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

		PitStopsName currentPitStop = PitStopsName.valueOf(raceTrackPage.getCurrentPitStopName());
		List<PitStopsName> nexPitStop = Arrays.stream(PitStopsName.values())
				.filter(p -> p.getPitStopIndex() == currentPitStop.getPitStopIndex() + 1)
				.collect(Collectors.toList());
		raceTrackPage.moveToNextPitstop();            //moves to next pitStop using arrow
		assertEquals(nexPitStop.get(0).getPitStopName(), raceTrackPage.getPreviewPitStopName());
		Allure.step("Changed the Pitstop from " + currentPitStop + " to " + nexPitStop);
	}

	@Test(description = "T133913: Clicking a pitstop should move preview to pitstop")
	public void verifyClickingOnNewPitStop() {
		RaceTrackPage raceTrackPage = new RaceTrackPage();
		raceTrackPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkAssuranceUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);

		PitStopsName currentPitStop = PitStopsName.valueOf(raceTrackPage.getCurrentPitStopName());
		raceTrackPage.changePitStop(PitStopsName.Adopt);            //moves to next pitsop using arrow
		assertEquals(PitStopsName.Adopt.getPitStopName(), raceTrackPage.getPreviewPitStopName());
		Allure.step("Changed the Pitstop from " + currentPitStop + " to " + PitStopsName.Adopt);
	}

	@Test(description = "T133914: Previewing a pitstop should refresh all lifecycle content to that pitstop")
	public void verifyPreviewingAPitStop() {
		RaceTrackPage raceTrackPage = new RaceTrackPage();
		SoftAssert softAssert = new SoftAssert();
		raceTrackPage.login()
				.switchCXCloudAccount(cxCloudAccount)
				.selectContextSelector(CxHomePage.SUCCESS_TRACK, successTrack)
				.selectContextSelector(CxHomePage.USE_CASE, CampusNetworkSegmentationUseCase)
				.selectCarousel(CarouselName.LIFECYCLE);
		raceTrackPage.changePitStop(PitStopsName.Onboard);
		List<String> usePitstopContentDetails = raceTrackPage.getContentDetailsFromPitstop();
		raceTrackPage.changePitStop(PitStopsName.Implement);
		//softAssert.assertNotEquals(usePitstopContentDetails.get(0), raceTrackPage.getSuccessTrackCommunityLink(), "Success Track Community details has not been changed");
		softAssert.assertNotEquals(usePitstopContentDetails.get(0), raceTrackPage.getSuccessTipsFirstTitle(), "Success Tips content has not been changed");
		softAssert.assertNotEquals(usePitstopContentDetails.get(1), raceTrackPage.getAskTheExpertFirstTitle(), "Ask The Expert content has not been changed");
		softAssert.assertNotEquals(usePitstopContentDetails.get(2), raceTrackPage.getAcceleratorFirstTitle(), "Accelerator content has not been changed");
		softAssert.assertAll();
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