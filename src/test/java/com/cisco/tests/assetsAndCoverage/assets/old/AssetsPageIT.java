package com.cisco.tests.assetsAndCoverage.assets.old;

import static com.cisco.testdata.Data.ASSETS_AND_DIAGNOSTICS_ASSETS_DATA;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.assetsAndCoverage.assets.AssetsPage;
import com.cisco.testdata.AssetsExcelDataReader;
import com.cisco.testdata.AssetsUIPojo;
import com.cisco.testdata.StaticData.CarouselName;

public class AssetsPageIT extends DriverBase {  
    private AssetsUIPojo systemData=AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ASSETS_SYSTEM");
    
    @Test(description = "Validate assets login", priority=1)
    public void validateAssetsLogin() {	
    	AssetsPage assetsPage = new AssetsPage(); 
    	assetsPage.login(systemData.getTrack());
    }
     
    @Test(description = "Select useCase", priority=2)
    public void selectUsecase() {
    	AssetsPage assetsPage = new AssetsPage(); 
    	assetsPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus")
    	.selectContextSelector(CxHomePage.USE_CASE, systemData.getUseCase())
        .selectCarousel(CarouselName.ASSETS_COVERAGE);
    }
      
    @Test(description = "Verify system count is > 0", priority=3)
    public void verifySystemCount() {
    	AssetsPage assetsPage = new AssetsPage();
    	assertTrue(assetsPage.getSystemCount()>0, "No systems are available"); 
    }
   
    @Test(groups= {"Severity 4"}, description = "Verify Column Count on List Grid", priority=4)
    public void verifySystemGridColumnCount() {
    	AssetsPage assetsPage = new AssetsPage(); 
        assertEquals(assetsPage.getSystemColumnCount(), Integer.parseInt(systemData.getGridColCount()), "System grid column count is invalid.");
    }
    
    @Test(groups= {"Severity 2"}, description = "Verify system360 is loaded", priority=5)
    public void validateSystem360() {
    	AssetsPage assetsPage = new AssetsPage(); 
        assetsPage.openSystem(systemData.getRowNum(),systemData.getColNum());
    }
    
/*    @Test(groups= {"Severity 2"}, description = "Verify total number of pages", priority=7)
    public void verifyTotalPages() {
    	AssetsPage assetsPage = new AssetsPage(); 
        assetsPage.validatePagination();
    }
    */
}
