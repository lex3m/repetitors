package com.kitapp.repetitor.entities;

/**
 * Created by denis on 9/20/17.
 */

public class PriceRange {
    private int startPrice;
    private int endPrice;

    public PriceRange(int startPrice, int endPrice) {
        this.startPrice = startPrice;
        this.endPrice = endPrice;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    @Override
    public String toString() {
        if (startPrice == -1 && endPrice == -1) return "Все цены";
        String s = "";
        if (startPrice > 0) {
            s += "от " + String.valueOf(startPrice) + " ";
        }
        if (endPrice != -1 && endPrice > startPrice) {
            s += "до " + String.valueOf(endPrice);
        }
        return s;
    }
}
