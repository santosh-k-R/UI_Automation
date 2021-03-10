package com.cisco.tests.insights.riskMitigation;

import static com.cisco.testdata.StaticData.DNAC_DropdownValue.ALL_CATEGORIES;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.insights.riskMitigation.CrashRiskPage;

import com.cisco.pages.insights.riskMitigation.RiskMitigationPage;

public class CrashRiskPageIT extends DriverBase {

	String filterValue;

	@BeforeClass(description = "Verify Login to Cx-Portal and Click on Use Case")
	public void verifyInsights() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.login();
		riskMitigationPage.resetContextSelector();
		riskMitigationPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus");
		riskMitigationPage.clickOnInsights();
	}

	@Test(description = "Tm5585718c-Verify RMC_ Crashed System Campus Network and Use case filters Campus Network Segmentation", priority = 1)
	public void verify90dUseCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Segmentation");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("30d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585717c-Verify RMC_ Crashed System 30d and Use case filters Campus Network Segmentation", priority = 2)
	public void verify30dUseCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Segmentation");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("30d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585716c-Verify RMC_ Crashed System Use case filters Campus Software Image Management", priority = 3)
	public void verifyUseCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Software Image Management");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("30d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585715c-Verify RMC_ Crashed System Use case filters Campus Network Segmentation", priority = 4)
	public void verify90dNDOUseCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Segmentation");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("30d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585714c-Verify RMC_ Crashed System Use case filters Campus Network Assurance ", priority = 5)
	public void verify90dSAPUseCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Assurance ");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("30d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585713c-Verify RMC_ Crashed default System Use case ", priority = 6)
	public void verify30dDefaultUseCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("30d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585712c-Verify RMC_ Crashed default System Use case Scalable Access Policy ", priority = 7)
	public void verify30dSCACaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Scalable Access Policy ");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("30d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585711c-Verify RMC_ Crashed default System Use case Network Device Onboarding ", priority = 8)
	public void verify30dNDOCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Network Device Onboarding");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("90d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585710c-Verify RMC_ Crashed default System Use case Campus Software Image Management ", priority = 9)
	public void verify90dCSIMCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Software Image Management");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("90d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585709c-Verify RMC_ Crashed default System Use case  Campus Network Segmentation", priority = 10)
	public void verify90dCNSCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, " Campus Network Segmentation");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("90d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585708c-Verify RMC_ Crashed default System Use case  Campus Network Assurance ", priority = 11)
	public void verify90dCNACaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Assurance ");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithTimeRange("90d");
		assertTrue(riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk"), "Crash Risk tab is not present");
	}

	@Test(description = "Tm5585707c-Verify RMC_ Crashed default System Use case  Campus Software Image Management", priority = 12)
	public void verifyriskCSIMaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Software Image Management");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585706c-Verify RMC_ Crashed default System Use case  Campus Software Image Managementn", priority = 13)
	public void verifyriskCNSCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Software Image Management");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585705c-Verify RMC_ Crashed default System Use case  Network Device Onboarding", priority = 14)
	public void verifyriskNDACaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Network Device Onboarding");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585704c-Verify RMC_ Crashed default System Use case  Campus Software Image Management", priority = 15)
	public void verifyriskCSIMCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Software Image Management");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585703c-Verify RMC_ Crashed default System Use case  Campus Network Assurance", priority = 16)
	public void verifyriskCNACaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Assurance");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585702c-Verify RMC_ Crashed default System Use case  Network Device Onboarding ", priority = 17)
	public void verifyriskNDOCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Network Device Onboarding");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585701c-Verify RMC_ Crashed default System Use case   Campus Software Image Management", priority = 18)
	public void verifyriskSIMCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Software Image Management");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585700c-Verify RMC_ Crashed default System Use case   Campus Network Segmentation", priority = 19)
	public void verifyriskcnsCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Segmentation");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("Medium");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585699c-Verify RMC_ Crashed default System Use case   Campus Network Assurance", priority = 20)
	public void verifyriskcnaCaseSelection() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.selectContextSelector(CxHomePage.USE_CASE, "Campus Network Assurance");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("High");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585698c-Verify RMC_ Crashed default Risk selection", priority = 21)
	public void verifyhighrisk() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk");
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.riskFilter(RiskMitigationPage.RISK_HIGH);
			riskMitigationPage.isDataPresentInFPGrid(RiskMitigationPage.RISK_HIGH);
			assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
		} else {
			System.out.println("No systems found in selected risk");
		}
	}

	@Test(description = "Tm5585697c-Verify RMC_ Crashed default Risk selection", priority = 22)
	public void verifyhighriskdefault() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk");
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.riskFilter(RiskMitigationPage.RISK_HIGH);
		} else {
			System.out.println("No systems found in selected risk");
		}
	}

	@Test(description = "Tm5585658c-Verify RMC_ medium Risk selection", priority = 23)
	public void verifymediumriskdefault() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk");
		CrashRiskPage crashriskPage = new CrashRiskPage();
		crashriskPage.usecaseSelectionwithRisk("High");
		assertEquals(RiskMitigationPage.RISKVALUE, RiskMitigationPage.RISKFROMFP);
	}

	@Test(description = "Tm5585655c-Verify RMC_ medium Risk selection", priority = 24)
	public void ClickCrashedAsset() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk");
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.crashedSystemCount();
			int deviceCount = riskMitigationPage.getTableRowCount();
			System.out.println("crashes found in selected time range" + deviceCount);
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Tm5585635c-Verify RMC CrashHistorySystemName", priority = 25)
	public void verifySystemName() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.timeRangeFilter("90d");
			riskMitigationPage.openCrashHistoryPage(1, 1);
			riskMitigationPage.getCrashHistorySystemName();
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Tm5585620c-Verify RMC CrashHistorySystemName", priority = 26)
	public void verifyxrashedSystemName() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {

			riskMitigationPage.searchDNACField(ALL_CATEGORIES, "C9300-24T");
			riskMitigationPage.closeSearchIcon();
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Tm5585635c-Verify RMC CrashHistorySystemName", priority = 27)
	public void verifyCrashHistorySystemName() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.crashedSystemCount();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.openCrashHistoryPage(1, 1);
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Tm5585601c-Verify RMC CrashSystem search", priority = 28)
	public void verifysearchincrash() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.searchDNACField(ALL_CATEGORIES, "C9899");
			riskMitigationPage.closeSearchIcon();
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Tm5585600c-Verify RMC search in crashedAssets", priority = 29)
	public void verifysearchincrashedAssets() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.searchDNACField(ALL_CATEGORIES, "*");
			riskMitigationPage.closeSearchIcon();
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

	@Test(description = "Tm5585580c-Verify RMC search for devices in crashedAssets", priority = 30)
	public void verifysearchcrashedAssets() {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.searchDNACField(ALL_CATEGORIES, "10_0_2_1");
			riskMitigationPage.closeSearchIcon();
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}

}