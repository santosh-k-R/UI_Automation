package com.cisco.pages.advisories.priorityBugs;

import java.util.List;

import com.cisco.utils.AppUtils;

import io.qameta.allure.Step;

public class PriorityBugsPage extends AppUtils {
	 public static final String ADVISORIES_FILTER = "ADVISORIES";
	 public static final String PRIORITY_BUGS_TAB= "Priority Bugs";
	  
 	@Step("Get Priority Bugs count")
    public int getPriorityBugsCount() {
    //	logger.info("pb count: "+getSystemCount());
	 return selectTab(PRIORITY_BUGS_TAB).getLabelCount();
    }

    @Step("Get Priority Bugs grid column count")
    public int getPBColumnCount() {
        return selectTab(PRIORITY_BUGS_TAB).getTableColumnCount();
    }
    
    @Step("Get Priority Bugs grid column names")
    public List<String> getPBGridColNames() {
        return selectTab(PRIORITY_BUGS_TAB).getAllTableTitles();
    }
    
    @Step("Verify on click of PB Title in pbView, 360 screen is loaded")
    public boolean openPB(String row, String column) {
    	boolean pageLoaded = false;  String pb360HeaderTitle=null;
		if(getPBColumnCount()>0) {
	        selectTab(PRIORITY_BUGS_TAB);
	        String pbBugID= getTableCellValue(Integer.parseInt(row),Integer.parseInt(column));
	        //logger.info("Validating for pb: "+pbTilte);
	        open360View(pbBugID); 		      
	        pb360HeaderTitle = getWebElement("//span[@data-auto-id='DetailsPanelTitle']").getText();
        	if(pb360HeaderTitle.contains(pbBugID)) {
        		pageLoaded=true;
        	}
	     }  
	return pageLoaded;  
    }
     
    public void closePB360() {
    	close360View();
    }
    
    @Step("Get total page count")
    public int getPbPageCount() {
    	return Integer.parseInt(selectTab(PRIORITY_BUGS_TAB).getTotalCountOfPages());
    }
    
    
   @Step("Verify default pagination functionality")
    public int validateDefaultPagination(){
	   return getCurrentPageNumber();
   }
    
   @Step("Verify pagination functionality for {type}")
   public boolean validatePaginationValue(String type) {
	  int pageCount= getPbPageCount();	  
	  return validatePagination(type, pageCount);	   
   }
	    
}
