package com.cs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class OrderService {

    private List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    public LiveBoard getLiveBoard() {

        return new LiveBoard(
                calculateLiveBoardFor(OrderType.SELL, comparing(LiveBoardSale::getPriceInGbpPerKilogram)),
                calculateLiveBoardFor(OrderType.BUY, comparing(LiveBoardSale::getPriceInGbpPerKilogram).reversed())
        );
    }

    private List<LiveBoardSale> calculateLiveBoardFor(OrderType type, Comparator<LiveBoardSale> comparator) {
        Map<Integer, Double> priceToWeightMap = orders.stream()
                .filter(order -> type == order.getOrderType())
                .collect(groupingBy(order -> order.getPriceInGbpPerKilogram(),
                        summingDouble(order -> order.getWeightInKilograms()))
                );

        return priceToWeightMap.entrySet().stream()
                .map(priceAndWeight -> new LiveBoardSale(priceAndWeight.getValue(), priceAndWeight.getKey()))
                .sorted(comparator)
                .collect(toList());
    }

    public void registerOrder(Order order) {
        orders.add(order);
    }
}
