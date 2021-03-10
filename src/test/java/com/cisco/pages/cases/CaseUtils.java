package com.cisco.pages.cases;

import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CaseUtils extends AppUtils {

    @Step("Verify if the Asset Hyperlink is present for the Case Search results and click on it")
    public boolean clickAssetHyperlink() {
        try {
            String deviceLinkXpath = "//a[@data-auto-id='DeviceLink']";
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(deviceLinkXpath)));
            getWebElement(deviceLinkXpath).click();
            return true;
        } catch (NoSuchElementException | ScriptTimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }
}
