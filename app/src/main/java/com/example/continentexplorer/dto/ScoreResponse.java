package com.example.continentexplorer.dto;

public class ScoreResponse {
    private int attemptsLeft;
    private double totalScore;
    private boolean isGameOver;

    public ScoreResponse(int attemptsLeft, double totalScore, boolean isGameOver) {
        this.attemptsLeft = attemptsLeft;
        this.totalScore = totalScore;
        this.isGameOver = isGameOver;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}