package com.cisco.pages.insights.software;

import static com.cisco.testdata.Data.SOFTWARE_GROUP_COLUMN_DATA;
import static com.cisco.testdata.StaticData.ChartType.PIE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.util.StringUtils;
import org.testng.annotations.Test;

import com.cisco.testdata.StaticData.FilterValue;
import com.cisco.utils.AppUtils;
import com.cisco.utils.ExcelReader;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class SoftwarePage extends AppUtils {
	public static final String RECOMMENDATIONS_FILTER_TYPE = "SUGGESTIONS";
	public static final String RISK_LEVEL_FILTER_TYPE = "RISK LEVEL";
	public String tabname;

	@Step("Verify the default selected Software Tab selected or not")
	public boolean isSoftwareTabSelected(String defaultTab) {
		String defaultTabInInsights = "//div[@class='tab__heading']//span[contains(text(),'Software')]";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(defaultTabInInsights)));
		String actualTab = getWebElement(defaultTabInInsights).getText();
		System.out.println("actualTab value is "+ actualTab);
		return actualTab.contains(defaultTab);
	}

	@Step("Verify Software group pop-up closed or not")
	public SoftwarePage softwareGroupPopupClose() {
		getWebElement("//div[@class='col-6']//button[contains(text(),'Close')]").click();
		return this;
	}

	@Step("Verify Software group pop-up closed or not")
	public SoftwarePage adminSettingClose() {
		getWebElement("//span[@data-auto-id='xbutton']").click();
		return this;
	}

	@Step("Click on Insights Tab")
	public void clickOnInsights() {
		getWebElement("//button[@class='facet-navigation__right']").click();
		getWebElement("//button[@id='insights']").click();
	}

	@Step("Click on Insights Tab")
	public void clickOnMyPortfolio() {
		String myPortfolio="//button[@class='flex-nav__action']//span[@class='refresh']";
		getWebElement(myPortfolio).click();
	}

	@Step("Verify the Software group count ")
	public int softwareGroupCount() {
		String sGroupCount = "//span[@class='qtr-margin-right text-huge']";
		String sGCountValue = getWebElement(sGroupCount).getText();
		int count = Integer.valueOf(sGCountValue);
		return count;
	}

	@Step("Verify the default filter for Software group")
	public boolean defaultFilterForSoftwareGroup(String defaultFilter) {
		String defaultRecommendationFilter = "//div[@class='col-md-11 flex']//span[@data-auto-id='FilterTag-automated']";
		String defaultRecommFilter = getWebElement(defaultRecommendationFilter).getText();
		return defaultRecommFilter.contains(defaultFilter);
	}

	@Step("Verify RECOMMENDATIONS Filter Applied")
	public String recommendationFilter(String filter) {
		FilterValue filterValue = new FilterValue(RECOMMENDATIONS_FILTER_TYPE, PIE, filter);
		filterByVisualFilters(filterValue);
		return filter;
	}

	@Step("Verify RISK LEVEL Filter Applied")
	public String riskLevelFilter(String filter) {
		FilterValue filterValue = new FilterValue(RISK_LEVEL_FILTER_TYPE, PIE, filter);
		filterByVisualFilters(filterValue);
		return filter;
	}

	@Step("Verify clear DNAC search")
	public void searchClear() {
		getWebElement("//div[@class='dropdown-search__searchbar']//input").clear();
		getActions().sendKeys(Keys.ENTER).build().perform();
	}

	@Step("Verify clear DNAC search")
	public String getSearchInputText() {
		return getWebElement("//div[@class='dropdown-search__searchbar']//input").getText();
	}

	@Step("Verify clear DNAC search")
	public void selectDropdown() {
		WebElement dropdownElement = getWebElement("//div[@class='dropdown-search__searchbar']//div/button/span");
		dropdownElement.click();
		getWebElement("//button/span[contains(text(),'DNA')]").click();
	}

	@Step("Verify Recommendation Interval from 360 window ")
	public String recommendationInterval() {
		return getWebElement("//div[@class='pull-right base-margin-bottom']/span[3]").getText();
	}

	@Step("Verify Admin Settings Button")
	public void adminSettings() {
		getWebElement("//a[@data-auto-id='Admin']").click();
		String tabName = "//div[@class='header-bar__heading base-padding-left']/h2";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tabName)));
		tabname= getWebElement(tabName).getText();
	}

	@Step("Verify Admin Settings Insight Tab")
	public void adminSettingsInsights() {
		getWebElement("//li[@class='sidebar__item']//div//a//span[contains(text(),'Insights')]").click();	
	}

	@Step("Verify Automated Recomm Interval Date in Admin Settings Insight Tab")
	public String automatedRecommIntervalDate() {
		String automatedRecommIntervalDate = "//div[@class='col-lg-5 base-margin-top base-margin-right']//div//div[3]//span[@class='base-margin-right']";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(automatedRecommIntervalDate)));
		return getWebElement(automatedRecommIntervalDate).getText();
	}

	@Step("Verify Automated Recomm Edit Button in Admin Settings Insight Tab")
	public void automatedRecommEditButton() {
		getWebElement("//a[@data-auto-id='editRecommButton']").click();
	}

	@Step("Verify current Release link in software group page")
	public void currentReleaseLink() {
		getWebElement("//span[@data-auto-id='SoftwareGroupCurrentVersions']").click();
	}

	@Step("Verify Next Recomm Interval Date in Admin Settings Insight Tab")
	public String nextRecommIntervalDate() {
		return getWebElement("//div[contains(text(),'Next Suggestion Date')]/..//div[3]/span").getText();
	}

	@Step("Verify Sorting")
	public void sorting(String[][] Sorting) {
		SoftwarePage softwarePage = new SoftwarePage();
		String columnList;
		for (int i = 0; i <= Sorting.length - 1; i++) {
			for (int j = 0; j < Sorting[i].length; j++) {
				columnList = Sorting[i][j];
				softwarePage.sortTable(columnList);
			}
		}

	}

	@Step("Verify filter and search table count")
	public void gridValidation(int index, String value, int tableRowCount) {
		SoftwarePage softwarePage = new SoftwarePage();
		String columValue;
		String invalidSearchResult;
		System.out.println("tableColumnCount----->" + tableRowCount);
		if (tableRowCount != 0) {

			for (int i = 1; i <= tableRowCount; i++) {

				columValue = softwarePage.getTableCellValue(i, index);
				System.out.println("columValue--->" + columValue);
				assertEquals(columValue, value);

			}
		} else {
			System.out.println("-------------------------");
			invalidSearchResult = softwarePage.noSoftwareGroups();
			assertEquals(invalidSearchResult, "No Software Groups Found");
		}
	}

	@Step("Verify Color code for Risk Level")
	public void colorCode() {
		String textVal;
		String xpath;
		int rowcount = getTableRowCount();
		for (int i = 1; i <= rowcount; i++) {
			textVal=getTableCellValue(i,6);
			 xpath=getWebElement("//tbody/tr[" + i + "]/td[ 6]/span").getAttribute("class");
			if (textVal.equals("Low")) {
				assertTrue(xpath.contains("success"));
			
			} else if (textVal.equals("High")) {
				assertTrue(xpath.contains("danger"));
			
			} else if (textVal.equals("Medium")) {
				assertTrue(xpath.contains("warning"));
			}
		}
	}

	@Step("Get No Software Groups Found from Software Groups tab")
	public String noSoftwareGroups() {
		return getWebElement("//span[contains(text(),'No Software Groups')]").getText();
	} 

	@Step("Get total numbers of rows visible in 360 window")
	public int getTableRowCountFor360Window() {
		try {
			return getWebElements("//div[@class='responsive-table']//tbody/tr").size();
		} catch (NoSuchElementException e) {
			return 0;
		}
	}

	@Step("Select a tab with name : {0}")
	public void currentReleaseTab(int tableRowCount) {
		String currentReelaseTabName=  getWebElement("//div[@class='tab__heading']//span[contains(text(),'CURRENT RELEASES')]").getText();
		System.out.println("currentReelasetext------->"+currentReelaseTabName);
		Assert.assertEquals(currentReelaseTabName,"CURRENT RELEASES ("+tableRowCount+")");
	}

	@Step("To Validate the Software Groups information")
	public void informativeIcon()
	{
		getWebElement("//span[@class='pull-right text-primary relative']").click();
		getWebElement("//span[text()='Software Groups']").isDisplayed();
	}

	@Step("To Validate the tool tip")
	public void toolTip()
	{
		Actions actions = new Actions(getDriver()); 

		WebElement element1=getWebElement("//i[@class='icon-question-circle info-large icon-small info-icon-osv qtr-margin-left']");
		actions.moveToElement(element1).perform();
		WebElement toolTip1=getWebElement("//div[@class='tooltip-container not-close-360']");
		String toolTipText1 = toolTip1.getText();
		Assert.assertEquals("A Software Group may be mapped to multiple DNACs. Hence the total number of rows, shown in the table may be greater than the total number of Software Groups.",toolTipText1);	
	}

	@Step("To Validate pagenation with Prev & next option")
	public void paginationCheck()
	{
		String pageCount= getTotalCountOfPages();
		int i=Integer.parseInt(pageCount);	
		System.out.println(i);
		if(i>1 )
		{
			WebElement nextbutton = getWebElement("//span[text()='Next']");
			nextbutton.isDisplayed();
			nextbutton.click();
			WebElement prevbutton = getWebElement("//span[text()='Prev']");
			prevbutton.isDisplayed();
			prevbutton.click();
		}else
		{
			Allure.step("pagination not provided as we have only 1page data");
		}
	}

	@Step("To Validate current releases information is displayed on click of Release link")
	public void currentReleasesInfo()
	{
		String currentReleasesInfo = "//span[@data-auto-id='SoftwareGroupCurrentVersions']";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(currentReleasesInfo))).click();
		try{
			String release= getWebElement("//span[@data-auto-id='Release-Header']").getText();
			String releaseDate= getWebElement("//span[@data-auto-id='Release Date-Header']").getText();
			Assert.assertEquals("Release", release , "Release subtab is not displayed");
			Assert.assertEquals("Release Date", releaseDate , "Release Header subtab is not displayed");
			getWebElement("//a[@data-auto-id='CloseDetails']").click();
		} catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Current Release Tab is not displayed on click of Release link");
			getWebElement("//a[@data-auto-id='CloseDetails']").click();
		}
	}

	@Step("To Validate risk color code from grid to flyout")
	public void riskColor()
	{
		int j= getTableRowCount();
		for(int i=1;i<=j;i++)
		{
			WebElement value = getWebElement("(//span[starts-with(@class,'label label--circle label')])["+i+"]");
			String gridValColor=value.getAttribute("class");
			value.click();
			WebElement value2 = getWebElement("(//div[@class='flex justify-content-start']/span)[1]");
			String flyoutValColor=value2.getAttribute("class");
			getWebElement("//a[@data-auto-id='CloseDetails']").click();
			Assert.assertEquals(gridValColor, flyoutValColor , "Risk Level color code is not matching from grid to flyout");
		}
	}

	@Step(" Search Clear ")
	public void clearSearch()
	{
		getWebElement("//button[@data-auto-id='clearSearchButton']").click();
	}

	@Step("verify Software Release are unique")
	public void uniqueSWRelease() {
		String swGroup;
		String dnac;
		int countVal;

		String countval="//span[text()='Software Groups']/../../div/span";
		WebElement count= getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(countval)));
		int softwareGroupCount = Integer.parseInt(count.getText());

		System.out.println("Software Group Count value is " + softwareGroupCount);

		SoftwarePage softwarePage = new SoftwarePage();

		List<String> swReleaseTab = new ArrayList<>();
		int tableColumnCount = softwarePage.getTableRowCount();
		System.out.println("tableColumnCount---->"+tableColumnCount);

		Map<String,String> swReleaseTab1 = new HashMap<>();
		for (int i = 1; i <= tableColumnCount; i++) {


			swGroup = softwarePage.getTableCellValue(i, 1);
			dnac = softwarePage.getTableCellValue(i, 2);
			if (!StringUtils.isEmpty(swGroup) && !StringUtils.isEmpty(dnac)) {
				if (swReleaseTab1.keySet().contains(swGroup)) {
					if (swReleaseTab1.get(swGroup).equals(dnac)) {
						Assert.assertFalse(false, "Duplicate value ---->");

					} else {
						swReleaseTab1.put(swGroup, dnac);
						System.out.println("insideswGroup--->"+swGroup+"----insidednac----->"+dnac);
					}
				} else {
					swReleaseTab1.put(swGroup, dnac);
					System.out.println("swGroup--->"+swGroup+"----dnac----->"+dnac);
				}

			}
		}
		countVal= swReleaseTab1.size();
		System.out.println("Unique software Profile count taken from Grid is----------->"+countVal);
		Assert.assertEquals(softwareGroupCount , countVal , "Unique Software Group Count doesn't Match");
	}

	@Step("Verify the current Release Tab ")
	public boolean isCurrentReleaseAvailable() {
		try {
			getWebElement("//div[text()='Current Releases']").isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	@Step("Verify Recommendation pop-up messages")
	public String recommMessage() {
		return getWebElement("//div[@class='success-msg-container text-left']//div//div[2]//span").getText();
	}

	@Step("Verify the pop-up messages if there are no recommendations ")
	public void noRecommMessages() {
		SoftwarePage softwarePage = new SoftwarePage();
		softwarePage.recommendationFilter("No");
		String profileName;
		String release;
		String recommendation;
		String riskLevel;
		String noMP;
		String recommendationAvailable="//div[@class='base-margin-top']//table//tbody//tr//td[3]/div[1]/span[contains(text(),'OPTION 1')]";
		int gridCount = softwarePage.getTableRowCount();

		for (int i = 1; i <= gridCount; i++) {

			recommendation = softwarePage.getTableCellValue(i, 9);
			riskLevel = softwarePage.getTableCellValue(i, 6);

			if (recommendation.equalsIgnoreCase("No") && riskLevel.equalsIgnoreCase("NA")) {
				release = softwarePage.getTableCellValue(i, 5);
				if (release.equalsIgnoreCase("16.8.1a")) {
					profileName = softwarePage.getTableCellValue(i, 1);
					softwarePage.open360View(profileName);
					noMP = softwarePage.recommMessage();
					assertEquals(noMP,
							"Suggestions are currently not available for this release(s). For generic suggestions, go to");
					assertTrue(softwarePage.isCurrentReleaseAvailable(), "Current Release Tab is not available");
					assertFalse(softwarePage.isElementPresent(recommendationAvailable), "Recomm tab is not available");
					softwarePage.close360View();


				} else {
					profileName = softwarePage.getTableCellValue(i, 1);
					softwarePage.open360View(profileName);
					noMP = softwarePage.recommMessage();
					assertEquals(noMP,
							"Suggestions are not available for this custom release. This release is not evaluated against standard releases. For general suggestions, go to");
					assertTrue(softwarePage.isCurrentReleaseAvailable(), "Current Release Tab is not available");
					assertFalse(softwarePage.isElementPresent(recommendationAvailable), "Recomm tab is not available");

					softwarePage.close360View();
				}

			} else {
				profileName = softwarePage.getTableCellValue(i, 1);
				softwarePage.open360View(profileName);
				noMP = softwarePage.recommMessage();

				assertEquals(noMP,
						"You have already deployed the recommended software ");
			}
		}
	}

	@Step("Verify Applied Filter ")
	public String verifyFilter(String appliedfilter, String filterName) {
		FilterValue filterValue = new FilterValue(filterName, PIE, appliedfilter);
		filterByVisualFilters(filterValue);
		return appliedfilter;
	}

	@Step("PIE Chart Filters")
	public void filterCount(String filterType, int columnIndexValue) {
		SoftwarePage softwarePage = new SoftwarePage();
		String pieChart = "//div[text()='" + filterType + "']/../..//" + PIE
				+ "-chart//*[name()='svg']//*[name()='tspan'][2]";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pieChart)));
		int filterCount=	 getWebElements(pieChart).size();

		String filter;

		int tableRowCount;
		boolean booleanValue;
		for(int i=1; i<=filterCount; i++)
		{

			filter=getWebElement("("+pieChart+")["+i+"]").getText();
			softwarePage.verifyFilter(filter,filterType);

			//assertTrue(softwarePage.checkFilterApplied());
			tableRowCount = softwarePage.getTableRowCount();
			softwarePage.gridValidation(columnIndexValue, filter, tableRowCount);
			booleanValue = softwarePage.clearAllFilter();
			assertFalse(booleanValue, "Filter is not removed");
		}
	}
}

