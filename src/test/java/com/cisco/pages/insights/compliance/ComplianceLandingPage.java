package com.cisco.pages.insights.compliance;

import com.cisco.testdata.RccConstants;
import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import io.restassured.http.Header;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplianceLandingPage extends AppUtils {
    private static long tokenCreatedTime;

    @Step("Clear search field")
    public AppUtils clearSearch() {
        getWebElement("//button[@data-auto-id='clearSearchButton']").click();
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//cxui-table")));
        isElementPresent("//cxui-table");

        return this;
    }

    @Step("Validate  Compliance is active")
    public boolean checkActiveTab(String label) {
        try {
            getWebElement("//div[text()='" + label + "']//ancestor::li[contains(@class,'tab active')]");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Validate Default search bar")
    public boolean checkDefualtSearchWithAll() {
        try {
            getWebElement("//button[@data-auto-id=\"dropdownSearchButton\"]/span[text()=\" All \"]");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Validate Compliance Sub tab is active")
    public boolean checkActiveSubTab(String label) {
        try {
            getWebElement("//div[text()='" + label + "']//ancestor::div[contains(@class,'view__selected')]");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Validate Asc sort header tab is active")
    public boolean checkSortActiveWithAscSign(String label) {
        try {
            getWebElement("//span[text()='"+label+"']/../span[contains(@class,'sort-icon--asc')]");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Validate dec sort header tab is active")
    public boolean checkSortActiveWithDecSign(String label) {
        try {
            getWebElement("//span[text()='"+label+"']/../span[contains(@class,'sort-icon--desc')]");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Validate No results found is present")
    public boolean checkNoResultFound() {
        try {
            isElementPresent("//span[text()='No Results Found']");
//            getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(getWebElement("//span[text()='No Results Found']")));
            getWebElement("//span[text()='No Results Found']");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Validate Banner title")
    public boolean checkBannerTitle(String label) {
        try {
            getWebElement("//span[text()='"+label+"']");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Validate Violation summary table is present is present")
    public boolean checkTableVisible() {
        try {
            getWebElement("//table");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Get the Count of {0} on the Insights Carousel")
    public String getCountOnCarousel(String label) {
//        String labelXpath = "//*[text()='Compliance']/preceding-sibling::div/span";
        String labelXpath = "div[class*='insights__title']>div:nth-child(4)>div:nth-child(2)>div";
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(labelXpath)));
        return getWebElement(labelXpath).getText();
    }

    @Step("Get the placeHolder value")
    public String getPlaceholderText() {
        String labelXpath = "//input[@data-auto-id='dropdownSearchInput']";
        isElementPresent(labelXpath);
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelXpath)));
        return getWebElement(labelXpath).getAttribute("placeholder");
    }

    @Step("Get the Count from Sub Tabs")
    public String getCountSubTabViolations(String label) {
        String labelXpath = "//div[text()='" + label + "']/preceding-sibling::div";
        isElementPresent(labelXpath);
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelXpath)));
        return getWebElement(labelXpath).getText();
    }

    @Step("Click on compliance tab")
    public AppUtils clickCompianceTab(String label) {
////        String labelXpath = "//*[text()='" + label + "']/../..";
////        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelXpath)));
//        getWebElement(labelXpath).click();
        getWebDriverWait().until(loadingWheelDisappears);
        getWebElement("//div[@class='tab__heading']/div[text()='Compliance']").click();
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath()));
        getWebDriverWait().until(loadingWheelDisappears);
        return this;
    }

    @Step("Click on compliance tab when its not opted in")
    public AppUtils clickCompianceTabWithBanner(String label) {
        String labelXpath = "//div[text()='" + label + "']/../..";
        isElementPresent(labelXpath);
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelXpath)));
        getWebElement(labelXpath).click();
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='alert-heading']")));
        isElementPresent("//span[@class='alert-heading']");
        return this;
    }

    @Step("Click on sub Tab")
    public AppUtils clickSysWithVioTab(String label) {
        String labelXpath = "//div[text()='" + label + "']/preceding-sibling::div";
       // getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(getWebElement("//cui-table")));
        isElementPresent(labelXpath);
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelXpath)));
        getWebElement(labelXpath).click();
        isElementPresent("//cxui-table");
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//cxui-table")));
        return this;
    }

