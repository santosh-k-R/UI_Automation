package com.cisco.utils;

import com.cisco.base.DriverBase;
import com.cisco.base.DriverUtils;
import com.cisco.testdata.StaticData.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Semaphore;

public class AppUtils extends DriverUtils {

    private static final Logger logger = Logger.getLogger(AppUtils.class);
    private static final Semaphore semaphore = new Semaphore(4);
    private final String USERNAME = System.getProperty("username");
    private final String PASSWORD = System.getProperty("password");
    private final String URL = System.getProperty("url");
    private final Boolean skipLoginScreen = Boolean.parseBoolean(System.getProperty("skipLoginScreen"));
    String env = System.getProperty("env");

    /**
     * <p>Login to the application by passing Username and password through environment variable</p>
     * <p>There are two ways to login</p>
     * <p>1. Login Screen. Change the "skipLoginScreen" variable value to "false" in pom.xml</p>
     * <p>2. Through API (ObSSOCookie). By default "skipLoginScreen" is "true" in pom.xml</p>
     *
     * @param track Appends to the base URl
     */
    @Step("Login to application")
    public AppUtils login(String track) {
        if (isLoggedIn(USERNAME) && (!isPortalDown())) {
            /*getDriver().navigate().to(URL + track);
            waitForInitialPageToLoad();
            closePrivacyConsentDialog();*/
            return this;
        }
        initializeDriverObjects();
        if (!isPageBlank()) {
            DriverBase.closeDriverInstance();
        }
        if (skipLoginScreen) {
            loginUsingOBSSOCookie(track, USERNAME, PASSWORD);
        } else {
            loginUsingSSOLoginPage(track, USERNAME, PASSWORD);
        }
        waitForInitialPageToLoad();
        /*if (!getDriver().getTitle().contains("CX Cloud")) {
            Assert.fail("Portal did not load");
//            System.exit(0);
        }*/
        System.out.println("Successfully logged in to the Application");
        closeWelcomePopUp();
        closeInfoPopups();
        closePrivacyConsentDialog();
        return this;
    }

    /**
     * This methods is used to login and take the user to the Home page(Lifecycle)
     *
     * @return
     * @author mohnasir
     */
    public AppUtils login() {
        login("");
        return this;
    }

    @Step("Login to application with the user having role {0}")
    public AppUtils login(String userRole, String track) {
        String userName = System.getenv(userRole + "_username");
        String password = System.getenv(userRole + "_password");
        if (isLoggedIn(userName) && (!isPortalDown())) {
            //getDriver().navigate().to(URL + track);
            //waitForInitialPageDrToLoad();
            //closePrivacyConsentDialog();
            return this;
        }
        if (!isPageBlank()) {
            DriverBase.closeDriverInstance();
        }
        if (skipLoginScreen) {
            loginUsingOBSSOCookie(track, userName, password);
        } else {
            loginUsingSSOLoginPage(track, userName, password);
        }
        waitForInitialPageToLoad();
        /*System.out.println("Title:-"+getDriver().getTitle());
        if (!getDriver().getTitle().contains("M & W Data Entry")) {
            Assert.fail("Portal did not load");
//            System.exit(0);
        }*/
        System.out.println("Successfully logged in to the Application");
        closeWelcomePopUp();
        closeInfoPopups();
        closePrivacyConsentDialog();
        return this;
    }

