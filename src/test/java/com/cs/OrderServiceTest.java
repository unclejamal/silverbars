package com.cs;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderServiceTest {

    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        orderService = new OrderService();
    }

    @Test
    public void hasInitiallyNoSellOrders() throws Exception {
        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSellSummary(), is(empty()));
    }

    @Test
    public void hasInitiallyNoBuyOrders() throws Exception {
        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuySummary(), is(empty()));
    }

    @Test
    public void hasOneRegisteredBuyOrder() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 7.7, 707, OrderType.BUY));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuySummary(), equalTo(asList(new LiveBoardSummaryItem(7.7, 707))));
    }

    @Test
    public void hasOneRegisteredSellOrder() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 3.5, 303, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSellSummary(), equalTo(asList(new LiveBoardSummaryItem(3.5, 303))));
    }

    @Test
    public void addsTwoRegisteredBuyOrdersForSamePrice() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 7.7, 707, OrderType.BUY));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 8.8, 707, OrderType.BUY));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuySummary(), equalTo(asList(new LiveBoardSummaryItem(16.5, 707))));
    }

    @Test
    public void addsTwoRegisteredSellOrdersForSamePrice() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 3.5, 303, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 2.1, 303, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSellSummary(), equalTo(asList(new LiveBoardSummaryItem(5.6, 303))));
    }

    @Test
    public void sortsTwoRegisteredBuyOrdersForDifferentPricesByPriceInDescendingOrder() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 8.8, 888, OrderType.BUY));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 7.7, 777, OrderType.BUY));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getBuySummary(), equalTo(asList(new LiveBoardSummaryItem(8.8, 888), new LiveBoardSummaryItem(7.7, 777))));
    }

    @Test
    public void sortsTwoRegisteredSellOrdersForDifferentPricesByPriceInAscendingOrder() throws Exception {
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 2.2, 222, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 1.1, 111, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSellSummary(), equalTo(asList(new LiveBoardSummaryItem(1.1, 111), new LiveBoardSummaryItem(2.2, 222))));
    }

    @Test
    public void cancelsOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        orderService.registerOrder(new Order(orderId, "userId2", 1.1, 111, OrderType.SELL));

        orderService.cancelOrder(orderId);

        LiveBoard liveBoard = orderService.getLiveBoard();
        assertThat(liveBoard.getSellSummary(), is(empty()));
    }
}
