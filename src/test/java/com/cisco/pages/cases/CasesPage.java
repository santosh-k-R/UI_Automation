package com.cisco.pages.cases;

import com.cisco.testdata.StaticData.CasesTableHeader;
import com.cisco.utils.AppUtils;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.cisco.testdata.StaticData.ButtonName.ALL_OPEN_CASES;
import static com.cisco.testdata.StaticData.ButtonName.MY_OPEN_CASES;
import static org.testng.Assert.assertTrue;

public class CasesPage extends CaseUtils {

    public static final String CASES_TAB = "Cases";
    public static final String SUPPORT_METRICS_TAB = "Support Metrics";
    public static final String OPEN_CASES = "Open Cases";
    public static final String WITH_RMAS_LABEL = "With RMAs";
    public static final String STATUS_FILTER_TITLE = "STATUS";
    public static final String SEVERITY_FILTER_TITLE = "SEVERITY";
    public static final String UPDATED_FILTER_TITLE = "UPDATED";
    public static final String TOTAL_TIME_OPEN_FILTER_TITLE = "TOTAL TIME OPEN";
    public static final String RMAS_FILTER_TITLE = "RMAs";
    public static final String AUTO_CREATED_FILTER_TITLE = "CASE OPENED";
    public static final String PRODUCT_TYPE_FILTER_TITLE = "PRODUCT TYPE";
    public static final String S1_VALUE = "S1";
    public static final String S2_VALUE = "S2";
    public static final String S3_VALUE = "S3";
    public static final String S4_VALUE = "S4";
    public static final String CUSTOMER_PENDING_STATUS = "Customer...";
    public static final String CLOSED_PENDING_STATUS = "Close Pe...";
    public static final String CISCO_PENDING_STATUS = "Cisco Pe...";
    public static final String SERVICE_ORDER_PENDING_STATUS = "Service O...";
    public static final String WITHOUT_RMAS_VALUE = "Without RMAs";
    public static final String TWENTY_FOUR_HOUR_VALUE = "<1 day";
    public static final String ONE_DAY_VALUE = "1-7 days";
    public static final String ONE_WEEK_VALUE = ">7 days";
    public static final String AUTO_CREATED_VALUE = "Auto-Created";
    public static final String ALL_VALUE = "All Methods";
    public static final String DATA_CENTER_PROD_TYPE = "Data Cent...";
    public static final String SWITCHES_PROD_TYPE = "Switches";
    public static final String ROUTERS_PROD_TYPE = "Routers";
    public static final String WIRELESS_PROD_TYPE = "Wireless";
    public static final String OTHERS_PROD_TYPE = "Others";
    public static final String INDUSTRIAL_PROD_TYPE = "Industria";

    @Step("Search Case: {0}")
    public AppUtils searchCase(String caseNumber) {
        WebElement searchField = getWebElement("//input[@data-auto-id='caseSearchBox']");
        searchField.clear();
        searchField.sendKeys(caseNumber);
        getActions().sendKeys(Keys.ENTER);
        getActions().build();
        getActions().perform();
        getWebDriverWait().until(loadingWheelDisappears);
        getActions().sendKeys(Keys.ENTER).build().perform();
        return this;
    }

    @Step("Clear search field")
    public AppUtils clearSearchCase() {
        getWebElement("//button[@data-auto-id='closeButton']").click();
        return this;
    }

