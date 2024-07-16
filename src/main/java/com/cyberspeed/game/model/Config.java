package com.cyberspeed.game.model;


import java.util.Map;
import java.util.StringJoiner;

public class Config {
    private int columns;
    private int rows;
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;
    private Map<String, WinCombination> winCombinations;

    public Config(final int columns, final int rows, final Map<String, Symbol> symbols, final Probabilities probabilities, final Map<String, WinCombination> winCombinations) {
        this.columns = columns;
        this.rows = rows;
        this.symbols = symbols;
        this.probabilities = probabilities;
        this.winCombinations = winCombinations;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(final int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(final int rows) {
        this.rows = rows;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(final Map<String, Symbol> symbols) {
        this.symbols = symbols;
    }

    public Probabilities getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(final Probabilities probabilities) {
        this.probabilities = probabilities;
    }

    public Map<String, WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(final Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Config.class.getSimpleName() + "[", "]")
                .add("columns=" + columns)
                .add("rows=" + rows)
                .add("symbols=" + symbols)
                .add("probabilities=" + probabilities)
                .add("winCombinations=" + winCombinations)
                .toString();
    }
}
