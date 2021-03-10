package com.cisco.pages.advisories.securityAdvisories;

import java.util.List;

import com.cisco.utils.AppUtils;

import io.qameta.allure.Step;

public class SecurityAdvisoriesPage extends AppUtils {
	public static final String ADVISORIES_FILTER = "ADVISORIES";
	public static final String ADVISORIES_TAB= "Security Advisories";
  
 	@Step("Get Advisories count")
    public int getAdvisoriesCount() {
    //	logger.info("Advisory count: "+getSystemCount());
	 return selectTab(ADVISORIES_TAB).getLabelCount();
    }

    @Step("Get Advisories grid column count")
    public int getSAColumnCount() {
        return selectTab(ADVISORIES_TAB).getTableColumnCount();
    }
    
    @Step("Get Advisories grid column names")
    public List<String> getSAGridColNames() {
        return selectTab(ADVISORIES_TAB).getAllTableTitles();
    }
    
    @Step("Verify on click of SA Title in AdvisoryView, 360 screen is loaded")
    public boolean openAdvisory(String row, String column) {
    String advTitle=null, advisoryTitle=null; boolean pageLoaded=false;
      if(getSAColumnCount()>0) {
        selectTab(ADVISORIES_TAB);
        advisoryTitle = getTableCellValue(Integer.parseInt(row),Integer.parseInt(column));
        open360View(advisoryTitle); 
        advTitle=getWebElement("//div[@data-auto-id='SecurityDetailsTitleText']").getText();
        	if(advisoryTitle.equals(advTitle)) {
        		pageLoaded=true;
        	}
	     }  
      return pageLoaded;
    }
    
    public void closeAdvisory360() {
    	close360View();
    }
    
    @Step("Get total page count")
    public int getAdvPageCount() {
    	return Integer.parseInt(selectTab(ADVISORIES_TAB).getTotalCountOfPages());
    }
       
   @Step("Verify default pagination functionality")
    public int validateDefaultPagination(){
	   return getCurrentPageNumber();
   }
    
   @Step("Verify pagination functionality for {type}")
   public boolean validatePaginationValue(String type) {
	  int pageCount= getAdvPageCount();	  
	  return validatePagination(type, pageCount);	   
   }
	}
