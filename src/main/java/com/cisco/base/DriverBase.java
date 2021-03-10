package com.cisco.base;

import com.cisco.config.DriverFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverBase {

    private static final List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static final Logger logger = Logger.getLogger(DriverBase.class);
    private static ThreadLocal<DriverFactory> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThread.get().getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public static void closeDriverInstance() {
        logger.info("Closing the Driver instance");
        driverThread.get().quitDriver();
    }

    @BeforeSuite(alwaysRun = true)
    public void instantiateDriverObject() {
        logger.info("Setting Up the Web Driver Object");
        driverThread = ThreadLocal.withInitial(() -> {
            DriverFactory webDriverThread = new DriverFactory();
            webDriverThreadPool.add(webDriverThread);
            return webDriverThread;
        });
    }

    @BeforeMethod(alwaysRun = true)
    public void launchBrowser() {
        driverThread.get().getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        logger.info("Clearing up the Cookies...");
        try {
            getDriver().manage().getCookies().stream().filter(cookie -> !cookie.getName().contains("authorization"))
                    .forEach(getDriver().manage()::deleteCookie);
        } catch (Exception ex) {
            logger.error("Unable to delete cookies: " + ex);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void closeDriverObjects() {
        logger.info("Closing All Driver Objects");
        for (DriverFactory webDriverThread : webDriverThreadPool) {
            if (webDriverThread != null)
                webDriverThread.quitDriver();
        }
    }

}
