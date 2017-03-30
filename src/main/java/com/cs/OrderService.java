package com.cs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class OrderService {

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
                .sorted(Comparator.comparing(liveBoardSale -> liveBoardSale.getPriceInGbpPerKilogram()))
                .collect(toList());

        return new LiveBoard(liveBoardSales);
    }

    public void registerOrder(Order order) {
        orders.add(order);
    }
}
