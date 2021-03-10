package com.cisco.pages.lifecycle;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cisco.utils.LifeCycleUtils;

import io.qameta.allure.Step;

public class CiscoCommunityPage extends LifeCycleUtils {
	
	@Step("Click Success Track Community Link")
	public CiscoCommunityPage clickSuccessTrackCommunityLink() {
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='PanelTitle-PrivateCommunities']")));
		getWebElement("PanelTitle-PrivateCommunities").click();
		return this;
	}
	
	@Step("Click Public Community Link")
	public CiscoCommunityPage clickPublicCommunityLink() {
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='PanelTitle-PublicCommunities']")));
		getWebElement("PanelTitle-PublicCommunities").click();
		return this;
	}
	
	@Step("Get Public Community URL")
	public String getPublicCommunityURL() {
		return getWebElement("communitytitle-Public Communities-URL").getAttribute("href");
	}
	
	@Step("Click on Racecar icon")
	public CiscoCommunityPage clickOnRaceCar() {
		getWebElement("racecar").click();
		return this;
	}
	
	@Step("Get Success Track Community Text")
	public String getSuccessTrackCommunityText() {
		return getWebElement("//*[@data-auto-id='PanelTitle-PrivateCommunities']/span/a").getText();
	}
	
	@Step("Check Element displayed on the Web Page")
	public boolean IsDisplayed(String locator) {
		try { 
			getWebElement(locator).isDisplayed();
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	@Step("Get Top 1 Notification in Public community")
	public boolean getTop1PublicNotification() {
		return this.IsDisplayed("(//*[@id='communitytitle'])[2]");
	}
	
	@Step("Get Top 1 Notification in Success Track community")
	public boolean getTop1SuccessTrackNotification() {
		return this.IsDisplayed("(//*[@id='communitytitle'])[1]");
	}
	
	@Step("Get Cisco Community Tile Title")
	public String getCiscoCommunityTileTitle() {
		return getWebElement("PanelTitle-Communities").getText();
	}
	
	@Step("Check Ask Q&A Panel Displayed")
	public boolean checkAskQandADisplayed() {
		return this.isElementPresent("ATXQnASessionTitle");
	}
	
	@Step("Click on Ask Q&A Title")
	public CiscoCommunityPage clickOnAskQandATitle() {
		getWebElement("ATXQnASessionTitle").click();
		return this;
	}
	
	@Step("Get Session Title From Home Page")
	public String getAskQandASessionTitleFromHomePage() {
		return getWebElement("ATXQnASessionTitle").getText();
	}
	
	@Step("Check Ask Q&A 360 View Displayed")
	public boolean checkAskQandA360ViewDisplayed() {
		return this.isElementPresent("QnASession-details-panel"); 
	}
	
	@Step("Get Ask Q&A Session title from 360 View")
	public String getAskQandASessionTitleFrom360View() {
		return getWebElement("//*[contains(@class,'session-header')]/span").getText();
	}
	
	@Step("Get Ask Q&A Event Time From Home Page")
	public String getAskQandAEventTimeFromHomePage() {
		return getWebElement("//*[contains(@class,'QnASubtitle')]/div").getText();
	}
	
	@Step("Get Ask Q&A Event Time From 360 View")
	public String getAskQandAEventTimeFrom360View() {
		return getWebElement("//*[contains(@class,'session-time')]/span").getText();
	}
	
	@Step("Check Add To Calender Button present in the Ask Q&A 360 View")
	public boolean checkAddToCalenderDisplayedIn360View() {
		return this.isElementPresent("AddToCalendar");
	}
	
	@Step("Check Presenter Information Displayed")
	public boolean verifyPresenterInformationDisplayedForAskQandA360View() {
		boolean result = true;
		if(this.getWebElements("//*[contains(@class,'presenterImage')]").size()<= 0)
			result = false;
		if(this.getWebElements("//*[@class='presenter-name']").size()<= 0)
			result = false;
		if(this.getWebElements("//*[contains(@class,'presenter-jobTitle')]").size()<= 0)
			result = false;
		if(this.getWebElements("//*[@class='presenter-description']").size()<= 0)
			result = false;
		return result;
	}
	
	@Step("Get Top Success Track Community Topic")
	public String getTopSuccessTrackCommunityTopic() {
		return getWebElement("(//*[@id='communitytitle'])[1]").getText();
	}
	
	@Step("Get Top Public Community Topic")
	public String getTopPublicCommunityTopic() {
		return getWebElement("(//*[@id='communitytitle'])[2]").getText();
	}
	
	@Step("Click Success Track Community URL with Credentials")
	public CiscoCommunityPage loadSuccessTrackCommunityURLWithCred() {
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='PanelTitle-PrivateCommunities']/span/a")));
		String successTrackURL = getWebElement("//*[@data-auto-id='PanelTitle-PrivateCommunities']/span/a").getAttribute("href");
		String successTrackURLWithCerdentials = successTrackURL.split("//")[0] + "//" + System.getenv("community_username") + ":" + System.getenv("community_password") + "@" + successTrackURL.split("//")[1];
		System.out.println("Success Track URL with appended Credentials: "+successTrackURLWithCerdentials);
		this.clickSuccessTrackCommunityLink().switchTab();
		getDriver().get(successTrackURLWithCerdentials);
		try {
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='close_btn_thick']")));
		getDriver().findElement(By.xpath("//*[@class='close_btn_thick']")).click();
		} catch (Exception e) {
			System.out.println("Close button is not displayed");
		}
		return this;
	}
	
	@Step("Get Latest Post Time For Success Track Community URL")
	public String getLatestPostFromSuccessTrackCommunityURL(String topicName) {
		this.loadSuccessTrackCommunityURLWithCred();
		int totalTopicSize = getDriver().findElements(By.xpath("//*[@itemprop='name']/span/a")).size();
		for(int i = 1; i <= totalTopicSize; i++) {
			String topic = getDriver().findElement(By.xpath("(//*[@itemprop='name']/span/a)[" + i + "]")).getText();
			System.out.println("Topic Name: "+topic);
			if(topic.contains("...")) {
				topic = topic.split("\\.\\.\\.")[0];
			}
			System.out.println("Topic Name: "+topic);
			if(topicName.contains(topic)) {
				String date = getDriver().findElement(By.xpath("((//*[@itemprop='name']/span/a)[" + i + "]//following::span[@class='local-date'][2])")).getText();
				String time = getDriver().findElement(By.xpath("((//*[@itemprop='name']/span/a)[" + i + "]//following::span[@class='local-time'][2])")).getText();
				this.switchToMainTab();
				return date + " " + time;
			}
		}
		return "";
	}
	
	@Step("Get Latest Post Time For Success Track Community URL")
	public String getLatestPostFromPublicCommunityURL(String topicName) {
		this.clickPublicCommunityLink().switchTab();
		getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@itemprop='name']/span/a")));
		int totalTopicSize = getDriver().findElements(By.xpath("//*[@itemprop='name']/span/a")).size();
		System.out.println("totalTopicSize: "+totalTopicSize);
		for(int i = 1; i <= totalTopicSize; i++) {
			String topic = getDriver().findElement(By.xpath("(//*[@itemprop='name']/span/a)[" + i + "]")).getText();
			System.out.println("Topic Name: "+topic);
			if(topic.contains("...")) {
				topic = topic.split("\\.\\.\\.")[0];
			}
			System.out.println("Topic Name: "+topic);
			if(topicName.contains(topic)) {
				String date = getDriver().findElement(By.xpath("((//*[@itemprop='name']/span/a)[" + i + "]//following::span[@class='local-date'][1])")).getText();
				String time = getDriver().findElement(By.xpath("((//*[@itemprop='name']/span/a)[" + i + "]//following::span[@class='local-time'][1])")).getText();
				this.switchToMainTab();
				return date + " " + time;
			}
		}
		return "";
	}
	
	@Step("Get Last Comment from Success Track Top Notification")
	public String getLastCommentfromSuccessTrackTopNotification() {
		return getWebElement("(//*[@class='cx-lifecycle-card--subtitle'])[1]").getText();
	}
	
	@Step("Get Last Comment from Public Community Top Notification")
	public String getLastCommentfromPubicCommunityTopNotification() {
		return getWebElement("(//*[@class='cx-lifecycle-card--subtitle'])[2]").getText();
	}
	
	@Step("Click On Q and A Session")
	public CiscoCommunityPage clickOnQandASession() {
		getWebElement("ATXQnASessionTitle").click();
		return this;
	}
	
	@Step("Click on ASK Q&A Session Join Discussion Button")
	public CiscoCommunityPage clickOnAskQandAJoinDiscussionButton() {
		getWebElement("JoinDiscussionButton").click();
		return this;
	}
	
	@Step("Get Q and A session Topic from Lifecycle Page")
	public String getQandATopicNameFromLifecyclePage() {
		return this.getWebElement("ATXQnASessionTitle").getText();
	}
	
	@Step("Get Q and A session topic from Community Page")
	public String getQandATopicNameFromCommunityPage() {
		return getDriver().getTitle();
	}
	
}
