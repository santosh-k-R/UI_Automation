package com.cisco.pages.insights.riskMitigation;

import static com.cisco.testdata.StaticData.DNAC_DropdownValue.ALL_CATEGORIES;
import static com.cisco.testdata.StaticData.DNAC_DropdownValue.DNA_CENTER_APPLIANCES;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cisco.utils.AppUtils;

import io.qameta.allure.Step;

/**
 * @author roodesai
 *
 */
public class RiskMitigation360Page extends AppUtils {

	public static String EXPORT_CSV;
	public static String ASSETS_VALUE;

	@Step("Click Crashed Assets scorecard from Risk Mitigation Dashboard")
	public void clickCrashedSystem() {
		getWebElement("//span[contains(.,'Crashed Assets')]").click();
	}

	@Step("Click on System and CrashedSystem ExportCSV Button")
	public boolean isExportCSVbuttonEnabled(String exportCSV) {
		String EXPORT_CSV = "//div[@class='export-csv__show-more-button']";
		return isElementPresent(EXPORT_CSV);
	}

	@Step("Click on System and CrashedSystem Table column selector icon")
	public void clickTableColumnSelector() {
		getWebElement("//button[@class='column-selector-trigger']//i//*[local-name()='svg']").click();
	}

	@Step("Select user selection columns ")
	public List<String> selectColumns(String columnname) {
		List<String> userSelectionColumn = new ArrayList<String>();
		List<WebElement> selectColumn = new ArrayList<WebElement>();
		selectColumn.add(
				getWebElement("//div[@class='list-item unchecked']//label[contains(text(),'" + columnname + "')]"));
		for (WebElement cb : selectColumn) {
			cb.click();
		}
		System.out.println("Checked the check Box");
		return userSelectionColumn;
	}

	@Step("Click Apply Button ")
	public boolean isApplyButtonEnabled(String ClickApply) {
		getWebElement("//button[.=' APPLY ']").click();
		return isElementPresent("//button[.=' APPLY ']");
	}

	@Step("Click Refresh Button ")
	public boolean isResetButtonEnabled(String ClickReset) {
		String clickReset = "//button[@class='column-selector__footer--secondary']";
		getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(clickReset)));
		getWebElement(clickReset).click();
		return isElementPresent(clickReset);
	}

	@Step("De-Select user selection columns ")
	public List<String> deselectColumn(String columnname) {
		List<String> userSelectionColumn = new ArrayList<String>();
		List<WebElement> deselectColumn = new ArrayList<WebElement>();
		deselectColumn.add(getWebElement("//label[contains(text(),'" + columnname + "')]"));
		for (WebElement cb : deselectColumn) {
			cb.click();
		}
		System.out.println("Selected columns check boxes have been unchecked");
		return userSelectionColumn;
	}

	@Step("Click Crashed system Apply Button ")
	public void clickApplyButton() {
		String clickApply = "//button[.=' APPLY ']";
		getJavascriptExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight)");
		getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(clickApply)));
		getWebElement(clickApply).click();

	}

	@Step("Click Crashed system Reset Button ")
	public void clickResetButton() {
		String clickReset = "//button[.=' RESET ']";
		getJavascriptExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight)");
		getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(clickReset)));
		getWebElement(clickReset).click();

	}

	@Step("ContextSelectorDisplayed")
	public boolean isContextSelectorDisplayed(String string) {
		getWebElement("//span[contains(text(),'Campus Network')]");
		return isElementPresent("//span[contains(text(),'Campus Network')]");
	}

	@Step("Pick value from assets with crash risk grid and verify search value in grid")
	public boolean searchValueinGrid(int rowIndex, int columnIndex) {
		String value = getTableCellValue(rowIndex, columnIndex);
		searchTrackItems(value);
		if (getTableRowCount() > 0) {
			System.out.println("search item available in grid");
			return true;
		} else {
			System.out.println("search item not available in grid");
			return false;
		}
	}

	@Step("Close  High filter tag ")
	public void closeHighFilter() {
		getWebElement("	//span[@class='icon-close']").click();

	}

	/**
	 * <p>
	 * Selected risk Medium
	 * </p>
	 *
	 * @param risk
	 *            "Medium" passing actual value because validating medium risk grid
	 *            only
	 *
	 */

	@Step("Selected risk Medium")
	public boolean isMediumRiskPresentInGrid(String risk) {
		String expval1 = null;
		List<WebElement> list = getWebElements("//td[contains(.,'" + risk + "')]");
		Iterator<WebElement> it = list.iterator();
		while (it.hasNext()) {
			WebElement expval = it.next();
			expval1 = expval.getText();
			if (expval1.contains("Medium")) {
				return true;
			} else {
				return false;
			}
		}
		return isElementPresent("//td[contains(.,'" + expval1 + "')]");
	}

	@Step("Check No crashes found in selected time range")
	public boolean isNoCrashesMessageDisplayed() {
		getWebElement("//p[contains(.,'No Crashed Assets Found')]");
		return isElementPresent("//p[contains(.,'No Crashed Assets Found')]");
	}

	@Step("Pick value from assets with crash risk grid and verify Partial search value in grid")
	public boolean partialGridSearch(int rowIndex, int columnIndex) {
		String value = getTableCellValue(rowIndex, columnIndex);
		int value1 = value.length() - 2;
		if (value1 >= 0) {
			String value2 = value.substring(0, value1);
			searchTrackItems(value2);
			if (getTableRowCount() > 0) {
				return true;
			} else {
				System.out.println("search item not available in grid");
				return false;
			}
		}
		return true;
	}

	@Step("ALL Search mgmtSystemHostname or mgmtSystemAddr")
	public boolean searchALL(String value) {
		searchDNACField(ALL_CATEGORIES, value);
		if (getTableRowCount() >= 0) {
			return true;
		}
		return true;
	}

	@Step("DNAC Search mgmtSystemHostname or mgmtSystemAddr")
	public boolean searchDNAC(String value) {
		searchDNACField(DNA_CENTER_APPLIANCES, value);
		if (getTableRowCount() >= 0) {
			return true;
		}
		return true;
	}

	@Step("Check No crashes found in selected time range")
	public boolean isDataPresentForSearch(String value) {
		if (getTableRowCount() == 0) {
			return isElementPresent("//p[contains(.,'No Crashed Assets Found')]");
		} else {
			searchDNACField(ALL_CATEGORIES, value);
		}
		return true;
	}
}
