package com.cisco.testdata.StaticData;

public enum TimeRangeFilterData {
    TWENTY_FOUR_24h("1"),//
    SEVEN_7d("2"),//
    THIRTY_30d("3"),//
    NINETY_90d("4");//

    private String rowNumber;

    private TimeRangeFilterData(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }


}
