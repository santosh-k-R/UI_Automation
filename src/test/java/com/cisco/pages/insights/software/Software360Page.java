package com.cisco.pages.insights.software;

import static com.cisco.testdata.StaticData.ChartType.PIE;
import static org.junit.Assert.fail;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.cisco.testdata.StaticData.ButtonName;
import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.utils.AppUtils;

import io.qameta.allure.Step;

public class Software360Page extends AppUtils {

	public static final String STATE = "State";
	public static final String SEVERITY = "Severity";
	public static final String IMPACT = "Impact";

	public String cdet;	
	public String severity;
	public String title;
	public int affectedSystemCount;
	public int affectedSystemBugScreen;
	public int featuresBugScreen;
	public int featuresASCount;
	public int count;
	public String summaryTab;
	public String affectedSystemTab;
	public String featuresAffected360W;
	public String featuresActive360W;
	public String featureName;
	public String systems;
	public String recommFeaturesAffected360W;
	public String recommFeaturesActive360W;
	public String titleSA;
	public int exposedCount;

	@Step("Verify & Getting Software Group Name from 360 window")
	public String softwareGroupName() {
		return getWebElement("//div[@class='margin-left-label text-xlarge half-margin-bottom']").getText();
	}

	@Step("Verify & Getting Software Group Product Family from 360 window")
	public String softwareGroupProductFamily() {
		return getWebElement("//div[@class='col-6 text-small']//span[2]").getText();
	}

	@Step("Verify & Getting Software Group Product Family from 360 window")
	public String softwareGroupRelease() {
		return getWebElement("//div[@class='half-margin-top']//span[@class='link toggle']").getText();
	}

	@Step("Get No Recommendations from 360 window")
	public String getNoRecommendations() {
		return  getWebElement("//div[@class='flex-center-horizontal text-xlarge text-muted dbl-margin-top half-margin-bottom']").getText();
	}

	@Step("Verify the cancel three dot button from 360 window")
	public void cancelThreeDot() {
		WebElement cancelButton = getWebElement("//span[@class='icon-more icon-small rotate-90 text-muted']");
		cancelButton.click();
	}

	@Step("Verify the accept button from 360 window")
	public void acceptButton() {
		WebElement acceptButton = getWebElement("//div[@class='compare-recommendations responsive-table']//table//tbody//tr[2]//td[4]//button");
		acceptButton.click();
	}

	@Step("Verify the cancel button from 360 window")
	public void cancelButton() {
		getWebElement("//span[contains(text(),'CANCEL')]").click();
	}

	@Step("Get reccomendation one release from 360 window")
	public String getReccomendationRlease() {
		return getWebElement("//td[@class='recommendation']//div[@class='half-margin-top']").getText();
	}

	@Step("Get reccomendation one release from 360 window")
	public String getReccomendationRlease2() {
		return getWebElement("(//td[@class='recommendation']//div[@class='half-margin-top'])[3]").getText();
	}

	@Step("Get selected reccomendation one release from 360 window")
	public String getselectedReccomendationRlease() {
		return getWebElement("//span[contains(text(),'Success')]/../span[2]").getText();
	}

	@Step("Get reccomendation one release from 360 window")
	public void getBugs() {
		getWebElement("//tr[@id='bugs-details']//td[1]").click();
	} 

	@Step("Get Currently Exposed To bugs from 360 window")
	public String getCurrentlyExposedToBugs() {
		return  getWebElement("//td[@data-auto-id='ShowBugDetailsCurrentReleasesTab']//div[2]").getText();
	}
	
	@Step("Get Currently Exposed To Psirt from 360 window")
	public String getCurrentlyExposedToSA() {
		return  getWebElement("//td[@data-auto-id='ShowPsirtDetailsCurrentReleaseTab']//div[2]").getText();
	}
	
	@Step("Get Currently Exposed To Field Notices from 360 window")
	public String getCurrentlyExposedToFN() {
		return  getWebElement("//td[@data-auto-id='ShowFieldDetailsCurrentReleasesTab']//div[2]").getText();
	}

	@Step("Get Currently Exposed To bugs from bugs window")
	public String getCurrentlyExposedToBugsFromBugstab() {
		return getWebElement("//div[@data-auto-id='TotalExposed']//div//span[@class='qtr-margin-right text-huge']").getText();
	} 

	@Step("Get Back button from bugs window")
	public void backButton() {
		getWebElement("//span[@class='toggle icon-chevron-left icon-small qtr-margin-bottom']").click();
	} 

	@Step("Get Recommendations1 from bugs/psirt/FnF window")
	public String Recommendation1() {
		getWebElement("//a[@data-auto-id='OPTION 1Tab']").click();
		return getWebElement("//a[@data-auto-id='OPTION 1Tab']").getText();
	} 

