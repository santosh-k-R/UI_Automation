package com.cisco.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(IListenersAnnotation annotation, Class testClass) {
        //unused
    }

    @Override
    public void transform(IConfigurationAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        boolean serverCookie = Boolean.parseBoolean(System.getProperty("serverCookie"));
        if (serverCookie) {
            if (testMethod.getName().equals("clearCookies"))
                annotation.setEnabled(false);
        } else {
            if (testMethod.getName().equals("closeDriverInstance"))
                annotation.setEnabled(false);
        }

    }

    @Override
    public void transform(IDataProviderAnnotation annotation, Method method) {
        //unused
    }

    @Override
    public void transform(IFactoryAnnotation annotation, Method method) {
        //unused
    }

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class testClass, Constructor testConstructor, Method testMethod) {
        int maxRetryCount = Integer.parseInt(System.getProperty("maxretryCount"));
        String[] groups = iTestAnnotation.getGroups();
        if (maxRetryCount > 0) {
            IRetryAnalyzer retry = iTestAnnotation.getRetryAnalyzer();

            if (retry == null) {
                iTestAnnotation.setRetryAnalyzer(RetryListener.class);
            }
        }
        if (groups.length == 0)
            iTestAnnotation.setGroups(new String[]{"regression"});
        else {
            String[] mgroups = Arrays.copyOf(groups, groups.length + 1);
            mgroups[groups.length] = "regression";
            iTestAnnotation.setGroups(mgroups);
        }
        if (testMethod.getAnnotation(Test.class) != null) {
            iTestAnnotation.setTimeOut(600000);
        }
    }
}
