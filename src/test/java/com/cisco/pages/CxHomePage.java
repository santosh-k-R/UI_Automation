package com.cisco.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cisco.utils.AppUtils;

import io.qameta.allure.Step;

public class CxHomePage extends AppUtils {

	public static final String AMDOCS_COMM = "AMDOCS COMM";
	public static final String STATE_OF_MARYLAND = "STATE OF MARYLAND WCC";
	public static final String IBN = "IBN";
	public static final String SMART_ACCOUNT = "Smart Account";
	public static final String SUCCESS_TRACK = "filter0";
	public static final String USE_CASE = "filter1";

	/**
	 * <p>Click Feedback button present on home page</p>
	 * */
	@Step("Clicks the Feedback Button present on CxHomePage")
	public CxHomePage clickFeedBackButton() {
		String feedBackButtonCssSelector = "#slideout>div>div";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(feedBackButtonCssSelector)));
//		ngWebDriver.waitForAngularRequestsToFinish();
		getActions().moveToElement(getWebElement(feedBackButtonCssSelector)).click().build().perform();
		return this;
	}

	/**
	 * <p>Returns Feedback modal title</p>
	 * @return String
	 */
	@Step("Get Feedback Modal Title")
	public String getFeedbackModalTitle() {
		String feedBackModalTitleCssSelector = "[class^='modal__title']";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(feedBackModalTitleCssSelector)));
		return getWebElement(feedBackModalTitleCssSelector).getText();
	}

	/**
	 * <p>Pass comment to Feedback modal</p>
	 * @param comment
	 */
	@Step("Type the Comment in the Comment text box")
	public CxHomePage typeComment(String comment) {
		getWebElement(".form-group__text>textarea").sendKeys(comment);
		return this;
	}

	/**
	 * <p>Click Like button  present on Feebback modal</p>
	 * @author sidraman
	 */
	@Step("Click the Like button")
	public CxHomePage clickLikeButton() {
		getWebElement("thumbUpBtn").click();
		return this;
	}

    /**
     * <p>Click Submit button present on Feedback modal</p>
     * @author sidraman	
     */
	@Step("Click the Submit Button")
	public CxHomePage clickSubmit() {
		getWebElement("[data-auto-id='submitBtn']").click();
		return this;
	}
	
	/**
	 *<p>Returns success message present on Feedback modal </p> 
	 * @return String
	 */
	@Step("Get Feedback success message")
	public String getFeedbackMessage() {
		String feedbackMessageCssSelector = ".modal__body>span";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(feedbackMessageCssSelector)));
		return getWebElement(feedbackMessageCssSelector).getText();
	}

	/**
	 * <p>Click CLOSE button present on Feedback window</p>
	 * @author sidraman
	 */
	@Step("Close the Feedback Window")
	public CxHomePage clickCloseButton() {
		getWebElement("[data-auto-id='closeButton']").click();
		return this;
	}

	/**
	 * <p>Click Right Carousel present on home page</p>
	 * @author sidraman
	 */
	@Step("Click Right Carousel")
	public CxHomePage clickRightCarousel() {
		String rightCarousalCssSelector = "[data-auto-id='carousel-right']";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(rightCarousalCssSelector)));
		getWebElement(rightCarousalCssSelector).click();
		return this;
	}

	/**
	 * <p>Click Cisco Community link present on header</p>
	 * @author sidraman
	 * @return
	 */
	@Step("Click Cisco Community Link")
	public CxHomePage clickCiscoCommunityLink() {
		String ciscoCommunityLinkCssSelector = "[data-auto-id='HeaderCommunityLink']>div";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ciscoCommunityLinkCssSelector)));
		getWebElement(ciscoCommunityLinkCssSelector).click();
		return this;
	}

	/**
	 * <p>Click Learning link present on header</p>
	 * @author sidraman
	 * */
	@Step("Click Learning Link")
	public CxHomePage clickLearningLink() {
		String learningLinkCssSelector = "[data-auto-id='HeaderLearningLink']>div";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(learningLinkCssSelector)));
		getWebElement(learningLinkCssSelector).click();
		return this;
	}

}
