package com.example.continentexplorer.dto;

public class ScoreRequestEuropa {
    private Long userId;
    private Long gameId;
    private Long countryId;
    private int attemptNumber;
    private double pointsAwarded;
    private double totalScore;
    private boolean isFinalAttempt;
    private Long attemptTime;


    public ScoreRequestEuropa(Long userId, Long gameId, Long countryId, int attemptNumber, double pointsAwarded, double totalScore, boolean isFinalAttempt, Long attemptTime) {
        this.userId = userId;
        this.gameId = gameId;
        this.countryId = countryId;
        this.attemptNumber = attemptNumber;
        this.pointsAwarded = pointsAwarded;
        this.totalScore = totalScore;
        this.isFinalAttempt = isFinalAttempt;
        this.attemptTime = attemptTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public double getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(double pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isFinalAttempt() {
        return isFinalAttempt;
    }

    public void setFinalAttempt(boolean finalAttempt) {
        isFinalAttempt = finalAttempt;
    }

    public Long getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(Long attemptTime) {
        this.attemptTime = attemptTime;
    }
}
