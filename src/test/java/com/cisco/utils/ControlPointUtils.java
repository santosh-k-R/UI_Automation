package com.cisco.utils;

import io.qameta.allure.Step;

public class ControlPointUtils extends AppUtils {

    @Step("click AdminSettings icon is displayed")
    public ControlPointUtils clickAdminSettingsIcon(){
        getWebElement("//a[@data-auto-id='Admin']").click();
        return this;
    }

    @Step("Check if X button is present on admin screen")
    public boolean checkIfCloseButtonPresent(){
        return isElementPresent("//span[contains(@data-auto-id,'xbutton')]");
    }

    @Step("Check if pagination is displayed")
    public boolean checkIfpaginationIsDisplayed(){
        return isElementPresent("//ul[contains(@class,'pagination')]");
    }

    @Step("Skip the info popups on admin page")
    public void skipInfoPopups(){
        while(isElementPresent("//div[contains(@class,'popup--')]")){
            getWebElement("//div[contains(@class,'popup--')]//button[position()=1]").click();
        }
    }

    @Step("Close the admin screen")
    public ControlPointUtils clickCloseButton(){
        waitForInvisibilityOfElement("//div[contains(@class,'modal__dialog')]");
        getWebElement("//span[contains(@data-auto-id,'xbutton')]").click();
        return this;
    }

}