    private void loginUsingSSOLoginPage(String track,String userName, String password) {
       // private void loginUsingSSOLoginPage(String track, String userName, String password)
        logger.info("Logging in via sso login page");
        getDriver().get(URL);
        //getDriver().get(URL + track);
        String userNameText = "//input[@id='userNameInput']";
        String loginButton = "//span[@id='submitButton']";
        String passwordText = "//input[@id='passwordInput']";
        try {
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userNameText)));
        } catch (WebDriverException e) {
            initializeDriverObjects();
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userNameText)));
        }
        getDriver().findElement(By.xpath(userNameText)).sendKeys(userName);
        getDriver().findElement(By.xpath(loginButton)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordText)));
        getDriver().findElement(By.xpath(passwordText)).sendKeys(password);
        try {
            semaphore.acquire();
            getDriver().findElement(By.xpath(loginButton)).click();
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    private void loginUsingOBSSOCookie(String track, String userName, String password) {
        logger.info("Logging in via OBSSO cookie");
        try {
            addOBSSOCookieToBrowser(userName, password, false);
        } catch (Exception e) {
            addOBSSOCookieToBrowser(userName, password, true);
        }
        getDriver().get(URL + track);
    }

    /**
     * After login or page refresh, it waits for the page to load completely
     *
     * @return
     * @author mohnasir
     */
    public AppUtils waitForInitialPageToLoad() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Main Menu']")));
        return this;
    }

    /**
     * Checks whether user is already logged in
     *
     * @return author mohnasir
     */
    private boolean isLoggedIn(String user) {
        try {
            if (user.equals(getLoggedInUser()) && getDriver().manage().getCookies().stream().anyMatch(x -> x.getName().contains("authorization")))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
//        return getDriver().manage().getCookies().stream().anyMatch(x -> x.getName().contains("authorization"));
    }

    public String getLoggedInUser() {
        try {
            return (String) getJavascriptExecutor().executeScript("return jsonData.userID");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isPageBlank() {
        return getDriver().getTitle().equals("");
    }

    public boolean isHomePageVisible() {
        return getDriver().findElement(By.xpath("//a[normalize-space()='Main Menu']")).isDisplayed();
    }

    public boolean isPortalDown() {
        return getDriver().getCurrentUrl().contains("portal/error/portal-down");
    }

    /**
     * This method is used internally by login method.
     *
     * @param userid
     * @param password
     * @param prodUser
     */
    @Step("Login using OBSSO cookie")
    public void addOBSSOCookieToBrowser(String userid, String password, boolean prodUser) {
        String url = prodUser ? "https://sso.cisco.com/autho/login/loginaction.html" : "https://sso-test.cisco.com/autho/login/loginaction.html";
        String ssoUrl = env.equals("prod") ? "https://cloudsso.cisco.com" : "https://cloudsso-test.cisco.com";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String value = "";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("password", password));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            CloseableHttpResponse response = null;
            response = client.execute(httpPost);
            System.out.println("Response Entity : " + response.getEntity());
            System.out.println("Response Status Line : " + response.getStatusLine());
            Header[] headers = response.getAllHeaders();
            for (Header h : headers) {
                value = h.getValue();
                if (value.contains("ObSSOCookie=")) {
                    value = value.split("=|;")[1];
                    break;
                }
                value = null;
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getDriver().get(ssoUrl);
        getDriver().manage().addCookie(new Cookie("ObSSOCookie", value));
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
        getWebDriverWait().until(ExpectedConditions.urlContains("log-out-success"));
        return this;
    }

    @Step("Click on the User profile button")
    public AppUtils clickUserButton() {
        getWebElement("//button[@data-auto-id='HeaderUserProfileButton']").click();
        return this;
    }

    /**
     * @return
     */
    @Step("Get the Smart account that is selected for user")
    public String getSmartAccount() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='smart-account-selections__label']")));
        return getWebElement("//span[@class='smart-account-selections__label']").getText().substring(10).trim();
    }

    /**
     * @param smartAccount
     * @return
     */
    @Step("Switch Smart account to {0}")
    public AppUtils switchCXCloudAccount(String smartAccount) {
        String smartAccountXpath = "button[class*='open-button']";
        String smartAccountVal = "//li[@class='portal-account-selection-modal__list-item'][text()=' " + smartAccount + " ']";
        clickUserButton();
        if (!getSmartAccount().contains(smartAccount)) {
            getWebElement("//button[@class='smart-account-selections__button']").click();
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(smartAccountXpath)));
            getWebElement(smartAccountXpath).click();
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(smartAccountVal)));
            getWebElement(smartAccountVal).click();
            getWebElement("//button[text()=' Continue ']").click();
            getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[contains(text(),'CX Cloud Account')]")));
        } else {
            clickUserButton();
            System.out.println("CX Cloud account : " + smartAccount + " already selected");
        }
        waitForInitialPageToLoad();
        return this;
    }

    @Step("Switch Smart account to {0}")
    public AppUtils switchCXCloudAccount(String smartAccount, String partyID) {
        String smartAccountXpath = "button[class*='open-button']";
        String smartAccountID = "//li[@data-portal-account='" + partyID + "']";
        String smartAccountVal = "//li[@class='portal-account-selection-modal__list-item'][text()=' " + smartAccount + " ']";
        clickUserButton();
        getWebElement("//button[@class='smart-account-selections__button']").click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(smartAccountXpath)));
        getWebElement(smartAccountXpath).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(smartAccountVal)));
        if (getWebElements(smartAccountVal).size() > 1) {
            getWebElement(smartAccountID).click();
        } else {
            getWebElement(smartAccountVal).click();
        }
        getWebElement("//button[text()=' Continue ']").click();
        getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[contains(text(),'CX Cloud Account')]")));
        waitForInitialPageToLoad();
        return this;
    }

    /**
     * <p>Selects value under label</p>
     * <p>Example: selectMainDropDown(CxHomePage.SMART_ACCOUNT, CxHomePage.STATE_OF_MARYLAND)</p>
     *
     * @param label, value
     * @author sidraman
     */
    @Step("Select Main dropdown from dropdown: {0} and selecting value {1}")
    public AppUtils selectContextSelector(String label, String value) {
        String dropDownXpath = "//button[@data-auto-id='" + label + "']";
        WebElement dropdown;
        String addFilter = "add-filter"; //for plus icon
        if (isElementPresent(addFilter)) {
            getWebElement(addFilter).click();
            getWebElement("//div[@class='filter-item']/label[contains(text(),'" + value + "')]").click();
            waitForInitialPageToLoad();
            return this;
        }
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dropDownXpath)));
        dropdown = getWebElement(dropDownXpath);
        if (!dropdown.getText().equalsIgnoreCase(value)) {
            dropdown.click();
            getWebElement("//div[@class='filter-item']/label[contains(text(),'" + value + "')]").click();
            waitForInitialPageToLoad();
        } else {
            System.out.println("Dropdown value : " + value + " already selected");
        }
        return this;
    }

    public AppUtils resetContextSelector() {
        String refreshXpath = "//span[@class='refresh']//*[name()='svg']";
        WebElement element;
        try {
            element = getWebElement(refreshXpath);
            getActions().moveToElement(element).click(element).build().perform();
            waitForInitialPageToLoad();
        } catch (NoSuchElementException e) {
            System.out.println("Context selector is already reset");
        }
        return this;
    }

    /**
     * <p>Pass carouselName to select it</p>
     *
     * @param carouselName
     * @author sidraman
     */
    @Step("Select Carousel {0}")
    public AppUtils selectCarousel(CarouselName carouselName) {
        String carouselPath = "//span[contains(text(),'" + carouselName.getCarouselName() + "')]/..";

        try {
            getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(carouselPath)));
            getWebElement(carouselPath).click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
            switch (carouselName) {
                case ADVISORIES:
                case LIFECYCLE:
                case ASSETS_COVERAGE:
                    moveCarousel("left");
                    break;
                case CASES:
                case INSIGHTS:
                    moveCarousel("right");
                    break;
            }
            getWebDriverWait().until(loadingWheelDisappears);
            getWebElement(carouselPath).click();
        }
        return this;
    }


    /**
     * <p>Move carousel by the specified parameter of either left or right</p>
     *
     * @param left_right
     * @return
     * @author sidraman
     */
    @Step("Move carousel to {0}")
    public AppUtils moveCarousel(String left_right) {
        Allure.step("Move carousel to " + left_right);
        String moveCarouselPath=null;
        if (left_right.equalsIgnoreCase("left") || left_right.equalsIgnoreCase("right")) {
            moveCarouselPath = "//button[@data-auto-id='carousel-" + left_right + "']/*[name()='svg']/*[name()='polygon']";
        } else {
            throw new NoSuchElementException("Not the right Carousel");
        }
        try{
            getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(moveCarouselPath)));
            getWebElement(moveCarouselPath).click();
        }catch (ElementClickInterceptedException e){
            try{
                getActions().moveToElement(getWebElement(moveCarouselPath)).click().perform();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return this;
    }

    /**
     * Search Track related items in Assets & Coverage page and Advisories page
     *
     * @param searchItem
     * @author sidraman
     */
    @Step("Search Item: {0}")
    public AppUtils searchTrackItems(String searchItem) {
        WebElement searchField = null;

        try {
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='search']")));
            searchField = getWebElement("//input[@formcontrolname='search']");
        } catch (NoSuchElementException e) {
            searchField = getWebElement("//cui-search//input");
        }
        searchField.clear();
        searchField.sendKeys(searchItem);
        getActions().sendKeys(Keys.ENTER);
        getActions().build();
        getActions().perform();
        return this;
    }

    @Step("Search DNAC field with the dropdown as {0} and Search item as {1}")
    public AppUtils searchDNACField(DNAC_DropdownValue dropdownValue, String searchItem) {
        WebElement searchField = null, dropdownElement;
        String dropdownText;

        try {
            dropdownElement = getWebElement("//div[@class='dropdown-search__searchbar']//div/button/span");
            dropdownText = dropdownElement.getText().trim();
            if (!dropdownValue.getDnac_dropdown().contains(dropdownText)) {
                dropdownElement.click();
                getWebElement("//button/span[contains(text(),'" + dropdownValue.getDnac_dropdown() + "')]").click();
            }
            searchField = getWebElement("//div[@class='dropdown-search__searchbar']//input");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Unable to find the search field");
        }
        searchField.clear();
        searchField.sendKeys(searchItem);
        getActions().sendKeys(Keys.ENTER).build().perform();
        return this;
    }

    /**
     * <p>clear Specific Filter</p>
     *
     * @param filterNames
     * @author sidraman
     */
    @Step("Clear specific filterNames ")
    public Boolean clearSpecificFilter(String... filterNames) {
        for (String filterName : filterNames) {
            getWebElement("//span[contains(text(),'" + filterName + "')]/following-sibling::span[@class='icon-close']").click();
        }
        return checkFilterApplied();
    }

    /**
     * <p>Clears all filters</p>
     *
     * @author sidraman
     */
    @Step("Clear all filters")
    public Boolean clearAllFilter() {
        getWebElement("//a[@data-auto-id='FilterBarClearAllFilters']").click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(getWebElement("//cxui-table")));
        return checkFilterApplied();
    }

    /**
     * <p>Filter results by clicking on different visual filters. Create class object of FilterValue and add any number of filters to be applied to the results</p>
     * <p>For Pie chart filter, please mention the filterValue filed as numbers starting from 0 mapping from the first colour to others in the clock-wise direction</p>
     * <p>For Bar and column chart filter, please mention the position of filter needed starting from 1 and top to bottom or left to right</p>
     *
     * @param filterValues
     * @return
     * @author sidraman
     */
    @Step("Filter by Visual Filters")
    public AppUtils filterByVisualFilters(FilterValue... filterValues) {
        WebElement element = null;
        List<WebElement> elements = null;

        for (FilterValue filterValue : filterValues) {
            try {
                switch (filterValue.getChartType()) {
                    case PIE:
                    case BUBBLE:
                        element = getWebElement("//div[text()='" + filterValue.getFilterTitle() + "']/../..//" + filterValue.getChartType().getChartTypeValue() + "-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue.getFilterValue() + "')]");
                        break;
                    case BAR:
                    case COLUMN:
                        int count = 1;
                        elements = getWebElements("//div[text()='" + filterValue.getFilterTitle() + "']/../..//" + filterValue.getChartType().getChartTypeValue() + "-chart//*[name()='svg']//*[name()='g']//*[name()='text']");
                        for (WebElement columnElement : elements) {
                            if (columnElement.getText().contains(filterValue.getFilterValue())) {
                                break;
                            }
                            count++;
                        }
                        element = getWebElement("//div[text()='" + filterValue.getFilterTitle() + "']/../..//" + filterValue.getChartType().getChartTypeValue() + "-chart//*[name()='svg']//*[name()='g']/*[name()='rect'][" + count + "]");
                        break;
                    default:
                        break;
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.out.println("Failed to find the Visual filter throwing exception");
                continue;
                //Assert.fail("Selected filter value is not present. Please check the filter option is present for the user.");
            }
            getActions().moveToElement(element).click(element).build().perform();
            getWebDriverWait().until(loadingWheelDisappears);
        }

        return this;
    }

    /**
     * <p>Click on a tab to open the same.</p>
     *
     * @param tabName
     * @author sidraman
     */
    @Step("Select a tab with name : {0}")
    public AppUtils selectTab(String tabName) {
        String tabXpath = "//div[@class='tab__heading']//span[contains(text(),'" + tabName + "')]";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tabXpath)));
        getWebElement(tabXpath).click();
        return this;
    }

    /**
     * <p>Click on any subtab in all tabs of the carousel. </p>
     *
     * @param tabName
     * @param subTabName
     * @return
     */
    @Step("Select sub tab: {0}, inside the tab: {1}")
    public AppUtils clickSubTab(String tabName, String subTabName) {
        getWebElement("//span[text()='" + tabName + "']//ancestor::div[contains(@class,'panel')]//*[contains(text(),'" + subTabName + "')]").click();
        return this;
    }

    /**
     * <p>Click on the first row of any grid</p>
     *
     * @author vbollimu
     */
    @Step("Select a specific row of a grid")
    public boolean clickGridFirstRow(int rowIndex) {
        try {
            getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(getWebElement("//tbody")));
            getWebElement("//tbody/tr[" + rowIndex + "]");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println(" row in grid not available");
            return false;
        }
    }

    /**
     * <p>Pass the columnName name to sort it</p>
     *
     * @param columnName
     * @author sidraman
     */
    @Step("Sort the table")
    public boolean sortTable(String columnName) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        try {
            getWebElement("//*[contains(@class,'sortable')]/span[text()='" + columnName + "'] | " +
                    "//*[contains(@class,'sortable')]/span[text()=' " + columnName + " ']").click();
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Table column " + columnName + " is not sortable");
            return false;
        }
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

    @Step("Clicks the 3 dots option of {1} from a row with Unique text of {0}")
    public void openRowOption(String uniqueRowText, String option) {
        try {
            getWebElement("//span[text()='" + uniqueRowText + "']/../..//cui-dropdown//span").click();
            getWebElement("//div[contains(@class,'active')]//span[contains(text(),'" + option + "')]").click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Unable to find the Row option to click");
        }
    }

    @Step("Open 360 view of record having the value : {0}")
    public AppUtils open360View(String value) {
        getWebElement("//span[contains(text(),'" + value + "')]").click();
        return this;
    }

    @Step("Close 360 view")
    public AppUtils close360View() {
        try {
            getWebElement("//a[@data-auto-id='CloseDetails']").click();
        }catch (NoSuchElementException e) {
            System.out.println("No 360 view found");
        }
        return this;
    }

    @Step("Make 360 view as full screen")
    public AppUtils fullscreen360View() {
        getWebElement("//a[@data-auto-id='FullScreen']").click();
        return this;
    }

    @Step("Click the button : {0}")
    public AppUtils clickButton(ButtonName buttonName) {
        getWebElement("//*[contains(@class,'btn')][text()='" + buttonName.getButtonName() + "']").click();
        return this;
    }

    @Step("Verify if the button : {0} selected or not")
    public Boolean verifyButtonSelected(ButtonName buttonName) {
        String classValue = getWebElement("//*[contains(@class,'btn')][text()='" + buttonName.getButtonName() + "']").getAttribute("class");
        return classValue.contains("selected");
    }

    @Step("Wait till the button {0} is available")
    public void waitTillButtonVisible(ButtonName buttonName) {
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'btn')][text()='" + buttonName.getButtonName() + "']")));
    }

    /**
     * <p>Method returns true if the button is enabled and returns false if disabled</p>
     *
     * @param buttonName
     * @return
     */
    @Step("Verify if the button is enabled : {0}")
    public boolean isButtonEnabled(ButtonName buttonName) {
        String classText = getWebElement("//*[contains(@class,'btn')][text()='" + buttonName.getButtonName() + "']").getAttribute("class");
        return !classText.contains("disabled");
    }

    /***
     * <p>Scroll to view Web Element</p>
     * @author rtejanak
     * @param identifier of web element
     */
    @Step("Scroll to the element view")
    public AppUtils jsScrollToView(String identifier) {
        try {
            waitTillElementIsVisible(identifier);
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].scrollIntoView();", getWebElement(identifier));
        } catch (Exception e) {

        }
        return this;
    }

    /***
     * <p>On parameter true, list view is selected. On parameter false, grid view
     * selected.</p>
     * @param grid_listView
     * @author rtejanak
     */
    @Step("Select {0} view")
    public AppUtils selectViewTo(String grid_listView) {
        if (grid_listView.equalsIgnoreCase("list")) {
            clickView("button[data-auto-id='list-view-btn']");
            waitTillElementIsVisible(".half-margin-top");
        } else if (grid_listView.equalsIgnoreCase("grid")) {
            clickView("button[data-auto-id='grid-view-btn']");
            waitTillElementIsVisible(".panel--ltgray");
        }
        jsScrollToView(".cui-pager-page");
        return this;
    }

    /***
     * <p>On prevOrNext prev, PREV is selected. On prevOrNext next, NEXT is selected.prevOrNext value is NOT case sensitive</p>
     * @author rtejanak
     * @param  prevOrNext
     *
     */
    @Step("Navigate to {0}")
    public AppUtils goToPrevOrNext(String prevOrNext) {
        if (prevOrNext.equalsIgnoreCase("prev")) {
            String prev = "a[data-auto-id='CUIPager-PrevPage']";
            getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(getWebElement("//table")));
            if (isNextOrPreviousEnabled("prev")) {
                getWebElement(prev).click();
            }
            jsScrollToView(prev);
        } else if (prevOrNext.equalsIgnoreCase("next")) {
            String next = "a[data-auto-id='CUIPager-NextPage']";
            getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(getWebElement("//table")));
            if (isNextOrPreviousEnabled("next")) {
                getWebElement(next).click();
            }
