package com.cisco.pages.assetsAndCoverage.contracts;

import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Contract360Page extends AppUtils {

    @Step("Get the Contract header value from the Contract 360 view")
    public String getContractNumber() {
        String assetHeaderXpath = "//div[@data-auto-id='Contract360SupportLevel']";
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(assetHeaderXpath)));
        return getWebElement(assetHeaderXpath).getText();
    }

}
