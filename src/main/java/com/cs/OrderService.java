package com.cs;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class OrderService {

    private List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    public LiveBoard getLiveBoard() {
        return new LiveBoard(
                calculateLiveBoardItemsFor(OrderType.SELL, comparing(LiveBoardSummaryItem::getPriceInGbpPerKilogram)),
                calculateLiveBoardItemsFor(OrderType.BUY, comparing(LiveBoardSummaryItem::getPriceInGbpPerKilogram).reversed())
        );
    }

    private List<LiveBoardSummaryItem> calculateLiveBoardItemsFor(OrderType type, Comparator<LiveBoardSummaryItem> comparator) {
        Map<Integer, Double> priceToWeightMap = orders.stream()
                .filter(order -> type == order.getOrderType())
                .collect(groupingBy(Order::getPriceInGbpPerKilogram,
                        summingDouble(Order::getWeightInKilograms))
                );

        return priceToWeightMap.entrySet().stream()
                .map(priceAndWeight -> new LiveBoardSummaryItem(priceAndWeight.getValue(), priceAndWeight.getKey()))
                .sorted(comparator)
                .collect(toList());
    }

    public void registerOrder(Order order) {
        orders.add(order);
    }

    public void cancelOrder(UUID orderId) {
        List<Order> ordersAfterRemoval = orders.stream()
                .filter(order -> !Objects.equals(orderId, order.getOrderId()))
                .collect(toList());
        this.orders = ordersAfterRemoval;
    }
}
