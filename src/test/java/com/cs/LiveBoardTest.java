package com.cs;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;

public class LiveBoardTest {


    @Test
    public void hasInitiallyNoSales() throws Exception {
        OrderService orderService = new OrderService();

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), Matchers.empty());
    }

    public static class LiveBoard {
        public List<Sale> getSales() {
            return asList();
        }
    }

    public static class Sale {
    }

    public static class OrderService {

        public LiveBoard getLiveBoard() {
            return new LiveBoard();
        }
    }
}