//    public String getToken(String bearerToken,int landing) {
//        JavascriptExecutor js=(JavascriptExecutor) getDriver();
//
//
//       // resetTable();
//
//        tokenCreatedTime = System.currentTimeMillis();
//        System.out.println("RBAC JWT Token Created at ::: " + tokenCreatedTime);
//
//        bearerToken = (String) js.executeScript(String.format(
//                "return window.sessionStorage.getItem('%s');", "okta-token-storage"));
//
//        JSONObject jsonObject = new JSONObject(bearerToken);
//        return bearerToken = jsonObject.getJSONObject("accessToken").getString("value");
//
//       /* if(isTokenExpired(bearerToken)) {
//            getDriver().get(getDriver().getCurrentUrl());
//            waitForInitialPageToLoad();
//            clickCompianceTab(RccConstants.COMPLIANCE_TAB);
//            resetTable();
//            if(landing==2)
//                clickSysWithVioTab(RccConstants.SYSTEMS_VIOLATIONS_SUB_VIEW);
//
//            tokenCreatedTime = System.currentTimeMillis();
//            System.out.println("RBAC JWT Token Created at ::: " + tokenCreatedTime);
//
//            bearerToken = (String) js.executeScript(String.format(
//                    "return window.sessionStorage.getItem('%s');", "okta-token-storage"));
//
//            JSONObject jsonObject = new JSONObject(bearerToken);
//           return bearerToken = jsonObject.getJSONObject("accessToken").getString("value");
//
//
//        }
//        else
//        {
//            System.out.println("Token didnt expire");
//        }
//        return bearerToken;*/
//    }

