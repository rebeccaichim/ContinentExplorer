package com.example.continentexplorer.dto;

import com.example.continentexplorer.model.ScoreCountiesGame;
import com.example.continentexplorer.model.ScoreCountriesGame;

import java.util.List;

public class CombinedScoresResponse {
    private List<ScoreCountiesGame> romaniaScores;
    private List<ScoreCountriesGame> europaScores;

    // Getteri și setteri
    public List<ScoreCountiesGame> getRomaniaScores() {
        return romaniaScores;
    }

    public void setRomaniaScores(List<ScoreCountiesGame> romaniaScores) {
        this.romaniaScores = romaniaScores;
    }

    public List<ScoreCountriesGame> getEuropaScores() {
        return europaScores;
    }

    public void setEuropaScores(List<ScoreCountriesGame> europaScores) {
        this.europaScores = europaScores;
    }
}

