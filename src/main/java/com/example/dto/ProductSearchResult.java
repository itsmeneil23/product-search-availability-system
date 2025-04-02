package com.example.dto;

public class ProductSearchResult {
    private String marketName;
    private Integer quantity;

    public ProductSearchResult(String marketName, Integer quantity) {
        this.marketName = marketName;
        this.quantity = quantity;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
} 