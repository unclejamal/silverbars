package com.cs;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AcceptanceTest {
    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        orderService = new OrderService();
    }


//    3) Get summary information of live orders (see explanation below)
//    Imagine we have received the following orders:
//    a) SELL: 3.5 kg for £306 [user1]
//    b) SELL: 1.2 kg for £310 [user2]
//    c) SELL: 1.5 kg for £307 [user3]
//    d) SELL: 2.0 kg for £306 [user4]
//
//    Our ‘Live Order Board’ should provide us the following summary information:
//            5.5 kg for £306 // order a + order d
//    1.5 kg for £307 // order c
//    1.2 kg for £310 // order b

    //    The first thing to note here is that orders for the same price should be merged together
//    (even when they are from different users).
//    In this case it can be seen that order a) and d) were for the same amount (£306) and this is why only their sum (5.5 kg) is displayed (for £306)
//    and not the individual orders (3.5 kg and 2.0 kg).The last thing to note is that for SELL orders the orders with lowest prices are displayed first.
//    Opposite is true for the BUY orders.

    @Test
    public void requirement3_GetSummaryInformationOfLiveSellOrders() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "user1", 3.5, 306, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user2", 1.2, 310, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user3", 1.5, 307, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user4", 2.0, 306, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSellSummary(), equalTo(asList(
                new LiveBoardSummaryItem(5.5, 306),
                new LiveBoardSummaryItem(1.5, 307),
                new LiveBoardSummaryItem(1.2, 310)
        )));
    }

    @Test
    public void requirement3_GetSummaryInformationOfLiveBuyOrders() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "user1", 3.5, 306, OrderType.BUY));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user2", 1.2, 310, OrderType.BUY));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user3", 1.5, 307, OrderType.BUY));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user4", 2.0, 306, OrderType.BUY));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuySummary(), equalTo(asList(
                new LiveBoardSummaryItem(1.2, 310),
                new LiveBoardSummaryItem(1.5, 307),
                new LiveBoardSummaryItem(5.5, 306)
        )));
    }

}
