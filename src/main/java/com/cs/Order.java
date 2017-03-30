package com.cs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

public class Order {
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

    public UUID getUuid() {
        return uuid;
    }

    public double getWeightInKilograms() {
        return weightInKilograms;
    }

    public int getPriceInGbpPerKilogram() {
        return priceInGbpPerKilogram;
    }

    public OrderType getOrderType() {
        return orderType;
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
