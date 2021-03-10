package com.cisco.testdata.StaticData;

public class FilterValue {

    private String filterTitle;
    private ChartType chartType;
    private String filterValue;

    public FilterValue(String filterTitle, ChartType chartType, String filterValue) {
        this.filterTitle = filterTitle;
        this.chartType = chartType;
        this.filterValue = filterValue;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }
}
