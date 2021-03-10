package com.cisco.tests.advisories.priorityBugs;

import static com.cisco.testdata.Data.ASSETS_AND_DIAGNOSTICS_ASSETS_DATA;

import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.advisories.priorityBugs.PriorityBugsPage;
import com.cisco.testdata.AssetsDataProvider;
import com.cisco.testdata.AssetsExcelDataReader;
import com.cisco.testdata.AssetsUIPojo;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.utils.Commons;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class PriorityBugsPageIT extends DriverBase {
  
    private AssetsUIPojo pbData=AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ADVISORIES_PB");

    @Test(description = "Validate Assets login", priority=1)
    public void validateAssetsLogin() {	
    	PriorityBugsPage pbPage = new PriorityBugsPage(); 
    	pbPage.login(pbData.getTrack());
    }
     
    @Test(description = "Select useCase for Advisories", priority=2)
    public void selectUsecase() {
    	PriorityBugsPage pbPage = new PriorityBugsPage(); 
    	pbPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus")
    	.selectContextSelector(CxHomePage.USE_CASE, pbData.getUseCase())
        .selectCarousel(CarouselName.ADVISORIES);
    }
    
   @Test(description = "Verify priority bugs count is > 0", priority=3)
    public void verifyAdvisoriesCount() {
	   PriorityBugsPage pbPage = new PriorityBugsPage(); 
	   assertTrue(pbPage.getPriorityBugsCount()>0, "No priority bugs are available"); 
    }
      
   @Test(groups= {"Severity 2"}, description = "Verify PB Column Count on List Grid", priority=4)
    public void verifyPBGridColumnCount() {
	   PriorityBugsPage pbPage = new PriorityBugsPage(); 
        assertEquals(pbPage.getPBColumnCount(), Integer.parseInt(pbData.getGridColCount()), "Security Advisories grid column count is invalid.");
    }
   
   @Test(groups= {"Severity 2"}, description = "Verify PB grid column names and their order", priority=5)
   public void validatePBGridColNamesAndOrder() {
	   PriorityBugsPage pbPage = new PriorityBugsPage(); 
       assertEquals(pbPage.getPBGridColNames(), Commons.parseStringtoList(pbData.getColumnsToSelect()), "Security Advisories grid column names / order is invalid.");
   }
   
    @Test(groups= {"Severity 2"}, description = "Verify PB360 is loaded", priority=6)
    public void validatePB360() {
	   PriorityBugsPage pbPage = new PriorityBugsPage(); 
        boolean pageLoaded=pbPage.openPB(pbData.getRowNum(),pbData.getColNum());
        assertTrue(pageLoaded, "360 page not loaded"); 
    }
    @Test(groups= {"Severity 2"}, description = "Verify PB360 is closed", priority=7)
    public void closePB360() {
    	PriorityBugsPage pbPage = new PriorityBugsPage(); 
    	pbPage.closePB360();
    }
    @Test(groups= {"Severity 2"}, description = "Verify total number of pages", priority=8)
    public void verifyTotalPages() {
	   PriorityBugsPage pbPage = new PriorityBugsPage(); 
	   assertTrue(pbPage.getPbPageCount()>0, "Pagination doesn't exist"); 
    }

    @Test(groups= {"Severity 2"}, description = "Verify Default Pagination", priority=9)
    public void verifyDefaultPagination() {
	   PriorityBugsPage pbPage = new PriorityBugsPage(); 
	   assertEquals(pbPage.validateDefaultPagination(), 1, "Default pagination should be 1");       
    }

    @Test(groups= {"Severity 2"}, description = "Validate Pagination", priority=10, dataProvider="genericValues", dataProviderClass=AssetsDataProvider.class)
    public void validatePagination(String value) {     
	   PriorityBugsPage pbPage = new PriorityBugsPage(); 
	   assertTrue(pbPage.validatePaginationValue(value), "Pagination for -> "+value+" failed"); 
    }
}
