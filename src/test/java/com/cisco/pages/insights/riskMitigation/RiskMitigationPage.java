package com.cisco.pages.insights.riskMitigation;

import com.cisco.testdata.StaticData.ChartType;
import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.utils.AppUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.cisco.testdata.StaticData.ChartType.PIE;
import static com.cisco.testdata.StaticData.CarouselName.INSIGHTS;

public class RiskMitigationPage extends AppUtils {

	public static final String FILTER_TYPE = "RISK";
	public static final String RISK_HIGH = "High";
	public static final String RISK_MEDIUM = "Medium";
	public static final String RISK_LOW = "Low";
	public static final String TIME_RANGE_FILTER_TYPE = "TIME RANGE";
	public static String RISKVALUE;
	public static String SYSTEMNAME;
	public static String SYSTEMNAMEFROMFP;
	public static String RISKFROMFP;
	public static String INSIGHTS_BUTTON;
	public String compareDeviceRisk;
	public String assets360DeviceName;
	public String crashRiskAssetName;
	public String fingerprintSimilarAssets;
	public String softwareHighAssetName;
	public String softwareMediumAssetName;
	public String fpHighAssetName;
	public String fpMediumAssetName;
	public String fingerPrintSimilarAsset;

	@Step("Verify home Page")
	public String homePageTitle(){
		String homePageTitle="//td[contains(text(),'M & W Data Entry')]";
		return getWebElement(homePageTitle).getText();
	}
	@Step("Preventive Maintenance Page")
	public boolean PreventiveMaintenanceLink() {
		String preventiveMaintenance = "//span[normalize-space()='Preventive Maintenance']";
		return isElementPresent(preventiveMaintenance);
	}

	@Step("Click Logout Link")
	public void logout() {
		String logoutLink = "//span[@id='form1:textLogout']";
		getWebElement(logoutLink).click();
	}






	@Step("My Portfolio Tab")
	public boolean myPortfolioTab() {
		String myPortfolio = "//span[text()='My Portfolio']";
		return isElementPresent(myPortfolio);
	}

	@Step("Click on Insights Tab")
	public void clickOnInsights() {
		selectCarousel(INSIGHTS);
	}

	@Step("Verify the default Software Tab selected or not")
	public boolean isSoftwareTabSelected(String defaultTab) {
		String defaultTabInInsights = "//div[@class='tab__heading']//span[contains(text(),'Software')]";
		String actualTab = getWebElement(defaultTabInInsights).getText();
		return actualTab.contains(defaultTab) ? true : false;
	}

	@Step("Verify Navigation to Risk Mitigation Tab")
	public boolean isRiskMitigationTabEnabled(String navigateTo) {
		selectTab(navigateTo);
		return true;
	}

	@Step("Verify Assets with Crash Risk Tab")
	public boolean isAssetsWithCrashRiskDisplayed() {
		return isElementPresent(
				"//div[@class='base-margin-bottom base-margin-top cursor row flex-center filter__selected']//div[2]");
	}

