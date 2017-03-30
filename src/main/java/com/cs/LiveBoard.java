package com.cs;

import java.util.List;

public class LiveBoard {

    private final List<LiveBoardSale> sales;

    public LiveBoard(List<LiveBoardSale> sales) {
        this.sales = sales;
    }

    public List<LiveBoardSale> getSales() {
        return sales;
    }
}
