package com.cyberspeed.game.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class GameResult {
    private String[][] matrix;
    private int reward;
    private Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
    private String appliedBonusSymbol;

    public GameResult(String[][] matrix) {
        this.matrix = matrix;
    }



    public GameResult(final String[][] matrix, final int reward, final Map<String, List<String>> appliedWinningCombinations, final String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    @JsonProperty("matrix")
    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    @JsonProperty("reward")
    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    @JsonProperty("applied_winning_combinations")
    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public void setAppliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) {
        this.appliedWinningCombinations = appliedWinningCombinations;
    }

    @JsonProperty("applied_bonus_symbol")
    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    public void setAppliedBonusSymbol(String appliedBonusSymbol) {
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GameResult.class.getSimpleName() + "[", "]")
                .add("matrix=" + Arrays.toString(matrix))
                .add("reward=" + reward)
                .add("appliedWinningCombinations=" + appliedWinningCombinations)
                .add("appliedBonusSymbol='" + appliedBonusSymbol + "'")
                .toString();
    }
}
