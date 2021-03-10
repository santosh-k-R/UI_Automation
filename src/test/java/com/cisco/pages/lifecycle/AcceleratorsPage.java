package com.cisco.pages.lifecycle;

import com.cisco.utils.LifeCycleUtils;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AcceleratorsPage extends LifeCycleUtils {
	
	public static final String ACC360ViewDataAutoId = "acc-details-panel";
	public static final String ACCFeedbackRatingClassName = "feedback-count";
	public static final String ACCFeedbackPositiveThumbsUpClassName = "feedback-positive";
	public static final String ACCCardDataAutoIDFromHomePage = "recommendedACC-Text";
	public static final String ACCCardDataAutoIdFromCardView = "ACCCard";
    static String text;
    static int i;

    @Step("Click View All button of ACC tile")
    public AcceleratorsPage clickACCViewAllLink(){
        getWebElement("ShowModalPanel-_Accelerators_").click();
        return this;
    }

    @Step("Get ACC Modal Window Title")
    public String getACCModalWindowTitle(){
        return getWebElement("ViewAllModal-Title").getText();
    }

    @Step("Get All Request 1-1 buttons text")
    public List<String> getAllRequest1_1ButtonText() {
        List<WebElement> btnElements = getWebElements("Request1on1ACCButton");
        List<String> btnTexts = new ArrayList<>();
        for (WebElement button : btnElements){
            btnTexts.add(button.getText());
        }
        return btnTexts;
    }

    @Step("Get the Title of the Accelerator")
    public String getACCTitle(){
        return getWebElement("PanelTitle-_Accelerators_").getText();
    }

    @Step("Get the ACC Subtitle")
    public String getACCSubtitle(){
        return getWebElement("//*[@data-auto-id='Accelerators Panel']//div[contains(@class,'-card--subtitle')]").getText();
    }

    @Step("Get ACC Image attribute")
    public String getSourceAttributeOfACCImage(){
        return getWebElement("recommendedACC-Image").getAttribute("src");
    }

    @Step("Get ACC Description")
    public String getACCDescription(){
        return getWebElement("recommendedACC-Title").getText();
    }
    
    @Step("Click Accelerator View All Table View")
    public AcceleratorsPage clickACCTableViewButton() {
    	getWebElement("acc-table-view-btn").click();
    	return this;
    }
    
    @Step("Click Accelerator View All Card View")
    public AcceleratorsPage clickACCCardViewButton() {
    	WebElement cardViewButton = getWebElement("acc-card-view-btn");
    	getWebDriverWait().until(ExpectedConditions.visibilityOf(cardViewButton));
    	cardViewButton.click();
    	getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='ACCCard']")));
    	return this;
    }

    @Step("Click Bookmark Ribbon")
    public AcceleratorsPage clickBookMarkRibbon() {
        getWebElement("(//*[@data-auto-id='ACCCardRibbon'])[2]").click();
        return this;
    }

    @Step("Get ACC Card Ribbon class")
    public String getACCRibbonClass(){
        return getWebElement("(//*[@data-auto-id='ACCCardRibbon'])[2]/span").getAttribute("class");
    }
    
    @Step("Get total number of topics displayed on top")
	public int getTotalNumberOfTopicsInTitle() {
		String[] topicsDisplayed = getWebElement("//div[@class='text-left modal__body__topics cx-text-default']").getText().split("\\s+");
		return Integer.parseInt(topicsDisplayed[0]);
	}
    
    @Step("Get total number of Cards Displayed in Card View")
    public int getTotalNoOfCardsDisplayed() {
    	System.out.println("Total Number of Cards: " + getWebElements("//*[@data-auto-id='ACCCard']").size());
    	return getWebElements("//*[@data-auto-id='ACCCard']").size();
    }
    
    @Step("Get Total Number of ACC description displayed in Card View")
    public int getTotalNoOfCardsDescriptionDisplayed() {
    	return getWebElements("//*[@data-auto-id='ACCCardDescription']").size();
    }
    
    @Step("Get total number of ACC Titles displayed in Card View")
    public int getTotalNoOfCardTitleDisplayed() {
    	return getWebElements("//*[@data-auto-id='ACCCardTitle']/*[contains(@class,'cx-lifecycle-card__title')]").size();
    }
    
    @Step("Get Total number of tiles with status")
    public HashMap getTotalNoOfTilesWithStatus() {
    	HashMap<String, Integer> statusCount = new HashMap<String, Integer>();
    	int totalNoOfCards = getTotalNoOfCardsDisplayed();
    	statusCount.put("Requested", 0);
		statusCount.put("Scheduled", 0);
		statusCount.put("In Progress", 0);
		statusCount.put("Completed", 0);
    	for(int i = 1; i <= totalNoOfCards; i++) {
    		try {
    			String status = getWebElement("(//*[@data-auto-id='ACCCardFooter'])[" + i + "]/div/span[2]").getText();
    			statusCount.put(status, statusCount.get(status)+1);
    		} catch (NoSuchElementException e) {
    			
    		}
    	}
    	return statusCount;
    }
    
    @Step("Apply Filter from View All Page")
    public void getTotalFilteredACCInViewAllPage(String filterName, String filterValue) {
    	WebElement element = getWebElement("//*[contains(@data-auto-id,'ViewAllModal-" + filterName + "')]");
    	element.click();
    	int totalNoOfFilterValues = getWebElements("//*[contains(@data-auto-id,'ViewAllModal-" + filterName + "')]//*[@data-auto-id='MultiSelect-DropdownList']/div").size();
    	System.out.println("Todal Drop down count: "+totalNoOfFilterValues);
    	for(int i = 0; i < totalNoOfFilterValues; i++) {
    		if(filterValue.equalsIgnoreCase("All")) {
    			clickFilterMultiSelectClearButton();
    			getWebElement("MultiSelect-SelectAll").click();
    			clickFilterMultiSelectSaveButton();
    			getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
    			break;
    		}
    		WebElement filterElement = getWebElement("//*[@data-auto-id='MultiSelect-ListItem" + i + "Text']");
    		if(filterElement.getText().equalsIgnoreCase(filterValue)) {
    			clickFilterMultiSelectClearButton();
    			filterElement.click();
    			clickFilterMultiSelectSaveButton();
    			getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
    			break;
    		}
    	}
    }
    
    @Step("Apply Filter from Home Page")
    public int getTotalFilteredACCInHomePage(String filterName, String filterValue) {
    	WebElement ele=getWebElement("//*[@name='"+filterName+"']/div/input[@data-auto-id='MultiSelect-SearchInput' and contains(@placeholder,'" + filterName + "')]");
    	JavascriptExecutor executor = (JavascriptExecutor)getDriver();
    	executor.executeScript("arguments[0].click();", ele);
    	this.clickFilterMultiSelectClearButton();
    	if(isElementPresent("//div[contains(@data-auto-id,'MultiSelect-ListItem') and text()='" + filterValue + "']")) {
    	getWebElement("//div[contains(@data-auto-id,'MultiSelect-ListItem') and text()='" + filterValue + "']").click();
    	}else {
    		throw new SkipException("This partner is not avilable on this filter.");
    	}
		getWebElement("MultiSelect-SaveButton").click();
		return this.getTotalNoOfACCUnderLifecyclePage();
    }

    @Step("Check Partner Content Displayed")
    public boolean checkPartnerContentDisplayed(String filterName) {
    	getWebElement("//*[@data-auto-id='MultiSelect-SearchInput' and contains(@placeholder,'" + filterName + "')]").click();
    	return getWebElements("//div[contains(@data-auto-id,'MultiSelect-ListItem')]").size() > 2;
    }

    @Step("Get Total Number of Accelerators Under Lifecycle")
    public int getTotalNoOfACCUnderLifecyclePage() {
    	return getWebElements("//*[@data-auto-id='recommendedACC']").size();
    }
    
    @Step("Click Filter Save Button")
    public AcceleratorsPage clickFilterMultiSelectSaveButton() {
    	getWebElement("MultiSelect-SaveButton").click();
    	return this;
    }
    
    @Step("Click Filter Clear Button")
    public AcceleratorsPage clickFilterMultiSelectClearButton() {
//    	avascriptExecutor executor = (JavascriptExecutor)getDriver();
//    	executor.executeScript("arguments[0].click();", ele);
    	WebElement ele=getWebElement("MultiSelect-ClearButton");
    	JavascriptExecutor executor = (JavascriptExecutor)getDriver();
    	executor.executeScript("arguments[0].click();", ele);
//    	getDriver().findElement(By.xpath("MultiSelect-ClearButton")).click();
//    	getWebElement("MultiSelect-ClearButton").click();
    	return this;
    }
    
    @Step("Get Status Column name in View All Table View")
    public String getStatusColumnNameViewAllTableView() {
    	return getWebElement("//div[@data-auto-id='ViewAllTable']//table//th[5]/span").getText();
    }
    
    @Step("Get Ribbon Icon for Bookmark")
	public String getClassAttribute(WebElement element) {
		return element.getAttribute("class");
	}
    
    @Step("Verify Bookmark on Home Page and Card View")
	public boolean verifyBookmark(WebElement element, String bookmarkOnText, String bookmarkOffText) {
    	boolean result = true;
		if(getClassAttribute(element).contains(bookmarkOffText)) {
			getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
			if(getClassAttribute(element).contains(bookmarkOffText))
				result = false;
		}
		if(getClassAttribute(element).contains(bookmarkOnText)) {
			getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
			if(getClassAttribute(element).contains(bookmarkOnText))
				result = false;
		}
		return result;
	}
    
    @Step("Verify Bookmark for Learning tile on Card View")
	public boolean verifyBookmarkOnCardView() {
		WebElement bookmark = getWebElement("(//*[@data-auto-id='ACCCardRibbon'])[1]/span");
		return verifyBookmark(bookmark, "ribbon__blue", "ribbon__white");
	}
    
    @Step("Verify Bookmark on Table view")
	public boolean verifyBookmarkOnTableView() {
		WebElement bookmark = getWebElement("(//*[@data-auto-id='SBListRibbon']/div)[1]");
		return verifyBookmark(bookmark, "icon-bookmark--on", "icon-bookmark--off");
	}
    
    @Step("Get Total No Of ACC Card with status")
    public int getTotalNoOfACCWithStatus() {
    	return getWebElements("(//*[@data-auto-id='ACCCardFooter']/div/span[2])").size();
    }
    
    @Step("Click On First Title in ACC from Home Page")
    public String clickOnFirstTitleFromHomePage() {
    	getWebElement("(//*[@data-auto-id='recommendedACC-tile'])[1]").click();
    	return this.getAccTitleFrom360View();
    }
    
    @Step("Get ACC First Title from Home Page")
    public String getACCFirstTitleFromHomePage() {
    	return getWebElement("(//*[@data-auto-id='recommendedACC-Title'])[1]").getText();
    }
    
    @Step("Get Partner Name for First ACC Title")
    public String getPartnerNameForFirstACCInHomePage() {
    	return getWebElement("(//p[@data-auto-id='recommendedACC-ProviderText'])[1]").getText();
    }
    
    @Step("Get ACC Title from 360 View")
    public String getAccTitleFrom360View() {
    	return getWebElement("//*[@class='acc-header']/span").getText();
    }
    
    @Step("Get Partner Name from 360 View")
    public String getPartnerNameFrom360View() {
    	return getWebElement("(//*[@class='duration qtr-margin-left'])[1]").getText();
    }
    
    @Step("Check Bookamrk availability on 360 View")
    public boolean checkBookmarkIn360ViewDisplayed() {
    	return this.isElementPresent("//*[@class='button-ribbon']");
    }
    
    @Step("Check Engagements availability in 360 view")
    public boolean checkEngagementsIn360ViewDisplayed() {
    	return this.isElementPresent("//*[contains(@class,'acc-engagements')]");
    }
    
    @Step("Check Request A 1-On-1 button availability in 360 view")
    public boolean checkRequestA1On1ButtonDisplayed() {
    	try {
    		if(!this.isElementPresent("(//*[@class='duration qtr-margin-left'])[2]"))
    			return getWebElement("Request1on1Button").isDisplayed();
    	} catch(Exception e) {
    		return false;
    	}
		return true;
    }
    
    @Step("Check Overview Tab availability in 360 view")
    public boolean checkOverviewIn360ViewDisplayed() {
    	return this.isElementPresent("OVERVIEWTab");
    }
    
    @Step("Click on ACC First Title from View All Card View")
    public String clickOnAccFirstTitleFromCardView() {
    	getWebElement("(//*[@data-auto-id='ACCCard'])[" + this.getACCPositionWithNoOrCompletesStatusInCardView() + "]").click();
    	return this.getAccTitleFrom360View();
    }
    
    @Step("Click on ACC First Title from View All Table View")
    public String clickOnAccFirstTitleFromTableView() {
    	getWebElement("(//*[@data-auto-id='ACC-Title'])[" + this.getACCPositionWithNoOrCompletesStatusInTableView() + "]").click();
    	return this.getAccTitleFrom360View();
    }
    
    @Step("Get ACC First Title from View All Card View")
    public String getAccFirstTitleFromCardView() {
    	return getWebElement("(//*[@data-auto-id='ACCCardTitle']/span)[" + this.getACCPositionWithNoOrCompletesStatusInCardView() + "]").getText();
    }

    @Step("Get ACC Card Position with No or Completed Status in Card View")
    public int getACCPositionWithNoOrCompletesStatusInCardView() {
    	List<WebElement> allCards = getWebElements("//*[@data-auto-id='ACCCard']");
    	for(int i = 1; i <= allCards.size(); i++) {
    		if((!this.isElementPresent("(//*[@data-auto-id='ACCCard'])[" + i + "]//child::*[contains(@class,'atx__card__status')]/span[2]")) || this.getACCStatusFromCardView(i).equalsIgnoreCase("Completed")) {
    			System.out.println("Clicking "+ i + "th Element");
    			return i;
    		}
    	}
    	return 0;
    }

    @Step("Get ACC Card Position with No or Completed Status in Table View")
    public int getACCPositionWithNoOrCompletesStatusInTableView() {
    	int totalNoOfACC = this.getTableRowCount();
    	for(int i = 2; i <= totalNoOfACC; i++) {
    		if((!this.isElementPresent("//*[@data-auto-id='ViewAllTableBody']/tr[" + i + "]//child::*[contains(@data-auto-id,'Table-Status')]/span[2]")) || this.getACCStatusFromTableView(i).equalsIgnoreCase("Completed")) {
    			System.out.println("Clicking "+ i + "th Element");
    			return i;
    		}
    	}
    	return 0;
    }
    
    @Step("Get Partner Name for First ACC from View All Card View")
    public String getPartnerNameForFirstACCInViewAllCardView() {
    	return getWebElement("(//*[@data-auto-id='ACCCardTitle' and contains(@class,'card__header')])[1]").getText();
    }
    
    @Step("Get ACC First Title from View All Table View")
    public String getAccFirstTitleFromTableView() {
    	return this.getWebElement("(//*[@data-auto-id='ACC-Title'])[" + this.getACCPositionWithNoOrCompletesStatusInTableView() + "]").getText();
    }
    
    @Step("Get Partner Name for First ACC from View All Table View")
    public String getPartnerNameForFirstACCInViewAllTableView() {
    	return getWebElement("(//*[@data-auto-id='partner-name'])[1]").getText();
    }
    
    @Step("Check Data availability")
    public boolean checkDataAvailability(String locator) {
    	if(getWebElements(locator).size()>0)
    		return true;
    	else
    		return false;
    }
    
    @Step("Check availability of ACC Card with Status")
    public boolean checkStatusInCardViewDisplayed() {
    	return checkDataAvailability("//*[@data-auto-id='ACCCardFooter']/div/span[2]");
    }
    
    @Step("Check ACC with Completed Status displayed in Table View")
    public boolean checkStatusInTableViewDisplayed() {
    	return checkDataAvailability("//*[@data-auto-id='Table-Status-Completed']");
    }
    
    @Step("Check availablity of ACC Title in Card View")
    public boolean checkACCTitleInCardViewDisplayed() {
    	return this.isElementPresent("//*[@data-auto-id='ACCCardTitle' and @class='card-description']/span");
    }
    
    @Step("Check availability of Partner Name in Card View")
    public int checkACCPartnerNameInCardViewDisplayed() {
    	return getWebElement("//*[@data-auto-id='ACCCardTitle' and contains(@class,'card__header')]").getText().length();
    }
    
    @Step("Check availability of Partner Name in Table View")
    public String checkACCPartnerNameInTableViewDisplayed() {
    	return getWebElement("(//*[@data-auto-id='Delivery Type']/span)[1]").getText();
    }
    
    @Step("Check availability of ACC Title in Table View")
    public boolean checkACCTitleInTableViewDisplayed() {
    	return this.isElementPresent("//*[@data-auto-id='ACC-Title']");
    }
    
    @Step("Get total No of ACC Card with Ratings and Positive Thumbs Up")
    public int getTotalNoOfACCWithRatings(String className) {
    	return this.getWebElements("(//*[@data-auto-id='ACCCard']//descendant::*[contains(@class,'" + className + "')]/span[1])").size();
    }

    @Step("Get total No of ACC Card with Positive Feedback")
    public int getTotalNoOfACCWithPositiveFeedback(String className) {
    	return this.getWebElements("(//*[@data-auto-id='ACCCard']//descendant::*[contains(@class,'" + className + "')]/span[2])").size();
    }

    @Step("Check availability of Tickmark and Positive Thumbs Up Symbol In Card View")
    public boolean checkCountAndPositiveSymbolDisplayedInCardView(String className) {
    	return this.isElementPresent("(//*[@data-auto-id='ACCCard']//descendant::*[contains(@class,'" + className + "')]/span[1])");
    }
    
    @Step("Check availability of Tickmark and Positive Thumbs Up Symbol In Table View")
    public boolean checkCountAndPositiveSymbolDisplayedInTableView(String className) {
    	return this.isElementPresent("(//*[@data-auto-id='ViewAllTable']//descendant::*[contains(@class,'" + className + "')]/span[1])");
    }
    
    @Step("Check availability of Positive Feedback and No of Completion In Card View")
    public boolean checkNoOfCompletionAndFeedbackCountDisplayedInCardView(String className) {
    	return this.isElementPresent("(//*[@data-auto-id='ACCCard']//descendant::*[contains(@class,'" + className + "')]/span[2])");
    }
    
    @Step("Check availability of Positive Feedback and No of Completion In Table View")
    public boolean checkNoOfCompletionAndFeedbackCountDisplayedInTableView(String className) {
    	return this.isElementPresent("(//*[@data-auto-id='ViewAllTable']//descendant::*[contains(@class,'" + className + "')]/span[2])");
    }
    
    @Step("Check ACC Engagement displayed in 360 view")
    public boolean checkACCEngagementsDisplayedIn360View() {
    	if(getWebElements("//*[contains(@class,'acc-engagements')]").size()>0)
    		return true;
    	else
    		return false;
    }
    
    @Step("Check available ACC Request")
    public int checkAvailableAccRequest() {
    	return Integer.parseInt(this.getWebElement("//*[contains(@class,'acc-engagements')]/span").getText().split(" ")[0]);
    }


    @Step("Check ACC UI When 3 Request Left")
    public boolean checkACCUIWhenRequestAvailable(int numberOfRequest) {
    	boolean result = false;
    	int engagementLeft = this.checkAvailableAccRequest();
    	int noOfGreenFilledBox = this.getWebElements(("//*[@class='fill-green']")).size();
    	int noOfRedFilledBox = this.getWebElements(("//*[@class='fill-red']")).size();
    	System.out.println("No OF Green Filled Box: "+ noOfGreenFilledBox);
    	System.out.println("No of Red Filled Box: "+noOfRedFilledBox);
    	if(numberOfRequest != 1) {
    		if(numberOfRequest == engagementLeft && numberOfRequest == noOfGreenFilledBox) {
    			System.out.println("Inside first If Statement");
    			result = true;
    		}
    	} else {
    		if(numberOfRequest == engagementLeft && numberOfRequest == noOfRedFilledBox ) {
    			System.out.println("Inside Second If Statement");
    			result = true;
    		}
    	}
    	return result;
    }

    @Step("Check ACC 360 View Displayed")
    public boolean checkACC360ViewDisplayed() {
    	return this.isElementPresent("acc-details-panel");
    }

    @Step("Check ACC UI When 0 Request Left")
    public boolean checkACCUIWhen0RequestAvailable() {
    	boolean result = false;
    	String engagementLeft=getWebElement("//*[contains(@class,'acc-engagements')]/span").getText();
		int requestleft = Integer.parseInt(engagementLeft.substring(0, engagementLeft.indexOf(" ")));
		if(requestleft==0) {
			result = this.isElementPresent("Request1on1Button");

		}else {
			throw new SkipException("No Cisco ACC available with 0 Request Left to test this scenario");
		}
    	return !result;
    }

    @Step("Filling the ACC request form")
    public boolean FillACCRequestForm() {
    	Select dropdown = new Select(getWebElement("attendees-select"));
    	dropdown.selectByIndex(0);
       	getJavascriptExecutor().executeScript("arguments[0].click();", getWebElement("//*[text()='Morning']/../input"));
    	dropdown = new Select(getWebElement("time-zone-select"));
    	dropdown.selectByIndex(0);
    	getWebElement("accRequestModal-WhyInterestedAccelerator-Input").sendKeys("it contains a vast knowledge on the topic");
    	getWebElement("accRequestModal-WhatWouldLikeToSeeOutcome-Input").sendKeys("expect to get a clear understanding on the topic");
    	boolean formelements=true;
//    	getWebElement("accRequestModal-Submit").click();
    	getDriver().findElement(By.xpath("//*[@data-auto-id='accRequestModal-Submit']")).click();
    	return formelements;
    }

    @Step("get acc request submission messge")
    public String getACCRequestSubmissionmsg() {
    	text = "";
    	getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='RequestFormSubmitSuccessBanner']")));
    	text = getWebElement("//*[@data-auto-id='RequestFormSubmitSuccessBanner']").getText();
    	getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='acc-details-panel']")));
    	return text;
    }

    @Step("request ACC from 360")
    public String requestACCFrom360() {
    	int counter=0;

    	for(int i=1;i<=getTotalNoOfCardsDisplayed();i++) {
    		counter++;
    		boolean check=isElementPresent("//*[@data-auto-id='ACCCard']["+i+"]//span[@class='text-medium']");
    		if(check) {
    		text=getWebElement("//*[@data-auto-id='ACCCard']["+i+"]//span[@class='text-medium']").getText();
    		}
    		if(!check||!text.equals("Requested")) {
    			WebElement myelement = getWebElement("//*[@data-auto-id='ACCCard']["+i+"]");
       			getJavascriptExecutor().executeScript("arguments[0].click();", myelement);
    			if(isElementPresent("Request1on1Button")) {
    				text=getWebElement("//*[@data-auto-id='ACCCard']["+i+"]//div[@class='card-description']/span").getText();
    				getWebElement("Request1on1Button").click();
    		    	FillACCRequestForm();
    		    	break;
    			}
       		}
    	if(counter==getTotalNoOfCardsDisplayed())	{
    		throw new SkipException("No Cisco ACC with session available to test this scenario");
    	}
    	}
    	return text;
    }

    @Step("verify the acc request submission messge")
    public String verifySuccessMsgOnSubmission() {
    	requestACCFrom360();
    	return getACCRequestSubmissionmsg();
    }

    @Step("verify 'Requested' status is showing on the requested tile")
    public String verifytheACCrequest() {
    	text=requestACCFrom360();
    	getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-auto-id='ACCCardTitle' and @class='card-description'])[1]/span[text()='"+text+"']")));
    	text=getWebElement("//span[text()='"+text+"']/../following-sibling::div//span[@class='text-medium']").getText();
    	return text;
    }

    @Step("verify maximize 360")
    public String verifymaximize360() {
    	fullscreen360View();
       	return getWebElement("//*[@id='acc-details-panel']").getAttribute("class");
    }

    @Step("Verify the tile coloour when opens the 360 view")
    public String getTileColorWhen360Open(String view) {
    	WebElement element = null;
    	if(view.equalsIgnoreCase("Table"))
    		element = getWebElement("//tr[@class='td list-view-row active'][1]");
    	else
    		element = getWebElement("//*[contains(@class,'cx-lifecycle-card--recommended selected-atx')]");
    	text=element.getCssValue("background-color").trim().split("none")[0];
    	text=Color.fromString(text).asHex();
    	System.out.println("Colour after opening 360: "+text);
    	return text;
    }

    @Step("get the status of Last ACC card")
    public String getACCStatusForLastCard1() {
    	boolean status=checkDataAvailability("(//*[@data-auto-id='ACCCardFooter'])["+getTotalNoOfCardsDisplayed()+"]/div/span[2]");
    	if(status) {
    		text=getWebElement("(//*[@data-auto-id='ACCCardFooter'])["+getTotalNoOfCardsDisplayed()+"]/div/span[2]").getText();
    	}
       	return text;
    }

    @Step("Click on the Last ACC card")
    public AcceleratorsPage clickACCLastCard() {
    	getWebElement("//*[@data-auto-id='ACCCard']["+getTotalNoOfCardsDisplayed()+"]").click();
       	return this;
    }

    @Step("Click on Request 1 on button")
    public AcceleratorsPage clickRequest1On1Button() {
    	getWebElement("Request1on1Button").click();
       	return this;
    }

    @Step("verfying that requset 1 on button is enabled for a customer with completed status if it has request left")
    public boolean verifyRequest1On1IsEnabledForCompletedTile(String name) {
    	boolean status=false;
    	text=getACCStatusForLastCard1();
    	if(text!=null&&text.equals("Completed")) {
    		clickACCLastCard();
    		if(name.equals("Cisco")) {
    		text=getWebElement("//*[contains(@class,'acc-engagements')]/span").getText();
    		int requestleft = Integer.parseInt(text.substring(0, text.indexOf(" ")));
    		if(requestleft>0) {
    			status=isElementPresent("Request1on1Button");
    		}else {
    			throw new SkipException("No ACC Session left to test this scenario");
    		}
    		}else {
    			status=isElementPresent("Request1on1Button");
    		}
    	}
    	else {
    		throw new SkipException("dont have tile with Completed status. Hence testing this scenario is not possible");
    	}
       	return status;
    }

    @Step("Doing ACC request from a completed status tile")
    public String ACCRequestFromCompletedStatusTile() {
    	boolean status=verifyRequest1On1IsEnabledForCompletedTile("none");
    	if(status) {
    		clickRequest1On1Button();
    		FillACCRequestForm();
    		return getACCRequestSubmissionmsg();
    	}else {
    		throw new SkipException("No ACC with Completed Status available to test this scenario");
    	}
    }

    @Step("Get ACC Last Title from View All Card View")
    public String getAccLastTitleFromCardView() {
    	return getWebElement("(//*[@data-auto-id='ACCCardTitle' and @class='card-description']/span)["+getTotalNoOfCardsDisplayed()+"]").getText();
    }

    @Step("Get total number of Notifications Displayed in lifecycle page")
    public int getTotalNoOfNotificationsDisplayed() {
    	getWebElement("facetNotificationButton").click();
    	System.out.println("Total Number of Cards: " + getWebElements("//*[@class='notificationDiv']//ul/li/div/div[1]").size());
    	return getWebElements("//*[@class='notificationDiv']//ul/li/div/div[1]").size();
    }

    @Step("verifying the notification is present in lifecycle for a completed ACC and give the feed back")
    public AcceleratorsPage verifyNotificationIsPresentInLifecycleForCompleteACCAndGiveFeedback(String title,String feedback,String FeedbackText) {
    	int counter =0;
    	for(int i=1;i<=getTotalNoOfNotificationsDisplayed();i++) {
    		counter++;
    		text=getWebElement("//*[@class='notificationDiv']//ul/li["+i+"]/div/div[1]").getText().split("\"")[1];
    		System.out.println(text);
    		if(text.equals(title)) {
    			getWebElement("//ul/li["+i+"]/div/div[2]//a[@data-auto-id='"+feedback+"']").click();
//    			getWebElement("//div[@data-auto-id='FeedbackPopup-Comments']/textarea").sendKeys(FeedbackText);
    			enterFeedbackCommentsToBox(FeedbackText);
//    			getWebElement("FeedbackPopup-Submit").click();
    			clickFeedbacksave();
    			getWebElement("//ul/li["+i+"]/div/div[3]/i[@data-auto-id='DeleteNotification']").click();
    			getWebElement("ToggleSessionNotifications").click();
    			break;

    		}else {
    			if(counter==getTotalNoOfNotificationsDisplayed())
    			throw new SkipException("dont have notification for this tile on lifecycle. Hence testing this scenario is not possible");
    		}
       	}

    	return this;
    }

    @Step("verify the user can give the feed back from lc tile Notifcations and its showing in the view all card")
    public boolean[] verifyGivenFeedbackFromLCNotificationDisplayedInCard() {
    	boolean[] booleanArray=new boolean[2];
    	boolean status=false;
    	text=getACCStatusForLastCard1();
    	if(text!=null&&text.equals("Completed")) {
    		text=getAccLastTitleFromCardView();
    		closeViewAllScreen();
       		int Beforefeedback=getTotalNoOfNotificationsDisplayed();
       		verifyNotificationIsPresentInLifecycleForCompleteACCAndGiveFeedback(text,"thumbUpBtn","Need to improve");
       		int AfterFeedback=getTotalNoOfNotificationsDisplayed();
       		status=Beforefeedback>AfterFeedback;
    	    booleanArray[0]=status;
       		clickACCViewAllLink();
       		status=isElementPresent("(//span[text()='"+text+"']/../..//descendant::*[contains(@class,'feedback-positive')]/span[1])");
       		booleanArray[1]=status;
    	}
    	else {
    		throw new SkipException("No ACC with Completed Status available to test this scenario");
    	}
       	return booleanArray;
    }

    @Step("Enter feedback comments to the feedback box")
    public AcceleratorsPage enterFeedbackCommentsToBox(String feedbackcomment) {
    	getWebElement("//*[@data-auto-id='FeedbackPopup-Comments']/textarea").sendKeys(feedbackcomment);
    	return this;
    }

    @Step("click on feedback save button")
    public AcceleratorsPage clickFeedbacksave() {
    	getWebElement("FeedbackPopup-Submit");
    	return this;
    }

    @Step("verify the user can give the feed back from 360 ")
    public boolean verifyFeedbackFrom360(String feedback,String feedbackcomment) {
    	boolean status=false;
    	text=getACCStatusForLastCard1();
    	if(text!=null&&text.equals("Completed")) {
    		clickACCLastCard();
    		text=getAccLastTitleFromCardView();
    		if(!isElementPresent("//*[@data-auto-id='thumbUpBtn' and contains(@class,'selected')]") && !isElementPresent("//*[@data-auto-id='thumbDownBtn' and contains(@class,'selected')]")) {
    			getWebElement(feedback).click();
    			enterFeedbackCommentsToBox(feedbackcomment);
    			clickFeedbacksave();
//    			jsWaiter.waitAllRequest();
    			getWebElement("//div/span[text()='"+text+"']").click();
    			status=isElementPresent("//*[@data-auto-id='"+feedback+"' and contains(@class,'selected')]");
    		}else {
    			throw new SkipException("Already given feedback to the session. Hence testing this scenario is not possible");
    		}
    	}
    	else {
    		throw new SkipException("dont have tile with Completed status. Hence testing this scenario is not possible");
    	}
       	return status;
    }

    @Step("Get the status of Card Displayed in Home")
    public String getStatusOfCardDisplayedInHome(int cardno) {
    	boolean status=false;
    	status=isElementPresent("(//*[@data-auto-id='recommendedACC-tile']["+cardno+"])/../div[2]//*[@class='item-status']");
    	if(status) {
    		text=getWebElement("(//*[@data-auto-id='recommendedACC-tile'])["+cardno+"]/../div[2]//*[@class='item-status']/span[2]").getText();
    	}
    	return text;
    }

    @Step("verify request form can open from home page")
    public boolean verifyRequestformOpenInHome() {
    	boolean status=false;
    	int cardDisplayedinHome=getTotalNoOfACCUnderLifecyclePage();
    	getWebElement("(//*[@data-auto-id='recommendedACC-tile'])["+cardDisplayedinHome+"]").click();
    	if(isElementPresent("Request1on1Button")) {
			getWebElement("Request1on1Button").click();
			status =isElementPresent("accRequestModal");
		}else {
			throw new SkipException("0 request left,hence request button is disabled .So can't test this scenario");
		}
    	return status;
    }

    @Step("Get ACC Status from Card View")
    public String getACCStatusFromCardView(int cardNumber) {
    	String result = "";
    	if(this.isElementPresent("(//*[@data-auto-id='ACCCard'])[" + cardNumber + "]//child::*[contains(@class,'atx__card__status')]/span[2]"))
    		result = getWebElement("(//*[@data-auto-id='ACCCard'])[" + cardNumber + "]//child::*[contains(@class,'atx__card__status')]/span[2]").getText();
    	System.out.println("Status: "+result);
    	return result;
    }

    @Step("Get ACC Status from Table View")
    public String getACCStatusFromTableView(int cardNumber) {
    	String result = "";
    	if(this.isElementPresent("//*[@data-auto-id='ViewAllTableBody']/tr[" + cardNumber + "]//child::*[contains(@data-auto-id,'Table-Status')]/span[2]"))
    		result = getWebElement("//*[@data-auto-id='ViewAllTableBody']/tr[" + cardNumber + "]//child::*[contains(@data-auto-id,'Table-Status')]/span[2]").getText();
    	return result;
    }

    @Step("Check availability of ACC with Completed status")
    public boolean checkACCAvailabilityWithCompletedStatus() {
    	return this.getACCStatusFromCardView(this.getTotalNoOfCardsDisplayed()).equalsIgnoreCase("Completed");
    }

    @Step("Check Thumbs Up Button Displayed")
    public boolean checkThumbsUpButtonDisplayed() {
    	System.out.println("Like Button");
    	return this.isElementPresent("//button[@aria-label='Like']");
    }

    @Step("Check Thumbs Down Button Displayed")
    public boolean checkThumbsDownButtonDisplayed() {
    	System.out.println("Dislike Button");
    	return this.isElementPresent("//button[@aria-label='Dislike']");
    }

    @Step("Click on ACC with Completed Status")
    public AcceleratorsPage clickOnACCWithCompletedStatus() {
    	List<WebElement> allCards = getWebElements("//*[@data-auto-id='ACCCard']");
    	for(int i = 1; i <= allCards.size(); i++) {
    		if(this.isElementPresent("(//*[@data-auto-id='ACCCard'])[" + i + "]//child::*[contains(@class,'atx__card__status')]/span[2]") && this.getACCStatusFromCardView(i).equalsIgnoreCase("Completed")) {
    			System.out.println("Clicking "+ i + "th Element");
    			allCards.get(i-1).click();
    			return this;
    		}
    	}
    	return null;
    }
    
    @Step("verify request form can open from home page")
    public boolean verifyRequestformOpenInHome(String name) {
    	boolean status=false;
    	if(name.equals("Home")){
    	int cardDisplayedinHome=getTotalNoOfACCUnderLifecyclePage();
    	getWebElement("(//*[@data-auto-id='recommendedACC-tile'])["+cardDisplayedinHome+"]").click();}
    	else if(name.equals("Card")) {
    		int cardDisplayedinHome=getTotalNoOfCardsDisplayed();
        	getWebElement("(//*[@data-auto-id='ACCCard'])["+cardDisplayedinHome+"]").click();
    	}
    	if(isElementPresent("Request1on1Button")) {
			getWebElement("Request1on1Button").click();
			status =isElementPresent("accRequestModal");
		}else {
			throw new SkipException("0 request left,hence request button is disabled .So can't test this scenario");
		}
    	return status;
    }


    @Step("Get ACC First Title from View All Card ")
    public String getAccFirstTitleFromCard() {
    	return getWebElement("(//*[@data-auto-id='ACCCardTitle']/span)[1]").getText();
    }

    @Step("Verify Bookmark on ACC 360")
	public void verifyBookmarkIn360(int no) {
		WebElement bookmark = getWebElement("(//*[@attr.data-auto-id='flyOut-BookmarkRibbon'])[1]/span");
		verifyBookmark(bookmark, "ribbon__blue", "ribbon__white");
	}

    @Step("Check bookmark Ribbon avilability on ACC tiles")
    public boolean checkBookmarkRibbonDisplayed() {
    	return this.isElementPresent("SBCardRibbon");
    }

    @Step("Check view datasheet link avilability on ACC tiles")
    public boolean checkViewDatasheetLinkDisplayed() {
    	return this.isElementPresent("(//*[@attr.data-auto-id='acc-HoverModal-Datasheet'])");
    }

    @Step("Click view datasheet link displayed on ACC tiles")
    public AcceleratorsPage clickViewDatasheetLinkDisplayed() {
    	getWebElement("(//*[@attr.data-auto-id='acc-HoverModal-Datasheet'])").click();
    	return this;
    }
    
    @Step("Get Status in Card View with Title")
    public String getStatusWithTitleInCardView(String title) {
    	return getWebElement("//*[text()='" + title + "']//following::div[contains(@class,'atx__card__status')]/span[2]").getText();
    }

    @Step("Check Disclaimer avilability on ACC tiles")
    public boolean checkDisclaimerOfViewDatasheetDisplayed() {
    	boolean value=false;

         value=isElementPresent("//*[text()='Disclaimer']");

      return value;
    }

    @Step("Accept Disclaimer avilability on ACC tiles")
    public AcceleratorsPage acceptDisclaimerOfViewDatasheet() {
    	if(isElementPresent("AcceptDisclaimer")) {
    	getWebElement("AcceptDisclaimer").click();
    	getWebDriverWait().until(ExpectedConditions.invisibilityOf(getWebElement("//*[text()='Disclaimer']")));
    	}
    	return this;
    }

    @Step("Verify after clicking on the viewdatsheet accept we can get access of the sheets")
    public boolean verifyAfterViewDatasheetClickGoestoDatasheetpage() {
    	String parent=getDriver().getWindowHandle();
    	acceptDisclaimerOfViewDatasheet();
    	Set<String>handles=getDriver().getWindowHandles();
    	boolean no=handles.size()!=1;
    	Iterator<String> I1= handles.iterator();
    	while(I1.hasNext())
    	{
    	String child_window=I1.next();
    	if(!parent.equals(child_window))
    	{
    	    System.out.println("datasheet has been opened.");
    	}
    	}

    	return no;
    }

    @Step("Get ACC Name from request form in Card View")
    public String getAccNameFromRequestFormInViewAllCardView() {
    	return getWebElement("accRequestModal-ItemTitle").getText();
    }

    @Step("Get Total no of Notification in bell icon")
    public int getTotalNoOfNotificationInBellicon() {
    	text=getWebElement("//*[@data-auto-id='facetNotificationButton']/span[@class='pull-right']").getText();
       	return Integer.parseInt(text);
    }

    @Step("Click on homepage Notification bell icon")
    public AcceleratorsPage clickHomepageNotificationIcon() {
    	getWebElement("facetNotificationButton").click();
    	return this;
    }

    @Step("Check availability of ACC Notifications in lifecycle notifications")
    public boolean checkACCNotificationAvailability() {
       	return this.getWebElement("//*[@class='absolute profile-info']//li/div[@class='row no-margin']/div[1]").getText().contains("Accelerators");
    }

    @Step("verify acc title and completion date is present in a completion notification")
    public String[] verifyTitleAndcompletionDateInNotification() {
    	String[]arr1=new String[4];
    	if((isElementPresent("(//*[@class='absolute profile-info']//li/div[@class='row no-margin']/div[1][contains(text(),'Accelerators')])[1]"))) {
       	String text=getWebElement("(//*[@class='absolute profile-info']//li/div[@class='row no-margin']/div[1][contains(text(),'Accelerators')])[1]").getText();
       	String title=text.split("\"")[1];
       	System.out.println(title);
       	String[] splitStr = text.split("\\s+");
       	String Completiondate=splitStr[splitStr.length-3]+splitStr[splitStr.length-2]+splitStr[splitStr.length-1];
       	System.out.println(Completiondate);
       	arr1[0]= title;
       	arr1[1]= Completiondate;
       	if(this.isElementPresent("thumbUpBtn"))
       		arr1[2] = "Thumbs Up Button Present";
       	if(this.isElementPresent("thumbDownBtn"))
       		arr1[3] = "Thumbs Down Button Present";
    	}else {
    		throw new SkipException("ACC notifications are not avilable to test the scenario");
    	}
       	return arr1;
    }

    @Step("Check availability of ACC Card with Status")
    public String getStatusInCardViewDisplayed() {
    	getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='ACCCardFooter']/div/span[2]")));
    	return getWebElement("//*[@data-auto-id='ACCCardFooter']/div/span[2]").getText();
    }

    @Step("get status from opeed 360 view")
    public String getStatusfrom360() {
    	getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='acc-header']/../div[2]/span[3]")));
    	return getWebElement("//*[@class='acc-header']/../div/span[text()='Status']/../span[3]").getText();
    }

    @Step("Click on ACC First Title from View All Card View")
    public AcceleratorsPage clickOnAccFirstTitleFromCard() {
    	getWebElement("(//*[@data-auto-id='ACCCard'])[1]").click();
    	return this;
    }

   
    @Step("Check Request A 1-On-1 enabled in 360 view")
    public boolean checkRequestA1On1Buttonenbled() {
       	return getWebElement("Request1on1Button").isEnabled();
    }

    @Step("Check Selected Feedback Option")
	public String getSelectedFeedbackOption() {
		if(getWebElement("thumbUpBtn").getAttribute("class").contains("btn--selected"))
			return "Thumbs Up";
		else if(getWebElement("thumbDownBtn").getAttribute("class").contains("btn--selected"))
			return "Thumbs Down";
		else return "No Feedback Selected";
	}
    
    @Step("Click Thumbs Up Button")
	public AcceleratorsPage provideThumbsUpFeedback() {
		getWebElement("thumbUpBtn").click();
		this.clickOnFeedbackSubmitOption("Test Feedback Thumbs Up Option");
		return this;
	}
	
	@Step("Click Thumbs Down Button")
	public AcceleratorsPage provideThumbsDownFeedback() {
		getWebElement("thumbDownBtn").click();
		this.clickOnFeedbackSubmitOption("Test Feedback Thumbs Down Option");
		return this;
	}
	
	@Step("Click on Feedback Submit option")
	public AcceleratorsPage clickOnFeedbackSubmitOption(String feedbackComment) {
		getWebElement("FeedbackPopup-Comments-Input").sendKeys(feedbackComment);
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='FeedbackPopup-Submit']")));
		getWebElement("FeedbackPopup-Submit").click();
		//getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='requested-status'])")));
		return this;
	}
	
	@Step("Click ACC with Specific Title")
	public AcceleratorsPage clickAccWithTitleInCardView(String title) {
		getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + title + "']")));
		getWebElement("//span[text()='" + title + "']").click();
		return this;
	}
}
