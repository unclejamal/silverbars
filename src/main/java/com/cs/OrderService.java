package com.cs;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class OrderService {

    private List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    public LiveBoard getLiveBoard() {

        return new LiveBoard(
                calculateLiveBoardFor(OrderType.SELL, comparing(LiveBoardItem::getPriceInGbpPerKilogram)),
                calculateLiveBoardFor(OrderType.BUY, comparing(LiveBoardItem::getPriceInGbpPerKilogram).reversed())
        );
    }

    private List<LiveBoardItem> calculateLiveBoardFor(OrderType type, Comparator<LiveBoardItem> comparator) {
        Map<Integer, Double> priceToWeightMap = orders.stream()
                .filter(order -> type == order.getOrderType())
                .collect(groupingBy(Order::getPriceInGbpPerKilogram,
                        summingDouble(Order::getWeightInKilograms))
                );

        return priceToWeightMap.entrySet().stream()
                .map(priceAndWeight -> new LiveBoardItem(priceAndWeight.getValue(), priceAndWeight.getKey()))
                .sorted(comparator)
                .collect(toList());
    }

    public void registerOrder(Order order) {
        orders.add(order);
    }

    public void cancelOrder(UUID orderId) {
        List<Order> ordersAfterRemoval = orders.stream()
                .filter(order -> !Objects.equals(orderId, order.getUuid()))
                .collect(Collectors.toList());
        this.orders = ordersAfterRemoval;
    }
}
