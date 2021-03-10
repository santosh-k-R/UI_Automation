package com.cisco.testdata.StaticData;

public enum ButtonName {

	ATTACH_FILE("Attach File"), //
    ADD_NOTE("Add Note"), //
    OPEN_A_CASE("Open a Case"), //
    VIEW_DEVICE_DETAILS("View Device Details"), //
    VIEW_CASE_DETAILS("View Case Details"), //
    OPEN_CASE("Open Case"), //
    VIEW_ADVISORY("View Advisory"), //
    ADD("Add"), //
    VIEW_BUG_DETAIL("View Bug Detail"), //
    VIEW_NOTICE("View Notice"), //
    ATTACH("Attach"), //
    NEXT("Next"), //
    ACCEPT("ACCEPT"), //
    TRY_IT_OUT("Try it out "), //
    SUBMIT("Submit"), //
    REQUEST_1_ON_1("Request a 1-on-1"), //
    EXECUTE("Execute"), //
    VIEW_SESSIONS("View Sessions"), //
    WATCH_NOW("Watch Now"), //
    CONTINUE_OPENING_CASE("Continue Opening Case"), //
    CONTINUE("Continue"), //
    SWITCH_CONTINUE(" Continue "), //
    DONE("Done"), //
    VIEW_CASE("View Case"), //
    ALL_OPEN_CASES("All Open Cases"), //
    MY_OPEN_CASES("My Open Cases"), //
    CANCEL_OPENING_CASE("Cancel Opening Case"), //
    CLOSE("Close"), //
    CANCEL("Cancel"), //
    VIEW_ALL_OPEN_CASES("View All Open Cases"),
    VIEW("View"),
    SAVE("Save"),
    SELECT("SELECT"), //
    SELECT1("Select"), //
    CONTRACT_DETAILS("Contract Details"), //
    LAUNCH_COURSE("Launch Course");

    private String buttonName;

    ButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}