	@Step("Verify Assets with Crash Risk Tab")
	public boolean isCrashedAssetsDisplayed() {
		getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(getWebElement("//tbody")));
		return isElementPresent("//div[@class='half-margin-bottom qtr-margin-top cursor row flex-center']//div[2]");
	}

	@Step("Verify Systems Count from Risk Mitigation Dashboard")
	public int systemsScoreCardCount() {
		String systems = "//div[@class='base-margin-bottom base-margin-top cursor row flex-center filter__selected']//div[1]";
		String systemCountValue = getWebElement(systems).getText();
		int count = Integer.valueOf(systemCountValue);
		return count;
	}

	@Step("Verifying the Pagination Count")
	public int pagiNationCount() {
		String pagiNation = "//a[contains(@data-auto-id,'CUIPager-Page')]/span";
		List<WebElement> pages = getWebElements(pagiNation);
		int countOfPages = pages.size();
		return Integer.valueOf(pages.get(countOfPages - 1).getText());
	}

	/**
	 * <p>
	 * Getting Score Card Count for Crashed System of Risk Mitigation Page
	 * </p>
	 * 
	 * @return
	 * @author yseeniv
	 */

	@Step("Verify Crashed System Count from Risk Mitigation Dashboard")
	public void crashedSystemCount() {
		String crashedSystem = "//span[contains(text(),'Crashed Assets')]";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='visual-filter__card__title text-primary']")));
		getWebElement(crashedSystem).click();
	}

	@Step("Verify & Getting Fingerprint Details Header System Name")
	public String fingerprintDetails() {
		return getWebElement("//a[@data-auto-id='selectedName']").getText();
	}

	@Step("Verify Risk Filter Applied")
	public void riskFilter(String filter) {
		FilterValue filterValue = new FilterValue(FILTER_TYPE, PIE, filter);
		filterByVisualFilters(filterValue);
		
	}

	@Step("Verify Time Range Filter Applied")
	public void timeRangeFilter(String filter) {
		FilterValue filterValue = new FilterValue(TIME_RANGE_FILTER_TYPE, ChartType.COLUMN, filter);
		filterByVisualFilters(filterValue);
	}

	@Step("Fingerprint Details page shows SIMILAR SYSTEMS as default screen")
	public String similerAssetsTab() {
		return getWebElement("//div[@class='text-large text-dkgray-4']").getText();
	}

	@Step("Verifying the Similarity Criteria for High")
	public boolean getSimilarDevicesForHigh(String filterValue ) {
		WebElement toolTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
		String filterCount = toolTipValueElement.getText();
		int count = Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
		if (count > 0) {
			fpHighAssetName = getTableCellValue(3, 1);
			open360View(fpHighAssetName);
			softwareHighAssetName = fingerprintDetails();
			if (isElementPresent("//span[@class='text-center dbl-padding-top']")) {
				System.out.println("There are no similar assets with a lower risk in your environment to provide a comparison.");
				close360View();
				return true;

			} else if (isElementPresent("//a[text()='compare']")) {
				getWebElement("//a[text()='compare']").click();
				softwareHighAssetName = getWebElement("//div[@class='text-left']//div[1]//div[1]//div[1]//a").getText();
				getWebElement("//div[@class='flex-fill btn-group btn-group--wide']//button[2]").click();
				compareDeviceRiskColumn();
				close360View();
				return true;

			}
		}
		else {
			System.out.println("No Data Found ");
		}

		return false;

	}
	@Step("Verifying the Similarity Criteria for Medium")
	public boolean getSimilarDevicesForMedium(String filterValue ) {
		WebElement toolTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
		String filterCount = toolTipValueElement.getText();
		int count = Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
		if (count > 0) {
			fpMediumAssetName = getTableCellValue(6, 1);
			open360View(getTableCellValue(6, 2));
			softwareMediumAssetName = fingerprintDetails();
			if (isElementPresent("//span[@class='text-center dbl-padding-top']")) {
				System.out.println("There are no similar assets with a lower risk in your environment to provide a comparison.");
				close360View();
				return true;

			} else if (isElementPresent("//a[text()='compare']")) {
				getWebElement("//a[text()='compare']").click();
				softwareMediumAssetName = getWebElement("//div[@class='text-left']//div[1]//div[1]//div[1]//a").getText();
				getWebElement("//div[@class='flex-fill btn-group btn-group--wide']//button[2]").click();
				close360View();
				return true;

			}
		}
		else {
			System.out.println("No Data Found ");
			return false;
		}
		return false;

	}

	@Step("Compare Devices Risk Column for Medium")
	public boolean compareDeviceRiskColumn() {
		return isElementPresent("//p[@class='text-left']//span[contains(text(),'Low')]");
	}

	@Step("Get filter value")
	public String getFilterValue(String filterValue) {
		return getWebElement("//span[@class='label filter-bar__item']//span[contains(text(),'" + filterValue + "')]")
				.getText();
	}

	@Step("Get page iteration Message")
	public String pageIterationMessage() {
		return getWebElement("//span[@class='base-margin-left text-small text-muted']").getText();
	}

	@Step("Verifying default filter value")
	public String crashedSystemsDefaultFilterValue() {
		return getWebElement("//span[contains(text(),'24h')]").getText();
	}

	@Step("Verifying default filter value")
	public String defaultFilterValue() {
		return getWebElement("//span[contains(text(),'24h')]").getText();
	}

	@Step("No Results Found Message")
	public boolean noResultsFoundMessage() {
		return isElementPresent("//p[@class='text-muted text-xlarge err-msg']");
	}

	@Step("Get Risk Tooltip Value")
	public int getRiskFilterToolTipValue(String filterValue) {
		WebElement tootlTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
		String filterCount = tootlTipValueElement.getText();
		return Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
	}

	@Step("To get Tool Tip value from Column Chart  Bar")
	public int getToolTipValue(String filterValue) {
		String tootlTipValueElement = getWebElement("//div[text()='" + TIME_RANGE_FILTER_TYPE
				+ "']/../..//column-chart//div[@data-auto-id='" + filterValue + "Tooltip']//b").getText();
		System.out.println("Time Range Filter ToolTip Value is "+Integer.parseInt(tootlTipValueElement));
		return Integer.parseInt(tootlTipValueElement);
	}

	@Step("Navigate to Crash History Page")
	public void openCrashHistoryPage(int rowIndex, int columnIndex) {
		WebElement rowValue = getWebElement("//tbody/tr[" + rowIndex + "]/td[" + columnIndex + "]");
		getWebDriverWait().until(ExpectedConditions.visibilityOf(getWebElement("//div[@class='responsive-table']")));
		rowValue.click();
	}

	@Step("Toggle Full Screen")
	public void toggleFullScreen() {
		String fullScreen = "//span[@data-auto-id='asset-details-toggle-fullscreen-icon']";
		getWebElement(fullScreen).click();
	}

	@Step("Get System name from Crash History page")
	public String getCrashHistorySystemName() {
		return getWebElement("//a[@data-auto-id='selectedName']").getText();
	}

	@Step("Get all label names in Crash History page Header")
	public List<String> getAllLabelNamesCrashHistoryPage() {
		List<String> labelNames = new ArrayList<String>();

		labelNames.add(getWebElement("//div[contains(text(),'IP Address')]").getText());
		labelNames.add(getWebElement("//div[contains(text(),'SN')]").getText());
		labelNames.add(getWebElement("//div[contains(text(),'Crashes')]").getText());
		labelNames
				.add(getWebElement("//*[contains(@class,'details-panel-container')]//div/table//tr/th/span").getText());
		labelNames.add(
				getWebElement("//*[contains(@class,'details-panel-container')]//div/table//tr/th[2]/span").getText());
		for (String labelName : labelNames)
			System.out.println("Label Name is: " + labelName);
		return labelNames;
	}

	@Step("Get total numbers of rows visible in Crashed History table")
	public int getCrashedHistoryTableRowCount() {
		List<WebElement> numberOfRow = getWebElements(
				"//div[@class='details-panel-container']//div[@class='responsive-table']//tbody/tr");
		getWebDriverWait().until(ExpectedConditions.visibilityOf(
				getWebElement("//div[@class='details-panel-container']//div[@class='responsive-table']")));
		return numberOfRow.size();
	}

	@Step("Get total count of coulumns in Crashed History table")
	public int getCrahedHistoryTableColumnCount() {
		List<WebElement> numberOfcolumn = getWebElements(
				"//div[@class='details-panel-container']//div[@class='responsive-table']//table/thead/tr/th");
		getWebDriverWait().until(ExpectedConditions.visibilityOf(
				getWebElement("//div[@class='details-panel-container']//div[@class='responsive-table']")));
//		ngWebDriver.waitForAngularRequestsToFinish();
		return numberOfcolumn.size();
	}

	@Step("Get record value by using row index and column index from Crashed History table")
	public String getCrashedHistoryTableCellValue(int rowIndex, int columnIndex) {
		WebElement rowValue = getWebElement(
				"//div[@class='details-panel-container']//div[@class='responsive-table']//tbody/tr[" + rowIndex
						+ "]/td[" + columnIndex + "]");
		getWebDriverWait().until(ExpectedConditions.visibilityOf(
				getWebElement("//div[@class='details-panel-container']//div[@class='responsive-table']")));
		return rowValue.getText();
	}

	@Step("Verifying Page Iteration Available")
	public String isPageIterationAvailable(int count) {
		String expectedMessage;
		if (count == 0) {

			expectedMessage = "Showing " + count + "-" + count + " of " + count + " assets";

		} else {
			expectedMessage = "Showing 1-" + count + " of " + count + " assets";
			System.out.println("isPageIterationAvailable------>"+expectedMessage);

		}
		return expectedMessage;
	}

	@Step("verifying is Data Present in FingerPrint Grid ")
	public boolean isDataPresentInFPGrid(String filterValue) {
		WebElement tootlTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
		String filterCount = tootlTipValueElement.getText();
		int count = Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
		if (count > 0) {
			SYSTEMNAME = getTableCellValue(2, 1);
			String SOFTWARERELEASE = getTableCellValue(2, 4);
			RISKVALUE = getTableCellValue(2, 5);
			open360View(SOFTWARERELEASE);
			SYSTEMNAMEFROMFP = fingerprintDetails();
			RISKFROMFP = getWebElement("//div[@class='col-md-12 text-small half-margin-bottom cursor']//span[contains(text(),'"
					+ filterValue + "')]").getText();
			close360View();
			return true;
		} else {
			System.out.println("No Data Available for " + filterValue + " Filrer in Grid");
			return false;
		}
	}

	@Step("Close icon Search Box")
	public void closeSearchIcon() {
		String closeIcon = "//span[@class='dropdown-search__close-icon']";
		getWebElement(closeIcon).click();
	}

	@Step("Verify Search Box")
	public boolean searchIcon() {
		String closeIcon = "//span[@class='dropdown-search__search-icon']";
		return isElementPresent(closeIcon);
	}

	@Step("Sort the table Columns")
	public boolean columnSortTable(String headingName) {
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
		try {
			getWebElement("//div[contains(@class,'column-header__cell sortable')]/span[contains(text(),'" + headingName + "')]").click();
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("Table column " + headingName + " is not sortable");
			return false;
		}
	}

	@Step("Check Insights Tab When Closed the Success Track")
	public RiskMitigationPage insightsTabWhenClosedSuccessTrackButton() {
		String successTrackFilter = "//span[@data-auto-id='remove-filter-0']";
		WebElement element;
//		jsWaiter.waitAllRequest();
		try {
			element = getWebElement(successTrackFilter);
			getActions().moveToElement(element).click(element).build().perform();
			waitForInitialPageToLoad();
		} catch (NoSuchElementException e) {
			System.out.println("Success Track is removed already");
		}
		return this;
	}

	@Step("Donut Chart Filters Count")
	public int donutChartFilterCount() {
		String donutChartCount = "//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][2]";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(donutChartCount)));
		return getWebElements(donutChartCount).size();
	}

	@Step("Open Assets 360 View Page")
	public String openAssetsWithCrash360View(String filterValue) {
		String deviceConnected = null;
		String clickDeviceName = "//a[@data-auto-id='selectedName']";
		String isConnected = "//span[contains(.,'Connected')]";
		WebElement toolTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
		String filterCount = toolTipValueElement.getText();
		int count = Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
		if (count > 0) {
			crashRiskAssetName = getTableCellValue(1, 1);
			open360View(crashRiskAssetName);
			getWebElement(clickDeviceName).click();
			deviceConnected = getWebElement(isConnected).getText();
			assets360DeviceName=getWebElement("//div[@class='col-md-12 no-margin-bottom']").getText();
			getWebElement("//asset-details//span[@class='icon-close icon--mid']").click();
		}
		close360View();
		return deviceConnected;
	}

	@Step("Open Assets 360 View Page")
	public String openCrashedAssets360View() {
		String deviceConnected = null;
		getWebElement("//a[@data-auto-id='selectedName']").click();
		deviceConnected = getWebElement("//span[contains(.,'Connected')]").getText();
		getWebElement("//asset-details//span[@class='icon-close icon--mid']").click();
		close360View();
		return deviceConnected;
	}

	@Step("Crash History Page Labels")
	public List<String> crashHistoryLabels() {
		List<String> expectedLabelNames = new ArrayList<String>();
		expectedLabelNames.add("IP Address");
		expectedLabelNames.add("SN");
		expectedLabelNames.add("Crashes");
		expectedLabelNames.add("Reset Reason");
		expectedLabelNames.add("Date And Time");
		return expectedLabelNames;
	}

	@Step("Get All FingerPrint Lable Names")
	public List<String> fingerPrintLableNames(String globalRiskRank) {
		List<String> fingerPrintLabelNames = new ArrayList<String>();
		fingerPrintLabelNames
				.add(getWebElement("//div[@class='col-md-12 text-small half-margin-bottom cursor']//span[contains(text(),'"
						+ globalRiskRank + "')]").getText());
		fingerPrintLabelNames.add(getWebElement("//div[contains(text(),'IP Address')]").getText());
		fingerPrintLabelNames.add(getWebElement("//div[contains(text(),'SN')]").getText());
		for (String labelNames : fingerPrintLabelNames)
			System.out.println("Label Name is: " + labelNames);
		return fingerPrintLabelNames;
	}

	@Step("Crash History Page Labels")
	public List<String> fingerPrintLabels(String risk) {
		List<String> expectedFingerPrintLabelNames = new ArrayList<String>();
		expectedFingerPrintLabelNames.add(risk);
		expectedFingerPrintLabelNames.add("IP Address");
		expectedFingerPrintLabelNames.add("SN");
		return expectedFingerPrintLabelNames;
	}
	@Step("Verifying the Compare Tabs")
	public List<String> fingerPrint360LabelNames(String filterValue) {
		List<String> fingerPrintLabelNames=null;
	WebElement toolTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
	String filterCount = toolTipValueElement.getText();
	int count = Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
	if (count > 0) {
		SYSTEMNAME = getTableCellValue(5, 1);
		open360View(SYSTEMNAME);
		 fingerPrintLabelNames = new ArrayList<String>();
		fingerPrintLabelNames
				.add(getWebElement("//div[@class='col-md-12 text-small half-margin-bottom cursor']//span[contains(text(),'"
						+ filterValue + "')]").getText());
		fingerPrintLabelNames.add(getWebElement("//div[contains(text(),'IP Address')]").getText());
		fingerPrintLabelNames.add(getWebElement("//div[contains(text(),'SN')]").getText());
		for (String labelNames : fingerPrintLabelNames)
			System.out.println("Label Name is: " + labelNames);
		fingerprintSimilarAssets=getWebElement("//div[@class='text-large text-dkgray-4']").getText();
		close360View();
	}
	else {
		System.out.println("No Data Available");
	}
	return fingerPrintLabelNames;
	
}
	@Step("Verifying the Compare Tabs")
	public List<String> compareTabNamesHigh(String filterValue ) {
		List<String> fingerPrintCompareTabNames=new ArrayList<>();
		WebElement toolTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
		String filterCount = toolTipValueElement.getText();
		int count = Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
		if (count > 0) {
		open360View(getTableCellValue(3, 1));
		getWebElement("//a[text()='compare']").click();
		fingerPrintSimilarAsset=getWebElement("//p[@class='text-left']//span[contains(text(),'Medium')]").getText();
		int elementCount=getWebElements("//div[@class='flex-fill btn-group btn-group--wide']//button").size();
		for(int i=1;i<=elementCount;i++) {
					fingerPrintCompareTabNames.add(getWebElement("//div[@class='flex-fill btn-group btn-group--wide']//button["+i+"]").getText());
		}
		close360View();
		}
		return fingerPrintCompareTabNames;
	}
	@Step("Verifying the Compare Tabs")
	public List<String> compareTabNamesMedium(String filterValue) {
		List<String> fingerPrintCompareTabNames=new ArrayList<>();
		WebElement toolTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
		String filterCount = toolTipValueElement.getText();
		int count = Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));
		if (count > 0) {
		open360View(getTableCellValue(5, 2));
		getWebElement("//a[text()='compare']").click();
		fingerPrintSimilarAsset=getWebElement("//p[@class='text-left']//span[contains(text(),'Low')]").getText();
		int elementCount=getWebElements("//div[@class='flex-fill btn-group btn-group--wide']//button").size();
		System.out.println("elementCount-------------->"+elementCount);
		for(int i=1;i<=elementCount;i++) {
					fingerPrintCompareTabNames.add(getWebElement("//div[@class='flex-fill btn-group btn-group--wide']//button["+i+"]").getText());
		}
		close360View();
		}
		return fingerPrintCompareTabNames;
	}
	 @Step("Sort the table")
	    public boolean riskColumnSort(String columnName) {
	        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	        try {
	            getWebElement("//div[@class='column-header__cell sortable']//span[contains(text(),'"+columnName+"')]").click();
	            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	            return true;
	        } catch (NoSuchElementException e) {
	            System.out.println("Table column " + columnName + " is not sortable");
	            return false;
	        }
	    }
	 @Step("Risk Column Tooltip Text")
		public String riskTooltip() {
		 WebElement element= getWebElement("//div[@class='column-header__cell sortable']//span[contains(text(),'Risk')]");
			getActions().moveToElement(element).build().perform();
			return getWebElement("//span[.='Risk']").getText();
			
		}
	 @Step("FingerPrint High Risk Tooltip Text")
		public String FPRiskRankTooltip() {
		 open360View(getTableCellValue(1, 2));
		 getWebElement("//span[@class='icon-help-alt qtr-margin']").click();
			String fpTooltipText= getWebElement("//div[@class='col-11']//span").getText();
			close360View();
			return fpTooltipText;
		}
	 @Step("Assets Page Iteration Label Text")
		public String assetsPageIterationLabel() {
		 return getWebElement("//span[@class='base-margin-left text-small text-muted']").getText();
		   
			
		}
	 @Step("donut Chart Filters Device Count")
		public int riskDonutFilterDataCount(String filterValue) {
		 WebElement toolTipValueElement = getWebElement("//div[text()='" + FILTER_TYPE + "']/../..//pie-chart//*[name()='svg']//*[name()='tspan'][contains(text(),'" + filterValue + "')]");
			String filterCount = toolTipValueElement.getText();
			return Integer.parseInt(filterCount.substring(filterCount.indexOf("(") + 1, filterCount.indexOf(")")));	
		}
	@Step("Close Applied Risk Filter")
	public void closeRiskFilter(){
		 getWebElement("//span[@class='icon-close']").click();
	}
	@Step("Filtered Applied on Grid")
	public boolean filteredRiskFilter(){
		return isElementPresent("//span[contains(text(),'Filtered')]");
		
	}
	@Step("Crash Risk insights Count")
	public String crashRiskInsightCount(){
		return getWebElement("div.flex.align-items-center.selected-text-count:nth-child(2) > div.insight_count.text-left>div").getText();
		
	}
	@Step("To verify Use Case options displayed for Campus Success Track in the context selec")
	public boolean useCaseAddIconOption(){
		return isElementPresent("//button[@class='wrapper__btn']//*[local-name()='svg']");
		
	}
	@Step("To verify Insights Button")
	public boolean verifyInsightsButton(){
		return isElementPresent("//button[@id='insights']");
		
	}
	 
	 
}
