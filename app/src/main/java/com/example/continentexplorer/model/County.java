package com.example.continentexplorer.model;

public class County {
    private Long countyId;
    private String countyName;
    private String countyAbbreviation;

    public County() {
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyAbbreviation() {
        return countyAbbreviation;
    }

    public void setCountyAbbreviation(String countyAbbreviation) {
        this.countyAbbreviation = countyAbbreviation;
    }

}
