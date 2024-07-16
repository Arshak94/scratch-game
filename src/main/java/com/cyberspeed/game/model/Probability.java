package com.cyberspeed.game.model;

import java.util.Map;
import java.util.StringJoiner;

public class Probability {
    private int column;
    private int row;
    private Map<String, Integer> symbols;

    public Probability(final int column, final int row, final Map<String, Integer> symbols) {
        this.column = column;
        this.row = row;
        this.symbols = symbols;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(final int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(final int row) {
        this.row = row;
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(final Map<String, Integer> symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Probability.class.getSimpleName() + "[", "]")
                .add("column=" + column)
                .add("row=" + row)
                .add("symbols=" + symbols)
                .toString();
    }
}
