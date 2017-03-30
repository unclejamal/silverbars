package com.cs;

import java.util.List;

public class LiveBoard {

    private final List<LiveBoardSale> sales;
    private final List<LiveBoardSale> buys;

    public LiveBoard(List<LiveBoardSale> sales, List<LiveBoardSale> liveBoardSales) {
        this.sales = sales;
        this.buys = liveBoardSales;
    }

    public List<LiveBoardSale> getSales() {
        return sales;
    }

    public List<LiveBoardSale> getBuys() {
        return buys;
    }
}
