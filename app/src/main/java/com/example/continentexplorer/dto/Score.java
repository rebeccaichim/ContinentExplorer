package com.example.continentexplorer.dto;

public class Score {
    private int gameId;
    private String attemptTime;
    private double totalScore;
    private boolean fromCountiesGame;
    private int attemptNumber;
    private boolean isFinalAttempt;
    private double pointsAwarded;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(String attemptTime) {
        this.attemptTime = attemptTime;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isFromCountiesGame() {
        return fromCountiesGame;
    }

    public void setFromCountiesGame(boolean fromCountiesGame) {
        this.fromCountiesGame = fromCountiesGame;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public boolean isFinalAttempt() {
        return isFinalAttempt;
    }

    public void setFinalAttempt(boolean finalAttempt) {
        isFinalAttempt = finalAttempt;
    }

    public double getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(double pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }
}
