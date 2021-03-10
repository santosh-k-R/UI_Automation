package com.cisco.pages.lifecycle;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.cisco.utils.LifeCycleUtils;

import io.qameta.allure.Step;

public class SuccessTipsPage extends LifeCycleUtils {
	
	public static final String ProductDocWebIconDataAutoIdInCardView = "SuccessBytesCard-WebIcon";
	public static final String ProductDocVideoIconDataAutoIdInCardView = "SuccessBytesCard-VideoIcon";
	public static final String ProductDocPDFIconDataAutoIdInCardView = "SuccessBytesCard-PDFIcon";
	public static final String ProductDocDataSheetIconDataAutoIdInCardView = "SuccessBytesCard-DataSheetIcon";
	
	public static final String SuccessBytesWebIconDataAutoIdInTableView = "TableView-WebIcon";
	public static final String SuccessBytesVideoIconDataAutoIdInTableView = "TableView-VideoIcon";
	public static final String SuccessBytesPDFIconDataAutoIdInTableView = "TableView-PDFIcon";
	public static final String SuccessBytesDataSheetIconDataAutoIdInTableView = "TableView-DataSheetIcon";
	
	public static final String ProductGuideWebIconDataAutoId = "ProductGuidesCard-WebIcon";
	public static final String ProductGuideVideoIconDataAutoId = "ProductGuidesCard-VideoIcon";
	public static final String ProductGuidePDFIconDataAutoId = "ProductGuidesCard-PDFIcon";
	public static final String ProductGuideDataSheetIconDataAutoId = "ProductGuidesCard-DataSheetIcon";
	
	@Step("Click Success Tips View All Link")
	public SuccessTipsPage clickSuccessTipsViewAllLink() {
		getWebElement("ShowModalPanel-_SuccessTips_").click();
		return this;
	}
	
	@Step("Get Window Title in View All Modal")
	public String getWindowTitleInViewAllModal() { 
		return getWebElement("ViewAllModal-Title").getText();
	}
	
	@Step("Click Success Tips Card View Modal Button")
	public SuccessTipsPage clickSuccessTipsCardModalButton() {
		getWebElement("sb-card-view-btn").click();
		return this;
	}
	
	@Step("Click Success Tips Table View Modal Button")
	public SuccessTipsPage clickSuccessTipsTableModalButton() {
		getWebElement("sb-table-view-btn").click();
		return this;
	}
	
	@Step("Click Product Documentation and Videos Card View Modal Button")
	public SuccessTipsPage clickProdDocAndVideosCardModalButton() {
		getWebElement("pg-card-view-btn").click();
		return this;
	}
	
	@Step("Get All Topic Under Success Tips")
	public void getAllTopicUnderSuccessTips() {
		List<WebElement> allTopics = getWebElements("//div[@data-auto-id='SBCard']");
		System.out.println("in getAllTopicUnderSuccessTips: "+allTopics.size());
		for(int topic=1; topic<=allTopics.size(); topic++) {
			//To Check Bookmark Ribbon in all the topics
			if(getWebElement("(//div[@data-auto-id='SBCard'])["+ topic +"]//button[@data-auto-id='SBCardRibbon']").isDisplayed());
			//To Check Arche Type
			if(getWebElement("(//div[@data-auto-id='SBCard'])["+ topic +"]//div[@data-auto-id='SBCardArchetype']").isDisplayed());
			//To Check Title of Success Tips
			if(getWebElement("(//div[@data-auto-id='SBCard'])["+ topic +"]//a[@data-auto-id='successtitle']").isDisplayed());
			//To Check Duration
			//if(getWebElement("(//div[@data-auto-id='SBCard'])["+ topic +"]//div[@class='duration text-muted']").isDisplayed());
			//To Check Description
			if(getWebElement("(//div[@data-auto-id='SBCard'])["+ topic +"]//div[@data-auto-id='SBCardDescription']").isDisplayed());
		}
	}
	
	@Step("Check Success Tips Display Total Number of Topics")
	public void getTotalNumberOfTopicsDisplayed() {
		List<WebElement> allTopics = getWebElements("//div[@data-auto-id='SBCard']");
		System.out.println("List Size: "+allTopics.size());
		if(getWebElement("//div[@class='text-left modal__body__topics cx-text-default']").getText().contains(allTopics.size() + " topics"))
			System.out.println("Total number of topics displayed: "+allTopics.size());
		else
			System.out.println(getWebElement("//div[@class='text-left modal__body__topics cx-text-default']").getText());
	}
	
