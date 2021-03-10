package com.cisco.pages.lifecycle;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

import com.cisco.utils.LifeCycleUtils;

import io.qameta.allure.Step;

public class LearningPage extends LifeCycleUtils {
	
	public static final String Learning360ViewOverviewTabDataAutoId = "OVERVIEWTab";
	public static final String Learning360ViewCourseOutlineTabDataAutoId = "COURSE OUTLINETab";
	public static final String Learning360ViewDataAutoID = "learning-details-panel";
	public static final String LearningFeedbackRatingClassName = "feedback-count";
	public static final String LearningFeedbackPositiveThumbsUpClassName = "feedback-positive half-margin-left";
	
	public static final String ELearningTitleInHomePageDataAutoId = "_ELearning_ -item.title";
	public static final String ELearningCardViewBookmarkDataAutoID = "EL-Bookmark";
	public static final String ELearningCardViewDataAutoID = "EL-Card";
	public static final String ELearningBlockDataAutoId = "LearnPanel-ELearningBlock";
	public static final String ELearningProgressBarDataAutoId = "_ELearning_-progressBar";
	
	public static final String CertPrepTitleInHomePageDataAutoId = "_CertificationPrep_ -item.title";
	public static final String CertPrepCardViewBookmarkDataAutoID = "CF-Bookmark";
	public static final String CertPrepCardViewDataAutoID = "CF-Card";
	public static final String CertPrepBlockDataAutoId = "LearnPanel-CertificationsBlock";
	public static final String CertPrepProgressBarDataAutoId = "_CertificationPrep_-progressBar";
	
	public static final String RemotePracLabsTitleInHomePageDataAutoId = "_RemotePracticeLabs_ -item.title";
	public static final String RemotePracLabsCardViewBookmarkDataAutoID = "RPL-Bookmark";
	public static final String RemotePracLabsCardViewDataAutoID = "RPL-Card";
	public static final String RemotePracLabsBlockDataAutoId = "LearnPanel-RemotePracticeLabsBlock";
	
	@Step("Click View All Link of Certification Prep")
	public LearningPage clickCertPrepViewAllLink() {
		getWebElement("_CertificationPrep_-ViewAll").click();
		return this;
	}
	
	@Step("Click View All Link of E-Learning")
	public LearningPage clickELearningViewAllLink() {
		getWebElement("_ELearning_-ViewAll").click();
		return this;
	}
	
	@Step("Click View All Link of Remote Practices Labs")
	public LearningPage clickRemotePracLabsViewAllLink() {
		getWebElement("_RemotePracticeLabs_-ViewAll").click();
		return this;
	}
	
	@Step("Click Learning Tile Card View Modal Button")
	public LearningPage clickLearningCardViewButton() {
		getWebElement("atx-card-view-btn").click();
		return this;
	}
	
	@Step("Click Learning Tile Table View Modal Button")
	public LearningPage clickLearningTableViewButton() {
		getWebElement("atx-table-view-btn").click();
		return this;
	}
	
	@Step("Click First Title in Learning Tile Table View")
	public LearningPage clickFirstTitleInLearningTableView() {
		getWebElement("(//*[@data-auto-id='LEARNING-Title'])[1]").click();
		return this;
	}
	
	@Step("Get First Title in Learning Tile Table View")
	public String getFirstTitleInLearningTableView() {
		return getWebElement("(//*[@data-auto-id='LEARNING-Title'])[1]").getText();
	}
	
	@Step("Get Window Title in View All Modal")
	public String getWindowTitleInViewAllModal() { 
		return getWebElement("ViewAllModal-Title").getText();
	}
	
	@Step("Get Learning Title in 360 View")
	public String getLearningTitleIn360View() {
		return this.getTextBasedOnClassAttribute("(//*[contains(@class,'cx-lifecycle-card--subheader')])[1]/span");
	}
	
	@Step("Click First Title from Home Page")
	public LearningPage clickFirstTitleFromHomePage(String locator)
	{
		getWebElement("(//*[@data-auto-id='" + locator + "'])[1]/span").click();
		return this;
	}
	
