package com.cs;

import java.util.List;

public class LiveBoard {

    private final List<LiveBoardSummaryItem> sellSummary;
    private final List<LiveBoardSummaryItem> buySummary;

    public LiveBoard(List<LiveBoardSummaryItem> sellSummary, List<LiveBoardSummaryItem> buySummary) {
        this.sellSummary = sellSummary;
        this.buySummary = buySummary;
    }

    public List<LiveBoardSummaryItem> getSellSummary() {
        return sellSummary;
    }

    public List<LiveBoardSummaryItem> getBuySummary() {
        return buySummary;
    }
}
