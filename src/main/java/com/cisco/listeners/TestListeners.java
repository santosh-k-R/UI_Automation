package com.cisco.listeners;

import com.cisco.base.DriverBase;
import com.cisco.base.DriverUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners extends DriverBase implements ITestListener {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TestListeners.class);


    @Override
    public void onTestStart(ITestResult result) {
        //unused
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("********************************************************************************************");
        System.out.println(result.getMethod().getMethodName() + " has passed");
        logger.info(result.getMethod() + "--Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("********************************************************************************************");
        System.out.println(result.getMethod().getMethodName() + " has failed");
        logger.error(result.getMethod() + "--Failed", result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("********************************************************************************************");
        System.out.println(result.getMethod().getMethodName() + " has been skipped");
        logger.info(result.getMethod() + "--Skipped", result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //unused
    }

    @Override
    public void onStart(ITestContext context) {
        //unused
    }

    @Override
    public void onFinish(ITestContext context) {
        //unused
    }
}
