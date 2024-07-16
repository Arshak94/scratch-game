package com.cyberspeed.game.model;

import java.util.Map;
import java.util.StringJoiner;

public class Symbols {
    private Map<String, Integer> symbols;

    public Symbols(final Map<String, Integer> symbols) {
        this.symbols = symbols;
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(final Map<String, Integer> symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Symbols.class.getSimpleName() + "[", "]")
                .add("bonusSymbols=" + symbols)
                .toString();
    }
}
