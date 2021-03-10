package com.cisco.pages.controlPoint.assets;

import com.cisco.utils.ControlPointUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AssetGroupPage extends ControlPointUtils {

    @Step("check if AssetGroups tab is Displayed")
    public boolean checkIfAssetGroupsTabDisplayed(){
          return  isElementPresent("//span[contains(normalize-space(),'Asset Groups')]/./preceding-sibling::span//div[contains(@class,'icon-asset-group')]");
    }

    @Step("click AssetGroups tab is displayed")
    public AssetGroupPage clickAssetGroupsTab(){
         getJavascriptExecutor().executeScript("arguments[0].click();",getWebElement("//div[contains(@class,'icon-asset-group')]"));
         return this;
    }


    @Step("get AssetGroup page heading")
    public String getAssetGroupPageHeading(){
        return (isElementPresent("//*[contains(text(),'Asset Groups')]"))?getWebElement("//*[contains(text(),'Asset Groups')]").getText():"";
    }

    @Step("check if PlaceHolder is displayed in Assets tab searchbar")
    public String getPlaceHolderInSearchbar(){
        return (isElementPresent("//input[contains(@placeholder,'Search asset groups')]"))?getWebElement("//input[contains(@placeholder,'Search asset groups')]").getAttribute("placeholder"):"";
    }

    @Step("check if CreateAssertGroup link is displayed")
    public boolean checkCreateAssertGroupLink(){
        return isElementPresent("//a/span[contains(.,'Create Asset Group')]");
    }


    @Step("asset groups names naturally sorted")
    public boolean isNaturallySorted(){
        List<WebElement> assetGroups=getWebElements("//span[contains(@data-auto-id,'asset-group-name')]");
        List <String>justNames= new ArrayList();
        for(WebElement eachan: assetGroups){
            justNames.add(eachan.getText());
        }
        return getNaturalSortOrder(justNames).equals(justNames);
    }

    @Step("asset groups names reverse sorted")
    public boolean isReverselySorted(){
        getWebElement("//th[contains(@data-auto-id,'name-column-sort')]").click();
        List<WebElement> assetGroups=getWebElements("//span[contains(@data-auto-id,'asset-group-name')]");
        List <String>justNames= new ArrayList();
        for(WebElement eachElement: assetGroups){
            justNames.add(eachElement.getText());
        }
        return getReverseSortOrder(justNames).equals(justNames);
    }


    @Step("Check if X button is displayed when value entered in Asset Group's search bar")
    public boolean checkSearchBarWithCloseButton(){
        getWebElement("//input[contains(@placeholder,'Search asset groups')]").sendKeys("group");
        return isElementPresent("//button/span[contains(@class,'icon-close')]");
    }


    @Step("Check if pagination is working")
    public String checkIfPaginationWorking(){

        String status= (!isNextOrPreviousEnabled("next"))?"single page":"multiple page";
        if(status.equals("multiple page")){ navigateToPage(2);  }

     return status;
    }


    @Step("Check if search for asset groups is working")
    public boolean checkIfSearchWorking(){
        getWebElement("//input[contains(@placeholder,'Search asset groups')]").sendKeys("assets");
        List<WebElement> assetGroupsRecords=getWebElements("//tr[td[span[contains(@data-auto-id,'asset-group-name')]]]");

         return ((isElementPresent("//button/span[contains(@class,'icon-close')]")) && (assetGroupsRecords.size()>0)) ;
    }


    @Step("Check if Asset Group creation is working")
    public boolean checkCreateAssertGroupModalDisplayed(){

        if( isElementPresent("//a/span[contains(.,'Create Asset Group')]")){
            getWebElement("//a/span[contains(.,'Create Asset Group')]").click();
            return isElementPresent("#addAssetGroupModal");
        }else{
            return false;
        }
    }

    @Step("Click X button on Create Asset Group modal")
    public void closeCreateAssetGroupPopup(){
        getWebElement("//div/a/span[contains(@class,'icon-close')]").click();
    }

    @Step("Check if Asset Group Creation is working")
    public boolean createAssertGroup(){
        getWebElement("#input-groupName").sendKeys("AG_AllAssets_Test_UI_Automation");
        getWebElement("//th[span[contains(.,'Name')]]/preceding-sibling::th//span[contains(@class,'checkbox')]").click();
        if(isElementPresent("//button[contains(@data-auto-id,'save-btn')]")){
            getWebElement("//button[contains(@data-auto-id,'save-btn')]").click();
        }
        List<WebElement> assetGroups=getWebElements("//span[contains(@data-auto-id,'asset-group-name')]");
        List <String>justNmaes= new ArrayList();
        for(WebElement eachan: assetGroups){
            justNmaes.add(eachan.getText());
        }
        return justNmaes.contains("AG_AllAssets_Test_UI_Automation");

    }


    @Step("Check if deleting Asset Group is working")
    public boolean deleteAssertGroup() {
        boolean status=false;
        clickAssetGroupFor360View("AG_AllAssets_Test_UI_Automation");
        getWebElement("//cui-dropdown[contains(@icon,'icon-more')]").click();
        getWebElement("//span[contains(.,'Delete')]").click();
        try{
            if(isElementPresent("(//button[@data-auto-id='delete-modal-confirm'])[1]") ){
               getJavascriptExecutor().executeScript("let ele =document.querySelector(\"[data-auto-id='delete-modal-confirm']\");ele.click();");
               status=true;
            }
        }
        catch(NoSuchElementException e){   }
        return status;
    }


    @Step("Launch Asset Group 360 view")
    public void clickAssetGroupFor360View(String assetGroupName){
        if(assetGroupName!=""){ getWebElement("//input[contains(@placeholder,'Search asset groups')]").sendKeys(assetGroupName);}

        if (isElementPresent("//span[contains(@data-auto-id,'asset-group-name')]")) {
            getWebElements("//span[contains(@data-auto-id,'asset-group-name')]").get(0).click();
        }
    }

    @Step("Check for title on Create Asset Group popup")
    public boolean checkTitleOnCreateAssetGroupPopup(){
        return (isElementPresent("//h4[contains(.,'Create Asset Group')]"))?true:false;
    }

    @Step("Check for Asset Attribute Field on Create Asset Group popup")
    public boolean checkAssetAttributesFieldOnCreateAssetGroupPopup(){
        return ((isElementPresent("//button[contains(@data-auto-id,'dropdownSearchButton')]") && isElementPresent("//input[contains(@data-auto-id,'dropdownSearchInput')]")))?true:false;
    }

    @Step("Check for Group Name field on Create Asset Group popup")
    public boolean checkGroupNameFieldOnCreateAssetGroupPopup(){
        return (isElementPresent("//input[contains(@id,'input-groupName')]"))?true:false;
    }
}