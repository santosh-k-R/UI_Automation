package com.cisco.tests.sp2k;


import com.cisco.base.DriverBase;
import com.cisco.pages.sp2k.PreventiveMaintenancePage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PreventiveMaintenanceIT extends DriverBase {
    @BeforeClass(description = "Verify Login to SP2K")
    public void verifyLoginToSP2K() {
        PreventiveMaintenancePage preventiveMaintenancePage = new PreventiveMaintenancePage();
        preventiveMaintenancePage.login();
        assertEquals("M & W Data Entry", preventiveMaintenancePage.homePageTitle());
    }

    @Test(description = "Verify Sp2k Main Menu Pages List", priority = 1)
    public void checkPreventiveMaintenancePageLink() {
        PreventiveMaintenancePage preventiveMaintenancePage = new PreventiveMaintenancePage();
        ArrayList expectedPages = new ArrayList();
        expectedPages.add("Preventive Maintenance");
        expectedPages.add("Quality Assurance");
        expectedPages.add("Resource Tracking");
        expectedPages.add("Labor");
        expectedPages.add("Safety");
        expectedPages.add("Audit Report");
        System.out.println("Actual Pages:-"+preventiveMaintenancePage.mainMenuPagesList());
        assertEquals(expectedPages, preventiveMaintenancePage.mainMenuPagesList());
        //assertTrue(preventiveMaintenancePage.mailMenuPagesList(), "Preventive Maintenance Page Link is Available");
    }

    @Test(description = "verify and clicking on the Preventive Maintenance Page Link", priority = 2)
    public void clickPreventiveMaintenancePageLink() {
        PreventiveMaintenancePage preventiveMaintenancePage = new PreventiveMaintenancePage();
       assertEquals("Preventive Maintenance", preventiveMaintenancePage.clickPreventiveMaintenanceLink());
    }
    /*@Test(description = "verify PreventiveMaintenance Logout",priority = 3)
    public void clickLogout(){
        PreventiveMaintenancePage preventiveMaintenancePage = new PreventiveMaintenancePage();
        preventiveMaintenancePage.logout();
    }*/
}