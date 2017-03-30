package com.cs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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

    @Test
    public void addsTwoRegisteredSalesForSamePrice() throws Exception {
        OrderService orderService = new OrderService();
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 3.5, 303, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 2.1, 303, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(new LiveBoardSale(5.6, 303))));
    }

    @Test
    public void sortsTwoRegisteredSalesForDifferentPricesByPriceInAscendingOrder() throws Exception {
        OrderService orderService = new OrderService();
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId1", 2.2, 222, OrderType.SELL));
        orderService.registerOrder(new Order(UUID.randomUUID(), "userId2", 1.1, 111, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(new LiveBoardSale(1.1, 111), new LiveBoardSale(2.2, 222))));
    }

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

        public final double weightInKilograms;
        public final int priceInGbpPerKilogram;

        public LiveBoardSale(double weightInKilograms, int priceInGbpPerKilogram) {
            this.weightInKilograms = weightInKilograms;
            this.priceInGbpPerKilogram = priceInGbpPerKilogram;
        }

        public double getWeightInKilograms() {
            return weightInKilograms;
        }

        public int getPriceInGbpPerKilogram() {
            return priceInGbpPerKilogram;
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
            Map<Integer, Double> priceToWeightMap = orders.stream()
                    .collect(Collectors.groupingBy(order -> order.getPriceInGbpPerKilogram(),
                            Collectors.summingDouble(order -> order.getWeightInKilograms()))
                    );

            List<LiveBoardSale> liveBoardSales = priceToWeightMap.entrySet().stream()
                    .map(priceAndWeight -> new LiveBoardSale(priceAndWeight.getValue(), priceAndWeight.getKey()))
                    .sorted(Comparator.comparing(liveBoardSale -> liveBoardSale.priceInGbpPerKilogram))
                    .collect(toList());

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

        public double getWeightInKilograms() {
            return weightInKilograms;
        }

        public int getPriceInGbpPerKilogram() {
            return priceInGbpPerKilogram;
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
