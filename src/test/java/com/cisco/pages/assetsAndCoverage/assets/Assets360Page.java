package com.cisco.pages.assetsAndCoverage.assets;

import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Assets360Page extends AppUtils {

    @Step("Get the Asset header value from the Asset 360 view")
    public String getAssetHeader() {
        String assetHeaderXpath = "//div[contains(@class,'not-close-360')]//div[@class='text-xlarge row']//div[2]//span";
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(assetHeaderXpath)));
        return getWebElement(assetHeaderXpath).getText();
    }

    @Step("Click the Open Case button in Hardware tab of Asset 360 page")
    public void clickOpenCaseButton() {
        String openCaseButtonXpath = "//button[@data-auto-id='Hardware360OpenCaseBtn']";
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(openCaseButtonXpath)));
        getWebElement(openCaseButtonXpath).click();
    }

}