//            jsScrollToView(next);
        }
        waitTillElementIsVisible("//table");
        return this;
    }

    /**
     * <p> On passing the pageNumber, user will be navigated to that pageNumber, if pageNumber does ot exist, user will be taken to last available page</p>
     *
     * @Param pageNumber
     * @author rtejanak
     */
    @Step("Go to page number : {0}")
    public AppUtils navigateToPage(int pageNumber) {
        String paginationBar = ".cui-pager-page";
        jsScrollToView(paginationBar);
//        waitTillElementIsVisible(paginationBar);

        List<Integer> pageNumbers = new ArrayList<Integer>();
        for (WebElement eachPage : getWebElements("//a[contains(@data-auto-id,'CUIPager-Page')]"))
            pageNumbers.add(Integer.parseInt(eachPage.getAttribute("data-auto-id").replaceAll("CUIPager-Page", "").trim()));
        if (pageNumber > pageNumbers.get((pageNumbers.size() - 1))) {
            getWebElement("//a[contains(@data-auto-id,'CUIPager-Page" + pageNumbers.get((pageNumbers.size() - 1)) + "')]").click();
            waitTillElementIsVisible(paginationBar);
            jsScrollToView(paginationBar);
        } else if (pageNumbers.contains(pageNumber)) {
            getWebElement("//a[contains(@data-auto-id,'CUIPager-Page" + pageNumber + "')]").click();
//            waitTillElementIsVisible(paginationBar);
            jsScrollToView(paginationBar);
        } else {
            for (int i = 0; i < (pageNumber - getCurrentPageNumber()); i++) {
                goToPrevOrNext("next");
            }
        }
        jsScrollToView(paginationBar);
        return this;
    }

    /**
     * <p> Returns the current page number</p>
     *
     * @Param pageNumber
     * @author vbollimu
     */
    public int getCurrentPageNumber() {
        String paginationBar = ".cui-pager-page";
        jsScrollToView(paginationBar);
        waitTillElementIsVisible(paginationBar);
        return Integer.parseInt(getWebElement("//ul[@class='pagination pagination--bordered']//li[@class='active']//span").getText());
    }

    @Step("Verify pagination functionality for {type}")
    public boolean validatePagination(String type, int pageCount) {
        int rowCount = 1, pagesLoaded = 0;
        boolean loadCheck = false;
        String countLabel = null;
        if (type.equals("numbers")) {
            for (int page = 1; page <= pageCount; page++) {
                navigateToPage(page);
                countLabel = getWebElement("//span[contains(@data-auto-id, 'Showing')]").getText();
                if (countLabel.contains("Showing " + rowCount)) {
                    ++pagesLoaded;
                    rowCount += 10;
                }
            }
            if (pagesLoaded == pageCount) {
                loadCheck = true;
            }
        } else {
            pagesLoaded = getCurrentPageNumber();
            if (type.equals("prev") & pagesLoaded > 1) {
                goToPrevOrNext(type);
                if (getCurrentPageNumber() == pagesLoaded - 1) {
                    loadCheck = true;
                }
            } else if (type.equals("next") & pagesLoaded != pageCount) {
                goToPrevOrNext(type);
                if (getCurrentPageNumber() == pagesLoaded + 1) {
                    loadCheck = true;
                }
            }
        }
        return loadCheck;
    }

    /**
     * <p>Used internally in AppUtils page</p>
     *
     * @param nextOrPrevious
     * @return boolean
     * @author rtejanak
     */
    @Step("Verify if {0} is enabled")
    public boolean isNextOrPreviousEnabled(String nextOrPrevious) {
        int length = 0;

        try {
            if (nextOrPrevious.equalsIgnoreCase("next")) {
                String next = "a[data-auto-id='CUIPager-NextPage']";
                WebElement element = getWebElement(next);
                if (element.isDisplayed() && element.isEnabled()) {
                    getJavascriptExecutor().executeScript("arguments[0].scrollIntoView();", element);
                    length = getWebElement(next).getAttribute("class").length();
                }
            } else if (nextOrPrevious.equalsIgnoreCase("prev")) {
                String prev = "a[data-auto-id='CUIPager-PrevPage']";
                if (isElementPresent(prev)) {
                    getWebDriverWait().until(ExpectedConditions.visibilityOf(getWebElement(prev)));
                    jsScrollToView(prev);
                    length = getWebElement(prev).getAttribute("class").length();
                }
            }
        }catch (NoSuchElementException e) {
            System.out.println("No Next or Previous button present");
        }
        return length == 0;
    }

    /**
     * <p>Used internally in AppUtils page</p>
     *
     * @param identifier
     * @author rtejanak
     */
    public void waitTillElementIsVisible(String identifier) {
        try {
            getWebDriverWait().until(ExpectedConditions.visibilityOf(getWebElement(identifier)));
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(getWebElement(identifier)));
        } catch (NullPointerException e) {

        }
    }

    /**
     * <p>Used internally in AppUtils page</p>
     *
     * @param identifier
     * @author rtejanak
     */
    public void clickView(String identifier) {
        jsScrollToView(identifier);
        waitTillElementIsVisible(identifier);
        getWebDriverWait().until(ExpectedConditions.invisibilityOf(getWebElement("//*[contains(@class,'row flex-center-vertical')]/span[2]")));
        getWebElement(identifier).click();
    }

    /**
     * <p> Get total number of count for available Columns.</p>
     *
     * @return :  It returns the total number (int) of columns.
     * @author ankumalv
     */
    @Step("Get total count of columns in table")
    public int getTableColumnCount() {
        return getWebElements("//table/thead/tr/th").size();
    }

    /**
     * <p> Get count on subtab.</p>
     *
     * @author vbollimu
     */

    @Step("Get Count on sub tab: {0}, inside the tab: {1}")
    public String getCountOnSubTab(String tabName, String subTabName) {
        return getWebElement("//span[text()='" + tabName + "']//ancestor::div[contains(@class,'panel')]//*[contains(text(),'" + subTabName + "')]").getText().replace(subTabName, "").trim();
    }

    @Step("Get label count on grid panel")
    public int getLabelCount() {
        getWebDriverWait().until(ExpectedConditions.visibilityOf(getWebElement("//div[@data-auto-id='VisualFilter-total']")));
//        ngWebDriver.waitForAngularRequestsToFinish();
        return Integer.parseInt(getWebElement("TotalVisualFilter").getText());
    }

    /**
     * <p> Get all titles of table.</p>
     *
     * @author ankumalv
     */
    @Step("Get all titles of table")
    public List<String> getAllTableTitles() {
        List<String> columnNames = new LinkedList<>();
        String titlesXpath = "//table/thead/tr/th";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(titlesXpath)));
        for (WebElement title : getWebElements(titlesXpath))
            columnNames.add(title.getText().trim());
        return columnNames;
    }

    /**
     * <p> Get total count of available Rows in table.</p>
     *
     * @return : It returns the total number of rows in table.
     * @author ankumalv
     */
    @Step("Get total numbers of rows visible in table")
    public int getTableRowCount() {
        try {
            return getWebElements("//tbody/tr").size();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    /**
     * <p> Get any table cell value by passing row index and column index from records, such as for rowIndex = 2 & columnIndex = 3 will get "System name" value. </p>
     *
     * @param rowIndex    starts from 1
     * @param columnIndex starts from 1
     * @return : It returns the table cell value (String).
     * @author ankumalv
     */
    @Step("Get record value by using row index : {0} and column index : {1} from table")
    public String getTableCellValue(int rowIndex, int columnIndex) {
        String cellXpath = "//tbody/tr[" + rowIndex + "]/td[" + columnIndex + "]";
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(cellXpath)));
        return getWebElement(cellXpath).getText();
    }

    /**
     * <p> Check Filter is applied or not. </p>
     *
     * @return : It returns the boolean if any filter applied on the Grid or not
     * @author ysreeniv
     */
    @Step("Verify the Filter Applied on Grid")
    public boolean checkFilterApplied() {
        try {
            getWebElement("span.label.filter-bar__item");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getTotalCountOfPages() {
        List<WebElement> pageNumberElements = getWebElements("//a[@class='cui-pager-page']/span");
        int lastIndex = pageNumberElements.size() - 1;
        try {
            return pageNumberElements.get(lastIndex).getText();
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            return "1";
        }
    }

    /**
     * <p>Clear the searched field .</p>
     *
     * @author shpattna
     */
    @Step("To clear searched field in Crashed Systems grid")
    public void clearSearchedValue() {
        WebElement searchField = getWebElement("//button[@class='link clear-button']//span[@class='icon-close']");
        searchField.click();
    }

    @Step("Check Element is Present in the Page")
    public boolean isElementPresent(String identifier) {
        try {
            WebElement element = getWebElement(identifier);
            return element.isDisplayed() && element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Close Welcome Page if Present")
    public AppUtils closeWelcomePopUp() {
        String locator = "*[data-auto-id='welcome-page-close-icon']";
        if (isElementPresent(locator)) {
            getWebElement(locator).click();
            System.out.println("Closed Welcome Popup");
        }
        return this;
    }

    @Step("Close info popups")
    public AppUtils closeInfoPopups() {
        String infoPopup = "//div[contains(@class,'popup--')]";
        String infoPopupSkipButton = "//div[@class='popup popup--bottom']//button[position()=1]";
        while (isElementPresent(infoPopup) && isElementPresent(infoPopupSkipButton)) {
            getWebElement(infoPopupSkipButton).click();
            System.out.println("Closed Info Popup");
        }
        return this;
    }


    @Step("Close Privacy Consent Dialog")
    public AppUtils closePrivacyConsentDialog() {
        String locator = "div[class='privacy_prompt explicit_consent']>div[class='close_btn_thick']";
        if (isElementPresent(locator)) {
            getWebElement(locator).click();
            System.out.println("Closed Privacy Consent Dialog");
        }
        return this;
    }

    @Step("Get Current Pitstop Name")
    public String getCurrentPitStopName() {
        return getWebElement("[data-auto-id='CurrentPitstopTitle']").getText();
    }

    @Step("Change the Pitstop to {0}")
    public AppUtils changePitStop(PitStopsName toPitStop) {
        PitStopsName currentPiStop = PitStopsName.valueOf(getCurrentPitStopName());
        System.out.println("Changing the pistop from " + currentPiStop.getPitStopName() + " to " + toPitStop.getPitStopName());
        getWebElement("//*[name()='circle' and @data-auto-id='" + toPitStop.getPitStopName() + "']").click();
        return this;
    }

    @Step("Move to Next Pitstop")
    public AppUtils moveToNextPitstop() {
        getWebElement("//*[name()='g' and @data-auto-id='Racetrack-RightArrow']/circle").click();
        return this;
    }

    @Step("Move to Previous Pitstop")
    public AppUtils moveToPreviousPitstop() {
        getWebElement("//*[name()='g' and @data-auto-id='Racetrack-LeftArrow']").click();
        return this;
    }

    @Step("Move to Current Pitstop")
    public AppUtils moveToCurrentPitstop() {
        getWebElement("//*[name()='g' and @id='racecar']").click();
        return this;
    }

    @Step("Close All pop ups")
    public AppUtils closeAllPopUps() {
        while (isElementPresent("//a[@data-auto-id='CloseDetails']")) {
            getWebElement("//a[@data-auto-id='CloseDetails']").click();
        }

        while (isElementPresent("//button[@data-auto-id='ViewAllCloseModal']")) {
            getWebElement("//button[@data-auto-id='ViewAllCloseModal']").click();
        }
        return this;
    }

    @Step("Close All dropDowns")
    public AppUtils closeAllDropDowns() {
        while (isElementPresent("[class*=flipped]")) {
            getWebElement("[class*=flipped]").click();
        }
        return this;
    }

    @Step("Reload the page")
    public AppUtils reload() {

        getDriver().navigate().refresh();
        waitForInitialPageToLoad();
        return this;
    }


    @Step("Reverse Lexicographical order ")
    public List<String> getReverseSortOrder(List list) {
        Collections.sort(list, (s1, s2) -> ((String) s2).compareToIgnoreCase((String) s1));
        return list;
    }

    @Step("Lexicographical order")
    public List<String> getNaturalSortOrder(List list) {
        Collections.sort(list, (s1, s2) -> ((String) s1).compareToIgnoreCase((String) s2));
        return list;
    }

    @Step("Wait for the overlapping element to get invisible")
    public void waitForInvisibilityOfElement(String locator) {
        if (isElementPresent(locator)) {
            getWebDriverWait().until(ExpectedConditions.invisibilityOfAllElements(getWebElements(locator)));
        }
    }

    /**
     * <p>Select column/header from the column selector</p>
     *
     * @param tableHeader(String - Case Sensitive) : Pass desired to be selected if one ex: "Product ID" and if more than one separated by comma i.e, "Product ID , Updated "
     * @return
     */
    @Step("Select column/header from the column selector")
    public AppUtils selectColumnFromColumnSelector(String tableHeader) {
        String header;

        List<WebElement> headers = getWebElements("//span[@class='column-header']");
        List<String> headerList = new ArrayList<String>();
        for (WebElement title : headers) {
            headerList.add(title.getText());
        }
        getWebElement("//*[@class='column-selector-trigger']").click();
        StringTokenizer headersArray = new StringTokenizer(tableHeader, ",");
        while (headersArray.hasMoreTokens()) {
            header = headersArray.nextToken();
            if (headerList.contains(header)) {
            } else if (header.equals("Created") & !headerList.contains(header)) {
                getJavascriptExecutor().executeScript("arguments[0].click();", getWebElement("//label[text()=' " + header + " ']/span[@class='check-mark']"));
            } else {
                getJavascriptExecutor().executeScript("arguments[0].click();", getWebElement("//label[contains(text(),'" + header + "')]/span[@class='check-mark']"));
            }
        }
        getJavascriptExecutor().executeScript("arguments[0].click();", getWebElement("//button[contains(text(),'APPLY')]"));
        return this;
    }

    @Step("Reset Column's from Column Selector")
    public AppUtils resetColumnsFromColumnSelector() {

        getWebElement("//button[@class='column-selector-trigger']").click();
        getWebElement("//div[@class='cdk-overlay-container']//button[text()=' RESET ']").click();
        return this;
    }

    /**
     * <p>De-select column/header from the column selector</p>
     *
     * @param tableHeader(String - Case Sensitive) : Pass desired to be selected if one ex: "Product ID" and if more than one separated by comma i.e, "Product ID , Updated "
     * @return
     */
    @Step("De-select column/header from the column selector")
    public AppUtils deselectColumnFromColumnSelector(String tableHeader) throws InterruptedException {
        String header;

        List<WebElement> headers = getWebElements("//span[@class='column-header']");
        List<String> headerList = new ArrayList<String>();
        for (WebElement title : headers) {
            headerList.add(title.getText());
        }

        getWebElement("//*[@class='column-selector-trigger']").click();
        StringTokenizer headersArray = new StringTokenizer(tableHeader, ",");
        while (headersArray.hasMoreTokens()) {
            header = headersArray.nextToken();
            if (header.equals("Created") && headerList.contains(header)) {
                getJavascriptExecutor().executeScript("arguments[0].click();", getWebElement("//label[text()=' " + header + " ']/span[@class='check-mark']"));
            } else if (headerList.contains(header)) {
                getJavascriptExecutor().executeScript("arguments[0].click();", getWebElement("//label[contains(text(),'" + header + "')]/span[@class='check-mark']"));
            }
        }
        getJavascriptExecutor().executeScript("arguments[0].click();", getWebElement("//button[contains(text(),'APPLY')]"));
        return this;
    }

    @Step("MouseOver and get tooltip Value")
    public String getToolTipData(String label) throws InterruptedException {
        WebElement toollTip=getWebElement("//span[text()='"+label+"']/../span[contains(@class,'icon-help-alt')]");
        getActions().moveToElement(toollTip).perform();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tooltip")));
        return getWebElement("//tooltip").getText();
    }

    @Step("Get dropdown values")
    public List<String> getDropDownValues(String dropDownName){
        List<WebElement> options = new ArrayList<WebElement>();
        List<String> val = new ArrayList<String>();
        try {
            WebElement dropDown = getWebElement(dropDownName);
            Select slct = new Select(dropDown);
            options = slct.getOptions();
        }catch(NoSuchElementException e) {
            throw e;
        }

        options.forEach(ele -> val.add(ele.getText()));
        return val;

    }

    /**
     *
     * @return current page Index incase of working with table
     */
    public String getCurrentPage() {
        return getWebElement("//a[@class='cui-pager-page']/span/../ancestor::li[contains(@class,'active')]").getText();
    }

    public AppUtils goToLastPage() {
        String totalCount;
        List<WebElement> pageNumberElements = getWebElements("//a[@class='cui-pager-page']/span");
        int lastIndex = pageNumberElements.size() - 1;
        try {
            totalCount = pageNumberElements.get(lastIndex).getText();
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException e) {
            totalCount = "1";
        }
        navigateToPage(Integer.valueOf(totalCount));
        return this;

    }
    public AppUtils resetTable() {
        getWebDriverWait().until(loadingWheelDisappears);
        WebElement elemt =getWebElement("//button[@class='column-selector-trigger']");
        elemt.click();
        WebElement resetTable;
        try {
            resetTable = getWebElement("//button[text()=' RESET ']");
            resetTable.click();
        }catch (Exception e){
            System.out.println("Inside catch");
            jsScrollToView("//button[text()=' RESET ']");
            resetTable = getWebElement("//button[text()=' RESET ']");
            resetTable.click();
        }
        getWebDriverWait().until(loadingWheelDisappears);
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//cxui-table")));
        return this;
    }

    public AppUtils refreshAndWait()
    {
        getDriver().get(getDriver().getCurrentUrl());
        waitForInitialPageToLoad();
        return this;
    }

    public AppUtils scrollToBottom() {
        getJavascriptExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight);");
        return this;
    }

    /**
     * Caution: Should be used only when the browser is running
     *
     * @param format    date format
     * @param epochTime time in millis
     * @return formatted date
     */
    public String getFormattedDate(String format, String epochTime) {
        String systemTimeZone = String.valueOf(getJavascriptExecutor().executeScript("return Intl.DateTimeFormat().resolvedOptions().timeZone;"));
        return DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.of(systemTimeZone))
                .format(Instant.ofEpochMilli(Long.parseLong(epochTime)));
    }
    /**
     * <p>To Click on Export Option and Validate export to CSV is enabled/disabled</p>
     *
     * @author shrrama2
     */
    @Step("Export Button Click")
	public void getExportClick()
	{
		getWebElement("//div[@data-auto-id='showMoreButton']").click();
	}
	
	@Step("Verify Export to CSV is Enabled or Disabled")
	public boolean getExportCSV()
	{
		return getWebElement("//button[@data-auto-id='exportToCSVButton']").isEnabled();
	}
	 
	/**
     * <p>To verify Beta tag </p>
     *
     * @author shrrama2
     */
	@Step("To verify Beta Tag is displayed")
	public boolean betaToolTip(String tabName)
	{
		return getWebElement("//span[@class='beta-tab-title'][text()='" + tabName + "']/../img").isDisplayed();

	}

}