	@Step("Verify the System tab from 360 window")
	public String recommendedTab() {
		getWebElement("//span[contains(text(),'OPTION 1')]").click();
		return getWebElement("//span[contains(text(),'OPTION 1')]").getText();
	} 

	@Step("Get Security Advisories from 360 window")
	public String getSecurityAdvisories() {
		getWebElement("//td[contains(text(),'Security Advisories')]").click();
		return getWebElement("//span[text()='Security Advisories']").getText();
	}

	@Step("Get Field Notices from 360 window")
	public String getFieldNotices() {
		getWebElement("//td[contains(text(),'Field Notices')]").click();
		return getWebElement("//span[text()='Field Notices']").getText();
	}

	@Step("Get No Bugs Information Available from bugs window")
	public String noBugsInformationAvailable() {
		return getWebElement("//span[contains(text(),'No Bugs Information Available')]").getText();
	} 

	@Step("Get No FNs Found from bugs window")
	public String noFNsFound() {
		return getWebElement("//span[contains(text(),'No FNs Found')]").getText();
	} 

	@Step("Get No Security Advisories Found from bugs window")
	public String noSecurityAdvisoriesFound() {
		return getWebElement("//span[contains(text(),'No Security Advisories Found')]").getText();
	} 

	@Step("Get No Features Found from bugs window")
	public String noFeaturesFound() {
		return getWebElement("//span[contains(text(),'No Features are Available')]").getText();
	} 

	@Step("Get Current Release Risk Score Value from 360 window")
	public String riskScore(String riskScore) {
		return getWebElement("//tr[@id='risk-score']/td["+riskScore+"]//div//span[@class='text-large']").getText();
	}

	@Step("Get Risk Score Trend Chart from 360 window")
	public void riskScoretrendChart() {
		getWebElement("//tr[@id='risk-trend']//td[1]//div[contains(text(),'Risk Trend')]").click();

	}

	@Step("Get  Risk Score Value from Risk Score Trend")
	public String riskScoreTrendWindow() {
		return getWebElement("//div[@class='details-panel-container']//div//span[2]").getText();
	}

	@Step("Get  Risk Score Value from Risk Score Trend")
	public int systemTabRowCount() {
		return getWebElements("//table[@class='table table--bordered table--hover table--wrap table--compressed']//tbody/tr").size();
	}

	@Step("Get total numbers of rows visible in table")
	public int getFlyoutTableRowCount() {
		return getWebElements("//div[@class='responsive-table']//tbody/tr").size();
	}

	@Step("Select Method")
	public void selectFunction() 
	{
		Software360Page software360Page = new Software360Page();
		software360Page.clickButton(ButtonName.SELECT);
		getWebElement("//h1[contains(text(),'Select Release')]").getText();
		software360Page.clickButton(ButtonName. SELECT1);
		getWebElement("//span[contains(text(),'Next Steps')]").isDisplayed();
	}

	@Step("Date Validation")
	public void dateFunction() 
	{
		String selectedDate =getWebElement("//compare-recommendations[1]/div[1]/table[1]/tbody[1]/tr[2]/td[3]/div[1]/div[1]/div[2]/div[2]").getText();
		Calendar calendar = Calendar.getInstance();
		DateFormat date = DateFormat.getDateInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		String currentDate = date.format(calendar.getTime());
		Assert.assertEquals(selectedDate, currentDate, "Date Doesn't Match");
	}