//    private  boolean isTokenExpired(String bearerToken) {
//        if (bearerToken == null) {System.out.println("First time"); return true;}
//        long currentTimeMillis = System.currentTimeMillis();
//        System.out.println(currentTimeMillis - tokenCreatedTime);
//        System.out.println(currentTimeMillis - tokenCreatedTime > 1740000);
//        return currentTimeMillis - tokenCreatedTime > 1740000;
//    }

    @Step("Return the Showing violations condition")
    public String verifyShowingViolationsNumbers() {
//        ngWebDriver.waitForAngularRequestsToFinish();
        String violationNumberElement = "//span[@data-auto-id='ShowingCount']";
        String violationumber = getWebElement(violationNumberElement).getText();
        return (violationumber.split(" ")[violationumber.split(" ").length - 3]).trim();
    }

    @Step("Return the Asset violations condition")
    public String verifyShowingAssetsNumbers() {
//        ngWebDriver.waitForAngularRequestsToFinish();
        String violationNumberElement = "//span[@data-auto-id='ShowingCount']";
        String violationumber = getWebElement(violationNumberElement).getText();
        return (violationumber.split(" ")[violationumber.split(" ").length - 4]).trim();
    }

    @Step("Return the column value and number of times it repeated")
    public Map<String, Integer> verifyValueOnViolationView(int columnIndex, int colIndexSum) {
        Map<String, Integer> hashMap = new HashMap<>();
        int rowCount, pageCount;

        List<String> list = new ArrayList<>();
        pageCount = Integer.valueOf(getTotalCountOfPages());
        int violationCount=0;

        for (int k = 0; k < pageCount; k++) {
            rowCount = getTableRowCount();

            if (rowCount > 0) {
                for (int i = 1; i <= rowCount; i++) {
                    list.add(getTableCellValue(i, columnIndex));
                    violationCount = violationCount + Integer.parseInt(getTableCellValue(i, colIndexSum));

                }
            }
            if (isNextOrPreviousEnabled("Next"))
                goToPrevOrNext("Next");
        }

        //Rest pagination
        navigateToPage(1);

        for (String violationValue : list) {
            if (hashMap.containsKey(violationValue)) {
                hashMap.put(violationValue, hashMap.get(violationValue) + 1);
            } else {
                hashMap.put(violationValue, 1);
            }
        }
        hashMap.put("Sum", violationCount);
        return hashMap;
    }

    @Step("Return the sum of all the vioaltions")
    public String verifyCountOnViolationView(int columIndex) {
        int rowCount, pageCount;
        pageCount = Integer.valueOf(getTotalCountOfPages());
        int violationCount = 0;
        for (int k = 0; k < pageCount; k++) {
            rowCount = getTableRowCount();
            if (rowCount > 0) {

                for (int i = 1; i <= rowCount; i++) {
                    System.out.println(getTableCellValue(i, columIndex));
                    violationCount = violationCount + Integer.parseInt(getTableCellValue(i, columIndex));
                }
            }
            if (isNextOrPreviousEnabled("Next"))
                goToPrevOrNext("Next");
        }
        return String.valueOf(violationCount);
    }

    @Step("Return the column values")
    public List<String> verifyColmmnValue(int columnIndex) {
        int rowCount, pageCount;

        List<String> list = new ArrayList<>();
        pageCount = Integer.parseInt(getTotalCountOfPages());

        for (int k = 0; k < pageCount; k++) {
            rowCount = getTableRowCount();
           // System.out.println("rowCount on " + rowCount);

            if (rowCount > 0) {
                for (int i = 1; i <= rowCount; i++) {
                    list.add(getTableCellValue(i, columnIndex).trim());
                }
            }
            if (isNextOrPreviousEnabled("Next")) {
                goToPrevOrNext("Next");
            }
        }
        return list;
    }

    @Step("Return search validatity")
    public boolean verifySearch(String searchValue)
    {
        System.out.println("The search value is" + searchValue);
        int rowCount, pageCount;
        boolean flag=false;
        List<Integer> list = new ArrayList<>();
        pageCount = Integer.valueOf(getTotalCountOfPages());

        for (int k = 0; k < pageCount; k++)
        {
            rowCount = getTableRowCount();
          //  System.out.println("rowCount on " + rowCount);

            if (rowCount > 0)
            {
                for (int i = 1; i <= rowCount; i++)
                {
                    list.add(getWebElements("//table//tr[" + i + "]/descendant-or-self::td/span[contains(text(),'" + searchValue + "')]|//table//tr[" + i + "]/descendant-or-self::td/div[contains(text(),'" + searchValue + "')]").size());
                }
            }
            if (isNextOrPreviousEnabled("Next"))
                goToPrevOrNext("Next");
        }
        System.out.println("the size os search " + list.toString());
        flag=!list.contains(0);

        //Rest pagination
        navigateToPage(1);
        return flag;
    }

    @Step("Get Banner Title")
    public String getBannerTitle()
    {
        isElementPresent("//span[@class='alert-heading']");
//        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='alert-heading']")));
        return getWebElement("//span[@class='alert-heading']").getText();
    }

    @Step("Get Banner Message")
    public String getBannerMessage()
    {
        isElementPresent("//span[@class='alert-heading']/../div/span");
//        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='alert-heading']/../div/span")));
        return getWebElement("//span[@class='alert-heading']/../div/span").getText();
    }

    @Step("Click on run compliance check")
    public AppUtils clickRunComplianceCheck(int index) {
        getWebDriverWait().until(loadingWheelDisappears);
//        String labelXpath = "//tr["+index+"]//div[@data-auto-id='RccActions']";
        String labelXpath = "//tbody/tr["+ index +"]//div[@data-auto-id='RccActions']/cui-dropdown";
        isElementPresent(labelXpath);
//        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelXpath)));
        getWebElement(labelXpath).click();
        getWebElement("//a[contains(.,'Run compliance check')][1]").click();
        getWebDriverWait().until(loadingWheelDisappears);
        isElementPresent("//*[@class='modal__header']");
//        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='modal__header']")));
        return this;
    }

    @Step("Close the run compliance check")
    public AppUtils clickCloseTheRunComplianceCheck() {
        getDriver().findElement(By.xpath("//*[@data-auto-id='RccOnCloseModal']")).click();
        getWebDriverWait().until(loadingWheelDisappears);
        getWebDriverWait().until(ExpectedConditions.attributeContains(By.xpath("//div[@class='modal__dialog']/.."),"class","hide"));
        return this;
    }

    @Step("Close the run compliance check using the cancel button")
    public AppUtils clickCloseButtonTheRunComplianceCheck() {
        getDriver().findElement(By.xpath("//*[@data-auto-id='RccOnScanCancel']")).click();
        getWebDriverWait().until(ExpectedConditions.attributeContains(By.xpath("//div[@class='modal__dialog']/.."),"class","hide"));

        return this;
    }

    @Step("Close the run compliance check using the cancel button")
    public AppUtils clickInitateButtonTheRunComplianceCheck() {
        getDriver().findElement(By.xpath("RccOnScanStart")).click();
        getWebDriverWait().until(ExpectedConditions.attributeContains(By.xpath("//div[@class='modal__dialog']/.."),"class","hide"));
        return this;
    }

    @Step("Click  on ok button to close the modal")
    public AppUtils clickOkRunComplianceCheck() {
        getDriver().findElement(By.xpath("//*[@data-auto-id='RccOnOK']")).click();
        getWebDriverWait().until(ExpectedConditions.attributeContains(By.xpath("//div[@class='modal__dialog']/.."),"class","hide"));
        return this;
    }

    @Step("Get Run Compliance Check Popup Header Name")
    public String getRunComplianceHeaderName() {
        return getDriver().findElement(By.xpath("//*[@class='modal__header']")).getText();
    }

    @Step("Get First Asset Name")
    public String getFirstAssetName() {
        return getDriver().findElement(By.xpath("//span[@data-auto-id='Asset Name-Cell'][1]")).getText();
    }

    @Step("Get Asset Name in Run Compliance Check Popup")
    public String getRunComplianceSelectedSystemName() {
        return getDriver().findElement(By.xpath("//div[@data-auto-id='RccUserscan']//div[2]")).getText();
    }

    @Step("Get Policy Group Names in Run Compliance Check Popup")
    public String getRunComplianceSelectedPGName() {
        return getDriver().findElement(By.xpath("//div[@data-auto-id='RccUserRegulatoryTypes']//div[2]")).getText();
    }
}
