package com.cyberspeed.game.game;

import com.cyberspeed.game.model.Config;
import com.cyberspeed.game.model.Probability;
import com.cyberspeed.game.model.WinCombination;
import com.cyberspeed.game.result.GameResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {
    private static final String SAME_SYMBOL_WINNING_COMBINATION = "same_symbol_%d_times";
    private static final String SAME_SYMBOL_HORIZONTALLY_WINNING_COMBINATION = "same_symbols_%s";
    private static final String SAME_SYMBOL_DIAGONAL_WINNING_COMBINATION = "same_symbols_diagonally_%s";

    private final Config config;
    private final String[][] matrix;

    public Game(Config config) {
        this.config = config;
        this.matrix = new String[config.getRows()][config.getColumns()];
    }

    public GameResult play(int betAmount) {
        initMatrix();
        printMatrix();
        return calculateReward(betAmount);
    }

    public void initMatrix() {
        fillStandardSymbols(matrix);
        placeSingleBonusSymbol(matrix);
    }

    private void fillStandardSymbols(String[][] matrix) {
        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                matrix[i][j] = getRandomStandardSymbol(i, j);
            }
        }
    }

    private String getRandomStandardSymbol(int row, int column) {
        Map<String, Integer> symbolProbabilities = config.getProbabilities().getStandardSymbols().get(0).getSymbols();

        for (Probability ssp : config.getProbabilities().getStandardSymbols()) {
            if (ssp.getRow() == row && ssp.getColumn() == column) {
                symbolProbabilities = ssp.getSymbols();
                break;
            }
        }

        return getRandomSymbolBasedOnProbabilities(symbolProbabilities);
    }

    private String getRandomSymbolBasedOnProbabilities(Map<String, Integer> symbolProbabilities) {
        int total = symbolProbabilities.values().stream().mapToInt(Integer::intValue).sum();
        int rand = new Random().nextInt(total);
        int cumulative = 0;

        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            cumulative += entry.getValue();
            if (rand < cumulative) {
                return entry.getKey();
            }
        }
        return null; // should not reach here
    }

    private void placeSingleBonusSymbol(String[][] matrix) {
        List<int[]> emptyCells = new ArrayList<>();

        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                emptyCells.add(new int[]{i, j});
            }
        }

        Collections.shuffle(emptyCells);
        int[] position = emptyCells.get(0);

        matrix[position[0]][position[1]] = getRandomBonusSymbol();
    }

    private String getRandomBonusSymbol() {
        return getRandomSymbolBasedOnProbabilities(config.getProbabilities().getBonusSymbols().getSymbols());
    }

    private void printMatrix() {
        for (String[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public GameResult calculateReward(int betAmount) {
        GameResult result = new GameResult(this.matrix);
        //double totalReward = 0;

        for (WinCombination combination : config.getWinCombinations().values()) {
            if (combination.getWhen().equals("same_symbols")) {
                checkSameSymbols(combination, betAmount, result);
            } else if (combination.getWhen().equals("linear_symbols")) {
                checkLinearSymbols(combination, betAmount, result);
            }
        }

        applyBonusSymbols(result);

        System.out.println("Reward: " + result.getReward());
        System.out.println("Applied Winning Combinations: " + result.getAppliedWinningCombinations());
        System.out.println("Applied Bonus Symbol: " + result.getAppliedBonusSymbol());

        return result;
    }

    private void checkSameSymbols(WinCombination combination, int betAmount, GameResult result) {
        Map<String, Integer> symbolCounts = new HashMap<>();

        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                String symbol = matrix[i][j];
                if (config.getSymbols().get(symbol).getType().equals("standard")) {
                    symbolCounts.put(symbol, symbolCounts.getOrDefault(symbol, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
            if (entry.getValue() >= combination.getCount()) {
                String symbol = entry.getKey();
                result.setReward((int)(result.getReward() + betAmount * config.getSymbols().get(symbol).getRewardMultiplier() * combination.getRewardMultiplier()));
                result.getAppliedWinningCombinations().computeIfAbsent(symbol, k -> new ArrayList<>()).add(String.format(SAME_SYMBOL_WINNING_COMBINATION, combination.getCount()));
            }
        }

    }

    private void checkLinearSymbols(WinCombination combination, int betAmount, GameResult result) {
        for (List<String> area : combination.getCoveredAreas()) {
            Map<String, Integer> symbolCounts = new HashMap<>();
            for (String cell : area) {
                int row = Integer.parseInt(cell.split(":")[0]);
                int col = Integer.parseInt(cell.split(":")[1]);
                String symbol = matrix[row][col];
                if (config.getSymbols().get(symbol).getType().equals("standard")) {
                    symbolCounts.put(symbol, symbolCounts.getOrDefault(symbol, 0) + 1);
                }
            }

            for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
                if (entry.getValue() == area.size()) {
                    String symbol = entry.getKey();
                    result.setReward((int)(result.getReward()+betAmount * config.getSymbols().get(symbol).getRewardMultiplier() * combination.getRewardMultiplier()));
                    result.getAppliedWinningCombinations().computeIfAbsent(symbol, k -> new ArrayList<>()).add(String.format(SAME_SYMBOL_HORIZONTALLY_WINNING_COMBINATION, combination.getGroup().startsWith("horizontally")?"horizontally":"vertically"));
                }
            }
        }
        if (combination.getGroup().contains("diagonally_linear_symbols")){
            for (List<String> area : combination.getCoveredAreas()) {
                String symbol = null;
                boolean win = true;

                for (String pos : area) {
                    String[] indices = pos.split(":");
                    int row = Integer.parseInt(indices[0]);
                    int col = Integer.parseInt(indices[1]);

                    if (symbol == null) {
                        symbol = matrix[row][col];
                    } else if (!matrix[row][col].equals(symbol)) {
                        win = false;
                        break;
                    }
                }

                if (win && config.getSymbols().get(symbol).getType().equals("standard")) {
                    result.setReward((int)(result.getReward() + betAmount * config.getSymbols().get(symbol).getRewardMultiplier() * combination.getRewardMultiplier())); ;
                    result.getAppliedWinningCombinations().computeIfAbsent(symbol, k -> new ArrayList<>()).add(String.format(SAME_SYMBOL_DIAGONAL_WINNING_COMBINATION, combination.getGroup().startsWith("ltr") ? "left_to_right" : "right_to_left"));
                }
            }
        }


    }

    private void applyBonusSymbols(GameResult result) {
        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                String symbol = matrix[i][j];
                if (config.getSymbols().get(symbol).getType().equals("bonus")) {
                    switch (config.getSymbols().get(symbol).getImpact()) {
                        case "multiply_reward":
                            result.setReward((int)(result.getReward()*config.getSymbols().get(symbol).getRewardMultiplier()));
                            result.setAppliedBonusSymbol(symbol);
                            break;
                        case "extra_bonus":
                            result.setReward(result.getReward()+config.getSymbols().get(symbol).getExtra());
                            result.setAppliedBonusSymbol(symbol);
                            break;
                        case "miss":
                            result.setAppliedBonusSymbol("MISS");
                            break;
                    }
                }
            }
        }
    }

    /*private int calculateReward(int betAmount) {
        Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
        List<String> appliedBonusSymbols = new ArrayList<>();

        double totalReward = 0;

        // Calculate reward for standard symbols
        for (Map.Entry<String, Symbol> symbolEntry : config.getSymbols().entrySet()) {
            String symbol = symbolEntry.getKey();
            Symbol symbolDetails = symbolEntry.getValue();
            if ("standard".equals(symbolDetails.getType())) {
                int symbolCount = countSymbol(symbol);
                for (Map.Entry<String, WinCombination> winEntry : config.getWinCombinations().entrySet()) {
                    WinCombination winCombination = winEntry.getValue();
                    if (winCombination.getWhen().equals("same_symbols") && winCombination.getCount() <= symbolCount) {
                        double reward = betAmount * symbolDetails.getRewardMultiplier() * winCombination.getRewardMultiplier();
                        totalReward += reward;
                        appliedWinningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(winEntry.getKey());
                    } else if (winCombination.getWhen().equals("linear_symbols")) {

                    }
                }
            }
        }

        // Apply bonus symbols if any winning combination is matched
        if (!appliedWinningCombinations.isEmpty()) {
            for (int i = 0; i < this.config.getRows(); i++) {
                for (int j = 0; j < this.config.getColumns(); j++) {
                    if (config.getProbabilities().getBonusSymbols().getSymbols().containsKey(matrix[i][j])) {
                        applyBonus(matrix[i][j], totalReward);
                        appliedBonusSymbols.add(matrix[i][j]);
                        break;
                    }
                }

            }
        }

        System.out.println("Applied Winning Combinations: " + appliedWinningCombinations);
        System.out.println("Applied Bonus Symbols: " + appliedBonusSymbols);

        return (int) totalReward;
    }*/

    /*private int countSymbol(String symbol) {
        int count = 0;
        for (String[] row : matrix) {
            for (String cell : row) {
                if (symbol.equals(cell)) {
                    count++;
                }
            }
        }
        return count;
    }

    private void applyBonus(String bonusSymbol, double totalReward) {
        Symbol bonus = config.getSymbols().get(bonusSymbol);
        switch (bonus.getImpact()) {
            case "multiply_reward":
                totalReward *= bonus.getRewardMultiplier();
                break;
            case "extra_bonus":
                totalReward += bonus.getExtra();
                break;
            case "miss":
                break; // Do nothing
        }
    }*/
}
