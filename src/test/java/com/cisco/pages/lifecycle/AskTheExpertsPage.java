package com.cisco.pages.lifecycle;

import com.cisco.testdata.lifecycle.ATXPoJo;
import com.cisco.utils.LifeCycleUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AskTheExpertsPage extends LifeCycleUtils {

    public static final String ATXHomePageDataAutoId = "recommendedATX-Tile";
    public static final String ATXCardVieweDataAutoId = "ATXCard";
    public static final String ATXTableVieweDataAutoId = "ATX-Title";
    public static final String ATXFeedbackPositiveThumbsUpClassName = "feedback-positive";
    public static final String ATXFeedbackRatingClassName = "feedback-count";
    public static final String ATX360ViewDataAutoId = "atx-details-panel";
    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    static int counter = 0;
    boolean icon;
    boolean status;
    String text;
    List<String> ATXTexts;

    @Step("Get the ATX Subtitles from lifecycle page")
    public List<String> getATXSubtitles() {
        waitForInitialPageToLoad();
        status = isElementPresent("//div/span[@data-auto-id='PanelTitle-_AskTheExperts_']/../following-sibling::div//div/p");
        List<String> ATXTexts = new ArrayList<>();
        if (status) {
            List<WebElement> ATXsubtitles = getWebElements("//div/span[@data-auto-id='PanelTitle-_AskTheExperts_']/../following-sibling::div//div/p[contains(@class,'card__title')]");
            for (WebElement ATXsubtitle : ATXsubtitles) {
                ATXTexts.add(ATXsubtitle.getText());
            }
        } else {

            throw new SkipException("There are no atxs in lifecycle's page");
        }

        return ATXTexts;
    }

    @Step("Get the ATX Subtitles from lifecycle view all page")
    public List<String> getATXViewAllSubtitles() {
        waitForInitialPageToLoad();
        List<WebElement> ATXsubtitles;
        String text = getWebElement("//button[contains(@class,' selected')]").getAttribute("aria-label");
        if (text.equals("Card View")) {
            status = isElementPresent("//div/span[contains(@class,'title atx__card')]");
            ATXsubtitles = getWebElements("//div/span[contains(@class,'title atx__card')]");
        } else {
            ATXsubtitles = getWebElements("//div[@data-auto-id='ATX-Title']");
        }
        List<String> ATXTexts = new ArrayList<>();
        for (WebElement ATXsubtitle : ATXsubtitles) {
            ATXTexts.add(ATXsubtitle.getText());
        }
        return ATXTexts;
    }

    @Step("Get the ATX completedicon")
    public Boolean getATXcompletedicon() throws InterruptedException {
        List<String> ATXTexts = getATXSubtitles();
        for (String title : ATXTexts) {
            counter++;
            try {
                status = getWebElement("//p[@data-auto-id='" + title + "-Title']/following-sibling::div/span[2]").isDisplayed();
                if (status)
                    icon = getWebElement("//p[@data-auto-id='" + title + "-Title']/following-sibling::div/span[1]").isDisplayed();
            } catch (NoSuchElementException ignored) {
                if (counter == 3) {
                    throw new SkipException("There are no atx which are completed in lifecycle's page");
                }
            }
        }
        return icon;
    }

    @Step("Registering atx from the lifecycle page")
    public boolean registerATXfromHome() throws InterruptedException {
        ATXTexts = getATXSubtitles();
        for (String subtitle : ATXTexts) {
            counter++;
            icon = isElementPresent("//div/div[@data-auto-id='" + subtitle + "-Date']");
            {
                if (icon != true) {

                    try {
                        getWebElement("//p[contains(text(),'" + subtitle + "')]").click();
                        getWebElement("//tbody/tr[@data-index='0']").click();
                        getWebElement("AtxScheduleCardRegisterButton").click();
                        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[@data-auto-id='" + subtitle + "-Date']")));
                        status = isElementPresent("//div/div[@data-auto-id='" + subtitle + "-Date']");
                        if (status) {
                            break;
                        }

                    } catch (NoSuchElementException ignored) {
                        if (counter == 3) {
                            throw new SkipException("There are no atx sessions present for these lifecycle's page ATXs");
                        }
                    }
                } else {
                    if (counter == 3) {
                        throw new SkipException("All atx sessions are scheduled in lifecycle's page");
                    }
                }
            }
        }
        return status;
    }

    @Step("verify atx cancel button enables when register for any ATX from the lifecycle page")
    public boolean isATXCancelButtonEnabled() {
        return getWebElement("//*[@data-auto-id='AtxScheduleCardCancelButton']").isEnabled();
    }

    @Step("select the schduled session and cancel")
    public AskTheExpertsPage clickCancelButton() {
        String locator = "//*[@data-auto-id='AtxScheduleCardCancelButton']";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        getWebElement(locator).click();
        return this;
    }

    @Step("Registering atx from the view All page")
    public boolean registerAtxfromViewAll(String name) throws InterruptedException {
        String subtitle = getATXViewAllSubtitles().get(0);
        if (name.equals("Card")) {
            icon = isElementPresent("//span[text()='" + subtitle + "']/../following-sibling::div//div[contains(@data-auto-id,'ScheduledIcon')]");
        } else {
            icon = isElementPresent("//div[text()='" + subtitle + "']/ancestor::td/following-sibling::td//span[contains(@data-auto-id,'Scheduled-Icon')]");
        }

        if (icon != true) {
            try {
                ;
                counter++;
                getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-auto-id='ATXCardTitle']//span[contains(text(),'" + subtitle + "')]")));
                getDriver().findElement(By.xpath("//*[@data-auto-id='ATXCardTitle']//span[contains(text(),'" + subtitle + "')]")).click();
//	        	    getWebElement("//span[contains(text(),'"+ subtitle +"')]").click();
                getWebElement("//tbody/tr[@data-index='0']").click();
                getWebElement("AtxScheduleCardRegisterButton").click();
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'card__footer')]//span")));
                status = isElementPresent("//div[contains(@class,'card__footer')]//span");
            } catch (NoSuchElementException ignored) {
                if (counter == 1) {
                    throw new SkipException("There are no atx sessions present for theATXs");
                }
            }
        } else {

            throw new SkipException("All atxs are scheduled ");
        }

        return status;
    }

    @Step("Verify the registered session ")
    public boolean verifyRegisteredSession(String title) {
        try {
            if (getWebElement("AtxScheduleCardRegisterButton").isEnabled()) {
                getWebElement("AtxScheduleCardRegisterButton").click();
                status = getWebElement("//div/div[@data-auto-id='" + title + "-Date']").isDisplayed();
            }
            return status;
        } catch (WebDriverException e) {
            System.out.println("Since there is already a registered session for this ATX topic it is not registrable");
            return false;
        }
    }

    @Step("Verify the registered session from the ViewAll window")
    public boolean verifyViewAllRegisteredSession(String title) {
        try {
            if (getWebElement("AtxScheduleCardRegisterButton").isEnabled()) {
                getWebElement("AtxScheduleCardRegisterButton").click();
                status = getWebElement("//div[text()='" + title + "']/..//div/span[@data-auto-id='ATXCardFooter-ScheduledDate']").isDisplayed();
            }
            return status;
        } catch (WebDriverException e) {
            System.out.println("Since there is already a registered session for this ATX topic it is not registrable");
            return false;
        }
    }

    @Step("Click View All button of ATX tile")
    public AskTheExpertsPage clickATXViewAll() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-auto-id='ShowModalPanel-_AskTheExperts_']")));
        getWebElement("ShowModalPanel-_AskTheExperts_").click();
        return this;
    }

    @Step("Click ATX table view button")
    public AskTheExpertsPage clickATXtableView() {
        getWebElement("atx-table-view-btn").click();
        return this;
    }

    @Step("Get ATX table class")
    public boolean isATXTableDisplayed() {
        return getWebElement("//div[@data-auto-id='ViewAllTable']/div/table").isDisplayed();
    }

    @Step("Click ATX card view button")
    public AskTheExpertsPage clickATXCardView() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='atx-card-view-btn']")));
        getWebElement("atx-card-view-btn").click();
        return this;
    }

    @Step("Get ATX card class")
    public boolean isATXCardDisplayed() {
        return getWebElement("//div[contains(@class,'card-view')]").isDisplayed();
    }

    @Step("Verify enabled cancel button")
    public Boolean verifyEnabledCancelButton() {
        return isElementPresent("//button[@data-auto-id='AtxScheduleCardCancelButton']");
    }

    @Step("Get scheduled atx count")
    public int getScheduledATXcount() {
        List<String> ATXTexts = getATXSubtitles();
        List<WebElement> ScheduledATX = new ArrayList<WebElement>();
        for (String ATXsubtitle : ATXTexts) {
            try {
                status = getWebElement("//div[@data-auto-id='" + ATXsubtitle + "-Date']").isDisplayed();
                if (status) {
                    ScheduledATX.add(getWebElement("//div[@data-auto-id='" + ATXsubtitle + "-Date']"));
                }
            } catch (NoSuchElementException ignored) {
            }
        }
        int count = ScheduledATX.size();
        return count;
    }

    @Step("Get ATX card Scheduled time")
    public String getScheduledTime(String ATXsubtitle) {
        return getWebElement("//p[text()=' " + ATXsubtitle + " ']/following-sibling::div//*[@data-auto-id='recommendedATX-Date']").getText();
    }

    @Step("Get ATX card presenter name")
    public String getPresenterName(String ATXsubtitle) {
        String locator = "//p[text()=' " + ATXsubtitle + " ']/following-sibling::div//*[@data-auto-id='recommendedATX-Presenter']";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        return getWebElement(locator).getText().split(":")[1].trim();
    }

    @Step("verify tooltip text on ATx tile")
    public String getTooltip(String atxTitle) {
        return getWebElement("//*[text()=' " + atxTitle + " ']//div[@data-auto-id='recommendedATX-HoverModal-Description']").getText();
    }

    @Step("Click Bookmark icon")
    public AskTheExpertsPage clickBookmark(String atxTitle) {
        getWebElement("button[aria-label*='" + atxTitle + "']>span").click();
        return this;
    }

    @Step("Click ViewAll Bookmark icon")
    public AskTheExpertsPage clickViewAllBookmark() {
        ATXTexts = getATXViewAllSubtitles();
        String ATXsubtitle = ATXTexts.get(0);
        //getWebElement("//div[text()='"+ATXsubtitle+"']/ancestor::tr/td//button[@data-auto-id='SBListRibbon']").click();
        text = getWebElement("//span[contains(text(),'" + ATXsubtitle + "')]/../../button/span").getAttribute("class");
        if (text.contains("ribbon__blue")) {
            System.out.println("ATX tile is alredy bookmarked");
        } else {
            getWebElement("//span[contains(text(),'" + ATXsubtitle + "')]/../../button").click();
        }
        return this;
    }

    @Step("verify Bookmark icon")
    public String getBookMarkColor(String atxTitle) {
        String locator = getHomeATXTileXpath(atxTitle) + "//button[@data-auto-id='SBCardRibbon']/span";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        return getWebElement(locator).getAttribute("class");
    }

    @Step("verify Bookmark icon in viewAll page")
    public String verifyViewAllBookmark() {
        ATXTexts = getATXViewAllSubtitles();
        String ATXsubtitle = ATXTexts.get(0);
        //return getWebElement("//div[text()='"+ATXsubtitle+"']/ancestor::tr/td//button[@data-auto-id='SBListRibbon']/div").getAttribute("class");
        return getWebElement("//span[contains(text(),'" + ATXsubtitle + "')]/../../button/span").getAttribute("class");
    }

    @Step("Get ATX Image attribute")
    public String getSourceAttributeOfATXImage(String atxTitle) {
        return getWebElement("//*[text()=' " + atxTitle + " ']/../..//img").getAttribute("src");
    }

    @Step("Get opened Cisco ATX 360")
    public boolean openciscoATX360FromHome() {
        ATXTexts = getATXSubtitles();
        String ATXsubtitle = ATXTexts.get(0);
        getWebElement("//p[contains(text(),'" + ATXsubtitle + "')]").click();
        return getWebElement("//div[contains(@class,'not-close-360')]").isDisplayed();
    }

    @Step("Get opened Cisco ATX 360 from view All card")
    public boolean openciscoATX360FromViewAllCard() {
        ATXTexts = getATXViewAllSubtitles();
        String ATXsubtitle = ATXTexts.get(0);
        getWebElement("//*[@data-auto-id='ATXCardTitle']//span[contains(text(),'" + ATXsubtitle + "')]").click();
        return getWebElement("//div[contains(@class,'not-close-360')]").isDisplayed();
    }

    @Step("Get opened Cisco ATX 360 from view All table ")
    public boolean openciscoATX360FromViewAllTable() {
        ATXTexts = getATXViewAllSubtitles();
        String ATXsubtitle = ATXTexts.get(0);
        getWebElement("//div[text()='" + ATXsubtitle + "']").click();
        return getWebElement("//div[contains(@class,'not-close-360')]").isDisplayed();
    }

    @Step("Get opened Cisco ATX 360 details")
    public boolean getATX360details(String name) throws InterruptedException {
//		 SoftAssert softAssert = new SoftAssert();
        ArrayList<String> Atxlist = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        if (name.equals("Lifecycle")) {
            temp = verifyLifecycleATXtileMatchesMockup();
            openciscoATX360FromHome();
        } else if (name.equals("viewAllCard")) {
            temp = verifyViewAllATXtileMatchesMockup();
            openciscoATX360FromViewAllCard();
        } else {
            temp = verifyViewAllATXtileMatchesMockup();
            isATXTableDisplayed();
            openciscoATX360FromViewAllTable();
        }
        if (isElementPresent("//div[contains(@class,'not-close-360')]//div[contains(@class,'atx-header')]/span")) {
            text = getWebElement("//div[contains(@class,'not-close-360')]//div[contains(@class,'atx-header')]/span").getText();
            Atxlist.add(text);
        }
        if (isElementPresent("//div[contains(@class,'not-close-360')]//div[contains(@class,'feedback-count')]")) {
            status = getWebElement("//div[contains(@class,'not-close-360')]//div[contains(@class,'feedback-count')]/span[1]").isDisplayed();
            Atxlist.add(String.valueOf(status));
            text = getWebElement("//div[contains(@class,'not-close-360')]//div[contains(@class,'feedback-count')]/span[2]").getText();
            Atxlist.add(text);
        }
        if (isElementPresent("//div[contains(@class,'not-close-360')]//div[contains(@class,'feedback-positive')]")) {
            status = getWebElement("//div[contains(@class,'not-close-360')]//div[contains(@class,'feedback-positive')]/span[1]").isDisplayed();
            Atxlist.add(String.valueOf(status));
            text = getWebElement("//div[contains(@class,'not-close-360')]//div[contains(@class,'feedback-positive')]/span[2]").getText();
            Atxlist.add(text);
        }
        if (isElementPresent("//div[contains(@class,'not-close-360')]//div[@class='item-status']")) {
            if (isElementPresent("//div[contains(@class,'not-close-360')]//div/span[text()='Partner']")) {
                text = getWebElement("//div[contains(@class,'not-close-360')]//div[@class='item-status']/span[4]").getText();
            } else {
                text = getWebElement("//div[contains(@class,'not-close-360')]//div[@class='item-status']/span[3]").getText();
            }
            Atxlist.add(text);
        }
        if (isElementPresent("//div[contains(@class,'not-close-360')]//div/span[text()='Partner']")) {
            text = getWebElement("//div[contains(@class,'not-close-360')]//div/span[text()='Partner']/../span[3]").getText();
            Atxlist.add(text);
        }
        status = temp.equals(Atxlist);

        if (Atxlist.get(Atxlist.size() - 1).equals("Completed")) {
            icon = getWebElement("//session-feedback/button/a[@data-auto-id='thumbUpBtn']").isDisplayed() && getWebElement("//session-feedback/button/a[@data-auto-id='thumbDownBtn']").isDisplayed();
//			 //add the code for 'first to rate' in or condition in softassert
        }
        return status;
    }

    @Step("Get lifecycle ATX tile details")
    public ArrayList<String> verifyLifecycleATXtileMatchesMockup() {
        SoftAssert softAssert = new SoftAssert();
        ATXTexts = getATXSubtitles();
        ArrayList<String> ATXtileList = new ArrayList<String>();
        if (!ATXTexts.isEmpty()) {
            ATXtileList.add(ATXTexts.get(0));
            if (isElementPresent("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div/div[@class='feedback-count']")) {
                status = getWebElement("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div/div[@class='feedback-count']/span[1]").isDisplayed();
                ATXtileList.add(String.valueOf(status));
                softAssert.assertTrue(status, "asset completion mark is not displayed");
                text = getWebElement("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div/div[@class='feedback-count']/span[2]").getText();
                ATXtileList.add(text);
                softAssert.assertNotNull(text, "asset completion count is not displayed");
            }
            if (isElementPresent("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div/div[contains(@class,'feedback-positive')]")) {
                status = getWebElement("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div/div[2]/span[1]").isDisplayed();
                ATXtileList.add(String.valueOf(status));
                softAssert.assertTrue(status, "feedback thumbsup mark is not displayed");
                text = getWebElement("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div/div[2]/span[2]").getText();
                ATXtileList.add(text);
                softAssert.assertNotNull(text, "feedback engagement percenatge is not displayed");
            }
            if (isElementPresent("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div[3]")) {
                status = getWebElement("//span[contains(@data-auto-id,'" + ATXtileList.get(0) + "')][1]").isDisplayed();
                softAssert.assertTrue(status, "atx completion mark is not displayed");
                text = getWebElement("//span[contains(@data-auto-id,'" + ATXtileList.get(0) + "')][2]").getText();
                ATXtileList.add(text);
                softAssert.assertNotNull(text, "ATX completed status is not displayed");
            } else if (isElementPresent("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div[2]/div/..")) {
                status = getWebElement("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div[2]/div[1]").isDisplayed();
                softAssert.assertTrue(status, "atx scheduled mark is not displayed");
                text = getWebElement("//p[contains(text(),'" + ATXtileList.get(0) + "')]/../div[2]/div[2]/div").getText();
                ATXtileList.add(text);
                softAssert.assertNotNull(text, "ATX scheduled date is not displayed");
            }

            if (isElementPresent("//p[@data-auto-id='" + ATXtileList.get(0) + "-ProviderText']")) {
                text = getWebElement("//p[@data-auto-id='" + ATXtileList.get(0) + "-ProviderText']").getText();
                ATXtileList.add(text);
                softAssert.assertNotNull(text, "partner name is not displayed");
            }
            softAssert.assertAll();
        } else {
            throw new SkipException("Partner ATXes are not available. Hence there is no data available to test this scenario");
        }
        return ATXtileList;
    }

    @Step("Get view All ATX tile details")
    public ArrayList<String> verifyViewAllATXtileMatchesMockup() {
//		 SoftAssert softAssert = new SoftAssert();
        ATXTexts = getATXViewAllSubtitles();
        ArrayList<String> ATXtileList = new ArrayList<String>();
        ATXtileList.add(ATXTexts.get(0));
        if (isElementPresent("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div/div[@class='feedback-count']")) {
            status = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div/div[@class='feedback-count']/span[1]").isDisplayed();
            ATXtileList.add(String.valueOf(status));
//			 softAssert.assertTrue(status, "asset completion mark is not displayed");
            text = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div/div[@class='feedback-count']/span[2]").getText();
            ATXtileList.add(text);
//			 softAssert.assertNotNull(text,"asset completion count is not displayed");
        }
        if (isElementPresent("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div/div[contains(@class,'feedback-positive')]")) {
            status = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div/div[contains(@class,'feedback-positive')]/span[1]").isDisplayed();
            ATXtileList.add(String.valueOf(status));
//			 softAssert.assertTrue(status, "feedback thumbsup mark is not displayed");
            text = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div/div[contains(@class,'feedback-positive')]/span[2]").getText();
            ATXtileList.add(text);
//			 softAssert.assertNotNull(text,"feedback engagement percenatge is not displayed");
        }
        if (!(getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div//div[contains(@class,'card__status')]").getText()).isEmpty()) {
            status = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div//div[contains(@class,'card__status')]/span[1]").isDisplayed();
//			 softAssert.assertTrue(status, "atx completion mark is not displayed");
            text = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div//div[contains(@class,'card__status')]/span[2]").getText();
            ATXtileList.add(text);
//			 softAssert.assertNotNull(text,"ATX completed status is not displayed");
        } else if (isElementPresent("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div//div[contains(@data-auto-id,'ScheduledIcon')]")) {
            status = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div//div[contains(@data-auto-id,'ScheduledIcon')]").isDisplayed();
//			 softAssert.assertTrue(status, "atx scheduled mark is not displayed");
            text = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div//span[contains(@data-auto-id,'ScheduledDate')]").getText();
            ATXtileList.add(text);
//			 softAssert.assertNotNull(text,"ATX scheduled date is not displayed");}

            if (isElementPresent("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div[contains(@class,'card__header')]")) {
                text = getWebElement("//span[contains(text(),'" + ATXtileList.get(0) + "')]/../../div[contains(@class,'card__header')]").getText();
                int no = text.length();
                if (no > 0) {
                    ATXtileList.add(text);
//				 softAssert.assertNotNull(text,"partner name is not displayed");
                }

            }
//		 softAssert.assertAll();

        }
        return ATXtileList;
    }

    @Step("Get the ATX Titles from lifecycle home page")
    public int getATXHomePagecount() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-auto-id='recommendedATX-Tile']")));
        return getWebElements("[data-auto-id='recommendedATX-Tile']").size();
    }

    @Step("Get total number of Cards Displayed in Card View")
    public int getTotalNoOfCardsDisplayed() {
        return getDriver().findElements(By.xpath("//div[contains(@class,'card-view')]//div[@class='card-description']")).size();
    }

    @Step("Get Total number of tiles with status")
    public HashMap<String, Integer> getTotalNoOfTilesWithStatus() {
        HashMap<String, Integer> statusCount = new HashMap<String, Integer>();
        int totalNoOfCards = getTotalNoOfCardsDisplayed();
        statusCount.put("Scheduled", 0);
        statusCount.put("In Progress", 0);
        statusCount.put("Completed", 0);
        for (int i = 1; i <= totalNoOfCards; i++) {
            try {
                String status = getWebElement("(//*[@data-auto-id='ATXCardFooter'])[" + i + "]/div/span[2]").getText();
                statusCount.put(status, statusCount.get(status) + 1);
            } catch (NoSuchElementException e) {

            }
        }
        return statusCount;
    }

    @Step("Click Filter Clear Button")
    public AskTheExpertsPage clickFilterMultiSelectClearButton() {
        getWebElement("MultiSelect-ClearButton").click();
        return this;
    }

    @Step("Click Filter Save Button")
    public AskTheExpertsPage clickFilterMultiSelectSaveButton() {
        getWebElement("MultiSelect-SaveButton").click();
        return this;
    }

    @Step("Apply ATX Filter in View All {0} and {1}")
    public AskTheExpertsPage applyATXFilterInViewAllPage(String filterName, String... filterValues) {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-auto-id='ViewAllModal-StatusMultiFilter']")));
        getWebElement("//*[@data-auto-id='ViewAllModal-StatusMultiFilter']").click();
        getWebElement("MultiSelect-ClearButton").click();
        for (String filterValue : filterValues) {
            getWebElement("//*[@data-auto-id='ViewAllModal-" + filterName + "MultiFilter']//*[text()='" + filterValue + "']").click();
        }
        getWebElement("MultiSelect-SaveButton").click();
        return this;
    }

    @Step("verify 360 bookmark button functionality in homepage")
    public String bookmarkATX360FromHome() throws InterruptedException {
        openciscoATX360FromHome();
        text = getWebElement("//div[@class='button-ribbon']/button/span").getAttribute("class");
        if (text.contains("ribbon__blue")) {
            System.out.println("ATX tile is already bookmarked");
        } else {
            getWebElement("//div[@class='button-ribbon']/button").click();
        }
        text = getWebElement("//div[@class='button-ribbon']/button/span").getAttribute("class");
        getWebElement("//a/span[contains(@class,'icon-close')]").click();
        return text;
    }

    @Step("verify 360 bookmark button functionality in view All page")
    public String bookmarkATX360FromViewAll(String name) {
        clickATXViewAll();
        if (name.equals("Card")) {
            openciscoATX360FromViewAllCard();
        } else {
            isATXTableDisplayed();
            openciscoATX360FromViewAllTable();
        }
        text = getWebElement("//div[@class='button-ribbon']/button/span").getAttribute("class");
        if (text.contains("ribbon__blue")) {
            getWebElement("//div[@class='button-ribbon']/button").click();
            System.out.println("ATX tile was already bookmarked,so unbookmarking");
        }
        getWebElement("//div[@class='button-ribbon']/button").click();
        text = getWebElement("//div[@class='button-ribbon']/button/span").getAttribute("class");
        getWebElement("//a/span[contains(@class,'icon-close')]").click();
        getWebElement("//button/span[contains(@class,'icon-close')]").click();

        return text;
    }


    @Step("verify watch on Demand button functionality in view All page")
    public boolean watchOnDemand(String name) throws InterruptedException {
        try {
            getWebElement("//div/button[@data-auto-id='LaunchCourseButton']").click();
            WebElement el = getWebElement("//iframe[@class='video-player']");
            getWebDriverWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(el));
            getWebDriverWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
//		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'play-button')]")));
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'play-button')]")));
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'play-button')]")));
            getDriver().findElement(By.xpath("//button[contains(@class,'play-button')]")).click();
            getDriver().findElement(By.xpath("//button[contains(@class,'vjs-mute-control')]")).click();
            TimeUnit.MINUTES.sleep(1);
            Actions act = new Actions(getDriver());
            act.moveToElement(getDriver().findElement(By.xpath("//button[contains(@class,'play-control')]"))).click().perform();
            text = getDriver().findElement(By.xpath("//div[@class='vjs-current-time-display']")).getText();
            System.out.println(text);
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='vjs-current-time-display']/span")));
            String inside = getDriver().findElement(By.xpath("//div[@class='vjs-current-time-display']/span")).getText();
            text = text.replace(inside, "").replaceAll("^\\s+|\\s+$", "");
            Time timeValue = Time.valueOf(text);
            Time ti = Time.valueOf("00:00:00");
            int compareValue = timeValue.compareTo(ti);
            status = compareValue > 0;
        } finally {
            getDriver().switchTo().defaultContent();
        }
