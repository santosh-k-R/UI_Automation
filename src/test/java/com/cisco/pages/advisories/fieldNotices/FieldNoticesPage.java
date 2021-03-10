package com.cisco.pages.advisories.fieldNotices;

import java.util.List;

import com.cisco.utils.AppUtils;

import io.qameta.allure.Step;

public class FieldNoticesPage extends AppUtils {
	public static final String ADVISORIES_FILTER = "ADVISORIES";
	public static final String FIELD_NOTICE_TAB= "Field Notices";
	  
 	@Step("Get Field Notices count")
    public int getFieldNoticesCount() {
    //	logger.info("Field Notices count: "+getSystemCount());
	 return selectTab(FIELD_NOTICE_TAB).getLabelCount();
    }

    @Step("Get Field Notices grid column count")
    public int getFNColumnCount() {
        return selectTab(FIELD_NOTICE_TAB).getTableColumnCount();
    }
    
    @Step("Get Field Notices grid column names")
    public List<String> getFNGridColNames() {
        return selectTab(FIELD_NOTICE_TAB).getAllTableTitles();
    }    

    @Step("Verify on click of FN Title in fnView, 360 screen is loaded")
    public boolean openFN(String row, String column) {
	boolean pageLoaded = false;  String fn360HeaderTitle=null;
		if(getFNColumnCount()>0) {
	        selectTab(FIELD_NOTICE_TAB);
	        String fnID= getTableCellValue(Integer.parseInt(row),Integer.parseInt(column));
	        //logger.info("Validating for fn: "+fnID);
	        open360View(fnID); 		      
	        fn360HeaderTitle = getWebElement("//span[@data-auto-id='DetailsPanelTitle']").getText();
	    	if(fn360HeaderTitle.contains(fnID)) {
	    		pageLoaded=true;
	    	}
	     }  
	return pageLoaded;  
    }
    
    public void closeFN360() {
    	close360View();
    }
    
    @Step("Get total page count")
    public int getFNPageCount() {
    	return Integer.parseInt(selectTab(FIELD_NOTICE_TAB).getTotalCountOfPages());
    }
        
   @Step("Verify default pagination functionality")
    public int validateDefaultPagination(){
	   return getCurrentPageNumber();
   }
   
   @Step("Verify pagination functionality for {type}")
   public boolean validatePaginationValue(String type) {
	  int pageCount= getFNPageCount();	  
	  return validatePagination(type, pageCount);	   
   }
}
