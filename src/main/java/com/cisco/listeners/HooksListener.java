package com.cisco.listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.cisco.base.DriverBase.getDriver;
import static com.cisco.utils.Commons.compress;

public class HooksListener implements IHookable {
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            try {
                byte[] input = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                Allure.addAttachment(testResult.getName(), new ByteArrayInputStream(compress(input, output, 0.3f)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