//	      getDriver().switchTo().defaultContent();
        getDriver().findElement(By.xpath("//a[contains(@data-auto-id,'closeModalIcon')]/span")).click();
        if (name.equals("Table")) {
            getDriver().findElement(By.xpath("//button/span[contains(@class,'icon-close')]")).click();
        }


        return status;
    }

    @Step("Clear contentprovider filter")
    public AskTheExpertsPage clearContentproviderHomepageButton() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//multiselect[@name='Content Provider']/div/span")));
        getWebElement("//multiselect[@name='Content Provider']/div/span").click();
        List<WebElement> contents = getWebElements("//multiselect[@name='Content Provider']//div[@data-auto-id='MultiSelect-DropdownList']/div/div");
        for (WebElement content : contents) {
            text = content.getText();
            if (!text.equals("All")) {
                status = getWebElement("//div[text()='" + text + "']/..").getAttribute("class").contains("item--selected");
                if (status) {
                    content.click();
                    getWebElement("//multiselect[@name='Content Provider']//button[2]").click();
                    getWebElement("//multiselect[@name='Content Provider']//button[1]").click();
                    getWebElement("//multiselect[@name='Content Provider']/div/span").click();
                }
            }
        }
        return this;
    }

    @Step("Clear contentprovider filter in view All page")
    public AskTheExpertsPage clearContentproviderViewAllButton() {
        getWebElement("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']/div/input").click();
        List<WebElement> contents = getWebElements("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']//div[@data-auto-id='MultiSelect-DropdownList']/div/div");
        List<WebElement> sample = contents;
        for (WebElement content : sample) {
            text = content.getText();
            if (!text.equals("All")) {
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + text + "']/..")));
                status = getWebElement("//div[text()='" + text + "']/..").getAttribute("class").contains("item--selected");
                if (status) {
                    content.click();
                    getWebElement("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']//button[2]").click();
                    getWebElement("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']//button[1]").click();
                    getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']/div/input")));
                    getWebElement("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']/div/input").click();
                }
            }
        }
        return this;
    }


    @Step("select content from content provider")
    public AskTheExpertsPage selectContentProviderHomepage(String name) {
        clearContentproviderHomepageButton();
        status = getWebElement("//multiselect[@name='Content Provider']/div").getAttribute("class").contains("opened");
        if (status != true) {
            getWebElement("//multiselect[@name='Content Provider']/div/span").click();
        }
        List<WebElement> contents = getWebElements("//multiselect[@name='Content Provider']//div[@data-auto-id='MultiSelect-DropdownList']/div/div");

        for (WebElement content : contents) {
            text = content.getText();
            if (text.contains(name)) {
                getWebElement("//div[text()='" + text + "']/..").click();
                icon = true;
            } else if (name.equals("Partner")) {
                status = (!text.equals("All"));
                boolean n = (!text.contains("Cisco"));
                if (status && n) {
                    getWebElement("//div[text()='" + text + "']/..").click();
                    icon = true;
                    break;
                }
            }

        }
        if (icon != true) {
            throw new SkipException("Partner contents are not available. Hence there is no data available to test this scenario");
        }
        getWebElement("//multiselect[@name='Content Provider']//button[1]").click();
        return this;
    }

    @Step("select content from content provider in view All")
    public AskTheExpertsPage selectContentProviderViewAll(String name) {
        clearContentproviderViewAllButton();
        status = getWebElement("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']/div").getAttribute("class").contains("opened");
        if (status != true) {
            getWebElement("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']/div/input").click();
        }
        List<WebElement> contents = getWebElements("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']//div[@data-auto-id='MultiSelect-DropdownList']/div/div");

        for (WebElement content : contents) {
            text = content.getText();
            if (text.contains(name)) {
                getWebElement("//div[text()='" + text + "']/..").click();
            } else if (name.equals("Partner")) {
                status = (!text.equals("All"));
                boolean n = (!text.contains("Cisco"));
                if (status && n) {
                    getWebElement("//div[text()='" + text + "']/..").click();
                    icon = true;
                }
            }

        }
        if (icon != true) {
            throw new SkipException("Partner contents are not available. Hence there is no data available to test this scenario");
        }
        getWebElement("//multiselect[@data-auto-id='ViewAllModal-PartnerMultiFilter']//button[1]").click();
        return this;
    }

    @Step("get ATX launchcourse button")
    public boolean verifATXLaunchCourseButton() {
        status = getWebElement("//div/button[@data-auto-id='LaunchCourseButton']").isDisplayed();
        return status;
    }

    @Step("get ATX 360 expand button")
    public boolean verifyATX360ExpandButton() {
        status = getWebElement("//a/span[contains(@data-auto-id,'toggle-fullscreen')]").isDisplayed();
        return status;
    }

    @Step("get ATX 360 close button")
    public boolean verifyATX360CloseButton() {
        status = getWebElement("//a/span[contains(@class,'icon-close')]").isDisplayed();
        return status;
    }

    @Step("get Atx 360 Heading button")
    public String verifyATXHeading() {
        text = getWebElement("//div[contains(@class,'not-close-360')]//div/span[contains(@class,'uppercase atx')]").getText();
        return text;
    }

    @Step("get Atx 360 sessions")
    public boolean verifyATXSessions() {
        int no = getWebElements("//tbody/tr").size();
        if (no != 0) {
            status = getWebElement("//div[contains(@class,'not-close-360')]//tbody").isDisplayed();
        } else {
            throw new SkipException("atx sessions are not present. Hence there is no data available to test this scenario");
        }
        return status;
    }

    @Step("get Atx 360 description")
    public boolean verifyATXdescrption() {
        status = getWebElement("//div[contains(@class,'not-close-360')]//div[contains(@class,'description')]").isDisplayed();
        return status;
    }

    @Step("verify 360close button functionality in view All page")
    public boolean closeATX360(String name) throws InterruptedException {

        getWebElement("//div/a/span[contains(@class,'icon-close')]").click();
        status = isElementPresent("//div[contains(@class,'not-close-360')]");
        if (name.equals("Table")) {
            getWebElement("//button/span[contains(@class,'icon-close')]").click();
        }
        return status;
    }

    @Step("verify 360maximize button functionality in view All page")
    public String maximizeATX360(String name) {
        fullscreen360View();
        text = getWebElement("//details-panel[@id='atx-details-panel']").getAttribute("class");
        getWebElement("//a/span[contains(@class,'icon-close')]").click();
        if (name.equals("Table")) {
            getWebElement("//button/span[contains(@class,'icon-close')]").click();
        }
        return text;
    }

    @Step("Get first ATX title from home page")
    public String getFirstAtxTitleFromHome() {
        return getWebElement("//div/span[@data-auto-id='PanelTitle-_AskTheExperts_']/../following-sibling::div//div/p[contains(@class,'card__title')][1]").getText();
    }

    @Step("verify atx is scheduled or not in view all")
    public boolean verifyATXscheduledorNot(String name) throws InterruptedException {
        if (name.contentEquals("Home")) {
            icon = isElementPresent("//div/div[@data-auto-id='" + getFirstAtxTitleFromHome() + "-Date']");
            if (icon != true) {
                registerATX();
            }
        } else if (name.contentEquals("Card")) {
            icon = isElementPresent("//span[text()='" + getATXViewAllSubtitles().get(0) + "']/../following-sibling::div//div[contains(@data-auto-id,'ScheduledIcon')]");
            if (icon != true) {
                registerAtxfromViewAll1("Card");
            }
        } else {
            icon = isElementPresent("//div[text()='" + getATXViewAllSubtitles().get(0) + "']/ancestor::td/following-sibling::td//span[contains(@data-auto-id,'Scheduled-Icon')]");
            if (icon != true) {
                registerAtxfromViewAll1("Table");
            }
        }
        return icon;
    }

    @Step("open the 360 in home")
    public AskTheExpertsPage open360InHome(String name) {
        if (name.equals("Home")) {
            getWebElement("//p[contains(text(),'" + getFirstAtxTitleFromHome() + "')]").click();
        } else if (name.equals("Card")) {
            getWebElement("//span[contains(text(),'" + getATXViewAllSubtitles().get(0) + "')]").click();
        } else {
            getWebElement("//div[contains(text(),'" + getATXViewAllSubtitles().get(0) + "')]").click();
        }
        return this;
    }

    @Step("cancel atx from home and view All page")
    public boolean cancelATX(String name) throws InterruptedException {
        icon = verifyATXscheduledorNot(name);
        if (icon) {
            open360InHome(name);
        } else {
//	        			registerATX();
            throw new SkipException("Scheduled atxes are not there.Hence we cant test this scenario");
        }
        List<WebElement> sessions = getWebElements("//tbody/tr");
        for (WebElement session : sessions) {
            text = session.getAttribute("class");
            if (!text.isEmpty()) {
                session.click();
                getWebElement("AtxScheduleCardCancelButton").click();
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[@data-auto-id='" + getFirstAtxTitleFromHome() + "-Date']")));
                status = isElementPresent("//div/div[@data-auto-id='" + getFirstAtxTitleFromHome() + "-Date']");
                break;
            }
        }
        return !status;
    }

    @Step("Get first ATX title from home page")
    public String getTileColorWhenOpen360() {
        String colorValue = getWebElement("//*[@data-auto-id='recommended-atx-tile'][1]").getCssValue("background-color").trim().split("none")[0];
        System.out.println("Colour before opening 360 : " + Color.fromString(colorValue).asHex());
        getWebElement("//*[@data-auto-id='recommended-atx-tile'][1]").click();
        colorValue = getWebElement("//*[@data-auto-id='recommended-atx-tile'][1]").getCssValue("background-color").trim().split("none")[0];
        colorValue = Color.fromString(colorValue).asHex();
        System.out.println("Colour after opening 360: " + colorValue);
        return colorValue;
    }

    @Step("verify the cancellation if session time is less than 24 hr")
    public boolean verifyCancellationIn24HrDifference() {
        status = isElementPresent("//*[@data-auto-id='recommended-atx-tile'][1]//div[contains(@data-auto-id,'-Date')]");
        if (status) {
            String[] parts = getWebElement("//*[@data-auto-id='recommended-atx-tile'][1]//div[contains(@data-auto-id,'-Date')]").getText().trim().split(" ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
            String currenttime = dateFormat.format(new Date()).toString();
            String part2 = parts[1].replaceAll(",([^,]*)$", "$1");
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
            TemporalAccessor accessor = parser.parse(parts[0]);
            String sessiondate = parts[2] + "-" + "0" + accessor.get(ChronoField.MONTH_OF_YEAR) + "-" + part2 + " " + parts[3] + ":00" + " " + parts[4];
            parser = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
            long hour = java.time.Duration.between(LocalDateTime.parse(currenttime, parser), LocalDateTime.parse(sessiondate, parser)).toHours();
            if (hour < 24) {
                getWebElement("//*[@data-auto-id='recommended-atx-tile'][1]").click();
//                status = clickCancelButton();
            } else {
                throw new SkipException("session time difference is more than 24 hr. Hence testing this scenario is not possible");
            }
        } else {
            throw new SkipException("scheduled ATXs are not available. Hence there is no data available to test this scenario");
        }
        return !status;
    }

    //***************************************
    @Step("Get ATX Card with Completed status")
    public String getATXStatusForLastCard() {
        try {
            int totalNoofElements = getTotalNoOfCardsDisplayed();
            text = getWebElement("(//*[@data-auto-id='ATXCardFooter'])[" + totalNoofElements + "]/div/span[2]").getText();
        } catch (NoSuchElementException ignored) {
        }
        return text;
    }

    @Step("Click last ATX card in viewAll Card")
    public AskTheExpertsPage clickLastATXCard() {
        int totalNoofElements = getTotalNoOfCardsDisplayed();
        getWebElement("//*[@data-auto-id='ATXCard'][" + totalNoofElements + "]").click();
        return this;
    }

    @Step("verify green checkmark icon on completed ATX")
    public boolean verifyGreenCheckMarckIcon() {
        status = clickOnFirstCompletedATX();
        if (status) {
            status = isElementPresent("(//*[@data-auto-id='ATXCardFooter-CompletedIcon'])");
        } else {
            throw new SkipException("Completed ATX are not present.Hence can't test this scenario");

        }
        return status;
    }

    @Step("Get total no of ATX in Homepage")
    public int getTotalATXInHome() {
        return getWebElements("//*[@data-auto-id='recommended-atx-tile']/p[contains(@class,'card__title')]").size();
    }

    @Step("register ATX from Homepage and verify it.")
    public boolean VerifyRegisterfromHome() {
        int totalNoofElements = getTotalATXInHome();
        status = isElementPresent("//p[contains(@class,'card__title')][" + totalNoofElements + "]/..//*[local-name() = 'g'][@id='icons/icon-calendar']");
        if (!status) {
            text = getWebElement("//*[@data-auto-id='recommended-atx-tile']/p[contains(@class,'card__title')]").getText();
            getWebElement("(//*[@data-auto-id='recommended-atx-tile']/p[contains(@class,'card__title')])[" + totalNoofElements + "]").click();

            if (isElementPresent("//tbody/tr[@data-index='0']")) {
                getWebElement("//tbody/tr[@data-index='0']").click();
                getWebElement("AtxScheduleCardRegisterButton").click();
//				 jsWaiter.waitAllRequest();
                status = isElementPresent("//div/div[@data-auto-id='" + text + "-Date']");

            } else {
                throw new SkipException("No sessions displayed for this ATX.Hence can't register from home");
            }

        } else {
            throw new SkipException("All ATX in homepage are scheduled.Hence can't register from home");
        }

        return status;
    }

    @Step("Get Total No Of ATX in Card View")
    public int getTotalNoofATXInCardView() {
        return getWebElements("//*[@data-auto-id='ATXCard']").size();
    }

    @Step("Get Total No Of ATX in Home Page")
    public int getTotalNoofATXInHomePage() {
        return getWebElements("//*[@data-auto-id='recommendedATX-Tile']").size();
    }

    @Step("Click On ATX where Session available in Home Page")
    public String clickOnATXWhereSessionAvailable(String locator) {
        int totalNoofATX = this.getTotalNoofATXInHomePage();
        for (int i = 1; i <= totalNoofATX; i++) {
            getWebElement("(//*[@data-auto-id='" + locator + "'])[" + i + "]").click();
            if (!this.isElementPresent("//*[text()='There are no sessions yet for this ATX. Please check back later']")) {
                return getWebElement("//*[contains(@class,'atx-header')]/span").getText();
            }
            this.close360View();
        }
        return "";
    }

    @Step("Get ATX Status")
    public String getATXSessionStatus() {
        if (this.isElementPresent("//*[@id='atx-details-panel']//*[contains(@class,'item-status')]")) {
            if (this.isElementPresent("//*[@id='atx-details-panel']//*[contains(@class,'item-status')]/span[4]"))
                return getWebElement("//*[@id='atx-details-panel']//*[contains(@class,'item-status')]/span[3]").getText() + " " + getWebElement("//*[@id='atx-details-panel']//*[contains(@class,'item-status')]/span[4]").getText();
            return getWebElement("//*[@id='atx-details-panel']//*[contains(@class,'item-status')]/span[3]").getText();
        }
        return "";
    }

    @Step("Schedule Session")
    public String scheduleFirstSession() {
        String status = this.getATXSessionStatus();
        String sessionTiming = "";
        if (status.equalsIgnoreCase("") || status.equalsIgnoreCase("Completed")) {
            getWebElement("(//*[contains(@data-auto-id,'SelectSession')])[2]/td[4]").click();
            sessionTiming = getWebElement("(//*[@class='session-date'])[2]").getText();
            getWebElement("AtxScheduleCardRegisterButton").click();
        }
        if(isATX360Visible())
        	this.close360View();
        this.closeViewAllScreen();
        this.refreshLifecycleTiles();
        this.clickATXViewAll();
        System.out.println("Session Timing: "+sessionTiming);
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='" + sessionTiming + "']")));
        return sessionTiming;
    }

    @Step("Click on ATX Title from Home Page {0}")
    public AskTheExpertsPage clickATXTitleOnHomePage(String atxName) {
        scrollToBottom();
        String locator = "//p[text()=' " + atxName + " ']";
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        getWebElement(locator).click();
        return this;
    }

    @Step("Click on ATX with Scheduled Status")
    public String cancelSheduledSessionInCardView() {
        int totalNoofATX = this.getTotalNoofATXInCardView();
        for (int i = 1; i <= totalNoofATX; i++) {
            getWebElement("(//*[@data-auto-id='ATXCard'])[" + i + "]").click();
            if (this.getATXSessionStatus().contains("Scheduled on"))
                break;
            this.close360View();
        }
        getWebElement("(//*[contains(@data-auto-id,'SelectSession')])[2]/td[4]").click();
        String atxName = getWebElement("//*[contains(@class,'atx-header')]/span").getText();
        getWebElement("AtxScheduleCardCancelButton").click();
        this.closeViewAllScreen();
        this.refreshLifecycleTiles();
        this.clickATXViewAll();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@data-auto-id='ATXCard'])[1]")));
        System.out.println(atxName);
        return atxName;
    }

    @Step("Click On First Completed ATX")
    public boolean clickOnFirstCompletedATX() {
        int noOfCourseWithStatus = this.getTotalNoofATXInCardView();
        for (int i = 1; i <= noOfCourseWithStatus; i++) {
        	getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@data-auto-id='ATXCard'])[" + i + "]")));
            getWebElement("(//*[@data-auto-id='ATXCard'])[" + i + "]").click();
            if (this.getATXSessionStatus().equalsIgnoreCase("Completed")) {
                return true;
            }
            this.close360View();
        }
        return false;
    }

    @Step("Check Selected Feedback Option")
    public String getSelectedFeedbackOption() {
        if (getWebElement("thumbUpBtn").getAttribute("class").contains("btn--selected"))
            return "Thumbs Up";
        else if (getWebElement("thumbDownBtn").getAttribute("class").contains("btn--selected"))
            return "Thumbs Down";
        else return "No Feedback Selected";
    }

    @Step("Click on Feedback Submit option")
    public AskTheExpertsPage clickOnFeedbackSubmitOption(String feedbackComment) {
        getWebElement("FeedbackPopup-Comments-Input").sendKeys(feedbackComment);
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='FeedbackPopup-Submit']")));
        getWebElement("FeedbackPopup-Submit").click();
        return this;
    }

    @Step("Click Thumbs Up Button")
    public AskTheExpertsPage provideThumbsUpFeedback() {
        getWebElement("thumbUpBtn").click();
        this.clickOnFeedbackSubmitOption("Test Feedback Thumbs Up Option");
        return this;
    }

    @Step("Click Thumbs Down Button")
    public AskTheExpertsPage provideThumbsDownFeedback() {
        getWebElement("thumbDownBtn").click();
        this.clickOnFeedbackSubmitOption("Test Feedback Thumbs Down Option");
        return this;
    }

    @Step("Get total No of ATX Card with Positive Feedback")
    public int getTotalNoOfATXWithPositiveFeedback(String className) {
        return this.getWebElements("(//*[@data-auto-id='ATXCard']//descendant::*[contains(@class,'" + className + "')]/span[2])").size();
    }

    @Step("Get total No of ATX Card with Completion of Sessions no")
    public int getTotalNoOfATXWithCompletionTickMark(String className) {
        return this.getWebElements("(//*[@data-auto-id='ATXCard']//descendant::*[contains(@class,'" + className + "')]/span[2])").size();
    }

    @Step("Check Data availability")
    public boolean checkDataAvailability(String locator) {
        if (getWebElements(locator).size() > 0)
            return true;
        else
            return false;
    }

    @Step("Check availability of ATX Card with Status")
    public boolean checkStatusInCardViewDisplayed(String atxTitle) {
        return !getWebElement("//span[text()='" + atxTitle + "']/ancestor::div[@data-auto-id='ATXCard']//div[@data-auto-id='ATXCardFooter']").getText().isEmpty();
    }

    @Step("Check availablity of ATX Title in Card View")
    public boolean checkATXTitleInCardViewDisplayed() {
        return this.isElementPresent("//*[@data-auto-id='ATXCardTitle' and @class='card-description']/span");
    }

    @Step("Check availability of Partner Name in Card View")
    public int checkATXPartnerNameInCardViewDisplayed() {
        return getWebElement("//*[@data-auto-id='ATXCardTitle' and contains(@class,'card__header')]").getText().length();
    }

    @Step("Check availability of Tickmark and Positive Thumbs Up Symbol In Card View")
    public boolean checkCountAndPositiveSymbolDisplayedInCardView(String className) {
        return this.isElementPresent("(//*[@data-auto-id='ATXCard']//descendant::*[contains(@class,'" + className + "')]/span[1])");
    }

    @Step("Check availability of Positive Feedback and No of Completion In Card View")
    public boolean checkNoOfCompletionAndFeedbackCountDisplayedInCardView(String className) {
        return this.isElementPresent("(//*[@data-auto-id='ATXCard']//descendant::*[contains(@class,'" + className + "')]/span[2])");
    }

    @Step("Check ATX with Completed Status displayed in Table View")
    public boolean checkStatusInTableViewDisplayed(String atxTitle) {
        return !getWebElement("//*[text()='" + atxTitle + "']/ancestor::tr/td[@data-auto-id='Status']").getText().isEmpty();
    }

    @Step("Check availability of ATX Title in Table View")
    public boolean checkATXTitleInTableViewDisplayed() {
        return this.isElementPresent("//*[@data-auto-id='ATX-Title']");
    }

    @Step("Check availability of Partner Name in Table View")
    //need to check this code
    public boolean checkATXPartnerNameInTableViewDisplayed() {
        List<WebElement> providerNames = getWebElements("(//*[@data-auto-id='Delivery Type']/span)");
        for (WebElement provider : providerNames) {
            if (provider.getText().equalsIgnoreCase("Cisco")) {
                return true;
            }
        }
        return false;
    }

    @Step("Check availability of Tickmark and Positive Thumbs Up Symbol In Table View")
    public boolean checkCountAndPositiveSymbolDisplayedInTableView(String className) {
        return this.isElementPresent("(//*[@data-auto-id='ViewAllTable']//descendant::*[contains(@class,'" + className + "')]/span[1])");
    }

    @Step("Check availability of Positive Feedback and No of Completion In Table View")
    public boolean checkNoOfCompletionAndFeedbackCountDisplayedInTableView(String className) {
        return this.isElementPresent("(//*[@data-auto-id='ViewAllTable']//descendant::*[contains(@class,'" + className + "')]/span[2])");
    }


    @Step("Apply Filter from Home Page")
    public int getTotalFilteredATXInHomePage(String filterName, String filterValue) {
        WebElement ele = getWebElement("//*[@name='" + filterName + "']/div/input[@data-auto-id='MultiSelect-SearchInput' and contains(@placeholder,'" + filterName + "')]");
        getJavascriptExecutor().executeScript("arguments[0].click();", ele);
        this.clickFilterMultiSelectClearButton();
        if (isElementPresent("//div[contains(@data-auto-id,'MultiSelect-ListItem') and text()='" + filterValue + "']")) {
            getWebElement("//div[contains(@data-auto-id,'MultiSelect-ListItem') and text()='" + filterValue + "']").click();
        } else {
            throw new SkipException("This partner is not available now.Hence can't test this scenario");
        }
        getWebElement("MultiSelect-SaveButton").click();
        return this.getTotalATXInHome();
    }

    @Step("Get ATX Title from 360 View")
    public String getAtxTitleFrom360View() {
        return getWebElement("//*[contains(@class,'atx-header')]/span").getText();
    }

    @Step("Check Bookamrk availability on 360 View")
    public boolean checkBookmarkIn360ViewDisplayed() {
        return this.isElementPresent("//*[@class='button-ribbon']");
    }

    @Step("Click on ATX First Title from View All Card View")
    public AskTheExpertsPage clickOnAtxFirstTitleFromCardView() {
        getWebElement("(//*[@data-auto-id='ATXCardTitle' and @class='card-description'])[1]").click();
        return this;
    }

    @Step("Get ATX First Title from View All Card View")
    public String getAtxFirstTitleFromCardView() {
        return getWebElement("(//*[@data-auto-id='ATXCardTitle' and @class='card-description'])[1]").getText();
    }

    @Step("Get ATX First Title from View All Table View")
    public String getAtxFirstTitleFromTableView() {
        return this.getWebElement("(//*[@data-auto-id='ATX-Title'])[1]").getText();
    }

    @Step("Click on ATX First Title from View All Table View")
    public AskTheExpertsPage clickOnAtxFirstTitleFromTableView() {
        getWebElement("(//*[@data-auto-id='ATX-Title'])[1]").click();
        return this;
    }

    @Step("Check ATX launchcourse availability in 360 view")
    public boolean checkLaunchCourseIn360ViewDisplayed() {
        return this.isElementPresent("//div/button[@data-auto-id='LaunchCourseButton']");
    }

    @Step("Check ATX description availability in 360 view")
    public boolean checkAtxDescriptionIn360ViewDisplayed() {
        return this.isElementPresent("//div[contains(@class,'not-close-360')]//div[contains(@class,'description')]");
    }

    @Step("Check ATX header availability in 360 view")
    public boolean checkAtxHeaderIn360ViewDisplayed() {
        return this.isElementPresent("//div[contains(@class,'not-close-360')]//div/span[contains(@class,'uppercase atx')]");
    }

    @Step("Check ATX partnerName availability in 360 view")
    public boolean checkPartnerIn360ViewDisplayed() {
        return this.isElementPresent("//*[text()='Partner']");
    }

    @Step("Get first ATX title from home page")
    public AskTheExpertsPage clickFirstAtxTitleFromHome() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(getWebElement("//div/span[@data-auto-id='PanelTitle-_AskTheExperts_']/../following-sibling::div//div/p[contains(@class,'card__title')][1]")));
        getWebElement("//div/span[@data-auto-id='PanelTitle-_AskTheExperts_']/../following-sibling::div//div/p[contains(@class,'card__title')][1]").click();
        return this;
    }

    @Step("Find the ATX with session in Homepage")
    public String findATXwithSessions() {
        int totalNoOfATXInHome = getTotalATXInHome();
        ATXTexts = getATXSubtitles();
        for (String subtitle : ATXTexts) {
            counter++;
            icon = isElementPresent("//div/div[@data-auto-id='" + subtitle + "-Date']");
            {
                if (icon != true) {
                    getWebElement("//p[contains(text(),'" + subtitle + "')]").click();
                    if (isElementPresent("//tbody/tr")) {
                        text = subtitle;
                        break;
                    } else if (counter == totalNoOfATXInHome) {
                        throw new SkipException("There are no atx sessions present or no unscheduled ATX in lifecycle page");
                    }
                } else {
                    if (counter == totalNoOfATXInHome) {
                        throw new SkipException("All atx sessions are scheduled in lifecycle's page");
                    }
                }
            }
        }
        return text;
    }

    @Step("Registering atx from the lifecycle page")
    public boolean registerATX() {
        text = findATXwithSessions();
        clickATXSession();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[@data-auto-id='" + text + "-Date']")));
        status = isElementPresent("//div/div[@data-auto-id='" + text + "-Date']");
        getWebElement("//p[contains(text(),'" + text + "')]").click();
        clickCancelButton();
        return status;
    }

    @Step("Click on session in ATX")
    public AskTheExpertsPage clickATXSession() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr[@data-index='0']/td[2]")));
        getWebElement("//tbody/tr[@data-index='0']/td[2]").click();