	@Step("Get record value by using row index : {0} and column index : {1} from table")
	public String getTableCellValueFlyout(int rowIndex, int columnIndex) {
		String cellXpath = "//div[@class='responsive-table']/table/tbody/tr[" + rowIndex + "]/td[" + columnIndex + "]";
		getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(cellXpath)));
		return getWebElement(cellXpath).getText();
	}

	@Step("To Verify Cdet id,Severity and Title from bug screen to cdet sumary")
	public void bugsCdetSummary()
	{
		getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[2]").click();
		cdet =getWebElement("//span[@class='base-margin-left text-xlarge']").getText();
		severity=getWebElement("//div[@class='text-small']/span[4]").getText();
		title =getWebElement("//div[@class='text-uppercase']/../div[2]").getText();
	}

	@Step("To Hide Contextual Detail View")
	public void hideContextualDetailsView()
	{
		getWebElement("//span[@data-auto-id='hideContextualDetailsView']").click();
	}

	@Step("To verify Affected system count from bug screen and Affected Summary")
	public void bugsAffectSystemsCount()
	{

		Software360Page software360Page = new Software360Page();
		affectedSystemBugScreen=Integer.parseInt(software360Page.getTableCellValueFlyout(1,5));
		getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[2]").click();
		getWebElement("//ul[@class='tabs']/li[2]").click();
		affectedSystemCount=getWebElements("//div[@class='responsive-table']/table/tbody/tr").size();
	}

	@Step("To verify features in bugs Affected Systems for a cdet")
	public void bugsFeaturesCount()
	{
		Software360Page software360Page = new Software360Page();
		featuresBugScreen=Integer.parseInt(software360Page.getTableCellValueFlyout(1,6));
		getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[2]").click();
		getWebElement("//ul[@class='tabs']/li[2]").click();
		featuresASCount=Integer.parseInt(software360Page.getTableCellValueFlyout(1,5));
		getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[5]").click();
		count =getWebElements("//div[@class='col-md-9 base-margin-top']/div").size();
	}

	@Step("To verify clicking on each bug has 2 Tabs i.e Summary and Affected Systems")
	public void bugsTabs()
	{
		getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[2]").click();
		summaryTab=getWebElement("//ul[@class='tabs']/li[1]").getText();
		affectedSystemTab=getWebElement("//ul[@class='tabs']/li[2]").getText();
	}

	@Step("To get Active and Affected Features count from 360 window")
	public void featuresCount360Window()
	{
		featuresAffected360W =getWebElement("(//td[@data-auto-id='ShowFeatureDetailsCurrentReleasesTab']//div)[6]").getText();
		featuresActive360W = getWebElement("(//td[@data-auto-id='ShowFeatureDetailsCurrentReleasesTab']//div)[7]").getText();
	}

	@Step("To get Active and Affected Features count from 360 window")
	public void recommFeaturesCount360Window()
	{
		recommFeaturesAffected360W =getWebElement("(//td[@data-auto-id='ShowFeatureDetailsRecommendedReleasesTab'][1]//div)[5]").getText();
		recommFeaturesActive360W = getWebElement("(//td[@data-auto-id='ShowFeatureDetailsRecommendedReleasesTab'][1]//div)[6]").getText();
	}

	@Step("Get Features from 360 window")
	public String getFeatures() 
	{
		getWebElement("//td[contains(text(),'Features')]").click();
		return getWebElement("//span[text()='Features']").getText();
	}

	@Step("Get Features from 360 window")
	public String getRecommFeatures() 
	{
		getWebElement("//a[@data-auto-id='OPTION 1Tab']").click();
		return getWebElement("//a[@data-auto-id='OPTION 1Tab']").getText();
	}

	@Step("Get Features from 360 window")
	public String featuresCount(String name) 
	{
		return getWebElement("//span[text()='"+name+"']/../../div/span").getText();	
	}

	@Step("Get Features from 360 window")
	public void featuresclick(String name) 
	{
		getWebElement("//span[text()='"+name+"']/../../div/span").click();	
	}

	@Step("To Get Features cell row count")
	public int featuresRowCount()
	{
		return getWebElements("//span[@data-auto-id='Features-Cell']").size();
	}

	@Step("To Validate Features names and systems count")
	public void featuresNameSystems()
	{
		featureName = getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[1]").getText();
		systems= getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[2]").getText();
		getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[1]").click();	 
	}

	@Step("To Get title name")
	public String titleName()
	{
		return getWebElement("//span[@class='base-margin-left text-xlarge']").getText();
	}

	@Step("To validate the Feature grid column names")
	public List<String> systemTableTitle()
	{
		List<String> labelValues = new ArrayList<String>();
		labelValues.add(getWebElement("//span[@data-auto-id='System Name-Header']").getText());
		labelValues.add(getWebElement("//span[@data-auto-id='IP Address-Header']").getText());
		labelValues.add(getWebElement("//span[@data-auto-id='Product ID-Header']").getText());
		labelValues.add(getWebElement("//span[@data-auto-id='Software Release-Header']").getText());
		for (String labelName : labelValues)
			System.out.println("Label Name is: " + labelName);
		return labelValues;
	}	

	@Step("To verify the feature name is disabled on click in recommendation Feature Screen ")
	public boolean recommFeatureIsDisabled()
	{
		return getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[1]").isEnabled();
	}

	@Step("Search Item: {0}")
	public void searchOSVTrackItems(String searchItem) {
		WebElement searchField;
		searchField = getWebElement("//div[@class='dropdown-search__searchbar']//input");
		searchField.sendKeys(searchItem);
		searchField.sendKeys(Keys.ENTER);
	}

	@Step("Search close")
	public void searchClear() {
		getWebElement("//button[@data-auto-id='clearSearchButton']").click();
	}

	@Step("Security Advisories title columna name validation")
	public void getTitleColnameSA()
	{

		getWebElement("//div[@class='responsive-table']/table/tbody/tr[1]/td[2]").click();
		titleSA=getWebElement("//div[@class='text-bold']").getText();		
	}

	@Step("Security Advisories title description validation")
	public String getTitleDetailsSA()
	{
		return getWebElement("//div[@class='text-bold']/../div[2]").getText();
	}

	@Step("Security Advisories description columna name validation")
	public String getDescriptionColnameSA()
	{
		return getWebElement("//div[text()='Description']").getText();
	}

	@Step("Field Notices problem resolution header check")
	public String problemResolutionFN()
	{
		return getWebElement("//div[text()='PROBLEM DESCRIPTION']").getText();
	}

	@Step("Verify Applied Filter ")
	public String verifyFilter(String appliedfilter, String filterName) {
		FilterValue filterValue = new FilterValue(filterName, PIE, appliedfilter);
		filterByVisualFilters(filterValue);
		return appliedfilter;
	}

	@Step("Clear specific filterNames ")
	public Boolean clearSpecificFilterFlyout(String... filterNames) {
		for (String filterName : filterNames) {
			getWebElement("//span[contains(text(),'" + filterName + "')]/following-sibling::span[@class='icon-close']").click();
		}
		try {
			getWebElement("//span[@class='text-bold text-uppercase half-padding-right']");
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}	    }

	@Step("PIE Chart Filters")
	public void filterCount(String filterType, int columnIndexValue) {
		SoftwarePage softwarePage = new SoftwarePage();
		Software360Page software360Page = new Software360Page();
		String pieChart = "//div[text()='" + filterType + "']/../..//" + PIE
				+ "-chart//*[name()='svg']//*[name()='tspan'][2]";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pieChart)));
		int filterCount=	 getWebElements(pieChart).size();
		System.out.println("filterCount is "+ filterCount);
		String filter;

		int tableRowCount;
		boolean booleanValue;
		for(int i=1; i<=filterCount; i++)
		{
			filter=getWebElement("("+pieChart+")["+i+"]").getText();
			softwarePage.verifyFilter(filter,filterType);

			tableRowCount = software360Page.getFlyoutTableRowCount();
			softwarePage.gridValidation(columnIndexValue, filter, tableRowCount);
			booleanValue=software360Page.clearSpecificFilterFlyout(filter);
			assertFalse(booleanValue, "Filter is not removed");
		}
	}
	@Step("Verify the Filter Applied on Grid")
	public boolean getSecurityAdvisoryMessage() {
			return getWebElement("//span[contains(text(),'No Security Advisories Found')]").isDisplayed();
	}

			@Step("Total page count for 360 window")
			public String getTotalCountOfPagesFlyout() {
	
				List<WebElement> pageNumberElements = getWebElements("//div[@class='base-margin-top']//a[@class='cui-pager-page']/span");
				int lastIndex = pageNumberElements.size() - 1;
				try {
					return pageNumberElements.get(lastIndex).getText();
				} catch (NoSuchElementException | ArrayIndexOutOfBoundsException e) {
					return "1";
				}
			}
			@Step("Current Release Exposed Count for bugs,PSIRTS,FN's and features")
			public int currentReleaseExposedCount() {
				String countval="//span[text()='Exposed']/../../div/span";
				WebElement count= getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(countval)));
				exposedCount = Integer.parseInt(count.getText());
	
				System.out.println("Exposed Count value is " + exposedCount);
	
				return exposedCount;
			}
			@Step("verify Software Release are unique")
			public int uniqueSWRelease() {
				int countVal;
	
				List<String> swReleaseTab = new ArrayList<>();
				int tableColumnCount =getFlyoutTableRowCount();
				System.out.println("tableColumnCount---->"+tableColumnCount);
				String columValue;
				for (int i = 1; i <= tableColumnCount; i++) {
	
					columValue = getTableCellValue(i, 2);
					System.out.println("columValue--->"+columValue);
					if (columValue != null) {
						if (swReleaseTab.contains(columValue)) {
							System.out.println("Duplicate value ---->" + columValue);
						} else {
							swReleaseTab.add(getTableCellValue(i,1));
						}
					}
				}
				countVal = swReleaseTab.size();
				System.out.println("Unique software Releases count taken from Grid is----------->"+countVal);
	
				return countVal;
			}
	
			@Step("To Validate pagenation with Prev & next option")
			public int paginationCheck1() {
				int unique;
				int totalGroupCOunt=0;
				String pageCount = getTotalCountOfPagesFlyout();
				int paginationCount = Integer.parseInt(pageCount);
				System.out.println("paginationCount----->"+paginationCount);
				for(int i=0; i<=paginationCount-1; i++) {
					unique=	uniqueSWRelease();
					goToPrevOrNext("Next");
					totalGroupCOunt=  totalGroupCOunt + unique;				
					System.out.println("totalGroupCOunt inside ------>"+totalGroupCOunt);
				}
				System.out.println("totalGroupCOunt------>"+totalGroupCOunt);
				System.out.println("Exposed Count value is " + exposedCount);
	
				return totalGroupCOunt;
			}
			
			
}
