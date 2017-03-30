package com.cs;

import org.junit.Test;

import java.util.UUID;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AcceptanceTest {

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
    @Test
    public void requirement3_GetSummaryInformationOfLiveOrders() throws Exception {
        OrderService orderService = new OrderService();
        orderService.registerOrder(new Order(UUID.randomUUID(), "user1", 3.5, 306, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user2", 1.2, 310, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user3", 1.5, 307, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "user4", 2.0, 306, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(
                new LiveBoardSale(5.5, 306),
                new LiveBoardSale(1.5, 307),
                new LiveBoardSale(1.2, 310)
        )));
    }

}
