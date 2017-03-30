package com.cs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LiveBoardTest {


    @Test
    public void hasInitiallyNoSales() throws Exception {
        OrderService orderService = new OrderService();

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), Matchers.empty());
    }

    @Test
    public void hasOneRegisteredSale() throws Exception {
        OrderService orderService = new OrderService();
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 3.5, 303, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(new LiveBoardSale(3.5, 303))));
    }

//    @Test
//    public void addsTwoRegisteredSalesForSamePrice() throws Exception {
//        OrderService orderService = new OrderService();
//        UUID orderId1 = UUID.randomUUID();
//        UUID orderId2 = UUID.randomUUID();
//        orderService.registerOrder(new Order(orderId1, "userId1", 3.5, 303, OrderType.SELL));
//        orderService.registerOrder(new Order(orderId2, "userId2", 2.1, 303, OrderType.SELL));
//
//        LiveBoard liveBoard = orderService.getLiveBoard();
//
//        assertThat(liveBoard.getSales(), equalTo(asList(new Sale(orderId1, "userId1", 5.6, 303, OrderType.SELL))));
//    }

    public enum OrderType {
        SELL
    }

    public static class LiveBoard {

        private final List<LiveBoardSale> sales;

        public LiveBoard(List<LiveBoardSale> sales) {
            this.sales = sales;
        }

        public List<LiveBoardSale> getSales() {
            return sales;
        }
    }

    public static class LiveBoardSale {

        private final double weightInKilograms;
        private final int priceInGbpPerKilogram;

        public LiveBoardSale(double weightInKilograms, int priceInGbpPerKilogram) {
            this.weightInKilograms = weightInKilograms;
            this.priceInGbpPerKilogram = priceInGbpPerKilogram;
        }

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static class OrderService {

        private List<Order> orders;

        public OrderService() {
            orders = new ArrayList<>();
        }

        public LiveBoard getLiveBoard() {
            List<LiveBoardSale> liveBoardSales = orders.stream().map(o -> new LiveBoardSale(o.weightInKilograms, o.priceInGbpPerKilogram)).collect(toList());
            return new LiveBoard(liveBoardSales);
        }

        public void registerOrder(Order order) {
            orders.add(order);
        }
    }

    public static class Order {
        private final UUID uuid;
        private final String userId;
        private final double weightInKilograms;
        private final int priceInGbpPerKilogram;
        private final OrderType orderType;

        public Order(UUID uuid, String userId, double weightInKilograms, int priceInGbpPerKilogram, OrderType orderType) {
            this.uuid = uuid;
            this.userId = userId;
            this.weightInKilograms = weightInKilograms;
            this.priceInGbpPerKilogram = priceInGbpPerKilogram;
            this.orderType = orderType;
        }

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
