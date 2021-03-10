package com.cisco.testdata.StaticData;

public enum CasesTableHeader {

    SEVERITY("Severity"), //
    CASE_NUMBER("Case Number"), //
    TITLE("Title"), //
    STATUS("Status"), //
    WITH_RMAS("With RMAs"), //
    AUTO_CREATED("Auto-Created"), //
    OWNER("Owner"), //
    UPDATED("Updated");

    private String tableHeader;

    CasesTableHeader(String casesTableHeader) {
        this.tableHeader = casesTableHeader;
    }

    public String getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(String tableHeader) {
        this.tableHeader = tableHeader;
    }
}