//		 getWebElement("AtxScheduleCardRegisterButton").click();
        return this;
    }

    @Step("find ATX session from view All card")
    public String findATXwithSessionsInViewAllCard() throws InterruptedException {
        ATXTexts = getATXViewAllSubtitles();
        for (String subtitle : ATXTexts) {
            counter++;
            int totalNoOfATXinViewAllcard = getTotalNoofATXInCardView();
            icon = isElementPresent("//span[text()='" + subtitle + "']/../../div[contains(@class,'card__footer')]//*[@data-auto-id='ATXCardFooter-ScheduledIcon']");
            if (icon != true) {
                getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-auto-id='ATXCardTitle']//span[contains(text(),'" + subtitle + "')]")));
                getDriver().findElement(By.xpath("//*[@data-auto-id='ATXCardTitle']//span[contains(text(),'" + subtitle + "')]")).click();
                if (isElementPresent("//tbody/tr")) {
                    text = subtitle;
                    break;
                } else {
                    close360View();
                }
            } else if (counter == totalNoOfATXinViewAllcard) {
                throw new SkipException("There are no atx sessions present or no unscheduled ATX in lifecycle page");
            }
        }

        return text;
    }

    @Step("find ATX session from view All table")
    public String findATXwithSessionsInViewAllTable() throws InterruptedException {
        int totalNoOfATXinViewAllTable = getTotalNoofATXInTableView();
        ATXTexts = getATXViewAllSubtitles();
        System.out.println(getATXViewAllSubtitles().size());
        for (String subtitle : ATXTexts) {
            counter++;
            icon = isElementPresent("//div[text()='" + subtitle + "']/ancestor::td/following-sibling::td//span[contains(@data-auto-id,'Scheduled-Icon')]");
            if (icon != true) {
                getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + subtitle + "']")));
                getDriver().findElement(By.xpath("//div[text()='" + subtitle + "']")).click();
                if (isElementPresent("//tbody/tr[@data-index='0']")) {
                    text = subtitle;
                    break;
                } else {
                    close360View();
                }
            } else if (counter == totalNoOfATXinViewAllTable) {
                throw new SkipException("There are no atx sessions present or no unscheduled ATX in lifecycle page");
            }
        }
        return text;
    }

    @Step("Get Total No Of ATX in Table View")
    public int getTotalNoofATXInTableView() {
        return getWebElements("//*[@data-auto-id='ATX-Title']").size();
    }

    @Step("Registering atx from the view All page")
    public boolean registerAtxfromViewAll1(String name) throws InterruptedException {
        if (name.equals("Card")) {
            text = findATXwithSessionsInViewAllCard();
            clickATXSession();
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + text + "']/../../div[contains(@class,'card__footer')]//*[@data-auto-id='ATXCardFooter-ScheduledIcon']")));
            status = isElementPresent("//span[text()='" + text + "']/../../div[contains(@class,'card__footer')]//*[@data-auto-id='ATXCardFooter-ScheduledIcon']");
            getDriver().findElement(By.xpath("//*[@data-auto-id='ATXCardTitle']//span[contains(text(),'" + text + "')]")).click();
            clickCancelButton();
        } else {
            text = findATXwithSessionsInViewAllTable();
            clickATXSession();
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + text + "']/ancestor::td/../td[5]//*[@data-auto-id='Table-Status-Scheduled-Icon']")));
            status = isElementPresent("//div[text()='" + text + "']/ancestor::td/../td[5]//*[@data-auto-id='Table-Status-Scheduled-Icon']");
            getDriver().findElement(By.xpath("//div[text()='" + text + "']")).click();
            clickCancelButton();
        }

        return status;
    }

    @Step("Click on the Register Button")
    public AskTheExpertsPage clickRegisterButton() {
        String locator = "//button[@data-auto-id='AtxScheduleCardRegisterButton']";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        getWebElement(locator).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='recommended-atx-tile']")));
        return this;
    }

    @Step("Check Calendar icon is present after atx registration")
    public boolean isCalendarIconPresent(String atxTitle) {
        return isElementPresent("//p[text()=' " + atxTitle + " ']/following-sibling::div//*[@data-auto-id='recommendedATX-Calendar']");
    }

    @Step("CLick on the ATX Title in Card View")
    public AskTheExpertsPage clickATXTitleOnCardView(String atxTitle) {
        String locator = "//span[text()='" + atxTitle + "']";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        getWebElement(locator).click();
        return this;
    }

    @Step("CLick on the ATX Title in Table View")
    public AskTheExpertsPage clickATXTitleOnTableView(String atxTitle) {
        String locator = "//div[text()='" + atxTitle + "']";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        getWebElement(locator).click();
        return this;
    }

    public boolean isGreenCheckMarkPresent(String atxTitle) {
        String locator = "//*[text()=' " + atxTitle + " ']/ancestor::div[@data-auto-id='recommended-atx-tile']//span[contains(@data-auto-id,'Checkmark')]";
        return isElementPresent(locator);
    }

    public String getCheckMarkIconColor(String atxTitle) {
        String locator = "//*[text()=' " + atxTitle + " ']/ancestor::div[@data-auto-id='recommended-atx-tile']//span[contains(@data-auto-id,'Checkmark')]";
        return Color.fromString(getWebElement(locator).getCssValue("color")).asHex();
    }

    public boolean waitForCalendarIconToDisappear(String atxTitle) {
        return getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@data-auto-id='" + atxTitle + "-Calendar']")));
    }

    public AskTheExpertsPage waitForATXTilesToLoad() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='ATXCard'] | //*[@data-auto-id='ViewAllTableBody'] | //*[@data-auto-id='recommended-atx-tile']")));
        return this;
    }

    @Step("Mouse Hover ATX Tile in home page {0}")
    public void mouseHoverATXTile(String title) {
        scrollToBottom();
        getActions().moveToElement(getWebElement("//*[text()=' " + title + " ']")).build().perform();
    }

    @Step("Click Watch on Demand button in ATX 360 view")
    public AskTheExpertsPage clickWatchOnDemand() {
        getWebElement("//div/button[@data-auto-id='LaunchCourseButton']").click();
        return this;
    }

    @Step("Get ATX Title from Home Page")
    public String getATXTitleFromHomePage(String atxTitle) {
        return getWebElement(getHomeATXTileXpath(atxTitle) + "//*[@data-auto-id='recommendedATX-Title']").getText().trim();
    }

    @Step("Get Feedback Percentage from ATX in home page")
    public String getATXFeedbackPercentage(String atxTitle) {
        return getWebElement(getHomeATXTileXpath(atxTitle) + "//*[contains(@class,'feedback-positive')]").getText().trim();
    }

    @Step("Get Feedback Count From ATX in Home Page")
    public String getATXFeedbackCount(String atxTitle) {
        return getWebElement(getHomeATXTileXpath(atxTitle) + "//*[contains(@class,'feedback-count')]/span[2]").getText().trim();
    }

    public String getHomeATXTileXpath(String atxTitle) {
        return "//*[text()=' " + atxTitle + " ']/ancestor::div[@data-auto-id='recommendedATX']";
    }

    public String getTableATXTileXpath(String atxTitle) {
        return "//div[text() ='" + atxTitle + "']/ancestor::tr";
    }

    @Step("Verify ATX 360 View is visible")
    public boolean isATX360Visible() {
        return isElementPresent("atx-details-panel");
    }

    @Step("Get the Class of ATX Detail Panel")
    public String getATXDetailPanelClass() {
        return getWebElement("atx-details-panel").getAttribute("class");
    }

    @Step("Check Session Table is Displayed")
    public boolean checkSessionTableDisplayed() {
        return isElementPresent("//*[contains(@class,'session-table')]");
    }

    @Step("Check Partner Data availability")
    public void checkPartnerDataAvailability(ATXPoJo.ItemsItem partnerData) {
        if (null == partnerData)
            throw new SkipException("No Partner Data available to test this scenario");
    }

    @Step("Wait for the ATX Status to Disappear")
    public boolean waitForStatusToDisappear(String atxTitle) {
        return getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(getHomeATXTileXpath(atxTitle) + "//div[@data-auto-id='recommendedATX-Date']")));
    }

    @Step("Get Bookmark color from table view")
    public String getBookMarkColorFromTableView(String atxTitle) {
        String locator = getTableATXTileXpath(atxTitle) + "//button[@data-auto-id='SBListRibbon']/div";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        String bookmarkColor = getWebElement(locator).getCssValue("color");
        return Color.fromString(bookmarkColor).asHex();
    }

    @Step("Click Bookmark button on Table View")
    public void clickBookmarkOnTableView(String atxTitle) {
        String locator = getTableATXTileXpath(atxTitle) + "//button[@data-auto-id='SBListRibbon']/div";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        getWebElement(locator).click();
    }

    @Step("Click Bookmark button in 360 view")
    public void click360BookmarkButton() {
        getWebElement("//*[@attr.data-auto-id='flyOut-BookmarkRibbon']/span").click();
    }

    @Step("Get Bookmark color from 360 view")
    public String getBookMarkColorFrom360View() {
        String locator = "//*[@attr.data-auto-id='flyOut-BookmarkRibbon']/span";
        getWebDriverWait().until(ExpectedConditions.attributeContains(By.xpath(locator), "class", "ribbon__blue"));
        String bookMarkColor = getWebElement(locator).getCssValue("color");
        return Color.fromString(bookMarkColor).asHex();
    }

    public List<String> getATXCardTitlesInCardView() {
        return getDriver().findElements(By.xpath("//div[@class='card-description']")).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void attachDummyElementTo(WebElement element) {
        getJavascriptExecutor().executeScript("var node = document.createElement('div');" +
                "var textnode = document.createTextNode('DummyElement');" +
                "node.appendChild(textnode);" +
                "arguments[0].appendChild(node);", element);
    }

    @Step("Wait till the number of atx is {0}")
    public void waitTillNumberOfCardsIs(int atxCount) {
        getWebDriverWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='card-description']"),atxCount));
    }
}
