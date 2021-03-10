package com.cisco.tests.advisories.securityAdvisories;

import static com.cisco.testdata.Data.ASSETS_AND_DIAGNOSTICS_ASSETS_DATA;
import com.cisco.base.DriverBase;
import com.cisco.pages.CxHomePage;
import com.cisco.pages.advisories.securityAdvisories.SecurityAdvisoriesPage;
import com.cisco.testdata.AssetsDataProvider;
import com.cisco.testdata.AssetsExcelDataReader;
import com.cisco.testdata.AssetsUIPojo;
import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.utils.Commons;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class SecurityAdvisoriesPageIT extends DriverBase {
  
    private AssetsUIPojo saData=AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ADVISORIES_SA");

    @Test(description = "Validate Assets login", priority=1)
    public void validateAssetsLogin() {	
    	SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
    	saPage.login(saData.getTrack());
    }
     
    @Test(description = "Select useCase for Advisories", priority=2)
    public void selectUsecase() {
    	SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
    	saPage.selectContextSelector(CxHomePage.SUCCESS_TRACK, "Campus")
    	.selectContextSelector(CxHomePage.USE_CASE, saData.getUseCase())
        .selectCarousel(CarouselName.ADVISORIES);
    }
    
   @Test(description = "Verify advisories count is > 0", priority=3)
    public void verifyAdvisoriesCount() {
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
	   assertTrue(saPage.getAdvisoriesCount()>0, "No advisories are available"); 
    }
      
   @Test(groups= {"Severity 2"}, description = "Verify SA Column Count on List Grid", priority=4)
    public void verifySAGridColumnCount() {
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
        assertEquals(saPage.getSAColumnCount(), Integer.parseInt(saData.getGridColCount()), "Security Advisories grid column count is invalid.");
    }
   
   @Test(groups= {"Severity 2"}, description = "Verify SA grid column names and their order", priority=5)
   public void validateSAGridColNamesAndOrder() {
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
       assertEquals(saPage.getSAGridColNames(), Commons.parseStringtoList(saData.getColumnsToSelect()), "Security Advisories grid column names / order is invalid.");
   }
   
    @Test(groups= {"Severity 2"}, description = "Verify SA360 is loaded", priority=6)
    public void validateSA360() {
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
       boolean pageLoaded= saPage.openAdvisory(saData.getRowNum(),saData.getColNum());
        assertTrue(pageLoaded, "360 Page is not loaded successfully"); 
    }

    @Test(groups= {"Severity 2"}, description = "Verify SA360 is closed", priority=7)
    public void closeSA360() {
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
       saPage.closeAdvisory360();
    }
    @Test(groups= {"Severity 2"}, description = "Verify total number of pages", priority=8)
    public void verifyTotalPages() {
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
	   assertTrue(saPage.getAdvPageCount()>0, "Pagination doesn't exist"); 
    }

    @Test(groups= {"Severity 2"}, description = "Verify Default Pagination", priority=9)
    public void verifyDefaultPagination() {
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
	   assertEquals(saPage.validateDefaultPagination(), 1, "Default pagination should be 1");       
    }

    @Test(groups= {"Severity 2"}, description = "Validate Pagination", priority=10, dataProvider="genericValues", dataProviderClass=AssetsDataProvider.class)
    public void validatePagination(String value) {     
	   SecurityAdvisoriesPage saPage = new SecurityAdvisoriesPage(); 
	   assertTrue(saPage.validatePaginationValue(value), "Pagination for -> "+value+" failed"); 
    }
    
}
