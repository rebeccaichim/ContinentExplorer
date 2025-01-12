package com.example.continentexplorer.model;

public class ScoreCountriesGame {
    private Long attemptId;
    private Long gameId;
    private Long userId;
    private int attemptNumber;
    private double pointsAwarded;
    private double totalScore;
    private boolean isFinalAttempt;
    private String attemptTime;

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(String attemptTime) {
        this.attemptTime = attemptTime;
    }
}
