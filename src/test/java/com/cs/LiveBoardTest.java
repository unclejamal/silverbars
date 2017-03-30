package com.cs;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
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
        UUID orderId = UUID.randomUUID();
        orderService.registerOrder(new Order(orderId, "userId1", 3.5, 303, OrderType.SELL));

        LiveBoard liveBoard = orderService.getLiveBoard();

        assertThat(liveBoard.getSales(), equalTo(asList(new Order(orderId, "userId1", 3.5, 303, OrderType.SELL))));
    }

    public enum OrderType {
        SELL
    }

    public static class LiveBoard {

        private final List<Order> sales;

        public LiveBoard(List<Order> sales) {
            this.sales = sales;
        }

        public List<Order> getSales() {
            return sales;
        }
    }

    public static class Sale {
    }

    public static class OrderService {

        private List<Order> orders;

        public OrderService() {
            orders = new ArrayList<>();
        }

        public LiveBoard getLiveBoard() {
            return new LiveBoard(orders);
        }

        public void registerOrder(Order order) {
            orders.add(order);
        }
    }

    public static class Order {
        private final UUID uuid;
        private final String userId;
        private final double weightInKilograms;
        private final int pricePerKilogram;
        private final OrderType orderType;

        public Order(UUID uuid, String userId, double weightInKilograms, int pricePerKilogram, OrderType orderType) {
            this.uuid = uuid;
            this.userId = userId;
            this.weightInKilograms = weightInKilograms;
            this.pricePerKilogram = pricePerKilogram;
            this.orderType = orderType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Order order = (Order) o;

            if (Double.compare(order.weightInKilograms, weightInKilograms) != 0) return false;
            if (pricePerKilogram != order.pricePerKilogram) return false;
            if (uuid != null ? !uuid.equals(order.uuid) : order.uuid != null) return false;
            if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
            return orderType == order.orderType;

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = uuid != null ? uuid.hashCode() : 0;
            result = 31 * result + (userId != null ? userId.hashCode() : 0);
            temp = Double.doubleToLongBits(weightInKilograms);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + pricePerKilogram;
            result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "uuid=" + uuid +
                    ", userId='" + userId + '\'' +
                    ", weightInKilograms=" + weightInKilograms +
                    ", pricePerKilogram=" + pricePerKilogram +
                    ", orderType=" + orderType +
                    '}';
        }
    }
}