	@Step("Get First Title from Home Page")
	public String getFirstTitleFromHomePage(String locator) {
		return getWebElement("(//*[@data-auto-id='" + locator + "'])[1]/span").getText();
	}
	
	@Step("Click First Title in Card View XPath")
	public LearningPage clickFirstTitleInCardViewXPath(String locator) {
		getWebElement("//*[@data-auto-id='" + locator + "--0']//following::div[2]/span").click();
		return this;
	}
	
	@Step("Get First Title in Card View XPath")
	public String getFirstTitleInCardViewXPath(String locator) {
		return getWebElement("//*[@data-auto-id='" + locator + "--0']//following::div[2]/span").getText();
	}
	
	/*
	 * @Step("Get First Title Duration in Card View XPath") public String
	 * getFirstTitleDurationInCardView(String locator) { return
	 * getWebElement("//*[@data-auto-id='" + locator +
	 * "--0']/div[4]/span[3]").getText(); }
	 */
	
	@Step("Get Last Launched Details In Home Page for Remote Practices Labs")
	public String getLastLaunchesDetailsFromHomePage() {
		return getWebElement("//*[contains(@class,'launch-status')]/span[2]").getText() + " " + getWebElement("//*[contains(@class,'launch-status')]/span[3]").getText();
	}
	
	@Step("Get Last Launched Details in Card View for Remote Practices Labs")
	public String getFirstTitleLastLaunchedDetailsFromCardView() {
		return getWebElement("(//*[@data-auto-id='" + RemotePracLabsCardViewDataAutoID + "--0']//span[2])").getText() + " " +getWebElement("(//*[@data-auto-id='" + RemotePracLabsCardViewDataAutoID + "--0']//span[3])").getText();
	}
	
	@Step("Get Last Launched Details in Card View for Remote Practices Labs")
	public String getFirstTitleLastLaunchedDetailsFromTableView() {
		return getWebElement("//*[@data-auto-id='ViewAllTableBody']/tr[2]/td[@data-auto-id='Status']//span[2]").getText() + " " +getWebElement("//*[@data-auto-id='ViewAllTableBody']/tr[2]/td[@data-auto-id='Status']//span[3]").getText();
	}
	
	@Step("Get Total Number of Titles displayed in Card View")
	public int getTotalNoOfTitlesDisplayedInCardView(String locator) {
		return getWebElements("//div[contains(@data-auto-id,'" + locator + "')]").size();
	}
	
	@Step("Get Title from Learning 360 view")
	public String getTitleFrom360View() {
		return getWebElement("//*[@class='learning-header']/span").getText();
	}
	
	@Step("Get First Title Duration from Card view")
	public String getFirstTitleDurationInCardView() {
		return getWebElement("(//*[@data-auto-id='Duration'])[1]/div/span").getText();
	}
	
	@Step("Get Duration from Learning 360 view")
	public String getDurationFrom360View() {
		return "//*[@class='video-status base-margin-top']/span[5]";
	}
	
	@Step("Get Delivery Type from Learning 360 view")
	public String getDeliveryTypeFrom360View() {
		return "//*[@class='video-status base-margin-top']/span[3]";
	}
	
