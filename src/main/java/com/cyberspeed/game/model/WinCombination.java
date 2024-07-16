package com.cyberspeed.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.StringJoiner;

public class WinCombination {

    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    @JsonProperty("when")
    private String when;

    @JsonProperty("count")
    private int count;

    @JsonProperty("group")
    private String group;

    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;

    public WinCombination() {
    }

    public WinCombination(final double rewardMultiplier, final String when, final int count, final String group, final List<List<String>> coveredAreas) {
        this.rewardMultiplier = rewardMultiplier;
        this.when = when;
        this.count = count;
        this.group = group;
        this.coveredAreas = coveredAreas;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(final double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(final String when) {
        this.when = when;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public List<List<String>> getCoveredAreas() {
        return coveredAreas;
    }

    public void setCoveredAreas(final List<List<String>> coveredAreas) {
        this.coveredAreas = coveredAreas;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WinCombination.class.getSimpleName() + "[", "]")
                .add("rewardMultiplier=" + rewardMultiplier)
                .add("when='" + when + "'")
                .add("count=" + count)
                .add("group='" + group + "'")
                .add("coveredAreas=" + coveredAreas)
                .toString();
    }
}
