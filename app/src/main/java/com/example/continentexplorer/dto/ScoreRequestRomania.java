package com.example.continentexplorer.dto;

public class ScoreRequestRomania {
    private Long userId;
    private Long gameId;
    private Long countyId;
    private int attemptNumber;
    private double pointsAwarded;
    private double totalScore;
    private boolean isFinalAttempt;
    private Long attemptTime;


    public ScoreRequestRomania(Long userId, Long gameId, Long countyId, int attemptNumber, double pointsAwarded, double totalScore, boolean isFinalAttempt, Long attemptTime) {
        this.userId = userId;
        this.gameId = gameId;
        this.countyId = countyId;
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

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
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
