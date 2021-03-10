package com.cisco.testdata.StaticData;

public enum DNAC_DropdownValue {

    ALL_CATEGORIES("All"), //
    DNA_CENTER_APPLIANCES("DNA");

    private String dnac_dropdown;

    DNAC_DropdownValue(String dnac_dropdown) {
        this.dnac_dropdown = dnac_dropdown;
    }

    public String getDnac_dropdown() {
        return dnac_dropdown;
    }

    public void setDnac_dropdown(String dnac_dropdown) {
        this.dnac_dropdown = dnac_dropdown;
    }
}
