package com.cisco.testdata.StaticData;

public enum SeverityRequestType {
    S1("System Down"), //
    S2("Severely Degraded"), //
    S3("System Impaired"), //
    S4("Ask a Question");

    private String severityRequestType;

    SeverityRequestType(String severityRequestType) {
        this.severityRequestType = severityRequestType;
    }

    public String getSeverityRequestType() {
        return severityRequestType;
    }

    public void setSeverityRequestType(String severityRequestType) {
        this.severityRequestType = severityRequestType;
    }
}
