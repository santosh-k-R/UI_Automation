package com.cisco.testdata.StaticData;

public enum SeverityIcon {
    S1("danger"), //
    S2("warning"), //
    S3("warning-alt"), //
    S4("info");

    private String severityIcon;

    SeverityIcon(String severityIcon) {
        this.severityIcon = severityIcon;
    }

    public String getSeverityIcon() {
        return severityIcon;
    }

    public void setSeverityIcon(String severityIcon) {
        this.severityIcon = severityIcon;
    }
}
