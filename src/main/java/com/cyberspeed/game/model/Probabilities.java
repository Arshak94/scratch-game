package com.cyberspeed.game.model;

import java.util.List;
import java.util.StringJoiner;

public class Probabilities {
    private List<Probability> standardSymbols;
    private Symbols bonusSymbols;

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
