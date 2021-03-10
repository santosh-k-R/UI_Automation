package com.cisco.testdata.StaticData;

public enum UseCaseName {
    NETWORK_DEVICE_ONBOARDING("Network Device Onboarding"), //
    CAMPUS_NETWORK_SEGEMENTATION("Campus Network Segmentation"), //
    CAMPUS_NETWORK_ASSURANCE("Campus Network Assurance"), //
    SCALABLE_ACCESS_POLICY("Scalable Access Policy"), //
    CAMPUS_SOFTWARE_IMAGE_MANAGEMENT("Campus Software Image Management");

    private String useCaseName;

    public String getUseCaseName() {
        return useCaseName;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }

    UseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }
}
