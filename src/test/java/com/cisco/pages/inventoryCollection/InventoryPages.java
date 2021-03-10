package com.cisco.pages.inventoryCollection;

import com.cisco.testdata.StaticData.ButtonName;
import com.cisco.utils.AppUtils;
import com.cisco.utils.Commons;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class InventoryPages extends AppUtils {


    @Step("Triggering the collection profile upload from the Swagger API")
    public String triggerAPI() {
        String responseCode, inventoryFileName, dataCollectionStatus;
        String swaggerLink = System.getProperty("swaggerApiURL");
        String collectionProfile = System.getProperty("collectionProfileName");

        System.out.println("Swagger url : " + swaggerLink);

        getDriver().get(swaggerLink);
        clickAPIMethod("/runprofile/{cpName}");
        clickButton(ButtonName.TRY_IT_OUT);
        inputValue("cpName", collectionProfile);
        clickButton(ButtonName.EXECUTE);
        responseCode = getServerResponseText();
        if (!responseCode.equalsIgnoreCase("200")) {
            Assert.fail("200 response not found");
        }
        clickAPIMethod("/runprofile/{cpName}");
        clickAPIMethod("/status/{cpName}");
        clickButton(ButtonName.TRY_IT_OUT);
        inputValue("cpName", collectionProfile);


        try {
            do {
                clickButton(ButtonName.EXECUTE);
                dataCollectionStatus = getDataCollectionStatus();
                if (dataCollectionStatus.equalsIgnoreCase("\"FAILED\"")) {
                    Assert.fail("Failed to upload file");
                }
                Thread.sleep(10000);

            } while (getServerResponseText().equalsIgnoreCase("200") && !dataCollectionStatus.equalsIgnoreCase("\"SUCCESS\""));
        } catch (InterruptedException e) {

        }
        inventoryFileName = getInventoryFileName();
        System.out.println("Inventory File name ------> " + inventoryFileName);
        return inventoryFileName;
    }

    public String verifySimsMonitor(String inventoryFileName) {
        String simsURL = "http://v01app-stg021.cisco.com:8221/SimsMonitorCDX/monitor";
        String collUUID;
        WebElement completedInputField;
        int count = 0;

        getDriver().get(simsURL);
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Completed/Errored')]/input")));

        while (true) {
            try {
                if(count >= 100) {
                    Assert.fail("Unable to find file in sims url");
                }
                Thread.sleep(10000);
                completedInputField = getWebElement("//*[contains(text(),'Completed/Errored')]/input");
                completedInputField.clear();
                completedInputField.sendKeys(inventoryFileName);
                getWebElement("//table[@id='doneTable']//tr/td/a[@data-title='Detail'][contains(@data-content,'" + inventoryFileName + "')]").click();
                break;
            } catch (NoSuchElementException | InterruptedException e) {
                getDriver().navigate().refresh();
                count++;
                continue;
            }
        }
        collUUID = getWebElement("//div[@class='popover-content']//b[text()='Coll UUID']/../../td[2]").getText();
        System.out.println("Collection UUID ----> " + collUUID);
        return collUUID;
    }

    public void inputValue(String labelName, String value) {
        WebElement element = getWebElement("//div[text()='" + labelName + "']/../..//input");
        element.clear();
        element.sendKeys(value);
    }

    public String getServerResponseText() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Server response']/..//tr[@class='response']/td[contains(@class,'response-col_status')]")));
        return getWebElement("//h4[text()='Server response']/..//tr[@class='response']/td[contains(@class,'response-col_status')]").getText();
    }

    public String getDataCollectionStatus() {
        String statusXpath = "//h4[text()='Server response']/..//pre[@class=' microlight']//span[text()='\"status\"']/following-sibling::span[3]";
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(statusXpath)));
        return getWebElement(statusXpath).getText();
    }

    public void clickAPIMethod(String methodName) {
        String apiMethodXpath = "//span[@data-path='" + methodName + "']";
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(apiMethodXpath)));
        getWebElement(apiMethodXpath).click();
    }

    public String getInventoryFileName() {
        String fileNameXpath = "//h4[text()='Server response']/..//pre[@class=' microlight']//span[text()='\"inventoryFileName\"']/following-sibling::span[3]";
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(fileNameXpath)));
        return getWebElement(fileNameXpath).getText().replace("\"", "");
    }

    public boolean callElasticSearch(String collUUID) {
        JsonObject jsonObject = new JsonObject();
        String elasticHostUrl = System.getProperty("esHostURL");
        jsonObject.addProperty("query",
                "select alertsSummaryProcessingStatus, assetsSummaryProcessingStatus, inventory from pfm_inventory_notification " +
                        "where wfid.keyword='" + collUUID + "'");
        int count = 0;
        Response responseElasticSearch;
        responseElasticSearch = Commons.elasticSearchPost(elasticHostUrl, jsonObject.toString());
        System.out.println("Response as String ---> " + responseElasticSearch.asString());

        try {
            while (true) {
                responseElasticSearch = Commons.elasticSearchPost(elasticHostUrl, jsonObject.toString());
                if (responseElasticSearch.getStatusCode() != 200) {
                    Assert.fail("Elastic search not success 200");
                    System.out.println("Elastic Search Connection Status " + responseElasticSearch.getStatusCode());
                }
                if (count > 150) {
                    return false;
                }
                System.out.println("Response as String ---> " + responseElasticSearch.body().asString());
                if (responseElasticSearch.asString().contains("SEND_TO_DOWNSTREAM")
                        && responseElasticSearch.asString().contains("INVENTORY_DONE")) {
                    return true;
                }
                Thread.sleep(10000);
                count++;
                continue;
            }
        } catch (InterruptedException e) {
            return false;
        }

    }
}
