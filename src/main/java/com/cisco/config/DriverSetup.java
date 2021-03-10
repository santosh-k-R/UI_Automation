package com.cisco.config;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;

public interface DriverSetup {

    boolean HEADLESS = Boolean.getBoolean("headless");
    String operatingSystem = System.getProperty("os.name").toUpperCase();

    WebDriver getWebDriverObject();

    static Capabilities getDesiredCapability(String browser) {
        switch (browser.toUpperCase()) {
            case "CHROME":
                return getChromeOptions();
            case "FIREFOX":
                return getFireFoxOptions();
            case "IE":
                return getInternetExplorerOptions();
            case "EDGE":
                return getEdgeOptions();
            case "OPERA":
                return getOperaOptions();
            case "SAFARI":
                return getSafariOptions();
            default:
                throw new RuntimeException("Invalid browser option specified ");
        }
    }

    static ChromeOptions getChromeOptions() {
        HashMap<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.password_manager_enabled", false);
        ChromeOptions options = new ChromeOptions();
        if (operatingSystem.contains("WINDOWS")) {
            options.addArguments("start-maximized");
        } else if (operatingSystem.contains("MAC")) {
            options.addArguments("--window-size=1920,1080");
        }
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.addArguments("--no-sandbox");
        options.addArguments("--no-default-browser-check");
        options.setExperimentalOption("prefs", chromePreferences);
        options.addArguments("incognito");
        String browserVersion = System.getProperty("browserVersion","");
        if (!browserVersion.isEmpty())
            options.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
        options.setHeadless(HEADLESS);

        return options;
    }

    static FirefoxOptions getFireFoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(HEADLESS);
        return options;
    }

    static InternetExplorerOptions getInternetExplorerOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
        options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        return options;
    }

    static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        return options;
    }

    static SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        return options;
    }

    static OperaOptions getOperaOptions() {
        OperaOptions options = new OperaOptions();
        return options;
    }
}