package com.example.dto;

public class MarketProductQuantity {
    private Long pid;
    private String productName;
    private Integer quantity;

    public MarketProductQuantity(Long pid, String productName, Integer quantity) {
        this.pid = pid;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
} 