    @Step("Get the Count of {0} on the Problem Resolution Carousel")
    public String getCountOnPR(String label) {
        try {
            String labelXpath = "//div[text()='" + label + "']/following-sibling::div";
            getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelXpath)));
            return getWebElement(labelXpath).getText();
        } catch (NoSuchElementException e) {
            return "0";
        }
    }

    /**
     * <p> It return the string showing in front of "open cases" below to visual filters i.e., "Showing 1-10 of 52 open cases"</P
     *
     * @return Open cases numbers (i.e., "Showing 1-10 of 52 open cases")
     * @author ankumalv
     */
    @Step("Return the Showing cases number")
    public String getShowingCaseNumbersCount() {
        String caseNumber;
        try {
            getWebDriverWait().until(loadingWheelDisappears);
            caseNumber = getWebElement("//span[@data-auto-id='rmaShowingXcasesHeader']").getText();
            return (caseNumber.split(" ")[caseNumber.split(" ").length - 3]).trim();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * <p>It returns the map<String, Integer> , the case details along with how many time it appears as Key and value pairs i.e.,For Severity column: S1=2 , S3=8 ,For Updated: 6 days ago = 2</p?
     *
     * @param columnIndex as Column Number on table , for which we are doing filtering i.e. Severity = 1, Updated = 7.
     * @return returns the map<String, Integer> case details along with how many times it appears.(i.e., S1=2 )
     */
    @Step("Return the case details along with times it appears")
    public Map<String, Integer> verifyValueOnCaseView(int columnIndex) {
        Map<String, Integer> hashMap = new HashMap<>();
        int rowCount, pageCount;
        List<String> list = new ArrayList<>();
        pageCount = Integer.parseInt(getTotalCountOfPages());

        for (int k = 0; k < pageCount; k++) {
            rowCount = getTableRowCount();
            if (rowCount > 0) {
                for (int i = 1; i <= rowCount; i++) {
                    list.add(getTableCellValue(i, columnIndex));
                }
            }
            if (isNextOrPreviousEnabled("Next") && pageCount > 1)
                goToPrevOrNext("Next");
        }
        for (String caseValue : list) {
            if (hashMap.containsKey(caseValue)) {
                hashMap.put(caseValue, hashMap.get(caseValue) + 1);
            } else {
                hashMap.put(caseValue, 1);
            }
        }
        return hashMap;
    }

    /**
     * <p>To get FilterTab tag WebElemnt</p?
     *
     * @param filterTabName filterTabName (String)
     * @return webelement of Filter tag
     */
    @Step("Return the filter tag webelement")
    public WebElement selectFilterTag(String filterTabName) {
        return getWebElement("//span[@class='label filter-bar__item']//span[contains(text(),'" + filterTabName + "')]");
    }

    /**
     * <p> To get total case number on "My Open Cases" tab.</p>
     *
     * @return total case number
     * @author ankumalv
     */
    @Step("Return the My Open Cases Number")
    public String getOpenCasesNumber() {
        return getWebElement("//div[@data-auto-id='TotalVisualFilter']").getText();
    }

    @Step("Click on the My Open Cases Number")
    public void clickOpenCasesView() {
        getWebElement("//div[@data-auto-id='TotalVisualFilter']").click();
    }


    /**
     * <p>This method is to get desired, back date from now (i.e., suppose todays date is 17 and if we want to get yesterday date , then pass "1" so that we can get 16, in UTC format.</p>
     *
     * @param duration , pass the number to get back date from now.
     * @return back date along with time in UTC format (i.e., 2020-03-17T06:25:37.894Z )
     * @author ankumalv
     */
    @Step("Return date")
    public String date(int duration) {
        return OffsetDateTime.now(ZoneOffset.UTC).minus(duration, ChronoUnit.DAYS).toString();

    }

    /**
     * <p>Return invalid case alert for invalid case number</p>
     *
     * @return invalid case alert
     */
    @Step("Return invalid case alert")
    public String getInvalidCaseAlert() {
        return getWebElement("//span[@data-auto-id='invalidCaseNumber']").getText();
    }

    /**
     * <p>Return random number starts from "685", length range from 4 to 12.	</p>
     *
     * @return random number
     */
    @Step("Return random number")
    public String getRandomNumber() {
        return String.format("%08d", new Random().nextInt(100000000));
    }

    @Step("Verify the filters applied results in No Cases Found Text")
    public boolean verifyNoCasesFoundText() {
        return isElementPresent("//div[text()='No Open Cases Found']");
    }

    @Step("Click on the Clear All Filters link when there are no cases present after applying filters")
    public void clearAllFiltersNoResults() {
        try {
            getWebElement("//a[@data-auto-id='NoResultsClearAll']").click();
        } catch (NoSuchElementException e) {
            Assert.fail("Clear All filters link not present");
        }
    }

    @Step("Refresh Cases Page if there are any issues and the Case page did not load properly")
    public void refreshCasesPage() {
        if (isElementPresent("//div[text()='Service Unavailable']")
                || isElementPresent("//div[contains(text(),'No Open')]")
                || isElementPresent("//span[contains(text(),'You do not have')]")) {
            getDriver().navigate().refresh();
            waitForInitialPageToLoad();
            getWebDriverWait().until(loadingWheelDisappears);
            clickButton(ALL_OPEN_CASES);
            clickButton(MY_OPEN_CASES);
        }
    }

    @Step("Get all the column values from the Case list table from the column number {0}")
    public List<String> getAllColumnValues(int columnIndex) {
        List<WebElement> allElements = getWebElements("//table//tbody/tr/td[" + columnIndex + "]//span");
        return allElements.stream().map(WebElement::getText).collect(Collectors.toList());


    }

    @Step("Verify if the sorting of records is proper according to the column {0} in {2} order")
    public void verifyColumnOrdering(CasesTableHeader casesTableHeader, int columnIndex, String order) {
        List<String> columnList;

        sortTable(casesTableHeader.getTableHeader());
        assertTrue(verifyColumnSorted(casesTableHeader.getTableHeader(), order), "Not sorted in " + order + " order by " + casesTableHeader.getTableHeader() + " column");
        columnList = getAllColumnValues(columnIndex);
        assertTrue(Ordering.allEqual().isOrdered(columnList), casesTableHeader.getTableHeader() + " not in " + order + " order");
    }

}
