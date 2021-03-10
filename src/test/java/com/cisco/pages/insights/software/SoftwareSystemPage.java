package com.cisco.pages.insights.software;


import static com.cisco.testdata.Data.HOME_DATA;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.cisco.utils.AppUtils;

import io.qameta.allure.Step;

public class SoftwareSystemPage extends AppUtils{

	@Step("Verify System Page from Software Dashboard")
	public SoftwareSystemPage systemPage() {

		getWebElement("//div[@data-auto-id='TotalVisualFilterAssets']//div[@class='col-6 margin-auto']//span[@class='text-large ']").click();
		return this;
	}

	@Step("Verify System Page from Software Dashboard")
	public SoftwareSystemPage SoftwareReleasesPageReleasePage() {

		getWebElement("//span[text()='Software Releases']").click();	
		String val = "//span[text()='Releases']";
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(val)));
		return this;
	}

	@Step("Verify & Getting System Name from 360 window")
	public String systemName() {
		return getWebElement("//div[@class='qtr-margin-left text-xlarge half-margin-bottom']']").getText();
	}

	@Step("Verify & Getting ipAddress from 360 window")
	public String ipAddress() {
		return getWebElement("//div[@class='qtr-margin-left text-medium']//span[@class='qtr-margin-left']").getText();
	}

	@Step("verify Software Release are unique")
	public void uniqueSWRelease() {
		int countVal;
		String countval="//span[text()='Software Releases']/../../div/span";
		WebElement count= getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(countval)));
		int softwareReleasesCount = Integer.parseInt(count.getText());

		System.out.println("Software Releases Count value is " + softwareReleasesCount);
		SoftwareSystemPage SoftwareSystemPage = new SoftwareSystemPage();

		List<String> swReleaseTab = new ArrayList<>();
		int tableColumnCount =SoftwareSystemPage.getTableRowCount();
		System.out.println("tableColumnCount---->"+tableColumnCount);
		String columValue;
		for (int i = 1; i <= tableColumnCount; i++) {

			columValue = SoftwareSystemPage.getTableCellValue(i, 1);
			System.out.println("columValue--->"+columValue);
			if (columValue != null) {
				if (swReleaseTab.contains(columValue)) {
					System.out.println("Duplicate value ---->" + columValue);
				} else {
					swReleaseTab.add(SoftwareSystemPage.getTableCellValue(i,1));
				}
			}
		}
		countVal = swReleaseTab.size();
		System.out.println("Unique software Releases count taken from Grid is----------->"+countVal);
		Assert.assertEquals(softwareReleasesCount , countVal , "Unique Software Releases Count doesn't Match");
	}

	
}
