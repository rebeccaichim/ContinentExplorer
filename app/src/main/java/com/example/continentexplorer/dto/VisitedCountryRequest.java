package com.example.continentexplorer.dto;

public class VisitedCountryRequest {
    private String visitedCountryName;
    private Long userId;
    private String countryVisitedDate;
    private Long continentId;

    public VisitedCountryRequest(String visitedCountryName, Long userId, String countryVisitedDate, Long continentId) {
        this.visitedCountryName = visitedCountryName;
        this.userId = userId;
        this.countryVisitedDate = countryVisitedDate;
        this.continentId = continentId;
    }

    public String getVisitedCountryName() {
        return visitedCountryName;
    }

    public void setVisitedCountryName(String visitedCountryName) {
        this.visitedCountryName = visitedCountryName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCountryVisitedDate() {
        return countryVisitedDate;
    }

    public void setCountryVisitedDate(String countryVisitedDate) {
        this.countryVisitedDate = countryVisitedDate;
    }

    public Long getContinentId() {
        return continentId;
    }

    public void setContinentId(Long continentId) {
        this.continentId = continentId;
    }

    @Override
    public String toString() {
        return "VisitedCountryRequest{" +
                "visitedCountryName='" + visitedCountryName + '\'' +
                ", userId=" + userId +
                ", countryVisitedDate='" + countryVisitedDate + '\'' +
                ", continentId='" + continentId + '\'' +
                '}';
    }
}
