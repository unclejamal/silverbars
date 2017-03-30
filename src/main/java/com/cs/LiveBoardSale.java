package com.cs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LiveBoardSale {

    private final double weightInKilograms;
    private final int priceInGbpPerKilogram;

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
