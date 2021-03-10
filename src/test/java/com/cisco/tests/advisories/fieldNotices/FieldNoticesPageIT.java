package com.cisco.tests.advisories.fieldNotices;

import static com.cisco.testdata.Data.ASSETS_AND_DIAGNOSTICS_ASSETS_DATA;
import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.advisories.fieldNotices.FieldNoticesPage;
import com.cisco.testdata.AssetsDataProvider;
import com.cisco.testdata.AssetsExcelDataReader;
import com.cisco.testdata.AssetsUIPojo;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.utils.Commons;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class FieldNoticesPageIT extends DriverBase {
  
    private AssetsUIPojo fnData=AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ADVISORIES_FN");

    @Test(description = "Validate Assets login", priority=1)
    public void validateAssetsLogin() {	
    	FieldNoticesPage fnPage = new FieldNoticesPage(); 
    	fnPage.login(fnData.getTrack());
    }
     
    @Test(description = "Select useCase for FNisories", priority=2)
    public void selectUsecase() {
    	FieldNoticesPage fnPage = new FieldNoticesPage(); 
    	fnPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus")
    	.selectContextSelector(CxHomePage.USE_CASE, fnData.getUseCase())
        .selectCarousel(CarouselName.ADVISORIES);
    }
    
   @Test(description = "Verify field notices count is > 0", priority=3)
    public void verifyFNCount() {
	   FieldNoticesPage fnPage = new FieldNoticesPage(); 
	   assertTrue(fnPage.getFieldNoticesCount()>0, "No field notices are available"); 
    }
      
   @Test(groups= {"Severity 2"}, description = "Verify FN Column Count on List Grid", priority=4)
    public void verifyFNGridColumnCount() {
	   FieldNoticesPage fnPage = new FieldNoticesPage(); 
        assertEquals(fnPage.getFNColumnCount(), Integer.parseInt(fnData.getGridColCount()), "Security FN grid column count is invalid.");
    }
   
   @Test(groups= {"Severity 2"}, description = "Verify FN grid column names and their order", priority=5)
   public void validateFNGridColNamesAndOrder() {
	   FieldNoticesPage fnPage = new FieldNoticesPage(); 
       assertEquals(fnPage.getFNGridColNames(), Commons.parseStringtoList(fnData.getColumnsToSelect()), "Security FNisories grid column names / order is invalid.");
   }
   
    @Test(groups= {"Severity 2"}, description = "Verify FN360 is loaded", priority=6)
    public void validateFN360() {
	   FieldNoticesPage fnPage = new FieldNoticesPage(); 
       fnPage.openFN(fnData.getRowNum(),fnData.getColNum());
    }
    
    @Test(groups= {"Severity 2"}, description = "Verify FN360 is closed", priority=7)
    public void closeFN360() {
    	FieldNoticesPage fnPage = new FieldNoticesPage(); 
    	fnPage.closeFN360();
    }
    
    @Test(groups= {"Severity 2"}, description = "Verify total number of pages", priority=7)
    public void verifyTotalPages() {
	   FieldNoticesPage fnPage = new FieldNoticesPage(); 
	   assertTrue(fnPage.getFNPageCount()>0, "Pagination doesn't exist"); 
    }

    @Test(groups= {"Severity 2"}, description = "Verify Default Pagination", priority=8)
    public void verifyDefaultPagination() {
	   FieldNoticesPage fnPage = new FieldNoticesPage(); 
	   assertEquals(fnPage.validateDefaultPagination(), 1, "Default pagination should be 1");       
    }

    @Test(groups= {"Severity 2"}, description = "Validate Pagination", priority=9, dataProvider="genericValues", dataProviderClass=AssetsDataProvider.class)
    public void validatePagination(String value) {     
	   FieldNoticesPage fnPage = new FieldNoticesPage(); 
	   assertTrue(fnPage.validatePaginationValue(value), "Pagination for -> "+value+" failed"); 
    }
    
}
