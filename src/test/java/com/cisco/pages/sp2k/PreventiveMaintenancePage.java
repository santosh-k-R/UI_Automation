package com.cisco.pages.sp2k;

import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PreventiveMaintenancePage extends AppUtils {
    @Step("Verify home Page")
    public String homePageTitle(){
        String homePageTitle="//td[contains(text(),'M & W Data Entry')]";
        return getWebElement(homePageTitle).getText();
    }
    @Step("verify Main Menu Pages")
    public ArrayList mainMenuPagesList() {
        ArrayList pageNames= new ArrayList();
        //String preventiveMaintenance = "//span[normalize-space()='Preventive Maintenance']";
        //List<WebElement> pages=getWebElements("//tbody/tr");
        for(int i=2;i<=7;i++){
            pageNames.add(getWebElement("//tbody/tr["+i+"]/td[2]").getText());
        }
        return pageNames;
        //return isElementPresent(preventiveMaintenance);
    }
    @Step("verify and Click on Preventive Maintenance Page")
    public String  clickPreventiveMaintenanceLink(){
        String preventiveManintenancepage="//span[normalize-space()='Preventive Maintenance']";
        getWebElement(preventiveManintenancepage).click();
        return getWebElement("//font[normalize-space()='Preventive Maintenance']").getText();
    }
    @Step("Click Logout Link")
    public void logout() {
        String logoutLink = "//span[@id='form1:textLogout']";
        getWebElement(logoutLink).click();
    }
}
