package com.cisco.testdata.StaticData;

public enum CarouselName {
    LIFECYCLE("LIFECYCLE"), //
    ASSETS_COVERAGE("ASSETS & COVERAGE"), //
    ADVISORIES("ADVISORIES"), //
    CASES("CASES"), //
    INSIGHTS("INSIGHTS");

    private String carouselName;

    CarouselName(String carouselName) {
        this.carouselName = carouselName;
    }

    public String getCarouselName() {
        return carouselName;
    }

    public void setCarouselName(String carouselName) {
        this.carouselName = carouselName;
    }
}
