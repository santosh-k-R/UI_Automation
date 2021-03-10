package com.cisco.pages.lifecycle;

import org.openqa.selenium.By;
import java.text.Collator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import com.cisco.pages.CxHomePage;
import com.cisco.utils.AppUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cisco.base.DriverBase;
import com.cisco.utils.LifeCycleUtils;

import io.qameta.allure.Step;

public class LifecyclePage extends LifeCycleUtils {
	String text;
	boolean status;
	
	@Step("get the usecase count in the pitstops from portfolio racetrack ")
    public String getRacetrackcount(String name){
		try {
		text=getWebElement(".//*[local-name() = 'g'][@data-stage='"+name+"']//*[local-name() = 'text']").getText();
		}catch (NoSuchElementException e) {}
		if(text.isEmpty()) {
			text="0";
		}
		return text;
    }
	@Step("Get total number of usecases on portfolio page")
    public int getTotalNoOfUsecasesDisplayed() {
    	return getWebElements("//div[contains(@class,'usecase flex')]").size();
    }
	@Step("Get Total number of usecases in pitstops")
    public HashMap<String, Integer> getTotalNoOfUsecasePitstop() {
    	HashMap<String, Integer> pitstopCount = new HashMap<String, Integer>();
//    	int totalNoOfUsecases = getTotalNoOfUsecasesDisplayed();
    	pitstopCount.put("Purchase", 0);
    	pitstopCount.put("Onboard", 0);
    	pitstopCount.put("Implement", 0);
    	pitstopCount.put("Use", 0);
    	pitstopCount.put("Engage", 0);
    	pitstopCount.put("Adopt", 0);
    	pitstopCount.put("Optimize", 0);
    	List<WebElement>usecases=getWebElements("//span[contains(@class,'primary text')]");
       	for(WebElement usecase:usecases) {
    		String usecase1 = usecase.getAttribute("innerHTML");
    	    usecase1=usecase1.replaceAll("[^\\w\\s\\-_]", "");
			pitstopCount.put(usecase1, pitstopCount.get(usecase1)+1);
    	}
    	return pitstopCount;
    }
	@Step("Get total number of successtrack")
    public int getTotalNoOfsuccesstrack() {
    	return getWebElements("//div[contains(@class,'track-title')]").size();
    }
	@Step("Get successtrack names")
    public HashMap<String, String> getSuccesstrackNames() {
		HashMap<String, String> adoptionPercentage = new HashMap<String, String>();
//		status=isElementPresent("//span[text()='ADOPTION JOURNEY']");
//		if(status!=true) {
//			refreshportfolio();
//		}
		getWebElement("//button[@data-auto-id='add-filter']").click();
		List<WebElement>successtracks=getWebElements("//div[@class='filter-item']/label");
		for(int j=1;j<=successtracks.size();j++) {
			text=getWebElement("//div[@class='filter-item']["+j+"]/label").getText();
			getWebElement("//div[@class='filter-item']/input").click();
			getWebElement("//button[@data-auto-id='add-filter']").click();
			List<WebElement>usecases=getWebElements("//div[@class='filter-item']/label");
			for(int i=1;i<=usecases.size();i++) {
				text=getWebElement("//div[@class='filter-item']["+i+"]/label").getText();
				getWebElement("//div/label[text()=' "+text+" ']/../input").click();
				String percentage=getWebElement("//span[text()='LIFECYCLE']/..//*[local-name() = 'text'][@class='value']").getText().concat("%");
				adoptionPercentage.put(text, percentage);
				if(i!=5) {
				getWebElement("//button[@data-auto-id='filter1']").click();
				}
			}
			WebElement close = getWebElement("remove-filter-0");
			getActions().moveToElement(close).build().perform();
			close.click();
			getWebElement("//button[@data-auto-id='add-filter']").click();
		}

    	return adoptionPercentage;
    }
	@Step("Get usecase completion perecnatge and name from portfolio tile")
    public HashMap<String, String> getUsecasePercentageAtPortfolioPage() {
    	HashMap<String, String> usecasePercentageAtPortfolio = new HashMap<String, String>();
//    	int totalNoOfUsecases = getTotalNoOfUsecasesDisplayed();
    	int SuccesstracksCount = getTotalNoOfsuccesstrack();
    	for(int i=1;i<=SuccesstracksCount;i++) {
    		List<WebElement>usecaseTitles=getWebElements("//div[contains(@class,'track-title')]["+i+"]/../..//div[contains(@class,'usecase-title')]/span");
    		for(WebElement usecase:usecaseTitles) {
    			text=usecase.getText();
    			String percentage=getWebElement("//div/span[text()='"+text+"']/../following-sibling::div/span").getText();
    			usecasePercentageAtPortfolio.put(text, percentage);
    		}
    		    	}
      	return usecasePercentageAtPortfolio;
    }
	@Step("verify default view in portfolio")
    public boolean verifyDefaultView() {

		status=isElementPresent("//singleselect[@data-auto-id='CAV-BU-MultiFilter']");
		if(status) {
			getWebElement("//singleselect[@data-auto-id='CAV-BU-MultiFilter']/div/input").click();
			List<WebElement>buids=getWebElements("//singleselect[@data-auto-id='CAV-BU-MultiFilter']//div[contains(@class,'list__item')]/div");
			text=getWebElement("//singleselect[@data-auto-id='CAV-BU-MultiFilter']//div[contains(@class,'item--selected')]/div").getText();
			String[]buIds=new String[buids.size()];
			for (int k = 0; k < buids.size(); k++) {
				buIds[k]=buids.get(k).getText();
			}
			Collator myCollator = Collator.getInstance();
			for (int i = 0; i < buIds.length; i++)
	        {
	            for (int j = i + 1; j < buIds.length; j++) {
	            	if (myCollator.compare(buIds[i],buIds[j])<0)
	                {
	                    String temp = buIds[i];
	                    buIds[i] = buIds[j];
	                    buIds[j] = temp;
	                }
	            }
	        }
			status=text.equals(buIds[0]);
		}else {
			throw new SkipException("Logged in with single buid user. Hence can't test this scenario");
		}

		getWebElement("//span[@data-auto-id='MultiSelect-DropdownToggle']").click();
      	return status;
    }
	@Step("Verify filter in portfolio page")
    public boolean verifyFilterPortfolio() {
		HashMap<String, Integer> bupitstopCount = new HashMap<String, Integer>();
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-auto-id='usecase-row']")));
		status=isElementPresent("//singleselect[@data-auto-id='CAV-BU-MultiFilter']");
		if(status) {
			getWebElement("//singleselect[@data-auto-id='CAV-BU-MultiFilter']/div/input").click();
			List<WebElement>buids=getWebElements("//singleselect[@data-auto-id='CAV-BU-MultiFilter']//div[contains(@class,'list__item')]/div");
			text=getWebElement("//singleselect[@data-auto-id='CAV-BU-MultiFilter']//div[contains(@class,'item--selected')]/div").getText();
			HashMap<String, Integer> selectedBuPitstopcount=getTotalNoOfUsecasePitstop();
			for(WebElement buid:buids) {
				if(!text.equals(buid.getText())) {
					getWebElement("//div[text()='"+buid.getText()+"']/..").click();
					bupitstopCount=getTotalNoOfUsecasePitstop();
					break;
				}
			}
			status=!selectedBuPitstopcount.equals(bupitstopCount);
		}else {
			throw new SkipException("User logged on using Single BU ID. Hence there is no data available to test this scenario");
		}

