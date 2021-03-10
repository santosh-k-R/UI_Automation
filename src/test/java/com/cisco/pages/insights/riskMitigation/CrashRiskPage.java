package com.cisco.pages.insights.riskMitigation;

import static com.cisco.testdata.StaticData.DNAC_DropdownValue.ALL_CATEGORIES;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cisco.testdata.StaticData.ChartType;
import com.cisco.testdata.StaticData.DNAC_DropdownValue;
import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.utils.AppUtils;
import com.cisco.pages.insights.riskMitigation.RiskMitigationPage;

import io.qameta.allure.Step;

public class CrashRiskPage extends RiskMitigationPage {

	@Step("Validation of usecase with risk")
	public void usecaseSelectionwithRisk(String risk) {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();

		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk");
			riskMitigationPage.systemsScoreCardCount();
			riskMitigationPage.riskFilter(risk);
			int deviceCount = riskMitigationPage.getTableRowCount();
			System.out.println("Systems found in selected risk" + deviceCount);

		} else {
			System.out.println("No systems found in selected risk");
		}
	}

	@Step("Validation of usecase with timerange")
	public void usecaseSelectionwithTimeRange(String timeRange) {
		RiskMitigationPage riskMitigationPage = new RiskMitigationPage();
		riskMitigationPage.isRiskMitigationTabEnabled("Crash Risk");
		if (!riskMitigationPage.noResultsFoundMessage()) {
			riskMitigationPage.crashedSystemCount();
			riskMitigationPage.timeRangeFilter(timeRange);
			int deviceCount = riskMitigationPage.getTableRowCount();
			System.out.println("crashes found in selected time range" + deviceCount);
		} else {
			System.out.println("No crashes found in selected time range.");
		}
	}
	 public AppUtils logOut() {
	        String userProfileLocator = "button[title='User Profile']";
	        String logOutLocator = "[data-auto-id='Header-DoLogout']";
	        String closeIconLocator = "[class*='icon-close']";
	        if (isElementPresent(closeIconLocator))
	            getWebElement(closeIconLocator).click();

	        getWebElement(userProfileLocator).click();
	        waitTillElementIsVisible(logOutLocator);
	        getWebElement(logOutLocator).click();
	      //  getWebDriverWait().until(ExpectedConditions.urlContains("log-out-success"));
	        return this;
	    }


	@Step("Relogin")
	public void relogin() {
		String clicklogin = "//a[contains(@href,'/') and @class='logged-out-page__link btn btn--primary']";
		getWebElement(clicklogin).click();
	}
	
	 public boolean verifyColumnSorted(String columnName, String order) {
	        String classText;
	        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	        try {
	            classText = getWebElement("//span[text()=' " + columnName + " ']/..").getAttribute("class");
	            if (classText.contains("sorted")) {
	                classText = getWebElement("//span[text()=' " + columnName + " ']/../span[contains(@class,'sort-icon')]").getAttribute("class");
	                if (classText.contains("desc") && order.contains("Desc"))
	                    return true;
	                else if (classText.contains("asc") && order.contains("Asc"))
	                    return true;
	                else
	                    return false;
	            } else
	                return false;
	        } catch (NoSuchElementException e) {
	            e.printStackTrace();
	            return false;
	        }

	    }

	
}