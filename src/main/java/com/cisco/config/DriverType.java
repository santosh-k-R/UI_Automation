package com.cisco.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ThreadGuard;

public enum DriverType implements DriverSetup {


    FIREFOX {
        public WebDriver getWebDriverObject() {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = DriverSetup.getFireFoxOptions();
            return new FirefoxDriver(options);
        }
    },
    CHROME {
        public WebDriver getWebDriverObject() {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = DriverSetup.getChromeOptions();
            return new ChromeDriver(options);
        }
    },
    IE {
        public WebDriver getWebDriverObject() {
            WebDriverManager.iedriver().setup();
            InternetExplorerOptions options = DriverSetup.getInternetExplorerOptions();
            return new InternetExplorerDriver(options);
        }
    },
    EDGE {
        public WebDriver getWebDriverObject() {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = DriverSetup.getEdgeOptions();
            return new EdgeDriver(options);
        }
    },
    SAFARI {
        public WebDriver getWebDriverObject() {
            SafariOptions options = DriverSetup.getSafariOptions();
            return new SafariDriver(options);
        }
    },
    OPERA {
        public WebDriver getWebDriverObject() {
            WebDriverManager.operadriver().setup();
            OperaOptions options = DriverSetup.getOperaOptions();
            return new OperaDriver(options);
        }
    };

}