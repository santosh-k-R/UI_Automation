package com.cisco.testdata.StaticData;

public enum  PitStopsName {
    Onboard("Racetrack-Point-Onboard", 1),
    Implement("Racetrack-Point-Implement", 2),
    Use("Racetrack-Point-Use", 3),
    Engage("Racetrack-Point-Engage",4 ),
    Adopt("Racetrack-Point-Adopt",5 ),
    Optimize("Racetrack-Point-Optimize",6);


    public String getPitStopName() {
        return pitStopName;
    }

    public int getPitStopIndex() {
        return pitStopIndex;
    }

    private String pitStopName;
    private int pitStopIndex;
    PitStopsName(String name, int pitStopIndex) {
        this.pitStopName = name;
        this.pitStopIndex = pitStopIndex;
    }
}
