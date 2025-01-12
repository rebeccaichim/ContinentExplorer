package com.example.continentexplorer.dto;

public class VisitedCountyRequest {
    private String visitedCountyName;
    private Long countryId;
    private String countyVisitedDate;
    private Long userId;


    public VisitedCountyRequest(String visitedCountyName, Long countryId, String countyVisitedDate, Long userId) {
        this.visitedCountyName = visitedCountyName;
        this.countryId = countryId;
        this.countyVisitedDate = countyVisitedDate;
        this.userId = userId;
    }

    public String getVisitedCountyName() {
        return visitedCountyName;
    }

    public void setVisitedCountyName(String visitedCountyName) {
        this.visitedCountyName = visitedCountyName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountyVisitedDate() {
        return countyVisitedDate;
    }

    public void setCountyVisitedDate(String countyVisitedDate) {
        this.countyVisitedDate = countyVisitedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VisitedCountyRequest{" +
                "visitedCountyName='" + visitedCountyName + '\'' +
                ", countryId=" + countryId +
                ", countyVisitedDate='" + countyVisitedDate + '\'' +
                ", userId=" + userId +
                '}';
    }
}