    	return status;
    }
	@Step("Get usecase completion perecnatge and name from AllUsecase page")
    public HashMap<String, String> getUsecasePercentageAtAllUsecasePage() {
    	HashMap<String, String> usecasePercentageAtAllUsecase = new HashMap<String, String>();
    	getWebElement("//div[@data-auto-id='all-usecase-link']/span").click();
    		List<WebElement>usecaseTitles=getWebElements("//div[@class='usecase-card']/header");
    		for(WebElement usecase:usecaseTitles) {
    			text=usecase.getText();
    			String percentage=getWebElement("//header[text()='"+text+"']/../section/div[2]/div[1]").getText().concat("%");
    			usecasePercentageAtAllUsecase.put(text, percentage);
    		}
      	return usecasePercentageAtAllUsecase;
    }
	@Step("verify the successtracks present in the portfolio page")
    public boolean verifyportfoliosuccesstracks() {
		List<WebElement>SuccesstracksOnPortfolio=getWebElements("//div[contains(@class,'track-title')]/span");
		int successtrckNoPortfolio=SuccesstracksOnPortfolio.size();
    		for(WebElement Successtrack:SuccesstracksOnPortfolio) {
    			text=Successtrack.getText();
    			status=isElementPresent("//span[text()='"+text+"']/../../parent::div[contains(@class,'container base')]");
    		}
    	getWebElement("add-filter").click();
    	List<WebElement>SuccesstracksOnFilter=getWebElements("//div[contains(@class,'filter-item')]");
    	int successtrckNoFilter=SuccesstracksOnFilter.size();
       	return successtrckNoPortfolio==successtrckNoFilter;
    }
	@Step("verify portfolio title and overall progress click functionality")
    public boolean verifyportflioTitle() {

    	status=isElementPresent("//span[text()='ADOPTION JOURNEY']");
    	getActions().moveToElement(getWebElement("CXP-Icons/Help")).build().perform();
    	String messge="Percentage based on total completed checklist items in the Lifecycle journey";
    	text=getWebElement("//div[@class='help-msg']/span").getText();
        return status&&(messge.equals(text));
    }
	@Step("verify portfolio subtitle ")
    public boolean verifyFilterTitleportflio() throws InterruptedException {
   		status=isElementPresent("//singleselect[@data-auto-id='CAV-BU-MultiFilter']");
		if(status) {
			getWebElement("//singleselect[@data-auto-id='CAV-BU-MultiFilter']/div/input").click();
			List<WebElement>buids=getWebElements("//singleselect[@data-auto-id='CAV-BU-MultiFilter']//div[contains(@class,'list__item')]/div");
			text=getWebElement("//singleselect[@data-auto-id='CAV-BU-MultiFilter']//div[contains(@class,'item--selected')]/div").getText();
			status=isElementPresent("//span[text()='"+text+"']");
			for(WebElement buid:buids) {
				if(!text.equals(buid.getText())) {
					String value=buid.getText();
					getWebElement("//div[text()='"+buid.getText()+"']").click();
                    text=getWebElement("//*[contains(@class,'bu-title')]/span").getText();
					status=text.equals(value);
					break;
				}
			}

		}else {
			throw new SkipException("User logged on using Single BU ID. Hence there is no data available to test this scenario");
		}

    	return status;
	}
	@Step("Verify filter in Home page")
    public boolean verifyFilterHome() {
		status=isElementPresent("SINGLESELECT-3");
		if(status) {
			getWebElement("//*[@id='SINGLESELECT-3']/..").click();
			List<WebElement>buids=getWebElements("//*[@id='SINGLESELECT-3']/following-sibling::div/*/*");
			text=getWebElement("//*[@id='SINGLESELECT-3']/following-sibling::div/*/*[contains(@class,'selected')]/div").getText();
			String percent1=getWebElement("//span[text()='LIFECYCLE']/..//*[local-name() = 'text'][@class='value']").getText();
			for(WebElement buid:buids) {
				if(!text.equals(buid.getText())) {
					getWebElement("//div[text()='"+buid.getText()+"']/..").click();
					text=getWebElement("//span[text()='LIFECYCLE']/..//*[local-name() = 'text'][@class='value']").getText();
					status=percent1.equals(text);
					break;
				}
			}
		}else {
			throw new SkipException("User logged on using Single BU ID. Hence there is no data available to test this scenario");
		}

    	return status;
    }
	@Step("verify tooltip text portfolio raw")
    public boolean getTooltipPortfolioRaw(){
	    getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'track-header')]/following-sibling::div[1]//div/img[@data-auto-id='-Image']")));
	    WebElement element = getWebElement("//div[contains(@class,'track-header')]/following-sibling::div[1]//div/img[@data-auto-id='-Image']");
        //getActions().clickAndHold().moveToElement(element);
		getActions().moveToElement(element).build().perform();
        String[]arr=new String[2];
        for(int i=0;i<2;i++) {
        	i++;
        	text=getWebElement("//div[contains(@class,'track-header')]/following-sibling::div[1]//div[contains(@class,'item-text')]/span["+i+"]").getText();
        	 arr[--i]=text;
        }
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(getWebElement("//div[contains(@class,'track-header')]/following-sibling::div[1]/div[2]")));
        getWebElement("//div[contains(@class,'track-header')]/following-sibling::div[1]/div[2]").click();
        waitForInitialPageToLoad();
        String[]arr1=new String[2];
        arr1[0]=getWebElement("//div[@class='row base-padding-top']/div[1]/div/*/*[1]").getText().concat(":");
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='row base-padding-top']/div[2]/div/*/*[contains(@class,'selected')]/div")));
        arr1[1]=getWebElement("//div[@class='row base-padding-top']/div[2]/div/*/*[contains(@class,'selected')]/div").getText();
        status=Arrays.equals(arr, arr1);
         return status;
    }




	@Step("Click on Lifecycle tab")
	public boolean checkLifecycleTabAvailability() {
		try {
			getWebElement("//span[contains(text(),'LIFECYCLE')]/..");
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}

	@Step("Check Success Track community Panel Displayed")
	public boolean checkSuccessTrackCommunityDisplayed() {
		return this.isElementPresent("PanelTitle-PrivateCommunities");
	}

	@Step("Check Public community Panel Displayed")
	public boolean checkPublicCommunityDisplayed() {
		return this.isElementPresent("PanelTitle-PublicCommunities");
	}

	@Step("Check Ask Q&A Panel Displayed")
	public boolean checkAskQandADisplayed() {
		return this.isElementPresent("ATXQnASessionTitle");
	}

	@Step("Check Ask The Expert Panel displayed")
	public boolean checkATXPanelDisplayed() {
		return this.isElementPresent("Ask the Experts Panel");
	}

	@Step("Check Accelerator Panel Displayed")
	public boolean checkACCPanelDisplayed() {
		return this.isElementPresent("Accelerators Panel");
	}

	@Step("Check Product Documentation Panel Displayed")
	public boolean checkProductDocumentationDisplayed() {
		return this.isElementPresent("Product Documentation Panel");
	}

	@Step("Check E-Learning Panel displayed")
	public boolean checkELearningPanelDisplayed() {
		return this.isElementPresent("LearnPanel-ELearningBlock");
	}

	@Step("Check Certification Prep Panel displayed")
	public boolean checkCertificationPrepDisplayed() {
		return this.isElementPresent("LearnPanel-CertificationsBlock");
	}

	@Step("Check Remote Practices Labs Panel displayed")
	public boolean checkRemotePracLabsDisplayed() {
		return this.isElementPresent("LearnPanel-RemotePracticeLabsBlock");
	}

	@Step("Check Pit Stop Checklist Displayed")
	public boolean checkPitstopChecklistDisplayed() {
		return this.isElementPresent("pitstopAction");
	}

	@Step("Check Admin Button is Enable")
	public boolean checkAdminButtonDisplayed() {
		return this.isElementPresent("Admin");
	}

	@Step("Check Cisco ACC availability")
	public int getTotalNoOfCiscoACC() {
		getWebElement("//*[@data-auto-id='MultiSelect-SearchInput' and contains(@placeholder,'Content Provider')]").click();
		getWebElement("//div[contains(@data-auto-id,'MultiSelect-ListItem') and text()='Cisco']").click();
		getWebElement("MultiSelect-SaveButton").click();
//		jsWaiter.waitAllRequest();
//		jsWaiter.waitUntilAngularReady();
		//System.out.println("Total No of Cisco ACC Available: "+getWebElements("//*[@data-auto-id='recommendedACC']").size());
		return getWebElements("//*[@data-auto-id='recommendedACC']").size();
	}

	@Step("Verify user able to check Manual Checklist for Read Only users")
	public boolean verifyPitstopActionsManagable() {
		boolean status = false;
		String checkBoxBeforeValue = getWebElement("//div[@class='checklist-status-icon']/div").getAttribute("class");
		getWebElement("icon-checklist-check-allow").click();
		String checkBoxAfterValue = getWebElement("//div[@class='checklist-status-icon']/div").getAttribute("class");
		if(!checkBoxBeforeValue.equalsIgnoreCase(checkBoxAfterValue))
			status = true;
		return status;
	}

	@Step("Verify Admin access for Technical Admin user")
	public boolean verifyAdminAccessForTechnicalAdmin() {
		boolean result = false;
		getWebElement("Admin").click();
		if(this.isElementPresent("sidebar.users"))
			result = true;
		getWebElement("xbutton").click();
		return result;
	}

	@Step("Get Highlighted Pitstop Data Auto ID")
	public String getHighlightedPitstopName() {
		if(getWebElements("//*[contains(@data-auto-id,'Racetrack-Point') and @r='12']").size() > 0) {
			String[] pitStopName = getWebElement("//*[contains(@data-auto-id,'Racetrack-Point') and @r='12']").getAttribute("data-auto-id").split("-");
			return pitStopName[2];
		} else
			return this.getCurrentPitStopName();
	}

	@Step("Get Generic Error Title")
	public String getUnauthorizedUserHeadline() {
		return getDriver().findElement(By.xpath("//*[@class='unauthorized-user__headline']")).getText();
	}

	@Step("Get Unauthorized Error Text")
	public String getUnathorizedErrorText() {
		return getDriver().findElement(By.xpath("//*[@class='unauthorized-user__copy']")).getText();
	}

	@Step("Get Unauthorized User Error Redirect")
	public String getLogoutUserRedirectWebsite() {
		return getDriver().findElement(By.xpath("//*[@data-auto-id='UnauthorizedPageLogOutSSO']")).getAttribute("href");
	}

	@Step("Login with Unauthorized Error")
	public void loginWithUnauthorizedError(String userRole) {
		String userName = System.getenv(userRole+"_username");
        String password = System.getenv(userRole+"_password");
        if (!getDriver().getTitle().equals("")) {
            DriverBase.closeDriverInstance();
            initializeDriverObjects();
        }
        String URL = System.getProperty("url");
        getDriver().navigate().to(URL);
        String userNameText = "//input[@name='pf.username']";
        String loginButton = "//input[@name='login-button'] | //*[text()='Sign in']";
        String passwordText = "//input[@name='password' or @name='pf.pass']";
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userNameText)));
        getDriver().findElement(By.xpath(userNameText)).sendKeys(userName);
        getDriver().findElement(By.xpath(loginButton)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordText)));
        getDriver().findElement(By.xpath(passwordText)).sendKeys(password);
        getDriver().findElement(By.xpath(loginButton)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='unauthorized-user__headline']")));
	}
	
	@Step("refresh the portfolio tile ")
	public LifecyclePage refreshportfolio(){
		if(!isElementPresent("//span[text()='ADOPTION JOURNEY']")) {
			WebElement refreshData = getWebElement("refresh-data");
			getActions().moveToElement(refreshData).build().perform();
			refreshData.click();
		}
		return this;
	}
	
	@Step("Get Success Track Name in My Portfolio Page")
	public String getSuccessTrackUnderMyPortfolio() {
		return this.getWebElement("//*[contains(@class,'track-title')]/span").getText();
	}

	@Step("Get first Use Case from My Portfolio Page")
	public String getFirstUseCaseNameUnderMyPortfolioPage() {
		return this.getWebElement("(//*[contains(@class,'usecase-title')]/span)[1]").getText();
	}

	@Step("Click on First Use Case in My Portfolio Page")
	public LifecyclePage clickOnFirstUseCaseUnderMyPortFolioPage() {
		getWebElement("(//*[contains(@class,'usecase-title')]/span)[1]").click();
		return this;
	}

	@Step("Get Success Track Name in Lifecycle Tile")
	public String getSuccessTrackNameinLifeCycleTile() {
		return this.getWebElement("//*[@data-auto-id='filter0']/span/span/span[2]").getText();
	}

	@Step("Get Use Case Name in Lifecycle Tile")
	public String getUseCaseNameinLifecycleTile() {
		return this.getWebElement("//*[@data-auto-id='filter1']/span/span/span[2]").getText();
	}

	@Step("Mouse Hover on Next Check List Item")
	public LifecyclePage mouseHoverOnFirstNextChecklistItem() {
		WebElement raceCarImage = this.getWebElement("(//*[@data-auto-id='-Image'])[1]");
		getActions().moveToElement(raceCarImage).build().perform();
		return this;
	}

	@Step("Get Next Checklist Item Pitstop")
	public String getNextCheckListItemPitStopForFirstUseCase() {
		String[] pitStopName = getWebElement("(//*[@class='next-checklist-item-text']/span[1])[1]").getText().split(":");
		return pitStopName[0];
	}

	@Step("Get Next Checklist Item")
	public String getNextCheckListItemForFirstUseCase() {
		return getWebElement("(//*[@class='next-checklist-item-text']/span[2])[1]").getText();
	}

	@Step("Get Selected Checklist from Lifecycle Page")
	public String getSelectedChecklistInLifecyclePage() {
		return getWebElement("//*[@class='selected-checklist-title']/div").getText();
	}

	@Step("Get Active Checklist Item under Checklist")
	public String getActiveCheckListItemFromLifeCyclePage() {
		return getWebElement("//*[contains(@class,'active-checklist-action')]/div/div/div[2]").getText();
	}

	@Step("Check Active Checklist Sub Text Displayed")
	public boolean checkActiveCheckListSubItemDisplayed() {
		try {
			getWebElement("//*[contains(@class,'active-checklist-action')]/div[2]/div/span").isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Step("Check Checklist Percentage Bar displayed")
	public boolean checkChecklistPercentageDisplayed() {
		return this.isElementPresent("//*[contains(@class,'checklist-progressfill')]") && this.isElementPresent("//*[contains(@class,'checklist-adoptionpercentage')]");
	}
}
