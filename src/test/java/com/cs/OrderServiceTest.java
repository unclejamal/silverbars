package com.cs;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OrderServiceTest {

    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        orderService = new OrderService();
    }

    @Test
    public void hasInitiallyNoSales() throws Exception {
        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), Matchers.empty());
    }

    @Test
    public void hasInitiallyNoBuys() throws Exception {
        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuys(), Matchers.empty());
    }

    @Test
    public void hasOneRegisteredBuy() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 7.7, 707, OrderType.BUY));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuys(), equalTo(asList(new LiveBoardSale(7.7, 707))));
    }

    @Test
    public void hasOneRegisteredSale() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 3.5, 303, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(new LiveBoardSale(3.5, 303))));
    }

    @Test
    public void addsTwoRegisteredBuysForSamePrice() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 7.7, 707, OrderType.BUY));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 8.8, 707, OrderType.BUY));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuys(), equalTo(asList(new LiveBoardSale(16.5, 707))));
    }

    @Test
    public void addsTwoRegisteredSalesForSamePrice() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 3.5, 303, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 2.1, 303, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(new LiveBoardSale(5.6, 303))));
    }

    @Test
    public void sortsTwoRegisteredBuysForDifferentPricesByPriceInAscendingOrder() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 8.8, 888, OrderType.BUY));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 7.7, 777, OrderType.BUY));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuys(), equalTo(asList(new LiveBoardSale(8.8, 888), new LiveBoardSale(7.7, 777))));
    }

    @Test
    public void sortsTwoRegisteredSalesForDifferentPricesByPriceInAscendingOrder() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 2.2, 222, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 1.1, 111, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(new LiveBoardSale(1.1, 111), new LiveBoardSale(2.2, 222))));
    }

    @Test
    public void cancelsOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        orderService.registerOrder(new Order(orderId, "userId2", 1.1, 111, OrderType.SELL));

        orderService.cancelOrder(orderId);

        LiveBoard liveBoard = orderService.getLiveBoard();
        assertThat(liveBoard.getSales(), Matchers.empty());
    }
}
