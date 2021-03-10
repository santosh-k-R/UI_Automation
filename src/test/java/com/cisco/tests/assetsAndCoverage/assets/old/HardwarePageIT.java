package com.cisco.tests.assetsAndCoverage.assets.old;

import static com.cisco.testdata.Data.ASSETS_AND_DIAGNOSTICS_ASSETS_DATA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.assetsAndCoverage.assets.AssetsPage;
import com.cisco.testdata.AssetsExcelDataReader;
import com.cisco.testdata.AssetsUIPojo;
import com.cisco.testdata.StaticData.CarouselName;

public class HardwarePageIT extends DriverBase {
    private AssetsUIPojo hardwareData=AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ASSETS_HARDWARE");

    @Test(description = "Validate assets login", priority=1)
    public void validateAssetsLogin() {	
    	AssetsPage assetsPage = new AssetsPage(); 
    	assetsPage.login(hardwareData.getTrack());
    }
     
    @Test(description = "Select useCase", priority=2)
    public void selectUsecase() {
    	AssetsPage assetsPage = new AssetsPage(); 
    	assetsPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus")
    	.selectContextSelector(CxHomePage.USE_CASE, hardwareData.getUseCase())
        .selectCarousel(CarouselName.ASSETS_COVERAGE);
    }
    
   @Test(description = "Verify hardware count is > 0", priority=3)
    public void verifyHardwareCount() {
    	AssetsPage assetsPage = new AssetsPage(); 
        assertTrue(assetsPage.getHardwareCount()>0, "No hardwares are available"); 
    }
/*      
    @Test(groups= {"Severity 4"}, description = "Verify Column Count on List Grid", priority=4)
    public void verifyHardwareGridColumnCount() {
    	AssetsPage assetsPage = new AssetsPage(); 
        assertEquals(assetsPage.getHardwareColumnCount(), Integer.parseInt(hardwareData.getGridColCount()), "Hardware grid column count is invalid.");
    }

    @Test(groups= {"Severity 2"}, description = "Verify hardware360 is loaded", priority=5)
    public void validateHardware360() {
    	AssetsPage assetsPage = new AssetsPage(); 
        assetsPage.openHardware(hardwareData.getRowNum(),hardwareData.getColNum());
    }
*/
    @Test(groups= {"Severity 2"}, description = "Verify total number of pages", priority=7)
    public void verifyTotalPages() {
    	AssetsPage assetsPage = new AssetsPage(); 
        assetsPage.validatePagination();
    }
}