	@Step("Get Progress Bar for ELearning")
	public String getXPathForELearningProgressBar(String viewType, String locator) {
		String XPath = null;
		if(viewType.equalsIgnoreCase("Card"))
			XPath = "//*[@data-auto-id='" + locator + "']";
		else if(viewType.equalsIgnoreCase("Table"))
			XPath = "//*[contains(@data-auto-id,'" + locator + "')]";
		return XPath;
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
	
	@Step("Verify on Bookmark for learning tile from Home Page")
	public boolean verifyBookMarkonFirstTitleFromHomePage(String locator) {
		WebElement bookmark = getWebElement("(//*[@data-auto-id='" + locator + "']//*[@data-auto-id='-HoverModal-BookmarkRibbon'])[1]/span");
		return verifyBookmark(bookmark, "ribbon__blue", "ribbon__white");
	}
	
	@Step("Verify Bookmark for Learning tile on Card View")
	public boolean verifyBookmarkOnCardView(String locator) {
		WebElement bookmark = getWebElement("//*[@data-auto-id='" + locator + "--0']/span");
		return verifyBookmark(bookmark, "ribbon__blue", "ribbon__white");
	}
	
	@Step("Verify Bookmark on Table view")
	public boolean verifyBookmarkOnTableView() {
		WebElement bookmark = getWebElement("(//*[@data-auto-id='SBListRibbon']/div)[1]");
		return verifyBookmark(bookmark, "icon-bookmark--on", "icon-bookmark--off");
	}
	
	@Step("Click bookmark on first card")
	public LearningPage clickBookmarkOnFirstCardTile(String locator) {
		getWebElement("//*[@data-auto-id='" + locator + "--0']/span").click();
		return this;
	}
	
	@Step("Get Total Number of Topics")
	public void getTotalNumberOfTopicsDisplayed() {
		List<WebElement> allTopics = getWebElements("//div[@data-auto-id='SBCard']");
		System.out.println("List Size: "+allTopics.size());
		if(getWebElement("//div[@class='text-left modal__body__topics cx-text-default']").getText().contains(allTopics.size() + " topics"))
			System.out.println("Total number of topics displayed: "+allTopics.size());
		else
			System.out.println(getWebElement("//div[@class='text-left modal__body__topics cx-text-default']").getText());
	}
	
	@Step("Get Texts from WebElements")
	public List<String> getTextsFromWebElements(String locator) {
        List<WebElement> allWebElements = getWebElements(locator);
        System.out.println("Total View Button Number Counts: "+allWebElements.size());
        List<String> allTexts = new ArrayList<>();
        for (WebElement button : allWebElements){
        	allTexts.add(button.getText());
        }
        return allTexts;
    }
	
	@Step("Get All Remote Practices Labs Topic Under Life Cycle Page")
	public int getAllRemotePracLabsTopicUnderLifeCycle() {
		return getWebElements("//*[@data-auto-id='_RemotePracticeLabs_ -item.title']").size();
	}
	
	@Step("Get All Certification Topic Under Life Cycle Page")
	public int getAllCertificationPrepTopicUnderLifeCycle() {
		return getWebElements("//*[@data-auto-id='_CertificationPrep_ -item.title']").size();
	}
	
	@Step("Get All E-Learning Topic Under Life Cycle Page")
	public int getAllELearningTopicUnderLifeCycle() {
		return getWebElements("//*[@data-auto-id='_ELearning_ -item.title']").size();
	}
	
	@Step("Get total number of topics displayed")
	public int getTotalNumberOfTopicsInTitle() {
		String[] topicsDisplayed = getWebElement("//div[@class='text-left modal__body__topics cx-text-default']").getText().split("\\s+");
		return Integer.parseInt(topicsDisplayed[0]);
	}
	
	@Step("Get Table Column Names for All Product Documentation and Videos Table View")
	public List<String> getAllTableColumnsName() {
		List<WebElement> allColumns = getWebElements("//div[@data-auto-id='ViewAllTable']//table//th");
		List<String> allColumnsName = new ArrayList<String>();
		for(int i = 1; i < allColumns.size(); i++) {
			allColumnsName.add(getWebElement("//div[@data-auto-id='ViewAllTable']//table//th["+i+"]/span").getText());
		}
		return allColumnsName;
	}
	
	@Step("Get Hover Over Operations for Certification Prep")
	public void getHoverOverOperationOnCertificationPrepLink() {
		List<WebElement> certificationPrepLinks = getWebElements("//*[@data-auto-id='_CertificationPrep_ -item.title']");
		for(int i = 1; i <= certificationPrepLinks.size(); i++) {
			hoverOveraWebElement("(//*[@data-auto-id='_CertificationPrep_ -item.title'])[" + i + "]");
			assertTrue(getWebElement("((//*[@data-auto-id='_CertificationPrep_ -item.title'])[1]//following::*[@data-auto-id='recommendedElearning-HoverModal-Description'])["+i+"]").getText().length()>0, "Certification Prep Hover Over Operation is not working in Home Page");
		}
	}
	
	@Step("Get Hover Over Option on Certification Prep Card View")
	public void getHoverOverOptionOnCertPrepCardView() {
			hoverOveraWebElement("(//div[contains(@class,'eLearning__card__title')])[1]/span");
			assertTrue(getWebElement("(//div[contains(@class,'eLearning__card__title')])[1]//div[@data-auto-id='recommendedEl-CF-HoverModal-Description']").getText().length() > 0, "Certification Prep Hover Over Operation is not working in Card View");
	}
	
	@Step("Wait Until Remote Practices Labs Opened")
	public void waitUntilRemotePracticesLabsOpened() {
		while(getTotalNumberOfTabsOpened() <=1 );
	}
	
	@Step("Get Table Data Xpath")
	public String getTableDataXPath(int rowNumber, String dataAutoId) {
		String locator = "(//tbody/tr)[" + rowNumber + "]//*[@data-auto-id='" + dataAutoId + "']";
		return locator;
	}
	
	@Step("Verify Progress Bar displayed for E-Learning in Home Page")
	public boolean verifyELearningProgressBarInHomePage() {
		return isElementPresent("_ELearning_-progressBar");
	}
	
	@Step("Verify Progress Bar displayed for E-Learning in Card View")
	public boolean verifyLearningProgressBarInCardView() {
		return isElementPresent("-progressBar");
	}
	
	@Step("Verify Progress Bar Displayed for E-Learning in Table View")
	public boolean verifyLearningProgressBarInTableView() {
		return isElementPresent("//*[@data-auto-id='Status']/div/div[2]");
	}
	
	@Step("Check Progress Bar for Cousrses which is in Progress in Home Page")
	public boolean checkProgressBarDetailsForLearningInHomePage(String locator) {
		boolean result = true;
		String dataPercentage, status;
		int totalNoOfCourseWithProgressBar = getWebElements("//*[@data-auto-id='" + locator + "']/parent::div/div[1]/span").size();
		for(int i = 1; i <= totalNoOfCourseWithProgressBar; i++) {
			dataPercentage = getWebElement("//*[@data-auto-id='" + locator + "']/parent::div/div[1]/span//following::div[1]").getAttribute("data-percentage");
			status = getWebElement("(//*[@data-auto-id='" + locator + "']/parent::div/div[1]/span)[" + i + "]").getText();
			if(status.equalsIgnoreCase("In Progress")) {
				if(Integer.parseInt(dataPercentage) == 100)
					result = false;
			} else if(status.equalsIgnoreCase("Completed")) {
				if(Integer.parseInt(dataPercentage) != 100)
					result = false;
			}
		}
		return result;
	}
	
	@Step("Check Progress Bar for Cousrses which is in Progress and Completed")
	public boolean checkProgressBarDetailsForLearningInViewAll() {
		boolean result = true;
		String dataPercentage, status;
		int totalNoOfCourseWithProgressBar = getWebElements("//*[@class='requested-status']").size();
		for(int i = 1; i <= totalNoOfCourseWithProgressBar; i++) {
			dataPercentage = getWebElement("(//*[@class='requested-status'])[" + i + "]//following::div[contains(@data-auto-id,'-progressBar')][1]").getAttribute("data-percentage");
			status = getWebElement("(//*[@class='requested-status'])[" + i + "]").getText();
			if(status.equalsIgnoreCase("In Progress")) {
				if(Integer.parseInt(dataPercentage) == 100)
					result = false;
			} else if(status.equalsIgnoreCase("Completed")) {
				if(Integer.parseInt(dataPercentage) != 100)
					result = false;
			}
		}
		return result;
	}
	
	@Step("Get total number of Courses with Completed status for Learning")
	public int getTotalNoOfCompletedCourses(String locator) {
		return getWebElements("//div[@data-auto-id='" + locator + "']//span[text()='Completed']").size();
	}
	
	@Step("Sort by Column Name")
	public void sortByColumnNameInTableView(String columnName) {
		getWebElement("ViewAllTable-columnHeader-" + columnName).click();
	}
	
	@Step("Find total number of courses with Rating in Learning Home Page")
	public int getTotalNoOfCoursesWithRatingsFromHomePage(String dataAutoId, String className) {
		return getWebElements("//*[@data-auto-id='" + dataAutoId + "']//child::*[@class='" + className + "']/span[2]").size();
	}
	
	@Step("Click on Course with Ratings for Learning in Home Page")
	public LearningPage clickOnCourseWithRatingsInHomePage(String dataAutoId, String className) {
		getWebElement("//*[@data-auto-id='" + dataAutoId + "']//child::*[@class='" + className + "']/span[2]").click();
		return this;
	}
	
	@Step("Find total number of courses with Ratings in Learning View All Page")
	public int getTotalNoOfCoursesWithRatingsFromViewAll(String dataAutoId, String className) {
		return getWebElements("//*[contains(@data-auto-id,'" + dataAutoId + "')]//child::*[@class='" + className + "']/span[2]").size();
	}
	
	@Step("Click on Course with Ratings for Learning in Card View")
	public LearningPage clickOnCourseWithRatingsInCardView(String dataAutoId, String className) {
		getWebElement("//*[contains(@data-auto-id,'" + dataAutoId + "')]//child::*[@class='" + className + "']/span[2]").click();
		return this;
	}
	
	@Step("Click on Course with Ratings for Learning in Table View")
	public LearningPage clickOnCourseWithRatingsInTableView(String className) {
		getWebElement("//*[@data-auto-id='Ratings']//child::*[@class='" + className + "']/span[2]").click();
		return this;
	}
	
	@Step("Get Positive Thumbs up and Ratings For Learning in Home Page")
	public String getRatingsInHomePage(String dataAutoId, String className) {
		return getWebElement("//*[@data-auto-id='" + dataAutoId + "']//child::*[@class='" + className + "']/span[2]").getText();
	}
	
	@Step("Get Positive Thumbs up and Ratings For Certification Prep in Card View")
	public String getRatingsInCardView(String dataAutoId, String className) {
		return getWebElement("//*[contains(@data-auto-id,'" + dataAutoId + "')]//child::*[@class='" + className + "']/span[2]").getText();
	}
	
	@Step("Check Positive Thumbs up and Ratings For Certification Prep in Card View")
	public boolean checkRatingsInCardViewDisplayed(String dataAutoId, String className) {
		return this.isElementPresent("//*[contains(@data-auto-id,'" + dataAutoId + "')]//child::*[@class='" + className + "']/span[2]");
	}
	
	@Step("Get Positive Thumbs up and Ratings for Certification Prep in 360 View")
	public String getRatingsIn360View(String className) {
		return getWebElement("//*[@id='learning-details-panel']//child::*[@class='" + className + "']/span[2]").getText();
	}
	
	@Step("Get Positive Thumbs up and Ratings For Certification Prep in Table View")
	public String getRatingsInTableView(String className) {
		return getWebElement("//*[@data-auto-id='Ratings']//child::*[@class='" + className + "']/span[2]").getText();
	}
	
	@Step("Check Class Attribute and get Text in Capitals")
	public String getTextBasedOnClassAttribute(String locator) {
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		WebElement element = getWebElement(locator);
		if(element.getAttribute("class").contains("text-uppercase"))
			return element.getText().toUpperCase();
		else
			return element.getText();
	}
	
	@Step("Get Learning Tile Name")
	public String getLearningTileName() {
		return this.getTextBasedOnClassAttribute("//*[@data-auto-id='Learning Panel']/div/span[1]");
	}
	
	@Step("Get Learning Block tile name")
	public String getLearningBlockTileName(String blockDataAutoId) {
		return this.getTextBasedOnClassAttribute("//*[@data-auto-id='" + blockDataAutoId + "']/div/span");
	}
	
	@Step("Check Ratings and Thumbs Up in 360 View Displayed")
	public boolean checkRatingsandThumbsUpIn360ViewDisplayed(String className) {
		boolean result = false;
		if(this.isElementPresent("//*[@id='learning-details-panel']//child::*[@class='" + className + "']/span[2]") && this.isElementPresent("//*[@id='learning-details-panel']//child::*[@class='" + className + "']/span[1]"))
			result = true;
		return result;
	}
	
	@Step("Get No of Course with Status")
	public int getNoOfCourseWithStatus() {
		List<WebElement> elementsWithStatus = getWebElements("//*[@class='requested-status']");
		getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(elementsWithStatus));
		return elementsWithStatus.size();
	}
	
