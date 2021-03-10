package com.cisco.pages.assetsAndCoverage.assets;

import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

public class AssetsPage extends AppUtils {

    //private static final Logger logger = Logger.getLogger(AssetsPage.class);
    public static final String ADVISORIES_FILTER = "ADVISORIES";
    public static final String ROLE_FILTER = "ROLE";
    public static final String PRODUCT_TYPE_FILTER = "PRODUCT TYPE";
    public static final String CORE = "Core";
    public static final String BORDER_ROUTER = "Border Router";
    public static final String ACCESS = "Access";
    public static final String ASSETS_TAB = "Assets";
    public static final String SYSTEMS_SUBTAB = "Systems";
    public static final String HARDWARE_TAB = "HARDWARE";
    public static final String RUN_DIAGNOSTIC_SCAN_OPTION = "Run Diagnostic Scan";
    public static final String OPEN_SUPPORT_CASE_OPTION = "Open Support Case";

    @Step("Search Asset from hardware view via serial number")
    public void searchAssetViaSerialNumber(String serialNumber) {
        selectCarousel(CarouselName.ASSETS_COVERAGE);
        searchTrackItems(serialNumber + Keys.ENTER);
    }

    @Step("Open hardware from serial number")
    public void searchOpenAssetViaSerialNumber(String serialNumber) {
        searchAssetViaSerialNumber(serialNumber);
        open360View(serialNumber);
    }

    @Step("Get system count")
    public int getSystemCount() {
        //	logger.info("System count: "+getSystemCount());
        return Integer.parseInt(getCountOnSubTab(ASSETS_TAB, SYSTEMS_SUBTAB));
    }

    @Step("Get hardware count")
    public int getHardwareCount() {
        //	logger.info("Hardware count: "+getSystemCount());
        return Integer.parseInt(getCountOnSubTab(ASSETS_TAB, HARDWARE_TAB));
    }

    @Step("Get system grid column count")
    public int getSystemColumnCount() {
        clickSubTab(ASSETS_TAB, SYSTEMS_SUBTAB);
        return getTableColumnCount();
    }

    @Step("Get hardware grid column count")
    public int getHardwareColumnCount() {
        clickSubTab(ASSETS_TAB, HARDWARE_TAB);
        return getTableColumnCount();
    }

    @Step("Verify on click of systemName in SystemView, 360 screen is loaded")
    public void openSystem(String row, String column) {
        String systemName = null;
        if(getSystemCount()>0) {
            clickSubTab(ASSETS_TAB, SYSTEMS_SUBTAB);
            systemName= getTableCellValue(Integer.parseInt(row),Integer.parseInt(column));
            //logger.info("Validating for System: "+systemName);
            open360View(systemName);
            close360View();
        }
    }

    @Step("Verify on click of systemName in HardwareView, 360 screen is loaded")
    public void openHardware(String row, String column) {
        String systemName = null;
        if(getHardwareCount()>0) {
            clickSubTab(ASSETS_TAB, HARDWARE_TAB);
            systemName= getTableCellValue(Integer.parseInt(row),Integer.parseInt(column));
            //logger.info("Validating for Hardware: "+systemName);
            open360View(systemName);
            close360View();
        }
    }

    @Step("Verify pagination functionality")
    public void validatePagination() {
        System.out.println(clickSubTab(ASSETS_TAB, HARDWARE_TAB).getTotalCountOfPages());
    }

}
