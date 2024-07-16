package com.cyberspeed.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.StringJoiner;

public class Probabilities {

    @JsonProperty("standard_symbols")
    private List<Probability> standardSymbols;

    @JsonProperty("bonus_symbols")
    private Symbols bonusSymbols;

    public Probabilities() {
    }

    public Probabilities(final List<Probability> standardSymbols, final Symbols bonusSymbols) {
        this.standardSymbols = standardSymbols;
        this.bonusSymbols = bonusSymbols;
    }

    public List<Probability> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(final List<Probability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public Symbols getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(final Symbols bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Probabilities.class.getSimpleName() + "[", "]")
                .add("standardSymbols=" + standardSymbols)
                .add("symbols=" + bonusSymbols)
                .toString();
    }
}