	@Step("Click on first course with Completed status")
	public LearningPage clickOnFirstCourseWithCompletedStatus() {
		int noOfCourseWithStatus = this.getNoOfCourseWithStatus();
		for(int i = 1; i <= noOfCourseWithStatus; i++) {
			WebElement element = getWebElement("(//*[@class='requested-status'])[" + i + "]");
			getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
			if(element.getText().equalsIgnoreCase("Completed")) {
				element.click();
				return this;
			}
		}
		return this;
	}
	
	@Step("Get no of course with completed status")
	public int getNoOfCourseWithCompletedStatus() {
		int noOfCourseWithStatus = this.getNoOfCourseWithStatus();
		int completedCourseCount = 0;
		for(int i = 1; i <= noOfCourseWithStatus; i++) {
			WebElement element = getWebElement("(//*[@class='requested-status'])[" + i + "]");
			if(element.getText().equalsIgnoreCase("Completed"))
				completedCourseCount++;
		}
		return completedCourseCount;
	}
	
	@Step("Click On Course with Be The First To Rate Experiance")
	public boolean getnoOfCourseWithBeTheFirstToRateExperience() {
		int noofCourseWithStatus = this.getNoOfCourseWithStatus();
		for(int i = 1; i <= noofCourseWithStatus; i++) {
			WebElement element = getWebElement("(//*[@class='requested-status'])[" + i + "]");
			element.click();
			if(this.isElementPresent("//span[text()='Be the first to rate']"))
				return true;
			this.close360View();
		}
		return false;
	}
	
