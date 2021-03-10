package com.cisco.pages.lifecycle;

import com.cisco.utils.LifeCycleUtils;

import com.cisco.testdata.StaticData.PitStopsName;
import com.cisco.utils.AppUtils;
import com.cisco.utils.LifeCycleUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class RaceTrackPage extends LifeCycleUtils{
	
	@Step("Click on Racecar icon")
	public RaceTrackPage clickOnRaceCar() {
		getWebElement("racecar").click();
		return this;
	}

	@Step("Click Success Track Community Link")
	public String getSuccessTrackCommunityLink() {
		getWebElement("PanelTitle-PrivateCommunities").click();
		return getCurrentURLFromSecondTab();
	}

	@Step("Get Total No of Pitstop Actions")
	public int getTotalNoOfPitstopActions() {
		return getWebElements("//*[contains(@class,'icon-checklist-item')]").size();
	}
	
	@Step("Verify Checkbox Display for each Pit Stop Action")
	public boolean verifyPitStopActionCheckBox() {
		boolean result = true;
		//SoftAssert softAssert = new SoftAssert();
		int totalNoOfPitStopActions = getTotalNoOfPitstopActions();
		System.out.println("Total No Of Pit Stop Actions: " +totalNoOfPitStopActions);
		for(int i = 1; i <= totalNoOfPitStopActions; i++) 
			if(!isElementPresent("(//div[@class='checklist-status-icon']//*[contains(@class,'icon-checklist-item')])[" + i + "]"))
				result = false;
			//softAssert.assertTrue(isElementPresent("(//div[@class='checklist-status-icon']//*[contains(@class,'icon-checklist-item')])[" + i + "]"), "Check Box is not displayed for Pit Stop Action");
		//softAssert.assertAll();
		return result;
	}
	
	@Step("Get Success Tips First Title")
	public String getSuccessTipsFirstTitle() {
		return getWebElement("(//*[@data-auto-id='_SuccessTips_ -item.title'])[1]").getText();
	}
	
	@Step("Get Ask The Expert First Title")
	public String getAskTheExpertFirstTitle() {
		return getWebElement("(//*[@data-auto-id='Ask the Experts Panel']//div/p)[1]").getText();
	}
	
	@Step("Get Accelerator First Title")
	public String getAcceleratorFirstTitle() {
		return getWebElement("(//*[@data-auto-id='recommendedACC-Title'])[2]").getText();
	}
	
	@Step("Get Content Details from Use Pitstop")
	public List<String> getContentDetailsFromPitstop() {
		List<String> contentDetails = new ArrayList<String>();
		//contentDetails.add(getSuccessTrackCommunityLink());
		contentDetails.add(getSuccessTipsFirstTitle());
		contentDetails.add(getAskTheExpertFirstTitle());
		contentDetails.add(getAcceleratorFirstTitle());
		return contentDetails;
	}
	
	@Step("Verify Pit Stop actions are not checkable if manualCheckAllowed is False")
	public boolean verifyPitstopActionsWithManualCheckAllowedFalse() {
		boolean status = false;
		try {
			getWebElement("//*[@class='checklist-status-icon']/div").click();
		} catch (ElementClickInterceptedException e) {
			status = true;
		}
		return status;
	}
	
	@Step("Verify Pit Stop actions are not checkable if manualCheckAllowed is False")
	public boolean verifyPitstopActionsWithManualCheckAllowedTrue() {
		boolean status = false;
		String checkBoxBeforeValue = getWebElement("icon-checklist-check-allow").getAttribute("class");
		getWebElement("icon-checklist-check-allow").click();
		String checkBoxAfterValue = getWebElement("icon-checklist-checked").getAttribute("class");
		//Reverting back the selected check list
		getWebElement("icon-checklist-checked").click();
		if(!checkBoxBeforeValue.equalsIgnoreCase(checkBoxAfterValue))
			status = true;
		return status;
	}

	public String getPreviewPitStopName() {
				return getWebElement("//*[name()='circle' and @fill='rgb(20, 189, 244)']").getAttribute("data-auto-id");
	}
}
