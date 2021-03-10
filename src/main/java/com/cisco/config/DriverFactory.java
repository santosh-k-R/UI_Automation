package com.cisco.config;

import org.apache.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;

import java.net.MalformedURLException;
import java.net.URL;

import static com.cisco.config.DriverType.CHROME;
import static com.cisco.config.DriverType.valueOf;


public class DriverFactory {

    private static final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private static final Logger logger = Logger.getLogger(DriverFactory.class);
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
    private WebDriver webDriver;
    private DriverType selectedDriverType;

    public DriverFactory() {
        DriverType driverType = CHROME;
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public WebDriver getDriver() {
        if (null == webDriver) {
            instantiateWebDriver(selectedDriverType);
        }
        return webDriver;
    }

    public void quitDriver() {
        if (null != webDriver) {
            try {
                webDriver.quit();
            } catch (WebDriverException e) {
                logger.info("Unable to quit the driver, it may be closed already");
            } finally {
                webDriver = null;
            }
        }
    }

    private boolean isDriverHealthy() {
        try {
            webDriver.getWindowHandles();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            webDriver = null;
            logger.info("Driver died, creating new instance");
            return false;
        }
    }

    private void instantiateWebDriver(DriverType driverType) {

        logger.info("Local Operating System: " + operatingSystem);
        logger.info("Local Architecture: " + systemArchitecture);
        logger.info("Selected Browser: " + selectedDriverType);

        String seleniumToken = System.getProperty("seleniumBoxToken");
        if (seleniumToken.isEmpty()) {
            //Token generated for Siddharth
            seleniumToken = "c6a23c26-0497-4a";
        }

        if (useRemoteWebDriver) {
            DesiredCapabilities desiredCapabilities =  new DesiredCapabilities(DriverSetup.getDesiredCapability(selectedDriverType.name()));

            URL  seleniumGridURL = null;
            try {
                seleniumGridURL = new URL(System.getProperty("gridURL"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
//            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
//            String desiredPlatform = System.getProperty("desiredPlatform");

//            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
//                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
//            }
//
//            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
//                desiredCapabilities.setVersion(desiredBrowserVersion);
//            }

//            desiredCapabilities.setVersion(desiredBrowserVersion);
            desiredCapabilities.setCapability("enableVNC", true);
            desiredCapabilities.setCapability("enableVideo", true);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            desiredCapabilities.setCapability("e34:token", seleniumToken);
            desiredCapabilities.setCapability("e34:video", true);
            desiredCapabilities.setCapability("pageLoadStrategy", PageLoadStrategy.NONE);
            webDriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            webDriver = driverType.getWebDriverObject();
        }
        logger.info("Successfully launched the browser");
    }
}
