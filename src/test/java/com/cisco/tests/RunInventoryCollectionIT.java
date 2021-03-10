package com.cisco.tests;

import com.cisco.base.DriverBase;
import com.cisco.pages.inventoryCollection.InventoryPages;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RunInventoryCollectionIT extends DriverBase {

    @Test
    public void runInventoryCollection() {
        InventoryPages inventoryPages = new InventoryPages();
        String inventoryFileName, collUUID;

        inventoryFileName = inventoryPages.triggerAPI();
        collUUID = inventoryPages.verifySimsMonitor(inventoryFileName);

        Assert.assertTrue(inventoryPages.callElasticSearch(collUUID), "pfm_inventory_notification collection not updated successfully");
    }
}
