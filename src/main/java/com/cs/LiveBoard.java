package com.cs;

import java.util.List;

public class LiveBoard {

    private final List<LiveBoardItem> sales;
    private final List<LiveBoardItem> buys;

    public LiveBoard(List<LiveBoardItem> sales, List<LiveBoardItem> buys) {
        this.sales = sales;
        this.buys = buys;
    }

    public List<LiveBoardItem> getSales() {
        return sales;
    }

    public List<LiveBoardItem> getBuys() {
        return buys;
    }
}