	public int getTopicsOpenInNewTab(String locator) {
		getWebElement(locator).click();
		return getTotalNumberOfTabsOpened();
	}
	
	@Step("Get Topics from Home Page in New Tab")
	public int getNoOfTabsWhileClickingOnFirstTitleInHomePage() {
		return this.getTopicsOpenInNewTab("(//*[@data-auto-id='_SuccessTips_ -item.title'])[1]");
	}
	
	@Step("Get Product Documentation Topic Under Life Cycle Page")
	public int getAllProdDocTopicUnderLifeCycle() {
		return getWebElements("//*[@data-auto-id='_SuccessTips_ -item.title']").size();
	}

	@Step("Get All View buttons text")
    public List<String> getAllViewButtonText() {
		return getTextsFromWebElements("//*[@data-auto-id='LaunchButton']");
    }
	
	@Step("Get Texts from WebElements")
	public List<String> getTextsFromWebElements(String locator) {
        List<WebElement> allWebElements = getWebElements(locator);
        System.out.println("Total View Button Number Counts: "+allWebElements.size());
        List<String> allTexts = new ArrayList<>();
        for (WebElement button : allWebElements){
        	allTexts.add(button.getText());
        }
        return allTexts;
    }
	
	@Step("Get Total no of topics displayed")
	public int getTotalNoOfTopicsDisplayed(String locator) {
		return getWebElements("(//div[@data-auto-id='"+locator+"'])[1]//following::*[@data-auto-id='LaunchButton'][1]").size();
	}
	
	@Step("Check Topics Open in New Tab")
	public int getTopicsinNewTab(String locator) {
		return this.getTopicsOpenInNewTab("(//div[@data-auto-id='"+locator+"'])[1]//following::*[@data-auto-id='LaunchButton'][1]");
	}
	
	@Step("Get All Success Tips Topic Under Life Cycle Page")
	public int getAllSuccessTipsTopicUnderLifecycle() {
		return getWebElements("//div[@data-auto-id='successbytes-item']").size();
	}
	
	@Step("Click Product Doc and Videos")
	public SuccessTipsPage clickProdDocAndVideosButton() {
		getWebElement("ShowModalPanel-_ProductGuides_").click();
		return this;
	}
	
	@Step("Click on First Title in Card View")
	public int getNoOfTabsWhileClickingOnFirstTitleInCardView() {
		return this.getTopicsOpenInNewTab("(//div[@data-auto-id='SBCard'])[1]//preceding::div[1]/a");
	}
	
	@Step("Click on First Title in Table View")
	public int getNoOfTabsWhileClickingOnFirstTitleInTableView() {
		return this.getTopicsOpenInNewTab("(//*[@data-auto-id='SB-Name-rowValue'])[1]");
	}
	
	@Step("Get total number of topics displayed")
	public int getTotalNumberOfTopicsInTitle() {
		String[] topicsDisplayed = getWebElement("//div[@class='text-left modal__body__topics cx-text-default']").getText().split("\\s+");
		return Integer.parseInt(topicsDisplayed[0]);
	}
	
	@Step("Get Product Documentation and Videos Table View Button")
	public SuccessTipsPage getProductDocAndVideosTableViewButton() {
		getWebElement("pg-table-view-btn").click();
		return this;
	}
	
	@Step("Get Product Documentation and Videos Card View Button")
	public SuccessTipsPage getProductDocAndVideosCardViewButton() {
		getWebElement("pg-card-view-btn").click();
		return this;
	}
	
	@Step("Get Total Number of Topics in Card View for Product Guides")
	public int getTotalNoOfTopicsinCardView() {
		return getTotalSize("//*[@data-auto-id='PGCard']");
	}
	
	@Step("Get Size")
	public int getTotalSize(String locator) {
		return getWebElements(locator).size();
	}
	
