package com.cisco.testdata.StaticData;

public enum CustomerActivity {

    CONFIGURATION("Configuration"), //
    INSTALLATION("Installation"), //
    OPERATE("Operate"), //
    UPGRADE("Upgrade");

    private String customerActivity;

    CustomerActivity(String customerActivity) {
        this.customerActivity = customerActivity;
    }

    public String getCustomerActivity() {
        return customerActivity;
    }

    public void setCustomerActivity(String customerActivity) {
        this.customerActivity = customerActivity;
    }
}
