package com.cisco.base;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.function.Function;

public class DriverUtils {

    protected static final Logger logger = Logger.getLogger(DriverUtils.class);
    private static ThreadLocal<WebDriverWait> wait = ThreadLocal.withInitial(() -> new WebDriverWait(DriverBase.getDriver(), 60));
    private static ThreadLocal<Actions> actions = ThreadLocal.withInitial(() -> new Actions(DriverBase.getDriver()));
    private static ThreadLocal<JavascriptExecutor> javascriptExecutor = ThreadLocal.withInitial(() -> (JavascriptExecutor) DriverBase.getDriver());


//    public DriverUtils() {
//        initializeDriverObjects();
//    }

    public void initializeDriverObjects() {
        wait.set(new WebDriverWait(DriverBase.getDriver(), 60));
        actions.set(new Actions(DriverBase.getDriver()));
        javascriptExecutor.set((JavascriptExecutor) DriverBase.getDriver());
//            getDriver().manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
//            getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

    }

    public WebDriver getDriver() {
        return DriverBase.getDriver();
    }

    public WebDriverWait getWebDriverWait() {
        return wait.get();
    }

    public Actions getActions() {
        return actions.get();
    }

    public JavascriptExecutor getJavascriptExecutor() {
        return javascriptExecutor.get();
    }

    public WebElement getWebElement(String locator) {
//        ngWebDriver.waitForAngularRequestsToFinish();
//        jsWaiter.waitAllRequest();
        //getWebDriverWait().until(loadingWheelDisappears);
        //getWebDriverWait().until(loadingWheelDisappears);
        Map<By, String> locatorsMap = new LinkedHashMap<>();

        locatorsMap.put(By.xpath(locator), "xpath");
        locatorsMap.put(By.cssSelector(locator), "cssSelector");
        locatorsMap.put(By.id(locator), "id");
        locatorsMap.put(By.className(locator), "className");
        locatorsMap.put(By.linkText(locator), "linkText");
        locatorsMap.put(By.xpath("//*[@data-auto-id='" + locator + "']"), "dataAutoID");


        for (Map.Entry<By, String> entry : locatorsMap.entrySet()) {
            try {
                getDriver().findElement(entry.getKey());
                System.out.println("Fetching element with the locator : " + locator + " by --> " + entry.getValue());
                return getDriver().findElement(entry.getKey());
            } catch (NoSuchElementException ignored) {
            }
        }
        throw new NoSuchElementException("Element with locator not found : " + locator);
    }

    public List<WebElement> getWebElements(String locator) {
//        ngWebDriver.waitForAngularRequestsToFinish();
//        jsWaiter.waitAllRequest();
        getWebDriverWait().until(loadingWheelDisappears);


        List<By> locators = new ArrayList<>();

        System.out.println("Fetching elements with the locator : " + locator);

        locators.add(By.xpath(locator));
        locators.add(By.cssSelector(locator));
        locators.add(By.id(locator));
        locators.add(By.className(locator));
        locators.add(By.linkText(locator));
        locators.add(By.xpath("//*[@data-auto-id='" + locator + "']"));


        for (By by : locators) {
            try {
                return getDriver().findElements(by);
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        throw new NoSuchElementException("Elements with locator not found : " + locator);
    }

    /**
     * <p>Title of page is returned</p>
     *
     * @return String
     * @author sidraman
     */
    @Step("Get the HomePage Title")
    public String getTitle() {
        return getDriver().getTitle();
    }

    /**
     * <p>Switch control on to child window or tab</p>
     *
     * @return
     * @author sidraman
     */
    @Step("Switch from main window to tab")
    public DriverUtils switchTab() {
        String currentWindowHandle = getDriver().getWindowHandle();
        for (String tab : getDriver().getWindowHandles()) {
            if (!tab.equals(currentWindowHandle)) {
                getDriver().switchTo().window(tab);
                break;
            }
        }
        return this;
    }

    /**
     * <p>Switch control on to main window or tab</p>
     *
     * @return
     * @author sidraman
     */
    @Step("Switch from tab to main window and close other tabs")
    public DriverUtils switchToMainTab() {
        String mainWindow = null;
        for (String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
            if (getDriver().getTitle().contains("CX Cloud")) {
                mainWindow = getDriver().getWindowHandle();
            } else {
                getDriver().close();
            }
        }
        getDriver().switchTo().window(mainWindow);
        return this;
    }

    /**
     * <p>To get total number of Tabs opened</p>
     *
     * @return
     * @author devikris
     */
    @Step("Get total number of Tabs opened")
    public int getTotalNumberOfTabsOpened() {
        Set<String> tabs = getDriver().getWindowHandles();
        return tabs.size();
    }

    /**
     * <p>To Hover over a WebElement</p>
     *
     * @return
     * @author devikris
     */
    @Step("Hover over WebElement")
    public DriverUtils hoverOveraWebElement(String locator) {
        getActions().moveToElement(getWebElement(locator)).build().perform();
        return this;
    }

    /**
     * <p>To get current URL</p>
     *
     * @return
     * @author devikris
     */
    @Step("Get Current URL")
    public String getCurrentURLFromSecondTab() {
        switchTab();
        String URL = getDriver().getCurrentUrl();
        switchToMainTab();
        return URL;
    }

    public Function<WebDriver, Boolean> loadingWheelDisappears = driver -> {

        if (getDriver().findElements(By.xpath("//app-root")).size() == 0)
            return true;

        List<WebElement> loadingWheel = getDriver().findElements(By.cssSelector("[class*='loading-spinner'] [class*='wheel']"));
        if (loadingWheel.size() == 1) {
            return (!loadingWheel.get(0).isDisplayed());
        }
        return false;
    };

}