	@Step("Verify All filter option in All Product Doc and Videos Table View")
	public void verifyFilterInAllProdDocAndVideosTableView() {
		SoftAssert softAssert = new SoftAssert();
		List<WebElement> allFilterElements = getWebElements("//div[@class='cui-virtual-scroll-content-wrapper']/div");
		for(int i = 2; i<=allFilterElements.size(); i++) {
			allFilterElements.get(i-1).click();
			softAssert.assertEquals(this.getTotalNumberOfTopicsInTitle(), this.getTableRowCount()-1);
			String filterText = getWebElement("//div[@class='cui-virtual-scroll-content-wrapper']/div["+i+"]/a").getText();
			List<WebElement> cardElements = getWebElements("//div[@data-auto-id='ViewAllTable-Category-rowValue']") ;
			for(WebElement element : cardElements) {
				softAssert.assertEquals(element.getText(), filterText);
			}
		}
		softAssert.assertAll();
	}
	
	@Step("Verify All filter option in All Product Doc and Videos Card View")
	public void verifyFilterInAllProdDocAndVideosCardView() {
		SoftAssert softAssert = new SoftAssert();
		List<WebElement> allFilterElements = getWebElements("//div[@class='cui-virtual-scroll-content-wrapper']/div");
		for(int i = 1; i<=allFilterElements.size(); i++) {
			allFilterElements.get(i-1).click();
			softAssert.assertEquals(this.getTotalNumberOfTopicsInTitle(), this.getTotalNoOfTopicsinCardView());
			String filterText = getWebElement("//div[@class='cui-virtual-scroll-content-wrapper']/div["+i+"]/a").getText();
			List<WebElement> cardElements = getWebElements("//*[@data-auto-id='PGCardArchetype']") ;
			for(WebElement element : cardElements) {
				softAssert.assertEquals(element.getText(), filterText);
			}
		}
		softAssert.assertAll();
	}
	
	@Step("Get Table Column Names for All Product Documentation and Videos Table View")
	public List<String> getAllTableColumnsName() {
		List<WebElement> allColumns = getWebElements("//div[@data-auto-id='ViewAllTable']//table//th");
		List<String> allColumnsName = new ArrayList<String>();
		for(int i = 1; i < allColumns.size(); i++) {
			allColumnsName.add(getWebElement("//div[@data-auto-id='ViewAllTable']//table//th["+i+"]/span").getText());
		}
		return allColumnsName;
	}
	
	@Step("Get Hover Over operations for Success Bytes Link")
	public void getHoverOverOperationOnSuccessBytesLink() {
		List<WebElement> successBytesLinks = getWebElements("//div[@data-auto-id='successbytes-item']");
		for(int i = 1; i <= successBytesLinks.size(); i++) {
			hoverOveraWebElement("(//div[@data-auto-id='successbytes-item'])[" + i + "]");
			assertTrue(getWebElement("(//div[@data-auto-id='successbytes-HoverModal-Description'])[" + i + "]").getText().length() > 0, "Success Bytes Hover Over Operation is not working");
		}
	}
	
	@Step("Get total number of title description displayed in Success Tips View All card modal")
	public int getTotalNoOfSuccessTipsCardDescription() {
		return getWebElements("//*[@data-auto-id='SBCardDescription']").size();
	}
	
	@Step("Get total number of title description displayed in Product Documentation and Videos View All card modal")
	public int getTotalNoOfProdDocAndVideosCardDescription() {
		return getWebElements("//*[@data-auto-id='PGCardDescription']").size();
	}
	
	@Step("Get Hyperlink for Success Tips Title")
	public String getHyperlinkInSuccessTipsTitle() {
		return getWebElement("(//*[@data-auto-id='successtitle'])[1]").getAttribute("href");
	}
	
	@Step("Check Hover Over opertation from Home Page")
	public boolean checkHoverOverFromHomePage() {
		boolean result = false;
		System.out.println(result);
		int totalNoofSuccessBytes = getWebElements("//div[@data-auto-id='successbytes-item']").size();
		String successBytesTitle = "";
		for(int i = 1; i <= totalNoofSuccessBytes; i++) {
			successBytesTitle = getWebElement("(//*[@data-auto-id='_SuccessTips_ -item.title'])[" + i + "]").getText();
			System.out.println("Title: "+successBytesTitle);
			this.hoverOveraWebElement("(//div[@data-auto-id='successbytes-item'])[" + i + "]");
			if(getWebElement("(//*[@data-auto-id='successbytes-HoverModal']/div[2]/span)[" + i + "]").getText().equalsIgnoreCase(successBytesTitle) && getWebElement("(//*[@data-auto-id='successbytes-HoverModal-Description'])[" + i + "]").getText().length() > 0)
				result = true;
		}
		System.out.println(result);
		return result;
	}
}
