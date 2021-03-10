package com.cisco.pages.assetsAndCoverage.assets;

import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.ChartType;
import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import java.util.*;

public class AllAssetsPage extends AppUtils {
    public static final String ASSETS_TAB = "Assets";
    public static final String CONTRACTS_TAB="Contracts";
    public static final String ASSET_COUNT = "//div[@data-auto-id='TotalVisualFilter']";
    public static final String COLUMN_SELECTOR ="//div[@class='column-selector-component']";

    @Step("Click on Assets Tab")
    public String getAssetsTitle() {
        selectCarousel(CarouselName.ASSETS_COVERAGE);
        return CarouselName.ASSETS_COVERAGE.getCarouselName();
    }

    @Step("Click on Assets Tab")
    public String clickOnAssets() {
       selectCarousel(CarouselName.ASSETS_COVERAGE);
        System.out.println("enabled: " +getWebElement(ASSETS_TAB).isEnabled());
       getWebElement(ASSETS_TAB).click();
       System.out.println("assets text: " +getWebElement(ASSETS_TAB).getText());
       return getWebElement(ASSETS_TAB).getText();
    }
    
    @Step("verify existing of Contracts Tab")
    public String verifyContractsTab() {
        System.out.println("enabled: " +getWebElement(CONTRACTS_TAB).isEnabled());
        System.out.println("contracts text: " +getWebElement(CONTRACTS_TAB).getText());
        return getWebElement(CONTRACTS_TAB).getText();
    }

    @Step("Get visual filters title")
    public String getVisualFilterTitle(String filterName) {
         return getWebElement("//div[text()='"+filterName+"']").getText();
    }

    @Step("Click on visual filters")
    public String selectVisualFilter(List<String> filters, String filterLabel) {
        FilterValue value=new FilterValue(filters.get(0), ChartType.valueOf(filters.get(1)), filters.get(2));
        filterByVisualFilters(value);
        return getWebElement("//span[text()='"+filterLabel+"']").getText();
    }

    @Step("Get Assets count")
    public int getAssetCount() {
        System.out.println("System count: " +getWebElement(ASSET_COUNT).getText());
        return Integer.parseInt(getWebElement(ASSET_COUNT).getText());
    }

    @Step("Set columns to Map")
    public Map<String, String> setColumns(List<String> keys) {
        Map columns= new HashMap<String, String>();
        for(String key : keys) {
            columns.put(key, "//label[text()=' " + key + " ']");
        }
        return columns;
    }

    @Step("Select columns in column selector")
    public void selectColumns(Map<String, String> keys) {
        getWebElement(COLUMN_SELECTOR).click();
        for(String key : keys.keySet()) {
            System.out.println("key is : "+key+ " | value is: "+keys.get(key));
            if (!getWebElement(keys.get(key)).isSelected() && getWebElements(keys.get(key)).size()>1) {
                hoverOveraWebElement("(" + keys.get(key) + ")[2]");
                getWebElement("(" + keys.get(key) + ")[2]").click();
            }
            else{
             System.out.println(key+ " is already selected");
            }
        }
    }

    @Step("Select columns in column selector")
    public void deSelectColumns(Map<String, String> keys) {
        getWebElement(COLUMN_SELECTOR).click();
        for(String key : keys.keySet()) {
            System.out.println("key is : "+key+ " | value is: "+keys.get(key));
            if (getWebElement(keys.get(key)).isSelected() && !(getWebElements(keys.get(key)).size()>1)) {
                hoverOveraWebElement(keys.get(key));
                getWebElement(keys.get(key)).click();
            }
            else{
                System.out.println(key+ " is yet to be selected");
            }
        }
    }
}
