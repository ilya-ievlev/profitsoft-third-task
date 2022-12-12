package com.profitsoft.first_task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SortedOutputDataTransferObject {

    @JsonProperty("fineForSpeeding")
    private final Map<Integer, Double> fineAmountsForSpeeding;
    @JsonProperty("fineForDrunkDriving")
    private final Map<Integer, Double> fineAmountsForDrunkDriving;
    @JsonProperty("fineForRedLight")
    private final Map<Integer, Double> fineAmountsForRedLight;

    public SortedOutputDataTransferObject(Map<Integer, Double> fineAmountsForSpeeding,
                                          Map<Integer, Double> fineAmountsForDrunkDriving,
                                          Map<Integer, Double> fineAmountsForRedLight) {
        this.fineAmountsForSpeeding = fineAmountsForSpeeding;
        this.fineAmountsForDrunkDriving = fineAmountsForDrunkDriving;
        this.fineAmountsForRedLight = fineAmountsForRedLight;
    }
}
