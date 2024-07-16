package com.cyberspeed.game.model;

import java.util.StringJoiner;

public class Symbol {
    private double rewardMultiplier;
    private String type;
    private String impact;
    private int extra;

    public Symbol(final double rewardMultiplier, final String type, final String impact, final int extra) {
        this.rewardMultiplier = rewardMultiplier;
        this.type = type;
        this.impact = impact;
        this.extra = extra;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(final double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(final String impact) {
        this.impact = impact;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(final int extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Symbol.class.getSimpleName() + "[", "]")
                .add("rewardMultiplier='" + rewardMultiplier + "'")
                .add("type='" + type + "'")
                .add("impact='" + impact + "'")
                .add("extra=" + extra)
                .toString();
    }
}