	@Step("Click on Feedback Submit option")
	public LearningPage clickOnFeedbackSubmitOption(String feedbackComment) {
		getWebElement("FeedbackPopup-Comments-Input").sendKeys(feedbackComment);
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='FeedbackPopup-Submit']")));
		getWebElement("FeedbackPopup-Submit").click();
		//getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='requested-status'])")));
		return this;
	}
	
	@Step("Click Thumbs Up Button")
	public LearningPage provideThumbsUpFeedback() {
		getWebElement("thumbUpBtn").click();
		this.clickOnFeedbackSubmitOption("Test Feedback Thumbs Up Option");
		return this;
	}
	
	@Step("Click Thumbs Down Button")
	public LearningPage provideThumbsDownFeedback() {
		getWebElement("thumbDownBtn").click();
		this.clickOnFeedbackSubmitOption("Test Feedback Thumbs Down Option");
		return this;
	}
	
	@Step("Check Selected Feedback Option")
	public String getSelectedFeedbackOption() {
		if(getWebElement("thumbUpBtn").getAttribute("class").contains("btn--selected"))
			return "Thumbs Up";
		else if(getWebElement("thumbDownBtn").getAttribute("class").contains("btn--selected"))
			return "Thumbs Down";
		else return "No Feedback Selected";
	}
	
	@Step("Get Feedback Submit Message")
	public boolean checkFeedbackSubmitMessageDisplayed() {
		//WebDriver driver = getWebDriver();
		return getDriver().findElement(By.xpath("//*[text()='Thank you for your feedback!']")).isDisplayed();
		//return this.isElementPresent("//*[text()='Thank you for your feedback!']");
	}
	
	@Step("Get Total No of Courses displayed in Card View")
	public int getTotalNoofCoursesDisplayedInCardView(String locator) {
		return getWebElements("//*[contains(@data-auto-id,'" + locator + "')]").size();
	}
	
	@Step("Get Total number of tiles with status")
    public HashMap getTotalNoOfTilesWithStatus(String learningCardID, String learningBookmarkID) {
    	HashMap<String, Integer> statusCount = new HashMap<String, Integer>();
    	int totalNoOfCards = getTotalNoofCoursesDisplayedInCardView(learningCardID);
    	String bookmark = null;
    	String status = null;
    	statusCount.put("In Progress", 0);
		statusCount.put("Bookmarked", 0);
		statusCount.put("Completed", 0);
		statusCount.put("Launched", 0);
		statusCount.put("Not Launched", 0);
    	for(int i = 0; i < totalNoOfCards; i++) {
    		if(this.isElementPresent("//*[@data-auto-id='RPL-Card--" + i + "']//*[contains(@class,'status-label')][1]")) {
    			statusCount.put("Launched", statusCount.get("Launched")+1);
    		} else
    			statusCount.put("Not Launched", statusCount.get("Not Launched")+1);
    		if(getWebElement("//*[@data-auto-id='" + learningBookmarkID + "--" + i + "']/span").getAttribute("class").contains("ribbon__blue")) {
    			System.out.println("Inside Bookmark");
    			statusCount.put("Bookmarked", statusCount.get("Bookmarked")+1);
    		}
    		if(this.isElementPresent("//*[@data-auto-id='" + learningCardID + "--" + i + "']//*[@class='status']/span")) {
    			status = getWebElement("//*[@data-auto-id='" + learningCardID + "--" + i + "']//*[@class='status']/span").getText();
    			statusCount.put(status, statusCount.get(status)+1);
    		}
    	}
    	System.out.println(statusCount);
    	return statusCount;
    }
	
	@Step("Apply Filter from View All Page")
    public void getTotalFilteredELearningInViewAllPage(String filterName, String filterValue) {
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
	
	@Step("Click Filter Clear Button")
    public LearningPage clickFilterMultiSelectClearButton() {
		getWebElement("MultiSelect-ClearButton").click();
    	return this;
    }
	
	@Step("Click Filter Save Button")
    public LearningPage clickFilterMultiSelectSaveButton() {
    	getWebElement("MultiSelect-SaveButton").click();
    	return this;
    }
	
	@Step("Click on Course with Specific Title")
	public boolean clickOnCourseWithTitle(String title) {
		System.out.println(title);
		List<WebElement> elements = getWebElements("//span[text()='" + title + "']");
		try {
		if(elements.size() > 1) {
			elements.get(1).click();
			return true;
		} else if(elements.size() == 1) {
			elements.get(0).click();
			return true;
		} else if(this.isElementPresent("//div[text()='" + title + "']")) {
			getWebElement("//div[text()='" + title + "']").click();
			return true;
		}
		} catch(ElementNotInteractableException e) {
			if(this.isElementPresent("//div[text()='" + title + "']")) {
				getWebElement("//div[text()='" + title + "']").click();
				return true;
			}
		}
			return false;
	}
	
	@Step("Get Title From eLearning Portal")
	public String getTitleFromELearningPortal() {
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='heading heading--s text-white']")));
		WebElement element = getDriver().findElement(By.xpath("//*[@class='heading heading--s text-white']"));
		if(!element.isDisplayed()) 
			getDriver().findElement(By.xpath("//*[@class='btn navbar-toggle']")).click();
		System.out.println(element.getText());
		return element.getText();
	}
	
	@Step("Status with Specific Title")
	public String getStatsWithSpecificTitleInCardView(String title) {
		return getWebElement("//span[text()='" + title + "']//following::*[@class='requested-status'][1]").getText();
	}
	
	@Step("Check Feedback with Specific Title displayed")
	public boolean checkRatingsandFeedbackWithTitleDisplayed(String title, String className) {
		try {
			getWebElement("//span[text()='" + title + "']//following::*[@class='" + className + "']/span[2]").isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	@Step("Get Feedback Percentage with specific to title")
	public String getRatingsandFeedbackWithTitle(String title, String className) {
		return getWebElement("//span[text()='" + title + "']//following::*[@class='" + className + "']/span[2]").getText();
	}	
}