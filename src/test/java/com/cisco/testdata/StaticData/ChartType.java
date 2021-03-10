package com.cisco.testdata.StaticData;

public enum ChartType {
    PIE("pie"),//
    BUBBLE("bubble"),//
    COLUMN("column"),//
    BAR("bar");

    private String chartType;

    ChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartTypeValue() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
}
