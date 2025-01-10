package com.example.continentexplorer.dto;

public class GuessRequest {
    private Long userId;
    private Long countyId;
    private String guess;

    public GuessRequest(Long userId, Long countyId, String guess) {
        this.userId = userId;
        this.countyId = countyId;
        this.guess = guess;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
